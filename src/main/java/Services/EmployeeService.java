package Services;

import Entity.Employee;
import Entity.Expense;

import java.util.List;

public interface EmployeeService
{
    Employee addEmployee(Employee employee);

    Employee replaceEmployee(Employee employee);

    Employee getEmployeeByID(int ID);

    List<Employee> getAllEmployees();

    boolean removeEmployee(int ID);
}
