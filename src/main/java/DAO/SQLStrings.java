package DAO;

import javax.swing.plaf.synth.Region;

public class SQLStrings
{
    static final String employeeInsert = "insert into employeeTable(firstname,lastname) values (?, ?)";
    static final String employeeGetByID = "select * from employeeTable where employeeID = ?";
    static final String employeeGetAll = "select * from employeeTable";
    static final String employeeUpdateByID = "update employeeTable set firstname = ?, lastname= ? where employeeID = ?";
    static final String employeeDeleteByID = "delete from employeeTable where employeeID = ?";

    static final String expenseInsert = "insert into expenseTable(employeeID ,amount, status) values (?, ?, ?)";
    static final String expenseGetByID = "select * from expenseTable where expenseID = ?";
    static final String expenseGetAll = "select * from expenseTable";
    static final String expenseGetAllByStatus = "select * from expenseTable where status = ?";
    static final String expenseGetAllByEmployeeID = "select * from expenseTable where employeeID = ?";
    static final String expenseUpdateByID = "update expenseTable set employeeID = ?, amount= ?, status = ? where expenseID = ?";
    static final String expenseUpdateStatusByID = "update expenseTable set status = ? where expenseID = ?";
    static final String expenseDeleteByID = "delete from expenseTable where expenseID = ?";
}
