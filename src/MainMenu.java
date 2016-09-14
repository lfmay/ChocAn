//

//Import Statements
import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class is the main GUI for the program. It acts as the medium between the user and the GUI
 * @author Kurt Anderson
 * @version 1.0
 */

public class MainMenu extends JFrame implements ActionListener {
	
	//Declaration of member Variables
	//Declaration of GUI Variables
	
	private ButtonGroup mPersonalGroup;
	private JLabel mUserNameLbl, mPasswordLbl;
	private JRadioButton mOperatorRB, mProviderRB, mManagerRB;
	private JTextField mUserNameTF;
	private JPasswordField mPasswordTF;
	private JButton mLoginButton, mSimulateSwipeBttn, mMainTransactionBttn;
	private JCheckBox mRemMeCB;
	
	private String mPasswordData, mUserNameData;
	public ChocAn mChocAn;
	
	
	public MainMenu(ChocAn chocAn) {
		//Initialize MainMenu
		super("Main Menu");
		mChocAn = chocAn;
	
		//Create the container for all the JFRame Objects
		Container c = getContentPane(); 
		c.setLayout(null);
		
		//Create Grid Variables to keep things consistent
		int labelColumn = 90;
		int labelRow = 50;
		int labelGap = 30; //Creates the gap between labels
		int RBGap = 110;
		int JTFGap = 90; //Creates gap between TF
		int buttonGap = 190; //Set the x gap for the login button
		int CBGap = 185;
		
		//Remember Me Variables
		String usernameText = "";
		String passwordText = "";
		
		//Create labels
		mUserNameLbl = new JLabel("Username");
		mUserNameLbl.setSize(100,20);
		mUserNameLbl.setLocation(labelColumn,labelRow);
		mUserNameLbl.setForeground(Color.blue);
		c.add(mUserNameLbl);
		
		mPasswordLbl = new JLabel("Password");
		mPasswordLbl.setSize(100,20);
		mPasswordLbl.setLocation(labelColumn,labelRow + labelGap);
		mPasswordLbl.setForeground(Color.blue);
		c.add(mPasswordLbl);
		
		
		//Create TextFields
		
		mUserNameTF = new JTextField(passwordText);
		mUserNameTF.setSize(200,20);
		mUserNameTF.setLocation(labelColumn + JTFGap, labelRow);
		c.add(mUserNameTF);
		
		mPasswordTF = new JPasswordField(usernameText);
		mPasswordTF.setSize(200,20);
		mPasswordTF.setLocation(labelColumn + JTFGap, labelRow + labelGap);
		c.add(mPasswordTF);
		
			
		//Create Radio Button field
		
		mOperatorRB = new JRadioButton("Operator");
		mOperatorRB.setSize(100,50);
		mOperatorRB.setLocation(labelColumn, labelRow + 3*labelGap);
		c.add(mOperatorRB);
		
		mManagerRB = new JRadioButton("Manager");
		mManagerRB.setSize(100,50);
		mManagerRB.setLocation(labelColumn + RBGap, labelRow + 3*labelGap);
		c.add(mManagerRB);
		
		mProviderRB = new JRadioButton("Provider");
		mProviderRB.setSize(100,50);
		mProviderRB.setLocation(labelColumn + 2*RBGap, labelRow + 3*labelGap);
		c.add(mProviderRB);
		
		mPersonalGroup = new ButtonGroup();
		mPersonalGroup.add(mOperatorRB);
		mPersonalGroup.add(mManagerRB);
		mPersonalGroup.add(mProviderRB);
		
		//Create the Remember Me CheckBox
		mRemMeCB = new JCheckBox("Remember Me");
		mRemMeCB.setSize(150,50);
		mRemMeCB.setLocation(labelColumn + CBGap , labelRow + 2*labelGap - 10 );
		c.add(mRemMeCB);
		
		//Create the Login Button
		mLoginButton = new JButton("Log on");
		mLoginButton.addActionListener(this);
		mLoginButton.setSize( 100, 25 );
		mLoginButton.setLocation(labelColumn + buttonGap + 50, labelRow + 5*labelGap );
		c.add(mLoginButton);
		
		//Create the Simulate Button
		mSimulateSwipeBttn = new JButton("Card Swipe");
		mSimulateSwipeBttn.addActionListener(this);
		mSimulateSwipeBttn.setSize( 100, 25 );
		mSimulateSwipeBttn.setLocation(labelColumn - 50, labelRow + 5*labelGap );
		c.add(mSimulateSwipeBttn);
		
		//Create the MainTransaction Button
		mMainTransactionBttn = new JButton("12 Friday");
		mMainTransactionBttn.addActionListener(this);
		mMainTransactionBttn.setSize( 100, 25 );
		mMainTransactionBttn.setLocation(labelColumn + buttonGap - 100, labelRow + 5*labelGap );
		c.add(mMainTransactionBttn);
		
		// Activates the JFRAME
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //Grabs the screen resolution
		setSize(500, 300);
		setLocation( dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2); //Sets the mainMenu to center regardless of screen resoulution
		setVisible(true);
	}
	/**
	 * This opens up the operator menu through creating an operator menu class
	 * 
	 */

