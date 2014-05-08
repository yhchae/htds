package htds;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * 
 * @author Zachary Oliveira
 *
 *		This class analyzes the data by finding the repeating phone numbers, creating a 
 *		corresponding alert, and uploading all the alerts to the alert database.
 */
public class Analyzer extends HTDSModule implements ActionListener{
	
	/**
	 * Our connection to the data base.
	 */
	private DBConnector dbConnector = new DBConnector();
	/**
	 * An array of information uploaded by the agent.
	 * Each entry in the array holds phone numbers, victim names, id, log id, and a frequency.
	 */
	private Data[] data;
	/**
	 * Contains data that determines the parameters of the alerts we create based on
	 * the number of repeats in our data
	 */
	private AlertProfile theProfile;
	/**
	 * Array of string of Data ID numbers for the user to select from
	 */
	private String[] dataIDString;
	
	private String[] alertProfileLogString;
	
	//GUI fields
	private JFrame frmHtdstoolsAnalyzer;
	private JComboBox cmbSelectData;
	private JComboBox cmbSelectAlertProfileLog;
	private JButton btnAnalyze;
	

	/**
	 * Creates an analyzer
	 */
	public Analyzer(){
		super("HTDS Analyzer");
		populateDataList();
		populateAlertProfileLogList();
		initialize();
		show();
	}
	
	/**
	 * Shows the window
	 */
	public void show()
	{
		frmHtdstoolsAnalyzer.setVisible(true);
	}
	
	/**
	 * Hides the window
	 */
	public void hide()
	{
		frmHtdstoolsAnalyzer.setVisible(false);
	}

	/**
	 * Fills an array of data log ID numbers that will be added to the combo box
	 */
	private void populateDataList()
	{
		Data[] data = dbConnector.getData();
		int[] dataID = new int[data.length];
		dataIDString = new String[data.length];
		for(int i = 0; i < data.length; i++)
		{
			dataID[i] = data[i].getDataLog().getID();
			String tempString = Integer.toString(dataID[i]);
			dataIDString[i] = tempString;
		}
	}
	
	/**
	 * Fills an array of alert profile log descriptions that will be added to the combo box
	 */
	private void populateAlertProfileLogList()
	{
		AlertProfileLog[] alertProfileLog = dbConnector.getAlertProfileLog();
		alertProfileLogString = new String[alertProfileLog.length];
		for(int i = 0; i < alertProfileLog.length; i++)
		{
			//if(alertProfileLog[i].getDescription() != null)
				alertProfileLogString[i] = alertProfileLog[i].getDescription();
		}
	}
	
