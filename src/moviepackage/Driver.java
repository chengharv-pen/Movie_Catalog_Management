package moviepackage;

import java.util.Scanner;

import custom_exceptions.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/**
 * 
 * This program is a movie catalog management system that starts by partitionning all valid movie records into new genre-based
 * text files. Then, it loads an array of movie records from each of the [movieGenre].csv files, serializes the resulting
 * movie array to a binary file, and deserializes the serialized arrays from the binary files into a 2D-array of Movie objects, 
 * and finally provide an interactive program that allows the user to navigate the 2D-array with console input, 
 * displaying user-specified number of movie-records.
 * 
 */
public class Driver {
	
	/**
	 * 
	 * This is the main method
	 * 
	 * @param args Arguments for the main method
	 * 
	 */
	public static void main(String[] args) {
		
		Movie[] arrayMovies = new Movie[100];
		String[] genreArray = new String[] {"musical", "comedy", "animation", "adventure", "drama", "crime", "biography", "horror", "action", "documentary",
				"fantasy", "mystery", "sci-fi", "family", "western", "romance", "thriller"};
		
		resetFiles(genreArray);
		
		// part 1’s manifest file
		String part1_manifest = "part1_manifest.txt";
		
		// part 2’s manifest file
		
		String part2_manifest = "";
		try {
			part2_manifest = do_part1(part1_manifest, genreArray); // partition
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (BadYearException e) {
			e.getMessage();
		} catch (BadTitleException e) {
			e.getMessage();
		} catch (BadGenreException e) {
			e.getMessage();
		} catch (BadScoreException e) {
			e.getMessage();
		} catch (BadDurationException e) {
			e.getMessage();
		} catch (BadRatingException e) {
			e.getMessage();
		} catch (BadNameException e) {
			e.getMessage();
		} catch (MissingQuotesException e) {
			e.getMessage();
		} catch (ExcessFieldsException e) {
			e.getMessage();
		} catch (MissingFieldsException e) {
			e.getMessage();
		}
		
		
		// part 3’s manifest file
		String part3_manifest = do_part2(part2_manifest, arrayMovies, genreArray); // serialize
		
		do_part3(part3_manifest, arrayMovies, genreArray); // deserialize and navigate
		

		
		
		
	}
	
	/**
	 *
	 * This method will reset all the .csv and .ser files created with this program
	 * 
	 * @param genreArray An array that holds every movie genre
	 * 
	 */
	public static void resetFiles(String[] genreArray) {
		PrintWriter resetFiles = null;
		try {
			resetFiles = new PrintWriter(new FileOutputStream("bad_movie_records.txt", false));
			resetFiles = new PrintWriter(new FileOutputStream("part2_manifest.txt", false));
			
			for (String element : genreArray) {
				resetFiles = new PrintWriter(new FileOutputStream(element + ".csv"));
				resetFiles = new PrintWriter(new FileOutputStream(element + ".ser"));
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		
		resetFiles.close();
		
	}
	
	
	/**
	 *
	 * This method will generate 17 .csv files related to each movie genre,
	 * a .txt file that holds the bad movies and part2_manifest.txt
	 * 
	 * @param text A .txt file that holds all the .csv files for movies in a certain time range
	 * @param genreArray An array that holds every movie genre
	 * 
	 * @throws BadYearException Corresponds to a missing or invalid year
	 * @throws BadTitleException Corresponds to a missing title
	 * @throws BadGenreException Corresponds to a missing or invalid genre
	 * @throws BadScoreException Corresponds to a missing or invalid score
	 * @throws BadDurationException Corresponds to a missing or invalid duration
	 * @throws BadRatingException Corresponds to a missing rating
	 * @throws BadNameException Corresponds to a missing name
	 * @throws MissingQuotesException Corresponds to missing quotes at the start AND end of a movie title
	 * @throws ExcessFieldsException Corresponds to excess fields to a movie
	 * @throws MissingFieldsException Corresponds to missing fields to a movie
	 * @throws FileNotFoundException Thrown when a file is not found
	 * 
	 * @return part2_manifest.txt
	 * 
	 */
	public static String do_part1(String text, String[] genreArray) throws BadYearException, BadTitleException, BadGenreException, BadScoreException, 
		BadDurationException, BadRatingException, BadNameException, MissingQuotesException, ExcessFieldsException, MissingFieldsException, FileNotFoundException {

		//Step 1: Read the CSV files from part1_manifest.csv
		String CSVFile = "";
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new FileInputStream(text));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
		} catch (Exception e) {
			System.out.println("Exception");
		}
		
		//Check for 10 CSV files using for loop
		for (int i = 0; i < 10; i++) {	
			
			int lineNumber = 1;
			System.out.println("-----------------------------------------------");
			System.out.println("File " + (i+1));
			CSVFile = fileInput.nextLine(); //Registering file name in CSVFile
			
			//Step 2: Sort by genre, there are 17.
			Scanner fileInput2 = null;
			try {
				fileInput2 = new Scanner(new FileInputStream(CSVFile)); //open input stream for CSVFile
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFound");
			} catch (Exception e) {
				System.out.println("Exception");
			}
			
			//Check for every movie in current CSV file, using a while loop
			while (CSVFile != null) { 
				
				PrintWriter fileOutput = null;
				PrintWriter badFileOutput = null;
				String CSVLine = "";
				
				try {
					CSVLine = fileInput2.nextLine();
				} catch (RuntimeException e) {
					System.out.println("end of csv file");
					CSVFile = null;
					break; //Exit while loop if no remaining lines
				}
				
				//I have no idea why this works, but this does split CSVLine properly, while accounting for commas in an entry
				String[] CSVMovie = CSVLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				CSVMovie[1] = CSVMovie[1].replaceAll("�", "");
				System.out.println(CSVMovie[1]);
				
				//This is for later
				try {
					badFileOutput = new PrintWriter(new FileOutputStream("bad_movie_records.txt", true));
				} catch (FileNotFoundException e) {
					System.out.println("Error opening the file");
				}
				
				//--------------------------------------------------------------------------------
				//-------------------This is the error throwing segment---------------------------
				//--------------------------------------------------------------------------------
				int year = 0;
				int duration = 0;
				double score = 0;
				
				try {
					//ExcessFieldsException
					if (CSVMovie.length > 10) {
						throw new ExcessFieldsException("Syntax Error: Excess field(s)");
					}	
					
					//MissingFieldsException
					if (CSVMovie.length < 10) {
						throw new MissingFieldsException("Syntax Error: Missing field(s)");
					}
					
					//BadYearException
					try {
						if (CSVMovie[0] == "") {
							year = 0;
						} else {
							year = Integer.parseInt(CSVMovie[0]);
						}
						
						if (year < 1990 || year > 1999) {
							throw new BadYearException("Semantic Error: invalid year");
						}
						if (year == 0) {
							throw new BadYearException("Semantic Error: missing year");
						}
					} catch(NumberFormatException e) {
						throw new BadYearException("Semantic Error: invalid year");
					}
					
					//BadDurationException
					try {
						if (CSVMovie[2] == "") {
							duration = 0;
						} else {
							duration = Integer.parseInt(CSVMovie[2]);
						}
						
						if (duration < 30 || duration > 300) {
							throw new BadDurationException("Semantic Error: invalid duration");
						}
						if (duration == 0) {
							throw new BadDurationException("Semantic Error: missing duration");
						}
					} catch (NumberFormatException e) {
						throw new BadDurationException("Semantic Error: invalid duration");
					}
					
					//BadScoreException
					try {
						if (CSVMovie[5] == "") {
							score = 0;
						} else {
							score = Double.parseDouble(CSVMovie[5]);
						}
						
						if (score < 0 || score > 10) {
							throw new BadScoreException("Semantic Error: invalid score");
						}
						if (score == 0) {
							throw new BadScoreException("Semantic Error: missing score");
						}
					} catch (NumberFormatException e) {
						throw new BadScoreException("Semantic Error: invalid score");
					}
					
					//BadTitleException
					if (CSVMovie[1] == "") {
						throw new BadTitleException("Semantic Error: missing title");
					}
					
				
					//BadGenreException
					if (CSVMovie[3] == "") {
						throw new BadGenreException("Semantic Error: missing genre");
					}
				
					//---1. check if a genre in genreArray matches CSVMovie[3]'s genre.---
					boolean genreCheck = false;
					String genre = CSVMovie[3];
					for (String element : genreArray) {
						if (element.compareToIgnoreCase(genre) == 0) {
							genreCheck = true;
						}
					}
					
					//---2. throw exception if genreCheck is false.---
					if (genreCheck == false) {
						throw new BadGenreException("Semantic Error: invalid genre");
					}
				
					
					//BadRatingException
					if (CSVMovie[4] == "") {
						throw new BadRatingException("Semantic Error: missing rating");
					}
		
					
					//BadNameException
					if (CSVMovie[7] == "" || CSVMovie[8] == "" || CSVMovie[9] == "") {
						throw new BadNameException("Semantic error: missing name");
					}
					
					//MissingQuotesException
					int length = CSVMovie[1].length();
					char first = CSVMovie[1].charAt(0);
					char last = CSVMovie[1].charAt(length - 1);
					
					
					if (first != '"' && last != '"') {
						throw new MissingQuotesException("Syntax Error: missing quotes");
					}
					
					//Create a Movie object movie, using the values from CSVMovie array as parameters
					Movie movie = new Movie(year, CSVMovie[1], duration, CSVMovie[3], CSVMovie[4], score, CSVMovie[6], CSVMovie[7], CSVMovie[8], CSVMovie[9]);
					String movieGenre = (movie.getGenre()).toLowerCase();
					String CSVMovieGenre = movieGenre + ".csv";
					
					//Apppend to genre file (movieGenre).csv
					try {
						fileOutput = new PrintWriter(new FileOutputStream(CSVMovieGenre, true));
					} catch (FileNotFoundException e) {
						System.out.println("Error opening the file");
					}
				
					fileOutput.println(movie);
					fileOutput.close();	
					
				} catch (BadYearException | BadTitleException | BadGenreException | BadScoreException | BadDurationException |
						BadRatingException | BadNameException | MissingQuotesException | ExcessFieldsException | MissingFieldsException e) {
					for (String element : CSVMovie) {
						badFileOutput.print(element + ",");
					}
					badFileOutput.print(" --- " + e.getMessage() + " ---  Line number: " + lineNumber + " --- Input File: " + CSVFile);
					badFileOutput.println();
					badFileOutput.close();
				}
				
				lineNumber++;
				//--------------------------------------------------------------------------------
				//------------------------End of error throwing segment---------------------------
				//--------------------------------------------------------------------------------
				
			} //end of while loop		
			
		} //end of for loop
		
		//After the genre files have the movies in them...
		//Write all the genre files to part2_manifest.txt, then returning part2_manifest.txt
		PrintWriter pwP2Manifest = null;
		String nextFile = "part2_manifest.txt";
		try {
			pwP2Manifest = new PrintWriter(new FileOutputStream(nextFile));
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file");
		}
		
		for (String element : genreArray) {
			pwP2Manifest.println(element + ".csv");
		}
		
		pwP2Manifest.close();
		fileInput.close();
		return nextFile;
		
	} //end of method
	
	
	/**
	 *
	 * This method will serialize the genre files into a .ser format
	 * and create part3_manifest.txt
	 * 
	 * @param text A .txt file that holds all the [MovieGenre].csv files
	 * @param movieArray An empty array of Movie objects that will be filled
	 * @param genreArray An array that holds every movie genre
	 * 
	 * @return part3_manifest.txt
	 * 
	 */
	public static String do_part2(String text, Movie[] movieArray, String[] genreArray) {
		//Step 1: Load part2_manifest records into an array of Movie objects
		//Reading files inside of part2_manifest.txt
		String CSVFile = "";
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new FileInputStream(text));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
		} catch (Exception e) {
			System.out.println("Exception");
		}
		
