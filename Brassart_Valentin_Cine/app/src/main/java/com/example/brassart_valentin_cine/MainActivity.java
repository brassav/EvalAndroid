package com.example.brassart_valentin_cine;

import android.app.AlertDialog;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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

        FloatingActionButton mail = findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilmManager m = new FilmManager(MainActivity.this);
                m.open();
                Cursor c = m.getFilms();
                String text = "";
                if (c.moveToFirst()) {
                    do {
                        HashMap<String,String> hashMap=new HashMap<>();

                        text = text + getResources().getString(R.string.note_scenario) + ": " + c.getString(c.getColumnIndex(FilmManager.KEY_TITLE_FILM)) + "\n" +
                        getResources().getString(R.string.note_scenario) + ": " + c.getString(c.getColumnIndex(FilmManager.KEY_NOTE_SCENARIO)) + "\n" +
                        getResources().getString(R.string.note_musique) + ": " +  c.getString(c.getColumnIndex(FilmManager.KEY_NOTE_MUSIC)) + "\n" +
                        getResources().getString(R.string.note_realisation) + ": " + c.getString(c.getColumnIndex(FilmManager.KEY_NOTE_REALISATION)) + "\n" +
                        getResources().getString(R.string.avis) + ": " +  c.getString(c.getColumnIndex(FilmManager.KEY_AVIS)) + "\n" +
                        getResources().getString(R.string.date) + ": " +  c.getString(c.getColumnIndex(FilmManager.KEY_DATE)) + "\n\n";
                    } while (c.moveToNext());
                }
                c.close();
                m.close();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Liste des critique de films");
                i.putExtra(Intent.EXTRA_TEXT   , text);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
            ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
            String[] from={"title","note_scenario", "note_music", "note_realisation", "avis", "date"};//string array
            int[] to={R.id.tv_title,R.id.tv_note_scenario, R.id.tv_note_music, R.id.tv_note_realisation, R.id.tv_avis, R.id.tv_date};//int array of views id's


            if (c.moveToFirst()) {
                do {
                    HashMap<String,String> hashMap=new HashMap<>();

                    hashMap.put("title", c.getString(c.getColumnIndex(FilmManager.KEY_TITLE_FILM)));
                    hashMap.put("note_scenario", getResources().getString(R.string.note_scenario) + ": " + c.getString(c.getColumnIndex(FilmManager.KEY_NOTE_SCENARIO)));
                    hashMap.put("note_music",getResources().getString(R.string.note_musique) + ": " +  c.getString(c.getColumnIndex(FilmManager.KEY_NOTE_MUSIC)));
                    hashMap.put("note_realisation", getResources().getString(R.string.note_realisation) + ": " + c.getString(c.getColumnIndex(FilmManager.KEY_NOTE_REALISATION)));
                    hashMap.put("avis", getResources().getString(R.string.avis) + ": " +  c.getString(c.getColumnIndex(FilmManager.KEY_AVIS)));
                    hashMap.put("date", getResources().getString(R.string.date) + ": " +  c.getString(c.getColumnIndex(FilmManager.KEY_DATE)));
                    arrayList.add(hashMap);
                } while (c.moveToNext());
            }
            c.close();
            SimpleAdapter simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.list,from,to);//Create object and set the parameters for simpleAdapter
            ListView simpleListView=(ListView)findViewById(R.id.simpleListView);
            simpleListView.setAdapter(simpleAdapter);
            m.close();
        } catch (Exception e) {

        }
    }
}
