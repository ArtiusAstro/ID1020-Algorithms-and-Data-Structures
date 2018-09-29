import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Q6 {
    public static void main(String args[]) throws FileNotFoundException {
        System.out.println("--------------------\nQ6\n--------------------");

        RBST rbst = RBST.fillRBST();
        int i=0;

        try(Scanner sc = new Scanner(System.in)){
            System.out.print("Input word x to get where it occurs: ");
            LIFOQueue lifoQueue = rbst.get(sc.next());
            if (null!=lifoQueue) {
                for (Object location : lifoQueue) {
                    System.out.print(location + ", ");
                    if (i++ % 5 == 0) System.out.println();
                }
            }
            else System.out.println("\nSelected word isn't in the txt");
        }
        catch (InputMismatchException e){
            e.printStackTrace();
        }
    }
}

class RBST {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        String key; // key
        LIFOQueue val; // associated data
        Node left, right; // subtrees
        int N; // # nodes in this subtree
        boolean color; // color of link from
        Node(String key, LIFOQueue val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }
    
    public Node getRoot() {
        return root;
    }
    public LIFOQueue getNodeVal(Node node){
        return node.val;
    }
    public String getNodeKey(Node node){
        return node.key;
    }

    public LIFOQueue get(String key) {
        return get(root, key);
    }

    private LIFOQueue get(Node x, String key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        return x.val;
    }

    public void put(String key, int val) {
        // Search for key. Update value if found; grow table if new.
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, String key, int val) {
        if (h == null)// Do standard insert, with red link to parent.
            return new Node(key, new LIFOQueue(val), 1, RED);
        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val.Queue(val);
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public boolean contains(String key) {
        return get(key)!=null;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left)
                + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left)
                + size(h.right);
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public int size() {
        return size(root);
    }
    private int size(Node x) {
        return (x == null) ? 0 : x.N;
    }

    public static RBST fillRBST() throws FileNotFoundException {
        RBST rbst = new RBST();
        String word;
        int WORD_LOCATION = 0;

        Scanner sc = new Scanner(new File("98-0-filtered.txt"));
        while (sc.hasNextLine()) {
            Scanner sc2 = new Scanner(sc.nextLine());
            while (sc2.hasNext()){
                word = sc2.next();
                rbst.put(word, WORD_LOCATION++);
                WORD_LOCATION += word.length();
            }
            sc2.close();
        }
        sc.close();

        return rbst;
    }
}

class LIFOQueue implements Iterable{
    private Node head;
    private Node tail;
    private int size;

    /**
     * A node holds an item and info on prev or next
     *
     * @author Ayub Atif
     */
    private class Node {
        int location;
        Node next;
        public Node(int location) {
            this.location = location;
        }
    }

    public LIFOQueue(){
        this.size = 0;
    }

    public LIFOQueue(int location) {
        this.size = 0;
        Queue(location);

    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Adds item at end of list
     *
     * @param location to be added
     */
    public void Queue(int location) {
        Node node = new Node(location);

        if(isEmpty()) tail = head = node;
        else {
            if(size==1) head.next = node;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public Node Dequeue(){
        if(isEmpty()) return null;
        Node tmp = head;
        head = (size==1) ? null : head.next;
        size--;
        return tmp;
    }

    /**
     * Iterates starting from head and until you loop back to head
     *
     * @return iterator that goes from head to tail
     */
    @Override
    public Iterator<Integer> iterator() {
        return new LIFOIterator();
    }


    private class LIFOIterator implements Iterator<Integer>{
        private Node current = Dequeue();
        int i = 0;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Integer location = current.location;
            current = Dequeue();
            i++;
            return location;
        }
    }
}