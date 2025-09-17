/**
 * @Link https://www.acmicpc.net/problem/1976
 * @RunningTime 172ms
 * @Memory 16864KB
 * @Stratgy 플로이드-워셜 알고리즘
 * - 플로이드-워셜 알고리즘으로 모든 도시 간의 연결 여부를 파악한 뒤, 여행 계획에 속한 도시들이 순서대로 모두 연결되어 있는지 확인합니다.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); // 도시의 수
		int M = Integer.parseInt(br.readLine()); // 여행 계획에 있는 도시의 수
		int[][] dist = new int[N][N]; // 도시 간의 최단 거리를 저장할 배열
		final int INF = 200 * 1000 + 1; // 충분히 큰 값으로 INF 설정

		// 거리 배열 초기화
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], INF); // 모든 경로를 무한대로 초기화
			dist[i][i] = 0;            // 자기 자신으로의 거리는 0
		}

		// 도시 연결 정보(인접 행렬) 입력
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				int input = Integer.parseInt(st.nextToken());
				if (i == j) continue; // 자기 자신으로의 경로는 0이므로 건너뜀
				if (input != 0) dist[i][j] = input; // 직접 연결된 도시는 거리를 1로 설정
			}
		}

		// 플로이드-워셜 알고리즘 수행
		// k: 경유지, i: 출발지, j: 도착지
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// i -> j로 가는 기존 경로보다 i -> k -> j 경로가 더 짧으면 갱신
					dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
				}
			}
		}

		// 여행 계획 입력 (0-based 인덱스로 변환)
		int[] route = new int[M];
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			route[i] = Integer.parseInt(st.nextToken()) - 1;
		}
		
		// 여행 계획이 가능한지 확인
		boolean isOK = true;
		// 계획의 첫 도시부터 마지막 도시까지 순서대로 연결되어 있는지 체크
		for (int i = 1; i < M; i++) {
			// 이전 도시에서 다음 도시로 가는 경로가 없다면(INF) 여행 불가
			if (dist[route[i - 1]][route[i]] == INF) {
				isOK = false;
				break;
			}
		}
		System.out.println(isOK ? "YES" : "NO");
	}
}
