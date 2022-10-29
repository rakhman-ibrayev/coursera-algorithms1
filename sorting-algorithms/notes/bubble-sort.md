## Bubble sort

### General logic

Swap the adjacent elements if they are in the wrong order.

### Implementation

```java
public class BubbleSort {
    public static void sort (Comparable[] a) {
        int N = a.length;

        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < N - 1 - i; j++) {
                if (less(a[j + 1], a[j])){
                    exch(a, j, j + 1);
                }
            }
        }
    }

    public static boolean less (Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
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