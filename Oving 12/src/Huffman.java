import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.PriorityQueue;

class Node implements Comparable<Node> {
    char tegn;
    int frekvens;
    Node venstre;
    Node hoyre;

    public Node(char tegn, int frekvens, Node venstre, Node hoyre){
        this.tegn = tegn;
        this.frekvens = frekvens;
        this.venstre = venstre;
        this.hoyre = hoyre;
    }

    public int compareTo(Node annen){
        int out = -1;
        if(this.frekvens != annen.frekvens){
            return (this.frekvens < annen.frekvens)? -1 : 1;
        }

        // Hvis de har lik frekvens, sorter på leksigografisk verdi.
        if(this.tegn != '\0' && annen.tegn == '\0' || this.tegn < annen.tegn) out = 1;
        return out;
    }
}

public class Huffman{
    private String[] bitverditabell;
    private String fraFil;
    private String tilFil;

    public Huffman(String fraFil, String tilFil) throws Exception{
        this.fraFil = fraFil;
        this.tilFil = tilFil;
        int biggest = 0;
        try(BufferedReader in = new BufferedReader(new FileReader(fraFil))){
            String linje;
            while((linje = in.readLine()) != null){
                for(int i = 0; i < linje.length(); i++){
                    if(linje.charAt(i) > biggest){
                        System.out.println(linje.charAt(i) + " " + (int)linje.charAt(i));
                        biggest = linje.charAt(i);
                    }
                }
            }
        }
        this.bitverditabell = new String[biggest+1];
    }

    public void komprimerTilFil() throws Exception{
        try(
                BufferedReader in = new BufferedReader(new FileReader(fraFil));
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tilFil)))
        ){
            String bitmoenstre = "";
            String frekvenstabellString = "";

            int[] frekvenstabell = byggFrekvenstabell(in);
            Node rot = byggTre(frekvenstabell);
            finnAlleVerdier(rot, new String());

            // Lager outputstreng
            // Bruk bytebyffer til ints og putte inn
            ArrayList<Byte> bytesInn = new ArrayList<>();
            for(int i = 0; i < frekvenstabell.length; i++){
                if(frekvenstabell[i] != 0){
                    int lengdeBytes = ("" + frekvenstabell[i]).length();
                    frekvenstabellString += ((char)i + "" + lengdeBytes + "" + frekvenstabell[i]);
                }
            }
            out.write((frekvenstabell.length + "" + frekvenstabellString).getBytes());

            byte[] array = Files.readAllBytes(Paths.get(fraFil));

            for(int i = 0; i < array.length; i++){
                char tegn = ((char)array[i] > -1 && (char)array[i] < 256)? (char)array[i] : 'o';
                //if(bitverditabell[tegn] != null) {
                    bitmoenstre += bitverditabell[tegn];
                //}
            }
            /*String[] bytesTilArray = bitmoenstre.split("(?<=\\G........)");

            String komprimert = "";
            for(String s : bytesTilArray){
                int verdi = Integer.parseInt(s, 2); // Parser bitverdi
                komprimert += (verdi + 127);
            }*/

            BitSet bs = new BitSet(bitmoenstre.length());
            for(int i = 0; i < bitmoenstre.length(); i++){
                if(bitmoenstre.charAt(i) == '1'){
                    bs.set(i);
                } else if(bitmoenstre.charAt(i) == '0'){
                    bs.clear(i);
                }
            }
            out.write(bs.toByteArray());

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void finnAlleVerdier(Node n, String bitmoenster){
        if(n == null) return;

        if(n.venstre == null && n.hoyre == null){
            //System.out.println((char)n.tegn + " " + n.frekvens + " " + bitmoenster);
            bitverditabell[(int)n.tegn] = bitmoenster;
            System.out.println(n.tegn + " " + n.frekvens + " " + bitmoenster);
        }

        finnAlleVerdier(n.venstre, bitmoenster + "0");
        finnAlleVerdier(n.hoyre, bitmoenster + "1");
    }


    private Node byggTre(int[] frekvenstabell){
        PriorityQueue<Node> koe = new PriorityQueue<>(); // Koe til bruk for aa sortere nodene senere i metoden

        for(int i = 0; i < frekvenstabell.length; i++){
            int frekvens = frekvenstabell[i];
            if(frekvens > 0){
                koe.add(new Node((char)i, frekvens, null, null));
            }
        }

        //while(koe.size() != 0) System.out.println(koe.poll().frekvens);

        while(koe.size() > 1){ // Naar koen har 1 element har vi kommet til rota
            Node venstre = koe.poll();
            //System.out.println((char)venstre.tegn + " " + venstre.frekvens);

            Node hoyre = koe.poll();
            //System.out.println((char)hoyre.tegn + " " + hoyre.frekvens);

            koe.add(new Node('\0', venstre.frekvens + hoyre.frekvens, venstre, hoyre));
        }

        return koe.poll(); // Trenger bare polle siste element i koen saa har vi rota til lista
    }



    private int[] byggFrekvenstabell(BufferedReader buf) throws Exception{
        //ArrayList<Node> ut = new ArrayList<>();

        int[] tab = new int[bitverditabell.length]; // Tabellen )har str 256 fordi det skal vaere plass til all ASCII
        /*for(int i = 0; i < tekst.length(); i++){
            tab[tekst.charAt(i)]++;
        }*/
        String linje;
        while((linje = buf.readLine()) != null){
            for(int i = 0; i < linje.length(); ++i){ // Kan hende dette maa endres pga casting til byte
                tab[linje.charAt(i)]++;
            }
        }
        return tab;
    }

    private void skrivTilFil(DataOutputStream out){

    }

    public static void main(String[] args) throws Exception{
        String filNavnFra = "./src/Test.txt";
        String filNavnTil = "./src/TestTil.txt";

        Huffman huff = new Huffman(filNavnFra, filNavnTil);
        huff.komprimerTilFil();
        //Dekomprimer.dekomprimer("./src/TestTil.txt", "./src/dekomprimert");
    }
}
