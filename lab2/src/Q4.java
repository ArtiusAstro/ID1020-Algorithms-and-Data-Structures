import java.util.Scanner;

/**
 * Sort an array with insertion sort and reverse it with a simple for loop
 * Also prints out the number of swaps performed during a sort.
 * Also prints out the inversions in an array before sorting it.
 *
 * @author Ayub Atif
 */
public class Q4 {

    /**
     * String formatting to clearly display the inversions from the array
     * @param stringBuilder use same one as first at each call
     * @param i index
     * @param x element
     * @param postScript
     * @return part of final stringbuilder
     */
    public static StringBuilder arrayBuilder(StringBuilder stringBuilder, int i, Comparable x, String postScript){
        stringBuilder.append('[').append(i).append(',').append(x).append(']').append(postScript);

        return stringBuilder;
    }

    /**
     * Prints all inversions in array
     * @param a the array
     * @return number of inversions
     */
    public static int printInversions(Comparable[] a) {
        // prints a list of all inversions on the format [i,a[i]], [j, a[j]]
        // where i and j are indices and a[i], a[j] are the values of the elements.
        int n = a.length;
        int swaps = 0;
        StringBuilder stringBuilder = new StringBuilder("");

        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                if(more(a[i], a[j])) {
                    stringBuilder = arrayBuilder(stringBuilder, i, a[i], ", ");
                    stringBuilder = arrayBuilder(stringBuilder, j, a[j], "\n");
                    swaps++;
                }
            }
        }

        System.out.print(stringBuilder.toString());
        return swaps;
    }

    public static Comparable[] reverseSortedArray(Comparable[] a) {
        Comparable[] reverse = new Comparable[a.length];

        for(int i = 0; i < a.length; i++)
            reverse[i] = a[a.length - 1 - i];

        return reverse;
    }

    public static boolean isReverseSorted(Comparable[] a) {
        // Test whether the array entries are in reverse order.
        for (int i = 1; i < a.length; i++)
            if (more(a[i], a[i-1])) return false;
        return true;
    }

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

    private static boolean more(Comparable v, Comparable w){
        return v.compareTo(w) > 0;
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
            System.out.print("State ints: ");
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

        System.out.println(printInversions(input)+" inversions\n");

        System.out.print("Input:      ");
        show(ascending);
        System.out.print("Ascending:  ");
        int swaps = sort(ascending);
        assert isSorted(ascending);
        show(ascending);
        System.out.println("Performed "+swaps+" swaps");

        System.out.print("Descending: ");
        ascending = reverseSortedArray(ascending);
        assert isReverseSorted(ascending);
        show(ascending);

    }
}
