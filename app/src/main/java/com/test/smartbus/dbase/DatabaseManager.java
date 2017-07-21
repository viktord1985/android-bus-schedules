package com.test.smartbus.dbase;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.test.smartbus.api.Trip;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Viktor Derk on 1/20/17.
 */

public class DatabaseManager {

    static private DatabaseManager mInstance;
    private DatabaseHelper mHelper;
    private List<PropertyChangeListener> mContentChangedListeners = new ArrayList<>();
    public static String CONTENT_CHANGED_EVENT="content";

    private DatabaseManager(Context context) {
        mHelper = new DatabaseHelper(context);
    }

    static public DatabaseManager getInstance() {
        return mInstance;
    }

    static public void init(Context context) {
        if (mInstance ==null) {
            mInstance = new DatabaseManager(context);
        }
    }

    private DatabaseHelper getHelper() {
        return mHelper;
    }

    private void notifyContentChangedListeners(){
        for (PropertyChangeListener listener : mContentChangedListeners)
            listener.propertyChange(new PropertyChangeEvent(this, CONTENT_CHANGED_EVENT, null, null));
    }

    public List<TripDetails> bulkInsert(List<Trip> data){

        // Transform data from List<Trip> to List<TripDetails>

        String pattern = "yyyy-MM-ddHH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        final ArrayList<TripDetails> trips = new ArrayList<>();

        for (Trip trip : data) {

            CityDetails fromCity = new CityDetails(trip.getFromCity().getId(),
                    trip.getFromCity().getName());
            CityDetails toCity = new CityDetails(trip.getToCity().getId(),
                    trip.getToCity().getName());

            Date fromDate = null;
            Date toDate = null;
            try {
                fromDate = dateFormat.parse(trip.getFromDate() + trip.getFromTime());
                toDate = dateFormat.parse(trip.getToDate() + trip.getToTime());
            } catch (ParseException e) {
            }

            TripDetails tripDetails = new TripDetails(trip.getId(), fromCity,
                    trip.getFromCity().getHighlight(), fromDate, trip.getFromInfo(),
                    toCity, trip.getFromCity().getHighlight(), toDate, trip.getToInfo(),
                    trip.getInfo(), trip.getPrice(), trip.getBusId(), trip.getReservationCount());

            trips.add(tripDetails);
        }

        // write data to db
        try {
            final Dao<CityDetails, Integer> cityDao = getHelper().getCityDao();
            final Dao<TripDetails, Integer> tripDao = getHelper().getTripDao();

            cityDao.callBatchTasks(new Callable<Void>() {
                public Void call() throws Exception {
                    for (TripDetails trip : trips) {
                        cityDao.createIfNotExists(trip.getFromCity());
                        cityDao.createIfNotExists(trip.getToCity());
                    }
                    return null;
                }
            });
            tripDao.callBatchTasks(new Callable<Void>() {
                public Void call() throws Exception {
                    for (TripDetails trip : trips) {
                        tripDao.createIfNotExists(trip);
                    }
                    return null;
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyContentChangedListeners();
        return trips;
    }

    public List<TripDetails> getAll() {
        try {
            Dao<TripDetails, Integer> tripDao = getHelper().getTripDao();
            return tripDao.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }

    public TripDetails getTripById(int id) {
        try {
            Dao<TripDetails, Integer> tripDao = getHelper().getTripDao();
            return tripDao.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }

    public void addContentChangedListener(PropertyChangeListener newListener) {
        mContentChangedListeners.add(newListener);
    }

    public void removeContentChangedListener(PropertyChangeListener newListener) {
        mContentChangedListeners.remove(newListener);
    }

    public List<TripDetails> getTripsFromToDate(Date dateFrom, Date dateTo) {
        try {
            QueryBuilder<TripDetails, Integer> qb = getHelper().getTripDao().queryBuilder();
            qb.where().between(TripDetails.DATE_FROM_FIELD,
                    dateFrom, dateTo);
            qb.orderBy(TripDetails.DATE_FROM_FIELD, true);
            return qb.query();
        } catch (SQLException e) {
            return null;
        }
    }
}
