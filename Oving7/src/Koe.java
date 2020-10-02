public class Koe {
    private Object[] tab;
    private int start = 0;
    private int slutt = 0;
    private int antall = 0;

    public Koe(int str){
        tab = new Object[str];
    }

    public boolean tom(){ return antall == 0;}

    public void leggIKoe(Object o){
        if(antall == tab.length) return;
        tab[slutt] = o;
        slutt = (slutt + 1)%tab.length;
        ++antall;
    }

    public Object neste(){
        if(!tom()){
            Object e = tab[start];
            start = (start+1)%tab.length;
            --antall;
            return e;
        }
        return null;
    }
}
