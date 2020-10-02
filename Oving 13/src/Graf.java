import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.PriorityQueue;

public class Graf {
    private final static double JORDRADIUS = 6371000.0;
    private final static double MAKS_FARTSGRENSE = 130/3.6; // m/s
    private final static int MAKS_AVSTAND = Integer.MAX_VALUE;

    private Node[] noder;
    private int antNoder;
    private int antKanter;
    private int antNoderTatt = 0;


    public Graf(String nodeFil, String kantFil) throws Exception{

        // Noder
        BufferedReader nodeleser = new BufferedReader(new FileReader(nodeFil));
        antNoder = Integer.parseInt(hsplit(nodeleser.readLine())[0]);
        noder = new Node[antNoder];

        for(int i = 0; i < noder.length; i++){
            String[] verdier = hsplit(nodeleser.readLine());
            //int nodenr = Integer.parseInt(verdier[0]);
            double breddegrad = Double.parseDouble(verdier[1]);
            double lengdegrad = Double.parseDouble(verdier[2]);
            noder[i] = new Node(i, breddegrad, lengdegrad);
        }
        nodeleser.close();

        // Kanter
        BufferedReader kantleser = new BufferedReader(new FileReader(kantFil));
        antKanter = Integer.parseInt(hsplit(kantleser.readLine())[0]);

        for(int i = 0; i < antKanter; i++){
            String[] verdier = hsplit(kantleser.readLine());
            int fra = Integer.parseInt(verdier[0]);
            int til = Integer.parseInt(verdier[1]);
            int kjoretid = Integer.parseInt(verdier[2]);
            int lengde = Integer.parseInt(verdier[3]);
            int fartsgrense = Integer.parseInt(verdier[4]);
            noder[fra].kanter.add(new VKant(noder[til], kjoretid, lengde, fartsgrense));
        }
        kantleser.close();
    }

    public void printForste(){
        for(int i = 0; i < 20; i++){
            for(VKant k : noder[i].kanter) {
                System.out.println(i + " " + k.kjoretid + " " + k.tilnode.nodeNr);
            }
        }
    }

    public double haversineAvstand(Node a, Node b){ // Gir avstand i m
        return(
            2.0*JORDRADIUS*Math.asin(
                Math.sqrt(
                    Math.pow(
                        Math.sin(
                            (a.breddegrad - b.breddegrad)/2.0
                        ), 2
                    ) + (
                        a.cosBreddeRad*b.cosBreddeRad*Math.pow(
                            Math.sin(
                                (a.breddegrad - b.breddegrad)/2.0
                            ), 2
                        )
                    )
                )
            )
        );
    }

    public Node dijkstra(int startnode, int sluttnode){
        klargjorSok(startnode);
        Node start = noder[startnode];
        Node slutt = noder[sluttnode];
        PriorityQueue<Node> ko = new PriorityQueue<>();

        ko.add(start);
        while(ko.size() > 0){
            Node n = ko.poll();
            antNoderTatt++;

            for (VKant k : n.kanter) {
                if (!k.tilnode.funnet) {

                    if (k.tilnode.distFraStart > n.distFraStart + k.kjoretid) {
                        k.tilnode.distFraStart = n.distFraStart + k.kjoretid;
                        k.tilnode.forgjenger = n;
                        ko.add(k.tilnode);
                    }
                }
            }
            if(n == slutt){
                System.out.println("halla");
                return n;
            }
        }
        return slutt;
    }

    public Node aStar(int start, int slutt){
        klargjorSok(start);

        Node startNode = noder[start];
        Node sluttNode = noder[slutt];
        PriorityQueue<Node> ko = new PriorityQueue<>();

        startNode.samlet = kjoretid(startNode, sluttNode);
        ko.add(startNode);
        while(ko.size() > 0){
            Node n = ko.poll();
            antNoderTatt++;

            for (VKant k : n.kanter) {
                if (!k.tilnode.funnet) {
                    if (k.tilnode.distFraStart > n.distFraStart + k.kjoretid){
                        k.tilnode.distFraStart = n.distFraStart + k.kjoretid;
                        k.tilnode.samlet = k.tilnode.distFraStart + kjoretid(k.tilnode, sluttNode);
                        k.tilnode.forgjenger = n;
                        ko.add(k.tilnode);
                    }
                }
            }
            if(n == sluttNode){
                System.out.println("halla");
                return n;
            }
        }
        return noder[slutt];
    }

    public void toString(Node sluttnode){
        Node n = sluttnode;
        int antNoder = 1;

        while(n.forgjenger != null){
            antNoder++;
            n = n.forgjenger;
        }
        System.out.println(
        "Fra-node: " + n.nodeNr + " " + n.distFraStart +
        "\nTil-node: " + sluttnode.nodeNr + "\nAntall noder: " + antNoder +
        "\nReisetid: " + formaterTid(sluttnode.distFraStart) + "\nNoder tatt fra kø: " + antNoderTatt);
        //for(Node n = sluttnode; n.forgjenger != null; n = n.forgjenger){
    }

    private String formaterTid(int tid){
        int tidISekunder = tid/100;
        int utTimer = tidISekunder/3600;
        tidISekunder -= utTimer*3600;
        int utMinutter = tidISekunder/60;
        tidISekunder -= utMinutter*60;

        return utTimer + ":" + utMinutter + ":" + tidISekunder;
    }


    // Hjelpemetoder
    private int kjoretid(Node a, Node b){ // Gir tid i hundredeler
        return (int)((haversineAvstand(a, b) / MAKS_FARTSGRENSE)*100);
    }

    private String[] hsplit(String linje){
        String[] verdier = new String[10];
        int j = 0;
        int lengde = linje.length();
        boolean ferdig = false;
        int index = 0;

        while(!ferdig){
            while (linje.charAt(j) <= ' ') ++j;
            int ordstart = j;

            while (j < lengde && linje.charAt(j) > ' ') ++j;
            verdier[index] = linje.substring(ordstart, j);
            index++;

            if(j++ == lengde) ferdig = true;
        }
        return verdier;
    }

    public void skrivTilFil(Node n, String filnavn) throws Exception{
        BufferedWriter buf = new BufferedWriter(new FileWriter(filnavn, false));
        while(n.forgjenger != null){
            buf.write(n.breddegrad/Node.GRAD_TIL_RAD + ", " + n.lengdegrad/Node.GRAD_TIL_RAD);
            buf.newLine();
            n = n.forgjenger;
        }
        buf.write(n.breddegrad/Node.GRAD_TIL_RAD + ", " + n.lengdegrad/Node.GRAD_TIL_RAD);
        buf.close();
    }

    private void klargjorSok(int start){
        for(int i = 0; i < noder.length; i++){
            noder[i].distFraStart = MAKS_AVSTAND;
            noder[i].forgjenger = null;
            noder[i].funnet = false;
            noder[i].samlet = MAKS_AVSTAND;
        }
        noder[start].distFraStart = 0;
        antNoderTatt = 0;
    }
}
