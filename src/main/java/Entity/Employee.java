package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee
{
    public Employee(String first, String Last)
    {
        this.firstName = first;
        this.lastName = Last;
    }

    private int employeeID;
    private String firstName;
    private String lastName;

    public static String getWhereQuery(boolean employeeID, boolean firstName, boolean lastName)
    {
        return setQuery(employeeID,firstName,lastName," AND ", " where");
    }

    public static String getSet(boolean employeeID, boolean firstName, boolean lastName)
    {
        return setQuery(employeeID, firstName,lastName," , ", "");
    }

    private static String setQuery(boolean employeeID, boolean firstName, boolean lastName, String join, String Beginning)
    {
        StringBuilder builder = new StringBuilder();
        boolean multiple = false;
        if (employeeID) multiple = buildQuery(builder, " employeeID = ?", false, join);
        if (firstName) multiple = buildQuery(builder, " firstName = ?", multiple, join);
        if (lastName) multiple = buildQuery(builder, " lastName = ?", multiple, join);
        if (multiple) builder.insert(0, Beginning);
        return builder.toString();
    }

    private static boolean buildQuery(StringBuilder builder, String param, boolean multiple, String join)
    {
        if (multiple) builder.append(join);
        builder.append(param);
        return true;
    }
}
