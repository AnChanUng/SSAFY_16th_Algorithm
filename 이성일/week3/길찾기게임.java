import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;


class Solution {

    static class Node implements Comparable<Node>{
        int idx;
        int x;
        int y;
        Node left;
        Node right;

        Node(int idx, int x, int y) {
            this.idx = idx;
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Solution.Node o) {
            
            return (this.y == o.y ? Integer.compare(this.x, o.x) : (-1) * (Integer.compare(this.y, o.y)));
        }
    }

    static int treePreSearch(int idx, ArrayDeque<Node> q) {
        
        while (!q.isEmpty()) {
            Node currentNode = q.poll();
            answer[0][idx++] = currentNode.idx;
            if (currentNode.left != null) {
                q.add(currentNode.left);
                idx = treePreSearch(idx, q); 
            } if (currentNode.right != null) {
                q.add(currentNode.right);
                idx = treePreSearch(idx, q);
            }
        }
        return idx;
    }

    static int treePostSearch(int idx, ArrayDeque<Node> q) {

        while (!q.isEmpty()) {
            Node currentNode = q.poll();
            if (currentNode.left != null) {
                q.add(currentNode.left);
                idx = treePostSearch(idx, q);
            } if (currentNode.right != null) {
                q.add(currentNode.right);
                idx = treePostSearch(idx, q);
            }
            answer[1][idx++] = currentNode.idx;
        }
        return idx;
    }

    static Node[] nodes;

    static ArrayDeque<Node> bst;
    
    static int[][] answer;

    public int[][] solution(int[][] nodeinfo) {
        answer = new int[2][nodeinfo.length];
        nodes = new Node[nodeinfo.length];
        
        for (int i = 0; i < nodeinfo.length; i++) {
            nodes[i] = new Node(i+1, nodeinfo[i][0], nodeinfo[i][1]);
        }

        Arrays.sort(nodes);

        for (int i = 1; i < nodes.length; i++) {
            Node stage = nodes[0];
            while (true) {
                if (nodes[i].x < stage.x) {
                    if (stage.left == null) {
                        stage.left = nodes[i];
                        break;
                    } else {
                        stage = stage.left;
                        continue;
                    }
                }
                if (nodes[i].x > stage.x) {
                    if (stage.right == null) {
                        stage.right = nodes[i];
                        break;
                    }
                    else {
                        stage = stage.right;
                        continue;
                    }
                }
            }
        }

        bst = new ArrayDeque<>();

        // 전위 순회
        bst.add(nodes[0]);
        treePreSearch(0, bst);

        // 후위 순회
        bst.add(nodes[0]);
        treePostSearch(0, bst);

        return answer;
    }
}