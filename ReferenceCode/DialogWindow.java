//CS350, Project #5, Kurt Anderson
// A class created to support the main window, this class interacts with the CSample class
// It modifys the CSample objects that have been created. 

import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*; 


public class DialogWindow extends JDialog implements ActionListener {
	//Declaration of all the varaibles used in the class
	private JLabel carrierLabel, ratingLabel, zipLabel, participantLabel, IDLabel, servicesLabel;
	private JRadioButton attRB, tmobRB, verizonRB, sprintRB, othersRB;
	private ButtonGroup carrierGroup, ratingGroup;
	private JRadioButton excellentRB, vGoodRB, goodRB, fairRB, poorRB;
	private JTextField zipCodeField;
	private JCheckBox voiceCB, textCB, dataCB;
	private JButton cancelButton, submitButton;
	private CSample mCSample;
	
	private boolean cancelled = true;
	public boolean isCancelled() {return cancelled;}
	
	
	public DialogWindow(JFrame owner, String title, CSample cSample){
		super(owner, "Add a Survey Sample", true);
		
		// If a csample is provided, it sets all the data to that csample entity
		//Used for the modify button
		String partNumber = (String) cSample.getmIDNum();
		String zipCodeInit = (String) cSample.getmZipCode();
		String initCarrier = (String) cSample.getmCarrierSel();
		String initRating = (String) cSample.getmRatingSel();
		String initVoice = (String) cSample.getmVSel();
		String initText = (String) cSample.getmTSel();
		
		String initData = cSample.getmDSel();
		
		mCSample = cSample;
		
		
		
		//Creates a container for all the java objects
		Container c = getContentPane();
		c.setLayout(null);
		
		//Sets the Participant Label
		participantLabel = new JLabel("Participant ID");
		participantLabel.setForeground(Color.blue);
		participantLabel.setSize(100,20);
		participantLabel.setLocation(40, 50);
		c.add(participantLabel);
		
		//Sets the Participant Label
		IDLabel = new JLabel(partNumber);
		IDLabel.setForeground(Color.MAGENTA);
		IDLabel.setSize(100,20);
		IDLabel.setLocation(150, 50);
		c.add(IDLabel);
		
		//Sets the ZipCode Label
		zipLabel = new JLabel("Zip Code");
		zipLabel.setForeground(Color.blue);
		zipLabel.setSize(50,20);
		zipLabel.setLocation(40, 150);
		c.add(zipLabel);
		
		//Sets the ZipCode enter box
		zipCodeField = new JTextField(zipCodeInit);
		zipCodeField.setSize(70,20);
		zipCodeField.setLocation(150, 150);
		c.add(zipCodeField);
		
		//Sets the Carrier Label
		carrierLabel = new JLabel("Carrier");
		carrierLabel.setForeground(Color.blue);
		carrierLabel.setSize(50,20);
		carrierLabel.setLocation(40, 250);
		c.add(carrierLabel);
		
		//carrier Group Declarations
		attRB = new JRadioButton("AT&T", initCarrier.equals("AT&T"));
		attRB.setSize(150,50);
		attRB.setLocation(60, 270 );
		c.add(attRB);
		
		tmobRB = new JRadioButton("T-Mobile", initCarrier.equals("T-Mobile"));
		tmobRB.setSize(150,50);
		tmobRB.setLocation(60, 300 );
		c.add(tmobRB);
		
		verizonRB = new JRadioButton("Verizon", initCarrier.equals("Verizon"));
		verizonRB.setSize(150,50);
		verizonRB.setLocation(60, 330 );
		c.add(verizonRB);
		
		sprintRB = new JRadioButton("Sprint", initCarrier.equals("Sprint"));
		sprintRB.setSize(150,50);
		sprintRB.setLocation(60, 360 );
		c.add(sprintRB);
		
		othersRB = new JRadioButton("Others", initCarrier.equals("Others"));
		othersRB.setSize(150,50);
		othersRB.setLocation(60, 390 );
		c.add(othersRB);
		
		carrierGroup = new ButtonGroup();
		carrierGroup.add(attRB);
		carrierGroup.add(tmobRB);
		carrierGroup.add(verizonRB);
		carrierGroup.add(sprintRB);
		carrierGroup.add(othersRB);
		
		
		//Rating Label Creation
		ratingLabel = new JLabel("Rating");
		ratingLabel.setForeground(Color.blue);
		ratingLabel.setSize(50,20);
		ratingLabel.setLocation(390, 250);
		c.add(ratingLabel);
		
		//rating Group Declarations
		excellentRB = new JRadioButton("Excellent(E)", initRating.equals("E"));
		excellentRB.setSize(150,50);
		excellentRB.setLocation(410, 270 );
		c.add(excellentRB);
		
		
		vGoodRB = new JRadioButton("Very Good (V)", initRating.equals("V"));
		vGoodRB.setSize(150,50);
		vGoodRB.setLocation(410, 300 );
		c.add(vGoodRB);
		
		goodRB = new JRadioButton("Good (G)", initRating.equals("G"));
		goodRB.setSize(150,50);
		goodRB.setLocation(410, 330 );
		c.add(goodRB);
		
		fairRB = new JRadioButton("Fair (F)", initRating.equals("F"));
		fairRB.setSize(150,50);
		fairRB.setLocation(410, 360 );
		c.add(fairRB);
		
		poorRB = new JRadioButton("Poor (P)", initRating.equals("P"));
		poorRB.setSize(150,50);
		poorRB.setLocation(410, 390 );
		c.add(poorRB);
		
		ratingGroup = new ButtonGroup();
		ratingGroup.add(excellentRB);
		ratingGroup.add(vGoodRB);
		ratingGroup.add(goodRB);
		ratingGroup.add(fairRB);
		ratingGroup.add(poorRB);
		
		//Services Label Creation
		servicesLabel = new JLabel("What Services do you subscribe to?");
		servicesLabel.setForeground(Color.blue);
		servicesLabel.setSize(250,20);
		servicesLabel.setLocation(40, 480);
		c.add(servicesLabel);
		
		//Services CB group
		voiceCB = new JCheckBox("Voice (V)", initVoice.equals("V"));
		voiceCB.setSize(150,50);
		voiceCB.setLocation(40, 510 );
		c.add(voiceCB);
		
		textCB = new JCheckBox("Text (T)", initText.equals("T"));
		textCB.setSize(150,50);
		textCB.setLocation(225, 510 );
		c.add(textCB);
		
		dataCB = new JCheckBox("Data (D)", initData.equals("D"));
		dataCB.setSize(150,50);
		dataCB.setLocation(410, 510 );
		c.add(dataCB);
		
		//Add the two buttons at the bottom, one for cancel, one for submiting,
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setSize( 150, 50 );
		cancelButton.setLocation( 350, 580 );
		c.add(cancelButton);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		submitButton.setSize( 150, 50 );
		submitButton.setLocation( 60, 580 );
		c.add(submitButton);

		//SEts the size of the window and makes the window visible after all the above has completed
		setSize( 600, 700);
		setLocationRelativeTo(owner);
		if (title.equals("Something_Unique")) {
			activateSubmitButton();
			
		}
		else{
			setVisible(true);
		}
		
		
		
		
		
	}
	
