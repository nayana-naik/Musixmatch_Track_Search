package com.example.inclass07;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;

public class Music {

    String trackName, albumName, artistName, updatedTime, trackShareUrl;

    @NonNull
    @Override
    public String toString() {


       return trackName+albumName+artistName+updatedTime+trackShareUrl;
        //return artistName;
    }
}
