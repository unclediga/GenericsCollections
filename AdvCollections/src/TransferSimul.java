import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TransferSimul implements Callable<Boolean> {
    private static final long WAIT_TIME = 500;
    private final int MAX_TRY = 5;
    private Account accD;
    private Account accK;
    private int sum;
    private int transferId;
    private CountDownLatch latch;
    private Random rnd = new Random(10);

    public TransferSimul(int transferId, CountDownLatch latch,Account accD, Account accK, int sum) {
        this.transferId = transferId;
        this.latch = latch;
        this.accD = accD;
        this.accK = accK;
        this.sum = sum;

    }

    @Override
    public Boolean call() throws Exception {

        System.out.println("t:" + transferId + " latch.await()");

        latch.await();

        int tryCount = 0;
        do {
            System.out.println("t:" + transferId + " New Try #" + tryCount);
            System.out.println("t:" + transferId + " Try lock:" + accD);
            try {
                if (accD.getLock().tryLock(WAIT_TIME, TimeUnit.MILLISECONDS)) {
                    System.out.println("t:" + transferId + " Got lock:" + accD);
                    try {

//                        Thread.sleep(1000);
                        System.out.println("t:" + transferId + " Try lock:" + accK);
                        if (accK.getLock().tryLock(WAIT_TIME, TimeUnit.MILLISECONDS)) {
                            System.out.println("t:" + transferId + " Got lock:" + accK);
                            try {


                                System.out.println("t:" + transferId + " " + accD + " <- " + accK + " : " + sum);
                                if (accK.getBalance() < sum) {
                                    throw new Exception("No Money on " + accK);
                                }
                                accK.withdrow(sum);
                                accD.deposit(sum);
                                accD.increaseSuccessTransfers();
                                accK.increaseSuccessTransfers();

                                Thread.sleep(500);
                                return true;


                            } finally {
                                System.out.println("t:" + transferId + "unlock "+accK);
                                accK.getLock().unlock();
                            }

                        } else {
                            System.out.println("t:" + transferId + " Can't get lock on KT" + accK);
                            //throw new Exception("t:" + transferId + " Can't get lock on " + accK);
                            //Thread.sleep(rnd.nextInt());

                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("t:" + transferId + "unlock "+accD);
                        accD.getLock().unlock();
                    }

                } else {
                    System.out.println("t:" + transferId + " Can't get lock on DT" + accD);
//                    throw  new Exception("t:" + transferId + " Can't get lock on " + accD);
                    //Thread.sleep(rnd.nextInt());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tryCount++;

        } while (tryCount < MAX_TRY);

        return false;


    }
}
