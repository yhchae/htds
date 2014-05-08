package htds;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

/**
 * @author Zachary Oliveira
 * 
 * To allow the administrator to perform duties that only pertain to him and 
 * not to a regular agent. Most of these duties involve adding/removing/editing 
 * users in the system, and getting/changing the path to the database.
 *
 */
public class ConfigMgr extends HTDSModule implements ActionListener{
	
	/**
	 * Database connector that connects us to the database
	 */
	private DBConnector dbConnector = new DBConnector();
	
	/**
	 * The path to the database
	 */
	private String dbPath;
	/**
	 * Create a file chooser which isused to choose which file the
	 * user would like to upload to the database
	 */
	private final JFileChooser fileChooser = new JFileChooser();
	
	private UserProfile[] userProfiles;
	private User[] users;
	
	//Configuration Manager GUI fields
	private JFrame frmHtdstoolsConfigurationManager;
	private JButton btnUserManagement;
	private JButton btnUserProfileManagement;
	private JButton btnDatabaseConfiguration;
	private JButton btnAlertProfile;
	
	//User Management GUI fields
	private JFrame frmConfigurationManagement = new JFrame();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textUserID;
	private JTextField textNewUserFullName;
	private JTextField textNewUserUserName;
	private JComboBox cmbNewUserUserProfile;
	private JComboBox cmbUser;
	private JTextField textUpdateUserPassword;
	private JTextField textNewUserPassword;
	private JComboBox cmbUpdateUserProfile;
	private JComboBox cmbDeleteUser;
	private JButton btnOk;
	private JRadioButton rdbtnAddNewUser;	
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnDeleteUser;
	
	//User Profile Management GUI
	private JFrame frmUserProfileManagement;
	private JTextField textProfileName;
	private JTextField textProfileID;
	private JCheckBox chckbxViewer;
	private JCheckBox chckbxUploader;
	private JCheckBox chckbxAnalyzer;
	private JCheckBox chckbxConfigurationManager;
	private JButton btnOkCM;
	
	//Configuration Manager GUI
	private JFrame frmDatabaseConfiguration;
	private JTextField textPath;
	private JButton btnBrowse;
	private JButton btnSetPath;
	
	//Alert Profile Management GUI
	private JFrame frmAlertProfileManagement;
	private JTextField textProfileMgID;
	private JTextField thresholdOne, thresholdTwo, thresholdThree, thresholdFour, thresholdFive;
	private JTable table;
	private JComboBox cmbColor;
	private JComboBox cmbLevel;
	private JTextField textdescription;
	private JButton btnOkAPM;
	
	/**
	 * Creates a configuration manager
	 */
	public ConfigMgr(){
		super("HTDS Configuration Manager");
		initialize();
		show();
	}
	
	/**
	 * Shows the window
	 */
	public void show()
	{
		frmHtdstoolsConfigurationManager.setVisible(true);
	}
	
	/**
	 * Hides the window
	 */
	public void hide()
	{
		frmHtdstoolsConfigurationManager.setVisible(false);
	}
	
	private String[] populateUserProfiles()
	{
		//populate profile list
		userProfiles = dbConnector.getUserProfile();
		String[] userProfileNames = new String[userProfiles.length];
		for(int i = 0; i < userProfiles.length; i++)
		{
			userProfileNames[i] = userProfiles[i].getProfileName();
		}
		
		return userProfileNames;
	}
	
	private String[] populateUsers()
	{
		users = dbConnector.getUser();
		String[] userNames = new String[users.length];
		for(int i = 0; i < users.length; i++)
		{
			userNames[i] = users[i].getFullName();
		}
		return userNames;
	}
	
	/**
	 * Allows the administrtor to add a new user
	 * @param user- the user to be added
	 * @return- true if addition was successful, false if it wasn't
	 */
	public boolean addUser(User user)
	{
		return dbConnector.addUser(user);
	}
	
