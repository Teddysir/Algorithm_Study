import java.util.*;

class Solution {
    public int solution(int distance, int[] rocks, int n) {
        int answer = 0;
        
        int low = 0;
        int high = distance;
        
        Arrays.sort(rocks);
        
        while(low <= high){
            int removed = 0;
            int prev = 0;
            int mid = low + (high - low)/2;
            
            for(int rock : rocks){
               
                if(rock - prev < mid){
                    removed++;
                }else{
                    prev = rock;
                }
            }
            
            if(distance - prev < mid) removed++;
            
            if(removed <= n){
                answer = mid;
                low = mid + 1;
            }else{
                high = mid - 1;
            }
            
        }
        
        
        return answer;
    }
}
