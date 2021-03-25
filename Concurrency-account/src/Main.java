import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    //TODO: Flera personer jobbar mot ett gemensamt konto i flera trådar
    // Trådfel, atomic, synchronized, lock

    private static final int NUMBER_OF_ITERATIONS = 8000;

    public static void main(String[] args) {

        Konto konto = new Konto();
        // Person p = new Person("Håkan", konto);
        // p.ändraSaldo(100);

        ExecutorService service = null;

        try {
            service = Executors.newFixedThreadPool(NUMBER_OF_ITERATIONS);
            for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                Person p = new Person("Person " + i, konto);
                // service.submit(() -> p.ändraSaldo(100));     This code insted of implement Runnable in the "Person" CLass
                service.submit(p);
            }

        } finally {
            if (service != null ) {
                service.shutdown();
                while (true) {
                    if (service.isTerminated()) {
                        System.out.println("\nFärdig! \nKontots slutgiltiga saldo är: [" + konto.getSaldo() + "]");
                        System.out.println("Kontots atomiska saldo är: [" +  konto.getAtomicSaldo() + "]");
                        System.out.println("Kontots synkroniserade saldo är: [" +  konto.getSynkroniseratSaldo() + "]");
                        System.out.println("Kontots låsta saldo är: [" +  konto.getLåstSaldo() + "]");
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