	/**
	 * Allows the admin to remove an existing user
	 * @param user- the user to remove
	 * @return- true if removal was successful, false if it wasn't
	 */
	public boolean removeUser(User user)
	{
		return dbConnector.removeUser(user);
	}
	
	/**
	 * Allows the admin to update a user's password
	 * @param user- the user to be updated
	 * @param password- the new password
	 * @return- true if it was successful, false if it wasn't
	 */
	public boolean updateUserPassword(User user, String password)
	{
		return dbConnector.updateUserPassword(user, password);
	}
	
	/**
	 * Allows the admin to update a user's profile
	 * @param user- the user to be updated
	 * @param userProfile- the new profile
	 * @return- true if it was successful, false if it wasn't
	 */
	public boolean upateUserProfile(User user, UserProfile userProfile)
	{
		return dbConnector.updateUserProfile(user, userProfile);
	}
	
	/**
	 * Allows the admin to create a new user profile
	 * @param userProfile- the new profile
	 * @return- true if it was successful, false if it wasn't
	 */
	public boolean createUserProfile(UserProfile userProfile)
	{
		return dbConnector.createUserProfile(userProfile);
	}
	
	/**
	 * Allows the user to create a new alert profile
	 * @param alertProfile- the new alert profile
	 * @return- true if it was successful, false if it wasn't
	 */
	public boolean createAlertProfile(AlertProfile alertProfile)
	{
		return dbConnector.createAlertProfile(alertProfile);
	}
	
	/**
	 * Allows the admin to change the path to the database
	 * @param userProfile- the user profile
	 * @param path- the path to the database
	 * @return- true if it was successful, false if it wasn't
	 */
	public boolean setdbPath(UserProfile userProfile, String path)
	{
		dbPath = path;
		//why do i have user profile?
		return true;
	}
	
	/**
	 * Gets the path to the database
	 * @return- database path to be returned
	 */
	public boolean getdbPath()
	{
		return true;
	}
	
	/**
	 * Displays a dialog for each menu
	 * @param menuName- the name of the selected menu item
	 */
	public void show(String menuName)
	{
		if(menuName.equals("User Management"))
		{			
			showUserManagement();
			hideUserProfileManagement();
			hideDatabaseConfiguration();
			hideAlertProfileManagement();
		}
		else if(menuName.equals("User Profile Management"))
		{
			showUserProfileManagement();
			hideUserManagement();
			hideDatabaseConfiguration();
			hideAlertProfileManagement();
		}
		else if(menuName.equals("Database Configuration"))
		{
			showDatabaseConfiguration();
			hideUserManagement();
			hideUserProfileManagement();
			hideAlertProfileManagement();
		}
		else if(menuName.equals("Alert Profile Management"))
		{
			showAlertProfileManagement();
			hideUserManagement();
			hideUserProfileManagement();
			hideDatabaseConfiguration();
		}
	}
	
	/**
	 * Gets the configuration file from the data warehouse.
	 * @return- the configuration file
	 */
	public Byte[] getConfigFile()
	{
		return null;
	}
	
