import java.util.Random;

public class Oving1 {

    static int[] makeArray(int n, int lower, int upper){
        Random numGen = new Random();
        int[] out = new int[n];
        if(lower < 0){
            lower *= -1;
        }

        for(int i = 0; i < n; ++i){
            out[i] = numGen.nextInt(lower+upper) - lower;
        }
        return out;
    }

    static int[] findBestProfit(int[] stonks){
        int bestDiff = stonks[0]; // 1
        int[] out = new int[3];   // 1

        for(int i = 0; i < stonks.length; ++i){ // 1+2n
            int helper = stonks[i]; // n
            int value = stonks[i];  // n
            for(int j = i+1; j < stonks.length; ++j){
                value += stonks[j];
                if((value-helper) > bestDiff){
                    bestDiff = value-helper;
                    out[0] = i;
                    out[1] = j;
                    out[2] = bestDiff;
                }
            }
        }
        return out;
    }

    static long avgTime(int n){
        int test[] = makeArray(n, -10, 10);
        long totTime = 0;
        for(int i = 0; i < 1000; ++i){
            long startTime = System.nanoTime();
            int[] best = findBestProfit(test);
            long endTime = System.nanoTime();
            totTime += (endTime - startTime);
        }
        return (totTime/(long)1000);
    }

    public static void main(String[] args){
        /*int[] test = makeArray(1000, -10, 10);
        long startTime = System.nanoTime();
        int[] best = findBestProfit(test);
        long endTime = System.nanoTime();
        System.out.println("Buy: " + best[0] + ", Sell: " + best[1] + "\nProfit: " + best[2]);
        System.out.println("Runtime for findBestProfit, N=1000: " + (endTime-startTime));

        test = makeArray(10000, -10, 10);
        startTime = System.nanoTime();
        best = findBestProfit(test);
        endTime = System.nanoTime();
        System.out.println("Buy: " + best[0] + ", Sell: " + best[1] + "\nProfit: " + best[2]);
        System.out.println("Runtime for findBestProfit, N=10000: " + (endTime-startTime));

        test = makeArray(100000, -10, 10);
        startTime = System.nanoTime();
        best = findBestProfit(test);
        endTime = System.nanoTime();
        System.out.println("Buy: " + best[0] + ", Sell: " + best[1] + "\nProfit: " + best[2]);
        System.out.println("Runtime for findBestProfit, N=100000: " + (endTime-startTime));*/

        System.out.println("n = 100: " + avgTime(100) + "\nn = 1000: " + avgTime(1000) + "\nn = 10000: " + avgTime(10000));
    }
}
