/*
 * 전략

- 규칙 찾기
- 나무 높이를 돌며 최대값과 전체 높이의 합을 찾는다.
- 목표 높이 합에서 전체 높이 합을 빼서 제일 적게 쓸 수 있는 1의 개수와 제일 많이 쓸 수 있는 2의 개수를 찾는다.
- 1과 2의 개수의 차가 최소가 될 때까지 반복한다(차의 종류는 1, 0, -1이 된다)
    - 2의 개수 1개를 1의 개수 2개로 바꾼다
- 1의 개수와 2의 개수의 차를 통해 결과를 계산한다
    - 1의 개수 > 2의 개수인 경우 1212....121로 끝남.
        - 따라서 1의 개수 * 2만큼 세되 마지막에 2가 없으니 1만큼 뺌
    - 1의 개수 == 2의 개수인 경우 1212....1212로 끝남. 1의 개수 < 2의 개수인 경우 1212....12X2로 끝남
        - 따라서 두 경우의 수 모두 2의 개수 * 2만큼 세면 됨

시행착오

- 처음에는 목표 높이 합 - 전체 높이 합에서 (1+2) 를 2번으로 묶어서 
  gap / 3 + gap % 3으로 계산했는데, 이 경우 10 9 9 9 반례를 보면 
  답은 5번이나 저 공식에 따르면 답이 2가 나오는 문제 찾음
- 이후 각 높이가 최대높이가 될 때까지 필요한 1의 개수와 2의 개수를 각각 구해서 
  1과 2의 개수가 겹치는 만큼 * 2일을 더하고, 두 횟수 중 남은 일수만큼 *2 -1이나 *2를 해서 계산함.
  이 경우 1과 2의 개수 차이가 커서 1X1X나 X2X2 처럼 횟수를 낭비하는 경우가 많았음
 * 
 */

import java.util.*;
import java.io.*;

public class SWEA14510 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine());
			int[] trees = new int[N];
			int maxHeight = 0;
			int day = 0;
			int oneCnt = 0; int twoCnt = 0;
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for(int i = 0; i < N; i++) {
				trees[i] = Integer.parseInt(st.nextToken());
				maxHeight = Math.max(maxHeight, trees[i]);
			}
			
			// 최소한으로 필요한 1의 개수와 최대로 쓸 수 있는 2의 개수 찾기
			for(int i = 0; i < N; i++) {
			    int diff = maxHeight - trees[i];
			    oneCnt += diff % 2; 
			    twoCnt += diff / 2; 
			}

			// 2 하나를 1 두개로 넘기기
			// 2의 개수와 1의 개수의 차가 최소가 될 떄까지
			while (twoCnt - oneCnt >= 2) {
			    twoCnt--;
			    oneCnt += 2;
			}

			// 1의 개수 > 2의 개수인 경우 1212....121로 끝남 
			// 따라서 1의 개수만큼 세되 마지막에 2가 없으니 1일을 뺌
			if (oneCnt > twoCnt) {
			    day = oneCnt * 2 - 1;
			// 1의 개수 == 2의 개수인 경우 1212...1212로 끝남
			// 1의 개수 < 2의 개수인 경우 1212...12X2로 끝남
			// 두 경우의 수 모두 2의 개수만큼 세면 됨
			} else {
			    day = twoCnt * 2;
			}

			sb.append("#").append(t).append(" ").append(day).append("\n");
		}
		
		System.out.print(sb);
	}
}
