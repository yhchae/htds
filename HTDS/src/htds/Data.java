package htds;
/**
 * The Data class is a basic data structure used to store [name-phone] records in the database
 * Files containing phone lists are converted into Data objects, each pertaining to a specific [name-phone]
 * Each Data object maintains a reference to a DataLog objects which records logging information about the Data object 
 * Once a Data object is created, it cannot be modified
 * @author Qutaiba
 * 
 */
public class Data {
	private int id; /**a unique integer id that identifies this Data object*/
	private String name; /**A string representing a potential victim name associated with a phone number*/
	private String phone; /**A string representing a phone number */
	private int dataLogID; /**an id referencing the DataLog object associated with this Data object*/
	private int frequency; /**this will be used only in one specific method: getAnalisys function*/
	
	/**
	 * Constructor : Initialize the private values.
	 */
	public Data(){
		setID(0);
		this.name = null;
		this.phone = null;
		this.dataLogID = 0;
		this.frequency = 0;
	}
	
	/**
	 * This constructor creates an object of type Data with properties set to values equal to the passed parameters
	 * @param dataID: a unique integer id that identifies this Data object
	 * @param name: A string representing a potential victim name associated with a phone number
	 * @param phone: phone number String
	 * @param dataLogID: The id of the DataLog associated with this Data object
	 */
	public Data(int dataID, String name, String phone, int dataLogID){
		setID(dataID);
		setName(name);
		setPhone(phone);
		setDataLogID(dataLogID);
		this.frequency = 0;
	}
	/**
	 * Constructor
	 * @param data: an object of type Data
	 */
	public Data(Data data){
		setID(data.getID());
		setName(data.getName());
		setPhone(data.getPhone());
		setDataLogID(data.getDataLogID());
		this.frequency = 0;
	}
	
	/**
	 * Set the DataID to a given integer
	 * @param dataID: an non-negative integer
	 */
	private void setID(int dataID){
		if(dataID >=0)
			this.id = dataID;
		else{
			this.id = -1;
			System.out.println("ERROR:Data:setID: invalid ID. Should be >= 0");
		}
	}
	
	/**
	 * 
	 * @param name: set the name to the given string
	 */
	private void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 * @param phone: set the phone number to the given string
	 */
	public void setPhone(String phone){
		this.phone = formatPhone(phone);
	}
	
	/**
	 * removes all formatting from a phone number
	 * @param phone: a String representing a non-formatted phone number
	 * @return: a string of digits
	 */
	private String formatPhone(String phone){
		return phone.replaceAll("([^0-9])", "");
	}
	
	/**
	 * A private function that sets the DataLogID to a given integer
	 * The given parameter has to be a non-negative integer
	 * @param dataLogID: set the DataLog Id to the given number
	 */
	private void setDataLogID(int dataLogID){
		if(dataLogID >=0)
			this.dataLogID = dataLogID;
		else{
			this.dataLogID = -1;
			System.out.println("ERROR:Data:setDataLogID: invalid ID. Should be >= 0");
		}
	}
	
	/**
	 * This utility function is used by the Analyzer
	 * It sets a Data object frequency to the given argument
	 * @param frequency: number of times a number is repeated in a dataset
	 */
	public void setFrequency(int frequency){
		this.frequency = frequency;
	}
	
	/**
	 * Return the ID of the Data object
	 * @return the ID of this Data object
	 */
	public int getID(){
		return this.id;
	}
	
	/**
	 * Return the Name associated with a  Data object
	 * @return the name String
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Return the phone number associated with a Data object
	 * @return the phone number (String)
	 */
	public String getPhone(){
		return this.phone;
	}
	
	/**
	 * Return the id of the DataLog object used to store the logging information of this Data object
	 * @return the id of the DataLog associated with this Data object
	 */
	public int getDataLogID(){
		return this.dataLogID;
	}
	/**
	 * prints the details of the Data object
	 */
	public void print(){
		System.out.println("Printing Data Details:");
		System.out.println("ID: "+ getID()+ " Name: " + getName()+ " Phone: "+ getPhone()+
				", DataLog ID: "+ getDataLogID());
		System.out.println();
	}
}
