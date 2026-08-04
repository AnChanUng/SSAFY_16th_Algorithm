package codetest;

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        // 1. enroll 입력받고 이름별로 직원번호 mapping
        Map<String, Integer> empNo = new HashMap<>();
        for (int i = 0; i < enroll.length; i++){
            empNo.put(enroll[i], i);
        }
        empNo.put("-", enroll.length);
        int[] account = new int[enroll.length];
            // 1-1. referrel에 직원번호를 인덱스로 활용하여 추천인 확인
        // 2. seller와 amount를 읽으며 center까지 이익 분배하기
        for (int i = 0; i < seller.length; i++){
            int publisherN = empNo.get(seller[i]);
            int money = amount[i] * 100;
            int pay = money / 10;

            account[publisherN] += (money - pay);
            
            String recommender = referral[publisherN];
            int recommenderN = empNo.get(recommender);
            
            if (pay < 1) {
                account[recommenderN] += money;
                continue;
            }
            
            while (!recommender.equals("-")) {
                money = pay;
                pay = money / 10;
                if (pay < 1) {
                    account[recommenderN] += money;
                    break;
                }
                account[recommenderN] += (money - pay);
                recommender = referral[recommenderN];
                recommenderN = empNo.get(recommender);
            
            }
            
        }
        
        
        return account;
    }
}