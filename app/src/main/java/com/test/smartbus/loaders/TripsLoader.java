package com.test.smartbus.loaders;

import android.content.Context;

import com.test.smartbus.dbase.DatabaseManager;
import com.test.smartbus.dbase.TripDetails;
import com.test.smartbus.util.DatePreferences;

import java.util.List;

/**
 * Created by Viktor Derk on 2/22/17.
 */

public class TripsLoader extends BaseLoader<List<TripDetails>> {

    private Context mContext;

    public TripsLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<TripDetails> doQuery(DatabaseManager databaseManager) {
        DatePreferences preferences = new DatePreferences(mContext);
        return databaseManager.getTripsFromToDate(preferences.getDateFrom(),
                preferences.getDateTo());
    }
}
