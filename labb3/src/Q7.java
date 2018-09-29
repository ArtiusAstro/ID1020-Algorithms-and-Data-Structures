
import org.knowm.xchart.Histogram;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Q7{

    public static void main(String[] args) throws FileNotFoundException {
        SeparateChainingHashST separateChainingHashST = (SeparateChainingHashST) ST.fillST(new SeparateChainingHashST());
        LinearProbingHashST linearProbingHashST = (LinearProbingHashST) ST.fillST(new LinearProbingHashST());

        //Fill Chain Chart
        int m = separateChainingHashST.getM();
        SequentialSearchST[] st = separateChainingHashST.getSt();

        ArrayList<Integer> hashes = new ArrayList<>();
        ArrayList<Integer> frequencies = new ArrayList<>();

        for(int i=0;i<m;i++){
            hashes.add(i); frequencies.add(st[i].getSize());
            System.out.print(i+": "+st[i].getSize()+", "); //display spread
            if (i%3==0) System.out.println();
        }
        // Create Chart
        Histogram histogram = new Histogram(frequencies, 10, 0, m);
        XYChart chart = QuickChart.getChart("Hash function spread", "Hashes", "Occupants", "spread", hashes, frequencies);
        // Show it
        new SwingWrapper(chart).displayChart();

        //Fill Probing Chart
        m = linearProbingHashST.getM();
        st = linearProbingHashST.getSt();

        hashes = new ArrayList<>();
        frequencies = new ArrayList<>();

        for(int i=0;i<m;i++){
            hashes.add(i); frequencies.add(st[i].getSize());
            System.out.print(i+": "+st[i].getSize()+", "); //display spread
            if (i%3==0) System.out.println();
        }
        // Create Chart
        histogram = new Histogram(frequencies, 10, 0, m);
        chart = QuickChart.getChart("Hash function spread", "Hashes", "Occupants", "spread", hashes, frequencies);
        // Show it
        new SwingWrapper(chart).displayChart();
    }

    /*
        // Test is to find the key with the highest frequency count.
        long start = System.currentTimeMillis();
        SeperateChainingTest(separateChainingHashST);
        long time = System.currentTimeMillis() - start;
        System.out.println("RedBlackBST time: "+time+"ms");
        start = System.currentTimeMillis();
        LinearProbingTest(linearProbingHashST);
        time = System.currentTimeMillis() - start;
        System.out.println("BST time: "+time+"ms");
    }

    private static void SeperateChainingTest(SeparateChainingHashST separateChainingHashST){
        System.out.println("MAX: "+separateChainingHashST.getNodeKey(separateChainingHashST.maxNode(separateChainingHashST.getRoot()))+" "+separateChainingHashST.getNodeVal(separateChainingHashST.maxNode(separateChainingHashST.getRoot())));
    }

    private static void LinearProbingTest(LinearProbingHashST linearProbingHashST){
        System.out.println("MAX: "+linearProbingHashST.getNodeKey(linearProbingHashST.maxNode(linearProbingHashST.getRoot()))+" "+linearProbingHashST.getNodeVal(linearProbingHashST.maxNode(linearProbingHashST.getRoot())));
    }
    */
}

class SeparateChainingHashST extends  ST implements Iterable{
    private int N; // number of key-value pairs
    private int M; // hash table size

    public int getM() {
        return this.M;
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

class LinearProbingHashST extends ST {
    private int N; // number of key-value pairs in the table
    private int M = 16; // size of linear-probing table
    private String[] keys; // the keys
    private SequentialSearchST[] st; // the values
    public LinearProbingHashST() {
        this(16);
    }

    public LinearProbingHashST(int cap) {
        keys = new String[cap];
        st = new SequentialSearchST[cap];
    }

    public int getM() {
        return this.M;
    }

    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    private void resize(int cap) {
        LinearProbingHashST t;
        t = new LinearProbingHashST(cap);
        for (int i = 0; i < M; i++)
            if (keys[i] != null)
                t.put(keys[i], st[i].getSize());
        keys = t.keys;
        st = t.st;
        M = t.M;
    }

    @Override
    public void put(String key, int val) {
        if (N >= M/2) resize(2*M); // double M (see text)
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key)) { st[i].put(key, val); return; }
        keys[i] = key;
        st[i] = new SequentialSearchST();
        st[i].put(key, val);
        N++;
    }

    @Override
    public int get(String key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key))
                return st[i].getSize();
        return -1;
    }

    @Override
    public boolean contains(String word) {
        return get(word)!=-1;
    }

    public SequentialSearchST[] getSt() {
        return this.st;
    }
}