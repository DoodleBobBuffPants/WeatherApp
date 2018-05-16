//necessary imports
import java.util.ArrayList;
import java.util.List;

public class weatherForADay {
	
    private List<weatherForAThreeHourlyPeriod> list = new ArrayList<>();	//list of weather information for each 3-hourly period
    private String dayOfWeek;
    
    //list getter and setter
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