/**
 * This class is a container class that works with generic types,
 * allowing the use of Collections.binarySearch.
 * @author EJ Kim
 */

import java.util.*;

public class MyArrayList<E extends Comparable<E>> extends ArrayList<E> {
	
	/**
	 * Sorts a MyArrayList<> based on its natural ordering.
	 */
	public void sort(){
		Collections.sort(this);
	}
	
	/**
	 * Compares every item in a MyArrayList<> to the 
	 * item after it. If any item gives a positive number
	 * when it is compared to the next item, the MyArrayList
	 * is not sorted.
	 * @return true if the MyArrayList<> is sorted.
	 * false if otherwise.
	 */
	public boolean isSorted() {
		/*
		 * go through every item in MyArrayList and check if any item,
		 * when compared to the next item, returns a positive compareTo value.
		 */
		for (int i = 0; i < this.size() - 1; i++) {
			if (this.get(i).compareTo(this.get(i + 1)) > 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Overrides the contains method in ArrayList.
	 * If the MyArrayList is sorted, object is searched
	 * using binarySearch. If it isn't sorted, object is 
	 * searched using linear search.
	 * @param o Object to be searched for in MyArrayList container.
	 * @return true if the object is in the MyArrayList.
	 * false if otherwise.
	 */
	@Override
	public boolean contains(Object o) {
		//perform a binarySearch if MyArrayList is sorted.
		if (isSorted()) {
			return Collections.binarySearch(this, (E)(o)) > -1;
		}
		//perform a lineary search if MyArrayList is not sorted.
		else {
			return super.contains(o);
		}
	}
}
