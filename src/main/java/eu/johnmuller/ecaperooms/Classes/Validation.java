/**
 * @author John Muller
 */
package eu.johnmuller.ecaperooms.Classes;

import javax.swing.JFrame;

public class Validation {
    public static boolean string(JFrame frame, String text, int minTextLength, int maxTextLength, String fieldName) {
        text = text.trim();
        if (!text.equals("") && !text.equals(" ") && text.matches("^[a-zA-Z\\s]*$") && ((text.length() >= minTextLength) && (text.length() <= maxTextLength)))
            return true;
        // if user input is invalid show an error dialog
        Dialog.showErrorDialog(fieldName + " field must be\nbetween" + minTextLength + " - " + maxTextLength + " alphabetical letters", frame, "Escape Rooms");
        return false;
    }

    public static boolean stringDigits(JFrame frame, String text, int minTextLength, int maxTextLength, String fieldName) {
        text = text.trim();
        // if user input is valid return true
        if (!text.equals("") && !text.equals(" ") && text.matches("\\d+") && ((text.length() >= minTextLength) && (text.length() <= maxTextLength)))
            return true;
        // if user input is invalid show an error dialog
        Dialog.showErrorDialog(fieldName + " field must be\nbetween " + minTextLength + " - " + maxTextLength + " digits", frame, "Escape Rooms");
        return false;
    }

}
