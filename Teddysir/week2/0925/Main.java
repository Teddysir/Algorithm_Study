import java.io.*;
import java.util.*;

public class Main {

	static int N, ans;

	static class Node implements Comparable<Node> {
		int start, end;

		Node(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(Node o) {
			if (this.start == o.start) {
				return this.end - o.end;
			}
			return this.start - o.start;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());
		ans = 1;

		Node[] classes = new Node[N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			classes[i] = new Node(start, end);

		}
		Arrays.sort(classes);

		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		pq.add(classes[0].end); // 첫번째 수업의 끝나는시간을 저장 ( 강의실 하나 개설 )

		for (int i = 1; i < N; i++) {
			Node temp = classes[i];

			if (pq.peek() <= temp.start) {
				pq.poll();
			}
			pq.add(temp.end);

		}

		System.out.println(pq.size());

	}

}