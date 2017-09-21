import java.util.Comparator;
import java.util.TreeSet;

public class TestTreeSet {
    public static void main(String[] args) {
        TreeSet<Integer> set = new TreeSet<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });

        set.add(3);
        set.add(2);
        set.add(1);
        System.out.println(set); // out is "[3]"
    }
}
