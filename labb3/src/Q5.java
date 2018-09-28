
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
        SeparateChainingHashST separateChainingHashST = new SeparateChainingHashST();
        separateChainingHashST = (SeparateChainingHashST) fillST(separateChainingHashST);

        int m = separateChainingHashST.getM();
        double[] index = new double[m];
        double[] frequencies = new double[m];
        SequentialSearchST[] st = separateChainingHashST.getSt();

        //Fill Chart
        for(int i=0;i<m;i++){
            index[i]=(double)i; frequencies[i]=(double)st[i].getSize();
            System.out.println(i+": "+separateChainingHashST.getSt()[i].getSize()); //display spread
        }
        // Create Chart
        XYChart chart = QuickChart.getChart("Hash function spread", "Hashes", "Occupants", "stuff", index, frequencies);
        // Show it
        new SwingWrapper(chart).displayChart();
    }

    private static ST fillST(ST st) throws FileNotFoundException {
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

class SeparateChainingHashST extends  ST implements Iterable{
    private int N; // number of key-value pairs
    private int M; // hash table size

    public int getM() {
        return M;
    }

    private SequentialSearchST[] st; // array of ST objects
    public SeparateChainingHashST() {
        this(997);
    }

    public SeparateChainingHashST(int M) {
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

class SequentialSearchST {
    private Node first; // first node in the linked list
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
        for (Node x = first; x != null; x = x.next)
            if (key.equals(x.key))
                return x.val; // search hit
        return -1; // search miss
    }

    public void put(String key, int val) {
        // Search for key. Update value if found; grow table if new.
        for (Node x = first; x != null; x = x.next)
            if (key.equals(x.key))
            { x.val = val; return; } // Search hit: update val.
        first = new Node(key, val, first); // Search miss: add new node.
        size++;
    }
}