import java.util.*;
class Solution
    
    /*
        각 인덱스의 문자가 중심이 된다고 가정하여 검사,
        나를 중심으로 양옆을 검사하는 경우 -> 펠린드롬길이가 홀수
        나와 오른쪽부터 검사해서 넓혀나가는 경우 -> 길이가 짝수
    */
{
    public int solution(String s)
    {
        int answer = 0;
        int max = 0;
        
        for(int i = 0; i < s.length(); i++){
            
            int oddCnt = PaildromeCnt(s, i-1, i+1);
            int evenCnt = PaildromeCnt(s,i,i+1);
            
            max = Math.max(oddCnt, evenCnt);
            answer = Math.max(max,answer);
        }
        
        return answer;

        
    }
    
    
    static int PaildromeCnt(String s, int left, int right){
        
        
        int cnt = 0;
        
        if(right-left > 1){   // 나를 중심으로 양 옆을 조사하는 경우에 초기 길이가 1임
            cnt = 1;
        }
        
        while(left >=0 && right <= s.length()-1){
            if(s.charAt(left) == s.charAt(right)){
                cnt += 2;
                left--;
                right++;
            }else{
                break;
            }
        }
        
        return cnt;
    }
    
    
}
