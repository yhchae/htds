package htds;

//import ColorTableCellRenderer;
//import SimpleColorTableModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Zachary Oliveira
 * 
 * To allow the agent or administrator to retrieve and view the 
 * generated alerts and data from the database.
 *
 */
public class Viewer extends HTDSModule implements ActionListener{
	
	
	/**
	 * Database connector used to connect to the database
	 */
	private DBConnector dbConnector = new DBConnector();
	
	//GUI variables
	private JFrame frmHtdsToolsViewer;
	private final Component horizontalStrut = Box.createHorizontalStrut(20);
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textFieldUser;
	private JTextField textFieldVictimName;
	private JTextField textFieldPhNumber;
	private JTextField textFieldVctmname;
	private JTextField textFieldPhnum;
	private JTextField textFielduser;
	private JTextField textFieldDataId;
	private JButton btnView;
	private JRadioButton rdbtnViewAlerts;
	private JRadioButton rdbtnViewData;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JRadioButton rdbtnViewAllAlerts;
	private JRadioButton rdbtnViewByDate;
	private JRadioButton rdbtnViewByColor;	
	private JRadioButton rdbtnViewByUser;
	private JRadioButton rdbtnViewByPhone;
	private JRadioButton rdbtnViewByName;
	private JRadioButton rdbtnViewAllData;
	private JRadioButton rdbtnViewByDataid;
	private JRadioButton rdbtnUser;
	private JRadioButton rdbtnViewByPhno;
	private JRadioButton rdbtnViewByName_1;	
	private JFormattedTextField TextFieldfromdate;
	private JFormattedTextField TextFieldtodate;
	private JComboBox comboBoxColor;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	// Young
	DefaultTableModel alertTM;
	DefaultTableModel dataTM;
	
	private void initAlertTable(){
		alertTM = new DefaultTableModel();
		alertTM.addColumn("Alert ID");
		alertTM.addColumn("Color");
		alertTM.addColumn("Date");
		alertTM.addColumn("Phone Number");
		alertTM.addColumn("Frequency");
		alertTM.addColumn("Victim Names");
	}
	
	private void initDataTable(){
		dataTM = new DefaultTableModel();
		dataTM.addColumn("Data ID");
		dataTM.addColumn("Victim Name");
		dataTM.addColumn("Phone Number");
		dataTM.addColumn("File Name");
		dataTM.addColumn("Date");
	}
	
	/**
	 * Creates a viewer
	 */
	public Viewer(){
		super("HTDS Viewer");
		initialize();
		show();
	}
	
	/**
	 * shows the window
	 */
	public void show()
	{
		frmHtdsToolsViewer.setVisible(true);
	}
	
	/**
	 * hides the window
	 */
	public void hide()
	{
		frmHtdsToolsViewer.setVisible(false);
	}
	
	/**
	 * Gets array of alerts
	 * @return- the array of alerts
	 */
	public Alert[] getAlerts()
	{
		return dbConnector.getAlerts();
	}
	
	/**
	 * Gets the array of alerts
	 * @param startDate- alerts to be retrieved after this day
	 * @param endDate- alerts to be retrieved before this day
	 * @return- the array of alerts
	 */
	public Alert[] getAlerts(Date startDate, Date endDate)
	{
		return dbConnector.getAlerts(startDate, endDate);
	}
	
	/**
	 * Gets the array of alerts that match string in one of the conditions
	 * @param string- condition to be matched
	 * @param type- what kind of string is being passed
	 * 0 = victim name
	 * 1 = phone number
	 * 2 = color
	 * @return- the array of alerts
	 */
	public Alert[] getAlerts(String string, int type)
	{
		return dbConnector.getAlerts(string, type);
	}
	
	/**
	 * Gets the array of alerts created by a specific user
	 * @param user- which user to get the alerts of
	 * @return- the array of alerts
	 */
	public Alert[] getAlerts(User user)
	{
		return dbConnector.getAlerts(user);
	}
	
	/**
	 * Gets the array of data
	 * @return- the array of data
	 */
	public Data[] getData()
	{
		return dbConnector.getData();
	}
	
	/**
	 * Gets the specified array of data
	 * @param data- the data to be retrieved
	 * @return- the array of data
	 */
	public Data[] getData(Data data)
	{
		return dbConnector.getData(data);
	}
	
	/**
	 * Gets the array of data with the corresponding dataLogID
	 * @param dataLogID- ID of the data to be retrieved
	 * @return- the array of data
	 */
	public Data[] getData(int dataLogID)
	{
		return dbConnector.getData(dataLogID);
	}
	
	/**
	 * Gets the array of Data uploaded by a specific user
	 * @param user- which user to get the data of
	 * @return- the array of data
	 */
	public Data[] getData(User user)
	{
		return dbConnector.getData(user);
	}
	
