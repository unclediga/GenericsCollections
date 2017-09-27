import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Transfer implements Callable<Boolean> {
    private static final long WAIT_TIME = 2;
    private Account accD;
    private Account accK;
    private int sum;
    private int transferId;

    public Transfer(int transferId, Account accD, Account accK, int sum) {
        this.transferId = transferId;
        this.accD = accD;
        this.accK = accK;
        this.sum = sum;

    }

    @Override
    public Boolean call() throws Exception {

        System.out.println("t:" + transferId + " Try lock:" + accD);
        try {
            if (accD.getLock().tryLock(WAIT_TIME, TimeUnit.SECONDS)) {
                System.out.println("t:" + transferId + " Got lock:" + accD);
                try {

                    Thread.sleep(1000);
                    System.out.println("t:" + transferId + " Try lock:" + accK);
                    if (accK.getLock().tryLock(WAIT_TIME, TimeUnit.SECONDS)) {
                        System.out.println("t:" + transferId + " Got lock:" + accK);
                        try {


                            System.out.println("t:"+transferId+ " "+accD + " <- " + accK + " : " + sum);
                            if (accK.getBalance() < sum) {
                                throw new Exception("No Money on " + accK);
                            }
                            accK.withdrow(sum);
                            accD.deposit(sum);
                            accD.increaseSuccessTransfers();
                            accK.increaseSuccessTransfers();

                            Thread.sleep(1000);
                            return true;


                        } finally {
                            accK.getLock().unlock();
                        }

                    } else {
                        System.out.println("t:" + transferId + " Can't get lock on " + accK);
                        throw new Exception("t:" + transferId + " Can't get lock on " + accK);

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    accD.getLock().unlock();
                }

            } else {
                System.out.println("t:" + transferId + " Can't get lock on " + accD);
                throw  new Exception("t:" + transferId + " Can't get lock on " + accD);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;


    }
}
