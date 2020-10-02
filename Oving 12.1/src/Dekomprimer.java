import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;

public class Dekomprimer {

    public static void dekomprimer(String fraFil, String tilFil) {
        int[] frekvenstabell = new int[256];
        String[] bitmoenstre = new String[256];
        byte[] bytesInnlest;
        int nuller;
        int frekvenstabellTegn;

        try {

            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fraFil)));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tilFil)));

            // Leser inn alle tegn og setter verdier for etterfoelgende nuller og antall tegn i frek.tabell osv.
            bytesInnlest = new byte[in.available()];
            in.read(bytesInnlest);

            nuller = bytesInnlest[0];
            frekvenstabellTegn = sjekkVerdi(bytesInnlest[1]);

            int offset = 2; // pga. to andre verdier ligger foerst
            for(int i = 0; i < frekvenstabellTegn; i++){
                int start = (i*5)+offset;
                int tegn = sjekkVerdi(bytesInnlest[start]);

                int intLest = 0;
                int grad = 3;
                for(int j = start + 1; j < start + 5; j++){
                    int verdi = sjekkVerdi(bytesInnlest[j]);
                    intLest += verdi*(Math.pow(256, grad));
                    grad--;
                }
                frekvenstabell[tegn] = intLest;
            }

            // Lager huffman-tre med metoden fra Komprimer
            Node rot = Komprimer.lagHuffmanTre(frekvenstabell);

            int nyOffset = (frekvenstabellTegn*5) + 2;
            byte[] innhold = new byte[bytesInnlest.length - nyOffset];
            for(int i = 0; i < innhold.length; i++){
                innhold[i] = bytesInnlest[i+nyOffset];
            }

            // Oppretter streng med alle bitmoenstre
            String moenster = "";
            BitSet bs = BitSet.valueOf(innhold);
            for(int i = 0; i < bs.length(); i++){
                if(bs.get(i)){
                    moenster += '1';
                } else {
                    moenster += '0';
                }
            }
            moenster += '0';

            String ut = "";
            Node n = rot;
            for(int i = 0; i < moenster.length(); i++){
                if (moenster.charAt(i) == '0') {
                    n = n.venstre;
                } else {
                    n = n.hoyre;
                }
                if(n.venstre == null) {
                    ut += n.tegn;
                    n = rot;
                }
            }
            System.out.println("Jonas winRar - trial edition expires soon. Consider upgrading at our website");
            //Komprimer.finnBitMoenstre(rot, new String(), bitmoenstre);


            // Gjoer om til vanlige tegn igjen
            /*int start = (frekvenstabellTegn * 5) + 2;
            for(int i = start; i < bytesInnlest.length; i++){
                int verdi = sjekkVerdi(bytesInnlest[i]);
                for(int j = 0; j < bitmoenstre.length; j++){
                    if(bitmoenstre[j] != null && verdi == Integer.parseInt(bitmoenstre[j],2)){
                        ut += (char)j;
                    }
                }
            }*/

            Files.writeString(Paths.get(tilFil), ut);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int sjekkVerdi(byte b){
        return (b < 0)? 256 + b : b;
    }
}
