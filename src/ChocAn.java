import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * The heart and blood of the chocAn program. Creates a link for the GUI to connect with the backend. 
 * @author Kurt Anderson, Nate Hochstetler, Laura May, Crawford King, Delaney D'Argenio  
 *
 */
public class ChocAn {
	
	private Records mMemberRecords = new Records("Member");
	private Records mProviderRecords = new Records("Provider");
	public int memberCounter, providerCounter;
	private MainMenu mMainMenu;
	private ProviderDirectory providerDirectory;
	
	/**
	 * The initializes the ChocAn system, filling the service directory in the process
	 */
	public ChocAn(){
		try {
			openRecords();
		} catch (FileNotFoundException e) {

		} catch (ClassNotFoundException e) {

		} catch (IOException e) {

		}
		//These two function upon initilization ,will search through the databases and look for the highest
		//Member number, it will then be saved so a new member number can then be created.
		ArrayList<Entry> entries = mProviderRecords.searchEntry();
		if (entries.size() > 0) {
			providerCounter = Integer.parseInt(entries.get(entries.size()-1).getNumber())+1;
		}
		else {
			providerCounter = 0;
		}
		
		entries = mMemberRecords.searchEntry();
		if (entries.size() > 0) {
			memberCounter = Integer.parseInt(entries.get(entries.size()-1).getNumber())+1;
		}
		else {
			memberCounter = 0;
		}
		
		providerDirectory = new ProviderDirectory();
		providerDirectory.addService("000000", "Physical Therapy", 75);
		providerDirectory.addService("598470", "Session with Dietion", 160);
		providerDirectory.addService("883948", "Aerobics Exercise Session", 35);
			
	}
	
	//MAIN FUNCTION HAS BEEN MOVED TO ActorTest.java SO A CHOCAN OBJECT COULD BE CREATED
	/**
	 * Starts up the terminal for a user to access the different things. 
	 */
	public void startTerminal() {
		MainMenu mainMenu = new MainMenu(this);
		mMainMenu = mainMenu;
		 
	}
	
	/**
	 * Adds the record to the respective record area. 
	 * @param entry - The entry to add
	 * @param menu - The menu from which it came
	 */
	
	public void addRecord(Entry entry, String menu) {
		
		//Creates a temp entry to make sure that will be stored as a new object in Records
		
		//Adds to the correct Records Set, depending on what menu invoked the call
		if (menu.equals("Provider")) {
			mProviderRecords.addEntry(entry);
		}
		else if (menu.equals("Member")) {
			mMemberRecords.addEntry(entry);
			
		}
	}
	
/**
 * Updates the record in the respective record
 * @param entry - The entry that was updated to replace
 * @param index - The location in which the entry is placed
 * @param menu - The menu from which the menu is invoked
 */
	public void updateRecord(Entry entry, int index, String menu) {
		if (menu.equals("Provider")) {
			mProviderRecords.updateEntry(entry, index);
		}
		else if (menu.equals("Member")) {
			mMemberRecords.updateEntry(entry, index);
		}
	}
	/**
	 * Removes the records from the respective record
	 * @param index - The index that the one is going to be removed
	 * @param menu - The menu from which the method was called
	 */
	public void removeRecord(int index, String menu){
		if (menu.equals("Provider")) {
			mProviderRecords.removeEntry(index);
		}
		else if (menu.equals("Member")) {
			mMemberRecords.removeEntry(index);
		}
	}
	
	/**
	 * The main transaction to be run
	 */
	public void runMainTransaction() {
		createMemberReports(1);
		createProviderReports(1);
		createSummaryReport(1);
		
	}
	
