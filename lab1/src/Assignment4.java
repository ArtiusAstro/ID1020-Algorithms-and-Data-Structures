import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  Implement a generic iterable circular linked list which allows
 *  the user to insert and remove elements to/from the front
 *  and back end of the queue.
 *
 *  @author Ayub Atif
 */

class CircularQueue<T> implements Iterable{
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

    public CircularQueue(){
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
            tail.next = head;
            head.prev = tail;
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
            tail.next = head;
            head.prev = tail;
        }
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
        int i = this.size-1;
        if(!this.isEmpty()) {
            for (Object item : this) {
                s.append('[').append(item).append("]");
                if(i-- > 0) {
                    s.append(", ");
                }
            }
        }
        else{
            s.append("[ ]");
        }
        return s.toString();
    }

    public static void main(String a[]){

        CircularQueue<Character> circularList = new CircularQueue<>();

        /* Collect the input from input.txt */
        String dir = System.getProperty("user.dir");
        try (FileReader inputStream = new FileReader(dir+"/input.txt")) {
            int stream;
            System.out.print("Input is: ");
            while ((stream = inputStream.read()) != -1) {
                System.out.write(stream); // display input
                circularList.addLast((char)stream); // push chars to stack
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        // testing the circularity of the list
        System.out.println("\nThe Circle: "+circularList);
        System.out.println("The HEAD: "+circularList.head.item);
        System.out.println("The before tail: "+circularList.tail.prev.item);
        System.out.println("The after tail: "+circularList.tail.next.item);
        System.out.println("The TAIL: "+circularList.tail.item+"\n");

        int i;
        System.out.println("\nIteratively removing first from the list:\n"+circularList);
        try {
            for (i = circularList.size; i>0; i--) {
                circularList.removeFirst();
                System.out.println(circularList);
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }
}
