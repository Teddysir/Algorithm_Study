// 비효율적인 반복 풀이 404ms
import java.io.*;
import java.util.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		int[] buildTime = new int[N];
		int[] posts = new int[N];
		List<Integer>[] prevs = new ArrayList[N];
		List<Integer>[] prevBuildTime = new ArrayList[N];
		
		for(int i = 0; i < N; i++) {
			prevs[i] = new ArrayList<>();
			prevBuildTime[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			buildTime[i] = Integer.parseInt(st.nextToken());
			
			for(int j = 1; j < st.countTokens(); ) {
				int prev = Integer.parseInt(st.nextToken()) - 1;
				
				prevs[prev].add(i);
				posts[i]++;
			}
		}
		
		while(true) {
			int idx = -1;
			
			for(int i = 0; i < N; i++) {
				if(posts[i] == 0) {
					idx = i;
					posts[i]--;
					break;
				}
			}
			
			if(idx == -1) break;
			
			for(int i : prevs[idx]) {
				if(--posts[i] >= 0) {
					prevBuildTime[i].add(buildTime[idx]);
					if(posts[i] == 0) {
						int max = 0;
						for(int j : prevBuildTime[i]) max = Math.max(max, j);
						buildTime[i] += max;
					}
				}
			}
		}
		
		for(int i = 0; i < N; i++) {
			System.out.println(buildTime[i]);
		}
		
	}
}

////큐를 사용한 풀이 420ms
//import java.io.*;
//import java.util.*;
//
//public class Main {
//	
//	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		
//		int N = Integer.parseInt(br.readLine());
//		int[] buildTime = new int[N];
//		int[] posts = new int[N];
//		List<Integer>[] prevs = new ArrayList[N];
//		List<Integer>[] prevBuildTime = new ArrayList[N];
//		Queue<Integer> q = new ArrayDeque<>();
//		
//		for(int i = 0; i < N; i++) {
//			prevs[i] = new ArrayList<>();
//			prevBuildTime[i] = new ArrayList<>();
//		}
//		
//		for(int i = 0; i < N; i++) {
//			StringTokenizer st = new StringTokenizer(br.readLine());
//			
//			buildTime[i] = Integer.parseInt(st.nextToken());
//			
//			if(st.countTokens() == 1) q.add(i);
//			
//			for(int j = 1; j < st.countTokens(); ) {
//				int prev = Integer.parseInt(st.nextToken()) - 1;
//				
//				prevs[prev].add(i);
//				posts[i]++;
//			}
//		}
//		
//		while(!q.isEmpty()) {
//			int idx = q.poll();
//			
//			for(int i : prevs[idx]) {
//				if(--posts[i] >= 0) {
//					prevBuildTime[i].add(buildTime[idx]);
//					if(posts[i] == 0) {
//						q.add(i);
//						int max = 0;
//						for(int j : prevBuildTime[i]) max = Math.max(max, j);
//						buildTime[i] += max;
//					}
//				}
//			}
//		}
//		
//		for(int i = 0; i < N; i++) {
//			System.out.println(buildTime[i]);
//		}
//		
//	}
//}

////priorityQueue이용한 풀이 496ms 
//import java.io.*;
//import java.util.*;
//
//public class Main {
//
//	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//		int N = Integer.parseInt(br.readLine());
//		int[] buildTime = new int[N];
//		int[] posts = new int[N];
//		List<Integer>[] prevs = new ArrayList[N];
//		List<Integer>[] prevBuildTime = new ArrayList[N];
//		PriorityQueue<Integer> q = new PriorityQueue<>((a,b)->{
//			return buildTime[a] - buildTime[b];
//		});
//
//		for (int i = 0; i < N; i++) {
//			prevs[i] = new ArrayList<>();
//			prevBuildTime[i] = new ArrayList<>();
//		}
//
//		for (int i = 0; i < N; i++) {
//			StringTokenizer st = new StringTokenizer(br.readLine());
//
//			buildTime[i] = Integer.parseInt(st.nextToken());
//
//			if (st.countTokens() == 1)
//				q.add(i);
//
//			for (int j = 1; j < st.countTokens();) {
//				int prev = Integer.parseInt(st.nextToken()) - 1;
//
//				prevs[prev].add(i);
//				posts[i]++;
//			}
//		}
//
//		while (!q.isEmpty()) {
//			int idx = q.poll();
//
//			for (int i : prevs[idx]) {
//				if (--posts[i] == 0) {
//					buildTime[i] += buildTime[idx];
//					q.add(i);
//				}
//			}
//		}
//
//		for (int i = 0; i < N; i++) {
//			System.out.println(buildTime[i]);
//		}
//
//	}
//}

////최대값만 저장하는 풀이 384ms 
//import java.io.*;
//import java.util.*;
//
//public class Main {
//	
//	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		
//		int N = Integer.parseInt(br.readLine());
//		int[] buildTime = new int[N];
//		int[] posts = new int[N];
//		List<Integer>[] prevs = new ArrayList[N];
//		int[] prevBuildTime = new int[N];
//		
//		for(int i = 0; i < N; i++) {
//			prevs[i] = new ArrayList<>();
//		}
//		
//		for(int i = 0; i < N; i++) {
//			StringTokenizer st = new StringTokenizer(br.readLine());
//			
//			buildTime[i] = Integer.parseInt(st.nextToken());
//			
//			for(int j = 1; j < st.countTokens(); ) {
//				int prev = Integer.parseInt(st.nextToken()) - 1;
//				
//				prevs[prev].add(i);
//				posts[i]++;
//			}
//		}
//		
//		while(true) {
//			int idx = -1;
//			
//			for(int i = 0; i < N; i++) {
//				if(posts[i] == 0) {
//					idx = i;
//					posts[i]--;
//					break;
//				}
//			}
//			
//			if(idx == -1) break;
//			
//			for(int i : prevs[idx]) {
//				if(--posts[i] >= 0) {
//					prevBuildTime[i] = Math.max(prevBuildTime[i], buildTime[idx]);
//					if(posts[i] == 0) {
//						buildTime[i] += prevBuildTime[i];
//					}
//				}
//			}
//		}
//		
//		for(int i = 0; i < N; i++) {
//			System.out.println(buildTime[i]);
//		}
//		
//	}
//}

