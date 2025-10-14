/**
 * @Link https://www.acmicpc.net/problem/1238
 * @RunningTime 140ms
 * @Memory 16884KB
 * @Strategy 다익스트라 (Dijkstra)
 * * 문제: N명의 학생이 X번 마을에 모여 파티를 하고 다시 자신의 마을로 돌아갈 때, 가장 오래 걸리는 학생의 소요시간 구하기.
 * * 학생 i의 총 소요시간 = (i번 마을 -> X번 마을 최단 시간) + (X번 마을 -> i번 마을 최단 시간)
 * 1. X -> i 최단 시간 계산: X번 마을을 시작점으로 하여 다른 모든 마을까지의 최단 거리를 다익스트라 알고리즘으로 한 번에 계산한다.
 * 2. i -> X 최단 시간 계산: 모든 단방향 도로(간선)의 방향을 뒤집은 역방향 그래프를 만든다. 그 후 X번 마을을 시작점으로 다익스트라를 실행하면, 이는 원래 그래프에서 모든 i번 마을로부터 X번 마을까지의 최단 거리를 구하는 것과 같다.
 * 3. 두 결과를 합산: 모든 학생(i=1~N)에 대해 (1번 결과 + 2번 결과)를 계산하고, 이 중 최댓값을 찾는다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	// N: 학생(마을) 수, M: 도로(간선) 수, X: 파티 장소
	static int N, M, X;
	// 도달할 수 없는 경우를 나타내기 위한 무한대 값
	static final int INF = Integer.MAX_VALUE;

	// 다익스트라 알고리즘에서 사용할 노드 클래스. 우선순위 큐에서 비용(cost) 기준으로 정렬됨.
	static class Node implements Comparable<Node> {
		int index, cost; // index: 노드 번호, cost: 시작점으로부터의 비용

		public Node(int index, int cost) {
			this.index = index;
			this.cost = cost;
		}

		@Override
		// 비용이 낮은 노드가 더 높은 우선순위를 갖도록 오름차순 정렬
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());

		// adjList: 원래 방향의 인접 리스트 (X -> i 계산용)
		ArrayList<Node>[] adjList = new ArrayList[N + 1];
		// revList: 도로 방향을 뒤집은 인접 리스트 (i -> X 계산용)
		ArrayList<Node>[] revList = new ArrayList[N + 1];

		// dist: X에서 각 마을로 가는 최단 시간 저장 배열
		int[] dist = new int[N + 1];
		// revDist: 각 마을에서 X로 가는 최단 시간 저장 배열
		int[] revDist = new int[N + 1];
		// 모든 최단 거리 배열을 무한대로 초기화
		Arrays.fill(dist, INF);
		Arrays.fill(revDist, INF);

		// 인접 리스트 초기화
		for (int i = 1; i <= N; i++) {
			adjList[i] = new ArrayList<>();
			revList[i] = new ArrayList<>();
		}

		// M개의 도로 정보 입력받아 인접 리스트 구성
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			adjList[start].add(new Node(end, cost)); // 정방향 그래프
			revList[end].add(new Node(start, cost)); // 역방향 그래프
		}

		// 1. X에서 각 마을로 돌아오는 최단 시간 계산 (다익스트라)
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(X, 0)); // 시작점 X를 큐에 추가
		dist[X] = 0; // 시작점의 거리는 0
		
		while (!pq.isEmpty()) {
			Node current = pq.poll(); // 현재 가장 비용이 적은 노드를 꺼냄
			// 이미 더 짧은 경로가 발견되었다면 무시
			if (dist[current.index] < current.cost)
				continue;
			// 현재 노드와 연결된 다른 노드들을 확인
			for (Node next : adjList[current.index]) {
				// 현재 노드를 거쳐 가는 것이 더 저렴하다면
				if (dist[next.index] > current.cost + next.cost) {
					dist[next.index] = current.cost + next.cost; // 거리 갱신
					pq.offer(new Node(next.index, dist[next.index])); // 갱신된 정보를 큐에 추가
				}
			}
		}
		
		// 2. 각 마을에서 X로 가는 최단 시간 계산 (역방향 그래프에서 다익스트라)
		pq.offer(new Node(X, 0)); // 시작점 X를 큐에 추가 (역방향 기준)
		revDist[X] = 0; // 시작점의 거리는 0
		
		while (!pq.isEmpty()) {
			Node current = pq.poll();
			if (revDist[current.index] < current.cost)
				continue;
			// 역방향 그래프를 탐색
			for (Node next : revList[current.index]) {
				if (revDist[next.index] > current.cost + next.cost) {
					revDist[next.index] = current.cost + next.cost;
					pq.offer(new Node(next.index, revDist[next.index]));
				}
			}
		}

		// 3. 왕복 시간이 가장 긴 학생의 시간 찾기
		int answer = 0;
		for (int i = 1; i <= N; i++) {
			// i번 학생의 총 소요시간 = (i -> X) + (X -> i)
			int totalTime = revDist[i] + dist[i];
			// 최댓값 갱신
			answer = Math.max(answer, totalTime);
		}
		System.out.println(answer);
	}
}