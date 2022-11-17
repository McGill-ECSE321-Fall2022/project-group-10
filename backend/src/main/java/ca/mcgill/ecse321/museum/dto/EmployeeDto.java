package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.Employee;

public class EmployeeDto extends AdministratorDto {

    private boolean isActive;
    private float salary;

    public EmployeeDto(Employee employee) {
        super(employee);
        this.isActive = employee.isActive();
        this.salary = employee.getSalary();
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
}