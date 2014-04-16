package htds;

import java.sql.Date;

/**
 * 
 * @author Younghun
 *
 */
public class AlertLog {
	private int alertLogID;
	private int userID;
	private int dataLogID;
	private int alertProfileLogID;
	private Date date;
	
	/**
	 * 
	 */
	public AlertLog(){
		this.alertLogID = 0;
		this.userID = 0;
		this.dataLogID =  0;
		this.alertProfileLogID = 0;
		this.date = null;
	}
	
	/**
	 * 
	 * @param alertLogID
	 */
	public void setAlertLogID(int alertLogID){
		this.alertLogID = alertLogID;
	}
	
	/**
	 * 
	 * @param userID
	 */
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	/**
	 * 
	 * @param dataLogID
	 */
	public void setDataLogID(int dataLogID){
		this.dataLogID = dataLogID;
	}
	
	/**
	 * 
	 * @param alertProfileLogID
	 */
	public void setAlertProfileLogID(int alertProfileLogID){
		this.alertProfileLogID = alertProfileLogID;
	}
	
	/**
	 * 
	 * @param date
	 */
	public void setDate(Date date){
		this.date = date;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAlertLogID(){
		return this.alertLogID;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getUserID(){
		return this.userID;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getDataLogID(){
		return this.dataLogID;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAlertProfileLogID(){
		return this.alertProfileLogID;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getDate(){
		return this.date;
	}
}
