import java.util.HashSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    private class Node {
        Node[] children = new Node[26];
        int index = -1;
    }

    private Node root;
    private String[] dict;

    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        root = new Node();
        dict = dictionary;
        for (int i = 0; i < dictionary.length; i++) {
            add(dictionary[i], i);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        HashSet<String> words = new HashSet<>();
        HashSet<Integer> incl = new HashSet<>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                incl.add(i * board.rows() + j);
                findWords(root, words, incl, board, i, j);
                incl.remove(i * board.rows() + j);
            }
        }
        return words;
    }

    private void findWords(Node cur, HashSet<String> words, HashSet<Integer> incl, BoggleBoard b, int row, int col) {
        if (cur.children[b.getLetter(row, col) - 'A'] == null)
            return;
        Node next = cur.children[b.getLetter(row, col) - 'A'];
        if (next.index != -1 && dict[next.index].length() > 2)
            words.add(dict[next.index]);
        for (int i = row - 1; i < row + 2; i++) {
            for (int j = col - 1; j < col + 2; j++) {
                if (i < 0 || j < 0 || i >= b.rows() || j >= b.cols())
                    continue;
                if (incl.contains(i * b.rows() + j))
                    continue;
                incl.add(i * b.rows() + j);
                findWords(next, words, incl, b, i, j);
                incl.remove(i * b.rows() + j);
            }
        }
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word.length() < 5) {
            return 1;
        } else if (word.length() == 5) {
            return 2;
        } else if (word.length() == 6) {
            return 3;
        } else if (word.length() == 7) {
            return 5;
        } else {
            return 11;
        }
    }

    public boolean add(String s, int index) {
        Node cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (cur.children[c - 'A'] == null)
                cur.children[c - 'A'] = new Node();
            cur = cur.children[c - 'A'];
            if (c == 'Q')
                i++;
            if (i == s.length() - 1) {
                if (cur.index != -1)
                    return false;
                cur.index = index;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
