class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        int answer = 0;
        
        int left = 1;
        int right = 100_000;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if(check(mid, diffs, times, limit)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return answer;
    }
    
    private static boolean check(int level, int[] diffs, int[] times, long limit) {
        long total = 0L;
        for (int i = 0; i < diffs.length; i++) {
            total += diffs[i] <= level ? times[i] : (times[i] + times[i - 1]) * (diffs[i] - level) + times[i];
        }
        return total <= limit;
    }
}
