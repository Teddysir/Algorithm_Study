import java.io.*;
import java.util.*;

public class Main {
	static int allDist = 0;
	static int N;
	static HashMap<Integer, Integer>[][] fares;
	static int[][] W;
	
	static class Pair {
		int start;
		int end;
		int history;

		Pair(int start, int end, int history) {
			this.start = start;
			this.end = end;
			this.history = history;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());

		W = new int[N + 1][N + 1];
		fares = new HashMap[N + 1][N + 1];
		List<Pair> pairs = new ArrayList<>();

		for (int i = 1; i <= N; i++) {
			allDist |= 1 << i;
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				W[i][j] = Integer.parseInt(st.nextToken());
				fares[i][j] = new HashMap<>();
			}
			fares[i][i].put(1<<i, 0);
		}
		
		pairs.add(new Pair(1, 1, 1 << 1));
		calculate(pairs);
		
		
		int result = Integer.MAX_VALUE;
		
		for(int i = 1; i <= N; i++) {
			if(fares[i][i].containsKey(allDist)) {
				result = Math.min(fares[i][i].get(allDist), result);
			}
		}
		
		System.out.println(result);
	}

	private static void calculate(List<Pair> pairs) {
		List<Pair> next = new ArrayList<Pair>();
		
		for(Pair pair : pairs) {
			
			if(pair.history == allDist && W[pair.end][pair.start] != 0) {
				if(fares[pair.start][pair.start].containsKey(allDist)) {
					fares[pair.start][pair.start].replace(allDist, Math.min(fares[pair.start][pair.end].get(allDist) + W[pair.end][pair.start], fares[pair.start][pair.start].get(allDist)));
					continue;
				}
				fares[pair.start][pair.start].put(allDist, fares[pair.start][pair.end].get(allDist) + W[pair.end][pair.start]);
				continue;
			}
			
			
			for(int i = 1; i <= N; i++) {
				if((pair.history & (1<<i)) != (1<<i)) {
					if(W[pair.end][i] == 0) continue;
					
					int hist = (pair.history | (1<<i));
					int dist = fares[pair.start][pair.end].get(pair.history) + W[pair.end][i];
					
					if(!fares[pair.start][i].containsKey(hist)) {
						fares[pair.start][i].put(hist, dist);
						next.add(new Pair(pair.start, i, hist));
					}else {
						fares[pair.start][i].replace(hist, Math.min(fares[pair.start][i].get(hist), dist));
					}
				}
			}
		}
		
		pairs.clear();
		if(!next.isEmpty()) calculate(next);
	}

}