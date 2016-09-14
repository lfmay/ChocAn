import java.util.ArrayList;

/**
 * The class for storing Member Entries, inherited from the Entry class
 * @author Nate Hochstetler
 * @version 1.0
 */

public class MemberEntry extends Entry {
	
	private boolean status;
	
	/**
	 * Creates a new Member Entry with the designated parameters
	 * @param name of Member Entry
	 * @param number of Member Entry
	 * @param streetAddress of Member Entry
	 * @param city of Member Entry
	 * @param state of Member Entry
	 * @param zipCode of Member Entry
	 */
	public MemberEntry(String name, String number, String streetAddress, String city, String state, String zipCode) {
		super(name, number, streetAddress, city, state, zipCode);
		status = true;
	}
	
	/**
	 * Changes the status of the Member Entry
	 * @param newStatus is the new status of the Member Entry
	 */
	public void changeStatus(boolean newStatus) {
		status = newStatus;
	}
	
	/**
	 * Returns the current status of the Member Entry
	 * @return status of the Member Entry
	 */
	public boolean getStatus() {
		return status;
	}
}
