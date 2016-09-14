import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
 * Class for creating Member Reports, inherited from the Reports class
 * 
 * @author Laura May
 * @version 1.0
*/

public class MemberReport extends Report implements Serializable{ 
	
	private int mRecordsLength;
	private int mServicesLength;
	private int i;
	private int j;
	private String mMemberService;
	
	private ArrayList<Entry>mEntries;
	private ArrayList<Services>mServices;
	private ArrayList<String> mReports;
	
	private Records mMemberRecords;
	
	/**
	 * Creates a new Member Report
	 * @param memberRecords - the record of all the members' information
	 */
	public MemberReport(Records memberRecords){
		super(memberRecords);
		mMemberRecords = memberRecords;
	}
	/**
	 * Generates the member reports
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
//		System.out.printf(fromdate);
		Date WorkingDate = null;
		try {
			WorkingDate = dateFormat.parse(fromdate);
		} catch (ParseException e) {

		}
		Date date2 = null;
		
		mEntries = mMemberRecords.searchEntry();
		mRecordsLength = mEntries.size();
		mReports = new ArrayList<String>();
		
		//Loops through each entry
		for(i=0; i < mRecordsLength; i++){
			int serviceStart = 0;
			mMemberService = "";
			mMemberService += (mEntries.get(i).getName()+ "\r\n");
			mMemberService += (mEntries.get(i).getNumber()+ "\r\n");
			mMemberService += (mEntries.get(i).getAddress()+ "\r\n");
			mMemberService += (mEntries.get(i).getCity()+ "\r\n");
			mMemberService += (mEntries.get(i).getState()+ "\r\n");
			mMemberService += (mEntries.get(i).getZip()+ "\r\n\r\n");
			mMemberService += "Service(s) Provided This Week:" + "\r\n";
			mServices = mEntries.get(i).searchServices();
			mServicesLength = mServices.size(); 
			
			//Finds where one week ago is and excludes the services before this date
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
				
				mMemberService+= ((MemberServices) mServices.get(j)).getDate()+ "\r\n"; 
				mMemberService+= ((MemberServices)mServices.get(j)).getProviderName() + "\r\n"; 
				mMemberService+= ((MemberServices)mServices.get(j)).getServiceName() + "\r\n\r\n";
				}
			mReports.add(mMemberService);
			}
		return mReports;
	}
}