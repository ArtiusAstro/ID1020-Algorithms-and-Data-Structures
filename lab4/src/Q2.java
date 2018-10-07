import java.io.FileNotFoundException;

/**
 * Find shortest path between two unweighted graph vertices using BFS
 *
 * @author Ayub Atif
 */
class Q2 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ2\n--------------------");

        UnDiGraph<String> grX = new UnDiGraph<>();
        grX.fillGraph("contiguous-usa.txt");

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        //Symmetry
        System.out.println(grX.BFShortsetNoWeight("NY","OH").UnDiPath());
        System.out.println(grX.BFShortsetNoWeight("OH","NY").UnDiPath());
        //Empty path
        System.out.println(grX.BFShortsetNoWeight("NY","NY").UnDiPath());
        //Long path
        System.out.println(grX.BFShortsetNoWeight("NY","FL").UnDiPath());
        //Disconnected src & dst
        System.out.println(grX.BFShortsetNoWeight("NY","DXB").UnDiPath());
    }
    /*
    --------------------
    Q2
    --------------------
    N: 49
    E: 107

    |NY->OH|
    Shortest Distance: 2
    Shortest Path: [NY]-[PA]-[OH]
    |OH->NY|
    Shortest Distance: 2
    Shortest Path: [OH]-[PA]-[NY]
    |NY->NY|
    Shortest Distance: 0
    Shortest Path: [NY]
    |NY->FL|
    Shortest Distance: 6
    Shortest Path: [NY]-[PA]-[WV]-[VA]-[TN]-[GA]-[FL]
    |NY->DXB|
    Disconnected src & dst
     */
}

