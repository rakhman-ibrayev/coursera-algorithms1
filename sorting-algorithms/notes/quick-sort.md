## Quick sort

### General logic
Find pivot index which satisfies the following conditon: [elements <= pivot < elements] and mutate the array accordingly -> recursively apply the above logic to two parts of the array: before the pivot index and after the pivot index.  

### Implementation

```java
public class QuickSort {
    private static int partition (Comparable[] a, int low, int high) {
        Comparable pivot = a[high];
        int index = low - 1;

        for (int i = low; i < high; i++) {
            if (lessOrEqual(a[i], pivot)) {
                index++;
                exch(a, i, index);
            }
        }

        index++;
        a[high] = a[index];
        a[index] = pivot;

        return index;
    }

    private static void sort(Comparable[] a, int low, int high) {
        if (low >= high) return;

        int pivotIndex = partition(a, low, high);
        sort(a, low, pivotIndex - 1);
        sort(a, pivotIndex + 1, high);
    }

    public static void sort (Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static boolean lessOrEqual (Comparable v, Comparable w) {
        return v.compareTo(w) <= 0;
    }

    private static void exch (Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
```

### Algorithm performance
#### Time: O(NlogN) (O(n^2) in extremely rare scenarios)
#### Space: O(N)
#### Stability: not stable