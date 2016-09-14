import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
 * Class for creating Summary Reports, inherited from the Reports class
 * 
 * @author Laura May
 * @version 1.0
*/

public class SummaryReport extends Report{
	
	private int mRecordsLength;
	private int mServicesLength;
	private int i;
	private int j;
	private String mSummary;
	private int mTotalConsultationCounter;
	private float mAllFees;
	private int mTotalProviders = 0;
	
	private ArrayList<Entry> mEntries;
	private ArrayList<Services> mServices;
	
	private Records mProviderRecord;
	
	/**
	 * Creates a new Summary Report
	 * @param providerRecords - the record of all the providers' information
	 */
	public SummaryReport(Records providerRecords){
		super(providerRecords); 
		mProviderRecord = providerRecords;
		}
	/**
	 * Generates the summary report
	 * @return a string of the entire report
	 */
	public String generateReport(){
		
		//Sets it up so that it only grabs services from one week prior. 
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String todate = dateFormat.format(date);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date todate1 = cal.getTime();
		String fromdate = dateFormat.format(todate1);
//		System.out.printf(fromdate);
		Date WorkingDate = null;
		try {
			WorkingDate = dateFormat.parse(fromdate);
		} catch (ParseException e) {

		}
		Date date2 = null;
		mEntries = mProviderRecord.searchEntry();
		mRecordsLength = mEntries.size();
		mSummary = "";
		
		//Loops through each entry
		for(i=0; i < mRecordsLength; i++){
			
			int serviceStart = 0;
	
			mServices = mEntries.get(i).searchServices();
			mServicesLength = mServices.size();
			int consultationCounter = 0;
			float totalFee = 0;
			
			for(j = 0; j < mServicesLength; j++){
				try {
					date2 = dateFormat.parse(mServices.get(j).getDate());
				} catch (ParseException e) {
				}
				
				if (WorkingDate.before(date2) || WorkingDate.equals(date2)){
					serviceStart = j;
					break;
				}
				else {
					serviceStart = j+1;
				}
			}
		
			//Loops through each service
			for(j = serviceStart; j < mServicesLength; j++){
				consultationCounter +=1;
				totalFee += Float.parseFloat(((ProviderServices) mServices.get(j)).getAmount());
			}
				
			if(consultationCounter != 0)
			{
				mSummary += (mEntries.get(i).getName() + "\r\n");
				mTotalProviders += 1;
				mSummary += ("Total Number of Consulations with Members: ");
				mSummary += String.format("%03d", consultationCounter);
				mSummary += ("\r\n" + "Total Fee for the Week: ");
				mSummary += String.format("$%07f", totalFee);
				mSummary += ("\r\n\r\n");
			}
			
			mTotalConsultationCounter += consultationCounter;
			mAllFees += totalFee;
		}
		mSummary += ("\r\n" + "Total Number of Providers This Week: ");
		mSummary += String.format("%03d", mTotalProviders);
		mSummary += ("\r\n" + "Total Number of Consultations This Week: ");
		mSummary += String.format("%03d", mTotalConsultationCounter);
		mSummary += ("\r\n" + "Overall Fee This Week: ");
		mSummary += String.format("$%07f", mAllFees);
		
		return mSummary;
}
}
