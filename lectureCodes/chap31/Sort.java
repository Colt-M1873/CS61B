public class Sort {
    /** Sorts strings destructively. */
    public static void sort(String[] x) {
        sort(x,0);
    }

    /** Sorts strings destructively starting from item start. */
    private static void sort(String[] x, int start) {
        if (start == x.length){
            return;
        }
        int smallestIndex = findSmallest(x, start);
        swap(x,start,smallestIndex);
        sort(x,start+1);
    }

    public static int findSmallest(String[] x, int start){
        int smallextIdx=start;
        for (int i=start;i<x.length;i++){
            if (x[i].compareTo(x[smallextIdx])<0){
                smallextIdx=i;
            }
        }
        return smallextIdx;
    }

    public static void swap(String[] x, int a, int b){
        String temp = x[a];
        x[a] = x[b];
        x[b] = temp;
    }


}