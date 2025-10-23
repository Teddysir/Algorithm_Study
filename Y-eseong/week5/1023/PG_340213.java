class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        String answer = "";
        // 분, 초 나누기 => time으로 합치기
        String[] video_split = video_len.split(":");
        int video_min = Integer.parseInt(video_split[0]);
        int video_sec = Integer.parseInt(video_split[1]);
        int video_time = video_min * 60 + video_sec;

        String[] pos_split = pos.split(":");
        int pos_min = Integer.parseInt(pos_split[0]);
        int pos_sec = Integer.parseInt(pos_split[1]);
        int pos_time = pos_min * 60 + pos_sec;

        String[] op_start_split = op_start.split(":");
        int start_min = Integer.parseInt(op_start_split[0]);
        int start_sec = Integer.parseInt(op_start_split[1]);
        int start_time = start_min * 60 + start_sec;

        String[] op_end_split = op_end.split(":");
        int end_min = Integer.parseInt(op_end_split[0]);
        int end_sec = Integer.parseInt(op_end_split[1]);
        int end_time = end_min * 60 + end_sec;

        if (pos_time >= start_time && pos_time <= end_time)
            pos_time = end_time;

        for (String x : commands) {
            if ("next".equals(x)) {
                pos_time += 10;
                if (pos_time > video_time)
                    pos_time = video_time;
            } else if ("prev".equals(x)) {
                pos_time -= 10;
                if (pos_time < 0)
                    pos_time = 0;
            }

            if (pos_time >= start_time && pos_time <= end_time)
                pos_time = end_time;
        }

        int answer_min = pos_time / 60;
        int answer_sec = pos_time % 60;

        if (answer_min < 10)
            answer += "0";
        answer += answer_min + ":";

        if (answer_sec < 10)
            answer += "0";
        answer += answer_sec;

        return answer;
    }
}