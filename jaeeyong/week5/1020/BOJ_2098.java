/**
 * @Link https://www.acmicpc.net/problem/2098
 * @RunningTime 132ms
 * @Memory 17220KB
 * @Strategy 동적 프로그래밍 (Dynamic Programming) + 비트마스킹 (Bitmasking)
 * * 모든 도시를 한 번씩만 방문하고 시작 도시로 돌아오는 최소 비용을 찾는 문제.
 * * N이 최대 16이므로, 모든 경로를 탐색하는 순열(O(N!))은 시간 초과.
 * * DP를 사용하여 중복 계산을 줄임.
 * * DP 상태 정의: dp[i][mask] = 'mask'에 해당하는 도시들을 방문했고, 현재 'i'번 도시에 있을 때,
 * 아직 방문하지 않은 나머지 도시들을 모두 방문한 후 시작 도시로 돌아가는 데 드는 최소 비용.
 * * 비트마스킹을 사용하여 방문한 도시들의 집합을 정수(mask)로 표현.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	// N: 도시의 수, answer: 최종 정답
	static int N, answer;
	// W: 도시 간의 이동 비용을 저장하는 인접 행렬, dp: 메모이제이션을 위한 DP 테이블
	static int[][] W, dp;
	// INF: 경로가 없을 때나 초기화를 위한 충분히 큰 값 (최대 비용: 16 * 1,000,000)
	static final int INF = 16 * 1_000_000 + 1;
	// START_CITY: 출발 도시의 인덱스 (0번으로 고정)
	static final int START_CITY = 0;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		W = new int[N][N];
		// DP 테이블 크기: [현재 도시][방문한 도시들의 집합]
		dp = new int[N][1 << N];

		// 비용 행렬(W) 입력 받기
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				W[i][j] = Integer.parseInt(st.nextToken());
			}
			// DP 테이블을 -1로 초기화하여 아직 계산되지 않은 상태임을 표시
			Arrays.fill(dp[i], -1);
		}

		// TSP 함수 호출. 시작 도시(0)에서 시작하며, 시작 도시를 방문했음을 마스크(1 << 0)에 표시.
		System.out.println(answer = TSP(START_CITY, 1 << START_CITY));
	}

	/**
	 * TSP 문제를 해결하기 위한 재귀 함수 (Top-down DP)
	 * @param currentCity 현재 위치한 도시의 인덱스
	 * @param visitedMask 현재까지 방문한 도시들의 집합을 나타내는 비트마스크
	 * @return 남은 도시들을 모두 방문하고 시작 도시로 돌아가는 최소 비용
	 */
	private static int TSP(int currentCity, int visitedMask) {

		// 기저 조건: 모든 도시를 방문했다면
		if (visitedMask == (1 << N) - 1) {
			// 현재 도시에서 시작 도시로 돌아가는 경로가 있는지 확인
			// 경로가 없으면(0) INF 반환, 있으면 해당 비용 반환
			return W[currentCity][START_CITY] == 0 ? INF : W[currentCity][START_CITY];
		}

		// 메모이제이션: 이미 계산된 상태라면 저장된 값을 즉시 반환
		if (dp[currentCity][visitedMask] != -1) {
			return dp[currentCity][visitedMask];
		}

		// 현재 상태의 최소 비용을 INF로 초기화
		dp[currentCity][visitedMask] = INF;
		
		// 다음으로 방문할 도시(nextCity)를 탐색
		for (int nextCity = 0; nextCity < N; nextCity++) {
			// 조건 1: 아직 방문하지 않은 도시인가? (마스크에 해당 비트가 꺼져 있는지 확인)
			// 조건 2: 현재 도시에서 다음 도시로 가는 길이 있는가?
			if ((visitedMask & (1 << nextCity)) == 0 && W[currentCity][nextCity] != 0) {
				// 재귀 호출을 통해 최소 비용 갱신
				// dp[i][mask] = min(dp[i][mask], W[i][j] + TSP(j, mask | (1<<j)))
				dp[currentCity][visitedMask] = Math.min(dp[currentCity][visitedMask],
						W[currentCity][nextCity] + TSP(nextCity, visitedMask | (1 << nextCity)));
			}
		}

		// 계산된 최소 비용을 반환
		return dp[currentCity][visitedMask];
	}
}
