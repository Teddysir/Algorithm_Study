import java.io.*;
import java.util.*;

public class Main {

	static int N, M;
	static int[] visit;

	static int[][] map;

	static int[] parent;

	static Set<Integer> set = new HashSet<Integer>();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		map = new int[N + 1][N + 1];
		parent = new int[N + 1];
		visit = new int[M + 1];

		for (int i = 1; i <= N; i++) {
			parent[i] = i;
		}

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				int num = Integer.parseInt(st.nextToken());
				if (num == 1) {
					map[i][j] = map[j][i] = 1;
				} else {
					map[i][j] = map[j][i] = 0;
				}
			}
		}

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= M; i++) {
			visit[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (map[i][j] == 1) {
					union(i, j);
				}
			}
		}

		for (int i = 1; i <= M; i++) {
			int num = find(visit[i]);
			set.add(num);
		}

		if (set.size() == 1) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}

	}

	static void union(int x, int y) {
		int nx = find(x);
		int ny = find(y);

		if (nx == ny) {
			return;
		} else {
			if (nx > ny) {
				parent[nx] = ny;
			} else {
				parent[ny] = nx;
			}
		}

	}

	static int find(int x) {
		if (parent[x] == x) {
			return x;
		}
		return find(parent[x]);
	}

}