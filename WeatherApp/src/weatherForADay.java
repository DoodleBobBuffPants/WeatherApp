import java.util.ArrayList;
import java.util.List;

public class weatherForADay {
    private List<weatherForAThreeHourlyPeriod> list = new ArrayList<>();	//list of weather information for each period
    
    //list getter
    public List<weatherForAThreeHourlyPeriod> getList() {
        return list;
    }
    
    //set the list on creation
    public void setList(List<weatherForAThreeHourlyPeriod> lst) {
        this.list = lst;
    }
}