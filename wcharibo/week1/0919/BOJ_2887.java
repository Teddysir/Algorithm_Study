import java.io.*;
import java.util.*;

public class BOJ_2887 {
	// 유니온파인드에 사용할 배열 
	static int[] sets;
	
	//행성 인덱스와 좌표를 저장할 클래스 
	static class Planet implements Comparable<Planet> {
		int l;
		int idx;

		Planet(int l, int idx) {
			this.l = l;
			this.idx = idx;
		}

		@Override
		public int compareTo(Main.Planet o) {
			return this.l - o.l;
		}
	}

	//간선 클래스 
	static class Edge implements Comparable<Edge> {
		int start;
		int end;
		int fare;

		Edge(int start, int end, int fare) {
			this.start = start;
			this.end = end;
			this.fare = fare;
		}

		@Override
		public int compareTo(Main.Edge o) {
			return this.fare - o.fare;
		}

	}
	
	//union find
	static int find(int a) {
		if (sets[a] == a)
			return a;
		return sets[a] = find(sets[a]);
	}

	static boolean union(int a, int b) {
		int pa = find(a);
		int pb = find(b);

		if (pa == pb)
			return false;

		sets[pb] = pa;
		return true;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		int result = 0;

		Planet[][] galaxy = new Planet[3][N];
		sets = new int[N];
		
		for (int i = 0; i < N; i++) {
			//유니온파인드 배열 초기화 
			sets[i] = i;
			st = new StringTokenizer(br.readLine());
			//행성들의 3차원 좌표들을 각각 저장하기 
			for(int j = 0; j < 3; j++) {
				galaxy[j][i] = new Planet(Integer.parseInt(st.nextToken()), i);
			}
		}
		
		//인접한 행성들만 보기위해 정렬 
		for(int i = 0; i < 3; i++) {
			Arrays.sort(galaxy[i]);
		}

		PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
		
		//인접한 행성들간의 거리만 pq에 삽입 
		for (int i = 1; i < N; i++) {
			for(int j = 0; j < 3; j++) {
				pq.add(new Edge(galaxy[j][i-1].idx, galaxy[j][i].idx, galaxy[j][i].l - galaxy[j][i-1].l));
			}
		}
		
		//크루스칼 
		while(!pq.isEmpty()) {
			Edge cur = pq.poll();
			
			if(union(cur.start, cur.end)) {
				result+=cur.fare;
			}
		}
		

		System.out.println(result);
	}
}
