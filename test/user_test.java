package Login;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;


public class user_test 
{ User m=new User();
@Test
public void testgetusername(){
String testusername="xyz";
m.setUsername(testusername);
assertEquals(m.getUsername(),testusername);

}
@Test
public void testgetpassword()
{
String password="1234567";
m.setPassword(password);
assertEquals(m.getPassword(),password); 
}


	


}