	/**
	 * Handles the action when the analyze button is clicked
	 */
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		//if the analyze button was clicked
		if(obj == btnAnalyze)
		{
			String id = (String)cmbSelectData.getSelectedItem(); //get selected item
			int dataID = Integer.parseInt(id); //convert it to int
			analyze(dbConnector.getData(dataID)); //analyze the selected data
		}
	}
	

	/**
	 * The method first sorts the data by putting the phone numbers in ascending order.
	 * Then, we go through the sorted list to see if there are duplicate phone numbers 
	 * (all duplicates will be "side-by-side" in the array).
	 * Then we go through all the duplicate phone numbers and create alerts for each one
	 * that has more appeareances than the minimal amount as listed in the alert profile,
	 * and upload the alerts to the database.
	 * @param dataArray- the data to be analyzed
	 * @return- Returns True if the data was analyzed correctly, false if it wasn't.
	 */
	public void analyze(Data[] dataArray)
	{ 
		
		//update variable
		data = dataArray;
		//sort the phone numbers in n*log(n) time using merge sort
		sort();
		
		
		//finding duplicate phone numbers in n time
		//for storing which phoneNumbers appear more than once and how many times they appear
		//they have corresponding positions in the array
		long[] repeatedPhoneNumbers = new long[data.length];
		int[] numberOfRepeats = new int[data.length];
		//keeps track of the repeated names
		String[] victimNames = new String[data.length];
		//keeps track of how many numbers have appeared more than once
		int repeatedPhoneNumbersCount = 0;
		//keeps track of total number of repeats
		int totalNumberOfRepeats = 0;
		//keeps track of if we're done counting a certain number
		boolean currentlyRepeating = false;
		
		//loop through entire list of phone numbers
		for(int i = 0; i < data.length; i++)
		{
			if(!currentlyRepeating) //first time we've seen a number
			{
				//if the current number equals the next number
				if(i+1 < data.length && data[i].getPhone().equals(data[i+1].getPhone()))
				{
					//System.out.println("REPEATING1");
					//add it to our arrays
					repeatedPhoneNumbers[repeatedPhoneNumbersCount] = Long.parseLong(data[i].getPhone());
					numberOfRepeats[repeatedPhoneNumbersCount] = 2; //seen it twice
					victimNames[totalNumberOfRepeats] = data[i].getName();
					totalNumberOfRepeats++;
					currentlyRepeating = true; //we've now seen the number more than once
				}
			}
			else //we've seen this number more than once
			{
				//System.out.println("CAN I GET IN HERE?");
				//we've seen the number yet again
				if(i+1 < data.length && data[i].getPhone().equals(data[i+1].getPhone()))
				{
					numberOfRepeats[repeatedPhoneNumbersCount]++; //seen it one more time
					//add name to list
					victimNames[totalNumberOfRepeats] = data[i].getName();
					totalNumberOfRepeats++;
				}
				else //the number has stopped repeating
				{
					repeatedPhoneNumbersCount++;
					//number is no longer occuring in the list
					currentlyRepeating = false;
					//add name to list
					victimNames[totalNumberOfRepeats] = data[i].getName();
					totalNumberOfRepeats++;
				}
			}
		}
		
		//alert profile
		
		
		//user needs to choose description in alert profile log, get the corresponding AP ID, 
		//pass it as parameter in getAlertProfile
		
		//get alert profile logs
		AlertProfileLog[] alertProfileLog = dbConnector.getAlertProfileLog();
		//get choosen alert profile log description
		String choosenDescription = cmbSelectAlertProfileLog.getSelectedItem().toString();
		//find correct log
		int alertProfileLogLocation = 0;
		for(alertProfileLogLocation = 0; alertProfileLogLocation < alertProfileLog.length; alertProfileLogLocation++)
		{
			if(choosenDescription.equals(alertProfileLog[alertProfileLogLocation].getDescription()))
				break;
		}
		//get ID of the desired log
		int alertProfileID = alertProfileLog[alertProfileLogLocation].getID();
		
		
		
		//update the choosen profile
		theProfile = getAlertProfile(alertProfileID);
		
		int[] theThresholds = theProfile.getThresholds(); //get the thresholds
		
		int numberOfAlerts = 0; //to know how big to make the alert Array
		//determine how many alerts we need to make
		for(int i = 0; i <data.length && numberOfRepeats[i] != 0; i++)
		{
			if(numberOfRepeats[i] >= theThresholds[4]) //if bigger than smallest risk
				numberOfAlerts++;
		}
		
		//stores the alerts
		if(numberOfAlerts > 0)
		{
			Alert[] alerts = new Alert[numberOfAlerts];
			//number of alerts
			int alertCounter = 0;
			//keeps track of where we are in name array
			int locationInNameArray = 0;
			//make all the alerts
			for(int i = 0; i <data.length && numberOfRepeats[i] != 0; i++)
			{
				
				if(numberOfRepeats[i] >= theThresholds[4]) //if bigger than smallest risk
				{
					
					//determine the alert color
					String alertColor = "";
					if(numberOfRepeats[i] >= theThresholds[0])
						alertColor = "Red";
					else if(numberOfRepeats[i] >= theThresholds[1])
						alertColor = "Orange";
					else if(numberOfRepeats[i] >= theThresholds[2])
						alertColor = "Yellow";
					else if(numberOfRepeats[i] >= theThresholds[3])
						alertColor = "Blue";
					else
						alertColor = "Green";
					
					//get the names for this phone number
					String[] victimNamesForAlert = new String[numberOfRepeats[i]];
					for(int j = 0; j < victimNamesForAlert.length; j++)
					{
						victimNamesForAlert[j] = victimNames[locationInNameArray];
						locationInNameArray++;
					}
					
					System.out.println("The phone number is: " + repeatedPhoneNumbers[i]);
					System.out.println("The alert color is: " + alertColor);
					System.out.println("The victim names are:");
					for(int j = 0; j < victimNamesForAlert.length; j++)
					{
						System.out.println(victimNamesForAlert[j]);
					}
					
					//create the alert
					AlertLog al = new AlertLog(Integer.parseInt(cmbSelectData.getSelectedItem().toString()), alertProfileID);
					Alert tempAlert = new Alert(alertColor, 
							Long.toString(repeatedPhoneNumbers[i]), victimNamesForAlert, al);
					
					alerts[alertCounter] = tempAlert;
					alertCounter++;
				}
			}
			if(uploadAlert(alerts))
			{
				//display upload successful
				System.out.println("Alerts uploaded successfully.");
			}
			else
			{
				//display upload unsuccessful
				System.out.println("Alerts not uploaded successfully.");
			}
		}
		else
		{
			//show screen that says no alert was generated
			System.out.println("No alert generated.");
		}
		
		//test comments to print out all of the repeating phone numbers 
		//and how many times it appeared
		//System.out.println("phone numbers that appeared more than once: " + repeatedPhoneNumbersCount);
		//System.out.println("Repeated phone numbers:");
		/*for(int i = 0; i < data.length && numberOfRepeats[i] != 0; i++)
		{
			//create alerts
			System.out.println(repeatedPhoneNumbers[i] + " appeared " + numberOfRepeats[i] + " times.");
		}*/
		
	}

	/**
	 * This method uploads an Array of alerts to the database
	 * @param alertArray-The array of alerts to be uploaded
	 */
	public boolean uploadAlert(Alert[] alertArray)
	{
		return dbConnector.uploadAlert(alertArray);
	}

	/**
	 * This method retrieves the alert profile from the database of the alert of the ID given
	 * @param alertProfileID: The ID of which alert profile to retrieve from the database
	 * @return: The alert profile retrieved from the database
	 */
	public AlertProfile getAlertProfile(int alertProfileID)
	{
		theProfile = dbConnector.getAlertProfile(alertProfileID);
		return theProfile;
	}
	
	/**
	 * Retrieves an array of alert profile logs from the database
	 * @return- array of alert profile logs
	 */
	public AlertProfileLog[] getAlertProfileLog()
	{
		return dbConnector.getAlertProfileLog();
	}
	
	/**
	 * Gets the data from the database with the ID given
	 * @param dataLogID- The ID of which data to retrieve from the database
	 * @return- The data retrieved from the database
	 */
	public Data[] getData(int dataLogID)
	{
		data = dbConnector.getAnalysis(dataLogID);
		return data;
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHtdstoolsAnalyzer = new JFrame();
		frmHtdstoolsAnalyzer.setTitle("HTDSTools: Analyzer");
		frmHtdstoolsAnalyzer.getContentPane().setBackground(Color.WHITE);
		frmHtdstoolsAnalyzer.setBounds(100, 100, 450, 300);
		frmHtdstoolsAnalyzer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHtdstoolsAnalyzer.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "analyserpic.png"));
		lblNewLabel.setBounds(6, 9, 88, 57);
		frmHtdstoolsAnalyzer.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("HTDS ANALYZER");
		lblNewLabel_1.setForeground(new Color(220, 20, 60));
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblNewLabel_1.setBounds(106, 12, 226, 42);
		frmHtdstoolsAnalyzer.getContentPane().add(lblNewLabel_1);
		
		JLabel lblSelectData = new JLabel("Select Data");
		lblSelectData.setForeground(new Color(30, 144, 255));
		lblSelectData.setBounds(26, 97, 75, 16);
		frmHtdstoolsAnalyzer.getContentPane().add(lblSelectData);
		
		//** combo box to select DataID **//
		cmbSelectData = new JComboBox(dataIDString);
		cmbSelectData.setBounds(26, 117, 187, 27);
		frmHtdstoolsAnalyzer.getContentPane().add(cmbSelectData);
		
		JLabel lblSelectAlertProfileLog = new JLabel("Select Alert Profile Log");
		lblSelectAlertProfileLog.setForeground(new Color(30, 144, 255));
		lblSelectAlertProfileLog.setBounds(225, 97, 75, 16);
		frmHtdstoolsAnalyzer.getContentPane().add(lblSelectAlertProfileLog);
		
		//** combo box to select DataID **//
		cmbSelectAlertProfileLog = new JComboBox(alertProfileLogString);
		cmbSelectAlertProfileLog.setBounds(225, 117, 187, 27);
		frmHtdstoolsAnalyzer.getContentPane().add(cmbSelectAlertProfileLog);
		
		//** Button analyze **//
		btnAnalyze = new JButton("Analyze");
		btnAnalyze.setBounds(94, 182, 117, 29);
		frmHtdstoolsAnalyzer.getContentPane().add(btnAnalyze);
		btnAnalyze.addActionListener(this);
	}

	//HELPER METHODS TO SORT

	//private long[] sortNumbers;
	private Data[] sortHelper;
	

	private void sort() {
		sortHelper = new Data[data.length];
		mergesort(0, data.length - 1);
		
		
		//code for testing to make sure it works
		/*for(int i = 0; i < data.length; i++) 
		{
			System.out.println(data[i].getVictimName());
			System.out.println(data[i].getPhone());
			System.out.println(i);
		}*/
			
	}

	private void merge(int low, int middle, int high) {

		for(int i = 0; i < data.length; i++)
		{
			sortHelper[i] = data[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;
		// Copy the smallest values from either the left or the right side back
		// to the original array
		while (i <= middle && j <= high) {
			if (Long.parseLong(sortHelper[i].getPhone()) <= Long.parseLong(sortHelper[j].getPhone())) {
				data[k]= sortHelper[i];
				i++;
			} else {
				data[k] = sortHelper[j];
				j++;
			}
			k++;
		}
		// Copy the rest of the left side of the array into the target array
		while (i <= middle) {
			data[k] = sortHelper[i];
			k++;
			i++;
		}
	}
	
	private void mergesort(int low, int high) {
		// check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			int middle = low + (high - low) / 2;
			// Sort the left side of the array
			mergesort(low, middle);
			// Sort the right side of the array
			mergesort(middle + 1, high);
			// Combine them both
			merge(low, middle, high);
		}
	}


}
