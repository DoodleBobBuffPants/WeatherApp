import java.time.*;
public class JourneyAlgorithm {
	public static void main(String[] args) {
	}
	public static String checkJourney(LocalTime startTime, Duration duration, WeatherEnum preferredWeather, WeeklyWeatherObj weeklyWeather) {
		
		int day = 0;
		
		if (validJourney(startTime, duration, preferredWeather, day, weeklyWeather)) return "You have a valid journey";
		
		
		
		return "";
	}
	private static boolean validJourney(LocalTime startTime, Duration duration, WeatherEnum preferredWeather, int day, WeeklyWeatherObj weeklyWeather) {
		
		DailyWeatherObj dailyArray[] = weeklyWeather.dailyArray;
		
		if (startTime.getHour() % 3 == 0) {
			startTime = startTime.minusHours(2);
		} else if (startTime.getHour() % 3 == 2) {
			startTime = startTime.minusHours(1);
		}
		
		LocalTime endTime = startTime.plus(duration);
		
		int startIndex = (startTime.getHour() - 1) / 3;
		int endIndex = (endTime.getHour() + (endTime.getMinute() > 0 ? 1: 0) - 2) / 3;
		
		LocalTime currentTime = startTime;
		
		for (int i = startIndex; i <= endIndex; i++) {
			if (dailyArray[day].weather[i] != preferredWeather) {
				return false;
			}
			if (currentTime.getHour() < 24 & currentTime.plusHours(3).getHour() >= 0) {
				day++;
			}
			currentTime = currentTime.plusHours(3);
		}
		
		return true;
	}
}