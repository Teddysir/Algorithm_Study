package forStudy.month04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1976 {
	static int N, M;
	static int[][] conn;
	static int[] plan;
	static int[] parent;
	static int count = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		conn = new int[N][N];
		StringTokenizer st;
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				conn[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		plan = new int[M];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<M; i++) {
			plan[i] = Integer.parseInt(st.nextToken()) -1;
		}
		
		parent = new int[N];
		for(int i=0; i<N; i++) parent[i] = i;
			
		for(int i=0; i<N; i++) {
			// Union-find
			for(int j=i+1; j<N; j++) {
				if(conn[i][j] == 1) union(i, j);
			}
		}
		
		boolean result = true;
		
		for(int i=1; i<M; i++) {
			if(find(plan[i-1]) != find(plan[i])) {
				result = false;
				break;
			}
		}
		
		System.out.println(result ? "YES" : "NO");
		
	}

	private static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		
		if(x < y) parent[x] = y;
		else parent[y] = x;
	}

	private static int find(int a) {
		if(parent[a] == a) return a;
		else return parent[a] = find(parent[a]);
	}
}
