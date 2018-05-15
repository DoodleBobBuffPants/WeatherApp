import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.System;
import java.net.SocketTimeoutException;

public class Weather {

    public static void main(String[] args) throws IOException, RequestFailed  {
        int timeoutInSeconds = 5;
        String city = "Chicago";
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeoutInSeconds * 1000).setConnectionRequestTimeout(timeoutInSeconds * 1000).setSocketTimeout(timeoutInSeconds * 1000).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpGet getCommand = new HttpGet("http://api.openweathermap.org/data/2.5/forecast?q=" + city + ",us&mode=json&appid=3f69dfc43f5609b2b1ff6217eb940866");
        WeatherData wd = (WeatherData) getContent(WeatherData.class, getCommand, httpClient, objectMapper);
        System.out.println("Hello World");

    }
    
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
}
