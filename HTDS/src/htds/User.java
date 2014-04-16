package htds;

/**
 * 
 * @author Younghun
 *
 */
public class User {
	private int userID;
	private String fullName;
	private String username;
	private String password;
	private UserProfile userProfile;
	
	/**
	 * 
	 */
	public User(){
		this.userID = 0;
		this.fullName = null;
		this.username = null;
		this.password = null;
		this.userProfile = null;
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
	 * @param fullName
	 */
	public void setFullname(String fullName){
		this.fullName = fullName;
	}
	
	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username){
		this.username = username;
	}
	
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * 
	 * @param userProfile
	 */
	public void setUserProfile(UserProfile userProfile){
		this.userProfile = userProfile;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getUserID(){
		return userID;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFullname(){
		return fullName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * 
	 * @return
	 */
	public UserProfile getUserProfile(){
		return userProfile;
	}
}
