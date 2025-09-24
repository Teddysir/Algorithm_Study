import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1976 {
	static int n;
	static int[][] forest, dist;
	static int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	
	public static int dfs(int i , int j) {
		int cur = forest[i][j];
		int candidates[] = new int[4];
		
		if(dist[i][j] != 0) return dist[i][j];
		
		for(int dir = 0; dir < 4; dir++) {
			int ni = i + dirs[dir][0];
			int nj = j + dirs[dir][1];
//			System.out.println(i + "    " + j);
			if(0 <= ni && ni < n && 0 <= nj && nj < n && forest[ni][nj] > cur) {
				if(dist[ni][nj] != 0) {
					candidates[dir] = dist[ni][nj] + 1;
					continue;
				}
				
				candidates[dir] = dfs(ni, nj);
			}			
		}
		
		int max = Integer.MIN_VALUE;
		for(int cnt = 0; cnt < candidates.length; cnt++) {
			max = Math.max(max, candidates[cnt]);
		}
		
		dist[i][j] = max;
		
		return dist[i][j]+1;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		n = Integer.parseInt(br.readLine());

		forest = new int[n][n];

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				forest[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		dist = new int[n][n];
		
		int result = Integer.MIN_VALUE;
		
		for(int i = 0; i < n; i++) {
OAOAOA			for(int j = 0; j < n; j++) {
OAOAOA				dfs(i, j);
OAOAOA				
				result = Math.max(result, dist[i][j]);
			}
OAOAOA		}
		
		System.out.println(result+1);
OAOAOA	}
}

