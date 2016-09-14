import java.util.ArrayList;

/**
 * The class for storing Provider Entries, inherited from the Entry class
 * @author Nate Hochstetler
 * @version 1.0
 */

public class ProviderEntry extends Entry {

	private int numOfConsultations;
	private String totalFee;
	
	/**
	 * Creates a new Provider Entry with the designated parameters, where number of consultations starts at 0
	 * @param name of Provider Entry
	 * @param number of Provider Entry
	 * @param streetAddress of Provider Entry
	 * @param city of Provider Entry
	 * @param state of Provider Entry
	 * @param zipCode of Provider Entry
	 */
	public ProviderEntry(String name, String number, String streetAddress, String city, String state, String zipCode) {
		super(name, number, streetAddress, city, state, zipCode);
		numOfConsultations = 0;
		
	}
	
	/**
	 * Increments number of consultations by 1
	 */
	public void incrementNumConsultations() {
		numOfConsultations += 1;
	}

}
