import java.util.ArrayList;
import java.util.List;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;


public class Movie extends Media{

    private String title;
    private String director;
    private int year;
    private List<String> cast = new ArrayList<String>();

    public Movie(Format format, String isbn, String genre, String title, String director, int year, List<String> cast) {
        super(format, isbn, genre);
        this.title = title;
        this.year = year;
        this.director = director;
        this.cast = cast;
    }

    public String getType() {
        return "Movie";
        
    }

    public String toString() {
        return "Title: " + title + ", Year: " + year + ", " + super.toString();
    }

    @Override
    public int compareTo(Media o) {
        
        System.out.println("HUH");
        if (o instanceof Book)
            return 1;
        if (o instanceof Movie) {
            if (((Movie) o).getTitle().compareTo(this.title) > 0)
                return 1; 
            if (((Movie) o).getTitle().compareTo(this.title) == 0) {
                if (((Movie) o).getYear() > this.year) 
                    return 1;
                if (((Movie) o).getYear() == this.year) 
                    return 0;
            }
        }
        return -1;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getDirector() {
        return this.director;
    }

    public final int getYear() {
        return this.year;
    }

    public final List<String> getCast() {
        return this.cast;
    }



    
}
