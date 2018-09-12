
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An IndexQueue is a generic iterable queue with which you can add or remove a node via an int index
 *
 * @param <T> generic data type
 */
class IndexQueue<T> extends AbstractQueue<T> {

    /**
     * add a node at an index with an item parameter
     *
     * @param index the index to be added to. The most recently added element has index 1;
     * @param item generic item to be added
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public void add(int index, T item) throws IndexOutOfBoundsException{
        int LIST_SIZE = this.size();
        int i;
        Node current = this.getHeadNode();
        Node newNode = new Node(item);

        if(index < 1 || index >= LIST_SIZE){
            throw new IndexOutOfBoundsException();
        }

        if(index == 1){
            newNode.setNext(current);
            this.setHeadNode(newNode);
        }

        for(i=1; i<LIST_SIZE - 1; i++){
            if(i == index-1) {
                newNode.setNext(current.getNext());
                current.setNext(newNode);
            }
            current = current.getNext();
        }

        this.setSize(this.size()+1);
    }

    /**
     *
     * @param index of the node to be removed. Most recently added node has index 1
     * @throws IndexOutOfBoundsException if index is out bounds
     */
    public void remove(int index) throws IndexOutOfBoundsException{
        int LIST_SIZE = this.size();
        int i;
        Node current = this.getHeadNode();

        if(index < 1 || index >= LIST_SIZE){
            throw new IndexOutOfBoundsException();
        }

        if(index == 1){
            this.setHeadNode(current.getNext());
        }

        for (i = 1; i < LIST_SIZE - 1; i++) {
            if (i == index-1) {
                current.setNext(current.getNext().getNext());
            }
            current = current.getNext();
        }

        this.setSize(this.size()-1);
    }
    
    public static void main(String[] args){
        IndexQueue<Character> indexQueue = new IndexQueue<Character>();

        /* Collect the input from input.txt */
        String dir = System.getProperty("user.dir");
        try (FileReader inputStream = new FileReader(dir+"/input.txt")) {
            int stream;
            System.out.print("Input is: ");
            while ((stream = inputStream.read()) != -1) {
                System.out.write(stream); // display input
                indexQueue.addLast((char)stream); // push chars to stack
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("\n\nAdd char x to the queue then remove it");
        System.out.println("\nThe Queue: "+indexQueue);

        indexQueue.add(1, 'x');
        System.out.println("\nThe Queue: "+indexQueue);

        indexQueue.remove(1);
        System.out.println("\nThe Queue: "+indexQueue);

    }
}

/**
 * basic queue
 *
 * @param <Item> generic data type
 */
class AbstractQueue<Item> implements Iterable<Item> {
    private int size;          // size of the stack
    private Node tail;          // tail of stack
    private Node head;

    /**
     * A node holds an item and info on next
     *
     * @author Ayub Atif
     */
    protected class Node {
        private Item item;
        private Node next;

        public Node(Item item) {
            this.item = item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node node) {
            this.next = node;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    /**
     * Initializes an empty stack.
     */
    public AbstractQueue() {
        size = 0;
    }

    public Node getHeadNode() {
        return head;
    }

    public void setHeadNode(Node head) {
        this.head = head;
    }

    /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the number of items in this stack.
     *
     * @return the number of items in this stack
     */
    public int size() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Adds the item to this queue.
     *
     * @param item the item to add
     */
    public void addLast(Item item) {
        Node tmp = new Node(item);

        if (head == null) {
            head = tmp;
        } else {
            if (head == tail) {
                head.next = tmp;
            } else {
                tail.next = tmp;
            }
        }
        tail = tmp;
        size++;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");
        Item item = head.item;        // save item to return
        head = head.next;            // delete tail node
        size--;
        return item;                 // return the saved item
    }

    /**
     * Returns the item at the tail of the stack (most recently added)
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item getFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return head.item;
    }

    public Item getLast() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return tail.item;
    }

    /**
     * Returns an iterator that LIFO iterates
     *
     * @return an iterator that LIFO iterates
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in this stack in LIFO order
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = this.size() - 1;
        for (Item item : this) {
            s.append('[').append(item).append(']');
            if (i-- > 0) {
                s.append(", ");
            }
        }
        return s.toString();
    }
}