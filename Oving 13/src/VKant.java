public class VKant {
    Node tilnode;
    int kjoretid; // hundredeler
    double lengde; // m
    double fartsgrense; // m/s

    public VKant(Node tilnode, int kjoretid, double lengde, double fartsgrense){
        this.tilnode = tilnode;
        this.kjoretid = kjoretid;
        this.lengde = lengde;
        this.fartsgrense = fartsgrense/3.6;
    }
}
