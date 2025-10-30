class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int answer = -1;
        long total = 0;
        int[] combo = new int[queue1.length*2];
        long cur = 0;
        
        for(int i = 0; i < queue1.length; i++){
            total += queue1[i];
            total += queue2[i];
            cur += queue1[i];
            combo[i] = queue1[i];
            combo[i + queue1.length] = queue2[i];
        }
        
        int start = 0;
        int end = queue1.length - 1;
        int operation = 0;
        
        while(start <= end && end < queue1.length*2){
            
            if(cur == total/2){
                return operation;
            }else if(cur < total/2){
                //없으니까 범위 벗어나서 런타임 에러남
                if(end == queue1.length*2 - 1) break;
                cur += combo[++end];
                operation++;
            }else{
                cur -= combo[start++];
                operation++;
            }
        }
        
        return answer;
    }
}
