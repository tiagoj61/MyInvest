package bean;

import java.util.Date;

public class DayInfos {

    private double max;
    private double min;
    private double fechamento;
    private double abertura;
    private Date date;

    public double getAbertura() {
        return abertura;
    }

    public void setAbertura(double abertura) {
        this.abertura = abertura;
    }


    public double getFechamento() {
        return fechamento;
    }

    public void setFechamento(double fechamento) {
        this.fechamento = fechamento;
    }


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


}
