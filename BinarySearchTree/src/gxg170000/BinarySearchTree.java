/** @authors
 * Gnaneswar Gandu gxg170000
 * Rutuj Ravindra Puranik rxp180014
 *  Binary search tree
 **/

package gxg170000;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
	    this.left = left;
	    this.right = right;
        }
    }
    
    Entry<T> root;
    int size;

    public BinarySearchTree() {
	root = null;
	size = 0;
    }


    /** TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        Entry<T> temp = get(x, root);
	    return temp!=null;
    }

    /** TO DO: Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {
        Entry<T> temp = get(x, root);
	    return  temp!=null ? temp.element : null ;
    }

    Entry<T> get(T x, Entry<T> ent){
        if(ent==null){
            return null;
        }
        int cmp = x.compareTo(ent.element);
        if(cmp == 0) return ent;
        else if(cmp<0) return  get(x, ent.left);
        else return get(x, ent.right);
    }



    private boolean in = false;

    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        root = add(x, root);
        return in;
    }

    Entry add(T x, Entry<T> ent){
        if(ent==null){
            ent = new Entry<>(x, null,null);
            size++;
            in = true;
            return ent;
        }
        int cmp = x.compareTo(ent.element);
        if(cmp<0) ent.left = add(x, ent.left);
        else if(cmp>0) ent.right = add(x, ent.right);
        else{
            ent = new Entry<>(x, ent.left,ent.right);
        }
        return ent;
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
        if(contains(x)){
            root= remove(x, root);
            size--;
            return x;
        }
        else {
            return null;
        }

    }

    private Entry remove(T x, Entry<T> ent){
        if(ent == null) return null;
        if(x.compareTo(ent.element)<0) ent.left = remove(x,ent.left);
        else if(x.compareTo(ent.element)>0) ent.right = remove(x,ent.right);
        else {
            if(ent.left==null){
                return ent.right;
            }
            else if(ent.right==null){
                return ent.left;
            }
            else{
                ent.element = min(ent.right);
                ent.right = remove(ent.element,ent.right);
            }
        }

        return ent;
    }



    public T min(Entry<T> root) {
        T minValue = root.element;
        while (root.left!=null){
            minValue = root.left.element;
            root = root.left;
        }
        return minValue;
    }

    public T max() {
        return null;
    }

    // TODO: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        inorderT(root);
        for(int i=0;i<size;i++){
            arr[i] = linkQueue.get(i);
        }
        return arr;
    }

    LinkedList<T> linkQueue = new LinkedList<>();

    void inorderT(Entry<T> ent){
        if(ent!=null){
            inorderT(ent.left);
            linkQueue.addLast(ent.element);
            inorderT(ent.right);
        }
    }


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
	return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

// End of Optional problem 2

    public static void main(String[] args) {
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }           
        }
    }


    public void printTree() {
	System.out.print("[" + size + "]");
	printTree(root);
	System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
	if(node != null) {
	    printTree(node.left);
	    System.out.print(" " + node.element);
	    printTree(node.right);
	}
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
