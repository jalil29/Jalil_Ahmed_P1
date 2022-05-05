import DAO.ConnectionUtil;
import Entity.Expense;
import Services.ExpenseService;
import Services.ExpenseServiceImpl;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseTests
{
    ExpenseService expenseService = new ExpenseServiceImpl();

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
    public void AddExpense()
    {
        double amount = 643.23;
        int EmployeeID = 1;
        Expense test = new Expense(0, EmployeeID, amount, "PENDING");
        test = expenseService.addNewExpense(test);
        //Expense added and test is replaced by updated Expense
        Assertions.assertNotEquals(0, test.getExpenseID());
        Assertions.assertEquals(EmployeeID, test.getEmployeeID());
        Assertions.assertEquals(amount, test.getAmount());
        Assertions.assertTrue(test.getStatus().equalsIgnoreCase("PENDING"));
        //Delete created Expense
        Assertions.assertTrue(expenseService.cancelExpense(test.getExpenseID()));
    }

    @Test
    @Order(2)
    public void ReplaceExpense()
    {
        double amount = 420;
        int EmployeeID = 1;
        //create expense to be updated
        Expense test = new Expense(0, 1, amount, "PENDING");
        test = expenseService.addNewExpense(test);
        //test created expense
        Assertions.assertNotEquals(0, test.getExpenseID());
        Assertions.assertEquals(EmployeeID, test.getEmployeeID());
        Assertions.assertEquals(amount, test.getAmount());
        Assertions.assertTrue(test.getStatus().equalsIgnoreCase("PENDING"));
        //change amount
        amount = 0;
        test = new Expense(test.getExpenseID(), 1, amount, "PENDING");
        //update amount and confirm
        test = expenseService.addNewExpense(test);
        Assertions.assertNotEquals(0, test.getExpenseID());
        Assertions.assertEquals(EmployeeID, test.getEmployeeID());
        Assertions.assertEquals(amount, test.getAmount());
        Assertions.assertTrue(test.getStatus().equalsIgnoreCase("PENDING"));
    }

    @Test
    @Order(0)
    public void FailedDeleteExpense()
    {
        //check if cancelling an invalid expense results in failure
        Assertions.assertFalse(expenseService.cancelExpense(0));
    }
}
