import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		int[] start = new int[n];
		int[] end = new int[n];
		
		for(int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			start[i] = Integer.parseInt(st.nextToken());
			end[i] = Integer.parseInt(st.nextToken());
		}
	
		int room = 0;
		int startIdx = 0;
		int endIdx = 0;
		int result = 0;
		Arrays.sort(start);
		Arrays.sort(end);
		
		while(startIdx != n) {
			if(start[startIdx] < end[endIdx]) {
				startIdx++;
				room++;
			}else if(start[startIdx] == end[endIdx]) {
				startIdx++;
				endIdx++;
			}else {
				endIdx++;
				room--;
			}
			
			result = Math.max(result, room);
		}
// priorityQueue 풀이		
//		PriorityQueue<Integer> start = new PriorityQueue<>();
//		PriorityQueue<Integer> end = new PriorityQueue<>();
//		for(int i = 0; i < n; i++) {
//			StringTokenizer st = new StringTokenizer(br.readLine());
//			start.add(Integer.parseInt(st.nextToken()));
//			end.add(Integer.parseInt(st.nextToken()));
//		}
//		while(!start.isEmpty()) {
//			if(start.peek() < end.peek()) {
//				start.poll();
//				room++;
//			}else if(start.peek() == end.peek()) {
//				start.poll();
//				end.poll();
//			}else{
//				end.poll();
//				room--;
//			}
//			result = Math.max(result, room);
//		}
		
		System.out.println(result);
	}
}

