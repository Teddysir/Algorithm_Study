import java.util.*;

class Solution {

    public int solution(int[] queue1, int[] queue2) {
        int answer = -2;
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        long sum = 0;
        int max = 0;
        int count = 0;
        long q1_sum = 0;
        long q2_sum = 0;

        for (int x : queue1) {
            q1.offer(x);
            q1_sum += x;
            max++;
        }
        for (int x : queue2) {
            q2.offer(x);
            q2_sum += x;
            max++;
        }
        max *= 2;
        sum = q1_sum + q2_sum;
        if (sum % 2 == 1) {
            answer = -1;
        } else {
            while (count < max) {
                if (q1_sum == q2_sum) {
                    break;
                }
                if (q1_sum > q2_sum) {
                    int x = q1.poll();
                    q2.offer(x);
                    q1_sum -= x;
                    q2_sum += x;
                } else {
                    int x = q2.poll();
                    q1.offer(x);
                    q1_sum += x;
                    q2_sum -= x;
                }
                count++;
            }
            if (count == max)
                answer = -1;
            else
                answer = count;
        }
        return answer;
    }
}