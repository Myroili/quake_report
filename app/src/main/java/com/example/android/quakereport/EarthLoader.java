package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

public class EarthLoader extends android.support.v4.content.AsyncTaskLoader{


    private String  mUrl;

  EarthLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthModel> loadInBackground() {
        if(mUrl == null){
            return null;
        }

        List<EarthModel> earthModels = null;
        try {
            earthModels = QueryUtils.fetchDataFromJson(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return earthModels;
    }
}
