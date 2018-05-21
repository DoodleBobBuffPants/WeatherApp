//necessary imports
import java.io.Serializable;

public class WeatherInformationParsed implements Serializable {
	
	//class to use parsed JSON data
	//this is essentially a 5-day structure to view 5 days of weather information
	
	//essential attributes for this weekly view
    private String cityName = "";
    private String countryName = "";
    private int population = 0;
    private weatherForADay[] weatherPerDay = new weatherForADay[5];

    //getters and setters
    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
    
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public weatherForADay[] getWeatherPerDay() {
        return weatherPerDay;
    }

    public void setWeatherPerDay(weatherForADay[] weatherPerDay) {
        this.weatherPerDay = weatherPerDay;
    }
}