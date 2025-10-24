import java.util.*;
class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        String[] arr = video_len.split(":", 2);
        int len_mm = Integer.parseInt(arr[0]);
        int len_ss = Integer.parseInt(arr[1]);
        int len = len_mm * 60 + len_ss;
        
        String[] arr_2 = pos.split(":", 2);
        int pos_mm = Integer.parseInt(arr_2[0]);
        int pos_ss = Integer.parseInt(arr_2[1]);
        int posval = pos_mm * 60 + pos_ss;
        
        String[] arr_3 = op_start.split(":", 2);
        int start_mm = Integer.parseInt(arr_3[0]);
        int start_ss = Integer.parseInt(arr_3[1]);
        int ops = start_mm * 60 + start_ss;
        
        String[] arr_4 = op_end.split(":", 2);
        int end_mm = Integer.parseInt(arr_4[0]);
        int end_ss = Integer.parseInt(arr_4[1]);
        int ope = end_mm * 60 + end_ss;
        
        int[] ans = new int[2];
        for(String cmd : commands) {
            if(posval >= ops && posval <= ope) {
                    posval = ope;
            }
            
            if(cmd.equals("next")) {
                if(Math.abs(len - posval) < 10 ) posval = len;
                else posval+= 10;
            }
            else if(cmd.equals("prev")) {
                if(posval < 10) posval = 0;
                else {
                    posval -= 10;
                }
                
            }
            if(posval >= ops && posval <= ope) {
                    posval = ope;
            }
            
        }
        
        
        
        // System.out.println(len_mm);
        // String answer = arr[0];
        String ans1;
        String ans2;
        pos_mm = posval / 60;
        pos_ss = posval % 60;
        if(pos_mm < 10){
            ans1 = "0" + String.valueOf(pos_mm);
        } else ans1 = String.valueOf(pos_mm);
        
        if(pos_ss < 10) {
            ans2 = "0" + String.valueOf(pos_ss);
        } else ans2 = String.valueOf(pos_ss);
        
        String answer = ans1 + ":" + ans2;
        return answer;
    }
    
}



