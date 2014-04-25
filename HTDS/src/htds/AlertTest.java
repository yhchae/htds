package htds;

import java.util.Arrays;
/**
 * The AlertTest class is used for exhaustive testing of the following classes:
 * 1- Alert
 * 2- AlertLog
 * 3- AlertProfile
 * 4- AlertProfileLog
 * @author Qutaiba
 *
 */
public class AlertTest {
	
//---------------------------------------------------------------------------
	/**
	 * Tests the AlertProfileLog class
	 */
	public void testAletProfileLogClass(){
		System.out.println("######## Start Testing AlertProfileLog Class ########");
		System.out.println();
		
		System.out.println("----- Test Constructor 1 -------");
		AlertProfileLog log1 = new AlertProfileLog(AlertRandom.randomID(), AlertRandom.randomID(), 
				"RandomProfile",AlertRandom.randomDate());
		log1.print();
		System.out.println();
		
		System.out.println("----- Test Constructor 2 -------");
		AlertProfileLog log2 = AlertRandom.randomAlertProfileLog();
		log2.print();
		System.out.println("The next AlertProfileLog should be identical to the above");
		AlertProfileLog log3 = new AlertProfileLog(log2);
		log3.print();
		System.out.println();
		
		System.out.println("----- Test Constructor 3 -------");
		System.out.println("Not implemented Yet, Needs AlertRandom.randomUser()");
		System.out.println();
		
		System.out.println("----- Test the Private Setters -------");
		
		System.out.println("Attempt creating an AlertProfileLog with an ID = -5 ");
		AlertProfileLog log4 = new AlertProfileLog(-5 , AlertRandom.randomID(),
				"RandomProfile", AlertRandom.randomDate());
		log4.print();
		System.out.println();
		
		System.out.println("Attempt creating an AlertProfileLog with a userID = -5 ");
		AlertProfileLog log5 = new AlertProfileLog(AlertRandom.randomID(),-5,
				"RandomProfile", AlertRandom.randomDate());
		log5.print();
		System.out.println();
		
		System.out.println("Attempt creating an AlertProfileLog with a null Date ");
		AlertProfileLog log6 = new AlertProfileLog(AlertRandom.randomID(),AlertRandom.randomID(),
				"RandomProfile", null);
		System.out.println();
		
		System.out.println("----- Test the Getters -------");
		AlertProfileLog log7 = AlertRandom.randomAlertProfileLog();
		log7.print();
		System.out.println("The following getter outputs should match the above details");
		System.out.println("AlertProfileLog ID = "+log7.getID());
		System.out.println("User ID = "+log7.getUserID());
		System.out.println("Description: "+log7.getDescription());
		System.out.println("Date = "+ log7.getDate().toString());
		
		System.out.println("######## End Testing AlertProfileLog Class ########");
	}
	//--------------------------------------------------------------------------
	/**
	 * Tests the AlertProfile Class
	 */
	public void testAletProfileClass(){
		System.out.println("######## Start Testing AlertProfile Class ########");
		System.out.println();
		
		System.out.println("----- Test Constructor 1 -------");
		AlertProfile profile1 = new AlertProfile(AlertRandom.randomID(), AlertRandom.randomThresholds(), 
				"RandomProfile",AlertRandom.randomAlertProfileLog());
		profile1.print();
		System.out.println();
		
		System.out.println("----- Test Constructor 2 -------");
		AlertProfile profile2 = AlertRandom.randomAlertProfile();
		profile2.print();
		System.out.println("The next AlertProfile should be identical to the above");
		AlertProfile profile3 = new AlertProfile(profile2);
		profile3.print();
		System.out.println();
		
		System.out.println("----- Test the Private Setters -------");
		
		System.out.println("Attempt creating an AlertProfile with an ID = -5 ");
		AlertProfile profile4 = new AlertProfile(-5 , AlertRandom.randomThresholds(),
				"RandomProfile", AlertRandom.randomAlertProfileLog());
		profile4.print();
		System.out.println();
		
		System.out.println("Attempt creating an AlertProfileLog with thresholds array less than 5 ");
		AlertProfile profile5 = new AlertProfile(AlertRandom.randomID(),new int[]{10,20,30},
				"RandomProfile", AlertRandom.randomAlertProfileLog());
		System.out.println();
		
		System.out.println("Attempt creating an AlertProfileLog with thresholds of negative values ");
		AlertProfile profile6 = new AlertProfile(AlertRandom.randomID(),new int[]{-1, -5, -6, -7, -8},
				"RandomProfile", AlertRandom.randomAlertProfileLog());
		System.out.println();
		
		System.out.println("Attempt creating an AlertProfileLog with a null Date ");
		AlertProfile profile7 = new AlertProfile(AlertRandom.randomID() , AlertRandom.randomThresholds(),
				"RandomProfile", null);
		System.out.println();
		System.out.println("######## End Testing AlertProfile Class ########");

	}
	//---------------------------------------------------------------------------
	/**
	 * Tests the AlertLog Class
	 */
	public void testAlertLogClass(){
		System.out.println("######## Start Testing AlertLog Class ########");
		System.out.println();
		
		System.out.println("----- Test Constructor 1 -------");
		AlertLog log1 = new AlertLog(AlertRandom.randomID(), AlertRandom.randomID(), AlertRandom.randomID(),
				AlertRandom.randomDate(), AlertRandom.randomID());
		log1.print();
		System.out.println();
		
		System.out.println("----- Test Constructor 2 -------");
		System.out.println("Not implemented Yet, Needs AlertRandom.randomAlertProfile()");
		System.out.println();
		
		System.out.println("----- Test Constructor 3 -------");
		AlertLog log2 = AlertRandom.randomAlertLog();
		log2.print();
		System.out.println("The next AlertLog should be identical to the above");
		AlertLog log3 = new AlertLog(log2);
		log3.print();
		System.out.println();
		
		System.out.println("----- Test Constructor 4 -------");
		System.out.println("Not implemented Yet");
		System.out.println();
		
		System.out.println("----- Test the Private Setters -------");
		
		System.out.println("Attempt creating an AlertLog with an ID = -5 ");
		AlertLog log4 = new AlertLog(-5 , AlertRandom.randomID(), AlertRandom.randomID(),
				AlertRandom.randomDate(), AlertRandom.randomID());
		log4.print();
		System.out.println();
		
		System.out.println("Attempt creating an AlertLog with a userID  = -5 ");
		AlertLog log5 = new AlertLog(AlertRandom.randomID() , -5 , AlertRandom.randomID(),
				AlertRandom.randomDate(), AlertRandom.randomID());
		log5.print();
		
		System.out.println();
		System.out.println("Attempt creating an AlertLog with a dataLogID  = -5 ");
		AlertLog log6 = new AlertLog(AlertRandom.randomID() , AlertRandom.randomID() , -5 ,
				AlertRandom.randomDate(), AlertRandom.randomID());
		log6.print();
		System.out.println();
		
		System.out.println("Attempt creating an AlertLog with a null Date ");
		AlertLog log7 = new AlertLog(AlertRandom.randomID() , AlertRandom.randomID() , AlertRandom.randomID() ,
				null, AlertRandom.randomID());
		System.out.println();
		
		System.out.println("Attempt creating an AlertLog with an alertProfileID = -5 ");
		AlertLog log8 = new AlertLog(AlertRandom.randomID() , AlertRandom.randomID() , AlertRandom.randomID() ,
				AlertRandom.randomDate(), -5 );
		log8.print();
		System.out.println();
		
		System.out.println("----- Test the Getters -------");
		AlertLog log9 = AlertRandom.randomAlertLog();
		log9.print();
		System.out.println("The following getter outputs should match the above details");
		System.out.println("AlertLog ID = "+log9.getID());
		System.out.println("User ID = "+log9.getUserID());
		System.out.println("DataLog ID "+ log9.getDataLogID());
		System.out.println("Date = "+ log9.getDate().toString());
		System.out.println("AlertProfile ID = "+ log9.getAlertProfileID());
		
		System.out.println("######## End Testing AlertLog Class ########");
	}
	//---------------------------------------------------------------------------	
	/**
	 * Tests the Alert class
	 */
	public void testAlertClass(){
		System.out.println("######## Start Testing Alert Class ########");
		System.out.println();
		
		System.out.println("----- Test Constructor 1 -------");
		Alert alert1 = new Alert(AlertRandom.randomID(),AlertRandom.randomColor(),AlertRandom.randomPhone(), 
				AlertRandom.randomAlertLog(), AlertRandom.randomStringArray(5));
		alert1.print();
		System.out.println();
		
		System.out.println("----- Test Constructor 2 -------");
		Alert alert2 = new Alert(alert1);
		System.out.println("This is copy of the previous Alert");
		alert2.print();
		System.out.println();
		
		System.out.println("----- Test the Private Setters -------");
		
		System.out.println("Attempt creating an Alert with an ID = -5 ");
		Alert alert3 = new Alert(-5,AlertRandom.randomColor(),AlertRandom.randomPhone(), 
				AlertRandom.randomAlertLog(), AlertRandom.randomStringArray(5));
		alert3.print();
		System.out.println();
		
		System.out.println("Attempt creating an Alert with an invalid color - black ");
		Alert alert4 = new Alert(AlertRandom.randomID(),"Black",AlertRandom.randomPhone(), 
				AlertRandom.randomAlertLog(), AlertRandom.randomStringArray(5));
		alert4.print();
		System.out.println();
		
		System.out.println("Attempt creating an Alert with a phone number containing formatting characters - the output should remove all non digit characters ");
		Alert alert5 = new Alert(AlertRandom.randomID(),AlertRandom.randomColor(),"(333)-222,1111", 
				AlertRandom.randomAlertLog(), AlertRandom.randomStringArray(5));
		alert5.print();
		System.out.println();
		
		//System.out.println("Attempt creating an Alert with empty victim name list ");
		//Alert alert6 = new Alert(AlertRandom.randomID(),AlertRandom.randomColor(),AlertRandom.randomPhone(), 
		//		AlertRandom.randomAlertLog(), null);
		//System.out.println();
		
		System.out.println("Attempt creating an Alert with an AlertLog = null ");
		Alert alert7 = new Alert(AlertRandom.randomID(),AlertRandom.randomColor(),AlertRandom.randomPhone(), 
				null , AlertRandom.randomStringArray(5));
		System.out.println();
			
		System.out.println("----- Test Getters -------");
		Alert alert8 = AlertRandom.randomAlert();
		alert8.print();
		System.out.println("The following getter outputs should match the above details");
		System.out.println("Alert ID = "+alert8.getID());
		System.out.println("Alert Color = "+ alert8.getColor());
		System.out.println("Suspect Phone = "+alert8.getSPN());
		System.out.println("Victim Names: ");
		System.out.println(Arrays.toString(alert8.getVictimNames()));
		System.out.println("AlertLog");
		alert8.getAlertLog().print();
		

		System.out.println();
		System.out.println("######## End Testing Alert Class ########");
	}
	//---------------------------------------------------------------------------
	/**
	 * Main function to executes all testing methods in AlertTest class
	 * @param args: to be left blank
	 */
	public static void main(String [ ] args){
		AlertTest test = new AlertTest();
		test.testAletProfileLogClass();
		test.testAletProfileClass();
		test.testAlertLogClass();
		test.testAlertClass();
	}
}
