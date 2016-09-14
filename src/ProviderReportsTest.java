//Author: Kurt Anderson
//JUnit tests the provider reports making sure it saves and whatnot. 

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProviderReportsTest {
	private ChocAn chocAn;
	private String dir;

	@Before
	public void setUp() throws Exception {
		chocAn = new ChocAn();
		dir = Paths.get(".").toAbsolutePath().normalize().toString()+"/providerRecords";

		
		//Create 3 provider Entries
		Entry providerEntry = new Entry("Gold's Gym", "000000000", "230 N ButterflyDrive", "Magnolia", "Delaware", " 18571" );
		Entry providerEntry2 = new Entry("Workout", "000000001", "28 Califlower Lane", "Dover", "Delaware", " 15441" );
		Entry providerEntry3 = new Entry("Rec Center", "000000002", "70 walrus avenue", "Tuscaloosa", "Alabama", "87545" );

		//Create 3 member Entries
		Entry memberEntry = new Entry("Kurt Anderson", "000000000", "230 N ButterflyDrive", "Magnolia", "Delaware", " 85471" );
		Entry memberEntry2 = new Entry("Liam Niesom", "000000001", "19 West Street Califlower Lane", "Wilmington", "Caliornia", " 18741" );
		Entry memberEntry3 = new Entry("Jennifer Lawrence", "000000002", "28 This st", "Baltimore", "California", "87545" );
		
		chocAn.addRecord(providerEntry, "Provider");
		chocAn.addRecord(providerEntry2, "Provider");
		chocAn.addRecord(providerEntry3, "Provider");
		
		chocAn.addRecord(memberEntry, "Member");
		chocAn.addRecord(memberEntry2, "Member");
		chocAn.addRecord(memberEntry3, "Member");
	}

	@After
	public void tearDown() throws Exception {
		File file = new File(dir);
		delete(file);
	}	
	
	public static void delete(File file) {
		if(file.isDirectory()){
			 
    		//directory is empty, then delete it
    		if(file.list().length==0){
    			
    		   file.delete();
//    		   System.out.println("Directory is deleted : " 
//                                                 + file.getAbsolutePath());
    			
    		}else{
    			
    		   //list all the directory contents
        	   String files[] = file.list();
     
        	   for (String temp : files) {
        	      //construct the file structure
        	      File fileDelete = new File(file, temp);
        		 
        	      //recursive delete
        	     delete(fileDelete);
        	   }
        		
        	   //check the directory again, if empty then delete it
        	   if(file.list().length==0){
           	     file.delete();
        	     System.out.println("Directory is deleted : " 
                                                  + file.getAbsolutePath());
        	   }
    		}
    		
    	}else{
    		//if file, then delete it
    		file.delete();
    		System.out.println("File is deleted : " + file.getAbsolutePath());
    	}
    }

	@Test
	public void SavesFileinCorrectDirectoryTestTest(){
		//Tests to make sure the directory was created and is visible
		chocAn.createProviderReports(1);//Should save in this directory under /providerRecords
		File f = new File(dir);
		assertTrue(f.exists());
	}
	
	@Test
	public void SavesCorrectNumberFilesTest() {
		//Makes sure the correct number of reports are saved
		chocAn.createProviderReports(1);
		File f = new File(dir);
		assertEquals("Should equal 4, 3 provider reports along with 1 EFT Report", 4, f.listFiles().length);
	}
	
	@Test
	public void StillSavesWithIncompleteData(){
		//Makes sure that the reports still prints with missing data
		Entry tempEntry = new Entry("", "000000004", "", "Baltimore", "", "87545" );
		chocAn.addRecord(tempEntry, "Provider");
		chocAn.createProviderReports(1);
		File f = new File(dir);
		assertEquals("Should equal 5, 3 original and 1 new report, with 1 EFT report", 5, f.listFiles().length);
	}
	
	@Test
	public void PrintsToFileCorrectly() {
		//Makes sure that the data makes it to the file
		chocAn.createProviderReports(1);

		File f = new File(dir);
		File[] directoryFiles = f.listFiles();
		
		FileReader fileReader;
		String line = null;
		try {
			fileReader = new FileReader(directoryFiles[1]);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			line = bufferedReader.readLine();
			bufferedReader.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
//		System.out.println(line);
		assertTrue(line.equals("Gold's Gym"));
	}
	
	@Test
	public void ReportsCorrectData() {
		//Makes sure the correct data is reported
		Entry tempEntry = new Entry("Bob Williams", "000000004", "563 N Walrus Road", "Baltimore", "Kansas", "87545" );
		chocAn.addRecord(tempEntry, "Provider");
		ProviderReport tempReport = new ProviderReport(chocAn.getmProviderRecords());
		ArrayList<String> tempReports = tempReport.generateReport();
		String[] lines = tempReports.get(3).split(System.getProperty("line.separator"));
		
		chocAn.createProviderReports(1);
		
//		System.out.println(lines[0]);
		assertTrue(lines[0].equals("Bob Williams"));
	}

	@Test 
	public void ReportsCorrectIncompleteData() {
		//Makes sure the correct incomplete data is recorded
		Entry tempEntry = new Entry("", "000000004", "563 N Walrus Road", "Baltimore", "Kansas", "87545" );
		chocAn.addRecord(tempEntry, "Provider");
		ProviderReport tempReport = new ProviderReport(chocAn.getmProviderRecords());
		ArrayList<String> tempReports = tempReport.generateReport();
		String[] lines = tempReports.get(3).split(System.getProperty("line.separator"));
		
		chocAn.createProviderReports(1);
		
//		System.out.println(lines[0]);
		assertTrue(lines[0].equals(""));
	}
	
	@Test
	public void OnlyReportsWithinOneWeek() {
		//Makes sure the services printed are only within 1 week
		ArrayList<Entry> tempRecords = chocAn.getmProviderRecords().searchEntry();
		ProviderServices providerService = new ProviderServices("11-8-2015","11-20-2015", "Kurt Anderson", "000000000", "000000", "75"); 
		Entry tempEntry = tempRecords.get(0);
		tempEntry.AddService(providerService);
		chocAn.updateRecord(tempEntry, 0, "Provider");
		ProviderReport tempReport = new ProviderReport(chocAn.getmProviderRecords());
		ArrayList<String> tempReports = tempReport.generateReport();
		String report = tempReports.get(0);
		String[] lines = tempReports.get(0).split(System.getProperty("line.separator"));
		
		chocAn.createProviderReports(1);
//		System.out.println(report);
//		System.out.println(String.format("%d", lines.length));
		assertTrue(lines.length < 11);
	}
	
	@Test 
	public void WritesCorrectService() {
		//Makes sure the data entered in makes it to the form
		ArrayList<Entry> tempRecords = chocAn.getmProviderRecords().searchEntry();
		
		//Grabs current date so the test can be performed at anytime
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String todate = dateFormat.format(date);
		
		ProviderServices providerService = new ProviderServices(todate,"11-20-2015", "Kurt Anderson", "000000000", "000000", "75"); 
		Entry tempEntry = tempRecords.get(0);
		tempEntry.AddService(providerService);
		chocAn.updateRecord(tempEntry, 0, "Provider");
		ProviderReport tempReport = new ProviderReport(chocAn.getmProviderRecords());
		ArrayList<String> tempReports = tempReport.generateReport();
		String report = tempReports.get(0);
		String[] lines = tempReports.get(0).split(System.getProperty("line.separator"));
		
		chocAn.createProviderReports(1);
//		System.out.println(report);
//		System.out.println(String.format("%d", lines.length));
		assertTrue(lines[9].equals("11-20-2015"));
	}

}
