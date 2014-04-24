package htds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 
 * @author Younghun
 * This Java code is depending on the Connector/J driver.
 * You can download the driver from the link below.
 * 
 * Link: http://dev.mysql.com/downloads/connector/j/
 * 
 * For some reason, it is not able to connect to the database on campus, we might need to install
 * MySQL for each computer.
 * 
 * Link: http://dev.mysql.com/downloads/
 */
public class DBConnector {
	private final String dbClass = "com.mysql.jdbc.Driver";
	//private final String user = "htds"; 
	//private final String pass = "htds2014";
	//private final String url = "jdbc:mysql://db4free.net:3306/csc505?" + "user=" + user + "&password=" + pass + "&useUnicode=true&characterEncoding=UTF-8";
	//private final String url = "jdbc:mysql://x10hotsting.com:3306/htdsx10m_htds?user=htdsx10m_agent&password=htds2014&useUnicode=true&characterEncoding=UTF-8";
	private final String url = "jdbc:mysql://localhost:3306/htds?user=htds&password=htds2014&useUnicode=true&characterEncoding=UTF-8";
	public static final int VICTIM_NAME = 0;
	public static final int PHONE_NUMBER = 1;
	public static final int COLOR = 2;
	private User g_user = null;
	
	/**
	 * Constructor. Doing nothing yet. 
	 */
	public DBConnector(){

	}

	/**
	 * Works only in this class.<br>
	 * Helps to connect to the database.
	 * @return connection
	 */
	private Connection connect(){
		
		try{
			Class.forName(dbClass);
			return DriverManager.getConnection(this.url);//, this.user, this.pass);
		}catch(Exception e){
			System.out.println("DBError" + e.toString());
			return null;
		}
	}
	
