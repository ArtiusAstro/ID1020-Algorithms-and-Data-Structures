import java.io.FileNotFoundException;

/**
 * Find shortest path between two weighted graph vertices using Dijkstra
 *
 * @author Ayub Atif
 */
class Q3 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ3\n--------------------");

        UnDiGraph<String> grX = new UnDiGraph<>();
        grX.fillGraph("contiguous-usa.txt");

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        grX.disjkstra(); // find shortest paths from each vertex to each vertex
        //grX.setShortest("NY");
        //Symmetry
        System.out.println(grX.getShortestPath("NY","OH").UnDiPath());
        System.out.println(grX.getShortestPath("OH","NY").UnDiPath());
        //Empty path
        System.out.println(grX.getShortestPath("NY","NY").UnDiPath());
        //Long path
        System.out.println(grX.getShortestPath("AL","GA").UnDiPath());
        //Disconnected src & dst
        System.out.println(grX.getShortestPath("NY","DXB").UnDiPath());
    }
    /*
    --------------------
    Q3
    --------------------
    N: 49
    E: 107

    Multi source Dijkstra activated
    |NY->OH|
    Shortest Distance: 196
    Shortest Path: [NY]-[PA]-[OH]
    |OH->NY|
    Shortest Distance: 196
    Shortest Path: [OH]-[PA]-[NY]
    |NY->NY|
    Shortest Distance: 0
    Shortest Path: [NY]
    |NY->FL|
    Shortest Distance: 318
    Shortest Path: [NY]-[NJ]-[DE]-[MD]-[DC]-[VA]-[TN]-[AL]-[FL]
    |NY->DXB|
    Disconnected src & dst
     */
}
