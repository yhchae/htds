package htds;

/**
 * The AlertProfile class controls how Alert objects are created. 
 * The AlertProfile defines 5-scale color system that represent the severity of an activity
 * Each color is bounded by a threshold value
 * Each AlertProfile is distinguished by a unique ID number and an optional string descriptor
 * Whenever an AlertProfile object is created, the logging information is stored at the AlertProfileLog which is a data member at the AlertProfile Class
 * Whenever an Alert is created the ID of the AlerProfile object used to create that Alert object is recorded at the AlertLog object contained at the Alert object
 * Once an AlertProfile object is created it cannot be modified
 * @author Qutaiba
 */
public class AlertProfile {
	/*Data Members*/
	private int id; // a unique integer id assigned to each AlertProfile object
	private AlertProfileLog alertProfileLog; //an AlertProfileLog to keep track of when and how the alert profile was created
	private String description; //an optional string description
	private final static String[] levels = 
			new String[]{"Level 1: Severe Risk",
		                 "Level 2: High Risk", 
		                 "Level 3: Significant Risk", 
		                 "Level 4: Ggeneral Risk",
						 "Level 5: Low Risk"};
	private final static String[] colors = 
			new String[]{	"Red", 
							"Orange", 
							"Yellow", 
							"Blue", 
							"Green"};
	private int[] thresholds;
	//------------------------- Constructors -----------------------------
	/**
	 * Constructor 1
	 * @param id: a unique integer id
	 * @param thresholds: an array of five positive integers
	 * @param description: a string descriptor, can be set to ""
	 * @param log: an AlertProfileLog to record logging information
	 */
	public AlertProfile(int id, int[] thresholds, String description, AlertProfileLog log){
		setID(id);
		setThresholds(thresholds);
		setAlertProfileLog(log);
		this.description = description;
	}
	
	public AlertProfile(int id, int[] thresholds, String description){
		setID(id);
		setThresholds(thresholds);
		this.description = description;
	}
	/**
	 * This constructor creates an object of type AlertProfile with same properties as the passed AlertProfile object
	 * @param alertProfile: an object of type AlertProfile
	 */
	public AlertProfile(AlertProfile alertProfile){
		setID(alertProfile.getID());
		setThresholds(alertProfile.getThresholds());
		setAlertProfileLog(alertProfile.getAlertProfileLog());
		this.description = alertProfile.getDescription();
	}
	//------------------------- Private Setters -----------------------------

	// a private function to to check the validity of an id
	//An id should be an integer of value >=0
	private boolean isValidID(int id){
		if(id >=0)
			return true;
		return false;
	}
	
	//A private function that sets the AlertProfile ID to a given integer
	//should be an integer >=0
	private void setID(int alertProfileID){
		if(isValidID(alertProfileID))
			this.id = alertProfileID;
		else{
			System.out.println("ERROR:AlertProfile: setAlertProfileID: ID should be >=0");
			this.id = -1;
		}
	}
	
	//A private function that sets the AlertProfile thresholds to a given integer array
	//The array size should contain exactly five elements, each with value > 0
	private void setThresholds(int[] thresholds){
		if(thresholds.length != 5){
			System.out.println("ERROR:AlertProfile: setThresholds: There should be exactly five elements");
			this.thresholds = null;
			return;
		}
		else{
			for(int i=0;i<5;i++){
				if(thresholds[i]<=0){
					System.out.println("ERROR:AlertProfile: setThresholds: All values should be greater than zero");
					this.thresholds = null;
					return;
				}
			this.thresholds = thresholds;
			}
		}
	}
	//A private function that sets the AlertProfileLog data member to the given AlertProfileLog object
	//The parameter should not be equal to null
	private void setAlertProfileLog(AlertProfileLog log){
		if(log == null){
			System.out.println("ERROR:AlertProfile: setAlertProfileLog: The given AlertProfileLog is null");
			alertProfileLog = null;
		}
		else
			this.alertProfileLog = new AlertProfileLog(log);
	}
	
	//------------------------- Getters -----------------------------
	/**
	 * @return alertProfile ID
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * @return the standard 5-color scale
	 */
	public static String[] getColors(){
		return colors;
	}
	/**
	 * @return the severity levels of the standard 5-color scale
	 */
	public static String[] getLevels(){
		return levels;
	}
	/**
	 * @return the integer array containing the thresholds used to define the 5-color scale
	 */
	public int[] getThresholds(){
		return thresholds;
	}
	/**
	 * @return the string descriptor of this object
	 */
	public String getDescription(){
		return description;
	}
	/**
	 * @return the AlertProfileLog object contained within the AlertProfile object
	 */
	public AlertProfileLog getAlertProfileLog(){
		return alertProfileLog;
	}
	
	/**
	 * Prints the details of an AlertProfile
	 */
	public void print(){
		System.out.println("Printing AlertProfile Details:");
		System.out.println("AlertProfile name = "+ description+ " , ID= : "+ getID());
		for(int i=0;i<5;i++)
			System.out.println(levels[i]+" , Color: "+ colors[i]+ " , Threshold= "+thresholds[i]);
		alertProfileLog.print();
	}
}

//These comments are to be reflected on the design document
//1- change "level" to "levels"
//2- change "color" to "colors"
//3- change "getLevel()" to "getLevels()"
//4- change "getColor()" to "getColors()"
//5- levels, colors and thresholds are arrays
//6- levels and colors are static
//7- add description data member