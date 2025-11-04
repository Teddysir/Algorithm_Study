class Solution {
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;
        
        short[][][] visited = new short[100][100][20_000];
        for (int i = 0; i < routes.length; i++) {
            int time = 0;
            int r = points[routes[i][0] - 1][0] - 1;
            int c = points[routes[i][0] - 1][1] - 1;
            if (visited[r][c][time] == 1) {
                answer++;
            }
            visited[r][c][time++]++;
            for (int j = 1; j < routes[i].length; j++) {
                int destR = points[routes[i][j] - 1][0] - 1;
                int destC = points[routes[i][j] - 1][1] - 1;
                while(r != destR || c != destC) {
                    if (r != destR) {
                        if (r < destR) {
                            r++;
                            if (visited[r][c][time] == 1) {
                                answer++;
                            }
                            visited[r][c][time++]++;
                        } else {
                            r--;
                            if (visited[r][c][time] == 1) {
                                answer++;
                            }
                            visited[r][c][time++]++;
                        }
                    } else if (c != destC) {
                        if (c < destC) {
                            c++;
                            if (visited[r][c][time] == 1) {
                                answer++;
                            }
                            visited[r][c][time++]++;
                        } else {
                            c--;
                            if (visited[r][c][time] == 1) {
                                answer++;
                            }
                            visited[r][c][time++]++;
                        }
                    }
                }
            }
        }
        
        return answer;
    }
}
