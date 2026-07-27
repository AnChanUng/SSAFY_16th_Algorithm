import java.util.*;
import java.io.*;


/*
 * 2시간 정도 고민해서 조건을 나눠 시뮬레이션으로 풀려고 했는데 시간 초과나서 도저히 모르겠어 gpt한테 힌트를 받아 필요한 날짜를 구하게 한다는 것을 알게 됌
 * two랑 one+1이 같은 경우에는 while문의 조건이 필요가 없지만 넘어가는 경우엔 two 1개를 one 2개로 바꿀 수 잇음
 */
public class SWEA14510{
	static int answer;
	static int max;
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			
			answer = 0;
			max = 0;
			int N = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());
			Integer[] tree = new Integer[N];
			
			for(int i = 0; i < N; i++) {
				
				tree[i] = Integer.parseInt(st.nextToken());
				max = Math.max(tree[i], max);
				
			}
			for(int j = 0; j < N; j++) {
				tree[j] = max - tree[j];
			}
			//tree[i]는 남은 길이
			
			
			int one = 0;
			int two = 0;
			
			for(int i = 0; i < N; i++) {
				one += tree[i]%2;
				two += tree[i]/2;
				
			}
			
			while(two > one +1) {
				two--;
				one += 2;
			}
			
			
			if(one > two) {
				answer = one * 2 -1;
				
			}else {
				answer = two * 2;
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(answer);	
			System.out.println(sb);
			
		}
		
	}
}