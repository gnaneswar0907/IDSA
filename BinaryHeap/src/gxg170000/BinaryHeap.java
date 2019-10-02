// Starter code for SP5

// Change to your netid
package gxg170000;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class BinaryHeap<T extends Comparable<? super T>> {
    Comparable[] pq;
    int size;

    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(int maxCapacity) {
	pq = new Comparable[maxCapacity];
	size = 0;
    }

    // add method: resize pq if needed
    public boolean add(T x) {
        if(size()>=pq.length){
            resize();
        }
        pq[size] = x;
        int current = size;
        percolateUp(current);
        size++;
	    return true;
    }

    public boolean offer(T x) {
	    return add(x);
    }

    // throw exception if pq is empty
    public T remove() throws NoSuchElementException {
	T result = poll();
	if(result == null) {
	    throw new NoSuchElementException("Priority queue is empty");
	} else {
	    return result;
	}
    }

    // return null if pq is empty
    public T poll() {
        if(size() == 0){
            return null;
        }
        T popped = (T) pq[0];
        pq[0] = pq[size-1];
        size--;
        percolateDown(0);
	return popped;
    }
    
    public T min() { 
	return peek();
    }

    // return null if pq is empty
    public T peek() {
	return (T)pq[0];
    }

    int parent(int i) {
	    return (i-1)/2;
    }

    int leftChild(int i) {
	    return 2*i + 1;
    }

    int rightChild(int i) {
        return 2*i + 2;
    }

    void swap(int a, int b) {
        Comparable temp;
        temp = pq[a];
        pq[a] = pq[b];
        pq[b] = temp;
    }

    /** pq[index] may violate heap order with parent */
    void percolateUp(int index) {
//        System.out.println(compare(pq[index], parent(index)));
        while (compare(pq[index], pq[parent(index)]) < 0){
            swap(index, parent(index));
            index = parent(index);
        }
    }

    /** pq[index] may violate heap order with children */
    void percolateDown(int index) {
        // If the node is not a leaf node and greater than any of its child
        if(!isALeaf(index)){
            if ((compare(pq[index], pq[leftChild(index)]) > 0) || (compare(pq[index], pq[rightChild(index)]) > 0)) {

                // Swap with the left child and percolate down
                if (compare(pq[leftChild(index)], pq[rightChild(index)]) < 0) {
                    swap(index, leftChild(index));
                    percolateDown(leftChild(index));
                }

                // Swap with the right child and percolate down
                else {
                    swap(index, rightChild(index));
                    percolateDown(rightChild(index));
                }
            }
        }
    }

    boolean isALeaf(int index) {
        if(index>=(size()/2) && index<=size()){
            return true;
        }
        return false;
    }


    int compare(Comparable a, Comparable b) {
	    return ((T) a).compareTo((T) b);
    }
    
    /** Create a heap.  Precondition: none. */
    void buildHeap() {
	for(int i=parent(size-1); i>=0; i--) {
	    percolateDown(i);
	}
    }

    public boolean isEmpty() {
	return size() == 0;
    }

    public int size() {
	return size;
    }

    // Resize array to double the current size
    void resize() {
        Comparable[] newpq = new Comparable[pq.length*2];
        for(int i=0;i<pq.length;i++){
            newpq[i] = pq[i];
        }

        this.pq = newpq;
    }

    public static void main(String[] args) {
	Integer[] arr = {0,9,7,5,3,1,8,6,4,2};
	BinaryHeap<Integer> h = new BinaryHeap(arr.length);

	System.out.print("Before:");
	for(Integer x: arr) {
	    h.offer(x);
	    System.out.print(" " + x);
	}
	System.out.println();

	for(int i=0; i<arr.length; i++) {
	    arr[i] = h.poll();
	}

	System.out.print("After :");
	for(Integer x: arr) {
	    System.out.print(" " + x);
	}
	System.out.println();
    }
}
