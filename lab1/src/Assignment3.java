
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * My implementation of a generic iterable double linked list
 * Each Node in the list holds a generic item and references to next node and previous node
 *
 * @param <T> is the generic data type
 */
class DoubleLinkedList<T> implements Iterable<T> {
    private Node head;
    private Node tail;
    private int size;

    /**
     * A node holds an item and info on prev or next
     *
     * @author Ayub Atif
     */
    private class Node {
        T item;
        Node next;
        Node prev;

        Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * init empty list
     */
    public DoubleLinkedList() {
        size = 0;
    }

    /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in this list.
     *
     * @return the number of items in this list
     */
    public int size() {
        return size;
    }

    /**
     * Adds item at start of list
     *
     * @param item to be added
     */
    public void addFirst(T item) {
        Node tmp = new Node(item, head, null);
        if(head != null ) {
            head.prev = tmp;
        }
        head = tmp;
        if(tail == null) {
            tail = tmp;
        }
        size++;
    }

    /**
     * Adds item at end of list
     *
     * @param item to be added
     */
    public void addLast(T item) {
        Node tmp = new Node(item, null, tail);
        if(tail != null) {
            tail.next = tmp;
        }
        tail = tmp;
        if(head == null) {
            head = tmp;
        }
        size++;
    }

    /**
     * Removes item from start of list
     */
    public void removeFirst() {
        if (size == 0){
            throw new NoSuchElementException();
        }
        if (size == 1){
            head = tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }
        size--;
    }

    /**
     * Removes item from end of list
     */
    public void removeLast() {
        if (size == 0){
            throw new NoSuchElementException();
        }
        if (size == 1){
            head = tail = null;
        }
        else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }

    /**
     *  Gets first item in list
     *
     * @return first item
     */
    public T getFirst(){
        if (size == 0){
            throw new NoSuchElementException();
        }
        return this.head.item;
    }

    /**
     * Iterates from head to tail
     *
     * @return iterator that goes from head to tail
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator() {
        };
    }

    private class ListIterator implements Iterator<T> {
        private Node node = head;

        public boolean hasNext() {
            return node != null;
        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            T item = node.item;
            node = node.next;
            return item;
        }
    }

    /**
     * Returns a string representation of this list.
     *
     * @return the sequence of items in this list from head to tail
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = this.size()-1;
        for (T item : this) {
            s.append('[').append(item).append(']');
            if(i-- > 0) {
                s.append(", ");
            }
        }
        return s.toString();
    }

    /**
     *
     *
     * @param args first argument should be the input filename
     */
    public static void main(String args[]){

        /* Despite its class being a proper DoubleLinkedList, queueFIFO tests only FIFO queue methods here */
        DoubleLinkedList<Character> queueFIFO = new DoubleLinkedList<>();

        /* Collect the input from input.txt */
        String dir = System.getProperty("user.dir");
        try (FileReader inputStream = new FileReader(dir+"/input.txt")) {
            int stream;
            System.out.print("Input is: ");
            while ((stream = inputStream.read()) != -1) {
                System.out.write(stream); // display input
                queueFIFO.addLast((char)stream); // push chars to stack
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        /* Iteratively remove the first element in the queue and print the updated queue */
        System.out.println("\nThe Queue: "+queueFIFO.toString()+"\n");
        try {
            while(!queueFIFO.isEmpty()) {
                char removed = queueFIFO.getFirst();
                queueFIFO.removeFirst();
                System.out.println("Mr." + removed + " has left the Queue: " + queueFIFO.toString());
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
