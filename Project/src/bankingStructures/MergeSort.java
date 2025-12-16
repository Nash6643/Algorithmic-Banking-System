package bankingStructures;
import bankingClasses.Transaction;
import java.util.ArrayList;

public class MergeSort {
    public ArrayList<Transaction> sort(ArrayList<Transaction> list) {
        if (list == null || list.size() <= 1) return list;

        int mid = list.size() / 2;
        ArrayList<Transaction> left = new ArrayList<>();
        ArrayList<Transaction> right = new ArrayList<>();

        for (int i = 0; i < mid; i++) left.add(list.get(i));
        for (int i = mid; i < list.size(); i++) right.add(list.get(i));

        left = sort(left);
        right = sort(right);
        merge(list, left, right);
        return list; // Return the list
    }

    private void merge(ArrayList<Transaction> result, ArrayList<Transaction> left, ArrayList<Transaction> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getAmount() <= right.get(j).getAmount()) result.set(k++, left.get(i++));
            else result.set(k++, right.get(j++));
        }
        while (i < left.size()) result.set(k++, left.get(i++));
        while (j < right.size()) result.set(k++, right.get(j++));
    }
}