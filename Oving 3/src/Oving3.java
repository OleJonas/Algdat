import java.util.Random;

class Oving3{

    static int split(int[] arr, int low, int high){
        int pivotIndex = (high+low)/2; // Velger siste element i tabellen som pivot
        int lower = low-1;

        for(int i = low; i<=high-1; ++i){
            if(arr[i] < arr[pivotIndex]){
                ++lower;
                int helper = arr[lower];
                arr[lower] = arr[i];
                arr[i] = helper;
            }
        }
        int helper = arr[lower+1];
        arr[lower+1] = arr[high];
        arr[high] = helper;
        return (lower+1); // Dette vil være det nye splittpunktet til de neste rekursive kallene
    }

    static void quickSortMod(int[] arr, int low, int high){
        if(high - low <= 25){
            insertionSort(arr, low, high);
        } else{
            int num = split(arr, low, high);
            quickSortMod(arr, low, num-1);
            quickSortMod(arr, num+1, high);
        }
    }

    static void insertionSort(int[] arr, int low, int high){
        for(int i = low+1; i < high; ++i){
            int helper = arr[i];
            int j = i-1;

            while(j >= low && arr[j] > helper){
                arr[j+1] = arr[j];
                --j;
            }
            arr[j+1] = helper;
        }
    }

    static void quickSortNett(int[] arr, int low, int high)
    {
        if (low < high)
        {
        /* pi is partitioning index, arr[pi] is now
           at right place */
            int pi = split(arr, low, high);

            quickSortNett(arr, low, pi - 1);  // Before pi
            quickSortNett(arr, pi + 1, high); // After pi
        }
    }

    /*static void quickSortNett(int arr[], int begin, int end) {
            int partitionIndex = split(arr, begin, end);

            if(partitionIndex-1 > begin){quickSortNett(arr, begin, partitionIndex-1);}
            if(partitionIndex+1 < end){quickSortNett(arr, partitionIndex+1, end);}
    }*/

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

    public static void main(String[] args){
        int sum1 = 0;
        int sum2 = 0;

        int[] arr1 = makeArray(1000000, 0, 100000);
        int[] arr2 = arr1;

        for(int i = 0; i<arr1.length; ++i){
            sum1 += arr1[i];
            //sum2 += arr2[i];
        }
        System.out.println(sum1 + "aaaa: " + sum2);

        long start = System.nanoTime();
        quickSortMod(arr1, 0, arr1.length-1);
        long res1 = System.nanoTime() - start;

        start = System.nanoTime();
        //quickSortNett(arr2, 0, arr2.length-1);
        long res2 = System.nanoTime() - start;

        System.out.println("Res1: " + res1 + "\nRes2: " + res2);
        //System.out.println("Hvis dette er negativt, er den modifiserte quicksort raskere: " + (res1-res2));

        sum1 = 0;
        sum2 = 0;
        for(int i = 0; i<arr1.length; ++i){
            sum1 += arr1[i];
            //sum2 += arr2[i];
        }
        System.out.println(sum1 + "aaaaa " + sum2);
    }

    int[] tabell = new int[10];
    int[] tabell1 = {1,2,3,4,5,6,7,8,9};
}