/*##############################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: CircularList.java
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄      Compilation: javac CircularList.java
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷      Execution: java CircularList < input.txt
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷
⡙⠆⢀⣀⠤⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢸⣿⣿⣿⣿⣿⣿
⣷⣖⠋⠁⢀⢀⢀⢀⢀⢀⣀⣀⣄⢀⢀⢀⢀⢸⠏⣿⣿⣿⢿⣿      > Description
⣿⣷⡀⢀⢀⢀⢀⢀⡒⠉⠉⢀⢀⢀⢀⢀⢀⢈⣴⣿⣿⡿⢀⡿      Generic circular linked list
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
 *  Implement a generic iterable circular linked list which allows
 *  the user to insert and remove elements to/from the front
 *  and back end of the queue.
 *
 *  @author Ayub Atif
 */

public class CircularList<T> implements Iterable<T> {
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
    public CircularList() {
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

    private void circlify(){
        if(!isEmpty()) {
            head.prev = tail;
            tail.next = head;
        }
    }

    /**
     * Adds item at start of list
     *
     * @param item to be added
     */
    public void addFirst(T item) {
        Node tmp = new Node(item, head, tail);
        if(head != null ) {
            head.prev = tmp;
        }
        head = tmp;
        if(tail != null) {
            tail = tmp;
        }
        size++;
        circlify();
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
        circlify();
    }

    /**
     * Removes item from start of list
     */
    public void removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        if (size == 1){
            head = tail = null;
        }
        else {
            head = head.next;
            head.prev = tail;
        }
        size--;
        circlify();
    }

    /**
     * Removes item from end of list
     */
    public void removeLast() {
        if (size == 0) throw new NoSuchElementException();
        if (size == 1){
            head = tail = null;
        }
        else {
            tail = tail.prev;
            tail.next = head;
        }
        size--;
        circlify();
    }

    /**
     * It's your typical iterator
     *
     * @return iterator that goes from head to tail
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator(){
        };
    }

    private class ListIterator implements Iterator<T>{
        private Node current = head;
        private Node post = head.next;
        boolean visitingAgain = false;

        @Override
        public boolean hasNext() {
            if(visitingAgain && (post == head)){
                return false;
            }
            visitingAgain = true;
            return true;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            T item = current.item;
            current = post;
            post = post.next;
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

        /* General testing
        CircularList<Integer> circularList2 = new CircularList<>();
        circularList2.addFirst(31);
        circularList2.addFirst(139);
        System.out.println(circularList2.toString()); //139, 31
        circularList2.addLast(9);
        circularList2.addLast(99);
        circularList2.addLast(25);
        System.out.println(circularList2.toString());// 139, 31 , 9, 99, 25
        circularList2.removeFirst();
        System.out.println(circularList2.toString());//31 , 9, 99, 25
        circularList2.removeLast();
        System.out.println(circularList2.toString());//31 , 9, 99

        /* Despite its class being a proper CircularList, we can use only FIFO queue methods */
        CircularList<Integer> circularList = new CircularList<>();
        int i;

        for(i=0; i<3; i++){
            circularList.addLast(rand.nextInt(50));
        }

        System.out.println("\nThe Circle: "+circularList.toString());
        System.out.println("\nThe HEAD: "+circularList.head.item);
        System.out.println("\nThe TAIL: "+circularList.tail.item);

        System.out.println("\nThe 0: "+circularList.tail.next.item);
        System.out.println("\nThe 1: "+circularList.head.next.item);
        System.out.println("\nThe 2: "+circularList.head.prev.item);

        /*try {
            for (; i>0; i--) {
                circularList.removeFirst();
                System.out.println(circularList.toString());
            }
        }
        catch (NullPointerException e){
            System.out.println(e);
        }*/

    }
}
