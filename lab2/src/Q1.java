import java.util.Scanner;

/**
 *  Uses insertion sort to sort an array of ints
 *
 *  @author Ayub Atif
 */
public class Q1 {

    /**
     * insertion sort
     * @param a comparable array
     * @return the number of swaps performed
     */
    public static int sort(Comparable[] a) {
        int n = a.length;
        int swaps = 0;

        for (int i = 1; i < n; i++){
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                swap(a, j, j - 1);
                swaps++;
            }
        }
        return swaps;
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void swap(Comparable[] a, int i, int j){
      Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    private static void show(Comparable[] a){
      // Print the array, on a single line.
        for (int i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a) {
        // Test whether the array entries are in order.
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    /**
     * Clone an array and return a new one
     * @param original the old one
     * @return the new one
     */
    public static Comparable[] xClone(Comparable[] original){
        Comparable[] clone = new Comparable[original.length];
        int i = 0;

        while(i<original.length)
            clone[i] = original[i++];

        return clone;
    }

    public static void main(String[] args) {
        Comparable[] input;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("No need to define size of input. State ints: ");
            String raw = scanner.next();

            input = new Comparable[raw.length()];
            for (int i = 0; i < raw.length(); i++){
                input[i] = raw.charAt(i) - '0';
            }

            System.out.print("Input: ");
            for (Comparable x : input) {
                System.out.print(x + " ");
            }
            System.out.print("\n");
        }

        Comparable[] ascending = xClone(input);

        System.out.print("Input:      ");
        show(ascending);
        System.out.print("Ascending:  ");
        int swaps = sort(ascending);
        assert isSorted(ascending);
        show(ascending);
    }
}
