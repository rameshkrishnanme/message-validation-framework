package smartvalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoundaryMatcherDemo {
    
   private static final String REGEX = "(.R\\/(FQTV|FQTU|FQTQ) [A-Z]{2} \\d+)+";
   private static final String INPUT = ".R/FQTV IB 123766565644 .R/FQTU AB 123766565644";

   public static void main(String[] args) {
      // create a pattern
      Pattern  pattern = Pattern.compile(REGEX);
      
      // get a matcher object
      Matcher matcher = pattern.matcher(INPUT); 

      while(matcher.find()) {
         //Prints the start index of the match.
         System.out.println("Match String start(): "+matcher.start());
      }
   }
}