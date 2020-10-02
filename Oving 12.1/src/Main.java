public class Main {
    public static void main(String[] args){
        String lesFil = "./src/testLes.txt";
        String skrivFil = "./src/testSkriv.txt";
        String dekomprimertFil = "./src/testDekomprimer.txt";

        Komprimer.komprimer(lesFil, skrivFil);
        Dekomprimer.dekomprimer(skrivFil, dekomprimertFil);
    }
}
