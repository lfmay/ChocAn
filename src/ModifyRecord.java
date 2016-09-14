import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * This class creates the modify record menu to display the records 
 * @author Kurt Anderson
 * @version 1.0
 */

public class ModifyRecord extends Menu implements ActionListener  {
	
	private JTable table;
	
	private JButton mAddBttn, mUpdateBttn, mRemoveBttn, mBackBttn;
	private JScrollPane mScrollPane;
	
	private Records mWorkingRecords;
	private String mMenu;
	private MainMenu mMainMenu;
	private int mCurrentNum;
	private DefaultTableModel model = new DefaultTableModel();
	
	/**
	 * Creates the dialog modifyRecord
	 * @param menu - Inputs a string name to be used as the title of the menu, depending on which menu called it
	 * @param mainMenu - This is a mainMenu object, keeps the dialog structure going. 
	 */
	
	public ModifyRecord(String menu, MainMenu mainMenu ) {
		super.Menu("Edit " + menu  + " Records", "", mainMenu);
		
		//Keep Object Chain Up
		mMenu = menu;
		mMainMenu = mainMenu;
		
		//Create Button Layout Variables
		int buttonRow = 412;
		int buttonColumn = 50;
		int buttonGap = 275;
		
		//Create the Buttons
		mAddBttn = new JButton("Add");
		mAddBttn.addActionListener(this);
		mAddBttn.setSize( 150, 25 );
		mAddBttn.setLocation( buttonColumn, buttonRow );
		c.add(mAddBttn);
		
		mUpdateBttn = new JButton("Update");
		mUpdateBttn.addActionListener(this);
		mUpdateBttn.setSize( 150, 25 );
		mUpdateBttn.setLocation( buttonColumn + buttonGap, buttonRow );
		c.add(mUpdateBttn);
		
		mRemoveBttn = new JButton("Remove");
		mRemoveBttn.addActionListener(this);
		mRemoveBttn.setSize( 150, 25 );
		mRemoveBttn.setLocation( buttonColumn + 2 *buttonGap, buttonRow );
		c.add(mRemoveBttn);
		
		mBackBttn = new JButton("Back");
		mBackBttn.addActionListener(this);
		mBackBttn.setSize( 150, 30 );
		mBackBttn.setLocation( buttonColumn, 20 );
		c.add(mBackBttn);
		
		//Create JTable
		createJTable(); 
		
		super.createMenu();
		
		
	}
	
	/**
	 * This creates the JTable as an intializer. 
	 */
	
	public void createJTable() {
		//New In Class Container to make the table touchable
		
			
		table = new JTable(model);
		
		//Add Columns to JTable
		model.addColumn("Name");
		model.addColumn("Number");
		model.addColumn("Address");
		model.addColumn("City");
		model.addColumn("State");
		model.addColumn("Zip Code");

		//Figures out which Record to grab from ChocAn
		if (mMenu.equals("Provider")) {
			mWorkingRecords = mMainMenu.mChocAn.getmProviderRecords();
			mCurrentNum = mMainMenu.mChocAn.getProviderCounter();
			
		}
		else if (mMenu.equals("Member")) {
			mWorkingRecords = mMainMenu.mChocAn.getmMemberRecords();
			mCurrentNum = mMainMenu.mChocAn.getMemberCounter();
		}
		
		//Creates a working entries to use
		ArrayList<Entry> entries = mWorkingRecords.searchEntry();
		
		//Creates a temporary holder to act as a median before being processed
		Entry temp;

		for (int i = 0; i < entries.size(); i++){
			temp = entries.get(i);
			Object[] tempObj = {temp.getName(), temp.getNumber(), temp.getAddress(), temp.getCity(), temp.getState(), temp.getZip()};
			model.addRow(tempObj);
		}
	
		//Adds the table to the screen 
		mScrollPane = new JScrollPane(table);
		mScrollPane.setSize(650,300);
		mScrollPane.setLocation(75,75);
		c.add(mScrollPane);
		this.revalidate();
	}
	
	/**
	 *This returnst the main menu passed in 
	 * @return the main meny
	 */
	
	public MainMenu getMainMenu() {
		return mMainMenu;
	}
	
	/**
	 * This updates number with ChocAn. 
	 * @param num - THis is the num that is used to update the number
	 */
	
	//Updates the global number to make sure the machine and this prompt ar always on the same system
	public void updateNum( int num) {
		if (mMenu.equals("Provider")) {
			mMainMenu.mChocAn.setProviderCounter(num);
		}
		else if (mMenu.equals("Member")) {
			mMainMenu.mChocAn.setMemberCounter(num);
		}
	}
	
	/**
	 * This overrights the actionlistener, making sure that when buttons are clicked they work. 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		super.actionPerformed(event); //Calls the super class for LogoutButton
		
			if (event.getSource() == mAddBttn) {
				AddDialog addDialog = new AddDialog(this, "Add Entry", mMainMenu, mCurrentNum); //Creates a new Diagram
				if (addDialog.isCancelled() != true) {
					Entry tempEntry = addDialog.getEntry();
					mMainMenu.mChocAn.addRecord(tempEntry, mMenu);
					mCurrentNum += 1;
					updateNum(mCurrentNum);
					c.remove(table);
					Object[] tempObj = {tempEntry.getName(), tempEntry.getNumber(), tempEntry.getAddress(), tempEntry.getCity(), tempEntry.getState(), tempEntry.getZip()};
					model.addRow(tempObj);
					//createJTable();
					try {
						mMainMenu.mChocAn.saveRecords();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			else if (event.getSource() == mUpdateBttn) {
				int index = table.getSelectedRow();
				//Grabs index, and if dialog is not cancelled, updates row, and database
				if (index >=0) {
					AddDialog addDialog = new AddDialog(this, "Edit Entry", mMainMenu, mWorkingRecords.searchEntry().get(index));
					if (addDialog.isCancelled() !=true) {
						Entry tempEntry = addDialog.getEntry();
						mMainMenu.mChocAn.updateRecord(addDialog.getEntry(), index, mMenu);
						model.removeRow(index);
						Object[] tempObj = {tempEntry.getName(), tempEntry.getNumber(), tempEntry.getAddress(), tempEntry.getCity(), tempEntry.getState(), tempEntry.getZip()};
						model.insertRow(index, tempObj);
						try {
							mMainMenu.mChocAn.saveRecords();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
			else if (event.getSource() == mRemoveBttn) {
				//Removes the selected index from table and the database
				int index = table.getSelectedRow();
				if (index >= 0) {
					model.removeRow(index);
					mMainMenu.mChocAn.removeRecord(index, mMenu);
					try {
						mMainMenu.mChocAn.saveRecords();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			else if (event.getSource() == mBackBttn) {
				mMainMenu.openOperatorMenu();
				this.dispose();
				
			}
		}
	
}
