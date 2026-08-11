import java.util.*;
/*
    콘이 정류장에 도착해야 하는 시각중 가장 늦은 시간 구하기
    :콘은 크루 중 제일 뒤에서 기다림
    
    셔틀 운행 횟수: n
    셔틀 운행 간격: t
    한 셔틀에 탈 수 있는 최대 크루 수: m
    크루가 버스 정류장에 도착한 시간: timetable
    
    1. 분으로 시간 저장하기
    2. crew 배열에 시간 저장해서 오름차순
    3. 콘 없이 크루만 태워본다
    4. 마지막 버스 상태로 답 결정
    5. 시간 문자열 변환 
*/
class Solution { 
    public String solution(int n, int t, int m, String[] timetable) {
        int[] crew = new int[timetable.length];
        int[] bus = new int[n];
        
        for(int i=0; i<timetable.length; i++) {
            String[] str = timetable[i].split(":");
            int hour = Integer.parseInt(str[0]);
            int min = Integer.parseInt(str[1]);
            int total = hour * 60 + min;
            crew[i] = total;
        }
        
        Arrays.sort(crew);
        
        for(int i=0; i<n; i++) {
            bus[i] = 540 + t * i;
        }
        
        int result = 0;
        int idx = 0;
        int cnt = 0;
        for(int i=0; i<n; i++) {
            cnt = 0;
            while(idx < crew.length && crew[idx] <= bus[i] && cnt < m) {
                idx++;
                cnt++;
            }
        }
        
        // 자리가 남으면 막차를 타고, 만석이면 마지막 탑승
        if(cnt < m) {
            result = bus[n-1];
        } else {
            result = crew[idx-1] - 1;
        }
        
        int hour = result / 60;
        int min = result % 60;

        StringBuilder sb = new StringBuilder();
        if(hour < 10) sb.append("0");
        sb.append(hour).append(":");
        if(min < 10) sb.append("0");
        sb.append(min);

        return sb.toString();
    }
}
