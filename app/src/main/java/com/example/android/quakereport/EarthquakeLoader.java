package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String mUrl;

    public EarthquakeLoader(Context context, String url){


        super(context);
mUrl=url;

    }
    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG,"LOader onstarting");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.e(LOG_TAG,"LOad in background");

        if(mUrl==null){



            return null;

        }

        List<Earthquake> result = QueryUtils.fetchEarthquakeData(mUrl);



        return result;
    }
}
