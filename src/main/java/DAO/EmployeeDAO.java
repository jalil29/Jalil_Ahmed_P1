package DAO;

import Entity.Employee;

import java.util.List;

public interface EmployeeDAO
{
    //POST /employees
    //returns a 201
    Employee addEmployee(Employee employee);

    //GET /employees
    //GET /employees/120
    //returns a 404 if employee not found
    List<Employee> getAllEmployees();
    //List<Employee> getAllEmployeesByStatus(Object... arguments);

    Employee GetEmployeeByID(int ID);

    //PUT /employees/150
    //returns a 404 if employee not found
    Employee replaceEmployeeByID(Employee employee);

    //DELETE /employees/190
    //returns a 404 if employee not found
    boolean deleteEmployeeByID(int ID);

    //Patch /employees/150
    //returns a 404 if employee not found
    Employee updateEmployeeByID(Employee employee);
}
