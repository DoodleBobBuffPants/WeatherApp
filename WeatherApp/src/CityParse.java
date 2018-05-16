//necessary imports
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeSet;

public class CityParse {
	
	public static TreeSet<String> parse() throws IOException {
		
		Path cityFile = Paths.get("cities.txt");	//path to txt from API website
		TreeSet<String> cities = new TreeSet<String>();	//alphabetised set of cities
		
		//try to read from file
		try (BufferedReader reader = Files.newBufferedReader(cityFile)) {	//open txt file for reading
			int i = 0;
			String txt = reader.readLine();
			while (txt != "" && i < 50) {
				String[] words = txt.split("\\s+");	//gets each field of each record
				String[] city = Arrays.copyOfRange(words, 1, words.length - 3);	//extract just the city name
				String lName = String.join(" ", city);
				cities.add(lName);	//adds city name to set
				txt = reader.readLine();	//next line
				i++;	//limit number of values
			}
			
			reader.close();	//close reader
			cities.remove("nm");	//removes header as only need city names
			return cities;	//returns the set of cities
			
		} catch (IOException e) {	//can't open file
			throw new IOException("Can't access file " + cityFile, e);	//exception only if file not available
		}
		
	}
	
}