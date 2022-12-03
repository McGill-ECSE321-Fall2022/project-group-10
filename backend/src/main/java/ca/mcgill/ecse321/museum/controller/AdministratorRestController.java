/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Administrator")
public class AdministratorRestController {

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
                @ApiResponse(code = 400, message = "No such employee")
            })
    @PostMapping(value = {"/administrator/employee"})
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @RequestBody EmployeeRequestDto body) {
        Employee employee =
                administratorService.createEmployee(
                        body.getFirstName(),
                        body.getLastName(),
                        body.getEmail(),
                        body.getPassword(),
                        body.getSalary());
        return new ResponseEntity<EmployeeResponseDto>(
                EmployeeResponseDto.createDto(employee), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Edit employee")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Employee edited"),
                @ApiResponse(code = 404, message = "No such employee")
            })
    @PutMapping(value = {"/administrator/employee/{employeeId}"})
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<EmployeeResponseDto> editEmployee(
            @RequestBody EmployeeRequestDto body, @PathVariable int employeeId)
            throws IllegalArgumentException {
        administratorService.editEmployee(
                employeeId,
                body.getFirstName(),
                body.getLastName(),
                body.getEmail(),
                body.getPassword(),
                body.getSalary());
        return new ResponseEntity<EmployeeResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Deactivate employee")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Employee deactivated"),
                @ApiResponse(code = 404, message = "No such employee")
            })
    @PutMapping(value = {"/administrator/employee/deactivate/{employeeId}"})
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<EmployeeResponseDto> deactivateEmployee(@PathVariable long employeeId)
            throws IllegalArgumentException {
        administratorService.deactivateEmployee(employeeId);
        return new ResponseEntity<EmployeeResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Reactivate employee")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Employee reactivated"),
                @ApiResponse(code = 404, message = "No such employee")
            })
    @PutMapping(value = {"/administrator/employee/reactivate/{employeeId}"})
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<EmployeeResponseDto> reactivateEmployee(@PathVariable long employeeId)
            throws IllegalArgumentException {
        administratorService.reactivateEmployee(employeeId);
        return new ResponseEntity<EmployeeResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get employee")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Employee returned"),
                @ApiResponse(code = 404, message = "No such employee")
            })
    @GetMapping(value = {"/administrator/employee/{employeeId}"})
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable long employeeId)
            throws IllegalArgumentException {
        var employee = administratorService.getEmployee(employeeId);
        return new ResponseEntity<>(EmployeeResponseDto.createDto(employee), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all employees")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employees returned")})
    @GetMapping(value = {"/administrator/employee"})
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees()
            throws IllegalArgumentException {
        var employees = administratorService.getAllEmployees();
        var EmployeeResponseDtos =
                employees.stream().map(employee -> EmployeeResponseDto.createDto(employee));
        return new ResponseEntity<List<EmployeeResponseDto>>(
                EmployeeResponseDtos.toList(), HttpStatus.OK);
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
            })
    @PostMapping(value = {"/administrator/owner"})
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDto> createOwner(@RequestBody OwnerRequestDto body) {
        Owner owner =
                administratorService.createOwner(
                        body.getFirstName(),
                        body.getLastName(),
                        body.getEmail(),
                        body.getPassword());
        return new ResponseEntity<OwnerResponseDto>(
                OwnerResponseDto.createDto(owner), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Edit owner")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Owner edited"),
                @ApiResponse(code = 404, message = "No such owner")
            })
    @PutMapping(value = {"/administrator/owner/{ownerId}"})
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDto> editOwner(
            @RequestBody OwnerRequestDto body, @PathVariable int ownerId)
            throws IllegalArgumentException {
        administratorService.editOwner(
                ownerId,
                body.getFirstName(),
                body.getLastName(),
                body.getEmail(),
                body.getPassword());
        return new ResponseEntity<OwnerResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get owner")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Owner returned"),
                @ApiResponse(code = 404, message = "No such owner")
            })
    @GetMapping(value = {"/administrator/owner/{ownerId}"})
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDto> getOwner(@PathVariable long ownerId)
            throws IllegalArgumentException {
        var owner = administratorService.getOwner(ownerId);
        return new ResponseEntity<>(OwnerResponseDto.createDto(owner), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all owners")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Owners returned")})
    @GetMapping(value = {"/administrator/owner"})
    public ResponseEntity<List<OwnerResponseDto>> getAllOwners() throws IllegalArgumentException {
        var owners = administratorService.getAllOwners();
        var OwnerResponseDtos = owners.stream().map(owner -> OwnerResponseDto.createDto(owner));
        return new ResponseEntity<List<OwnerResponseDto>>(
                OwnerResponseDtos.toList(), HttpStatus.OK);
    }
}
