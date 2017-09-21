import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleLRUCache extends LinkedHashMap{

    private int cap;

    public static void main(String[] args) {

        LinkedHashMap map = new SimpleLRUCache(3);
        map.put(1,'a');System.out.println(map);
        map.put(2,'b');System.out.println(map);
        map.put(3,'c');System.out.println(map);
        map.put(4,'d');System.out.println(map);
        map.get(2);System.out.println(map);
        map.put(5,'e');System.out.println(map);
        map.put(9,'f');System.out.println(map);


    }

    public SimpleLRUCache(int initialCapacity) {
        super(initialCapacity + 1,1.1f,true);
        cap = initialCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return cap < this.size();
    }
}
