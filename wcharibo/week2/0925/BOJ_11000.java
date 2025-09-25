import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_11000 {	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> start = new PriorityQueue<>();
		PriorityQueue<Integer> end = new PriorityQueue<>();
		
		for(int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			start.add(Integer.parseInt(st.nextToken()));
			end.add(Integer.parseInt(st.nextToken()));
		}
	
		int room = 0;
		int result = 0;
		
		while(!start.isEmpty()) {
			if(start.peek() < end.peek()) {
				start.poll();
				room++;
			}else if(start.peek() == end.peek()) {
				start.poll();
				end.poll();
			}else{
				end.poll();
				room--;
			}
			result = Math.max(result, room);
		}
		
		System.out.println(result);
	}
}

