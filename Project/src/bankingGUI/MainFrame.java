package bankingGUI;

import bankingClasses.Account;
import bankingClasses.Customer;
import bankingClasses.Transaction;
import bankingStructures.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame {

    private BinarySearchTree accountTree;
    private HashTable customerHash;
    private JTextArea displayArea;
    private JTextField idField, nameField, balanceField, amountField;

    private ComplexityGraphPanel cachedGraphPanel = null;
    private int cachedDataSize = 0;

    // Modern Professional Color Scheme (Dark Slate Theme)
    private final Color MAIN_BG = new Color(24, 28, 36);
    private final Color CARD_BG = new Color(33, 38, 49);
    private final Color HEADER_BG = new Color(15, 23, 42);
    private final Color TEXT_PRIMARY = new Color(241, 245, 249);
    private final Color ACCENT_BLUE = new Color(37, 99, 235);
    private final Color ACCENT_GREEN = new Color(16, 185, 129);
    private final Color ACCENT_RED = new Color(239, 68, 68);
    private final Color ACCENT_AMBER = new Color(245, 158, 11);
    private final Color ACCENT_PURPLE = new Color(139, 92, 246);
    private final Color BTN_NEUTRAL = new Color(51, 65, 85);

    public MainFrame() {
        accountTree = new BinarySearchTree();
        customerHash = new HashTable(10000);

        setTitle("Banking System Pro - Enterprise Algorithmic Dashboard");
        setSize(1280, 880);
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(MAIN_BG);

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(HEADER_BG);
        JLabel titleLabel = new JLabel("BANKING ALGORITHMS MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_PRIMARY);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Display Logs Output Center
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
        displayArea.setBackground(new Color(15, 23, 42));
        displayArea.setForeground(new Color(148, 163, 184));
        displayArea.setCaretColor(Color.WHITE);
        displayArea.setMargin(new Insets(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(createStyledTitledBorder("System Operations & Data Logs"));
        scrollPane.getViewport().setBackground(MAIN_BG);
        add(scrollPane, BorderLayout.CENTER);

        // Main Control Bottom Panel
        JPanel mainControlPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainControlPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainControlPanel.setBackground(MAIN_BG);

        // --- 1. Account Management ---
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        inputPanel.setBackground(CARD_BG);
        inputPanel.setBorder(createStyledTitledBorder("1. Account Management"));

        idField = createStyledTextField(6); 
        nameField = createStyledTextField(10); 
        balanceField = createStyledTextField(8);

        inputPanel.add(createStyledLabel("Account ID:")); inputPanel.add(idField);
        inputPanel.add(createStyledLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(createStyledLabel("Initial Bal ($):")); inputPanel.add(balanceField);

        JButton createBtn = new ModernButton("Create Account", ACCENT_BLUE);
        JButton searchBtn = new ModernButton("Search (BST)", BTN_NEUTRAL);
        JButton loadBtn = new ModernButton("Load 1k Data", ACCENT_PURPLE);
        JButton saveBtn = new ModernButton("Export (.csv)", ACCENT_GREEN);

        inputPanel.add(createBtn); 
        inputPanel.add(searchBtn);
        inputPanel.add(Box.createHorizontalStrut(15)); 
        inputPanel.add(loadBtn); 
        inputPanel.add(saveBtn);

        // --- 2. Financial Services ---
        JPanel financePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        financePanel.setBackground(CARD_BG);
        financePanel.setBorder(createStyledTitledBorder("2. Financial Transactions"));

        amountField = createStyledTextField(8);
        financePanel.add(createStyledLabel("Transaction Amount ($):")); 
        financePanel.add(amountField);

        JButton loanBtn = new ModernButton("Take Loan", ACCENT_AMBER);
        JButton payDebtBtn = new ModernButton("Pay Debt", ACCENT_GREEN);
        JButton transferBtn = new ModernButton("Transfer Funds", ACCENT_BLUE);

        financePanel.add(loanBtn); 
        financePanel.add(payDebtBtn);
        financePanel.add(Box.createHorizontalStrut(20));
        financePanel.add(transferBtn);

        // --- 3. Algorithmic Analysis ---
        JPanel algoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        algoPanel.setBackground(CARD_BG);
        algoPanel.setBorder(createStyledTitledBorder("3. Algorithmic Benchmarks & Analysis"));

        JButton mergeBtn = new ModernButton("Merge Sort", BTN_NEUTRAL);
        JButton quickBtn = new ModernButton("Quick Sort", BTN_NEUTRAL);
        JButton heapBtn = new ModernButton("Heap Sort", BTN_NEUTRAL);
        JButton binSearchBtn = new ModernButton("Binary Search", BTN_NEUTRAL);
        JButton graphBtn = new ModernButton("Run Complexity Graph", ACCENT_RED);

        algoPanel.add(mergeBtn); 
        algoPanel.add(quickBtn); 
        algoPanel.add(heapBtn);
        algoPanel.add(Box.createHorizontalStrut(15));
        algoPanel.add(binSearchBtn); 
        algoPanel.add(graphBtn);

        mainControlPanel.add(inputPanel); 
        mainControlPanel.add(financePanel); 
        mainControlPanel.add(algoPanel);
        add(mainControlPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---
        createBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Customer c = new Customer(nameField.getText(), id);
                Account a = new Account(id, c, Double.parseDouble(balanceField.getText()));
                accountTree.insert(a); customerHash.put(id, c);
                cachedGraphPanel = null;
                log("CREATED: " + a);
            } catch (Exception ex) { log("Error: Check input parameters."); }
        });

        searchBtn.addActionListener(e -> {
            try {
                long s = System.nanoTime();
                Account a = accountTree.search(Integer.parseInt(idField.getText()));
                if(a != null) {
                    log("FOUND: " + a + " (" + (System.nanoTime() - s) + " ns execution)");
                    printHistory(a);
                } else log("Account ID not found.");
            } catch (Exception ex) { log("Error: Invalid Account ID."); }
        });

        loadBtn.addActionListener(e -> {
            log("Generating 1,000 synthetic account records...");
            cachedGraphPanel = null;
            new Thread(() -> {
                FakeAccountGenerator.generateData(accountTree, customerHash, 1000);
                SwingUtilities.invokeLater(() -> log("Data Population Complete! (~15,000 transactions initialized)"));
            }).start();
        });

        saveBtn.addActionListener(e -> {
            try {
                java.io.File file = new java.io.File("bank_data.csv");
                java.io.FileWriter w = new java.io.FileWriter(file);
                java.time.LocalDateTime now = java.time.LocalDateTime.now();
                
                w.write("# BANKING SYSTEM DATA EXPORT\n");
                w.write("# Generated: " + now.toString() + "\n");
                w.write("ID,Name,Balance\n");
                
                int count = 0;
                for (Account a : accountTree.getAllAccounts()) {
                    w.write(a.getAccountNumber() + "," + a.getCustomer().getName() + "," + a.getBalance() + "\n");
                    count++;
                }
                w.close(); 
                log("EXPORT SUCCESS: " + count + " account records written to bank_data.csv");
            } catch (Exception ex) { 
                log("Error: Export failed - " + ex.getMessage()); 
            }
        });
        loanBtn.addActionListener(e -> {
            Account a = getAccount();
            if(a == null) return;
            try {
                double val = Double.parseDouble(amountField.getText());
                a.takeLoan(val);
                log("LOAN APPROVED: $" + val + " disbursed. Remaining Debt: $" + String.format("%.2f", a.getDebt()));
            } catch(Exception ex) { log("Error: Invalid Amount."); }
        });

        payDebtBtn.addActionListener(e -> {
            Account a = getAccount();
            if(a == null) return;
            try {
                double val = Double.parseDouble(amountField.getText());
                if(a.payOffDebt(val)) log("DEBT PAYMENT SUCCESSFUL: $" + val + ". Remaining Debt: $" + String.format("%.2f", a.getDebt()));
                else log("Payment Declined: Insufficient Account Balance.");
            } catch(Exception ex) { log("Error: Invalid Amount."); }
        });

        transferBtn.addActionListener(e -> {
            Account sender = getAccount();
            if(sender == null) {
                log("Error: Search for a SENDER account ID first.");
                return;
            }
            try {
                double amount = Double.parseDouble(amountField.getText());
                String input = JOptionPane.showInputDialog(this, "Enter Receiver Account ID:");
                if(input == null) return;

                int targetId = Integer.parseInt(input);
                if (targetId == sender.getAccountNumber()) {
                    log("Error: Self-transfer prohibited.");
                    return;
                }

                long startSearch = System.nanoTime();
                Account receiver = accountTree.search(targetId);
                long searchTime = System.nanoTime() - startSearch;

                if (receiver != null) {
                    boolean success = sender.transferTo(receiver, amount);
                    if (success) {
                        log("--- TRANSFER COMPLETED ---");
                        log("Sender ID: " + sender.getAccountNumber() + " -> Receiver ID: " + receiver.getAccountNumber());
                        log("Amount: $" + amount + " | BST Lookup Time: " + searchTime + " ns");
                        printHistory(sender);
                    } else {
                        log("Transfer Failed: Insufficient funds.");
                    }
                } else {
                    log("Error: Receiver ID " + targetId + " not found.");
                }
            } catch (Exception ex) {
                log("Error: Check transfer parameters.");
            }
        });

        mergeBtn.addActionListener(e -> runSort("Merge Sort", list -> new MergeSort().sort(list)));
        quickBtn.addActionListener(e -> runSort("Quick Sort", list -> new QuickSort().sort(list)));
        heapBtn.addActionListener(e -> runSort("Heap Sort", list -> new HeapSort().sort(list)));

        binSearchBtn.addActionListener(e -> {
            Account a = getAccount();
            if(a == null) return;
            try {
                long s = System.nanoTime();
                Transaction t = new BinarySearch().search(a.getHistory(), Double.parseDouble(amountField.getText()));
                if(t != null) log("TRANSACTION FOUND: " + t); else log("Transaction value not found in history.");
                log("Search completed in: " + (System.nanoTime() - s) + " ns");
            } catch(Exception ex) { log("Error: Invalid search amount."); }
        });

        graphBtn.addActionListener(e -> {
            ArrayList<Account> accounts = accountTree.getAllAccounts();
            if (accounts.isEmpty()) { log("Load dataset before initiating benchmarks!"); return; }
            if (cachedGraphPanel != null && cachedDataSize == accounts.size()) {
                showGraphWindow(cachedGraphPanel); return;
            }
            log("Running Algorithm Complexity Analysis...");
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

                    long mT = 0, qT = 0, hT = 0, bT = 0;
                    for(int r = 0; r < 5; r++) {
                        ArrayList<Transaction> c1 = new ArrayList<>(sub); long t1 = System.nanoTime(); new MergeSort().sort(c1); mT += (System.nanoTime() - t1);
                        ArrayList<Transaction> c2 = new ArrayList<>(sub); long t2 = System.nanoTime(); new QuickSort().sort(c2); qT += (System.nanoTime() - t2);
                        ArrayList<Transaction> c3 = new ArrayList<>(sub); long t3 = System.nanoTime(); new HeapSort().sort(c3); hT += (System.nanoTime() - t3);
                        long t4 = System.nanoTime(); accountTree.search(accounts.get(0).getAccountNumber()); bT += (System.nanoTime() - t4);
                    }
                    mergeT.add(mT / 5); quickT.add(qT / 5); heapT.add(hT / 5); bstT.add(bT / 5);
                }
                cachedGraphPanel = new ComplexityGraphPanel(sizes, bstT, mergeT, quickT, heapT);
                cachedDataSize = accounts.size();
                SwingUtilities.invokeLater(() -> showGraphWindow(cachedGraphPanel));
            }).start();
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    private JTextField createStyledTextField(int columns) {
        JTextField tf = new JTextField(columns);
        tf.setBackground(new Color(15, 23, 42));
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105), 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        return tf;
    }

    private CompoundBorder createStyledTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(51, 65, 85), 1), title);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        border.setTitleColor(new Color(148, 163, 184));
        return BorderFactory.createCompoundBorder(border, new EmptyBorder(5, 5, 5, 5));
    }

    private void showGraphWindow(JPanel panel) {
        JFrame f = new JFrame("Algorithm Complexity Analysis");
        f.setSize(900, 600); f.add(panel); f.setLocationRelativeTo(null); f.setVisible(true);
    }

    private Account getAccount() {
        try { return accountTree.search(Integer.parseInt(idField.getText())); } catch (Exception e) { return null; }
    }

    private void log(String s) { 
        displayArea.append(" > " + s + "\n"); 
        displayArea.setCaretPosition(displayArea.getDocument().getLength()); 
    }

    private void printHistory(Account acc) {
        displayArea.append(" --- Transaction History [Acc ID: " + acc.getAccountNumber() + "] ---\n");
        int count = 0;
        for(Transaction t : acc.getHistory()) {
            displayArea.append(String.format("   [Amount: $%.2f] | Type: %s\n", t.getAmount(), t.getType()));
            count++;
            if(count >= 15) {
                displayArea.append("   ... (" + (acc.getHistory().size() - 15) + " hidden entries)\n");
                break;
            }
        }
        displayArea.append(" ----------------------------------------\n");
    }

    private interface Sorter { void sort(ArrayList<Transaction> list); }
    private void runSort(String n, Sorter s) {
        Account a = getAccount();
        if(a != null) {
            long st = System.nanoTime();
            s.sort(a.getHistory());
            log(n + " execution time: " + (System.nanoTime() - st) + " ns");
            printHistory(a);
        }
    }
}