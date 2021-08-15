package com.example.helloworld;

public class list_data_period {
    private String period;
    private  String time;

    public String getPeriod() {
        return period ;

    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public list_data_period(String period,String time){
        this.period=period;
        this.time=time;
    }
    public list_data_period(){
        
    }
}
