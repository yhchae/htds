package htds;

/**
 * 
 * @author Younghun
 *
 */
public class UserProfile {
	private final int MAX_PERMISSIONS = 4;
	
	private int profileID;
	private String  profileName;
	private boolean[] permissions;
	
	/**
	 * 
	 */
	public UserProfile(){
		this.permissions = new boolean[MAX_PERMISSIONS];
	}
	
	/**
	 * 
	 * @param profileID
	 */
	public void setProfileID(int profileID){
		this.profileID = profileID;
	}
	
	/**
	 * 
	 * @param profileName
	 */
	public void setProfileName(String profileName){
		this.profileName = profileName;
	}
	
	/**
	 * 
	 * @param permissions
	 */
	public void setPermissions(boolean[] permissions){
		if(permissions.length == MAX_PERMISSIONS){
			this.permissions = permissions;
		} else {
			this.permissions[0] = false;
			this.permissions[1] = false;
			this.permissions[2] = false;
			this.permissions[3] = false;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getProfileID(){
		return profileID;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getProfileName(){
		return profileName;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean[] getPermissions(){
		return permissions;
	}
}
