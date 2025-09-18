import java.util.*;
import java.io.*;

public class BOJ_1976 {
	static int[] set;
	
	static void union(int i, int j) {
		int pi = find(i);
		int pj = find(j);
		
		if(pi == pj) return;
		
		set[pj] = pi;
	}
	
	static int find(int i) {
		if(set[i] == i) return set[i];
		
		return set[i] = find(set[i]);
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		
		set = new int[N];
		
		for(int i = 0; i < N; i++) set[i] = i;
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());	
			for(int j = 0; j < N; j++) {
				if(Integer.parseInt(st.nextToken()) == 1) {
					union(i, j);
				}
			}
		}
		
		st = new StringTokenizer(br.readLine());
		
		int flag = set[Integer.parseInt(st.nextToken()) - 1]; 
		
		for(int i = 1; i < M; i++) {
			if(flag != find(Integer.parseInt(st.nextToken()) - 1)) {
				System.out.println("NO");
				return;
			}
		}
		
		System.out.println("YES");
		
	}	
}
