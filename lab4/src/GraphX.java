import java.util.Iterator;
import java.util.NoSuchElementException;

public class GraphX<Key extends Comparable> {
    private int N;
    private int E;
    private HashMapX<Key, HashMapX<Key, Integer>> adjacencyLists = new HashMapX<>();

    GraphX(){
        N=E=0;
    }
    public int getN() {
        return N;
    }
    public int getE() {
        return E;
    }
    public HashMapX<Key, Integer> getEdges(Key state) {
        return this.adjacencyLists.get(state);
    }
    public void addVertex(Key state) {
        if (this.adjacencyLists.containsKey(state)) {
            return;
        }
        this.adjacencyLists.put(state, new HashMapX<>());
        N++;
    }
    public void addEdge(Key src, Key dst) {
        if (!this.adjacencyLists.containsKey(src) || !this.adjacencyLists.containsKey(dst))
            throw new NoSuchElementException();
        if (this.adjacencyLists.get(src).containsKey(dst))
            return;
        E++;
        this.adjacencyLists.get(src).put(dst, E);
        this.adjacencyLists.get(dst).put(src, E);
    }
    /**
     * @return true an edge between source and destination exists
     */
    public boolean isEdge(Key src, Key dst) {
        if (!this.adjacencyLists.containsKey(src) || !this.adjacencyLists.containsKey(dst))
            throw new NoSuchElementException();
        return this.adjacencyLists.get(src).containsKey(dst) || this.adjacencyLists.get(dst).containsKey(src);
    }
    public void printGraph(){
        for(Key state : adjacencyLists.getKeySet()){
            System.out.println(state+":");
            for(Key edge : this.getEdges(state).getKeySet())
                System.out.print(edge+" "+adjacencyLists.get(state).get(edge)+'\n');
            System.out.println();
        }
    }

    public void DFShortsetNoWeight(Key start, Key goal){
        System.out.println("DFS:");
        Bag<Key> visited = new Bag<>();
        LIFOQueue<Key> stack = new LIFOQueue<>();
        stack.push(start);
        int i=0;

        while (!stack.isEmpty() && !start.equals(goal)){
            start = stack.pop();
            if(!visited.contains(start))
            {
                System.out.println("visit #"+(++i)+": "+start);
                visited.add(start);
            }

            for (Key edge : getEdges(start).getKeySet()) {
                if (!visited.contains(edge))
                    stack.push(edge);
            }
        }
    }

    public boolean BFShortsetNoWeight(Key start, Key goal){
        System.out.println("BFS:");
        boolean connected = false;
        FIFOQueue<Key> toDoList = new FIFOQueue<>();
        Bag<Key> visited = new Bag<>();
        HashMapX<Key, Integer> distance = new HashMapX<>();
        for (Key key : adjacencyLists.getKeySet())
            distance.put(key, Integer.MAX_VALUE);
        HashMapX<Key, Key> pre = new HashMapX<>();

        toDoList.enqueue(start);
        visited.add(start);
        distance.put(start,0);
        int i=0;

        while(!toDoList.isEmpty()){
            start = toDoList.dequeue();
            System.out.println("visit #"+(++i)+": "+start);
            try {
                for (Key edge : getEdges(start).getKeySet())
                    if (!visited.contains(edge)) {
                        toDoList.enqueue(edge);
                        visited.add(edge);
                        pre.put(edge, start);
                        distance.put(edge,distance.get(start)+1);
                    }
            } catch (NullPointerException e) { break; }

            if(start.equals(goal)){
                connected=true;
                break;
            }
        }
        if(connected) {
            System.out.println("Shortest Distance: "+distance.get(goal));
            LIFOQueue<Key> path = new LIFOQueue<>();
            path.push(goal);
            while (pre.get(goal) != null) {
                path.push(pre.get(goal));
                goal = pre.get(goal);
            }
            System.out.println("Path: "+path.UnDiPath());
            return true;
        }
        else {
            System.out.println("Given src and dst are not connected");
            return false;
        }
    }

    public boolean BFShortsetWeighted(Key start, Key goal) {
        System.out.println("Dijkstra:");
        boolean connected = false;
        FIFOQueue<Key> toDoList = new FIFOQueue<>();
        Bag<Key> visited = new Bag<>();
        HashMapX<Key, Integer> distance = new HashMapX<>();
        for (Key key : adjacencyLists.getKeySet())
            distance.put(key, Integer.MAX_VALUE);
        HashMapX<Key, Key> pre = new HashMapX<>();
        FIFOQueue<Key> unsettled = new FIFOQueue<>();
        Key min = null;


        toDoList.enqueue(start);
        visited.add(start);
        distance.put(start,0);
        int i=0;
        
        while(!toDoList.isEmpty()){
            start = toDoList.dequeue();
            System.out.println("visit #"+(++i)+": "+start);
            try {
                for(Key key : getEdges(start).getKeySet())
                    unsettled.enqueue(key);
                while (!unsettled.isEmpty()) {
                    min = unsettled.getMin();
                    if (!visited.contains(min)) {
                        visited.add(min);
                        pre.put(min, start);
                        distance.put(min, distance.get(start) + 1);
                    }
                    unsettled.remove(min);
                }
            } catch (NullPointerException e) { break; }

            if(start.equals(goal)){
                connected=true;
            }
        }
        if(connected) {
            System.out.println("Shortest Distance: "+distance.get(goal));
            LIFOQueue<Key> path = new LIFOQueue<>();
            path.push(goal);
            while (pre.get(goal) != null) {
                path.push(pre.get(goal));
                goal = pre.get(goal);
            }
            System.out.println("Path: "+path.UnDiPath());
            return true;
        }
        else {
            System.out.println("Given src and dst are not connected");
            return false;
        }
    }
}

