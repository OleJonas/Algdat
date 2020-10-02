public class EnkelLenke {
    private Node hode = null;
    private Node hale = null;
    private int antElementer = 0;


    public int finnAntElementer(){
        return antElementer;
    }


    public void leggTilNode(Node n){
        if(hode == null){
            hode = n;
            hale = n;
            n.neste = hode;
            antElementer++;
        }else{
            hale.neste = n;
            hale = n;
            hale.neste = hode;
            antElementer++;
        }
    }

    public Node fjern(Node n){
        Node forrige = null;
        Node denne = hode;
        while(denne != null && denne != n){
            forrige = denne;
            denne = denne.neste;
        }
        if(denne != null) {
            if (forrige != null) forrige.neste = denne.neste;
            else hode = denne.neste;
            denne.neste = null;
            --antElementer;
            return denne;
        }else{
            return null;
        }
    }

    public void skrivUtLenke(){
        Node current = hode;
        do{
            System.out.print(current.finnElement() + " ");
            current = current.neste;
        }while(current != hode);
    }

    public void slett(int intervall){
        Node current = hode;
        int teller = 1;
        while(antElementer > 1){

            if(teller % intervall == 0){
                System.out.println(" Fjerner: " + current.finnElement());
                Node neste = current.neste;


                if (current == hode) {
                    hode = neste;
                }
                fjern(current);
                current = neste;
                skrivUtLenke();
            }else{
                current = current.neste;
            }
            teller++;
        }
    }
}
