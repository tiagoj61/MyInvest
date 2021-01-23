package bean;

import java.util.ArrayList;
import java.util.List;

public class Ativo {
    private String name;
    private List<DayInfos> dayInfos;

    public Ativo() {
        dayInfos = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<bean.DayInfos> getDayInfos() {
        return dayInfos;
    }

    public void addDayInfos(bean.DayInfos dayInfos) {
        this.dayInfos.add(dayInfos);
    }
}
