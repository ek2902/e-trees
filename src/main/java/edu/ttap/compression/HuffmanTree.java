package edu.ttap.compression;

/**
 * A HuffmanTree derives a space-efficient coding of a collection of byte
 * values.
 *
 * The huffman tree encodes values in the range 0--255 which would normally
 * take 8 bits.  However, we also need to encode a special EOF character to
 * denote the end of a .grin file.  Thus, we need 9 bits to store each
 * byte value.  This is fine for file writing (modulo the need to write in
 * byte chunks to the file), but Java does not have a 9-bit data type.
 * Instead, we use the next larger primitive integral type, short, to store
 * our byte values.
 */
public class HuffmanTree {

    /**
     * A node of the binary tree.
     */
    public static class Node<T> {
        public T value;

        public Node<T> left;

        public Node<T> right;

        /**
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(Node<T> left, Node<T> right) {
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this.value = value;
        }
    }

    Node<Integer> root;

    /**
     * helper for HuffmanTree
     * @param in the input file (as a BitInputStream)
     * @return tree so far
     */
    public Node<Integer> huffmanHelper(BitInputStream in) {
        int curBit = in.readBit();
        if (curBit == 1) {
            return new Node<Integer>(huffmanHelper(in), huffmanHelper(in));
        } else {
            return new Node<Integer>(in.readBits(9));
        }
    }

    /**
     * Constructs a new HuffmanTree from the given file.
     * @param in the input file (as a BitInputStream)
     */
    public HuffmanTree(BitInputStream in) {
        this.root = huffmanHelper(in);
    }

    /**
     * Decodes a stream of huffman codes from a file given as a stream of
     * bits into their uncompressed form, saving the results to the given
     * output stream. Note that the EOF character is not written to out
     * because it is not a valid 8-bit chunk (it is 9 bits).
     * @param in the file to decompress.
     * @param out the file to write the decompressed output to.
     */
    public void decode(BitInputStream in, BitOutputStream out) {
        Node<Integer> cur = root;
        int curBit = 0;
        
        while (in.hasBits()) {
            while (cur.value == null) {
                curBit = in.readBit();
                if (curBit == 1) {
                    cur = cur.right;
                } else if (curBit == 0) {
                    cur = cur.left;
                }
            }
            System.out.println("am writing out :)");
            if (Integer.toBinaryString(cur.value).length() == 9) {
                return;
            }
            out.writeBits(cur.value, 8);
            cur = root;
        }
    }
}
