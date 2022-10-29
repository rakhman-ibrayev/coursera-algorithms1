## Merge sort

### General logic
Divide an array into two arrays of equal length -> recursively sort both of them -> combine (merge) two sorted arrays into one sorted array

### Implementation

```java
public class MergeSort {
    private static void merge (Comparable[] a, Comparable[] aux, int low, int mid, int high) {
        // copy to auxiliary array aux[]
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        // two pointers to keep track of two array parts
        // i starts at the beginning of the first part
        // j starts at the beginning of the second part
        int i = low, j = mid + 1;

        // merge back to original array a[]
        // 1) if either pointers go out of bounds for their parts,
        // get the remaining elements from the other part and put them in the a[]
        // 2) if the current element at either index is smaller,
        // put that element in a[] and increment that index
        for (int k = low; k <= high; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > high)
                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }

    private static void sort(Comparable[] a, Comparable[] aux, int low, int high) {
        if (low >= high) return;

        // calculate the middle index so that
        // it is integer-overflow-proof
        int mid = low + (high - low) / 2;

        // sort the two parts
        sort(a, aux, low, mid);
        sort(a, aux, mid + 1, high);

        // merge the two parts
        merge(a, aux, low, mid, high);
    }

    public static void sort (Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static boolean less (Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
}
```

### Algorithm performance
#### Time: O(NlogN)
#### Space: O(N)
#### Stability: stable