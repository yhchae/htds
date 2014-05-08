package htds;

import java.sql.Date;

/**
 * The DataLog class is a data structure that stores logging information about Data objects read from a phone list (a file)
 * A single DataLog object is created for every file read by the Uploader
 * This DataLog object will be referenced by every Data object created in the upload activity through the DataLog id
 * The DataLog class records the userID who uploaded the file, the file name, and the date of the activity
 * Once a DataLog is created, it cannot be modified 
 * @author Qutaiba
 *
 */

public class DataLog {
	private int id;
	private int userID;
	private String fileName;
	private Date date;
	
	/**
	 * This constructor creates an object of type DataLog with properties set to values equal to those of the passed arguments
	 * @param logID: a unique integer that identifies this DataLog object
	 * @param userID: the user ID that created this DataLog object
	 * @param filename: A string representing the name of the file used to upload the Data entries
	 * @param date: the Date this DataLog was created
	 */
	public DataLog(int logID, int userID, String filename, Date date){
		setID(logID);
		setUserID(userID);
		setFileName(filename);
		setDate(date);
	}
	/**
	 * This constructor is similar to the basic constructor, except that an object of type User is passed instead of the userID
	 * @param logID: a unique integer that identifies this DataLog object
	 * @param user: an object of type User. This will be used to extract the User id
	 * @param fileName: A string representing the name of the file used to upload the Data entries
	 * @param date: the Date this DataLog was created
	 */
	public DataLog(int logID, User user, String fileName, Date date){
		setID(logID);
		setUserID(user.getID());
		setFileName(fileName);
		setDate(date);
	}
	
	/**
	 * Constructor
	 * @param log: an object of Type DataLog
	 */
	public DataLog(DataLog log){
		setID(log.getID());
		setUserID(log.getUserID());
		setFileName(log.getFileName());
		setDate(log.getDate());
	}
	/**
	 * 
	 * @param logID
	 */
	private void setID(int logID){
		if(logID >=0)
			this.id = logID;
		else{
			this.id = -1;
			System.out.println("ERROR:DataLog:setID: invalid ID. Should be >= 0");
		}
	}
	
	/**
	 * 
	 * @param userID
	 */
	private void setUserID(int userID){
		if(userID >=0)
			this.userID = userID;
		else{
			this.userID = -1;
			System.out.println("ERROR:DataLog:setUserID: invalid ID. Should be >= 0");
		}
	}
	
	/**
	 * set the filename of a DataLog object to a given STring
	 * @param fileName: A string representing a filename
	 */
	private void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	/**
	 * a private function to set the DataLog date to a given date. The date should not equal to null
	 * @param date: an object of type Date
	 */
	private void setDate(Date date){
		if(date == null){
			System.out.println("ERROR:DataLog: setDate: The given Date is null");
			this.date = null;
		}
		else
			this.date = date;
	}
	
	/**
	 * 
	 * @return: ID of the dataLog
	 */
	public int getID(){
		return this.id;
	}
	
	/**
	 * 
	 * @return: the User id who created this DataLog object
	 */
	public int getUserID(){
		return this.userID;
	}
	
	/**
	 * 
	 * @return: filename uploaded to HTDS associated with this log
	 */
	public String getFileName(){
		return this.fileName;
	}
	
	/**
	 * 
	 * @return: the date of creating this log
	 */
	public Date getDate(){
		return this.date;
	}
	
	/**
	 * Prints the details of DataLog
	 */
	public void print(){
		System.out.println("DataLog: Print - not implemented yet");
	}
}
