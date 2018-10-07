import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class GraphX<Key extends Comparable<Key>> {
    int N;
    int E;
    HashMapX<Key, FIFOQueue<Edge>> adjacencyLists = new HashMapX<>();
    HashMapX<Key, HashMapX<Key, Integer>> shortestDistance = new HashMapX<>();
    HashMapX<Key, HashMapX<Key, Key>> parents = new HashMapX<>(); // for disjkrtas shortest path

    GraphX(){
        N=E=0;
    }
    public int getN() {
        return N;
    }
    public int getE() {
        return E;
    }
    public FIFOQueue<Edge> getEdges(Key state) {
        return this.adjacencyLists.get(state);
    }
    public void addVertex(Key state) {
        if (this.adjacencyLists.containsKey(state))
            return;
        shortestDistance.put(state, new HashMapX<>());
        parents.put(state, new HashMapX<>());
        this.adjacencyLists.put(state, new FIFOQueue<>());
        N++;
    }
    public void addEdge(Key src, Key dst) {
        if (!this.adjacencyLists.containsKey(src) || !this.adjacencyLists.containsKey(dst))
            throw new NoSuchElementException();
        if (isEdge(src, dst))
            return;
        E++;
        this.adjacencyLists.get(src).enqueue(new Edge(src, dst, E));
    }
    public boolean isEdge(Key src, Key dst) {
        if (!this.adjacencyLists.containsKey(src) || !this.adjacencyLists.containsKey(dst))
            throw new NoSuchElementException();
        return this.adjacencyLists.get(src).contains(new Edge(src, dst, 0));
    }

    public void printGraph(){
        for(Key state : adjacencyLists.getKeySet()){
            System.out.println(state+":");
            for(Key edge : this.getEdges(state).getKeySet())
                System.out.print(edge+" "+adjacencyLists.get(state).get(edge)+'\n');
            System.out.println();
        }
    }

    public LIFOQueue<Key> DFShortsetNoWeight(Key start, Key goal){
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
        return null;
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

    public void setShortest() {
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
                for (Key b : getEdges(a).getKeySet()) {
                    int distFromA = srcDistances.get(a) + getEdges(a).get(b);
                    if (distFromA < srcDistances.get(b)) {
                        priorityQueue.remove(b);
                        srcDistances.put(b, distFromA);
                        parents.get(start).put(b, a);
                        priorityQueue.enqueue(b);
                    }
                }
            }
        }
    }

    public void setShortest(Key start) {
        System.out.println("Single source Dijkstra activated");

        for (Key key : adjacencyLists.getKeySet())
            shortestDistance.get(start).put(key, Integer.MAX_VALUE);
        FIFOQueue<Key> priorityQueue = new FIFOQueue<>(); //implemented priority queue
        HashMapX<Key, Integer> srcDistances = shortestDistance.get(start);

        priorityQueue.enqueue(start);
        srcDistances.put(start, 0);

        while (!priorityQueue.isEmpty()) {
            priorityQueue.sort();
            Key a = priorityQueue.dequeue();
            for (Key b : getEdges(a).getKeySet()) {
                int distFromA = srcDistances.get(a) + getEdges(a).get(b);
                if (distFromA < srcDistances.get(b)) {
                    priorityQueue.remove(b);
                    srcDistances.put(b, distFromA);
                    parents.get(start).put(b, a);
                    priorityQueue.enqueue(b);
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

    public FIFOQueue<Key> KruskalMST(){
        FIFOQueue<Key> mst = new FIFOQueue<Key>();
        MinPQX pq = new MinPQX();
        for (Key key : adjacencyLists.getKeySet())
            pq.insert(key);
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
        while (!pq.isEmpty() && mst.size() < N-1) {
            Key v = pq.delMinSrc();
            Key w = pq.delMinDst();
            if (uf.connected(v, w)) continue; // Ignore ineligible edges.
            uf.union(v, w); // Merge components.
            mst.enqueue(v); // Add edge to mst.
            mst.enqueue(w);
        }
        return mst;
    }

    class Edge implements Comparable<Edge> {
        private final Key v; // one vertex
        private final Key w; // the other vertex
        private final double weight; // edge weight
        public Edge(Key v, Key w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }
        public double weight() {
            return weight;
        }
        public Key either() {
            return v;
        }
        public Key other(Key vertex) {
            if (vertex == v) return w;
            else if (vertex == w) return v;
            else throw new RuntimeException("Inconsistent edge");
        }
        public int compareTo(Edge that) {
            if (this.weight() < that.weight()) return -1;
            else if (this.weight() > that.weight()) return +1;
            else return 0;
        }

        @Override
        public boolean equals(Object edge){
            return ((Edge)edge).v.compareTo(v)==0 && ((Edge)edge).w.compareTo(w)==0;
        }
        public String toString() {
            return String.format("%d-%d %.2f", v, w, weight);
        }
    }

    class MinPQX implements Iterable<Key> {
        private Key[] pq;                    // store items at indices 1 to n
        private int n;                       // number of items on priority queue

        public MinPQX(int size) {
            pq = (Key[]) new Object[size + 1];
            n = 0;
        }

        public MinPQX() {
            Key[] keys =  adjacencyLists.getKeySet().toArray();
            n = keys.length;
            pq = (Key[]) new Object[n + 1];
            for (int i = 0; i < n; i++)
                pq[i+1] = keys[i];
            for (int k = n/2; k >= 1; k--)
                sink(k);
            assert isMinHeap();
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public Key min() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            return pq[1];
        }

        private void xSize(int newSize){
            Key[] temp = (Key[]) new Object[newSize];
            for (int i = 1; i <= n; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
        }

        public void insert(Key x) {
            // double size of array if necessary
            if (n == pq.length - 1) xSize(2 * pq.length);

            // add x, and percolate it up to maintain heap invariant
            pq[++n] = x;
            swim(n);
            assert isMinHeap();
        }

        /**
         * Removes and returns a smallest key on this priority queue.
         *
         * @return a smallest key on this priority queue
         * @throws NoSuchElementException if this priority queue is empty
         */
        public Edge delMin() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            Key min = pq[1];
            exch(pq, 1, n--);
            sink(1);
            pq[n+1] = null;     // to avoid loiterig and help with garbage collection
            if ((n > 0) && (n == (pq.length - 1) / 4)) xSize(pq.length / 2);
            assert isMinHeap();
            return min;
        }

        /***************************************************************************
         * Helper functions to restore the heap invariant.
         ***************************************************************************/

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

        /***************************************************************************
         * Helper functions for compares and swaps.
         ***************************************************************************/
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


        /**
         * Returns an iterator that iterates over the keys on this priority queue
         * in ascending order.
         * <p>
         * The iterator doesn't implement {@code remove()} since it's optional.
         *
         * @return an iterator that iterates over the keys in ascending order
         */
        public Iterator<Key> iterator() {
            return new HeapIterator();
        }

        private class HeapIterator implements Iterator<Key> {
            // create a new pq
            private MinPQX copy;

            // add all items to copy of heap
            // takes linear time since already in heap order so no keys move
            public HeapIterator() {
                copy = new MinPQX(size());
                for (int i = 1; i <= n; i++)
                    copy.insert(pq[i]);
            }

            public boolean hasNext()  { return !copy.isEmpty();                     }
            public void remove()      { throw new UnsupportedOperationException();  }

            public Key next() {
                if (!hasNext()) throw new NoSuchElementException();
                return copy.delMin();
            }
        }
    }

    class WeightedQuickUnionUF {
        private int[] id; // parent link (site indexed)
        private int[] sz; // size of component for roots (site indexed)
        private int count; // number of components
        public WeightedQuickUnionUF(int N) {
            count = N;
            id = new int[N];
            for (int i = 0; i < N; i++) id[i] = i;
            sz = new int[N];
            for (int i = 0; i < N; i++) sz[i] = 1;
        }
        public int count() {
            return count;
        }
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }
        private int find(int p) {
            // Follow links to find a root.
            while (p != id[p]) p = id[p];
            return p;
        }
        public void union(int p, int q) {
            int i = find(p);
            int j = find(q);
            if (i == j) return;
            // Make smaller root point to larger one.
            if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
            else { id[j] = i; sz[i] += sz[j]; }
            count--;
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
        this.adjacencyLists.get(dst).put(src, E);
    }
}

class HashMapX<Key extends Comparable<Key>, Value> {
    private int N; // number of key-value pairs
    private int M; // hash table size
    private FIFOQueue<Key> keySet = new FIFOQueue<>();
    private SequentialSearchST[] st; // array of ST objects

    public HashMapX() {
        this.M = 67; // Create M linked lists.
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
        for (int i=0;i<size;i++) {
            proxy[i] = dequeue();
            enqueue(proxy[i]);
        }
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
}