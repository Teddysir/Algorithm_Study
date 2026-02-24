import java.util.*;
import java.io.*;

public class Main{
	
	static class Pair{
		int x;
		int y;
		boolean isCrashed;
		
		Pair(int x, int y){
			this.x = x;
			this.y = y;
			this.isCrashed = false;
		}
		
		Pair(int x, int y, boolean isCrashed){
			this.x = x;
			this.y = y;
			this.isCrashed = isCrashed;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[][] map = new int[N][M];
		int[][] before = new int[N][M];
		int[][] after = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			String cur = br.readLine();
			
			for(int j = 0; j < M; j++) {
				map[i][j] = cur.charAt(j) - 48;
			}
		}
		
		int[][] dirs = {{0, -1},{0, 1},{1, 0},{-1, 0}};
		
		Queue<Pair> q = new ArrayDeque<>();
		
		q.add(new Pair(0, 0));
		
		before[0][0] = 1;
		after[0][0] = 1;
		
		
		while(!q.isEmpty() && (q.peek().x != N-1 || q.peek().y != M-1)) {
			Pair cur = q.poll();
			
			for(int dir = 0; dir < dirs.length; dir++) {
				int nx = cur.x + dirs[dir][0];
				int ny = cur.y + dirs[dir][1];
				
				
				if(0 <= nx && nx < N && 0 <= ny && ny < M) {
					if(cur.isCrashed && after[nx][ny] == 0 && map[nx][ny] == 0) {
						after[nx][ny] = after[cur.x][cur.y] + 1;
						q.add(new Pair(nx, ny, true));
					}else if(!cur.isCrashed){
						
						if(after[nx][ny] == 0 && map[nx][ny] == 1) {
							
							after[nx][ny] = before[cur.x][cur.y] + 1;
							q.add(new Pair(nx, ny, true));
							
						}else if(before[nx][ny] == 0 && map[nx][ny] == 0) {
							
							before[nx][ny] = before[cur.x][cur.y] + 1;
							q.add(new Pair(nx, ny));
							
						}	
					}
				}	
			}
		}
		
				
		System.out.println(before[N-1][M-1] == 0 ? (after[N-1][M-1] == 0 ? -1 : after[N-1][M-1]) : (after[N-1][M-1] == 0 ? before[N-1][M-1] : Math.min(before[N-1][M-1], after[N-1][M-1])));
		
		
	}
}
