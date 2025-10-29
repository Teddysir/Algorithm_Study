import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

class Solution {
	static int[] dr = { 1, -1, 0, 0 };
	static int[] dc = { 0, 0, 1, -1 };
	static int answer;
	static int row_l, col_l;
	static boolean visited[][];
	static boolean visit[];
	static int[] answers;

	public int solution(int[][] land) {
		answer = 0;

		row_l = land.length; // 행 길이,
		col_l = land[0].length; // 열 길이

		answers = new int[col_l];
		visit = new boolean[col_l];
		visited = new boolean[row_l][col_l];

		for (int i = 0; i < row_l; i++) {
			for (int j = 0; j < col_l; j++) {
				if (land[i][j] == 1 && !visited[i][j]) {
					bfs(i, j, land);
				}
			}
		}

		for(int i = 0 ; i < answers.length; i++) {
			if(answer < answers[i]) {
				answer = answers[i];
			}
		}
		return answer;
	}

	static void bfs(int r, int c, int[][] land) {
		Queue<int[]> q = new ArrayDeque<int[]>();
		q.add(new int[] { r, c });
		visited[r][c] = true;
		Set<Integer> set = new HashSet<Integer>();

		int count = 1;

		while (!q.isEmpty()) {
			int[] temp = q.poll();
			set.add(temp[1]); // 열

			for (int i = 0; i < 4; i++) {
				int nr = temp[0] + dr[i];
				int nc = temp[1] + dc[i];

				if (nr >= 0 && nc >= 0 && nr < row_l && nc < col_l) {
					if (!visited[nr][nc] && land[nr][nc] == 1) {
						count += 1;
						visited[nr][nc] = true;
						q.add(new int[] { nr, nc });
					}
				}
			}

		}

		for (int s : set) {
			answers[s] += count;
		}
	}
}