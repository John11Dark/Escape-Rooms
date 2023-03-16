/**
 * @author John Muller
 */
package eu.johnmuller.ecaperooms.Classes;

import javax.swing.*;


public class Dialog {
    //static ImageIcon errorIcon = new ImageIcon("../Assets/Images/Logo.png");
    //static ImageIcon infoIcon = new ImageIcon("../Assets/Images/Logo.png");
    //static ImageIcon warnIcon = new ImageIcon("../Assets/Images/Logo.png");

    public static void showErrorDialog(String message, JFrame frame, String dialogTitle) {
        JOptionPane.showMessageDialog(frame, message, dialogTitle, JOptionPane.ERROR_MESSAGE);
    }

    public static boolean showConfirmationDialog(JFrame frame, String message, String dialogTitle) {
        return JOptionPane.showConfirmDialog(frame, message, dialogTitle, JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void notify(JFrame frame, String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void warn(JFrame frame, String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
