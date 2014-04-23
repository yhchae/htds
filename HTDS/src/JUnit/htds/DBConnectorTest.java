package JUnit.htds;

import static org.junit.Assert.*;
import htds.Alert;
import htds.AlertLog;
import htds.AlertProfile;
import htds.AlertProfileLog;
import htds.DBConnector;
import htds.User;
import htds.UserProfile;

import java.sql.Date;
import java.sql.ResultSet;

import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

public class DBConnectorTest {
	
	DBConnector dbConnector = new DBConnector();
	@Test
	public void testLogin(){
		User user = dbConnector.login("agent", "htds2014");
		assertNotNull(user);
	}
	
	@Test
	public void testAddUser(){
		boolean[] permission = {true, true, true, true};
		
		UserProfile up = new UserProfile();
		up.setProfileID(1);
		up.setPermissions(permission);
		up.setProfileName("admin");
		
		User user = new User();
		user.setFullname("unitTest");
		user.setPassword("unitTest");
		user.setUsername("unitTest");
		user.setUserProfile(up);
		
		assertTrue(dbConnector.addUser(user));
	}
	
	@Test
	public void testDeleteUser(){
		User user = new User();
		user.setFullname("unitTest");
		user.setPassword("unitTest");
		user.setUsername("unitTest");
		
		assertTrue(dbConnector.deleteUser(user));
	}
	
	@Test
	public void testUploadFile(){
		dbConnector.login("agent", "htds2014");
		String filename = "d:\\Studying\\workspace\\workspace\\HTDS\\test.txt";
		assertTrue(dbConnector.uploadFile(filename));
	}
	
	@Test
	public void testGetAnalysis(){
		assertNotNull(dbConnector.getAnalysis(1));
	}
	
	@Test
	public void testSetAlert(){
		@SuppressWarnings("deprecation")
		AlertLog al = new AlertLog(2, 2, 1, new Date(2014,10,2), 1);
		Alert[] alerts = new Alert[2];
		alerts[0] = new Alert(1, "Red", 100, "123456", al, new String[]{"b"});
		alerts[1] = new Alert(2, "Blue", 50, "654321", al, new String[]{"a"});
		assertTrue(dbConnector.setAlert(alerts));
	}
	
	@Test
	public void testCreateUserProfile(){
		UserProfile userProfile = new UserProfile();
		boolean[] permissions = {false, false, false, false};
		
		userProfile.setProfileName("UnitTest");
		userProfile.setPermissions(permissions);
		assertTrue(dbConnector.createUserProfile(userProfile));
	}
	
	@Test
	public void testCreateAlertProfile(){
		dbConnector.login("agent", "htds2014");
		int[] th = {50, 40, 30, 20, 10};
		AlertProfile alertProfile = new AlertProfile(0, th, "UnitTest", null);
		assertTrue(dbConnector.createAlertProfile(alertProfile));
	}
}
