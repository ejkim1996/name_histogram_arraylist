/**
 * This class loads all of the data from the files, receives user input,
 * handles exceptions, and creates histograms.
 * @author EJ Kim
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class BabyNames {
	
	public static void main(String[] args) {
		//call the getData() and store its info into a YearNames[] array.
		YearNames[] yearData = getData();
		
		//set up Scanner input.
		Scanner input = new Scanner(System.in);
		String userInput = "";
		
		//loop until user input is "q" or "Q".
		while (!userInput.equalsIgnoreCase("q")) {
			//ask for user input and save it into userInput variable.
			System.out.println("Which name's popularity would you like to check? (Enter 'q' to terminate program.)");
			userInput = input.nextLine();
			
			//stop the loop immediately if the user input is "q" or "Q".
			if (userInput.equalsIgnoreCase("q")) {
				break;
			}
			
			/*if user input isn't "q" or "Q", check if a Name object exists in
			 * any YearNames object in the YearNames[] array and display appropriate
			 * info.
			 */
			else {
				//set up variables to use.
				boolean nameDoesNotExist = true;
				Name userInputMale = null;
				Name userInputFemale = null;
				
				/*try/catch block to create Name objects for both genders
				 * with the given userInput string as the name.
				 */
				try {
					userInputMale = new Name(userInput, "m", 0);
					userInputFemale = new Name(userInput, "f", 0);
				}
				catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
				}
				
				/*check if the Name objects for both genders with given userInput string
				 * as the name was successfully created.
				 */
				if (userInputMale != null && userInputFemale != null) {
					/* for every YearNames object in the YearNames[] array,
					 * if the MyArrayList of any YearNames object contains either
					 * male or female Name object, display histogram information
					 * and break the loop.
					 */
					for (int i = 0; i < yearData.length; i++) {
						if (yearData[i].contains(userInputMale) || yearData[i].contains(userInputFemale)) {
							displayHistogram(userInput, yearData);
							nameDoesNotExist = false;
							break;
						}
					}
					//if the Name object isn't found in any YearNames object, print an error message.
					if (nameDoesNotExist) {
						System.err.println("No such name in dataset.");
					}
				}
			}
			
		}
		input.close();
	}
	
	/**
	 * reads through all of the files and creates and adds all names
	 * as Name objects to a YearNames object, which are then all stored
	 * in a YearNames[] array.
	 * @return an array of YearNames 
	 */
	public static YearNames[] getData() {
		//create YearNames[] array.
		YearNames[] yearData = new YearNames[136];
		
		//loop through every file in data set.
		for (int i = 0; i < yearData.length; i++) {
			//create File object and the Scanner for it. exit and show error
			//message if file can't be read or is not found.
			String fileName = "data/yob" + (i + 1880) + ".txt";
			File file = new File(fileName);
			if (!file.canRead()) {
				System.err.printf("Error: cannot read data from file " + fileName);
				System.exit(1);
			}
			Scanner inputFile = null; 
			try {
				inputFile = new Scanner(file);
			} 
			catch (FileNotFoundException e) {
				System.err.printf("Error: file " + fileName + "not found.");
				System.exit(1);
			}
			
			//create a yearNames object with given year.
			YearNames yearNames = new YearNames(i + 1880);
			
			//go through every line in each file
			while (inputFile.hasNextLine()) {
				/* save one line of the file to the String object input.
				 * separate the info using commas and store them into a String array.
				 */
				String input = inputFile.nextLine();
				String[] data = input.split(",");
				
				//set up variables for Name object using the String array.
				String name = data[0];
				String gender = data[1];
				int count = Integer.parseInt(data[2]);
				
				//create Name object reference and try to instantiate it.
				Name nameObject = null;
				try {
					nameObject = new Name(name, gender, count);
				}
				catch (IllegalArgumentException e) {
					System.err.println(e.getMessage() + " in " + fileName + ". Data not added.");
				}
				
				//if instantiation successful, try adding the nameObject to
				//the YearNames variable.
				if (nameObject != null) {
					try {
						yearNames.add(nameObject);
					}
					catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
					}
				}
			}
			//sort the YearNames variable and add it to the YearNames[] array.
			yearNames.getAllNames().sort();
			yearData[i] = yearNames;
			inputFile.close();
		}
		//return YearNames[] array.
		return yearData;
	}
	
	/**
	 * goes through the YearNames[] array and finds the percentage of
	 * babies with the given name and displays a histogram for each year.
	 * @param name the String of the name to check and present data for.
	 * @param yearData the YearNames[] array with the data for all files.
	 */
	public static void displayHistogram(String name, YearNames[] yearData) {
		//loop through YearNames[] array.
		for (int i = 0; i < yearData.length; i++) {
			//get year data from each YearNames object.
			String year = yearData[i].getYear() + "";
			
			//get fraction data for given name and multiply it by 100 to get percent
			double percent = ((yearData[i].getFractionByName(name))*100);
			
			//get number of bars data by dividing percent by 0.01 and rounding up.
			double numOfBars = Math.ceil(percent/0.01);
			String bars = "";
			for (int j = 0; j < numOfBars; j++) {
				bars += "|";
			}
			
			//print histogram data as "YYYY (F.FFFF): HISTOGRAM_BAR
			System.out.printf(year + " " + "(%.4f): " + bars + "\n", percent);
		}
	}

}
