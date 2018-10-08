import javafx.scene.input.KeyCode;

import java.io.FileNotFoundException;

/**
 * Check if two vertices are connected in a directed graph
 *
 * @author Ayub Atif
 */
class Q5 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ5\n--------------------");

        DiGraphX<String> grX = new DiGraphX<>();
        grX.fillGraph("contiguous-usa.txt");

        grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        System.out.println("NY->CA: "+grX.checkForConnection("NY","CA"));
        System.out.println("NY->WV: "+grX.checkForConnection("NY","WV"));
        System.out.println("CA->WY: "+grX.checkForConnection("CA","WY"));
        System.out.println("CA->FL: "+grX.checkForConnection("CA","FL"));
    }
    /*
    --------------------
    Q5
    --------------------
    N: 49
    E: 107

    NY->CA: false
    NY->WV: true
    CA->WY: true
    CA->FL: false

    AL:
    TN 4
    MS 3
    GA 2
    FL 1

    FL:
    GA 31

    GA:
    TN 34
    SC 33
    NC 32

    MS:
    TN 80

    TN:
    VA 105

    AR:
    TX 10
    TN 9
    OK 8
    MS 7
    MO 6
    LA 5

    LA:
    TX 63
    MS 62

    MO:
    TN 79
    OK 78
    NE 77

    OK:
    TX 101

    TX:

    AZ:
    UT 14
    NV 13
    NM 12
    CA 11

    CA:
    OR 16
    NV 15

    NM:
    TX 94
    OK 93

    NV:
    UT 96
    OR 95

    UT:
    WY 106

    OR:
    WA 102

    CO:
    WY 22
    UT 21
    OK 20
    NM 19
    NE 18
    KS 17

    KS:
    OK 56
    NE 55
    MO 54

    NE:
    WY 89
    SD 88

    WY:

    CT:
    RI 25
    NY 24
    MA 23

    MA:
    VT 67
    RI 66
    NY 65
    NH 64

    NY:
    VT 98
    PA 97

    RI:

    DC:
    VA 27
    MD 26

    MD:
    WV 70
    VA 69
    PA 68

    VA:
    WV 107

    DE:
    PA 30
    NJ 29
    MD 28

    NJ:
    PA 92
    NY 91

    PA:
    WV 103

    NC:
    VA 86
    TN 85
    SC 84

    SC:

    IA:
    WI 40
    SD 39
    NE 38
    MO 37
    MN 36
    IL 35

    IL:
    WI 50
    MO 49
    KY 48
    IN 47

    MN:
    WI 76
    SD 75
    ND 74

    SD:
    WY 104

    WI:

    ID:
    WY 46
    WA 45
    UT 44
    OR 43
    NV 42
    MT 41

    MT:
    WY 83
    SD 82
    ND 81

    WA:

    IN:
    OH 53
    MI 52
    KY 51

    KY:
    WV 61
    VA 60
    TN 59
    OH 58
    MO 57

    MI:
    WI 73
    OH 72

    OH:
    WV 100
    PA 99

    WV:

    NH:
    VT 90

    VT:

    ME:
    NH 71

    ND:
    SD 87
     */
}
