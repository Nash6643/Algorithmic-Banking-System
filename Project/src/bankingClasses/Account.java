package bankingClasses;

import java.util.ArrayList;

public class Account implements Comparable<Account> {
    private int accountNumber;
    private Customer customer;
    private double balance;
    private double debt;
    private ArrayList<Transaction> history;

    public Account(int accountNumber, Customer customer, double balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
        this.debt = 0.0;
        this.history = new ArrayList<>();
        addTransaction(new Transaction("Initial Deposit", balance));
    }


    public boolean transferTo(Account target, double amount) {
        if (target == null || amount <= 0 || amount > balance) {
            return false; // Transfer failed
        }

        // 1. Remove from Sender
        this.balance -= amount;
        this.addTransaction(new Transaction("Transfer Sent to #" + target.getAccountNumber(), amount));

        // 2. Add to Receiver
        target.receiveTransfer(this.accountNumber, amount);

        return true;
    }

    // Helper for the receiver side
    public void receiveTransfer(int senderID, double amount) {
        this.balance += amount;
        this.addTransaction(new Transaction("Transfer Received from #" + senderID, amount));
    }



    public void takeLoan(double amount) {
        if (amount <= 0) return;
        this.balance += amount;
        double interestRate = 0.05;
        double totalDebtToAdd = amount * (1 + interestRate);
        this.debt += totalDebtToAdd;
        addTransaction(new Transaction("Loan Received", amount));
        addTransaction(new Transaction("Interest Added", totalDebtToAdd - amount));
    }

    public boolean payOffDebt(double amount) {
        if (amount <= 0 || amount > balance) return false;
        double payment = Math.min(amount, debt);
        this.balance -= payment;
        this.debt -= payment;
        addTransaction(new Transaction("Debt Payment", payment));
        return true;
    }

    public double getDebt() { return debt; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction(new Transaction("Deposit", amount));
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            addTransaction(new Transaction("Withdrawal", amount));
            return true;
        }
        return false;
    }

    private void addTransaction(Transaction t) { history.add(t); }
    public int getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public ArrayList<Transaction> getHistory() { return history; }

    @Override
    public String toString() {
        return String.format("Acc: %d | %s | Bal: $%.2f | Debt: $%.2f",
                accountNumber, customer.getName(), balance, debt);
    }

    @Override
    public int compareTo(Account other) {
        return Integer.compare(this.accountNumber, other.accountNumber);
    }
}