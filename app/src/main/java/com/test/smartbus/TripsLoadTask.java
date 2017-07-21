package com.test.smartbus;

import android.os.AsyncTask;

import com.test.smartbus.api.Trips;
import com.test.smartbus.api.TripsService;
import com.test.smartbus.dbase.DatabaseManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Viktor Derk on 1/20/17.
 */
public class TripsLoadTask  {

    private TripsService mTripsService;
    private Call<Trips> mCall;
    private AsyncTask<Void,Void,Void> mInsertInDbTask;

    public void doInBackground(String... dates) {
        if ( dates[0].equals("") && dates[1].equals("") ) return;

        mTripsService = TripsService.builder.create(TripsService.class);
        mCall = mTripsService.getTripsByDate(dates[0], dates[1]);

        mCall.enqueue(new Callback<Trips>() {
            @Override
            public void onResponse(Call<Trips> call, Response<Trips> response) {
                if (response.isSuccessful()) {
                    final Trips trips = response.body();
                    if (trips.getSuccess()) {
                        mInsertInDbTask = new AsyncTask<Void, Void, Void>(){

                            @Override
                            protected Void doInBackground(Void... params) {
                                DatabaseManager.getInstance().bulkInsert(trips.getData());
                                return null;
                            }
                        }.execute();
                    }
                }
            }

            @Override
            public void onFailure(Call<Trips> call, Throwable t) {
            }
        });
    }

    public void cancel() {
        if(mInsertInDbTask !=null && mInsertInDbTask.getStatus()==AsyncTask.Status.RUNNING)
            mInsertInDbTask.cancel(true);
        if(mCall!=null && mCall.isExecuted())
            mCall.cancel();
    }
}
