import java.io.FileNotFoundException;

/**
 * Find and print a topological sort of a small data base
 *
 * @author Ayub Atif
 */
class Q7 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ7\n--------------------");

        DiGraphX<String> grX = new DiGraphX<>();
        grX.fillGraph("small-db.txt");

        grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        Iterable<String> iterable = grX.topologicalSort();
        System.out.println("Print Topological Sort");
        for (String v : iterable)
            System.out.print(v+"->");
    }
    /*
    --------------------
    Q7
    --------------------
    N: 13
    E: 12

    Print Topological Sort
    TJ->TK->TL->TM->TI->TH->TG->TD->TC->TA->TB->TF->TE->

    TA:
    TF 2
    TB 1

    TB:

    TF:
    TE 5

    TC:
    TA 3

    TD:
    TF 4

    TE:

    TG:
    TE 6

    TH:
    TG 7

    TI:
    TH 8

    TJ:
    TM 11
    TL 10
    TK 9

    TK:

    TL:
    TM 12

    TM
     */
}
