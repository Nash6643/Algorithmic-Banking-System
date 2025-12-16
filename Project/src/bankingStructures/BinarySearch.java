package bankingStructures;

import bankingClasses.Transaction;
import java.util.ArrayList;

public class BinarySearch {


    public Transaction search(ArrayList<Transaction> sortedList, double targetAmount) {
        int left = 0;
        int right = sortedList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            double midVal = sortedList.get(mid).getAmount();

            if (midVal == targetAmount) {
                return sortedList.get(mid);
            }

            if (midVal < targetAmount) {
                left = mid + 1; // Ignore left sub-Array
            } else {
                right = mid - 1; // Ignore right sub-Array
            }
        }

        return null;
    }
}