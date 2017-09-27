import java.util.concurrent.TimeUnit;

public class Operations {

    private static final long WAIT_TIME = 5;

    public static void transferSyn(Account accD, Account accK, int sum) {
        System.out.println("t:"+Thread.currentThread()+" Try lock:"+accD);
        synchronized (accD) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t:"+Thread.currentThread()+" Try lock acc:"+accK);
            synchronized (accK) {
                accD.deposit(sum);
                accK.withdrow(sum);
                System.out.println(accD + " -> " +accK+" : " + sum);

            }
        }

    }

    public static void transferLock(Account accD, Account accK, int sum) {


        // Deadlock avoid
        Account acc1 = null;
        Account acc2 = null;
        if (accD.getId().compareTo(accK.getId()) == -1){
            acc1 = accD;
            acc2= accK;
        }else {
            acc1 = accK;
            acc2= accD;
        }


        System.out.println("t:"+Thread.currentThread().getId() +" Try lock:"+acc1);
        try {
            if(acc1.getLock().tryLock(WAIT_TIME, TimeUnit.SECONDS)){
                System.out.println("t:"+Thread.currentThread().getId() +" Got lock:"+acc1);
                try{

                    Thread.sleep(1000);
                    System.out.println("t:"+Thread.currentThread().getId() +" Try lock:"+acc2);
                    if(acc2.getLock().tryLock(WAIT_TIME, TimeUnit.SECONDS)){
                        System.out.println("t:"+Thread.currentThread().getId() +" Got lock:"+acc2);
                        try{


                            System.out.println(accD + " <- " +accK + " : " + sum);
                            accK.withdrow(sum);
                            accD.deposit(sum);


                        }finally {
                            acc2.getLock().unlock();
                        }

                    }else {
                        System.out.println("t:"+Thread.currentThread().getId() +" Can't get lock on "+acc2);

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    acc1.getLock().unlock();
                }

            }else {
                System.out.println("t:"+Thread.currentThread().getId() + " Can't get lock on "+acc1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
