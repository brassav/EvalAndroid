package com.example.brassart_valentin_cine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = MainActivity.this;

                Class destinationActivity = Add_avis.class;
                Intent startChildActivityIntent = new Intent(context, destinationActivity);
                startActivity(startChildActivityIntent);

            }
        });

        afficheList();

    }
    @Override
    protected void onStart() {
        super.onStart();
        afficheList();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void afficheList() {
        try {
            FilmManager m = new FilmManager(this);
            m.open();
            Cursor c = m.getFilms();
            if (c.moveToFirst()) {
                do {
                    Log.d("test",
                            c.getInt(c.getColumnIndex(FilmManager.KEY_ID_FILM)) + "," +
                                    c.getString(c.getColumnIndex(FilmManager.KEY_TITLE_FILM)) + "," +
                                    c.getInt(c.getColumnIndex(FilmManager.KEY_NOTE_SCENARIO)) + "," +
                                    c.getInt(c.getColumnIndex(FilmManager.KEY_NOTE_MUSIC)) + "," +
                                    c.getInt(c.getColumnIndex(FilmManager.KEY_NOTE_REALISATION)) + "," +
                                    c.getString(c.getColumnIndex(FilmManager.KEY_AVIS)) + "," +
                                    c.getString(c.getColumnIndex(FilmManager.KEY_DATE))

                    );
                } while (c.moveToNext());
            }
            c.close();
            m.close();
        } catch (Exception e) {

        }
    }
}
