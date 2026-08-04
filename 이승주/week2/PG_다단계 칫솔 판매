import java.util.*;
class Solution {
    
    static HashMap<String, Integer> map;
    static String[] parent;
    static int[] profit;
    static int[] result;
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        this.parent = referral;
        result = new int[enroll.length];
        
        profit = new int[amount.length];
        
        for(int i = 0; i < seller.length; i++){
            profit[i] = amount[i]*100;
        }
        
        map = new HashMap<>();
        
        for(int i = 0; i < enroll.length; i++){
            map.put(enroll[i], i); // value값으로 인덱스를 저장
        }
        
        for(int i = 0; i < seller.length; i++){
            int index = map.get(seller[i]);
            String person = parent[index];
            if(person.equals("-")){
                result[index] += profit[i] - profit[i]/10;
            }else{
                dfs(seller[i],person,profit[i]); //금액을 나눠주는 사람, 금액을 받아야하는 사람, 전체 금액
            }
        }
        
        return result;
        
        
    }
    
    static void dfs(String from, String to, int cost){
        
        int money = cost / 10;
        if(money < 1){
            int index = map.get(from);
            result[index] += cost;
            return;
        }else{
            int fromIndex = map.get(from);
            result[fromIndex] += cost - money;
            
             int toIndex = map.get(to);
            
            if(!(parent[toIndex].equals("-"))){
                dfs(to,parent[toIndex],money);
            }else{
                result[toIndex] += money - money/10;
                return;
            }
        }
        return;
        
        
    }
}
