package com.test.smartbus.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.test.smartbus.R;
import com.test.smartbus.dbase.TripDetails;

import java.text.DateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Viktor Derk on 2/20/17.
 */
class TripsArrayAdapter extends ArrayAdapter<TripDetails> {
    private final Activity context;

    TripsArrayAdapter(Activity context, ArrayList<TripDetails> values) {
        super(context, -1, values);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.trip_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();

        TripDetails trip = getItem(position);

        holder.tripCities.setText(trip.getFromCity().getCityName() +
                " - " + trip.getToCity().getCityName());

        String date = DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.SHORT).format(trip.getToDate());

        holder.dateFrom.setText(date);
        holder.price.setText(String.valueOf(trip.getPrice()));
        holder.id = trip.getTripId();
        return convertView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.trip_cities_textView)
        TextView tripCities;

        @BindView(R.id.trip_date_from_textView)
        TextView dateFrom;

        @BindView(R.id.trip_price_textView)
        TextView price;

        int id;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
