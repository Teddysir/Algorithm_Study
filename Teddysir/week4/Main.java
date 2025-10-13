import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	static int N, M, X;
	static List<Node>[] graph;
	static List<Node>[] reverseGraph;
	static int[] target_to_all;
	static int[] all_to_target;// 모든 좌표에서 파티장소까지의 거리

	static class Node implements Comparable<Node> {
		int to, weight;

		Node(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.weight - o.weight;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // 마을 수
		M = Integer.parseInt(st.nextToken()); // 길 입력받는 개
		X = Integer.parseInt(st.nextToken()); // 목표지점

		graph = new ArrayList[N + 1]; // 마을간의 거리 저장 그래프
		reverseGraph = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<Node>();
			reverseGraph[i] = new ArrayList<Node>();
		}

		target_to_all = new int[N + 1]; // 1~N개의 마을까지
		Arrays.fill(target_to_all, Integer.MAX_VALUE); // 초기화를 해준다.

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			graph[start].add(new Node(end, weight));

		}

		djikstra(X); // 시작점부터 모든 노드까지의 거리

	}

	static void djikstra(int start) {

		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		target_to_all[start] = 0;

		pq.add(new Node(start, 0));

		while (!pq.isEmpty()) {

			Node temp = pq.poll();

			int curTo = temp.to;
			int curWeight = temp.weight;

			if (curWeight > target_to_all[curTo]) {
				continue;
			}

			for (Node next : graph[curTo]) {
				int weight = target_to_all[curTo] + next.weight; // 현재 가중치랑 다음 가중치

				if (weight < target_to_all[next.to]) {
					target_to_all[next.to] = weight;
					pq.add(new Node(next.to, weight));
				}
			}

		}

		System.out.println(Arrays.toString(target_to_all));

	}

}
