import java.util.Scanner;

/**
 * PoisedCheck is the Poised Firm superclass.
 * <p>
 * This class consists of three check methods for different user inputs.
 * These methods are then inherited by the three sub_classes of the program to check if all user input are correct.
 * @author Omphemetse Marwane
 */
public class PoisedCheck {  //Main class declared.
    /**
     * This method confirms user input when asked to input an integer(a whole number)
     * @param type type explains the input required from the user
     * @return returns the output integer
     */
    public static int intcheck(String type) {

        while(true) {  //While loop repeatedly ask the user for input until it's correct.
            Scanner numinput = new Scanner(System.in);
            String number = numinput.nextLine();

            try {
                int output = Integer.parseInt(number);  //Used to parse the input to an integer to check for correct input
                return output;

            } catch (NumberFormatException ex) {
                System.out.println("Invalid Entry. Please re-enter the " + type + ": \n");  //Error message is displayed if parsing is unsuccessful.

            }
        }
    }
    /**
     * This method confirms user input when asked to enter a string.
     * @param type type explains the input required from the user
     * @return returns the output string
     */
    public static String stringcheck(String type) {

        while(true) {  //While loop repeatedly ask the user for input until it's correct.
            Scanner userinput = new Scanner(System.in);
            String input = userinput.nextLine();

            if ((input == null) || (input.length() > 150)) {  //Used to check if the input is empty(null), short, or long.
                System.out.println("Invalid Entry. Please re-enter the " + type + ": \n");

            } else {
                return input;  //Used to return the user's correctly inputted String.

            }
        }
    }
    /**
     * This method confirms user input when asked to enter a float(a number with decimals)
     * @param type type explains the input required from the user
     * @return returns the output float
     */
    public static float floatcheck(String type) {

        while(true) {  //While loop repeatedly ask the user for input until it's correct.
            Scanner floatinput = new Scanner(System.in);
            String number = floatinput.nextLine();

            try {
                float output = Float.parseFloat(number);  //Used to parse the input to a double to check for correct input.
                return output;

            } catch (NumberFormatException ex) {
                System.out.println("Invalid Entry. Please re-enter the " + type + ": \n");  //Error message is displayed if parsing was unsuccessful

            }
        }
    }

}

