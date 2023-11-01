public class Label<K,V extends Comparable<V>> implements Comparable<Label<K,V>> {

    /**
     * attributes
     * key and value types
     */
    private K key;
    private V value;

    /**
     * constructor that set key and value to their private variables
     * @param key
     * @param value
     */
    public Label(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * checks to see if the values of an object and itself are equal, if so, 
     * @return true, if not, return false
     */
    public boolean equals(Object obj) {
        if (obj instanceof Label) 
            if (this.value.equals(((Label)obj).getValue())) {
                return true;
            }
        return false;
    }

    /**
     * compare to method, return 1 if argument is bigger, -1 if it is smaller, and 0 if it is the same
     */
    @Override
    public int compareTo(Label<K, V> o) {
        if (value.compareTo(o.getValue()) < 0)
            return -1;
        if (value.compareTo(o.getValue()) > 0)
            return 1;
        return 0;


    }

    /**
     * getter method for key
     * @return key
     */
    public final K getKey() {
        return key;
    }

    /**
     * getter method for value
     * @return value
     */
    public final V getValue() {
        return value;
    }

}
