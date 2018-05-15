//necessary imports
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;
import java.util.function.Consumer;

public class CityParse {
	
	public static TreeSet<String> parse() throws IOException {
		
		Path cityFile = Paths.get("cities.txt");	//path to txt from API website
		TreeSet<String> cities = new TreeSet<String>();	//alphabetised set of cities
		
		//try to read from file
		try (BufferedReader reader = Files.newBufferedReader(cityFile)){	//open txt file for reading
			reader.lines().forEach(new Consumer<String>() {	//iterate through all lines
				@Override
				public void accept(String line) {
					cities.add(line.split("\\s+")[1]);	//adds just city name to set
				}
			});
		} catch (IOException e) {	//can't open file
			throw new IOException("Can't access file " + cityFile, e);	//exception only if file not available
		}
		
		cities.remove("nm");	//removes header as only need city names
		return cities;	//returns the set of cities
		
	}
	
}