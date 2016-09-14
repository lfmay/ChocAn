//Author: Crawford King
//JUnit testing for the CheckProviderStatus() method in ChocAn
import static org.junit.Assert.*;

import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class MemberStatusTest {
	private ChocAn chocAn;
	private String dir;
		
	@Before
	public void setUp() throws Exception{
		chocAn = new ChocAn();
		dir = Paths.get(".").toAbsolutePath().normalize().toString()+"/memberRecords";
	
		//Create 3 member entries to test with
		Entry memberEntry1 = new MemberEntry("Delaney D'Argenio","000000000","123 C Drive","Atlanta","Georgia","39462");
		Entry memberEntry2 = new MemberEntry("Kurt Anderson","000000001","456 C++ Drive","San Diego","California","90832");
		
		Entry memberEntry3 = new MemberEntry("Laura May","000000002","789 C# Drive","Seattle","Washington","34527");
		Entry memberEntry4 = new MemberEntry("Crawford King","000000005","7 Java Drive","Panama City Beach","Florida","55555");
		memberEntry3.changeStatus(false);
		memberEntry4.changeStatus(false);
		
		chocAn.addRecord(memberEntry1,"Member");
		chocAn.addRecord(memberEntry2,"Member");
		chocAn.addRecord(memberEntry3,"Member");
		chocAn.addRecord(memberEntry4,"Member");
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
	public void CorrectlyReturnsTrueNumber1() {
		assertTrue(chocAn.checkMemberStatus("000000000", 1));
	}
	
	@Test
	public void CorrectlyReturnsTrueNumber2() {
		assertTrue(chocAn.checkMemberStatus("000000001", 1));
	}
	
	@Test
	public void CorrectlyReturnsFalseNumber1(){
		assertFalse(chocAn.checkMemberStatus("00000002" , 1));
	}
	
	@Test
	public void CorrectlyReturnsFalseNumber2(){
		assertFalse(chocAn.checkMemberStatus("00000005", 1));
	}
	

}
