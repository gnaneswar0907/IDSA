/* Starter code for LP3 */

/** Group Members
 *  Meetika Sharma - mxs173530
 *  Ch Muhammad Talal Muneer - cxm180004
 *  Chirag Shahi - cxs180005
 *  Gnaneswar Gandu - gxg170000
 *  */

// Change this to netid of any member of team
package gxg170000;

import java.util.Iterator;
import java.util.Random;

// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> {
    static final int PossibleLevels = 33;

    static class Entry<E> {
        E element;
        Entry<E>[] next;
        Entry<E> prev;

        public Entry(E x, int lev) {
            element = x;
            next = new Entry[lev];
            // add more code if needed
        }

        public E getElement() {
            return element;
        }
    }

    // Dummy header and tail is created
    private Entry<T> head, tail;

    // maxLevel is the level equal to the longest next[]
    public int size, maxLevel;

    // last[i]: Entry at which search came down from level i
    public Entry<T>[] last;

    // for random height (like using coin-flip)
    private Random rand;

    // Constructor
    public SkipList() {
        head = new Entry<T>(null, PossibleLevels);
        tail = new Entry<T>(null, PossibleLevels);
        size = 0;
        maxLevel = 1;
        last = new Entry[PossibleLevels];
        rand = new Random();
        // Each entry in head.next[] points to tail
        for (int i = 0; i < PossibleLevels; i++) {
            head.next[i] = tail;
        }
    }

    // Return the number of elements in the list
    public int size() {
        return size;
    }

    // Is the list empty?
    public boolean isEmpty() {
        return size()==0; // checking if size is zero or not
    }

    // Does list contain x?
    public boolean contains(T x) {
        if(x==null) return false;

        // finding x and updating last accordingly
        find(x);

        return last[0].next[0].element != null && last[0].next[0].element.compareTo(x) == 0;
    }

    // Helper method for to check if the skip list already contains x or not
    public void find(T x){
        Entry<T> ent = head;
        for(int i=PossibleLevels-1;i>=0;i--){
            while (ent.next[i].element != null && ent.next[i].element.compareTo(x) < 0){
                ent = ent.next[i];
            }
            last[i] = ent;
        }
    }

    // Add x to list. If x already exists, reject it. Returns true if new node is added to list
    public boolean add(T x) {
        if(contains(x)){
            return false;
        }
        int level = chooseLevel();
        Entry<T> ent = new Entry(x, level);
        for(int i=0;i<level;i++){
            if(last[i]==null){
                break;
            }
            ent.next[i] = last[i].next[i];
            last[i].next[i] = ent;
        }
        if (ent.next[0] != null) {
            ent.next[0].prev = ent;
        }
        if (last[0].element != null) {
            ent.prev = last[0];
        }
        size++;
	    return true;
    }

    // Helper Method used in add(x)
    public int chooseLevel(){
        int lev = 1 + Integer.numberOfLeadingZeros(rand.nextInt());
        lev = Math.min(lev,  maxLevel + 1);
        if (maxLevel < lev)
            maxLevel = lev;
        return lev;
    }

    // Return first element of list
    public T first() {
        return head.next[0].element; // immediate next of head
    }

    // Return last element of list
    public T last() {
        return tail.prev.element;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {

        // comparing x with the last element. if x is greater than last returning null.
        if(x.compareTo(last()) > 0)
            return null;

        // comparing x with the first element.
        if(x.compareTo(first()) < 0)
            return null;

        // if skip list doesn't contain x. return the very next element from last[0]
        if(!contains(x)){
            return last[0].next[0].element;
        }

        // if list contains x return x
        return x;
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        // comparing x with the last element. if x is greater than last returning null.
        if(x.compareTo(last()) > 0)
            return null;

        // comparing x with the first element.
        if(x.compareTo(first()) < 0)
            return null;

        // if skip list doesn't contain x
        if(!contains(x)){
            return last[0].element;
        }

        // if list contains x return x
        return x;
    }

    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        return null;
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n) {
	    return null;
    }

    // O(n) algorithm for get(n)
    public T getLinear(int n) {
	    return null;
    }

    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
	    return null;
    }

    // Optional operation: Reorganize the elements of the list into a perfect skip list
    // Not a standard operation in skip lists. Eligible for EC.
    public void rebuild() {

    }

    // Optional operation: Eligible for EC.
    // O(log n) expected time for get(n).
    public T getLog(int n) {
        return null;
    }

}
