/**
 * @Link https://www.acmicpc.net/problem/10800
 * @RunningTime 972ms
 * @Memory 87356kb
 * @Strategy 정렬 + 누적 합
 * * 각 공에 대해 자신보다 작고 색이 다른 공들의 크기 합을 구하는 문제.
 * * N이 최대 200,000이므로 O(N^2)의 단순 비교는 시간 초과.
 * 1. 모든 공을 크기(size) 순으로 오름차순 정렬한다.
 * 2. 정렬된 공들을 순서대로 순회하며, 현재까지 탐색한 공들의 크기 누적 합(totalSum)과 색깔별 크기 누적 합(colorSum)을 유지한다.
 * 3. 현재 공(B)을 기준으로, B보다 작은 모든 공들의 크기 합은 `totalSum`이다.
 * 4. 이 중 B와 색깔이 같은 공들의 크기 합은 `colorSum[B.color]`이다.
 * 5. 따라서, B에 대한 정답은 `totalSum - colorSum[B.color]` 이다.
 * 6. (주의점) 크기가 같은 공들은 같은 결과를 가져야 한다. 따라서 크기가 같은 공들을 한 그룹으로 묶어 처리한다.
 * - 그룹 내 모든 공들의 정답을 먼저 계산한다. (이때 누적 합은 이 그룹보다 작은 공들까지만의 합)
 * - 계산이 끝난 후, 그룹 내 모든 공들의 크기를 누적 합에 반영한다.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Main {

	// 공의 정보를 담는 클래스
	static class Ball implements Comparable<Ball> {
		int idx, color, size; // idx: 원래 입력 순서, color: 색상, size: 크기

		public Ball(int idx, int color, int size) {
			this.idx = idx;
			this.color = color;
			this.size = size;
		}

		@Override
		// 크기(size)를 기준으로 오름차순 정렬하기 위함
		public int compareTo(Ball o) {
			return Integer.compare(this.size, o.size);
		}

	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int N = Integer.parseInt(br.readLine());
		Ball[] ball = new Ball[N];
		
		// 모든 공 정보를 입력받아 배열에 저장
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int color = Integer.parseInt(st.nextToken()) - 1;
			int size = Integer.parseInt(st.nextToken());
			ball[i] = new Ball(i, color, size);
		}
		
		// 모든 공을 크기 순으로 오름차순 정렬
		Arrays.sort(ball);
		
		int[] answerArray = new int[N]; // 각 공의 정답을 원래 순서대로 저장할 배열
		int totalSum = 0; // 현재까지 탐색한 공들의 전체 크기 누적 합
		int[] colorSum = new int[N]; // 색깔별 크기 누적 합
		
		int i = 0;
		// 정렬된 공들을 순회
		while (i < N) {
			// 크기가 같은 공들을 하나의 그룹으로 처리하기 위해 그룹의 끝 인덱스(j)를 찾음
			int j = i;
			while (j < N && ball[j].size == ball[i].size) {
				j++;
			}
			
			// Step 1: 현재 그룹(i ~ j-1)에 속한 모든 공들의 정답을 계산
			// 이 시점의 누적 합(totalSum, colorSum)은 현재 그룹의 공들보다 작은 공들만의 정보임
			for (int k = i; k < j; k++) {
				// 정답 = (나보다 작은 모든 공들의 합) - (나보다 작으면서 색깔이 같은 공들의 합)
				answerArray[ball[k].idx] = totalSum - colorSum[ball[k].color];
			}
			
			// Step 2: 현재 그룹의 공들의 정보를 누적 합에 반영
			// 다음 크기의 공들을 계산할 때 사용됨
			for (int k = i; k < j; k++) {
				totalSum += ball[k].size;
				colorSum[ball[k].color] += ball[k].size;
			}
			
			// 다음 탐색 위치로 이동
			i = j;
		}
		
		// 원래 입력 순서에 맞게 정답 출력
		for (int j = 0; j < N; j++) {
			sb.append(answerArray[j]).append("\n");
		}
		System.out.println(sb);
	}
}
