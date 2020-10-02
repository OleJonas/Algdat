import java.io.*;
import java.util.Date;

public class Dekomprimer {
    public static void dekomprimer(String fraFil, String tilFil){
        int[] frekvenstabell = new int[256];
        int[] bitverdier = new int[256];
        int frekvensBokstaver, nuller;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fraFil)), "UTF8"));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tilFil)));

            frekvensBokstaver = Integer.parseInt((char)in.read() + "" + (char)in.read() + "" + (char)in.read());
            in.read();
            nuller = Integer.parseInt("" + (char)in.read());
            System.out.println(frekvensBokstaver + " " + nuller);

            /*for(int i = 0; i < frekvensBokstaver; i++){
                char a = (char)in.read();
                int numLength =
            }*/
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
