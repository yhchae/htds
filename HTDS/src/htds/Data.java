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
	private int id; // a unique integer id that identifies this Data object
	private String name; // A string representing a potential victim name associated with a phone number
	private String phone; // A string representing a phone number
	private DataLog dataLog; // an id referencing the DataLog object associated with this Data object
	private int frequency; //this will be used only specific case: getAnalisys function
	
	/**
	 * Constructor : Initialize the private values.
	 */
	public Data(){
		setID(0);
		this.name = null;
		this.phone = null;
		this.dataLog = null;
		this.frequency = 0;
	}
	
	/**
	 * This constructor creates an object of type Data with properties set to values equal to the passed parameters
	 * @param dataID: a unique integer id that identifies this Data object
	 * @param name: A string representing a potential victim name associated with a phone number
	 * @param phone: phone number String
	 * @param dataLogID: The id of the DataLog associated with this Data object
	 */
	public Data(int dataID, String name, String phone, DataLog dataLog){
		setID(dataID);
		setName(name);
		setPhone(phone);
		setDataLog(dataLog);
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
		setDataLog(data.getDataLog());
		this.frequency = 0;
	}
	
	public Data(int lineCounter, String string, String string2, Object object) {
		// TODO Auto-generated constructor stub
		id = lineCounter;
		name = string;
		phone = string2;
	}

	/**
	 * Set the DataID to a given integer
	 * @param dataID
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
	 * @param phone
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
	private void setDataLog(DataLog dataLog){
		this.dataLog = dataLog;
	}
	
	/**
	 * 
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
	public DataLog getDataLog(){
		return this.dataLog;
	}
	/**
	 * prints the details of the Data object
	 */
	public void print(){
		System.out.println("Printing Data Details:");
		System.out.println("ID: "+ getID()+ " Name: " + getName()+ " Phone: "+ getPhone()+
				", DataLog: "+ getDataLog());
		System.out.println();
	}
}
