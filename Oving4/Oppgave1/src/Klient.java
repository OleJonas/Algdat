public class Klient {
    public static void main(String[] args){

        int antPersoner = 40;
        int intervall = 8;

        int[] tab = new int[antPersoner];
        for(int i = 0; i < antPersoner; i++){
            tab[i] = (i+1);
        }

        EnkelLenke liste = new EnkelLenke();

        for(int i = 0; i < tab.length; i++){
            Node temp = new Node((i+1), null);
            liste.leggTilNode(temp);
        }

        System.out.println(liste.finnAntElementer());
        liste.skrivUtLenke();

        long startTid = System.nanoTime();
        liste.slett(intervall);
        long sluttTid = System.nanoTime();
        System.out.println(sluttTid - startTid + " ns");
    }
}
