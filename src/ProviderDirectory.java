import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The class representing the ChocAn 'Provider Directory'
 * @author Crawford King
 */

public class ProviderDirectory {
	
	private ArrayList<String[]> directory;
	
	/**
	 * Constructor for class
	 */
	public ProviderDirectory() {
		this.directory = new ArrayList<String[]>();
	}
	
	
	/**
	 * Adds a new service to the directory
	 * @param serviceCode Six digit code tied to service
	 * @param servicePrice Price of service
	 * @param serviceName Name of service
	 */
	public void addService(String serviceCode, String serviceName, int servicePrice) {
		
		String[] temp = new String[3];
		temp[0] = serviceCode;
		temp[1] = serviceName;
		temp[2] = Integer.toString(servicePrice);
		
		directory.add(temp);
	}
	
	/**
	 * Returns service name for given service code.
	 * @param serviceCode Six digit code corresponding to a particular service
	 * @return String name of service
	 */
	public String getServiceName(String serviceCode) {
		for (int i = 0; i < directory.size(); i++) {
			if (directory.get(i)[0].matches(serviceCode)) {
				return directory.get(i)[1];
			}
		}
				return "Service code not found!";
	}
	
	/**
	 * Returns service price for given service code.
	 * @param serviceCode Six digit code corresponding to a particular service
	 * @return int of fee
	 */
	public int getServicePrice(String serviceCode) {
		for (int i = 0; i < directory.size(); i++) {
			if (directory.get(i)[0].matches(serviceCode)) {
				return Integer.parseInt(directory.get(i)[2]);
			}
		}
				return 0;
	}
	
	/**
	 * Returns service code for given service name.
	 * @param serviceName Six digit code corresponding to a particular service
	 * @return String of service code
	 */
	public String getServiceCode(String serviceName) {
		for (int i = 0; i < directory.size(); i++) {
			if (directory.get(i)[1].matches(serviceName)) {
				return directory.get(i)[0];
			}
		}
				return "Service name not found!";
	}
	
	/**
	 * Writes a formatted text file of all service codes and names to current directory.
	 * This is the 'placebo' for Provider Directory being emailed to a provider.
	 * @param dir Gives the location where to save the file
	 * @throws FileNotFoundException handles FileNotFoundException 
	 * @throws UnsupportedEncodingException handles UnsupportedEncodingException
	 */
	
	public void writeToFile(String dir) throws FileNotFoundException, UnsupportedEncodingException {
		
		Collections.sort(directory,new Comparator<String[]>() {
            public int compare(String[] strings, String[] otherStrings) {
                return strings[1].compareTo(otherStrings[1]);
            }
        });
		
		PrintWriter writer = new PrintWriter(dir, "UTF-8");
		String format = "%-35s %s %35s %n";
		writer.printf(format, "------------", "------------","------------");	
		writer.printf(format, "Service Name", "Service Code", "Service Fee");
		writer.printf(format, "------------", "------------","------------");		
		format = "%-38s %s %35s %n";
		
		for (int i = 0; i < directory.size(); i++) {
			writer.printf(format, directory.get(i)[1], directory.get(i)[0], "$" + directory.get(i)[2]);
		}
		writer.close();
	}
	

}
