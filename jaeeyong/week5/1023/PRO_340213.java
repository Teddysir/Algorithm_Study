/**
 * @Link https://school.programmers.co.kr/learn/courses/30/lessons/340213
 * @RunningTime 2.16 ms
 * @Memory 77.7 MB
 * @Strategy 시뮬레이션 (Simulation)
 * * 1. "MM:SS" 형식의 모든 시간 문자열을 초(second) 단위의 정수로 변환하여 계산을 용이하게 한다.
 * * 2. commands 배열을 순회하며 "prev", "next" 명령을 처리한다.
 * * 3. 명령어를 처리하기 *전*에 현재 위치(p)가 OP 구간(s ~ e) 내에 있는지 확인하고, 해당하면 op_end로 위치를 점프시킨다. (skip 함수)
 * * 4. 명령어를 수행하여 현재 위치(p)를 10초씩 이동시킨다. (영상 경계 0, l을 넘지 않도록 처리)
 * * 5. 모든 명령어가 끝난 후, 최종 위치(p)가 OP 구간에 있을 수 있으므로 마지막으로 한 번 더 skip 로직을 적용한다.
 * * 6. 최종 위치(p)를 "MM:SS" 형식의 문자열로 변환하여 반환한다.
 */
import java.util.*;

class Solution {
    // solution: 메인 로직을 수행하는 함수
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        String answer = "";
        
        // 1. 모든 시간 문자열을 초 단위 정수로 변환
        int l = toSecond(video_len); // l: 영상 전체 길이 (seconds)
        int p = toSecond(pos); // p: 현재 재생 위치 (seconds)
        int s = toSecond(op_start); // s: OP 시작 시간 (seconds)
        int e = toSecond(op_end); // e: OP 종료 시간 (seconds)
        
        // 2. 명령어 배열 순회
        for (int i = 0; i < commands.length; i++) {
            // 3. [핵심] 명령어 수행 *전* OP 스킵 처리
            // (만약 현재 위치가 OP 구간이라면 op_end로 이동)
            p = skip(p, s, e);
            
            // 4. 명령어 수행
            if (commands[i].equals("prev")) {
                // "prev": 10초 뒤로, 0초 미만으로 내려가지 않음
                p = p - 10 >= 0 ? p - 10 : 0;
            } else if (commands[i].equals("next")) {
                // "next": 10초 앞으로, 영상 길이(l)를 초과하지 않음
                p = p + 10 <= l ? p + 10 : l;
            } 
            // (참고: 명령어 수행 *후*의 스킵 처리는 다음 루프의 시작(3번)에서 처리됨)
        }
        // 5. [핵심] 모든 명령이 끝난 후, 최종 위치에 대한 OP 스킵 처리
        // (마지막 명령어 수행 후 위치가 OP 구간일 수 있으므로)
        p = skip(p, s, e);
        
        // 6. 최종 위치를 "MM:SS" 문자열로 변환
        answer = toTimeStr(p);
        return answer;
    }
    
    /**
     * "MM:SS" 형식의 문자열을 초 단위 정수로 변환하는 헬퍼 함수
     * @param str "MM:SS" 형식의 시간 문자열
     * @return 총 초 (int)
     */
    static public int toSecond(String str) {
        StringTokenizer st = new StringTokenizer(str, ":");
        int minute = Integer.parseInt(st.nextToken());
        int second = Integer.parseInt(st.nextToken());
        return minute * 60 + second; // (분 * 60) + 초
    }
    
    /**
     * 초 단위 정수를 "MM:SS" 형식의 문자열로 변환하는 헬퍼 함수
     * @param second 총 초 (int)
     * @return "MM:SS" 형식의 시간 문자열 (두 자리 수 패딩 적용)
     */
    static public String toTimeStr(int second) {
        StringBuilder sb = new StringBuilder();
        int curMinute = second / 60; // 분 계산
        int curSecond = second % 60; // 초 계산
        // String.format을 사용하여 2자리 수(0으로 패딩) 형식으로 변환
        sb.append(String.format("%02d", curMinute)).append(":").append(String.format("%02d", curSecond));
        return sb.toString();
  S }
    
    /**
     * OP 구간 스킵 로직을 처리하는 헬퍼 함수
     * @param second 현재 위치 (초)
     * @param op_start OP 시작 시간 (초)
     * @param op_end OP 종료 시간 (초)
     * @return 스킵이 적용된 위치 (초)
     */
    static public int skip(int second, int op_start, int op_end) {
        // 현재 위치(second)가 OP 구간 [op_start, op_end] 내에 포함된다면
        if (op_start <= second && second <= op_end) {
            // 위치를 op_end (OP 종료 시간)로 즉시 이동시킴
            return op_end;
        }
        // OP 구간이 아니라면 원래 위치 반환
        return second;
    }
}
