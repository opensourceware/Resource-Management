package Login;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class guestuser_testrunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(guestuser_test.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
   }
}
