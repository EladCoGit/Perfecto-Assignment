package com.perfecto.assignment.API.responses;

import com.perfecto.assignment.songs.Song;
import java.util.Collection;

public class GetMatchingSongsResponse {

    public Collection<Song> similar_songs;

    public GetMatchingSongsResponse(Collection<Song> similarSongs) {
        this.similar_songs = similarSongs;
    }
}
