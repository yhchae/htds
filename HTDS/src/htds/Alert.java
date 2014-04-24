package htds;

import java.sql.Date;

/**
 * The Alert class is the basic data structure used to represent the results of analyzing a data set (phone number list)
 * The Alert represents a phone number attached to multiple potential victim names
 * Based on a specific AlertProfile, the number of times a phone number is repeated in a data set
 * is mapped to a color code representing the likelihood of being attached to a human trafficking activity
 * Note: once an Alert object is created, it can not be modified 
 * @author Qutaiba
 *
 */
public class Alert {
	private int alertID; //unique id assigned to each alert
	private String color; //color of alert denoting the severity of the alert
	private int frequency; //number of times a suspect phone appears in a data set - should match the number of victimNames
	private String SPN;  //suspect phone number - String of digits with no formatting
	private AlertLog alertLog; //an AlertLog to keep track of when and how the alert was created
	private String[] victimNames; //names of victims sharing the same phone number
	//---------------------------
	/**
	* @category: Constructor 1
	* @param: id: unique id assigned to each alert
	* @param: alertColor: color of alert denoting the severity of the alert
	* @param: frequency: number of times a suspect phone appears in a data set - matches the number of victimNames
	* @param: SPN: suspect phone number - string of digits with no formatting
	* @param: log: an AlertLog to keep track of when and how an alert was generated
	* @param: names: names of victims sharing the same phone number  
	*/
	public Alert(int id, String alertColor, int frequency, String phone, AlertLog log, String[] names){
		setAlertID(id);
		setColor(alertColor);
		setFrequency(frequency);
		setSPN(phone);
		setAlertLog(log);
		setVictimNames(names); 
		frequency = names.length;
	}
	//------------------------------
	/**
	 * @category: Constructor 2
	 * @param a: an object of type Alert
	 */
	public Alert(Alert alert){
		setAlertID(alert.getAlertID());
		setColor(alert.getColor());
		setSPN(alert.getSPN());
		setAlertLog(alert.getAlertLog());
		setVictimNames(alert.getVictimNames());
	}
	//--------------------------------
	 // a private class that sets the ID of an Alert Object
	 //@param alertID: a unique id assigned to each alert. 
	 //Note: It is the user's responsibility to ensure that each Alert has a unique id
	 //This could be done through the prime key in database 
	private void setAlertID(int alertID){
		if(alertID >=0)
			this.alertID = alertID;
		else{
			this.alertID = -1;
			System.out.println("ERROR:Alert:setAlertID: invalid ID. Should be >= 0");
		}
	}
	//--------------------------------
	
	//A private class that sets the color of an Alert Object
	private void setColor(String alertColor){
		if(isValidColor(alertColor))
			color = alertColor;
		else{
			color = "";
			System.out.println("ERROR:Alert:setColor: invalid color");
		}
	}
	//--------------------------------
	//Private function to check validity of a color assigned to an Alert Object
	private boolean isValidColor(String alertColor){
		String[] colors = AlertProfile.getColors();
		for (int i=0;i<colors.length;i++)
			if(colors[i].equals(alertColor))
				return true;
		return false;
	}
	//--------------------------------
	/**
	 * @param SPN
	 * Note: String of digits with no formatting
	 * All formatting will be removed
	 */
	private void setSPN(String phone){
		SPN = formatPhone(phone);
	}
	//removes all formatting from a phone number
	//returns a string of digits
	private String formatPhone(String phone){
		return phone.replaceAll("([^0-9])", "");
	}
	//--------------------------------
	//A private function that sets the AlertLog to the same properties of a given AlertLog object
	private void setAlertLog(AlertLog log){
		if(log == null){
			System.out.println("ERROR:Alert: setAlertLog: The given AleratLog is null");
			alertLog = null;
		}
		else
			this.alertLog = new AlertLog(log);
	}
	//--------------------------------
	/**
	 * 
	 * @param victimNames: an array of strings
	 * Note: setting the victimNames will automatically set the frequency of an Alert to match the number or provided names
	 */
	private void setVictimNames(String[] victimNames){
		if(victimNames == null){
			System.out.println("ERROR:Alert: setVictimNames: There should be at least one name");
			this.victimNames = new String[]{};
		}
		else{
			this.victimNames = victimNames;
			this.frequency = victimNames.length;
		}
	}
	/**
	 * 
	 * @param frequency
	 */
	private void setFrequency(int frequency){
		if(frequency < 0){
			System.out.println("ERROR:Alert: setAlertLog: The given AleratLog is null");
			this.frequency = -1;
		}
		else
			this.frequency = frequency;
	}
	//------------------------- Public Getters  -----------------------------
	/**
	 * @return the Alert ID
	 */
	public int getAlertID(){
		return this.alertID;
	}
	//--------------------------------
	/**
	 * @return color of the alert
	 */
	public String getColor(){
		return this.color;
	}
	//--------------------------------
	/**
	 * @return number of times a phone number appears in a data set
	 */
	public int getFrequency(){
		return this.frequency;
	}
	//--------------------------------
	/**
	 * @return the suspected phone number, which is shared by the list of victims
	 */
	public String getSPN(){
		return this.SPN;
	}
	//--------------------------------
	/**
	 * @return the AlertLog object contained in this Alert object
	 */
	public AlertLog getAlertLog(){
		return this.alertLog;
	}
	//--------------------------------
	/**
	 * @return the list of victim names which share the same phone number detected by the Alert object
	 */
	public String[] getVictimNames(){
		return this.victimNames;
	}
	//--------------------------------
		/**
		 * @return the id of the AlertLog associated with this Alert object
		 * This information will be extracted from the AlertLog data member
		 */
		public int getAlertLogID(){
			return alertLog.getAlertLogID();
		}
	//--------------------------------
	/**
	 * @return the id of the user that created this alert object
	 * This information will be extracted from the AlertLog data member
	 */
	public int getAlertUserID(){
		return alertLog.getUserID();
	}
	//--------------------------------
	/**
	 * @return the id of the data set (DataLog) from which this Alert was generated
	 * This information will be extracted from the AlertLog data member
	 */
	public int getAlertDataLogID(){
		return alertLog.getDataLogID();
	}
	//--------------------------------
	/**
	 * @return the date in which this alert object was generated (saved into the database)
	 * This information will be extracted from the AlertLog data member
	 */
	public Date getDate(){
		return alertLog.getDate();
	}
	//--------------------------------
	/**
	 * print the properties of an Alert object
	 */
	public void  print(){
		System.out.println("Printing Alert Details:");
		System.out.println("AlertID: "+ getAlertID()+ " AlertColor: " + getColor());
		System.out.println("The following phone number ("+ getSPN() + ") was repeated " 
				+getFrequency()+ " times");
		System.out.print("List of Names associated with this number: ");
		for(int i=0; i<getVictimNames().length;i++){
			System.out.print(getVictimNames()[i]);
			if(i!= getVictimNames().length -1)
				System.out.print(" , ");
		}
		System.out.println();
		alertLog.print();
	}
	//--------------------------------
}
//Notes:
//Delete setFrequency
//setVictimNames instead of setVictimName
//getters where added to facilitate extracting information from the AlertLog
