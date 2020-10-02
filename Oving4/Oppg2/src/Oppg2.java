import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

class Node{
    char element;
    Node neste;

    public Node(char element, Node neste){
        this.element = element;
        this.neste = neste;
    }
}

class Stack{
    private Node topp = null;

    public Stack(){}

    public Node finnTopp(){ return topp;}

    public void push(char tegn){
        topp = new Node(tegn, topp);
    }

    public boolean pop(){
        if(topp != null) {
            topp = topp.neste;
            return true;
        }
        return false;
    }

    public boolean tom(){ return topp == null;}
}

class Kodesjekker{
    private Stack stack;
    private char[] startParenteser = {'{','(','['};
    private char[] sluttParenteser = {'}',')',']'};

    public Kodesjekker(){
        this.stack = new Stack();
    }

    public int sjekkFil(String fil) throws Exception{
        FileReader input = new FileReader(fil);
        BufferedReader buf = new BufferedReader(input);
        String tekst = null;
        int linje = 1;

        while((tekst = buf.readLine()) != null){
            char[] tegn = tekst.toCharArray();
            int index = -1;

            for(int i = 0; i < tegn.length; ++i){
                if(sjekkOmParentes(startParenteser, tegn[i]) != -1){
                    stack.push(tegn[i]);
                } else if((index = sjekkOmParentes(sluttParenteser, tegn[i])) != -1){
                    if(stack.finnTopp() != null) {
                        int motpart = sjekkOmParentes(startParenteser, stack.finnTopp().element);
                        if (index == motpart) {
                            stack.pop();
                        } else {
                            return linje; // Metoden returnerer linjen det eventuelt gikk galt
                        }
                    } else{
                        return linje;
                    }
                }
            }
            linje++;
        }
        buf.close();
        return 0;
    }

    public int sjekkOmParentes(char[] tab, char tegn){
        int a = 0;
        while(a < 3){
            if(tegn == tab[a]){ return a;}
            a++;
        }
        return -1;
    }
}

public class Oppg2 {
    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        Kodesjekker sjekker = new Kodesjekker();
        System.out.println("Legg inn path til filen:\n");
        String input = in.nextLine();
        in.close();

        System.out.println(sjekker.sjekkFil(input));
    }
}