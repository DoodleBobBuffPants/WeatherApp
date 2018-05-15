//time imports
import java.time.LocalTime;
import java.time.Duration;

public class JourneyAlgorithm {
	
	public static void main(String[] args) {
		//check for a valid journey by inspecting the weather in all periods of time for the preferred duration, and recommending alternatives if the preferred is unachievable
	}

	public static String checkJourney(LocalTime startTime, Duration duration, WeatherEnum preferredWeather, WeatherInformationParsed weeklyWeather) {
		
		weatherForADay dailyArray[] = weeklyWeather.getWeatherPerDay();	//array storing weather data for each day of the week as objects within an object for the week
		if (validJourney(startTime, duration, preferredWeather, 0, dailyArray)) return "You have a valid journey";	//notifies the user of a valid journey as per preferences
		
		//variables for use in recommending a journey
		boolean noValidJourney = true;	//loop condition for finding another journey time to recommend
		boolean RValid = false;	//boolean identifier for valid journey on the right

		LocalTime RTime = startTime.plusHours(1);	//time after the start time
		
		int RDay = (RTime.getHour() - startTime.getHour() < 0) ? 1 : 0;	//variable for determining which day of the week we are looking at for journeys
		
		while (noValidJourney) {	//iteration until we find another journey time to recommend - exits when no journey exists
			
			//check all times after the start time
			if (RDay < 5) {	//condition for going as far right as possible
				RValid = validJourney(RTime, duration, preferredWeather, RDay, dailyArray);	//check if journey is valid
				RDay += (RTime.getHour() - RTime.minusHours(1).getHour() < 0) ? 1 : 0;	//update the day if we crossover the midnight point
				RTime = RTime.plusHours(1);	//adjust time for next iteration
			}
			
			if(RValid) return "There was no journey at the specified time. An alternative is at " + RTime.toString() + (RDay == 0 ? "today" : RDay + " days from now");	//return recommended times if they were found
			if(RDay >= 5) noValidJourney = false; //updates boolean condition to false if no more times can be checked
		}
		
		return "No valid journey was found";	//message to say no valid journey was found
		
	}
	
	private static boolean validJourney(LocalTime startTime, Duration duration, WeatherEnum preferredWeather, int day, weatherForADay dailyArray[]) {
		
		//declares variables for use
		LocalTime currentTime = startTime;	//current time for checking periods of the day
		LocalTime endTime = startTime.plus(duration);	//limit of our search
		
		int startIndex = (startTime.getHour() - 1) / 3;	//which slot in the array of 3-hourly weather data we start at - corrected for the 1 o'clock start
		int endIndex = (endTime.getHour() + (endTime.getMinute() > 0 ? 1 : 0) - 2) / 3;	//which slot in the array of 3-hourly weather data we end at - corrected for the 1 o'clock start and not including the next section
		int dayReset = 0;	//offset to make sure we index the correct location when day rolls over
		
		//iterate over the periods of the day in question
		for (int i = startIndex; i <= endIndex; i++) {
			
			if (dailyArray[day].getList().get(i - dayReset).getMain() != preferredWeather.toString()) return false;	//mismatch with preferred weather => no valid journey
			
			if (currentTime.getHour() < 24 & currentTime.plusHours(3).getHour() >= 0) {	//checks if we have moved to the next day
				day++;	//increment the day index variable
				if (day > 4) return false;	//no valid journey if we have gone through all of the days
				dayReset = i + 1;	//reset the period-of-day indexer to start at 0 upon day roll-over
			}

			currentTime = currentTime.plusHours(3);	//updates the time which corresponds to the period we are checking
			
		}
		
		return true;	//there is a valid journey at this point
		
	}
	
}