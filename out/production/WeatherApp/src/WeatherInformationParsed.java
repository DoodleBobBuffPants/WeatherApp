public class WeatherInformationParsed {
	
	//class to use parsed JSON data
	//this is essentially a 5-day structure to view 5 days of weather information
	
	//essential attributes for this weekly view
    private String cityName = "";
    private String countryName = "";
    private weatherForADay[] weatherPerDay = new weatherForADay[5];

    //getters and setters
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