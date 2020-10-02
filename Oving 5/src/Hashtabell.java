import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Hashtabell {
    private Node[] navn;
    private String[] filNavn;
    private int kollisjoner;

    public Hashtabell(int m, String path) { // Henter inn alle navnene og legger dem i en array saa jeg slipper aa styre med FileReader senere
        this.navn = new Node[m];
        this.kollisjoner = 0;
        this.filNavn = new String[96];
        try(FileReader input = new FileReader(path); BufferedReader buf = new BufferedReader(input)){
            int counter = 0;
            while((filNavn[counter] = buf.readLine()) != null && counter < 95) {
                counter++;
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public double getKollisjoner(){ return (double)kollisjoner;}
    public double getAntPers(){ return (double)filNavn.length;}

    public double getLastFaktor(){
        return (filNavn.length/(double)navn.length);
    }

    public int settInnTab(){
        for(int i = 0; i < filNavn.length; ++i){
            settInn(filNavn[i]);
        }
        return kollisjoner;
    }

    private int hashFunc(String key){
        int value = 0;
        for(int i = 0; i < key.length(); ++i){
            value += (key.charAt(i)*(i+1));
        }
        return value % navn.length;
    }

    public int settInn(String key){
        int pos = hashFunc(key);
        if(navn[pos] == null){
            navn[pos] = new Node(key, null);
        } else{
            System.out.println(navn[pos].element + " smell " + key);
            navn[pos] = new Node(key, navn[pos]);
            kollisjoner++;
        }
        return pos;
    }

    public String skrivUtIndeks(int indeks){
        return (navn[indeks].element + " |||| " + navn[indeks].neste.element);
    }

    public String finnPers(String key){
        int pos = hashFunc(key);
        if(navn[pos].element.equals(key)){
            return navn[pos].element;
        } else{
            Node denne = navn[pos];
            while(!denne.element.equals(key)){
                if(denne.neste == null) return null;
                denne = denne.neste;
            }
            //kollisjoner++;
            return denne.element;
        }
    }
}

