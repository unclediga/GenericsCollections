import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class TestConcurrency2 {


    public static void main(String[] args) throws InterruptedException {

        int bal1 = 100;
        int bal2 = 80;
        int bal3 = 0;
        int lengerSum = 0;
        Account a1 = new Account("A", bal1);
        Account a2 = new Account("B", bal2);
        Account a3 = new Account("C", bal3);
        Random rnd = new Random();

        ArrayList<Future> futures = new ArrayList(20);


        System.out.println("INIT STATE");
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);

        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            int sum = rnd.nextInt(20);
            Future f = service.submit(new Transfer(i, a2, a1, sum));
            bal2 += sum;
            bal1 -= sum;
            lengerSum += sum;
            futures.add(f);
        }

        ScheduledExecutorService servicei = Executors.newSingleThreadScheduledExecutor();
        servicei.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("--- TOTAL: ----");
                for (Future f : futures) {
                    if (f.isDone())
                        try {
                            System.out.println("done;  res=" + f.get());
                        } catch (InterruptedException e) {
                            System.out.println("not done ; Exception:"+e.getMessage());
                        } catch (ExecutionException e) {
                            System.out.println("not done ; Exception:"+e.getMessage());
                        }
                    else
                        System.out.println("not done yet");

                }
                System.out.println("Success Trans a1 : "+a1.getSucessTransfers());
                System.out.println("Success Trans a2 : "+a2.getSucessTransfers());
                System.out.println("--- END TOTAL ----");
            }
        }, 0, 2, TimeUnit.SECONDS);


        service.awaitTermination(15, TimeUnit.SECONDS);
        servicei.shutdown();
        service.shutdown();

        System.out.println("END STATE");
        System.out.format("%s %d\n", a1, bal1);
        System.out.format("%s %d\n", a2, bal2);
        System.out.format("%s %d\n", a3, bal3);
        System.out.format("ledger sum = %d\n", lengerSum);
    }

}
