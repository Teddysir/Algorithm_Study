
//최대값만 저장하는 풀이 384ms 
import java.io.*;
import java.util.*;

public class Main {
	static double[][] pos;
	static int[] sets;

	static class Edge implements Comparable<Edge> {
		double fare;
		int start;
		int end;

		public Edge(int start, int end, double fare) {
			this.fare = fare;
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(Edge o) {
			return this.fare < o.fare ? -1 : 1;
		}
	}

	static Double getDist(int s, int e) {
		return (pos[s][0] - pos[e][0]) * (pos[s][0] - pos[e][0]) + (pos[s][1] - pos[e][1]) * (pos[s][1] - pos[e][1]);
	}

	static int find(int i) {
		if (sets[i] == i)
			return sets[i];

		return sets[i] = find(sets[i]);
	}

	static boolean union(int i, int j) {
		int pi = find(i);
		int pj = find(j);

		if (pi == pj)
			return false;

		sets[pj] = pi;
		return true;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		double result = 0;
		int unionCnt = 0;

		pos = new double[N][2];
		sets = new int[N];
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		for (int i = 0; i < N; i++) {
			sets[i] = i;
			String[] temp = br.readLine().split(" ");
			pos[i][0] = Double.parseDouble(temp[0]);
			pos[i][1] = Double.parseDouble(temp[1]);
//			pos[i] = new double[] { Double.parseDouble(temp[0]), Double.parseDouble(temp[1]) };
		}

		for (int i = 0; i < N; i++) {
//			double min = Double.MAX_VALUE;
			for (int j = i + 1; j < N; j++) {
				pq.add(new Edge(i, j, getDist(i, j)));

			}
		}

		while (unionCnt != N - 1 && !pq.isEmpty()) {
			Edge cur = pq.poll();

			if (union(cur.start, cur.end)) {
				result += Math.sqrt(cur.fare);
				unionCnt++;
			}
		}

		System.out.println(result);
	}
}