import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Konto {
    private int saldo;
    private AtomicInteger atomicSaldo = new AtomicInteger();
    private final Object synkronisering = new Object();
    private Integer synkroniseratSaldo = 0;

    private Lock lock = new ReentrantLock();
    private int låstSaldo;

    public Konto() {
        saldo = 0;
    }

    public Konto(int saldo) {
        this.saldo = saldo;
        atomicSaldo.set(saldo);
        synkroniseratSaldo = saldo;
        låstSaldo = saldo;
    }

    public int getSaldo() {
        return saldo;
    }

    public int getAtomicSaldo() {
        return atomicSaldo.get();
    }

    public Integer getSynkroniseratSaldo() {
        // Read-only, syncronized kankse överflödigt
        synchronized (synkronisering) {
            return synkroniseratSaldo;
        }
    }

    public int getLåstSaldo() {
        return låstSaldo;
    }

    public int ändraAtomicSaldo(int delta) {
        return atomicSaldo.addAndGet(delta);
    }

    public int ändraSaldo(int delta) {
        saldo += delta;
        return saldo;
    }

    public Integer ändraSynkroniseratSaldo(int delta) {
        synchronized (synkronisering) {
            return synkroniseratSaldo += delta;
        }
    }

    public int ändraLåstSaldo(int delta) {
        while (true) {
            if (lock.tryLock()) {
                try {
                    return låstSaldo += delta;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Krock");
                try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(100));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
