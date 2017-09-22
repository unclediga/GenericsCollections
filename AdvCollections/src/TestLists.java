import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class TestLists {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Integer> cowlist = new CopyOnWriteArrayList<Integer>();
        List<Integer> slist = Collections.synchronizedList(new ArrayList<Integer>(100));
        fillList(cowlist, 1000);
        fillList(slist, 1000);

        System.out.println("Check CopyOnWriteArrayList");
        checkList(cowlist, false);
        checkList(cowlist, true);

        System.out.println("Check synchronizedList");
        checkList(slist, false);
        checkList(slist, true);


    }

    private static void checkList(List<Integer> list, boolean adding) throws ExecutionException, InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<Long> f1 = service.submit(new ListRunner(0, 500, list, latch, adding));
        Future<Long> f2 = service.submit(new ListRunner(500, 1000, list, latch, adding));

        latch.countDown();

        long tr1 = f1.get() / 1000;
        System.out.println("Thread1 " + tr1);
        long tr2 = f2.get() / 1000;
        System.out.println("Thread2 " + tr2);
        System.out.println("TOTAL " + (tr2 + tr1));

        service.shutdown();

    }

    private static void fillList(List<Integer> list, int size) {

        for (int i = 0; i < size; i++) {
            list.add(i);
        }
    }

    static class ListRunner implements Callable<Long> {
        int start;
        int end;
        List list;
        CountDownLatch latch;
        boolean adding;

        public ListRunner(int start, int end, List list, CountDownLatch latch, boolean adding) {
            this.start = start;
            this.end = end;
            this.list = list;
            this.latch = latch;
            this.adding = adding;
        }

        @Override
        public Long call() throws Exception {

            latch.await();

            long t1 = System.nanoTime();

            for (int i = start; i < end; i++) {
                list.get(i);
                if (adding)
                    if (i % 2 == 0) list.add(1001);
            }

            return System.nanoTime() - t1;
        }
    }

}
