package edu.ttap.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic binary tree implementation.
 */
public class Tree<T extends Comparable<T>> {
    /**
     * A node of the binary tree.
     */
    public static class Node<T> {
        public T value;

        public Node<T> left;

        public Node<T> right;

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

    ///// From the reading...

    private Node<T> root;

    /**
     * Constructs a new, empty binary tree.
     */
    public Tree() {
        this.root = null;
    }

    /**
     * @return a sample binary tree for testing purposes
     */
    public static Tree<Integer> makeSampleTree() {
        Tree<Integer> tree = new Tree<Integer>();
        tree.root = new Node<>(
            5,
            new Node<>(2,
                new Node<>(1),
                new Node<>(3)
            ),
            new Node<>(8,
                new Node<>(7,
                    new Node<>(6),
                    null),
                new Node<>(9,
                    null,
                    new Node<>(10)))
        );
        return tree;
    }


    /**
     * @param node the root of the tree 
     * @return the number elements found in this tree rooted at node
     */
    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /** @return the number of elements in the tree */
    public int size() {
        return sizeH(root);
    }

    ///// Part 1: Contains

    /**
     * @param value the value to search for
     * @param cur current node
     * @return true iff the tree so far contains <code>value</code>
     */
    public boolean containsH(T value, Node<T> cur) {
        if (cur == null) {
            return false;
        } else if (value.compareTo(cur.value) == 0) {
            return true;
        } else if (cur.left != null && containsH(value, cur.left)) {
            return true;
        } else if (cur.right != null && containsH(value, cur.right)) {
            return true;
        }
        return false;
    }

    /**
     * @param value the value to search for
     * @return true iff the tree contains <code>value</code>
     */
    public boolean contains(T value) {
        return containsH(value, root);
    }

    ///// Part 2: Traversals
    
    /**
     * @param node current position in tree
     * @param result current list of elements collected
     */
    
    private void inOrder(Node<T> node, List<T> result) {
        if (node == null) {
            return;
        }

        inOrder(node.left, result);
        result.add(node.value);
        inOrder(node.right, result);
    }

    /**
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        List<T> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void preOrder(Node<T> node, List<T> result) {
        if (node == null) {
            return;
        }

        result.add(node.value);
        preOrder(node.left, result);
        preOrder(node.right, result);
    }

    /**
     * @return the elements of this tree collected via a pre-order traversal
     */
    public List<T> toListPreorder() {
        List<T> result = new ArrayList<>();
        preOrder(root, result);
        return result;
    }

    /**
     * a helper for toListPostorder
     * @param node current position
     * @param result elements collected so far
     */
    private void postOrder(Node<T> node, List<T> result) {
        if (node == null) {
            return;
        }

        postOrder(node.left, result);
        postOrder(node.right, result);
        result.add(node.value);
    }

    /**
     * @return the elements of this tree collected via a post-order traversal
     */
    public List<T> toListPostorder() {
        List<T> result = new ArrayList<>();
        postOrder(root, result);
        return result;
    }

    ///// Part 3: Stringifying Trees
   
    /**
     * @param node the current position
     * @return the string representation so far
     */

    private String toStringHelper(Node<T> node) {
        StringBuffer buf = new StringBuffer("[");
        if (node != null) {

            buf.append(node.value);
            buf.append(toStringHelper(node.left));
            buf.append(toStringHelper(node.right));

        } else {

            return "";
            
        }
        buf.append("]");
        return buf.toString();
    }

    /**
     * @return a string represent of this tree in the form, "[x1, ..., xk]."
     * The order of the elements is left unspecified.
     */
    @Override
    public String toString() {
        return toStringHelper(root);
    }
    

    ///// Extra: Pretty Printing
    
    /**
     * @return a string representation of this tree in bulleted list form.
     */
    public String toPrettyString() {
        throw new UnsupportedOperationException();
    }

    /**
     * The main driver for this program
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Nothing to do. 'Run' via the JUnit tests instead!");
    }
}