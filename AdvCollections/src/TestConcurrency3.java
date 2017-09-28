import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class TestConcurrency3 {


    private static int TRANS_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {

        int bal1 = 100;
        int bal2 = 80;
        int bal3 = 0;
        int lengerSum = 0;
        Account a1 = new Account("A", bal1);
        Account a2 = new Account("B", bal2);
        Account a3 = new Account("C", bal3);
        Random rnd = new Random();

        CountDownLatch latch = new CountDownLatch(1);

        ArrayList<Future> futures = new ArrayList(20);


        System.out.println("INIT STATE");
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);

        ExecutorService service = Executors.newFixedThreadPool(TRANS_COUNT);
        for (int i = 0; i < TRANS_COUNT; i++) {
            int sum = rnd.nextInt(20);
            Future f = service.submit(new TransferSimul(i, latch, a2, a1, sum));
            bal2 += sum;
            bal1 -= sum;
            lengerSum += sum;
            futures.add(f);
        }
        System.out.println("--------- GO ! ");
        Thread.sleep(2000);
        latch.countDown();

        service.awaitTermination(20, TimeUnit.SECONDS);
        service.shutdown();

        System.out.println("END STATE");
        System.out.format("%s %d\n", a1, bal1);
        System.out.format("%s %d\n", a2, bal2);
        System.out.format("%s %d\n", a3, bal3);
        System.out.format("ledger sum = %d\n", lengerSum);
    }

}
