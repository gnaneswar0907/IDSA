
/**
 * @authors Gnaneswar Gandu - gxg170000, Rutuj Ravindra Puranik - rxp180014
 * Bounded Queue
 * Ver 1.0: 2019/09/05
 */

package gxg170000;

import java.util.Scanner;

public class BoundedQueue<T> {

    Object [] queueArray = null;

    int tail;

    // Constructor for queue of given size
    public BoundedQueue(int size){
        queueArray = (T[]) new Object[size];
        tail = 0;
    }

    // return the number of elements in the queue
    int size(){
        return tail;
    }

    // check if the queue is empty
    boolean isEmpty(){
        return tail==0;
    }

    // add a new element x at the rear of the queue
    // returns false if the element was not added because the queue is full
    boolean offer(T x){
        if(tail == queueArray.length){
            return false;
        }
        queueArray[tail] = x;
        tail++;
        return true;
    }

    // void toArray(T[] a):fill user supplied array with the elements of the queue,
    // in queue order
    T[] toArray(T[] a){
        if(isEmpty()){
            return null;
        }
        for(int i=0;i<tail;i++){
            a[i] = (T) queueArray[i];
        }
        return a;
    }

    // return front element, without removing it (null if queue is empty)
    T peek(){
        if(isEmpty()){
            return null;
        }
        return (T)queueArray[0];
    }

    // remove and return the element at the front of the queue
    // return null if the queue is empty
    T poll(){
        if(isEmpty()){
            return null;
        }
        T temp = (T) queueArray[0];
        for(int i=0; i<tail-1;i++){
            queueArray[i] = queueArray[i+1];
        }
        tail--;
        return temp;
    }

    // void clear(): clear the queue (size=0)
    void clear(){
        this.queueArray = (T[]) new Object[0];
        this.tail = 0;
    }

    public static void main(String [] args){

        int size = 5;

        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
        }

        BoundedQueue<Integer> q = new BoundedQueue<>(size);

        Scanner in = new Scanner(System.in);

        whileloop: while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1: // Offer (Add element to queue)
                    System.out.print("Enter element to add: ");
                    System.out.println((q.offer(in.nextInt())) ? "Element Added" : "Element Not Added");
                    break;
                case 2: // Poll (Remove element from queue)
                    System.out.println("Element removed: " + q.poll());
                    break;
                case 3: // Peek (Display first element of queue)
                    System.out.println("Peek: " + q.peek());
                    break;
                case 4: // Size (Display size of queue)
                    System.out.println("Size: " + q.size());
                    break;
                case 5: // isEmpty (Check if queue is empty)
                    System.out.println("isEmpty : " + q.isEmpty());
                    break;
                case 6: // Clear (clear the queue)
                    q.clear();
                    System.out.println("Queue cleared!");
                    break;
                case 7: // toArray (Fill user supplied array with the elements of the queue, in queue
                    // order)
                    Integer[] b = new Integer[5];
                    if(q.toArray(b)!=null){
                        for(int i=0;i<q.size();i++){
                            System.out.print(b[i]+" ");
                        }
                        System.out.println("");
                    }
                    else{
                        System.out.println("Queue is Empty");
                    }
                    break;
                default: // Exit loop
                    break whileloop;
            }
        }
    }

}