		int arrayIndex = 0;
		
		for (int i = 0; i < genreArray.length; i++) { //iterate through every genre.csv file
			
			System.out.println("File " + (i+1));
			CSVFile = fileInput.nextLine(); //Registering file name in line
			
			Scanner fileInput2 = null;
			try {
				//Read the CSV file registered in line
				fileInput2 = new Scanner(new FileInputStream(CSVFile));
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFound");
			} catch (Exception e) {
				System.out.println("Exception");
			}
			
			while (CSVFile != null) {
				
				String CSVLine = "";
				try {
					CSVLine = fileInput2.nextLine();
				} catch (RuntimeException e) {
					System.out.println("end of csv file");
					CSVFile = null;
					break; //Exit while loop if no remaining lines
				}
				
				//I have no idea why this works, but this does split CSVLine properly, while accounting for commas in an entry
				String[] CSVMovie = CSVLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				System.out.println(CSVLine);
				
				//Create Movie object out of CSVMovie array
				int year = Integer.parseInt(CSVMovie[0]);
				int duration = Integer.parseInt(CSVMovie[2]);
				double score = Double.parseDouble(CSVMovie[5]);
				Movie movieObject = new Movie(year, CSVMovie[1], duration, CSVMovie[3], CSVMovie[4], score, CSVMovie[6], CSVMovie[7], CSVMovie[8], CSVMovie[9]);
				
				//Put the Movie object inside of movieArray and increment index
				movieArray[arrayIndex] = movieObject;
				arrayIndex++;
			}
			
		}
		
