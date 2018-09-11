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




import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthModel>> {



    private ListView earthquakeListView;
    private CustomAdapter customAdapter;
    private static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();
    private static final String URL_USGS =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG,"Test: Earthquake Activity onCreate() called ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.


        // Find a reference to the {@link ListView} in the layout
         earthquakeListView = findViewById(R.id.list);

         customAdapter = new CustomAdapter(this,new ArrayList<EarthModel>());
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null ,this);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthModel currentEarth = customAdapter.getItem(i);

                assert currentEarth != null;
                Uri earthUri = Uri.parse(currentEarth.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW,earthUri);
                startActivity(websiteIntent);
            }
        });

    }

    @Override
    public Loader<List<EarthModel>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG,"Test: onCreateLoader called ");
        return new EarthLoader(this,URL_USGS);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<EarthModel>> loader, List<EarthModel> data) {
        Log.i(LOG_TAG,"TEST: onLoadFinished() called ...");
        customAdapter.clear();

        if(earthquakeListView!=null && !data.isEmpty()){
            customAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<EarthModel>> loader) {
        Log.i(LOG_TAG,"TEST: onLoadFinished() called ...");
        customAdapter.clear();
    }








}
