import java.io.Serializable;

/**
 * Class for storing data for a member service
 * @author Delaney D'Argenio
 * @version 1.0
 */

public class MemberServices extends Services implements Serializable {
	
	private String providerName;
	private String serviceName;
	
	/**
	 * Creates a new provider service
	 * @param date - the date that the class holds
	 * @param providerName - provider name that the class holds
	 * @param serviceName - service name that the class holds
	 */
	public MemberServices(String date, String providerName, String serviceName){
		super(date);
		this.providerName = providerName;
		this.serviceName = serviceName;
	}
	
	/**
	 * Returns the name of the provider
	 * @return providerName-the name of the provider
	 */
	public String getProviderName(){
		return providerName;
	}
	
	/**
	 * Returns the name of the service
	 * @return serviceName- the name of the service
	 */
	public String getServiceName(){
		return serviceName;
	}

}