	/**
	 * To set configuration file of the data warehouse.
	 * @param fileName- the name of the configuration file
	 * @return- true if it was successful, false if it wasn't
	 */
	public boolean setConfigFile(String fileName)
	{
		return true;
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		//configuration manager home screen
		if(obj == btnUserManagement)
		{
			show("User Management");
		}
		else if(obj == btnUserProfileManagement)
		{
			show("User Profile Management");
		}
		else if(obj == btnDatabaseConfiguration)
		{
			show("Database Configuration");
		}
		else if(obj == btnAlertProfile)
		{
			show("Alert Profile Management");
		}
		//user management screen
		else if(obj == btnOk)
		{
			if(rdbtnAddNewUser.isSelected())
			{
				int userID = 999;
				String password = textNewUserPassword.getText();
				String name = textNewUserFullName.getText();
				String username = textNewUserUserName.getText();
				String userProfileString = (String) cmbNewUserUserProfile.getSelectedItem();
				
				int i;
				for(i = 0; i < userProfiles.length; i++)
				{
					if(userProfiles[i].getProfileName().equals(userProfileString))
					{
						break;
					}
				}
				
				
				
				User tempUser = new User(userID, name, username, password, userProfiles[i]);
				
				if(addUser(tempUser))
				{
					//success screen
					System.out.println("User successfully added.");
				}
				else
				{
					//fail screen
					System.out.println("User not successfully added.");
				}
				
			}
			else if(rdbtnNewRadioButton.isSelected())
			{
				String userString = (String) cmbUser.getSelectedItem();
				int i;
				for(i = 0; i < users.length; i++)
				{
					if(users[i].getFullName().equals(userString))
					{
						break;
					}
				}
				
				String userProfileString = (String) cmbUpdateUserProfile.getSelectedItem();
				
				int j;
				for(j = 0; j < userProfiles.length; j++)
				{
					if(userProfiles[j].getProfileName().equals(userProfileString))
					{
						break;
					}
				}
				
				//update user profile
				if(dbConnector.updateUserProfile(users[i], userProfiles[j]))
				{
					//success screen
					System.out.println("User profile successfully updated.");
				}
				else
				{
					//failure screen
					System.out.println("User profile not successfully updated.");
				}
				
				//if something is there, update the password
				if(!textUpdateUserPassword.getText().equals(""))
				{
					if(dbConnector.updateUserPassword(users[i], textUpdateUserPassword.getText()))
					{
						//show success screen
						System.out.println("User password successfully updated.");
					}
					else
					{
						//show failure screen
						System.out.println("User password not successfully updated.");
					}
				}
				
				
			}
			else if(rdbtnDeleteUser.isSelected())
			{
				String userString = (String) cmbDeleteUser.getSelectedItem();
				int i;
				for(i = 0; i < users.length; i++)
				{
					if(users[i].getFullName().equals(userString))
					{
						break;
					}
				}
				
				if(dbConnector.removeUser(users[i]))
				{
					//remove successful screen
					System.out.println("User successfully removed.");
				}
				else
				{
					//remove failure screen
					System.out.println("User not successfully removed.");
				}
			}
		}
		//User Profile Management
		else if(obj == btnOkCM)
		{
			String userProfileName = textProfileName.getText();
			//get the new permissions
			boolean analyzerPermission = chckbxAnalyzer.isSelected();
			boolean uploaderPermission = chckbxUploader.isSelected();
			boolean viewerPermission = chckbxViewer.isSelected();
			boolean configurationManagerPermission = chckbxConfigurationManager.isSelected();
			//put them in an array
			boolean[] permissions = {analyzerPermission, uploaderPermission, viewerPermission,
					configurationManagerPermission};
			UserProfile tempUserProfile = new UserProfile(999, userProfileName, permissions);
			
			if(createUserProfile(tempUserProfile))
			{
				//show success screen
				System.out.println("User profile successfully added.");
			}
			else
			{
				//show fail screen
				System.out.println("User profile not successfully added.");
			}
		}
		//DATABASE CONFIGURATION
		else if(obj == btnBrowse)
		{
			fileChooser.showOpenDialog(btnBrowse);
	        File file = fileChooser.getSelectedFile(); //get the file
	        textPath.setText(file.getPath()); //show the path
		}
		else if(obj == btnSetPath)
		{
			if(!textPath.getText().equals(""))
			{
				//this is where the code would go for updating path to database but this
				//version does not support that
			}
		}
		//Alert Profile Management
		else if(obj == btnOkAPM)
		{
			int id = Integer.parseInt(textProfileMgID.getText());
			int[] thresholds = new int[5];
			thresholds[0] = Integer.parseInt(thresholdOne.getText());
			thresholds[1] = Integer.parseInt(thresholdTwo.getText());
			thresholds[2] = Integer.parseInt(thresholdThree.getText());
			thresholds[3] = Integer.parseInt(thresholdFour.getText());
			thresholds[4] = Integer.parseInt(thresholdFive.getText());
			String description = textdescription.getText();
			//what do I do for log?
			
			AlertProfile tempAlertProfile = new AlertProfile(id, thresholds, description);
			
			if(createAlertProfile(tempAlertProfile))
			{
				//show success alert profile created
				System.out.println("Alert profile successfully added.");
			}
			else
			{
				//show fail alert profile created
				System.out.println("Alert profile not successfully added.");
			}
		}
	}
	
