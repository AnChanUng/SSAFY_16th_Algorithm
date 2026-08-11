import java.util.*;

class Solution {

    public String solution(int n, int t, int m, String[] timetable) {

        int[] crew = new int[timetable.length];

        // 시간을 분으로 변환
        for (int i = 0; i < timetable.length; i++) {
            String[] s = timetable[i].split(":");
            crew[i] = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
        }

        Arrays.sort(crew);

        int idx = 0;
        int shuttleTime = 9 * 60;

        for (int bus = 0; bus < n; bus++) {

            int count = 0;
            int lastCrewTime = 0;

            while (idx < crew.length
                    && crew[idx] <= shuttleTime
                    && count < m) {

                lastCrewTime = crew[idx];
                idx++;
                count++;
            }

            // 마지막 셔틀
            if (bus == n - 1) {

                // 자리 남음
                if (count < m) {
                    return format(shuttleTime);
                }

                // 꽉 참
                return format(lastCrewTime - 1);
            }

            shuttleTime += t;
        }

        return "";
    }

    private String format(int time) {

        int h = time / 60;
        int m = time % 60;

        return String.format("%02d:%02d", h, m);
    }
}
