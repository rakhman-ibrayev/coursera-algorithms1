## Insertion sort

### General logic
Iterate through the input array -> check if the current element is less than the previous one -> if yes, sort the subarray [0...current element index], if not, keep iterating -> repeat the above steps.

### Implementation

```java
public class InsertionSort {
    public static void sort (Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (a[j] < a[j - 1]) {
                    exch(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    public static void exch (Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
```

### Algorithm performance
#### Time: O(n^2)
#### Space: O(1) (sorting in place).
#### Stability: stable