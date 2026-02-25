import java.io.*;
import java.util.*;

// 예측풀이 
// 1 -> N 까지 갈때, v1, v2를 지나는 최단경로를 구하라, 
// 1  > v1 / v1 -> v2 / v2 -> N 까지 가는 경로의 합을 구하면 되나?
// dist 배열을 3개를 만들고 시작점은 1, v1, v2의 시작점을 두고 합을 구한다?
// ElogN 이 시간 복잡도이니까 시간은 3ElogN -> ElogN과 같다고 보면된다. -> 3 X 200,000log800 -> 시간은 안터질거라고 예상 

// 수정  ->양방향, 해줘야함, 그리고다익스트라는 3개 메서드 만들 필요 없이 하나에서 재활용 해서 쓰면 됨, 
public class Main {

	static class Node implements Comparable<Node> {
		int next;
		int weight;

		Node(int next, int weight) {
			this.next = next;
			this.weight = weight;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.weight, o.weight);
		}
	}

	static final int INF = 1_000_000_000;
	static StringTokenizer st;
	static int N, E, v1, v2;
	static List<Node>[] graph;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		graph = new ArrayList[N + 1];

		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<Main.Node>();
		}

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			graph[from].add(new Node(to, weight));
			graph[to].add(new Node(from, weight));
		}
		st = new StringTokenizer(br.readLine());
		v1 = Integer.parseInt(st.nextToken()); // 지나야하는 정점 1
		v2 = Integer.parseInt(st.nextToken()); // 지나야하는 정점 2

		int[] dist_1 = djikstra(1);
		int[] dist_2 = djikstra(v1);
		int[] dist_3 = djikstra(v2);

		long ans_1 = (long)dist_1[v1] + (long)dist_2[v2] + (long)dist_3[N]; // -> long = int + int + int하면 int끼리 계산하고 long을 만들어서 오버플로우가 난다음 저장함 ;
		long ans_2 = (long)dist_1[v2] + (long)dist_2[N] + (long)dist_3[v1];

		if (ans_1 > INF || ans_2 > INF) {
			System.out.println(-1);
		} else {
			if (ans_1 > ans_2) {
				System.out.println(ans_2);
			} else {
				System.out.println(ans_1);
			}
		}
	}

	static int[] djikstra(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		int[] dist = new int[N + 1];
		Arrays.fill(dist, INF);
		pq.add(new Node(start, 0));
		dist[start] = 0;

		while (!pq.isEmpty()) {
			Node cur = pq.poll();

			if (cur.weight > dist[cur.next])
				continue;

			for (Node next : graph[cur.next]) {
				int newWeight = cur.weight + next.weight;

				if (newWeight < dist[next.next]) {
					dist[next.next] = newWeight;
					pq.add(new Node(next.next, newWeight));
				}
			}
		}
		return dist;
	}

}
