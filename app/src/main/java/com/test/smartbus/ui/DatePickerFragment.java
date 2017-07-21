package com.test.smartbus.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.test.smartbus.R;
import com.test.smartbus.util.DatePreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Viktor Derk on 2/21/17.
 */

public class DatePickerFragment extends DialogFragment {

    private OnDateChosenListener mCallback;
    private Date mDateFrom;
    private Date mDateTo;
    private DatePreferences datePrefs;

    public interface OnDateChosenListener {
        void onDateChanged(String dateFrom, String dateTo);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnDateChosenListener) activity;
        } catch (Exception ignored) {}
    }

    public static DatePickerFragment newInstance() {
        return new DatePickerFragment();
    }

    @BindView(R.id.date_from_Picker)
    DatePicker mDateFromPicker;

    @BindView(R.id.date_to_Picker)
    DatePicker mDateToPicker;

    @BindView(R.id.date_from_textView)
    TextView mDateFromTextView;

    @BindView(R.id.date_to_textView)
    TextView mDateToTextView;

    @OnClick(R.id.date_from_textView)
    void onDateFromClick(){
        mDateToPicker.setVisibility(View.GONE);
        mDateFromPicker.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.date_to_textView)
    void onDateToClick(){
        mDateFromPicker.setVisibility(View.GONE);
        mDateToPicker.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ok_button)
    void onOkButtonClick(){
        if(mDateFrom.getTime()>mDateTo.getTime()) {
            Toast.makeText(getActivity(), R.string.second_date_less_error, Toast.LENGTH_SHORT).show();
            return;
        }
        if(mCallback!=null)
            mCallback.onDateChanged(mDateFromTextView.getText().toString(),
                    mDateToTextView.getText().toString());
        // save query parameters
        datePrefs.saveDates(mDateFromTextView.getText().toString(),
                mDateToTextView.getText().toString());
        dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = ((LayoutInflater)getActivity().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE)).inflate (R.layout.fragment_request, null);

        ButterKnife.bind(this, rootView);

        datePrefs = new DatePreferences(getActivity());

        mDateFromTextView.setText(datePrefs.getStringDateFrom());
        mDateToTextView.setText(datePrefs.getStringDateTo());
        mDateFrom = datePrefs.getDateFrom();
        mDateTo = datePrefs.getDateTo();

        // init DatePickers
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(mDateFrom);

        mDateFromPicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        onDatePickerClick(true, year, monthOfYear, dayOfMonth);
                    }
                });

        calendar.setTime(mDateTo);

        mDateToPicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        onDatePickerClick(false, year, monthOfYear, dayOfMonth);
                    }
                });

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(rootView)
                .create();
    }

    /**
     * Write selected date to TextView
     */
    private void onDatePickerClick(boolean isDateFrom, int year, int monthOfYear, int dayOfMonth){
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(isDateFrom) {
            mDateFrom = calendar.getTime();
            mDateFromTextView.setText(dateFormat.format(mDateFrom));
        }
        else{
            mDateTo = calendar.getTime();
            mDateToTextView.setText(dateFormat.format(mDateTo));
        }
    }
}
