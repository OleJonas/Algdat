import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader buf = null;
        Graf graf = null;

        try {
            buf = new BufferedReader(new FileReader("C:/Users/Jonas/OneDrive/Documents/NTNU aar 2/ALGDAT/Oving7/src/2ern.txt"));
            graf = new Graf(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(graf.skrivBFS(graf.getNodetabell()[5]));
        //System.out.println(graf.skrivTopoSort());
    }
}
