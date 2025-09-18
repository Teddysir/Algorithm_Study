/**
 * @Link https://www.acmicpc.net/problem/4179
 * @RunningTime 312 ms
 * @Memory 51064 KB
 * @Stratgy 너비 우선 탐색 (BFS)
 * - 하나의 큐에 불과 지훈의 위치를 함께 넣어 BFS를 실행합니다. 불을 먼저 큐에 넣어, 매 시간(너비)마다 불의 확산이 지훈의 이동보다 먼저 처리되도록 하여 탈출 가능 여부와 최소 시간을 탐색합니다.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int R = Integer.parseInt(st.nextToken()); // 행
		int C = Integer.parseInt(st.nextToken()); // 열
		Queue<int[]> queue = new ArrayDeque<>(); // BFS를 위한 큐
		
		// visited 배열: 0(미방문), 1(벽/불), >1(지훈이 방문한 시간)
		int[][] visited = new int[R + 2][C + 2];
		
		// 맵 주위에 벽을 한 겹 둘러서 탈출 조건을 쉽게 처리
		for (int i = 0; i < R + 2; i++) {
			visited[i][0] = visited[i][C + 1] = 1;
		}
		for (int i = 0; i < C + 2; i++) {
			visited[0][i] = visited[R + 1][i] = 1;
		}
		
		int[] J = new int[2]; // 지훈이의 초기 위치
		// 맵 정보 입력 및 초기 설정
		for (int i = 1; i < R + 1; i++) {
			String str = br.readLine();
			for (int j = 1; j < C + 1; j++) {
				char cur = str.charAt(j - 1);
				if (cur == '#') // 벽
					visited[i][j] = 1;
				else if (cur == 'J') { // 지훈
					J[0] = i;
					J[1] = j;
					visited[i][j] = 1; // 시작 시간은 1분
				} else if (cur == 'F') { // 불
					queue.offer(new int[] { i, j, 1 }); // 큐에 추가 (타입 1: 불)
					visited[i][j] = 1;
				}
			}
		}
		// 불을 먼저 모두 넣은 후, 지훈이를 마지막에 넣어 같은 레벨에서 불이 먼저 처리되도록 함
		queue.offer(new int[] { J[0], J[1], 0 }); // 큐에 추가 (타입 0: 지훈)

		int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // 상하좌우

		while (!queue.isEmpty()) {
			int[] current = queue.poll();
			
			// 현재 위치가 지훈이이고, 맵의 가장자리에 도달했다면 탈출 성공
			if (current[2] == 0 && (current[0] == 1 || current[0] == R || current[1] == 1 || current[1] == C)) {
				System.out.println(visited[current[0]][current[1]]);
				return;
			}
			
			// 상하좌우 4방향으로 확산/이동
			for (int d = 0; d < 4; d++) {
				int nr = current[0] + dir[d][0];
				int nc = current[1] + dir[d][1];

				// 현재 탐색 대상이 '불'인 경우
				if (current[2] == 1) {
					// 다음 칸이 아직 불타지 않은 빈 공간이라면
					if (visited[nr][nc] == 0) {
						visited[nr][nc] = 1; // 불타는 것으로 처리
						queue.offer(new int[] { nr, nc, current[2] }); // 다음 확산을 위해 큐에 추가
					}
				// 현재 탐색 대상이 '지훈'인 경우
				} else if (current[2] == 0) {
					// 다음 칸에 처음 방문하거나, 더 빠른 시간에 도달할 수 있는 경우
					if (visited[nr][nc] == 0 || visited[nr][nc] > visited[current[0]][current[1]] + 1) {
						visited[nr][nc] = visited[current[0]][current[1]] + 1; // 시간 기록
						queue.offer(new int[] { nr, nc, current[2] }); // 다음 이동을 위해 큐에 추가
					}
				}
			}
		}
		// 큐가 비었는데 탈출하지 못했다면 불가능
		System.out.println("IMPOSSIBLE");
	}
}
