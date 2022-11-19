package ca.mcgill.ecse321.museum.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import ca.mcgill.ecse321.museum.dto.Request.EmployeeRequestDto;
import ca.mcgill.ecse321.museum.dto.Request.OwnerRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.EmployeeResponseDto;
import ca.mcgill.ecse321.museum.dto.Response.OwnerResponseDto;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Owner;
import ca.mcgill.ecse321.museum.service.AdministratorService;
import io.swagger.annotations.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Administrator")
public class AdministratorController {
    
    @Autowired private AdministratorService administratorService;

    /*
     * Employee Section
     * Post - Create
     * Put - Edit, Deactivate, Reactivate
     * Get - Get, GetAll
     */
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create Employee")
    @ApiResponses(
        value = {
            @ApiResponse(code = 201, message = "Employee successfully created"),
            @ApiResponse(code = 404, message = "No such employee")
        }
    )
    @PostMapping(value = {"/administrator/employee"})
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto body) {
        Employee employee = administratorService.createEmployee(
            body.getFirstName(),
            body.getLastName(),
            body.getEmail(),
            body.getPassword(),
            body.getSalary()
        );
        return new ResponseEntity<EmployeeResponseDto>(
            EmployeeResponseDto.createDto(employee), HttpStatus.CREATED
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Edit employee")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Employee edited"),
                @ApiResponse(code = 404, message = "No such employee")
            })
    @PutMapping(value = {"/administrator/employee/{id}"})
    public ResponseEntity<EmployeeResponseDto> editEmployee(@RequestBody EmployeeRequestDto body)
        throws IllegalArgumentException {
        administratorService.editEmployee(
            body.getId(), 
            body.getFirstName(), 
            body.getLastName(), 
            body.getEmail(), 
            body.getPassword(), 
            body.getSalary()
        );
        return new ResponseEntity<EmployeeResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Deactivate employee")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Employee deactivated"),
                @ApiResponse(code = 404, message = "No such employee")
            })
    @PutMapping(value = {"/administrator/employee/deactivate/{id}"})
    public ResponseEntity<EmployeeResponseDto> deactivateEmployee(@PathVariable long id)
        throws IllegalArgumentException {
        administratorService.deactivateEmployee(id);
        return new ResponseEntity<EmployeeResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Reactivate employee")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Employee reactivated"),
                @ApiResponse(code = 404, message = "No such employee")
            })
    @PutMapping(value = {"/administrator/employee/reactivate/{id}"})
    public ResponseEntity<EmployeeResponseDto> reactivateEmployee(@PathVariable long id)
        throws IllegalArgumentException {
        administratorService.reactivateEmployee(id);
        return new ResponseEntity<EmployeeResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get employee")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Employee returned"),
                @ApiResponse(code = 404, message = "No such employee")
            })
    @GetMapping(value = {"/administrator/employee/{id}"})
    public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable long id)
            throws IllegalArgumentException {
        var employee = administratorService.getEmployee(id);
        return new ResponseEntity<>(EmployeeResponseDto.createDto(employee), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all employees")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Employees returned")
            })
    @GetMapping(value = {"/administrator/employe/"})
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees()
            throws IllegalArgumentException {
        var employees = administratorService.getAllEmployees();
        var EmployeeResponseDtos = 
            employees.stream().map(employee -> EmployeeResponseDto.createDto(employee));
        return new ResponseEntity<List<EmployeeResponseDto>>(
            EmployeeResponseDtos.toList(), HttpStatus.OK
        );
    }

    /*
     * Owner Section
     * Post - Create
     * Put - Edit, Deactivate, Reactivate
     * Get - Get, GetAll
     */
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create Owner")
    @ApiResponses(
        value = {
            @ApiResponse(code = 201, message = "Owner successfully created"),
            @ApiResponse(code = 404, message = "No such owner")
        }
    )
    @PostMapping(value = {"/administrator/owner"})
    public ResponseEntity<OwnerResponseDto> createOwner(@RequestBody OwnerRequestDto body) {
        Owner owner = administratorService.createOwner(
            body.getFirstName(),
            body.getLastName(),
            body.getEmail(),
            body.getPassword()
        );
        return new ResponseEntity<OwnerResponseDto>(
            OwnerResponseDto.createDto(owner), HttpStatus.CREATED
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Edit owner")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Owner edited"),
                @ApiResponse(code = 404, message = "No such owner")
            })
    @PutMapping(value = {"/administrator/owner/{id}"})
    public ResponseEntity<OwnerResponseDto> editOwner(@RequestBody OwnerRequestDto body)
        throws IllegalArgumentException {
        administratorService.editOwner(
            body.getId(), 
            body.getFirstName(), 
            body.getLastName(), 
            body.getEmail(), 
            body.getPassword()
        );
        return new ResponseEntity<OwnerResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get owner")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Owner returned"),
                @ApiResponse(code = 404, message = "No such owner")
            })
    @GetMapping(value = {"/administrator/owner/{id}"})
    public ResponseEntity<OwnerResponseDto> getOwner(@PathVariable long id)
            throws IllegalArgumentException {
        var owner = administratorService.getOwner(id);
        return new ResponseEntity<>(OwnerResponseDto.createDto(owner), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all owners")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Owners returned")
            })
    @GetMapping(value = {"/administrator/owner/"})
    public ResponseEntity<List<OwnerResponseDto>> getAllOwners()
            throws IllegalArgumentException {
        var owners = administratorService.getAllOwners();
        var OwnerResponseDtos = 
            owners.stream().map(owner -> OwnerResponseDto.createDto(owner));
        return new ResponseEntity<List<OwnerResponseDto>>(
            OwnerResponseDtos.toList(), HttpStatus.OK
        );
    }
}
