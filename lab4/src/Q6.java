import java.io.FileNotFoundException;

/**
 * Find directed cycles in the graph (if none exist, add non-trivial edges to test)
 *
 * @author Ayub Atif
 */
class Q6 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\n64\n--------------------");

        DiGraphX<String> grX = new DiGraphX<>();
        grX.fillGraph("contiguous-usa.txt");
        grX.addEdge("WY","CA");

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        Iterable<String> cycles = grX.DirectedCycles();
        System.out.println("print cycle");
        for(String s : cycles)
            System.out.print(s+"->");
    }
    /*

     */
}
