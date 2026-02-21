class Solution {
    public long calc(long mid, int[] times){
        long result = 0;
        
        for(int time : times){
            result += (mid/time);    
        }
        
        return result;
    }
    
    public long solution(int n, int[] times) {
        long answer = 0L;
        
        long low = 1L;
        long high = 1000000000L * 100000L;
        
        while(low <= high){
            long mid = low + (high - low)/2;
            
            long cur = calc(mid, times);
            
            if(cur >= n){
                answer = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        
        
        return answer;
    }
}
