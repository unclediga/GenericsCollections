import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class TestSortedSet {
    static SortedSet<Integer> set;

    public static void main(String[] args) {

        set = new TreeSet<Integer>();
        for (int i = 1; i < 11; i++) {
            set.add(i);

        }
        System.out.println(set);

        System.out.println("for 6 next is " + getNextElem(6));
        System.out.println("for null next is " + getNextElem(null));
        System.out.println("for " + set.size() + " next is " + getNextElem(set.size()));

        System.out.println("for " + 6 + " prev is " + getPrevsElem(6));
        System.out.println("for " + set.size() + " prev is " + getPrevsElem(set.size()));

        System.out.println("for " + 6 + " lower is " + ((NavigableSet) set).lower(6));
        System.out.println("for " + 6 + " higher is " + ((NavigableSet) set).higher(6));

    }

    static Integer getNextElem(Integer integer) {

//        Iterator it = set.iterator();
//        while (it.hasNext()) {
//            if (it.next().equals(integer)) {
//                break;
//            }
//        }
//        if(!it.hasNext()) return null;
//        return (Integer) it.next();

        return integer == null ? null: (Integer) ((NavigableSet) set).higher(integer);
    }

    static SortedSet<Integer> getPrevsElem(Integer integer) {
//        if (set.contains(integer)){
//            return set.headSet(integer);
//        }else
//            return null;

        return integer == null ? null: ((NavigableSet) set).headSet(integer);
    }

}
