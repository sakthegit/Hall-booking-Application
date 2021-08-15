package com.example.helloworld;

public class request_fetch {
    private String name,dept,venue,time,date,mail;

    public request_fetch() {

    }
    public request_fetch(String name,String dept,String date,String venue,String time,String mail){
        this.dept=dept;
        this.name = name;
        this.time = time;
        this.mail = mail;
        this.date = date;
        this.venue = venue;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
