package htds;

import java.sql.Date;

/**
 * The AlertLog class is a data member in the Alert class to keep logging information about an Alert object
 * It records information about 
 * 1- Who created the alert (i.e. the user who performed the analysis)
 * 2- when (Date/Time) the alert was created (i.e. populated to the database)
 * 3- Which data tuples in the database were analyzed to generate the Alert
 * 4- The AlertProfile used to generate the alert
 * Once an AlertLog object is created, it can not be modified
 * @author Qutaiba
 *
 */
public class AlertLog {
	private int id; /**unique id assigned to each AlertLog*/
	private int userID; /**the id of the user who generated an alert object*/
	private int dataLogID; /**the id of the data set (a file) which were analyzed to generate an Alert object*/
	private int alertProfileID; /**the id of an alertProfile used to set the parameters of an alert object*/
	private Date date; /**the date and time when the alert object was recorded in the database*/
	
	//------------------------- Constructors -----------------------------
	/**
	 * Constructor 1 (Basic)
	 * @param id: unique integer assigned to each AlertLog
	 * @param userID: the unique id of the user who generated the alert object
	 * @param dataLogID: the unique integer id for the DataLog of the data set (a file) which were analyzed to generate the alert object
	 * @param date: an object of type Date that records the date when the Alert was generated
	 * @param alertProfileID: the unique integer id of the alertProfile used to set the parameters of the alert object
	 */

	public AlertLog(int id, int userID, int dataLogID, Date date, int alertProfileID){
		setID(id);
		setUserID(userID);
		setDataLogID(dataLogID);
		setAlertProfileID(alertProfileID);
		setDate(date);
	}
	
	/**
	 * This constructor is similar to the basic constructor except that an object of type AlertProfile is passed 
	 * instead of an integer referring to the AlertProfile id
	 * @param id: unique integer assigned to this AlertLog object
	 * @param userID: the unique id of the user who generated the alert object
	 * @param dataLogID: the unique integer id for the DataLog of the data set (a file) which were analyzed to generate the alert object
	 * @param date: an object of type Date that records the date when the Alert was generated
	 * @param alertProfile: an object of type AlertProfile
	 */
	public AlertLog(int id, int userID, int dataLogID, Date date, AlertProfile alertProfile){
		setID(id);
		setUserID(userID);
		setDataLogID(dataLogID);
		setAlertProfileID(alertProfile.getID());
		setDate(date);
			
	}
	/**
	 * This constructor creates an object of type AlertLog with same properties as the passed AlertLog object
	 * @param alertLog: an Object of type AlertLog
	 */
	public AlertLog(AlertLog alertLog){
		setID(alertLog.getID());
		setUserID(alertLog.getUserID());
		setDataLogID(alertLog.getDataLogID());
		setAlertProfileID(alertLog.getAlertProfileID());
		setDate(alertLog.getDate());
	}
	/**
	 * Constructor 4
	 * This constructor is similar to the basic constructor except that an object of type AlertProfile
	 * is passed instead of an integer referring to the AlertProfile id
	 * @param id: unique integer assigned to each AlertLog
	 * @param user: an Object of type User
	 * @param dataLog: an Object of type DataLog
	 * @param date: an object of type Date that records the date when the Alert was generated
	 * @param alertProfile: an object of type AlertProfile
	 */
	public AlertLog(int id, User user, DataLog dataLog, Date date, AlertProfile alertProfile){
		setID(id);
		setUserID(user.getID());
		setDataLogID(dataLog.getID());
		setAlertProfileID(alertProfile.getID());
		setDate(date);
	}
   //------------------------- Private Setters -----------------------------
	
	// a private function to to check the validity of an id
	//An id should be an integer of value >=0
	private boolean isValidID(int id){
		if(id >=0)
			return true;
		return false;
	}
	
	//A private function that sets the AlertLog ID to a given integer
	//should be an integer >=0
	private void setID(int alertLogID){
		if(isValidID(alertLogID))
			this.id = alertLogID;
		else{
			System.out.println("ERROR:AlertLog: setAlertLogID: ID should be >=0");
			this.id = -1;
		}
	}
	
	//A private function that sets the User ID to a given integer
	//should be an integer >=0
	private void setUserID(int userID){
		if(isValidID(userID))
			this.userID = userID;
		else{
			System.out.println("ERROR:AlertLog: setUserID: ID should be >=0");
			this.userID = -1;

		}
	}

	// A private function that sets the DataLogID to a given integer
	// should be an integer >=0
	private void setDataLogID(int dataLogID){
		if(isValidID(dataLogID))
			this.dataLogID = dataLogID;
		else{
			System.out.println("ERROR:AlertLog: setDataLogID: ID should be >=0.");
			this.dataLogID = -1;
		}
	}
	
	// A private function that sets the AlertProfileLogID to a given integer
	// should be an integer >=0
	private void setAlertProfileID(int alertProfileID){
		if(isValidID(alertProfileID))
			this.alertProfileID = alertProfileID;
		else{
			System.out.println("ERROR:AlertLog: setAlertProfileID: ID should be >=0.");
			this.alertProfileID = -1;
		}
	}
	
	//a private function to set the AlertLog date to a given date
	//The date should not equal to null
	private void setDate(Date date){
		if(date == null){
			System.out.println("ERROR:AlertLog: setDate: The given Date is null");
			this.date = null;
		}
		else
			this.date = date;
	}
	
	//------------------------- Public Getters  -----------------------------
	/**
	 * @return the id of an AlertLog object
	 */
	public int getID(){
		return this.id;
	}
	
	/**
	 * @return the id of the user who created an Alert object
	 */
	public int getUserID(){
		return this.userID;
	}
	
	/**
	 * @return the id of the data set (DataLog) from which the Alert was generated
	 */
	public int getDataLogID(){
		return this.dataLogID;
	}
	
	/**
	 * @return the id of the AlertProfile used to create the Alert object
	 */
	public int getAlertProfileID(){
		return this.alertProfileID;
	}
	
	/**
	 * @return the date of when the Alert was generated
	 */
	public Date getDate(){
		return this.date;
	}
	
	//------------------------- Other Functions -----------------------------
	/**
	 * Prints the details of an AlertLog object
	 */
	public void print(){
		System.out.println("Printing AlertLog Details:");
		System.out.println("AlertLog ID: "+id+ " , Created at: "
				+date.toString()+ " by user id "+userID);
		System.out.println("DataLog id: "+dataLogID+ " , AlertProfile id: "+ alertProfileID);
	}
}
