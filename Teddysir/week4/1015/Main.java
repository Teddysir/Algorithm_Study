import java.io.*;
import java.util.*;

// 6은 벽 1~5는 CCTV, 0은 빈칸 

public class Main {

	static int[][] map;
	static int R, C, ans;
	static List<Pos> posList = new ArrayList<Pos>();
	static int[] vector_num = { 0, 1, 2, 3 };

	static int[] dr = { -1, 0, 1, 0 };
	static int[] dc = { 0, 1, 0, -1 };

	static int[][][] cctv_dir = { 
			{}, 
			{ { 0 }, { 1 }, { 2 }, { 3 } }, 
			{ { 0, 2 }, { 1, 3 } },
			{ { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 0 } }, 
			{ { 0, 1, 2 }, { 1, 2, 3 }, { 2, 3, 0 }, { 3, 0, 1 } },
			{ { 0, 1, 2, 3 } } };

	static class Pos {
		int r, c, num;

		Pos(int r, int c, int num) {
			this.r = r;
			this.c = c;
			this.num = num;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken()); // 행
		C = Integer.parseInt(st.nextToken()); // 열

		map = new int[R][C];
		ans = Integer.MAX_VALUE;

		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				if (map[i][j] >= 1 && map[i][j] <= 5) {
					posList.add(new Pos(i, j, map[i][j])); // CCTV 좌표들과 어떤CCTV인지 저장,
				}
			}
		}

		solution(0, new int[posList.size()]);
		System.out.println(ans);

	}

	static void solution(int depth, int[] vectors) {

		if (depth == posList.size()) {
			get_answer(vectors);
			return;
		}

		int type = posList.get(depth).num;
		for (int i = 0; i < cctv_dir[type].length; i++) { // 이부분이 문제였구나 ,,, , , ,ㄹㄴㅇㅁㄴㄹ
			vectors[depth] = i;
			solution(depth + 1, vectors);
		}

	}

	static void get_answer(int[] vectors) {

		int[][] tempMap = new int[R][C];
		for (int i = 0; i < R; i++) {
			tempMap[i] = map[i].clone(); // 이렇게도 복사가 가능하구나 2차원 배열을
		}

		for (int i = 0; i < posList.size(); i++) {
			Pos pos = posList.get(i);

			int type = pos.num; // 어떤 cctv이며,
			int vector = vectors[i]; // 어디를 보는 cctv인지,

			int[][] cctv = cctv_dir[type];

			for (int d : cctv[vector]) {
				one_dir(tempMap, pos.r, pos.c, d);
			}

		}

		count_map(tempMap);

	}

	static void one_dir(int[][] tempMap, int r, int c, int dir) {

		int nr = r + dr[dir];
		int nc = c + dc[dir];

		while (true) {
			if (nr >= R || nc >= C || nr < 0 || nc < 0)
				break;

			if (tempMap[nr][nc] == 6)
				break;

			if (tempMap[nr][nc] == 0)
				tempMap[nr][nc] = -1; // 감시가능
			nr += dr[dir];
			nc += dc[dir];
		}
	}

	static void count_map(int[][] tempMap) { // 현재 맵의 사각지대를 구하는 메서드
		int temp_count = 0;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (tempMap[i][j] == 0) {
					temp_count++;
				}
			}
		}

		ans = Math.min(temp_count, ans);
	}

}
