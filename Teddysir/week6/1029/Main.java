import java.util.ArrayDeque;
import java.util.Queue;

class Solution {

	static int target;
	static long q_sum_1, q_sum_2;
	static Queue<Integer> q_1 = new ArrayDeque<>();
	static Queue<Integer> q_2 = new ArrayDeque<>();

	public int solution(int[] queue1, int[] queue2) {
		int answer = -2;

		for (int i = 0; i < queue1.length; i++) {
			q_sum_1 += queue1[i];
			q_1.add(queue1[i]); // 리스트에 저장
		}

		for (int i = 0; i < queue2.length; i++) {
			q_sum_2 += queue2[i];
			q_2.add(queue2[i]); // 리스트에 저장
		}

		if (q_sum_1 > q_sum_2) {
			target = 2;
		} else if (q_sum_1 < q_sum_2) {
			target = 1;
		} else {
			return 0;
		}

		int count = 0;
		while (!q_1.isEmpty() && !q_2.isEmpty()) {

			count++;

			if (target == 1) { // 1번합이 더 작을떄가 타겟

				int temp = q_2.poll();

				q_sum_1 += temp;
				q_sum_2 -= temp;
				q_1.add(temp);

				if (q_sum_1 > q_sum_2) {
					target = 2;
				} else {
					target = 1;
				}
			} else { // 2번이 타겟

				int temp = q_1.poll();

				q_sum_2 += temp;
				q_sum_1 -= temp;
				q_2.add(temp);

				if (q_sum_1 > q_sum_2) {
					target = 2;
				} else {
					target = 1;
				}
			}

			if (q_sum_2 == q_sum_1) {
				return answer = count;
			}
			
			if(count > 300_000) {
				return -1;
			}

			if (q_1.isEmpty() || q_2.isEmpty()) {
				return -1;
			}

		}

		answer = count;

		return answer;
	}

}