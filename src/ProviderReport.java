import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/** 
* Class for creating Provider Reports, inherited from the Reports class
 * 
 * @author Laura May
 * @version 1.0
*/

public class ProviderReport extends Report implements Serializable{
	
	private int mRecordsLength;
	private int mServicesLength;
	private int i;
	private String mProviderService;
	
	private ArrayList<Entry> mEntries;
	private ArrayList<Services> mServices;
	private ArrayList<String> mReports;
	
	private Records mProviderRecord;
	
	/**
	 * Creates a new Provider Report
	 * @param providerRecords - the record of all the providers' information
	 */
	public ProviderReport(Records providerRecords){
		super(providerRecords);
		mProviderRecord = providerRecords;
	}
	
	/**
	 * Generates the provider reports
	 * @return an array list of strings, each string is a different report
	 */
	public ArrayList<String> generateReport(){
				//Sets it up so that it only grabs services from one week prior. 
				DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
				Date date = new Date();
				String todate = dateFormat.format(date);
				
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -7);
				Date todate1 = cal.getTime();
				String fromdate = dateFormat.format(todate1);
//				System.out.printf(fromdate);
				Date WorkingDate = null;
				try {
					WorkingDate = dateFormat.parse(fromdate);
				} catch (ParseException e) {

				}
				Date date2 = null;
		
				mEntries = mProviderRecord.searchEntry();
				mRecordsLength = mEntries.size();
				mReports = new ArrayList<String>();
				
				//Loops through each entry
				for(i=0; i < mRecordsLength; i++){
					int serviceStart = 0;
					mProviderService = ""; 
					mProviderService += (mEntries.get(i).getName() + "\r\n");
					mProviderService += (mEntries.get(i).getNumber()+ "\r\n");
					mProviderService += (mEntries.get(i).getAddress()+ "\r\n");
					mProviderService += (mEntries.get(i).getCity()+ "\r\n");
					mProviderService += (mEntries.get(i).getState()+ "\r\n");
					mProviderService += (mEntries.get(i).getZip()+ "\r\n\r\n");
					mProviderService += "Service(s) Provided This Week:" + "\r\n";
					
					mServices = mEntries.get(i).searchServices();
					mServicesLength = mServices.size(); 
					
					for(int j = 0; j < mServicesLength; j++){
						try {
							date2 = dateFormat.parse(mServices.get(j).getDate());
						} catch (ParseException e) {

							serviceStart = j;
						}
						
						if (WorkingDate.before(date2) || WorkingDate.equals(date2)){
							serviceStart = j;

							break;
						}
						else {
							serviceStart = j+1;
						}
					}
					int consultationCounter = 0;
					float totalFee = 0;
					//Loops through each service
					for(int j = serviceStart; j < mServicesLength; j++){
						
						mProviderService+= mServices.get(j).getDate()+ "\r\n";
						mProviderService+= ((ProviderServices) mServices.get(j)).getDateReceived()+ "\r\n";
						mProviderService+= ((ProviderServices) mServices.get(j)).getMemberName()+ "\r\n";
						mProviderService+= ((ProviderServices) mServices.get(j)).getMemberNumber()+ "\r\n";
						mProviderService+= ((ProviderServices) mServices.get(j)).getServiceCode()+ "\r\n";
						mProviderService+= ((ProviderServices) mServices.get(j)).getAmount()+ "\r\n\r\n";
						consultationCounter +=1;
						totalFee += Float.parseFloat(((ProviderServices) mServices.get(j)).getAmount());
						
						}
					mProviderService += "Total Number of Consulations with Members: ";
					mProviderService += String.format("%03d", consultationCounter);
					mProviderService += "\r\n" + "Total Fee for Week: ";
					mProviderService += String.format("$%07f", totalFee);
					mReports.add(mProviderService);
				}
				return mReports;
			
		
	}
}