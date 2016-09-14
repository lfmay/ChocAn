//CS350, Project #5, Kurt Anderson
// This is the main window and the main function of the program
// This classs utilizes the dialogwindow class as a boundry and a Csample class as an entity


import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.util.ArrayList;
import java.io.*;


public class MainWindow extends JFrame implements ActionListener {
	//Declare variables to be used
	private JLabel participantIDLabel, zipCdLabel, carrierLabel, ratingLabel, servicesLabel;

	private JFileChooser chooser;
	private ArrayList<CSample> dataArray;
	private DefaultListModel samples;
	private JList sampleList;
	private JScrollPane scrollPane;
	private JButton addButton, deleteButton, modifyButton, removeAllButton, saveButton, openButton;
	private int buttonX; //So all buttons have the same X Coordinate
	private int buttonRowLocation;
	private int ID_Num = 0;
	
	//Sets up the JFrame
	public MainWindow(){
		super("Main Window");
		initialize();
	}
	public MainWindow(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		super("Main Window");
		initialize();
		
		dataArray.clear();
		samples.clear();
		ID_Num = 0;
		CSample temp;
		
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));){
			while(true) {
				
				temp = new CSample( (CSample) input.readObject());
				DialogWindow dialogWindow = new DialogWindow(this, "Something_Unique", temp);
				dataArray.add( dialogWindow.getSample() );
				samples.addElement(dialogWindow.getString());
				//System.out.printf(dataArray.get(i).makeString());
				sampleList.setSelectedIndex(samples.size()-1);
				sampleList.ensureIndexIsVisible(samples.size()-1);
				ID_Num += 1;
			}
		}
		catch (EOFException e) {
			
			} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void initialize() {
		//Sets text in corner
				
				
				//The container for all the Objects
				Container c = getContentPane();
				c.setLayout(null);
				
				//Create Top Lables
				participantIDLabel = new JLabel("Participant ID");
				participantIDLabel.setSize(100,20);
				participantIDLabel.setLocation(30, 20);
				participantIDLabel.setForeground(Color.blue);
				c.add(participantIDLabel);
				
				zipCdLabel = new JLabel("Zip Code");
				zipCdLabel.setSize(100,20);
				zipCdLabel.setLocation(190, 20);
				zipCdLabel.setForeground(Color.blue);
				c.add(zipCdLabel);
				
				carrierLabel = new JLabel("Carrier");
				carrierLabel.setSize(100,20);
				carrierLabel.setLocation(350, 20);
				carrierLabel.setForeground(Color.blue);
				c.add(carrierLabel);
				
				ratingLabel = new JLabel("Rating");
				ratingLabel.setSize(100,20);
				ratingLabel.setLocation(510, 20);
				ratingLabel.setForeground(Color.blue);
				c.add(ratingLabel);
				
				servicesLabel = new JLabel("Services");
				servicesLabel.setSize(100,20);
				servicesLabel.setLocation(670, 20);
				servicesLabel.setForeground(Color.blue);
				c.add(servicesLabel);
				
				//Create the editable data table
				dataArray = new ArrayList<CSample>();
				samples = new DefaultListModel();
				sampleList = new JList(samples);
				sampleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				sampleList.setFont(new Font("Monospaced", Font.PLAIN, 12));
				
				//Enable scrollpanse object on window (So it scrolls)
				scrollPane = new JScrollPane(sampleList);
				scrollPane.setSize(725, 420);
				scrollPane.setLocation(30,60);
				c.add(scrollPane);
				
				buttonRowLocation = 494;
				
				//Create AddButton on MainWindow
				addButton = new JButton("Add");
				addButton.setSize(150,48);
				addButton.setLocation(30,buttonRowLocation);
				addButton.addActionListener(this);
				c.add(addButton);
				
				//Create modifyButton on Main Window
				modifyButton = new JButton("Modify");
				modifyButton.setSize(150,48);
				modifyButton.setLocation(225,buttonRowLocation);
				modifyButton.addActionListener(this);
				c.add(modifyButton);
				
				//Create Delete Button
				deleteButton = new JButton("Remove");
				deleteButton.setSize(150,48);
				deleteButton.setLocation(410,buttonRowLocation);
				deleteButton.addActionListener(this);
				c.add(deleteButton);
				

				//Create Remove All Button Button
				removeAllButton = new JButton("Remove All");
				removeAllButton.setSize(150,48);
				removeAllButton.setLocation(600,buttonRowLocation);
				removeAllButton.addActionListener(this);
				c.add(removeAllButton);
				
				//Create Save Button Button
				saveButton = new JButton("Save");
				saveButton.setSize(150,48);
				saveButton.setLocation(410,buttonRowLocation + 100);
				saveButton.addActionListener(this);
				c.add(saveButton);
				
				//Create Open Button
				openButton = new JButton("Open");
				openButton.setSize(150,48);
				openButton.setLocation(225,buttonRowLocation + 100);
				openButton.addActionListener(this);
				c.add(openButton);
				
				//Creates the JFRame and makes it visible
				setSize(800, 700);
				setLocation( 100, 100);
				setVisible(true);
				
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		// The control statement that decides what to do depending on the button clicked
		// If it is the add button it goes to this statement
		if (event.getSource() == addButton) {
			String formatted = String.format("%06d", ID_Num); //Makes sure to format the ID Number
			CSample newCSample = new CSample(formatted); //Opens with the class selection of only an ID NUM
			DialogWindow dialogWindow = new DialogWindow(this, "Add", newCSample);
			//Makes sure that the dialog window is not in the cancelled state, then runs the code
			if (dialogWindow.isCancelled() != true){
				dataArray.add(dialogWindow.getSample());
				samples.addElement(dialogWindow.getString());
				sampleList.setSelectedIndex(samples.size()-1);
				sampleList.ensureIndexIsVisible(samples.size()-1);
				ID_Num += 1;
			}
			
		}
		
		else if (event.getSource() == modifyButton) {
			int index = sampleList.getSelectedIndex(); //Grabs the index of the selected text
			if (index >=0) {
				DialogWindow  dialogWindow = new DialogWindow(this, "Edit", dataArray.get(index)); // A new Dialog window is created
				
				//Again checks for the cancelled status
				if (dialogWindow.isCancelled() != true){
					dataArray.set(index, dialogWindow.getSample()); 
					samples.set(index, dialogWindow.getString());
				}
			}
		}
		
		else if (event.getSource() == deleteButton) {
			int index = sampleList.getSelectedIndex(); //Finds the index selected
			if (index >=0) {
				dataArray.remove(index); //Removes the index from both the array and the samples list
				samples.remove(index);
				if (samples.size()>0){
					if (index==samples.size()) {
						index--;
					}
					sampleList.setSelectedIndex(index);
					sampleList.ensureIndexIsVisible(index);
				}
			}
			
		}
		
		else if (event.getSource() == removeAllButton) {
			//Clears all the lists and resets the ID num as those entities are now deleted
			dataArray.clear();;
			samples.clear();
			ID_Num = 0;
		}
		
		else if (event.getSource() == saveButton) {
			//Clears all the lists and resets the ID num as those entities are now deleted
			File fileName;
			String fileName_str = null;
			chooser = new JFileChooser();

				int returnVal = chooser.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fileName = chooser.getSelectedFile();
					try {
						fileName.createNewFile();
					} catch (IOException e) {
						
					}
				fileName_str = fileName.getAbsolutePath();
			}
		
			ObjectOutputStream output = null;
			try {
				output = new ObjectOutputStream(new FileOutputStream(fileName_str));
			} catch (IOException e) {

			}
		
			for (int i=0; i < dataArray.size(); i++) {
				try {
					output.writeObject(dataArray.get(i));
				} catch (IOException e) {
					
				}
				
			

			}
			try {
				output.close();
			} catch (IOException e) {
			}
		}
		
		else if (event.getSource() == openButton) {
			//Clears all the lists and resets the ID num as those entities are now deleted
			
			String fileName = null;
			chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fileName = chooser.getSelectedFile().getAbsolutePath();
				dataArray.clear();
				samples.clear();
				ID_Num = 0;
				
				CSample temp;
				
				try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));){
					while(true) {
						
						temp = new CSample( (CSample) input.readObject());
						DialogWindow dialogWindow = new DialogWindow(this, "Something_Unique", temp);
						dataArray.add( dialogWindow.getSample() );
						samples.addElement(dialogWindow.getString());
						//System.out.printf(dataArray.get(i).makeString());
						sampleList.setSelectedIndex(samples.size()-1);
						sampleList.ensureIndexIsVisible(samples.size()-1);
						ID_Num += 1;
					}
				}
				catch (EOFException e) {
					
					} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (returnVal == JFileChooser.CANCEL_OPTION){
				
			}
			
			
			
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		//Starts the Class, and runs the program
		if (args.length == 1) {
			MainWindow mainWnd = new MainWindow(args[0]);
		}
		else{
			MainWindow mainWnd = new MainWindow();
		}
		
	}

}
