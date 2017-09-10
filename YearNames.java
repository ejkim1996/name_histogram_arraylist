/**
 * This class stores the data the year and
 * all the Name objects for the given year.
 * @author EJ Kim
 */

public class YearNames {
	private int year;
	private MyArrayList<Name> allNames = new MyArrayList<>();
	
	public YearNames(int year) {
		this.year = year;
	}
	
	public int getYear() {
		return year;
	}

	public MyArrayList<Name> getAllNames() {
		return allNames;
	}

	/**
	 * Add a Name object to the MyArrayList if it
	 * isn't already in it.
	 * @param n Name object to add into the allNames MyArrayList
	 * @throws IllegalArgumentException if allNames MyArrayList already contains the Name object to add.
	 */
	public void add(Name n) {
		//throw exception if Name object is already in the MyArrayList.
		if (allNames.contains(n)) {
			throw new IllegalArgumentException("Name object " + n.getName() + "(" + n.getGender() + ") already exists in list for " + year);
		}
		else {
			allNames.add(n);
		}
	}
	
	/**
	 * Goes through MyArrayList to see if there is a Name
	 * object with the name String parameter and both genders,
	 * and returns the added count.
	 * @param name the name String to find the count value
	 * in a given year.
	 * @return the count value of a name in a given year.
	 */
	public int getCountByName(String name) {
		int count = 0;
		
		//Search for a Name object with the name String parameter and "m" for gender.
		for (int i = 0; i < allNames.size(); i++) {
			//If found, add its count to the count variable.
			if (allNames.get(i).equalsIgnoreCount(new Name(name, "m", 0))) {
				count += allNames.get(i).getCount();
			}
		}
		
		//Search for a Name object with the name String parameter and "f" for gender.
		for (int i = 0; i < allNames.size(); i++) {
			//If found, add its count to the count variable.
			if (allNames.get(i).equalsIgnoreCount(new Name(name, "f", 0))) {
				count += allNames.get(i).getCount();
			}
		}
		return count;
	}
	
	/**
	 * Finds the fraction of a given name in a given year.
	 * @param name the name String to find the fraction value
	 * of a given year.
	 * @return a double value which is the fraction of a name's
	 * count over the total count of all Name objects in a given
	 * year.
	 */
	public double getFractionByName(String name) {
		double totalCount = 0;
		//use getCountByName() method to get the count for the name.
		double nameCount = getCountByName(name);
		double fraction = 0;
		
		//go through the MyArrayList and add up all the count variables.
		for (int i = 0; i < allNames.size(); i++) {
			totalCount += (double)(allNames.get(i).getCount());
		}
		
		fraction = nameCount/totalCount;
		return fraction;
	}
	
	/**
	 * Overrides the toString method to give the total count of
	 * babies in a given year.
	 * @return a String detailing the number of babies in a given year.
	 */
	@Override
	public String toString() {
		int numOfBabies = 0;
		for (int i = 0; i < allNames.size(); i++) {
			numOfBabies += allNames.get(i).getCount();
		}
		return "In " + year + ", there were " + numOfBabies + " babies.";
	}
	
	/**
	 * Search for a Name object in a given year's MyArrayList
	 * by using a linear search.
	 * @param n the Name object to look for.
	 * @return true if Name object is in MyArrayList of given year.
	 * false if it is not.
	 */
	public boolean contains(Name n) {
		/* perform a linear search to see if the given Name object
		 * matches the name and gender of any Name object in the MyArrayList.
		 */
		for (int i = 0; i < allNames.size(); i++) {
			if (allNames.get(i).equalsIgnoreCount(n)) {
				return true;
			}
		}
		return false;
	}
}
