// import java.util.*;

// class Solution {
//     static class Pair{
//         int x;
//         int y;

//         public Pair(int x, int y){
//             this.x = x;
//             this.y = y;
//         }
//     }
//     static Queue<Pair> q;
//     static boolean[][] visited;
//     static int[][] dir = {{-1,0}, {1,0}, {0,-1}, {0,1}};
//     static int n, m;
//     public int solution(int[][] land) {
//         int answer = 0;
//         q = new ArrayDeque<>();
//         n = land.length;
//         m = land[0].length;
//         for(int j=0; j<m; j++){
//             visited = new boolean[n][m];
//             int count = 0;
//             for(int i=0; i<n; i++){
//                 if(land[i][j] == 1){
//                     visited[i][j] = true;
//                     count++;
//                     q.offer(new Pair(i, j));
//                 }
//             }
//             count = bfs(land, count);
//             answer = Integer.max(answer, count);
//         }
//         return answer;
//     }

//     private static int bfs(int[][] land, int count){
//         while(!q.isEmpty()){
//             Pair cur = q.poll();
//             for(int z=0; z<4; z++){
//                 int nx = cur.x + dir[z][0];
//                 int ny = cur.y + dir[z][1];
//                 if(nx < 0 || nx >= n || ny < 0 || ny >= m) continue;

//                 if(!visited[nx][ny] && land[nx][ny] == 1){
//                     visited[nx][ny] = true;
//                     count++;
//                     q.offer(new Pair(nx, ny));
//                 }
//             }
//         }
//         return count;
//     }
// }

import java.util.*;

class Solution {
    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static Queue<Pair> q;
    static boolean[][] visited;
    static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static int n, m;
    static int[][] s_land;

    public int solution(int[][] land) {
        int answer = 0;
        q = new ArrayDeque<>();
        n = land.length;
        m = land[0].length;
        int index = 2;
        visited = new boolean[n][m];
        s_land = new int[n][m];
        for (int i = 0; i < n; i++) {
            s_land[i] = Arrays.copyOf(land[i], m);
        }

        HashMap<Integer, Integer> hm = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int count = 1;
                if (s_land[i][j] == 1 && !visited[i][j]) {
                    visited[i][j] = true;
                    s_land[i][j] = index;
                    q.offer(new Pair(i, j));
                    count = bfs(index, count);
                    hm.put(index, count);
                    index++;
                }
            }
        }

        for (int j = 0; j < m; j++) {
            boolean[] used = new boolean[hm.size()];
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if (s_land[i][j] == 0)
                    continue;
                int k = s_land[i][j];
                if (!used[k - 2]) {
                    used[k - 2] = true;
                    sum += hm.get(k);
                }
            }
            answer = Integer.max(answer, sum);
        }
        return answer;
    }

    private static int bfs(int index, int count) {
        while (!q.isEmpty()) {
            Pair cur = q.poll();
            for (int z = 0; z < 4; z++) {
                int nx = cur.x + dir[z][0];
                int ny = cur.y + dir[z][1];
                if (nx < 0 || nx >= n || ny < 0 || ny >= m)
                    continue;

                if (!visited[nx][ny] && s_land[nx][ny] == 1) {
                    visited[nx][ny] = true;
                    count++;
                    s_land[nx][ny] = index;
                    q.offer(new Pair(nx, ny));
                }
            }
        }
        return count;
    }
}