		//Step 2: Serialize the entries of movieArray into their appropriate genre.ser files 
		//Creating arrays for every genre...
		Movie[] musical = new Movie[100]; Movie[] comedy = new Movie[100];
		Movie[] animation = new Movie[100]; Movie[] adventure = new Movie[100];
		Movie[] drama = new Movie[100]; Movie[] crime = new Movie[100];
		Movie[] biography = new Movie[100]; Movie[] horror = new Movie[100];
		Movie[] action = new Movie[100]; Movie[] documentary = new Movie[100];
		Movie[] fantasy = new Movie[100]; Movie[] mystery = new Movie[100];
		Movie[] sci_fi = new Movie[100]; Movie[] family = new Movie[100];
		Movie[] western = new Movie[100]; Movie[] romance = new Movie[100];
		Movie[] thriller = new Movie[100];
		
		//Organizing entries in movieArray into arrays for each genre
		int count = 0;
		for (Movie element : movieArray) {
			count++;
			if (element != null) {
				switch((element.getGenre()).toLowerCase()) {
					case "musical":
						musical[count] = element;
						break;
					case "comedy":
						comedy[count] = element;
						break;
					case "animation":
						animation[count] = element;
						break;
					case "adventure":
						adventure[count] = element;
						break;
					case "drama":
						drama[count] = element;
						break;
					case "crime":
						crime[count] = element;
						break;
					case "biography":
						biography[count] = element;
						break;
					case "horror":
						horror[count] = element;
						break;
					case "action":
						action[count] = element;
						break;
					case "documentary":
						documentary[count] = element;
						break;
					case "fantasy":
						fantasy[count] = element;
						break;
					case "mystery":
						mystery[count] = element;
						break;
					case "sci-fi":
						sci_fi[count] = element;
						break;
					case "family":
						family[count] = element;
						break;
					case "western":
						western[count] = element;
						break;
					case "romance":
						romance[count] = element;
						break;
					case "thriller":
						thriller[count] = element;
						break;
				}
			}
		}
		
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		
		//-----------------------------------------------------------------------------
		//This try-catch block will iterate through an array of a specific genre,
		//so that its non-null entries are written into the correct genre.ser file
		//-----------------------------------------------------------------------------
		//there is probably a better way of doing this
		try {
			for (String element0 : genreArray) {
				switch(element0) {
					case "musical":
						fos = new FileOutputStream("musical.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element1 : musical) {
							if (element1 != null)
								oos.writeObject(element1);
						}
						break;
					case "comedy":
						fos = new FileOutputStream("comedy.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element2 : comedy) {
							if (element2 != null)
								oos.writeObject(element2);
						}
						break;
					case "animation":
						fos = new FileOutputStream("animation.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element3 : animation) {
							if (element3 != null)
								oos.writeObject(element3);
						}
						break;
					case "adventure":
						fos = new FileOutputStream("adventure.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element4 : adventure) {
							if (element4 != null)
								oos.writeObject(element4);
						}
						break;
					case "drama":
						fos = new FileOutputStream("drama.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element5 : drama) {
							if (element5 != null)
								oos.writeObject(element5);
						}
						break;
					case "crime":
						fos = new FileOutputStream("crime.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element6 : crime) {
							if (element6 != null)
								oos.writeObject(element6);
						}
						break;
					case "biography":
						fos = new FileOutputStream("biography.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element7 : biography) {
							if (element7 != null)
								oos.writeObject(element7);
						}
						break;
					case "horror":
						fos = new FileOutputStream("horror.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element8 : horror) {
							if (element8 != null)
								oos.writeObject(element8);
						}
						break;
					case "action":
						fos = new FileOutputStream("action.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element9 : action) {
							if (element9 != null)
								oos.writeObject(element9);
						}
						break;
					case "documentary":
						fos = new FileOutputStream("documentary.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element10 : documentary) {
							if (element10 != null)
								oos.writeObject(element10);
						}
						break;
					case "fantasy":
						fos = new FileOutputStream("fantasy.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element11 : fantasy) {
							if (element11 != null)
								oos.writeObject(element11);
						}
						break;
					case "mystery":
						fos = new FileOutputStream("mystery.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element12 : mystery) {
							if (element12 != null)
								oos.writeObject(element12);
						}
						break;
					case "sci-fi":
						fos = new FileOutputStream("sci-fi.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element13 : sci_fi) {
							if (element13 != null)
								oos.writeObject(element13);
						}
						break;
					case "family":
						fos = new FileOutputStream("family.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element14 : family) {
							if (element14 != null)
								oos.writeObject(element14);
						}
						break;
					case "western":
						fos = new FileOutputStream("western.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element15 : western) {
							if (element15 != null)
								oos.writeObject(element15);
						}
						break;
					case "romance":
						fos = new FileOutputStream("romance.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element16 : romance) {
							if (element16 != null)
								oos.writeObject(element16);
						}
						break;
					case "thriller":
						fos = new FileOutputStream("thriller.ser", true);
						oos = new ObjectOutputStream(fos);
						for (Movie element17 : thriller) {
							if (element17 != null)
								oos.writeObject(element17);
						}
						break;
				}
			}
			oos.flush();
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			System.out.println();
		} catch (IOException e) {
			System.out.println();
		}
		