class HashMapX<Key extends Comparable, Value> {
    private int N; // number of key-value pairs
    private int M; // hash table size
    private FIFOQueue<Key> keySet = new FIFOQueue<>();
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
        keySet.enqueue(key);
        N++;
    }

    public void delete(Key key){
        st[hash(key)].put(key, null);
        keySet.remove(key);
        N--;
    }

    public boolean containsKey(Key word) {
        return get(word) != null;
    }

    public FIFOQueue<Key> getKeySet() {
        return keySet;
    }
}

class SequentialSearchST<Key, Value> implements Iterable{
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
            { x.val = val; return; } // Search hit: update val.
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

class Bag<Item> implements Iterable<Item> {
    private Node first; // first node in list

    public Bag() {
    }

    private class Node {
        Item item;
        Node next;

        public Node(Item item) {
            this.item = item;
        }
    }
    public void add(Item item) {
        Node tmp = first;
        first = new Node(item);
        first.next = tmp;
    }

    public boolean contains(Item query){
        for (Item item : this){
            if(item.equals(query))
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

class LIFOQueue<Item> implements Iterable<Item> {
    private int size;          // size of the stack
    private Node top;     // top of stack

    /**
     * A node holds an item and info on next node
     *
     * @author Ayub Atif
     */
    private class Node {
        private Item item;
        private Node next;

        Node(Item item){
            this.item = item;
        }
    }

    /**
     * Initializes an empty stack.
     */
    public LIFOQueue() {
        top = null;
        size = 0;
    }

    /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return top == null;
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
    public void push(Item item) {
        Node tmp = top;
        top = new Node(item);
        if(!isEmpty())
            top.next = tmp;
        size++;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = top.item;        // save item to return
        top = top.next;            // delete top node
        size--;
        return item;                   // return the saved item
    }


    /**
     * Returns the item at the top of the stack (most recently added)
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item getTop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return top.item;
    }

    /**
     * Returns an iterator that LIFO iterates
     *
     * @return an iterator that LIFO iterates
     */
    public Iterator<Item> iterator()  { return new ListIterator();  }

    private class ListIterator implements Iterator<Item> {
        private Node current = top;

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
        int i = this.size()-1;
        for (Item item : this) {
            s.append('[').append(item).append(']');
            if(i-- > 0) {
                s.append(", ");
            }
        }
        return s.toString();
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in this stack in LIFO order
     */
    public String UnDiPath() {
        StringBuilder s = new StringBuilder();
        int i = this.size() - 1;
        for (Item item : this) {
            s.append('[').append(item).append(']');
            if (i-- > 0) {
                s.append("-");
            }
        }
        return s.toString();
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in this stack in LIFO order
     */
    public String DiPath() {
        StringBuilder s = new StringBuilder();
        int i = this.size() - 1;
        for (Item item : this) {
            s.append('[').append(item).append(']');
            if (i-- > 0) {
                s.append("->");
            }
        }
        return s.toString();
    }
}

class FIFOQueue<Item extends Comparable> implements Iterable<Item> {
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
    }

    /**
     * Initializes an empty stack.
     */
    public FIFOQueue() {
        size = 0;
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

    /**
     * Adds the item to this toDoList.
     *
     * @param item the item to add
     */
    public void enqueue(Item item) {
        Node tmp = new Node(item);

        if (head == null)
            head = tmp;
        else
            tail.next = tmp;
        tail = tmp;
        size++;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");
        Item item = head.item;        // save item to return
        head = head.next;            // delete tail node
        size--;
        return item;                 // return the saved item
    }

    public void remove(Item item){
        int LIST_SIZE = this.size();
        int i;
        Node current = head;
        if (isEmpty()) throw new NoSuchElementException("Empty queue");

        if (head.item.equals(item))
            head=null;
        for (i = 0; i < LIST_SIZE - 1; i++) {
            if (current.next.item.equals(item)) {
                if (i!=LIST_SIZE-2) current.next = current.next.next;
                else current.next = null;
            }
            current = current.next;
        }
        size--;
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

    public Item getMin(){
        if (isEmpty()) return null;
        Item min = head.item;
        for(Item item : this){
            if (item.compareTo(min)<0)
                min=item;
        }
        return min;
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