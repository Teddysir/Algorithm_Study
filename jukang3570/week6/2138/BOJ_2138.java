package 전구와스위치;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2138 {
	static int n;
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		int[] target = new int[n];
		String line = br.readLine();
		String line_2 = br.readLine();
		int[] on = new int[n+1];
		int[] off = new int[n+1];
		int ans = Integer.MAX_VALUE;
		
		for(int i=0; i<n; i++) {
			int a = line.charAt(i) - '0';
			on[i] = a;
			off[i] = a;
			target[i] = line_2.charAt(i) - '0';
		}
		
		for(int i=0; i<2; i++) {
			on[i] ^= 1;
		}
		
		int cnt_1 = 1; //on 의 카운팅.
		int cnt_2 = 0; //off 의 카운팅.
		
		for(int i=1; i<n ;i++) {
			if(on[i-1] != target[i-1]) {
				on[i-1] ^= 1;
				on[i] ^= 1;
				on[i+1] ^= 1;
				cnt_1++;
			}
			if(off[i-1] != target[i-1]) {
				off[i-1] ^=1;
				off[i] ^=1;
				off[i+1] ^=1;
				cnt_2++;
			}
			
		}
		
		if(check(on ,target)) {
			ans = Math.min(ans, cnt_1);
		}
		if(check(off, target)) {
			ans = Math.min(ans,  cnt_2);
		}
		if(ans == Integer.MAX_VALUE) ans = -1;
		System.out.println(ans);
		
		
		

	}

	private static boolean check(int[] arr, int[] target) {
		// TODO Auto-generated method stub
		for(int i=0; i<n; i++) {
			if(arr[i] != target[i]) return false;
		}
		return true;
	}

}
