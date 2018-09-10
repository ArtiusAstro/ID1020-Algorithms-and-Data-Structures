/*##############################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: DoubleLinkedList.java
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄      Compilation: javac DoubleLinkedList.java
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷      Execution: java DoubleLinkedList < input.txt
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷
⡙⠆⢀⣀⠤⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢸⣿⣿⣿⣿⣿⣿
⣷⣖⠋⠁⢀⢀⢀⢀⢀⢀⣀⣀⣄⢀⢀⢀⢀⢸⠏⣿⣿⣿⢿⣿      > Description
⣿⣷⡀⢀⢀⢀⢀⢀⡒⠉⠉⢀⢀⢀⢀⢀⢀⢈⣴⣿⣿⡿⢀⡿      Generic Dbl linked list
⣿⣿⣷⣄⢀⢀⢀⢀⠐⠄⢀⢀⢀⠈⢀⣀⣴⣿⣿⣿⡿⠁⢀⣡
⠻⣿⣿⣿⣿⣆⠢⣤⣄⢀⢀⣀⠠⢴⣾⣿⣿⡿⢋⠟⢡⣿⣿⣿
⢀⠘⠿⣿⣿⣿⣦⣹⣿⣀⣀⣀⣀⠘⠛⠋⠁⡀⣄⣴⣿⣿⣿⣿
⢀⢀⢀⠈⠛⣽⣿⣿⣿⣿⣿⣿⠁⢀⢀⢀⣡⣾⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢀⢰⣿⣿⣿⣿⣿⣿⣿⣦⣤⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
###############################################################################*/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *  The {@code Stack} class represents a last-in-top-out (LIFO) stack of generic items.
 *  It supports the usual <em>push</em> and <em>pop</em> operations, along with methods
 *  for peeking at the top item, testing if the stack is empty, getting the number of
 *  items in the stack, and iterating over the items in LIFO order.
 *  <p>
 *  This implementation uses a singly-linked list with a nested class for
 *  linked-list nodes.
 *  The <em>push</em>, <em>pop</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *
 *  @author Ayub Atif
 */

public class DoubleLinkedList<T> implements Iterable<T> {
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
        return head == null && tail == null;
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
    public void enqueue(T item) {
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
    public void dequeue() {
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
     * It's your typical iterator
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


    public static void main(String a[]) throws NullPointerException{

        Random rand = new Random();

        /* General testing */
        DoubleLinkedList<Integer> doubleLinkedList = new DoubleLinkedList<>();
        doubleLinkedList.addFirst(31);
        doubleLinkedList.addFirst(139);
        System.out.println("Tested addFirst(): "+doubleLinkedList.toString()); // 139, 31
        doubleLinkedList.enqueue(9);
        doubleLinkedList.enqueue(99);
        doubleLinkedList.enqueue(25);
        System.out.println("Tested enqueue(): "+doubleLinkedList.toString()); //
        doubleLinkedList.dequeue();
        System.out.println("Tested dequeue(): "+doubleLinkedList.toString());
        doubleLinkedList.removeLast();
        System.out.println("Tested removeLast(): "+doubleLinkedList.toString());
        System.out.println("Tested getFirst(): "+doubleLinkedList.getFirst());

        /* Despite its class being a proper DoubleLinkedList, we can use only FIFO queue methods */
        DoubleLinkedList<Integer> queueFIFO = new DoubleLinkedList<>();
        int i;
        for(i=0; i<3*2; i++){
            queueFIFO.enqueue(rand.nextInt(50));
        }

        System.out.println("\nThe Queue: "+queueFIFO.toString());
        try {
            for (; i>0; i--) {
                int dequeued = queueFIFO.getFirst();
                queueFIFO.dequeue();
                System.out.println("Mr." + dequeued + " has left the Queue: " + queueFIFO.toString());
            }
        }
        catch (NullPointerException e){
            System.out.println(e);
        }
    }
}
