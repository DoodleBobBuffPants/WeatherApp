//necessary imports
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;

public class CityParse {
	
	public static TreeSet<String> parse() throws IOException {
		
		Path cityFile = Paths.get("cities.txt");	//path to txt from API website
		TreeSet<String> cities = new TreeSet<String>();	//alphabetised set of cities

		try (BufferedReader reader = Files.newBufferedReader(cityFile)) {
			String txt = reader.readLine();
			while (txt != null && txt != "") {
				cities.add(txt);	//adds city name to set
				txt = reader.readLine();	//next line
			}
			reader.close();	//close reader
			return cities;
		}
		catch (IOException e) {	//can't open file
			throw new IOException("Can't access file " + cityFile, e);	//exception only if file not available
		}
		
	}
}