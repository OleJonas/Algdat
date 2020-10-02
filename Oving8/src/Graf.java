import java.io.BufferedReader;
import java.io.FileReader;

class Vkant{
    Node til;
    Vkant neste;
    int kapasitet;
    int fremFlyt;
    int reversFlyt;

    public Vkant(Node n, Vkant nst, int kapasitet, int frem, int revers){
        this.til = n;
        this.neste = nst;
        this.kapasitet = kapasitet;
        this.fremFlyt = frem;
        this.reversFlyt = revers;
    }

    public int restKapasitet(){
        return kapasitet-fremFlyt;
    }
}

class Node{
    Vkant kant1;
    int nr;
    int flyt;

    public Node(int nr, int flyt){
        this.nr = nr;
        this.flyt = flyt;
    }
}

public class Graf {
    private Node[] nodetabell;
    private Node[] forgjenger;
    private int[] dist;
    int antKanter;
    Node kilde;
    Node sluk;
    int maksFlyt = 0;

    public Graf(BufferedReader buf) throws Exception{
        String[] noderKanter = buf.readLine().trim().split(" ");
        int antNoder = Integer.parseInt(noderKanter[0]);
        nodetabell = new Node[antNoder];
        forgjenger = new Node[antNoder];
        antKanter = Integer.parseInt(noderKanter[1]);
        dist = new int[antNoder];

        for(int i = 0; i < antNoder; i++){
            nodetabell[i] = new Node(i, 0);
            forgjenger[i] = new Node(-1, 0);
            dist[i] = 100000;
        }

        for(int i = 0; i < antKanter; i++){
            String[] data = buf.readLine().trim().split(" ");
            int fra = Integer.parseInt(data[0]);
            int til = Integer.parseInt(data[1]);
            int kapasitet = Integer.parseInt(data[2]);
            Vkant k = new Vkant(nodetabell[til], nodetabell[fra].kant1, kapasitet, 0, 0);
            nodetabell[fra].kant1 = k;
        }
    }

    private void klarForSokEdmond(){
        for(int i = 0; i < nodetabell.length; i++){
            nodetabell[i].flyt = 0;
            for(Vkant k = nodetabell[i].kant1; k != null; k=k.neste){
                k.fremFlyt = 0;
                k.reversFlyt = 0;
                System.out.println(i);
            }
        }
    }

    public void klarForSokBFS(Node s){
        for(int i = 0; i < nodetabell.length; ++i){
            dist[i] = 10000;
            //forgjenger[i] = null;
        }
        dist[s.nr] = 0;
        System.out.println("ææææ");
    }

    public int bfs(Node s){
        Koe koe = new Koe(nodetabell.length-1);
        koe.leggIKoe(s);
        Vkant[] forrigeKanter = new Vkant[antKanter];
        while(!koe.tom()) {
            Node n = (Node) koe.neste();
            if (n == sluk) break;
            for (Vkant k = n.kant1; k != null; k = k.neste) {
                Node f = k.til;
                if (dist[f.nr] == 10000 && k.restKapasitet() > 0) { // Dette betyr at soeket ikke har vaert innom denne noden enda
                    dist[f.nr] = dist[n.nr] + 1; // Setter distansen = forgjengeren sin (+1)
                    forrigeKanter[f.nr] = k;
                    forgjenger[f.nr] = n;
                    koe.leggIKoe(f);
                    System.out.println(f.nr);
                }
            }
        }
        if(forrigeKanter[sluk.nr] == null) return 0;

        int flaskehals = 999999;
        for(Vkant k = forrigeKanter[sluk.nr]; k != null; k = forrigeKanter[forgjenger[k.til.nr].nr]){
            System.out.println("Hællæ");
            flaskehals = (flaskehals < k.restKapasitet())? flaskehals : k.restKapasitet();
            System.out.println(flaskehals);
        }

        for(Vkant k = forrigeKanter[sluk.nr]; k != null; k = forrigeKanter[forgjenger[k.til.nr].nr]){
            System.out.println("Hællæ2");
            k.fremFlyt += flaskehals;
            k.reversFlyt -= flaskehals;
        }
        return flaskehals;
    }

    public int kjoerEdmond(int kilde, int sluk){
        this.kilde = nodetabell[kilde];
        this.sluk = nodetabell[sluk];
        klarForSokEdmond();
        klarForSokBFS(nodetabell[kilde]);

        int flyt;
        do{
            flyt = bfs(nodetabell[kilde]);
            maksFlyt += flyt;
            System.out.println(flyt);
        } while(flyt != 0);
        return maksFlyt;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader buf = new BufferedReader(new FileReader("./divs/flytgraf1.txt"));
        Graf grafern = new Graf(buf);
        grafern.kjoerEdmond(0,7);
    }
}

