import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class GraphX<Key extends Comparable<Key>> {
    int N;
    int E;
    final HashMapX<Key, Bag<Edge>> adjacencyLists = new HashMapX<>();
    final HashMapX<Key, HashMapX<Key, Integer>> shortestDistance = new HashMapX<>();

    final LIFOQueue<Edge> edgeSet = new LIFOQueue<>();

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
    public FIFOQueue<Key> keySet(){
        return adjacencyLists.getKeySet();
    }
    Bag<Edge> getEdges(Key state) {
        return this.adjacencyLists.get(state);
    }

    void addVertex(Key state) {
        if (this.adjacencyLists.containsKey(state))
            return;
        shortestDistance.put(state, new HashMapX<>());
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
    private final HashMapX<Key, HashMapX<Key, Boolean>> connected = new HashMapX<>();

    DiGraphX(){
        super();
    }

    public void establishConnections(){
        new DirectedDFS(this);
    }

    public boolean checkForConnection(Key start, Key goal) {
        return connected.get(start).get(goal);
    }

    private class DirectedDFS {
        HashMapX<Key, Boolean> marked;

        public DirectedDFS(DiGraphX G) {
            for (Key start : keySet()){
                marked = new HashMapX<>();
                connected.put(start, marked);
                for(Key goal : keySet())
                    marked.put(goal,false);
                dfs(G, start);
            }
        }
        private void dfs(DiGraphX G, Key v) {
            marked.put(v, true);
            Bag<Edge> edges = G.getEdges(v);
            for (Edge w : edges)
                if (!marked.get(w.getDst()))
                    dfs(G, w.getDst());
        }
    }

    public boolean kosarajuCycleChecker(){
        return new Kosaraju(this).hasCycle();
    }

    private class Kosaraju {
        final HashMapX<Key, Boolean> visited;
        final HashMapX<Key, Boolean> onStack;
        Boolean hasCycle;

        // Check DFS trees for cycle
        public Kosaraju(DiGraphX<Key> graphX){
            visited = new HashMapX<>();
            onStack = new HashMapX<>();
            hasCycle = false;

            for (Key key : graphX.keySet()){
                visited.put(key, false);
                onStack.put(key, false);
            }

            for (Key key : graphX.keySet())
                if (kosarajuHelper(graphX, key)) {
                    hasCycle = true;
                    return;
                }

            hasCycle = false;
        }

        private boolean kosarajuHelper(DiGraphX<Key> graphX, Key v) {

            // Mark the current node as visited and on stack

            if (onStack.get(v)) {
                return true;
            }
            if (visited.get(v)) {
                return false;
            }

            visited.put(v, true);
            onStack.put(v, true);

            for (Edge edge: graphX.getEdges(v))
                if (kosarajuHelper(graphX, edge.getDst()))
                    return true;

            onStack.put(v, false);
            return false;
        }

        public boolean hasCycle() {
            return hasCycle;
        }
    }

    public Iterable<Key> topologicalSort(){
        Topological top = this.new Topological(this);
        return (top.isDAG()) ? top.order : () -> new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }
            @Override
            public Key next() {
                return null;
            }
        };
    }

    private class Topological {
        private Iterable<Key> order; // topological order
        public Topological(DiGraphX<Key> G) {
            if (kosarajuCycleChecker()) return;
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
        public Iterable<Key> order() {
            return order;
        }
        public boolean isDAG() {
            return order != null;
        }

        private class DepthFirstOrder {
            private final HashMapX<Key,Boolean> marked;
            private final FIFOQueue<Key> pre; // vertices in preorder
            private final FIFOQueue<Key> post; // vertices in postorder
            private final LIFOQueue<Key> reversePost; // vertices in reverse postorder
            public DepthFirstOrder(DiGraphX<Key> G) {
                pre = new FIFOQueue<>();
                post = new FIFOQueue<>();
                reversePost = new LIFOQueue<>();
                marked = new HashMapX<>();
                for (Key key : G.keySet())
                    marked.put(key,false);
                for (Key key : G.keySet())
                    if (!marked.get(key)) dfs(G, key);
            }

            private void dfs(DiGraphX<Key> G, Key v) {
                pre.enqueue(v);
                marked.put(v, true);
                for (Edge w : G.getEdges(v))
                    if (!marked.get(w.getDst()))
                        dfs(G, w.getDst());
                post.enqueue(v);
                reversePost.push(v);
            }
            public Iterable<Key> pre() {
                return pre;
            }
            public Iterable<Key> post() {
                return post;
            }
            public Iterable<Key> reversePost() {
                return reversePost;
            }
        }
    }
}

