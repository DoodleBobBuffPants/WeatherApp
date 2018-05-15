import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherInformationParsed {
    private String cityName;
    private String countryName;
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

    public class weatherForADay {
        private List<weatherForAThreeHourlyPeriod> list = new ArrayList<>();

        public List<weatherForAThreeHourlyPeriod> getList() {
            return list;
        }

        public void setList(List<weatherForAThreeHourlyPeriod> list) {
            this.list = list;
        }
    }

    public class weatherForAThreeHourlyPeriod {
        private Date dateAndTime;
        private double temp;
        private double temp_min;
        private double temp_max;
        private String main;
        private String description;
        private String icon;
        private double windSpeed;
        private double windDirection;
        private double rainAmount;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public Date getDateAndTime() {
            return dateAndTime;
        }

        public void setDateAndTime(Date dateAndTime) {
            this.dateAndTime = dateAndTime;
        }

        public double getRainAmount() {
            return rainAmount;
        }

        public void setRainAmount(double rainAmount) {
            this.rainAmount = rainAmount;
        }

        public double getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(double windDirection) {
            this.windDirection = windDirection;
        }

        public double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
        }

    }
}
