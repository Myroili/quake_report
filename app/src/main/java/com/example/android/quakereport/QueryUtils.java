package com.example.android.quakereport;


import android.text.TextUtils;
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
import java.util.ArrayList;

public final class QueryUtils {


        private static final String LOG_TAG = QueryUtils.class.getSimpleName();
        /** Sample JSON response for a USGS query */
        private QueryUtils() {
        }

        /**
         * Return a list of {@link EarthModel} objects that has been built up from
         * parsing a JSON response.
         */

        private static URL createUrl(String stringUrl){
            URL url = null;
            try {
                url = new URL(stringUrl);
            }catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error with creating URL ", e);
            }
            return url;
        }

        private static String makeHttpRequest(URL url) throws IOException{
            String jsonResponce = "";
            if(url == null){
            return jsonResponce;
            }
            HttpURLConnection  httpURLConnection = null;
            InputStream inputStream =null;
          try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000 /* milliseconds */);
            httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponce = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
        Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
    } finally {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }
        return jsonResponce;

        }
        private static String readFromStream(InputStream inputStream)throws IOException{
            StringBuilder output = new StringBuilder();
            if(inputStream!=null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while(line!=null){
                    output.append(line);
                    line = bufferedReader.readLine();
                }

            }
            return output.toString();
        }
        private static ArrayList<EarthModel> extractEarthquakes(String earthquakesJson) {

            // Create an empty ArrayList that we can start adding earthquakes to
           ArrayList<EarthModel> earthquakes = new ArrayList<>();

            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            if (TextUtils.isEmpty(earthquakesJson)) {
                return null;
            }
            try {
                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of Earthquake objects with the corresponding data.
                JSONObject baseJsonObject = new JSONObject(earthquakesJson);
                JSONArray earthquakeArray = baseJsonObject.getJSONArray("features");
                for (int i = 0;i<earthquakeArray.length();i++){
                    JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                    JSONObject properties = currentEarthquake.getJSONObject("properties");
                    double magnitude = properties.getDouble("mag");
                    String location = properties.getString("place");
                    long time = properties.getLong("time");
                    String url = properties.getString("url");
                    earthquakes.add(new EarthModel(magnitude,location,time,url));
                }

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            return earthquakes;
        }


        public static ArrayList<EarthModel> fetchDataFromJson(String urLS) throws IOException {
            Log.i(LOG_TAG,"TEST: fetchEarthquakeData()  called ...");
            URL url = createUrl(urLS);
            String responceJson = makeHttpRequest(url);
            return extractEarthquakes(responceJson);
        }


}
