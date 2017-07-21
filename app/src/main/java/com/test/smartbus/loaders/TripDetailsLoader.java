package com.test.smartbus.loaders;

import android.content.Context;

import com.test.smartbus.dbase.DatabaseManager;
import com.test.smartbus.dbase.TripDetails;

/**
 * Created by Viktor Derk on 2/22/17.
 */

public class TripDetailsLoader extends BaseLoader<TripDetails> {

    private int mId;

    public TripDetailsLoader(Context context, int id) {
        super(context);
        mId = id;
    }

    @Override
    public TripDetails doQuery(DatabaseManager databaseManager) {
        return databaseManager.getTripById(mId);
    }
}
