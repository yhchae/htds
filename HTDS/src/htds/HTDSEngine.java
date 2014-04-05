package htds;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class HTDSEngine extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JDesktopPane desktop;
	
	public HTDSEngine(){
		super("Human Trafficking Detection System");
		 
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds(0, 0,
	              screenSize.width,
	              screenSize.height - 25);
	
	    //Set up the GUI.
	    desktop = new JDesktopPane(); //a specialized layered pane
	    setContentPane(desktop);
	    
	    //
	    // The examples start here
	    //
	    Viewer viewer = new Viewer();
	    Analyzer analyzer = new Analyzer();
	    Uploader uploader = new Uploader();
	    ConfigMgr configMgr = new ConfigMgr();
	    
	    createFrame(viewer);
	    createFrame(analyzer);
	    createFrame(uploader);
	    createFrame(configMgr);
	    //
	    // The examples end here
	    //
	    
	    //Make dragging a little faster but perhaps uglier.
	    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	}
	
	//Create a new internal frame.
    protected void createFrame(JInternalFrame frame) {
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
        	frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
}
