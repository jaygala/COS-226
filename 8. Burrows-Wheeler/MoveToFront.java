/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */


import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static int R = 256;

    private static class LinkedList {

        /**
         * firstNode: the first node of the LinkedList
         * lastNode: the last node of the LinkedList
         */
        private Node firstNode;
        private Node lastNode;

        // Node class
        private class Node {

            /**
             * value: value corresponding to the node
             * next: next Node in the LinkedList
             * previous: previous Node in the LinkedList
             */
            private char value;
            private Node next;
            private Node previous;

            public Node(char value) {
                this.value = value;
                this.next = null;
                this.previous = null;
            }
        }

        public LinkedList() {
            this.firstNode = null;
            this.lastNode = null;
        }

        /**
         * Adds the char to the start of the LinkedList
         *
         * @param c - char to be added
         */
        private void addToFirst(char c) {
            if (firstNode == null) {
                firstNode = new Node(c);
                lastNode = firstNode;
                return;
            }
            Node newNode = new Node(c);
            newNode.next = firstNode;
            newNode.previous = null;
            firstNode.previous = newNode;
            firstNode = newNode;
        }

        /**
         * Adds the char to the end of the LinkedList
         *
         * @param c - char to be added
         */
        private void addToLast(char c) {
            if (lastNode == null && firstNode == null) {
                addToFirst(c);
                return;
            }
            Node newNode = new Node(c);
            newNode.next = null;
            newNode.previous = lastNode;
            lastNode.next = newNode;
            lastNode = newNode;
        }

        /**
         * Removes the current Node and returns it
         *
         * @param current - Node to be removed
         * @return - current Node
         */
        private Node remove(Node current) {
            if (current == null)
                throw new IllegalArgumentException("Invalid parameter in remove() of LinkedList");
            if (current.equals(firstNode)) {
                firstNode = current.next;
            }
            else if (current.equals(lastNode)) {
                current.previous = null;
            }
            else {
                current.previous.next = current.next;
                current.next.previous = current.previous;
            }
            return current;
        }

        /**
         * Searches a given char and moves it to the front
         * Returns the index corresponding to the location of the char
         *
         * @param c - char to be moved
         * @return - the index corresponding to the location of the given char
         */
        private int searchRemoveAndAdd(char c) {
            Node current = firstNode;
            int index = 0;
            while (current != null) {
                if (current.value == c) {
                    if (current.equals(firstNode)) {
                        return index;
                    }
                    addToFirst(c);
                    remove(current);
                    return index;
                }
                current = current.next;
                index++;
            }
            return -1;
        }

        /**
         * Returns the char in the LinkedList corresponding to the particular index
         * Moves the character to the front of the LinkedList
         *
         * @param x
         * @return
         */
        private char findValue(int x) {
            Node current = firstNode;
            int count = 0;
            while (current != null) {
                if (count == x) {
                    if (count == 0) {
                        return current.value;
                    }
                    addToFirst(current.value);
                    current = remove(current);
                    return current.value;
                }
                count++;
                current = current.next;
            }
            return '0';
        }
    }


    /**
     * Implements Move to Front encoding
     * Reads from standard input and writes to standard output
     */
    public static void encode() {
        LinkedList allChars = new LinkedList();
        for (int i = 0; i < R; i++) {
            allChars.addToLast((char) i);
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write((byte) allChars.searchRemoveAndAdd(c));
        }
        BinaryStdOut.close();
    }


    /**
     * Implements Move to Front decoding
     * Reads from standard input and writes to standard output
     */
    public static void decode() {
        LinkedList allChars = new LinkedList();
        for (int i = 0; i < R; i++) {
            allChars.addToLast((char) i);
        }
        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            BinaryStdOut.write((byte) allChars.findValue(i));
        }
        BinaryStdOut.close();
    }


    /**
     * Implements Move-to-front encoding and decoding
     * if args[0] is "-", applies move-to-front encoding
     * if args[0] is "+", applies move-to-front decoding
     *
     * @param args - the command line arguments
     *             first argument has to be "-" or "+"
     */
    public static void main(String[] args) {
        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
            decode();
    }

}
