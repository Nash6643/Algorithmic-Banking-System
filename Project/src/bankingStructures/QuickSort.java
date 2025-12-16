package bankingStructures;
import bankingClasses.Transaction;
import java.util.ArrayList;

public class QuickSort {
    public ArrayList<Transaction> sort(ArrayList<Transaction> list) {
        if (list == null || list.isEmpty()) return list;
        quickSort(list, 0, list.size() - 1);
        return list;
    }

    private void quickSort(ArrayList<Transaction> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    private int partition(ArrayList<Transaction> list, int low, int high) {
        Transaction pivot = list.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (list.get(j).getAmount() <= pivot.getAmount()) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private void swap(ArrayList<Transaction> list, int i, int j) {
        Transaction temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}