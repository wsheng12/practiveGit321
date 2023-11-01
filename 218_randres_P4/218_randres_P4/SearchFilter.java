public class SearchFilter implements MediaFilter{

    private String searchTerm;

    public SearchFilter(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean matches(Label<String, Media> label) {

        //equals method work right here??
        //questionable toString working here as well for containing seracht erm :()
        if (label.getKey().contains(searchTerm))
            return true;
        if (label.getValue().toString().contains(searchTerm))
            return true;
        return false;
    }
    
}
