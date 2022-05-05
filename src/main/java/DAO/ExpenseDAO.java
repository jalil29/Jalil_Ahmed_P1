package DAO;

import Entity.Expense;

import java.util.List;

public interface ExpenseDAO
{
    //POST /expenses
    Expense createNewExpense(Expense expense);

    //GET /expenses
    List<Expense> getAllExpenses();

    List<Expense> getAllExpenses(int employeeID);

    //GET /expenses?status=pending
    List<Expense> getExpensesByStatus(String currentStatus);
    //List<Expense> getExpensesByQuery(String currentStatus, String employeeID, double amount);

    //GET /expenses/12
    //returns a 404 if expense not found
    Expense getExpenseByExpenseID(int expenseID);

    //PUT /expenses/15
    //returns a 404 if expense not found
    Expense replaceExpenseByExpenseID(Expense expense);

    //PATCH /expenses/20/approve
    //returns a 404 if expense not found
    Expense updateExpenseStatus(Expense expense);

    //DELETE /expenses/19
    //returns a 404 if car not found
    boolean deleteExpense(int expenseID);
}
