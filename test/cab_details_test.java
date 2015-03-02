package Booking;

import static junit.framework.Assert.assertEquals;
import java.sql.Time;
import org.junit.Test;

public class cab_details_test {

	
	 Cab_Details  m=new Cab_Details();
	 @Test
     public void testgetplace()
     {
	   String testplace="xyz";
       m.set_place(testplace);
       assertEquals(m.get_place(),testplace);
     }
	 @Test
     public void testgetE_time()
     {
      String test_Etime="12 p.m.";
      m.setE_time(test_Etime);
	  String hour = test_Etime.substring(0,1);
	  Time e=new Time(Integer.valueOf(hour));
      assertEquals(m.getE_time(),e); 
     }
    	
	 @Test
     public void testget_Stime()
     {
	  String test_Stime="10 a.m.";
	  m.setS_time(test_Stime);
	  String hour = test_Stime.substring(0,1);
	  Time s=new Time(Integer.valueOf(hour));
      assertEquals(m.getS_time(),s); 
     }

}
