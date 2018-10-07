import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

abstract class GraphX<Key extends Comparable<Key>> {
    int N;
    int E;
    final HashMapX<Key, Bag<Edge>> adjacencyLists = new HashMapX<>();
    final HashMapX<Key, HashMapX<Key, Integer>> shortestDistance = new HashMapX<>();
    HashMapX<Key, HashMapX<Key, Key>> parents = new HashMapX<>(); // for disjkrtas shortest path
    LIFOQueue<Edge> edgeSet = new LIFOQueue<>();

    class Edge implements Comparable<Edge> {
        private final Key src; // one vertex
        private final Key dst; // the other vertex
        private final int weight; // edge weight
        Edge(Key src, Key dst, int weight) {
            this.src = src;
            this.dst = dst;
            this.weight = weight;
        }
        Key getDst() { return dst; }
        public Key getSrc() { return src; }
        int getWeight() { return weight; }
        Key either() {
            return src;
        }
        Key other(Key vertex) {
            if (vertex == src) return dst;
            else if (vertex == dst) return src;
            else throw new RuntimeException("Inconsistent edge");
        }
        public boolean equals(Edge edge){
            return edge.compareTo(this)==0 && edge.src.compareTo(src)==0
                    && edge.dst.compareTo(dst)==0;
        }
        @Override
        public int compareTo(Edge that) {
            return Integer.compare(this.getWeight(), that.getWeight());
        }
        @Override
        public String toString() {
            return String.valueOf(src) + "-" + dst + ": " + weight;
        }
    }

    GraphX(){
        N=E=0;
    }
    public int getN() {
        return N;
    }
    public int getE() {
        return E;
    }
    Bag<Edge> getEdges(Key state) {
        return this.adjacencyLists.get(state);
    }
    void addVertex(Key state) {
        if (this.adjacencyLists.containsKey(state))
            return;
        shortestDistance.put(state, new HashMapX<>());
        parents.put(state, new HashMapX<>());
        this.adjacencyLists.put(state, new Bag<>());
        N++;
    }
    void addEdge(Key src, Key dst) {
        if (!this.adjacencyLists.containsKey(src) || !this.adjacencyLists.containsKey(dst))
            throw new NoSuchElementException();
        if (isEdge(src, dst))
            return;
        E++;
        edgeSet.push(new Edge(src, dst, E));
        this.adjacencyLists.get(src).add(edgeSet.peek());
    }
    boolean isEdge(Key src, Key dst) {
        if (!this.adjacencyLists.containsKey(src) || !this.adjacencyLists.containsKey(dst))
            throw new NoSuchElementException();
        return this.adjacencyLists.get(src).contains(new Edge(src, dst, 0));
    }

    public void printGraph(){
        for(Key state : adjacencyLists.getKeySet()){
            System.out.println(state+":");
            for(Edge edge : this.getEdges(state))
                System.out.print(edge.dst+" "+edge.weight+'\n');
            System.out.println();
        }
    }

    public void fillGraph(String path) throws FileNotFoundException {
        try(Scanner sc = new Scanner(new File(path))) {
            Key src; Key dst;
            while(sc.hasNext()){
                src = (Key) sc.next();
                dst = (Key) sc.next();
                sc.nextLine();

                this.addVertex(src);
                this.addVertex(dst);
                this.addEdge(src, dst);
            }
        }
        catch(InputMismatchException e) {
            e.printStackTrace();
        }
    }
}

class DiGraphX<Key extends Comparable<Key>> extends GraphX<Key> {
    DiGraphX(){
        super();
    }
}

class UnDiGraph<Key extends Comparable<Key>> extends GraphX<Key> {
    public UnDiGraph(){
        super();
    }

    @Override
    public void addEdge(Key src, Key dst) {
        super.addEdge(src, dst);
        edgeSet.push(new Edge(dst, src, E));
        this.adjacencyLists.get(dst).add(edgeSet.peek());
    }

    @Override
    public boolean isEdge(Key src, Key dst) {
        if (!this.adjacencyLists.containsKey(src) || !this.adjacencyLists.containsKey(dst))
            throw new NoSuchElementException();
        return this.adjacencyLists.get(src).contains(new Edge(src, dst, 0)) || this.adjacencyLists.get(dst).contains(new Edge(dst, src, 0));
    }

