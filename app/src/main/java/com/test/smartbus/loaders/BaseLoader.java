package com.test.smartbus.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


import com.test.smartbus.dbase.DatabaseManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * {@link DatabaseManager} notify this loader throw {@link BaseLoader#mListener}
 * if content changed
 */
public abstract class BaseLoader<T> extends AsyncTaskLoader<T> {

    private final PropertyChangeListener mListener;
    private T mObject =null;
    private DatabaseManager mDatabaseManager;

    public BaseLoader(Context context) {
        super(context);
        mDatabaseManager = DatabaseManager.getInstance();

        mListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if (event.getPropertyName().equals(DatabaseManager.CONTENT_CHANGED_EVENT)) {
                    BaseLoader.super.onContentChanged();
                }
            }
        };

        mDatabaseManager.addContentChangedListener(mListener);
    }

    @Override
    protected void onStartLoading() {
        if (mObject != null)
            deliverResult(mObject);
        if (takeContentChanged() || mObject == null)
            forceLoad();
    }

    @Override
    public T loadInBackground() {
        mObject = doQuery(mDatabaseManager);
        return mObject;
    }

    @Override
    protected void onReset() {
        super.onReset();
        mDatabaseManager.removeContentChangedListener(mListener);
    }
    
    public abstract T doQuery(DatabaseManager databaseManager);
}
