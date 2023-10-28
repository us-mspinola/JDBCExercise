package pt.upskill;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class IdDesc <K, V> {

    private final K key;
    private final V value;


    public IdDesc(K key,V value){
        this.key = key;
        this.value =value;
    }



    public K getKey() {
        return key;
    }



    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IdDesc{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
