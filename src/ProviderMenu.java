import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates the provider menu for the menu
 * @author Kurt Anderson
 * @version 1.0
 */

public class ProviderMenu extends Menu implements ActionListener{

	private JButton mMemberStatusBttn, mSendDirectoryBttn;
	private JLabel mWelcomeLabel, nameLabel;
	private MainMenu mMainMenu;
	
	/**
	 * This is the constructor of the providermenu
	 * @param name - The String name for the number
	 * @param mainMenu - The main Menu, to keep the file string going
	 */
	public ProviderMenu( String name, MainMenu mainMenu) {
		super.Menu("Provider Menu for: " + name, "Welcome Provider", mainMenu);
		
		mMainMenu = mainMenu;
		//Create the container to store the objects
		Container c = getContentPane();
		
		//Create Grid Layout Variables
		int buttonColumn = 250;
		int buttonRow = 100;
		int buttonGap = 175;		
		
		//Create the two buttons
		mMemberStatusBttn = new JButton("Check Member \n Status");
		mMemberStatusBttn.addActionListener(this);
		mMemberStatusBttn.setSize( 300, 100 );
		mMemberStatusBttn.setLocation( buttonColumn, buttonRow );
		c.add(mMemberStatusBttn);
		
		mSendDirectoryBttn = new JButton("Send Provider \n Directory");
		mSendDirectoryBttn.addActionListener(this);
		mSendDirectoryBttn.setSize( 300, 100 );
		mSendDirectoryBttn.setLocation( buttonColumn, buttonRow + buttonGap );
		c.add(mSendDirectoryBttn);
		
		
		super.createMenu(); //Calls super class createMenu dialog
	}

	/**
	 * Check the member status through a call to mChocAn
	 * @param number - This is the memberNumber that will be checked. 
	 * @return - returns the status of the member
	 */
	
	public boolean checkMemberStatus(String number) {
		return mMainMenu.mChocAn.checkMemberStatus(number, 0); 
	}
	
	/**
	 * Sends the provider directory to a chosen file
	 * @throws FileNotFoundException - If a file is not found an error occurs
	 * @throws UnsupportedEncodingException - Handles the UnsupportedEncodingException
	 */
	public void sendProviderDirectory() throws FileNotFoundException, UnsupportedEncodingException {
		String emailAddress = JOptionPane.showInputDialog(this, "Enter Email Address", "Email Address", JOptionPane.PLAIN_MESSAGE); //Creates a dialog to checkMemberStatus
		mMainMenu.mChocAn.sendProviderDirectory();
	}
	
	/**
	 * This overrights the actionlistener, making sure that when buttons are clicked they work. 
	 */
	
	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event); //Calls the super class for 
		if (event.getSource() == mMemberStatusBttn) {
			try {
			String memberName = JOptionPane.showInputDialog(this, "Enter Member Number", "Member Name", JOptionPane.PLAIN_MESSAGE); //Creates a dialog to get the member name
			boolean status = checkMemberStatus(memberName);
			if (status == true){
				JOptionPane.showMessageDialog(this, "Validated - The member is active.");

			}
			} catch (NullPointerException e) {
				
			}
			
		}
		
		else if (event.getSource() == mSendDirectoryBttn) {
			try {
				sendProviderDirectory();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
			}
		}
		
	}
}
