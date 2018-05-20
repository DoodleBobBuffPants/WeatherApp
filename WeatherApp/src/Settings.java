//imports
import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
//class to store preferences
public class Settings {
	
	//variables for each preference
	private static LocalTime startTime;
	private static Duration duration;
	private static WeatherEnum preferredWeather;
	private static String location;
	
	//getters and setters for each preference
	public static void setStartTime(LocalTime newStart) {
		startTime = newStart;
	}
	
	public static LocalTime getStartTime() {
		return startTime;
	}
	
	public static void setDuration(Duration newDuration) {
		duration = newDuration;
	}
	
	public static Duration getDuration() {
		return duration;
	}
	
	public static void setPreferredWeather(WeatherEnum newPreferredWeather) {
		preferredWeather = newPreferredWeather;
	}
	
	public static WeatherEnum getPreferredWeather() {
		return preferredWeather;
	}
	
	public static void setLocation(String newLocation) {
		location = newLocation;
	}
	
	public static String getLocation() {
		return location;
	}
	
	public static void saveSettings() {
		//write to settings file
		try {
		    PrintWriter saver = new PrintWriter(new FileWriter("settings.txt", false));
		    saver.println(getStartTime().toString());
		    saver.println(getDuration().toString());
		    saver.println(getPreferredWeather().toString());
		    saver.println(getLocation());
		    saver.close();
		} catch (IOException e) {
		    e.printStackTrace();
		} 
	}
	
	public static void loadSettings() {
		//loads from setting file
		try (BufferedReader loader = new BufferedReader(new FileReader("settings.txt"))) {
		    String line = loader.readLine();
		    setStartTime(LocalTime.parse(line));
		    line = loader.readLine();
		    setDuration(Duration.parse(line));
		    line = loader.readLine();
		    for (WeatherEnum we: WeatherEnum.values()) {
		    	if (we.toString().equals(line)) {
		    		setPreferredWeather(we); break;
		    	}
		    }
		    line = loader.readLine();
		    setLocation(line);
		    loader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		} 
	}
}