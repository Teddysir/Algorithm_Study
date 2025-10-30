import java.util.*;

class Solution {
    static int cnt, limit; 
    static long target;
    
    public int solution(int[] queue1, int[] queue2) {
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        cnt = 0;
        long val1 = 0;
        long val2 = 0;
        limit = queue1.length * 3;
        for(int a : queue1) {
            q1.add(a);
            val1 += a;
        }
        for(int a : queue2) {
            q2.add(a);
            val2 += a;
        }
        System.out.println(val1 + " " + val2);
        if(val1 == val2 ) return 0;
        simulation(q1, q2, val1, val2);
        
        
        
        int answer = cnt;
        return answer;
    }
    static void simulation(Queue<Integer> q1, Queue<Integer> q2, long val1, long val2) {
        while(cnt < limit) {
            if(val1 == val2) return;
            if(val1 > val2) {
                int val = q1.poll();
                val1 -= val; val2 += val;
                q2.add(val);
                cnt++;
            }
            else if(val2 > val1) {
                int val = q2.poll();
                val2 -= val; val1 += val;
                q1.add(val);
                cnt++;
            }
            
        }
        if(val1 != val2) cnt = -1;
        return;
        
    }
}