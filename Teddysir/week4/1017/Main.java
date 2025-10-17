import java.util.*;
import java.io.*;

public class Main {

	static int N, C;
	static int[] house;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		house = new int[N];

		for (int i = 0; i < N; i++) {
			house[i] = Integer.parseInt(br.readLine());
		}

		Arrays.sort(house); // 이분탐색을 위한 정렬

		int lo = 1; // 최소거리 바로 인접.
		int hi = house[N - 1] - house[0] + 1; // 최대거리

		while (lo < hi) {

			int mid = (lo + hi) / 2;

			if (solution(mid) >= C) {
				lo = mid + 1;
			} else {
				hi = mid;
			}

		}

		System.out.println(lo - 1);
	}

	static int solution(int dist) {

		int count = 1;
		int lastLocate = house[0]; // 첫번째 집이 설치 위치

		for (int i = 0; i < N; i++) { // 지금 최소거리만큼 위치한곳에 설치
			int locate = house[i];

			if (locate - lastLocate >= dist) {
				count++;
				lastLocate = locate;
			}
		}

		return count;
	}

}
