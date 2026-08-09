import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class Solution {
    
    public static List<int[][]> visited = new ArrayList<>();
    
    public static boolean isVisited(int[][] key){
        for (int[][] v : visited) {
            boolean flag = false;
            for (int i = 0; i < v.length; i++){
                for (int j = 0; j < v.length; j++){
                    if (v[i][j] != key[i][j]) {
                        flag = true;
                        break;
                    }
                    if (flag) break;
                }
                
                if (!flag) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean verify(int[][] key, int[][] lock) {
        
        for (int i = 0; i <= (lock.length - key.length); i++) {
            for (int j = 0; j <= (lock.length - key.length); j++) {
                boolean flag = true;
                for (int r = i; r < (i + key.length); r++) {
                    for (int c = j; c < (j + key.length); c++) {
                        if ((key[r-i][c-j] ^ lock[r][c]) == 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) break;
                }
                if (flag) return true;
            }
        }
        return false;
    }
    
    public static void rotate(int[][] key, int t) {
        for (int i = 0; i < t; i++){
            for (int r = 0; r < key.length-1; r++){
                for (int c = r+1; c < key.length; c++){
                    int temp = key[r][c];
                    key[r][c] = key[c][r];
                    key[c][r] = temp;
                }
            }
        
        }
    }
    public static void move(int[][] key, int dir) {
        switch (dir) {
            case 0: {
                for (int i = 0; i < key.length; i++){
                    if (i == (key.length -1)) Arrays.fill(key[i], 0);
                    else key[i] = key[i+1];
                } break;
            }
            case 1: {
                for (int j = key.length-1; j >= 0; j--) {
                    for (int i = 0; i < key.length; i++){
                        if (j == 0) key[i][j] = 0;
                        else key[i][j] = key[i][j-1];
                    }
                } break;
            }
            case 2: {
                for (int i = (key.length -1); i >= 0; i--){
                    if (i == 0) Arrays.fill(key[i], 0);
                    else key[i] = key[i-1];
                } break;
            }
            case 3: {
                for (int j = 0; j < (key.length -1); j++) {
                    for (int i = 0; i < key.length; i++) {
                        if (j == (key.length - 1)) key[i][j] = 0;
                        else key[i][j] = key[i][j+1];
                    }
                } break;
            }
            default: break;
        }
    }
    
    public static boolean dfs(int[][] key, int[][] lock) {
        if (verify(key, lock)) {
            return true;
        }
        for (int i = 1; i <= 7; i++){
            if (i <= 3) {
                rotate(key, i);
                if (isVisited(key)) {
                    rotate(key,3); // 3번더회전하면 원위치,원위치로, 새탐색 시작
                    continue;
                } else {
                    visited.add(key);
                    if (dfs(key,lock)) return true;
                    else {
                        rotate(key,3);
                        continue;
                    }
                    
                }
            } else {
                int[][] temp  = key;
                move(key, i%4);
                if (isVisited(key)) {
                    key = temp; // 이동 전으로
                    continue;
                } else {
                    visited.add(key);
                    if (dfs(key,lock)) return true;
                    else {
                        key = temp;
                        continue;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean solution(int[][] key, int[][] lock) {
        boolean answer = dfs(key,lock);
        
        // DFS 기반 완전 탐색
            // 1. 회전 4가지, 이동 4가지 총 8가지의 리프
        
            // 2. 매 스테이지별로 8가지 탐색 진행
                // 2.1 탐색 후 이미 방문한 노드면 탐색 종료, 이전 스테이지로
                // 2-2. 첫 방문일 경우 방문 노드에 기입 후, 검증 
                    // 2-2-1 자물쇠 사이즈 안을 범위로 순회 
            // 3. 검증 실패 시, 계속해서 뉴스테이지로
                // 3-1. 검증 성공 시, 모든 탐색 종료
        return answer;
    }
}