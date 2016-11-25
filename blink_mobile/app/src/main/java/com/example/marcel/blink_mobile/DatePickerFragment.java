package com.example.marcel.blink_mobile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by tayna on 02/11/2016.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


        private int year, month, day, hour, minute;
        private EditText txt_data_inicio;
        private Button btn_data_inicio;
        private EditText txt_data_fim;
        private Button btn_data_fim;


    private void finish() {
        finish();
    }

    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        txt_data_inicio = (EditText) getActivity().findViewById(R.id.txt_data_inicio);
        txt_data_inicio.setText("Year: "+view.getYear()+" Month: "+view.getMonth()+" Day: "+view.getDayOfMonth());

        txt_data_fim = (EditText) getActivity().findViewById(R.id.txt_data_fim);
        txt_data_fim.setText("Year: "+view.getYear()+" Month: "+view.getMonth()+" Day: "+view.getDayOfMonth());

    }

}