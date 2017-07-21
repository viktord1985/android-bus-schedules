package com.test.smartbus.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Artem on 2/22/17.
 */

public class DatePreferences {

    public String QUERY_PREF_NAME = "QuerySettings";
    public String FROM_DATE_PREF_KEY = "fromDate";
    public String TO_DATE_PREF_KEY = "toDate";

    private SharedPreferences datePrefs;
    private Date mDateFrom;
    private Date mDateTo;

    private String mStringDateFrom;
    private String mStringDateTo;
    private String DEFAULT_DATE_FROM = "2016-01-01";
    private String DEFAULT_DATE_TO = "2016-05-01";

    public DatePreferences(Context context) {
        datePrefs = context.getSharedPreferences(QUERY_PREF_NAME, MODE_PRIVATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(datePrefs.contains(FROM_DATE_PREF_KEY) && datePrefs.contains(TO_DATE_PREF_KEY)){
            mStringDateFrom = datePrefs.getString(FROM_DATE_PREF_KEY, DEFAULT_DATE_FROM);
            mStringDateTo = datePrefs.getString(TO_DATE_PREF_KEY, DEFAULT_DATE_TO);
        }
        else {
            mStringDateFrom = DEFAULT_DATE_FROM;
            mStringDateTo = DEFAULT_DATE_TO;
        }

        try {
            mDateFrom = sdf.parse(mStringDateFrom);
            mDateTo = sdf.parse(mStringDateTo);
        } catch (ParseException e) {
            mStringDateFrom = DEFAULT_DATE_FROM;
            mStringDateTo = DEFAULT_DATE_TO;
            mDateFrom = new Date(1451599200000L);
            mDateTo = new Date(1462050000000L);
        }
    }

    public Date getDateFrom() {
        return mDateFrom;
    }

    public Date getDateTo() {
        return mDateTo;
    }

    public String getStringDateFrom() {
        return mStringDateFrom;
    }

    public String getStringDateTo() {
        return mStringDateTo;
    }

    public void saveDates(String dateFrom, String dateTo) {
        SharedPreferences.Editor editor = datePrefs.edit();
        editor.putString(FROM_DATE_PREF_KEY, dateFrom);
        editor.putString(TO_DATE_PREF_KEY, dateTo);
        editor.apply();
    }
}
