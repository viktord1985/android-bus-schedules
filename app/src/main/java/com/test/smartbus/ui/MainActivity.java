package com.test.smartbus.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.test.smartbus.R;
import com.test.smartbus.TripsLoadTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DatePickerFragment.OnDateChosenListener,
        TripsListFragment.OnTripsListFragmentVisibilityChangedListener{

    private final String FAB_VISIBLE_KEY = "fabKey";
    private final String HOME_AS_UP_VISIBLE_KEY = "homeAsUpKey";
    private String PICK_DATES_DIALOG = "datesDialog";
    private String TRIP_LIST_FRAGMENT_TAG = "tripList";
    private int mFabVisible;
    private boolean isHomeAsUpEnabled;
    private TripsLoadTask mTripsLoadTask;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @OnClick(R.id.fab)
    void startSearchTrips(){
        DatePickerFragment fragment = DatePickerFragment.newInstance();
        fragment.show(getFragmentManager(), PICK_DATES_DIALOG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            isHomeAsUpEnabled = true;
                        } else {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            isHomeAsUpEnabled = false;
                        }
                    }
                });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
            }
        });

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_main, TripsListFragment.newInstance(), TRIP_LIST_FRAGMENT_TAG)
                    .commit();
        }
        // init Floating and back navigation buttons visibility
        else {
            mFabVisible = savedInstanceState.getInt(FAB_VISIBLE_KEY);
            if(mFabVisible ==View.VISIBLE)
                fab.setVisibility(View.VISIBLE);
            else fab.setVisibility(View.INVISIBLE);
            isHomeAsUpEnabled = savedInstanceState.getBoolean(HOME_AS_UP_VISIBLE_KEY);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUpEnabled);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FAB_VISIBLE_KEY, mFabVisible);
        outState.putBoolean(HOME_AS_UP_VISIBLE_KEY, isHomeAsUpEnabled);
    }

    @Override
    public void onDateChanged(String dateFrom, String dateTo) {
        mTripsLoadTask = new TripsLoadTask();
        mTripsLoadTask.doInBackground(dateFrom,dateTo);
    }

    @Override
    public void onFragmentVisibleChanged(int visible) {
        fab.setVisibility(visible);
        mFabVisible = visible;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTripsLoadTask!=null && isFinishing())
            mTripsLoadTask.cancel();
    }
}
