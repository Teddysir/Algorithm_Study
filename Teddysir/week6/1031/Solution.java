import java.util.Arrays;

class Solution {

	static int N;
	static int answer;

	public int solution(int n, int[][] q, int[] ans) {
		answer = 0;
		N = n;
		makeCombination(0, new int[5], 1, q, ans);

		return answer;
	}

	private static void makeCombination(int cnt, int[] selected, int startIdx, int[][] compare_arr, int[] ans) {

		if (cnt == 5) {
			// 임의로 만들어짐,
			if (compare(selected, compare_arr, ans)) {
//				System.out.println(Arrays.toString(selected));
				answer++;
			}
			return;
		}

		for (int i = startIdx; i <= N; i++) {
			selected[cnt] = i;
			makeCombination(cnt + 1, selected, i + 1, compare_arr, ans);
		}

	}

	private static boolean compare(int[] arr, int[][] compare_arr, int[] ans) {

		for (int i = 0; i < compare_arr.length; i++) {
			int check = check_one_arr(arr, compare_arr[i]);

			if (ans[i] != check) {
				return false; // 만약 ans[i] 랑 일치 안하면 break;
			}
		}
		return true; // 성공

	}

	private static int check_one_arr(int[] arr, int[] compare) {

		int tempCount = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (arr[j] == compare[i]) {
					tempCount++;
				}
			}
		}
		return tempCount;
	}
}