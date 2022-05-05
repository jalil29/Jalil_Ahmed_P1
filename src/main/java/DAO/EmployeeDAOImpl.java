package DAO;

import Entity.Employee;

import java.sql.*;
import java.util.*;

import static DAO.ConnectionUtil.*;

public class EmployeeDAOImpl implements EmployeeDAO
{
    @Override
    public Employee addEmployee(Employee employee)
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.employeeInsert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) employee = getEmployeeFromResultSet(rs);
            else employee.setEmployeeID(0);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return employee;
    }

    @Override
    public List<Employee> getAllEmployees()
    {
        List<Employee> result = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.employeeGetAll);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) result.add(getEmployeeFromResultSet(rs));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }

/*
    @Override
    public List<Employee> getAllEmployeesByStatus(Object... arguments)
    {
        List<Employee> result = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.employeeGetAll+Employee.getWhereQuery(arguments[0]!=null,arguments[1]!=null, arguments[2]!=null));
            int parameterIndex = 0;
            for (Object arg : arguments)
            {
                parameterIndex++;
                if(arg==null) continue;
                if(arg instanceof Integer)statement.setInt(parameterIndex, (int)arg);
                else if(arg instanceof Double)statement.setDouble(parameterIndex, (double)arg);
                else if(arg instanceof String)statement.setString(parameterIndex, (String)arg);
                else throw new IllegalStateException("Unexpected Class: " + arg.getClass());
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) result.add(getEmployeeFromResultSet(rs));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }
*/
    @Override
    public Employee GetEmployeeByID(int ID)
    {
        Employee result = new Employee();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.employeeGetByID);
            statement.setInt(1, ID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return getEmployeeFromResultSet(rs);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Employee replaceEmployeeByID(Employee employee)
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.employeeUpdateByID, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setInt(3, employee.getEmployeeID());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) employee = getEmployeeFromResultSet(rs);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return employee;
    }

    @Override
    public boolean deleteEmployeeByID(int ID)
    {
        boolean result = false;
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.employeeDeleteByID, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ID);

            result = statement.executeUpdate()>0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Employee updateEmployeeByID(Employee employee)
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.employeeUpdateByID, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setInt(3, employee.getEmployeeID());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) employee = getEmployeeFromResultSet(rs);
            else  employee.setEmployeeID(0);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return employee;
    }

    private Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException
    {
        return new Employee(rs.getInt("employeeId"), rs.getString("firstName"), rs.getString("lastName"));
    }
}
