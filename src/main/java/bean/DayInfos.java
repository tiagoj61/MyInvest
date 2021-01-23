package bean;

import java.util.Date;

public class DayInfos {

    private double max;
    private double min;

    public double getFechamento() {
        return fechamento;
    }

    public void setFechamento(double fechamento) {
        this.fechamento = fechamento;
    }

    private double fechamento;

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;


}
