import java.io.*;
import java.util.*;

public class BOJ_4179 {
	//bfs에 담을 객체 생성 fire는 불인지 판단, dist는 bfs수행시에 불은 map에서 가지고 있는 값이 무엇이든 벽만 아니라면 확장되기 때문에 필요한 거리 값이 소실되는 경우가 발생해서 지훈이 객체의 경우에는 dist값을 저장해줌 
	static class MovingObject {
		boolean fire;
		int r;
		int c;
		int dist;
		// 불 
		MovingObject(boolean fire, int x, int y) {
			this.fire = fire;
			this.r = x;
			this.c = y;
		}
		// 지훈이 
		MovingObject(boolean fire, int x, int y, int dist) {
			this.fire = fire;
			this.r = x;
			this.c = y;
			this.dist = dist;
		}
		
		Pair getPair() {
			return new Pair(this.r, this.c);
		}
	}
	
	//행과 열의 index를 저장하기 위한 클래
	static class Pair{
		int r;
		int c;
		
		Pair(int x, int y){
			this.r = x;
			this.c = y;
		}
		
		int x() {
			return this.r;
		}
		
		int y() {
			return this.c;
		}
		//아래의 코드는 set에서 contains연산 수행시에 new Pair(x,y)를 인자값으로 주면 다른 hashCode를 가지는 별도의 instance이기 때문에 해당 (x,y)가 출구에 해당하는 위치여도 다르다고 판단하기 때문에
		//그 문제를 해결하기 위한 코드 
		@Override
	    public boolean equals(Object obj) {
			Pair other = (Pair) obj;
	        return this.r == other.r && this.c == other.c;
	    }
		
		@Override
		public int hashCode() {
			return Objects.hash(r,c);
		}
	}


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		
		
		//벽의 유무와 불의 유무로 다음 칸이 이동할 수 있는지 확인하기 위한 2차원 배
		int[][] map = new int[R][C];
		//4방 탐색 
		int dirs[][] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		// 출구를 저장할 set, 검색이 잦을 것 같아서 hashSet 사
		Set<Pair> exits = new HashSet<>();
		// BFS수행할 q
		Queue<MovingObject> q = new ArrayDeque<>();
		// 입력 처리 시에 저장할 시작
		MovingObject start = null;
		
		
		// 입력받으면서 필요한 정보 저장
		for (int i = 0; i < R; i++) {
			String str = br.readLine();
			for (int j = 0; j < C; j++) {
				// 벽이면 -1
				if (str.charAt(j) == '#') {
					map[i][j] = -1;
				//불이면 큐에 미리 추가하고 -1
				} else if (str.charAt(j) == 'F') {
					q.add(new MovingObject(true, i, j));
					map[i][j] = -1;
				//시작점은 큐에서 불의 탐색 다음에 탐색하도록 불의 시작점을 모두 큐에 넣은 뒤에 삽입하기 위해 저장 
				} else if (str.charAt(j) == 'J') {
					start = new MovingObject(false, i, j, 0);
					//시작점이 출구와 인접하게 주어지는 경우도 있
					if(i==0 || j ==0 || i == R-1 || j == C-1) {
						exits.add(new Pair(i,j));
					}
				// map의 가장 자리이면 출구로 추
				} else if(i==0 || j ==0 || i == R-1 || j == C-1) {
					exits.add(new Pair(i,j));
				}
			}
		}
		//시작점 q에 추가 
		q.add(start);

		while (!q.isEmpty()) {
			MovingObject cur = q.poll();
			
			//지훈이가 출구에 도착했다면 
			if(!cur.fire) {
				if(exits.contains(new Pair(cur.r, cur.c))){
					System.out.println(cur.dist +1);
					return;
				}
			}
			
			for(int i = 0; i < 4; i++) {
				int nr = cur.r + dirs[i][0];
				int nc = cur.c + dirs[i][1];
				
				if(0 <= nr && nr < R && 0 <= nc && nc < C) {
					//불이라면 벽이 아닌 모든 경우에 map을 -1 로 변경하고 q에 추가  
					if(cur.fire && map[nr][nc] >= 0) {
						q.add(new MovingObject( cur.fire, nr, nc));
						map[nr][nc] = -1;
					//지훈이라면 map이 0인 경우에만 q에 추가 
					}else if(!cur.fire && map[nr][nc] == 0) {
						map[nr][nc] = cur.dist + 1;
						q.add(new MovingObject(cur.fire, nr, nc , map[nr][nc]));
					}
				}
			}
			
		}
		// 지훈이가 출구에 도착하는 경우가 없다면 
		System.out.println("IMPOSSIBLE");
	}
}
