import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.PriorityQueue;

public class Komprimer {
    public static void komprimer(String fraFil, String tilFil) {
        int[] frekvenstabell = new int[256];
        String[] bitmoenstre = new String[256];
        byte[] bytesInnlest;

        try {

            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fraFil)));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tilFil)));

            bytesInnlest = in.readAllBytes(); // Leser inn alle tegn
            for (byte b : bytesInnlest) { // Finner frekvens av alle tegn
                int a = (int)b;
                if(a < 0) a = (256 + a);
                frekvenstabell[a]++;
            }

            // Finner bitmoenstre og legger dem i tabellen bitmoenstre
            Node rot = lagHuffmanTre(frekvenstabell);
            finnBitMoenstre(rot, new String(), bitmoenstre);


            // Formaterer frekvenstabellen som skal legges ved
            ArrayList<Byte> hjelper = new ArrayList<>();
            for(int i = 0; i < frekvenstabell.length; i++){

                if(frekvenstabell[i] > 0) {
                    // Legger ved hver char foerst
                    char a = (char) i;
                    hjelper.add((byte) a);

                    // Saa antall ganger den forekommer vha. bytebuffer. Dette deler en int opp i 4 bytes
                    ByteBuffer buf = ByteBuffer.allocate(4);
                    buf.putInt(frekvenstabell[i]);
                    buf.flip();
                    byte[] intTilBytes = buf.array();
                    for (int j = 0; j < 4; j++) {
                        hjelper.add(intTilBytes[j]); // Hvis int-verdien gaar over 256, vil den andre plassen i byte-buffer brukes
                    }
                }
            }

            byte[] frekvenstabellBytes = new byte[hjelper.size()+1];
            frekvenstabellBytes[0] = (byte)(frekvenstabellBytes.length/5);
            for(int i = 0; i < hjelper.size(); i++){
                frekvenstabellBytes[i+1] = hjelper.get(i);
            }


            // Lager strengen for den komprimerte teksten og legger den i en String array
            String utStreng = "";
            int antTegn = 0;
            for(byte b : bytesInnlest){
                antTegn++;
                int a = (b < 0)? (256 + b) : b;
                utStreng += bitmoenstre[a];
            }

            BitSet bs = new BitSet(utStreng.length());
            for(int i = 0; i < utStreng.length(); i++){
                if(utStreng.charAt(i) == '1'){
                    bs.set(i);
                } else if(utStreng.charAt(i) == '0'){
                    bs.clear(i);
                }
            }
            byte[] ferdigKomprimert = bs.toByteArray();

            // Lager saa strengen som brukes til slutt
            /*String ferdig = "";
            for(String s : strengHjelp){
                int parse = Integer.parseInt(s,2);
                ferdig += (char)((parse < 0)? 255 + parse: parse);
            }*/


            // Finner antall "tomme" bits som er til overs i siste byten
            byte nuller = (byte)(utStreng.length() % 8);

            out.write(nuller);
            out.write(frekvenstabellBytes);
            out.write(ferdigKomprimert);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Node lagHuffmanTre(int[] frekvenstabell){
        // Gjor klar for sortering
        PriorityQueue<Node> ko = new PriorityQueue<>(); // Koe til oppretting av Huffman-tre
        for (int i = 0; i < frekvenstabell.length; i++) {
            int frekvens = frekvenstabell[i];
            if (frekvens > 0) {
                ko.add(new Node((char) i, frekvens, null, null));
            }
        }

        // Sorterer. Siste gjenvaerende element som polles til slutt er rot-noden
        while (ko.size() > 1) {
            Node venstre = ko.poll();
            Node hoyre = ko.poll();
            ko.add(new Node('\0', venstre.frekvens + hoyre.frekvens, venstre, hoyre));
        }
        return ko.poll();
    }

    public static void finnBitMoenstre(Node n, String naavaerende, String[] bitmoenstre) {
        if(n ==null)return;
        if(n.venstre ==null && n.hoyre == null) {
            bitmoenstre[(int) n.tegn] = naavaerende;
        }

        finnBitMoenstre(n.venstre, naavaerende +"0", bitmoenstre);
        finnBitMoenstre(n.hoyre, naavaerende +"1", bitmoenstre);
    }
}