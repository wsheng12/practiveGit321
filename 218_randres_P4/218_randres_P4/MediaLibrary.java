import java.util.*;

public class MediaLibrary  {

    /**
     * attributes:
     * library - list of 
     */
    private List<Label<String, Media>> library;

    /**
     * default constructor that instatializes library
     */
    public MediaLibrary() {
        this.library = new ArrayList<Label<String, Media>>();
    }
    
    /**
     * add method for library - if two things are equals it throws an error
     * @param name - name
     * @param media - media
     * @throws MediaAlreadyInLibrary - if two items are equal, throw error
     */
    public void add(String name, Media media) throws MediaAlreadyInLibrary {

        
        Label<String, Media> temp = new Label<String, Media>(name, media);
        for (int i =0; i < library.size(); i++) {
            if (library.get(i).equals(temp)) {
                throw new MediaAlreadyInLibrary(name, media);
            }
        }
        library.add(temp);
    }


    /**
     * filters things by looking for a search keyword, if it has, return true
     * @param mediaFilter - keyword used for searching
     * @return true or false, based off of it contains the keyword or not
     */
    public List<Label<String, Media>> filter(MediaFilter mediaFilter) {

        List<Label<String, Media>> result = new ArrayList<Label<String, Media>>();
        for (int i = 0; i < library.size(); i++) {
            if (mediaFilter.matches(library.get(i))) {
                result.add(library.get(i));
            }
        }

        return result;
    
    }

    /**
     * sort method that's built into collections, uses my overriden compareTo methods
     */
    public void sort() {
        Collections.sort(library);
    }





    
}
