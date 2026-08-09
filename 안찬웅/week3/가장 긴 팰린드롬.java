import java.util.*;
/*
    뒤집어도 같은 수중에 가장 긴 것 구하기
    
    시간복잡도: n <= 2500 이므로 O(n^2)
    
    1. 길이 idx를 n부터 1까지 줄여가며
    2. 각 시작 위치 i에서 길이 idx짜리가 팰린드롬인지 판정
    3. 처음 찾은 순간이 가장 긴 것이므로 종료
*/
class Solution {
    static int maxLen; // 가장 긴 팰린드롬의 길이
    public int solution(String s) {
        maxLen = 1;
        palindrome(s);
        return maxLen;
    }
    
    static void palindrome(String s) {
        int n = s.length();
        int idx = n;
        while(idx > 1) {
            boolean flag = false;
            for(int i=0; i<n-idx+1; i++) {
                if(isPalindrome(s, i, i+idx-1)) {
                    maxLen = idx;
                    flag = true;
                    break;
                }
            }
            if(flag) break;
            idx--;
        }
    }
    
    static boolean isPalindrome(String s, int left, int right) {
        while(left < right) {
            if(s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
}
