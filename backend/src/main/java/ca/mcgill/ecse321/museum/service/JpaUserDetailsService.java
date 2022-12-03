/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Owner;
import ca.mcgill.ecse321.museum.model.Person;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Get the user from the database
        Person person = personRepository.findByEmail(username);
        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Get the roles of the user
        List<String> roles = new ArrayList<>();
        if (person instanceof Visitor) {

            roles.add("USER");
            roles.add("VISITOR");

            // Check if the user is active
            if (!((Visitor) person).isActive()) {
                throw new UsernameNotFoundException("User not found");
            }

        } else if (person instanceof Employee) {

            roles.add("USER");
            roles.add("EMPLOYEE");
            roles.add("ADMINISTRATOR");

            // Check if the user is active
            if (!((Employee) person).isActive()) {
                throw new UsernameNotFoundException("User not found");
            }

        } else if (person instanceof Owner) {

            roles.add("USER");
            roles.add("OWNER");
            roles.add("ADMINISTRATOR");

        } else {
            throw new UsernameNotFoundException("User not found");
        }

        String[] roleArray = new String[roles.size()];
        roleArray = roles.toArray(roleArray);

        // Create the user object with email, password and roles
        UserDetails userDetails =
                User.withUsername(person.getEmail())
                        .password(person.getPassword())
                        .roles(roleArray)
                        .build();

        return userDetails;
    }
}
