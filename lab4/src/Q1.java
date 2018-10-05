import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Q1 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ1\n--------------------");

        GraphX<String> grX = new GraphX<>();
        String src; String dst;
        try(Scanner sc = new Scanner(new File("contiguous-usa.txt"))) {
            while(sc.hasNext()){
                src = sc.next();
                dst = sc.next();
                sc.nextLine();

                grX.addVertex(src);
                grX.addVertex(dst);
                grX.addEdge(src, dst);
            }
        }
        catch(InputMismatchException e) {
            e.printStackTrace();
        }

        grX.printGraph();
        System.out.println("\nN: "+grX.getN());
        System.out.println("E: "+grX.getE());

    }
}

class Bag<Item extends Comparable> implements Iterable<Item> {
    private Node first; // first node in list

    // construct bag from Iterable
    public Bag(Bag<Item> items) {
        for(Item item : items)
            add(item);
    }

    public Bag() {

    }

    private class Node {
        Item item;
        Node next;
    }
    public void add(Item item) {
        // same as push() in Stack
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public boolean contains(Item query){
        for (Item item : this){
            if(item.compareTo(query)==0)
                return true;
        }
        return false;
    }

    public Iterator<Item> iterator() { return new ListIterator(); }
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()
        { return current != null; }
        public void remove() { }
        public Item next()
        {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}

/**
 * ST that uses Sequential Search
 * holds Key value pairs for the hash table
 *
 * @author Ayub Atif
 */
class SequentialSearchST<Key extends Comparable, Value> implements Iterable{
    private Node head; // first node in the linked list
    private int size;
    private class Node { // linked-list node
        Key key;
        Value val;
        Node next;
        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public int getSize() {
        return size;
    }

    public Value get(Key key) {
        // Search for key, return associated value.
        for (Node x = head; x != null; x = x.next)
            if (key.equals(x.key))
                return x.val; // search hit
        return null; // search miss
    }

    public void put(Key key, Value val) {
        // Search for key. Update value if found; grow table if new.
        for (Node x = head; x != null; x = x.next)
            if (key.equals(x.key))
            { x.val.equals(val); return; } // Search hit: update val.
        head = new Node(key, val, head); // Search miss: add new node.
        size++;
    }

    /**
     * Iterates from head to tail
     *
     * @return iterator that goes from head to tail
     */
    @Override
    public Iterator iterator() {
        return new SeqSTIterator() {
        };
    }

    private class SeqSTIterator implements Iterator {
        Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Value next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Value value = current.val;
            current = current.next;
            return value;
        }
    }
}

/**
 * Hash table that uses separate chaining to handle collisions
 *
 * @author Ayub Atif
 */
class HashMapX<Key extends Comparable, Value> implements Iterable{
    private int N; // number of key-value pairs
    private int M; // hash table size

    private SequentialSearchST[] st; // array of ST objects
    public HashMapX() {
        this(67);
    }

    public HashMapX(int M) {
        this.M = M; // Create M linked lists.
        st = new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST();
        }
    }

    int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    
    public Value get(Key key) {
        return (Value) st[hash(key)].get(key);
    }
    
    public void put(Key key, Value val) {
        st[hash(key)].put(key, val);
        N++;
    }
    
    public boolean containsKey(Key word) {
        return get(word) != null;
    }

    /**
     * Iterates from head to tail
     *
     * @return iterator that goes from head to tail
     */
    @Override
    public Iterator iterator() {
        return new ListIterator() {
        };
    }

    public void delete(Key key) {
        put(key,null);
    }

    public int size() {
        return N;
    }

    private class ListIterator implements Iterator {
        private String word = "";

        public boolean hasNext() {
            return word != null;
        }

        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return null;
        }
    }
}

final class GraphX<Key extends Comparable> {
    private int N;
    private int E;
    private ArrayList<Key> keys = new ArrayList();
    private HashMapX<Key, Bag<Key>> adjacencyLists = new HashMapX<>();
    private SequentialSearchST<Key, Integer> kv = new SequentialSearchST<>();

    GraphX(){
        N=E=0;
    }

    public int size() {
        return this.adjacencyLists.size();
    }

    public int getN() {
        return N;
    }

    public int getE() {
        return E;
    }
    
    public Bag<Key> getEdges(Key state) {
        return this.adjacencyLists.get(state);
    }

    public void addVertex(Key state) {
        if (this.adjacencyLists.containsKey(state)) {
            return;
        }
        this.adjacencyLists.put(state, new Bag<>());
        keys.add(state);
        kv.put(state, kv.getSize());
        N++;
    }

    public void addEdge(Key src, Key dst) {
        if (!this.adjacencyLists.containsKey(src) || !this.adjacencyLists.containsKey(dst)) {
            throw new IllegalArgumentException();
        }
        if (this.adjacencyLists.get(src).contains(dst)){
            return;
        }
        this.adjacencyLists.get(src).add(dst);
        this.adjacencyLists.get(dst).add(src);
        E++;
    }

    /**
     * @return true if there is an edge from source -> destination
     */
    public boolean isEdge(Key src, Key dst) {
        if (!this.adjacencyLists.containsKey(src) || !this.adjacencyLists.containsKey(dst)) {
            throw new IllegalArgumentException();
        }
        return this.adjacencyLists.get(src).contains(dst) || this.adjacencyLists.get(dst).contains(src);
    }

    public void printGraph(){
        for(Key state : this.keys){
            System.out.print(state+":");
            for(Key edges : this.getEdges(state))
                System.out.print(" "+edges);
            System.out.println();
        }
    }
}

/**
 * My implementation of a generic iterable stack
 * The stack is a list of nodes that contain an Item and a reference to the next node
 *
 * @param <Item> generic data type
 */
class Queue<Item> implements Iterable<Item> {
    private int size;          // size of the stack
    private Node first;

    /**
     * A node holds an item and info on next node
     *
     * @author Ayub Atif
     */
    private class Node {
        private Item item;
        private Node next;

        Node(Item item) {
            this.item = item;
        }
    }

    /**
     * Initializes an empty stack.
     */
    public Queue() {
        first = null;
        size = 0;
    }

    /**
     * Returns true if this queue is empty.
     *
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this stack.
     *
     * @return the number of items in this stack
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item to this stack.
     *
     * @param item the item to add
     */
    public void queue(Item item) {
        Node tmp = first;
        first = new Node(item);
        first.next = tmp;
        size++;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;
        first = first.next;
        size--;
        return item;
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
        private Node current = first;

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