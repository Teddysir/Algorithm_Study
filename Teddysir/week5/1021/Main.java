import java.util.*;
import java.io.*;

public class Main {

	static int N, ans;
	static int[][] map;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		map = new int[N][N];
		ans = -1;

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		left(map, 0);
		right(map, 0);
		up(map, 0);
		down(map, 0);
		System.out.println(ans);

	}

	static void left(int[][] input, int count) {

		// --------------- // 아래가 기저조건 함수
		if (count == 5) { // 기저조건,

			int temp_ans = -1;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					temp_ans = Math.max(temp_ans, input[i][j]);
				}
			}

			ans = Math.max(ans, temp_ans);
			return;
		}
		// ----------------- 아래까지 좌표 압축,

		int[][] new_map = new int[N][N]; // 다음으로 넘길 배열 선언

		List<Integer>[] list = new ArrayList[N]; // 계산하기 위한 인접리스트 (좌표압축?)

		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (input[i][j] != 0) { // 만약 그 좌표가 0이 아니면, list에 넣고,
					list[i].add(input[i][j]);
				}
			}
		}

		// -------------------

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < list[i].size() - 1; j++) {
				if (list[i].get(j).equals(list[i].get(j + 1)) && list[i].get(j) != 0) {
					int newValue = list[i].get(j) * 2;
					list[i].set(j, newValue);
					list[i].remove(j + 1);
				}
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < list[i].size(); j++) {
				new_map[i][j] = list[i].get(j);
			}
		}

		left(new_map, count + 1);
		up(new_map, count + 1);
		down(new_map, count + 1);
		right(new_map, count + 1);

	}

	static void right(int[][] input, int count) {

		// --------------- // 아래가 기저조건 함수
		if (count == 5) { // 기저조건,

			int temp_ans = -1;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					temp_ans = Math.max(temp_ans, input[i][j]);
				}
			}

			ans = Math.max(ans, temp_ans);
			return;
		}
		// ----------------- 아래까지 좌표 압축,

		int[][] new_map = new int[N][N]; // 다음으로 넘길 배열 선언

		List<Integer>[] list = new ArrayList[N]; // 계산하기 위한 인접리스트 (좌표압축?)

		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (input[i][j] != 0) { // 만약 그 좌표가 0이 아니면, list에 넣고,
					list[i].add(input[i][j]);
				}
			}
		}

		// -------------------
		// 오른쪽으로,

		for (int i = 0; i < N; i++) {
			for (int j = list[i].size() - 1; j > 0; j--) {
				if (list[i].get(j).equals(list[i].get(j - 1)) && list[i].get(j) != 0) {
					int newValue = list[i].get(j) * 2;
					list[i].set(j, newValue);
					list[i].remove(j - 1);
				}
			}
		}

		for (int i = 0; i < N; i++) {
			int k = N - 1; // 끝에서부터,
			for (int j = list[i].size() - 1; j >= 0; j--) {
				new_map[i][k] = list[i].get(j);
				k--;
			}
		}

		left(new_map, count + 1);
		up(new_map, count + 1);
		down(new_map, count + 1);
		right(new_map, count + 1);

	}

	static void up(int[][] input, int count) { // 위로 올리기,

		// --------------- // 아래가 기저조건 함수
		if (count == 5) { // 기저조건,

			int temp_ans = -1;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					temp_ans = Math.max(temp_ans, input[i][j]);
				}
			}

			ans = Math.max(ans, temp_ans);
			return;
		}
		// ----------------- 아래까지 좌표 압축,

		int[][] new_map = new int[N][N]; // 다음으로 넘길 배열 선언

		List<Integer>[] list = new ArrayList[N]; // 계산하기 위한 인접리스트 (좌표압축?)

		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<Integer>();
		}

		for (int j = 0; j < N; j++) {
			for (int i = 0; i < N; i++) {
				if (input[i][j] != 0) { // 만약 그 좌표가 0이 아니면, list에 넣고,
					list[j].add(input[i][j]);
				}
			}
		}

		// -------------------

		for (int j = 0; j < N; j++) {
			for (int i = 0; i < list[j].size() - 1; i++) {
//			for (int i = list[j].size() - 1; i > 0; i--) { // 틀림, 
				if (list[j].get(i).equals(list[j].get(i + 1)) && list[j].get(i) != 0) {
//				if (list[j].get(i) == list[j].get(i + 1) && list[j].get(i) != 0) {
					int newValue = list[j].get(i) * 2;
					list[j].set(i, newValue);
					list[j].remove(i + 1);
				}
			}
		}

		for (int j = 0; j < N; j++) {
			for (int i = 0; i < list[j].size(); i++) {
				new_map[i][j] = list[j].get(i);
			}
		}

		left(new_map, count + 1);
		up(new_map, count + 1);
		down(new_map, count + 1);
		right(new_map, count + 1);

	}

	static void down(int[][] input, int count) {

		// --------------- // 아래가 기저조건 함수
		if (count == 5) { // 기저조건,

			int temp_ans = -1;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					temp_ans = Math.max(temp_ans, input[i][j]);
				}
			}

			ans = Math.max(ans, temp_ans);
			return;
		}
		// ----------------- 아래까지 좌표 압축,

		int[][] new_map = new int[N][N]; // 다음으로 넘길 배열 선언

		List<Integer>[] list = new ArrayList[N]; // 계산하기 위한 인접리스트 (좌표압축?)

		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<Integer>();
		}

		for (int j = 0; j < N; j++) {
			for (int i = 0; i < N; i++) {
				if (input[i][j] != 0) { // 만약 그 좌표가 0이 아니면, list에 넣고,
					list[j].add(input[i][j]);
				}
			}
		}

		// -------------------

		for (int j = 0; j < N; j++) {
			for (int i = list[j].size() - 1; i > 0; i--) {
				if (list[j].get(i).equals(list[j].get(i - 1)) && list[j].get(i) != 0) {
					int newValue = list[j].get(i) * 2;
					list[j].set(i, newValue);
					list[j].remove(i - 1);
				}
			}
		}

		for (int j = 0; j < N; j++) {
			int k = N - 1;
			for (int i = list[j].size() - 1; i >= 0; i--) {
				new_map[k][j] = list[j].get(i);
			}
		}

		left(new_map, count + 1);
		up(new_map, count + 1);
		down(new_map, count + 1);
		right(new_map, count + 1);

	}

}
