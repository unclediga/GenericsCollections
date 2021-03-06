import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private String id;
    private int balance;
    private AtomicInteger successTrans = new AtomicInteger(0);

    public Lock getLock() {
        return lock;
    }

    private Lock lock = new ReentrantLock();

    public Account(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int sum) {
        balance += sum;
    }

    @Override
    public String toString() {
        return "Acc{" + id + "}=" + balance;
    }

    public void withdrow(int sum) {
        if(balance < sum) {
            System.err.println("no money on " + this);
            return;
        }
        balance -= sum;
    }

    public void increaseSuccessTransfers() {
        successTrans.incrementAndGet();
    }

    public int getSucessTransfers() {
        return successTrans.get();
    }
}
