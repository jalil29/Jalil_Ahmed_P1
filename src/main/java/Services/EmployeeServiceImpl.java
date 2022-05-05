package Services;

import DAO.EmployeeDAO;
import DAO.EmployeeDAOImpl;
import Entity.Employee;
import Entity.Expense;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService
{
    EmployeeDAO dao;

    public EmployeeServiceImpl()
    {
        dao = new EmployeeDAOImpl();
    }

    @Override
    public List<Employee> getAllEmployees()
    {
        return dao.getAllEmployees();
    }

    @Override
    public Employee getEmployeeByID(int ID)
    {
        return dao.GetEmployeeByID(ID);
    }

    @Override
    public Employee addEmployee(Employee employee)
    {
        return dao.addEmployee(employee);
    }

    @Override
    public Employee replaceEmployee(Employee employee)
    {
        return dao.replaceEmployeeByID(employee);
    }

    @Override
    public boolean removeEmployee(int ID)
    {
        return dao.deleteEmployeeByID(ID);
    }
}
