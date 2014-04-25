package JUnit.htds;

import static org.junit.Assert.*;
import htds.Alert;
import htds.AlertLog;

import org.junit.Test;

public class AlertTest {
	
	private Alert alert = new Alert(100);
	
	@Test
	public void testGetAlertID(){
		assertTrue(alert.getAlertID() == 100);
	}
	
	@Test
	public void testGetColor(){
		alert.setColor("Red");
		assertTrue(alert.getColor().equals("Red"));
	}
	
	@Test
	public void testGetFrequency(){
		alert.setFrequency(100);
		assertTrue(alert.getFrequency() == 100);
	}
	
	@Test
	public void testGetSPN(){
		alert.setSPN("UnitTest");
		assertTrue(alert.getSPN().equals("UnitTest"));
	}
	
	@Test
	public void testGetAlertLog(){
		AlertLog alertLog = new AlertLog(100);
		alert.setAlertLog(alertLog);
		assertTrue(alert.getAlertLog().getAlertLogID() == 100);
	}
	
	@Test
	public void testGetVictimNames(){
		String[] string = {"UnitTest1", "UnitTest2", "UnitTest3"};
		alert.setVictimName(string);
		assertTrue(alert.getVictimNames().equals(string));
	}
}
