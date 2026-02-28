package for_A_Problem;
import java.io.*;
import java.util.*;

public class BOJ_1261 {

	static class Node implements Comparable<Node> {
		int r;
		int c;
		int weight;

		Node(int r, int c, int weight) {
			this.r = r;
			this.c = c;
			this.weight = weight;
		}

		@Override
		public int compareTo(Node o) {
			return this.weight - o.weight;
		}
	}

	static int R, C;
	static StringTokenizer st;
	static int[][] map;
	static int[][] dist;
	static int[] dr = { 1, -1, 0, 0 };
	static int[] dc = { 0, 0, 1, -1 };

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());

		C = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		map = new int[R + 1][C + 1];
		dist = new int[R + 1][C + 1];

		for (int i = 1; i <= R; i++) {
			String input = br.readLine();
			for (int j = 1; j <= C; j++) {
				char temp = input.charAt(j - 1);
				map[i][j] = temp - 48;
				dist[i][j] = Integer.MAX_VALUE;
			}
		}
		djikstra();

		System.out.println(dist[R][C]);

	}

	static void djikstra() {
		PriorityQueue<Node> pq = new PriorityQueue<BOJ_1261.Node>();
		pq.add(new Node(1, 1, 0));
		dist[1][1] = 0;

		while (!pq.isEmpty()) {

			Node cur = pq.poll();

			for (int i = 0; i < 4; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];

				if (nr >= 1 && nc >= 1 && nr <= R && nc <= C) {

					if (cur.weight > dist[nr][nc])
						continue;

					int newWeight;
					if (map[nr][nc] == 1) {
						newWeight = dist[cur.r][cur.c] + 1;
					} else {
						newWeight = dist[cur.r][cur.c];
					}

					if (newWeight < dist[nr][nc]) {
						dist[nr][nc] = newWeight;
						pq.add(new Node(nr, nc, newWeight));
					}

				}
			}
		}
	}

}
