import java.util.ArrayList;
import java.util.List;

public class weatherForADay {
    private List<weatherForAThreeHourlyPeriod> list = new ArrayList<>();

    public List<weatherForAThreeHourlyPeriod> getList() {
        return list;
    }

    public void setList(List<weatherForAThreeHourlyPeriod> list) {
        this.list = list;
    }
}