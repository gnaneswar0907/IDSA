
/** Starter code for Red-Black Tree
 * @authors
 * Gnaneswar Gandu gxg170000
 * Rutuj Ravindra Puranik rxp180014
 */
package gxg170000;

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    static class Entry<T> extends BinarySearchTree.Entry<T> {
        boolean color;
        Entry<T> left, right, parent;
        Entry(T x, Entry<T> left, Entry<T> right, Entry<T> parent) {
            super(x, left, right);
            this.parent = parent;
            color = RED;
        }

        boolean isRed() {
	    return color == RED;
        }

        boolean isBlack() {
	    return color == BLACK;
        }
    }

    Entry<T> root; // Root Entry for the Tree


    RedBlackTree() {
	    super();
	    root = null;
    }

    /** Add x to tree.
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean addRB(T x){
        if(contains(x)){
            return false;
        }
        else{
            addRB(new Entry<>(x, null, null, null));
            size++;
            return true;
        }
    }

    /** Helper method to add an element in the tree */
    void addRB(Entry<T> ent){
        Entry<T> temp = root;
        if(root == null){
            root = ent;
            ent.color = BLACK;
            ent.parent = null;
        }
        else{
            ent.color = RED;
            while (true){
                int cmp = ent.element.compareTo(temp.element);
                if(cmp<0){
                    if(temp.left==null){
                        temp.left = ent;
                        ent.parent = temp;
                        break;
                    }
                    else{
                        temp = temp.left;
                    }
                }
                else if(cmp > 0){
                    if(temp.right==null){
                        temp.right = ent;
                        ent.parent = temp;
                        break;
                    }
                    else{
                        temp = temp.right;
                    }
                }
            }
        }
        fixTree(ent);
    }

    /** Helper method to fix the tree by rotating a node left or right */
    public void fixTree(Entry<T> ent){
        while (ent.parent !=null && ent.parent.color == RED){
            Entry<T> uncle = null;
            if(ent.parent == ent.parent.parent.left){
                uncle = ent.parent.parent.right;

                if(uncle!=null && uncle.color == RED){
                    ent.parent.color = BLACK;
                    uncle.color = BLACK;
                    ent.parent.parent.color = RED;
                    ent = ent.parent.parent;
                    continue;
                }

                if(ent == ent.parent.right){
                    ent = ent.parent;
                    rotateLeft(ent);
                }

                ent.parent.color = BLACK;
                ent.parent.parent.color = RED;
                rotateRight(ent.parent.parent);
            }
            else {
                uncle = ent.parent.parent.left;

                if(uncle!=null && uncle.color == RED){
                    ent.parent.color = BLACK;
                    uncle.color = BLACK;
                    ent.parent.parent.color = RED;
                    ent = ent.parent.parent;
                    continue;
                }

                if(ent == ent.parent.left){
                    ent = ent.parent;
                    rotateRight(ent);
                }

                ent.parent.color = BLACK;
                ent.parent.parent.color = RED;
                rotateLeft(ent.parent.parent);
            }
        }

        root.color = BLACK;
    }

    /** Helper method to rotate the node left */
    public void rotateLeft(Entry<T> ent){
        if(ent.parent!=null){
            if(ent == ent.parent.left){
                ent.parent.left = ent.right;
            }
            else{
                ent.parent.right = ent.right;
            }
            ent.right.parent = ent.parent;
            ent.parent = ent.right;
            if(ent.right.left!=null){
                ent.right.left.parent = ent;
            }
            ent.right = ent.right.left;
            ent.parent.left = ent;
        }
        else {
            Entry<T> right = root.right;
            root.right = right.left;
            if(right.left!=null) right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = null;
            root = right;
        }
    }

    /** Helper method to rotate the node right */
    public void rotateRight(Entry<T> ent){
        if(ent.parent!=null){
            if(ent == ent.parent.left){
                ent.parent.left = ent.left;
            }
            else {
                ent.parent.right = ent.left;
            }
            ent.left.parent = ent.parent;
            ent.parent = ent.left;
            if(ent.left.right != null){
                ent.left.right.parent = ent;
            }
            ent.left = ent.left.right;
            ent.parent.right = ent;
        }
        else {
            Entry<T> left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = null;
            root = left;
        }
    }

    /** Find if tree contains value x
     */
    public boolean contains(T x){
        return super.get(x,root) != null;
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.addRB(2);
        rbt.addRB(4);
        rbt.addRB(6);
        rbt.addRB(1);
        rbt.addRB(3);
        rbt.addRB(5);

        System.out.println(rbt.contains(9));
        System.out.println(rbt.contains(4));
    }
}

