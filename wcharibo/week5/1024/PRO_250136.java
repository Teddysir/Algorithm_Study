import java.util.*;

class Solution {
    public int solution(int[][] land) {
        int answer = 0;
        int n = land.length;
        int m = land[0].length;
        //bfs 방문 처리
        boolean[][] vis = new boolean[n][m];
        //열당 결과 기록
        int[] result = new int[m];
        //4방 탐색
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        //2차원 배열 순회하며 석유 시작점 찾기
        for(int i = 0 ; i < n; i++){
            for(int j = 0; j < m; j++){
                //석유가 있고 방문한 적 없으면? go
                if(land[i][j] == 1 && !vis[i][j]){
                    //bfs용 queue
                    Queue<int[]> q = new ArrayDeque<>();
                    //bfs수행하며 방문하는 열 기록할 Set
                    Set<Integer> cols = new HashSet<>();
                    //방문 처리 초기화
                    vis[i][j] = true;
                    q.add(new int[]{i, j});
                    int size = 0;
                    
                    while(!q.isEmpty()){
                        int cr = q.peek()[0];
                        int cc = q.poll()[1];
                        size++;
                        //방문한 열 추가
                        cols.add(cc);
                        
                        for(int dir = 0; dir < 4; dir++){
                            int nr = cr + dirs[dir][0];
                            int nc = cc + dirs[dir][1];
                            
                            if(0 <= nr && nr < n && 0 <= nc && nc < m){
                                if(land[nr][nc] == 1 && !vis[nr][nc]){
                                    vis[nr][nc] = true;
                                    q.add(new int[]{nr, nc});
                                }
                            }
                            
                            
                        }
                    }
                    
                    //방문한 열들 돌며 발견된 석유의 크기 저장
                    for(int col : cols){
                        result[col] += size;
                    }
                }
            }
        }
        
        
        //최대값 찾기
        for(int i = 0; i < m; i++){
            answer = Math.max(answer, result[i]);
        }
        
        
        return answer;
    }
}
