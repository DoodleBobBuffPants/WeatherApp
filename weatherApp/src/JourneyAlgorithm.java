import java.time.*;
public class JourneyAlgorithm {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static boolean checkJourney(ZonedDateTime startTime, Duration duration, WeatherEnum preferredWeather, WeeklyWeatherObj weeklyWeather) {
		//Takes the duration and start time set in settings and the preferred weather and checks if there is a valid journey
		//from an object storing the weather data for the day and the next day (in case of overlap)
		DailyWeatherObj dailyArray[] = weeklyWeather.dailyArray();
		
		return true;
	}
}