import java.util.*;
import java.io.*;

public class SWEA5658 {
	static TreeSet<Integer> set;
	static int M;

	
	public static void main(String[] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			
			set = new TreeSet<>(Collections.reverseOrder());
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			M = N / 4; //한 변의 문자열의 길이,M번만큼 움직이면 제자리로 돌아
			
			String line = br.readLine();
			
			for(int j = 0; j < M; j++) {
				
				addString(line);
				line = line.substring(N - 1) + line.substring(0, N - 1);    
			
		}
			
			int index = 0;
			StringBuilder result = new StringBuilder();
			for(Integer num : set) {
				if(index == K-1) {
					result.append("#" + tc + " "+ num);
					break;
				}
				index++;
			}
			System.out.println(result);
		
	}
}
		
	static void addString(String line) {
		
		
		for(int i = 0; i < 4; i++) {
			
			int start = i*M;
			int end = start+M;
			
			String str = line.substring(start, end);
			int num = Integer.parseInt(str,16); //10진수로 변환
			
			set.add(num);
			
		}
		return;
	}

}
