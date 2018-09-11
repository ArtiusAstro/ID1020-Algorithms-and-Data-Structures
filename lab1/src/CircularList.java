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

public class CircularList<T> implements Iterable{
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

        Node(T item) {
            this.item = item;
        }
    }

    public CircularList(){
        this.size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Adds item at start of list
     *
     * @param item to be added
     */
    public void addFirst(T item) {
        Node node = new Node(item);

        if(isEmpty()){
            head = node;
            tail = node;
        }
        else{
            node.next = head;
            head.prev = node;
            head = node;
        }
        tail.next = head;
        head.prev = tail;
        size++;
    }

    /**
     * Adds item at end of list
     *
     * @param item to be added
     */
    public void addLast(T item) {
        Node node = new Node(item);

        if(isEmpty()){
            head = node;
            tail = node;
        }
        else{
            if(size == 1){
                head.next = node;
            }
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        tail.next = head;
        head.prev = tail;
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
        }
        tail.next = head;
        head.prev = tail;
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
        }
        tail.next = head;
        head.prev = tail;
        size--;
    }

    /**
     * Iterates starting from head and until you loop back to head
     *
     * @return iterator that goes from head to tail
     */
    @Override
    public Iterator<T> iterator() {
        return new CircularIterator();
    }


    private class CircularIterator implements Iterator<T>{
        private Node current = head;
        int i = 0;

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            T item = current.item;
            current = current.next;
            i++;
            return item;
        }
    }

    /**
     * Returns a string representation of this list.
     *
     * @return the sequence of items in this list from head to tail
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if(!this.isEmpty()) {
            for (Object item : this) {
                s.append('[').append(item).append("], ");
            }
        }
        else{
            s.append("[ ]");
        }
        return s.toString();
    }

    public static void main(String a[]) throws NullPointerException{

        Random rand = new Random();

        /* General testing */
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

        for(i=0; i<6*2; i++){
            circularList.addLast(rand.nextInt(50));
        }

        System.out.println("\nThe Circle: "+circularList.toString());
        System.out.println("The HEAD: "+circularList.head.item);
        System.out.println("The before tail: "+circularList.tail.prev.item);
        System.out.println("The after tail: "+circularList.tail.next.item);
        System.out.println("The TAIL: "+circularList.tail.item+"\n");

        try {
            for (; i>0; i--) {
                circularList.removeFirst();
                System.out.println(circularList.toString());
            }
        }
        catch (NullPointerException e){
            System.out.println(e);
        }

    }
}
