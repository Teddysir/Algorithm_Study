import java.util.*;

class Solution {

	static int[] dr = { 1, 0, -1 }; // 아래, 오른쪽, 대각위왼쪽,
	static int[] dc = { 0, 1, -1 };

	static int size;
	static int[][] arr;
	static boolean[][] visited;

	public int[] solution(int n) {

		size = 0;
		for (int i = n; i > 0; i--) {
			size += i;
		}

		int[] answer = new int[size];

		arr = new int[n + 2][n + 2];
		visited = new boolean[n + 2][n + 2];

		for (int i = 0; i <= n + 1; i++) {
			arr[i][0] = arr[0][i] = arr[n + 1][i] = arr[i][n + 1] = -1;
		}
		arr[1][1] = 1;
		visited[1][1] = true;

		int start_r = 1;
		int start_c = 1;
		int dir = 0;
		int count = 1;


		while (count != size) {

			int check_r = start_r + dr[dir];
			int check_c = start_c + dc[dir];

			if (arr[check_r][check_c] == -1 || visited[check_r][check_c]) {
				dir = (dir + 1) % 3;
			}

			start_r += dr[dir];
			start_c += dc[dir];
			count++;
			arr[start_r][start_c] = count;
			visited[start_r][start_c] = true;

		}

		int k = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (arr[i][j] != 0) {
					answer[k] = arr[i][j];
					k++;
				}
			}
		}

		return answer;
	}

}