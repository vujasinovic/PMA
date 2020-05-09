package com.example.transportivo.ui;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;

import java.util.Calendar;

import static com.example.transportivo.utils.DateTimeUtils.TIME_SEPARATOR;

public final class TransportivoTimePicker {
    /**
     * Creates and opens new TimePicker dialog. Also sets chosen time to provided <code>view</code>.
     *
     * @param fragmentActivity the parent context
     * @param view             the desired TextView which will hold chosen date
     * @param is24HourView
     * @param <F>              the type of activity
     * @param <V>              any type that is child of <code>TextView</code>
     */
    public static <F extends Context, V extends TextView> void showAndSet(F fragmentActivity,
                                                                          final V view,
                                                                          boolean is24HourView) {
        final Calendar calendar = Calendar.getInstance();

        final TimePickerDialog timePickerDialog = new TimePickerDialog(
                fragmentActivity,
                (tpView, hourOfDay, minute) -> view.setText(format(hourOfDay, minute)),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24HourView);

        timePickerDialog.show();
    }

    private static String format(int hourOfDay, int minute) {
        return hourOfDay + TIME_SEPARATOR + minute;
    }

    private TransportivoTimePicker() {

    }
}
