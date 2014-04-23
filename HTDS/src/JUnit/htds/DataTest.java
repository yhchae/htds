package JUnit.htds;

import static org.junit.Assert.*;
import htds.Data;

import org.junit.Test;

public class DataTest {

	Data data = new Data(100);
	
	@Test
	public void testGetDataID() {
		assertTrue(data.getDataID() == 100);
	}

	@Test
	public void testGetVictimName() {
		String victimName = "UnitTest";
		data.setV_Name(victimName);
		assertTrue(data.getVictimName().equals(victimName));
	}

	@Test
	public void testGetPhone() {
		String phone= "3333333333";
		data.setPhone(phone);
	}

	@Test
	public void testGetDataLog() {
		fail("Not yet implemented");
	}

}
