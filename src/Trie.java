import java.util.HashMap;

public class Trie {
    private class Node {
        Node[] children = new Node[26];
        int index=-1;
    }

    private Node root;

    Trie() {
        root = new Node();
    }

    public boolean add(String s) {
        Node cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(cur.children[c-'A']==null)
                cur.children[c-'A'] = new Node();
            cur = cur.children[c-'A'];
            if(i==s.length()-1) {
                if(cur.index!=-1) return false;
                cur.index = 0;
            }
        }
        return true;
    }

    public boolean contains(String s) {
        Node cur = root;
        int n = s.length();
        for(int i=0;i<n;i++) {
            if(cur.children[s.charAt(i)-'A']==null) return false;
            cur = cur.children[s.charAt(i)-'A'];
        }
        return cur.index!=-1;
    }

    public void print() {
        String s = "";
        for(int i=0;i<26;i++) {
            if(root.children[i]!=null) {
                print(s, root.children[i], (char)(i+'A'));
            }
        }
    }

    private void print(String s, Node cur, char ch) {
        s += ch;
        if(cur.index!=-1) System.out.println(s);
        for(int i=0;i<26;i++) {
            if(cur.children[i]!=null) {
                print(s, cur.children[i], (char)(i+'A'));
            }
        }
        s = s.substring(0, s.length() - 1);
    }

    public static void main(String[] args) {
        Trie tr = new Trie();
        tr.add("ANANT");
        tr.add("AJ");
        tr.add("HELLO");
        tr.add("IS");
        tr.add("BITCH");
        tr.add("KARMA");
        System.out.println(tr.contains("AJ"));
        tr.print();
    }
}
