package htds;

/**
 * The Viewer module allows a user to view the HTDS data
 * There are two types of data to view: Alerts and Data (Raw)
 * The Viewer Module defines the following viewing methods:
 * 1- View All
 * 2- View by Date (range)
 * 3- View by User
 * 4- View a specific item
 * @author Young
 *
 */
public class Viewer extends HTDSModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Viewer(){
		super("HTDS Viewer");
		setLocation(30, 30);		// This is an example code.
	}
}
