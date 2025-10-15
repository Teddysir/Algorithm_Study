package forStudy.month04;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class BOJ_7569 {
	static int[] dx = {-1, 1, 0, 0}; //상하좌우
	static int[] dy = {0, 0, -1, 1};
	static int N,M;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());
		int answer = 0;
		
		for(int t=0; t<H; t++) {
			boolean[][] visited = new boolean[N][M];
			Deque<Point> list = new ArrayDeque<>();
			int count = 0;
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<M; j++) {
					if(st.nextToken().equals("0")) {
						visited[i][j] = true;
					}
					else if(st.nextToken().equals("1")) {
						list.addLast(new Point(i, j));
					}
				}
			}
			list.addLast(new Point(-1, -1));
			
			
			Point now;
			while(!list.isEmpty()) {
				now = list.pollFirst();
				
				if(now.x == -1) {
					if(list.isEmpty()) break;
					
					count++;
					list.addLast(new Point(-1, -1));
				}
				
				for(int a=0; a<4; a++) {
					if(inArray(now.x+dx[a], now.y+dy[a]) && visited[now.x+dx[a]][now.y+dy[a]]) {
						visited[now.x+dx[a]][now.y+dy[a]] = false;
						
					}
				}
			}
		}
	}
	
	public static boolean inArray(int x, int y) {
		if(x>=0 && x<N && y>=0 && y<M) return true;
		return false;
	}
}