	/**
	 * Gets the data that matches the condition of the string
	 * @param string- the condition to be matched
	 * @param type- what kind of string is being passed
	 * 0 = victim name
	 * 1 = phone number
	 * @return
	 */
	public Data[] getData(String string, int type)
	{
		return dbConnector.getData(string, type);
	}
	
	
	/**
	 * Checks to see what the user wants to view, and displays it to the user
	 */
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btnView) //view button is clicked
		{
			if(rdbtnViewAlerts.isSelected()) //options for viewing alerts
			{
				Alert[] alert;
				if(rdbtnViewAllAlerts.isSelected())
				{
					alert = getAlerts();
					initializeAlerts(alert);
					
				}
				else if(rdbtnViewByDate.isSelected())
				{
					//get the entered input from user
					String stringStartDate = TextFieldfromdate.getText();
					String stringEndDate = TextFieldtodate.getText();
					//convert strings to Date
					Date startDate = Date.valueOf(stringStartDate);
					Date endDate = Date.valueOf(stringEndDate);
					//get alerts and initialize
					alert = getAlerts(startDate, endDate);
					initializeAlerts(alert);
				}
				else if(rdbtnViewByColor.isSelected())
				{
					alert = getAlerts(comboBoxColor.getSelectedItem().toString(), 2);
					initializeAlerts(alert);
				}
				else if(rdbtnViewByUser.isSelected())
				{
					User tempUser = new User(null, textFieldUser.getText(), null);
					
					alert = getAlerts(tempUser);
					initializeAlerts(alert);
				}
				else if(rdbtnViewByPhone.isSelected())
				{
					alert = getAlerts(textFieldPhNumber.getText(), 1);
					initializeAlerts(alert);
				}
				else if(rdbtnViewByName.isSelected())
				{
					alert = getAlerts(textFieldVictimName.getText(), 0);
					initializeAlerts(alert);
				}
			}
			else if(rdbtnViewData.isSelected()) //options for viewing data
			{
				Data[] data;
				if(rdbtnViewAllData.isSelected())
				{
					data = getData();
					initializeData(data);
				}
				else if(rdbtnViewByDataid.isSelected())
				{
					data = getData(Integer.parseInt(textFieldDataId.getText()));
					initializeData(data);
				}
				else if(rdbtnUser.isSelected())
				{
					User tempUser = new User(null, textFielduser.getText(), null);
					
					data = getData(tempUser);
					initializeData(data);
				}
				else if(rdbtnViewByPhno.isSelected())
				{
					data = getData(textFieldPhnum.getText(), 1);
					initializeData(data);
				}
				else if(rdbtnViewByName_1.isSelected())
				{
					data = getData(textFieldVctmname.getText(), 0);
					initializeData(data);
				}
			}
		}
	}
	
	private void initializeAlerts(Alert[] alert)
	{
		initAlertTable();
		JFrame frame = new JFrame();
		JTable leftTable;
		
		frame.setTitle("Alert Table");
		//frame.setDefaultCloseOperation(1);

        frame.setLayout(new GridBagLayout());

        leftTable = new JTable(alertTM);

        leftTable.setFillsViewportHeight(true);

        populateAlerts((DefaultTableModel) leftTable.getModel(), alert);

        JPanel pnlActions = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.33;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weighty++;
        gbc.fill = GridBagConstraints.BOTH;

        frame.add(new JScrollPane(leftTable), gbc);
        gbc.gridx++;

        frame.setSize(1200, 600);
        frame.setVisible(true);
	}
	
	private void initializeData(Data[] data)
	{
		initDataTable();
		JFrame frame = new JFrame();
		JTable leftTable;
		
		frame.setTitle("Data Table");
		//frame.setDefaultCloseOperation(1);

        frame.setLayout(new GridBagLayout());

        leftTable = new JTable(dataTM);

        leftTable.setFillsViewportHeight(true);

        populateData((DefaultTableModel) leftTable.getModel(), data);

        JPanel pnlActions = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.33;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weighty++;
        gbc.fill = GridBagConstraints.BOTH;

        frame.add(new JScrollPane(leftTable), gbc);
        gbc.gridx++;

        frame.setSize(1200, 600);
        frame.setVisible(true);
	}
	
	private void populateAlerts(DefaultTableModel model, Alert[] alerts) {
		
		//fill the table
		for(int i = 0; i < alerts.length; i++)
		{
			//put array of victim names in a string
			String tempVictimNames = "";
			for(int j = 0; j < alerts[i].getVictimNames().length; j++)
			{
				if(!tempVictimNames.equals(""))
					tempVictimNames += ", "; 
				tempVictimNames += alerts[i].getVictimNames()[j];
			}
			model.addRow(new Object[]{alerts[i].getID(), alerts[i].getColor(), 
					alerts[i].getAlertLog().getDate(), alerts[i].getSPN(), 
					alerts[i].getFrequency(), tempVictimNames});
		}
		
        
    }
	
	private void populateData(DefaultTableModel model, Data[] data) {
		
		//fill the table
		for(int i = 0; i < data.length; i++)
		{
			model.addRow(new Object[]{data[i].getID(), data[i].getName(), 
					data[i].getPhone(), data[i].getDataLog().getFileName(), 
					data[i].getDataLog().getDate()});
		}
		
        
    }
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHtdsToolsViewer = new JFrame();
		
		frmHtdsToolsViewer.setTitle("HTDS Tools: Viewer");
		frmHtdsToolsViewer.getContentPane().setBackground(Color.WHITE);
		frmHtdsToolsViewer.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "viewerpic.png"));
		lblNewLabel.setBounds(6, 6, 55, 30);
		frmHtdsToolsViewer.getContentPane().add(lblNewLabel);
		
		JLabel lblHtdsViewer = new JLabel("HTDS VIEWER");
		lblHtdsViewer.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblHtdsViewer.setForeground(new Color(220, 20, 60));
		lblHtdsViewer.setBounds(73, 6, 193, 30);
		frmHtdsToolsViewer.getContentPane().add(lblHtdsViewer);
		
		rdbtnViewAlerts = new JRadioButton("View Alerts");
		rdbtnViewAlerts.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		rdbtnViewAlerts.setForeground(new Color(65, 105, 225));
		rdbtnViewAlerts.setSelected(true);
		buttonGroup.add(rdbtnViewAlerts);
		rdbtnViewAlerts.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent arg0) {
			}
		});
		rdbtnViewAlerts.setBounds(6, 48, 141, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewAlerts);
		horizontalStrut.setBounds(0, 26, 550, 29);
		frmHtdsToolsViewer.getContentPane().add(horizontalStrut);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(304, 48, 15, 234);
		frmHtdsToolsViewer.getContentPane().add(verticalStrut);
		
		rdbtnViewData = new JRadioButton("View Data");
		rdbtnViewData.setForeground(new Color(65, 105, 225));
		rdbtnViewData.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		buttonGroup.add(rdbtnViewData);
		rdbtnViewData.setBounds(314, 48, 141, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewData);
		
		rdbtnViewAllAlerts = new JRadioButton("View All Alerts");
		buttonGroup_1.add(rdbtnViewAllAlerts);
		rdbtnViewAllAlerts.setForeground(new Color(60, 179, 113));
		rdbtnViewAllAlerts.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewAllAlerts.setBounds(6, 72, 141, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewAllAlerts);
		
		rdbtnViewByDate = new JRadioButton("By Date: YYYY-MM-DD");
		buttonGroup_1.add(rdbtnViewByDate);
		rdbtnViewByDate.setForeground(new Color(60, 179, 113));
		rdbtnViewByDate.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewByDate.setBounds(6, 101, 141, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewByDate);
		
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setForeground(new Color(60, 179, 113));
		lblFrom.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		lblFrom.setBounds(27, 131, 32, 16);
		frmHtdsToolsViewer.getContentPane().add(lblFrom);
		
		TextFieldfromdate = new JFormattedTextField(dateFormat);
		TextFieldfromdate.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		TextFieldfromdate.setBounds(68, 125, 98, 28);
		frmHtdsToolsViewer.getContentPane().add(TextFieldfromdate);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setForeground(new Color(60, 179, 113));
		lblTo.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		lblTo.setBounds(167, 131, 22, 16);
		frmHtdsToolsViewer.getContentPane().add(lblTo);
		
		TextFieldtodate = new JFormattedTextField(dateFormat);
		TextFieldtodate.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		TextFieldtodate.setBounds(187, 125, 105, 28);
		frmHtdsToolsViewer.getContentPane().add(TextFieldtodate);
		
		rdbtnViewByColor = new JRadioButton("View By Color");
		buttonGroup_1.add(rdbtnViewByColor);
		rdbtnViewByColor.setForeground(new Color(60, 179, 113));
		rdbtnViewByColor.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewByColor.setBounds(6, 157, 111, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewByColor);
		
		rdbtnViewByUser = new JRadioButton("View By User");
		buttonGroup_1.add(rdbtnViewByUser);
		rdbtnViewByUser.setForeground(new Color(60, 179, 113));
		rdbtnViewByUser.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewByUser.setBounds(6, 186, 105, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewByUser);
		
		rdbtnViewByPhone = new JRadioButton("View By Phone Number");
		buttonGroup_1.add(rdbtnViewByPhone);
		rdbtnViewByPhone.setForeground(new Color(60, 179, 113));
		rdbtnViewByPhone.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewByPhone.setBounds(5, 218, 162, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewByPhone);
		
		rdbtnViewByName = new JRadioButton("View By Name");
		buttonGroup_1.add(rdbtnViewByName);
		rdbtnViewByName.setForeground(new Color(60, 179, 113));
		rdbtnViewByName.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewByName.setBounds(6, 248, 111, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewByName);
		
		String[] alertColors = {"Red", "Orange", "Yellow", "Blue", "Green"};
		comboBoxColor = new JComboBox(alertColors);
		comboBoxColor.setBounds(126, 157, 121, 27);
		frmHtdsToolsViewer.getContentPane().add(comboBoxColor);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(113, 183, 153, 28);
		frmHtdsToolsViewer.getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);
		
		textFieldVictimName = new JTextField();
		textFieldVictimName.setBounds(113, 243, 168, 28);
		frmHtdsToolsViewer.getContentPane().add(textFieldVictimName);
		textFieldVictimName.setColumns(10);
		
		textFieldPhNumber = new JTextField();
		textFieldPhNumber.setBounds(163, 215, 118, 28);
		frmHtdsToolsViewer.getContentPane().add(textFieldPhNumber);
		textFieldPhNumber.setColumns(10);
		
		rdbtnViewAllData = new JRadioButton("View All Data");
		buttonGroup_2.add(rdbtnViewAllData);
		rdbtnViewAllData.setForeground(new Color(60, 179, 113));
		rdbtnViewAllData.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewAllData.setBounds(314, 72, 111, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewAllData);
		
		rdbtnViewByDataid = new JRadioButton("View By DataLogID");
		buttonGroup_2.add(rdbtnViewByDataid);
		rdbtnViewByDataid.setForeground(new Color(60, 179, 113));
		rdbtnViewByDataid.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewByDataid.setBounds(314, 101, 185, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewByDataid);
		
		rdbtnUser = new JRadioButton("View By Upload User");
		buttonGroup_2.add(rdbtnUser);
		rdbtnUser.setForeground(new Color(60, 179, 113));
		rdbtnUser.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnUser.setBounds(314, 125, 148, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnUser);
		
		rdbtnViewByPhno = new JRadioButton("View By Phone Number");
		buttonGroup_2.add(rdbtnViewByPhno);
		rdbtnViewByPhno.setForeground(new Color(60, 179, 113));
		rdbtnViewByPhno.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewByPhno.setBounds(314, 157, 165, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewByPhno);
		
		rdbtnViewByName_1 = new JRadioButton("View By Name");
		buttonGroup_2.add(rdbtnViewByName_1);
		rdbtnViewByName_1.setForeground(new Color(60, 179, 113));
		rdbtnViewByName_1.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		rdbtnViewByName_1.setBounds(314, 186, 115, 23);
		frmHtdsToolsViewer.getContentPane().add(rdbtnViewByName_1);
		
		textFieldVctmname = new JTextField();
		textFieldVctmname.setBounds(432, 185, 162, 28);
		frmHtdsToolsViewer.getContentPane().add(textFieldVctmname);
		textFieldVctmname.setColumns(10);
		
		textFieldPhnum = new JTextField();
		textFieldPhnum.setBounds(483, 155, 111, 28);
		frmHtdsToolsViewer.getContentPane().add(textFieldPhnum);
		textFieldPhnum.setColumns(10);
		
		textFielduser = new JTextField();
		textFielduser.setBounds(472, 124, 134, 28);
		frmHtdsToolsViewer.getContentPane().add(textFielduser);
		textFielduser.setColumns(10);
		
		textFieldDataId = new JTextField();
		textFieldDataId.setBounds(506, 98, 134, 23);
		frmHtdsToolsViewer.getContentPane().add(textFieldDataId);
		textFieldDataId.setColumns(10);
		
		
		
		/** need to connect to DataReport.java, frame frmDataReport **/
		/** need call the records from database. **/
		/** Based upon the selection records will be called, either
		 * radio button of view Alerts rdbtnViewAlerts and one of other alerts button should be selected or,
		 * radio button of view data rdbtnViewData and one of other data button should be selected **/
		
		btnView = new JButton("View");
		btnView.setBounds(256, 298, 117, 29);
		frmHtdsToolsViewer.getContentPane().add(btnView);
		frmHtdsToolsViewer.setBounds(100, 100, 700, 400);
		frmHtdsToolsViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnView.addActionListener(this);
	}
	
}
