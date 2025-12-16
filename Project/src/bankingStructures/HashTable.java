package bankingStructures;

import bankingClasses.Customer;

public class HashTable {


    private class HashNode {
        Integer key;
        Customer value;
        HashNode next;

        public HashNode(Integer key, Customer value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private HashNode[] buckets;
    private int capacity;
    private int size;

    public HashTable(int capacity) {
        this.capacity = capacity;
        this.buckets = new HashNode[capacity];
        this.size = 0;
    }


    private int getBucketIndex(Integer key) {
        return Math.abs(key.hashCode() % capacity);
    }


    public void put(Integer key, Customer value) {
        int bucketIndex = getBucketIndex(key);
        HashNode head = buckets[bucketIndex];


        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }


        size++;
        head = buckets[bucketIndex];
        HashNode newNode = new HashNode(key, value);
        newNode.next = head;
        buckets[bucketIndex] = newNode;
    }


    public Customer get(Integer key) {
        int bucketIndex = getBucketIndex(key);
        HashNode head = buckets[bucketIndex];


        while (head != null) {
            if (head.key.equals(key)) {
                return head.value; //
            }
            head = head.next;
        }
        return null;
    }
}