    public LIFOQueue<Key> DFShortsetNoWeight(Key start, Key goal){
        System.out.println("|"+start+"->"+goal+"|");
        boolean connected = false;
        Bag<Key> visited = new Bag<>();
        LIFOQueue<Key> stack = new LIFOQueue<>();
        HashMapX<Key, Integer> distance = new HashMapX<>();
        for (Key key : adjacencyLists.getKeySet())
            distance.put(key, Integer.MAX_VALUE);
        
        stack.push(start);
        int i=0;

        while (!stack.isEmpty() && !start.equals(goal)){
            if((start = stack.pop()).equals(goal))
                connected = true;
            if(!visited.contains(start)) {
                System.out.println("visit #"+(++i)+": "+start);
                visited.add(start);
            }

            for (Edge edge : getEdges(start)) {
                if (!visited.contains(edge.getDst()))
                    stack.push(edge.getDst());
            }
        }
        if(connected) {
            System.out.print("Shortest Distance: "+distance.get(goal)+"\nShortest Path: ");
            LIFOQueue<Key> path = new LIFOQueue<>();
            path.push(goal);
            while (pre.get(goal) != null) {
                path.push(pre.get(goal));
                goal = pre.get(goal);
            }
            return path;
        }
        else {
            System.out.println("Disconnected src & dst");
            return new LIFOQueue<>();
        }
    }

    public LIFOQueue<Key> BFShortsetNoWeight(Key start, Key goal){
        System.out.println("|"+start+"->"+goal+"|");
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
        //int i=0;

        while(!toDoList.isEmpty()){
            start = toDoList.dequeue();
            //System.out.println("visit #"+(++i)+": "+start);
            try {
                for (Edge edge : getEdges(start))
                    if (!visited.contains(edge.getDst())) {
                        toDoList.enqueue(edge.getDst());
                        visited.add(edge.getDst());
                        pre.put(edge.getDst(), start);
                        distance.put(edge.getDst(),distance.get(start)+1);
                    }
            } catch (NullPointerException e) { break; }

            if(start.equals(goal)){
                connected=true;
                break;
            }
        }
        if(connected) {
            System.out.print("Shortest Distance: "+distance.get(goal)+"\nShortest Path: ");
            LIFOQueue<Key> path = new LIFOQueue<>();
            path.push(goal);
            while (pre.get(goal) != null) {
                path.push(pre.get(goal));
                goal = pre.get(goal);
            }
            return path;
        }
        else {
            System.out.println("Disconnected src & dst");
            return new LIFOQueue<>();
        }
    }

    public void disjkstra() {
        System.out.println("Multi source Dijkstra activated");
        for(Key start : adjacencyLists.getKeySet()) {

            for (Key key : adjacencyLists.getKeySet())
                shortestDistance.get(start).put(key, Integer.MAX_VALUE);
            FIFOQueue<Key> priorityQueue = new FIFOQueue<>(); //implemented priority queue
            HashMapX<Key, Integer> srcDistances = shortestDistance.get(start);

            priorityQueue.enqueue(start);
            srcDistances.put(start, 0);

            while (!priorityQueue.isEmpty()) {
                priorityQueue.sort();
                Key a = priorityQueue.dequeue();
                for (Edge b : getEdges(a)) {
                    int distFromA = srcDistances.get(a) + b.getWeight();
                    if (distFromA < srcDistances.get(b.getDst())) {
                        priorityQueue.remove(b.getDst());
                        srcDistances.put(b.getDst(), distFromA);
                        parents.get(start).put(b.getDst(), a);
                        priorityQueue.enqueue(b.getDst());
                    }
                }
            }
        }
    }

