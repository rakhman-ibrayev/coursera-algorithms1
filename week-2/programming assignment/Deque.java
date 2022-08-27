import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // Private member variables
    private Node first, last;
    private int n;

    // Node class for the linked list implementation/
    private class Node {
        Node next;
        Node prev;
        Item item;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty()) { 
            last = first;
        } else { 
            oldFirst.prev = first;
        }

        ++n;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;

        if (isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack Underflow");
        }

        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = first;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack Underflow");
        }

        Item item = last.item;
        last = last.prev;

        n--;

        if (isEmpty()) first = last;
        else last.next = null;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        public Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Stack Underflow");
            }

            Item item = current.item;
            current = current.next;
            return item;
        } 
    }

    private String deckToString() {
        StringBuilder ss = new StringBuilder();

        for (Item s : this) {
            ss.append(s + " ");
        }

        return ss.toString();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deck = new Deque<Integer>();

        System.out.println("deck before adding: " + deck.deckToString());
        System.out.println("size before adding: " + deck.size());

        // Adding to the front:
        for (int i = 1; i <= 5; i++) {
            deck.addFirst(i);
            System.out.println("deck after adding to front: " + deck.deckToString());
            System.out.println("size after adding: " + deck.size());
        }


        // Adding to the back:
        for (int i = 6; i <= 10; i++) {
            deck.addLast(i);
            System.out.println("deck after adding to back: " + deck.deckToString());
            System.out.println("size after adding: " + deck.size());
        }


        // Removing elements:
        for (int i = 0; i < 5; i++) {
            System.out.println("Removed First Element: " + deck.removeFirst());
            System.out.println("deck: " + deck.deckToString());
        }

        for (int i = 0; i < 5; i++) {
            System.out.println("Removed Last Element: " + deck.removeLast());
            System.out.println("deck: " + deck.deckToString());
        }


        // Adding againg (As FAQ for this assignment recommends):
        for (int i = 1; i <= 5; i++) {
            deck.addFirst(i);
            System.out.println("deck after adding to front: " + deck.deckToString());
            System.out.println("size after adding: " + deck.size());
        }

        // Testing the iterator:
        int n = 5;

        Deque<Integer> queue = new Deque<Integer>();

        for (int i = 0; i < n; i++)
            queue.addLast(i);
        for (int a : queue) {
            for (int b : queue)
                System.out.print(a + "-" + b + " ");
                System.out.println();
        }
    }
}