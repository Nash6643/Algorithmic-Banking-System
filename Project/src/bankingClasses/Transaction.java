package bankingClasses;

import java.util.Date;

public class Transaction {
    private String type;
    private double amount;
    private Date date;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "Date: " + date + " | Type: " + type + " | Amount: $" + amount;
    }


    public double getAmount() { return amount; }
    public String getType() { return type; }
    public Date getDate() { return date; }

    // to generate fake dates
    public void setDate(Date date) { this.date = date; }
}
