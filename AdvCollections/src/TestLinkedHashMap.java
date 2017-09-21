import java.util.LinkedHashMap;

public class TestLinkedHashMap {

    public static void main(String[] args) {

        LinkedHashMap map1 = new LinkedHashMap(5,1,true);
        LinkedHashMap map2 = new LinkedHashMap(5,1,false);
        map1.put(1, null);
        map1.put(2, null);
        map1.put(3, null);
        map1.put(4, null);
        map1.put(5, null);
        map1.get(5);
        map1.get(3);

        map2.put(1, null);
        map2.put(2, null);
        map2.put(3, null);
        map2.put(4, null);
        map2.put(5, null);
        map2.get(5);
        map2.get(3);
        System.out.println("map1 true " + map1);
        System.out.println("map2 false " + map2);


    }
}
