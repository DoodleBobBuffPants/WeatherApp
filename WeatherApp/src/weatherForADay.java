import java.util.ArrayList;
import java.util.List;

public class weatherForADay {
    private List<weatherForAThreeHourlyPeriod> list = new ArrayList<>();

    public List<weatherForAThreeHourlyPeriod> getList() {
        return list;
    }

    public void setList(List<weatherForAThreeHourlyPeriod> lst) {
        this.list = lst;
    }
}