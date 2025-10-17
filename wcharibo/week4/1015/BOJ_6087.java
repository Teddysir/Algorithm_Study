import java.io.*;
import java.util.*;

public class Main {
	static class Pair{
		int x;
		int y;
		
		Pair(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int W = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());
		
		int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		int[][] map = new int[H][W];
		Set[][] dirMap = new Set[H][W];
		int[] start = new int[2];
		int[] goal = new int[2];
		Queue<Pair> q = new ArrayDeque<>();
		
		for(int i = 0, idx = 0; i < H; i++) {
			dirMap[i] = new Set[W];
			String str = br.readLine();
			for(int j = 0; j < W; j++) {
				dirMap[i][j] = new HashSet<>();
				if(str.charAt(j) == '*') {
					map[i][j] = -2;
				}else if(str.charAt(j) == 'C') {
					map[i][j] = 0;
					
					if(idx++ == 0) {
						start[0] = i;
						start[1] = j;
					}else {
						goal[0] = i;
						goal[1] = j;
						map[i][j] = -1;
					}
				}else {
					map[i][j] = -1;
				}
			}
		}
		
		for(int k = 0; k < 4; k++) {
			int nx = start[0] + dirs[k][0];
			int ny = start[1] + dirs[k][1];
			
			if(0 <= nx && nx < H && 0 <= ny && ny < W && map[nx][ny] != -2) {
				q.add(new Pair(nx, ny));
				map[nx][ny] = 0;
				dirMap[nx][ny].add(k);
			}
		}
		
		while(!q.isEmpty()) {
			Pair cur = q.poll();
			
			for(int i = 0; i < 4; i++) {
				int nx = cur.x + dirs[i][0];
				int ny = cur.y + dirs[i][1];
				
				if(0 <= nx && nx < H && 0 <= ny && ny < W && map[nx][ny] != -2) {
					if(dirMap[cur.x][cur.y].contains(i) && (map[nx][ny] == -1 || map[nx][ny] >= map[cur.x][cur.y])) {
						if(map[nx][ny] == map[cur.x][cur.y]) {
							dirMap[nx][ny].add(i);
							continue;
						}
						map[nx][ny] = map[cur.x][cur.y];
						dirMap[nx][ny].clear();
						dirMap[nx][ny].add(i);
						q.add(new Pair(nx, ny));
					}else if(map[nx][ny] == -1 || map[nx][ny] >= map[cur.x][cur.y] + 1) {
						if(map[nx][ny] == map[cur.x][cur.y] + 1) {
							dirMap[nx][ny].add(i);
							continue;
						}
						map[nx][ny] = map[cur.x][cur.y] + 1;
						dirMap[nx][ny].clear();
						dirMap[nx][ny].add(i);
						q.add(new Pair(nx, ny));
					}
					
				}
			}
		}
		
		System.out.println(map[goal[0]][goal[1]]);
	}
}
