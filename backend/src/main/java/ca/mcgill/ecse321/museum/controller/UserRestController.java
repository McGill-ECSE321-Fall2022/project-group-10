/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;


import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Owner;
import ca.mcgill.ecse321.museum.model.Person;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import io.swagger.annotations.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "user")
public class UserRestController {

        @Autowired private PersonRepository personRepository;

    /*
     * Employee Section
     * Post - Create
     * Put - Edit, Deactivate, Reactivate
     * Get - Get, GetAll
     */
    @GetMapping(value = {"/user/role/"})
    public ResponseEntity<List<String>> getRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the email of the authenticated user
        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        Person person = personRepository.findByEmail(authEmail);
        List<String> role = new ArrayList<>();
        
        if (person instanceof Employee) {
            role.add("EMPLOYEE");   
        } else if (person instanceof Owner) {
            role.add("OWNER");   
        } else {
            role.add("VISITOR");
        }

        return new ResponseEntity<List<String>>(role, HttpStatus.OK);
    }
}
