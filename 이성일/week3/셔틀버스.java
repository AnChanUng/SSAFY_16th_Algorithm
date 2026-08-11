import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    static class Time implements Comparable<Time> {
        int hour;
        int minute;

        Time(String time) {
            String[] timeString = time.split(":");
            this.hour = Integer.parseInt(timeString[0]);
            this.minute = Integer.parseInt(timeString[1]);
        }

        Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        @Override
        public int compareTo(Solution.Time o) {
            return (this.hour == o.hour) ? Integer.compare(this.minute, o.minute)
                    : Integer.compare(this.hour, o.hour);
        }
    }

    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";

        // 1. n,t를 활용하여 버스 도착 타임테이블 구성하기
        List<Time> busTimeTable = new ArrayList<>();
        int hour = 9, minute = 0;
        busTimeTable.add(new Time("09:00"));
        for (int i = 0; i < n - 1; i++) {
            minute += t;
            if (minute >= 60) {
                hour += (minute / 60);
                minute = (minute % 60);
            }
            busTimeTable.add(new Time(hour, minute));
        }

        List<Time> crewTable = new ArrayList<>();
        for (String tt : timetable) {
            crewTable.add(new Time(tt));
        }
        Collections.sort(crewTable);

        // 2. 버스테이블을 돌면서 크루테이블에서 m명씩 태우기 (버스 시간 안쪽이어야 함) (크루테이블 정렬)
        Time cone;
        int coneHour = busTimeTable.get(busTimeTable.size() - 1).hour;
        int coneMinute = busTimeTable.get(busTimeTable.size() - 1).minute;
        cone = new Time(coneHour, coneMinute);

        int nextCrewIdx = 0;
        int lastBusPassengers = 0;
        for (int i = 0; i < busTimeTable.size(); i++) {
            int cnt = 0;
            for (int j = nextCrewIdx; j < crewTable.size(); j++) {
                if (cnt == m)
                    break;
                if (crewTable.get(j).compareTo(busTimeTable.get(i)) <= 0) {
                    // 버스시간보다 일찍 온 승객
                    cnt++;
                } else
                    break;
            }
            if (i == busTimeTable.size() - 1)
                lastBusPassengers = cnt;
            nextCrewIdx += cnt;
        }

        // 3. 콘은 마지막 버스도착 시간에 도착한다
        // 3-1 이 때, 마지막 버스 도착 시간 기준 이전에 온 대기열이 m명 이상이면 m번째 사람보다 1분전에는 와야함

        // 마지막 차가 꽉찼다
        if (lastBusPassengers == m) {
            cone = crewTable.get(nextCrewIdx - 1);
            cone.minute -= 1;
            if (cone.minute < 0) {
                cone.minute = 59;
                cone.hour -= 1;
            }
        }

        if (cone.hour >= 10 && cone.minute >= 10) {
            answer = String.format("%d:%d", cone.hour, cone.minute);
        } else if (cone.hour >= 10 && cone.minute < 10) {
            answer = String.format("%d:%d%d", cone.hour, 0, cone.minute);
        } else if (cone.hour < 10 && cone.minute >= 10) {
            answer = String.format("%d%d:%d", 0, cone.hour, cone.minute);
        } else {
            answer = String.format("%d%d:%d%d", 0, cone.hour, 0, cone.minute);
        }

        return answer;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.solution(0, 0, 0, new String[] { "hello", "java" });

    }
}
