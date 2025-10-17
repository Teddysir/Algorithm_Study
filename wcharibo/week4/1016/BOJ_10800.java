import java.io.*;
import java.util.*;

public class Main {
	static class Pair{
		int C;
		int S;
		int idx;
		
		Pair(int C, int S, int i){
			this.C = C;
			this.S = S;
			this.idx = i;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());
		
		int[] colors = new int[N + 1];
		Pair[] orders = new Pair[N];
		int[] results = new int[N];
		
//		for(int i = 0; i <= N; i++) colors[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int C = Integer.parseInt(st.nextToken());
			int S = Integer.parseInt(st.nextToken());
			
			orders[i] = new Pair(C, S, i);
		}
		
		Arrays.sort(orders, (a,b)->{
			return a.S - b.S;
		});
		
		int sum = 0;
		
		for(int i = 0; i < orders.length; i++) {
			Pair cur = orders[i];
			int temp = cur.S;
			List<int[]> tempColors = new ArrayList<>();
			results[cur.idx] = sum - colors[cur.C];
			
			while(i < orders.length - 1 && cur.S == orders[i+1].S) {
				results[orders[i+1].idx] = sum - colors[orders[i+1].C];
				
				temp += cur.S;
				tempColors.add(new int[] {orders[i+1].C , orders[i+1].S});
				i++;
			}
			
			sum += temp;
			colors[cur.C] += cur.S;
			
			for(int[] duo: tempColors) {
				colors[duo[0]] += duo[1];
			}
		}
		
		for(int i : results) sb.append(i).append("\n");
		
		System.out.println(sb);
	}
}
