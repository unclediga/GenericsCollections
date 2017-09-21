import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class TestWeakHashMap {

    public static void main(String[] args) {

        Date[] date = new Date[10];
        Date dt = new Date();
        WeakHashMap<Date, String> map = new WeakHashMap<Date, String>();
        for (int i = 0; i < 10; i++) {

            date[i] = new Date();
            date[i].setTime(dt.getTime()+1000*60*60*24*i);

            map.put(date[i], date[i].toString());

        }

        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            Object o = iterator.next();
            System.out.println("key "+ o +" || val " +map.get(o));
        }


        date = null;

        System.gc();

        System.out.println(map);

    }
}
