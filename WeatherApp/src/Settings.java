//imports
import java.time.LocalTime;
import java.time.Duration;
//class to store preferences
public class Settings {
	
	//variables for each preference
	private static LocalTime startTime = LocalTime.now();
	private static Duration duration = Duration.ZERO;
	private static WeatherEnum preferredWeather = WeatherEnum.Dry;
	private static String location = "London";
	
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
	
	public static void setpreferredWeather(WeatherEnum newPreferredWeather) {
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
}
