
import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*; 

/**
 * This creates a dialog to add or edit entries before adding them to the record
 * @author Kurt Anderson
 * @version 1.0
 */

public class AddDialog extends JDialog implements ActionListener {
	//Declaration of all the varaibles used in the class
	
	private String mName;
	private String mNumber;
	private String mStreetAddress;
	private String mCity;
	private String mState;
	private String mZipCode;
	private Boolean mStatus;
	private ArrayList<Services> services;
	
	public JFrame mOwner;
	
	private JLabel mNameLbl, mNumberActual, mNumberLbl, mStreetAddressLbl, mCityLbl, mStateLbl, mZipCodeLbl, mActiveLbl;
	private JTextField mNameTF, mCityTF, mStateTF, mZipCodeTF;
	private JTextArea mStreetAddressTA;
	private JButton mSubmitButton, mCancelButton;
	private JRadioButton mYesRB, mNoRB;
	private ButtonGroup mActivityGroup;
	private Entry mEntry;

	private boolean cancelled = true;
	public boolean isCancelled() {return cancelled;}
	
	/**
	 * This constructor makes sure the dialog is set up for editing an entry
	 * @param owner - This is the owner that called this function, needs to be a component
	 * @param title - Creates a title for the dialog
	 * @param mainMenu - Needs a mainmenu, this keeps the string of menus going 
	 * @param entry - An entry to edit
	 */
	public AddDialog(JFrame owner, String title, MainMenu mainMenu, Entry entry){
		super(owner, "Edit an Entry", true);
		mOwner = owner;
		mEntry = entry;
		mName = entry.getName();
		mNumber = entry.getNumber();
		mStreetAddress = entry.getAddress();
		mCity = entry.getCity();
		mState = entry.getState();
		mZipCode = entry.getZip();
		mStatus = entry.getStatus();
		initialize();
	}
	
	/**
	 * This constructor makes sure the dialog is set up for adding and entry
	 * @param owner - This is the owner that called this function, needs to be a component
	 * @param title - Creates a title for the dialog
	 * @param mainMenu - Needs a mainmenu, this keeps the string of menus going 
	 * @param Number - The number to initialize the entry to, being the provider or member number
	 */
	public AddDialog(JFrame owner, String title, MainMenu mainMenu, int Number) {
		super(owner, "Add a new Entry", true);
		mOwner = owner;
		mName = "";
		mNumber = String.format("%09d", Number);
		mStreetAddress = "";
		mCity = "";
		mState = "";
		mZipCode = "";
		mStatus = true;
		mEntry = new Entry(mName,mNumber, mStreetAddress, mCity, mState, mZipCode);
		initialize();
	}
	
