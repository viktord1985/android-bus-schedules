package com.test.smartbus.ui;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.test.smartbus.R;
import com.test.smartbus.dbase.TripDetails;
import com.test.smartbus.loaders.TripsLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTripsListFragmentVisibilityChangedListener} interface
 * to handle interaction events.
 * Use the {@link TripsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<TripDetails>>{

    private OnTripsListFragmentVisibilityChangedListener mListener;

    private final int TRIPS_LOADER_ID = 111;
    private ArrayAdapter<TripDetails> tripsAdapter;
    private String TRIP_DETAILS_FRAGMENT_TAG = "tripDet";

    @BindView(R.id.trips_listView)
    ListView mTripListView;

    public TripsListFragment() {
        // Required empty public constructor
    }

    public static TripsListFragment newInstance() {
        return new TripsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trips, container, false);
        ButterKnife.bind(this, rootView);

        tripsAdapter = new TripsArrayAdapter(getActivity(), new ArrayList<TripDetails>());

        mTripListView.setAdapter(tripsAdapter);

        // Show trip details
        mTripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TripsArrayAdapter.ViewHolder holder = (TripsArrayAdapter.ViewHolder) view.getTag();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, TripDetailsFragment.newInstance(holder.id),
                                TRIP_DETAILS_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });

        getLoaderManager().initLoader(TRIPS_LOADER_ID, Bundle.EMPTY, this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTripsListFragmentVisibilityChangedListener) {
            mListener = (OnTripsListFragmentVisibilityChangedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTripsListFragmentVisibilityChangedListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onFragmentVisibleChanged(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onFragmentVisibleChanged(View.INVISIBLE);
        }
    }

    @Override
    public Loader<List<TripDetails>> onCreateLoader(int id, Bundle args) {
        return new TripsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<TripDetails>> loader, List<TripDetails> data) {
        if(data==null || data.size()==0) {
            tripsAdapter.clear();
            Toast.makeText(getActivity(), R.string.nothing_found, Toast.LENGTH_SHORT).show();
            return;
        }
        tripsAdapter.clear();
        tripsAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<TripDetails>> loader) {

    }

    public interface OnTripsListFragmentVisibilityChangedListener {
        void onFragmentVisibleChanged(int visible);
    }
}