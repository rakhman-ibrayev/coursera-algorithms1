## Cycle sort

### General logic
This algorithm works only when the items of the integer array are given in the interval [1, n] where n is the size of the array. Therefore, we can iterate through the array and put the current element to its correct index in a linear time.

### Implementation

```java
public class CycleSort {
    public static void sort (int[] a) {
        int i = 0;

        while (i < a.length) {
            int correctIndex = a[i] - 1; // index at which a[i] needs to be

            // if current element is not where it should be
            // swap the elements at correctIndex and i
            if (a[i] != a[correctIndex]) {
                exch(a, i, correctIndex);
            } else {
                i++;
            }
        }
    }

    public static void exch (int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
```

### Algorithm performance
#### Time: O(n)
#### Space: O(1) (sorting in place).
#### Stability: stable