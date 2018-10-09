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
        System.out.println(grX.DFSPath("NY","OH"));
        System.out.println(grX.DFSPath("OH","NY"));
        //Empty path
        System.out.println(grX.DFSPath("NY","NY"));
        //Long path
        System.out.println(grX.DFSPath("NY","FL"));
        //Disconnected src & dst
        System.out.println(grX.DFSPath("NY","DXB"));

        System.out.println(grX.DFShortestPath("NY", "OH")+"\n");
    }
    /*
    --------------------
    Q1
    --------------------
    N: 49
    E: 107

    [NY], [PA], [WV], [VA], [TN], [NC], [SC], [GA], [FL], [AL], [MS], [LA], [TX], [OK], [NM], [CO], [WY], [UT], [NV], [OR], [WA], [ID], [MT], [SD], [NE], [MO], [KY], [OH]
    [OH], [WV], [VA], [MD], [PA], [NY]
    [NY]
    [NY], [PA], [WV], [VA], [TN], [NC], [SC], [GA], [FL]
    [Disconnected src/dst]
     */
}

