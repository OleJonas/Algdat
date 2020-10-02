import java.io.*;
import java.util.ArrayList;

public class FilIOGreier {

    public static void skrivTilFil(ArrayList<Long> input, String tilFil){
        ArrayList<Byte> bytePlasser = new ArrayList<>();

        int bitBuffer = 8;
        for(Long bitmoenster : input){
            int bits = Long.toBinaryString(bitmoenster).length();
            int indeks = 0;

            /*while(indeks != bits) {
                byte hjelp = (byte)(bitmoenster << indeks);
                if(bitBuffer == 8) {
                    //byte hjelp = (byte) (bitmoenster << indeks);
                    bytePlasser.add(hjelp);
                    indeks += bitBuffer;
                }
                else{
                    //byte hjelp = (byte)(bitmoenster << )
                    indeks += bitBuffer;

                }
            }*/
        }
    }
}
