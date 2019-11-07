package com.example.brassart_valentin_cine;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FilmManager {
    private static final String TABLE_NAME = "film";
    public static final String KEY_ID_FILM="id_film";
    public static final String KEY_TITLE_FILM="title_film";
    public static final String KEY_NOTE_SCENARIO="note_scenarion_film";
    public static final String KEY_NOTE_MUSIC="note_music_film";
    public static final String KEY_NOTE_REALISATION="note_realisation_film";
    public static final String KEY_AVIS="avis_film";
    public static final String KEY_DATE="date_film";

    public static final String CREATE_TABLE_FILM = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_FILM+" INTEGER primary key," +
            " "+KEY_TITLE_FILM+" TEXT," +
            " "+KEY_NOTE_SCENARIO+" INT," +
            " "+KEY_NOTE_MUSIC+" INT," +
            " "+KEY_NOTE_REALISATION+" INT," +
            " "+KEY_AVIS+" TEXT," +
            " "+KEY_DATE+" TEXT" +
            ");";

    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public FilmManager(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    public long addFilm(Film film) {

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE_FILM, film.title);
        values.put(KEY_NOTE_SCENARIO, film.note_scenario);
        values.put(KEY_NOTE_MUSIC, film.note_music);
        values.put(KEY_NOTE_REALISATION, film.note_realisation);
        values.put(KEY_AVIS, film.avis);
        values.put(KEY_DATE, film.date);

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int supFilm(Film film) {

        String where = KEY_ID_FILM+" = ?";
        String[] whereArgs = {film.id+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Cursor getFilms() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}
