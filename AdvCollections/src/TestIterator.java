import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

public class TestIterator {
    public static void main(String[] args) {

        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 1; i < 11; i++) list.add(i);
        System.out.println("List size = " + list.size());
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            try {
                System.out.println("get " + iterator.next());
            } catch (ConcurrentModificationException e) {
                System.out.println("Error concurrent modif");
                break;
            }
            iterator.remove();
        }
        System.out.println(list);
    }
}
