public class TestConcurrency1 {


    public static void main(String[] args) throws InterruptedException {

        Account a1 = new Account("A", 100);
        Account a2 = new Account("B", 80);
        Account a3 = new Account("C", 0);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Operations.transferLock(a2, a1, 50);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Operations.transferLock(a3, a2, 50);
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                Operations.transferLock(a1, a2, 50);
            }
        });

        System.out.println("INIT STATE");
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("END STATE");
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
    }

}
