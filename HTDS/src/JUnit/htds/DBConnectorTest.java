package JUnit.htds;

import static org.junit.Assert.*;
import htds.Alert;
import htds.AlertLog;
import htds.AlertProfile;
import htds.DBConnector;
import htds.Data;
import htds.User;
import htds.UserProfile;

import java.sql.Date;

import org.junit.Test;

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
		
		assertTrue(dbConnector.removeUser(user));
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
	public void testUploadAlert(){
		@SuppressWarnings("deprecation")
		AlertLog al = new AlertLog(2, 2, 1, new Date(114,9,2), 1);
		Alert[] alerts = new Alert[2];
		alerts[0] = new Alert(1, "Red", 100, "333585677", al, new String[]{"b"});
		alerts[1] = new Alert(2, "Blue", 50, "250452456", al, new String[]{"a"});
		assertTrue(dbConnector.uploadAlert(alerts));
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
	
	@Test
	public void testUpdateUserPassword(){
		User user = new User();
		user.setFullname("Administrator");
		String password = "htds2014";
		assertTrue(dbConnector.updateUserPassword(user, password));
	}
	
	@Test
	public void testUpdateUserProfile(){
		User user = new User();
		user.setFullname("Administrator");
		
		UserProfile userProfile1 = new UserProfile();
		userProfile1.setProfileID(2);
		
		UserProfile userProfile2 = new UserProfile();
		userProfile2.setProfileID(1);
		
		boolean test1 = dbConnector.updateUserProfile(user, userProfile1);
		boolean test2 = dbConnector.updateUserProfile(user, userProfile2); 
		assertTrue( test1 && test2);
	}
	
	@Test
	public void testGetData(){
		Data[] test1 = dbConnector.getData();
		assertNotNull(test1);
		
		Data data = new Data();
		data.setPhone("578229221");
		Data[] test2 = dbConnector.getData(data);
		assertNotNull(test2);
		
		int dataLogID = 2;
		Data[] test3 = dbConnector.getData(dataLogID);
		assertNotNull(test3);
		
		User user = new User();
		user.setUsername("admin");
		Data[] test4 = dbConnector.getData(user);
		assertNotNull(test4);
		
		Data[] test5 = dbConnector.getData("Genevre Lalonde", DBConnector.VICTIM_NAME);
		assertNotNull(test5);
		
		Data[] test6 = dbConnector.getData("56135060", DBConnector.PHONE_NUMBER);
		assertNotNull(test6);
	}
	
	@Test
	public void testGetAlerts(){
		Alert[] test1 = dbConnector.getAlerts();
		assertNotNull(test1);
		
		@SuppressWarnings("deprecation")
		Alert[] test2 = dbConnector.getAlerts(new Date(114,3,22), new Date(114,3,24));
		assertNotNull(test2);
		
		User user = new User();
		user.setUsername("agent");
		Alert[] test3 = dbConnector.getAlerts(user);
		assertNotNull(test3);
		
		int alertProfileID = 1;
		Alert[] test4 = dbConnector.getAlerts(alertProfileID);
		assertNotNull(test4);
		
		Alert[] test5 = dbConnector.getAlerts("Jeanette Grignon", DBConnector.VICTIM_NAME);
		assertNotNull(test5);
		
		Alert[] test6 = dbConnector.getAlerts("250452456", DBConnector.PHONE_NUMBER);
		assertNotNull(test6);
		
		Alert[] test7 = dbConnector.getAlerts("Red", DBConnector.COLOR);
		assertNotNull(test7);
	}
}
