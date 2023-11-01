public class Single extends Music{
    

    /**
     * Constructor that sets all the varibles to their corresponding private ones
     * @param format
     * @param isbn
     * @param genre
     * @param artist
     * @param title
     * @param year
     */
    public Single(Format format, String isbn, String genre, String artist, String title, int year) {
        super(format, isbn, genre, artist, title, year);
    }

    /**
     * return class name
     */
    public final String getType() {
        return "Single";
    }
    
    
}
