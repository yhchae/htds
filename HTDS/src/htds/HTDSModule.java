package htds;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class HTDSModule implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String moduleName; 
	
	public HTDSModule(String moduleName){
	}
	
	public String getModuleName(){
		return this.moduleName;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
