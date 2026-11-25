
class Wallet {

    int id;
    int balance;

    Wallet(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }
}

public class WalletTransfer {

    static void transfer(Wallet a, Wallet b, int amount) {

        Wallet first = a.id < b.id ? a : b;
        Wallet second = a.id < b.id ? b : a;

        synchronized (first) {
            synchronized (second) {

                if (a.balance >= amount) {
                    a.balance -= amount;
                    b.balance += amount;

                    System.out.println("Transferred " + amount
                            + " from Wallet " + a.id
                            + " to Wallet " + b.id);
                } else {
                    System.out.println("Insufficient balance.");
                }
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Wallet w1 = new Wallet(1, 5000);
        Wallet w2 = new Wallet(2, 5000);

        Thread t1 = new Thread(() -> transfer(w1, w2, 1000));
        Thread t2 = new Thread(() -> transfer(w2, w1, 1500));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Wallet 1 balance: " + w1.balance);
        System.out.println("Wallet 2 balance: " + w2.balance);
    }
}
