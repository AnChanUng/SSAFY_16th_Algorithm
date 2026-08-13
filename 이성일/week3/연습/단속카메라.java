import java.util.Arrays;

class Solution {
    public int solution(int[][] routes) {
        // 1. 정렬
        Arrays.sort(routes,
                (o1, o2) -> (o1[0] == o2[0] ? Integer.compare(o1[1], o2[1]) : Integer.compare(o1[0], o2[0])));

        // 1.1 정렬 기준은 출발지점, 같으면 도착지점
        // 2. 설치 구역 설정 후 루프 돌면서 현재 설치 구역 도착지점과 출발지점 비교
        int[] area = Arrays.copyOf(routes[0], 2);
        int camera = 1;
        for (int[] route : routes) {
            // 2.2 비교 후 같거나 작으면 구역을 현재 차량 출발지점과 기존 차량 도착지점으로 변경
            // 2.3 더 크면, 새 구역으로 현재 차량의 경로를 설치
            // 새 구역이 하나 생길 때마다 설치 카메라 개수 + 1
            if (route[0] <= area[1]) {
                area[0] = route[0];
                area[1] = Math.min(route[1], area[1]);
            } else {
                camera++;
                area[0] = route[0];
                area[1] = route[1];
            }

        }

        return camera;
    }
}
