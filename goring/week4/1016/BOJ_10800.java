package forStudy.month04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ_10800 {
	public static class Node implements Comparable<Node> {
		int index;
		int color;
		int size;
		
		public Node(int index, int color, int size) {
			this.index = index;
			this.color = color;
			this.size = size;
		}
		
		public int compareTo(Node o) {
			return Integer.compare(size, o.size);
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		ArrayList<Node> ball = new ArrayList<>();
		
		StringTokenizer st;
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			ball.add(new Node(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		
		Collections.sort(ball);
		
		int[] answer = new int[N];
		int[] sum = new int[N+1];
		
		
		int sumV = 0;
		int index = 0;
		for(int i=0; i<N; i++) {
			// 현재 공 하나 빼오기
			Node now = ball.get(i);
			
			while(ball.get(index).size < now.size) {
				// 현재 공보다 작은 공의 누적 합 구하기
				sumV += ball.get(index).size;
				// 공의 색 별로 누적합 구하기
				sum[ball.get(index).color] += ball.get(index).size;
				index++;
			}
			
			answer[now.index] = sumV - sum[now.color];
			
		}
		
		for(int i=0; i<N; i++) {
			System.out.println(answer[i]);
		}
		
	}
}
