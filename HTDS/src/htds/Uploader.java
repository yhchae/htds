package htds;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 * 
 * @author Zachary Oliveira
 *
 */
public class Uploader extends HTDSModule implements ActionListener{
	
	/**
	 * Create a file chooser which isused to choose which file the
	 * user would like to upload to the database
	 */
	private final JFileChooser fileChooser = new JFileChooser();

	/**
	 * Data base connector to connect us to the database
	 */
	private DBConnector dbConnector = new DBConnector();
	
	
	//GUI fields
	private JFrame frmHtdsToolsUploader;
	private JTextField textSelectFile;
	private JButton btnValidate;
	private JButton btnUpload;
	private JButton btnBrowse;
	
	
	/**
	 * Creates an uploader
	 */
	public Uploader(){
		super("HTDS Uploader");
		initialize();
		show();
	}
	
	/**
	 * Shows the window
	 */
	public void show()
	{
		frmHtdsToolsUploader.setVisible(true);
	}
	
	/**
	 * Hides the window
	 */
	public void hide()
	{
		frmHtdsToolsUploader.setVisible(false);
	}
	
	/**
	 * Determines if the given file is in a valid format or not
	 * @param filename- the file to be verified
	 * @return- true if the file is valid, false if it isn't
	 */
	public boolean isValidFile(String filename)
	{
		boolean validFile; //stores the validity of the file
		
		//try to parse the file
		try{
		    Scanner file = new Scanner(new File(filename));
		    while(file.hasNextLine()) //go through all lines of the file
		    {
		        String message = file.nextLine();
		        String store[] = message.split(","); //split the line at the comma
		    }
		    file.close(); //close the file
		    validFile = true; //we have successfully parsed the file
		}
		//error in the format of the file
		catch(Exception e)
		{
			validFile = false;
		}
		
		return validFile; //validity of file is returned
	}
	
	/**
	 * Attempts-
	 * @param filename- the file to attempt to upload
	 * @return- true if file was uploaded, false if it wasn't
	 */
	public boolean uploadFile(String filename)
	{
		boolean uploadStatus = dbConnector.uploadFile(filename);
		if(uploadStatus)
		{
			//display screen saying successful upload
		}
		else
		{
			//display screen saying failed upload
		}
		return uploadStatus;
	}
	
	/**
	 * Handles the actions when each button is clicked
	 */
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		//determine which button was clicked
		if(obj == btnValidate)
		{
			if(isValidFile(textSelectFile.getText()))
			{
				System.out.println("File is valid");
				//new screen that displays valid input
			}
			else
			{
				System.out.println("File is invalid");
				//new screen that displays invalid input
			}
		}
		else if(obj == btnUpload)
		{
			if(uploadFile(textSelectFile.getText()))
			{
				//new screen that displays successful upload
				System.out.println("Upload successful");
			}
			else
			{
				//new screen that displays unsuccessful upload
				System.out.println("Upload not successful");
			}
			
		}
		else if(obj == btnBrowse)
		{
			fileChooser.showOpenDialog(btnBrowse);
	        File file = fileChooser.getSelectedFile(); //get the file
	        textSelectFile.setText(file.getPath()); //show the path
		}
		
		
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHtdsToolsUploader = new JFrame();
		frmHtdsToolsUploader.setTitle("HTDS Tools: Uploader");
		frmHtdsToolsUploader.setBackground(Color.WHITE);
		frmHtdsToolsUploader.setBounds(100, 100, 450, 300);
		frmHtdsToolsUploader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHtdsToolsUploader.getContentPane().setLayout(null);
		
		/** text box to write or select file name **/
		textSelectFile = new JTextField();
		textSelectFile.setBounds(77, 84, 285, 28);
		frmHtdsToolsUploader.getContentPane().add(textSelectFile);
		textSelectFile.setColumns(10);
		
		JLabel lblSelectFile = new JLabel("Select File");
		lblSelectFile.setForeground(new Color(100, 149, 237));
		lblSelectFile.setBounds(10, 90, 71, 16);
		frmHtdsToolsUploader.getContentPane().add(lblSelectFile);
		
		/** need to check the compatibility of file **/
		btnValidate = new JButton("Validate");
		btnValidate.setBounds(102, 143, 117, 29);
		frmHtdsToolsUploader.getContentPane().add(btnValidate);
		btnValidate.addActionListener(this);
		
		/** need to connect to the database to save the file **/
		btnUpload = new JButton("Upload");
		btnUpload.setBounds(231, 143, 117, 29);
		frmHtdsToolsUploader.getContentPane().add(btnUpload);
		btnUpload.addActionListener(this);
		
		/** Browse button **/
		btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(361, 85, 71, 29);
		frmHtdsToolsUploader.getContentPane().add(btnBrowse);
		btnBrowse.addActionListener(this);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:" + File.separator + "Users" + File.separator + "Zachary" + File.separator + "Documents" + File.separator + "eclipse" + File.separator + "JavaFiles" + File.separator + "HTDS" + File.separator + "src" + File.separator + "htds" + File.separator + "image" + File.separator + "uploaderpic.png"));
		lblNewLabel.setBounds(6, 6, 70, 49);
		frmHtdsToolsUploader.getContentPane().add(lblNewLabel);
		
		JLabel lblHtdsUploader = new JLabel("HTDS Uploader");
		lblHtdsUploader.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblHtdsUploader.setForeground(new Color(220, 20, 60));
		lblHtdsUploader.setBounds(88, 18, 201, 29);
		frmHtdsToolsUploader.getContentPane().add(lblHtdsUploader);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(0, 84, 444, 1);
		frmHtdsToolsUploader.getContentPane().add(horizontalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setBounds(0, 67, 444, 16);
		frmHtdsToolsUploader.getContentPane().add(horizontalStrut_1);
	}
}
