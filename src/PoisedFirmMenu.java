import java.util.Scanner;
import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The Poised ProjectsDetails class runs the main method for the Poised Program to execute.
 * ProjectsDetails class inherits input check methods from the superclass 'PoisedChecks' to validate user input.
 * <p>
 * This program was designed for a small engineering company Poised Firm.
 * This program act as a Project Management System for Poised Firm.
 * It uses a JDBC driver to access external information from the database (PoisePMS) for use within the program.
 * A menu with options is shown to the user, which enables the user to add new projects, edit existing projects, update existing projects etc.,
 * The results are then displayed to the user and updated in the external database (PoisePMS).
 * The 'PoisePMS' database contains two tables (project_info) and (person_details). The project_info
 * table is accessed, edited and used in this program.
 *
 * @author Omphemetse Marwane
 */
public class PoisedFirmMenu extends PoisedCheck {  //PoisedFirmMenu class is declared.

    /**
     * The main method which runs the Poised Firm
     * <p>
     * @param args java command line arguments
     * @throws ParseException occurs if a date string is in the wrong format
     */
    public static void main(String[] args) throws ParseException {  //Main method declared.

        //A ProjectsDetails object is created to call methods from the ProjectsDetails class.
        ProjectsDetails projObject = new ProjectsDetails();

        //A welcome message is showed to the user.
        System.out.println("Welcome to the Poised Firm Management System \n");

        // A while loop repeatedly return the user to the main menu after each choice made
        //If the user select option 8,they are allowed to exit the loop and log out of the program.
        while(true) {

            //Menu displayed to the user with various options.
            System.out.println("\nChoose an option from the menu below: "
                    + "\n1. View Existing Projects"
                    + "\n2. Add a New Project"
                    + "\n3. Update Existing Project Info"
                    + "\n4. Finalize a Project"
                    + "\n5. View Incomplete Projects"
                    + "\n6. View Overdue Projects"
                    + "\n7. Find a Project"
                    + "\n8. Delete a Project"
                    + "\n9. Exit program");

            //This checks the user's choice and save in an integer variable.
            // The intCheck() method is used to check if the user input's an integer.
            int userChoice = intcheck("menu choice");

            //A try-catch block is used to connect to the MySQL server and access the PoisePMS database, is also used for error handling
            try {

                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/PoisePMS?useSSL=false",
                        "otheruser",
                        "swordfish");

                System.out.println("Project successfully added");

                //This creates a Statement object.
                Statement statement = connection.createStatement();


                /* The actions related to user choice are now acted on.
                 * If the user choose '1', they can view all the projects listed in the database.
                 * The printAllFromTable() method is used to view all the information.
                 */
                if (userChoice == 1) {

                    projObject.printAllFromTable(statement);

                    /* If the user choose '2', they are asked for new project information.
                     * The user's inputs is checked and stored in a corresponding variables for use.
                     */
                } else if (userChoice == 2) {

                    projObject.addproject(statement);

                    /* If the user choose '3' they are allowed to edit project details of a chosen project.
                     * The user is asked to enter a project number, the user is then shown a mini-menu with two edit choices.
                     * The user then choose whether they want to edit the due date or amount paid of the project.
                     */
                } else if (userChoice == 3) {

                    projObject.editproject(statement);

                    /* If the user choose option '4' from the main menu, they are asked for a project number to finalise it.
                     * The program then locates the total fee and amount paid for that particular project using the project number.
                     * These amounts are stored in (total_Fee and 'amount_paid) variables.
                     */
                } else if (userChoice == 4) {

                    projObject.finaliseproject(statement);

                    /* If the user choose option '5', they can view all incomplete projects in the database.
                     * The project is incomplete if it's not finalised and has no completion date,
                     * The program finds incomplete project information by checking the two columns
                     * mentioned in the project_info table of the PoisePMS database
                     */
                } else if (userChoice == 5) {

                    projObject.viewIncomplete(statement);

                    /* If the user choose option '6' from the main menu they can view all overdue projects, if any are listed.
                     * A boolean project_check variable is crated
                     * This variable is used to check if overdue projects are present to be displayed.
                     * A string array (info) is created to store date information once it has been located and split from the table (project_info).
                     * Two arrays for months in integers and months in string are also created to get the month value stored in the table (project_info).
                     */
                } else if (userChoice == 6) {

                    projObject.viewOverdue(statement);

                    // If the user choose option '7' from the main menu, they can find a project by number or name.
                } else if (userChoice == 7) {

                    projObject.findProject(statement);

                    //If the user choose option '8', they are allowed to delete the project from the system.
                } else if (userChoice == 8) {

                    projObject.deleteProject(statement);

                    //If the user choose option '8', they are allowed to log out of the system.
                } else if (userChoice == 9) {

                    //A log out message is displayed to the user, while loop is exited.
                    System.out.println("You have logged out successfully.");
                    break;

                }
                // Catch created for SQL exception.
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}