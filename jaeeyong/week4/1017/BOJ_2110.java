/**
 * @Link https://www.acmicpc.net/problem/2110
 * @RunningTime 272ms
 * @Memory 39400kb
 * @Strategy 파라메트릭 서치
 * * 결정 문제: "공유기 사이의 최소 거리를 'mid'로 할 때, C개의 공유기를 설치할 수 있는가?"
 * * 탐색 범위: 최소 거리 1 (left) ~ 최대 거리 (가장 끝 집 - 가장 첫 집) (right)
 * 1. 집들의 좌표(H)를 오름차순으로 정렬한다.
 * 2. `left`, `right`를 설정하고 이분 탐색.
 * 3. `mid`를 '가장 인접한 두 공유기 사이의 최소 거리' 후보로 설정한다.
 * 4. 정렬된 집 배열을 순회하며 첫 번째 집에 공유기를 설치하고, `mid` 거리 이상 떨어진 다음 집에 순서대로 공유기를 설치(Greedy)해본다.
 * 5. 설치된 공유기 개수가 C개 이상이면, `mid`는 가능한 거리이므로 더 큰 거리를 탐색하기 위해 `left = mid + 1`로 범위를 좁힌다.
 * 6. 설치된 공유기 개수가 C개 미만이면, `mid` 거리가 너무 넓어 불가능하므로 거리를 좁히기 위해 `right = mid - 1`로 범위를 좁힌다.
 * 7. 이 과정을 반복하여 가능한 `mid` 중 최댓값을 찾는다.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken()); // 집의 개수
		int C = Integer.parseInt(st.nextToken()); // 공유기의 개수
		int[] H = new int[N]; // 집의 좌표를 저장할 배열
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int X = Integer.parseInt(st.nextToken());
			H[i] = X;
		}
		// 이분 탐색을 위해 집의 좌표를 오름차순으로 정렬
		Arrays.sort(H);
		
		// 이분 탐색 범위 설정
		long left = 1; // 가능한 최소 거리
		long right = H[N - 1] - H[0]; // 가능한 최대 거리
		long answer = 0; // 최종 결과 (최대 거리)
		
		// left가 right보다 커지기 전까지 이분 탐색 수행
		while (left <= right) {
			// 현재 탐색할 거리(중간값)
			long mid = left + (right - left) / 2;
			
			// mid 거리를 유지하며 공유기를 몇 개 설치할 수 있는지 확인
			// 첫 번째 집에는 무조건 설치하므로, 그 이후에 설치되는 공유기 수를 센다.
			long count = 0;
			int i = 0; // i: 마지막으로 공유기가 설치된 집의 인덱스
			// 마지막으로 설치된 집(i) 이후로 탐색
			while (i < N) {
				int j = i; // j: 다음에 공유기를 설치할 집을 찾기 위한 인덱스
				
				// i번 집으로부터 mid 이상 떨어진 가장 가까운 집 j를 찾음
				while (j < N) {
					if (H[j] - H[i] >= mid) {
						count++; // 추가 공유기 설치
						break;   // 집을 찾았으므로 내부 루프 탈출
					}
					j++;
				}
				// 다음 탐색을 위해 마지막 설치 위치를 j로 점프
				// 만약 j를 못찾았다면 j는 N이 되고, 바깥 while 루프가 종료됨
				i = j;
			}
			
			// 설치된 추가 공유기 수가 C-1개 이상인가? (첫 번째 집 포함 총 C개)
			if (count >= C - 1) {
				// C개 이상 설치 가능하다면, mid는 정답이 될 수 있음
				// 더 넓은 거리가 가능한지 탐색하기 위해 left를 늘림
				answer = mid;
				left = mid + 1;
			} else {
				// C개 미만으로 설치된다면, mid 거리가 너무 넓다는 의미
				// 거리를 줄여야 하므로 right를 줄임
				right = mid - 1;
			}
		}
		System.out.println(answer);
	}
}
