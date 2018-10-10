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
        System.out.println(grX.diskjtraShortestPath("NY","OH").UnDiPath());
        System.out.println(grX.diskjtraShortestPath("OH","NY").UnDiPath());
        //Empty path
        System.out.println(grX.diskjtraShortestPath("NY","NY").UnDiPath());
        //Long path
        System.out.println(grX.diskjtraShortestPath("UT","WY").UnDiPath());
        //Disconnected src & dst
        System.out.println(grX.diskjtraShortestPath("NY","DXB").UnDiPath());
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
    |UT->WY|
    Shortest Distance: 43
    Shortest Path: [UT]-[CO]-[WY]
    |NY->DXB|
    Disconnected src & dst
     */
}
