import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st  = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int ans = 0;
		int[] arr = new int[n];
		for(int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(arr);
		int start = 1;
		int end = arr[n-1] - arr[0];
		while(start <= end) {
			int mid = (start + end ) / 2;
			int cnt = 1;
			int pos =0;
			for(int i=0; i<n; i++) {
				if(mid <= arr[i] - arr[pos]) {
					cnt++;
					pos = i;
				}
					
			}
			if(cnt >= c)  {
				ans = Math.max(mid, ans);
				start = mid + 1;
			}
			else end = mid - 1;
		}
		System.out.println(ans);
	}
	

}