class UnDiGraph<Key extends Comparable<Key>> extends GraphX<Key> {
    private final HashMapX<Key, HashMapX<Key, Key>> parents = new HashMapX<>(); // parents for pathing

    public UnDiGraph() {
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

    @Override
    void addVertex(Key state) {
        super.addVertex(state);
        parents.put(state, new HashMapX<>());
    }

    public LIFOQueue<Key> DFSPath(Key start, Key goal) {
        return (keySet().contains(start) && keySet().contains(goal)) ?
                new DFS(this, start).pathTo(goal) : new LIFOQueue("Disconnected src & dst");
    }
    private class DFS {
        private final HashMapX<Key, Boolean> marked;
        private final Key s; // source
        public DFS(UnDiGraph<Key> G, Key s) {
            marked = new HashMapX<>();
            for (Key key : G.keySet())
                marked.put(key, false);
            parents.put(s, new HashMapX<>());
            this.s = s;
            dfs(G, s);
        }
        private void dfs(UnDiGraph<Key> G, Key v) {
            marked.put(v, true);
            for (Edge w : G.getEdges(v))
                if (!marked.get(w.getDst())) {
                    parents.get(s).put(w.getDst(), v);
                    dfs(G, w.getDst());
                }
        }
        public boolean hasPathTo(Key v) {
            return marked.get(v);
        }
        public LIFOQueue<Key> pathTo(Key v) {
            if (!hasPathTo(v)) return null;
            LIFOQueue<Key> path = new LIFOQueue<>();
            for (Key x = v; x != s; x = parents.get(s).get(x))
                path.push(x);
            path.push(s);
            return path;
        }
    }

    public LIFOQueue<Key> BFShortsetNoWeight(Key start, Key goal){
        System.out.println("|"+start+"->"+goal+"|");
        if (!keySet().contains(start)) return new LIFOQueue<>();

        HashMapX<Key, Integer> distance = shortestDistance.get(start);
        for (Key key : keySet())
            if (distance.get(start)==null)
                distance.put(key, Integer.MAX_VALUE);
        distance.put(start, 0);

        HashMapX<Key, Key> pre = parents.get(start);
        boolean done = pre.get(goal) != null;

        System.out.println("Path already done: "+done);
        if (!done) {
            FIFOQueue<Key> toDoList = new FIFOQueue<>();
            Bag<Key> visited = new Bag<>();

            toDoList.enqueue(start);
            visited.add(start);

            while (!toDoList.isEmpty()) {
                start = toDoList.dequeue();
                if (start.equals(goal)) {
                    done = true;
                    break;
                }
                try {
                    for (Edge edge : getEdges(start))
                        if (!visited.contains(edge.getDst())) {
                            toDoList.enqueue(edge.getDst());
                            visited.add(edge.getDst());
                            pre.put(edge.getDst(), start);
                            distance.put(edge.getDst(), distance.get(start) + 1);
                        }
                } catch (NullPointerException e) { break; }
            }
        }
        if (done) {
            System.out.print("Shortest Distance: " + distance.get(goal) + "\nShortest Path: ");
            LIFOQueue<Key> path = new LIFOQueue<>();
            path.push(goal);
            while (pre.get(goal) != null) {
                path.push(pre.get(goal));
                goal = pre.get(goal);
            }
            return path;
        }
        System.out.println("Disconnected src & dst");
        return new LIFOQueue<>();
    }

    public LIFOQueue<Key> diskjtraShortestPath(Key start, Key goal){
        System.out.println("|"+start+"->"+goal+"|");
        if(shortestDistance.get(start)==null){
            System.out.println("Disconnected src & dst");
            return new LIFOQueue<>();
        }
        if(shortestDistance.get(start).get(goal)==null){
            System.out.println("Disconnected src & dst");
            return new LIFOQueue<>();
        }
        System.out.print("Shortest Distance: "+shortestDistance.get(start).get(goal)+"\nShortest Path: ");
        LIFOQueue<Key> path = new LIFOQueue<>();
        for(Key end = goal; end!=null; end = parents.get(start).get(end))
            path.push(end);
        return path;
    }

    public void disjkstra() {
        System.out.println("Multi source Dijkstra activated");
        for(Key start : keySet())
            disjkstra(start);
    }

    public void disjkstra(Key start) {
        if(!keySet().contains(start)) return;
        HashMapX<Key, Integer> srcDistances = shortestDistance.get(start);
        FIFOQueue<Key> priorityQueue = new FIFOQueue<>(); //FIFOQueue implemented priority queue

        for (Key key : keySet())
            srcDistances.put(key, Integer.MAX_VALUE);
        srcDistances.put(start, 0);
        priorityQueue.enqueue(start);

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

    public FIFOQueue<Edge> kruskalMST(){
        FIFOQueue<Edge> mst = new FIFOQueue<>();
        WQUF wquf = new WQUF(keySet().toArray());
        MinPQX<Edge> pq = new MinPQX<>(N);

        int i=0;
        for (Edge edge : edgeSet)
            if(i++%2==1)
                pq.insert(edge);

        Edge tmp = pq.delMin();
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            Key v = e.getSrc(), w = e.getDst();
            if (wquf.connected(v, w)){
                System.out.println("skipped "+e); continue;
            }
            wquf.union(v, w);
            System.out.println("added "+e); mst.enqueue(e);
        }
        Key v = tmp.getSrc(), w = tmp.getDst();
        if (wquf.connected(v, w)) return mst;

        return mst;
    }

    private class WQUF {
        private final HashMapX<Key, Key> comps = new HashMapX<>();
        private final HashMapX<Key, Integer> treeSizes = new HashMapX<>();

        WQUF(Key[] comps) {
            for (Key comp : comps) {
                this.comps.put(comp, comp);
                this.treeSizes.put(comp, 1);
            }
        }

        void union(Key leftComp, Key rightComp) {
            Key leftCompTree = find(leftComp);
            Key rightCompTree = find(rightComp);

            if (leftCompTree == rightCompTree) return;

            int leftTreeSize = treeSizes.get(leftCompTree);
            int rightTreeSize = treeSizes.get(rightCompTree);

            if (leftTreeSize < rightTreeSize) {
                comps.put(leftCompTree, rightCompTree);
                treeSizes.put(rightCompTree, leftTreeSize + rightTreeSize);
            } else {
                comps.put(rightCompTree, leftCompTree);
                treeSizes.put(leftCompTree, leftTreeSize + rightTreeSize);
            }
        }

        private Key find(Key comp) {
            // path compression
            for (Key u, v; (u = comps.get(comp)) != comp; comp = v)
                comps.put(comp, v = comps.get(u));
            return comp;
        }

        boolean connected(Key leftComp, Key rightComp) {
            return find(leftComp) == find(rightComp);
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

class LIFOQueue<Item> implements Iterable<Item>, Comparable {
    private int size;          // size of the stack
    private Node top;     // top of stack

    @Override
    public int compareTo(Object other) {
        return Integer.compare(this.size(),((LIFOQueue)other).size());
    }

    /**
     * A node holds an item and info on next node
     *
     * @author Ayub Atif
     */
    private class Node {
        private final Item item;
        private Node next;

        Node(Item item) {
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

    public LIFOQueue(Item def) {
        top = null;
        size = 0;
        this.push(def);
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
        if (!isEmpty())
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

    public boolean contains(Item dst) {
        for (Item item : this)
            if(item.equals(dst))
                return true;
        return false;
    }

    public Item[] toArray() {
        Item[] proxy = (Item[]) new Comparable[size];
        Node current = top;
        for (int i = 0; i < size; current = current.next)
            proxy[i++] = current.item;
        return proxy;
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
     * Returns a undirected vertex path representation of this stack.
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

    public int size() {
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

    public boolean contains(T dst){
        for (T item : this)
        if(item.equals(dst))
            return true;
        return false;
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