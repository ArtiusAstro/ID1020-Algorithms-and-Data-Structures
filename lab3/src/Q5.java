
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Q5{

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ5\n--------------------");

        HastST hashST = (HastST) ST.fillST(new HastST());
        int m = hashST.getM();
        int n = hashST.getN();
        int totalCollisions = (n<m) ? 0 : n-m;
        double[] hashes = new double[m];
        int hashCollision;
        double[] collisions = new double[m];
        SequentialSearchST[] st = hashST.getSt();

        System.out.println(n+" key-value pairs and "+totalCollisions+" collisions\nLEGEND = Hash: Collisions");
        for(int i=0;i<m;i++){ //Fill Chart and print spread
            hashCollision = st[i].getSize()-1;
            hashes[i]=i; collisions[i]=hashCollision;
            System.out.print(i+": "+hashCollision+", ");
            if (i%8==0) System.out.println();
        }

        XYChart chart = QuickChart.getChart("Hash function spread", "Hash", "Collisions", "spread", hashes, collisions); // Create Chart
        new SwingWrapper(chart).displayChart(); // Show it
    }
}

/**
 * Hash table that uses seperate chaining to handle collisions
 *
 * @author Ayub Atif
 */
class HastST extends ST implements Iterable{
    private int N; // number of key-value pairs
    private int M; // hash table size
    private SequentialSearchST[] st; // array of ST objects

    public int getM() {
        return M;
    }
    public int getN() { return N; }

    public HastST() {
        this(997);
    }
    public HastST(int M) {
        this.M = M; // Create M linked lists.
        st = new SequentialSearchST[M];
        for (int i = 0; i < M; i++)
            st[i] = new SequentialSearchST();
    }

    public SequentialSearchST[] getSt() {
        return st;
    }

    int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    @Override
    public int get(String key) {
        return st[hash(key)].get(key);
    }

    @Override
    public void put(String key, int val) {
        st[hash(key)].put(key, val);
        N++;
    }

    @Override
    public boolean contains(String word) {
        return get(word)!=-1;
    }

    public String[] keys(){
        return null;
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

/**
 * ST that uses Sequential Search
 * holds Key value pairs for the hash table
 *
 * @author Ayub Atif
 */
class SequentialSearchST implements Iterable{
    private Node head; // first node in the linked list
    private int size;
    private class Node { // linked-list node
        String key;
        int val;
        Node next;
        public Node(String key, int val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public int getSize() {
        return size;
    }

    public int get(String key) {
        // Search for key, return associated value.
        for (Node x = head; x != null; x = x.next)
            if (key.equals(x.key))
                return x.val; // search hit
        return -1; // search miss
    }

    public void put(String key, int val) {
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
        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            String key = current.key;
            current = current.next;
            return key;
        }
    }
}

/**
 * a list of methods used by all ST that use <String,Integer>
 *
 * @author Ayub Atif
 */
abstract class ST{

    public abstract int get(String key);

    public abstract void put(String key, int val);

    public abstract boolean contains(String word);

    public static ST fillST(ST st) throws FileNotFoundException {
        String word;
        Scanner sc = new Scanner(new File("98-0-filtered.txt"));
        while (sc.hasNextLine()) {
            Scanner sc2 = new Scanner(sc.nextLine());
            while (sc2.hasNext()){
                word = sc2.next();
                if (!st.contains(word)) st.put(word, 1);
                else st.put(word, st.get(word) + 1);
            }
            sc2.close();
        }
        sc.close();

        return st;
    }
}
