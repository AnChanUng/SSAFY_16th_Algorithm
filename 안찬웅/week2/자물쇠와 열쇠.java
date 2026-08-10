import java.util.*;
/*
    Key를 회전시키고 이동시켜서 Lock에 넣으면 열린다
    :열쇠는 회전과 이동이 가능

    알고리즘: 완전탐색

    자물쇠(lock): N * N 격자 형태
    열쇠(key): M * M 격자 형태
    
    홈: 0
    돌기: 1
    
    1. 완전탐색으로 모든 경우의 수에 다음을 대입한다. 
    1.1 key를 90도 회전시키거나 상하좌우로 이동시켜서 Lock에 대입한다.
    1.2 대입해서 홈에 들어가고, 돌기(1)과 겹치지 않으면 true
    1.3 홈이(0)이아니고 돌기(1)과 겹치면 false
    
    아닌 경우를 코드로 짜고 else true가 더 잘 맞을듯 싶음
*/
class Solution {
    static boolean answer;
    static int n, m;
    public boolean solution(int[][] key, int[][] lock) {
        answer = false;
        m = key.length;
        n = lock.length;
        
        // 1.0도 이동 -> 오른쪽 한칸, 왼쪽 한칸, 위로 한칸 아래로 한칸
        // 1.90도 이동 -> 오른쪽 한칸, 왼쪽 한칸, 위로 한칸 아래로 한칸
        // 2.180도 이동 -> 오른쪽 한칸, 위쪽 한칸, 위로 한칸, 아래로 한칸
        // 3.270도 이동 -> 오른쪽 한칸, 위쪽 한칸, 위로 한칸, 아래로 한칸
        rotate(key, lock, 0);
        if(answer) return true;
        rotate(key, lock, 90);
        if(answer) return true;
        rotate(key, lock, 180);
        if(answer) return true;
        rotate(key, lock, 270);
        
        return answer;
    }
    static void rotate(int[][] key, int[][] lock, int degree) {
        int[][] rotate = new int[m][m];
       
        for(int i=0; i<m; i++) {
            for(int j=0; j<m; j++) {
                switch(degree) {
                    case 0:
                        rotate[i][j] = key[i][j];
                        break;
                    case 90:
                        rotate[i][j] = key[m-1-j][i];
                        break;
                    case 180:
                        rotate[i][j] = key[m-1-i][m-1-j];
                        break;
                    case 270:
                        rotate[i][j] = key[j][m-1-i];
                        break;
                }
            }
        }      
        // 상하좌우 이동
        moveKey(rotate, lock);
    }
    // 상하좌우 이동하는 함수
    static void moveKey(int[][] key, int[][] lock) {
        // 한번에 다같이 이동해야함 
        // 모두 1인 부분을 오른쪽으로 이동 (왼쪽, 아래, 위)
        for(int x=-(m-1); x<n; x++) {
            for(int y=-(m-1); y<n; y++) {
                keyVerseLock(key, lock, x, y);
                if(answer) return;
            }
        }
    }
    
    // key랑 Lock을 비교하는 함수
    static void keyVerseLock(int[][] key, int[][] lock, int x, int y) {
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                int dx = i - x;
                int dy = j - y;
                int keyVal = 0;
                if(dx >= 0 && dx < m && dy >= 0 && dy < m) {
                    keyVal = key[dx][dy];
                }
                if(keyVal + lock[i][j] != 1) {
                    answer = false;
                    return;
                }
            }
        }
        answer = true;
    }
}