    public void disjkstra(Key start) {
        System.out.println("Single source Dijkstra activated");

        for (Key key : adjacencyLists.getKeySet())
            shortestDistance.get(start).put(key, Integer.MAX_VALUE);
        FIFOQueue<Key> priorityQueue = new FIFOQueue<>(); //FIFOQueue implemented priority queue
        HashMapX<Key, Integer> srcDistances = shortestDistance.get(start);

        priorityQueue.enqueue(start);
        srcDistances.put(start, 0);

        while (!priorityQueue.isEmpty()) {
            priorityQueue.sort();
            Key a = priorityQueue.dequeue();
            for (Edge b : getEdges(a)) {
                int distFromA = srcDistances.get(a) + b.getWeight();
                if (distFromA < srcDistances.get(b.getDst())) {
                    priorityQueue.remove(b.getDst());
                    srcDistances.put(b.getDst(), distFromA);
                    parents.get(start).put(b.getDst(), a);
                    priorityQueue.enqueue(b.getDst());
                }
            }
        }
    }

    public LIFOQueue<Key> getShortestPath(Key start, Key goal){
        System.out.println("|"+start+"->"+goal+"|");
        if(shortestDistance.get(start).get(goal)==null){
            System.out.println("Disconnected src & dst");
            return new LIFOQueue<>();
        }
        System.out.print("Shortest Distance: "+shortestDistance.get(start).get(goal)+"\nShortest Path: ");
        LIFOQueue<Key> path = new LIFOQueue<>();
        for(Key end = goal; end!=null; end = parents.get(start).get(end)){
            path.push(end);
        }

        return path;
    }

    public FIFOQueue<Edge> kruskalMST(){
        FIFOQueue<Edge> mst = new FIFOQueue<>();
        WQUF wquf = new WQUF(adjacencyLists.getKeySet().toArray());
        MinPQX<Edge> pq = new MinPQX<>(N);

        int i=0;
        for (Edge edge : edgeSet)
            if(i++%2==1)
                pq.insert(edge);

        Edge tmp = pq.delMin();
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            Key v = e.either(), w = e.other(v);
            if (wquf.connected(v, w)){
                System.out.println("continued "+e); continue;
            }
            wquf.union(v, w);
            System.out.println("added "+e); mst.enqueue(e);
        }
        Key v = tmp.either(), w = tmp.other(v);
        if (wquf.connected(v, w)){
            System.out.println("continued "+tmp); return mst;
        }
        System.out.println("added "+tmp); mst.enqueue(tmp);

        return mst;
    }

    class WQUF {
        private final HashMapX<Key, Key> components = new HashMapX<>();
        private final HashMapX<Key, Integer> treeSizes = new HashMapX<>();

        WQUF(Key[] components) {
            for (Key component : components) {
                this.components.put(component, component);
                this.treeSizes.put(component, 1);
            }
        }

        private int getTreeSize(Key component) {
            return treeSizes.get(component);
        }

        void union(Key leftComponent, Key rightComponent) {
            Key leftComponentTree = find(leftComponent);
            Key rightComponentTree = find(rightComponent);

            if (leftComponentTree == rightComponentTree) return;

            int leftTreeSize = getTreeSize(leftComponentTree);
            int rightTreeSize = getTreeSize(rightComponentTree);
            if (leftTreeSize < rightTreeSize) {
                components.put(leftComponentTree, rightComponentTree);
                treeSizes.put(rightComponentTree, leftTreeSize + rightTreeSize);
            } else {
                components.put(rightComponentTree, leftComponentTree);
                treeSizes.put(leftComponentTree, leftTreeSize + rightTreeSize);
            }
        }

        private Key find(Key component) {
            // Lookup with path compression
            for (Key u, v; (u = components.get(component)) != component; component = v)
                components.put(component, v = components.get(u));
            return component;
        }

        boolean connected(Key leftComponent, Key rightComponent) {
            return find(leftComponent) == find(rightComponent);
        }

    }
}

class HashMapX<Key extends Comparable<Key>, Value> {
    private int N; // number of key-value pairs
    private final int M; // hash table size
    private final FIFOQueue<Key> keySet = new FIFOQueue<>();
    private SequentialSearchST<Key, Value>[] st; // array of ST objects

    public HashMapX() {
        this.M = 67; // Create M linked lists.
        st = new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        return st[hash(key)].get(key);
    }

