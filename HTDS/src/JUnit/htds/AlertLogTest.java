package JUnit.htds;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;

import htds.AlertLog;

import org.junit.Test;

public class AlertLogTest {

	private AlertLog alertLog = new AlertLog(100);
	
	@Test
	public void testGetAlertLogID() {
		assertTrue(alertLog.getAlertLogID() == 100);
	}

	@Test
	public void testGetUserID() {
		alertLog.setUserID(100);
		assertTrue(alertLog.getUserID() == 100);
	}

	@Test
	public void testGetDataLogID() {
		alertLog.setDataLogID(1000);
		assertTrue(alertLog.getDataLogID() == 1000);
	}

	@Test
	public void testGetAlertProfileLogID() {
		alertLog.setAlertProfileLogID(10000);
		assertTrue(alertLog.getAlertProfileLogID() == 10000);
	}

	@Test
	public void testGetDate() {
		Calendar cal = java.util.Calendar.getInstance();
		Date date = new Date(cal.getTime().getTime());
		alertLog.setDate(date);
		assertTrue(alertLog.getDate().toString().equals(date.toString()));
	}

}
