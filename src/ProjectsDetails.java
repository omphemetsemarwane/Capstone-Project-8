import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

/**
 * The ProjectsDetails.java class consists of all the methods used in the (PoisedFirmMenu) which includes adding, editing, finalising projects,
 * and viewing different projects in the Poised System.
 * ProjectsDetails inherits input check methods from the superclass (PoisedCheck) to confirms the user input.
 * <p>
 * @author Omphemetse Marwane
 */
public class ProjectsDetails extends PoisedCheck {
    /**
     * This method allows the user to create a new project object, this project object is then added to the (project_info) table in the
     * (PoisePMS) database.
     * <p>
     * This method ask the users to enter information related to the new project object, it then connect to the external
     * database(PoisePMS) to update project details. The 'PoisedFirmMenu' class is used to call this method when the user choose to add
     * the new project to the Poised Firm Management System.
     * <p>
     * @param statement statement object linked to connection to perform SQL commands
     * @throws SQLException occurs if an error occurs while accessing the database(PoisePMS) information
     */
    public void addproject(Statement statement) throws SQLException {

        System.out.println("\nAdd a new project number: ");
        int project_number = intcheck("project number");

        System.out.println("\nAdd a new project name: ");
        String project_name = stringcheck("project name: ");

        System.out.println("\nAdd the building type for the project: ");
        String type_building = stringcheck("building type");

        System.out.println("\nAdd the physical address for the project: ");
        String address = stringcheck("physical address");

        System.out.println("\nAdd the ERF number: ");
        String erf_num = stringcheck("ERF number");

        System.out.println("\nAdd a total fee for the project: ");
        float total_fee = floatcheck("total fee");

        System.out.println("\nAdd the current amount paid to date for the project: ");
        float amount_paid = floatcheck("amount paid");

        System.out.println("Add the deadline for the project (e.g. 26-Feb-2021): ");
        String deadline = stringcheck("deadline");

        String finalised = "No";
        String completion_date = "None";

        /* The project_info table is updated, this table is in the 'PoisePMS' database.
         * The information entered by the user is inserted into the relevant columns,
         * this creates and store's a new project object.
         */
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/PoisePMS?useSSL=false",
                "otheruser",
                "swordfish");

        String sql = "INSERT INTO project_info(PROJ_NUM, PROJ_NAME, BUILDING_TYPE, ADDRESS, ERF_NUM, TOTAL_FEE, AMOUNT_PAID, DEADLINE, FINALISED, COMPLETION_DATE)VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement1 = connection.prepareStatement(sql);
        statement1.setString(1, String.valueOf(project_number));
        statement1.setString(2, project_name);
        statement1.setString(3, type_building);
        statement1.setString(4, address);
        statement1.setString(5, erf_num);
        statement1.setString(6, String.valueOf(total_fee));
        statement1.setString(7, String.valueOf(amount_paid));
        statement1.setString(8, deadline);
        statement1.setString(9, finalised);
        statement1.setString(10, completion_date);
        statement1.executeUpdate();
        System.out.println("Book successfully added");

