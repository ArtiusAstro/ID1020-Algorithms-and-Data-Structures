import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Q3 {

    private static LinkedArrayST<String> fillLAST() throws FileNotFoundException {
        LinkedArrayST<String> linkedArrayST = new LinkedArrayST<>();
        String word;

        Scanner sc = new Scanner(new File("98-0-filtered.txt"));
        while (sc.hasNextLine()) {
            Scanner sc2 = new Scanner(sc.nextLine());
            while (sc2.hasNext()){
                word = sc2.next();
                if (!linkedArrayST.contains(word)) linkedArrayST.put(word, 1);
                else linkedArrayST.put(word, linkedArrayST.get(word) + 1);
            }
        }

        return linkedArrayST;
    }

    public static void main(String args[]) throws FileNotFoundException {
        LinkedArrayST<String> lst = fillLAST();
        int a,b;
        try(Scanner sc = new Scanner(System.in)){
            System.out.print("Input range[a-b] with a=1 as max frequency: ");
            String input = sc.next();
            a = Character.getNumericValue(input.charAt(0)); b = Character.getNumericValue(input.charAt(2));
        }

        if(a>0 && b>0)
            lst.frequencyRange();
    }
}

class LinkedArrayST<Key extends Comparable<Key>>{
    private Key[] keys;
    private int[] vals;
    private int N;
    LinkedArrayST() {
        keys = (Key[]) new Comparable[60000];
        vals = new int[60000];
    }

    private void xDouble(){
        Key[] keysClone = (Key[]) new Comparable[keys.length*2];
        int[] valsClone = new int[vals.length*2];
        int i = 0;

        while(i<keys.length){
            keysClone[i] = keys[i++];
            valsClone[i] = vals[i++];
        }

        keys = keysClone;
        vals = valsClone;
    }

    public int size() { return N; }

    public int rank(Key key) {
        int lo = 0, hi = N-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    public Key[] keys() {
        return this.keys;
    }

    public int get(Key key){
        if (this.size() == 0) return -1;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        else return -1;
    }

    public void put(Key key, int val) {
        // Search for key. Update value if found; grow table if new.
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }
        for (int j = N; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key; vals[i] = val;
        N++;
        if(N==keys.length)    xDouble();
    }

    public boolean contains(Key word) {
        return this.get(word) != -1;
    }

    public void frequencyRange() {
    }
}