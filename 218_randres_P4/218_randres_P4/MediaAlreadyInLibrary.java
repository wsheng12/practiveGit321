public class MediaAlreadyInLibrary extends Exception {

    private String name;
    private Media media;

    public MediaAlreadyInLibrary(String name, Media media) {
        super("Media already in library");
        this.name = name;
        this.media = media;
    }

    public final String getName() {
        return this.name;
    }

    public final Media getMedia() {
        return this.media;
    }
}
