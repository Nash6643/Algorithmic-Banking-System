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
import javax.swing.border.EmptyBorder;

/**
 * Main Frame Dashboard for the Algorithmic Banking Management System.
 * Handles UI interactions, dual-structure indexing (Binary Search Tree & Hash Map),
 * and transaction execution logging.
 */

public class MainFrame extends JFrame {

    private static final Color PANEL_BG_LIGHT = new Color(245, 247, 250);
    private static final Color PANEL_BG_DARK = new Color(40, 44, 52);

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
        if (!displayArea.getFont().getFamily().equals("JetBrains Mono")) {
            displayArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        }
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
        
        // Jan 10 - Feature 3: Standardized Layout Gaps
inputPanel.setLayout(new java.awt.GridLayout(2, 4, 8, 8));
buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 8));
        idField = createStyledTextField(6); 
        nameField = createStyledTextField(10); 
        balanceField = createStyledTextField(8);
        amountField = createStyledTextField(8);

        idField.setToolTipText("Enter numeric Account ID (e.g. 101)");
        nameField.setToolTipText("Enter Customer Full Name");
        balanceField.setToolTipText("Enter initial balance amount (numeric)");
        amountField.setToolTipText("Enter transaction or loan amount");

        inputPanel.add(createStyledLabel("Account ID:")); inputPanel.add(idField);
        inputPanel.add(createStyledLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(createStyledLabel("Initial Bal ($):")); inputPanel.add(balanceField);

        JButton createBtn = new ModernButton("Create Account", ACCENT_BLUE);
        JButton searchBtn = new ModernButton("Search (BST)", BTN_NEUTRAL);
        JButton loadBtn = new ModernButton("Load 1k Data", ACCENT_PURPLE);
        JButton saveBtn = new ModernButton("Export (.csv)", ACCENT_GREEN);

        JButton resetBtn = new ModernButton("Reset Fields", ACCENT_RED);
        inputPanel.add(resetBtn);
        
        resetBtn.addActionListener(e -> {
            resetFields();
            log("Form fields cleared.");
        });

        inputPanel.add(createBtn); 
        inputPanel.add(searchBtn);
        inputPanel.add(Box.createHorizontalStrut(15)); 
        inputPanel.add(loadBtn); 
        inputPanel.add(saveBtn);

        // --- 2. Financial Services ---
        JPanel financePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        financePanel.setBackground(CARD_BG);
        financePanel.setBorder(createStyledTitledBorder("2. Financial Transactions"));

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
                Account a = getAccount();
                if (a != null) {
                    log("SEARCH FOUND: " + a.getAccountNumber() + " | Owner: " + a.getCustomer() + " | Balance: $" + a.getBalance());
                }
            } catch (Exception ex) {
                log("Error: BST Search failed - " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
        });

        loanBtn.addActionListener(e -> {
            if (!isPositiveNumber(amountField.getText())) {
                log("Error: Please enter a valid positive numeric amount.");
                return;
            }
            Account a = getAccount();
            if (a == null) return;
            double amount = Double.parseDouble(amountField.getText().trim());
            a.takeLoan(amount);
            log("SUCCESS: Loan of $" + amount + " approved for ID: " + a.getAccountNumber());
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

    private javax.swing.border.Border createStyledTitledBorder(String title) {
        return javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                title,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 12),
                ACCENT_BLUE
            ),
            javax.swing.BorderFactory.createEmptyBorder(6, 8, 6, 8)
        );
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
        displayArea.append("====================================================\n");
        displayArea.append(" [ACCOUNT STATEMENT] ID: " + acc.getAccountNumber() + " | Owner: " + acc.getCustomer() + "\n");
        displayArea.append("====================================================\n");
        
        int count = 0;
        if (acc.getHistory().isEmpty()) {
            displayArea.append("   [INFO] No recorded transactions available.\n");
        } else {
            for (Transaction t : acc.getHistory()) {
                count++;
                String formattedAmount = String.format("$%,10.2f", t.getAmount());
                displayArea.append(String.format("   #%02d | %-12s | Amount: %s | Status: OK\n", 
                    count, t.getType(), formattedAmount));
                if (count >= 15) {
                    displayArea.append("   ... (" + (acc.getHistory().size() - 15) + " older transactions truncated)\n");
                    break;
                }
            }
        }
        displayArea.append("----------------------------------------------------\n\n");
    }

    private void resetFields() {
        idField.setText("");
        nameField.setText("");
        balanceField.setText("");
        amountField.setText("");
        cachedGraphPanel = null;
        cachedDataSize = 0;
    }

    private boolean isPositiveNumber(String text) {
        if (text == null || text.trim().isEmpty()) return false;
        try {
            double val = Double.parseDouble(text.trim());
            return val >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Feature 2: Financial Risk & Analytics Model
private String calculateAccountMetrics(Account acc) {
    double balance = acc.getBalance();
    int totalTx = acc.getHistory().size();
    
    String riskLevel = "LOW";
    if (balance < 100 && totalTx > 10) {
        riskLevel = "HIGH (Low Balance & High Volatility)";
    } else if (balance < 500) {
        riskLevel = "MEDIUM";
    }
    
    return String.format("Risk Level: %s | Total Activity: %d transactions", riskLevel, totalTx);
}
// Jan 17 - Feature 2: Total Liquidity Aggregator
private double calculateTotalSystemLiquidity() {
    double total = 0.0;
    for (Account acc : accountTree.getAllAccounts()) {
        total += acc.getBalance();
    }
    return total;
}
// Jan 17 - Feature 1: High-Value Account Detector
private boolean isVIPAccount(Account acc) {
    if (acc == null) return false;
    return acc.getBalance() >= 10000.00;
}
// Feature 3: Modular Data Exporter Strategy
public interface DataExporter {
    void exportData(java.util.List<Account> accounts, java.io.File targetFile) throws Exception;
}

public class JSONDataExporter implements DataExporter {
    @Override
    public void exportData(java.util.List<Account> accounts, java.io.File targetFile) throws Exception {
        try (java.io.FileWriter w = new java.io.FileWriter(targetFile)) {
            w.write("[\n");
            for (int i = 0; i < accounts.size(); i++) {
                Account a = accounts.get(i);
                w.write(String.format("  {\"id\": %d, \"name\": \"%s\", \"balance\": %.2f}%s\n",
                    a.getAccountNumber(), a.getCustomer().getName(), a.getBalance(),
                    (i < accounts.size() - 1) ? "," : ""));
            }
            w.write("]\n");
        }
    }
}


// Feature 4: Memory Cache Layer for Rapid Access
private final java.util.Map<Integer, Account> accountCache = 
    new java.util.LinkedHashMap<Integer, Account>(16, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<Integer, Account> eldest) {
            return size() > 50; // Keep top 50 accounts cached in memory
        }
    };

private Account getCachedAccount(int accNum) {
    if (accountCache.containsKey(accNum)) {
        return accountCache.get(accNum);
    }
    Account a = accountTree.search(accNum);
    if (a != null) {
        accountCache.put(accNum, a);
    }
    return a;
}
    // Feature 1: Asynchronous Security Logger
private static class AuditLogger {
    private static final String AUDIT_FILE = "security_audit.log";
    
    public static void logEvent(String eventType, String details) {
        new Thread(() -> {
            try (java.io.PrintWriter out = new java.io.PrintWriter(new java.io.FileWriter(AUDIT_FILE, true))) {
                String timestamp = java.time.LocalDateTime.now().toString();
                out.printf("[%s] [%s] %s%n", timestamp, eventType, details);
            } catch (Exception ignored) {}
        }).start();
    }
}

// Jan 10 - Feature 2: Name Search Handler
private Account findAccountByName(String name) {
    if (name == null || name.trim().isEmpty()) return null;
    for (Account acc : accountTree.getAllAccounts()) {
        if (acc.getCustomer().toString().equalsIgnoreCase(name.trim())) {
            return acc;
        }
    }
    return null;
}

// Jan 10 - Feature 1: Account Profile Formatter
private String formatAccountSummary(Account acc) {
    if (acc == null) return "No account data available.";
    return String.format(
        "Account #%d | Owner: %s | Current Balance: $%.2f | Total Transactions: %d",
        acc.getAccountNumber(), acc.getCustomer(), acc.getBalance(), acc.getHistory().size()
    );
}
// Feature 5: Runtime Diagnostics & System Monitor
public void logSystemDiagnostics() {
    Runtime rt = Runtime.getRuntime();
    long totalMem = rt.totalMemory() / (1024 * 1024);
    long freeMem = rt.freeMemory() / (1024 * 1024);
    long usedMem = totalMem - freeMem;
    
    log(String.format("DIAGNOSTICS: Memory Used: %d MB | Free: %d MB | Total: %d MB", 
        usedMem, freeMem, totalMem));
}

// Feature 5: Real-time UI Status Indicator
private JLabel statusBar = new JLabel(" System Ready");

private void updateStatus(String statusText, boolean isError) {
    statusBar.setText(" Status: " + statusText);
    statusBar.setForeground(isError ? ACCENT_RED : new Color(40, 167, 69));
    log("STATUS: " + statusText);
}
// Feature 5: Numeric Input Restrictor Filter
private void restrictToNumericOnly(JTextField field) {
    ((javax.swing.text.AbstractDocument) field.getDocument()).setDocumentFilter(new javax.swing.text.DocumentFilter() {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            if (string != null && string.matches("[0-9.]*")) super.insertString(fb, offset, string, attr);
        }
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
            if (text != null && text.matches("[0-9.]*")) super.replace(fb, offset, length, text, attrs);
        }
    });
}

// Feature 5: Component Factory Helper
private JPanel createFormGroup(String labelText, JTextField field) {
    JPanel panel = new JPanel(new java.awt.BorderLayout(5, 0));
    panel.setOpaque(false);
    JLabel label = new JLabel(labelText);
    label.setFont(new Font("SansSerif", Font.BOLD, 12));
    panel.add(label, java.awt.BorderLayout.WEST);
    panel.add(field, java.awt.BorderLayout.CENTER);
    return panel;
}

// Jan 10 - Feature 4: Transaction Type Sanitizer
private String normalizeTransactionType(String rawType) {
    if (rawType == null) return "UNKNOWN";
    String clean = rawType.trim().toUpperCase();
    switch (clean) {
        case "DEP": return "DEPOSIT";
        case "WITH": return "WITHDRAWAL";
        case "LN": return "LOAN";
        default: return clean;
    }
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