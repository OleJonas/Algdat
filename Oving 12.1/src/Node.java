public class Node implements Comparable<Node> {
    char tegn;
    int frekvens;
    Node venstre;
    Node hoyre;

    public Node(char tegn, int frekvens, Node venstre, Node hoyre){
        this.tegn = tegn;
        this.frekvens = frekvens;
        this.venstre = venstre;
        this.hoyre = hoyre;
    }

    public int compareTo(Node annen){
        int out = -1;
        if(this.frekvens != annen.frekvens){
            return (this.frekvens < annen.frekvens)? -1 : 1;
        }

        // Hvis de har lik frekvens, sorter på leksigografisk verdi.
        if(this.tegn != '\0' && annen.tegn == '\0' || this.tegn < annen.tegn) out = 1;
        return out;
    }
}