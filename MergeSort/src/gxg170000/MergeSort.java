package gxg170000;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {
    public static Random random = new Random();
    public static int numTrials = 100;
    public static void main(String[] args) {
        int mil = 1000000;
        int n = 2*mil;
        int choice = 1 + random.nextInt(4);
        if(args.length > 0) { n = Integer.parseInt(args[0]); }
        if(args.length > 1) { choice = Integer.parseInt(args[1]); }
        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
            arr[i] = i;
        }
        Timer timer = new Timer();
        switch(choice) {
            case 1:
                for(int i=0; i<numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSort1(arr);

                }
                break;
            case 2:
                for(int i=0; i<numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSort3(arr);
                }
                break;
            case 3:
                for(int i=0; i<numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSortTake4(arr);
                }
                break;
            case 4:
                for(int i=0; i<numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSortTake5(arr);
                }
                break;
        }
        timer.end();
        timer.scale(numTrials);

        System.out.println("Choice: " + choice + "\n" + timer);
    }

    public static void insertionSort(int[] arr) {
        for(int i=1;i<arr.length;i++){
            for(int j=i;j>0;j--){
                if(arr[j]<=arr[j-1]){
                    int temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                }
            }
        }
    }

    public static void mergeSort1(int[] arr) {
        mergeSortTake1(arr, 0, arr.length-1);
    }

    private static void mergeSortTake1(int[] A, int low, int high){
        if(low<high){
            int mid = (low + high)/2;
            mergeSortTake1(A, low, mid);
            mergeSortTake1(A, mid+1, high);
            mergeTake1(A, low, mid, high);
        }
    }

    private static void mergeTake1(int[] A, int low, int mid, int high){
        int n1 = mid - low + 1;
        int n2 = high - mid;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for(int i=0;i<L.length;i++){
            L[i] = A[i+low];
        }

        for(int i=0;i<R.length;i++){
            R[i] = A[i+mid+1];
        }

        int i = 0, j = 0, k=low;

        while (i<n1 && j<n2){
            if(L[i]<=R[j]){
                A[k++] = L[i++];
            }
            else {
                A[k++] = R[j++];
            }
        }
        while (i<n1){
            A[k++] = L[i++];
        }
        while (j<n2){
            A[k++] = R[j++];
        }
    }

    public static void mergeSort3(int[] arr) {
        int[] B = arr.clone();
        mergeSortTake3(arr, B, 0, arr.length-1);
    }

    private static void mergeSortTake3(int[] A, int[] B, int low, int high){
        if(low<high){
            int mid = (low + high)/2;
            mergeSortTake3(B, A, low, mid);
            mergeSortTake3(B, A, mid+1, high);
            mergeTake3(A, B, low, mid, high);
        }
    }

    private static void mergeTake3(int[] A, int[] B, int low, int mid, int high){
        int i = low, j = mid+1, k=low;
        while (i<=mid && j<=high){
            if(B[i]<=B[j]){
                A[k++] = B[i++];
            }
            else {
                A[k++] = B[j++];
            }
        }
        while (i<=mid) A[k++] = B[i++];
        while (j<=high) A[k++] = B[j++];
    }

    public static void mergeSortTake4(int[] A){
        int[] B = A.clone();
        mergeSortTake4(A, B, 0, A.length-1);
    }

    private static void mergeSortTake4(int[] A, int[] B, int low, int high){
        if(high-low<numTrials){
            insertionSort(A);
        }
        else{
            mergeSortTake3(A, B, low, high);
        }
    }

    public static void mergeSortTake5(int[] A){
        int[] B = A.clone();
        mergeSortTake5(A, B);
    }

    private static void mergeSortTake5(int[] A, int[] B){
        int [] T = A;
        for(int i=1;i<A.length;i=2*i){
            for(int j=0;j<A.length-1;j+=2*i){
                int mid = Math.min(j+i-1, A.length-1);
                int right = Math.min(j+2*i-1, A.length-1);
                mergeTake3(A, B, j, mid, right);
            }
            T = A;
            A = B;
            B = T;
        }
    }


    /** Timer class for roughly calculating running time of programs
     *  @author rbk
     *  Usage:  Timer timer = new Timer();
     *          timer.start();
     *          timer.end();
     *          System.out.println(timer);  // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() { if(!ready) { end(); }  return elapsedTime; }

        public long memory()   { if(!ready) { end(); }  return memUsed; }

        public void scale(int num) { elapsedTime /= num; }

        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }

    /** @author rbk : based on algorithm described in a book
     */


    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {

        public static void shuffle(int[] arr) {
            shuffle(arr, 0, arr.length-1);
        }

        public static<T> void shuffle(T[] arr) {
            shuffle(arr, 0, arr.length-1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from  + 1;
            for(int i=1; i<n; i++) {
                int j = random.nextInt(i);
                swap(arr, i+from, j+from);
            }
        }

        public static<T> void shuffle(T[] arr, int from, int to) {
            int n = to - from  + 1;
            Random random = new Random();
            for(int i=1; i<n; i++) {
                int j = random.nextInt(i);
                swap(arr, i+from, j+from);
            }
        }

        static void swap(int[] arr, int x, int y) {
            int tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        static<T> void swap(T[] arr, int x, int y) {
            T tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        public static<T> void printArray(T[] arr, String message) {
            printArray(arr, 0, arr.length-1, message);
        }

        public static<T> void printArray(T[] arr, int from, int to, String message) {
            System.out.print(message);
            for(int i=from; i<=to; i++) {
                System.out.print(" " + arr[i]);
            }
            System.out.println();
        }
    }
}
