package edu.grinnell.csc207.bsts;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading

    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * @param node the root of the tree
     * @return the number of elements in the specified tree
     */
    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in this tree
     */
    public int size() {
        return sizeH(root);
    }

    /** @return the updated tree after inserting h into the given tree */
    private Node<T> insertH(T v, Node<T> cur) {
        if (cur == null) {
            return new Node<>(v);
        } else {
            if (v.compareTo(cur.value) < 0) {
                cur.left = insertH(v, cur.left);
            } else {
                cur.right = insertH(v, cur.right);
            }
            return cur;
            }
        }
        public void insert(T v) { root = insertH(v, root);
    }


    public boolean containsHelper(Node<T> n, T value) {
        if(n == null) {
            return false;
        }
        int comp = n.value.compareTo(value);
        if(comp == 0) {
            return true;
        } else if(comp > 0) {
            return containsHelper(n.left, value);
        }
        return containsHelper(n.right, value);
        
    }

    ///// Part 1: Contains
    /**
       The contains method will check for the given value by first checking if the tree is empty,
     * then checking whether the value is less than or greater then to the value in root, then either descending
     * down the left or right branch. If the current node is equal to null, then the function returns false. If the current
     * node has the desired value, the method returns true.
     * @param value the value to search for
     * 
     * @param v the value to find
     * @return true iff this tree contains <code>v</code>
     */
    public boolean contains(T v) {
        return containsHelper(root, v);
    }

    ///// Part 2: Ordered Traversals

   
    public void toStringHelper(StringBuffer l, Node<T> n) {
        if(n != null) {
            toStringHelper(l, n.left);
            l.append(n.value);
            l.append(", ");
            toStringHelper(l, n.right);
        }
    }

     /**
     * @return the (linearized) string representation of this BST
     */
    public String toString() {
        StringBuffer str = new StringBuffer("[");
        if(root != null){
            toStringHelper(str, root);
            str.delete(str.length()-2, str.length());
        }
        str.append("]");
        return str.toString();
    }


    public void toListHelper(List<T> l, Node<T> n) {
        if(n != null) {
            toListHelper(l, n.left);
            l.add(n.value);
            toListHelper(l, n.right);
        }
    }

    /**
     * @return a list contains the elements of this BST in-order.
     */
    public List<T> toList() {
        List<T> l = new ArrayList<T>();
        toListHelper(l, root);
        return l;
    }

    ///// Part 3: BST Sorting

    public static <T extends Comparable<? super T>> void sortHelper(List<T> lst, Node<T> tree) {

    }

    /**
     * @param <T> the carrier type of the lists
     * @param lst the list to sort
     * @return a copy of <code>lst</code> but sorted
     * @implSpec <code>sort</code> runs in ___ time if the tree remains balanced. 
     */
    public static <T extends Comparable<? super T>> List<T> sort(List<T> lst) {
        BinarySearchTree<T> tree = new BinarySearchTree<T>();
        for(int i = 0; i < lst.size(); i++){
            tree.insert(lst.get(i));
        }
        return tree.toList();
    }

    ///// Part 4: Deletion
  
    /*
     * The three cases of deletion are:
     * 1. if curr_node.left is null, curr_node is set to node.right
     * 2. if curr_node.right is null, curr_node is set to node.left
     * 3. if neither is null, sets curr_node to be 
     */

     public void deleteH(Node<T> curr_node){
        Node<T> temp1 = curr_node;
        Node<T> temp = curr_node.right;
        temp1 = temp1.left;
        while(temp1.right != null){
            temp1 = temp1.right;
        }
        temp1.right = temp;

        curr_node = curr_node.left;
     }

    public void deleteRecur(Node<T> n, T value) throws IllegalArgumentException{
        if(n == null) {
            return;
        }
        int comp = n.value.compareTo(value);
        if(comp == 0) {
            if(n.right == null && n.left == null){
                n = null;
            } else if(n.right == null) {
                n = n.left;
            } else if(n.left == null){
                n = n.right;
            } else {
                deleteH(n);

            }
            return;
        } else if(comp > 0) {
            deleteRecur(n.left, value);
        }
            deleteRecur(n.right, value);
        
    }
    

    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code> found
     * in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        deleteRecur(root, value);
    }
}
