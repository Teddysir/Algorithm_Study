import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	static int n, ans;
	
	public static void main(String[] args)throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ans = Integer.MAX_VALUE;
		n = Integer.parseInt(br.readLine());
		String st = br.readLine();
		String st2 = br.readLine();
		
		permutation(1, st, st2, Integer.MAX_VALUE);
		if(ans == Integer.MAX_VALUE) ans = -1;
		System.out.println(ans);
		

	}

	private static void permutation(int depth, String st, String st2, int curr) {
		if(depth == n+1 ) return;

		for(int i=0; i<n; i++) {
			if(curr == i) continue;
			char[] val = st.toCharArray();
			String result;
			if(i == 0 ) {
				val[i] ^= 1;
				val[i+1] ^= 1;
			}
			else if(i == n-1) {
				val[i] ^= 1;
				val[i-1] ^= 1;
			}
			else {
				for(int j = -1; j < 2 ; j ++) {
					val[i+ j] ^= 1;
				}
			}
			result = new String(val);
			if(result.equals(st2)) {
				ans = Math.min(ans,  depth);
				return;
			}
			else if(ans != Integer.MAX_VALUE && depth >= ans) return;
			else {
				permutation(depth+1, result, st2, i);
			}
			
		}
		
	}
}
