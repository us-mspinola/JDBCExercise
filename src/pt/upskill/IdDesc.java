package pt.upskill;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class IdDesc <K, V> {

    private K key;
    private V value;


    private void buildCurso(String line){
        StringTokenizer st = new StringTokenizer(line, ";");
        key = (K)st.nextToken().trim();
        value =(V)st.nextToken().trim();
    }


    public static void loadIdDesc(ArrayList<IdDesc> list, String fileName)
    {

        FileRead fileRead = new FileRead();
        fileRead.init(fileName);
        String line;
        IdDesc idDesc;


        do{
            line=fileRead.readLine();
            if (line != null){
                    idDesc = new IdDesc();
                    idDesc.buildCurso(line);
                    list.add(idDesc);

            }
        } while (line!=null);

        fileRead.close();
    }


    public static void loadIdDescFromFile(ListIdDesc list, String fileName)
    {

        FileRead fileRead = new FileRead();
        fileRead.init(fileName);
        String line;
        IdDesc idDesc;


        do{
            line=fileRead.readLine();
            if (line != null){
                idDesc = new IdDesc();
                idDesc.buildCurso(line);
                list.add(idDesc);

            }
        } while (line!=null);

        fileRead.close();
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
