import java.util.*;

//album comment added
public class Album extends Music {
    /**CHANGED HEREEEEE */
    /**
     * attribute:
     * ArrayList of tracks
     */
    private List<String> trackList = new ArrayList<String>();

    /**
     * constructor that sets the following variables to their private variables
     * @param format - format
     * @param isbn - unique string identifieier
     * @param genre  - genre
     * @param artist - artsit
     * @param title - title
     * @param year - yeaer
     * @param trackList - arraylist mentioned above
     */
    public Album(Format format, String isbn, String genre, String artist, String title, int year, List<String> trackList) {

        super(format, isbn, genre, artist, title, year);
        this.trackList = trackList;

    }

    /**
     * @return name of class
     */
    public String getType() {
        return "Album";
    }

    /**
     * toString, calls upon super's toString as well
     */
    public String toString() {
        return super.toString() + ", Tracks: " + trackList;
    }

    /**
     * getter method for tracklist
     * @return tracklist
     */
    public final List<String> getTrackList() {
        return trackList;
    }





}
