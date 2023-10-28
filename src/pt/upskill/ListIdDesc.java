package pt.upskill;

import java.util.HashMap;

public class ListIdDesc<K, V>{


    public HashMap<K, V> getKvMap() {

        HashMap<K,V> newKVMap= kvMap;
        return newKVMap;
    }

    private final HashMap<K,V> kvMap;


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
