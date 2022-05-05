package DAO;

import Entity.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static DAO.ConnectionUtil.getConnection;

public class ExpenseDAOImpl implements ExpenseDAO
{
    @Override
    public Expense createNewExpense(Expense expense)
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.expenseInsert, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, expense.getEmployeeID());
            statement.setDouble(2, expense.getAmount());
            statement.setString(3, expense.getStatus());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) expense = getExpenseFromResultSet(rs);
            else expense.setExpenseID(0);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return expense;
    }

    @Override
    public List<Expense> getAllExpenses()
    {
        List<Expense> result = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.expenseGetAll);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) result.add(getExpenseFromResultSet(rs));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<Expense> getAllExpenses(int employeeID)
    {
        List<Expense> result = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.expenseGetAllByEmployeeID);
            statement.setInt(1, employeeID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) result.add(getExpenseFromResultSet(rs));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<Expense> getExpensesByStatus(String currentStatus)
    {
        List<Expense> result = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.expenseGetAllByStatus);
            statement.setString(1, currentStatus);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) result.add(getExpenseFromResultSet(rs));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Expense getExpenseByExpenseID(int expenseID)
    {
        Expense expense = new Expense();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.expenseGetByID, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, expenseID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) expense = getExpenseFromResultSet(rs);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return expense;
    }

    @Override
    public Expense replaceExpenseByExpenseID(Expense expense)
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.expenseUpdateByID, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, expense.getEmployeeID());
            statement.setDouble(2, expense.getAmount());
            statement.setString(3, expense.getStatus());
            statement.setInt(4, expense.getExpenseID());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) expense = getExpenseFromResultSet(rs);
            else expense.setExpenseID(0);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return expense;
    }

    @Override
    public Expense updateExpenseStatus(Expense expense)
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.expenseUpdateStatusByID, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, expense.getStatus());
            statement.setInt(2, expense.getExpenseID());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) expense = getExpenseFromResultSet(rs);
            else expense.setExpenseID(0);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return expense;
    }

    @Override
    public boolean deleteExpense(int expenseID)
    {
        boolean result = false;
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SQLStrings.expenseDeleteByID, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, expenseID);
            result = statement.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return result;
    }

    private Expense getExpenseFromResultSet(ResultSet rs) throws SQLException
    {
        return new Expense(rs.getInt("expenseID"), rs.getInt("employeeID"), rs.getDouble("amount"), rs.getString("status"));
    }
}
