class Solution {
    public int solution(String[] user_id, String[] banned_id) {
        int answer = 0;

        // 제재 아이디 별로 한명의 응모자를 매핑한 조합의 개수를 센다.

        // 응모자 아이디 컬렉트 dfs 함수
        // 제재 아이디 순으로 dfs 진행, 다음 아이디가 다음 스테이지
        // 스테이지별로 응모자아이디와 같은지 비교하기
        // 아이디 비교 시 길이가 다르면 비교 x
        // 어순으로 비교 , *은 pass 어순 비교 시 다르면 다른 문자 취급
        // 모든 어순 같으면 같은 문자
        // 같은 문자 발생하면 (미방문 일 시) 조합에 담고
        return answer;
    }
}
