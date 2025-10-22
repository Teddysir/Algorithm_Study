/**
 * @Link https://www.acmicpc.net/problem/12100
 * @RunningTime 224ms
 * @Memory 24484kb
 * @Strategy 백트래킹 (Backtracking) + 시뮬레이션
 * * 최대 5번 이동하여 만들 수 있는 가장 큰 블록을 찾는 문제.
 * * 5번의 이동에 대해, 각 이동은 상/하/좌/우 4가지 방향이 가능하므로, 모든 경우의 수는 4^5 = 1024개.
 * * 이 숫자는 충분히 작으므로, 모든 이동 순서 조합을 시도하는 완전탐색(백트래킹)이 가능하다.
 * 1. 재귀 함수 `select(cnt)`를 통해 5번의 이동 방향을 선택한다. (cnt: 현재까지 이동한 횟수)
 * 2. 각 이동마다 4가지 방향을 모두 시도한다.
 * 3. 다음 재귀 호출 전에 현재 보드 상태를 임시 배열에 저장해두고, 재귀 호출이 끝나면 복원하여 다른 방향을 탐색할 수 있도록 한다. (백트래킹)
 * 4. 5번의 이동이 모두 끝나면(cnt == 5), 현재 보드에서 가장 큰 블록 값을 찾아 정답을 갱신한다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	// N: 보드 크기, answer: 최종 정답(가장 큰 블록 값)
	static int N, answer;
	// board: 게임 보드
	static int[][] board;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		board = new int[N][N];

		// 보드 초기 상태 입력
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		answer = 0;
		// 백트래킹 시작 (0번 이동부터)
		select(0);

		System.out.println(answer);

	}

	/**
	 * 백트래킹을 통해 5번의 이동 방향 조합을 생성하는 함수
	 * @param cnt 현재까지 이동한 횟수
	 */
	private static void select(int cnt) {

		// 기저 조건: 5번 이동을 모두 완료했다면
		if (cnt == 5) {
			// 현재 보드에서 가장 큰 블록 값을 찾아 정답 갱신
			calc();
			return;
		}

		// 재귀 파트: 4가지 방향(상, 우, 하, 좌)으로 이동 시도
		for (int d = 0; d < 4; d++) {

			// 백트래킹을 위해 현재 보드 상태를 임시 배열에 저장
			int[][] temp = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					temp[i][j] = board[i][j];
				}
			}

			// d 방향으로 보드를 이동시킴
			move(d);

			// 다음 이동을 위해 재귀 호출
			select(cnt + 1);

			// 재귀 호출이 끝나면, 이전 상태로 보드를 복원 (백트래킹)
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					board[i][j] = temp[i][j];
				}
			}
		}
	}

	/**
	 * 주어진 방향(d)으로 보드의 블록을 이동하고 합치는 시뮬레이션 함수
	 * @param d 이동 방향 (0:상, 1:우, 2:하, 3:좌)
	 */
	private static void move(int d) {
		
		switch (d) {
		case 0: // 위로 이동
			for (int col = 0; col < N; col++) { // 각 열에 대해 독립적으로 수행
				boolean[] merge = new boolean[N]; // 해당 이동에서 이미 합쳐진 블록인지 체크
				int row = 0;
				int value = 0;
				while (row < N) {
					// 1. 이동할 블록(value) 찾기
					while (row < N) {
						if (board[row][col] != 0) {
							value = board[row][col];
							board[row][col] = 0; // 원래 위치는 비움
							break;
						}
						row++;
					}
					if (row == N) break; // 이동할 블록이 더 이상 없으면 종료

					// 2. 블록의 최종 위치 찾기 (합치거나, 다른 블록/벽 앞까지)
					boolean isMerge = false;
					while (true) {
						if (row < 0) { // 보드 위쪽 끝에 도달
							row++;
							break;
						} else if (board[row][col] == value && !isMerge && !merge[row]) { // 같은 값의 블록 만나 합치기
							value *= 2;
							board[row][col] = 0;
							isMerge = true;
							row--;
						} else if (board[row][col] == 0) { // 빈 공간이면 계속 위로 이동
							row--;
						} else { // 다른 블록을 만나면 그 앞이 최종 위치
							row++;
							break;
						}
					}
					if (isMerge) merge[row] = true;
					board[row++][col] = value; // 최종 위치에 블록 놓기
				}
			}
			break;
		case 1: // 오른쪽으로 이동
			for (int row = 0; row < N; row++) { // 각 행에 대해 수행
				boolean[] merge = new boolean[N];
				int col = N - 1;
				int value = 0;
				while (col >= 0) {
					while (col >= 0) {
						if (board[row][col] != 0) {
							value = board[row][col];
							board[row][col] = 0;
							break;
						}
						col--;
					}
					if (col == -1) break;

					boolean isMerge = false;
					while (true) {
						if (col == N) {
							col--;
							break;
						} else if (board[row][col] == value && !isMerge && !merge[col]) {
							value *= 2;
							board[row][col] = 0;
							isMerge = true;
							col++;
						} else if (board[row][col] == 0) {
							col++;
						} else {
							col--;
							break;
						}
					}
					if (isMerge) merge[col] = true;
					board[row][col--] = value;
				}
			}
			break;
		case 2: // 아래로 이동
			for (int col = 0; col < N; col++) { // 각 열에 대해 수행
				boolean[] merge = new boolean[N];
				int row = N - 1;
				int value = 0;
				while (row >= 0) {
					while (row >= 0) {
						if (board[row][col] != 0) {
							value = board[row][col];
							board[row][col] = 0;
							break;
						}
						row--;
					}
					if (row == -1) break;

					boolean isMerge = false;
					while (true) {
						if (row == N) {
							row--;
							break;
						} else if (board[row][col] == value && !isMerge && !merge[row]) {
							value *= 2;
							board[row][col] = 0;
							isMerge = true;
							row++;
						} else if (board[row][col] == 0) {
							row++;
						} else {
							row--;
							break;
						}
					}
					if (isMerge) merge[row] = true;
					board[row--][col] = value;
				}
			}
			break;
		case 3: // 왼쪽으로 이동
			for (int row = 0; row < N; row++) { // 각 행에 대해 수행
				boolean[] merge = new boolean[N];
				int col = 0;
				int value = 0;
				while (col < N) {
					while (col < N) {
						if (board[row][col] != 0) {
							value = board[row][col];
							board[row][col] = 0;
							break;
						}
						col++;
					}
					if (col == N) break;
					
					boolean isMerge = false;
					while (true) {
						if (col < 0) {
							col++;
							break;
						} else if (board[row][col] == value && !isMerge && !merge[col]) {
							value *= 2;
							board[row][col] = 0;
							isMerge = true;
							col--;
						} else if (board[row][col] == 0) {
							col--;
						} else {
							col++;
							break;
						}
					}
					if (isMerge) merge[col] = true;
					board[row][col++] = value;
				}
			}
			break;
		}

	}

	/**
	 * 5번의 이동이 끝난 보드에서 가장 큰 블록 값을 찾아 answer를 갱신하는 함수
	 */
	private static void calc() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				answer = Math.max(answer, board[i][j]);
			}
		}
	}

}