	private void showUserManagement()
	{		
		frmConfigurationManagement = new JFrame();
		frmConfigurationManagement.setBackground(Color.WHITE);
		frmConfigurationManagement.setTitle("Configuration Management");
		frmConfigurationManagement.setBounds(100, 100, 650, 450);
		frmConfigurationManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConfigurationManagement.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "userpic.png"));
		lblNewLabel.setBounds(6, 6, 61, 54);
		frmConfigurationManagement.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("USER MANAGEMENT");
		lblNewLabel_1.setForeground(new Color(220, 20, 60));
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		lblNewLabel_1.setBounds(85, 12, 263, 39);
		frmConfigurationManagement.getContentPane().add(lblNewLabel_1);
		
		/** radio button is set to select action add, update or delete user **/
		rdbtnAddNewUser = new JRadioButton("Add New User");
		buttonGroup.add(rdbtnAddNewUser);
		rdbtnAddNewUser.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		rdbtnAddNewUser.setForeground(new Color(65, 105, 225));
		rdbtnAddNewUser.setBounds(17, 89, 128, 23);
		frmConfigurationManagement.getContentPane().add(rdbtnAddNewUser);
		
		rdbtnNewRadioButton = new JRadioButton("Update User");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		rdbtnNewRadioButton.setForeground(new Color(65, 105, 225));
		rdbtnNewRadioButton.setBounds(314, 91, 116, 23);
		frmConfigurationManagement.getContentPane().add(rdbtnNewRadioButton);
		
		rdbtnDeleteUser = new JRadioButton("Delete User");
		buttonGroup.add(rdbtnDeleteUser);
		rdbtnDeleteUser.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		rdbtnDeleteUser.setForeground(new Color(65, 105, 225));
		rdbtnDeleteUser.setBounds(317, 241, 110, 23);
		frmConfigurationManagement.getContentPane().add(rdbtnDeleteUser);
		
		textUserID = new JTextField();
		textUserID.setBounds(107, 124, 134, 28);
		frmConfigurationManagement.getContentPane().add(textUserID);
		textUserID.setColumns(10);
		
		JLabel lblUserId = new JLabel("User ID");
		lblUserId.setForeground(new Color(34, 139, 34));
		lblUserId.setBounds(36, 130, 49, 16);
		frmConfigurationManagement.getContentPane().add(lblUserId);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(34, 139, 34));
		lblPassword.setBounds(36, 164, 61, 16);
		frmConfigurationManagement.getContentPane().add(lblPassword);
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setForeground(new Color(34, 139, 34));
		lblFullName.setBounds(36, 205, 67, 16);
		frmConfigurationManagement.getContentPane().add(lblFullName);
		
		JLabel lblUserProfile = new JLabel("User Profile");
		lblUserProfile.setForeground(new Color(34, 139, 34));
		lblUserProfile.setBounds(36, 283, 78, 16);
		frmConfigurationManagement.getContentPane().add(lblUserProfile);
		
		JLabel labelUserName = new JLabel("Username");
		labelUserName.setForeground(new Color(34, 139, 34));
		labelUserName.setBounds(36, 239, 78, 16);
		frmConfigurationManagement.getContentPane().add(labelUserName);
		
		textNewUserFullName = new JTextField();
		textNewUserFullName.setBounds(106, 199, 159, 28);
		frmConfigurationManagement.getContentPane().add(textNewUserFullName);
		textNewUserFullName.setColumns(10);
		
		textNewUserUserName = new JTextField();
		textNewUserUserName.setBounds(106, 239, 159, 28);
		frmConfigurationManagement.getContentPane().add(textNewUserUserName);
		textNewUserUserName.setColumns(10);
		
		/** need to connect with data of userprofile **/
		cmbNewUserUserProfile = new JComboBox(populateUserProfiles());
		cmbNewUserUserProfile.setBounds(108, 279, 144, 27);
		frmConfigurationManagement.getContentPane().add(cmbNewUserUserProfile);
		
		JLabel lblSelectUser = new JLabel("Select User");
		lblSelectUser.setForeground(new Color(34, 139, 34));
		lblSelectUser.setBounds(324, 121, 78, 16);
		frmConfigurationManagement.getContentPane().add(lblSelectUser);
		
		/** username will be selected. 
		 * then password, Full name, User Profile will displayed in respected text boxes to edit **/
		/** need to connect with username **/
		cmbUser = new JComboBox(populateUsers());
		cmbUser.setBounds(404, 115, 171, 27);
		frmConfigurationManagement.getContentPane().add(cmbUser);
		
		JLabel label = new JLabel("Password");
		label.setForeground(new Color(34, 139, 34));
		label.setBounds(348, 143, 61, 16);
		frmConfigurationManagement.getContentPane().add(label);
		
		JLabel label_2 = new JLabel("User Profile");
		label_2.setForeground(new Color(34, 139, 34));
		label_2.setBounds(348, 178, 78, 16);
		frmConfigurationManagement.getContentPane().add(label_2);
		
		textUpdateUserPassword = new JTextField();
		textUpdateUserPassword.setBounds(426, 143, 161, 28);
		frmConfigurationManagement.getContentPane().add(textUpdateUserPassword);
		textUpdateUserPassword.setColumns(10);
		
		textNewUserPassword = new JTextField();
		textNewUserPassword.setBounds(107, 158, 134, 28);
		frmConfigurationManagement.getContentPane().add(textNewUserPassword);
		textNewUserPassword.setColumns(10);
		
		/** need to connect to User Profile to change profile **/
		cmbUpdateUserProfile = new JComboBox(populateUserProfiles());
		cmbUpdateUserProfile.setBounds(426, 172, 144, 27);
		frmConfigurationManagement.getContentPane().add(cmbUpdateUserProfile);
		
		JLabel label_3 = new JLabel("Select User");
		label_3.setForeground(new Color(34, 139, 34));
		label_3.setBounds(327, 269, 78, 23);
		frmConfigurationManagement.getContentPane().add(label_3);
		
		/** connect the combobox with user database **/
		/** combobox will display username. Username will be selected, 
		 * then User Full name and User Profile will be displayed in respected text boxes **/
		cmbDeleteUser = new JComboBox(populateUsers());
		cmbDeleteUser.setBounds(404, 268, 171, 27);
		frmConfigurationManagement.getContentPane().add(cmbDeleteUser);
		
		/** set the OK button. Depends on the which radiobutton(choose of add, update or delete user) is selected, the action will be performed.**/
		/** OK button can add, update or delete data **/
		/** need to connect with database **/
		btnOk = new JButton("OK");
		btnOk.setBounds(260, 359, 117, 29);
		frmConfigurationManagement.getContentPane().add(btnOk);
		btnOk.addActionListener(this);
		
		frmConfigurationManagement.setVisible(true);
	}
	
	private void showUserProfileManagement()
	{
		frmUserProfileManagement = new JFrame();
		frmUserProfileManagement.setBackground(Color.WHITE);
		frmUserProfileManagement.setTitle("User Profile Management");
		frmUserProfileManagement.setBounds(100, 100, 450, 350);
		frmUserProfileManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUserProfileManagement.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "uerprofilepic.png"));
		lblNewLabel.setBounds(6, 6, 73, 59);
		frmUserProfileManagement.getContentPane().add(lblNewLabel);
		
		JLabel lblUserProfileManagement = new JLabel("User Profile Management");
		lblUserProfileManagement.setForeground(new Color(220, 20, 60));
		lblUserProfileManagement.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		lblUserProfileManagement.setBounds(85, 16, 315, 30);
		frmUserProfileManagement.getContentPane().add(lblUserProfileManagement);
		
		JLabel lblProfileName = new JLabel("Profile Name");
		lblProfileName.setForeground(new Color(0, 128, 0));
		lblProfileName.setBounds(62, 99, 80, 16);
		frmUserProfileManagement.getContentPane().add(lblProfileName);
		
		textProfileName = new JTextField();
		textProfileName.setBounds(154, 93, 134, 28);
		frmUserProfileManagement.getContentPane().add(textProfileName);
		textProfileName.setColumns(10);
		
		/** checkbox are made to make selection of permissions **/
		/** more than one checkbox can be selected **/
		chckbxViewer = new JCheckBox("Viewer");
		chckbxViewer.setForeground(new Color(0, 128, 0));
		chckbxViewer.setBounds(85, 195, 73, 23);
		frmUserProfileManagement.getContentPane().add(chckbxViewer);
		
		chckbxUploader = new JCheckBox("Uploader");
		chckbxUploader.setForeground(new Color(0, 128, 0));
		chckbxUploader.setBounds(202, 195, 94, 23);
		frmUserProfileManagement.getContentPane().add(chckbxUploader);
		
		chckbxAnalyzer = new JCheckBox("Analyzer");
		chckbxAnalyzer.setForeground(new Color(0, 128, 0));
		chckbxAnalyzer.setBounds(84, 219, 86, 23);
		frmUserProfileManagement.getContentPane().add(chckbxAnalyzer);
		
		chckbxConfigurationManager = new JCheckBox("Configuration Manager");
		chckbxConfigurationManager.setForeground(new Color(0, 128, 0));
		chckbxConfigurationManager.setBounds(202, 219, 176, 23);
		frmUserProfileManagement.getContentPane().add(chckbxConfigurationManager);
		
		/** need to connect to database userprofile **/
		btnOkCM = new JButton("OK");
		btnOkCM.setBounds(154, 259, 117, 29);
		frmUserProfileManagement.getContentPane().add(btnOkCM);
		btnOkCM.addActionListener(this);
		
		JLabel lblPermissions = new JLabel("Permissions:");
		lblPermissions.setBounds(62, 180, 80, 16);
		frmUserProfileManagement.getContentPane().add(lblPermissions);
		
		frmUserProfileManagement.setVisible(true);
	}
	
	private void showDatabaseConfiguration()
	{
		frmDatabaseConfiguration = new JFrame();
		frmDatabaseConfiguration.setBackground(Color.WHITE);
		frmDatabaseConfiguration.setTitle("Database Configuration");
		frmDatabaseConfiguration.setBounds(100, 100, 450, 300);
		frmDatabaseConfiguration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDatabaseConfiguration.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "databasepic.png"));
		lblNewLabel.setBounds(6, 10, 61, 56);
		frmDatabaseConfiguration.getContentPane().add(lblNewLabel);
		
		JLabel lblDatabaseConfiguration = new JLabel("DATABASE CONFIGURATION");
		lblDatabaseConfiguration.setForeground(new Color(220, 20, 60));
		lblDatabaseConfiguration.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		lblDatabaseConfiguration.setBounds(67, 17, 366, 35);
		frmDatabaseConfiguration.getContentPane().add(lblDatabaseConfiguration);
		
		JLabel lblSelectPath = new JLabel("Select Path");
		lblSelectPath.setForeground(new Color(0, 191, 255));
		lblSelectPath.setBounds(24, 112, 68, 16);
		frmDatabaseConfiguration.getContentPane().add(lblSelectPath);
		
		/** text field to write or select path for database connection **/
		textPath = new JTextField();
		textPath.setBounds(93, 106, 269, 28);
		frmDatabaseConfiguration.getContentPane().add(textPath);
		textPath.setColumns(10);
		
		/** Button browse **/
		btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(359, 107, 74, 29);
		frmDatabaseConfiguration.getContentPane().add(btnBrowse);
		btnBrowse.addActionListener(this);
		
		/** Button setpath **/
		btnSetPath = new JButton("Set Path");
		btnSetPath.setBounds(175, 168, 117, 29);
		frmDatabaseConfiguration.getContentPane().add(btnSetPath);
		btnSetPath.addActionListener(this);
		
		frmDatabaseConfiguration.setVisible(true);
	}
	
	private void showAlertProfileManagement()
	{
		frmAlertProfileManagement = new JFrame();
		frmAlertProfileManagement.setBackground(Color.WHITE);
		frmAlertProfileManagement.setTitle("Alert Profile Management");
		frmAlertProfileManagement.setBounds(100, 100, 450, 350);
		frmAlertProfileManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlertProfileManagement.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "alertpic.png"));
		lblNewLabel.setBounds(6, 6, 68, 60);
		frmAlertProfileManagement.getContentPane().add(lblNewLabel);
		
		JLabel lblAlertProfileManagement = new JLabel("Alert Profile Management");
		lblAlertProfileManagement.setForeground(new Color(220, 20, 60));
		lblAlertProfileManagement.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		lblAlertProfileManagement.setBounds(86, 17, 318, 30);
		frmAlertProfileManagement.getContentPane().add(lblAlertProfileManagement);
		
		JLabel lblProfileId = new JLabel("Profile ID");
		lblProfileId.setBounds(26, 98, 61, 16);
		frmAlertProfileManagement.getContentPane().add(lblProfileId);
		
		textProfileMgID = new JTextField();
		textProfileMgID.setBounds(86, 92, 134, 28);
		frmAlertProfileManagement.getContentPane().add(textProfileMgID);
		textProfileMgID.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(15, 130, 78, 16);
		frmAlertProfileManagement.getContentPane().add(lblDescription);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(342, 98, 1, 16);
		frmAlertProfileManagement.getContentPane().add(textArea);
		
		textdescription = new JTextField();
		textdescription.setBounds(86, 130, 117, 45);
		frmAlertProfileManagement.getContentPane().add(textdescription);
		textdescription.setColumns(10);
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setBounds(20, 213, 68, 16);
		frmAlertProfileManagement.getContentPane().add(lblThreshold);
		
		JLabel lblThresholdColors = new JLabel("(Red First)");
		lblThresholdColors.setBounds(281, 213, 68, 16);
		frmAlertProfileManagement.getContentPane().add(lblThresholdColors);
		
		thresholdOne = new JTextField();
		thresholdOne.setBounds(86, 207, 35, 28);
		frmAlertProfileManagement.getContentPane().add(thresholdOne);
		thresholdOne.setColumns(10);
		
		thresholdTwo = new JTextField();
		thresholdTwo.setBounds(126, 207, 35, 28);
		frmAlertProfileManagement.getContentPane().add(thresholdTwo);
		thresholdTwo.setColumns(10);
		
		thresholdThree = new JTextField();
		thresholdThree.setBounds(166, 207, 35, 28);
		frmAlertProfileManagement.getContentPane().add(thresholdThree);
		thresholdThree.setColumns(10);
		
		thresholdFour = new JTextField();
		thresholdFour.setBounds(206, 207, 35, 28);
		frmAlertProfileManagement.getContentPane().add(thresholdFour);
		thresholdFour.setColumns(10);
		
		thresholdFive = new JTextField();
		thresholdFive.setBounds(246, 207, 35, 28);
		frmAlertProfileManagement.getContentPane().add(thresholdFive);
		thresholdFive.setColumns(10);
		
		btnOkAPM = new JButton("OK");
		btnOkAPM.setBounds(54, 240, 117, 29);
		frmAlertProfileManagement.getContentPane().add(btnOkAPM);
		btnOkAPM.addActionListener(this);
		
		table = new JTable();
		table.setBackground(Color.LIGHT_GRAY);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setBounds(266, 290, 174, -85);
		frmAlertProfileManagement.getContentPane().add(table);
		frmAlertProfileManagement.setVisible(true);
	}
	
	private void hideUserManagement()
	{
		if(frmConfigurationManagement != null)
			frmConfigurationManagement.setVisible(false);
	}
	
	private void hideUserProfileManagement()
	{
		if(frmUserProfileManagement != null)
			frmUserProfileManagement.setVisible(false);
	}
	
	private void hideDatabaseConfiguration()
	{
		if(frmDatabaseConfiguration != null)
			frmDatabaseConfiguration.setVisible(false);
	}
	
	private void hideAlertProfileManagement()
	{
		if(frmAlertProfileManagement != null)
			frmAlertProfileManagement.setVisible(false);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHtdstoolsConfigurationManager = new JFrame();
		frmHtdstoolsConfigurationManager.setBackground(Color.WHITE);
		frmHtdstoolsConfigurationManager.setTitle("HTDSTools: Configuration Manager");
		frmHtdstoolsConfigurationManager.setBounds(100, 100, 550, 400);
		frmHtdstoolsConfigurationManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHtdstoolsConfigurationManager.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "confmgrpic.png"));
		lblNewLabel.setBounds(0, 2, 89, 59);
		frmHtdstoolsConfigurationManager.getContentPane().add(lblNewLabel);
		
		JLabel lblHtdsConfigurationManager = new JLabel("HTDS Configuration Manager");
		lblHtdsConfigurationManager.setForeground(new Color(220, 20, 60));
		lblHtdsConfigurationManager.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblHtdsConfigurationManager.setBounds(86, 6, 358, 42);
		frmHtdstoolsConfigurationManager.getContentPane().add(lblHtdsConfigurationManager);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "userpic.png"));
		lblNewLabel_1.setBounds(78, 94, 77, 67);
		frmHtdstoolsConfigurationManager.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "uerprofilepic.png"));
		lblNewLabel_2.setBounds(334, 94, 77, 67);
		frmHtdstoolsConfigurationManager.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "databasepic.png"));
		lblNewLabel_3.setBounds(89, 220, 66, 67);
		frmHtdstoolsConfigurationManager.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "alertpic.png"));
		lblNewLabel_4.setBounds(345, 220, 77, 67);
		frmHtdstoolsConfigurationManager.getContentPane().add(lblNewLabel_4);
		
		//** need to call UserMgmt page, frame frmUserProfileManagement **//
		btnUserManagement = new JButton("User Management");
		btnUserManagement.setBounds(40, 161, 150, 29);
		frmHtdstoolsConfigurationManager.getContentPane().add(btnUserManagement);
		btnUserManagement.addActionListener(this);
		
		//** need to call UserProfileMgmt page, frame frmUserProfileManagement **//
		btnUserProfileManagement = new JButton("User Profile Management");
		btnUserProfileManagement.setBounds(283, 161, 181, 29);
		frmHtdstoolsConfigurationManager.getContentPane().add(btnUserProfileManagement);
		btnUserProfileManagement.addActionListener(this);
		
		//** need to call DatabaseConfig page, frame frmDatabaseConfiguration **//
		btnDatabaseConfiguration = new JButton("Database Configuration");
		btnDatabaseConfiguration.setBounds(28, 286, 181, 29);
		frmHtdstoolsConfigurationManager.getContentPane().add(btnDatabaseConfiguration);
		btnDatabaseConfiguration.addActionListener(this);
		
		//** need to call AlertProfileMgmt page, frame frmAlertProfileManagement **//
		btnAlertProfile = new JButton("Alert Profile Management");
		btnAlertProfile.setBounds(283, 286, 192, 29);
		frmHtdstoolsConfigurationManager.getContentPane().add(btnAlertProfile);
		btnAlertProfile.addActionListener(this);
		
		frmHtdstoolsConfigurationManager.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnUserManagement, btnUserProfileManagement, btnDatabaseConfiguration, btnAlertProfile}));
	}
}
