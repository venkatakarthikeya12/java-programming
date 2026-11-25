
import java.io.*;
import java.util.*;

public class ATMWithdrawal {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter account number: ");
            int accountNumber = sc.nextInt();

            System.out.print("Enter withdrawal amount: ");
            int amount = sc.nextInt();

            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }

            boolean found = false;

            try (BufferedReader br = new BufferedReader(
                    new FileReader("Task01_ATMExceptionHandling/input/accounts.txt"))) {

                String line;

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");

                    int accNo = Integer.parseInt(data[0]);
                    double balance = Double.parseDouble(data[1]);

                    if (accNo == accountNumber) {
                        found = true;

                        if (amount > balance) {
                            throw new ArithmeticException("Insufficient account balance");
                        }

                        System.out.println("Withdrawal successful!");
                        System.out.println("Amount withdrawn: " + amount);
                        System.out.println("Remaining balance: " + (balance - amount));
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("Account not found.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter numeric values.");

        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (FileNotFoundException e) {
            System.out.println("Error: Account file not found.");

        } catch (IOException e) {
            System.out.println("File reading error: " + e.getMessage());

        } finally {
            System.out.println("ATM transaction session ended.");
            sc.close();
        }
    }
}
