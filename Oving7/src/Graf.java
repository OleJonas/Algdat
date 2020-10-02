import java.io.BufferedReader;

public class Graf {
    private Node[] nodetabell;
    private Node[] forgjenger;
    private int[] dist;
    private int[] flyt;
    int antNoder;
    int antKanter;

    public Graf(BufferedReader buf) throws Exception{
        String[] noderKanter = buf.readLine().trim().split(" ");
        antNoder = Integer.parseInt(noderKanter[0]);
        nodetabell = new Node[antNoder];
        forgjenger = new Node[antNoder];
        dist = new int[antNoder];
        flyt = new int[antNoder];
        antKanter = Integer.parseInt(noderKanter[1]);

        for(int i = 0; i < antNoder; ++i){
            nodetabell[i] = new Node(i);
            forgjenger[i] = new Node(-1);
        }

        for(int i = 0; i < antKanter; ++i){
            String[] fraTil = buf.readLine().split(" ");
            int fra = Integer.parseInt(fraTil[0]);
            int til = Integer.parseInt(fraTil[1]);
            Kant k = new Kant(nodetabell[til], nodetabell[fra].kant1);
            nodetabell[fra].kant1 = k;
        }
    }

    public Node[] getNodetabell(){ return nodetabell;}

    public void klarForSok(Node s){
        for(int i = 0; i < antNoder; ++i){
            dist[i] = 10000;
        }
        dist[s.nr] = 0;
    }

    public void bfs(Node s){
        klarForSok(s);
        Koe koe = new Koe(antNoder-1);
        koe.leggIKoe(s);
        while(!koe.tom()){
            Node n = (Node)koe.neste();
            for(Kant k = n.kant1; k != null; k = k.neste){
                Node f = k.til;
                if(dist[f.nr] == 10000){ // Dette betyr at soeket ikke har vaert innom denne noden enda
                    dist[f.nr] = dist[n.nr] + 1; // Setter distansen = forgjengeren sin (+1)
                    forgjenger[f.nr] = n;
                    koe.leggIKoe(f);
                }
            }
        }
    }

    public String skrivBFS(Node s){
        String out = "Node  Forgj  Dist";
        bfs(s);
        for(int i = 0; i < nodetabell.length; ++i){
            if(dist[i] != 0){
                out += ("\n" + i + "     " + forgjenger[i].nr + "      " + dist[i]);
            } else{
                out += ("\n" + i + "            " + dist[i]);
            }
        }
        return out;
    }

    public Node topoDF(Node start, Node foerst){
        Topo_lst n = start.topo;
        if(n.funnet) return foerst;
        n.funnet = true;
        for(Kant k = start.kant1; k != null; k = k.neste){
            foerst = topoDF(k.til, foerst);
        }
        n.neste = foerst;
        return start;
    }

    public Node topoSort(){
        Node n = null;
        for(int i = antNoder; i-- > 0;){
            nodetabell[i].topo = new Topo_lst();
        }
        for(int i = antNoder; i-- > 0;) n = topoDF(nodetabell[i], n);
        return n;
    }

    public String skrivTopoSort(){
        String out = "";
        for(Node a = topoSort(); a != null; a = a.topo.neste){
            out += ("\n" + a.nr);
        }
        return out;
    }
}
