package htds;

import java.sql.Date;
import java.util.Arrays;
import java.util.Random;

/**
 * This class generates random data structures for testing purposes
 * The following structures are supported:
 * 1- Random ID number
 * 2- Random Phone number
 * 3- Random Color
 * 4- Random Date
 * 5- Random String
 * 6- Random String Array
 * 7- Random Alert
 * 8- Random AlertLog
 * @author Qutaiba
 *
 */
public class AlertRandom {
		
	public static int randomID(){
		return 0 + (int)(Math.random()*Integer.MAX_VALUE);
	}
	
	public static String randomPhone(){
		int randDigit;
		String phone = "";
		for (int i=0;i<9;i++){
			randDigit = 0 + (int)(Math.random()*9);
			phone = phone + Integer.toString(randDigit);
		}
		return phone;
	}
	
	public static String randomColor(){
		int randIndex = 0 + (int)(Math.random()*4);
		return AlertProfile.getColors()[randIndex];
	}
	
	public static Date randomDate(){
		return new Date(randomID());
	}
	
	public static char randomCharacter(){
		Random r = new Random();
		char c = (char)(r.nextInt(26) + 'a');
		return c;
	}
	
	public static String randomString(){
		String name = "";
		int StringLength = (3 + (int)(Math.random()*8));
		for (int i = 0; i<= StringLength;i++)
			name = name + Character.toString(randomCharacter());
		return name;
	}
	
	public static String[] randomStringArray(int size){
		String[] randomStringArray = new String[size];
		for (int i=0;i<size;i++)
			randomStringArray[i] = randomString();
		return randomStringArray;
	}
	
	public static int[] randomThresholds(){
		int[] randomThresholdsArray = new int[5];
		randomThresholdsArray[4] = 1 + (int)(Math.random()*3);
		randomThresholdsArray[3] = 4 + (int)(Math.random()*5);
		randomThresholdsArray[2] = 5 + (int)(Math.random()*7);
		randomThresholdsArray[1] = 7 + (int)(Math.random()*9);
		randomThresholdsArray[0] = 9 + (int)(Math.random()*12);
		return randomThresholdsArray;
	}
	
	public static AlertLog randomAlertLog(){
		return new AlertLog(randomID(), randomID(), randomID(), randomDate(), randomID());
	}
	
	/**
	 * @return an object of type Alert with random properties
	 * This function should be used for testing purposes only
	 */
	public static Alert randomAlert(){
		return new Alert(randomID(), randomColor(), randomPhone(), randomAlertLog(), randomStringArray(5));
	}
	
	/**
	 * @return an object of type AlertProfileLog with random properties
	 * This function should be used for testing purposes only
	 */
	public static AlertProfileLog randomAlertProfileLog(){
		return new AlertProfileLog(randomID(), randomID(), "RandomProfile", randomDate());
	}
	
	public static AlertProfile randomAlertProfile(){
		return new AlertProfile(randomID(),randomThresholds(), "Random", randomAlertProfileLog());
	}
	
	public static void main(String [ ] args){
		System.out.println("Random Character "+ AlertRandom.randomCharacter()); System.out.println();
		System.out.println("Random ID = "+ AlertRandom.randomID()); System.out.println();
		System.out.println("Random Color = "+ AlertRandom.randomColor()); System.out.println();
		System.out.println("Random Phone " + AlertRandom.randomPhone()); System.out.println();
		System.out.println("Random String "+ AlertRandom.randomString()); System.out.println();
		System.out.println("Random String Array "+ Arrays.toString(AlertRandom.randomStringArray(5))); System.out.println();
		System.out.println("Random Date "+ AlertRandom.randomDate().toString()); System.out.println();
		System.out.println("Random AlertLog "); 
		AlertRandom.randomAlertLog().print(); System.out.println();
		System.out.println("Random Alert");
		AlertRandom.randomAlert().print(); System.out.println();
		System.out.println("Random AlertProfileLog");
		AlertRandom.randomAlertProfileLog().print(); System.out.println();
		System.out.println("Random AlertProfile");
		AlertRandom.randomAlertProfile().print(); System.out.println();
	}
}
