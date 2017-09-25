import java.util.*;

public class TestIterator {

    private static LinkedList<Integer> list;

    public static void main(String[] args) {

        List<Integer> list = new LinkedList<Integer>();
        List<Integer> listSyn = Collections.synchronizedList(new LinkedList<Integer>());

        System.out.println("**** NO SYN *********");
        testList(list);
        System.out.println("**** SYN *********");
        testList(listSyn);
    }

    private static void testList(List<Integer> list) {
        for (int i = 1; i < 11; i++) list.add(i);
        System.out.println("List.size()=" + list.size());
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            try {
                System.out.println("get " + iterator.next());
            } catch (ConcurrentModificationException e) {
                System.out.println("Error concurrent modif");
                break;
            }
            list.add(22);
        }
        System.out.println("LIST="+list);
    }
}
