import java.io.Serializable;
import java.util.ArrayList;

/**
 * The class for storing Records
 * @author Nate Hochstetler
 * @version 1.0
 */

public class Records implements Serializable {

	private ArrayList<Entry> entries;
	private String type;
	
	/**
	 * Creates a new record tied to a list of entries and a type of record
	 * @param type is the type of the Records class
	 */
	public Records(String type) {
		entries = new ArrayList<Entry>();
		this.type = type;
	}
	
	/**
	 * Adds a new Entry to the list
	 * @param entry is a parameter of Entry type
	 */
	public void addEntry(Entry entry) {
		entries.add(entry);
	}
	
	/**
	 * Update Entry in list
	 * @param entry is a parameter of Entry type
	 * @param index is the index of the entry to edit
	 */
	public void updateEntry(Entry entry, int index) {
		entries.set(index, entry);
	}
	
	/**
	 * Removes Entry from the list
	 * @param entry is the index to remove
	 */
	public void removeEntry(int entry) {
		entries.remove(entry);
	}
	
	/**
	 * Returns type of record
	 * @return type of record
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Returns the searched for Entry
	 * @return entries the searched for Entry
	 */
	public ArrayList<Entry> searchEntry() {
		return entries;
	}
	
}
