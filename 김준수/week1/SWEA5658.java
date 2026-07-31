/*
전략
- 0, 1, 2 … N-1 번째 회전에서 나올 수 있는 숫자 N개 찾기
    - 중복 제거 필요.
- 해당 숫자들을 10진수로 변환 후 정렬해서 K번째 수 찾기
- 한번 회전시킬때마다의 자물쇠의 상태를 String 배열에 저장해서 검사

로직
- 자물쇠 4변을 저장하는 String[4] 배열 사용
- 각 변에 대해, 이전 변의 마지막 문자를 내 변의 첫 문자로 추가
- 문자 하나 추가했으니 현재 변의 길이가 유지되도록 마지막 문자 제거
    - 위 2 과정을 for문에서 한번에 하려고 하면 마지막 문자를 다음 변에 줄 수 없어서 로직이 꼬임
- 각 변을 16진수 문자열에서 10진수 long으로 변환해서 중복 체크 후 저장
    - 한 변의 최대값 FFFFFFF = 16^7 - 1 = 약 27억 > Integer.MAX_VALUE이므로 long 사용
- 저장한 숫자 배열을 내림차순 정렬 후 K-1 인덱스의 값을 꺼내서 출력
*/

import java.io.*;
import java.util.*;

public class SWEA5658 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			List<Long> nums = new ArrayList<>();
			String[] locks = new String[4];

      // 초기 locks 4변의 상태 입력
			String line = br.readLine();
			for(int i = 0; i < N; i += N / 4) {
				locks[i / (N / 4)] = line.substring(i, i + (N / 4));
			}
			
			for(int i = 0; i < (N / 4); i++) {
				// 이전 변의 마지막 문자를 내 변의 가장 앞에 추가
				for(int j = 0; j < 4; j++) {
					locks[j] = locks[(j - 1 + 4) % 4].charAt(locks[(j - 1 + 4) % 4].length() - 1) + locks[j];
				}
				
				for(int j = 0; j < 4; j++) {
					// 문자 추가한 만큼 길이 쳐내기
					locks[j] = locks[j].substring(0, N / 4);
					
					// 중복 검사 후 결과 리스트에 추가
					long lockNum = Long.parseLong(locks[j], 16);
					if(!nums.contains(lockNum)) {
						nums.add(lockNum);
					}
				}
			}
			
			Collections.sort(nums, Collections.reverseOrder());
			
			sb.append("#").append(t).append(" ").append(nums.get(K - 1)).append("\n");
		}
		
		System.out.print(sb);
	}
}
