/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.Employee;

public class EmployeeResponseDto extends AdministratorResponseDto {

    private boolean isActive;
    private float salary;

    public EmployeeResponseDto(
            boolean isActive,
            float salary,
            String firstName,
            String lastName,
            String email,
            String password,
            Long id) {
        super(firstName, lastName, email, password, id);
        this.isActive = isActive;
        this.salary = salary;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public static Employee createModel(EmployeeResponseDto employeeDto) {
        if (employeeDto == null) return null;
        Employee employee = new Employee();
        employee.setActive(employeeDto.isActive);
        employee.setSalary(employeeDto.salary);
        return employee;
    }

    public static EmployeeResponseDto createDto(Employee employee) {
        if (employee == null) return null;
        EmployeeResponseDto employeeDto =
                new EmployeeResponseDto(
                        employee.isActive(),
                        employee.getSalary(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail(),
                        employee.getPassword(),
                        employee.getId());

        return employeeDto;
    }
}
