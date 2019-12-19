package com.perfecto.assignment.songs;

import java.util.ArrayList;
import java.util.Random;


/**
 * Generator for fake songs
 */
public class SongsStub {

    static Random rand = new Random();

    public static ArrayList<Song> getRandomSongs(String songName) {
        int numSongs = rand.nextInt(6);
        int randomInt = rand.nextInt(1000);
        ArrayList<Song> response = new ArrayList<>();
        for (int i = 0 ; i < numSongs; i++ ) {
            Song song = new Song(1019 + randomInt,
                            "Artist_" + randomInt + i,
                            "SongLike_" + songName + i + randomInt);
            response.add(song);
        }
        return response;
    }

}
