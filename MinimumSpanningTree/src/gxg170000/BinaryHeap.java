// Starter code for SP9

// Change to your netid
package gxg170000;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class BinaryHeap<T extends Comparable<? super T>> {
    Comparable[] pq; // Priority Queue
    int size; // current size of Priority Queue

    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(int maxCapacity) {
        pq = new Comparable[maxCapacity];
        size = 0;
    }
    // add method: resize pq if needed
    public boolean add(T x) {
        // resize() PQ
        if(size()>=pq.length){
            resize();
        }
        // Adding to the leaf
        move(size, x);
        percolateUp(size); // Moving to the appropriate place
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
        if(size() == 0) return null;
        // The first element which is to be removed
        T min = min();
        move(0, pq[--size]);

        // Moving newly added element to appropriate place
        percolateDown(0);

        return min;
    }

    //Returns the top element of the heap (or head of the queue).
    public T min() { 
	return peek();
    }

    // return null if pq is empty
    public T peek() {
        return (T)pq[0];
    }

    // index of parent
    int parent(int i) {
	return (i-1)/2;
    }

    // index of left child
    int leftChild(int i) {
	return 2*i + 1;
    }

    // index of right child
    int rightChild(int i) {
        return 2*i + 2;
    }

    // helper function for swapping items
    void swap(int a, int b) {
        Comparable temp;
        temp = pq[a];
        pq[a] = pq[b];
        pq[b] = temp;
    }

    /**
     * pq[index] may violate heap order with parent
     * Move the element[index] up in the heap, at it's appropriate place.
     * */
    void percolateUp(int index) {
        Comparable<? super T> x = pq[index];
        //pq[index] may violate heap order with parent***
        while (0 < index && (compare(x, pq[parent(index)]) < 0)) {
            move(index, pq[parent(index)]); // pq[index] = pq[parent(index)];
            index = parent(index);
        }
        move(index, x); // pq[index] = x;
    }

    /**
     * pq[index] may violate heap order with children
     * Move the element[index] down in the heap, at it's appropriate place.
     * */
    void percolateDown(int index) {
        Comparable<? super T> x = pq[index];
        int c = leftChild(index); // (2 * index) + 1;

        // pq[i] may violate heap order with children***
        while (c <= size - 1) {
            // right child has higher priority
            if (c < (size - 1) && (compare(pq[c], pq[c + 1]) > 0)) { c++; }

            if (compare(x, pq[c]) <= 0) { break; }

            move(index, pq[c]); // pq[index] = pq[c];
            index = c;
            c = leftChild(index); // 2 * index + 1;
        }
        move(index, x); // pq[index] = x;
    }

	/** use this whenever an element moved/stored in heap. Will be overridden by IndexedHeap */
    void move(int dest, Comparable x) {
	    pq[dest] = x;
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
    
    public interface Index {
        public void putIndex(int index);
        public int getIndex();
    }

    public static class IndexedHeap<T extends Index & Comparable<? super T>> extends BinaryHeap<T> {
        /** Build a priority queue with a given array */
        IndexedHeap(int capacity) {
            super(capacity);
	}

        /** restore heap order property after the priority of x has decreased */
        void decreaseKey(T x) {
            int index = x.getIndex();
            percolateUp(index);
        }

	@Override
        void move(int i, Comparable x) {
            super.move(i, x);
            T newItem = (T) x;
            newItem.putIndex(i);
        }
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
