package com.ssafy.swb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class SWEA5643 {

	public static List<Set<Integer>> bigger = new ArrayList<>();
	public static List<Set<Integer>> smaller = new ArrayList<>();
	public static int ans = 0;
	
	public static void process(int small, int big) {
		bigger.get(small-1).add(big);
		for (int s : smaller.get(small - 1)) {
			bigger.get(s-1).add(big);
		}
		for (int b : bigger.get(big-1)) {
			bigger.get(small -1).add(b);
			for (int s : smaller.get(small-1)) {
				bigger.get(s-1).add(b);
			}
		}
		smaller.get(big-1).add(small);
		for (int s : smaller.get(small -1)) {
			smaller.get(big-1).add(s);
		}
		for (int b : bigger.get(big-1)) {
			smaller.get(b-1).add(small);
			for (int s : smaller.get(small - 1)) {
				smaller.get(b-1).add(s);
			}
		}
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			
			int n = Integer.parseInt(br.readLine());
			int m = Integer.parseInt(br.readLine());
			
			for (int i = 0; i < n; i++) {
				bigger.add(new HashSet<>());
				smaller.add(new HashSet<>());
			}
			
			for (int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken()), b= Integer.parseInt(st.nextToken());
				process(a,b);
			}
			
			for (int i = 0; i < n; i++) {
				if ((bigger.get(i).size() + smaller.get(i).size()) == (n - 1)) {
					ans++;
				}
			}
			
			
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(ans);
			System.out.println(sb);
			ans = 0;
		}
	}
}