	public void activateSubmitButton () {
		long temp;
		 //Save Zip Code Information
		 //Makes sure that the zip code is always a 5 digit number
		 
		 //Attempts to control the length of the zipcode
		 //Not complete due to lack of knowledge on validation
		 if (zipCodeField.getText().length() == 0) {
			 temp = 0;
		 }
		 else {
			 temp = Integer.parseInt(zipCodeField.getText());
			 
		 }
		 mCSample.setmZipCode(String.format("%05d", temp));
		 
		 
		 
		 //Save the selection data for the rating
		 if (excellentRB.isSelected()) mCSample.setmRatingSel("E");
		 if (vGoodRB.isSelected()) mCSample.setmRatingSel("V");
		 if (goodRB.isSelected()) mCSample.setmRatingSel("G");
		 if (fairRB.isSelected()) mCSample.setmRatingSel("F");
		 if (poorRB.isSelected()) mCSample.setmRatingSel("P");
		 
		 
		 //Save the selection data for the Carrier
		 if (attRB.isSelected()) mCSample.setmCarrierSel("AT&T");
		 if (tmobRB.isSelected()) mCSample.setmCarrierSel("T-Mobile");
		 if (verizonRB.isSelected()) mCSample.setmCarrierSel("Verizon");
		 if (sprintRB.isSelected()) mCSample.setmCarrierSel("Sprint");
		 if (othersRB.isSelected()) mCSample.setmCarrierSel("Others");
		 
		 //All the setters for the three ending elements
		 if (voiceCB.isSelected()) {
			 mCSample.setmVSel("V");
		 }
		 else {
			 mCSample.setmVSel("-");
		 }
		 if (textCB.isSelected()) {
			 mCSample.setmTSel("T");
		 }
		 else {
			 mCSample.setmTSel("-");
		 }
		 if (dataCB.isSelected()) {
			 mCSample.setmDSel("D");
		 }
		 else {
			 mCSample.setmDSel("-");
		 }
		 cancelled = false; 
		 setVisible(false);
	
		 
	 }


	
	//Makes sure the information can be passed back to mainwindow
	public CSample getSample() {return mCSample; }
	public String getString() {return mCSample.makeString();}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		 if (event.getSource() == submitButton) {
			 long temp;
			 //Save Zip Code Information
			 //Makes sure that the zip code is always a 5 digit number
			 
			 //Attempts to control the length of the zipcode
			 //Not complete due to lack of knowledge on validation
			 if (zipCodeField.getText().length() == 0) {
				 temp = 0;
			 }
			 else {
				 temp = Integer.parseInt(zipCodeField.getText());
				 
			 }
			 mCSample.setmZipCode(String.format("%05d", temp));
			 
			 
			 
			 //Save the selection data for the rating
			 if (excellentRB.isSelected()) mCSample.setmRatingSel("E");
			 if (vGoodRB.isSelected()) mCSample.setmRatingSel("V");
			 if (goodRB.isSelected()) mCSample.setmRatingSel("G");
			 if (fairRB.isSelected()) mCSample.setmRatingSel("F");
			 if (poorRB.isSelected()) mCSample.setmRatingSel("P");
			 
			 
			 //Save the selection data for the Carrier
			 if (attRB.isSelected()) mCSample.setmCarrierSel("AT&T");
			 if (tmobRB.isSelected()) mCSample.setmCarrierSel("T-Mobile");
			 if (verizonRB.isSelected()) mCSample.setmCarrierSel("Verizon");
			 if (sprintRB.isSelected()) mCSample.setmCarrierSel("Sprint");
			 if (othersRB.isSelected()) mCSample.setmCarrierSel("Others");
			 
			 //All the setters for the three ending elements
			 if (voiceCB.isSelected()) {
				 mCSample.setmVSel("V");
			 }
			 else {
				 mCSample.setmVSel("-");
			 }
			 if (textCB.isSelected()) {
				 mCSample.setmTSel("T");
			 }
			 else {
				 mCSample.setmTSel("-");
			 }
			 if (dataCB.isSelected()) {
				 mCSample.setmDSel("D");
			 }
			 else {
				 mCSample.setmDSel("-");
			 }
			 cancelled = false; 
			 setVisible(false);
		 }
		 else if (event.getSource() == cancelButton) {
			 cancelled = true;
			 setVisible(false);
			 
		 }
	}

}
