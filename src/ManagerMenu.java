import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This creates a manager menu so that the manager can access stuff
 * @author Kurt Anderson
 * @version 1.0
 */

public class ManagerMenu extends Menu implements ActionListener{

	
	private JButton mMemberReportBttn, mSummaryReportBttn, mProviderReportBttn, mAllReportsBttn;
	private MainMenu mMainMenu;
	
	/**
	 * The constructor for the manager menu, creates and initializes the menu
	 * @param name - The name provided through the main menu to display in dialog title
	 * @param mainMenu - The main menu object, creating a menu tree to allow a return statement. 
	 */
	
	public ManagerMenu(String name, MainMenu mainMenu) {
		super.Menu("Manager Menu for: " + name, "Welcome Manager",  mainMenu);
		
		mMainMenu = mainMenu;
		//Create the container to store the objects
		Container c = getContentPane();
		c.setLayout(null);
		
		//Create Grid Layout Variables
		int leftGridX = 80;
		int leftGridY = 100;
		int buttonGap = 200;
		
		mMemberReportBttn = new JButton("Create Member Report");
		mMemberReportBttn.addActionListener(this);
		mMemberReportBttn.setSize( 200, 100 );
		mMemberReportBttn.setLocation( leftGridX, leftGridY);
		c.add(mMemberReportBttn);
		
		mProviderReportBttn = new JButton("Create Provider Report");
		mProviderReportBttn.addActionListener(this);
		mProviderReportBttn.setSize( 200, 100 );
		mProviderReportBttn.setLocation( leftGridX, leftGridY + buttonGap);
		c.add(mProviderReportBttn);
		
		mSummaryReportBttn = new JButton("Create Summary Report");
		mSummaryReportBttn.addActionListener(this);
		mSummaryReportBttn.setSize( 200, 100 );
		mSummaryReportBttn.setLocation( leftGridX + 2*buttonGap, leftGridY);
		c.add(mSummaryReportBttn);
		
		mAllReportsBttn = new JButton("Create All Reports");
		mAllReportsBttn.addActionListener(this);
		mAllReportsBttn.setSize( 200, 100 );
		mAllReportsBttn.setLocation( leftGridX + 2*buttonGap, leftGridY + buttonGap);
		c.add(mAllReportsBttn);
			
		super.createMenu(); //Creates the menu using the interface
		
		
	}
	
	/**
	 * Creates the memberReprot through a call to chocAn
	 */
	
	public void createMemberReport() {
		mMainMenu.mChocAn.createMemberReports(0);
	}
	
	/**
	 * Creates the providerReport through a call to chocAn
	 */
	public void createProviderReport() {
		mMainMenu.mChocAn.createProviderReports(0);
	}
	
	/**
	 * Creates the providerReport through a call to chocAn
	 */
	
	public void createSummaryReport() {
		mMainMenu.mChocAn.createSummaryReport(0);
	}
	
	/**
	 * Creates all the reports by running the other reports
	 */
	public void createAllReports() {
		createMemberReport();
		createProviderReport();
		createSummaryReport();
	}

	/**
	 * This overrights the actionlistener, making sure that when buttons are clicked they work. 
	 */
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		super.actionPerformed(event); //Calls the super class for 
		
		
		if (event.getSource() == mMemberReportBttn) {
			createMemberReport();
		}
		
		else if (event.getSource() == mProviderReportBttn){
			createProviderReport();
		}
		
		else if (event.getSource() == mSummaryReportBttn) {
			createSummaryReport();
		}
		
		else if (event.getSource() == mAllReportsBttn) {
			createAllReports();
		}
	}
	
	
}