	/**
	 * Sequence Diagram 1: Login<br>
	 * To compare a pair of username and password to the information in the database
	 * 
	 * @param username - user input for the username
	 * @param password - user input for the password
	 * @return User tuple if there is a matched information
	 */
	public User login(String username, String password){
		Connection connection = null;
		ResultSet rs = null;
		User user = new User();
		UserProfile userProfile = new UserProfile();
		PreparedStatement pstmt = null;
		String query = "SELECT * FROM USER JOIN USERPROFILE ON USER.USERPROFILEID = USERPROFILE.USERPROFILEID WHERE USERNAME = ? AND PASSWORDS = PASSWORD(?)";
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					user.setUserID(rs.getInt("USERID"));
					user.setFullname(rs.getString("FULLNAME"));
					user.setUsername(rs.getString("USERNAME"));
					user.setPassword(rs.getString("PASSWORDS"));
					
					boolean[] permissions = new boolean[4];
					permissions[0] = rs.getBoolean("VIEWS");
					permissions[1] = rs.getBoolean("UPLOAD");
					permissions[2] = rs.getBoolean("ANALYZES");
					permissions[3] = rs.getBoolean("CONFIGURE");
					
					userProfile.setProfileID(rs.getInt("USERPROFILEID"));
					userProfile.setPermissions(permissions);
					userProfile.setProfileName(rs.getString("PROFILENAME"));
					
					user.setUserProfile(userProfile);
				}
				pstmt.close();
				rs.close();
				connection.close();				
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		g_user = user;
		return user;
	}
	
	/**
	 * 
	 * @param _SPN
	 * @return
	 */
	private String[] getVictimNames(String _SPN, int dataLogID){
		Connection connection = null;
		ResultSet rs = null;
		String[] victimNames = null;
		
		PreparedStatement pstmt = null;
		String query = "SELECT V_NAME FROM DATA WHERE PHONE = ? AND DATALOGID = ?";
		
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, _SPN);
				pstmt.setInt(2, dataLogID);
				rs = pstmt.executeQuery();
				
				int count = 0;
				
				int size = 0;
				if(rs.last()){
					size = rs.getRow();
					rs.beforeFirst();
				}
				
				victimNames = new String[size];
				while(rs.next()){
					victimNames[count++] = rs.getString("V_NAME");
				}
				
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		return victimNames;
	}
	
	/**
	 * Sequence Diagram 3: View Alerts<br>
	 * Retrieves all the alerts from the database
	 * 
	 * @return An array of the retrieved alerts
	 */
	public Alert[] getAlerts(){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Alert[] alert = null;
		String query = "SELECT * FROM ALERTS A JOIN ALERTLOG AL ON A.ALERTLOGID = AL.ALERTLOGID";
		
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				rs = pstmt.executeQuery();
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					alert = new Alert[size];
					int count = 0;
					while(rs.next()){
						int alertID = rs.getInt("ALERTID");
						String color = rs.getString("COLOR");
						int frequency = rs.getInt("FREQUENCY");
						String phone = rs.getString("SPN");

						int alertLogID = rs.getInt("ALERTLOGID");
						int userID = rs.getInt("USERID");
						int dataLogID = rs.getInt("DATALOGID");
						Date date = rs.getDate("DATE");
						int alertProfileLogID = rs.getInt("ALERTPROFILELOGID");
						AlertLog alertLog = new AlertLog(alertLogID, userID, dataLogID, date, alertProfileLogID);
						
						String[] victimNames = getVictimNames(phone, dataLogID);
						
						alert[count++] = new Alert(alertID, color, frequency, phone, alertLog, victimNames);
					}
				}
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return alert;
	}
	
	/**
	 * Sequence Diagram 3: View Alerts<br>
	 * Retrieves the all the alerts that have been generated between the startDate and endDate from the database
	 * 
	 * @param startDate - the desired start date
	 * @param endDate - the desired end date
	 * @return An array of the retrieved alerts
	 */
	public Alert[] getAlerts(Date startDate, Date endDate){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Alert[] alert = null;
		String query = "SELECT * FROM ALERTS A JOIN ALERTLOG AL ON A.ALERTLOGID = AL.ALERTLOGID WHERE DATE >= ? AND DATE <= ?";
		
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setDate(1, startDate);
				pstmt.setDate(2, endDate);
				rs = pstmt.executeQuery();
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					alert = new Alert[size];
					int count = 0;
					while(rs.next()){
						int alertID = rs.getInt("ALERTID");
						String color = rs.getString("COLOR");
						int frequency = rs.getInt("FREQUENCY");
						String phone = rs.getString("SPN");

						int alertLogID = rs.getInt("ALERTLOGID");
						int userID = rs.getInt("USERID");
						int dataLogID = rs.getInt("DATALOGID");
						Date date = rs.getDate("DATE");
						int alertProfileLogID = rs.getInt("ALERTPROFILELOGID");
						AlertLog alertLog = new AlertLog(alertLogID, userID, dataLogID, date, alertProfileLogID);
						
						String[] victimNames = getVictimNames(phone, dataLogID);
						
						alert[count++] = new Alert(alertID, color, frequency, phone, alertLog, victimNames);
					}
				}
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return alert;
	}
	
	/**
	 * Sequence Diagram 3: View Alerts<br>
	 * To get the profile of the given alert.
	 * 
	 * @param alertProfileLogID - the ID of the alert profile to be retrieved.
	 * @return An array of the retrieved alerts
	 */
	public Alert[] getAlerts(int alertProfileLogID){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Alert[] alert = null;
		String query = "SELECT * FROM ALERTS A JOIN ALERTLOG AL ON A.ALERTLOGID = AL.ALERTLOGID WHERE AL.ALERTPROFILELOGID = ?";
		
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, alertProfileLogID);
				rs = pstmt.executeQuery();
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					alert = new Alert[size];
					int count = 0;
					while(rs.next()){
						int alertID = rs.getInt("ALERTID");
						String color = rs.getString("COLOR");
						int frequency = rs.getInt("FREQUENCY");
						String phone = rs.getString("SPN");

						int alertLogID = rs.getInt("ALERTLOGID");
						int userID = rs.getInt("USERID");
						int dataLogID = rs.getInt("DATALOGID");
						Date date = rs.getDate("DATE");
						//int alertProfileLogID = rs.getInt("ALERTPROFILELOGID");
						AlertLog alertLog = new AlertLog(alertLogID, userID, dataLogID, date, alertProfileLogID);
						
						String[] victimNames = getVictimNames(phone, dataLogID);
						
						alert[count++] = new Alert(alertID, color, frequency, phone, alertLog, victimNames);
					}
				}
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return alert;
	}
	
	/**
	 * Sequence Diagram 3: View Alerts<br>
	 * Retrieves from the database all the alerts that match string in one of the conditions.<br><br>
	 * types:<br>
	 * VICTIM_NAME<br>
	 * PHONE_NUMBER<br>
	 * COLOR
	 * 
	 * @param string - keyword by which to retrieve the alerts
	 * @param type - signifies what kind of string is being passed in
	 * @return An array of the retrieved alerts
	 */
	public Alert[] getAlerts(String string, int type){
		if(type < 0 || type > 3)	return null;
		
		Connection connection = null;
		ResultSet rs = null;
		Statement statement = null;
		Alert[] alert = null;
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT * FROM ALERTS A JOIN DATA D ON A.SPN = D.PHONE JOIN ALERTLOG AL ON A.ALERTLOGID = AL.ALERTLOGID WHERE ");
		
		if(type == VICTIM_NAME){
			query.append("V_NAME = \'");
			query.append(string);
			query.append("\'");
		} else if (type == PHONE_NUMBER){
			query.append("PHONE = \'");
			query.append(string);
			query.append("\'");
		} else if (type == COLOR){
			query.append("COLOR = \'");
			query.append(string);
			query.append("\'");
		}
		
		connection = connect();
		if(connection != null){
			try{
				statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					alert = new Alert[size];
					int count = 0;
					while(rs.next()){
						int alertID = rs.getInt("ALERTID");
						String color = rs.getString("COLOR");
						int frequency = rs.getInt("FREQUENCY");
						String phone = rs.getString("SPN");

						int alertLogID = rs.getInt("ALERTLOGID");
						int userID = rs.getInt("USERID");
						int dataLogID = rs.getInt("DATALOGID");
						Date date = rs.getDate("DATE");
						int alertProfileLogID = rs.getInt("ALERTPROFILELOGID");
						AlertLog alertLog = new AlertLog(alertLogID, userID, dataLogID, date, alertProfileLogID);
						
						String[] victimNames = getVictimNames(phone, dataLogID);
						
						alert[count++] = new Alert(alertID, color, frequency, phone, alertLog, victimNames);
					}
				}
				statement.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return alert;
	}
	
	/**
	 * Sequence Diagram 3: View Alerts<br>
	 * Retrieves all the alerts that have been generated by the user from the database
	 * 
	 * @param user - the user information
	 * @return An array of the retrieved alerts
	 */
	public Alert[] getAlerts(User user){
		Connection connection = null;
		ResultSet rs = null;
		Alert[] alert = null;
		boolean first = true;
		Statement statement = null;
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT * FROM ALERTS A JOIN ALERTLOG AL ON A.ALERTLOGID = AL.ALERTLOGID JOIN USER U ON AL.USERID = U.USERID WHERE ");
		if(user.getUserID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getUserID()));
			first = false;
		} 
		
		if(user.getFullname() != null){
			if(first)	query.append("FULLNAME = \'");
			else		query.append("AND FULLNAME = \'");
			query.append(user.getFullname());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append("AND USERNAME = \'");
			query.append(user.getUsername());
			query.append("\'");
		} 
		
		connection = connect();
		if(connection != null){
			try{
				statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					alert = new Alert[size];
					int count = 0;
					while(rs.next()){
						int alertID = rs.getInt("ALERTID");
						String color = rs.getString("COLOR");
						int frequency = rs.getInt("FREQUENCY");
						String phone = rs.getString("SPN");
						
						int alertLogID = rs.getInt("ALERTLOGID");
						int userID = rs.getInt("USERID");
						int dataLogID = rs.getInt("DATALOGID");
						Date date = rs.getDate("DATE");
						int alertProfileLogID = rs.getInt("ALERTPROFILELOGID");
						AlertLog alertLog = new AlertLog(alertLogID, userID, dataLogID, date, alertProfileLogID);
						
						String[] victimNames = getVictimNames(phone, dataLogID);
						
						alert[count] = new Alert(alertID, color, frequency, phone, alertLog, victimNames);
						count++;
					}
				}
				statement.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return alert;
	}
	
	/**
	 * Sequence Diagram 5: Analyze Data<br>
	 * To get the profile of the given alert.
	 * 
	 * @param alertProfileLogID - the ID of the alert profile to be retrieved.
	 * @return AlertProfile Object
	 */
	public AlertProfile getAlertProfile(int alertProfileLogID){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		AlertProfile alertProfile = null;
		AlertProfileLog alertProfileLog = null;
		
		String query = "SELECT * FROM ALERTPROFILE A JOIN ALERTPROFILELOG AL ON A.ALERTPROFILELOGID = AL.ALERTPROFILELOGID WHERE A.ALERTPROFILELOGID = ?;";
		
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, alertProfileLogID);
				rs = pstmt.executeQuery();
								
				if(rs.next()){
					int userID = rs.getInt("USERID");
					String alertDescription = rs.getString("DESCRIPTION");
					Date date = rs.getDate("DATE");
					
					alertProfileLog = new AlertProfileLog(alertProfileLogID, userID, alertDescription, date);
					
					int alertProfileID = rs.getInt("ALERTPROFILEID");
					int[] thresholds = new int[5];
					
					rs.beforeFirst();
					while(rs.next()){
						String color = rs.getString("COLOR");
						int th = rs.getInt("THRESHOLD");
						if(color.equals("Red")){
							thresholds[0] = th; 
						} else if (color.equals("Orange")){
							thresholds[1] = th;
						} else if (color.equals("Yellow")){
							thresholds[2] = th;
						} else if (color.equals("Blue")){
							thresholds[3] = th;
						} else if (color.equals("Green")){
							thresholds[4] = th;
						}
					}
					alertProfile = new AlertProfile(alertProfileID, thresholds, alertDescription, alertProfileLog);
				}
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return alertProfile;
	}
	
	/**
	 * Sequence Diagram 4: Upload Input Files<br>
	 * 
	 * To upload a file of victim names and phone numbers to the database.
	 * @param filename - What the user is willing to insert into the database.
	 * @return True if the operation succeeded.
	 */
	public boolean uploadFile(String filename){
		Connection connection = null;
		int dataLogID = 0, result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query1 = "INSERT INTO DataLog (FileName, Date, UserID) VALUES (?, ?, ?)";
		String query2 = "INSERT INTO Data (V_Name, Phone, DataLogID) VALUES (?, ?, ?)";
		
		connection = connect();
		if(connection != null){
			try{
				// Insert into DATALOG
				pstmt = connection.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, filename);
				
				java.util.Date u_date = new java.util.Date();
				Timestamp timestamp = new Timestamp(u_date.getTime());
				pstmt.setTimestamp(2, timestamp);
				pstmt.setInt(3, g_user.getUserID());
				
				result = pstmt.executeUpdate();
				
				if(result == 0){
					connection.close();
					return false;
				}
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					dataLogID = rs.getInt(1);
				}
				rs.close();
				pstmt.close();
				
				// Insert into DATA
				pstmt = connection.prepareStatement(query2);
				try{
					BufferedReader br = new BufferedReader(new FileReader(filename));
					String line;
					while((line = br.readLine()) != null){
						String[] lineArray = line.trim().split(",");
						pstmt.setString(1, lineArray[0].trim());
						pstmt.setString(2, lineArray[1].trim());
						pstmt.setInt(3, dataLogID);
						result = pstmt.executeUpdate();
						if(result == 0){
							br.close();
							pstmt.close();
							connection.close();
							return false;
						}
					}
					br.close();
				} catch(Exception e) {
					System.out.println(e.toString());
				}
				pstmt.close();
				connection.close();
				return true;
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		return false;
	}

	/**
	 * To upload a file of victim names and phone numbers to the database.
	 * @param datalogID
	 * @return 
	 */
	public Data[] getAnalysis(int dataLogID){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Data[] data = null;
		String query = "SELECT PHONE, COUNT(PHONE) AS FREQUENCY FROM DATA D JOIN DATALOG DL ON D.DATALOGID = DL.DATALOGID WHERE D.DATALOGID = ? GROUP BY PHONE HAVING COUNT(PHONE) > 1";
		
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, dataLogID);
				rs = pstmt.executeQuery();
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					data = new Data[size];
					int count = 0;
					while(rs.next()){
						String phone = rs.getString("PHONE");
						int frequency = rs.getInt("FREQUENCY");
						
						data[count] = new Data();
						data[count].setPhone(phone);
						data[count].setFrequency(frequency);
					}
				}
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return data;
	}

	/**
	 * Sequence Diagram 5: Analyze Data <br>
	 * 
	 * To add the alerts to the database
	 * @param alerts - an array of the Alert object
	 * @return True if the operation succeeded.
	 */
	public boolean uploadAlert(Alert[] alerts){
		Connection connection = null;
		int alertLogID = 0, result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query1 = "INSERT INTO ALERTLOG (USERID, DATALOGID, ALERTPROFILELOGID, DATE) VALUES (?, ?, ?, ?)";
		String query2 = "INSERT INTO ALERTS (COLOR, FREQUENCY, SPN, ALERTLOGID) VALUES (?, ?, ?, ?)";
		
		connection = connect();
		if(connection != null){
			try{
				// Insert into ALERTLOG
				pstmt = connection.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, alerts[0].getAlertLog().getUserID());
				pstmt.setInt(2, alerts[0].getAlertLog().getDataLogID());
				pstmt.setInt(3, alerts[0].getAlertLog().getAlertProfileID());
				
				java.util.Date u_date = new java.util.Date();
				Timestamp timestamp = new Timestamp(u_date.getTime());
				pstmt.setTimestamp(4, timestamp);
				
				result = pstmt.executeUpdate();
				
				if(result == 0){
					connection.close();
					return false;
				}
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					alertLogID = rs.getInt(1);
				}
				rs.close();
				pstmt.close();
				
				// Insert into ALERT
				pstmt = connection.prepareStatement(query2);
				int i = 0;
				for(i = 0; i < alerts.length; i++){
					pstmt.setString(1, alerts[i].getColor());
					pstmt.setInt(2, alerts[i].getFrequency());
					pstmt.setString(3, alerts[i].getSPN());
					pstmt.setInt(4, alertLogID);
					result = pstmt.executeUpdate();
					if(result == 0){
						pstmt.close();
						connection.close();
						return false;
					}
				}
				pstmt.close();
				connection.close();
			} catch(Exception e) {
				System.out.println(e.toString());
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Sequence Diagram 6: Add User<br>
	 * To add a new user to the database.
	 * 
	 * @param user - information about the user to be added to the system.
	 * @return True if the operation succeeded.
	 */
	public boolean addUser(User user){
		int result = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO USER (FULLNAME, USERNAME, PASSWORDS, USERPROFILEID) VALUES( ?, ?, password(?), ?)";
		connection = connect();
		
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, user.getFullname());
				pstmt.setString(2, user.getUsername());
				pstmt.setString(3, user.getPassword());
				pstmt.setInt(4, user.getUserProfile().getProfileID());
				result = pstmt.executeUpdate();
		
				pstmt.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		if(result == 0)	return false;
		else			return true;
	}
	
	/**
	 * Sequence Diagram 6: Delete User<br>
	 * To remove a user from the database.
	 * 
	 * @param user
	 * @return true or false
	 */
	public boolean removeUser(User user){
		int result = 0;
		boolean first = true;
		Connection connection = null;
		Statement statement = null;
		StringBuffer query = new StringBuffer();
		
		query.append("DELETE FROM USER WHERE ");
		if(user.getUserID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getUserID()));
			first = false;
		} 
		
		if(user.getFullname() != null){
			if(first)	query.append("FULLNAME = \'");
			else		query.append("AND FULLNAME = \'");
			query.append(user.getFullname());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append("AND USERNAME = \'");
			query.append(user.getUsername());
			query.append("\'");
		} 
				
		connection = connect();
		if(connection != null){
			try{
				statement = connection.createStatement();
				result = statement.executeUpdate(query.toString());
				statement.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		if(result == 0)	return false;
		else			return true;
	}
	
	/**
	 * Sequence Diagram 8: Update User <br>
	 * 
	 * Changes the password of the given user.<br> 
	 * Only the administrator has the power to change the passwords of the users<br>
	 * the users do not have the power to change their own passwords.
	 * 
	 * @param user - which user¡¯s password that is being changed
	 * @param password - the string of the new passphrase
	 * @return
	 */
	public boolean updateUserPassword(User user, String password){
		int result = 0;
		boolean first = true;
		Connection connection = null;
		Statement statement = null;
		StringBuffer query = new StringBuffer();
		
		query.append("UPDATE USER SET PASSWORDS=PASSWORD(\'");
		query.append(password);
		query.append("\') WHERE ");
		if(user.getUserID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getUserID()));
			first = false;
		} 
		
		if(user.getFullname() != null){
			if(first)	query.append("FULLNAME = \'");
			else		query.append("AND FULLNAME = \'");
			query.append(user.getFullname());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append("AND USERNAME = \'");
			query.append(user.getUsername());
			query.append("\'");
		} 
				
		connection = connect();
		if(connection != null){
			try{
				statement = connection.createStatement();
				result = statement.executeUpdate(query.toString());
				statement.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		if(result == 0)	return false;
		else			return true;
	}
	
	/**
	 * Sequence Diagram 8: Update User <br>
	 * Changes the profile of the given user.
	 * 
	 * @param user - which user¡¯s profile that is being changed
	 * @param userProfile - the new profile that will overwrite the old profile (Uses only the UserProfileID)
	 * @return True if the operation succeeded.
	 */
	public boolean updateUserProfile(User user, UserProfile userProfile){
		int result = 0;
		boolean first = true;
		Connection connection = null;
		Statement statement = null;
		StringBuffer query = new StringBuffer();
		
		query.append("UPDATE USER SET USERPROFILEID = ");
		query.append(Integer.toString(userProfile.getProfileID()));
		query.append(" WHERE ");
		if(user.getUserID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getUserID()));
			first = false;
		} 
		
		if(user.getFullname() != null){
			if(first)	query.append("FULLNAME = \'");
			else		query.append("AND FULLNAME = \'");
			query.append(user.getFullname());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append("AND USERNAME = \'");
			query.append(user.getUsername());
			query.append("\'");
		} 
				
		connection = connect();
		if(connection != null){
			try{
				statement = connection.createStatement();
				result = statement.executeUpdate(query.toString());
				statement.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		if(result == 0)	return false;
		else			return true;
	}
	
	/**
	 * Sequence Diagram 8: Update User<br>
	 * Inserts a user profile into the database. 
	 * 
	 * @param userProfile - desired user profile information
	 * @return True if the operation succeeded.
	 */ 
	public boolean createUserProfile(UserProfile userProfile){
		int result = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO USERPROFILE (PROFILENAME, VIEWS, UPLOAD, ANALYZES, CONFIGURE) VALUES( ?, ?, ?, ?, ?)";
		connection = connect();
		
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, userProfile.getProfileName());
				
				boolean[] permissions = userProfile.getPermissions();
				pstmt.setBoolean(2, permissions[0]);
				pstmt.setBoolean(3, permissions[1]);
				pstmt.setBoolean(4, permissions[2]);
				pstmt.setBoolean(5, permissions[3]);
				result = pstmt.executeUpdate();

				pstmt.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		if(result == 0)	return false;
		else			return true;
	}
	
	/**
	 * Sequence Diagram 8: Update User<br>
	 * Inserts an alert profile into the database.
	 * 
	 * @param alertProfile - desired alert profile information
	 * @return True if the operation succeeded.
	 */
	public boolean createAlertProfile(AlertProfile alertProfile){
		Connection connection = null;
		int alertProfileLogID = 0, result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query1 = "INSERT INTO ALERTPROFILELOG (USERID, DATE, DESCRIPTION) VALUES (?, ?, ?)";
		String query2 = "INSERT INTO ALERTPROFILE (LEVELS, COLOR, THRESHOLD, ALERTPROFILELOGID) VALUES (?, ?, ?, ?)";
		
		connection = connect();
		if(connection != null){
			try{
				// Insert into ALERTPROFILELOG
				pstmt = connection.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, g_user.getUserID());
				
				java.util.Date u_date = new java.util.Date();
				Timestamp timestamp = new Timestamp(u_date.getTime());
				pstmt.setTimestamp(2, timestamp);
				
				pstmt.setString(3, alertProfile.getDescription());
				result = pstmt.executeUpdate();
				
				if(result == 0){
					connection.close();
					return false;
				}
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					alertProfileLogID = rs.getInt(1);
				}
				rs.close();
				pstmt.close();
				
				// Insert into ALERTPROFILE
				pstmt = connection.prepareStatement(query2);
				String[] color = AlertProfile.getColors();
				int[] thresholds = alertProfile.getThresholds();
				int i = 0;
				for(i = 0; i < 5; i++){
					pstmt.setShort(1, (short)(i + 1));
					pstmt.setString(2, color[i]);
					pstmt.setInt(3, thresholds[i]);
					pstmt.setInt(4, alertProfileLogID);
					result = pstmt.executeUpdate();
					
					if(result == 0){
						pstmt.close();
						connection.close();
						return false;
					}
				}
				
				pstmt.close();
				connection.close();
			} catch(Exception e) {
				System.out.println(e.toString());
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Sequence Diagram 11: View Data<br>
	 * Retrieves all the data from the database
	 * 
	 * @return An array of the retrieved data
	 */
	public Data[] getData(){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Data[] data = null;
		String query = "SELECT * FROM DATA D JOIN DATALOG DL ON D.DATALOGID = DL.DATALOGID";
		
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				rs = pstmt.executeQuery();
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					data = new Data[size];
					int count = 0;
					while(rs.next()){
						data[count] = new Data();
						data[count].setDataID(rs.getInt("DATAID"));
						data[count].setV_Name(rs.getString("V_NAME"));
						data[count].setPhone(rs.getString("PHONE"));
						
						DataLog dl = new DataLog();
						dl.setDataLogID(rs.getInt("DATALOGID"));
						dl.setFileName(rs.getString("FILENAME"));
						dl.setDate(rs.getDate("DATE"));
						dl.setUserID(rs.getInt("USERID"));
						
						data[count++].setDataLog(dl);
					}
				}
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return data;
	}
	
	/**
	 * Sequence Diagram 11: View Data<br>
	 * Retrieves all the data that matches the data information from the database
	 * 
	 * @param data - the desired data information
	 * @return An array of the retrieved data
	 */
	public Data[] getData(Data data){
		Connection connection = null;
		ResultSet rs = null;
		Data[] _data = null;
		Statement statement = null;
		boolean first = true;
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT * FROM DATA D JOIN DATALOG DL ON D.DATALOGID = DL.DATALOGID WHERE ");
		if(data.getVictimName() != null){
			query.append("V_NAME = \'");
			query.append(data.getVictimName());
			query.append("\'");
			first = false;
		} 
		if(data.getPhone() != null){
			if(first)	query.append("PHONE = \'");
			else		query.append("AND PHONE = \'");
			query.append(data.getPhone());
			query.append("\'");
		} 
		if(data.getDataLog() != null){
			if(data.getDataLog().getDataLogID() != 0){
				if(first)	query.append("DATALOGID = \'");
				else		query.append("AND DATALOGID = \'");
				query.append(Integer.toString(data.getDataLog().getDataLogID()));
			}
		}
		
		connection = connect();
		if(connection != null){
			try{
				statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					_data = new Data[size];
					int count = 0;
					while(rs.next()){
						_data[count] = new Data();
						_data[count].setDataID(rs.getInt("DATAID"));
						_data[count].setV_Name(rs.getString("V_NAME"));
						_data[count].setPhone(rs.getString("PHONE"));
						
						DataLog dl = new DataLog();
						dl.setDataLogID(rs.getInt("DATALOGID"));
						dl.setFileName(rs.getString("FILENAME"));
						dl.setDate(rs.getDate("DATE"));
						dl.setUserID(rs.getInt("USERID"));
						
						_data[count++].setDataLog(dl);
					}
				}
				statement.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return _data;
	}
	
	/**
	 * Sequence Diagram 11: View Data<br>
	 * Retrieves all the data that matches the data log information from the database
	 * 
	 * @param dataLogID - the desired data log information
	 * @return An array of the retrieved data
	 */
	public Data[] getData(int dataLogID){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Data[] data = null;
		String query = "SELECT * FROM DATA D JOIN DATALOG DL ON D.DATALOGID = DL.DATALOGID WHERE D.DATALOGID = ?";
		
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, dataLogID);
				rs = pstmt.executeQuery();
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					data = new Data[size];
					int count = 0;
					while(rs.next()){
						data[count] = new Data();
						data[count].setDataID(rs.getInt("DATAID"));
						data[count].setV_Name(rs.getString("V_NAME"));
						data[count].setPhone(rs.getString("PHONE"));
						
						DataLog dl = new DataLog();
						dl.setDataLogID(rs.getInt("DATALOGID"));
						dl.setFileName(rs.getString("FILENAME"));
						dl.setDate(rs.getDate("DATE"));
						dl.setUserID(rs.getInt("USERID"));
						
						data[count++].setDataLog(dl);
					}
				}
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return data;
	}
	
	/**
	 * Sequence Diagram 11: View Data<br>
	 * Retrieves all the data that has been generated by the user from the database
	 * 
	 * @param user - the user information
	 * @return An array of the retrieved data
	 */
	public Data[] getData(User user){
		Connection connection = null;
		ResultSet rs = null;
		Data[] data = null;
		boolean first = true;
		Statement statement = null;
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT * FROM DATA D JOIN DATALOG DL ON D.DATALOGID = DL.DATALOGID JOIN USER U ON DL.USERID = U.USERID WHERE ");
		if(user.getUserID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getUserID()));
			first = false;
		} 
		
		if(user.getFullname() != null){
			if(first)	query.append("FULLNAME = \'");
			else		query.append("AND FULLNAME = \'");
			query.append(user.getFullname());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append("AND USERNAME = \'");
			query.append(user.getUsername());
			query.append("\'");
		}
		
		connection = connect();
		if(connection != null){
			try{
				statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					data = new Data[size];
					int count = 0;
					while(rs.next()){
						data[count] = new Data();
						data[count].setDataID(rs.getInt("DATAID"));
						data[count].setV_Name(rs.getString("V_NAME"));
						data[count].setPhone(rs.getString("PHONE"));
						
						DataLog dl = new DataLog();
						dl.setDataLogID(rs.getInt("DATALOGID"));
						dl.setFileName(rs.getString("FILENAME"));
						dl.setDate(rs.getDate("DATE"));
						dl.setUserID(rs.getInt("USERID"));
						
						data[count++].setDataLog(dl);
					}
				}
				statement.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return data;
	}
	
	/**
	 * Sequence Diagram 11: View Data<br>
	 * Retrieves all the data that matches string in one of the conditions.<br><br>
	 * types:<br>
	 * VICTIM_NAME<br>
	 * PHONE_NUMBER<br>
	 * 
	 * @param string - keyword by which the data is retrieved
	 * @param type - signifies what kind of string is being passed in
	 * @return An array of the retrieved data
	 */
	public Data[] getData(String string, int type){
		if(type < 0 || type > 2)	return null;
		
		Connection connection = null;
		ResultSet rs = null;
		Statement statement = null;
		Data[] data = null;
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT * FROM DATA D JOIN DATALOG DL ON D.DATALOGID = DL.DATALOGID WHERE ");
		
		if(type == VICTIM_NAME){
			query.append("V_NAME = \'");
			query.append(string);
			query.append("\'");
		} else if (type == PHONE_NUMBER){
			query.append("PHONE = \'");
			query.append(string);
			query.append("\'");
		} else if (type == COLOR){
			return null;
		}
		
		connection = connect();
		if(connection != null){
			try{
				statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());
								
				if(rs != null){
					int size = 0;
					if(rs.last()){
						size = rs.getRow();
						rs.beforeFirst();
					}

					data = new Data[size];
					int count = 0;
					while(rs.next()){
						data[count] = new Data();
						data[count].setDataID(rs.getInt("DATAID"));
						data[count].setV_Name(rs.getString("V_NAME"));
						data[count].setPhone(rs.getString("PHONE"));
						
						DataLog dl = new DataLog();
						dl.setDataLogID(rs.getInt("DATALOGID"));
						dl.setFileName(rs.getString("FILENAME"));
						dl.setDate(rs.getDate("DATE"));
						dl.setUserID(rs.getInt("USERID"));
						
						data[count++].setDataLog(dl);
					}
				}
				statement.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return data;
	}
}
