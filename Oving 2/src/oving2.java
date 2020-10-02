import static java.lang.System.nanoTime;
import java.lang.Math;

class Oving2{
    static double myPow(double x, int n){
        if(n == 0){
            return 1;
        } else if(n > 0){
            return x*myPow(x, n - 1);
        }
        return -1;
    }

    static double newPow(double x, int n){
        if(n == 0){
            return 1;
        } else if(n > 0){
            if(n % 2 == 0){
                return newPow(x*x, n/2);
            }
            return x*newPow(x*x, (n-1)/2);
        }
        return -2;
    }

    public static void main(String[] args){
        long startTime = 0;
        long totTime = 0;

        for(int i = 0; i < 1000; ++i){
            startTime = nanoTime();
            myPow(4, 9999);
            totTime += (nanoTime() - startTime);
        }
        System.out.println("myPow: " + totTime/1000);

        totTime = 0;
        for(int i = 0; i < 1000; ++i){
            startTime = nanoTime();
            newPow(4, 9999);
            totTime += (nanoTime() - startTime);
        }
        System.out.println("newPow: " + totTime/1000);

        totTime = 0;
        for(int i = 0; i < 1000; ++i){
            startTime = nanoTime();
            Math.pow(4, 9999);
            totTime += (nanoTime() - startTime);
        }
        System.out.println("Math.pow: " + totTime/1000);
    }
}