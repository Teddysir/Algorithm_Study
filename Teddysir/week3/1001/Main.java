import java.io.*;
import java.util.*;

public class Main {

	static int N;
	static List<Edge>[] adjList;
	static boolean[] visited;
	static double ans;

	static double[][] posArr;

	static class Edge implements Comparable<Edge> {
		int from;
		double weight;

		Edge(int from, double weight) {
			this.from = from;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) {
			// TODO Auto-generated method stub
			return (int) (this.weight - o.weight);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());
		visited = new boolean[N + 1];

		adjList = new ArrayList[N + 1];
		posArr = new double[N+1][2];
		for (int i = 1; i <= N; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());

			posArr[i][0] = x;
			posArr[i][1] = y;
		}

		for (int i = 1; i <= N; i++) {
			double x = posArr[i][0];
			double y = posArr[i][1];

			for (int j = i + 1; j <= N; j++) {
				double dist = uclid(x, y, posArr[j][0], posArr[j][1]);
				adjList[i].add(new Edge(j, dist));
				adjList[j].add(new Edge(i, dist));
			}

		}
		
		prim(1);
		System.out.printf("%.2f", ans);
	}

	static void prim(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
		pq.add(new Edge(start, 0));

		while (!pq.isEmpty()) {
			Edge temp = pq.poll();

			int v = temp.from;
			if (visited[v]) {
				continue;
			}
			ans += temp.weight;
			visited[v] = true;
			for (Edge e : adjList[v]) {
				if (!visited[e.from]) {
					pq.add(new Edge(e.from, e.weight));
				}
			}
		}
	}

	static double uclid(double x1, double y1, double x2, double y2) {
		double nx = Math.abs(x2 - x1);
		double ny = Math.abs(y2 - y1);
		return Math.sqrt(Math.pow(nx, 2) + Math.pow(ny, 2));
	}

}