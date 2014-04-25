package htds;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;

/**
 * The HTDSModule is a parent class inherited by the four HTDS Modules: Viewer, Uploader, Analyzer and ConfigMgr
 * It defines utilities/methods shared by these four modules
 * @author Young
 *
 */
public class HTDSModule extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String moduleName; 
	
	public HTDSModule(String moduleName){
		super(moduleName, 
				true,		// resizable 
				true, 		// closable
				true, 		// maximizable
				true);		// iconifiable
		this.moduleName = moduleName;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width - 30, screenSize.height - 100);
	}
	
	public String getModuleName(){
		return this.moduleName;
	}
}
