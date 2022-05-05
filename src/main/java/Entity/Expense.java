package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Expense
{
    private int expenseID;
    private int employeeID;
    private double amount;
    private String status;

    public static String getWhereQuery(boolean expenseID, boolean employeeID, boolean amount, boolean status)
    {
        return setQuery(expenseID,employeeID,amount,status," AND ", " where");
    }

    public static String getSet(boolean expenseID, boolean employeeID, boolean amount, boolean status)
    {
        return setQuery(expenseID,employeeID,amount,status," , ", "");
    }

    private static String setQuery(boolean expenseID, boolean employeeID, boolean amount, boolean status, String join, String Beginning)
    {
        StringBuilder builder = new StringBuilder();
        boolean multiple = false;
        if (expenseID) multiple = buildQuery(builder, " expenseID = ?", false, join);
        if (employeeID) multiple = buildQuery(builder, " employeeID = ?", multiple, join);
        if (amount) multiple = buildQuery(builder, " amount = ?", multiple, join);
        if (status) multiple = buildQuery(builder, " status = ?", multiple, join);
        if (multiple) builder.insert(0, Beginning);
        return builder.toString();
    }

    private static boolean buildQuery(StringBuilder builder, String param, boolean multiple, String join)
    {
        if (multiple) builder.append(join);
        builder.append(param);
        return true;
    }
    /*
                PreparedStatement statement = connection.prepareStatement(SQLStrings.expenseGetAll + Expense.getWhereQuery(arguments[0]!=null,arguments[1]!=null,arguments[2]!=null,arguments[3]!=null));
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
            while (rs.next()) result.add(getExpenseFromResultSet(rs));
     */
}
