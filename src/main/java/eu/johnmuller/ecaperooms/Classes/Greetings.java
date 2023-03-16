
/**
 * @author John Muller
 * This Function returns greetings according to the current time
 */
package eu.johnmuller.ecaperooms.Classes;
import java.util.Date;

public class Greetings {
    public static String Display() {
   int time = new Date().getHours();
        if(time>=0 && time<=12)
            return "Good Morning";
        if(time>=12 && time<=16)
            return "Good Evening";
        if(time>=16 && time<=21)
             return "Good Evening";
      
        return "Good Night";  
    }
}
