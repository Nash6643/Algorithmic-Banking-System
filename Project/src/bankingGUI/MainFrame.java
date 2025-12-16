package bankingGUI;

import bankingClasses.Account;
import bankingClasses.Customer;
import bankingClasses.Transaction;
import bankingStructures.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainFrame extends JFrame {

    private BinarySearchTree accountTree;
    private HashTable customerHash;
    private JTextArea displayArea;
    private JTextField idField, nameField, balanceField, amountField;

    private ComplexityGraphPanel cachedGraphPanel = null;
    private int cachedDataSize = 0;

    // Visual Colors
    private final Color BG_COLOR = new Color(245, 245, 250);
    private final Color BTN_BLUE = new Color(173, 216, 230);
    private final Color BTN_GREEN = new Color(152, 251, 152);
    private final Color BTN_RED = new Color(255, 182, 193);
    private final Color BTN_YELLOW = new Color(255, 255, 200);
    private final Color BTN_ORANGE = new Color(255, 200, 150); // New Color for Transfer
    private final Color BTN_GRAY = new Color(230, 230, 230);

    public MainFrame() {
        accountTree = new BinarySearchTree();
        customerHash = new HashTable(10000);

        setTitle("Banking System Pro - Final Build");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("BANKING ALGORITHMS SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);


        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("System Logs & Output"));
        add(scrollPane, BorderLayout.CENTER);


        JPanel mainControlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        mainControlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainControlPanel.setBackground(BG_COLOR);


        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.setBackground(BG_COLOR);
        inputPanel.setBorder(BorderFactory.createTitledBorder("1. Account Management"));

        idField = new JTextField(5); inputPanel.add(new JLabel("ID:")); inputPanel.add(idField);
        nameField = new JTextField(8); inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        balanceField = new JTextField(6); inputPanel.add(new JLabel("Bal:")); inputPanel.add(balanceField);

        JButton createBtn = new ModernButton("Create", BTN_GRAY);
        JButton searchBtn = new ModernButton("Search (BST)", BTN_GRAY);
        JButton loadBtn = new ModernButton("Load 1k Data", BTN_RED);
        JButton saveBtn = new ModernButton("Save (.csv)", BTN_GREEN);

        inputPanel.add(createBtn); inputPanel.add(searchBtn);
        inputPanel.add(Box.createHorizontalStrut(20)); inputPanel.add(loadBtn); inputPanel.add(saveBtn);


        JPanel financePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        financePanel.setBackground(BG_COLOR);
        financePanel.setBorder(BorderFactory.createTitledBorder("2. Financial Services"));

        amountField = new JTextField(6);
        financePanel.add(new JLabel("Amount ($):")); financePanel.add(amountField);

        JButton loanBtn = new ModernButton("Take Loan", BTN_YELLOW);
        JButton payDebtBtn = new ModernButton("Pay Debt", BTN_GREEN);


        JButton transferBtn = new ModernButton("Transfer Funds", BTN_ORANGE);

        financePanel.add(loanBtn); financePanel.add(payDebtBtn);
        financePanel.add(Box.createHorizontalStrut(20));
        financePanel.add(transferBtn);


        JPanel algoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        algoPanel.setBackground(BG_COLOR);
        algoPanel.setBorder(BorderFactory.createTitledBorder("3. Algorithmic Analysis"));

        JButton mergeBtn = new ModernButton("Merge Sort", BTN_GRAY);
        JButton quickBtn = new ModernButton("Quick Sort", BTN_GRAY);
        JButton heapBtn = new ModernButton("Heap Sort", BTN_GRAY);
        JButton binSearchBtn = new ModernButton("Bin Search", BTN_GRAY);
        JButton graphBtn = new ModernButton("Complexity Graph", BTN_BLUE);

        algoPanel.add(mergeBtn); algoPanel.add(quickBtn); algoPanel.add(heapBtn);
        algoPanel.add(Box.createHorizontalStrut(20));
        algoPanel.add(binSearchBtn); algoPanel.add(graphBtn);

        mainControlPanel.add(inputPanel); mainControlPanel.add(financePanel); mainControlPanel.add(algoPanel);
        add(mainControlPanel, BorderLayout.SOUTH);



        createBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Customer c = new Customer(nameField.getText(), id);
                Account a = new Account(id, c, Double.parseDouble(balanceField.getText()));
                accountTree.insert(a); customerHash.put(id, c);
                cachedGraphPanel = null;
                log("Created: " + a);
            } catch (Exception ex) { log("Error: Check inputs"); }
        });

        searchBtn.addActionListener(e -> {
            try {
                long s = System.nanoTime();
                Account a = accountTree.search(Integer.parseInt(idField.getText()));
                if(a!=null) {
                    log("FOUND: " + a + " (" + (System.nanoTime()-s) + " ns)");
                    printHistory(a);
                } else log("Not Found");
            } catch (Exception ex) { log("Invalid ID"); }
        });

        loadBtn.addActionListener(e -> {
            log("Generating 1,000 accounts...");
            cachedGraphPanel = null;
            new Thread(() -> {
                FakeAccountGenerator.generateData(accountTree, customerHash, 1000);
                SwingUtilities.invokeLater(() -> log("Data Loaded! (~15,000 txns ready)"));
            }).start();
        });

        saveBtn.addActionListener(e -> {
            try {
                java.io.FileWriter w = new java.io.FileWriter("bank_data.csv");
                w.write("ID,Name,Balance\n");
                for (Account a : accountTree.getAllAccounts()) w.write(a.getAccountNumber() + "," + a.getBalance() + "\n");
                w.close(); log("Saved to bank_data.csv");
            } catch (Exception ex) { log("Error saving file"); }
        });

        loanBtn.addActionListener(e -> {
            Account a = getAccount();
            if(a == null) return;
            try {
                double val = Double.parseDouble(amountField.getText());
                a.takeLoan(val);
                log("LOAN APPROVED: Added $" + val + ". New Debt: $" + String.format("%.2f", a.getDebt()));
            } catch(Exception ex) { log("Invalid Amount"); }
        });

        payDebtBtn.addActionListener(e -> {
            Account a = getAccount();
            if(a == null) return;
            try {
                double val = Double.parseDouble(amountField.getText());
                if(a.payOffDebt(val)) log("PAID $" + val + ". Remaining Debt: $" + String.format("%.2f", a.getDebt()));
                else log("Payment Failed (Check Balance)");
            } catch(Exception ex) { log("Invalid Amount"); }
        });

        // --- NEW TRANSFER LISTENER ---
        transferBtn.addActionListener(e -> {
            Account sender = getAccount(); // Get current selected account
            if(sender == null) {
                log("Error: Please search/select a SENDER account first (using ID field).");
                return;
            }
            try {
                // 1. Get Amount
                double amount = Double.parseDouble(amountField.getText());

                // 2. Ask for Target ID using a Popup
                String input = JOptionPane.showInputDialog("Enter Receiver Account ID:");
                if(input == null) return; // User cancelled

                int targetId = Integer.parseInt(input);
                if (targetId == sender.getAccountNumber()) {
                    log("Error: Cannot transfer to yourself.");
                    return;
                }

                // 3. Search BST for Target
                long startSearch = System.nanoTime();
                Account receiver = accountTree.search(targetId);
                long searchTime = System.nanoTime() - startSearch;

                if (receiver != null) {
                    // 4. Execute Transfer
                    boolean success = sender.transferTo(receiver, amount);
                    if (success) {
                        log("--- TRANSFER SUCCESSFUL ---");
                        log("From: " + sender.getAccountNumber() + " -> To: " + receiver.getAccountNumber());
                        log("Amount: $" + amount);
                        log("Target Search Time: " + searchTime + " ns (BST Efficiency!)");
                        printHistory(sender);
                    } else {
                        log("Transfer Failed: Insufficient funds.");
                    }
                } else {
                    log("Error: Receiver ID " + targetId + " not found in database.");
                }
            } catch (Exception ex) {
                log("Error: Invalid input. Check Amount or ID.");
            }
        });

        mergeBtn.addActionListener(e -> runSort("Merge", list -> new MergeSort().sort(list)));
        quickBtn.addActionListener(e -> runSort("Quick", list -> new QuickSort().sort(list)));
        heapBtn.addActionListener(e -> runSort("Heap", list -> new HeapSort().sort(list)));

        binSearchBtn.addActionListener(e -> {
            Account a = getAccount();
            if(a == null) return;
            try {
                long s = System.nanoTime();
                Transaction t = new BinarySearch().search(a.getHistory(), Double.parseDouble(amountField.getText()));
                if(t!=null) log("FOUND: " + t); else log("Not found");
                log("Time: " + (System.nanoTime()-s) + " ns");
            } catch(Exception ex) { log("Invalid Amount"); }
        });

        graphBtn.addActionListener(e -> {
            ArrayList<Account> accounts = accountTree.getAllAccounts();
            if (accounts.isEmpty()) { log("Load accounts first!"); return; }
            if (cachedGraphPanel != null && cachedDataSize == accounts.size()) {
                showGraphWindow(cachedGraphPanel); return;
            }
            log("Generating Benchmark");
            new Thread(() -> {
                ArrayList<Transaction> allTxns = new ArrayList<>();
                for (Account a : accounts) allTxns.addAll(a.getHistory());
                int N = allTxns.size();
                ArrayList<Integer> sizes = new ArrayList<>();
                ArrayList<Long> bstT = new ArrayList<>(), mergeT = new ArrayList<>(), quickT = new ArrayList<>(), heapT = new ArrayList<>();

                int step = Math.max(10, N / 10);
                for (int n = step; n <= N; n += step) {
                    sizes.add(n);
                    ArrayList<Transaction> sub = new ArrayList<>(allTxns.subList(0, n));
                    Collections.shuffle(sub, new Random(12345));

                    long mT=0, qT=0, hT=0, bT=0;
                    for(int r=0; r<5; r++) {
                        ArrayList<Transaction> c1 = new ArrayList<>(sub); long t1=System.nanoTime(); new MergeSort().sort(c1); mT+=(System.nanoTime()-t1);
                        ArrayList<Transaction> c2 = new ArrayList<>(sub); long t2=System.nanoTime(); new QuickSort().sort(c2); qT+=(System.nanoTime()-t2);
                        ArrayList<Transaction> c3 = new ArrayList<>(sub); long t3=System.nanoTime(); new HeapSort().sort(c3); hT+=(System.nanoTime()-t3);
                        long t4=System.nanoTime(); accountTree.search(accounts.get(0).getAccountNumber()); bT+=(System.nanoTime()-t4);
                    }
                    mergeT.add(mT/5); quickT.add(qT/5); heapT.add(hT/5); bstT.add(bT/5);
                }
                cachedGraphPanel = new ComplexityGraphPanel(sizes, bstT, mergeT, quickT, heapT);
                cachedDataSize = accounts.size();
                SwingUtilities.invokeLater(() -> showGraphWindow(cachedGraphPanel));
            }).start();
        });
    }

    private void showGraphWindow(JPanel panel) {
        JFrame f = new JFrame("Complexity Analysis");
        f.setSize(900, 600); f.add(panel); f.setVisible(true);
    }
    private Account getAccount() {
        try { return accountTree.search(Integer.parseInt(idField.getText())); } catch (Exception e) { return null; }
    }
    private void log(String s) { displayArea.append(s + "\n"); displayArea.setCaretPosition(displayArea.getDocument().getLength()); }

    private void printHistory(Account acc) {
        displayArea.append("History for Account " + acc.getAccountNumber() + ":\n");
        int count = 0;
        for(Transaction t : acc.getHistory()) {
            displayArea.append(String.format("   [%.2f] %s\n", t.getAmount(), t.getType()));
            count++;
            if(count >= 15) {
                displayArea.append("   ... (" + (acc.getHistory().size() - 15) + " more)\n");
                break;
            }
        }
        displayArea.append("----------------------\n");
    }

    private interface Sorter { void sort(ArrayList<Transaction> list); }
    private void runSort(String n, Sorter s) {
        Account a = getAccount();
        if(a!=null) {
            long st = System.nanoTime();
            s.sort(a.getHistory());
            log(n + " done: " + (System.nanoTime()-st) + " ns");
            printHistory(a);
        }
    }
}