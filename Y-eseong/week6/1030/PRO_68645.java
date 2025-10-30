class Solution {
    public int[] solution(int n) {
        int all = 0;
        for (int i = 1; i <= n; i++)
            all += i;
        int[] answer = new int[all];
        int value = 1;
        int i = 0, j = 0;
        int dir = 0; // 0 아래 1 오른쪽 2 좌상

        int[][] map = new int[n][n];
        while (value <= all) {
            map[i][j] = value++;
            if (dir == 0) {
                i++;
                if (i == n || map[i][j] != 0) {
                    i--;
                    j++;
                    dir = 1;
                }
            } else if (dir == 1) {
                j++;
                if (j == n || map[i][j] != 0) {
                    j -= 2;
                    i--;
                    dir = 2;
                }
            } else if (dir == 2) {
                i--;
                j--;
                if (map[i][j] != 0) {
                    i += 2;
                    j++;
                    dir = 0;
                }
            }
        }

        int index = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j <= i; j++) {
                answer[index++] = map[i][j];
            }
        }
        return answer;
    }
}