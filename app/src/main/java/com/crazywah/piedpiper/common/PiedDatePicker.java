package com.crazywah.piedpiper.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

public class PiedDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Date date;
    private Calendar calendar;

    public static PiedDatePicker create() {
        return new PiedDatePicker();
    }

    public PiedDatePicker() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        // Create a new instance of TimePickerDialog and return it
        date = new Date(System.currentTimeMillis());
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
//        return new DatePickerDialog(getActivity(), 0, this, date.getYear(), date.getMonth(), date.getDate());
        return new DatePickerDialog(getActivity(), 0, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (date != null) {
            date = new Date();
        }
//        date.setYear(year);
//        date.setMonth(month);
//        date.setDate(dayOfMonth);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();
        EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_AFTER_SELECT_DATE));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}