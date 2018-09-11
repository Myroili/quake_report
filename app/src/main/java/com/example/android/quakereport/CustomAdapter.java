package com.example.android.quakereport;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends ArrayAdapter<EarthModel> {
    private static  final String LOCATION_SEPARATOR = " of ";


    CustomAdapter(@NonNull Context context, @NonNull ArrayList<EarthModel> objects) {
        super(context,0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView== null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_holder,parent,false);
        }
         EarthModel model = getItem(position);
        TextView mag = listItemView.findViewById(R.id.mag);
        TextView city = listItemView.findViewById(R.id.primaryLocation);
        TextView date = listItemView.findViewById(R.id.date);
        TextView near = listItemView.findViewById(R.id.nearThe);
        TextView time = listItemView.findViewById(R.id.time);

        assert model != null;

        String originalLocation = model.getCity();


        String primaryLocation;
        String locationOffSet;

        if(originalLocation.contains(LOCATION_SEPARATOR)){
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffSet = parts[0]+LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }else {
            locationOffSet = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }


        long timesInMil = model.getTime();
        Date dateObject = new Date(timesInMil);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
        String dateToDisplay = simpleDateFormat.format(dateObject);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timedateFormat = new SimpleDateFormat("hh:mm a");
        String timeToDisplay = timedateFormat.format(dateObject);
        double magnitude = model.getMag();
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();
        int colorMagnitude = getMagnitudeColor(model.getMag());
        magnitudeCircle.setColor(colorMagnitude);

        date.setText(dateToDisplay);
        time.setText(timeToDisplay);
        mag.setText(output);
        city.setText(primaryLocation);
        near.setText(locationOffSet);

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId ;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            case 10:
                magnitudeColorResourceId = R.color.magnitude10plus;
                return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
            default:
                return ContextCompat.getColor(getContext(),R.color.colorPrimary);
        }

    }
}
