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
 * The DBConnector class is the interface between the HTDS modules and the database
 * It provides utility functions to read and write from/to the database
 * The code was developed based on the Connector/J driver, 
 * The driver can be downloaded from the following link:
 * Link: http://dev.mysql.com/downloads/connector/j/
 * @author Younghun
 */

//Note:For some reason, it is not able to connect to the database on campus, we might need to install  MySQL for each computer.
// Link: http://dev.mysql.com/downloads/

public class DBConnector {
	private final String dbClass = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/htds?user=root&password=celtpat15&useUnicode=true&characterEncoding=UTF-8";
	
	public static final int VICTIM_NAME = 0;
	public static final int PHONE_NUMBER = 1;
	public static final int COLOR = 2;
	private static User g_user = null;
	
	/**
	 * Constructor. Doing nothing yet.
	 */
	public DBConnector(){
		
	}

	/**
	 * This function controls the login activity of a User to the HTDS system by comparing a pair of username of password
	 * to the user information stored in the database
	 * Reference: [Design Document v6.0]: 5.1 Sequence Diagram 1: Login
	 * @param username - user input for the username
	 * @param password - user input for the password
	 * @return User tuple if there is a matched information
	 */
	public User login(String username, String password){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		User user = null;
		UserProfile userProfile = null;
		
		String query = "SELECT * FROM USER JOIN USERPROFILE ON USER.USERPROFILEID = USERPROFILE.USERPROFILEID WHERE USERNAME = ? AND PASSWORDS = PASSWORD(?)";
		
		connection = connect();
		if(connection != null){
			try{
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					boolean[] permissions = new boolean[] {rs.getBoolean("VIEWS"),
							rs.getBoolean("UPLOAD"), rs.getBoolean("ANALYZES"), rs.getBoolean("CONFIGURE")} ;
					
					userProfile = new UserProfile(rs.getInt("USERPROFILEID"),rs.getString("PROFILENAME"),permissions );
					
					user = new User(rs.getInt("USERID"), rs.getString("FULLNAME"), rs.getString("USERNAME"),
							rs.getString("PASSWORDS"), userProfile);
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
	 * This method queries the database for previously generated alerts and return all available alerts
	 * Reference: [Design Document v6.0]: 5.3 Sequence Diagram 3: View Alerts
	 * @return: an Array of Alert objects 
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
						String phone = rs.getString("SPN");
						int dataLogID = rs.getInt("DATALOGID");
						String[] victimNames = getVictimNames(phone, dataLogID);
						
						AlertLog alertLog = new AlertLog(rs.getInt("ALERTLOGID"), rs.getInt("USERID"),
								dataLogID, rs.getDate("DATE"), rs.getInt("ALERTPROFILELOGID"));
						
						alert[count++] = new Alert(rs.getInt("ALERTID"), 
								rs.getString("COLOR"), phone, alertLog, victimNames);
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
	 * This method queries the database for previously generated alerts and return alerts generated within a date period
	 * Reference: [Design Document v6.0]: 5.3 Sequence Diagram 3: View Alerts
	 * @param startDate: 
	 * @param endDate: 
	 * @return an Array of Alert objects
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
						String phone = rs.getString("SPN");
						int dataLogID = rs.getInt("DATALOGID");
						String[] victimNames = getVictimNames(phone, dataLogID);
						
						AlertLog alertLog = new AlertLog(rs.getInt("ALERTLOGID"), rs.getInt("USERID"),
								dataLogID, rs.getDate("DATE"), rs.getInt("ALERTPROFILELOGID"));
						
						alert[count++] = new Alert(rs.getInt("ALERTID"), 
								rs.getString("COLOR"), phone, alertLog, victimNames);
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
	 * 
	 * @param string
	 * @param type
	 * @return
	 */
	public Alert[] getAlerts(String string, int type){
		if(type < 0 || type > 3)	
			return null;
		
		Connection connection = null;
		ResultSet rs = null;
		Statement statement = null;
		Alert[] alert = null;
		StringBuffer query = new StringBuffer();
		
		//query.append("SELECT * FROM ALERTS A JOIN ALERTLOG AL ON A.ALERTLOGID = AL.ALERTLOGID WHERE ");
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
						int dataLogID = rs.getInt("DATALOGID");
						AlertLog alertLog = new AlertLog(rs.getInt("ALERTLOGID"),
								rs.getInt("USERID"), dataLogID, rs.getDate("DATE"), rs.getInt("ALERTPROFILELOGID"));
						
						String phone = rs.getString("SPN");
						String[] victimNames = getVictimNames(phone, dataLogID);
						
						alert[count++] = new Alert(rs.getInt("ALERTID"), rs.getString("COLOR"),
								phone , alertLog, victimNames);
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
	 * 
	 * @param user
	 * @return
	 */
	public Alert[] getAlerts(User user){
		Connection connection = null;
		ResultSet rs = null;
		Alert[] alert = null;
		boolean first = true;
		Statement statement = null;
		StringBuffer query = new StringBuffer();		
		PreparedStatement pstmt = null;
		
		query.append("SELECT * FROM ALERTS A JOIN ALERTLOG AL ON A.ALERTLOGID = AL.ALERTLOGID JOIN USER U ON AL.USERID = U.USERID WHERE ");
		if(user.getID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getID()));
			first = false;
		} 

		if(user.getFullName() != null){
			if(first)	query.append("FULLNAME = \'");
			else		query.append(" AND FULLNAME = \'");
			query.append(user.getFullName());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append(" AND USERNAME = \'");
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
						String phone = rs.getString("SPN");
						int dataLogID = rs.getInt("DATALOGID");
						String[] victimNames = getVictimNames(phone, dataLogID);
						
						AlertLog alertLog = new AlertLog(rs.getInt("ALERTLOGID"), rs.getInt("USERID"),
								dataLogID, rs.getDate("DATE"), rs.getInt("ALERTPROFILELOGID"));
						
						alert[count] = new Alert(rs.getInt("ALERTID"), rs.getString("COLOR"), phone, alertLog, victimNames);
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
						String phone = rs.getString("SPN");
						int dataLogID = rs.getInt("DATALOGID");
						String[] victimNames = getVictimNames(phone, dataLogID);

						AlertLog alertLog = new AlertLog(rs.getInt("ALERTLOGID"), rs.getInt("USERID"), dataLogID, rs.getDate("DATE"), alertProfileLogID);


						alert[count++] = new Alert(rs.getInt("ALERTID"), rs.getString("COLOR"), phone, alertLog, victimNames);
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
	 * This function upload an input file into the database 
	 * Reference: [Design Document v6.0]: 5.4 Sequence Diagram 4: Upload Input File
	 * @param filename: String referring to the filename
	 * @return
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
				pstmt.setInt(3, g_user.getID());
				
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
					return false;
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
	 * This method queries the database for a specified AlertProfile
	 * Reference: [Design Document v6.0]: 5.5 Sequence Diagram 5: Analyze Data
	 * @param alertProfileLogID: The id of the AlertProfileLog associated with the requested alertProfile
	 * @return: an object of type AlertProfile
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
					
					alertProfileLog = new AlertProfileLog(alertProfileLogID, rs.getInt("USERID"), rs.getString("DESCRIPTION"), rs.getDate("DATE"));
		
					int[] thresholds = new int[5];			
					rs.beforeFirst();
					int alertProfileID = 0;
					while(rs.next()){
						alertProfileID = rs.getInt("ALERTPROFILEID");
						String color = rs.getString("COLOR");
						int th = rs.getInt("THRESHOLD");
						if(color.equalsIgnoreCase("Red")){
							thresholds[0] = th; 
						} else if (color.equalsIgnoreCase("Orange")){
							thresholds[1] = th;
						} else if (color.equalsIgnoreCase("Yellow")){
							thresholds[2] = th;
						} else if (color.equalsIgnoreCase("Blue")){
							thresholds[3] = th;
						} else if (color.equalsIgnoreCase("Green")){
							thresholds[4] = th;
						}
					}
					alertProfile = new AlertProfile(alertProfileID, thresholds, alertProfileLog.getDescription(), alertProfileLog);
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
				pstmt.setInt(1, g_user.getID());
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
				return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 5.6 Sequence Diagram 6: Add User
	 * 
	 * @param username
	 * @param password
	 * @return true or false
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
				pstmt.setString(1, user.getFullName());
				pstmt.setString(2, user.getUsername());
				pstmt.setString(3, user.getPassword());

				// By YOUNG on 30, April
				//
				//pstmt.setInt(4, user.getUserProfileID());
				//
				pstmt.setInt(4, user.getUserProfile().getID());
				result = pstmt.executeUpdate();
				pstmt.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		if(result == 0)	
			return false;
		else
			return true;
	}
	
	/**
	 * To remove a user from the database
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
		if(user.getID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getID()));
			first = false;
		} 
		
		if(user.getFullName() != null){
			if(first)	query.append("FULLNAME = \'");
			else		query.append(" AND FULLNAME = \'");
			query.append(user.getFullName());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append(" AND USERNAME = \'");
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
	 * This method stores a UserProfile object in the database
	 * Reference: [Design Document v6.0]: 5.10 Sequence Diagram 10: Create Profile
	 * @param userProfile: a userProfile object to be saved in the database
	 * @return: true: if the userprofile stores successfully, and false: otherwise
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
		
		if(result == 0)	
			return false;
		else			
			return true;
	}
	
	/**
	 * Stores an AlertProfile object in the database
	 * 
	 * @param alertProfile
	 * @return
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
				pstmt.setInt(1, g_user.getID());
				
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
	 * Sequence Diagram 8: Update User <br>
	 * 
	 * Changes the password of the given user.<br> 
	 * Only the administrator has the power to change the passwords of the users<br>
	 * the users do not have the power to change their own passwords.
	 * 
	 * @param user - which user is password that is being changed
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
		if(user.getID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getID()));
			first = false;
		} 

		if(user.getFullName() != null){
			if(first)	query.append("FULLNAME = \'");
			else		query.append(" AND FULLNAME = \'");
			query.append(user.getFullName());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append(" AND USERNAME = \'");
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
	
	
	//
	// By YOUNG on 30, April
	// Roll back
	//
	/**
	 * Sequence Diagram 8: Update User <br>
	 * Changes the profile of the given user.
	 * 
	 * @param user - which user¢®?s profile that is being changed
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
		query.append(Integer.toString(userProfile.getID()));
		query.append(" WHERE ");
		if(user.getID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getID()));
			first = false;
		} 
		
		if(user.getFullName() != null){
			if(first)	query.append("FULLNAME = \'");
			else		query.append(" AND FULLNAME = \'");
			query.append(user.getFullName());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append(" AND USERNAME = \'");
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
						DataLog dataLog = new DataLog(rs.getInt("DATALOGID"), rs.getInt("USERID"), rs.getString("FILENAME"), rs.getDate("DATE"));
						data[count++] = new Data(rs.getInt("DATAID"),rs.getString("V_NAME"),
								rs.getString("PHONE"), dataLog );
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
		if(data.getName() != null){
			query.append("V_NAME = \'");
			query.append(data.getName());
			query.append("\'");
			first = false;
		} 
		if(data.getPhone() != null){
			if(first)	query.append("PHONE = \'");
			else		query.append(" AND PHONE = \'");
			query.append(data.getPhone());
			query.append("\'");
		} 
		if(data.getDataLog().getID() != 0){
			if(first)	
				query.append("DATALOGID = \'");
			else		
				query.append(" AND DATALOGID = \'");
			query.append(Integer.toString(data.getDataLog().getID()));
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
						DataLog dataLog = new DataLog(rs.getInt("DATALOGID"), rs.getInt("USERID"), rs.getString("FILENAME"), rs.getDate("DATE"));
						_data[count++] = new Data(rs.getInt("DATAID"),rs.getString("V_NAME"),
								rs.getString("PHONE"), dataLog );

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
						DataLog dataLog = new DataLog(rs.getInt("DATALOGID"), rs.getInt("USERID"), rs.getString("FILENAME"), rs.getDate("DATE"));
						data[count++] = new Data(rs.getInt("DATAID"),rs.getString("V_NAME"),
								rs.getString("PHONE"), dataLog );
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
		if(user.getID() != 0){
			query.append("USERID = ");
			query.append(Integer.toString(user.getID()));
			first = false;
		} 

		if(user.getFullName() != null){
			if(first)	
				query.append("FULLNAME = \'");
			else		
				query.append(" AND FULLNAME = \'");
			query.append(user.getFullName());
			query.append("\'");
			first = false;
		} 
		if(user.getUsername() != null){
			if(first)	query.append("USERNAME = \'");
			else		query.append(" AND USERNAME = \'");
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
						DataLog dataLog = new DataLog(rs.getInt("DATALOGID"), rs.getInt("USERID"), rs.getString("FILENAME"), rs.getDate("DATE"));
						data[count++] = new Data(rs.getInt("DATAID"),rs.getString("V_NAME"),
								rs.getString("PHONE"), dataLog );
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
						DataLog dataLog = new DataLog(rs.getInt("DATALOGID"), rs.getInt("USERID"), rs.getString("FILENAME"), rs.getDate("DATE"));
						data[count++] = new Data(rs.getInt("DATAID"),rs.getString("V_NAME"),
								rs.getString("PHONE"), dataLog );
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
	 * Returns User list
	 * @return An array of the User
	 */
	public User[] getUser(){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		User[] user = null;
		
		String query = "SELECT * FROM USER JOIN USERPROFILE ON USER.USERPROFILEID = USERPROFILE.USERPROFILEID";
		
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
					
					user = new User[size];
					int count = 0;
					while(rs.next()){
						
						boolean[] permissions = new boolean[] {rs.getBoolean("VIEWS"),
								rs.getBoolean("UPLOAD"), rs.getBoolean("ANALYZES"), rs.getBoolean("CONFIGURE")} ;
						
						UserProfile userProfile = new UserProfile(rs.getInt("USERPROFILEID"),rs.getString("PROFILENAME"),permissions );
						
						user[count++] = new User(rs.getInt("USERID"), rs.getString("FULLNAME"), rs.getString("USERNAME"),
								rs.getString("PASSWORDS"), userProfile);
					}
				}
				pstmt.close();
				rs.close();
				connection.close();				
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}
		
		return user;
	}
	
	/**
	 * Returns all the user profiles.
	 * @return An array of the User Profile
	 */
	public UserProfile[] getUserProfile(){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		UserProfile[] userProfile = null;
		String query = "SELECT * FROM USERPROFILE";

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

					userProfile = new UserProfile[size];
					int count = 0;
					while(rs.next()){
						boolean[] permissions = new boolean[] {rs.getBoolean("VIEWS"), rs.getBoolean("UPLOAD"), rs.getBoolean("ANALYZES"), rs.getBoolean("CONFIGURE")} ;
						userProfile[count++] = new UserProfile(rs.getInt("USERPROFILEID"), rs.getString("PROFILENAME"), permissions);
					}
				}
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}

		return userProfile;
	}
	
	/**
	 * Returns all the user profiles.
	 * @return An array of the User Profile
	 */
	public AlertProfileLog[] getAlertProfileLog(){
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		AlertProfileLog[] alertProfileLog = null;
		String query = "SELECT * FROM ALERTPROFILELOG";

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

					alertProfileLog = new AlertProfileLog[size];
					int count = 0;
					while(rs.next()){
						alertProfileLog[count++] = new AlertProfileLog(rs.getInt("ALERTPROFILELOGID"), rs.getInt("USERID"), rs.getString("DESCRIPTION"), rs.getDate("DATE"));
					}
				}
				pstmt.close();
				rs.close();
				connection.close();
			}catch(SQLException e){
				System.out.println(e.toString());
			}
		}

		return alertProfileLog;
	}
//-------------------------------- Utility Functions -------------------------------
	/**
	 * A private function that establishes a connection to the database
	 * @return: an object of type Connection
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
	 * The getVictimNames method is a private utility function that queries the database for a phone number
	 * within a specific data set, through the dataLogID
	 * @param _SPN: a string representing a suspect phone number
	 * @param dataLogID: the id of a DataLog object.
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
	 * 
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
}