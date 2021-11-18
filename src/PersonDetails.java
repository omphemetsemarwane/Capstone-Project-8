import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The PersonDetails class contains a method to view a customer's, contractor's, architecture's details relating to a specific project in a friendly_manner.
 * <p>
 * The method in this class is used in the ProjectsDetails class to create a customer object and then display their details to generate an invoice, also contractor, architecture objects .
 * @author Omphemetse Marwane
 */
public class PersonDetails {

    /**
     * This method is used to display a customer object in a friendly_manner.
     * <p>
     * It chooses customer's information from the 'person_info' table in the 'PoisePMS' database by
     * finding a specific project number, and then shows the relevant information in an easy_to_read format to the user.
     * <p>
     * @param statement statement object linked to connection performs SQL commands
     * @param project_num project_num is an integer inputted by the user to find a certain project object
     * @throws SQLException occurs if there is an error while accessing the database information
     */
    public void showCustomer(Statement statement, int project_num) throws SQLException {

        ResultSet results1 = statement.executeQuery("SELECT NAME, CONTACT_NUM, PHYSICAL_ADDRESS, EMAIL_ADDRESS FROM person_info WHERE PROJ_NUM = " + project_num
                + " AND TYPE_PERSON = 'Customer'");

        while (results1.next()) {  // Customer's details are shown using information in the table(person_info).
            System.out.println(
                    "\nCustomer Name: " + results1.getString("NAME")
                            + "\nContact Number: " + results1.getInt("CONTACT_NUM")
                            + "\nPhysical Address: " + results1.getString("PHYSICAL_ADDRESS")
                            + "\nEmail Address: " + results1.getString("EMAIL_ADDRESS")
            );

        }

    }
    public void showConstractor(Statement statement, int project_num) throws SQLException {

        ResultSet results1 = statement.executeQuery("SELECT NAME, CONTACT_NUM, PHYSICAL_ADDRESS, EMAIL_ADDRESS FROM person_info WHERE PROJ_NUM = " + project_num
                + " AND TYPE_PERSON = 'Contractor'");

        while (results1.next()) {  // Customer's details are shown using information in the table(person_info).
            System.out.println(
                    "\nConstractor Name: " + results1.getString("NAME")
                            + "\nContact Number: " + results1.getInt("CONTACT_NUM")
                            + "\nPhysical Address: " + results1.getString("PHYSICAL_ADDRESS")
                            + "\nEmail Address: " + results1.getString("EMAIL_ADDRESS")
            );
        }
    }
    public void showArchitecture(Statement statement, int project_num) throws SQLException {

        ResultSet results1 = statement.executeQuery("SELECT NAME, CONTACT_NUM, PHYSICAL_ADDRESS, EMAIL_ADDRESS FROM person_info WHERE PROJ_NUM = " + project_num
                + " AND TYPE_PERSON = 'Architecture'");

        while (results1.next()) {  // Customer's details are shown using information in the table(person_info).
            System.out.println(
                    "\nArchitecture Name: " + results1.getString("NAME")
                            + "\nContact Number: " + results1.getInt("CONTACT_NUM")
                            + "\nPhysical Address: " + results1.getString("PHYSICAL_ADDRESS")
                            + "\nEmail Address: " + results1.getString("EMAIL_ADDRESS")
            );
        }
    }
}
