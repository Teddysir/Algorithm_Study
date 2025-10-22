/**
 * @Link https://www.acmicpc.net/problem/19236
 * @RunningTime 72ms
 * @Memory 11656kb
 * @Strategy 백트래킹 (Backtracking) + 시뮬레이션
 * * 상어가 이동할 수 있는 모든 경우의 수를 탐색하여 먹을 수 있는 물고기 번호 합의 최댓값을 찾는 문제.
 * * 재귀 함수를 통해 (물고기 이동 -> 상어 이동)의 한 턴을 시뮬레이션한다.
 * * 각 재귀 단계마다 현재 상태(물고기, 공간 정보)를 깊은 복사로 저장해두고, 재귀 호출이 끝난 뒤 복원하여 다른 경우의 수를 탐색한다. (백트래킹)
 * * 물고기 번호로 위치와 방향을 바로 찾을 수 있도록 별도의 fish 배열을 사용하여 관리한다.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	// answer: 먹을 수 있는 물고기 번호 합의 최댓값
	static int answer;
	// space: 4x4 공간, 각 칸에 있는 물고기 번호를 저장. 상어=0, 빈칸=-1
	static int[][] space;
	// fish: 각 물고기의 정보를 저장하는 배열. fish[번호] = {행, 열, 방향}. 상어는 0번 인덱스 사용.
	static int[][] fish;
	// dir: 8방향(↑, ↖, ←, ↙, ↓, ↘, →, ↗)에 대한 행/열 변화량
	static int[][] dir = { { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 } };
	// 상수 정의
	static final int SPACE_SIZE = 4;
	static final int NUM_OF_FISH = 16;
	static final int ROW = 0;
	static final int COL = 1;
	static final int DIR = 2;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		space = new int[SPACE_SIZE][SPACE_SIZE];
		fish = new int[NUM_OF_FISH + 1][3]; // 0: 상어, 1~16: 물고기

		// 입력 처리: 4x4 공간의 물고기 번호(a)와 방향(b)을 입력받아 space와 fish 배열 초기화
		for (int i = 0; i < SPACE_SIZE; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < SPACE_SIZE; j++) {
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken()) - 1; // 방향은 0-based index로 변환

				space[i][j] = a;
				fish[a][ROW] = i;
				fish[a][COL] = j;
				fish[a][DIR] = b;
			}
		}

		// 시뮬레이션 시작: 상어가 (0, 0) 위치의 물고기를 먹음
		int score = space[0][0]; // 초기 점수 획득
		fish[0][ROW] = 0; // 상어 위치 설정
		fish[0][COL] = 0;
		fish[0][DIR] = fish[space[0][0]][DIR]; // 먹은 물고기의 방향을 가짐
		fish[space[0][0]][ROW] = -1; // 먹힌 물고기는 비활성화
		space[0][0] = 0; // (0,0) 위치에 상어가 있음을 표시

		answer = 0;
		// 백트래킹 시뮬레이션 시작
		simulation(score);

		System.out.println(answer);
	}

	/**
	 * (물고기 이동 -> 상어 이동) 과정을 재귀적으로 시뮬레이션하는 함수
	 * @param score 현재까지 누적된 점수
	 */
	private static void simulation(int score) {
		// 현재 점수를 기준으로 최댓값 갱신. (상어가 더 이상 움직일 수 없는 경우를 위한 기저 역할)
		answer = Math.max(answer, score);

		// --- 1. 물고기 이동 ---
		// 1번부터 16번 물고기까지 순서대로 이동
		for (int i = 1; i <= NUM_OF_FISH; i++) {
			// 해당 물고기가 살아있는 경우에만 이동
			if (fish[i][ROW] != -1) {
				// 8방향을 순서대로 탐색하며 이동할 수 있는 칸 찾기 (현재 방향부터 45도씩 반시계 회전)
				for (int j = 0; j < 8; j++) {
					int newDir = (fish[i][DIR] + j) % 8;
					int nr = fish[i][ROW] + dir[newDir][ROW];
					int nc = fish[i][COL] + dir[newDir][COL];
					
					// 이동할 위치가 공간 내에 있고, 상어가 없는 칸이라면 이동
					if (0 <= nr && nr < SPACE_SIZE && 0 <= nc && nc < SPACE_SIZE && space[nr][nc] != 0) {
						// 이동할 위치가 빈 칸(-1)이 아닌 다른 물고기가 있는 칸인 경우
						if (space[nr][nc] != -1) {
							int tmpNum = space[nr][nc]; // 이동할 위치에 있던 물고기 번호
							
							int tmpRow = fish[i][ROW]; // 현재 물고기의 위치 임시 저장
							int tmpCol = fish[i][COL];
							
							// space 배열에서 두 물고기 위치 교환
							space[fish[i][ROW]][fish[i][COL]] = space[nr][nc];
							
							// fish 배열에서 현재 물고기(i) 정보 갱신 (위치, 방향)
							fish[i][ROW] = fish[tmpNum][ROW];
							fish[i][COL] = fish[tmpNum][COL];
							fish[i][DIR] = newDir;
							
							// fish 배열에서 상대 물고기(tmpNum) 정보 갱신 (위치)
							fish[tmpNum][ROW] = tmpRow;
							fish[tmpNum][COL] = tmpCol;
							space[nr][nc] = i;
						} else { // 이동할 위치가 빈 칸(-1)인 경우
							space[fish[i][ROW]][fish[i][COL]] = -1; // 원래 위치는 빈 칸으로
							
							// fish 배열 정보 갱신
							fish[i][ROW] = nr;
							fish[i][COL] = nc;
							fish[i][DIR] = newDir;
							
							space[nr][nc] = i; // 새로운 위치에 현재 물고기 번호 기록
						}
						break; // 이동을 마쳤으므로 방향 탐색 중단
					}
				}
			}
		}
		
		// --- 백트래킹을 위한 상태 저장 ---
		// 물고기 이동이 끝난 후, 상어가 이동하기 전의 상태를 저장
		int[][] originMatrix = new int[SPACE_SIZE][SPACE_SIZE];
		int[][] originFish = new int[NUM_OF_FISH + 1][3];
		for (int i = 0; i < SPACE_SIZE; i++) {
			for (int j = 0; j < SPACE_SIZE; j++) {
				originMatrix[i][j] = space[i][j];
			}
		}
		for (int i = 0; i <= NUM_OF_FISH; i++) {
			for (int j = 0; j < 3; j++) {
				originFish[i][j] = fish[i][j];
			}
		}
		
		// --- 2. 상어 이동 ---
		// 상어는 현재 방향으로 1칸, 2칸, 3칸 이동 가능
		for (int i = 1; i < SPACE_SIZE; i++) {
			int nr = fish[0][ROW] + dir[fish[0][DIR]][ROW] * i;
			int nc = fish[0][COL] + dir[fish[0][DIR]][COL] * i;

			// 이동할 위치가 공간 내에 있고, 먹을 물고기가 있는 경우
			if (0 <= nr && nr < SPACE_SIZE && 0 <= nc && nc < SPACE_SIZE && space[nr][nc] > 0) {
				
				int fishNum = space[nr][nc]; // 먹힐 물고기 번호

				// 상어 이동 및 물고기 먹기 처리
				space[fish[0][ROW]][fish[0][COL]] = -1; // 상어의 원래 위치는 빈 칸으로
				fish[0][ROW] = nr; // 상어 위치 갱신
				fish[0][COL] = nc;
				fish[0][DIR] = fish[space[nr][nc]][DIR]; // 먹은 물고기의 방향을 가짐
				fish[space[nr][nc]][ROW] = -1; // 먹힌 물고기 비활성화
				space[nr][nc] = 0; // 새로운 위치에 상어 표시

				// 다음 턴으로 재귀 호출
				simulation(score + fishNum);

				// --- 백트래킹: 상태 복원 ---
				// 재귀 호출이 끝나면, 상어가 다른 이동을 시도할 수 있도록 이전 상태로 복원
				for (int j = 0; j < SPACE_SIZE; j++) {
					for (int k = 0; k < SPACE_SIZE; k++) {
						space[j][k] = originMatrix[j][k];
					}
				}
				for (int j = 0; j <= NUM_OF_FISH; j++) {
					for (int k = 0; k < 3; k++) {
						fish[j][k] = originFish[j][k];
					}
				}
			}
		}
	}
}
