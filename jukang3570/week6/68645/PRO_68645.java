class Solution {
    int[] dr = {1, 0, -1};
    int[] dc = {0, 1, -1};
    
    public int[] solution(int n) {
        int size = (n * (n + 1)) / 2;
        boolean[][] visited = new boolean[n+1][n+1];
        int[][] arr = new int[n][n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<=i; j++) {
                visited[i][j] = true;
            }
        }
        // System.out.println(size);
        int cnt = 2;
        arr[0][0] = 1; visited[0][0] = false;
        int r =0; int c = 0; int dir =0;
        while(cnt <= size) {
            int nr = r + dr[dir];
            int nc = c + dc[dir];
            if(!(nr < 0 || nr >=n || nc < 0 || nc >= n) &&
               visited[nr][nc]) {
                arr[nr][nc] = cnt;
                cnt++;
                r = nr; c = nc;
                visited[nr][nc] = false;
            }
            else {
                for(int i=0; i<3; i++) {
                    if(i == dir) continue;
                    nr = r + dr[i];
                    nc = c + dc[i];
                    if(nr < 0 || nr >=n || nc < 0 || nc >= n ) continue;
                    if(!visited[nr][nc]) continue;
                
                    if(visited[nr][nc]) {
                        // System.out.println(nr + " " + nc);
                        dir = i;
                        arr[nr][nc] = cnt;
                        cnt++;
                        r = nr; c = nc;
                        visited[nr][nc] = false;
                        break;
                    }
                }
            }
        }
        
//         for(int i=0; i<n; i++) {
//             for(int j=0; j<n; j++){ 
//                 System.out.print(arr[i][j] + " ");
//             }
//             System.out.println();
//         }
        
//         for(int i=0; i<n; i++) {
//             for(int j=0; j<n; j++){ 
//                 System.out.print(visited[i][j] + " ");
//             }
//             System.out.println();
//         }
        int[] answer = new int[size];
        
        cnt =0;
        for(int i=0; i<n; i++) {
            for(int j=0; j<=i; j++) {
                answer[cnt] = arr[i][j];
                cnt++;
            }
        }
        
        
        
        
        return answer;
    }
}