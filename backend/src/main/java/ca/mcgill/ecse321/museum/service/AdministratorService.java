/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Owner;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.security.CredentialsEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired AdministratorRepository administratorRepository;

    /**
     * Create a new employee
     *
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     * @param email email of the employee
     * @param password password of the employee
     * @param salary salary of the employee
     * @return the created employee
     */
    @Transactional
    public Employee createEmployee(
            String firstName, String lastName, String email, String password, float salary) {
        Employee employee = new Employee();

        if (!email.endsWith("@mail.museum.com"))
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Administrator emails must end with @mail.museum.com");

        if (administratorRepository.findByEmail(email).size() > 0) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        CredentialsEncoder encoder = new CredentialsEncoder();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPassword(encoder.encode(password));
        employee.setSalary(salary);
        employee.setActive(true);
        return administratorRepository.save(employee);
    }

    /**
     * Create a new owner
     *
     * @param firstName first name of the owner
     * @param lastName last name of the owner
     * @param email email of the owner
     * @param password password of the owner
     * @return the created owner
     */
    @Transactional
    public Owner createOwner(String firstName, String lastName, String email, String password) {
        Owner owner = new Owner();

        if (!email.endsWith("@mail.museum.com"))
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Administrator emails must end with @mail.museum.com");

        if (administratorRepository.findByEmail(email).size() > 0) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        CredentialsEncoder encoder = new CredentialsEncoder();

        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setEmail(email);
        owner.setPassword(encoder.encode(password));
        return administratorRepository.save(owner);
    }

    /**
     * Edit an employee
     *
     * @param employeeId id of the employee to edit
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     * @param email email of the employee
     * @param password password of the employee
     * @param salary salary of the employee
     * @return the created employee
     */
    @Transactional
    public Employee editEmployee(
            long id,
            String firstName,
            String lastName,
            String email,
            String password,
            float salary) {
        Employee employee = (Employee) administratorRepository.findById(id).orElse(null);

        if (employee == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "Employee not found");

        if (!email.endsWith("@mail.museum.com"))
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Administrator emails must end with @mail.museum.com");


        if (!employee.getEmail().equals(email)
                && administratorRepository.findByEmail(email).size() > 0) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        CredentialsEncoder encoder = new CredentialsEncoder();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPassword(encoder.encode(password));
        employee.setSalary(salary);
        return administratorRepository.save(employee);
    }

    /**
     * Edit an owner
     *
     * @param employeeId id of the owner to edit
     * @param firstName first name of the owner
     * @param lastName last name of the owner
     * @param email email of the owner
     * @param password password of the owner
     * @param salary salary of the owner
     * @return the created owner
     */
    @Transactional
    public Owner editOwner(
            long id, String firstName, String lastName, String email, String password) {
        Owner owner = (Owner) administratorRepository.findById(id).orElse(null);

        if (owner == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "Employee not found");
        
        if (!email.endsWith("@mail.museum.com"))
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Administrator emails must end with @mail.museum.com");


        if (!owner.getEmail().equals(email)
                && administratorRepository.findByEmail(email).size() > 0) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        CredentialsEncoder encoder = new CredentialsEncoder();

        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setEmail(email);
        owner.setPassword(encoder.encode(password));
        return administratorRepository.save(owner);
    }

    /**
     * Get an employee by id
     *
     * @param id id of the employee
     * @return the employee
     */
    @Transactional
    public Employee getEmployee(long id) {
        Employee employee = (Employee) administratorRepository.findById(id).orElse(null);

        if (employee == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such employee");

        return employee;
    }

    /**
     * Get all employees
     *
     * @return list of all employees
     */
    @Transactional
    public List<Employee> getAllEmployees() {
        List<Administrator> administrators = administratorRepository.findAll();
        List<Employee> employees = new ArrayList<Employee>();
        for (Administrator admin : administrators) {
            if (admin instanceof Employee) employees.add((Employee) admin);
        }
        return employees;
    }

    /**
     * Get an owner by id
     *
     * @param id id of the owner
     * @return the owner
     */
    @Transactional
    public Owner getOwner(long id) {
        Owner owner = (Owner) administratorRepository.findById(id).orElse(null);

        if (owner == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such owner");

        return owner;
    }

    /**
     * Get all owners
     *
     * @return list of all owners
     */
    @Transactional
    public List<Owner> getAllOwners() {
        List<Administrator> administrators = administratorRepository.findAll();
        List<Owner> owners = new ArrayList<Owner>();
        for (Administrator admin : administrators) {
            if (admin instanceof Owner) owners.add((Owner) admin);
        }
        return owners;
    }

    /**
     * Deactivate a employee
     *
     * @param id id of the employee to deactivate
     */
    @Transactional
    public void deactivateEmployee(long id) {
        Employee employee = (Employee) administratorRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such employee");
        }

        employee.setActive(false);
        administratorRepository.save(employee);
    }

    /**
     * Reactivate a employee
     *
     * @param id id of the employee to activate
     */
    @Transactional
    public void reactivateEmployee(long id) {
        Employee employee = (Employee) administratorRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such employee");
        }

        employee.setActive(true);
        administratorRepository.save(employee);
    }
}
