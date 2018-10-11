import java.io.FileNotFoundException;

/**
 * Find directed cycles in the graph (if none exist, add non-trivial edges to test)
 *
 * @author Ayub Atif
 */
class Q6 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ6\n--------------------");

        DiGraphX<String> grX = new DiGraphX<>();
        grX.fillGraph("contiguous-usa.txt");

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        System.out.println("Cycle presence: "+grX.kosarajuCycleChecker());

        grX.addEdge("WY","CA"); System.out.println("\ngrX.addEdge(\"WY\",\"CA\");");

        System.out.println("Cycle presence: "+grX.kosarajuCycleChecker());
    }
    /*
    --------------------
    Q6
    --------------------
    N: 49
    E: 107

    Cycle presence: false

    grX.addEdge("WY","CA");
    Cycle presence: true
     */
}
