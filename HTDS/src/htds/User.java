package htds;

/**
 * The User class is a basic data structure that stores basic information about an HTDS user.
 * Every User has a unique ID, a username, a password and full name.
 * Every User has a UserProfile object that specifies the access permissions for that user in HTDS
 * Once a user is created, the id and username are fixed, but the fullName, password and UserProfileID could be updated
 * 
 * @author Qutaiba
 *
 */
public class User {
	private int id; /**a unique ID identifying each user */
	private String fullName; /**full name of the user*/
	private String username; /**username to be used during login*/
	private String password; /**password to be used during login*/
	private int userProfileID; /**id referring to the userprofile used to specify the access permission to HTDS modules*/
	
	/**
	 * This constructor creates an object of type User with properties equal to the passed arguments
	 * @param id: unique id number to identify the user
	 * @param fullName: a String for the full name of the user to be used for documentation
	 * @param username: a String which the user will use to login to HTDS
	 * @param password: a String which the user will use to login to HTDS
	 * @param profile: an object of type UserProfile to define access permissions
	 */
	public User(int id, String fullName, String username, String password, UserProfile profile){
		setID(id);
		this.fullName = fullName;
		setUsername(username);
		this.password = password;
		this.userProfileID = profile.getID();
	}
	
	/**
	 * This constructor creates an object of type User with properties equal to the passed arguments
	 * @param id: unique id number to identify the user
	 * @param fullName: a String for the full name of the user to be used for documentation
	 * @param username: a String which the user will use to login to HTDS
	 * @param password: a String which the user will use to login to HTDS
	 * @param userProfileID: an ID referring to the UserProfile to be used in defining access permissions
	 */
	public User(int id, String fullName, String username, String password, int userProfileID){
		setID(id);
		this.fullName = fullName;
		setUsername(username);
		this.password = password;
		this.userProfileID = userProfileID;
	}
	
	/**
	 * This constructor creates an object of type User with same properties as the passed User object
	 * @param user: an object of type User
	 */
	public User(User user){
		setID(user.getID());
		this.fullName = user.getFullName();
		setUsername(user.getUsername());
		this.password = user.getPassword();
		this.userProfileID = user.getUserProfileID();
	}
	/**
	 * 
	 * @param id: a unique id assigned to each alert. 
	 */
	private void setID(int id){
		if(id >=0)
			this.id = id;
		else{
			this.id = -1;
			System.out.println("ERROR:User:setUserID: invalid ID. Should be >= 0");
		}
	}
	
	/**
	 * 
	 * @param fullName: a String for the full name of the user to be used for documentation 
	 */
	public void setFullName(String fullName){
		this.fullName = fullName;
	}
	
	/**
	 * 
	 * @param username: a String which the user will use to login to HTDS
	 */
	private void setUsername(String username){
		this.username = username;
	}
	
	/**
	 * 
	 * @param password: a String which the user will use to login to HTDS
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * 
	 * @param userProfile: 
	 */
	public void setUserProfileID(UserProfile userProfile){
		this.userProfileID = userProfile.getID();
	}
	
	/**
	 * 
	 * @param userProfileID: the id of the UserProfile object to be associated with this User object
	 */
	public void setUserProfileID(int userProfileID){
		this.userProfileID = userProfileID;
	}
	
	/**
	 * 
	 * @return the user ID
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * 
	 * @return the full name of the User
	 */
	public String getFullName(){
		return fullName;
	}
	
	/**
	 * 
	 * @return: the username (the string used for login) of this user
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * 
	 * @return the password of this user
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * 
	 * @return: the id of the UserProfile associated with this User object
	 */
	public int getUserProfileID(){
		return userProfileID;
	}
	
	/**
	 * Prints the details of a User object
	 */
	public void print(){
		System.out.println("Not implemented yet");
	}
}
