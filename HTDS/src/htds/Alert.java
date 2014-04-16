package htds;

/**
 * 
 * @author Younghun
 *
 */
public class Alert {
	private int alertID;
	private String color;
	private int frequency;
	private String SPN;
	private AlertLog alertLog;
	private String[] victimNames;
	
	/**
	 * 
	 */
	public Alert(){
		this.alertID = 0;
		this.color = null;
		this.frequency = 0;
		this.SPN = null;
		this.alertLog = null;
		this.victimNames = null;
	}
	
	/**
	 * 
	 * @param alertID
	 */
	public void setAlertID(int alertID){
		this.alertID = alertID;
	}
	
	/**
	 * 
	 * @param color
	 */
	public void setColor(String color){
		this.color = color;
	}
	
	/**
	 * 
	 * @param frequency
	 */
	public void setFrequency(int frequency){
		this.frequency = frequency;
	}
	
	/**
	 * 
	 * @param SPN
	 */
	public void setSPN(String SPN){
		this.SPN = SPN;
	}
	
	/**
	 * 
	 * @param alertLog
	 */
	public void setAlertLog(AlertLog alertLog){
		this.alertLog = alertLog;
	}
	
	/**
	 * 
	 * @param victimNames
	 */
	public void setVictimName(String[] victimNames){
		this.victimNames = victimNames;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAlertID(){
		return this.alertID;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getColor(){
		return this.color;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFrequency(){
		return this.frequency;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSPN(){
		return this.SPN;
	}
	
	/**
	 * 
	 * @return
	 */
	public AlertLog getAlertLog(){
		return this.alertLog;
	}
	
	/**
	 * 
	 * @return
	 */
	public String[] getVictimNames(){
		return this.victimNames;
	}
}
