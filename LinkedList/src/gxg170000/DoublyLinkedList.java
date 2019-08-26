/**
 * @author gnaneswar
 * Doubly Linked List
 * Ver 1.0: 2019/08/24
 */

package gxg170000;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {

    /**
     * Class Entry holds a single node of the list
     * Extends the Entry class of Single Linked List
     */
    static class Entry<E> extends SinglyLinkedList.Entry<E>{
        Entry<E> prev, next;
        public Entry(E e, Entry<E> nxt, Entry<E> prev){
            super(e,null);
            this.prev = prev;
            this.next = nxt;
        }
    }

    Entry<T> head, tail;

    public DoublyLinkedList(){
        head = new Entry<>(null,null,null);
        tail = null;
    }

    public DLLIterator dllIterator(){
        return new DLLIterator();
    }

    private class DLLIterator extends SLLIterator{
        Entry<T> cursor, next, prev;

        public DLLIterator(){
            cursor = head;
            next = null;
            prev = null;
        }

        public boolean hasPrev(){
            return cursor.prev != null;
        }

        public boolean hasNext(){
            return cursor.next != null;
        }

        public T prev(){
            next = cursor;
            cursor = cursor.prev;
            ready = true;
            return cursor.element;
        }

        public T next() {
            prev = cursor;
            cursor = cursor.next;
            ready = true;
            return cursor.element;
        }

        public void add(T x){
            Entry<T> e = new Entry<>(x, null, null);
            Entry<T> e1 = cursor.next;
            cursor.next = e;
            e.prev = cursor;
            e.next = e1;
            e1.prev = e;
        }

        public void remove(){
            if (!ready) {
                throw new NoSuchElementException();
            }
            prev.next = cursor.next;
            // Handle case when tail of a list is deleted
            if (cursor == tail) {
                tail = prev;
            }
            else{
                cursor.next.prev = prev;
            }
            cursor = prev;
            ready = false;  // Calling remove again without calling next will result in exception thrown
            size--;

        }
    }

    public void add(T x) {
        add(new Entry<>(x, null, null));
    }

    private void add(Entry<T> ent) {
        if(head.element == null) {head = ent; tail = ent; size++;}
        else{
            tail.next = ent;
            ent.prev = tail;
            tail = tail.next;
            size++;
        }
    }

    public void printList() {

        System.out.print(this.size + ": ");

        if(head == null) return;
        Entry<T> ent = head;
        while(ent!=null){
            System.out.print(ent.element+" ");
            ent = ent.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {
        int n = 10;
        DoublyLinkedList<Integer> dlst = new DoublyLinkedList<>();

        for(int i=0;i<n;i++){
            dlst.add(Integer.valueOf(i+1));
        }

        dlst.printList();

        DoublyLinkedList.DLLIterator it = dlst.dllIterator();

        Scanner in = new Scanner(System.in);

        System.out.println(dlst.dllIterator().hasNext());

        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:  // Move to next element and print it
                    if (it.hasNext()) {
                        System.out.println(it.next());
                    } else {
                        break;
                    }
                    break;
                case 2:  // Remove element
                    if (it.hasPrev()) {
                        System.out.println(it.prev());
                    } else {
                        break;
                    }
                    break;
                case 3:
                    it.remove();
                    dlst.printList();
                    break;
                case 13:
                    it.add(Integer.valueOf(com));
                    dlst.printList();
                    break;
                case 4:
                    dlst.printList();
                    break;
                default:  // Exit loop
                    break;
            }
        }

        System.out.println(dlst.dllIterator());
    }
}
