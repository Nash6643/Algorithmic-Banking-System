package bankingMain;

import bankingClasses.Account;
import bankingClasses.Customer;
import bankingClasses.Transaction;
import bankingStructures.BinarySearchTree;
import bankingStructures.MergeSort;
import bankingStructures.QuickSort;
import bankingStructures.HeapSort;

import java.util.ArrayList;
import java.util.Random;

public class ComplexityAnalysis {

    public static void main(String[] args) {
        System.out.println("--- STARTING ALGORITHMIC ANALYSIS ---");
        System.out.println("N (Size), BST Search (ns), Merge Sort (ns), Quick Sort (ns), Heap Sort (ns)");

        // We test for N = 100, 1000, 5000, 10000 as per your report
        int[] testSizes = {100, 500, 1000, 2500, 5000, 7500, 10000};

        for (int n : testSizes) {
            runTest(n);
        }

        System.out.println("--- ANALYSIS COMPLETE ---");
        System.out.println("Copy the data above into Excel to create your complexity graphs.");
    }

    private static void runTest(int n) {
        Random rand = new Random();

        // 1. SETUP DATA
        BinarySearchTree bst = new BinarySearchTree();
        ArrayList<Transaction> history = new ArrayList<>();

        // Create N dummy accounts and transactions
        for (int i = 0; i < n; i++) {
            // Add Account to BST
            Customer c = new Customer("User" + i, i);
            Account a = new Account(i, c, 1000);
            bst.insert(a);

            // Add Transaction to the list (for sorting tests)
            double amount = rand.nextDouble() * 1000;
            history.add(new Transaction("Deposit", amount));
        }

        // 2. MEASURE BST SEARCH (O(log n))
        // We search for a random ID that definitely exists to be fair
        int targetId = rand.nextInt(n);
        long startSearch = System.nanoTime();
        bst.search(targetId);
        long endSearch = System.nanoTime();
        long searchTime = endSearch - startSearch;

        // 3. MEASURE MERGE SORT (O(n log n))
        ArrayList<Transaction> listForMerge = new ArrayList<>(history); // Clone list
        long startMerge = System.nanoTime();
        new MergeSort().sort(listForMerge);
        long endMerge = System.nanoTime();
        long mergeTime = endMerge - startMerge;

        // 4. MEASURE QUICK SORT (O(n log n))
        ArrayList<Transaction> listForQuick = new ArrayList<>(history); // Clone list
        long startQuick = System.nanoTime();
        new QuickSort().sort(listForQuick);
        long endQuick = System.nanoTime();
        long quickTime = endQuick - startQuick;

        // 5. MEASURE HEAP SORT (O(n log n))
        ArrayList<Transaction> listForHeap = new ArrayList<>(history); // Clone list
        long startHeap = System.nanoTime();
        new HeapSort().sort(listForHeap);
        long endHeap = System.nanoTime();
        long heapTime = endHeap - startHeap;

        // PRINT ROW OF DATA
        System.out.printf("%d   ,    %d        ,    %d      ,   %d    ,        %d    %n", n, searchTime, mergeTime, quickTime, heapTime);
    }
}