/**
 * @Link https://www.acmicpc.net/problem/6087
 * @RunningTime 96ms
 * @Memory 13984kb
 * @Strategy 다익스트라 (Dijkstra)
 * * 레이저를 연결하는데 필요한 거울의 최소 개수를 구하는 문제로, 최단 경로 알고리즘으로 접근.
 * * 경로의 비용(가중치)은 '거리'가 아닌 '설치된 거울의 개수'.
 * - 직선으로 이동: 비용 0
 * - 방향 전환(90도): 비용 1 (거울 1개 필요)
 * * 다익스트라 알고리즘을 사용하여 시작점 'C'에서 도착점 'C'까지의 최소 비용(거울 개수)을 탐색.
 * * 방문 상태를 (행, 열, 방향)으로 관리해야 함. 어느 방향에서 해당 칸에 도착했는지에 따라 다음 이동 시 거울이 필요한지 여부가 결정되기 때문.
 * * 우선순위 큐에는 (행, 열, 방향, 현재까지 사용한 거울 개수) 정보를 담아 거울 개수가 적은 순서대로 탐색.
 * * `mirrorCount[H][W][4]` 배열을 두어 각 칸에 4가지 방향으로 도착했을 때의 최소 거울 개수를 기록.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	// W: 가로, H: 세로, answer: 최종 결과(최소 거울 개수)
	static int W, H, answer;
	// map: 지도 정보
	static char[][] map;
	// C: 두 'C'의 위치 좌표
	static int[][] C;
	// dir: 4방향(동, 남, 서, 북) 이동을 위한 벡터
	static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
	// INF: 최댓값 (비용 초기화용)
	static final int INF = Integer.MAX_VALUE;

	// 다익스트라 탐색을 위한 상태를 저장하는 클래스
	static class Laser implements Comparable<Laser> {
		int row, col, dir, count; // 행, 열, 현재 진행 방향, 사용한 거울 개수

		public Laser(int row, int col, int dir, int count) {
			this.row = row;
			this.col = col;
			this.dir = dir; // 0:동, 1:남, 2:서, 3:북, -1:시작점
			this.count = count;
		}

		@Override
		// 우선순위 큐에서 거울 개수(count)가 적은 순서대로 정렬하기 위함
		public int compareTo(Laser o) {
			return Integer.compare(this.count, o.count);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		// 경계 검사를 간편하게 하기 위해 맵의 상하좌우를 '*'로 패딩
		map = new char[H + 2][W + 2];
		for (int i = 0; i < H + 2; i++) {
			map[i][0] = map[i][W + 1] = '*';
		}
		for (int i = 0; i < W + 2; i++) {
			map[0][i] = map[H + 1][i] = '*';
		}
		
		C = new int[2][2]; // 두 'C'의 좌표 저장
		int count = 0;
		// 맵 정보를 입력받고 'C'의 위치를 저장
		for (int i = 1; i <= H; i++) {
			String str = br.readLine();
			for (int j = 1; j <= W; j++) {
				map[i][j] = str.charAt(j - 1);
				if (map[i][j] == 'C') {
					C[count][0] = i;
					C[count++][1] = j;
					map[i][j] = '.'; // 'C' 위치도 빈 공간으로 처리
				}
			}
		}

		answer = Integer.MAX_VALUE;
		PriorityQueue<Laser> pq = new PriorityQueue<>();
		// mirrorCount[row][col][dir]: (row, col) 위치에 dir 방향으로 도착했을 때의 최소 거울 개수
		int[][][] mirrorCount = new int[H + 2][W + 2][4];
		// 모든 비용을 무한대로 초기화
		for (int i = 0; i < H + 2; i++) {
			for (int j = 0; j < W + 2; j++) {
				Arrays.fill(mirrorCount[i][j], INF);
			}
		}
		
		// 다익스트라 시작점 설정
		pq.offer(new Laser(C[0][0], C[0][1], -1, 0)); // 시작점은 방향이 없으므로 -1, 거울 개수 0
		Arrays.fill(mirrorCount[C[0][0]][C[0][1]], 0); // 시작점의 비용은 0으로 초기화

		while (!pq.isEmpty()) {
			Laser current = pq.poll(); // 현재까지 거울 개수가 가장 적은 상태를 꺼냄

			// 꺼낸 위치가 도착점이라면, 이것이 최적해이므로 탐색 종료.
			if (current.row == C[1][0] && current.col == C[1][1]) {
					answer = current.count; // 정답 기록
					break; // while 루프 탈출
			}

			// 이미 더 적은 거울 개수로 해당 위치, 해당 방향에 도달한 적이 있다면 무시
			if (current.dir != -1 && mirrorCount[current.row][current.col][current.dir] < current.count)
				continue;

			// 4방향 탐색
			for (int i = 0; i < 4; i++) {
				int nr = current.row + dir[i][0];
				int nc = current.col + dir[i][1];

				// 벽을 만나면 진행 불가
				if (map[nr][nc] == '*')
					continue;

				// 다음 상태의 거울 개수 계산
				int nextCount = current.count;
				// 시작점이 아니고, 진행 방향이 바뀌었다면 거울 1개 추가
				if (current.dir != -1 && i != current.dir) {
					nextCount++;
				}
				
				// 새로 계산된 경로(거울 개수)가 기존 경로보다 더 좋으면 갱신하고 PQ에 추가
				if (mirrorCount[nr][nc][i] > nextCount) {
					mirrorCount[nr][nc][i] = nextCount;
					pq.offer(new Laser(nr, nc, i, nextCount));
				}
			}
		}
		System.out.println(answer);
	}
}