    public void put(Key key, Value val) {
        keySet.enqueue(key);
        st[hash(key)].put(key, val);
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
        final Key key;
        Value val;
        Node next;
        Node(Key key, Value val, Node next) {
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
        final Item item;
        Node next;

        Node(Item item) {
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
        private final Item item;
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
    int size() {
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
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return top.item;
    }

    public Item[] toArray(){
        Item[] proxy = (Item[]) new Comparable[size];
        Node current = top;
        for (int i=0;i<size;current=current.next)
            proxy[i++] = current.item;
        return proxy;
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

class FIFOQueue<Item extends Comparable<Item>> implements Iterable<Item> {
    private int size;          // size of the stack
    private Node tail;          // tail of stack
    private Node head;

    public boolean contains(Item dst) {
        for (Item item : this)
            if(item.equals(dst))
                return true;
        return false;
    }

    /**
     * A node holds an item and info on next
     *
     * @author Ayub Atif
     */
    class Node {
        private final Item item;
        private Node next;

        Node(Item item) {
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
    int size() {
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
        if (size<0) size=0;
        return item;                 // return the saved item
    }

    public void remove(Item item){
        int LIST_SIZE = this.size();
        int i;
        Node current = head;
        if (isEmpty()) return;

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

    public Item[] toArray(){
        Item[] proxy = (Item[]) new Comparable[size];
        Node current = head;
        for (int i=0;i<size;current=current.next)
            proxy[i++] = current.item;
        return proxy;
    }

    public void sort(){
        Item[] proxy = (Item[]) new Comparable[size];
        for (int i=0;i<size;i++)
            proxy[i] = dequeue();
        insertionSort(proxy);
        for(Item item : proxy)
            enqueue(item);
    }

    /**
     * insertion sort
     * @param a comparable array
     */
    private void insertionSort(Comparable[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++){
            for (int j = i; j > 0 && a[j].compareTo(a[j-1]) < 0; j--) {
                Comparable t = a[j]; a[j] = a[j-1]; a[j-1] = t;
            }
        }
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

    public String tree() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item).append('\n');
        }
        return s.toString();
    }
}

class MinPQX<T extends Comparable> implements Iterable<T> {
    private T[] pq;                    // store items at indices 1 to n
    private int n;                       // number of items on priority queue

    public MinPQX(int size) {
        pq = (T[]) new Comparable[size + 1];
        n = 0;
    }

    public MinPQX(T[] t) {
        n = t.length;
        pq = (T[]) new Comparable[n + 1];
        System.arraycopy(t, 0, pq, 1, n);
        for (int k = n/2; k >= 1; k--)
            sink(k);
        assert isMinHeap();
    }

    public boolean isEmpty() {
        return n == 0;
    }

    private int size() {
        return n;
    }

    public T min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    private void xSize(int newSize){
        T[] temp = (T[]) new Comparable[newSize];
        System.arraycopy(pq, 1, temp, 1, n);
        pq = temp;
    }

    public void insert(T x) {
        // double size of array if necessary
        if (n == pq.length - 1) xSize(2 * pq.length);

        // add x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);
        assert isMinHeap();
    }

    public T delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        T min = pq[1];
        exch(pq, 1, n--);
        sink(1);
        pq[n+1] = null;     // to avoid loitering and help with garbage collection
        if ((n > 0) && (n == (pq.length - 1) / 4)) xSize(pq.length / 2);
        assert isMinHeap();
        return min;
    }

    private void swim(int k) {
        while (k > 1 && more(k/2, k)) {
            exch(pq, k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && more(j, j+1)) j++;
            if (!more(k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private boolean more(Comparable v, Comparable w){
        return v.compareTo(w) > 0;
    }

    private void exch(Comparable[] a, int i, int j){
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    // is pq[1..N] a min heap?
    private boolean isMinHeap() {
        return isMinHeap(1);
    }

    // is subtree of pq[1..n] rooted at k a min heap?
    private boolean isMinHeap(int k) {
        if (k > n) return true;
        int left = 2*k;
        int right = 2*k + 1;
        if (left  <= n && more(k, left))  return false;
        if (right <= n && more(k, right)) return false;
        return isMinHeap(left) && isMinHeap(right);
    }

    public Iterator<T> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<T> {
        // create a new pq
        private final MinPQX<T> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        HeapIterator() {
            copy = new MinPQX<>(size());
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
}