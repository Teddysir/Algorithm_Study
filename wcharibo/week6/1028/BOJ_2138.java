import java.io.*;
import java.util.*;

public class Main {
	static class Pair{
		int[] str;
		int cnt;
		
		Pair(int[] str, int cnt){
			this.str = str;
			this.cnt = cnt;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		String startStr = br.readLine();
		String goalStr = br.readLine();
		int[] start = new int[N+3];
		int[] goal = new int[N+3];
		start[0] = start[N+1] = goal[0] = goal[N+1] = start[N+2] = goal[N+2] = -1;
		
		for(int i = 0; i < N; i++) {
			start[i+1] = startStr.charAt(i) - 48;
			goal[i+1] = goalStr.charAt(i) - 48;
		}
		
		Queue<Pair> strs = new ArrayDeque<>();
		strs.add(new Pair(new int[] {start[0], start[1], start[2]}, 0));

		for (int i = 1; i <= N; i++) {

			Queue<Pair> next = new ArrayDeque<>();
			
			while (!strs.isEmpty()) {
				Pair prevPair = strs.poll();
				
				if(i-1 == 0 || prevPair.str[0] == goal[i - 1]) {
					next.add(new Pair(new int[] {prevPair.str[1], prevPair.str[2], start[i+2]}, prevPair.cnt));
				}
				
				int[] toggled = Arrays.copyOf(prevPair.str, 3);
				for(int idx = 0; idx < 3; idx++) {
					if((i == 1 && idx == 0) || (i==N && idx==2) ) continue;
					
					toggled[idx] ^= 1;
				}
				if(i-1 == 0 || toggled[0] == goal[i - 1]) {
					next.add(new Pair(new int[] {toggled[1], toggled[2], start[i+2]}, prevPair.cnt + 1));
				}
			}
			strs = next;
		}
		
		int max = Integer.MAX_VALUE;
		
		for(Pair i : strs) {
			if(i.str[0] == goal[N]) {
				max = Math.min(max, i.cnt);
			}
		}
		
		System.out.println(max == Integer.MAX_VALUE ? -1 : max);
	}
}
