import java.io.*;
import java.util.*;

public class Main {

	static int[] dr = { 0, -1, -1, 0, 1, 1, 1, 0, -1 }; // 무방향, 위, 왼대, 왼, 왼아, 아, 아오, 오, 오위
	static int[] dc = { 0, 0, -1, -1, -1, 0, 1, 1, 1 }; // 0 무방향 1 ~ 8 ..
	static int ans = 0;

	static class Fish {
		int num, vector;

		Fish(int num, int vector) {
			this.num = num;
			this.vector = vector;
		}

		void setter(int input_num, int input_vector) {
			this.num = input_num;
			this.vector = input_vector;
		}

		void set_vector(int input_vector) {
			this.vector = input_vector;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		Fish[][] origin_map = new Fish[4][4];
		// 4x4 의 맵과 1~8까지의 백터를 저장하는 3차원 배열,

		for (int i = 0; i < 4; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 4; j++) {
				int fish_num = Integer.parseInt(st.nextToken());
				int vector = Integer.parseInt(st.nextToken());

				origin_map[i][j] = new Fish(fish_num, vector);
			}
		}

		int temp_ans = origin_map[0][0].num; // 처음 0,0 값 먹고.
		boolean[] death = new boolean[17]; // 먹은거지워주고
		death[origin_map[0][0].num] = true; // death 배열에서 체크
		origin_map[0][0] = new Fish(20, origin_map[0][0].vector); // 상어 만들어주고, 상어는 20번

		start(origin_map, death, temp_ans);

		System.out.println(ans);

	}

	static void start(Fish[][] map, boolean[] death, int temp_ans) {

		Fish[][] copy_map = arr_copy(map); // 깊은 복사로 배열을 만들어준다,
		Fish shark = null;
		int method_ans = temp_ans; // 첫번째 때문에 값을 복사해준다,
		int r = -1; // 상어 좌표 행
		int c = -1; // 상어 좌표 열
		fish_swap(copy_map, death); // 먹고 들어왔으니까 일단 물고기 돌려주고,

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (copy_map[i][j] != null) {
					if (copy_map[i][j].num == 20) { // 상어를 찾아준다 배열에서,
						shark = new Fish(copy_map[i][j].num, copy_map[i][j].vector);
						r = i;
						c = j;
					}
				}
			}
		}
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				if (copy_map[i][j] != null) {
//					System.out.print(copy_map[i][j].num + " ");
//				}
//			}
//			System.out.println();
//		}
//		System.out.println(" ------ ");

		for (int i = 1; i <= 3; i++) {
			int nr = r + (dr[shark.vector] * i);
			int nc = c + (dc[shark.vector] * i);

			if (nr < 0 || nc < 0 || nr >= 4 || nc >= 4) {
				continue;
			}
			if (copy_map[nr][nc] == null) {
				continue;
			}

			if (copy_map[nr][nc] != null) {

				Fish remember = new Fish(copy_map[nr][nc].num, copy_map[nr][nc].vector);
				copy_map[nr][nc] = new Fish(20, remember.vector); // 이거 깊은복사 되는지 체크,
				copy_map[r][c] = null;
				death[remember.num] = true;
//				System.out.println(temp_ans + remember.num + "값 ");
//				System.out.println();
				start(copy_map, death, temp_ans + remember.num);

				// 백트래킹,
				copy_map[r][c] = shark;
				copy_map[nr][nc] = remember;
				death[remember.num] = false;

//				move_count++;
			}
		}

		ans = Math.max(method_ans, ans);

	}

	static void fish_swap(Fish[][] input_map, boolean[] death) {

		for (int i = 1; i <= 16; i++) {
			if (!death[i]) {

				// TODO : 스왑,

				out: for (int j = 0; j < 4; j++) {
					for (int k = 0; k < 4; k++) {
						if (input_map[j][k] != null) { // 빈칸이 아니고,
							if (input_map[j][k].num == i) { // 해당 번호와 현재 맵의 fish 번호가 같다면,

								int vector = input_map[j][k].vector; // 해당 물고기의 방향과
								int vector_find_count = 0; // 방향을 돌리는 카운트

								while (vector_find_count < 9) {

									if (vector == 0) {
										vector++;
									}

									int nr = j + dr[vector]; // 만약 다음 방향이 가능한지계속 체크
									int nc = k + dc[vector];

									if (nr < 0 || nc < 0 || nr >= 4 || nc >= 4) {
										vector_find_count++;
										vector = (vector % 8) + 1;
										continue; // 맵 밖이면 넘기고
									}

									if (input_map[nr][nc] == null) {
										input_map[nr][nc] = input_map[j][k];
										input_map[nr][nc].set_vector(vector);
										input_map[j][k] = null;
										break out;
									}

									if (input_map[nr][nc].num == 20) { // 상어면 넘기고
										vector_find_count++;
										vector = (vector % 8) + 1;
										continue;
									}

									if (input_map[nr][nc] != null) {
										Fish temp = input_map[nr][nc];
										input_map[nr][nc] = input_map[j][k];
										input_map[j][k] = temp;
										input_map[nr][nc].set_vector(vector);
										break out;
									}

								}

							}
						}

					}
				}

			}
		}

	}

	static Fish[][] arr_copy(Fish[][] map) {

		Fish[][] copy_map = new Fish[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (map[i][j] != null) {
					Fish temp = new Fish(map[i][j].num, map[i][j].vector);
					copy_map[i][j] = temp;
				}
			}
		}

		return copy_map;
	}

}