        // A successful message is then displayed to the user, the user can then view the updated project list in a friendly_manner.
        System.out.println("\nYour new project was added successfully. Please view updated projects below: \n");
        printAllFromTable(statement);

    }
    /**
     * This method allows the users to edit project information such as the project due date and the total amount paid to date.
     * <p>
     * This method displays a mini-menu to the user with two edit choices, it then executes the actions depending on the user choice.
     * The edited information is then written under the appropriate column of the 'project_info' table in the
     * external (PoisePMS) database.
     * <p>
     * @param statement statement object linked to connection to perform SQL commands
     * @throws SQLException occurs if an error occurred while accessing the database information
     */
    public void editproject(Statement statement) throws SQLException {

        //The user is asked to enter the project number they want to edit.
        System.out.println("Enter the number of the project you want to update: \n");
        int project = intcheck("project number");

        System.out.println("Would you like to:" +
                "\n1. Edit the project due date or" +
                "\n2. Edit the total amount paid of the fee paid to date?" +  //The edit options are displayed to the user.
                "\nChoose 1 or 2");

        int editChoice = intcheck("edit choice");

        /* If the user choose option 1, they are asked to enter a new deadline.
         * The new value is then inserted in the project_info table with the executeUpdate() SQL statement.
         */
        if (editChoice == 1) {
            System.out.println("Enter a new project deadline: ");
            String new_date = stringcheck("new project deadline");

            statement.executeUpdate(
                    "UPDATE project_info SET DEADLINE = '" + new_date + "'" + " WHERE PROJ_NUM = " + project
            );

            // A successful message is then displayed to the user, the user is able to view the list of updated projects.
            System.out.println("Your project information was updated successfully. Please view projects below: ");
            printAllFromTable(statement);

            /* If the user choose option 2, they are asked to enter a new amount paid to date.
             * The new value is then inserted to the project_info table with the executeUpdate() SQL statement.
             */
        } else if (editChoice == 2) {
            System.out.println("Enter a new total amount paid: ");
            float new_amount = floatcheck("new amount paid");

            statement.executeUpdate(
                    "UPDATE project_info SET AMOUNT_PAID = " + new_amount + " WHERE PROJ_NUM = " + project
            );

            // A successful message is then displayed to the user, the user is able to view the list of updated projects.
            System.out.println("Your project information was updated successfully. View projects below: ");
            printAllFromTable(statement);

        }
    }
    /**
     * This method allows users to finalise a project found in the project_info table in the external 'PoisePMS' database.
     * <p>
     * The user is asked to enter a project number to find the project. An invoice is generated and displayed with customer details
     * if there is an outstanding amount on the project. If not the project is just marked as finalised and
     * completion date is added.
     * <p>
     * @param statement statement object linked to connection to perform SQL commands
     * @throws SQLException occurs if an error occurred while accessing the database information
     */
    public void finaliseproject(Statement statement) throws SQLException {

        //The user is asked to enter a project number to finalise.
        System.out.println("Enter the number of the project that you want to finalise: ");
        int project_number = intcheck("project number");

        //This select the TOTAL_FEE and AMOUNT_PAID columns from the table (project_info).
        ResultSet results2 = statement.executeQuery("SELECT TOTAL_FEE, AMOUNT_PAID FROM project_info WHERE PROJ_NUM = " + project_number);
        float total_Fee = 0;
        float amount_paid = 0;

        //Going through the columns to store the two float numbers into appropriate variables.
        while (results2.next()) {
            total_Fee = results2.getFloat("TOTAL_FEE");
            amount_paid = results2.getFloat("AMOUNT_PAID");

        }
        /*If the project amount has been paid in full, the amount paid equals to the total fee for the project.
        * No invoice needs to be generated.*/
        if (total_Fee == amount_paid) {
            System.out.println("The project has been paid in full. No invoice generated.");

            /*The user is then asked to enter a completion date for the project, it is inserted into the 'COMPLETION_DATE' column
            * in the project_info table with the executeUpdate() statement.*/
            System.out.println("Add a completion date for the project: ");
            String completion_date = stringcheck("completion date");

            //The completion date is then added to the user's chosen project using project number.
            statement.executeUpdate(
                    "UPDATE project_info SET COMPLETION_DATE = " + "'" + completion_date + "'" + " WHERE PROJ_NUM = " + project_number
            );

            // The project is marked as finalised by writing 'Yes' to the FINALISED column in the table (project_info).
            statement.executeUpdate(
                    "UPDATE project_info SET FINALISED = 'Yes' WHERE PROJ_NUM = " + project_number
            );

            //A successful message is then displayed, the user is able to view the updated project list.
            System.out.println("Your project was finalised successfully. Please view projects below: ");
            printAllFromTable(statement);

            /* If there is still amount outstanding amount on the project, an invoice is generated.
             * A 'PersonDetails' object is then created to access the 'showPerson() method from the PersonDetails class.
             * The customer details for the selected project are then displayed to the user for the invoice.
             */
        } else if (total_Fee != amount_paid) {
            System.out.println("There is an outstanding amount to be paid for this project. Please view your invoice below: \n");

            PersonDetails customer = new PersonDetails();
            customer.showCustomer(statement, project_number);

            //The amount owing on the project is added to the customer information.
            System.out.println("\nOutstanding Amount: R" + (total_Fee - amount_paid));

            //The user is then asked to enter the completion dated for the project.
            System.out.println("\nAdd a completion date for the project: ");
            String completion_date = stringcheck("completion date");

            //The date inputted by the user is inserted to the project_info table under the 'COMPLETION_DATE' column.
            statement.executeUpdate(
                    "UPDATE project_info SET COMPLETION_DATE = " + "'" + completion_date + "'" + " WHERE PROJ_NUM = " + project_number
            );

            //The project is then marked as finalised by writing 'Yes' to the FINALISED column in the table (project_info).
            statement.executeUpdate(
                    "UPDATE project_info SET FINALISED = 'Yes' WHERE PROJ_NUM = " + project_number
            );

            //A successful message is the displayed to the user, the user is able to view the updated project list.
            System.out.println("Your project was finalised successfully. Please view projects below: ");
            printAllFromTable(statement);

            PersonDetails contractor = new PersonDetails();
            contractor.showConstractor(statement, project_number);

            PersonDetails architecture = new PersonDetails();
            architecture.showArchitecture(statement, project_number);

        }
    }
    /**
     * This method allows users to view all project objects that are incomplete ( projects which are not finalised and no completion
     * date has been added/set) in the project_info table in the external (PoisePMS) database.
     * <p>
     * @param statement statement object linked to connection to perform SQL commands
     * @throws SQLException occurs if an error occurred while accessing the database information
     */
    public void viewIncomplete(Statement statement) throws SQLException {

        System.out.println("\nView all incomplete projects below: \n");

        ResultSet results3 = statement.executeQuery("SELECT * FROM project_info WHERE FINALISED = 'No' AND COMPLETION_DATE = 'None'");

        //All the incomplete projects are then displayed to the user bt iterating through the table.
        while (results3.next()) {
            System.out.println(
                    "Project Number: \t" + results3.getInt("PROJ_NUM")
                            + "\nProject Name: \t" + results3.getString("PROJ_NAME")
                            + "\nBuilding Type: \t" + results3.getString("BUILDING_TYPE")
                            + "\nPhysical Address: " + results3.getString("ADDRESS")
                            + "\nERF Number: \t" + results3.getString("ERF_NUM")
                            + "\nTotal Fee: \tR" + results3.getFloat("TOTAL_FEE")
                            + "\nAmount Paid: \t" + results3.getFloat("AMOUNT_PAID")
                            + "\nDeadline: \t" + results3.getString("DEADLINE")
                            + "\nFinalised: \t" + results3.getString("FINALISED")
                            + "\nCompletion Date: " + results3.getString("COMPLETION_DATE")
                            + "\n"
            );
        }
    }
    /**
     * This method allows users to view all project objects that are overdue in the prject_info table in the
     * external (PoisePMS) database.
     * <p>
     * When this method is called on, it runs through all deadlines of incomplete projects and compares the deadline date with the
     * current date. If the project is overdue, the project is displayed in a friendly_manner. If there are no overdue projects are present, an
     * appropriate error message is displayed to the user.
     * <p>
     * @param statement statement object linked to connection to perform SQL commands
     * @throws SQLException occurs if an error occurred while accessing the database information
     * @throws ParseException occurs if a date string is in the wrong format
     */
    public void viewOverdue(Statement statement) throws SQLException, ParseException {

        boolean project_check = false;
        String[] info;
        int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        String[] months_year = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int month_num = 0;

        //The overdue projects will be incomplete so only the deadline date information from columns of incomplete projects are found.
        ResultSet results4 = statement.executeQuery("SELECT DEADLINE FROM project_info WHERE FINALISED = 'No' AND COMPLETION_DATE = 'None'");

        //Going through the deadline dates in the incomplete projects to check if they are overdue.
        while (results4.next()) {

            /*The string variable (date_info) is used to store deadline date.
            * The variable is split into an array (info) by eliminating the dash character ('-') from the date (e.g. 26-Feb-2021)
            * The 1st index value of array (info) is then parsed and stored into an integer variable (day).*/
            String date_info = results4.getString("DEADLINE");
            info = date_info.split("-");
            int day = Integer.parseInt(info[0]);

            /* The 2nd index value from the array (info) is stored in a variable (month_info).
             * The variable (month_info) is then split further to store three letters of the month name into string variable (month) (e.g. 'Feb').
             * A variable (year) is created and assigned the parsed value from the third index in array (info).
             */
            String month_info = info[1];
            String month = (month_info.substring(0,2));
            int year = Integer.parseInt(info[2]);

            /* A for loop is used to compare the sub_string (month) with the string array (months_year).
             * Once they match with an abbreviated month of the year, the corresponding number from the integer array (months)
             * is stored in the variable (month_num) to use as date information.
             */
            for (int index = 0; index < months_year.length ; index++) {
                if (month.equalsIgnoreCase(months_year[index])) {
                    month_num = months[index];

                }
            }
            //Used to get the current date and storing it as a string.
            String current = "" + java.time.LocalDate.now();

            //Used to create a new simple date format object.
            SimpleDateFormat dateObject = new SimpleDateFormat("yyyy-mm-dd");


            //Dates (date1 and date2) are created by parsing string info from 'current' date, and date info gathered from the file above.

            Date date1 = dateObject.parse(current);

            Date date2 = dateObject.parse(day + "-" + month_num + "-" + year);

            //The project is overdue if the current date has passed the deadline.
            //The project_check is set to 'true' and all of the columns for that project are selected and displayed.
            if (date1.compareTo(date2) < 0) {
                project_check = true;

                System.out.println("\nView all overdue projects below: \n");
                ResultSet results5 = statement.executeQuery("SELECT * from project_info WHERE DEADLINE = '" + date_info + "'");

                //Going through the table and showing all info related to the overdue project.
                while (results5.next()) {
                    System.out.println(
                            "Project Number: \t" + results5.getInt("PROJ_NUM")
                                    + "\nProject Name: \t" + results5.getString("PROJ_NAME")
                                    + "\nBuilding Type: \t" + results5.getString("BUILDING_TYPE")
                                    + "\nPhysical Address: " + results5.getString("ADDRESS")
                                    + "\nERF Number: \t" + results5.getString("ERF_NUM")
                                    + "\nTotal Fee: \tR" + results5.getFloat("TOTAL_FEE")
                                    + "\nAmount Paid: \t" + results5.getFloat("AMOUNT_PAID")
                                    + "\nDeadline: \t" + results5.getString("DEADLINE")
                                    + "\nFinalised: \t" + results5.getString("FINALISED")
                                    + "\nCompletion Date: " + results5.getString("COMPLETION_DATE")
                                    + "\n"
                    );
                }
                //If there are no overdue projects, project_check is set to 'false'.
            } else {
                project_check = false;
            }
            //If project_check is set at false after all the projects are checked, an appropriate message is displayed to the user.
        } if (project_check == false) {
            System.out.println("No overdue projects listed on the system.");
        }
    }
    /**
     * This method allows users to find project objects from the project_info table in the external 'PoisePMS'
     * database by entering either the project name or number.
     * <p>
     * Using either the name or number, the project is then found from the external database (PoisePMS) and displayed in a friendly_manner.
     * <p>
     * @param statement statement object linked to connection to perform SQL commands
     * @throws SQLException occurs if an error occured while accessing the database information
     */
    public void findProject(Statement statement) throws SQLException {

        System.out.println("Do you want to search for your project by 1.) project number or 2.) project name? \nSelect 1 or 2.");
        int search_choice = intcheck("number search option");

        /* If the user choose option 1, they are asked to enter the project number.
         * Once the number has been entered, the program selects all the information related to that project to display to the user.
         */
        if (search_choice == 1) {
            System.out.println("\nEnter the number of the project you want to find: ");
            int project_number = intcheck("project number");

            System.out.println("\nView your project details below: \n");

            ResultSet results6 = statement.executeQuery("SELECT * from project_info WHERE PROJ_NUM = " + project_number);

            //Going through the project information by column of the project selected by the user.
            while (results6.next()) {
                System.out.println(
                        "Project Number: \t" + results6.getInt("PROJ_NUM")
                                + "\nProject Name: \t" + results6.getString("PROJ_NAME")
                                + "\nBuilding Type: \t" + results6.getString("BUILDING_TYPE")
                                + "\nPhysical Address: " + results6.getString("ADDRESS")
                                + "\nERF Number: \t" + results6.getString("ERF_NUM")
                                + "\nTotal Fee: \tR" + results6.getFloat("TOTAL_FEE")
                                + "\nAmount Paid: \t" + results6.getFloat("AMOUNT_PAID")
                                + "\nDeadline: \t" + results6.getString("DEADLINE")
                                + "\nFinalised: \t" + results6.getString("FINALISED")
                                + "\nCompletion Date: " + results6.getString("COMPLETION_DATE")
                                + "\n"
                );
            }
            /* If the user choose option 2, they are asked to enter the project name.
             * Once entered, the program selects all the information related to that project to display to the user.
             */
        } else if (search_choice == 2) {
            System.out.println("\nEnter the name of the project you wish to locate: ");
            String project_name = stringcheck("project name");

            System.out.println("\nView your project details below: \n");

            ResultSet results7 = statement.executeQuery("SELECT * from project_info WHERE PROJ_NAME = '" + project_name + "'");

            //Going through the project information by column of the project selected by the user.
            while (results7.next()) {
                System.out.println(
                        "Project Number: \t" + results7.getInt("PROJ_NUM")
                                + "\nProject Name: \t" + results7.getString("PROJ_NAME")
                                + "\nBuilding Type: \t" + results7.getString("BUILDING_TYPE")
                                + "\nPhysical Address: " + results7.getString("ADDRESS")
                                + "\nERF Number: \t" + results7.getString("ERF_NUM")
                                + "\nTotal Fee: \tR" + results7.getFloat("TOTAL_FEE")
                                + "\nAmount Paid: \t" + results7.getFloat("AMOUNT_PAID")
                                + "\nDeadline: \t" + results7.getString("DEADLINE")
                                + "\nFinalised: \t" + results7.getString("FINALISED")
                                + "\nCompletion Date: " + results7.getString("COMPLETION_DATE")
                                + "\n"
                );
            }
        }
    }
    /**
     * This method allows users to delete project objects from the project_info table in the external 'PoisePMS'
     * database by entering the project  number.
     * <p>
     * Using the project number, the project is then found from the external database (PoisePMS) and displayed in a friendly_manner.
     * <p>
     * @param statement statement object linked to connection to perform SQL commands
     * @throws SQLException occurs if an error occured while accessing the database information
     */

    public void deleteProject(Statement statement) throws SQLException {

        System.out.println("\nPlease enter the project number to delete: ");
        String project_name = stringcheck("project name");

        statement.executeUpdate(
                "DELETE FROM project_info WHERE PROJ_NAME = '" + project_name + "'");

        // A successful message is displayed and the user can then view the updated book list.
        System.out.println("\nYour project was successfully deleted. Please view updated projects below: \n");
        printAllFromTable(statement);

    }


    /**
     * This method shows all information from the project_info table in the PoisePMS database when it is called on.
     * <p>
     * @param statement statement object linked to connection to perform SQL commands
     * @throws SQLException occurs if an error occurred while accessing the database information
     */
    public void printAllFromTable(Statement statement) throws SQLException{

        //This selects all information by all rows from the project_info table in the external database (PoisePMS).
        ResultSet results = statement.executeQuery("SELECT * FROM project_info");

        //Going through the information in each column to display to the user.
        while (results.next()) {
            System.out.println(
                    "Project Number: \t" + results.getInt("PROJ_NUM")
                            + "\nProject Name: \t" + results.getString("PROJ_NAME")
                            + "\nBuilding Type: \t" + results.getString("BUILDING_TYPE")
                            + "\nPhysical Address: " + results.getString("ADDRESS")
                            + "\nERF Number: \t" + results.getString("ERF_NUM")
                            + "\nTotal Fee: \tR" + results.getFloat("TOTAL_FEE")
                            + "\nAmount Paid: \t" + results.getFloat("AMOUNT_PAID")
                            + "\nDeadline: \t" + results.getString("DEADLINE")
                            + "\nFinalised: \t" + results.getString("FINALISED")
                            + "\nCompletion Date: " + results.getString("COMPLETION_DATE")
                            + "\n"
            );
        }
    }
}