		//Step 3: Produce part3_manifest.txt
		//Write all the genre.ser to part3_manifest.txt, then returning part3_manifest.txt
		PrintWriter pwP3Manifest = null;
		String nextFile = "part3_manifest.txt";
		try {
			pwP3Manifest = new PrintWriter(new FileOutputStream(nextFile));
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file");
		}
		
		for (String element : genreArray) {
			pwP3Manifest.println(element + ".ser");
		}
		
		pwP3Manifest.close();
		return nextFile;
		
	} //end of method
	
	
	/**
	 *
	 * This method will call the method deserializeFiles, 
	 * then display an interactive menu in the console.
	 * 
	 * @param text A .txt file that holds all the [MovieGenre].ser files
	 * @param movieArray An array of Movie objects, previously filled in do_part2
	 * @param genreArray An array that holds every movie genre
	 * 
	 * @see deserializeFiles
	 * 
	 */
	public static void do_part3(String text, Movie[] movieArray, String[] genreArray) {
		
		//Deserialize the genre.ser files
		Movie[][] all_movies = deserializeFiles(text, genreArray);
		
		//How do I assign a unique count to each genre?
		//Variables to keep track of genre count. They are all -1 because I used the positions [i][0] as dummy Movie objects to hold the genre
		int genre1 = -1; int genre2 = -1; int genre3 = -1; int genre4 = -1; int genre5 = -1; int genre6 = -1; 
		int genre7 = -1; int genre8 = -1; int genre9 = -1; int genre10 = -1; int genre11 = -1; int genre12 = -1;
		int genre13 = -1; int genre14 = -1; int genre15 = -1; int genre16 = -1; int genre17 = -1;
		
		//Iterate through the all_movies[][] array to count the number of non-null entries, for each genre.
		for (int i = 0; i < genreArray.length; i++) {
			for (int j = 0; j < 100; j++) {
				try {	
					String currentGenre = all_movies[i][j].getGenre();
					if (currentGenre.compareToIgnoreCase("musical") == 0)
						genre1++;
					if (currentGenre.compareToIgnoreCase("comedy") == 0)
						genre2++;
					if (currentGenre.compareToIgnoreCase("animation") == 0)
						genre3++;
					if (currentGenre.compareToIgnoreCase("adventure") == 0)
						genre4++;
					if (currentGenre.compareToIgnoreCase("drama") == 0)
						genre5++;
					if (currentGenre.compareToIgnoreCase("crime") == 0)
						genre6++;
					if (currentGenre.compareToIgnoreCase("biography") == 0)
						genre7++;
					if (currentGenre.compareToIgnoreCase("horror") == 0)
						genre8++;
					if (currentGenre.compareToIgnoreCase("action") == 0)
						genre9++;
					if (currentGenre.compareToIgnoreCase("documentary") == 0)
						genre10++;
					if (currentGenre.compareToIgnoreCase("fantasy") == 0)
						genre11++;
					if (currentGenre.compareToIgnoreCase("mystery") == 0)
						genre12++;
					if (currentGenre.compareToIgnoreCase("sci-fi") == 0)
						genre13++;
					if (currentGenre.compareToIgnoreCase("family") == 0)
						genre14++;
					if (currentGenre.compareToIgnoreCase("western") == 0)
						genre15++;
					if (currentGenre.compareToIgnoreCase("romance") == 0)
						genre16++;
					if (currentGenre.compareToIgnoreCase("thriller") == 0)
						genre17++;		
				} catch (NullPointerException e) {
					System.out.println();
				}
			}
		}
		
		
		boolean check = true;
		int genreNumber = 0;
		int genreCount = 0;
		
		//This while loop handles the interactive part of the program
		while(check) {
			
			System.out.println("-----------------------------\n" +
							   "         Main Menu           \n" +
							   "-----------------------------\n" +
							   "s - Select a movie array to navigate\n" +
							   "n - Navigate " + all_movies[genreNumber][0].getGenre() + " movies (" + genreCount + " records)\n" +
							   "x - Exit\n" +
							   "-----------------------------\n");
			System.out.print("Enter Your Choice: ");
			Scanner input = new Scanner(System.in);
			String userInput = input.next();
			
			//Main Menu - This if statement will let the user choose the movie genre
			if (userInput.compareToIgnoreCase("s") == 0) {
				
				try {
					System.out.println("-----------------------------\n" +
									   "       Genre Sub-Menu        \n" +
									   "-----------------------------\n" +
									   "1 - musical					(" + genre1 + " movies)\n" +
									   "2 - comedy					(" + genre2 + " movies)\n" +
									   "3 - animation					(" + genre3 + " movies)\n" +
									   "4 - adventure					(" + genre4 + " movies)\n" +
									   "5 - drama					(" + genre5 + " movies)\n" +
									   "6 - crime					(" + genre6 + " movies)\n" +
									   "7 - biography					(" + genre7 + " movies)\n" +
									   "8 - horror					(" + genre8 + " movies)\n" +
									   "9 - action					(" + genre9 + " movies)\n" +
									   "10 - documentary				(" + genre10 + " movies)\n" +
									   "11 - fantasy					(" + genre11 + " movies)\n" +
									   "12 - mystery					(" + genre12 + " movies)\n" +
									   "13 - sci-fi					(" + genre13 + " movies)\n" +
									   "14 - family					(" + genre14 + " movies)\n" +
									   "15 - western					(" + genre15 + " movies)\n" +
									   "16 - romance					(" + genre16 + " movies)\n" +
									   "17 - thriller					(" + genre17 + " movies)\n" +
									   "18 - Exit\n" +
									   "-----------------------------\n");
					System.out.print("Enter your choice: ");
					genreNumber = (input.nextInt()) - 1;
					
					//Assigns genreCount to the appropriate Genre Sub-Menu option
					if (genreNumber == 0)
						genreCount = genre1;
					if (genreNumber == 1)
						genreCount = genre2;
					if (genreNumber == 2)
						genreCount = genre3;
					if (genreNumber == 3)
						genreCount = genre4;
					if (genreNumber == 4)
						genreCount = genre5;
					if (genreNumber == 5)
						genreCount = genre6;
					if (genreNumber == 6)
						genreCount = genre7;
					if (genreNumber == 7)
						genreCount = genre8;
					if (genreNumber == 8)
						genreCount = genre9;
					if (genreNumber == 9)
						genreCount = genre10;
					if (genreNumber == 10)
						genreCount = genre11;
					if (genreNumber == 11)
						genreCount = genre12;
					if (genreNumber == 12)
						genreCount = genre13;
					if (genreNumber == 13)
						genreCount = genre14;
					if (genreNumber == 14)
						genreCount = genre15;
					if (genreNumber == 15)
						genreCount = genre16;
					if (genreNumber == 16)
						genreCount = genre17;
					
					//Exits program
					if (genreNumber == 17) {
						System.out.print("Goodbye!");
						input.close();
						System.exit(0);
					}
					
				} catch (RuntimeException e) {
					e.getMessage();
				}
				
			}
			
			//Main Menu - This if statement will let the user navigate through +j and -j of all_movies[genreNumber][j]
			if (userInput.compareToIgnoreCase("n") == 0) {
				int currentIndex = 0;
				int navigationIndex = 123;
				
				System.out.println("Navigating " + all_movies[genreNumber][0].getGenre() + " movies (" + genreCount + ")");
				System.out.print("Enter your choice: ");
				currentIndex = input.nextInt();
				
				//Avoids ArrayIndexOutOfBoundsException
				if (currentIndex > genreCount)
					currentIndex = genreCount;
				else if (currentIndex < 0)
					currentIndex = 0;
				
				//Handles navigation through a genre's library of movies
				while (navigationIndex != 0) {	
					Movie currentMovie = all_movies[genreNumber][currentIndex];
					System.out.println("Current movie: " + currentMovie);
					
					System.out.print("Enter navigation index: ");
					navigationIndex = input.nextInt();
					
					if (navigationIndex < 0) {
						currentIndex = currentIndex + navigationIndex;
						if (currentIndex < 0) {
							System.out.println("EOF has been reached");
							currentIndex = 0;
						}
					}
					
					if (navigationIndex > 0) {
						currentIndex = currentIndex + navigationIndex;
						if (currentIndex > genreCount) {
							System.out.println("EOF has been reached");
							currentIndex = genreCount;
						}
					}
				}
			}
			
			//Main Menu - This if statement will exit the program.
			if (userInput.compareToIgnoreCase("x") == 0) {
				System.out.print("Goodbye!");
				input.close();
				System.exit(0);
			}
			
		}
		
	} //end of method
	
	
	/**
	 *
	 * This method handles the deserialization of the genre.ser files, for do_part3.
	 * 
	 * @param text A .txt file that holds all the [MovieGenre].ser files
	 * @param genreArray An array that holds every movie genre
	 * 
	 * @see do_part3
	 * 
	 * @return all_movies
	 */
	public static Movie[][] deserializeFiles(String text, String[] genreArray) {
		
		System.out.println("Starting deserialization.");
		Movie[][] all_movies = new Movie[genreArray.length][100];
		
		//Reading files inside of part3_manifest.txt
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new FileInputStream(text));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
		} catch (Exception e) {
			System.out.println("Exception");
		}
		
		//1. put the genres in [i] of all_movies
		//the movies will start appearing when j > 0. j = 0 will be a dummy movie that stores the genre's name.
		for (int i = 0; i < genreArray.length; i++) {
			all_movies[i][0] = new Movie(0, "", 0, genreArray[i], "", 0, "", "", "", "");
		}
		
		//Variables to keep track of genre count
		int genre1 = 1; int genre2 = 1; int genre3 = 1; int genre4 = 1; int genre5 = 1; int genre6 = 1; 
		int genre7 = 1; int genre8 = 1; int genre9 = 1; int genre10 = 1; int genre11 = 1; int genre12 = 1;
		int genre13 = 1; int genre14 = 1; int genre15 = 1; int genre16 = 1; int genre17 = 1;
		
		//2. put the movies in [j] of all_movies, and also in the right [i]	
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		String ser = "";
		
		//Every iteration g in this for loop corresponds to an individual genre
		for (int g = 0; g < genreArray.length - 1; g++) {

			try {				
				
				System.out.println("before fis and ois");
				ser = fileInput.nextLine();
				File file = new File(ser);
				
				System.out.println(file);
				
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				
				System.out.println("after fis and ois");
				System.out.println("Current file: " + file);
				boolean check = false;
				
				//Deserializing one movie at a time, for a single .ser file
				while (!check) {
					
					Movie movie = null;
					try {
						movie = (Movie) ois.readObject(); //Read one Movie Object in the .ser file
					} catch (ClassNotFoundException e) {
						System.out.println("ClassNotFoundException: movie");
						check = true;  
						break; //Exit while loop if no remaining movies
					} catch (IOException e) {
						System.out.println("IOException: Empty .ser file");
						check = true; 
						break; //Exit while loop if no remaining movies
					} 
					
					String movieGenre = (movie.getGenre()).toLowerCase();
					System.out.println(movie);
					System.out.println(movieGenre);
					
					//Assigning movie to [g][genreCount], then increment genreCount by 1.
					System.out.println("Before switch");
					switch(movieGenre) {
						case "musical":
							all_movies[g][genre1] = movie;
							genre1++;
							break;
						case "comedy":
							all_movies[g][genre2] = movie;
							genre2++;
							break;
						case "animation":
							all_movies[g][genre3] = movie;
							genre3++;
							break;
						case "adventure":
							all_movies[g][genre4] = movie;
							genre4++;
							break;
						case "drama":
							all_movies[g][genre5] = movie;
							genre5++;
							break;
						case "crime":
							all_movies[g][genre6] = movie;
							genre6++;
							break;
						case "biography":
							all_movies[g][genre7] = movie;
							genre7++;
							break;
						case "horror":
							all_movies[g][genre8] = movie;
							genre8++;
							break;
						case "action":
							all_movies[g][genre9] = movie;
							genre9++;
							break;
						case "documentary":
							all_movies[g][genre10] = movie;
							genre10++;
							break;
						case "fantasy":
							all_movies[g][genre11] = movie;
							genre11++;
							break;
						case "mystery":
							all_movies[g][genre12] = movie;
							genre12++;
							break;
						case "sci-fi":
							all_movies[g][genre13] = movie;
							genre13++;
							break;
						case "family":
							all_movies[g][genre14] = movie;
							genre14++;
							break;
						case "western":
							all_movies[g][genre15] = movie;
							genre15++;
							break;
						case "romance":
							all_movies[g][genre16] = movie;
							genre16++;
							break;
						case "thriller":
							all_movies[g][genre17] = movie;
							genre17++;
							break;
					} //end of switch
						
					System.out.println("After switch");
						
				} //end of while loop
					
				// close the input stream
				ois.close();
				fis.close();
				
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
			} catch (IOException e) {
				System.out.println("IOException");
			}
		}
		
		System.out.println("Finished deserialization. Returning all_movies array.");
		fileInput.close();
		return all_movies;
		
	} //end of method
	
}
