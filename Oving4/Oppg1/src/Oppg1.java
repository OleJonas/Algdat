class Node{
    int element;
    Node neste;

    public Node(int element, Node neste){
        this.element = element;
        this.neste = neste;
    }

    public int getElement(){ return element;}
}

class Sirkulaer{
    private Node hode;
    private int antElementer;
    private Node siste;

    public Sirkulaer(){}

    public void leggTilNode(int verdi){
        if(hode != null){
            Node ny = new Node(verdi, hode);
            siste.neste = ny;
            siste = ny;
            ++antElementer;
        } else{
            hode = new Node(verdi, hode);
            siste = hode;
            ++antElementer;
        }
    }

    /*public void settInnBakerst(int verdi){
        if(hode != null){
            sistInnlagt.neste = new Node(verdi, hode);
            sistInnlagt = sistInnlagt.neste;
            ++antElementer;
        } else{
            hode = new Node(verdi, hode);
            sistInnlagt = hode;
            ++antElementer;
        }
    }*/

   /* public void settInnFremst(int verdi){
        if(hode != null) {
            sistInnlagt.neste = new Node(verdi, hode);
            antElementer++;
        } else{
            hode = new Node(verdi, hode);
            sistInnlagt = hode;
            antElementer++;
        }
    }*/

    public void fjernElement(Node n){
        Node forrige = null;
        Node denne = hode;
        while(denne != null && denne != n){
            forrige = denne;
            denne = denne.neste;
        }

        if(denne != null){
            if(forrige != null){
                forrige.neste = denne.neste;
            } else{
                hode = denne.neste;
            }
            denne.neste = null;
            --antElementer;
        }
    }

    public int utslettelse(int intervall){
        Node denne = hode;
        int teller = 1;
        while(antElementer > 1){
            if(teller % intervall == 0) {
                Node neste = denne.neste;

                if(denne == hode){
                    hode = neste;
                }

                fjernElement(denne);
                denne = neste;

            } else{
                denne = denne.neste;
            }
            teller++;
        }
        return denne.getElement();
    }

    public int getAntElementer(){ return antElementer;}
}

class Overlevelse{
    Sirkulaer liste = new Sirkulaer();

    private void leggTilSoldater(int n){
        for(int i = 0; i < n; ++i){
            liste.leggTilNode(i+1);
        }
    }

    public int hvemOverlever(int antSoldater, int intervall){
        leggTilSoldater(antSoldater);
        return liste.utslettelse(intervall);
    }
}

public class Oppg1 {
    public static void main(String[] args){
        Overlevelse overlevelseshjelp = new Overlevelse();
        System.out.println(overlevelseshjelp.hvemOverlever(5000, 10));
    }
}