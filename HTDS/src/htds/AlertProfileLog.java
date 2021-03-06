package htds;

import java.sql.Date;
/**
 * The AlertProfileLog class records historical data about AlertProfile
 * Each AlertProfile object has an AlertProfileLog object as a data member
 * Whenever an (administrator) user creates an AlertProfile in the database, the AlertProfileLog class records the following properties:
 * 1- who created the alertProfile (the userID)
 * 2- when was the alertProfile created (date)
 * Each AlertProfileLog object has a unique ID and optional description
 * Once an AlertProfileLog has been created, it can not be edited
 * @author Qutaiba
 */
public class AlertProfileLog {
	private int id;
	private int userID;
	private String description;
	private Date date;
	
	/**
	 * Constructor 1
	 * @param alertProfileLogID: a unique integer assigned to each AlertProfileLog object
	 * @param userID: the id of the user who created the AlertProfile
	 * @param alertDescription: A string that briefly describes the AlertProfile object - can be left blank
	 * @param date: The date when the AlertProfile was created (stored at the database)
	 */
	public AlertProfileLog(int alertProfileLogID, int userID, String alertDescription, Date date){
		setID(alertProfileLogID);
		setUserID(userID);
		description = alertDescription;
		setDate(date);
	}
	
	/**
	 * Constructor 2
	 * This constructor creates an object of type AlertProfileLog with same properties as the passed AlertProfileLog object
	 * @param log: an object of type AlertProfileLog
	 */
	public AlertProfileLog(AlertProfileLog log){
		setID(log.getID());
		setUserID(log.getUserID());
		description = log.getDescription();
		setDate(log.getDate());
	}
	
	/**
	 * Constructor 3
	 * @param alertProfileLogID: a unique integer assigned to each AlertProfileLog object
	 * @param user: an object of type User
	 * @param alertDescription: A string that briefly describes the AlertProfile object - can be left blank
	 * @param date: The date when the AlertProfile was created (stored at the database)
	 */
	
	public AlertProfileLog(int alertProfileLogID, User user, String alertDescription, Date date){
		setID(alertProfileLogID);
		setUserID(user.getID());
		description = alertDescription;
		setDate(date);
	}
	// a private function to to check the validity of an id
	//An id should be an integer of value >=0
	private boolean isValidID(int id){
		if(id >=0)
			return true;
		return false;
	}
	
	//A private function that sets the AlertProfileLog ID to a given integer
	//should be an integer >=0
	private void setID(int alertProfileLogID){
		if(isValidID(alertProfileLogID))
			this.id = alertProfileLogID;
		else{
			System.out.println("ERROR:AlertProfileLog: setAlertProfileLogID: ID should be >=0");
			this.id = -1;
		}
	}
	
	//A private function that sets the User ID to a given integer
	//should be an integer >=0
	private void setUserID(int userID){
		if(isValidID(userID))
			this.userID = userID;
		else{
			System.out.println("ERROR:AlertProfileLog: setUserID: ID should be >=0");
			this.userID = -1;

		}
	}
		
	/**
	 * a private function to set the AlertLog date to a given date. The date should not equal to null
	 * @param date
	 */
	private void setDate(Date date){
		if(date == null){
			System.out.println("ERROR:AlertProfileLog: setDate: The given Date is null");
			this.date = null;
		}
		else
			this.date = date;
	}
	/**
	 * 	
	 * @return
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getUserID(){
		return userID;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getDate(){
		return date;
	}
	
	/**
	 * 
	 */
	public void print(){
		System.out.println("Printing AlertProfileLog Details:");
		System.out.println("AlertProfileLog name = " + description+ " , ID =  "+id+
				" , Created at: " +date.toString()+ " by user id "+userID);
	}
	
}
