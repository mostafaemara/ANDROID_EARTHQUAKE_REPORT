package com.example.android.quakereport;

public class Earthquake {

private String url;
    private String location;
   private long date;
   private double magnitude;

    public Earthquake(double eqMagnitude, String eqLocation,long eqDate,String eqUrl){



        magnitude=eqMagnitude;
        location=eqLocation;
        date=eqDate;
        url=eqUrl;




    }


    public String getUrl() {
        return url;
    }

    public long getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public double getMagnitude() {
        return magnitude;
    }
}
