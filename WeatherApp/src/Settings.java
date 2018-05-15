//imports
import java.time.LocalTime;
import java.time.Duration;
//class to store preferences
public class Settings {
	private static LocalTime startTime;
	private static Duration duration;
	private static WeatherEnum preferredWeather;
	private static String location;
	
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
