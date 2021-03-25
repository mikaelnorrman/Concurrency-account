public class Person implements Runnable {

    private String namn;
    private Konto konto;


    public Person(String namn, Konto konto) {
        this.namn = namn;
        this.konto = konto;
    }

    public void ändraSaldo(int delta) {
        // System.out.println("Saldo: " + konto.getSaldo());
        int nyttSaldo = konto.ändraSaldo(delta);
        konto.ändraAtomicSaldo(delta);
        konto.ändraSynkroniseratSaldo(delta);
        konto.ändraLåstSaldo(delta);

        System.out.println("Person: [" + namn + "] ändrade saldo till: " + nyttSaldo);
    }

    @Override
    public void run() {
        ändraSaldo(100);
    }
}
