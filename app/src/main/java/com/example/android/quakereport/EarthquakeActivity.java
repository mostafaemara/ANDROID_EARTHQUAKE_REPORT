/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
private EarthquakeAdapter adapter;
private  static final int EARTHQUAKE_LOADER_ID=1;
private  TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        emptyTextView=(TextView)findViewById(R.id.empty_view);




        // Create a fake list of earthquake locations.


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);


        // Create a new {@link ArrayAdapter} of earthquakes
      adapter= new EarthquakeAdapter(this,new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                Earthquake currentEarthquake= (Earthquake) adapter.getItem(postion);
                Uri earthquakeUri=Uri.parse(currentEarthquake.getUrl());

                Intent i=new Intent(Intent.ACTION_VIEW,earthquakeUri);
                startActivity(i);
            }
        });


        ConnectivityManager conectivityManger=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=conectivityManger.getActiveNetworkInfo();

        if(networkInfo!=null&& networkInfo.isConnected()){

            LoaderManager loaderManager=getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);



        }else {

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet_connection);

        }

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG,"LOader Created");

        return new EarthquakeLoader(this,QueryUtils.EARTHQUAKE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        {
            emptyTextView.setText(R.string.no_earthquakes);
            Log.e(LOG_TAG,"LOader Finished");
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);


            adapter.clear();

            if(data!=null && !data.isEmpty()) {
               adapter.addAll(data);
               emptyTextView.setVisibility(View.GONE);

            }



        }



    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.e(LOG_TAG,"LOader Reset");
        adapter.clear();

    }


}
