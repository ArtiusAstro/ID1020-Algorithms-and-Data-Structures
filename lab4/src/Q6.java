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

        Iterable<String> cycles = grX.directedCycles();
        System.out.println("Print cycle");
        for(String s : cycles)
            System.out.print(s+"->");

        grX.addEdge("WY","CA");
        System.out.println("\n\ngrX.addEdge(\"WY\",\"CA\");");
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");
        cycles = grX.directedCycles();
        System.out.println("Print cycle");
        for(String s : cycles)
            System.out.print(s+"->");
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
