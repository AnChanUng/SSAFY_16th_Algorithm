import java.util.*;
import java.lang.*;
import java.io.*;
/*
    16진수 수가 K번째 인수를 10진수로 변환하기
    
    자료구조: ArrayList
    시간복잡도: O(n^2)

    1. 큐의 숫자를 앞에서부터 3개씩 합침
    2. 한바퀴 돌때마다 ArrayList에 있는 숫자가 하나씩 이동함 마지막껀 맨 앞으로옴
    3. 10진수를 새로운 ArrayList에 저장해서 내림차순함
      - 중복으로 저장되어선 안됨
    4. K번째 수를 10진수로 변환해서 출력
*/
public class SWEA5658 {
    static List<Character> list1;
    static List<Integer> list2;
    static int n, k;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int t = Integer.parseInt(br.readLine());

        for(int tc=1; tc<=t; tc++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());

            int section = n / 4;
            
            String jinsu = br.readLine(); // 1B3B3B81F75E

            list1 = new ArrayList<>();
            list2 = new ArrayList<>();
            
            for(int i=0; i<jinsu.length(); i++) {
                char ch = jinsu.charAt(i);
                list1.add(ch);
            }

            int answer = 0;
            for(int k=0; k<section; k++) {      
                String str = "";
                int cnt = 0;
                
                for(int i=0; i<list1.size(); i++) {
                    str += list1.get(i);
                    cnt++;
                    if(cnt == section) {
                        int x = Integer.parseInt(str, 16);
                        //int x = translate(str); // str 16진수를 10진수로 변환
                        if(!list2.contains(x)) {
                            list2.add(x);
                        }
                        str = "";
                        cnt = 0;
                    }
                }

                // list2 내림차순
                Collections.sort(list2, Collections.reverseOrder());

                // list1의 마지막 것을 지우고 맨 앞으로 넣기
                list1.add(0, list1.remove(list1.size()-1));
            }
            //for(int i=0; i<list2.size(); i++) System.out.println(list2.get(i));
            
            answer = list2.get(k-1);
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
    }
}