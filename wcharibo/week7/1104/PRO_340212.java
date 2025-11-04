class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        int answer = Integer.MAX_VALUE;
        int maxLevel = 0;
        for(int i = 0; i < diffs.length; i++) maxLevel = Math.max(maxLevel, diffs[i]);
        
        int start = 1;
        int end = maxLevel;
        //이분탐색
        while(start <= end){
            int mid = (start + end)/2;
            //0번의 난이도는 항상 1이므로
            long time = times[0];
            //난이도가 현재 선택된 난이도(mid)보다 낮다면 0, 아니면 연산
            for(int i = 1; i < diffs.length; i++){
                time += ((diffs[i] > mid ? diffs[i] - mid : 0)*(times[i] + times[i-1]) + times[i]);
            }
            
            if(time > limit){
                start = mid + 1;
            }else{
            //성공한(limit보다 작은) 경우에만 결과 업데이트
                answer = Math.min(answer, mid);
                end = mid - 1;
            }
        }
        
        return answer;
    }
}
