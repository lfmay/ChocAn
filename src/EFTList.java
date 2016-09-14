import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

/**
 * The class for writing EFT Data to disk
 * @author Crawford King
 */
public class EFTList {
	
	private String EFTData;
	
	
	/**
	 * Creates a new EFTList object for writing data to disk
	 */
	public EFTList() {
		EFTData = "";
	}
	
	/**
	 * Sets the number of the entry
	 * @return returns the String that is written to file
	 */
	public String getEFTData() {
		return this.EFTData;
	}
	
	/**
	 * Sets the number of the entry
	 * @param name Name of service provided
	 * @param number 6 digit number of service provided
	 * @param cost Cost of service provided
	 */
	public void AddEFTData(String name, String number, String cost) {
		EFTData += name + " "+ number + "" + cost + "\r\n";
	
	/**
	 * Writes EFTData String to new file. Creates new text file in working directory stamped with data and time.
	 */
	

	}
	public void createEFTReport() throws FileNotFoundException, UnsupportedEncodingException {
		
		// Instantiate a Date object for filename
        Date date = new Date();
        String filename = "EFT_Data_" + date.toString() + ".txt";
        
		
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		writer.println(this.getEFTData());
		writer.close();
		
	}
	

}
