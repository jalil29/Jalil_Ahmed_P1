import DAO.ConnectionUtil;
import Entity.Employee;
import Services.EmployeeService;
import Services.EmployeeServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeTests
{
    EmployeeService employeeService = new EmployeeServiceImpl();

    @Test
    @Order(0)
    public void ConnectionTest()
    {
        try (Connection connection = ConnectionUtil.getConnection())
        {

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    public void createEmployee()
    {
        String firstName = "firstTest";
        String lastName = "lastTest";
        //create employee Entity
        Employee test = new Employee(firstName, lastName);
        test = employeeService.addEmployee(test);
        //test employee Entity against knowns
        Assertions.assertNotEquals(0, test.getEmployeeID());
        Assertions.assertEquals(firstName, test.getFirstName());
        Assertions.assertEquals(lastName, test.getLastName());
        //test delete employee entity
        Assertions.assertTrue(employeeService.removeEmployee(test.getEmployeeID()));
    }

    @Test
    @Order(2)
    public void getEmployee()
    {
        int previousID = 1;
        //get test employee from database
        Employee test = employeeService.getEmployeeByID(previousID);
        String replaceFirstName = "replaceFirstTest";
        String replaceLastName = "replaceLastTest";
        // test known values
        Assertions.assertEquals(previousID, test.getEmployeeID());
        Assertions.assertEquals(replaceFirstName, test.getFirstName());
        Assertions.assertEquals(replaceLastName, test.getLastName());
    }

    @Test
    @Order(3)
    public void replaceEmployee()
    {
        int previousID = 1;
        //create test employee entity
        Employee test = employeeService.getEmployeeByID(previousID);
        //replace first and last name with test names
        String firstName = "replaceFirstTest2";
        String lastName = "replaceLastTest2";
        test = new Employee(test.getEmployeeID(), firstName, lastName);
        test = employeeService.replaceEmployee(test);

        //test result
        Assertions.assertEquals(previousID, test.getEmployeeID());
        Assertions.assertEquals(firstName, test.getFirstName());
        Assertions.assertEquals(lastName, test.getLastName());

        //replace with original values
        firstName = "replaceFirstTest";
        lastName = "replaceLastTest";
        Employee testReplace = new Employee(test.getEmployeeID(), firstName, lastName);
        test = employeeService.replaceEmployee(testReplace);

        //confirm data is original values
        Assertions.assertEquals(previousID, test.getEmployeeID());
        Assertions.assertEquals(firstName, test.getFirstName());
        Assertions.assertEquals(lastName, test.getLastName());
    }
}
