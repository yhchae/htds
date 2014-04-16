package htds;

import java.sql.Date;

/**
 * 
 * @author Younghun
 *
 */
public class DataLog {
	private int dataLogID;
	private int userID;
	private String fileName;
	private Date date;
	
	/**
	 * 
	 */
	public DataLog(){
		this.dataLogID = 0;
		this.userID = 0;
		this.fileName = null;
		this.date = null;
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
	 * @param userID
	 */
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName){
		this.fileName = fileName;
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
	public int getDataLogID(){
		return this.dataLogID;
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
	public String getFileName(){
		return this.fileName;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getDate(){
		return this.date;
	}
}
