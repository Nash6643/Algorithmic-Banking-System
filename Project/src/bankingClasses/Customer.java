package bankingClasses;

public class Customer {
    private String name;
    private int customerId;

    public Customer(String name, int customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "ID: " + customerId + " | Name: " + name;
    }

    // Getters
    public String getName() { return name; }
    public int getCustomerId() { return customerId; }
}