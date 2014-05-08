package htds;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import org.eclipse.wb.swing.FocusTraversalOnArray;

/**
 * The UserProfile class is a data structure in the User class that defines the access permissions of the User to the HTDS modules
 * Every User has a UserProfile instance as a data member
 * The permissions are defined as a boolean array referring to the following modules: [Viewer, Uploader, Analyzer, ConfigMgr]
 * Once a user profile is created, only the user profile name and permission matrix could be updated
 * @author Qutaiba
 *
 */
public class UserProfile {
	private final int MAX_PERMISSIONS = 4;
	
	private int id;
	private String  profileName;
	private boolean[] permissions;
	
	
	
	/**
	 * This constructor creates a UserProfile with all properties set to null
	 */
	public UserProfile(){
		this.id = 0;
		this.permissions = new boolean[MAX_PERMISSIONS];
	}
	

	
	/**
	 * This Constructor creates a userProfile with the given id, but the profileName and permissions are set to null
	 * @param id
	 */
	public UserProfile(int id){
		this.id = id;
		this.permissions = new boolean[MAX_PERMISSIONS];
		this.profileName = "";
	}
	/**
	 * This constructor creates an object of type UserProfile with all properties set according to the passed arguments
	 * @param id: a unique integer number identifying each UserProbile object
	 * @param name: a String referring to the UserProfile Name
	 * @param permissions: an array of boolean values
	 */
	public UserProfile(int id, String name, boolean[] permissions){
		this.id = id;
		this.profileName = name;
		this.permissions = permissions;
	}
	/**
	 * This constructor creates an object of type UserProfile with same properties as the passed UserProfile object
	 * @param profile: an object of type UserProfile
	 */
	public UserProfile(UserProfile profile){
		setID(profile.getID());
		this.profileName = profile.getProfileName();
		this.permissions = profile.getPermissions();
	}
	/**
	 * A private method that sets the userprofile id to a given non-negative integer
	 * @param profileID: a unique integer number identifying each UserProbile object
	 */
	private void setID(int profileID){
		if(profileID >=0)
			this.id = profileID;
		else{
			this.id = -1;
			System.out.println("ERROR:UserProfile:setID: invalid ID. Should be >= 0");
		}
	}
	
	/**
	 * 
	 * @param profileName: a String referring to the UserProfile Name
	 */
	public void setProfileName(String profileName){
		this.profileName = profileName;
	}
	
	/**
	 * 
	 * @param permissions: a boolean array referring to the following modules: [Viewer, Uploader, Analyzer, ConfigMgr] 
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
	 * @return the id of this UserProfile
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * 
	 * @return: the name of this UserProfile
	 */
	public String getProfileName(){
		return profileName;
	}
	
	/**
	 * 
	 * @return the permission matrix associated with this UserProfile object
	 */
	public boolean[] getPermissions(){
		return permissions;
	}
	
	/**
	 * prints the details of a UserProfile
	 */
	public void print(){
		System.out.println("Not implemented yet");
	}
}
