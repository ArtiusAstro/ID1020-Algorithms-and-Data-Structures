import java.io.FileNotFoundException;

/**
 * Find shortest path between two unweighted graph vertices using DFS
 *
 * @author Ayub Atif
 */
class Q1 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ1\n--------------------");

        UnDiGraph<String> grX = new UnDiGraph<>();
        grX.fillGraph("contiguous-usa.txt");

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        //Symmetry
        System.out.println(grX.DFSPath("AL","TN").UnDiPath());
        System.out.println(grX.DFSPath("AL","MS").UnDiPath());
        //Empty path
        System.out.println(grX.DFSPath("NY","NY").UnDiPath());
        //Long path
        System.out.println(grX.DFSPath("NY","NJ").UnDiPath());
        //Disconnected src & dst
        System.out.println(grX.DFSPath("NY","DXB").UnDiPath());

    }
    /*
    --------------------
    Q1
    --------------------
    N: 49
    E: 107

    [AL]-[TN]
    [AL]-[TN]-[VA]-[WV]-[PA]-[OH]-[MI]-[WI]-[MN]-[SD]-[WY]-[UT]-[NV]-[OR]-[CA]-[AZ]-[NM]-[TX]-[OK]-[MO]-[AR]-[MS]
    [NY]
    [NY]-[PA]-[WV]-[VA]-[MD]-[DE]-[NJ]
    [Disconnected src & dst]
     */
}

