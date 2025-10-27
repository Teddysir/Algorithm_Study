import java.io.*;
import java.util.*;

public class Main {
	static class Edge{
		int dest;
		int fare;
		
		Edge(int dest, int fare){
			this.dest = dest;
			this.fare = fare;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int P = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int maxFare = 0;
		
		List<Edge>[] conns = new List[N+1];
		
		for(int i = 1; i <= N; i++) conns[i] = new ArrayList<Edge>();
		
		for(int i = 0; i < P; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			conns[a].add(new Edge(b, c));
			conns[b].add(new Edge(a, c));
			maxFare = Math.max(maxFare, c);
		}
		
		//이분 탐색을 통해 가장 저렴하게 연결한 전체 간선들 중 가장 큰 간선의 값을 찾아보기 
		int start = 0;
		int end = maxFare;
		int answer = -1;
		
		while(start <= end) {
			// 0-1 BFS 통해서 K번 간선을 무시하며 연결할 수 있는 가장 저렴한 경로를 찾으려고 하기 때문에 BFS에 사용할 거리 정보 배열 
			int[] costs = new int[N+1];
			Arrays.fill(costs, Integer.MAX_VALUE);
			
			int mid = (start + end)/2;
			//0-1 BFS는 0은 앞에 넣고 1은 뒤에 넣어서 priorityqueue를 쓸 때 발생하는 정렬에 대한 시간을 줄일 수 있음, 그러기 위해 deque사용 
			ArrayDeque<Integer> q = new ArrayDeque<>();
			q.addFirst(N);
			costs[N] = 0;
			
			while(!q.isEmpty()) {
				//queue의 정렬을 보장하면서 priority queue의 장점은 얻기 위해 0-1 BFS 사용한거니까 똑같이 앞에 값 poll();
				int cur = q.poll();
				
				for(Edge next : conns[cur]) {
					// 내가 찾으려고 하는 값보다 크면 1 작거나 같으면 0의 비용으로 계산 
					int oneZero = next.fare > mid ? 1 : 0;
					
					if(costs[next.dest] > costs[cur] + oneZero) {
						costs[next.dest] = costs[cur] + oneZero;
						
						//1이면 뒤
						if(oneZero == 1) q.addLast(next.dest);
						//0이면 앞
						else {
							q.addFirst(next.dest);
						}
					}
				}
			}
			//1에 도착하는 최소의 비용을 가지는 경로를 찾았는 데, costs[1]의 값이 K보다 크다는 말은 현재의 경로를 선택하기 위해서는 K보다 더 많은 간선을 무료로 제공받아야
			//가능한 경로라는 뜻이므로 불가능한 경로라는 판단. 따라서, 더 적은 간선을 무료로 제공받기 위해 더 큰 값들을 탐색함. 현재 경로는 불가능하므로 answer 업데이트 안함.
			if(costs[1] > K) {
				start = mid + 1;
			}
			//1에 도착하는 최소의 비용을 가지는 경로를 찾았는 데, 더 작은 값도 가능할 수도 있음.(더 저렴하게 인터넷을 연결할 수도 있음) 
			//따라서 더 작은 값들을 탐색함. 현재 경로는 가능한 경로이므로 answer 업데이트 
			else {
				answer = mid;
				end = mid - 1;
			}
			
			//밑의 주석 풀고 실행하면 더 적은 값들이 가능한지 반복하고 있음 -> 최소값을 찾고 있다.
//			System.out.println("-: " + mid);
		}
		
		System.out.println(answer);
	}

}
