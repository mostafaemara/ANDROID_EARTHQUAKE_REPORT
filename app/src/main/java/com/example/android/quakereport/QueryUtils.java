package com.example.android.quakereport;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {



    /** Sample JSON response for a USGS query */
    public static final String EARTHQUAKE_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */


private static URL createUrl(String stringUrl){

    URL url=null;
    try {
        url = new URL(stringUrl);
        
       

    }catch (MalformedURLException exp) {
       Log.e(LOG_TAG, "Error with creating URL",exp);
        return null;
    }
    
    return url;


}


private static String readFromInputStream(InputStream inputStream) throws IOException {

    StringBuilder output = new StringBuilder();

    if(inputStream!=null){

        InputStreamReader inputStreamReader= new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader=new BufferedReader(inputStreamReader);
        String line=reader.readLine();
        while (line!=null){

            output.append(line);
            line=reader.readLine();

        }





    }


    return output.toString();


}
private static  String makeHttpRequest(URL url) throws IOException{


    String jsonResponse="";

    if(url==null){

        return jsonResponse;

    }

    HttpURLConnection httpURLConnection=null;
    InputStream inputStream=null;

    try{
httpURLConnection=(HttpURLConnection)url.openConnection();
httpURLConnection.setRequestMethod("GET");
httpURLConnection.setReadTimeout(10000);
httpURLConnection.setConnectTimeout(15000);
httpURLConnection.connect();
if(httpURLConnection.getResponseCode()==200){

    inputStream=httpURLConnection.getInputStream();
    jsonResponse=readFromInputStream(inputStream);


}

    }catch (IOException e){

Log.e(LOG_TAG,"Error code json");



    } finally {


        if(httpURLConnection!=null){


            httpURLConnection.disconnect();

        }
        if(inputStream!=null){


            inputStream.close();
        }









    }






return jsonResponse;



}


    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> extractEarthquakes(String earthquakeJson) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject baseJasonResponse=new JSONObject(earthquakeJson);
            JSONArray earthQuakeArray=baseJasonResponse.getJSONArray("features");
            for(int i=0;i<earthQuakeArray.length();i++){

JSONObject currentEarthQuake=earthQuakeArray.getJSONObject(i);
JSONObject properties=currentEarthQuake.getJSONObject("properties");
double  magnitude=properties.getDouble("mag");
String  place=properties.getString("place");
long  time=properties.getLong("time");
String url=properties.getString("url");

earthquakes.add(new Earthquake(magnitude,place,time,url));

}

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    public static  List<Earthquake> fetchEarthquakeData(String requestURl){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        URL url=createUrl(requestURl);
        String jsonResponse=null;
        try{

            jsonResponse=makeHttpRequest(url);



        }catch (IOException e){

            Log.e(LOG_TAG,"problem making http request");




        }

        List<Earthquake> earthquakeList=extractEarthquakes(jsonResponse);



return  earthquakeList;






    }


}