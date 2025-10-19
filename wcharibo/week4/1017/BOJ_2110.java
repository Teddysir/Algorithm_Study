import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] strs = br.readLine().split(" ");
		
		int N = Integer.parseInt(strs[0]);
		int C = Integer.parseInt(strs[1]);
		int[] houses = new int[N];
		
		for(int i = 0; i < N; i++) {
			houses[i] = Integer.parseInt(br.readLine());
		}
		
		Arrays.sort(houses);
		
		int start = 1;
		int end = houses[N-1] - houses[0];
		int maxDist = 0;
		
		while(start <= end) {
			int mid = (start + end)/2;
			
			int prev = houses[0];
			int installed = 1;
			
			for(int i = 1; i < N; i++) {
				if(houses[i] - prev >= mid) {
					installed++;
					prev = houses[i];
				}
			}
			
			if(installed >= C) {
				maxDist = mid;
				start = mid + 1;
			}else if(installed < C) {
				end = mid - 1;
			}	
		}
		
		
		System.out.println(maxDist);

	}
}
