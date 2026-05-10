package edu.ttap.compression;

import java.io.IOException;

/**
 * The driver for the Grin compression program.
 */
public class Grin {
    /**
     * Decodes the .grin file denoted by infile and writes the output to the
     * .grin file denoted by outfile.
     * @param infile the file to decode
     * @param outfile the file to ouptut to
     */
    public static void decode(String infile, String outfile) throws IOException {
        BitInputStream in = new BitInputStream(infile);
        BitOutputStream out = new BitOutputStream(outfile);
        
        if (in.readBits(32) != 1846) {
            throw new IllegalArgumentException();
        }

        HuffmanTree tree = new HuffmanTree(in);
        tree.decode(in, out);
    }

    /**
     * The entry point to the program.
     * @param args the command-line arguments.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IOException("Usage: java Grin <infile> <outfile>");
        }
        
        decode(args[0], args[1]);
        
    }
}
