package htds;


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
	
	private int id; /** unique id assigned to each alert */
	private String color; /** color of alert denoting the severity of the alert */
	private int frequency; /**number of times a suspect phone appears in a data set - should match the number of victimNames*/
	private String SPN;  /**suspect phone number - String of digits with no formatting*/
	private AlertLog alertLog; /**an AlertLog to keep track of when and how the alert was created*/
	private String[] victimNames; /**names of potential victims sharing the same phone number*/
	//---------------------------
	/**
	 * Constructor
	 * @param id: unique id assigned to each alert
	 * @param alertColor: color of alert denoting the severity of the alert
	 * @param phone: suspect phone number - string of digits with no formatting
	 * @param log: an AlertLog to keep track of when and how an alert was generated
	 * @param names: names of victims sharing the same phone number
	 */

	public Alert(int id, String alertColor, String phone, AlertLog log, String[] names){
		setID(id);
		setColor(alertColor);
		setSPN(phone);
		setAlertLog(log);
		setVictimNames(names); 
		frequency = names.length;
	}
	/**
	 * This constructor creates an Alert with ID = 0 and a null AlertLog
	 * All other properties are set according to the passed arguments. 
	 * This is to be used by the Analyzer class
	 * @param alertColor: color of alert denoting the severity of the alert
	 * @param phone: suspect phone number - string of digits with no formatting
	 * @param names: names of victims sharing the same phone number
	 */
	public Alert(String alertColor, String phone, String[] names){
		setID(0);
		setColor(alertColor);
		setSPN(phone);
		alertLog = null;
		setVictimNames(names); 
		frequency = names.length;
	}
	//------------------------------
	/**
	 * This Constructor creates an object of type Alert with properties similar to the passed Alert object
	 * @param alert: an object of type Alert
	 */
	public Alert(Alert alert){
		setID(alert.getID());
		setColor(alert.getColor());
		setSPN(alert.getSPN());
		setAlertLog(alert.getAlertLog());
		setVictimNames(alert.getVictimNames());
	}
	//--------------------------------
	/**
	 *  a private class that sets the ID of an Alert Object
	 * @param alertID: a unique id assigned to each alert. 
	 * Note: It is the user's responsibility to ensure that each Alert has a unique id. This could be done through the prime key in database 
	 */
	private void setID(int alertID){
		if(alertID >=0)
			this.id = alertID;
		else{
			this.id = -1;
			System.out.println("ERROR:Alert:setID: invalid ID. Should be >= 0");
		}
	}
	//--------------------------------
	/**
	 * private class that sets the color of an Alert Object
	 * @param alertColor: should be one of the colors defined in the AlertProfile
	 */
	private void setColor(String alertColor){
		if(isValidColor(alertColor))
			color = alertColor;
		else{
			color = "";
			System.out.println("ERROR:Alert:setColor: invalid color");
		}
	}
	//--------------------------------
	/**
	 * Private function to check validity of a color assigned to an Alert Object
	 * @param alertColor: given String representing a color
	 * @return: true if one of the five colors defined in the AlertProfile, false: otherwise,
	 */
	private boolean isValidColor(String alertColor){
		String[] colors = AlertProfile.getColors();
		for (int i=0;i<colors.length;i++)
			if(colors[i].equals(alertColor))
				return true;
		return false;
	}
	//--------------------------------
	/**
	 * @param phone
	 * Note: String of digits with no formatting
	 * All formatting will be removed
	 */
	private void setSPN(String phone){
		SPN = formatPhone(phone);
	}
	/**
	 * removes all formatting from a phone number
	 * @param phone: a String representing a non-formatted phone number
	 * @return: a string of digits
	 */
	private String formatPhone(String phone){
		return phone.replaceAll("([^0-9])", "");
	}
	//--------------------------------
	/**
	 * A private function that sets the AlertLog to the same properties of a given AlertLog object
	 * @param log: an object of type AlertLog
	 */
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
	 * A private method to set the array of victim names associated with this Alert object
	 * Note: setting the victimNames will automatically set the frequency of an Alert to match the number or provided names
	 * @param victimNames: an array of strings
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
	//------------------------- Public Getters  -----------------------------
	/**
	 * @return the Alert ID
	 */
	public int getID(){
		return this.id;
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
		return alertLog.getID();
	}
	
	/**
	 * print the properties of an Alert object
	 */
	public void  print(){
		System.out.println("Printing Alert Details:");
		System.out.println("AlertID: "+ getID()+ " AlertColor: " + getColor());
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

