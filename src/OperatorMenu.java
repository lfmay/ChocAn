import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This is the Operator menu for activating the operatormenu. 
 * @author Kurt Anderson
 * @version 1.0
 */

public class OperatorMenu extends Menu implements ActionListener{

	private JButton mProviderRecordsBttn, mMemberRecordsBttn;
	private MainMenu mMainMenu; 
	/**
	 * 
	 * @param name - This is the name entered in by the main menu
	 * @param mainMenu - This is the mainMenu object, to be passed in as a way of always returning to the Main Menu
	 */
	
	public OperatorMenu(String name, MainMenu mainMenu) {
		
		super.Menu("Operator Menu for: " + name, "Welcome Operator", mainMenu);
		
		mMainMenu = mainMenu; //Keep the Menu chain going to always provide a way to logout
		
		//Create the container to store the objects
		Container c = getContentPane();
		c.setLayout(null);
		

		//Create Grid Layout Variables
		int buttonColumn = 250;
		int buttonRow = 100;
		int buttonGap = 175;
		
		//Create the two buttons
		mProviderRecordsBttn = new JButton("Provider Records");
		mProviderRecordsBttn.addActionListener(this);
		mProviderRecordsBttn.setSize( 300, 100 );
		mProviderRecordsBttn.setLocation( buttonColumn, buttonRow );
		c.add(mProviderRecordsBttn);
		
		mMemberRecordsBttn = new JButton("Member Records");
		mMemberRecordsBttn.addActionListener(this);
		mMemberRecordsBttn.setSize( 300, 100 );
		mMemberRecordsBttn.setLocation( buttonColumn, buttonRow + buttonGap );
		c.add(mMemberRecordsBttn);
		
		
		super.createMenu();
		//Creates the Menu
		
	}
	
	/**
	 * This opens up the modify record menu for the provider menu
	 */
	
	public void openProvider() {
		ModifyRecord modifyProviderRecord = new ModifyRecord("Provider", mMainMenu);
		this.dispose();
	}
	/**
	 * This opens up the member modify record menu
	 */
	
	public void openMember() {
		ModifyRecord modifyMemberRecord = new ModifyRecord("Member" , mMainMenu);
		this.dispose();
	}
	
	/**
	 * This overrides the actionperformed function. Activating the button clicks of the folder 
	 */

	@Override
	public void actionPerformed(ActionEvent event) {
		
		super.actionPerformed(event); //Calls the super class for 
		
		if (event.getSource() == mProviderRecordsBttn) {
			openProvider();
		}
		
		else if (event.getSource() == mMemberRecordsBttn) {
			openMember();
		}
		
		
	}
	
}
