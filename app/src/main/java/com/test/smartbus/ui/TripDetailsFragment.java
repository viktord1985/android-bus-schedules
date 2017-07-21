package com.test.smartbus.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.smartbus.R;
import com.test.smartbus.dbase.TripDetails;
import com.test.smartbus.loaders.TripDetailsLoader;

import java.text.DateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<TripDetails>{
    private static final String ID_ARG = "param1";
    private final int TRIP_DETAILS_LOADER_ID = 112;

    private int mId;

    @BindView(R.id.from_city_textView)
    TextView fromCityTextView;

    @BindView(R.id.from_date_textView)
    TextView fromDateTextView;

    @BindView(R.id.from_info_textView)
    TextView fromInfoTextView;

    @BindView(R.id.to_city_textView)
    TextView toCityTextView;

    @BindView(R.id.to_date_textView)
    TextView toDateTextView;

    @BindView(R.id.to_info_textView)
    TextView toInfoTextView;

    @BindView(R.id.info_textView)
    TextView infoTextView;

    @BindView(R.id.price_textView)
    TextView priceTextView;

    @BindView(R.id.bus_id_textView)
    TextView busIdTextView;

    @BindView(R.id.reservation_count_textView)
    TextView resCntTextView;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    public static TripDetailsFragment newInstance(int param1) {
        TripDetailsFragment fragment = new TripDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ID_ARG, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(ID_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trip_details, container, false);
        ButterKnife.bind(this, rootView);
        getLoaderManager().initLoader(TRIP_DETAILS_LOADER_ID, Bundle.EMPTY, this);
        return rootView;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new TripDetailsLoader(getActivity(), mId);
    }

    @Override
    public void onLoadFinished(Loader<TripDetails> loader, TripDetails trip) {
        fromCityTextView.setText(trip.getFromCity().getCityName());
        String date = DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.SHORT).format(trip.getFromDate());
        fromDateTextView.setText(date);
        fromInfoTextView.setText(trip.getFromInfo());

        toCityTextView.setText(trip.getToCity().getCityName());
        date = DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.SHORT).format(trip.getToDate());
        toDateTextView.setText(date);
        toInfoTextView.setText(trip.getToInfo());

        infoTextView.setText(trip.getInfo());
        priceTextView.setText(String.valueOf(trip.getPrice()));
        busIdTextView.setText(String.valueOf(trip.getBusId()));
        resCntTextView.setText(String.valueOf(trip.getReservationCount()));
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
