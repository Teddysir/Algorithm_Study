package forStudy.month04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2098 {
	static int N;
	static int[][] map;
	static int[][] dp;
	static final int INF = 16_000_000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		StringTokenizer st;
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		dp = new int[N][(1<<N)-1];
		for(int i=0; i<N; i++) Arrays.fill(dp[i], -1);
		
		System.out.println(dfs(0, 1));
		
	}

	private static int dfs(int now, int visit) {
		// 모든 도시를 방문함
		if(visit == (1<<N)-1) {
			// now->0으로 가는 경로가 없는 경우, 최솟값이 되지 않도록 큰 더미값을 넣어버림
			if(map[now][0] == 0) return INF;
			return map[now][0];
		}
		
		// 현재 노드가 방문한 적이 있는 곳이면 저장했던 값을 내보냄
		if(dp[now][visit] != -1) return dp[now][visit];
		// 값 초기화
		dp[now][visit] = INF;
		
		for(int i=0; i<N; i++) {
			// (visit & (1<<i)) == 0 -> 해당 목적지를 방문하지 않은 상태일 때
			// map[now][i] != 0 -> 해당 경로가 존재하는 지
			if((visit & (1<<i)) == 0 && map[now][i] != 0) {
				// dfs(i, visit | (1 << i)) -> i도시로 이동해서, 남은 도시들을 모두 방문하고 다시 0번으로 돌아가는 최소 비용
				// map[now][i] -> 현재 도시(now)에서 i도시로 가는 데 드는 비용
				// 경로가 확정이 된 뒤에 돌아오면서 갈 때와, 올 때 값을 모두 구해서 최솟값을 갱신하는 방식
				dp[now][visit] = Math.min(dfs(i, visit | (1 << i)) + map[now][i], dp[now][visit]);
			}
		}
		
		return dp[now][visit];
		
	}
}
