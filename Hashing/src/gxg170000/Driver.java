package gxg170000;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

/**
 * Short Project SP08: Hashing
 * @authors
 * Gnaneswar Gandu (gxg170000)
 * Rutuj Ravindra Puranik (rxp180014)
 */


public class Driver {
    public static Random random = new Random();
    public static int numTrials = 10;

    /**
     * Calculate distinct elements in an array
     */
    public static int distinctElements(Integer[] arr){
        Set<Integer> hs = new HashSet<>();
        for (Integer e : arr) { hs.add(e); }
        return hs.size();
    }

    public static void main(String[] args) {
        int Million = 1000000;
        int choice = 0;

        Scanner sc =new Scanner(System.in);

        while(true){
            System.out.println("Enter the choice :\n 1. Double Hashing\n 2. Java HashSet ");

            choice = sc.nextInt();

            System.out.println("Enter the size in Millions: ");

            int n = (sc.nextInt())*Million;

            Integer[] arr = new Integer[n];
            for (int i = 0; i < n; i++) {
                arr[i] = random.nextInt(n);
            }

            Timer timer = new Timer();

            switch (choice) {
                case 1:
                    for (int i = 0; i < numTrials; i++) {
                        Shuffle.shuffle(arr);
                        DoubleHashing.distinctElements(arr);
                    }
                    break;
                default:
                    for (int i = 0; i < numTrials; i++) {
                        Shuffle.shuffle(arr);
                        distinctElements(arr);
                    }
                    break;
            }
            timer.end();
            timer.scale(numTrials);
            String impl = choice == 1 ? "Double Hashing" : "Java HashSet";
            System.out.println("Choice: " + impl + "\n" + timer);
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
