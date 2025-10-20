import java.io.*;
import java.util.*;

public class Main {

	static int INF = 1_000_000_000;
	static int N;
	static int[][] W;
	static int[][] dp; // 1차원은 출발점, 2차원은 방문 체크

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());

		W = new int[N][N];
		dp = new int[N][(1 << N)]; // 배열 만들고,

		for (int i = 0; i < N; i++) {
			Arrays.fill(dp[i], -1); // 방문 안했기에 -1로 초기화,
		}

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				W[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(dfs(0, 1));

	}

	static int dfs(int now, int visited) {

		if (visited == (1 << N) - 1) { // 만약 모두 방문했다면 ,
			if (W[now][0] == 0) { // 근데 만약 돌아갈 수 없다면,
				return INF; // 무한,
			}
			return W[now][0]; // 아니면 최종값 반환
		}

		if (dp[now][visited] != -1) { // 만약 방문 했던곳이라면,
			return dp[now][visited]; // 반환
		}

		dp[now][visited] = INF; // 일단 최대값으로 갱신해주고,

		for (int i = 0; i < N; i++) { // 재귀를 타면서 Top-down 방식으로 질의,
			if ((visited & (1 << i)) == 0 && W[now][i] != 0) { // 방문하지 않았고, 지나갈 수 있다면, 
				dp[now][visited] = Math.min(dp[now][visited], dfs(i, visited | (1 << i)) + W[now][i]);
			}
		}

		return dp[now][visited];
	}

}
