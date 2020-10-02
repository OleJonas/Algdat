public class Node {
    private double element;
    public Node neste;
    public Node(double e, Node n){
        element = e;
        neste = n;
    }

    public double finnElement(){
        return element;
    }

    public Node finnNeste(){
        return neste;
    }

}
