import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * The class for storing Entries
 * @author Nate Hochstetler
 * @version 1.0
 */

public class Entry implements Serializable {

	private String name;
	private String number;
	private String streetAddress;
	private String city;
	private String state;
	private String zipCode;
	private ArrayList<Services> services;
	private boolean status;
	
	/**
	 * Creates a new Entry with the designated parameteres
	 * @param name of Entry
	 * @param number of Entry
	 * @param streetAddress of Entry
	 * @param city of Entry
	 * @param state of Entry
	 * @param zipCode of Entry
	 */
	public Entry(String name, String number, String streetAddress, String city, String state, String zipCode) {
		this.name = name;
		this.number = number;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		services = new ArrayList<Services>();
		status = true;
	}
	
	/**
	 * Sets the name of the entry
	 * @param newName is the new name to be given to the entry
	 */
	public void setName(String newName) {
		name = newName;
	}
	
	/**
	 * Returns the name of the entry
	 * @return name of the entry
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the number of the entry
	 * @param newNumber is the new number to be given to the entry
	 */
	public void setNumber(String newNumber) {
		number = newNumber;
	}
	
	/**
	 * Returns the number of the entry
	 * @return number of the entry
	 */
	public String getNumber() {
		return number;
	}
	
	/**
	 * Sets the address of the entry
	 * @param newAddress is the new address to be given to the entry
	 */
	public void setAddress(String newAddress) {
		streetAddress = newAddress;
	}
	
	/**
	 * Returns the address of the entry
	 * @return address of the entry
	 */
	public String getAddress() {
		return streetAddress;
	}
	
	/**
	 * Sets the city of the entry
	 * @param newCity is the new city to be given to the entry
	 */
	public void setCity(String newCity) {
		city = newCity;
	}
	
	/** 
	 * Returns the city of the entry
	 * @return city of the entry
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Sets the state of the entry
	 * @param newState is the new state to be given to the entry
	 */
	public void setState(String newState) {
		state = newState;
	}
	
	/**
	 * Returns the state of the entry
	 * @return state of the entry
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the zip code of the entry
	 * @param newZip is the new zip code to be given to the entry
	 */
	public void setZip(String newZip) {
		zipCode = newZip;
	}
	
	/**
	 * Returns the zip code of the entry
	 * @return zip code of the entry
	 */
	public String getZip() {
		return zipCode;
	}
	
	/**
	 * Adds a service to services
	 * @param service is the entry to be added to services
	 */
	public void AddService(Services service){
		//Introduces the date type to be compared
		String date = service.getDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date1 = null, date2 = null;
		int stopFlag = 0; //Makes sure to add the date if it is the newest date and doesn't find a comparision in the already added dates. 
	
		//Takes the date of the to be added service
		try {
			date1 = dateFormat.parse(date);
		} catch (ParseException e) {
		
		}
		
		//Compares it against the list of services that have already been added to place in chronological order, going from oldest to newest. 
		for (int i = 0; i < services.size(); i++) {
			Services tempService = services.get(i);
			try {
				date2 = dateFormat.parse(tempService.getDate());
			} catch (ParseException e) {
			
			}
			if (date1.before(date2)) {
				services.add(i, service); //Adds it to the position that it is before. 
				stopFlag = 1;
				break;
			}
			
		}
		if (stopFlag != 1){
			services.add(service); // The service was not added as it was not before anything in the list, therefore add it to the end. 
		}
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
	 * @return current status of the Member Entry
	 */
	public boolean getStatus() {
		return status;
	}
	
	/**
	 * Returns the services ArrayList
	 * @return services ArrayList
	 */
	public ArrayList<Services> searchServices() {
		return services;
	}
}
