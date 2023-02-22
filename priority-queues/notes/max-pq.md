## Maximum priority queue

### Implementation with binary heap

```java
public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int n;

    public MaxPQ(int capacity) { 
        pq = (Key[]) new Comparable[capacity + 1]; 
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void insert(Key key) {
        pq[++n] = key;
        swim(n);
    }

    public Key max() {
        return pq[1];
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n + 1] = null;
        return max;
    }

    private void swim(int k) {
        // while node is not smaller than its parent
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2); // swap the node with its parent
            k = k / 2; // move up to keep traversing the heap
        }
    }

    private void sink(int k) {
        // while the left child is not at the end
        while (2 * k <= n) {
            int j = 2 * k; // take the child
            if (j < n && less(j, j + 1)) j++; // get the largest child
            if (!less(k, j)) break; // break if parent is not less than the largest child
            exch(k, j); // swap the parent with its largest child
            k = j; // move down to keep traversing the heap
        }
    }

    private boolean less(int i, int j) { 
        return pq[i].compareTo(pq[j]) < 0; 
    }

    private void exch(int i, int j) { 
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp; 
    }
}
```