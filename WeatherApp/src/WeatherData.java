//imports to parse JSON
import com.fasterxml.jackson.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
	
	//reads in raw JSON
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class city {
    	
    	//variables for the attributes
        private int id;
        private String name;
        private coord coord;
        private String country;
        private int population;
        
        //getters and setters for each attribute
        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int get_id() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public coord getCoord() {
            return coord;
        }

        public void setCoord(coord coord) {
            this.coord = coord;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class coord {
    	
    	//coordinate data is in JSON so is parsed here and skipped in clean-up
        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class weather {
    	
    	//variables for weather data
        private String main;
        private String description;
        private String icon;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class weatherDetails {
    	
    	//more weather details
        private main main;
        private List<weather> weather;
        private String dt_txt;
        private Date date;


        private wind wind;
        private rain rain;

        public WeatherData.rain getRain() {
            return rain;
        }

        public void setRain(WeatherData.rain rain) {
            this.rain = rain;
        }

        public WeatherData.wind getWind() {
            return wind;
        }

        public void setWind(WeatherData.wind wind) {
            this.wind = wind;
        }

        public main getMain() {
            return main;
        }

        public void setMain(main main) {
            this.main = main;
        }


        public List<weather> getWeather() {
            return weather;
        }

        public void setWeather(List<weather> weather) {
            this.weather = weather;
        }

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                this.date = parser.parse(this.dt_txt);
            }
            catch(ParseException p)
            {
                //just leave it alone
            }
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class wind {
    	
    	//wind data
        private double speed = 0;
        private double deg = 0;

        public double getDeg() {
            return deg;
        }

        public void setDeg(double deg) {
            this.deg = deg;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class rain {
    	
    	//rain data
        private double amount = 0;

        @JsonProperty("3h")
        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class main {
    	
    	//main weather data - temperature
        private double temp;
        private double temp_min;
        private double temp_max;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }

    }

    @JsonProperty("city")
    private city city;

    @JsonProperty("city")
    public WeatherData.city getCity() {
        return city;
    }


    @JsonProperty("city")
    public void setCity(WeatherData.city city) {
        this.city = city;
    }

    @JsonProperty("list")
    private List<WeatherData.weatherDetails> list;

    @JsonProperty("list")
    public List<WeatherData.weatherDetails> getList() {
        return list;
    }

    @JsonProperty("list")
    public void setList(List<weatherDetails> list) {
        this.list = list;
    }

}
