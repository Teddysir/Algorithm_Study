package for_A_Problem;
import java.io.*;
import java.util.*;

public class BOJ_11779 {

	static class Node implements Comparable<Node> {
		int to;
		int weight;

		Node(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.weight, o.weight);
		}
	}

	static int N, M, S, E;
	static int[] dist;
	static List<Node>[] graph;
	static StringTokenizer st;
	static int[] ans;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		dist = new int[N + 1];
		ans = new int[N + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);

		graph = new ArrayList[N + 1];

		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<Node>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			graph[from].add(new Node(to, weight));
		}

		st = new StringTokenizer(br.readLine());

		S = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		djikstra(S);

		int cur = E;
		List<Integer> list = new ArrayList<Integer>();

		while (cur != 0) {
			list.add(cur);
			cur = ans[cur];
		}

		sb.append(dist[E]).append("\n");
		sb.append(list.size()).append("\n");
		for (int i = list.size() - 1; i >= 0; i--) {
			sb.append(list.get(i)).append(" ");
		}
		sb.append("\n");

		System.out.println(sb);

	}

	static void djikstra(int start) {
		dist[start] = 0;
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(start, 0));

		while (!pq.isEmpty()) {
			Node cur = pq.poll();

			if (cur.weight > dist[cur.to])
				continue;

			for (Node nextNode : graph[cur.to]) {
				int newWeight = nextNode.weight + cur.weight;

				if (newWeight < dist[nextNode.to]) {
					dist[nextNode.to] = newWeight;
					pq.add(new Node(nextNode.to, newWeight));
					ans[nextNode.to] = cur.to;
				}
			}

		}
	}

}
