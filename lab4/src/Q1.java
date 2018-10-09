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
        System.out.println(grX.DFSPath("NY","OH").UnDiPath());
        System.out.println(grX.DFSPath("OH","NY").UnDiPath());
        //Empty path
        System.out.println(grX.DFSPath("NY","NY").UnDiPath());
        //Long path
        System.out.println(grX.DFSPath("NY","FL").UnDiPath());
        //Disconnected src & dst
        System.out.println(grX.DFSPath("NY","DXB").UnDiPath());

        System.out.println("Is:   "+grX.DFShortestPath("NY", "OH").UnDiPath());
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

