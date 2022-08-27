import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // private member variables
    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        if (n == items.length) resize(2 * items.length);
        items[n++] = item;
    }
    
    private void resize(int capacity) {
        Item[] tempArray = (Item[]) new Object[capacity];
        
        for (int i = 0; i < n; i++) {
            tempArray[i] = items[i];
        }
        
        items = tempArray;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack Overflow");
        
        int randomIndex = StdRandom.uniform(n);
        Item item = items[randomIndex];
        items[randomIndex] = items[n - 1];
        items[n - 1] = null;
        n--;
        
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack Overflow");
        return items[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Item[] copy = (Item[]) new Object[items.length];
        private int copySize = n;
        
        public ListIterator() {
            for (int i = 0; i < items.length; i++) {
                copy[i] = items[i];
            }
        }
        
        @Override
        public boolean hasNext() {
            return copySize > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            int randomIndex = StdRandom.uniform(copySize);
            Item randomItem = copy[randomIndex];
            copy[randomIndex] = copy[copySize - 1];
            copy[copySize - 1] = null;
            copySize--;
            
            if (n > 0 && n == items.length / 4) resize(items.length / 2);
            
            return randomItem;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
    }
    
    private String queueToString() {
        StringBuilder ss = new StringBuilder();

        for (Item s : this) {
            ss.append(s + " ");
        }

        return ss.toString();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomq = new RandomizedQueue<>();
        
        // Adding to the queue
        for (int i = 1; i <= 5; i++) {
            randomq.enqueue(i);
        }
        
        // Print the queue
        System.out.println(randomq.queueToString());
        
        // Removing from the queue
        for (int i = 0; i < 3; i++) {
            System.out.println("Removed First Element: " + randomq.dequeue());
            System.out.println("deck: " + randomq.queueToString());
        }
    }
}