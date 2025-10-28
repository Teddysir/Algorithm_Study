/**
 * @Link https://www.acmicpc.net/problem/2138
 * @RunningTime 128ms
 * @Memory 17716kb
 * @Strategy 그리디 알고리즘 (Greedy Algorithm)
 * * i번째 스위치는 i-1, i, i+1 전구에만 영향을 준다.
 * * 이 특성을 이용하면, i-1번째 전구의 상태를 올바르게 맞출 수 있는 마지막 기회는 i번째 스위치를 누르는 것이다.
 * * 이를 바탕으로 그리디 전략을 사용할 수 있다: 왼쪽부터 순서대로, i-1번째 전구가 목표 상태와 다르면 i번째 스위치를 반드시 눌러야 한다.
 * * 하지만 이 전략은 첫 번째 전구에는 적용할 수 없다. 첫 번째 스위치를 누를지 말지 결정할 근거가 없기 때문이다.
 * * 따라서, 두 가지 경우의 수를 모두 시도한다.
 * * 1. 첫 번째 스위치를 누르지 않는 경우: 이 상태에서 그리디 전략을 끝까지 수행한다.
 * * 2. 첫 번째 스위치를 누르는 경우: 이 상태에서 그리디 전략을 끝까지 수행한다.
 * * 두 경우 중 목표 상태를 만들 수 있으면서, 스위치를 누른 횟수가 더 적은 쪽이 정답이 된다.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	static int N, answer = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		String str = br.readLine();
		
		// bulb[0]: 첫 스위치를 안 누른 경우, bulb[1]: 첫 스위치를 누른 경우
		boolean[][] bulb = new boolean[2][N]; 
		for (int i = 0; i < N; i++) {
			bulb[0][i] = str.charAt(i) == '1';
			bulb[1][i] = str.charAt(i) == '1';
		}
		
		// 시나리오 2: 첫 번째 스위치(인덱스 0)를 누른 상태를 만듦. (0, 1번 전구가 바뀜)
		for (int i = 0; i < 2; i++) {
			bulb[1][i] = !bulb[1][i];
		}
		
		// 목표 전구 상태를 입력받음
		str = br.readLine();
		boolean[] dest = new boolean[N];
		for (int i = 0; i < N; i++) {
			dest[i] = str.charAt(i) == '1';
		}
		
		// 2가지 시나리오(i=0, i=1)에 대해 각각 그리디 알고리즘 수행
		for (int i = 0; i < 2; i++) {
			// count: 스위치를 누른 횟수. 시나리오 1(i=1)은 이미 한 번 눌렀으므로 1로 시작.
			int count = i;
			
			// 1번부터 N-2번 스위치까지 순서대로 결정 (j는 스위치 인덱스)
			for (int j = 1; j < N - 1; j++) {
				// 이전 전구(j-1)가 목표 상태와 같은지 확인
				if (bulb[i][j - 1] == dest[j - 1]) {
					// 같다면, j번째 스위치를 누를 필요가 없음
					continue;
				} else {
					// 다르다면, j-1번째 전구를 맞추기 위해 j번째 스위치를 반드시 눌러야 함
					bulb[i][j - 1] = !bulb[i][j - 1];
					bulb[i][j] = !bulb[i][j];
					bulb[i][j + 1] = !bulb[i][j + 1];
					++count;
				}
			}
			
			// 마지막 스위치(N-1) 처리: N-2번째 전구가 목표와 다르면 마지막 스위치를 눌러야 함
			if (bulb[i][N - 2] != dest[N - 2]) {
				bulb[i][N - 2] = !bulb[i][N - 2];
				bulb[i][N - 1] = !bulb[i][N - 1];
				++count;
			}
			
			// 모든 그리디한 선택이 끝난 후, 최종 상태가 목표 상태와 일치하는지 확인
			if (isEqual(bulb[i], dest)) {
				// 일치한다면, 최소 횟수를 갱신
				answer = Math.min(answer, count);
			}
		}
		
		// 두 시나리오 모두 실패했다면 answer는 초기값 그대로이므로 -1 출력
		System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
	}

	/**
	 * 두 전구 상태 배열이 완전히 일치하는지 확인하는 함수
	 * @param bulb 현재 전구 상태 배열
	 * @param dest 목표 전구 상태 배열
	 * @return 일치하면 true, 아니면 false
	 */
	private static boolean isEqual(boolean[] bulb, boolean[] dest) {
		for (int i = 0; i < N; i++) {
			if (bulb[i] != dest[i]) {
				return false;
			}
		}
		return true;
	}
}
