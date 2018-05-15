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
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class WeatherGet {

    public static void main(String[] args) throws IOException, RequestFailed  {
    	
    }
    
    public static WeatherInformationParsed run() throws IOException, RequestFailed {
    	int timeoutInSeconds = 5;
        String city = "Chicago";
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeoutInSeconds * 1000).setConnectionRequestTimeout(timeoutInSeconds * 1000).setSocketTimeout(timeoutInSeconds * 1000).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpGet getCommand = new HttpGet("http://api.openweathermap.org/data/2.5/forecast?q=" + city + ",us&mode=json&appid=3f69dfc43f5609b2b1ff6217eb940866");
        WeatherData wd = (WeatherData) getContent(WeatherData.class, getCommand, httpClient, objectMapper);
        WeatherInformationParsed wiP = convertFromWeatherDataToWeatherInformationParse(wd);
        return wiP;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object getContent(Class c, HttpUriRequest getCommand, CloseableHttpClient httpClient, ObjectMapper objectMapper) throws IOException, RequestFailed {
        try {
            HttpResponse httpResponse = httpClient.execute(getCommand);
            return objectMapper.readValue(EntityUtils.toString(httpResponse.getEntity()), c);
        } catch(SocketTimeoutException t) {
            throw new RequestFailed("Request has timed out");
        } catch(org.apache.http.NoHttpResponseException p) {
            throw new RequestFailed("API Failed");
        }
    }

    private static WeatherInformationParsed convertFromWeatherDataToWeatherInformationParse (WeatherData wd) {
        WeatherInformationParsed toReturn = new WeatherInformationParsed();
        toReturn.setCityName(wd.getCity().getName());
        toReturn.setCountryName(wd.getCity().getCountry());
        weatherForADay[] newArray = new weatherForADay[5];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = new weatherForADay();
        }
        for (int i = 0; i < wd.getList().size(); i++) {
            Date todaysDate = new Date();
            Date dateOfWeather = wd.getList().get(i).getDate();
            int index = (int) ChronoUnit.DAYS.between( todaysDate.toInstant() ,  dateOfWeather.toInstant() );
            weatherForAThreeHourlyPeriod thisPeriod = new weatherForAThreeHourlyPeriod();
            WeatherData.weatherDetails thisPeriodOld = wd.getList().get(i);
            thisPeriod.setDateAndTime(thisPeriodOld.getDate());
            thisPeriod.setTemp(Math.floor((thisPeriodOld.getMain().getTemp() - 273.05) * 100) / 100);
            thisPeriod.setTemp_max(Math.floor((thisPeriodOld.getMain().getTemp_max() - 273.05) * 100) / 100);
            thisPeriod.setTemp_min(Math.floor((thisPeriodOld.getMain().getTemp_min() - 273.05) * 100) / 100);
            thisPeriod.setMain(thisPeriodOld.getWeather().get(0).getMain());
            thisPeriod.setDescription(thisPeriodOld.getWeather().get(0).getDescription());
            thisPeriod.setIcon(thisPeriodOld.getWeather().get(0).getIcon());
            thisPeriod.setWindSpeed(thisPeriodOld.getWind().getSpeed());
            thisPeriod.setWindDirection(thisPeriodOld.getWind().getDeg());
            thisPeriod.setRainAmount(thisPeriodOld.getRain().getAmount());
            newArray[index].getList().add(thisPeriod);
        }
        toReturn.setWeatherPerDay(newArray);
        return toReturn;
    }
}