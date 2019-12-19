package com.perfecto.assignment.songs;

import com.perfecto.assignment.entitlement.EntitlementService;
import com.perfecto.assignment.exceptions.InsufficientSearchesLeftException;
import java.util.ArrayList;

/**
 * Service that finds songs which are similar to the given song (song with the same accords/rhythm etc..)
 */
public class MatchingSongsService {

    public static ArrayList<Song> getMatchingSongs(int userId, String songName) throws InsufficientSearchesLeftException {
        if (EntitlementService.isValidForSearchingMatchingSongs(userId)) {
            ArrayList<Song> matchingSongs = SongsStub.getRandomSongs(songName);
            EntitlementService.increaseUserSearchCount(userId);
            return matchingSongs;
        } else {
            throw new InsufficientSearchesLeftException();
        }
    }
}
