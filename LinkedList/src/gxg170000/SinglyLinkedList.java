/**
 * @author gnaneswar
 * Singly linked list
 * Ver 1.0: 2019/08/24
 * Entry class has generic type associated with it, to allow inheritance.
 */

package gxg170000;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SinglyLinkedList<T> implements Iterable<T> {

    // Dummy header is used.  tail stores reference of tail element of list
    Entry<T> head, tail;
    int size;

    public SinglyLinkedList() {
        head = new Entry<>(null, null);
        tail = head;
        size = 0;
    }

    public Iterator<T> iterator() {
        return new SLLIterator();
    }

    // Add new elements to the end of the list
    public void add(T x) {
        add(new Entry<>(x, null));
    }

    public void add(Entry<T> ent) {
        tail.next = ent;
        tail = tail.next;
        size++;
    }

    public void addFirst(T x){
        addFirst(new Entry<T>(x, null));
    }

    private void addFirst(Entry<T> ent){
        ent.next = head;
        head = ent;
        size++;
    }

    public T removeFirst(){
        if(head==null) return null;
        head = head.next;
        return head.element;
    }

    public T remove(T x){

        Entry<T> ent = head;
        Entry<T> prev = null;
        if(ent==null){
            throw new NoSuchElementException();
        }
        while(ent!=null){
            if(ent.element==x){
                if(prev==null){
                    return removeFirst();
                }
                prev.next = ent.next;
                ent.next = null;
                return ent.element;
            }
            ent = ent.next;
            prev = ent;
        }

        return x;
    }

    public void printList() {
        System.out.print(this.size + ": ");
        for (T item : this) {
            System.out.print(item + " ");
        }

        System.out.println();
    }

    public void printValues(Entry<T> ent){
        if(ent==null){
            System.out.println("empty");
        }
        else {
            while(ent!=null){
                System.out.print(ent.element+" ");
                ent = ent.next;
            }
        }
    }

    // Rearrange the elements of the list by linking the elements at even index
    // followed by the elements at odd index. Implemented by rearranging pointers
    // of existing elements without allocating any new elements.
    public void unzip() {
        if (size < 3) {  // Too few elements.  No change.
            return;
        }

        Entry<T> tail0 = head.next;
        Entry<T> head1 = tail0.next;
        Entry<T> tail1 = head1;
        Entry<T> c = tail1.next;
        int state = 0;

        // Invariant: tail0 is the tail of the chain of elements with even index.
        // tail1 is the tail of odd index chain.
        // c is current element to be processed.
        // state indicates the state of the finite state machine
        // state = i indicates that the current element is added after taili (i=0,1).
        while (c != null) {
            if (state == 0) {
                tail0.next = c;
                tail0 = c;
                c = c.next;
            } else {
                tail1.next = c;
                tail1 = c;
                c = c.next;
            }
            state = 1 - state;
        }
        tail0.next = head1;
        tail1.next = null;
        // Update the tail of the list
        tail = tail1;
    }

    public Entry<T> reverseList(){
        Entry<T> ent = head;
        Entry<T> prev=null, next=null;
        if(ent==null || ent.next==null){
            return ent;
        }
        while(ent!=null){
            next = ent.next;
            ent.next = prev;
            prev = ent;
            ent = next;
        }
        return prev;
    }

    /**
     * Class Entry holds a single node of the list
     */
    static class Entry<E> {
        E element;
        Entry<E> next;

        Entry(){}

        Entry(E x, Entry<E> nxt) {
            element = x;
            next = nxt;
        }
    }

    protected class SLLIterator implements Iterator<T> {
        Entry<T> cursor, prev;
        boolean ready;  // is item ready to be removed?

        SLLIterator() {
            cursor = head;
            prev = null;
            ready = false;
        }

        public boolean hasNext() {
            return cursor.next != null;
        }

        public T next() {
            prev = cursor;
            cursor = cursor.next;
            ready = true;
            return cursor.element;
        }

        // Removes the current element (retrieved by the most recent next())
        // Remove can be called only if next has been called and the element has not been removed
        public void remove() {
            if (!ready) {
                throw new NoSuchElementException();
            }
            prev.next = cursor.next;
            // Handle case when tail of a list is deleted
            if (cursor == tail) {
                tail = prev;
            }
            cursor = prev;
            ready = false;  // Calling remove again without calling next will result in exception thrown
            size--;
        }
    }  // end of class SLLIterator

    public static void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        for (int i = 1; i <= n; i++) {
            lst.add(Integer.valueOf(i));
        }
        lst.printList();

        Entry<Integer> reversedHead = lst.reverseList();
        lst.printValues(reversedHead);

        Iterator<Integer> it = lst.iterator();
        Scanner in = new Scanner(System.in);
        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:  // Move to next element and print it
                    if (it.hasNext()) {
                        System.out.println(it.next());
                    } else {
                        break whileloop;
                    }
                    break;
                case 2:  // Remove element
                    it.remove();
                    lst.printList();
                    break;
                default:  // Exit loop
                    break whileloop;
            }
        }
        lst.printList();
        lst.unzip();
        lst.printList();
    }


}