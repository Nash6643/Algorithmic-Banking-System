package bankingGUI;

import bankingClasses.Account;
import bankingClasses.Customer;
import bankingStructures.BinarySearchTree;
import bankingStructures.HashTable;

import java.util.Random;

public class FakeAccountGenerator {

    private static final String[] NAMES = {
            "Ahmed", "Omar", "Utku", "Walter White","Jesse Pinkman","Gus Fring"
    };

    public static void generateData(BinarySearchTree bst, HashTable hash, int count) {
        Random rand = new Random();

        for (int i = 1; i <= count; i++) {
            // 1. Create Basic Account
            int id = 1000 + i;
            String name = NAMES[rand.nextInt(NAMES.length)] + "_" + i;
            double initialBalance = 1000 + rand.nextDouble() * 5000;

            Customer cust = new Customer(name, id);
            Account acc = new Account(id, cust, initialBalance);


            int historySize = 15;
            for (int j = 0; j < historySize; j++) {
                double rawAmount = 10 + rand.nextDouble() * 1000;
                double amount = Math.round(rawAmount * 100.0) / 100.0; // Round to 2 decimals

                // Randomly choose Deposit or Withdrawal
                if (rand.nextBoolean()) {
                    acc.deposit(amount);
                } else {
                    acc.withdraw(amount);
                }
            }


            bst.insert(acc);
            hash.put(id, cust);
        }

        System.out.println("Generated " + count + " fake accounts, each with 15 transactions.");
    }
}