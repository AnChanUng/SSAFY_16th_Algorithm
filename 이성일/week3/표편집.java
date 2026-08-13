import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Solution {

    static List<Node> ll;
    static List<Node> deq;

    static class Node {
        int prev;
        int val;
        int next;

        Node(int val) {
            this.val = val;
        }
    }

    public String solution(int n, int k, String[] cmd) {
        String answer = "";
        ll = new LinkedList<>();
        deq = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            ll.add(new Node(i));
        }
        for (String c : cmd) {
            char cc = c.charAt(0);
            if (cc == 'U') {
                int x = Integer.parseInt(c.split(" ")[1]);
                k = k - x;
            } else if (cc == 'D') {
                int x = Integer.parseInt(c.split(" ")[1]);
                k = k + x;
            } else if (cc == 'C') {
                Node removedNode = ll.remove(k);
                removedNode.prev = k - 1;
                deq.add(removedNode);
            } else {
                Node restoreNode = deq.removeLast();
                ll.add(restoreNode.prev + 1, restoreNode);
            }
        }

        String x = "X";
        String ox = x.repeat(n);
        char[] oxArray = ox.toCharArray();

        for (Node node : ll) {
            oxArray[node.val] = 'O';
        }
        for (char chart : oxArray) {
            answer += chart;
        }

        return answer;
    }
}
