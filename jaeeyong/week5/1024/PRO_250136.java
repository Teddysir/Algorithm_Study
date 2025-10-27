import java.util.*;

class Solution {
	public int solution(int[][] land) {
		int answer = 0;

		int rowLen = land.length;
		int colLen = land[0].length;

    int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		Queue<int[]> queue = new ArrayDeque<>();
		int[][] visited = new int[rowLen][colLen];
    
		HashMap<Integer, Integer> loafSize = new HashMap<>();
		int loafNum = 1;

    // 여기가 땅에 석유 덩어리가 어떻게 있는지 찾아보는 코드 (일반적인 BFS)
		for (int col = 0; col < colLen; col++) {
			for (int row = 0; row < rowLen; row++) {

				if (land[row][col] == 1 && visited[row][col] == 0) {

					int count = 1;
					visited[row][col] = loafNum;
					queue.offer(new int[] { row, col });

					while (!queue.isEmpty()) {
						int[] current = queue.poll();
						for (int[] d : dir) {
							int nr = current[0] + d[0];
							int nc = current[1] + d[1];

							if (0 <= nr && nr < rowLen && 0 <= nc && nc < colLen && visited[nr][nc] == 0 && land[nr][nc] == 1) {
								count++;
								visited[nr][nc] = loafNum;
								queue.offer(new int[] { nr, nc });
							}

						}
					}
          // 임의로 정한 1씩 증가하는 덩어리 인덱스를 key로, 덩어리의 크기를 value로 가지는 키, 값 쌍을 HashMap에 저장
					loafSize.put(loafNum++, count);

				}
			}
		}

		HashSet<Integer> contain;

    // 여기가 각 열마다 어떤 석유 덩어리가 걸쳐 있는지 보고, 점수를 더하는 코드
		for (int col = 0; col < colLen; col++) {
      // 매 열 탐색마다 새로운 HashSet을 만들기
			contain = new HashSet<>();
			int count = 0;
			for (int row = 0; row < rowLen; row++) {
        // HashSet에 들어있지 않은 덩어리를 만나면, 해당 덩어리의 크기를 점수에 더해주고 해당 덩어리를 HashSet에 넣어주기
				if (visited[row][col] != 0 && !contain.contains(visited[row][col])) {
					count += loafSize.get(visited[row][col]);
					contain.add(visited[row][col]);
				}
        
			}
      // 하나의 열 탐색이 모두 끝나면, 지금까지의 최댓값과 비교해서 최댓값 갱신
			answer = Math.max(answer, count);
		}

		return answer;
    
	}
}
