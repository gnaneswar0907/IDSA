package gxg170000;

public class BoundedStack<T> {

    Object[] stackArray = null;
    int tail;

    public BoundedStack(int size){
        stackArray = (T[]) new Object[size];
        tail = 0;
    }

    public boolean isEmpty(){
        return tail==0;
    }

    public boolean isFull(){
        return tail==stackArray.length;
    }

    public T peek(){
        if(isEmpty()) return null;
        return (T)stackArray[0];
    }

    public T pop(){
        if(isEmpty()){
            return null;
        }
        T headElement =  (T)stackArray[0];

        for(int i=0;i<tail-1;i++){
            stackArray[i] = stackArray[i+1];
        }
        tail--;
        return headElement;
    }

    public boolean push(T x){
        if(isFull()){
            return false;
        }

        for(int i=tail-1;i>=0;i--){
            stackArray[i+1] = stackArray[i];
        }
        stackArray[0] = x;
        tail++;
        return true;
    }

    public static void main(String [] args){
        BoundedStack<Integer> bstack = new BoundedStack<>(5);

        System.out.println(bstack.isEmpty());

        bstack.push(1);
        bstack.push(2);
        bstack.push(3);
        bstack.push(4);
        bstack.push(5);

        System.out.println(bstack.isFull());

        System.out.println(bstack.peek());
        System.out.println(bstack.pop());
        System.out.println(bstack.peek());
        System.out.println(bstack.pop());

        System.out.println(bstack.push(6));
    }
}
