package com.example.brassart_valentin_cine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_avis extends AppCompatActivity  implements View.OnClickListener{
    Button btnDatePicker, btnTimePicker, btnAdd;
    TextView txtDate, txtTime, txtError;
    EditText etTitle, etAvis, etNoteScenario, etNoteMusic, etNoteRealisation;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avis);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(TextView)findViewById(R.id.in_date);
        txtTime=(TextView)findViewById(R.id.in_time);
        btnAdd = (Button)findViewById(R.id.btn_add);
        etTitle = findViewById(R.id.et_title);
        etAvis = findViewById(R.id.et_avis);
        etNoteMusic = findViewById(R.id.et_note_music);
        etNoteScenario = findViewById(R.id.et_note_scenario);
        etNoteRealisation = findViewById(R.id.et_note_realisation);
        txtError = findViewById(R.id.error_msg);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == btnAdd) {
            FilmManager m = new FilmManager(this);
            m.open();
            try {
                String title = etTitle.getText().toString();
                String avis = etAvis.getText().toString();
                int note_musique = Integer.parseInt(etNoteMusic.getText().toString());
                int note_realisation = Integer.parseInt(etNoteRealisation.getText().toString());
                int note_scenario = Integer.parseInt(etNoteScenario.getText().toString());
                String day = txtDate.getText().toString();
                String time = txtTime.getText().toString();
                String date = day + " " + time;
                if (date.length() != 0 && title.length() != 0 && avis.length() != 0 && note_musique >= 0 && note_realisation >= 0 && note_scenario >= 0) {
                    m.addFilm(new Film(id, title, note_scenario, note_musique, note_realisation, avis, date));
                    txtError.setText("");
                    Add_avis.this.finish();
                } else {
                    txtError.setText(R.string.error);
                }
            } catch (Exception e){
                Log.d("Add", e.toString());
                txtError.setText(R.string.error);

            }
            m.close();
        }
    }
}
