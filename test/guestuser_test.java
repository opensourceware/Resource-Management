package Login;
import org.junit.Test;

import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;

public class guestuser_test 
{
	
    
   Guest m=new Guest();
    @Test   public void testgetname(){
    	String testname="xyz";
        m.setname("xyz");
        assertEquals(m.getname(),testname);
     
    }
    @Test  public void testgetnumber()
      {
    	  String test_no="1234567";
    	  m.setnumber(test_no);
       assertEquals(m.getContact_no(),test_no); 
      }
    


	
}
