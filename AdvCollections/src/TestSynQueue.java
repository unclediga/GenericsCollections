import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class TestSynQueue {


    static SynchronousQueue<Integer> queue;

    public static void main(String[] args) {

        queue = new SynchronousQueue<Integer>();
        Runnable producer = new MyProducer();
        Runnable consumer = new MyConsumer();

//         ERROR!!!
//        execute in main thread.
//        Thread will be blocked on first put() in permanent waiting state
//        CONSTANT BLOCKING MAIN TREAD
//
//        producer.run();
//        consumer.run();

//      VERSION 1
        new Thread(producer).start();
        new Thread(consumer).start();

//      VERISON 2
//        ExecutorService service = Executors.newFixedThreadPool(2);
//        service.submit(consumer);
//        service.submit(producer);
//        service.shutdown();


    }

    private static class MyProducer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("P: I want to put "+i);
                    queue.put(i);
                    System.out.println("P: I put "+ i);
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    private static class MyConsumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("C: I want to take...");
                    int res = queue.take();
                    System.out.println("C: I taked : "+ res);
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
