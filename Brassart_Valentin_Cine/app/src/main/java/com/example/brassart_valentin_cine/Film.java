package com.example.brassart_valentin_cine;


import java.util.Date;

public class Film {
    public int id;
    public String title;
    public int note_scenario;
    public int note_music;
    public int note_realisation;
    public String avis;
    public String date;


    public Film(int id, String title, int note_scenario, int note_music, int note_realisation, String avis, String date){
        this.id = id;
        this.title = title;
        this.note_scenario = note_scenario;
        this.note_music = note_music;
        this.note_realisation = note_realisation;
        this.avis = avis;
        this.date = date;
    }

}
