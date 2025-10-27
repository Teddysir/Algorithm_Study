/**
 * @Link https://www.acmicpc.net/problem/1800
 * @RunningTime 220ms
 * @Memory 20140kb
 * @Strategy 파라메트릭 서치 (Parametric Search) + 다익스트라 (Dijkstra)
 * * 'K개의 인터넷 선을 공짜로 연결할 때, 나머지 선 중 가장 비싼 요금을 최소화' 하는 문제.
 * * 이는 '최대 요금을 x원으로 설정했을 때, K개 이하의 공짜 선으로 1번부터 N번까지 연결이 가능한가?' 라는 결정 문제로 변환하여 풀 수 있다.
 * 1. 이분 탐색(Parametric Search)을 통해 가능한 '최대 요금(x)'의 최솟값을 찾는다.
 * - 탐색 범위: left=0, right=1,000,000 (가능한 최대 요금)
 * 2. `check(mid)` 함수: 특정 `mid` 값이 최대 요금으로 가능한지 판별한다.
 * - 이 판별 과정은 다익스트라 알고리즘을 변형하여 해결한다.
 * - 다익스트라의 비용(가중치)을 '실제 케이블 비용'이 아닌, 'mid 값보다 비싼 케이블의 개수'로 설정한다.
 * - 즉, 비용이 `mid` 이하인 케이블은 가중치 0, `mid` 초과인 케이블은 가중치 1로 간주한다.
 * - 다익스트라 탐색 후, `dist[N]`은 1번에서 N번까지 가는 데 필요한 `mid` 초과 케이블의 최소 개수를 의미한다.
 * 3. `dist[N] <= K` 라면, `mid`는 가능한 최대 요금이므로 더 작은 값을 찾기 위해 `right = mid - 1`로 범위를 좁힌다.
 * 4. `dist[N] > K` 라면, `mid`가 너무 낮아 K개의 공짜 선으로 부족하므로, 더 큰 값을 허용하기 위해 `left = mid + 1`로 범위를 넓힌다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	// 다익스트라 알고리즘에 사용할 노드 클래스
	static class Node implements Comparable<Node> {
		int index, cost; // index: 노드 번호, cost: 비용 (여기서는 실제 비용 또는 'mid' 초과 케이블 개수)

		public Node(int index, int cost) {
			super();
			this.index = index;
			this.cost = cost;
		}

		@Override
		// 비용(cost)이 낮은 순서대로 우선순위 큐에서 정렬
		public int compareTo(Main.Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	// N: 컴퓨터 수, P: 케이블 수, K: 공짜 케이블 수
	static int N, P, K;
	// adjList: 인접 리스트 (그래프)
	static ArrayList<Node>[] adjList;
	// dist: 다익스트라 알고리즘에서 사용할 거리(비용) 배열
	static int[] dist;
	// INF: 도달 불가능을 나타내는 무한대 값
	static final int INF = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		adjList = new ArrayList[N + 1];
		dist = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			adjList[i] = new ArrayList<Main.Node>();
		}
		// 케이블 정보를 입력받아 무방향 그래프 구성
		for (int i = 0; i < P; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			adjList[start].add(new Node(end, cost));
			adjList[end].add(new Node(start, cost));
		}
		
		int answer = -1;
		// 이분 탐색(파라메트릭 서치) 범위 설정
		int right = 1_000_000;
		int left = 0;
		
		while (left <= right) {
			int mid = left + (right - left) / 2; // mid: 현재 검사할 '최대 허용 요금'
			
			// mid 요금으로 1->N 연결이 가능한지 확인
			if(check(mid)) {
				// 가능하다면 mid는 답이 될 수 있음. 더 좋은 답(더 낮은 요금)을 찾기 위해 범위를 줄임.
				answer = mid;
				right = mid - 1;
			} else {
				// 불가능하다면 mid 요금이 너무 낮다는 의미. 더 높은 요금을 허용해야 함.
				left = mid + 1;
			}
		}
		System.out.println(answer);
	}

	/**
	 * 최대 허용 요금이 mid일 때, K개 이하의 공짜 케이블로 1번에서 N번까지 연결 가능한지 확인하는 함수
	 * @param mid 현재 검사할 '최대 허용 요금'
	 * @return 연결 가능 여부 (true/false)
	 */
	private static boolean check(int mid) {
		Arrays.fill(dist, INF);
		PriorityQueue<Node> pq = new PriorityQueue<Main.Node>();
		pq.offer(new Node(1, 0));
		dist[1] = 0; // 시작점까지의 비용(mid 초과 케이블 개수)은 0
		
		while (!pq.isEmpty()) {
			Node current = pq.poll();
			
			// 이미 더 좋은 경로가 발견되었다면 무시
			if (dist[current.index] < current.cost) {
				continue;
			}
			
			// 목적지에 도착했다면 탐색 중단 (최단 경로를 찾았으므로)
			if (current.index == N) {
				break;
			}
			
			// 현재 노드와 연결된 다른 노드들을 확인
			for (Node next : adjList[current.index]) {
				// 새로운 비용 계산: 케이블 비용이 mid보다 크면 1, 아니면 0을 더함
				int newCost = current.cost + (next.cost > mid ? 1 : 0);
				
				// 더 적은 수의 '비싼' 케이블로 도달할 수 있다면 정보 갱신
				if (dist[next.index] > newCost) {
					dist[next.index] = newCost;
					pq.offer(new Node(next.index, newCost));
				}
			}
		}
		// N번 컴퓨터까지 가는 데 필요한 'mid 초과 케이블'의 최소 개수가 K개 이하인지 반환
		return dist[N] <= K;
	}
}
