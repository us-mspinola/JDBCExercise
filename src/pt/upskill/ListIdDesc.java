package pt.upskill;

import java.util.HashMap;
import java.util.Map;

public class ListIdDesc<K, V>{


    public Map<K, V> getKvMap() {

        Map<K,V> newKVMap= kvMap;
        return newKVMap;
    }

    private final Map<K,V> kvMap;


    public ListIdDesc( )
    {
        kvMap= new HashMap<>();

    }

    public void add(K key, V value){
        kvMap.put(key,value);
    }

    public void add(IdDesc idDesc){
        this.add((K)idDesc.getKey(), (V)idDesc.getValue());
    }





}
