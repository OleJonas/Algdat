import java.lang.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Koe {
    private Object[] tab;
    private int start = 0;
    private int slutt = 0;
    private int antall = 0;

    public Koe(int str){
        tab = new Object[str];
    }

    public boolean tom(){ return antall == 0;}

    public void leggIKoe(Object o){
        if(antall == tab.length) return;
        tab[slutt] = o;
        slutt = (slutt + 1)%tab.length;
        ++antall;
    }

    public Object neste(){
        if(!tom()){
            Object e = tab[start];
            start = (start+1)%tab.length;
            --antall;
            return e;
        }
        return null;
    }
}

public class GrafV2 {
    private final int antNoder;
    private int[][] kapasitet;

    public GrafV2(BufferedReader buf) throws Exception{
        String[] noderKanter = buf.readLine().trim().split(" ");
        this.antNoder = Integer.parseInt(noderKanter[0]);
        int antKanter = Integer.parseInt(noderKanter[1]);
        this.kapasitet = new int[antNoder][antNoder];

        for(int i = 0; i < antKanter; ++i){
            String[] data = new String[3];
            StringTokenizer tokenizer = new StringTokenizer(buf.readLine(), " ");
            for(int j = 0; j < 3; j++) data[j] = tokenizer.nextToken();
            this.kapasitet[Integer.parseInt(data[0])][Integer.parseInt(data[1])] = Integer.parseInt(data[2]);
        }
    }

    private boolean bfs(int[][] restGraf, int kilde, int sluk, int[] forrige){
        boolean[] besokt = new boolean[antNoder];

        for(int i = 0; i < antNoder; ++i) besokt[i] = false;

        Koe ko = new Koe(antNoder);
        ko.leggIKoe(kilde);
        //forrige[kilde] = -1; // For aa vise at denne er foerste
        besokt[kilde] = true;

        while(!ko.tom()){
            int hjorne1 = (int)ko.neste();
            // if(hjorne1 == sluk) return besokt[sluk] == true;
            // Kan kanskje sjekke naa om hjorne1 er sluket? I saa tilfelle, burde man vel gaa ut av while-loekka.

            for(int hjorne2 = 0; hjorne2 < antNoder; hjorne2++){
                if(!besokt[hjorne2] && restGraf[hjorne1][hjorne2] > 0){
                    ko.leggIKoe(hjorne2);
                    forrige[hjorne2] = hjorne1;
                    besokt[hjorne2] = true;
                }
            }
        }
        //Sjekker om vi kom til sluket (altsaa om det er en vei fra kilde til sluk)
        return besokt[sluk] == true;
    }

    public String kjoerEdmond(int kilde, int sluk){
        int maksFlyt = 0;
        ArrayList<ArrayList<Integer>> flytokninger = new ArrayList<>();
        int restkapasitet[][] = new int[antNoder][antNoder];

        for(int i = 0; i < antNoder; i++){
            for(int j = 0; j < antNoder; j++){
                restkapasitet[i][j] = kapasitet[i][j];
            }
        }

        // Lag så en tabell for å finne veien tilbake fra sluket til senere bruk. Denne blir ogsaa fylt av bfs
        int[] forrige = new int[antNoder];

        while(bfs(restkapasitet, kilde, sluk, forrige)){
            int stiFlyt = Integer.MAX_VALUE;
            for(int i = sluk; i != kilde; i=forrige[i]){
                stiFlyt = Math.min(stiFlyt, restkapasitet[forrige[i]][i]); // Sjekker hva som er begrensende for flyten.
            }

            // Deretter gjoer vi restkapasitetene up-to-date
            for(int i = sluk; i != kilde; i=forrige[i]){
                int a = forrige[i];
                restkapasitet[a][i] -= stiFlyt; // Dette er for "riktig" retning
                restkapasitet[i][a] += stiFlyt;
            }
            maksFlyt += stiFlyt;

            if(stiFlyt > 0){
                ArrayList<Integer> flytokningMVei = new ArrayList<>();
                //int[] riktigVei = reverser(forrige);
                flytokningMVei.add(sluk);
                for(int i = sluk; i != kilde; i=forrige[i]) flytokningMVei.add(forrige[i]);
                flytokningMVei.add(stiFlyt);
                flytokninger.add(flytokningMVei);
            }
        }
        String out = "";
        for(ArrayList<Integer> vei : flytokninger){
            for(int i = vei.size()-1; i >= 0; i--){
                out += ((int)vei.get(i) + " ");
            }
            out += "\n";
        }
        out += "Maksflyt: " + maksFlyt;
        return out;
    }

    /*private int[] reverser(int[] arr){
        int[] out = new int[arr.length];
        for(int i = 0; i < arr.length; ++i) out[i] = arr[i];

        for(int i = 0; i < arr.length / 2; i++){
            int temp = out[i];
            out[i] = out[out.length - i - 1];
            out[out.length - i - 1] = temp;
        }
        return out;
    }*/

    private int finnMinste(int a, int b){
        if(a < b || a == b) return a;
        return b;
    }

    public static void main (String[] args) throws Exception{
        BufferedReader buf = new BufferedReader(new FileReader("./divs/flytgraf3.txt"));
        GrafV2 grafern = new GrafV2(buf);
        System.out.println(grafern.kjoerEdmond(0,1));
    }
}