	public void openOperatorMenu () {
		//Creates a new Operator Menu
		OperatorMenu operatorMenu = new OperatorMenu(mUserNameTF.getText(), this);
		this.dispose(); //Closes and deletes the main menu, keeping memory
	}
	
	/**
	 * This opens up the providerMenu through creating a provider menu
	 */
	
	public void openProviderMenu() {
		//Creates a new provider Menu
		ProviderMenu providerMenu = new ProviderMenu(mUserNameTF.getText(), this);
		this.dispose(); //Closes and deletes the main menu, keeping memory clear
	}

	/**
	 * This opens up the managerMenu through creating a manager menu
	 */
	
	public void openManagerMenu() {
		//Creates a new ManagerMenu
		ManagerMenu managerMenu = new ManagerMenu(mUserNameTF.getText(),this );
		this.dispose(); //Closes and deletes the main menu, keeping memory
	}
	
	/**
	 * This simulates the card swipe through using the mChocAn simulateCardSwipe()
	 */
	
	public void SimulateCardSwipe() {
		mChocAn.SimulateCardSwipe();
	}
	/**
	 * reformats the menu after a button is clicked to clear out the password field, and the user field if the checkbox isn't selected. 
	 */
	
	public void reformatMenu() {
		if (!mRemMeCB.isSelected()) {
			//Resets the username field
			mUserNameTF.setText("");
			
			//Resets all the RB's AS Well
			mManagerRB.setSelected(false);
			mOperatorRB.setSelected(false);
			mProviderRB.setSelected(false);
		}
		//Password field is always reset
		mPasswordTF.setText("");
	}
	
	/**
	 * This is a modified actionListener, it is used to activate the buttons upon pressing of the buttons. 
	 */
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource() == mLoginButton && mProviderRB.isSelected()) {
			
			openProviderMenu();
			reformatMenu();
		}
		
		else if (event.getSource() == mLoginButton && mOperatorRB.isSelected()) {
			
			openOperatorMenu();
			reformatMenu();
		}
		
		else if (event.getSource() == mLoginButton && mManagerRB.isSelected()) {
			
			openManagerMenu();
			reformatMenu();
		}
		
		else if (event.getSource() == mSimulateSwipeBttn) {
			SimulateCardSwipe();
		}
		
		else if (event.getSource() == mMainTransactionBttn) {
			int accept_number = JOptionPane.showConfirmDialog(null, "Are you sure you wish to proceed with the Run Main Accounting Procedure");
			if (accept_number == JOptionPane.YES_OPTION) {
				mChocAn.runMainTransaction();
			}
		}
		
	}
}
