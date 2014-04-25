package htds;

/**
 * The ConfigMgr (Configuration Manager) is one of the four HTDS Module providing an administrator with tools
 * for managing the HTDS System. 
 * The following activities could be achieved through the configuraiton manager
 * 1- Create User Profiles
 * 2- Create Alert Profiles
 * 3- Add/Remover/Update Users
 * 4- Configure Database location
 * @author Young
 *
 */
public class ConfigMgr extends HTDSModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public ConfigMgr(){
		super("HTDS Configuration Manager");
		setLocation(0, 0);		// This is an example code.
	}
}
