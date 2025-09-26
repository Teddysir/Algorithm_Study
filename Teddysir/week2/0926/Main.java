import java.io.*;
import java.util.*;

public class Main {

	static int N, ans, cut;
	static int[][] arr;
	static boolean[] visited;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());
		arr = new int[N][N];
		visited = new boolean[N];

		StringTokenizer st = new StringTokenizer(br.readLine());

		int root = -1;
		for (int i = 0; i < N; i++) {

			int num = Integer.parseInt(st.nextToken());
			if (num == -1) {
				root = i;
			} else {
				arr[num][i] = 1;
			}

		}
		cut = Integer.parseInt(br.readLine());
		if (cut == root) {
			ans = 0;
		} else {
			for (int i = 0; i < N; i++) {
				arr[cut][i] = arr[i][cut] = 0;
			}
			solution(root);
		}
		System.out.println(ans);

	}

	static void solution(int start) {

		boolean flag = false;
		for (int i = 0; i < N; i++) {
			if (arr[start][i] == 1) {
				flag = true;
				solution(i);
			}
		}
		if (!flag) {
			ans++;
		}
	}

}