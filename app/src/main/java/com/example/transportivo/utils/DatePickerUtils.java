package com.example.transportivo.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.TextView;

import java.util.Calendar;

public final class DatePickerUtils {
    private static final String SEPARATOR = "/";

    /**
     * Creates and opens new DatePicker dialog. Also sets chosen date to provided <code>view</code>.
     *
     * @param fragmentActivity the parent context
     * @param view the desired TextView which will hold chosen date
     * @param <F> the type of activity
     * @param <V> any type that is child of <code>TextView</code>
     */
    public static <F extends Context, V extends TextView> void showAndSet(F fragmentActivity, final V view) {
        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                fragmentActivity,
                (dpView, year, month, dayOfMonth) -> view.setText(formatDate(year, month, dayOfMonth)),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private static String formatDate(int year, int month, int day) {
        return ++month + SEPARATOR + day + SEPARATOR + year;
    }

    private DatePickerUtils() {
    }
}
