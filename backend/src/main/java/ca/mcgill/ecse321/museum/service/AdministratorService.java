package ca.mcgill.ecse321.museum.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.ArrayList;
import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Owner;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;

public class AdministratorService {
    @Autowired
    AdministratorRepository administratorRepository;

    /**
     * Create a new employee
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     * @param email email of the employee
     * @param password password of the employee
     * @param salary salary of the employee
     * @return the created employee
     */
    @Transactional
    public Employee createEmployee(String firstName, String lastName, String email, String password, float salary){
        Employee employee = new Employee();

        if (administratorRepository.findByEmail(email) != null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPassword(password);
        employee.setSalary(salary);
        employee.setActive(true);
        return administratorRepository.save(employee);
    }

    /**
     * Create a new owner
     * @param firstName first name of the owner
     * @param lastName last name of the owner
     * @param email email of the owner
     * @param password password of the owner
     * @param salary salary of the owner
     * @return the created owner
     */
    @Transactional
    public Owner createOwner(String firstName, String lastName, String email, String password, float salary){
        Owner owner = new Owner();

        if (administratorRepository.findByEmail(email) != null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setEmail(email);
        owner.setPassword(password);
        return administratorRepository.save(owner);
    }

    /**
     * Edit an employee
     * @param employeeId id of the employee to edit
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     * @param email email of the employee
     * @param password password of the employee
     * @param salary salary of the employee
     * @return the created employee
     */
    @Transactional
    public Employee editEmployee(long id, String firstName, String lastName, String email, String password, float salary){
        Employee employee = (Employee) administratorRepository.findById(id).orElse(null);
        
        if (employee == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "Employee not found");
        
        if (employee.getEmail() != email && administratorRepository.findByEmail(email) != null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPassword(password);
        employee.setSalary(salary);
        return administratorRepository.save(employee);
    }

    /**
     * Edit an owner
     * @param employeeId id of the owner to edit
     * @param firstName first name of the owner
     * @param lastName last name of the owner
     * @param email email of the owner
     * @param password password of the owner
     * @param salary salary of the owner
     * @return the created owner
     */
    @Transactional
    public Owner editOwner(long id, String firstName, String lastName, String email, String password){
        Owner owner = (Owner) administratorRepository.findById(id).orElse(null);
        
        if (owner == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "Employee not found");
        
        if (owner.getEmail() != email && administratorRepository.findByEmail(email) != null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setEmail(email);
        owner.setPassword(password);
        return administratorRepository.save(owner);
    }

    /**
     * Get an employee by id
     * @param id id of the employee
     * @return the employee
     */
    @Transactional
    public Employee getEmployee(long id){
        Employee employee = (Employee) administratorRepository.findById(id).orElse(null);

        if (employee == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such employee");

        return employee;
    }

    /**
     * Get all employees
     * @return list of all employees
     */
    @Transactional
    public List<Employee> getAllEmployees() {
        List<Administrator> administrators= administratorRepository.findAll();
        List<Employee> employees = new ArrayList<Employee>();
        for (Administrator admin : administrators){
            if (admin instanceof Employee) employees.add((Employee) admin);
        }
        return employees;
    }

    /**
     * Get an owner by id
     * @param id id of the owner
     * @return the owner
     */
    @Transactional
    public Owner getOwner(long id){
        Owner owner = (Owner) administratorRepository.findById(id).orElse(null);

        if (owner == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such owner");

        return owner;
    }

    /**
     * Get all owners
     * @return list of all owners
     */
    @Transactional
    public List<Owner> getAllOwners() {
        List<Administrator> administrators= administratorRepository.findAll();
        List<Owner> owners = new ArrayList<Owner>();
        for (Administrator admin : administrators){
            if (admin instanceof Owner) owners.add((Owner) admin);
        }
        return owners;
    }

    /**
     * Deactivate a employee
     * @param id id of the employee to deactivate
     */
    @Transactional
    public void deactivateEmployee(long id) {
        Employee employee = (Employee) administratorRepository.findById(id).orElse(null);

        if (employee == null) { throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such employee"); }

        employee.setActive(false);
        administratorRepository.save(employee);
    }

    /**
     * Reactivate a employee
     * @param id id of the employee to activate
     */
    @Transactional
    public void reactivateEmployee(long id) {
        Employee employee = (Employee) administratorRepository.findById(id).orElse(null);

        if (employee == null) { throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such employee"); }

        employee.setActive(true);
        administratorRepository.save(employee);
    }
}