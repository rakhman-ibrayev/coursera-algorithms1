## Selection sort

### General logic
    Iterate through the input array -> find the min value to the current element's right -> swap current and min value.

### How it works

    Start from the first element of the input array and find the minimum value to its right and once the value is found, swap minimum value and current element.

### Implementation

```java
public class SelectionSort {
	public static void sort (Comparable[] a) {
		int N = a.length;

		for (int i = 0; i < N; i++) {
			int min = i;
			for (int j = i + 1; j < N; j++) {
				if (less(a[j], a[min]) {
					min = j;
				}
			}
			exch(a, min, i);
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
####    Time: O(n^2)
####    Space: O(1) (sorting in place).
####    Stability: not stable