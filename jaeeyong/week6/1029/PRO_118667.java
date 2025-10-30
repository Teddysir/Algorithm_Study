import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int answer = -1;
        Queue<Long> q1 = new ArrayDeque<>();
        Queue<Long> q2 = new ArrayDeque<>();
        
        // 두 큐에 담긴 모든 원소의 합 구하기
        long sum1 = 0L;
        long sum2 = 0L;
        long current = 0L;
        for(int i = 0; i < queue1.length; i++) {
            current = queue1[i];
            sum1 += current;
            q1.offer(current);
        }
        for(int i = 0; i < queue2.length; i++) {
            current = queue2[i];
            sum2 += current;
            q2.offer(current);
        }
        
        // 합이 큰 큐에서 작은 큐로 값 이동시키기
        int count = 0;
        while(true) {
            if(sum1 < sum2) {
                current = q2.poll();
                q1.offer(current);
                sum1 += current;
                sum2 -= current;
                count++;
            } else if (sum1 > sum2) {
                current = q1.poll();
                q2.offer(current);
                sum2 += current;
                sum1 -= current;
                count++;
            } else {
                return count;
            }

            // 이동 횟수가 두 큐 길이의 합의 2배보다 크면 종료
            if(count >= (queue1.length + queue2.length) * 2) {
                return -1;
            }
        }
    }
}
