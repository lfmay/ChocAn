//Author: Laura May
//JUnit testing for the CreateSummayReport() method in ChocAn

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;



public class SummaryReportTest {

	private ChocAn chocAn;
	private String dir;
	
	@Before
	public void setUp() throws Exception {
		chocAn = new ChocAn();
		dir = Paths.get(".").toAbsolutePath().normalize().toString()+"/summaryRecord";
		
		//Creates 4 provider entries
		Entry pEntry0 = new Entry("Laura May","000000000", "2765 Hi Place", "Zionsville", "Indiana","46077");
		Entry pEntry1 = new Entry("Bob Smith","000000001", "1234 Hello Ave","Tuscaloosa","Alabama","35401");
		Entry pEntry2 = new Entry("Julia Myers","000000002","3345 Bonjour Blvd","Annapolis","Maryland","21412");
		Entry pEntry3 = new Entry("Michael Jones","000000003","6683 Hola Street","Haubstadt","Indiana","47639");
		
		//Creates 3 member entries
		Entry mEntry0 = new Entry("Harriet Miller","000000000", "9786 Bye Place", "Zionsville", "Indiana","46077");
		Entry mEntry1 = new Entry("Joe Martin","000000001", "7936 Goodbye Ave","Tuscaloosa","Alabama","35401");
		Entry mEntry2 = new Entry("Hannah Thomas","000000002","9827 Bonjour Blvd","Annapolis","Maryland","21412");

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String todate = dateFormat.format(date);
		
		//Creates 5 services
		ProviderServices service0 = new ProviderServices(todate,"10-17-2015","Harriet Miller", "000000000","598470","160.00");
		ProviderServices service1 = new ProviderServices(todate,"9-17-2015","Harriet Miller", "000000000","598470","160.00");
		ProviderServices service2 = new ProviderServices(todate,"11-01-2015","Joe Martin", "000000001","598470","160.00");
		ProviderServices service3 = new ProviderServices(todate,"04-12-2015","Hannah Thomas", "000000002","883948","35.00");
		ProviderServices service4 = new ProviderServices(todate,"12-19-2015","Harriet Miller", "000000000","883948","35.00");
		
		//Adds the services to providers
		pEntry0.AddService(service0);
		pEntry0.AddService(service1);
		pEntry1.AddService(service2);
		pEntry3.AddService(service3);
		pEntry0.AddService(service4);
		
		//Adds the records to ChocAn
		chocAn.addRecord(pEntry0, "Provider");
		chocAn.addRecord(pEntry1, "Provider");
		chocAn.addRecord(pEntry2, "Provider");
		chocAn.addRecord(pEntry3, "Provider");
		
		chocAn.addRecord(mEntry0, "Member");
		chocAn.addRecord(mEntry1, "Member");
		chocAn.addRecord(mEntry2, "Member");
		
	}
	
	@After 
	public void tearDown() throws Exception{
		File file = new File(dir);
		delete(file);
	}
	public static void delete(File file){
		if(file.isDirectory()){
			if(file.list().length==0){
			
				file.delete();
				System.out.println("Directory is deleted: " + file.getAbsolutePath());
			}
			else{
				String files[] = file.list();
				for(String temp : files){
					File fileDelete = new File(file,temp);
					delete(fileDelete);
				}
				if(file.list().length == 0){
					file.delete();
					System.out.println("Directory is deleted : " + file.getAbsolutePath());
				}
			}
		}
		else{
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
	}
	
	@Test
	public void SavesFileinCorrectLocationTest(){
		chocAn.createSummaryReport(1);
		File f = new File(dir);
		assertTrue(f.exists());
	}
	
	@Test
	public void SavesFilewithCorrectName(){
		chocAn.createSummaryReport(1);
		File f = new File(dir);
		assertEquals("Should be summaryRecord", "summaryRecord",f.getName());
	}
	
	@Test
	public void SavesCorrectNumberFilesTest() {
		chocAn.createSummaryReport(1);
		File f = new File(dir);
		assertEquals("Should equal 1", 1, f.listFiles().length);
	}
	
	
	@Test
	public void ReportsCorrectDataTest() {
		chocAn.createSummaryReport(1);

		File f = new File(dir);
		File[] directoryFiles = f.listFiles();
		
		FileReader fileReader;
		String line = null;
		
		try {
			fileReader = new FileReader(directoryFiles[0]);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			line = bufferedReader.readLine();
			bufferedReader.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} 
		assertTrue(line.equals("Laura May"));
	}
	
	@Test
	public void ReportsCorrectCalculationsTest() {
		chocAn.createSummaryReport(1);

		File f = new File(dir);
		File[] directoryFiles = f.listFiles();
		
		FileReader fileReader;
		String line = null;
		
		try {
			fileReader = new FileReader(directoryFiles[0]);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			line = bufferedReader.readLine();
			line = bufferedReader.readLine();
			for( int i = 0; i < 14; i++){
				line = bufferedReader.readLine(); 
			}
			
			bufferedReader.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} 
		assertTrue(line.equals("Overall Fee This Week: $550.000000"));
	}
	


}
