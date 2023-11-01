import java.util.ArrayList;
import java.util.List;

public class Series extends Media{

    private String title;
    private List<String> cast = new ArrayList<String>();
    private List<String> episodes = new ArrayList<String>();

    public Series(Format format, String isbn, String genre, String title, List<String> cast, List<String> episodes) {
        super(format, isbn, genre);
        this.title = title;
        this.cast = cast;
        this.episodes = episodes;
    } 

    public String getType() {
        return "Series";
    }

    public String toString() {
        return "Title: " + title + ", " + super.toString();
    }

    @Override
    public int compareTo(Media o) {
        System.out.println("HUH");
        if (o instanceof Book || o instanceof Movie)
            return 1;
        if (o instanceof Series) {
            if (((Series) o).getTitle().compareTo(this.title) < 0) 
                return 1;
            if (((Series) o).getTitle().compareTo(this.title) == 0) 
                return 0;
        }
            
        return -1;
    }

    public final String getTitle() {
        return this.title;
    }

    public final List<String> getCast() {
        return this.cast;
    }

    public final List<String> getEpisodes() {
        return this.episodes;
    }




}
