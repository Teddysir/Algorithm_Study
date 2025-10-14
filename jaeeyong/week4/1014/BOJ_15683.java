/**
 * @Link https://www.acmicpc.net/problem/15683
 * @RunningTime 348ms
 * @Memory 52100kb
 * @Strategy 백트래킹 (Backtracking) & 시뮬레이션
 * * CCTV의 개수가 최대 8개로 많지 않으므로, 각 CCTV가 가질 수 있는 모든 방향의 조합을 시도하는 완전탐색이 가능.
 * * 백트래킹을 사용하여 0번 CCTV부터 마지막 CCTV까지 순서대로 4가지 방향(0, 90, 180, 270도) 중 하나를 선택.
 * * 모든 CCTV의 방향이 결정되면(재귀의 기저 조건), 해당 방향 조합으로 감시할 수 있는 영역의 크기를 시뮬레이션을 통해 계산.
 * * 모든 조합 중 감시 영역이 가장 넓은 경우(monitor)를 찾음.
 * * 최종 결과는 (전체 빈 공간의 수) - (최대 감시 영역의 크기) 로 사각지대의 최소 크기를 구함.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

	// N, M: 사무실 크기, blind: 사각지대(0)의 총 개수, monitor: 감시 가능한 최대 영역 크기
	static int N, M, blind, monitor;
	// map: 사무실 지도
	static int[][] map;
	// cctv: 사무실에 있는 CCTV 목록
	static ArrayList<CCTV> cctv;
	// dir: 기본 방향(상,하,좌,우), p*: dir을 90, 180, 270도 회전시킨 방향 벡터 배열
	static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // 0:상, 1:하, 2:좌, 3:우
	static int[][] p90 = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } }; // 상->우, 하->좌, 좌->상, 우->하
	static int[][] p180 = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
	static int[][] p270 = { { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };
	
	
	// CCTV 정보를 저장하는 클래스
	static class CCTV {
		int type, dir, row, col; // type: CCTV 종류, dir: 현재 방향, row/col: 위치

		public CCTV(int row, int col, int type) {
			this.row = row;
			this.col = col;
			this.type = type;
			this.dir = 0; // 방향은 초기에 0으로 설정
		}
		
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 경계 처리를 간편하게 하기 위해 상하좌우에 벽(6)으로 패딩을 줌
		map = new int[N + 2][M + 2];
		
		for (int i = 0; i < N + 2; i++) {
			map[i][0] = map[i][M + 1] = 6;
		}
		
		for (int i = 0; i < M + 2; i++) {
			map[0][i] = map[N + 1][i] = 6;
		}
		
		cctv = new ArrayList<>();
		
		blind = 0; // 총 빈 공간(사각지대) 수
		monitor = 0; // 최대 감시 영역 크기 (결과 갱신용)
		
		// 지도 정보를 입력받으면서 CCTV와 빈 공간의 개수를 셈
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j <= M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				
				// CCTV인 경우 리스트에 추가
				if (map[i][j] != 0 && map[i][j] != 6) cctv.add(new CCTV(i, j, map[i][j]));
				// 빈 공간인 경우 blind 카운트 증가
				else if (map[i][j] == 0) ++blind;
				
			}
		}
		
		// 백트래킹 함수 호출하여 모든 경우의 수 탐색 시작
		selectDirection(0);
		
		// 최종 결과: (총 빈 공간 수) - (최대 감시 가능 영역)
		System.out.println(blind - monitor);
	}

	/**
	 * 백트래킹을 통해 각 CCTV의 방향을 결정하는 함수
	 * @param cnt 현재 방향을 결정할 CCTV의 인덱스
	 */
	private static void selectDirection(int cnt) {
		// 기저 조건: 모든 CCTV의 방향이 결정되었을 때
		if (cnt == cctv.size()) {
			// 현재 방향 조합으로 감시 가능한 영역을 계산
			int currentMonitor = 0;
			boolean[][] visited = new boolean[N + 2][M + 2]; // 중복 카운팅 방지를 위한 방문 배열
			for (CCTV c : cctv) {
				int row = c.row;
				int col = c.col;
				// 각 CCTV 타입과 설정된 방향(c.dir)에 따라 감시 영역 시뮬레이션
				switch (c.type) {
				case 1: // 1번 CCTV: 한 방향
					while (true) {
						row += dir[c.dir][0];
						col += dir[c.dir][1];
						if(map[row][col] == 6) break; // 벽을 만나면 중단
						else if(map[row][col] != 0 || visited[row][col]) continue; // 다른 CCTV거나 이미 감시된 곳이면 통과
						else { // 빈 공간이면 감시 영역 카운트
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					break;
				case 2: // 2번 CCTV: 180도 양방향
					// 정방향
					while (true) {
						row += dir[c.dir][0];
						col += dir[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					row = c.row; col = c.col; // 위치 초기화
					// 180도 반대 방향
					while (true) {
						row += p180[c.dir][0];
						col += p180[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					break;
				case 3: // 3번 CCTV: 90도 직각 방향
					while (true) {
						row += dir[c.dir][0];
						col += dir[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					row = c.row; col = c.col;
					while (true) {
						row += p90[c.dir][0];
						col += p90[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					break;
				case 4: // 4번 CCTV: 세 방향
					while (true) {
						row += dir[c.dir][0];
						col += dir[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					row = c.row; col = c.col;
					while (true) {
						row += p180[c.dir][0];
						col += p180[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					row = c.row; col = c.col;
					while (true) {
						row += p270[c.dir][0];
						col += p270[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					break;
				case 5: // 5번 CCTV: 네 방향
					// 4방향을 모두 탐색 (dir, p90, p180, p270)
					while (true) {
						row += dir[c.dir][0];
						col += dir[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					row = c.row; col = c.col;
					while (true) {
						row += p90[c.dir][0];
						col += p90[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					row = c.row; col = c.col;
					while (true) {
						row += p180[c.dir][0];
						col += p180[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					row = c.row; col = c.col;
					while (true) {
						row += p270[c.dir][0];
						col += p270[c.dir][1];
						if(map[row][col] == 6) break;
						else if(map[row][col] != 0 || visited[row][col]) continue;
						else {
							++currentMonitor;
							visited[row][col] = true;
						}
					}
					break;
				}
			}
			
			// 최대 감시 영역 크기 갱신
			monitor = Math.max(monitor, currentMonitor);
			return;
		}
		
		// 재귀 호출: 현재 CCTV(cnt)의 방향을 4가지(0~3)로 설정해보며 다음 CCTV로 넘어감
		for (int i = 0; i < 4; i++) {
			int temp = cctv.get(cnt).dir; // 현재 방향 상태 저장 (사실상 이 코드에선 불필요)
			cctv.get(cnt).dir = i; // 방향 설정
			selectDirection(cnt + 1); // 다음 CCTV로 재귀 호출
			cctv.get(cnt).dir = temp; // 원래 방향으로 복구 (백트래킹)
		}
	}
}
