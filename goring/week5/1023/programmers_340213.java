package forStudy.month04;

class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        String answer = "";
        
        String[] posS = pos.split(":");
        String[] opsS = op_start.split(":");
        String[] opeS = op_end.split(":");
        String[] endS = video_len.split(":");
        
        int[] time = new int[2];
        int[] op_s = new int[2];
        int[] op_e = new int[2];
        int[] endT = new int[2];
        for(int i=0; i<posS.length; i++) {
            time[i] = Integer.parseInt(posS[i]);
            op_s[i] = Integer.parseInt(opsS[i]);
            op_e[i] = Integer.parseInt(opeS[i]);
            endT[i] = Integer.parseInt(endS[i]);
        }
        
        time = checkTime(time, op_s, op_e, endT);
        
        for(int i=0; i<commands.length; i++) {
            
            if("prev".equals(commands[i])) {
                time[1] -= 10;
                
                if(time[1] < 0) {
                    time[1] += 60;
                    time[0] -= 1;
                }
                time = checkTime(time, op_s, op_e, endT);
                
            } else if("next".equals(commands[i])) {
                time[1] += 10;
                
                if(time[1] >= 60) {
                    time[1] -= 60;
                    time[0] += 1;
                }
                time = checkTime(time, op_s, op_e, endT);
            }
        }
        
        answer = (time[0]<10 ? ("0"+time[0]) : time[0]) + ":" + (time[1]<10 ? ("0"+time[1]) : time[1]);
        return answer;
    }
    
    public int[] checkTime(int[] time, int[] op_s, int[] op_e, int[] endT) {
        
        // 비디오 시작 시간 체크
        if(time[0] < 0 || (time[0] == 0 && time[1] < 0)) time = new int[] {0, 0};
        
        // 비디오 끝나는 시간 체크
        if(endT[0] < time[0] || (endT[0] == time[0] && endT[1] < time[1])) return new int[] {endT[0], endT[1]};
        
        // 비디오 오프닝 체크
        // 시간이 오프닝 시작보다 작거나, 오프닝 끝보다 큼 -> 오프닝 X
        if(time[0] < op_s[0] || op_e[0] < time[0]) return time;
        
        // 시간이 오프닝 시작과 같고, 초가 더 작음 -> 오프닝 X
        else if(time[0] == op_s[0] && time[1] < op_s[1]) return time;
        
        // 시간이 오프닝 끝과 같고, 초가 더 큼 -> 오프닝 X
        else if(time[0] == op_e[0] && op_e[1] < time[1]) return time;
        
        else return new int[] {op_e[0], op_e[1]};
        
    }
}