	/**
	 * This initializes the dialog panel to bring up the stuff needed. 
	 */
	private void initialize() {
		Container c = getContentPane();
		c.setLayout(null);
		
		//Grid Variables to make all measurements in the same area
		int firstLabelColumn = 40;
		int firstLabelRow = 80;
		int labelGap = 50;
		
		//Creation of the labels
		mNumberLbl = new JLabel("Number");
		mNumberLbl.setForeground(Color.blue);
		mNumberLbl.setSize(100,20);
		mNumberLbl.setLocation(firstLabelColumn, firstLabelRow - labelGap);
		c.add(mNumberLbl);
		
		mNumberActual = new JLabel(mNumber);
		mNumberActual.setForeground(Color.blue);
		mNumberActual.setSize(100,20);
		mNumberActual.setLocation(firstLabelColumn + labelGap, firstLabelRow - labelGap);
		c.add(mNumberActual);
		
		mNameLbl = new JLabel("Name");
		mNameLbl.setForeground(Color.blue);
		mNameLbl.setSize(100,20);
		mNameLbl.setLocation(firstLabelColumn, firstLabelRow);
		c.add(mNameLbl);
		
		mStreetAddressLbl = new JLabel("Street Address");
		mStreetAddressLbl.setForeground(Color.blue);
		mStreetAddressLbl.setSize(100,20);
		mStreetAddressLbl.setLocation(firstLabelColumn, firstLabelRow + labelGap);
		c.add(mStreetAddressLbl);
		
		mActiveLbl = new JLabel("Active");
		mActiveLbl.setForeground(Color.blue);
		mActiveLbl.setSize(100,20);
		mActiveLbl.setLocation(firstLabelColumn+ 3 *labelGap, firstLabelRow + labelGap);
		c.add(mActiveLbl);
		
		mCityLbl = new JLabel("City");
		mCityLbl.setForeground(Color.blue);
		mCityLbl.setSize(100,20);
		mCityLbl.setLocation(firstLabelColumn, firstLabelRow + 4 *labelGap);
		c.add(mCityLbl);
		
		mStateLbl = new JLabel("State");
		mStateLbl.setForeground(Color.blue);
		mStateLbl.setSize(100,20);
		mStateLbl.setLocation(firstLabelColumn, firstLabelRow + 5 *labelGap);
		c.add(mStateLbl);
		
		mZipCodeLbl = new JLabel("Zip Code");
		mZipCodeLbl.setForeground(Color.blue);
		mZipCodeLbl.setSize(100,20);
		mZipCodeLbl.setLocation(firstLabelColumn + 3 * labelGap, firstLabelRow + 5 * labelGap);
		c.add(mZipCodeLbl);
		
		//Create all the text Fields for the information
		mNameTF = new JTextField(mName);
		mNameTF.setSize(200,20);
		mNameTF.setLocation(firstLabelColumn + labelGap, firstLabelRow);
		c.add(mNameTF);
		
		mStreetAddressTA = new JTextArea(mStreetAddress);
		mStreetAddressTA.setSize(300,100);
		mStreetAddressTA.setLocation(firstLabelColumn, firstLabelRow + 2 * labelGap -20);
		c.add(mStreetAddressTA);
		
		mCityTF = new JTextField(mCity);
		mCityTF.setSize(200,20);
		mCityTF.setLocation(firstLabelColumn + labelGap, firstLabelRow + 4 *labelGap);
		c.add(mCityTF);
		
		mStateTF = new JTextField(mState);
		mStateTF.setSize(75,20);
		mStateTF.setLocation(firstLabelColumn + labelGap, firstLabelRow + 5 *labelGap);
		c.add(mStateTF);
		
		mZipCodeTF = new JTextField(mZipCode);
		mZipCodeTF.setSize(75,20);
		mZipCodeTF.setLocation(firstLabelColumn + 220, firstLabelRow + 5 * labelGap);
		c.add(mZipCodeTF);
		
		//Creation of the buttons 
		mSubmitButton = new JButton("Submit");
		mSubmitButton.addActionListener(this);
		mSubmitButton.setSize( 100, 25 );
		mSubmitButton.setLocation( 240, 400 );
		c.add(mSubmitButton);
		
		mCancelButton = new JButton("Cancel");
		mCancelButton.addActionListener(this);
		mCancelButton.setSize( 100, 25 );
		mCancelButton.setLocation( 40, 400 );
		c.add(mCancelButton);
		
		//Create the Radio Buttons
		
		mYesRB = new JRadioButton("Yes",mStatus);
		mYesRB.setSize(100,50);
		mYesRB.setLocation(firstLabelColumn+ 4 *labelGap, firstLabelRow + labelGap -15);
		c.add(mYesRB);
		
		mNoRB = new JRadioButton("No",!mStatus);
		mNoRB.setSize(50,50);
		mNoRB.setLocation(firstLabelColumn+ 5 *labelGap, firstLabelRow + labelGap - 15);
		c.add(mNoRB);
		
		mActivityGroup = new ButtonGroup();
		mActivityGroup.add(mYesRB);
		mActivityGroup.add(mNoRB);
		
		//Sets the Window Size and Makes it Visilbe
		setSize( 400, 500);
		setLocationRelativeTo(mOwner);
		setVisible(true);
	}
	/**
	 * Returns the entry associated with the dialog
	 * @return Entry returns the entry associated with the dialog
	 */
	public Entry getEntry() {return mEntry;}
	
	/**
	 * This overrights the actionlistener, making sure that when buttons are clicked they work. 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		 if (event.getSource() == mSubmitButton) {
			 mEntry.setName(mNameTF.getText());
			 mEntry.setNumber(mNumber);
			 mEntry.setAddress(mStreetAddressTA.getText());
			 mEntry.setCity(mCityTF.getText());
			 mEntry.setState(mStateTF.getText());
			 mEntry.setZip(mZipCodeTF.getText());
			 mEntry.changeStatus(mStatus);
			 cancelled = false;
			 setVisible(false);
		 }
		 
		 else if (event.getSource() == mCancelButton) {
			 cancelled = true;
			 this.dispose();
		 }
	}
	
	
}

