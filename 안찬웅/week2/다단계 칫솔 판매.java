/*
    이익의 10% -> 추천인
    
    예외
    : if (이익의 10%가 1원미만) -> x
    : else 이익의 10% -> 추천인은
    
    알고리즘: DFS
    
    1. seller의 판매량을 hash로 만든다 Hash<seller, amount * 100>
    2. roop
     2.1 enroll[i]의 사람이 hash에 있으면
      2.1.1 판매자의 추천인 있으면 판매량의 10%를 추천인으로 저장
      2.1.2 판매자의 추천이 없으면 판매량 저장
     2.2 enroll[i]의 사람이 hash에 없으면 continue
*/
class Solution { // 판매원이름, 다른 판매원이름, 판매량 집계 데이터의 판매원 이름
    static int[] result;
    static Map<String, Integer> totalMoney; 
    static Map<String, Integer> sellMoney;
    static int center = 0;
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        totalMoney = new HashMap<>();
        sellMoney = new HashMap<>();
        result = new int[enroll.length];

        // seller의 판매량 <seller, amount * 100>
        for(int i=0; i<seller.length; i++) {
            sellMoney.put(seller[i], amount[i] * 100);
        }
        
        for(int i=0; i<enroll.length; i++) {
            if(!totalMoney.containsKey(enroll[i])) {
               totalMoney.put(enroll[i], 0);
            }
        }
        
        for(int i=0; i<enroll.length; i++) {
            if(sellMoney.containsKey(enroll[i])) {
                recommend(referral[i], sellMoney.get(enroll[i]), enroll, referral);
            }
        }
        System.out.println("최종답: " + totalMoney);
        return result;
    }
    /* 
        refferal의 추천인을 result에서 찾아 저장해주는 함수
        referralName: 추천인
        money: 판매자의 판매량
    */
    static void recommend(String referralName, int money, String[] enroll, String[] referral) {
        System.out.println("referralName: " + referralName + " money: " + money);
        if(referralName.equals("-")) {
            // 이익의 10%가 1원이상 저장
            // if(money * 0.1 >= 1) { 
            //     center += money * 0.1; 
            // }
            // 판매량의 90%는 본인에게 저장
            if(totalMoney.containsKey(referralName)) {
                totalMoney.put(referralName, totalMoney.get(referralName) + money - money / 10);
            }
            return;
        } else {
            for(int i=0; i<enroll.length; i++) {
                //System.out.println("referralName: " + referralName);
                if(enroll[i].equals(referralName)) {
                    recommend(referral[i], money - money / 10, enroll, referral);
                    if(sellMoney.containsKey(referralName)) {
                        totalMoney.put(enroll[i], totalMoney.get(referralName) + money - money / 10);
                    }
                }
            }
        }
    }
}
