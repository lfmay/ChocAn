import java.io.Serializable;

/**
 * Class for storing data for a provider service
 * @author Delaney D'Argenio
 * @version 1.0
 */

public class ProviderServices extends Services implements Serializable {
	
	private String mDate;
	private String dateReceived;
	private String memberName;
	private String memberNumber;
	private String serviceCode;
	private String amount;
	
	/**
	 * Creates a new provider service
	 * @param date- date that the class holds
	 * @param dateReceived - date received that the class holds
	 * @param memberName - member name that the class holds
	 * @param memberNumber - member number that the class holds
	 * @param serviceCode - service code that the class holds
	 * @param amount - amount that the class holds
	 */
	public ProviderServices(String date,String dateReceived,String memberName, String memberNumber, String serviceCode, String amount){
		super(date);
		this.date = date;
		this.dateReceived = dateReceived;
		this.memberName = memberName;
		this.memberNumber = memberNumber;
		this.serviceCode = serviceCode;
		this.amount = amount;
	}
	
	/**
	 * Returns the date the data was received by the computer
	 * @return dateReceived - the date received that the class holds
	 */
	public String getDateReceived(){
		return dateReceived;
	}
	
	
	/**
	 * Returns the members name
	 * @return memberName- the member name that the class holds
	 */
	public String getMemberName(){
		return memberName;
	}
	
	/**
	 * Returns the member number
	 * @return memberNumber - the member number that the class holds
	 */
	public String getMemberNumber(){
		return memberNumber;
	}
	
	/**
	 * Returns the service code of the service provided
	 * @return serviceCode -the service code that the class holds
	 */
	public String getServiceCode(){
		return serviceCode;
	}

	/**
	 * Returns the fee to be paid
	 * @return amount - the amount that the class holds
	 */
	public String getAmount(){
		return amount;
	}
}
