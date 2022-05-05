package Services;

import DAO.ExpenseDAO;
import DAO.ExpenseDAOImpl;
import Entity.Expense;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService
{
    ExpenseDAO dao;

    public ExpenseServiceImpl()
    {
        dao = new ExpenseDAOImpl();
    }

    @Override
    public Expense addNewExpense(Expense newExpense)
    {
        return this.dao.createNewExpense(newExpense);
    }

    @Override
    public Expense replaceExpense(Expense expense)
    {
        return this.dao.replaceExpenseByExpenseID(expense);
    }

    @Override
    public Expense updateExpenseStatus(Expense expense)
    {
        return this.dao.updateExpenseStatus(expense);
    }

    @Override
    public List<Expense> getAllExpenses()
    {
        return this.dao.getAllExpenses();
    }
    public List<Expense> getAllExpenses(String status)
    {
        if(status==null) return this.dao.getAllExpenses();
        return this.dao.getExpensesByStatus(status);
    }
/*
    @Override
    public List<Expense> getAllExpensesByQuery(Integer expenseID, Integer employeeID, Double amount, String Status)
    {
        return this.dao.getExpensesByQuery(expenseID, employeeID, amount,Status);
    }
*/
    @Override
    public List<Expense> getAllExpensesByID(int ID)
    {
        return this.dao.getAllExpenses(ID);
        //return this.dao.getExpensesByQuery(null,ID,null,null);
    }

    @Override
    public Expense getExpense(int expenseID)
    {
        return this.dao.getExpenseByExpenseID(expenseID);
    }

    @Override
    public boolean cancelExpense(int expenseID)
    {
        return this.dao.deleteExpense(expenseID);
    }
}
