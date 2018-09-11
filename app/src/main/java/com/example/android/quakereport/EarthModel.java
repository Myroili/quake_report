package com.example.android.quakereport;

public class EarthModel
{
    private Double mag;
    private String city;
    private long time;
    private String url;


    EarthModel(Double mag, String city, long time,String url) {
        this.mag = mag;
        this.city = city;
        this.time = time;
        this.url = url;
    }

    public Double getMag() {
        return mag;
    }

    public String getCity() {
        return city;
    }

    public String getUrl() {
        return url;
    }

    public long getTime() {
        return time;
    }
}
