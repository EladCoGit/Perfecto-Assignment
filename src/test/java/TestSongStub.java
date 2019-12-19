import com.perfecto.assignment.songs.Song;
import com.perfecto.assignment.songs.SongsStub;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class TestSongStub {

    @Test
    public void testGetRandomSongs() {
        ArrayList<Song> songs = SongsStub.getRandomSongs("song1"); // 1
        int counter = songs.size();
        counter += SongsStub.getRandomSongs("song1").size(); // 2
        counter += SongsStub.getRandomSongs("song1").size(); // 3
        counter += SongsStub.getRandomSongs("song1").size(); // 4
        counter += SongsStub.getRandomSongs("song1").size(); // 5
        Assert.assertTrue(counter > 0 && counter < 26);
    }
}
