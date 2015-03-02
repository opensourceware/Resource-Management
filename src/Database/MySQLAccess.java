package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;


public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private ResultSetMetaData metaData = null;
    MySQLCabAccess CabDbase = new MySQLCabAccess();
    MySQLClassRoomAccess ClassDbase = new MySQLClassRoomAccess();
    Object[] entries = null;
    int BookingNo;
    
    public MySQLAccess() {
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
  
  
    public boolean validateUser(String username, String password) {
	    try {
		    //resultSet gets the result of the SQL query
		    resultSet = statement.executeQuery("select pwd from registered_users where"+
		  	  	"UserName = " + username);
		    resultSet.first();
		    String truePwd = resultSet.getString("pwd");
		  // make changes in GUI from here or return true;
		    if (password == truePwd) {
		        return true;
		    }
	  }
	    catch (SQLException e) {
		    e.printStackTrace();
	    }
	    return false;
  }
  
    
    public void displayResultSet(String username) {
	    try {
	        resultSet = statement.executeQuery("select * from log where " +
		    		"UserName="+username);
	        metaData = resultSet.getMetaData();
	        displayResultSet();
	  } catch (Exception e){
    	    e.printStackTrace();
        }
}


    public void getBookingStatus(int bookingNo) throws Exception {
	    try {
	        resultSet = statement.executeQuery("select * from log where " +
		    		"BookingNo="+bookingNo);
	        metaData = resultSet.getMetaData();
	        displayResultSet();
   	}   catch (SQLException e){
        	e.printStackTrace();
        }
  }

    
    public void displayResultSet() throws Exception{
		String[] columnName = new String[3];
		int RowCount = getRowCount();
		int ColumnCount = getColumnCount();
	    Object[] entries = new Object[RowCount*3];
	    resultSet.first();
		int k = 0;
		for (int j = 1; j <= RowCount; j++) {
			for  (int i = 1; i <= ColumnCount ; i++){
				columnName[i] = metaData.getColumnName(i);
				entries[k] = getValueAt(resultSet, k);
				k++;
			}
		}
    }
    

    public int getRowCount() throws SQLException {
    	resultSet.last();
	    int rowCount = resultSet.getRow();
	    return rowCount;
    }
        
    public int getColumnCount() throws SQLException {
    	int ColumnCount = metaData.getColumnCount();
    	return ColumnCount;
    }
    
    class MySQLClassRoomAccess {
    	
    	public boolean sendClassRoomDetails(Time StartingTime, Time EndingTime, String RoomNo) throws Exception {
		    String status = "Available";
		    resultSet = statement.executeQuery("select * from Rooms " +
		  	  	"where classRoom = "+ RoomNo);
		    metaData = resultSet.getMetaData();
		    
		    if (!resultSet.wasNull()) {
		    	return false;
		    }
		    else {
			    return true;
		    }
		}
	   
        public void updateDatabase(String username, String ClassRoom, String purpose, Time StartingTime, Time EndingTime) throws Exception {
		    preparedStatement = connect.prepareStatement("insert into log" +
		  	  	"values (default, ?, ?, ?, ?, ?)");
		    preparedStatement.setString(1, username);
		    preparedStatement.setString(2, ClassRoom);
		    preparedStatement.setString(3, purpose);
		    preparedStatement.setTime(4, StartingTime);
		    preparedStatement.setTime(5, EndingTime);
		    preparedStatement.executeUpdate();
		    resultSet = statement.executeQuery("select BookingNo from log");
		    resultSet.last();
		    int BookingNo = (Integer) getValueAt(resultSet, 1);
	  }
		    
	}
    
    
    class MySQLCabAccess {
    	
	    public boolean sendCabDetails(Time StartingService, Time EndingService) throws Exception {
		    String status = "Available";
		    resultSet = statement.executeQuery("select * from cabs " +
		  	  	"where status = "+ status);
		    metaData = resultSet.getMetaData();
		    
		    if (!resultSet.wasNull()) {
		    	setAvailableCabDetails();
		    	return true;
		    }
		    else {
			    resultSet = statement.executeQuery("select * from cabs " +
			    		"where BookedTime exists");
			    while (resultSet.next()) {
			    	if ((resultSet.getTime("StartingBookedTime").compareTo(StartingService) < 0 
					  	&& resultSet.getTime("EndingBookedTime").compareTo(StartingService) < 0) ||
					 		 (resultSet.getTime("StartingBookedTime").compareTo(EndingService) > 0 
								 && resultSet.getTime("EndingBookedTime").compareTo(EndingService) > 0)) {
			    		setAvailableCabDetails();
			    		return true;
			    	}
			    }
		    }
		    return false;
//		  int rowCount = getRowCount(resultSet);
		    
	}
	    
	    public void setAvailableCabDetails() throws Exception{
	    	String[] columnName = new String[3];
		    entries = new Object[3];
		    resultSet.first();
		    for  (int i = 1; i <= metaData.getColumnCount(); i++){
		        columnName[i] = metaData.getColumnName(i);
			    entries[i] = getValueAt(resultSet, i);
		    }
	    }
	}


	    public Object getValueAt(ResultSet resultSet, int i) throws Exception {
		    resultSet.absolute(1);
		    return resultSet.getObject(i);
	    }
	    
  	    public int getRowCount(ResultSet resultSet) throws Exception{
  		    resultSet.last();
  		    return resultSet.getRow();
    	  }
  	    
	    public void updateDatabase(String username, String CabDriver, String CabNo, Time StartingService, Time EndingService) throws Exception {
		    preparedStatement = connect.prepareStatement("insert into log" +
		  	  	"values (default, ?, ?, ?, ?, ?)");
		    preparedStatement.setString(1, username);
		    preparedStatement.setString(2, CabDriver);
		    preparedStatement.setString(3, CabNo);
		    preparedStatement.setTime(4, StartingService);
		    preparedStatement.setTime(5, EndingService);
		    preparedStatement.executeUpdate();
		    resultSet = statement.executeQuery("select BookingNo from log");
		    resultSet.last();
		    int BookingNo = (Integer) getValueAt(resultSet, 1);
	  }
  }


  