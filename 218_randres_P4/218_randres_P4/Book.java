import java.util.ArrayList;
import java.util.List;

public class Book extends Media{

    /**
     * attributes:
     * title, author, and publisher of book
     */
    private String title;
    private String author;
    private String publisher;

    /**construcotr
     * sets the following parameters to their private variables
     * @param format 
     * @param isbn
     * @param genre
     * @param author
     * @param title
     * @param publisher
     */
    public Book(Format format, String isbn, String genre, String author, String title, String publisher) {
        super(format, isbn, genre);
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    } 

    /**
     * return's name of class
     */
    public String getType() {
        return "Book";
    }
    

    /**
     * tostring method that displays variables
     */
    public String toString() {
        return "Title: " + title + ", Author: " + author+ ", "+ super.toString();
    }

    /**
     * overriden compareto method, determines according to the herearchy described in the directions
     */
    @Override
    public int compareTo(Media o) {
        if (o instanceof Book) {
            if (((Book) o).getAuthor().compareTo(this.author) < 0) 
                return 1;
            if (((Book) o).getAuthor().compareTo(this.author) == 0) {
                if (((Book) o).getTitle().compareTo(this.title) < 0) 
                    return 1;
                if (((Book) o).getTitle().compareTo(this.title) == 0) 
                    return 0;
                
            }
            
        }
            
        return -1;
        
    }

    /**
     * getter method for title
     * @return title
     */
    public final String getTitle() {
        return this.title;
    }

    /**
     * getter method for author
     * @return author
     */
    public final String getAuthor() {
        return this.author;
    }

    /**
     * getter for publisher
     * @return publisher
     */
    public final String getPublisher() {
        return this.publisher;
    }




}