	/**
	 * Creates the member report from the member Directory
	 * @param flag - If 1, no human input is needed, if 0, then human input will be needed
	 */
	public void createMemberReports(int flag) {
		MemberReport tempReport = new MemberReport( mMemberRecords);
		ArrayList<String> tempReports = tempReport.generateReport();
		
		//Get Current Date to save the stuff
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		String dir = "";
		
		if ( flag == 1){
			dir = Paths.get(".").toAbsolutePath().normalize().toString()+"/memberRecords";
			File f = new File(dir);
			f.mkdir();
			int returnVal = JFileChooser.APPROVE_OPTION;
			
			for (int i = 0; i < tempReports.size(); i++){
				//Get the name for the file name
				String[] lines = tempReports.get(i).split(System.getProperty("line.separator"));
				String fileName = lines[0] + "_"  + currentDate; //Make the file printable
				String fileNameAdjusted = fileName.replaceAll(" ", "_"); //Put the files into the directory chosen
				File file = new File(dir+"/" + fileNameAdjusted+".txt");
				if (returnVal == JFileChooser.APPROVE_OPTION){
					try{
						FileWriter writer = new FileWriter(file);
						writer.write(tempReports.get(i));
						writer.flush();
						writer.close();
					}
					catch(FileNotFoundException e1){

					}
					catch(IOException e2){
				}
				}
			}
			
		}
		
		else {
			
			int returnVal = 0;
			
			JFileChooser fileChooser = new JFileChooser();
			
			//Set up the file chooser to make sure a dir is chosen
			fileChooser.setCurrentDirectory(new java.io.File("."));
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setDialogTitle("Choose a Directory to save the files");
		    fileChooser.setAcceptAllFileFilterUsed(false);
		
		    returnVal = fileChooser.showSaveDialog(mMainMenu);
		  
		    try {
			dir = fileChooser.getSelectedFile().getAbsolutePath();
		    } catch (NullPointerException e) {
		    	return;
		    }
		    
		    for (int i = 0; i < tempReports.size(); i++){
				//Get the name for the file name
				String[] lines = tempReports.get(i).split(System.getProperty("line.separator"));
				String fileName = lines[0] + "_"  + currentDate; //Make the file printable
				String fileNameAdjusted = fileName.replaceAll(" ", "_"); //Put the files into the directory chosen
				File file = new File(dir+"/" + fileNameAdjusted+".txt");
				if (returnVal == JFileChooser.APPROVE_OPTION){
					try{
						FileWriter writer = new FileWriter(file);
						writer.write(tempReports.get(i));
						writer.flush();
						writer.close();
					}
					catch(FileNotFoundException e1){

					}
					catch(IOException e2){

					}
				}
			}
		}
		
		
	}
	/**
	 * Creates the provider report from the member Directory
	 * @param flag - If 1, no human input is needed, if 0, then human input will be needed
	 */
	public void createProviderReports(int flag) {
		ProviderReport tempReport = new ProviderReport( mProviderRecords);
		ArrayList<String> tempReports = tempReport.generateReport();
		
		 //Get Current Date to save the stuff
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		String dir = "";
		
		int returnVal = 0;
		if(flag == 1) {
			EFTList newEFT = new EFTList();
			dir = Paths.get(".").toAbsolutePath().normalize().toString()+"/providerRecords";
			File f = new File(dir);
			f.mkdir();
			returnVal = JFileChooser.APPROVE_OPTION;
			
			for (int i = 0; i < tempReports.size(); i++){
				//Get the name for the file name
				String[] lines = tempReports.get(i).split(System.getProperty("line.separator"));
				String name = lines[0]; //Grabs the name of the provider
				String number = lines[1]; //Grabs the provider number
				String price = lines[lines.length - 1];
				String fileName = lines[0] + "_"  + currentDate; //Make the file printable
				String fileNameAdjusted = fileName.replaceAll(" ", "_"); //Put the files into the directory chosen
				File file = new File(dir+"/" + fileNameAdjusted+".txt");
				if (returnVal == JFileChooser.APPROVE_OPTION){
					try{
						FileWriter writer = new FileWriter(file);
						writer.write(tempReports.get(i));
						writer.flush();
						writer.close();
					}
					catch(FileNotFoundException e1){		}
					catch(IOException e2){					}
					newEFT.AddEFTData(name, number, price); //Keeps adding the new data into the EFT list with each provider
				}
			}
			//Grabs the cretaed EFT string
			String eftReport = newEFT.getEFTData();
			File file = new File(dir+"/" + "EFT_DATA" + ".txt");
			try{
				FileWriter writer = new FileWriter(file);
				writer.write(eftReport);
				writer.flush();
				writer.close();
			}
			catch(FileNotFoundException e1){		}
			catch(IOException e2){					}
			
		}
		
		else {
			JFileChooser fileChooser = new JFileChooser();
			
			//Set up the file chooser to make sure a dir is chosen
			fileChooser.setCurrentDirectory(new java.io.File("."));
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setDialogTitle("Choose a Directory to save the files");
		    fileChooser.setAcceptAllFileFilterUsed(false);
	
		    returnVal = fileChooser.showSaveDialog(mMainMenu);
		  
		    try {
			dir = fileChooser.getSelectedFile().getAbsolutePath();
		    } catch (NullPointerException e) {
		    	return;
		    }
			
			for (int i = 0; i < tempReports.size(); i++){
	
				if (returnVal == JFileChooser.APPROVE_OPTION){
					//Get the name for the file name
					String[] lines = tempReports.get(i).split(System.getProperty("line.separator"));
					String fileName = lines[0] + "_"  + currentDate; //Make the file printable
					String fileNameAdjusted = fileName.replaceAll(" ", "_"); //Put the files into the directory chosen
					File file = new File(dir+"/" + fileNameAdjusted+".txt");
					try{
						FileWriter writer = new FileWriter(file);
						writer.write(tempReports.get(i));
						writer.flush();
						writer.close();
					}
					catch(FileNotFoundException e1){
	
					}
					catch(IOException e2){
	
					}
				}
			}
		}
	}
	/**
	 * Creates the summary report from the summary Directory
	 * @param flag - If 1, no human input is needed, if 0, then human input will be needed
	 */
	public void createSummaryReport(int flag) {
		SummaryReport summaryReport = new SummaryReport(mProviderRecords);
		String summary = summaryReport.generateReport();
		String dir = "";
		
		if (flag == 1) {
			dir = Paths.get(".").toAbsolutePath().normalize().toString()+"/summaryRecord";
			File f = new File(dir);
			f.mkdir();
			int returnVal = JFileChooser.APPROVE_OPTION;
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = new File(dir + "/SummaryReport" + ".txt");
				try{
					FileWriter writer = new FileWriter(file);
					writer.write(summary);
					writer.flush();
					writer.close();
				}
				catch(FileNotFoundException e1) {
					
				}
				catch(IOException e2){
					
				}
			}
		}
		
