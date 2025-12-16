package bankingStructures;

import bankingClasses.Account;

public class BinarySearchTree {

    // The Node class is the building block of the tree
    private class Node {
        Account data;
        Node left;
        Node right;

        Node(Account data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    // INSERTION
    public void insert(Account newAccount) {
        root = insertRec(root, newAccount);
    }

    // Recursive helper method
    private Node insertRec(Node current, Account newAccount) {
        // If the tree/subtree is empty, return a new node
        if (current == null) {
            return new Node(newAccount);
        }

        // Compare account numbers to decide Left or Right
        if (newAccount.getAccountNumber() < current.data.getAccountNumber()) {
            current.left = insertRec(current.left, newAccount);
        } else if (newAccount.getAccountNumber() > current.data.getAccountNumber()) {
            current.right = insertRec(current.right, newAccount);
        }


        return current;
    }

    // SEARCH LOGIC
    public Account search(int accountNumber) {
        return searchRec(root, accountNumber);
    }

    private Account searchRec(Node root, int target) {
        // Base Case: Root is null or Key is present at root
        if (root == null) {
            return null; // Account not found
        }
        if (root.data.getAccountNumber() == target) {
            return root.data;
        }

        // Val is greater than root's key. Go Left
        if (target < root.data.getAccountNumber()) {
            return searchRec(root.left, target);
        }

        // Val is less than root's key -> Go Right
        return searchRec(root.right, target);
    }


    public void printInOrder() {
        printInOrderRec(root);
    }

    private void printInOrderRec(Node root) {
        if (root != null) {
            printInOrderRec(root.left);
            System.out.println(root.data); // Prints the Account.toString()
            printInOrderRec(root.right);
        }
    }
    public java.util.ArrayList<Account> getAllAccounts() {
        java.util.ArrayList<Account> list = new java.util.ArrayList<>();
        collectAccounts(root, list);
        return list;
    }
    private void collectAccounts(Node node, java.util.ArrayList<Account> list) {
        if (node != null) {
            collectAccounts(node.left, list);
            list.add(node.data);
            collectAccounts(node.right, list);
        }
    }
}