/**
 * @Link https://school.programmers.co.kr/learn/courses/30/lessons/68645
 * @RunningTime 27.14 ms
 * @Memory 145 MB
 * @Strategy 시뮬레이션 (Simulation)
 * * 숫자를 채우는 규칙이 '아래 -> 오른쪽 -> 왼쪽 위 대각선' 순서로 반복되는 것을 파악.
 * * 이 3가지 방향을 방향 벡터 배열(dir)로 정의.
 * * n x n 크기의 2차원 배열(map)을 만들어 삼각 달팽이를 채우는 과정을 시뮬레이션.
 * * 현재 방향으로 계속 이동하다가, 더 이상 갈 수 없는 경우(배열 범위를 벗어나거나 이미 숫자가 채워진 칸)를 만나면 방향을 바꾼다.
 * * 모든 숫자를 다 채울 때까지 이 과정을 반복.
 * * 마지막으로, 2차원 배열에 채워진 숫자들을 순서대로 1차원 배열에 옮겨 담아 반환.
 */
class Solution {
    public int[] solution(int n) {
        // end: 삼각형에 채워져야 할 총 숫자의 개수
        int end = n * (n + 1) / 2;
        int[] answer = new int[end];
        
        int count = 1; // 채워 넣을 현재 숫자
        
        // dir: 이동 방향을 정의한 벡터 배열 (0:아래, 1:오른쪽, 2:왼쪽 위 대각선)
        int[][] dir = { { 1, 0 }, { 0, 1 }, { -1, -1 } };
        // map: 숫자를 채울 2차원 공간
        int[][] map = new int[n][n];
        
        int row = 0; // 현재 위치 (행)
        int col = 0; // 현재 위치 (열)
        int d = 0;   // 현재 방향 인덱스
        
        map[0][0] = 1; // 시작점 (0,0)에 1을 채움
        
        // count가 마지막 숫자인 end에 도달할 때까지 반복
        while (count < end) {
            // nr, nc: 현재 방향으로 이동했을 때의 다음 위치
            int nr = row + dir[d][0];
            int nc = col + dir[d][1];
            int newCount = count + 1; // 다음에 채울 숫자
            
            // 다음 위치가 유효한지(배열 범위 내에 있고, 아직 채워지지 않았는지) 확인
            if (0 <= nr && nr < n && 0 <= nc && nc < n && map[nr][nc] == 0) {
                // 유효하다면, 해당 위치에 숫자를 채우고 현재 위치와 카운터를 업데이트
                map[nr][nc] = newCount;
                row = nr;
                col = nc;
                count = newCount;
            } else {
                // 유효하지 않다면(벽에 부딪혔거나 이미 채워진 칸), 방향을 바꿈
                d = (d + 1) % 3; // 0 -> 1 -> 2 -> 0 순환
            }
        }
        
        // 2차원 map 배열을 1차원 answer 배열로 변환
        count = 0; // answer 배열의 인덱스로 재활용
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 해당 칸이 비어있으면(삼각형 바깥 부분) 그 줄의 탐색을 멈춤
                if (map[i][j] == 0) {
                    break;
                } else {
                    // 숫자가 있으면 순서대로 answer 배열에 추가
                    answer[count++] = map[i][j];
                }
            }
        }
        
        return answer;
    }
}
