package edu.ttap.bsts;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading

    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {
        public T value;

        public Node<T> left;

        public Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;

            this.left = left;

            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        public Node(T value) {
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

    ///// Part 1: Insertion
    
    /**
     * helper for insert
     * @param v the value to insert
     * @param cur current node
     * @return root node of tree so far
     */
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

    /**
     * Inserts the given value into this binary search tree.
     * @param v the value to insert
     */
    public void insert(T v) {
        root = insertH(v, root);
    }

    ///// Part 2: Contains
    
    /**
     * helper for contains
     * @param v the value to find
     * @param node current node
     * @return true iff the tree so far contains node
     */

    private boolean containsH(Node<T> node, T v) {
        if (node == null) {
            return false;
        }

        if (node.value == v) {
            return true;
        }

        if (v.compareTo(node.value) > 0) {
            return containsH(node.right, v);
        }

        return containsH(node.left, v);
    }
   
    /**
     * @param v the value to find
     * @return true iff this tree contains <code>v</code>
     */
    public boolean contains(T v) {
        return containsH(root, v);
    }

    ///// Part 3: Ordered Traversals
    
    /**
     * @param node the current position
     * @return the string representation of the tree so far
     */
    private String toStringHelper(Node<T> node) {
        StringBuffer buf = new StringBuffer("[");
        if (node != null) {
            buf.append(toStringHelper(node.left));
            buf.append(node.value);
            buf.append(toStringHelper(node.right));
            buf.append(", ");

        } else {
            return "";
        }
        buf.append("]");
        return buf.toString();
    }

    /**
     * @return the (linearized) string representation of this BST
     */
    @Override
    public String toString() {
        return toStringHelper(root);
    }

    private ArrayList<T> toListHelper(Node<T> node, ArrayList<T> buf) {
        if (node != null) {
            toListHelper(node.left, buf);
            buf.add(node.value);
            toListHelper(node.right, buf);

        } else {
            return null;
        }
        return buf;
    }

    /**
     * @return a list contains the elements of this BST in-order.
     */
    public List<T> toList() {
        ArrayList<T> buf = new ArrayList<>();
        return toListHelper(root, buf);
    }

    ///// Part 4: BST Sorting

    /**
     * @param <T> the carrier type of the lists
     * @param lst the list to sort
     * @return a copy of <code>lst</code> but sorted
     * @implSpec <code>sort</code> runs in ___ time if the tree remains balanced. 
     */

    public static <T extends Comparable<? super T>> List<T> sort(List<T> lst) {
        BinarySearchTree<T> tree = new BinarySearchTree<T>();
        for (T v : lst) {
            tree.insert(v);
        }
        return tree.toList();
    }

    ///// Part 5: Deletion
  
    /*
     * The three cases of deletion are:
     * 1. node has 0 children
     * 2. node has 1 child (l or r)
     * 3. node has 2 children
     */

    /**
     * helper for delete
     * @param cur
     * @return previous node
     */
    private Node<T> getPrev(Node<T> cur) {
        cur = cur.right;
        while (cur != null && cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    /**
     * helper for delete
     * @param node the current node
     * @param value the value to delete
     * @return node so far
     */
    public Node<T> deleteH(Node<T> node, T value) {

        if (node == null) {
            return node;
        }

        if (value.compareTo(node.value) < 0) {
            node.left = deleteH(node.left, value);
        } else if (value.compareTo(root.value) > 0) {
            node.right = deleteH(node.right, value);
        } else {
            if (node.left == null) { 
                return node.right;
            }
            if (node.right == null) { 
                return node.left; 
            }

            // Node with 2 children
            Node<T> prev = getPrev(node);
            node.value = prev.value;
            node.right = deleteH(node.right, prev.value);
        }
        return node;
    }

    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code> found
     * in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        root = deleteH(root, value);
    }
}
