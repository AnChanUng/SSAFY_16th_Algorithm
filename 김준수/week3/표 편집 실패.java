
import java.util.*;

class Solution {
    class Chart{
        int curr;
        List<Integer> names;
        Deque<int[]> stack;
        
        Chart(int n, int curr){
            this.curr = curr;
            names = new ArrayList<>();
            stack = new ArrayDeque<>();
            
            for(int i = 0; i < n; i++){
                names.add(i);
            }
        }
        
        public void delete(){
            int del = names.get(curr);
            names.remove(curr);
            stack.push(new int[]{curr, del});
            
            // 마지막 행이면
            if(curr == names.size()){
                curr--;
            }
        }
        
        public void recover(){
            int[] item = stack.pop();
            names.add(item[0], item[1]);
            
            // 선택 행이 복구 행보다 뒤에 있으면
            if(curr >= item[0]){
                curr++;
            }
        }
    }
    
    public String solution(int n, int k, String[] cmd) {
        Chart chart = new Chart(n, k);
        
        for(String c : cmd){
            StringTokenizer st = new StringTokenizer(c);
            String curr = st.nextToken();
            
            switch(curr){
                case "U":
                    int uNum = Integer.parseInt(st.nextToken());
                    chart.curr -= uNum;
                    break;
                case "D":
                    int dNum = Integer.parseInt(st.nextToken());
                    chart.curr += dNum;
                    break;
                case "C":
                    chart.delete();
                    break;
                case "Z":
                    chart.recover();
                    break;
            }
        }
        
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < n; i++){
            if(chart.names.contains(i)){
                sb.append("O");
            }
            else{
                sb.append("X");
            }
        }
        
        return sb.toString();
    }
}