import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Calendar;

public class WeatherGet {
    
    public static WeatherInformationParsed run(String city) throws IOException, RequestFailed {
    	int timeoutInSeconds = 5;	//connection timeout
        city = city.replaceAll(" ", "&");	//need "&" instead of " " to search
        
        //essentials to make http connection to API
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeoutInSeconds * 1000).setConnectionRequestTimeout(timeoutInSeconds * 1000).setSocketTimeout(timeoutInSeconds * 1000).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        
        ObjectMapper objectMapper = new ObjectMapper();	//constructs our object from JSON
        HttpGet getCommand = new HttpGet("http://api.openweathermap.org/data/2.5/forecast?q=" + city + ",uk&mode=json&appid=3f69dfc43f5609b2b1ff6217eb940866");
        WeatherData wd = (WeatherData) getContent(WeatherData.class, getCommand, httpClient, objectMapper);	//parse data
        WeatherInformationParsed wiP = convertFromWeatherDataToWeatherInformationParse(wd);	//cleans the parsed so we can use it as needed
        return wiP;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })	//suppresses type warnings
	private static Object getContent(Class c, HttpUriRequest getCommand, CloseableHttpClient httpClient, ObjectMapper objectMapper) throws IOException, RequestFailed {
        try {
            HttpResponse httpResponse = httpClient.execute(getCommand);
            return objectMapper.readValue(EntityUtils.toString(httpResponse.getEntity()), c);	//parses JSON into our object which has necessary getters and setters
        } catch(SocketTimeoutException t) {	//connection errors
            throw new RequestFailed("Request has timed out");
        } catch(org.apache.http.NoHttpResponseException p) {
            throw new RequestFailed("API Failed");
        }
    }

    private static WeatherInformationParsed convertFromWeatherDataToWeatherInformationParse (WeatherData wd) {
    	
    	//clean parsed JSON into usable information
        WeatherInformationParsed toReturn = new WeatherInformationParsed();	//cleaned object
        
        //location data
        toReturn.setCityName(wd.getCity().getName());
        toReturn.setCountryName(wd.getCity().getCountry());
        
        weatherForADay[] newArray = new weatherForADay[5];	//structure for each day obtained from the API
        
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = new weatherForADay();	//objects to store information per day
            
            //set day of week
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, i);
            newArray[i].setDayOfWeek(dateFormat.format(cal.getTime()));
        }
        
        for (int i = 0; i < wd.getList().size(); i++) {
        	
        	//adds all of the data
            Date todaysDate = new Date();
            Date dateOfWeather = wd.getList().get(i).getDate();
            
            int index = (int) ChronoUnit.DAYS.between(todaysDate.toInstant(), dateOfWeather.toInstant());
            
            weatherForAThreeHourlyPeriod thisPeriod = new weatherForAThreeHourlyPeriod();
            WeatherData.weatherDetails thisPeriodOld = wd.getList().get(i);
            
            thisPeriod.setDateAndTime(thisPeriodOld.getDate());
            thisPeriod.setTemp(Math.floor((thisPeriodOld.getMain().getTemp() - 273.05) * 100) / 100);
            thisPeriod.setTemp_max(Math.floor((thisPeriodOld.getMain().getTemp_max() - 273.05) * 100) / 100);
            thisPeriod.setTemp_min(Math.floor((thisPeriodOld.getMain().getTemp_min() - 273.05) * 100) / 100);
<<<<<<< HEAD
            thisPeriod.setWeatherForBackground(thisPeriodOld.getWeather().get(0).getMain());
=======
            
            //groups weather
>>>>>>> 23ce28cabc21726ba186b3ffd4bd0e3db20608a1
            switch(thisPeriodOld.getWeather().get(0).getMain())
            {
                case "Thunderstorm":
                    thisPeriod.setMain("Wet");
                    break;
                case "Drizzle":
                    thisPeriod.setMain("Wet");
                    break;
                case "Rain":
                    thisPeriod.setMain("Wet");
                    break;
                case "Snow":
                    thisPeriod.setMain("Wet");
                    break;
                case "Atmosphere":
                    thisPeriod.setMain("Foggy");
                    break;
                case "Clear":
                    thisPeriod.setMain("Dry");
                    break;
                case "Clouds":
                    thisPeriod.setMain("Dry");
                    break;
                default:
                    thisPeriod.setMain("Dry");
                    break;
            }

            //thisPeriod.setMain(thisPeriodOld.getWeather().get(0).getMain());
            thisPeriod.setDescription(thisPeriodOld.getWeather().get(0).getDescription());
            thisPeriod.setIcon(thisPeriodOld.getWeather().get(0).getIcon());
            thisPeriod.setWindSpeed(thisPeriodOld.getWind().getSpeed());
            thisPeriod.setWindDirection(thisPeriodOld.getWind().getDeg());
            
            try {
            	thisPeriod.setRainAmount(thisPeriodOld.getRain().getAmount());
            } catch (Exception e) {
            	thisPeriod.setRainAmount(0);
            }
            
            newArray[index].getList().add(thisPeriod);
            
        }
        
        //return the new object
        toReturn.setWeatherPerDay(newArray);
        return toReturn;
    }
}