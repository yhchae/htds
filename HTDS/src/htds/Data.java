package htds;
/**
 * 
 * @author Younghun
 * To provide the data structure for the data information
 * 
 */
public class Data {
	private int dataID;
	private String victimName;
	private String phone;
	private DataLog dataLog;
	private int frequency;
	
	/**
	 * Constructor : Initialize the private values.
	 */
	public Data(){
		this.dataID = 0;
		this.victimName = null;
		this.phone = null;
		this.dataLog = null;
		this.frequency = 0;
	}
	
	/**
	 * To initialize the victim name
	 * @param dataID - the name of the victim
	 */
	public void setDataID(int dataID){
		this.dataID = dataID;
	}
	
	/**
	 * 
	 * @param victimName
	 */
	public void setV_Name(String victimName){
		this.victimName = victimName;
	}
	
	/**
	 * To initialize or change the phone number
	 * @param phone - the phone number
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}

	/**
	 * 
	 * @param dataLog
	 */
	public void setDataLog(DataLog dataLog){
		this.dataLog = dataLog;
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
	 * @return
	 */
	public int getDataID(){
		return this.dataID;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getVictimName(){
		return this.victimName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPhone(){
		return this.phone;
	}
	
	/**
	 * 
	 * @return
	 */
	public DataLog getDataLog(){
		return this.dataLog;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFrequency(){
		return this.frequency;
	}
}
