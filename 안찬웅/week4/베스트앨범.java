import java.util.*;
/*
    장르 별 가장 많이 재생된 노래를 두개씩 모으기
    
    노래 수록 기준
    1. 많이 재생된 장르
    2. 많이 재싱된 노래
    3. 고유 번호가 낮은 노래
    
    genres: 노래 장르
    plays: 노래별 재생 횟수
    
    많이 재생된 장르: classic -> pop
    : play[3] -> play[0] -> play[1] => 3, 0
    : play[4] -> play[1] => 4, 1
    
    1. 장르별 전체 재생횟수를 구합니다.
    2. 많이 재생된 장르, 많이재싱된 노래, 고유번호가 낮은 노래 순으로 정렬합니다.
*/
class Solution {
    class Node implements Comparable<Node> {
        int genresCount; // 장르별 노래횟수
        int playsCount; // 노래별 횟수
        int idx; // 인덱스=고유번호
        String genres; // 장르이름
        
        public Node(int genresCount, int playsCount, int idx, String genres) {
            this.genresCount = genresCount;
            this.playsCount = playsCount;
            this.idx = idx;
            this.genres = genres;
        }
        
        @Override
        public int compareTo(Node o) {
            if(this.genresCount != o.genresCount) { // 장르별 노래횟수내림차순
                return Integer.compare(o.genresCount, this.genresCount);
            }
            if(this.playsCount != o.playsCount) { // 노래별 횟수
                return Integer.compare(o.playsCount, this.playsCount);
            }
            return Integer.compare(this.idx, o.idx);
        }
        
        @Override
        public String toString() {
            return genresCount + " " + playsCount + " " + idx + " " + genres;
        }
    }  
    public int[] solution(String[] genres, int[] plays) {
        int[] answer = {};
        
        HashMap<String, Integer> sing = new HashMap<>(); // 장르별 전체재생횟수
        HashMap<Integer, Integer> idx = new HashMap<>(); // 인덱스 재생횟수
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // 1. 장르별 전체 재생횟수를 구합니다.
        for(int i=0; i<genres.length; i++) {
            if(!sing.containsKey(genres[i])) {
                sing.put(genres[i], plays[i]);    
            } else {
                sing.put(genres[i], sing.get(genres[i]) + plays[i]);
            }
        }
        
        // 장르별 노래횟수, 노래별 횟수, 인덱스=고유번호, 장르이름
        for(int i=0; i<genres.length; i++) {
            int singCnt = 0; // 장르별 노래횟수
            if(sing.containsKey(genres[i])) {
                singCnt = sing.get(genres[i]);
            }    
            pq.add(new Node(singCnt, plays[i], i, genres[i]));
        }
        
        // 2. 인덱스 재생횟수
        for(int i=0; i<plays.length; i++) {
            if(!idx.containsKey(i)) {
                idx.put(i, plays[i]);
            } else {
                idx.put(i, idx.get(i) + plays[i]);
            }
        }
        // 장르별로 2개씩 뽑기 뽑아서 리스트에 저장
        // int genresCount; // 장르별 노래횟수
        // int playsCount; // 노래별 횟수
        // int idx; // 인덱스=고유번호
        // String genres; // 장르이름
        int cnt = 0;
        String str = "";
        ArrayList<Integer> list = new ArrayList<>();
        while(!pq.isEmpty()) {
            Node n = pq.poll();
            // 처음일때
            if(str.equals("")) {
                str = n.genres;
                cnt++;
                list.add(n.idx);
                continue;
            }
            
            // 문자열이 같고 두번 이하일 때
            if(str.equals(n.genres) && cnt <= 1) {
                str = n.genres;
                cnt++;
                list.add(n.idx);  
            // 문자열이 같고 세번 이상일 때
            } else if (str.equals(n.genres) && cnt >= 2) {
                str = n.genres;
                cnt++;
            } else { // 문자열이 다를때
                str = n.genres;
                list.add(n.idx);
                cnt = 1;
            }
        }
        
        int[] res = new int[list.size()];
        for(int i=0; i<list.size(); i++) {
            res[i] = list.get(i);
        }
        
        return res;
    }
}
