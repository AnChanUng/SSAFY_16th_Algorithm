/* 풀이 과정
1. 열쇠가 자물쇠 밖에서도 시작할 수 있으므로 자물쇠를 상하좌우로 M-1칸 확장했다.
   확장 배열에 자물쇠를 복사하는 과정에서 인덱스 오류가 발생해 범위를 다시 확인했다.(gpt도움)

2. 열쇠를 모든 위치에 놓고 90도씩 회전하며 검사했다.
   실제 자물쇠 영역의 합이 모두 1이면 열 수 있다고 판단했다.

3. 처음에는 확장 배열 전체를 검사해 오답이 발생했다. 문제에 나온 예시로 실행하면 열쇠와 자물쇠의 돌기부분끼리 더해져 2가되버림 -> 정답인데 정답이 아니도록 나오게 됌
   자물쇠 밖으로 튀어나온 열쇠는 상관없으므로 실제 N×N 자물쇠 영역만 검사해야 한다.
*/

class Solution {
    static int N;
    static int M;
    static int[][] expendedLock;
    public boolean solution(int[][] key, int[][] lock) {
        this.M = key.length;
        this.N = lock.length;
       
        expendedLock = new int[N+2*M-2][N+2*M-2];
        
        for(int i = 0; i < M-1; i++){
            for(int j = 0; j < expendedLock[0].length; j++){
                expendedLock[i][j] = 1;
            }
        }
        
        for(int i = M-1; i < N+M-1; i++){
            for(int j = 0; j < expendedLock[0].length; j++){
                if(j < M-1 || j >= N+M-1){
                    expendedLock[i][j] = 1;
                }else{
                    expendedLock[i][j] = lock[i-(M-1)][j-(M-1)];
                }
            }
        }
        
        for(int i = N+M-1; i < expendedLock.length; i++){
            for(int j = 0; j < expendedLock[0].length; j++){
                expendedLock[i][j] = 1;
            }
        }

        boolean flag = false;
        for(int i = 0; i < M+N-1; i++){
            for(int j = 0; j < M+N-1; j++){
                flag = simulate(i,j,key);
                    if(flag) return true;
            }
        }
        
        
        
        
        
        
        
        
        
        return false;
    }
    
static boolean simulate(int startX, int startY, int[][] key) {

    for (int a = 0; a < 4; a++) {

        // 회전 방향마다 원본 자물쇠 복사
        int[][] copyMap = copy(expendedLock);

        // 현재 위치에 열쇠를 겹쳐서 값 더하기
        for (int i = startX; i < startX + M; i++) {
            for (int j = startY; j < startY + M; j++) {
                copyMap[i][j] += key[i - startX][j - startY];
            }
        }
        
        boolean possible = true;

        for (int i = M - 1; i < N + M - 1; i++) {
            for (int j = M - 1; j < N + M - 1; j++) { //중앙부분만 감사

                if (copyMap[i][j] != 1) {
                    possible = false;
                    continue;
                }
            }
        }

        // 현재 회전 방향에서 자물쇠가 열림
        if (possible) {
            return true;
        }

        // 열쇠를 90도 회전
        key = rotate(key);
    }

    // 네 가지 방향 모두 실패
    return false;
}
    
    static int[][] rotate(int[][] key){
        
        int[][] copyKey = new int[M][M];
        
        for(int i = 0; i < M; i++){
            for(int j = 0; j < M; j++){
                copyKey[i][j] = key[M-1-j][i];
        }
    }
        return copyKey;
    }
    
        
    
    static int[][] copy(int[][] map){  //맵을 복사하는 메서드
        
        int[][] copy = new int[map.length][map[0].length];
        
        for(int i = 0; i < copy.length; i++){
            copy[i] = map[i].clone();
        }
        
        return copy;
    }
    
}
