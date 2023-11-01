public abstract class Media implements Comparable<Media>{

   /**
    * attributes:
    *isbn = unique string to identify object
    *genre = genre of media
    format = enum of different types of formats
    */

    private String isbn;
    private String genre;
    private Format format;

    /**
     * Constructor - sets format, isbn, and genre to their respective private variables
     * 
     */
    
    public Media(Format format, String isbn, String genre) {
        this.isbn = isbn;
        this.genre = genre;
        this.format = format;
    }

    /**
     * abstract method that will display class name in subclasses
     * 
     */
    public abstract String getType();

    /**
     * equals method o
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Media)
            if (((Media)obj).getIsbn().equals(this.isbn))
                return true;
        return false;
    }

    public final String getIsbn() {
        return this.isbn;
    }

    public final String getGenre() {
        return this.genre;
    }

    public final Format getFormat() {
        return this.format;
    }

    public String toString() {
        return ("Type: " + getType() + ", ISBN: " + isbn + ", Genre: " + genre + ", Format: " + format);
    }
    
}
