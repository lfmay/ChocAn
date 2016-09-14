import java.io.Serializable;

/**
 * Abstract class for storing data for services
 * @author Delaney D'Argenio
 * @version 1.0
 */

public class Services implements Serializable {
	
	protected String date;
	
	/**
	 * Creates an instance of a new service
	 * @param date - date that the class holds
	 */
	public Services(String date){
		this.date = date;
	}
	
	/**
	 * Sets the date the service was provided
	 * @param newDate - new date for the class
	 */
	public void setDate(String newDate){
		date = newDate;
	}
	
	/**
	 * Returns the data the service was provided
	 * @return date - date that the class holds
	 */
	public String getDate(){
		return date;
	}

}
