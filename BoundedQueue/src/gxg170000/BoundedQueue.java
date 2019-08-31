package gxg170000;

public class BoundedQueue<T> {

    Object [] queueArray = null;

    int tail;

    public BoundedQueue(int size){
        queueArray = (T[]) new Object[size];
        tail = 0;
    }

    public int size(){
        return tail;
    }

    public boolean isEmpty(){
        return tail==0;
    }

    public boolean offer(T x){
        if(tail == queueArray.length){
            return false;
        }
        queueArray[tail] = x;
        tail++;
        return true;
    }

    public T[] toArray(T[] a){
        for(int i=0;i<tail;i++){
            a[i] = (T) queueArray[i];
        }

        return a;
    }

    public T peek(){
        if(isEmpty()){
            return null;
        }
        return (T)queueArray[0];
    }

    public T poll(){
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

    public void clear(){
        this.queueArray = (T[]) new Object[0];
        this.tail = 0;
    }

    public static void main(String [] args){
        BoundedQueue<Integer> bqueue = new BoundedQueue<>(5);

        System.out.println(bqueue.isEmpty());
        bqueue.offer(1);
        bqueue.offer(2);
        bqueue.offer(3);
        bqueue.offer(4);
        bqueue.offer(5);
        System.out.println(bqueue.offer(6));


        System.out.println(bqueue.size());

        System.out.println(bqueue.peek());
        System.out.println(bqueue.poll());
        System.out.println(bqueue.peek());
        System.out.println(bqueue.poll());
        System.out.println(bqueue.peek());
        System.out.println(bqueue.poll());
        System.out.println(bqueue.peek());
        System.out.println(bqueue.poll());
        System.out.println(bqueue.peek());
        System.out.println(bqueue.poll());

        bqueue.clear();

        System.out.println(bqueue.isEmpty());
        System.out.println(bqueue.size());
    }

}
