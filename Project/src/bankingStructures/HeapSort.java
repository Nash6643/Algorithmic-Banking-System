package bankingStructures;
import bankingClasses.Transaction;
import java.util.ArrayList;

public class HeapSort {
    public ArrayList<Transaction> sort(ArrayList<Transaction> list) {
        int n = list.size();
        for (int i = n / 2 - 1; i >= 0; i--) heapify(list, n, i);
        for (int i = n - 1; i > 0; i--) {
            swap(list, 0, i);
            heapify(list, i, 0);
        }
        return list; // Return the list
    }

    private void heapify(ArrayList<Transaction> list, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && list.get(l).getAmount() > list.get(largest).getAmount()) largest = l;
        if (r < n && list.get(r).getAmount() > list.get(largest).getAmount()) largest = r;
        if (largest != i) {
            swap(list, i, largest);
            heapify(list, n, largest);
        }
    }

    private void swap(ArrayList<Transaction> list, int i, int j) {
        Transaction temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}