//necessary imports
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class weatherForADay implements Serializable {
	
    private List<weatherForAThreeHourlyPeriod> list = new ArrayList<>();	//list of weather information for each 3-hourly period
    private String dayOfWeek;	//day of week
    
    //getters and setters
    public List<weatherForAThreeHourlyPeriod> getList() {
        return list;
    }
    
    public void setList(List<weatherForAThreeHourlyPeriod> lst) {
        this.list = lst;
    }
    
    public String getDayOfWeek() {
    	return dayOfWeek;
    }
    
    public void setDayOfWeek(String dayOfWeek) {
    	this.dayOfWeek = dayOfWeek;
    }
}