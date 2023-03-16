/**
 * @author John Muller
 */
package eu.johnmuller.ecaperooms.Classes;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;

public class FileIO {

        /*
            This method should take as input the path of a file, and return a String
            with the contents of that file. After every line it reads, it should add
            “\n” to the accumulated String. In case of an exception, it should show a
            user-friendly message in a dialog box.
        */

    public static String readTextFile(JFrame frame, String filePath) {
        StringBuilder data = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            // The commented lines was for testing purposes
            // cause the program was returning null
            //int i = 0;
            while ((line = reader.readLine()) != null) {

                //System.out.println(i + "'FileIO' reader instances : " + reader.readLine());
                //System.out.println(i + "'FileIO' read Line: " + line);
                if (!line.equals("") && !line.equals("\n") && !line.equals(" "))
                    data.append(line).append("\n");
                //System.out.println( i + "'FileIO' read Data: " + data + "\n");
                //i ++;
            }
            reader.close();

        } catch (IOException exception) {
            // for debugging purposes
            exception.printStackTrace();
            // display to the user in case an error happened
            Dialog.notify(frame, "Database error", "An error occurred while storing the booking information");
        }
        return data.toString();
    }

    /*
        This method should take as input the path of a file, and a String to
        be added to the file. The method should create the file if it does not
        exist, and append the given string to the file (not overwrite it!). This
        method does not return anything. In case of an exception, it should show
        a user-friendly message in a dialog box.
    */
    public static void appendToTextFile(JFrame frame, String filePath, String toWrite) {
        String previousData;
        try {
            Path path = Paths.get(filePath);

            if (Files.exists(path)) {
                previousData = readTextFile(frame, filePath);
                toWrite = previousData + toWrite;
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(toWrite);
            writer.close();

        } catch (IOException exception) {
            // for debugging purposes
            exception.printStackTrace();
            // display to the user in case an error happened
            Dialog.notify(frame, "Database error", "An error occurred while storing the booking information");
        }
    }
    
    public static void storeBooking(JFrame frame, String filePath, String data,  String title, String message){
        appendToTextFile(frame, filePath, data);
        Dialog.notify(frame, title, message);
    }
}
