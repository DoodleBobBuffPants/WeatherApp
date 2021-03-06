import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.io.Serializable;

/**
 * Weather info for a period of three hours
 * Used to provide a breakdown of information for any given day,
 * with detailed info such as wind speed and temperature
 *
 *
 */
public class weatherForAThreeHourlyPeriod implements Serializable {
	
	//variables corresponding to data from API
    private Date dateAndTime = new Date();
    private LocalTime time;
    private double temp = 0;
    private double temp_min = 0;
    private double temp_max = 0;
    private String main = "";
    private String weatherForBackground = "";
    private String description = "";
    private String icon = "";
    private double windSpeed = 0;
    private double windDirection = 0;
    private double rainAmount = 0;

    /**
     * @return The appropriate (relative) filename to be used for the background of the main screen
     */
    public String getWeatherForBackground() {
        return weatherForBackground;
    }

    public void setWeatherForBackground(String weatherForBackground) {
        this.weatherForBackground = weatherForBackground;
    }
    
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }
    
    public Path getIconPath() {
    	return Paths.get("resources/" + getIcon() + ".png");
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
        Instant instant = Instant.ofEpochMilli(dateAndTime.getTime());
        LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        setTime(res);
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