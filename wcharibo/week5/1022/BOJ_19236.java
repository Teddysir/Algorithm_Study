import java.io.*;
import java.util.*;

public class Main {
	static int[][] dirs = { { 0, 0 }, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 },
			{ -1, 1 } };
	static int maxAte = 0;
	static final int N = 4;
	static final int n = N*N;
	
	//물고리
	static class Fish {
		int fishNum;
		int dir;
		//먹혔나?
		boolean ate = false;

		Fish(int fishNum, int dir) {
			this.fishNum = fishNum;
			this.dir = dir;
		}

		Fish(int fishNum, int dir, boolean ate) {
			this.fishNum = fishNum;
			this.dir = dir;
			this.ate = ate;
		}
	}
	
	//상어
	static class Jaws {
		//물고기 배열
		Fish[][] curFishes;
		//몇 개 먹음? 
		int ate;
		int row;
		int col;
		int dir = 0;
		//순서를 저장하는 배열
		int[][] order;

		Jaws(int ate, Fish[][] curFishes, int row, int col, int[][] order) {
			this.ate = ate;
			this.curFishes = curFishes;
			this.row = row;
			this.col = col;
			this.order = order;
		}

		Jaws(int ate, Fish[][] curFishes, int row, int col, int dir, int[][] order) {
			this.ate = ate;
			this.curFishes = curFishes;
			this.row = row;
			this.col = col;
			this.dir = dir;
			this.order = order;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Fish[][] fishes = new Fish[N][N];
		int[][] order = new int[n + 1][2];
		
		//입력
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				int fishNum = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());
				fishes[i][j] = new Fish(fishNum, dir);
				order[fishNum][0] = i;
				order[fishNum][1] = j;
			}
		}
		
		//BFS
		Queue<Jaws> q = new ArrayDeque<>();
		q.add(new Jaws(0, fishes, 0, 0, order));
		
		while (!q.isEmpty()) {
			Jaws cur = q.poll();
			
			//섭취
			cur.ate += cur.curFishes[cur.row][cur.col].fishNum;
			cur.dir = cur.curFishes[cur.row][cur.col].dir;
			cur.curFishes[cur.row][cur.col].ate = true;
			//최대값 갱신
			maxAte = Math.max(maxAte, cur.ate);
			//물고기 대이동
			fishMove(cur.curFishes, cur, cur.order);
			//먹을 수 있는 물고기 찾기, 현재 방향으로 4번 뻗기
			for (int i = 1; i < N; i++) {
				int nr = cur.row + i * dirs[cur.dir][0];
				int nc = cur.col + i * dirs[cur.dir][1];
				
				if (0 <= nr && nr < N && 0 <= nc && nc < N) {
					//먹히지 않은 물고기면 먹기
					if (!cur.curFishes[nr][nc].ate) {
						//상어 깊은 복사
						Jaws next = deepCopy(cur);
						next.row = nr;
						next.col = nc;
						q.add(next);
					}
				}
			}

		}

		System.out.println(maxAte);
	}

	private static void fishMove(Fish[][] curFishes, Jaws jaws, int[][] order) {
		//모든 물고기 번호순으로 순회
		for(int k = 1; k <= n; k++) {
			int i = order[k][0];
			int j = order[k][1];
			Fish curFish = curFishes[i][j];
			
			//상어가 있는 곳이면(즉, 상어에게 먹힌 물고기면?) continue
			if (i == jaws.row && j == jaws.col)
				continue;
			//먹혔던 물고기라면 continue
			if (curFishes[i][j].ate)
				continue;
			//초기 방향 설정
			int nextDir = curFish.dir;
			//do while로 8방 탐색
			do {
				int nr = i + dirs[nextDir][0];
				int nc = j + dirs[nextDir][1];

				if (0 <= nr && nr < N && 0 <= nc && nc < N && !(nr == jaws.row && nc == jaws.col)) {
					
					order[curFishes[i][j].fishNum][0] = nr;
					order[curFishes[i][j].fishNum][1] = nc;
					order[curFishes[nr][nc].fishNum][0] = i;
					order[curFishes[nr][nc].fishNum][1] = j;
					
					Fish temp = curFishes[i][j];
					temp.dir = nextDir;
					curFishes[i][j] = curFishes[nr][nc];
					curFishes[nr][nc] = temp;
					
					break;
				}
				//45도 반시계 회전
				nextDir++; 
				nextDir = nextDir > 8 ? nextDir - 8 : nextDir;
			} while (nextDir != curFish.dir);
		}
	}

	static public Jaws deepCopy(Jaws p) {
		Fish[][] nf = new Fish[N][N];
		int[][] newO = new int[n + 1][2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				Fish tempFish = new Fish(p.curFishes[i][j].fishNum, p.curFishes[i][j].dir, p.curFishes[i][j].ate);
				nf[i][j] = tempFish;
			}
		}
		for(int i = 1; i <= n; i++) {
			newO[i][0] = p.order[i][0];
			newO[i][1] = p.order[i][1];
		}
		
		return new Jaws(p.ate, nf, p.row, p.col, p.dir, newO);
	}

}
