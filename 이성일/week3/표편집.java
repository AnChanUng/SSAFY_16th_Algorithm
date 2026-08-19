import java.util.ArrayList;
import java.util.List;

class Solution {

    static class Node{
        Node prev;
        int val;
        Node next;
        Node (int val) {
            this.val = val;
        }
        Node (Node prev, int val, Node next) {
            this.prev = prev;
            this.val = val;
            this.next = next;
        }
    }

    static Node[] nodes;

    static List<Node> spares;


    public String solution(int n, int k, String[] cmd) {
        nodes = new Node[n];
        spares = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        for(int i = 0; i < n; i++) {
            if (i == 0) {
                nodes[i].next = nodes[i+1];
                nodes[i].prev = null;
                continue;
            }
            if (i == n-1) {
                nodes[i].prev = nodes[i-1];
                nodes[i].next = null;
                break;
            }
            nodes[i].prev = nodes[i-1];
            nodes[i].next = nodes[i+1];
        }
        
        Node currNode = nodes[k];
        for (String s: cmd) {
            char c = s.charAt(0);
            switch(c) {
                case 'U': {
                    int x = Integer.parseInt(s.split(" ")[1]);
                    while (x > 0) {
                        currNode = currNode.prev;
                        x--;
                    }
                    break;
                }
                case 'D': {
                    int x = Integer.parseInt(s.split(" ")[1]);
                    while (x > 0) {
                        currNode = currNode.next;
                        x--;
                    }
                    break;
                }
                case 'C': {
                    spares.add(currNode);
                    if (currNode.next == null && currNode.prev == null) {
                        break;
                    }
                    if (currNode.next == null) {
                        currNode = currNode.prev;
                        currNode.next = null;
                    }
                    else if (currNode.prev == null) {
                        currNode = currNode.next;
                        currNode.prev = null;
                    }
                    else {
                        currNode.prev.next = currNode.next;
                        currNode.next.prev = currNode.prev;
                        currNode = currNode.next;
                    }
                    break;
                }
                case 'Z': {
                    Node restore = spares.removeLast();
                    if (restore.prev == null && restore.next == null) break;
                    if (restore.prev == null) {
                        restore.next.prev = restore;
                    } else if (restore.next == null) {
                        restore.prev.next = restore;
                    } else {
                        restore.prev.next = restore;
                        restore.next.prev = restore;
                    }
                    break;
                }
                default: break;
            }
        }
        Node node = currNode;
        while (node.prev != null) {
            node = node.prev;
        }
        String ox = "X".repeat(n);
        char[] oxArray = ox.toCharArray();
        while (node.next != null) {
            oxArray[node.val] = 'O';
            node = node.next;
        }
        oxArray[node.val] = 'O';
        String answer = new String(oxArray);

        return answer;
    }
}