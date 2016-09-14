//Author: Nate Hochstetler
//JUnit testing for the save records method in ChocAn

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class SaveTest {
	private String mDir;
	private String pDir;
	private File mFile;
	private File pFile;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		mDir = Paths.get(".").toAbsolutePath().normalize().toString() + "/member";
		pDir = Paths.get(".").toAbsolutePath().normalize().toString() + "/provider";
		mFile = new File(mDir);
		pFile = new File(pDir);
		
		Records pRecords = new Records("Provider");
		
		ObjectOutputStream pOutput = new ObjectOutputStream(new FileOutputStream(pDir));
		pOutput.writeObject(pRecords);
		pOutput.flush();
		pOutput.close();
		
		Records mRecords = new Records("Member");
		
		ObjectOutputStream mOutput = new ObjectOutputStream(new FileOutputStream(mDir));
		mOutput.writeObject(mRecords);
		mOutput.flush();
		mOutput.close();
	}
	
	
	@Test
	public void testProviderSave() throws IOException, ClassNotFoundException {
		pFile.delete();
		Records pRecords = new Records("Provider");
		
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(pDir));
		output.writeObject(pRecords);
		output.flush();
		output.close();
		
		assertTrue( pFile.exists());
	}
	
	@Test
	public void testMemberSave() throws IOException, ClassNotFoundException {
		mFile.delete();
		Records mRecords = new Records("Member");
		
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(mDir));
		output.writeObject(mRecords);
		output.flush();
		output.close();
		
		assertTrue( mFile.exists());
	}
	
	@Test
	public void testIfProviderFileExists() {
		assertTrue( pFile.exists());
	}
	
	@Test
	public void testIfMemberFileExists() {
		assertTrue( mFile.exists());
	}
	
	@Test
	public void testIfProviderFileDeletes() {
		pFile.delete();
		assertFalse(pFile.exists());
	}
	
	@Test
	public void testIfMemberFileDeletes() {
		mFile.delete();
		assertFalse(mFile.exists());
	}

}
