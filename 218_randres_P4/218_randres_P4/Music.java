
public abstract class Music extends Media{

    private String artist;
    private String title;
    private int year;

    public Music(Format format, String isbn, String genre, String artist, String title, int year) {
        
        super(format, isbn, genre);
        this.artist = artist;
        this.title = title;
        this.year = year;

    }

    public String toString() {
        return "Artist: " + artist + ", Year: " + year + ", Title: " + title + ", " + super.toString();
    }

    public String getType() {
        return "Music";
    }

    @Override
    public int compareTo(Media o) {
        if (!(o instanceof Music)) {
            return 1;
        }
        if (o instanceof Music) {

            if (((Music) o).getArtist().compareTo(this.artist) == 0) {

                if (((Music) o).getYear() > this.year) 
                    return 1;

                if (((Music) o).getYear() == this.year) {

                    if (((Music) o).getTitle().compareTo(this.title) < 0) 
                        return 1;

                    if (((Music) o).getTitle().compareTo(this.title) == 0) 
                        return 0;
                
                }
            }
            if (((Music) o).getArtist().compareTo(this.artist) < 0)  {
               return 1;
            }
            
        }
        return -1;

    }

    public final String getArtist() {
        return artist;
    }

    public final String getTitle() {
        return this.title;
    }

    public final int getYear() {
        return this.year;
    }

}
