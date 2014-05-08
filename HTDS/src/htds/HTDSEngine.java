package htds;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class HTDSEngine /*extends JFrame*/ implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	//GUI fields for login
	private JFrame frmLogin;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JButton btnLogin;
	
	//GUI fields for home screen(s)
	private JFrame frmHomeUser;
	private JButton btnViewer;
	private JButton btnAnalyzer;
	private JButton btnUploader;
	private JButton btnConfigManager;
	
	//GUI fields for menu bar
	private JMenuItem mntmLogout;
	private JMenuItem mntmExit;
	private JMenuItem mntmUploader;
	private JMenuItem mntmViewer;
	private JMenuItem mntmAnalyzer;
	private JMenuItem mntmConfigMgr;
	private JMenuItem mntmAddUser;
	private JMenuItem mntmUpdateUser;
	private JMenuItem mntmCreateProfile;
	private JMenuItem mntmDatabaseConfig;
	private JMenuItem mntmCopyrights;
	private JMenuItem mntmProduct;
	private JMenuItem mntmHumanTrafficking;
	private JMenuItem mntmUserManual;
	
	/**
	 * The analyzer
	 */
	private Analyzer analyze;
	/**
	 * The uploader
	 */
	private Uploader upload;
	/**
	 * The viewer
	 */
	private Viewer view;
	/**
	 * The configuration manager
	 */
	private ConfigMgr configurationManager;
	/**
	 * The database connector to connect us to the database
	 */
	private DBConnector dbConnector;
	
	/**
	 * Default constructor. Shows the login screen
	 */
	public HTDSEngine(){
		dbConnector = new DBConnector();
		
		try {
			showLoginScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}
		   
	}
	
	/**
	 * Shows the login screen
	 */
	public void showLoginScreen()
	{
		initialize();
		frmLogin.setVisible(true);
	}
	
	/**
	 * Hides the login screen
	 */
	public void hideLoginScreen()
	{
		frmLogin.setVisible(false);
	}
	
	/**
	 * Shows the home screen
	 * @param userProfile- the profile of the logged in user
	 */
	public void showHomeScreen(UserProfile userProfile)
	{
		//get the permissions of the user
		boolean[] permissions = userProfile.getPermissions();
		
		//not a admin, but can do everything else
		if(permissions[0] == true && permissions[1] == true
				&& permissions[2] == true && permissions[3] == false) 
		{
			initializeAgent();
		}
		//configuration manager, can do everything
		else if(permissions[0] == true && permissions[1] == true
				&& permissions[2] == true && permissions[3] == true) 
		{
			initializeAdministrator();
		}
		
		frmHomeUser.setVisible(true);
	}
	
	/**
	 * Hides the home screen
	 */
	public void hideHomeScreen()
	{
		frmHomeUser.setVisible(false);
	}
	
	/**
	 * Agent logs in
	 * @param username- username entered by the user
	 * @param password- password entered by the user
	 */
	public void login(String username, String password)
	{
		User user = dbConnector.login(username, password);
		if(user == null) //failed login
			System.out.println("Eventually show a failed login screen");
		else
			showMainMenu(user.getUserProfile());
		
	}
	
	/**
	 * Logs the current user out
	 */
	public void logout()
	{
		//how to log out?
		hideHomeScreen();
		showLoginScreen();
	}
	
	/**
	 * Show the corresponding main menu depending on if the person logged in is an
	 * administrator or just an agent.
	 * This code could be changed if we ever have a screen that to correspond with a
	 * user with different permissions than the two we currently have.
	 * Agent: Viewer, Uploader, Analyzer
	 * Configuration Manager: Viewer, Uploader, Analyzer, Configuration Manager
	 * 
	 * @param userProfile- the profile of the person logged in
	 */
	public void showMainMenu(UserProfile userProfile)
	{
		hideLoginScreen();
		showHomeScreen(userProfile);
	}
	
	/**
	 * Displays the selected module to the user
	 * @param moduleName- the name of the module to display
	 */
	public void getModule(String moduleName)
	{
		
	}
	
	/**
	 * Exit the program when the user is done
	 */
	public void exit()
	{
		System.exit(0);
	}

	/**
	 * Handles the actions in the log in screen and the home screen
	 */
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		//log in screen
		if(obj ==  btnLogin)
		{
			login(txtUsername.getText(), passwordField.getText());
		}
		//agent home or admin home
		else if(obj == btnAnalyzer)
		{
			//hideHomeScreen();
			analyze = new Analyzer();
		}
		else if(obj == btnUploader)
		{
			//hideHomeScreen();
			upload = new Uploader();
		}
		else if(obj == btnViewer)
		{
			//hideHomeScreen();
			view = new Viewer();
		}
		else if(obj == btnConfigManager)
		{
			//hideHomeScreen();
			configurationManager = new ConfigMgr();
		}
		else if(obj ==  mntmLogout)
		{
			logout();
		}
		else if(obj ==  mntmExit)
		{
			System.exit(0);
		}
		else if(obj ==  mntmUploader)
		{
			upload = new Uploader();
		}
		else if(obj ==  mntmViewer)
		{
			view = new Viewer();
		}
		else if(obj ==  mntmAnalyzer)
		{
			analyze = new Analyzer();
		}
		else if(obj ==  mntmConfigMgr)
		{
			configurationManager = new ConfigMgr();
		}
		else if(obj ==  mntmAddUser)
		{
			//add user
		}
		else if(obj ==  mntmUpdateUser)
		{
			//update user
		}
		else if(obj ==  mntmCreateProfile)
		{
			//create profile
		}
		else if(obj ==  mntmDatabaseConfig)
		{
			//database configuration
		}
		else if(obj ==  mntmCopyrights)
		{
			//copy right
		}
		else if(obj ==  mntmProduct)
		{
			//product
		}
		else if(obj ==  mntmHumanTrafficking)
		{
			//human trafficking
		}
		else if(obj ==  mntmUserManual)
		{
			//user's manual
		}
	}
	
	
	/**
	 * Creates the login screen
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.getContentPane().setForeground(new Color(0, 0, 0));
		frmLogin.setTitle("Login");
		frmLogin.setBackground(Color.WHITE);
		frmLogin.setBounds(100, 100, 450, 300);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		//** need to connect with database **//
		//** check the input username and password with the database **/
		//** if inavlid error message should be show **//
		btnLogin = new JButton("Login");
		btnLogin.setBackground(Color.GRAY);
		btnLogin.setBounds(171, 220, 117, 29);
		frmLogin.getContentPane().add(btnLogin);
		btnLogin.addActionListener(this);
		
		
		// input username entered by the user **// 
		txtUsername = new JTextField();
		txtUsername.setBounds(221, 112, 134, 28);
		frmLogin.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBounds(107, 118, 69, 16);
		frmLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setBounds(115, 158, 61, 16);
		frmLogin.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "loginpic.png"));
		lblNewLabel_2.setBounds(34, 17, 103, 78);
		frmLogin.getContentPane().add(lblNewLabel_2);
		
		JLabel lblHtds = new JLabel("HTDS");
		lblHtds.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblHtds.setForeground(UIManager.getColor("ComboBox.selectionBackground"));
		lblHtds.setBounds(149, 23, 117, 47);
		frmLogin.getContentPane().add(lblHtds);
		
		JLabel lblPhoneAnalysis = new JLabel("Phone Analysis");
		lblPhoneAnalysis.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lblPhoneAnalysis.setForeground(Color.RED);
		lblPhoneAnalysis.setBounds(261, 23, 183, 47);
		frmLogin.getContentPane().add(lblPhoneAnalysis);
		
		JLabel lblHumanTraffickingDetection = new JLabel("Human Trafficking Detection System");
		lblHumanTraffickingDetection.setForeground(UIManager.getColor("ComboBox.selectionBackground"));
		lblHumanTraffickingDetection.setBounds(149, 69, 260, 16);
		frmLogin.getContentPane().add(lblHumanTraffickingDetection);
		
		
		//** password entered by the user **/
		passwordField = new JPasswordField();
		passwordField.setBounds(221, 152, 134, 28);
		frmLogin.getContentPane().add(passwordField);
		frmLogin.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtUsername, btnLogin}));
		frmLogin.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frmLogin.getContentPane(), txtUsername, btnLogin}));
	}
	
	/**
	 * Initialize the contents of the frame for an agent
	 */
	private void initializeAgent() {
		frmHomeUser = new JFrame();
		frmHomeUser.getContentPane().setBackground(Color.WHITE);
		frmHomeUser.getContentPane().setForeground(Color.WHITE);
		frmHomeUser.setBounds(100, 100, 450, 300);
		frmHomeUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmHomeUser.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		/** need to connect Login page, frame frmLogin and close all other pages**/
		mntmLogout = new JMenuItem("LogOut");
		mnFile.add(mntmLogout);
		mntmLogout.addActionListener(this);
		
		/** need to close all pages **/
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(this);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		/** need to connect page uploader.java frame frmHtdsToolsUploader**/
		mntmUploader = new JMenuItem("Uploader");
		mnTools.add(mntmUploader);
		mntmUploader.addActionListener(this);
		
		/** need to connect to page Viewer.java, frame frmHtdsToolsViewer **/
		mntmViewer = new JMenuItem("Viewer");
		mnTools.add(mntmViewer);
		mntmViewer.addActionListener(this);
		
		/** need to connect to page Analyzer.java, frame frmHtdstoolsAnalyzer **/
		mntmAnalyzer = new JMenuItem("Analyzer");
		mnTools.add(mntmAnalyzer);
		mntmAnalyzer.addActionListener(this);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		mntmCopyrights = new JMenuItem("Copyrights");
		mnAbout.add(mntmCopyrights);
		mntmCopyrights.addActionListener(this);
		
		mntmProduct = new JMenuItem("Product");
		mnAbout.add(mntmProduct);
		mntmProduct.addActionListener(this);
		
		mntmHumanTrafficking = new JMenuItem("Human Trafficking");
		mnAbout.add(mntmHumanTrafficking);
		mntmHumanTrafficking.addActionListener(this);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmUserManual = new JMenuItem("User Manual");
		mnHelp.add(mntmUserManual);
		mntmUserManual.addActionListener(this);
		
		frmHomeUser.getContentPane().setLayout(null);
		
		/** Button named Viewer. 
		 * On click will display Viewer page, frame frmHtdsToolsViewer **/ 
		btnViewer = new JButton("Viewer");
		btnViewer.setBackground(Color.LIGHT_GRAY);
		btnViewer.setBounds(56, 109, 117, 29);
		frmHomeUser.getContentPane().add(btnViewer);
		btnViewer.addActionListener(this);
		
		/** Button named Analyzer. 
		 * On click will display Viewer page, frame frmHtdsToolsAnalyzer **/
		btnAnalyzer = new JButton("Analyzer");
		btnAnalyzer.setBounds(179, 221, 117, 29);
		frmHomeUser.getContentPane().add(btnAnalyzer);
		btnAnalyzer.addActionListener(this);
		
		/** Button named Uploader. 
		 * On click will display Viewer page, frame frmHtdsToolsUploader **/
		btnUploader = new JButton("Uploader");
		btnUploader.setBounds(282, 109, 117, 29);
		frmHomeUser.getContentPane().add(btnUploader);
		btnUploader.addActionListener(this);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "viewerpic.png"));
		lblNewLabel.setBounds(67, 38, 89, 59);
		frmHomeUser.getContentPane().add(lblNewLabel);
		
		JLabel lblUploader = new JLabel("");
		lblUploader.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "uploaderpic.png"));
		lblUploader.setBounds(282, 38, 89, 59);
		frmHomeUser.getContentPane().add(lblUploader);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "analyserpic.png"));
		lblNewLabel_1.setBounds(179, 138, 104, 77);
		frmHomeUser.getContentPane().add(lblNewLabel_1);
		frmHomeUser.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnViewer, btnUploader, btnAnalyzer}));
		frmHomeUser.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frmHomeUser.getContentPane(), menuBar, mnFile, mntmLogout, mntmExit, mnTools, mntmUploader, mntmViewer, mntmAnalyzer, mnAbout, mntmCopyrights, mntmProduct, mntmHumanTrafficking, mnHelp, mntmUserManual, btnViewer, btnUploader, btnAnalyzer}));
	}
	
	/**
	 * Initialize the contents of the frame for an administrator
	 */
	private void initializeAdministrator()
	{
		frmHomeUser = new JFrame();
		frmHomeUser.setTitle("HTDS Home");
		frmHomeUser.getContentPane().setBackground(Color.WHITE);
		frmHomeUser.setBounds(100, 100, 450, 300);
		frmHomeUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHomeUser.getContentPane().setLayout(null);
		
		/** need to connect to page Viewer.java, frame frmHtdsToolsViewer **/
		/* On click will open frame frmHtdsToolsViewer **/
		btnViewer = new JButton("Viewer");
		btnViewer.setBounds(53, 84, 117, 29);
		frmHomeUser.getContentPane().add(btnViewer);
		btnViewer.addActionListener(this);
		
		/** need to connect page uploader.java frame frmHtdsToolsUploader**/
		/** On click open frame frmHtdsToolsUploader **/
		btnUploader = new JButton("Uploader");
		btnUploader.setBounds(263, 84, 117, 29);
		frmHomeUser.getContentPane().add(btnUploader);
		btnUploader.addActionListener(this);
		
		/** need to connect to page Viewer.java, frame frmHtdstoolsAnalyzer **/
		/** On click open frame frmHtdstoolsAnalyzer */
		btnAnalyzer = new JButton("Analyzer");
		btnAnalyzer.setBounds(53, 202, 117, 29);
		frmHomeUser.getContentPane().add(btnAnalyzer);
		btnAnalyzer.addActionListener(this);
		
		/** need to connect to page ConfManager.java, frame frmHtdstoolsConfigurationManager **/
		/** On click open frame frmHtdstoolsConfigurationManager */
		btnConfigManager = new JButton("Configuration Manager");
		btnConfigManager.setBounds(244, 202, 168, 29);
		frmHomeUser.getContentPane().add(btnConfigManager);
		btnConfigManager.addActionListener(this);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "viewerpic.png"));
		lblNewLabel.setBounds(63, 34, 92, 50);
		frmHomeUser.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "uploaderpic.png"));
		lblNewLabel_1.setBounds(276, 9, 92, 81);
		frmHomeUser.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "analyserpic.png"));
		lblNewLabel_2.setBounds(53, 118, 107, 85);
		frmHomeUser.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "confmgrpic.png"));
		lblNewLabel_3.setBounds(286, 125, 93, 70);
		frmHomeUser.getContentPane().add(lblNewLabel_3);
		
		JMenuBar menuBar = new JMenuBar();
		frmHomeUser.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		/** need to connect Login page, frame frmLogin and close all other pages**/
		mntmLogout = new JMenuItem("LogOut");
		mnFile.add(mntmLogout);
		mntmLogout.addActionListener(this);
		
		
		/** need to close all pages **/
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(this);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		/** need to connect page uploader.java frame frmHtdsToolsUploader**/
		mntmUploader = new JMenuItem("Uploader");
		mnTools.add(mntmUploader);
		mntmUploader.addActionListener(this);
		
		/** need to connect to page Viewer.java, frame frmHtdsToolsViewer **/
		mntmViewer = new JMenuItem("Viewer");
		mnTools.add(mntmViewer);
		mntmViewer.addActionListener(this);
		
		/** need to connect to page Analyser.java, frame frmHtdstoolsAnalyzer **/
		mntmAnalyzer = new JMenuItem("Analyzer");
		mnTools.add(mntmAnalyzer);
		mntmAnalyzer.addActionListener(this);
		
		mntmConfigMgr = new JMenuItem("Configuration Manager");
		mnTools.add(mntmConfigMgr);
		mntmConfigMgr.addActionListener(this);
		
		JMenu mnConfig = new JMenu("Config");
		menuBar.add(mnConfig);
		
		mntmAddUser = new JMenuItem("Add User");
		mnConfig.add(mntmAddUser);
		mntmAddUser.addActionListener(this);
		
		mntmUpdateUser = new JMenuItem("Update User");
		mnConfig.add(mntmUpdateUser);
		mntmUpdateUser.addActionListener(this);
		
		mntmCreateProfile = new JMenuItem("Create Profile");
		mnConfig.add(mntmCreateProfile);
		mntmCreateProfile.addActionListener(this);
		
		mntmDatabaseConfig = new JMenuItem("DataBase Config");
		mnConfig.add(mntmDatabaseConfig);
		mntmDatabaseConfig.addActionListener(this);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		mntmCopyrights = new JMenuItem("Copyrights");
		mnAbout.add(mntmCopyrights);
		mntmCopyrights.addActionListener(this);
		
		mntmProduct = new JMenuItem("Product");
		mnAbout.add(mntmProduct);
		mntmLogout.addActionListener(this);
		
		mntmHumanTrafficking = new JMenuItem("Human Trafficking");
		mnAbout.add(mntmHumanTrafficking);
		mntmHumanTrafficking.addActionListener(this);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmUserManual = new JMenuItem("User Manual");
		mnHelp.add(mntmUserManual);
		mntmUserManual.addActionListener(this);
		
		frmHomeUser.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frmHomeUser.getContentPane(), menuBar, mnFile, mntmLogout, mntmExit, mnTools, mntmUploader, mntmViewer, mntmAnalyzer, mnConfig, mntmAddUser, mntmUpdateUser, mntmCreateProfile, mntmDatabaseConfig, mnAbout, mntmCopyrights, mntmProduct, mntmHumanTrafficking, mnHelp, mntmUserManual, btnViewer, btnUploader, btnAnalyzer, btnConfigManager}));
	}
}
