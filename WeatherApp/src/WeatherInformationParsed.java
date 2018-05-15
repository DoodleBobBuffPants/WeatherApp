
public class WeatherInformationParsed {
    private String cityName = "";
    private String countryName = "";
    private weatherForADay[] weatherPerDay = new weatherForADay[5];

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
