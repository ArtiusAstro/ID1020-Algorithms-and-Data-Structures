
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Compare the runtime of seperate chaining to lienar probing for hash tables
 *
 * @author Ayub Atif
 */
public class Q7{

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ7\n--------------------");

        SeparateChainingHashST separateChainingHashST = (SeparateChainingHashST) ST.fillST(new SeparateChainingHashST());
        LinearProbingHashST linearProbingHashST = (LinearProbingHashST) ST.fillST(new LinearProbingHashST());

        // Test is to find the key with the highest frequency count.
        long start = System.currentTimeMillis();
        SeperateChainingTest(separateChainingHashST);
        long time = System.currentTimeMillis() - start;
        System.out.println("SeperateChaining time: "+time+"ms");
        start = System.currentTimeMillis();
        LinearProbingTest(linearProbingHashST);
        time = System.currentTimeMillis() - start;
        System.out.println("LinearProbing time: "+time+"ms");
    }

    private static void SeperateChainingTest(SeparateChainingHashST separateChainingHashST){
        System.out.println("MAX: "+separateChainingHashST.getMaxKey()+" "+separateChainingHashST.get(separateChainingHashST.getMaxKey()));
    }

    private static void LinearProbingTest(LinearProbingHashST linearProbingHashST){
        System.out.println("MAX: "+linearProbingHashST.getMaxKey()+" "+ linearProbingHashST.get(linearProbingHashST.getMaxKey()));
    }

}

/**
 * Hash table that uses separate chaining to handle collisions
 *
 * @author Ayub Atif
 */
class SeparateChainingHashST extends  ST implements Iterable{
    private int N; // number of key-value pairs
    private int M; // hash table size

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

    public String getMaxKey(){
        String maxKey = null;
        int maxValue = 0;
        int val;

        for(SequentialSearchST st : st)
            for (Object key : st)
                if ((val=st.get((String) key)) > maxValue){
                    maxKey = (String) key;
                    maxValue = val;
                }

        return maxKey;
    }
}

/**
 * Hash table that uses linear probing to handle collisions
 *
 * @author Ayub Atif
 */
class LinearProbingHashST extends ST {
    private int N; // number of key-value pairs in the table
    private int M = 16; // size of linear-probing table
    private String[] keys; // the keys
    private int[] vals; // the values
    public LinearProbingHashST() {
        keys = new String[M];
        vals = new int[M];
    }

    public LinearProbingHashST(int cap) {
        keys = new String[cap];
        vals = new int[cap];
        M = cap;
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
                t.put(keys[i], vals[i]);
        keys = t.keys;
        vals = t.vals;
        M = t.M;
    }

    @Override
    public void put(String key, int val) {
        if (N >= M/2) resize(2*M); // double M (see text)
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key)) { vals[i] = val; return; }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    @Override
    public int get(String key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key))
                return vals[i];
        return -1;
    }

    @Override
    public boolean contains(String word) {
        return get(word)!=-1;
    }

    public String getMaxKey() {
        if (N==0) throw new NoSuchElementException();
        int maxIndex = 0;

        for(int i=0;i<M;i++)
            if (vals[i] > vals[maxIndex])
                maxIndex = i;

        return keys[maxIndex];
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
