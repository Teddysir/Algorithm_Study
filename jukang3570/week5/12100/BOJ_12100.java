import java.io.*;
import java.util.*;

public class Main {
    static int n;
    static int answer = 0;
    static int[][] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        arr = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, arr);
        System.out.println(answer);
    }

    // 깊이 5까지 탐색 (모든 경우의 수 구하기.)
    static void dfs(int depth, int[][] board) {
        if (depth == 5) {
            updateMax(board);
            return;
        }

        for (int dir = 0; dir < 4; dir++) {
            int[][] next = move(dir, board);
            dfs(depth + 1, next);
        }
    }

    // 한 방향으로 이동
    static int[][] move(int dir, int[][] board) {
        int[][] tmp = new int[n][n];
        for (int i = 0; i < n; i++) tmp[i] = Arrays.copyOf(board[i], n);

        for (int i = 0; i < n; i++) {
            LinkedList<Integer> list = new LinkedList<>(); //링크드 리스트 이용해서 shift 먼저 하기.
            /*
             * 링크드 리스트 이용한 이유가 set 기능 있어서 한번 써봣습니다. (배열 값 서로 바꾸는 명령어)
             */
            //쉬프트를 하고 합치는 쪽으로 접근.

            if (dir == 0) { // 왼쪽
                for (int j = 0; j < n; j++)  {
                	if (tmp[i][j] != 0) list.add(tmp[i][j]);
                }
                merge(list);
                for (int j = 0; j < n; j++) {
                	if(list.size() > j) tmp[i][j] = list.get(j);
                	else tmp[i][j] = 0;
                	
                }
            }

            else if (dir == 1) { // 오른쪽
                for (int j = n - 1; j >= 0; j--) { 
                	if (tmp[i][j] != 0) list.add(tmp[i][j]);
                }
                merge(list);
                for (int j = n - 1, idx = 0; j >= 0; j--, idx++) { //오른쪽은 거꾸로 두어야하기때문에 값 맞춰주기 위해서 idx 변수 사용했습니다.
                	if(list.size() > idx) tmp[i][j] = list.get(idx);
                	else tmp[i][j] = 0;
                }
            }

            else if (dir == 2) { // 위
                for (int j = 0; j < n; j++) {
                	if (tmp[j][i] != 0) list.add(tmp[j][i]);
                }
                merge(list);
                for (int j = 0; j < n; j++)  {
                	if(list.size() > j) tmp[j][i] = list.get(j);
                	else tmp[j][i] = 0;
                }
            }

            else { // 아래
                for (int j = n - 1; j >= 0; j--)  {
                	if (tmp[j][i] != 0) list.add(tmp[j][i]);
                }
                merge(list);
                for (int j = n - 1, idx = 0; j >= 0; j--, idx++)  {
                	if(list.size() > idx ) tmp[j][i] = list.get(idx);
                	else tmp[j][i] = 0;
                	
                }
            }
        }
        return tmp;
    }

    // 같은 수 병합
    /*
     * 가장 고민 많이 했던 부분이 한번 또 병합이 이루어지면 안되는데 (2244와 같이)
     * 왼쪽에서부터 업데이트를 하면 중복 병합이 되는 경우는 없어지니깐 이부분 해결
     */
    static void merge(LinkedList<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).equals(list.get(i + 1))) {
                list.set(i, list.get(i) * 2);
                list.remove(i + 1);
            }
        }
    }

    // 최대값 갱신
    static void updateMax(int[][] board) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                answer = Math.max(answer, board[i][j]);
            }
        }
    }
}
