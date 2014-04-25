package htds;
import htds.HTDSEngine;

/**
 * The HTDS class is the highest level class that executes the HTDSEngine
 * It has a single method which creates and executes an HTDSEngine module
 * @author Qutaiba
 *
 */
public class HTDS {

	/**
	 * The main function creates an executes an HTDSEngine object
	 * @param args: no arguments need to be passed
	 */
	public static void main(String[] args){
		HTDSEngine engine = new HTDSEngine();
		engine.setVisible(true);
	}
}
