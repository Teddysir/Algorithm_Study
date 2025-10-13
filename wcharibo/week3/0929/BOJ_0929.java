import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int result = 0;
		int N = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		
		int M = Integer.parseInt(br.readLine());
		
		int[][] send = new int[M][3];
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			send[i] = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
		}
		
		Arrays.sort(send, (a,b)->{
			if(a[1] == b[1]) {
				return a[0] - b[0];
			}
			return a[1] - b[1];
		});
		
		int[] vil = new int[N+1];
		
		for(int[] i : send) {
			int max = 0;
			for(int j = i[0]; j < i[1]; j++) {
				max = Math.max(max, vil[j]);
			}
			
			
			int pos = Math.min(C-max, i[2]);
			
			for(int j = i[0]; j < i[1]; j++) {
				vil[j] += pos;
			}
			
			result += pos;
		}
		
		System.out.println(result);
	}
}

