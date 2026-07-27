package swtb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class SWEA14510 {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int i = 1; i <= tc; i++) {
			int n = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			List<Integer> trees = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				trees.add(Integer.parseInt(st.nextToken()));
			}
			
			int top = Collections.max(trees);
			int gap, one = 0, two = 0;
		
			for(int j = 0; j < n; j++) {
				gap = top - trees.get(j);
				one += (gap%2);
				two += (gap / 2);
			}
			
			int ans = Integer.MAX_VALUE;
			for (int split = 0; split <= two; split++) {
				int newone = one + split * 2;
				int newtwo = two - split;
				
				int oddDay = 2 * newone - 1;
				int evenDay = 2 * newtwo;
				
				int day = Math.max(oddDay, evenDay);
				
				ans = Math.min(ans, day);
				
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(i).append(" " ).append(ans).append("\n");
			System.out.print(sb);
		}
	}
}
