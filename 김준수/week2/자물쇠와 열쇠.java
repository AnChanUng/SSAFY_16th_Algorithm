/*
전략
- 완전탐색 백트래킹
- 격자 최대 크기는 20 - 20 - 20이니까 60x60으로 만들기
- 열쇠의 [M-1][M-1]이 자물쇠의 [0][0]에 닿는 시점부터 우측으로 열쇠를 옮김
    - 열쇠의 [M-1][0]이 자물쇠의 [0][N-1]에 닿는 시점까지 옮김
    - 그 후 자물쇠의 행을 +1 하여 다시 좌→우 이동 반복
    - 열쇠가 회전 가능한 4 모양에 대해 모두 시도
- 만약 각 시도 중 fit한 경우가 생기면 즉시 종료 후 true 반환
- 전체 시도해도 fit한 경우 없었으면 false 반환
*/
class Solution {
    static int[][][] keys;
    static int[][] lock;
    
    public boolean solution(int[][] key, int[][] l) {
        boolean answer = false;
        
        lock = l;
        
        // 키의 회전 모양 초기화
        keys = new int[4][key.length][key.length];
        keys[0] = key; 
        for(int i = 1; i < 4; i++){
            int[][] newKey = new int[key.length][key.length];
            for(int j = 0; j < key.length; j++){
                for(int k = 0; k < key.length; k++){
                    newKey[j][k] = keys[i - 1][k][key.length - j - 1];
                }
            }
            keys[i] = newKey;
        }
        
        // board 초기화
        int[][] board = new int[60][60];
        int[][] keyBoard = new int[60][60];
        for(int i = 20; i < 20 + lock.length; i++){
            for(int j = 20; j < 20 + lock.length; j++){
                board[i][j] = lock[i - 20][j - 20];
                keyBoard[i][j] = lock[i - 20][j - 20];
            }
        }
        
        // keyboard에 key 배치
        for(int i = 0; i < 4; i++){
            for(int j = 20 - key.length; j <= 20 + lock.length; j++){
                for(int k = 20 - key.length; k <= 20 + lock.length; k++){
                    // keyBoard에 key 끼워넣기
                    makeKeyBoard(keyBoard, i, j, k);
                    
                    // keyBoard가 lock의 모든 홈을 채우는지 검사
                    if(isFull(keyBoard)){
                        answer = true;
                    }
                    
                    // keyBoard 복구
                    for(int a = 0; a < 60; a++){
                        for(int b = 0; b < 60; b++){
                            keyBoard[a][b] = board[a][b];
                        }
                    }
                }
            }
        }
        
        return answer;
    }
    
    // lock만 있는 keyBoard에 key를 겹쳐서 리턴
    private void makeKeyBoard(int[][] board, int keyIndex, int x, int y){
        for(int i = x; i < x + keys[0][0].length; i++){
            for(int j = y; j < y + keys[0][0].length; j++){
                board[i][j] += keys[keyIndex][i - x][j - y];
            }
        }
    }
    
    // lock의 모든 홈이 채워졌는지
    private boolean isFull(int[][] board){
        for(int i = 20; i < 20 + lock.length; i++){
            for(int j = 20; j < 20 + lock.length; j++){
                // 홈이 비어있거나, 돌기가 겹쳤거나
                if(board[i][j] == 0 || board[i][j] == 2){
                    return false;
                }
            }
        }
        return true;
    }
}