/*
전략
- 시뮬레이션
- 각 매출이 발생할 때마다 배분금을 정산
    - 매출을 전체 정산한 후 배분금을 정산하면 절삭 오류 발생

시행착오
- 매출을 전체 정산한 후 배분금을 정산하도록 했는데 절삭 오류로 많이 틀림. 
  반례를 못찾아서 AI의 도움을 받음
- ex) 한 사람에게 500 500이 들어오면 정산금이 0.5, 0.5가 되어 절삭되어야 하는데
  이전 코드로 하면 총 매출이 1000이므로 정산금이 1이 되어 절삭되지 않음
*/

import java.util.*;

class Solution {
    class Seller{
        String name;
        int parent;
        int money;
        int plus;
        
        Seller(String name){
            this.name = name;
            this.parent = 0;
            this.money = 0;
            this.plus = 0;
        }
    }
    
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {   
        // 판매원 초기화
        List<Seller> sellers = new ArrayList<>();
        // center 초기화 먼저
        sellers.add(new Seller("center"));
        sellers.get(0).parent = -1;
        
        for(String e : enroll){
            sellers.add(new Seller(e));
        }
        
        // 추천인 초기화
        for(int i = 0; i < referral.length; i++){
            for(int j = 0; j < sellers.size(); j++){
                if(sellers.get(j).name.equals(referral[i])){
                    sellers.get(i + 1).parent = j;
                }
            }
        }
    
        // 매출 초기화
        for(int i = 0; i < seller.length; i++){
            for(Seller s : sellers){
                if(s.name.equals(seller[i])){
                    // 배분금 정산
                    // 매출 다 초기화 한 후 배분금 정산하면 절삭 오류 발생
                    // ex) 1명에게 매출 500 500 가는 경우 0.5 0.5라 절삭 필요
                    int money = amount[i] * 100;
                    int give = money / 10;
                    s.money += money - give;
                    Seller parent = sellers.get(s.parent);
                    
                    // center의 자식까지만 반복
                    while(parent.parent != -1){
                        int nextGive = give / 10;
                        give -= nextGive;
                
                        parent.plus += give;
                        parent = sellers.get(parent.parent);
                
                        give = nextGive;
                
                        if(give == 0){
                            break;
                        }
                    }
            
                    // center에게 배분금 정산
                    parent.plus += give;
                }
            }
        }
        
        int[] answer = new int[sellers.size() - 1];
        for(int i = 1; i < sellers.size(); i++){
            answer[i - 1] = sellers.get(i).money + sellers.get(i).plus;
        }
        
        return answer;
    }
}
