// class Solution {

// int maxPalin(String s, int left, int right) {
// while ((left >= 0 && right < s.length()) && (s.charAt(left) ==
// s.charAt(right))) {
// left--;
// right++;
// }
// return right - left - 1;
// }

// public int solution(String s) {
// int answer = 1;
// // 문자열 인덱스 별로 투포인터 연산으로 펠린드롬 계산
// for (int idx = 0; idx < s.length(); idx++) {
// int oddP = maxPalin(s, idx, idx);
// int evenP = maxPalin(s, idx, idx + 1);
// answer = Math.max(answer, Math.max(oddP, evenP));
// }
// return answer;
// }

// }
