package com.example.android.quakereport;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import 	android.support.v4.content.ContextCompat;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Date;

import android.widget.TextView;

import com.example.android.quakereport.Earthquake;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;



public class EarthquakeAdapter extends ArrayAdapter {
    private static final String LOCATION_SEPARATOR = " of ";
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView =convertView;
        if(listView==null){


            listView= LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent,false);


        }
Earthquake currentEarthquake= (Earthquake) getItem(position);



        String mag=formatMagnitude(currentEarthquake.getMagnitude());

        String originallocation=currentEarthquake.getLocation();
        String primaryLocation,locationOffset;
        if(originallocation.contains(LOCATION_SEPARATOR)){

            String[] parts=originallocation.split(LOCATION_SEPARATOR);
            locationOffset=parts[0];
            primaryLocation=parts[1];



        }else{
            locationOffset=getContext().getString(R.string.near_the);
            primaryLocation=originallocation;



        }


        TextView magnitudeView=(TextView)listView.findViewById(R.id.magnitude);
        magnitudeView.setText(mag);
        GradientDrawable magnitudeCircle=(GradientDrawable)magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

                TextView primaryLocationView=(TextView)listView.findViewById(R.id.primary_location);
primaryLocationView.setText(primaryLocation);
        TextView locationOffsetView=(TextView)listView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);



        Date dateObject=new Date(currentEarthquake.getDate());

TextView dateView = (TextView)listView.findViewById(R.id.date);
String date=formatDate(dateObject);
dateView.setText(date);
TextView timeView =(TextView)listView.findViewById(R.id.time);
String time=formatTime(dateObject);
timeView.setText(time);










        return listView;
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

      
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }
}
