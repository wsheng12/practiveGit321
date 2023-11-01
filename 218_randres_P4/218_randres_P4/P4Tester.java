// P4Tester.java
/** Example of using unit tests for this assignment.  To run them on the command line, make
 * sure that the junit-cs211.jar is in the same directory.
 *
 * On Mac/Linux:
 *  javac -cp .:junit-cs211.jar *.java         # compile everything
 *  java -cp .:junit-cs211.jar P4Tester        # run tests
 *
 * On windows replace colons with semicolons: (: with ;)
 *  demo$ javac -cp .;junit-cs211.jar *.java   # compile everything
 *  demo$ java -cp .;junit-cs211.jar P4Tester  # run tests
 */
import org.junit.*;
import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.*;

public class P4Tester{
	public static void main(String[] args){
		org.junit.runner.JUnitCore.main("P4Tester");
	}

    ///////////////
    // Utilities //
    ///////////////
    public static void isClassAbstract(String name) {
        Class<?> c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue(name + " class not found", c != null);
        assertTrue("class " + name + " should be abstract", Modifier.isAbstract(c.getModifiers()));
    }
    // check whether an abstract class is comparable
    public static void isAbstractClassComparable(String name) {
        Class<?> c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue(name + " class not found", c != null);
        boolean foundComparableIface = false;
        boolean parameterizedCorrectly = false;
        for (Type t : c.getGenericInterfaces()) {
            if (t instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) t;
                if (pt.getRawType().equals(Comparable.class)) {
                    foundComparableIface = true;
                    for (Type st : pt.getActualTypeArguments()) {
                        if (st.equals(c)) {
                            parameterizedCorrectly = true;
                            break;
                        }
                    }
                }
            }
        }
        assertTrue(name + " class does not implement Comparable", foundComparableIface);
        assertTrue(name + " class does not specify generic type for Comparable interface correctly", parameterizedCorrectly);
    }
    // check whether a subclass implements compareTo method
    public static void doesSubClassImplementCompareTo(String name, Class<?> parameter_class) {
        Class<?> c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue(name + " class not found", c != null);
        Method m = null;
        try {
            Class[] cArg = new Class[1];
            cArg[0] = parameter_class;
            m = c.getMethod("compareTo", cArg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertTrue("compareTo method not found", m != null);
    }

    public static void checkField(String cName, String fieldName, String fieldTypeName, String getterName) {
        Class<?> fieldType = null;
        try {
            fieldType = Class.forName(fieldTypeName);
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue("Class " + fieldTypeName + " not found", fieldType != null);
        checkField(cName, fieldName, fieldType, getterName);
    }
    public static void checkField(String cName, String fieldName, Class<?> fieldType, String getterName) {
        Class<?> c = null;
        try {
            c = Class.forName(cName);
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue("Class " + cName + " not found", c != null);
        checkField(c, fieldName, fieldType, getterName);
    }
    public static void checkField(Class<?> c, String fieldName, Class<?> fieldType, String getterName) {
        Field f = null;
        try {
            f = c.getDeclaredField(fieldName);
        } catch (NoSuchFieldException nsfe) {
        }
        assertTrue("field " + fieldName + " does not exist in class " + c, f != null);
        assertTrue("field " + fieldName + " is not private in class " + c, Modifier.isPrivate(f.getModifiers()));
        assertTrue("field " + fieldName + " does not have the correct type (expected: " + fieldType + ", found:" + f.getType() + ") in class " + c, fieldType.equals(f.getType()));
        if (getterName != null) {
            Method m = null;
            try {
                m = c.getDeclaredMethod(getterName);
            } catch (NoSuchMethodException nsme) {
            }
            assertTrue("No getter with name \"" + getterName + "\" in class " + c, m != null);
            assertTrue("getter \"" + getterName + "\" in class " + c + " is not public", Modifier.isPublic(m.getModifiers()));
        }
    }
    // check whether child's parent class name is equal to parent
    public void checkParentClass(String child, String parent) {
        Class<?> c = null;
        try {
            c = Class.forName(child);
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue("Class " + child + " not found", c != null);

        assertTrue(child + "'s parent class is not " + parent, c.getSuperclass().getName() == parent);
    }
    // check the implement of GetType method
    public void checkGetType(String name, boolean isAbstract) {
        Method m = null;
        try {
            m = Class.forName(name).getDeclaredMethod("getType");
        } catch (NoSuchMethodException nsme) {
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue("getType() not declared in class " + name, m != null);
        if (isAbstract && m != null) {
            assertTrue("getType() is not abstract in class " + name, Modifier.isAbstract(m.getModifiers()));
        }
    }
    // check the implement of toString method
    public void checktoString(String name) {
        Method m = null;
        try {
            m = Class.forName(name).getDeclaredMethod("toString");
        } catch (NoSuchMethodException nsme) {
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue("toString() not declared in class " + name, m != null);
    }
    // check constructor
    public static void checkConstructor(String name, Class<?>... parameterTypes) {
        Constructor<?> c = null;
        try {
            c = Class.forName(name).getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException nsme) {
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue(name + " constructor with correct parameters not found", c != null);
    }
    // check whether field has a final modifier
    public static void checkIsFieldFinal(String name, String field) {
        Class<?> c = null;
        Field f = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException e) {

        }
        assertTrue(name + " class not found", c != null);
        try {
            f = c.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
        }
        assertTrue("private field " + field + " not found", f != null);

        assertTrue("Field " + field + " should be final", Modifier.isFinal(f.getModifiers()));
    }
    // check whether exists setter methods, not only for setField, but for all methods startswith `set`
    public static void checkSetter(String name) {
        Class<?> c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException e) {

        }
        assertTrue(name + " class not found", c != null);

        boolean flag = true;
        Method[] fields = c.getMethods();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().startsWith("set")) {
                flag = false;
                break;
            }
        }
        assertTrue(name + " shouldn't have setter methods", flag);
    }
    public static void checkFieldIgnoreType(String cName, String fieldName, String getterName) {
        Class<?> c = null;
        try{
            c = Class.forName(cName);
        } catch(ClassNotFoundException cnfe){
        }
        assertTrue("CLass "+cName+" not found", c!=null);
        Field f = null;
        try{
            f = c.getDeclaredField(fieldName);
        } catch(NoSuchFieldException nsfe){
        }
        assertTrue("field "+ fieldName + " does not exist in class "+ c, f != null);
        assertTrue("field "+ fieldName + " is not private in class "+ c, Modifier.isPrivate(f.getModifiers()));
        if(getterName != null){
            Method m = null;
            try{
                m = c.getDeclaredMethod(getterName);
            } catch(NoSuchMethodException nsme){
            }
            assertTrue("No getter with name \""+ getterName +"\" in class "+c, m!=null);
            assertTrue("getter \""+ getterName + "\" in class "+ c+" is not public", Modifier.isPublic(m.getModifiers()));
        }
    }

    public void checkLabeledMediaLists(List<Label<String, Media>> expectedList, List<Label<String, Media>> givenList) {
        System.out.println();
        System.out.println();

        System.out.println();

        System.out.println();
        System.out.println("GIVEN ONE");

        for (int i = 0; i < givenList.size(); i++) {
            System.out.println(givenList.get(i).getValue().getType());
        }
        System.out.println();
        System.out.println();

        System.out.println();

        System.out.println();

        System.out.println("EXPECTED ONE:");

        for (int i = 0; i < expectedList.size(); i++) {
            System.out.println(expectedList.get(i).getValue().getType());
        }
        assertEquals("returned list unexpected size", expectedList.size(), givenList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals("Unexpected content in returned list at position " + i, expectedList.get(i).getValue().getType(), givenList.get(i).getValue().getType());
            
            
        }
       
    }

	///////////////
	// Album Tests
	///////////////
    @Test(timeout = 1000)
    public void test_albumFields() {
        checkFieldIgnoreType("Album", "trackList", "getTrackList");
    }
    @Test(timeout = 1000)
    public void test_albumParentClass() {
        checkParentClass("Album", "Music");
    }
    @Test(timeout = 1000)
    public void test_albumGetType() {
        checkGetType("Album", false);
    }
    @Test(timeout = 1000)
    public void test_albumtoString() {
        checktoString("Album");
    }
    @Test(timeout = 1000)
    public void test_albumConstructor() {
        String name = "Album";
        boolean found_flag = true;
        try {
            checkConstructor(name, Class.forName("Format"), String.class, String.class, String.class, String.class, int.class, List.class);
        } catch (ClassNotFoundException cnfe) {
            found_flag = false;
        }
        assertTrue(name + " class not found", found_flag);
    }
    @Test(timeout = 1000)
    public void test_albumSetter() {
        checkSetter("Album");
    }
    @Test(timeout = 1000)
    public void test_album_1() {
        ArrayList<String> tl = new ArrayList<String>();
        tl.add("Track 1");
        tl.add("Track 2");
        tl.add("Track 3");
        Album a = new Album(Format.VINYL, "123-4-56-789012-3", "Unknown", "Unknown", "Untitled", 2021, tl);
        assertTrue("Album should be an instanceof Music", a instanceof Music);
        assertEquals("getType() in Album incorrect", "Album", a.getType());
        String strRep = "Artist: Unknown, Year: 2021, Title: Untitled, Type: Album, ISBN: 123-4-56-789012-3, Genre: Unknown, Format: " + Format.VINYL;
        strRep = strRep + ", Tracks: " + tl;
        assertEquals("Incorrect toString() for Album", strRep, a.toString());
    }

    @Test(timeout = 1000)
    public void test_album_2() {
        ArrayList<String> tl = new ArrayList<String>();
        tl.add("1st Track");
        tl.add("2nd Track");
        Album a = new Album(Format.CD, "111-1-11-111-1", "Sci", "Alan Walker", "Coolest", 2000, tl);
        assertTrue("Album should be an instanceof Music", a instanceof Music);
        assertEquals("getType() in Album incorrect", "Album", a.getType());
        String strRep = "Artist: Alan Walker, Year: 2000, Title: Coolest, Type: Album, ISBN: 111-1-11-111-1, Genre: Sci, Format: " + Format.CD;
        strRep = strRep + ", Tracks: " + tl;
        assertEquals("Incorrect toString() for Album", strRep, a.toString());
    }

	///////////
	// Book Tests //
	///////////
    @Test(timeout = 1000)
    public void test_bookImplementCompareTo() {
        doesSubClassImplementCompareTo("Book", Media.class);
    }
    @Test(timeout = 1000)
    public void test_bookFields_title() {
        checkField("Book", "title", "java.lang.String", "getTitle");
        // checkField("Book", "author", "java.lang.String", "getAuthor");
        // checkField("Book", "publisher", "java.lang.String", "getPublisher");
    }
    @Test(timeout = 1000)
    public void test_bookFields_author() {
        // checkField("Book", "title", "java.lang.String", "getTitle");
        checkField("Book", "author", "java.lang.String", "getAuthor");
        // checkField("Book", "publisher", "java.lang.String", "getPublisher");
    }
    @Test(timeout = 1000)
    public void test_bookFields_publisher() {
        // checkField("Book", "title", "java.lang.String", "getTitle");
        // checkField("Book", "author", "java.lang.String", "getAuthor");
        checkField("Book", "publisher", "java.lang.String", "getPublisher");
    }
    @Test(timeout = 1000)
    public void test_bookParentClass() {
        checkParentClass("Book", "Media");
    }
    @Test(timeout = 1000)
    public void test_bookGetType() {
        checkGetType("Book", false);
    }
    @Test(timeout = 1000)
    public void test_booktoString() {
        checktoString("Book");
    }
    @Test(timeout = 1000)
    public void test_bookConstructor() {
        String name = "Book";
        boolean found_flag = true;
        try {
            checkConstructor(name, Class.forName("Format"), String.class, String.class, String.class, String.class, String.class);
        } catch (ClassNotFoundException cnfe) {
            found_flag = false;
        }
        assertTrue(name + " class not found", found_flag);
    }
    @Test(timeout = 1000)
    public void test_bookSetter() {
        checkSetter("Book");
    }
    @Test(timeout = 1000)
    public void test_book_1() {
        Format f = Format.HARDBACK;
        String isbn = "456-7-89-012345-6";
        String genre = "Horror";
        String author = "John Smith";
        String title = "Not titled";
        String publisher = "Penguin Books";
        Book b = new Book(f, isbn, genre, author, title, publisher);
        assertTrue("Book should be an instanceof Media", b instanceof Media);
        assertEquals("getType() in Book incorrect", "Book", b.getType());
        String strRep = "Title: " + title + ", Author: " + author + ", Type: Book, ISBN: " + isbn + ", Genre: " + genre + ", Format: " + f;
        assertEquals("Incorrect toString() for Book", strRep, b.toString());
    }

    @Test(timeout = 1000)
    public void test_book_2() {
        Format f = Format.PAPERBACK;
        String isbn = "000-1-2222-3-9999999";
        String genre = "Writing";
        String author = "Nobody";
        String title = "Python Programming";
        String publisher = "Small Company";
        Book b = new Book(f, isbn, genre, author, title, publisher);
        assertTrue("Book should be an instanceof Media", b instanceof Media);
        assertEquals("getType() in Book incorrect", "Book", b.getType());
        String strRep = "Title: " + title + ", Author: " + author + ", Type: Book, ISBN: " + isbn + ", Genre: " + genre + ", Format: " + f;
        assertEquals("Incorrect toString() for Book", strRep, b.toString());
    }

    public static void checkFormatField(String field) {
        Class<?> c = null;
        try {
            c = Class.forName("Format");
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue("Class Format not found", c != null);

        Field f = null;
        try {
            f = c.getField(field);
        } catch (NoSuchFieldException e) {
        }
        assertTrue("Field " + field + " not found", f != null);
    }

    @Test(timeout = 1000)
    public void test_formatFieldAUDIOBOOK() {
        checkFormatField("AUDIOBOOK");
    }

    @Test(timeout = 1000)
    public void test_formatFieldBLURAY() {
        checkFormatField("BLURAY");
    }

    @Test(timeout = 1000)
    public void test_formatFieldCD() {
        checkFormatField("CD");
    }

    @Test(timeout = 1000)
    public void test_formatFieldDOWNLOAD() {
        checkFormatField("DOWNLOAD");
    }

    @Test(timeout = 1000)
    public void test_formatFieldDVD() {
        checkFormatField("DVD");
    }

    @Test(timeout = 1000)
    public void test_formatFieldHARDBACK() {
        checkFormatField("HARDBACK");
    }

    @Test(timeout = 1000)
    public void test_formatFieldLASERDISC() {
        checkFormatField("LASERDISC");
    }

    @Test(timeout = 1000)
    public void test_formatFieldPAPERBACK() {
        checkFormatField("PAPERBACK");
    }

    @Test(timeout = 1000)
    public void test_formatFieldTAPE() {
        checkFormatField("TAPE");
    }

    @Test(timeout = 1000)
    public void test_formatFieldVHS() {
        checkFormatField("VHS");
    }

    @Test(timeout = 1000)
    public void test_formatFieldVINYL() {
        checkFormatField("VINYL");
    }

	/////////////////
	// Label Tests //
	/////////////////
    @Test(timeout = 1000)
    public void test_labelImplementCompareTo() {
        doesSubClassImplementCompareTo("Label", Label.class);
    }
    @Test(timeout = 1000)
    public void test_lableSetter() {
        checkSetter("Label");
    }
    @Test(timeout = 1000)
    public void test_label() {
        Class<?> c = null;
        try {
            c = Class.forName("Label");
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue("No class named Label found", c != null);
        boolean foundComparableIface = false;
        boolean comparableToLabel = false;
        for (Type t : c.getGenericInterfaces()) {
            if (t instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) t;
                if (pt.getRawType().equals(Comparable.class)) {
                    foundComparableIface = true;
                    for (Type st : pt.getActualTypeArguments()) {
                        if (st instanceof ParameterizedType) {
                            if (((ParameterizedType) st).getRawType().equals(c)) {
                                comparableToLabel = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        assertTrue("Label class does not implement Comparable", foundComparableIface);
        assertTrue("Label class does not specify generic type for Comparable correctly", comparableToLabel);
        boolean numGenericTypesCorrect = false;
        boolean firstTypeUpperBoundCorrect = false;
        boolean secondTypeUpperBoundIsComparable = false;
        boolean secondTypeUpperBoundComparableGeneric = false;
        TypeVariable<?>[] genericTypes = c.getTypeParameters();
        if (genericTypes.length == 2) {
            numGenericTypesCorrect = true;
            if (genericTypes[0].getBounds().length > 0 && genericTypes[0].getBounds()[0].equals(Object.class)) {
                firstTypeUpperBoundCorrect = true;
            }
            if (genericTypes[1].getBounds().length > 0) {
                if (genericTypes[1].getBounds()[0] instanceof ParameterizedType) {
                    ParameterizedType pt2 = (ParameterizedType) genericTypes[1].getBounds()[0];
                    if (pt2.getRawType().equals(Comparable.class)) {
                        secondTypeUpperBoundIsComparable = true;
                        Type[] typeArgs = pt2.getActualTypeArguments();
                        // System.out.println("+: "+pt2);
                        for (Type t : typeArgs) {
                            if (t.equals(genericTypes[1])) {
                                secondTypeUpperBoundComparableGeneric = true;
                                break;
                            }
                            // System.out.println("-: "+t+"=="+genericTypes[1]+"? "+t.equals(genericTypes[1]));
                        }
                    }
                }
            }
        }
        assertTrue("Label does not specify the correct number (2) of generic types", numGenericTypesCorrect);
        assertTrue("Label's first generic type should have no upper bound (besides Object)", firstTypeUpperBoundCorrect);
        assertTrue("Label's second generic type should have an upper bound of Comparable with correct parameter", secondTypeUpperBoundIsComparable);
        assertTrue("Label's second generic type does not correctly specify Comparable's parameter", secondTypeUpperBoundComparableGeneric);
        Format f = Format.HARDBACK;
        String isbn = "456-7-89-012345-6";
        String genre = "Horror";
        String author = "John Smith";
        String title = "Not titled";
        String publisher = "Penguin Books";
        Book b = new Book(f, isbn, genre, author, title, publisher);
        Label<String, Media> aLabeledMedia = new Label<String, Media>("My favorite book", b);
        Label<String, Media> anotherLabeledMedia = new Label<String, Media>("My second favorite book", b);
        assertEquals("Incorrect equals() for Label", aLabeledMedia, anotherLabeledMedia);
    }

	/////////////////////////////////
	// MediaAlreadyInLibrary Tests //
	/////////////////////////////////
    @Test(timeout = 1000)
    public void test_mediaAlreadyInLibraryFields_name() {
        checkField("MediaAlreadyInLibrary", "name", "java.lang.String", "getName");
        // checkField("MediaAlreadyInLibrary", "media", "Media", "getMedia");
    }
    @Test(timeout = 1000)
    public void test_mediaAlreadyInLibraryFields_media() {
        checkField("MediaAlreadyInLibrary", "name", "java.lang.String", "getName");
        // checkField("MediaAlreadyInLibrary", "media", "Media", "getMedia");
    }
    @Test(timeout = 1000)
    public void test_mediaAlreadyInLibraryParentClass() {
        checkParentClass("MediaAlreadyInLibrary", "java.lang.Exception");
    }
    @Test(timeout = 1000)
    public void test_mediaAlreadyInLibraryConstructor() {
        String name = "MediaAlreadyInLibrary";
        boolean found_flag = true;
        try {
            checkConstructor(name, String.class, Class.forName("Media"));
        } catch (ClassNotFoundException cnfe) {
            found_flag = false;
        }
        assertTrue(name + " class not found", found_flag);
    }

	///////////
	// MediaFilter Tests //
	///////////
    @Test(timeout = 1000)
    public void test_mediaFilter() {
        Class<?> c = null;
        try {
            c = Class.forName("MediaFilter");
        } catch (ClassNotFoundException e) {
        }
        assertTrue("MediaFilter class not found", c != null);

        assertTrue("MediaFileter should be an interface", Modifier.isInterface(c.getModifiers()));
    }
    @Test(timeout = 1000)
    public void test_mediafilter_1() {
        Class<?> c = null;
        try {
            c = Class.forName("MediaFilter");
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue("MediaFilter interface not found", c != null);
        assertTrue("MediaFilter is not an interface", c.isInterface());
        Method m = null;
        Format f = Format.HARDBACK;
        String isbn = "456-7-89-012345-6";
        String genre = "Horror";
        String author = "John Smith";
        String title = "Not titled";
        String publisher = "Penguin Books";
        Book b = new Book(f, isbn, genre, author, title, publisher);
        try {

            Class<?> c2 = new Label<String, Media>("My favorite book", b).getClass();
            m = c.getMethod("matches", c2);
        } catch (NoSuchMethodException nsme) {
        }
        assertTrue("MediaFilter's matches method does not exist, or has incorrect arguments", m != null);
    }

    @Test(timeout = 1000)
    public void test_mediafilter_2() {
        Class<?> c = null;
        try {
            c = Class.forName("MediaFilter");
        } catch (ClassNotFoundException cnfe) {
        }
        assertTrue("MediaFilter interface not found", c != null);
        assertTrue("MediaFilter is not an interface", c.isInterface());
        Method m = null;
        Format f = Format.HARDBACK;
        ArrayList<String> cast = new ArrayList<String>();
        cast.add("One");
        cast.add("Two");
        cast.add("Three");
        Movie movie = new Movie(Format.DVD, "isbn", "genre", "007", "unknow", 2021, cast);
        try {

            Class<?> c2 = new Label<String, Media>("My favorite movie", movie).getClass();
            m = c.getMethod("matches", c2);
        } catch (NoSuchMethodException nsme) {
        }
        assertTrue("MediaFilter's matches method does not exist, or has incorrect arguments", m != null);
    }

	/////////////////
	// Media Tests //
	/////////////////
    @Test(timeout = 1000)
    public void test_mediaIsAbstract() {
        isClassAbstract("Media");
    }

    @Test(timeout = 1000)
    public void test_mediaComparable() {
        isAbstractClassComparable("Media");
    }

    @Test(timeout = 1000)
    public void test_mediaFields() {
        checkField("Media", "isbn", "java.lang.String", null);
        checkField("Media", "genre", "java.lang.String", "getGenre");
        checkField("Media", "format", "Format", "getFormat");
    }
    @Test(timeout = 1000)
    public void test_mediaGetType() {
        checkGetType("Media", true);
    }
    @Test(timeout = 1000)
    public void test_mediatoString() {
        checktoString("Media");
    }
    @Test(timeout = 1000)
    public void test_mediaConstructor() {
        String name = "Media";
        boolean found_flag = true;
        try {
            checkConstructor(name, Class.forName("Format"), String.class, String.class);
        } catch (ClassNotFoundException cnfe) {
            found_flag = false;
        }
        assertTrue(name + " class not found", found_flag);
    }
    @Test(timeout = 1000)
    public void test_mediaSetter() {
        checkSetter("Media");
    }

	////////////////////////
	// MediaLibrary Tests //
	////////////////////////
    @Test(timeout = 1000)
    public void test_mediaLibraryParentClass() {
        checkParentClass("MediaLibrary", "java.lang.Object");
    }
    @Test(timeout = 1000)
    public void test_mediaLibraryConstructor() {
        checkConstructor("MediaLibrary");
    }
    @Test(timeout = 1000)
    public void test_medialibrary_1() {
        MediaLibrary ml = new MediaLibrary();
        //a series
        Format f = Format.BLURAY;
        String isbn = "345-6-78-901234-5";
        String genre = "sitcom";
        String title = "That One Show";
        List<String> cast = new ArrayList<String>();
        cast.add("Actor 4");
        cast.add("Actor 5");
        cast.add("Actor 6");
        List<String> eps = new ArrayList<String>();
        eps.add("Episode 1");
        eps.add("Episode 2");
        eps.add("Episode 3");
        Series series = new Series(f, isbn, genre, title, cast, eps);
        //a single
        Single single = new Single(Format.CD, "098-7-65-432098-7", "Not known", "Anonymous", "Self-titled", 2005);
        //a movie
        f = Format.DVD;
        isbn = "234-5-67-890123-4";
        genre = "avant-garde";
        title = "inconnu";
        String dir = "Anonyme";
        int y = 1999;
        cast = new ArrayList<String>();
        cast.add("Actor 1");
        cast.add("Actor 2");
        cast.add("Actor 3");
        Movie mov = new Movie(f, isbn, genre, title, dir, y, cast);
        //an album
        ArrayList<String> tl = new ArrayList<String>();
        tl.add("Track 1");
        tl.add("Track 2");
        tl.add("Track 3");
        Album alb = new Album(Format.VINYL, "123-4-56-789012-3", "Unknown", "Unknown", "Untitled", 2021, tl);
        //a book
        f = Format.HARDBACK;
        isbn = "456-7-89-012345-6";
        genre = "Horror";
        String author = "John Smith";
        title = "Not titled";
        String publisher = "Penguin Books";
        Book book = new Book(f, isbn, genre, author, title, publisher);
        try {
            ml.add("My favorite series", series);
            ml.add("My favorite single", single);
            ml.add("My favorite movie", mov);
            ml.add("My favorite album", alb);
            ml.add("My favorite book", book);
        } catch (MediaAlreadyInLibrary mail) {
            assertTrue("Threw MediaAlreadyInLibrary unexpectedly", false);
        }
        SearchFilter sf = new SearchFilter("Horror");
        List<Label<String, Media>> filteredList = ml.filter(sf);
        List<Label<String, Media>> expectedList = new ArrayList<Label<String, Media>>();
        expectedList.add(new Label<String, Media>("My favorite book", book));
        checkLabeledMediaLists(expectedList, filteredList);
        List<Label<String, Media>> expectedSortedList = new ArrayList<Label<String, Media>>();
        expectedSortedList.add(new Label<String, Media>("My favorite book", book));
        expectedSortedList.add(new Label<String, Media>("My favorite movie", mov));
        expectedSortedList.add(new Label<String, Media>("My favorite series", series));
        expectedSortedList.add(new Label<String, Media>("My favorite single", single));
        expectedSortedList.add(new Label<String, Media>("My favorite album", alb));
        ml.sort();
        sf = new SearchFilter("ISBN"); //isbn is in every toString, should match all
        List<Label<String, Media>> listAfterSorting = ml.filter(sf);
        checkLabeledMediaLists(expectedSortedList, listAfterSorting);
        boolean exceptionThrownOnDuplicateAdd = false;
        Book duplicateISBN = new Book(Format.LASERDISC, "123-4-56-789012-3", "Some genre", "Somebody", "Something", "Some publisher");
        //all fields are different, EXCEPT for ISBN, which matches the album
        //should generate an exception when adding
        try {
            ml.add("A duplicate item", duplicateISBN);
        } catch (MediaAlreadyInLibrary mail) {
            exceptionThrownOnDuplicateAdd = true;
        }
        assertTrue("No MediaAlreadyInLibrary exception thrown when adding an item with an existing ISBN", exceptionThrownOnDuplicateAdd);
    }

    @Test(timeout = 1000, expected = Test.None.class)
    public void test_medialibrary_2() throws MediaAlreadyInLibrary {
        ArrayList<String> cast = new ArrayList<String>();
        cast.add("One");
        cast.add("Two");
        cast.add("Three");
        Movie movie = new Movie(Format.DVD, "isbn1", "genre", "007", "unknow", 2021, cast);
        Book book = new Book(Format.AUDIOBOOK, "isbn2", "genre", "luffy", "Hello Java", "Mason");
        Music music1 = new Single(Format.AUDIOBOOK, "isbn3", "genre", "Unknow", "Star sky", 2020);
        Music music2 = new Single(Format.AUDIOBOOK, "isbn4", "genre", "Unknow", "Star sky", 2021);
        ArrayList<String> episodes = new ArrayList<String>();
        episodes.add("E1");
        episodes.add("E2");
        episodes.add("E3");
        Series series = new Series(Format.DVD, "isbn5", "genre", "Loki", cast, episodes);

        MediaLibrary ml = new MediaLibrary();
        ml.add("movie", movie);
        ml.add("book", book);
        ml.add("music1", music1);
        ml.add("music2", music2);
        ml.add("series", series);

        //filter
        List<Label<String, Media>> filtered = ml.filter(new SearchFilter("music"));

        List<Label<String, Media>> expectedFilteredList = new ArrayList<Label<String, Media>>();
        expectedFilteredList.add(new Label<String, Media>("music1", music1));
        expectedFilteredList.add(new Label<String, Media>("music2", music2));

        checkLabeledMediaLists(expectedFilteredList, filtered);
    }

    @Test(timeout = 1000, expected = Test.None.class)
    public void test_medialibrary_3() throws MediaAlreadyInLibrary {
        ArrayList<String> cast = new ArrayList<String>();
        cast.add("One");
        cast.add("Two");
        cast.add("Three");
        Movie movie = new Movie(Format.DVD, "isbn1", "genre", "007", "unknow", 2021, cast);
        Book book = new Book(Format.AUDIOBOOK, "isbn2", "genre", "luffy", "Hello Java", "Mason");
        Music music1 = new Single(Format.AUDIOBOOK, "isbn3", "genre", "Unknow", "Star sky", 2020);
        Music music2 = new Single(Format.AUDIOBOOK, "isbn4", "genre", "Unknow", "Star sky", 2021);
        ArrayList<String> episodes = new ArrayList<String>();
        episodes.add("E1");
        episodes.add("E2");
        episodes.add("E3");
        Series series = new Series(Format.DVD, "isbn5", "genre", "Loki", cast, episodes);

        MediaLibrary ml = new MediaLibrary();
        ml.add("movie", movie);
        ml.add("book", book);
        ml.add("music1", music1);
        ml.add("music2", music2);
        ml.add("series", series);

        // List<Label<String, Media>> sorted = ml.sort();
        ml.sort();
        List<Label<String, Media>> sorted = ml.filter((i)->true);

        // sort
        // book, movie, series, music1, music2
        List<Label<String, Media>> expectedSortedList = new ArrayList<Label<String, Media>>();
        expectedSortedList.add(new Label<String, Media>("book", book));
        expectedSortedList.add(new Label<String, Media>("movie", movie));
        expectedSortedList.add(new Label<String, Media>("series", series));
        expectedSortedList.add(new Label<String, Media>("music1", music1));
        expectedSortedList.add(new Label<String, Media>("music2", music2));

        checkLabeledMediaLists(expectedSortedList, sorted);
    }
    @Test(timeout = 1000, expected = MediaAlreadyInLibrary.class)
    public void test_mediaalreadyinlibrary_2() throws MediaAlreadyInLibrary {
        MediaLibrary ml = new MediaLibrary();
        for (int i = 0; i < 2; i++) {
            ml.add("music", new Single(Format.CD, "8-1411-22622-5-799", "genre", "artist", "Star", 2021));
        }
    }

    @Test(timeout = 1000, expected = MediaAlreadyInLibrary.class)
    public void test_mediaalreadyinlibrary_3() throws MediaAlreadyInLibrary {
        MediaLibrary ml = new MediaLibrary();
        ml.add("music", new Single(Format.CD, "9-1111-22222-3-4444", "genre", "artist", "Star", 2021));
        ml.add("book", new Book(Format.PAPERBACK, "9-1111-22222-3-4444", "genre", "author", "Star", "Mason"));
    }

	/////////////////
	// Movie Tests //
	/////////////////
    @Test(timeout = 1000)
    public void test_movieImplementCompareTo() {
        doesSubClassImplementCompareTo("Movie", Media.class);
    }
    @Test(timeout = 1000)
    public void test_movieFields_title() {
        checkField("Movie", "title", "java.lang.String", "getTitle");
        // checkField("Movie", "director", "java.lang.String", "getDirector");
        // checkField("Movie", "year", int.class, "getYear");
        // checkField("Movie", "cast", "java.util.ArrayList", "getCast");
    }
    @Test(timeout = 1000)
    public void test_movieFields_dir() {
        // checkField("Movie", "title", "java.lang.String", "getTitle");
        checkField("Movie", "director", "java.lang.String", "getDirector");
        // checkField("Movie", "year", int.class, "getYear");
        // checkField("Movie", "cast", "java.util.ArrayList", "getCast");
    }
    @Test(timeout = 1000)
    public void test_movieFields_year() {
        // checkField("Movie", "title", "java.lang.String", "getTitle");
        // checkField("Movie", "director", "java.lang.String", "getDirector");
        checkField("Movie", "year", int.class, "getYear");
        // checkField("Movie", "cast", "java.util.ArrayList", "getCast");
    }
    @Test(timeout = 1000)
    public void test_movieFields_cast() {
        // checkField("Movie", "title", "java.lang.String", "getTitle");
        // checkField("Movie", "director", "java.lang.String", "getDirector");
        // checkField("Movie", "year", int.class, "getYear");
        checkFieldIgnoreType("Movie", "cast", "getCast");
    }
    @Test(timeout = 1000)
    public void test_movieParentClass() {
        checkParentClass("Movie", "Media");
    }
    @Test(timeout = 1000)
    public void test_movieGetType() {
        checkGetType("Movie", false);
    }
    @Test(timeout = 1000)
    public void test_movietoString() {
        checktoString("Movie");
    }
    @Test(timeout = 1000)
    public void test_movieConstructor() {
        String name = "Movie";
        boolean found_flag = true;
        try {
            checkConstructor(name, Class.forName("Format"), String.class, String.class, String.class, String.class, int.class, List.class);
        } catch (ClassNotFoundException cnfe) {
            found_flag = false;
        }
        assertTrue(name + " class not found", found_flag);
    }
    @Test(timeout = 1000)
    public void test_movieSetter() {
        checkSetter("Movie");
    }
    @Test(timeout = 1000)
    public void test_movie_1() {
        Format f = Format.DVD;
        String isbn = "234-5-67-890123-4";
        String genre = "avant-garde";
        String title = "inconnu";
        String dir = "Anonyme";
        int y = 1999;
        List<String> cast = new ArrayList<String>();
        cast.add("Actor 1");
        cast.add("Actor 2");
        cast.add("Actor 3");
        Movie m = new Movie(f, isbn, genre, title, dir, y, cast);
        assertTrue("Movie should be an instanceof Media", m instanceof Media);
        assertEquals("getType() in Movie incorrect", "Movie", m.getType());
        String strRep = "Title: " + title + ", Year: " + y + ", " + "Type: Movie, ISBN: " + isbn + ", Genre: " + genre + ", Format: " + f;
        assertEquals("Incorrect toString() for Movie", strRep, m.toString());
    }

    @Test(timeout = 1000)
    public void test_movie_2() {
        Format f = Format.DOWNLOAD;
        String isbn = "9999-8-777-6666666-5";
        String genre = "Action";
        String title = "007 No Time To Die";
        String dir = "Forgot it";
        int y = 2021;
        List<String> cast = new ArrayList<String>();
        cast.add("James Bond");
        Movie m = new Movie(f, isbn, genre, title, dir, y, cast);
        assertTrue("Movie should be an instanceof Media", m instanceof Media);
        assertEquals("getType() in Movie incorrect", "Movie", m.getType());
        String strRep = "Title: " + title + ", Year: " + y + ", " + "Type: Movie, ISBN: " + isbn + ", Genre: " + genre + ", Format: " + f;
        assertEquals("Incorrect toString() for Movie", strRep, m.toString());
    }

	/////////////////
	// Music Tests //
	/////////////////
    @Test(timeout = 1000)
    public void test_musicIsAbstract() {
        isClassAbstract("Music");
    }
    @Test(timeout = 1000)
    public void test_musicImplementCompareTo() {
        doesSubClassImplementCompareTo("Music", Media.class);
    }
    @Test(timeout = 1000)
    public void test_musicField_artist() {
        checkField("Music", "artist", "java.lang.String", "getArtist");
        // checkField("Music", "title", "java.lang.String", "getTitle");
        // checkField("Music", "year", int.class, "getYear");
    }
    @Test(timeout = 1000)
    public void test_musicField_title() {
        // checkField("Music", "artist", "java.lang.String", "getArtist");
        checkField("Music", "title", "java.lang.String", "getTitle");
        // checkField("Music", "year", int.class, "getYear");
    }
    @Test(timeout = 1000)
    public void test_musicField_year() {
        // checkField("Music", "artist", "java.lang.String", "getArtist");
        // checkField("Music", "title", "java.lang.String", "getTitle");
        checkField("Music", "year", int.class, "getYear");
    }
    @Test(timeout = 1000)
    public void test_musicConstructor() {
        String name = "Music";
        boolean found_flag = true;
        try {
            checkConstructor(name, Class.forName("Format"), String.class, String.class, String.class, String.class, int.class);
        } catch (ClassNotFoundException cnfe) {
            found_flag = false;
        }
        assertTrue(name + " class not found", found_flag);
    }
    @Test(timeout = 1000)
    public void test_musicSetter() {
        checkSetter("Music");
    }

	////////////////////////
	// SearchFilter Tests //
	////////////////////////
    @Test(timeout = 1000)
    public void test_searchFilterFields() {
        checkField("SearchFilter", "searchTerm", "java.lang.String", null);
    }
    @Test(timeout = 1000)
    public void test_searchFilterParentClass() {
        checkParentClass("SearchFilter", "java.lang.Object");
    }
    @Test(timeout = 1000)
    public void test_searchFilterConstructor() {
        checkConstructor("SearchFilter", String.class);
    }
    @Test(timeout = 1000)
    public void test_searchFilterSetter() {
        checkSetter("SearchFilter");
    }

	//////////////////
	// Series Tests //
	//////////////////
    @Test(timeout = 1000)
    public void test_seriesImplementCompareTo() {
        doesSubClassImplementCompareTo("Series", Media.class);
    }
    @Test(timeout = 1000)
    public void test_seriesFields_title() {
        checkField("Series", "title", "java.lang.String", "getTitle");
        // checkField("Series", "cast", "java.util.ArrayList", "getCast");
        // checkField("Series", "episodes", "java.util.ArrayList", "getEpisodes");
    }
    @Test(timeout = 1000)
    public void test_seriesFields_cast() {
        // checkField("Series", "title", "java.lang.String", "getTitle");
        checkFieldIgnoreType("Series", "cast", "getCast");
        // checkField("Series", "episodes", "java.util.ArrayList", "getEpisodes");
    }
    @Test(timeout = 1000)
    public void test_seriesFields_episodes() {
        // checkField("Series", "title", "java.lang.String", "getTitle");
        // checkField("Series", "cast", "java.util.ArrayList", "getCast");
        checkFieldIgnoreType("Series", "episodes", "getEpisodes");
    }
    @Test(timeout = 1000)
    public void test_seriesParentClass() {
        checkParentClass("Series", "Media");
    }
    @Test(timeout = 1000)
    public void test_seriesGetType() {
        checkGetType("Series", false);
    }
    @Test(timeout = 1000)
    public void test_seriestoString() {
        checktoString("Series");
    }
    @Test(timeout = 1000)
    public void test_seriesConstructor() {
        String name = "Series";
        boolean found_flag = true;
        try {
            checkConstructor(name, Class.forName("Format"), String.class, String.class, String.class, List.class, List.class);
        } catch (ClassNotFoundException cnfe) {
            found_flag = false;
        }
        assertTrue(name + " class not found", found_flag);
    }
    @Test(timeout = 1000)
    public void test_seriesSetter() {
        checkSetter("Series");
    }
    @Test(timeout = 1000)
    public void test_series_1() {
        Format f = Format.BLURAY;
        String isbn = "345-6-78-901234-5";
        String genre = "sitcom";
        String title = "That One Show";
        List<String> cast = new ArrayList<String>();
        cast.add("Actor 4");
        cast.add("Actor 5");
        cast.add("Actor 6");
        List<String> eps = new ArrayList<String>();
        eps.add("Episode 1");
        eps.add("Episode 2");
        eps.add("Episode 3");
        Series s = new Series(f, isbn, genre, title, cast, eps);
        assertTrue("Series should be an instanceof Media", s instanceof Media);
        assertEquals("getType() in Series incorrect", "Series", s.getType());
        String strRep = "Title: " + title + ", Type: Series, ISBN: " + isbn + ", Genre: " + genre + ", Format: " + f;
        assertEquals("Incorrect toString() for Series", strRep, s.toString());
    }

    @Test(timeout = 1000)
    public void test_series_2() {
        Format f = Format.AUDIOBOOK;
        String isbn = "777-6-5555-99999-8";
        String genre = "Pop";
        String title = "Last Chance";
        List<String> cast = new ArrayList<String>();
        cast.add("I'm actor.");
        cast.add("2nd Actor");
        List<String> eps = new ArrayList<String>();
        eps.add("S1E1");
        eps.add("S1E2");
        Series s = new Series(f, isbn, genre, title, cast, eps);
        assertTrue("Series should be an instanceof Media", s instanceof Media);
        assertEquals("getType() in Series incorrect", "Series", s.getType());
        String strRep = "Title: " + title + ", Type: Series, ISBN: " + isbn + ", Genre: " + genre + ", Format: " + f;
        assertEquals("Incorrect toString() for Series", strRep, s.toString());
    }

	//////////////////
	// Single Tests //
	//////////////////
    @Test(timeout = 1000)
    public void test_singleParentClass() {
        checkParentClass("Single", "Music");
    }
    @Test(timeout = 1000)
    public void test_SingleGetType() {
        checkGetType("Single", false);
    }
    @Test(timeout = 1000)
    public void test_singleConstructor() {
        String name = "Single";
        boolean found_flag = true;
        try {
            checkConstructor(name, Class.forName("Format"), String.class, String.class, String.class, String.class, int.class);
        } catch (ClassNotFoundException cnfe) {
            found_flag = false;
        }
        assertTrue(name + " class not found", found_flag);
    }
    @Test(timeout = 1000)
    public void test_singleSetter() {
        checkSetter("Single");
    }
    @Test(timeout = 1000)
    public void test_single_1() {
        Single s = new Single(Format.CD, "098-7-65-432098-7", "Not known", "Anonymous", "Self-titled", 2005);
        assertTrue("Single should be an instanceof Music", s instanceof Music);
        assertEquals("getType() in Single incorrect", "Single", s.getType());
        String strRep = "Artist: Anonymous, Year: 2005, Title: Self-titled, Type: Single, ISBN: 098-7-65-432098-7, Genre: Not known, Format: " + Format.CD;
        assertEquals("Incorrect toString() for Single", strRep, s.toString());
    }

    @Test(timeout = 1000)
    public void test_single_2() {
        Single s = new Single(Format.BLURAY, "000-1-22-33333333-4", "Fiction", "Atlas", "Revolution", 2020);
        assertTrue("Single should be an instanceof Music", s instanceof Music);
        assertEquals("getType() in Single incorrect", "Single", s.getType());
        String strRep = "Artist: Atlas, Year: 2020, Title: Revolution, Type: Single, ISBN: 000-1-22-33333333-4, Genre: Fiction, Format: " + Format.BLURAY;
        assertEquals("Incorrect toString() for Single", strRep, s.toString());
    }

}
