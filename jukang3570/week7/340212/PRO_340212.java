import java.util.*;

class Solution {
    public int solution(int[] diffs, int[] times, long limit) {

        int left = 1;
        int right = 100000; //diffs 값 최대값으로 잡기/

        int ans = Integer.MAX_VALUE;

        while(left <= right) {
            int mid = (left + right) / 2;
            long sum = 0;

            for(int i = 0; i < diffs.length; i++) {
                if(diffs[i] <= mid) {
                    sum += times[i];
                } else {
                    int diff = diffs[i] - mid;
                    sum += (long)(times[i] + times[i-1]) * diff + times[i];
                }
            }

            if(sum <= limit){   //limit 안에는 들어야하니깐 이분 탐색의 mid 값을 줄여야겠지?
                ans = Math.min(ans, mid);
                right = mid - 1;
            } else { //limit 밖에 벗어나니 이분 탐색 범위를 늘려야지?
                left = mid + 1;
            }
        }

        return ans;
    }
}
