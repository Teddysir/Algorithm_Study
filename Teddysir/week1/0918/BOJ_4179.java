import java.io.*;
import java.util.*;

public class Main {

	static int R, C, ans;
	static char[][] map;

	static int[] start;
	static List<int[]> fires = new ArrayList<int[]>();
	static int[] dr = { 1, -1, 0, 0 };
	static int[] dc = { 0, 0, 1, -1 };

	static int[][] fire_map;
	static int[][] walk_map;
	static boolean[][] visited;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		map = new char[R][C];
		fire_map = new int[R][C]; // 불이 퍼지는 시간을 저장하는 맵
		walk_map = new int[R][C]; // 지훈이가 걸어가는데 걸리는 시간을 저장하는 맵
		visited = new boolean[R][C];
		ans = 100_100_1001;

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				fire_map[i][j] = walk_map[i][j] = Integer.MAX_VALUE; // 우선 모든 좌표의 값은 최대값으로 갱신,
			}
		}

		for (int i = 0; i < R; i++) {
			String input = br.readLine();
			for (int j = 0; j < C; j++) {
				map[i][j] = input.charAt(j);
				if (map[i][j] == 'J') {
					start = new int[] { i, j };// 지훈이의 좌표 저장
					walk_map[i][j] = 0; // 걷기 시작한곳의 값은 0
				}
				if (map[i][j] == 'F') {
					fires.add(new int[] { i, j }); // 불 좌표가 여러개..
					// 불시작점의 좌표 저장
					fire_map[i][j] = 0; // 불이 시작한곳의 값은 0
				}
			}
		}

		bfs_walk(start[0], start[1]); // 걷기 최적동선 갱신
		for (int i = 0; i < fires.size(); i++) {
			bfs_fire(fires.get(i)[0], fires.get(i)[1]);
			// 불 번지는 동선 최적동선 갱신
		}

		start(start[0], start[1]);

		if (ans == 100_100_1001) { // 만약 못가는 경우
			System.out.println("IMPOSSIBLE");
		} else {
			System.out.println(ans + 1);
		}

	}

	static void start(int r, int c) {
		Queue<int[]> q = new ArrayDeque<int[]>();
		q.add(new int[] { r, c });
		visited[r][c] = true;

		while (!q.isEmpty()) {
			int temp[] = q.poll();

			for (int i = 0; i < 4; i++) {
				int nr = temp[0] + dr[i];
				int nc = temp[1] + dc[i];

				if (nr < 0 || nc < 0 || nr >= R || nc >= C) { // 밖으로 나갈 경우탈출 성공 
					ans = Math.min(ans, walk_map[nr - dr[i]][nc - dc[i]]);
					return;
				}

				if (!visited[nr][nc]) {
					if (map[nr][nc] == '.') {
						if (walk_map[nr][nc] < fire_map[nr][nc]) {
							visited[nr][nc] = true;
							q.add(new int[] { nr, nc });
						}
					}
				}
			}
		}
	}

	static void bfs_walk(int r, int c) { // 걷기 최적의 동선 구하는 함수 
		Queue<int[]> q = new ArrayDeque<int[]>();
		q.add(new int[] { r, c });

		while (!q.isEmpty()) {
			int[] temp = q.poll();

			for (int i = 0; i < 4; i++) {
				int nr = temp[0] + dr[i];
				int nc = temp[1] + dc[i];

				if (nr >= 0 && nc >= 0 && nr < R && nc < C) {
					if (map[nr][nc] == '.') {
						if (walk_map[nr][nc] > walk_map[nr - dr[i]][nc - dc[i]] + 1) {

							walk_map[nr][nc] = walk_map[nr - dr[i]][nc - dc[i]] + 1;
							q.add(new int[] { nr, nc });
						}
					}
				}
			}
		}
	}

	static void bfs_fire(int r, int c) { // 불이 번지는 최적의 동선 구하는 함수 
		Queue<int[]> q = new ArrayDeque<int[]>();
		q.add(new int[] { r, c });

		while (!q.isEmpty()) {
			int[] temp = q.poll();

			for (int i = 0; i < 4; i++) {
				int nr = temp[0] + dr[i];
				int nc = temp[1] + dc[i];

				if (nr >= 0 && nc >= 0 && nr < R && nc < C) {
					if (map[nr][nc] == '.') {
						if (fire_map[nr][nc] > fire_map[nr - dr[i]][nc - dc[i]] + 1) {

							fire_map[nr][nc] = fire_map[nr - dr[i]][nc - dc[i]] + 1;
							q.add(new int[] { nr, nc });
						}
					}
				}
			}
		}
	}

}