		else {
			JFileChooser fileChooser = new JFileChooser();
			int returnVal = fileChooser.showSaveDialog(mMainMenu);
			
			
			try {
				dir = fileChooser.getSelectedFile().getAbsolutePath();
			    } catch (NullPointerException e) {
			    	return;
			    }
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = new File(dir + ".txt");
				try{
					FileWriter writer = new FileWriter(file);
					writer.write(summary);
					writer.flush();
					writer.close();
				}
				catch(FileNotFoundException e1) {
					
				}
				catch(IOException e2){
					
				}
			}
		}
	}
	/**
	 *  Sends the provider driectory to a chosen file
	 * @throws FileNotFoundException - Handles the File Not Found Exception
	 * @throws UnsupportedEncodingException - Handles to UnsupportedEncodingException
	 */
	public void sendProviderDirectory() throws FileNotFoundException, UnsupportedEncodingException {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(mMainMenu);
		String dir = "";
		
		try {
			dir = fileChooser.getSelectedFile().getAbsolutePath();
		    } catch (NullPointerException e) {
		    	return;
		    }
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		providerDirectory.writeToFile(dir);
		}
	}
	
	/**
	 * Checks the member status through the member records
	 * @param number - The memberNumber to check. 
	 * @param automated - Declares whether dialogs are hidden or not, 1 makes human input needed, 0 hides the dialogs
	 * @return Returns a boolean whether 
	 */
	public boolean checkMemberStatus(String number, int automated) {
		if ( automated == 0) {
			for (int i = 0; i < mMemberRecords.searchEntry().size(); i++) {
				if (number.equals(mMemberRecords.searchEntry().get(i).getNumber())) {
					boolean tempBoolean = mMemberRecords.searchEntry().get(i).getStatus();
					if (tempBoolean == true) {
						return true;
					}
					else if (tempBoolean == false) {
						JOptionPane.showMessageDialog(null, "This member is suspended");
						return false;
					}
				}
			}
			JOptionPane.showMessageDialog(null, "Invalid - There is no member associated with that number");
			return false;
		}
		else {
			for (int i = 0; i < mMemberRecords.searchEntry().size(); i++) {
				if (number.equals(mMemberRecords.searchEntry().get(i).getNumber())) {
					boolean tempBoolean = mMemberRecords.searchEntry().get(i).getStatus();
					if (tempBoolean == true) {
						return true;
					}
					else if (tempBoolean == false) {
						return false;
					}
				}
			}
			return false;
		}
		
	}
	
	/**
	 * This checks the provider status 
	 * @param providerNumber - The providerNumber to check
	 * @return Returns whether true or not
	 */
	public boolean checkProviderStatus(String providerNumber) {
		for (int i = 0; i < mProviderRecords.searchEntry().size(); i++) {
			if (providerNumber.equals(mProviderRecords.searchEntry().get(i).getNumber())) {
				return true;
			}
		}
		JOptionPane.showMessageDialog(null, "Invalid Provider Number");

		return false;
	}
	/**
	 * Simulate the card swipe that a terminal would provide
	 */
	public void SimulateCardSwipe(){
		Boolean validProvider = false;
		Boolean validMember = false;
		Boolean dateValidation = false;
		Boolean validServiceCode = false;
		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		String memberNumber = null, providerNumber = null, inputDate = null;
		String serviceCode = null;
		String serviceName = null;
		String serviceFee = null;
		while (validProvider == false) {
			try {
				providerNumber = null;
				providerNumber = JOptionPane.showInputDialog("Please Enter in your Provider Number");
				if (providerNumber == null) {
					break;
				}
				validProvider = checkProviderStatus(providerNumber);
				
			} catch (NullPointerException e) {
				break;
			}
			
		}
		if (validProvider) { 
			while (validMember == false){
				try {
					memberNumber = null;
					memberNumber = JOptionPane.showInputDialog("Please enter in the member number");
					if (memberNumber == null) {
						break;
					}
					validMember = checkMemberStatus(memberNumber, 0);
				} catch (NullPointerException e) {
					break;
				}
			} // End While
		} //EndIF
		if (validMember){
			JOptionPane.showMessageDialog(null, "Validated - The member is active.");
			while (dateValidation == false)
			try {
				inputDate = null;
				inputDate = JOptionPane.showInputDialog("Please enter in date in mm-dd-yyyy format");
				if (inputDate == null) {
					break;
				}
				try {
					formatter.setLenient(false);
					formatter.parse(inputDate);
					dateValidation = true;
				} catch (java.text.ParseException e) {
					JOptionPane.showMessageDialog(null, "Invalid Date - Remember, mm-dd-yyyy");
				}
			} catch (NullPointerException e) {
				break;
			}

		} //eND iF
		
		if (dateValidation) {

			while (validServiceCode == false) {
				
				try {
					serviceCode = null;
					serviceCode = JOptionPane.showInputDialog("Please enter in the Service Code");
					if (serviceCode == null){
						break;
					}
				try {
						serviceName = providerDirectory.getServiceName(serviceCode);
						if(serviceName.equals("Service code not found!")) {
							JOptionPane.showMessageDialog(null, "Error! No service Code");
						}
						else {
						int accept_number = JOptionPane.showConfirmDialog(null, "Is the service " + serviceName + " the service you want?");
						if (accept_number == JOptionPane.YES_OPTION) {
							validServiceCode = true;
						}
						}
					} catch (NullPointerException et){
						JOptionPane.showMessageDialog(null, "That service code doesn't exist");
	
					}
				}
				catch (NullPointerException e) {
				break;
				}
			} 
			
		}
		
		if (validServiceCode) {
			serviceFee = String.format("%d",providerDirectory.getServicePrice(serviceCode));
			String serviceMessage = "The fee is $" + serviceFee;
			JOptionPane.showMessageDialog(null, serviceMessage);
		
			try {
			String commentText = JOptionPane.showInputDialog("Provide any comments");
			} catch (NullPointerException ex) {
				String commentText = "";
			}
			
			

			MemberServices tempService = new MemberServices(inputDate, providerNumber, serviceName);
			ArrayList<Entry> memberEntries = mMemberRecords.searchEntry();
			ArrayList<Entry> providerEntries = mProviderRecords.searchEntry();
			String memberName = null;
			ProviderServices providerService;
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyy HH:mm:ss");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			for (int i = 0; i < memberEntries.size(); i++) {
				if (memberNumber.equals(memberEntries.get(i).getNumber())) {
					Entry tempEntry = memberEntries.get(i);
					tempEntry.AddService(tempService);
					memberName = memberEntries.get(i).getName();
					mMemberRecords.updateEntry(tempEntry, i);
					try {
						saveRecords();
					} catch (IOException e) {
											}
					break;
				}
			}
			for (int i = 0; i < providerEntries.size(); i++) {
				if (providerNumber.equals(providerEntries.get(i).getNumber())) {
					Entry tempProviderEntry = providerEntries.get(i);
					providerService = new ProviderServices(inputDate, currentDate, memberName, memberNumber,serviceCode, serviceFee);
					tempProviderEntry.AddService(providerService);
					mProviderRecords.updateEntry(tempProviderEntry, i);
					try {
						saveRecords();
					} catch (IOException e) {
											}
				}
			}
		}
		
		
	} // End SimulateCard Swipe
		
		
	/**
	 * Returns the providerCounter
	 * @return providerCounter
	 */
	public int getProviderCounter() {
		return providerCounter;
	}
	/**
	 * Returns the memberCounter
	 * @return memberCounter
	 */
	public int getMemberCounter() {
		return memberCounter;
	}
	/**
	 * Sets the ProviderCounter
	 * @param num This is the num to set the provider counter to 
	 */
	public void setProviderCounter(int num) {
		providerCounter = num;
	}
	/**
	 * SEts the memberCoutner
	 * @param num - The num to set the memberCounter to
	 */
	public void setMemberCounter (int num) {
		memberCounter = num;
		
	}
	/**
	 * gets the member Records
	 * @return returns the MemberRecords
	 */
	public Records getmMemberRecords() {
		return mMemberRecords;
	}
	/**
	 * Gets the provider Records
	 * @return Returns the ProviderRecords
	 */
	public Records getmProviderRecords() {
		return mProviderRecords;
	}
	
	/**
	 * Saves the progress coutner
	 * @throws FileNotFoundException - Handles the FileNotFound Exception
	 * @throws IOException - Handles the IOException
	 */
	public void saveRecords() throws FileNotFoundException, IOException {
		String dir = Paths.get(".").toAbsolutePath().normalize().toString();
	    File f = new File(dir+"/member");
	   	if (f.exists()) {
	    	f.delete();
	    }
	    
	    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(dir+"/member"));
	    
	    output.writeObject(mMemberRecords);
	    output.flush();
	    output.close();
	    
	    f = new File(dir+"/provider");
	   	if (f.exists()) {
	    	f.delete();
	    }
	    
	    output = new ObjectOutputStream(new FileOutputStream(dir+"/provider"));
	    
	    output.writeObject(mProviderRecords);
	    output.flush();
	    output.close();
	}
	
	/**
	 * Opens up the records 
	 * @throws FileNotFoundException - Throws the file not found exception
	 * @throws IOException - Throws the ioexception
	 * @throws ClassNotFoundException - throws clsass not found exception
	 */
	
	public void openRecords() throws FileNotFoundException, IOException, ClassNotFoundException {
		String dir = Paths.get(".").toAbsolutePath().normalize().toString();
	   	ObjectInputStream memberInput = new ObjectInputStream(new FileInputStream(dir+"/member"));
	   	ObjectInputStream providerInput = new ObjectInputStream(new FileInputStream(dir+"/provider"));
	    	
	    mProviderRecords = (Records)providerInput.readObject();
	    mMemberRecords = (Records)memberInput.readObject();
	    	
	    providerInput.close();
	    memberInput.close();
	}
	
	public static void main(String[] args){
		ChocAn chocAn = new ChocAn();
		chocAn.startTerminal();
	}

	
}


