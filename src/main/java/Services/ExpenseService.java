package Services;

import Entity.Expense;

import java.util.List;

public interface ExpenseService
{
    Expense addNewExpense(Expense newExpense);

    Expense replaceExpense(Expense expense);

    Expense updateExpenseStatus(Expense expense);

    List<Expense> getAllExpenses();
    List<Expense> getAllExpenses(String Status);

    List<Expense> getAllExpensesByID(int ID);

    Expense getExpense(int expenseID);

    boolean cancelExpense(int expenseID);
}
