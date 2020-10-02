import java.util.HashMap;
import java.util.Random;

public class Klient {

    static int[] lagTab(int n, int lower, int upper){
        Random numGen = new Random();
        int[] out = new int[n];
        if(lower < 0){
            lower *= -1;
        }

        for(int i = 0; i < n; ++i){
            out[i] = numGen.nextInt(lower+upper) - lower;
        }
        return out;
    }


    public static void main(String[] args){
        Hashtabell tabOppg1 = new Hashtabell(113,"C:/Users/Jonas/OneDrive/Documents/NTNU aar 2/ALGDAT/Oving 5/src/navn.txt");

        int kollisjoner = tabOppg1.settInnTab();
        double lastfaktor = tabOppg1.getLastFaktor();
        double kollisjonPrPers = (kollisjoner/tabOppg1.getAntPers());

        System.out.println("Tot kollisjoner: " + kollisjoner + "\nLastfaktor: " + lastfaktor + "\nKollisjoner per pers.: " + kollisjonPrPers);

        System.out.println("Finnes Plahte,Eirik i tabellen?\nSvar: " + tabOppg1.finnPers("Plahte,Eirik"));

        int[] tab1 = lagTab(5000000, 1, 100000000);
        int[] tab2 = lagTab(5000000, 1, 100000000);

        HashtabDobbelHash tabOppg2 = new HashtabDobbelHash(6003079); // Bare legger inn et primtall ikke alt for langt fra 5M
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        long start = System.nanoTime();
        for(int i = 0; i < tab1.length; ++i){
            tabOppg2.settInn(tab1[i]);
        }
        long slutt = System.nanoTime();
        System.out.println((slutt - start)/Math.pow(10,9));

        start = System.nanoTime();
        for(int i = 0; i < tab2.length; ++i){
            map.put(tab2[i], tab2[i]);
        }
        slutt = System.nanoTime();
        System.out.println((slutt - start)/Math.pow(10,9));
    }
}
