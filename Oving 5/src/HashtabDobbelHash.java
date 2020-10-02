public class HashtabDobbelHash {
    private int[] tall;

    public HashtabDobbelHash(int str){
        this.tall = new int[str];
    }

    public int hashFunc1(int key){
        return key % tall.length;
    }

    public int hashFunc2(int index, int faktor){
        return (index + faktor) % tall.length;
    }

    public int settInn(int key){
        int index = hashFunc1(key);
        int faktor = 1;

        while(tall[index] != 0) {
            index = hashFunc2(index, faktor);
            ++faktor;
        }
        tall[index] = key;
        return index;
    }
}
