import java.io.FileNotFoundException;

/**
 * Find a minimum spanning tree for the largest connected component in a weighted graph
 *
 * @author Ayub Atif
 */
class Q4 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ4\n--------------------");

        UnDiGraph<String> grX = new UnDiGraph<>();
        grX.fillGraph("contiguous-usa.txt");

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        System.out.println("\nTree\n"+grX.kruskalMST().tree()+"Done");
    }
    /*
    --------------------
    Q4
    --------------------
    N: 49
    E: 107

    added AL-FL: 1
    added AL-GA: 2
    added AL-MS: 3
    added AL-TN: 4
    added AR-LA: 5
    added AR-MO: 6
    added AR-MS: 7
    added AR-OK: 8
    continued AR-TN: 9
    added AR-TX: 10
    added AZ-CA: 11
    added AZ-NM: 12
    added AZ-NV: 13
    added AZ-UT: 14
    continued CA-NV: 15
    added CA-OR: 16
    added CO-KS: 17
    added CO-NE: 18
    added CO-NM: 19
    added CO-OK: 20
    continued CO-UT: 21
    added CO-WY: 22
    added CT-MA: 23
    added CT-NY: 24
    added CT-RI: 25
    added DC-MD: 26
    added DC-VA: 27
    added DE-MD: 28
    added DE-NJ: 29
    added DE-PA: 30
    continued FL-GA: 31
    added GA-NC: 32
    added GA-SC: 33
    continued GA-TN: 34
    added IA-IL: 35
    added IA-MN: 36
    added IA-MO: 37
    continued IA-NE: 38
    added IA-SD: 39
    added IA-WI: 40
    added ID-MT: 41
    added ID-NV: 42
    continued ID-OR: 43
    continued ID-UT: 44
    added ID-WA: 45
    continued ID-WY: 46
    added IL-IN: 47
    added IL-KY: 48
    continued IL-MO: 49
    continued IL-WI: 50
    continued IN-KY: 51
    added IN-MI: 52
    added IN-OH: 53
    continued KS-MO: 54
    continued KS-NE: 55
    continued KS-OK: 56
    continued KY-MO: 57
    continued KY-OH: 58
    continued KY-TN: 59
    added KY-VA: 60
    added KY-WV: 61
    continued LA-MS: 62
    continued LA-TX: 63
    added MA-NH: 64
    continued MA-NY: 65
    continued MA-RI: 66
    added MA-VT: 67
    continued MD-PA: 68
    continued MD-VA: 69
    continued MD-WV: 70
    added ME-NH: 71
    continued MI-OH: 72
    continued MI-WI: 73
    added MN-ND: 74
    continued MN-SD: 75
    continued MN-WI: 76
    continued MO-NE: 77
    continued MO-OK: 78
    continued MO-TN: 79
    continued MS-TN: 80
    continued MT-ND: 81
    continued MT-SD: 82
    continued MT-WY: 83
    continued NC-SC: 84
    continued NC-TN: 85
    continued NC-VA: 86
    continued ND-SD: 87
    continued NE-SD: 88
    continued NE-WY: 89
    continued NH-VT: 90
    added NJ-NY: 91
    continued NJ-PA: 92
    continued NM-OK: 93
    continued NM-TX: 94
    continued NV-OR: 95
    continued NV-UT: 96
    continued NY-PA: 97
    continued NY-VT: 98
    continued OH-PA: 99
    continued OH-WV: 100
    continued OK-TX: 101
    continued OR-WA: 102
    continued PA-WV: 103
    continued SD-WY: 104
    continued TN-VA: 105
    continued UT-WY: 106
    continued VA-WV: 107

    Tree
    AL-FL: 1
    AL-GA: 2
    AL-MS: 3
    AL-TN: 4
    AR-LA: 5
    AR-MO: 6
    AR-MS: 7
    AR-OK: 8
    AR-TX: 10
    AZ-CA: 11
    AZ-NM: 12
    AZ-NV: 13
    AZ-UT: 14
    CA-OR: 16
    CO-KS: 17
    CO-NE: 18
    CO-NM: 19
    CO-OK: 20
    CO-WY: 22
    CT-MA: 23
    CT-NY: 24
    CT-RI: 25
    DC-MD: 26
    DC-VA: 27
    DE-MD: 28
    DE-NJ: 29
    DE-PA: 30
    GA-NC: 32
    GA-SC: 33
    IA-IL: 35
    IA-MN: 36
    IA-MO: 37
    IA-SD: 39
    IA-WI: 40
    ID-MT: 41
    ID-NV: 42
    ID-WA: 45
    IL-IN: 47
    IL-KY: 48
    IN-MI: 52
    IN-OH: 53
    KY-VA: 60
    KY-WV: 61
    MA-NH: 64
    MA-VT: 67
    ME-NH: 71
    MN-ND: 74
    NJ-NY: 91
    Done
     */
}
