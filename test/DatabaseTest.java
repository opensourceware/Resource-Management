package Database;

import static junit.framework.Assert.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import Database.MySQLAccess.MySQLCabAccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import org.junit.Ignore;
public class DatabaseTest 
{
	
		    private Connection connect = null;
		    private Statement statement = null;
		    private PreparedStatement preparedStatement = null;
		    private ResultSet resultSet = null;
		    private ResultSetMetaData metaData = null;
	      //public MySQLCabAccess CabDbase = new MySQLCabAccess();
		    public Object[] entries;
		    public DatabaseTest(){
			   try {
		  	//load the MySQL driver
			    Class.forName("com.mysql.jdbc.Driver");
			    //setup connection with the DB.
			    connect = DriverManager.getConnection("jdbc:mysql://localhost/" +
			  	  	"resource_management?user=root&password=k9d3h3j");
			    //statements allow to issue SQL queries to the database
			    statement = connect.createStatement(
				  	  ResultSet.TYPE_SCROLL_INSENSITIVE, 
					    ResultSet.CONCUR_READ_ONLY);

		  }
		  
	  	    catch (ClassNotFoundException e) {
			    e.printStackTrace();
		  }
		    catch (SQLException e) {
			    e.printStackTrace();
		  }
	  }

 MySQLAccess m=new MySQLAccess();
 MySQLAccess.MySQLCabAccess x=m.new MySQLCabAccess() ;
 MySQLAccess.MySQLClassRoomAccess y=m.new MySQLClassRoomAccess();
 
 
 @Test
 public void testValidateUser()
 {
	 boolean b=m.validateUser("","");
	 assertTrue(b);
 }
 
 
 @Test 
 public void testUpdateDatabaseCab() {
 Time start = null,end = null;
 String Cabdriver=null;
   
	    try {
			x.updateDatabase("x", "", "",start, end);
			//resultSet gets the result of the SQL query
            resultSet = statement.executeQuery("select CabDriver from Cabs where"+
  	  	    "UserName = " +"x" );
            resultSet.first();
            Cabdriver = resultSet.getString("CabDriver");
	        } catch (Exception e) {
			                       // TODO Auto-generated catch block
			                       e.printStackTrace();
		                          } 
	        assertEquals(Cabdriver,"expected cabdriver");
      
}

 @Test 
 public void testUpdateDatabaseClassroom(){
 Time start = null,end = null;
 String Classroom=null;

	    try {
			y.updateDatabase("x", "", "",start, end);
	        //resultSet gets the result of the SQL query
            resultSet = statement.executeQuery("select Classroom from log where"+
  	  	    "UserName = " +"x" );
            resultSet.first();
            Classroom = resultSet.getString("Classroom");
	        }
	    catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    assertEquals(Classroom,"expected classroom");
 } 

 @Test
 public void testSendCabDetails() 
 {
	 Time start = null,end = null;
	 
	
		boolean b=false;
        try {
			b = x.sendCabDetails(start,end);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertEquals(b,true);
		
		}
 @Test
 public void testSendClassroomDetails()
 {
	 Time start = null,end = null;
	 String RoomNo="C405";
	 boolean b=false;
	
		try {
			b=y.sendClassRoomDetails(start, end, RoomNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(b,true);
 }

 @Test
 public void testDisplayResultSet()
 {
	 int bookingNo=3;
	       
	        
				Object arr=  new Object[1][7];
				try {
					arr=m.displayResultSet(1,7);
				} catch (Exception e) {
			
					e.printStackTrace();
				}
				assertEquals(arr,"");
			
	       
	 
	
 }
 
 }
