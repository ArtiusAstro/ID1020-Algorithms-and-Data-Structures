import java.io.FileNotFoundException;
import java.util.List;

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

        System.out.println("Cycle presence: "+grX.kosarajuCycle());

        grX.addEdge("WY","CA"); System.out.println("\n\ngrX.addEdge(\"WY\",\"CA\");");

        System.out.println("Cycle presence: "+grX.kosarajuCycle());
    }
    /*
    --------------------
    Q6
    --------------------
    N: 49
    E: 107

    Print cycle
    Acyclic->

    grX.addEdge("WY","CA");
    N: 49
    E: 108

    Print cycle
    WY->CA->NV->UT->WY->
     */
}
