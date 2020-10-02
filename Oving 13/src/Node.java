import java.util.ArrayList;

public class Node implements Comparable<Node>{
    public final static double GRAD_TIL_RAD = (Math.PI/180.0);

    final int nodeNr;
    final double breddegrad;
    final double cosBreddeRad;
    final double lengdegrad;
    Node forgjenger;
    ArrayList<VKant> kanter;
    int distFraStart;
    int samlet;
    boolean funnet;

    public Node(int nodeNr, double breddegrad, double lengdegrad){
        this.nodeNr = nodeNr;
        this.breddegrad = breddegrad * GRAD_TIL_RAD;
        this.lengdegrad = lengdegrad * GRAD_TIL_RAD;

        this.cosBreddeRad = Math.cos(breddegrad);
        this.kanter = new ArrayList<>();
    }

    /*public int compareTo(Node n){
        if(this.distFraStart < n.distFraStart) return -1;
        if(this.distFraStart > n.distFraStart) return 1;
        return 0;
    }*/

    public int compareTo(Node n) {
        if(this.samlet < n.samlet) return -1;
        if(this.samlet > n.samlet) return 1;
        return 0;
    }
}
