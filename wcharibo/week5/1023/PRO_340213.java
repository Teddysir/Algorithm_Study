import java.util.*;

class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        String answer = "";
        StringBuilder sb = new StringBuilder();
        
        StringTokenizer st = new StringTokenizer(video_len, ":");
        int video = Integer.parseInt(st.nextToken())*60 + Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(pos, ":");
        int current = Integer.parseInt(st.nextToken())*60 + Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(op_start, ":");
        int start = Integer.parseInt(st.nextToken())*60 + Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(op_end, ":");
        int end = Integer.parseInt(st.nextToken())*60 + Integer.parseInt(st.nextToken());
        
        if(start <= current && current <= end){
            current = end;
        }
        
        for(String command : commands){
            if(command.equals("next")){
                if(video - current < 10){
                    current = video;
                }else{
                    current += 10;
                }
            }else{
                if(current < 10){
                    current = 0;
                }else{
                    current -= 10;
                }
            }
            if(start <= current && current <= end){
                    current = end;
                }
        }
        
        if(current/60 < 10){
            sb.append(0).append(current/60);
        }else{
            sb.append(current/60);
        }
        
        sb.append(":");
        
        if(current%60 < 10){
            sb.append(0).append(current%60);
        }else{
            sb.append(current%60);
        }
        
        return sb.toString();
    }
}
