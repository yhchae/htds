package htds;


/**
 * This class analyzes the data by finding the repeating phone numbers, creating a 
 *	corresponding alert, and uploading all the alerts to the alert database.
 * @author Zachary Oliveira
 *	NOTE: WHEN FINAL VERSION IS READY, COMMENT OUT THE HARD-CODED VALUES OF THRESHOLDS FOR ALERTS
 *		  AND UNCOMMENT THE ADDING OF THE ALERTS TO THE ALERT ARRAY, AND UPLOADING THE ALERT 
 *		  ARRAY TO THE DATABASE
 */
public class Analyzer extends HTDSModule {
	private static final long serialVersionUID = 1L;
	
	private DBConnector connector = new DBConnector(); /** HTDS connection to the data base. */
	/** An array of information uploaded by the agent.
	 * Each entry in the array holds phone numbers, victim names, id, log id, and a frequency.*/
	private Data[] data;
	/** Contains data that determines the parameters of the alerts we create based on the number of repeats in our data */
	private AlertProfile theProfile;

	/**
	 * Basic constructor.
	 */
	public Analyzer(){
		super("HTDS Analyzer");
		setLocation(20, 20);		// This is an example code.
	}

	/**
	 * The method first sorts the data by putting the phone numbers in ascending order.
	 * Then, we go through the sorted list to see if there are duplicate phone numbers 
	 * (all duplicates will be "side-by-side" in the array).
	 * Then we go through all the duplicate phone numbers and create alerts for each one
	 * that has more appearances than the minimal amount as listed in the alert profile,
	 * and upload the alerts to the database.
	 * @param dataArray- the data to be analyzed
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
		
		
		//find number of alerts
		//int[] theThresholds = theProfile.getThresholds(); //get the thresholds
		//JUST FOR TESTING. USE ABOVE LINE NORMALLY^^^^^^
		int[] theThresholds = {6,5,4,3,2};
		//JUST FOR TESTING
		
		int numberOfAlerts = 0; //to know how big to make the alert Array
		//determine how many alerts we need to make
		for(int i = 0; i <data.length && numberOfRepeats[i] != 0; i++)
		{
			if(numberOfRepeats[i] >= theThresholds[4]) //if bigger than smallest risk
				numberOfAlerts++;
		}
		
		//stores the alerts
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
				Alert tempAlert = new Alert(alertColor, 
						Long.toString(repeatedPhoneNumbers[i]), victimNames);
				
				alerts[alertCounter] = tempAlert;
				alertCounter++;
			}
			uploadAlert(alerts);
		}
		
		//test comments to print out all of the repeating phone numbers 
		//and how many times it appeared
		/*System.out.println("phone numbers that appeared more than once: " + repeatedPhoneNumbersCount);
		System.out.println("Repeated phone numbers:");
		for(int i = 0; i < data.length && numberOfRepeats[i] != 0; i++)
		{
			//create alerts
			System.out.println(repeatedPhoneNumbers[i] + " appeared " + numberOfRepeats[i] + " times.");
		}*/
		
	}

	/**
	 * This method uploads an Array of alerts to the database
	 * @param alertArray-The array of alerts to be uploaded
	 */
	public void uploadAlert(Alert[] alertArray)
	{
		connector.uploadAlert(alertArray);
	}

	/**
	 * This method retrieves the alert profile from the database of the alert of the ID given
	 * @param alertProfileID: The ID of which alert profile to retrieve from the database
	 * @return: The alert profile retrieved from the database
	 */
	public AlertProfile getAlertProfile(int alertProfileID)
	{
		theProfile = connector.getAlertProfile(alertProfileID);
		return theProfile;
	}
	
	/**
	 * Gets the data from the database with the ID given
	 * @param dataLogID- The ID of which data to retrieve from the database
	 * @return- The data retrieved from the database
	 */
	public Data[] getData(int dataLogID)
	{
		data = connector.getAnalysis(dataLogID);
		return data;
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
