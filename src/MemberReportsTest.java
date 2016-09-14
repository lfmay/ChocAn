//Author: Delaney D'Argenio
//JUnit testing for the CreateMemberReports() method in ChocAn
import static org.junit.Assert.*;

import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class MemberReportsTest {
	private ChocAn chocAn;
	private String dir;
		
	@Before
	public void setUp() throws Exception{
		chocAn = new ChocAn();
		dir = Paths.get(".").toAbsolutePath().normalize().toString()+"/memberRecords";
	
		//Create 3 member entries to test with
		Entry memberEntry1 = new Entry("Delaney D'Argenio","000000000","123 C Drive","Atlanta","Georgia","39462");
		Entry memberEntry2 = new Entry("Kurt Anderson","000000001","456 C++ Drive","San Diego","California","90832");
		Entry memberEntry3 = new Entry("Laura May","000000002","789 C# Drive","Seattle","Washington","34527");
		
		chocAn.addRecord(memberEntry1,"Member");
		chocAn.addRecord(memberEntry2,"Member");
		chocAn.addRecord(memberEntry3,"Member");
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
	public void SavesFileInCorrectDirectoryTest() {
		chocAn.createMemberReports(1);
		File f = new File(dir);
		assertTrue(f.exists());
	}
	
	@Test
	public void SavesFileWithCorrectName(){
		chocAn.createMemberReports(1);
		File f = new File(dir);
		assertEquals("Should be memberRecords","memberRecords",f.getName());
	}
	
	@Test
	public void SavesCorrectNumberFilesTest(){
		chocAn.createMemberReports(1);
		File f = new File(dir);
		assertEquals("Should equal 3 for the 3 member reports",3, f.listFiles().length);
	}
	
	@Test
	public void SavesCorrectNumberAfterAddMemberTest(){
		Entry tempEntry = new Entry("P.Sherman","000000004","42 Wallaby Way","Sydney","Australia","12345");
		chocAn.addRecord(tempEntry,"Member");
		chocAn.createMemberReports(1);
		File f = new File(dir);
		assertEquals("Should equal 4 for 3 original and 1 new report",4,f.listFiles().length);
	}
	
	@Test
	public void StillSavesWithIncompleteDataTest(){
		Entry tempEntry = new Entry("","000000004","123 Testing Way","Tuscaloosa","","");
		chocAn.addRecord(tempEntry,"Member");
		chocAn.createMemberReports(1);
		File f = new File(dir);
		assertEquals("Should equal 4 for 3 original and 1 new report",4,f.listFiles().length);
	}
	
	@Test
	public void SavesCorrectNumberAfterRemoveMemberTest(){
		chocAn.removeRecord(0,"Member");
		chocAn.createMemberReports(1);
		File f = new File(dir);
		assertEquals("Should equal 2 for the 2 remaining member reports",2,f.listFiles().length);
	}
	
	@Test
	public void PrintsCorrectDataTest(){
		MemberReport tempReport = new MemberReport(chocAn.getmMemberRecords());
		ArrayList<String> tempReports = tempReport.generateReport();
		String[] line = tempReports.get(0).split(System.getProperty("line.separator"));
		chocAn.createMemberReports(1);
		assertTrue(line[0].equals("Delaney D'Argenio"));
	}
}
