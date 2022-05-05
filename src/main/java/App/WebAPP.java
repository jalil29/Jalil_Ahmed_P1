package App;

import Entity.Employee;
import Entity.Expense;
import Services.EmployeeService;
import Services.EmployeeServiceImpl;
import Services.ExpenseService;
import Services.ExpenseServiceImpl;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.Locale;

public class WebAPP
{
    public ExpenseService expenseService = new ExpenseServiceImpl();
    public EmployeeService employeeService = new EmployeeServiceImpl();
    public Gson gson = new Gson();

    private WebAPP()
    {
        appStart();
    }

    private void appStart()
    {
        try
        {
            Javalin app = Javalin.create();

            //home page
            app.get("/", context ->
            {
                context.status(200);
                context.result("Welcome to Sterling Employee Expense DataBase");
            });

            //region Expense region
            //create expense
            app.post("/expenses", context ->
            {
                String body = context.body();
                Expense expense = gson.fromJson(body, Expense.class);
                if (expense.getAmount() < 0)
                {
                    context.status(400);
                    context.result("Unable to process negative expense amounts");
                    return;
                }
                if (employeeService.getEmployeeByID(expense.getEmployeeID()).getEmployeeID() < 1)
                {
                    context.status(404);
                    context.result("Employee not found");
                    return;
                }
                expense.setStatus("PENDING");
                expense = expenseService.addNewExpense(expense);
                if (expense.getExpenseID() > 0)
                {
                    context.status(201);
                    String expenseJSON = gson.toJson(expense);
                    context.result(expenseJSON);
                    return;
                }
                context.status(500);
                context.result("Internal Server Error");
            });

            //region Get Expenses
            //get expenses
            app.get("/expenses", context ->
            {
                String status = context.queryParam("status");
                if (status != null)
                {
                    status = status.toUpperCase(Locale.ROOT);
                    if (!status.equals("APPROVED") && !status.equals("DECLINED") && !status.equals("PENDING"))
                    {
                        context.status(400);
                        context.result("Invalid Status query: " + status);
                        return;
                    }
                }

                try
                {
                    String expenseJSON = gson.toJson(expenseService.getAllExpenses(status));
                    context.status(200);
                    context.result(expenseJSON);
                }
                catch (Exception e)
                {
                    context.status(500);
                    context.result("Failed to get Expenses");
                }
            });

            //get expenses by ID
            app.get("/expenses/{id}", context ->
            {
                Integer id = validInt(context, context.pathParam("id"));
                if (id == null) return;
                Expense expense = expenseService.getExpense(id);
                if (expense.getExpenseID() > 0)
                {
                    context.status(200);
                    context.result(gson.toJson(expense));
                    return;
                }
                context.status(404);
                context.result("Expense not found");
            });
            //endregion

            //replace existing expense
            app.put("/expenses/{id}", context ->
            {
                Integer id = validInt(context, context.pathParam("id"));
                if (id == null) return;
                Expense expense = getEditableExpenseByID(context, id);
                if (expense == null) return;
                String body = context.body();
                expense = gson.fromJson(body, Expense.class);
                if (expense.getAmount() < 0)
                {
                    context.status(400);
                    context.result("Expense can not be negative");
                    return;
                }
                expense.setExpenseID(id);
                expense.setStatus("PENDING");
                expense = expenseService.replaceExpense(expense);
                if (expense.getExpenseID() > 0)
                {
                    context.status(200);
                    context.result("Expense replaced: " + gson.toJson(expense));
                    return;
                }
                context.status(500);
            });

            app.patch("/expenses/{id}/{status}", context ->
            {
                String status = context.pathParam("status");
                if (!status.equalsIgnoreCase("approved") && !status.equalsIgnoreCase("declined"))
                {
                    context.status(400);
                    context.result("Invalid Status Change");
                    return;
                }
                Integer id = validInt(context, context.pathParam("id"));
                if (id == null) return;
                Expense expense = getEditableExpenseByID(context, id);
                if (expense == null) return;
                expense.setStatus(status.toUpperCase(Locale.ROOT));
                context.status(200);
                expense = expenseService.updateExpenseStatus(expense);
                if (expense.getExpenseID() > 0)
                {
                    context.result("Expense updated: " + gson.toJson(expense));
                    return;
                }
            });

            //delete expense if pending
            app.delete("/expenses/{id}", context ->
            {
                Integer id = validInt(context, context.pathParam("id"));
                if (id == null) return;
                Expense expense = getEditableExpenseByID(context, id);
                if (expense == null) return;
                if (expenseService.cancelExpense(id))
                {
                    context.status(202);
                    context.result("Expense Deleted");
                    return;
                }
                context.status(404);
                context.result("Expense not found");
            });
            //endregion

            //region Employee paths
            app.post("/employees", context ->
            {
                String body = context.body();
                Employee employee = gson.fromJson(body, Employee.class);
                if (employee.getFirstName() == null || employee.getLastName() == null)
                {
                    context.status(400);
                    context.result("First Name or Last Name can not be null");
                    return;
                }
                employee = employeeService.addEmployee(employee);
                if (employee.getEmployeeID() > 0)
                {
                    context.status(201);
                    String employeeJSON = gson.toJson(employee);
                    context.result(employeeJSON);
                    return;
                }
                context.status(500);
            });

            //region get employees
            app.get("/employees", context ->
            {
                try
                {
                    String expenseJSON = gson.toJson(employeeService.getAllEmployees());
                    context.result(expenseJSON);
                }
                catch (Exception e)
                {
                    context.status(500);
                    context.result("Failed to get Employees");
                }
            });

            app.get("/employees/{id}", context ->
            {
                Integer id = validInt(context, context.pathParam("id"));
                if (id == null) return;

                Employee employee = employeeService.getEmployeeByID(id);

                if (employee.getEmployeeID() > 0)
                {
                    context.status(200);
                    String employeeJSON = gson.toJson(employee);
                    context.result(employeeJSON);
                    return;
                }

                context.status(404);
                context.result("Employee " + id + " not found");
            });
            //endregion

            app.put("/employees/{id}", context ->
            {
                Integer id = validInt(context, context.pathParam("id"));
                if (id == null) return;

                String body = context.body();
                Employee employee = gson.fromJson(body, Employee.class);
                employee.setEmployeeID(id);

                if (employee.getFirstName() == null || employee.getLastName() == null)
                {
                    context.status(400);
                    context.result("First Name or Last Name can not be null");
                    return;
                }

                employee = employeeService.replaceEmployee(employee);
                if (employee.getEmployeeID() > 0)
                {
                    context.status(200);
                    context.result("employee replaced: " + gson.toJson(employee));
                    return;
                }

                context.status(400);
                context.result("employee ID not found");
            });

            app.delete("/employees/{id}", context ->
            {
                Integer id = validInt(context, context.pathParam("id"));
                if (id == null) return;
                Employee employee = employeeService.getEmployeeByID(id);
                if(employee.getEmployeeID()>0)
                {
                    List<Expense> expenses = expenseService.getAllExpensesByID(employee.getEmployeeID());
                    for (Expense exp : expenses)
                    {
                        expenseService.cancelExpense(exp.getExpenseID());
                    }
                }
                if (employeeService.removeEmployee(id))
                {
                    context.status(202);
                    context.result("Employee Deleted");
                    return;
                }

                context.status(404);
                context.result("Employee not found");
            });
            //endregion

            //region compound path
            app.get("/employees/{id}/expenses", context ->
            {
                Integer id = validInt(context, context.pathParam("id"));
                if (id == null) return;

                Employee employee = employeeService.getEmployeeByID(id);

                if (employee.getEmployeeID() > 0)
                {
                    context.status(200);
                    context.result("employee expenses: " + gson.toJson(expenseService.getAllExpensesByID(id)));
                }
                else
                {
                    context.status(404);
                    context.result("Employee " + id + " not found");
                }
            });

            app.post("/employees/{id}/expenses", context ->
            {
                Integer id = validInt(context, context.pathParam("id"));
                if (id == null) return;

                Employee employee = employeeService.getEmployeeByID(id);

                if (employee.getEmployeeID() > 0)
                {
                    String body = context.body();
                    Expense expense = gson.fromJson(body, Expense.class);
                    if (expense.getAmount() < 0)
                    {
                        context.status(400);
                        context.result("Unable to process negative expense amounts");
                        return;
                    }
                    expense.setEmployeeID(employee.getEmployeeID());
                    expense.setStatus("PENDING");
                    expense = expenseService.addNewExpense(expense);
                    if (expense.getExpenseID() > 0)
                    {
                        context.status(201);
                        context.result(gson.toJson(expense));
                    }
                    else
                    {
                        context.status(500);
                        context.result("failed to add expense");
                    }
                }
                else
                {
                    context.status(404);
                    context.result("Employee " + id + " not found");
                }
            });
            //endregion

            app.start(5000);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public Expense getEditableExpenseByID(Context context, int id)
    {
        Expense expense = expenseService.getExpense(id);
        if (expense.getExpenseID() == 0)
        {
            context.status(404);
            context.result("Expense " + id + " not found");
            return null;
        }
        if (!expense.getStatus().equalsIgnoreCase("pending"))
        {
            context.status(401);
            context.result("Unable To Change this Expense");
            return null;
        }
        return expense;
    }

    public Integer validInt(Context context, String test)
    {
        try
        {
            return Integer.parseInt(test);
        }
        catch (Exception e)
        {
        }
        context.status(400);
        context.result(String.format("Unable to parse [%s] as an Integer", test));
        return null;
    }

    public static void main(String[] args)
    {
        new WebAPP();
    }
}
