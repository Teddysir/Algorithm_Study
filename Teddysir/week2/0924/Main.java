package practice;

import java.io.*;
import java.util.*;

public class Main {

	static int N, ans;
	static int[][] map;
	static int[][] dp;
	static int[] dr = { 1, -1, 0, 0 };
	static int[] dc = { 0, 0, 1, -1 };
	static PriorityQueue<Pos> pq = new PriorityQueue<Pos>();

	static class Pos implements Comparable<Pos> {
		int r, c, value;

		Pos(int r, int c, int value) {
			this.r = r;
			this.c = c;
			this.value = value;
		}

		@Override
		public int compareTo(Pos o) {
			return this.value - o.value;
		}

	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		dp = new int[N][N];
		ans = -1;

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int num = Integer.parseInt(st.nextToken());
				map[i][j] = num;
				pq.add(new Pos(i, j, num));
				dp[i][j] = 1;
			}
		}

		while (!pq.isEmpty()) {
			Pos p = pq.poll();
			solution(p.r, p.c);
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				ans = Math.max(ans, dp[i][j]);
			}
		}
		System.out.println(ans);

	}

	static void solution(int r, int c) {

		for (int i = 0; i < 4; i++) {
			int nr = r + dr[i];
			int nc = c + dc[i];

			if (nr >= 0 && nc >= 0 && nr < N && nc < N) {
				if (map[nr][nc] < map[r][c]) {
					dp[r][c] = Math.max(dp[r][c], dp[nr][nc] + 1);
				}
			}

		}

	}

}
