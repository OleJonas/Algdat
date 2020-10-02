public class Main {
    public static void main(String[] args) throws Exception{
        String islandNoder = "./Kart/Island/noder.txt";
        String islandKanter = "./Kart/Island/kanter.txt";

        String nordenNoder = "./Kart/Norden/noder.txt";
        String nordenKanter = "./Kart/Norden/kanter.txt";

        /*
        Graf dijkstra = new Graf(nordenNoder, nordenKanter);
        long start = System.nanoTime();
        Node slutt = dijkstra.dijkstra(2460904, 2419175);
        long tidBrukt = System.nanoTime() - start;
        System.out.println("Tid brukt: " + tidBrukt/Math.pow(10,9) + "s");
        dijkstra.toString(slutt);
        dijkstra.skrivTilFil(slutt, "./Test.txt");
        */

        Graf aStar = new Graf(nordenNoder, nordenKanter);
        long start = System.nanoTime();
        Node slutt = aStar.aStar(2460904, 2419175);
        long tidBrukt = System.nanoTime() - start;
        System.out.println("Tid brukt: " + tidBrukt/Math.pow(10,9) + "s");
        aStar.toString(slutt);
        aStar.skrivTilFil(slutt, "./Test.txt");
        //island.printForste();

    }
}
