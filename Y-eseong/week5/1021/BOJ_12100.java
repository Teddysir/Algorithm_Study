import java.io.*;
import java.util.*;

public class BOJ_12100 {

    static int n, answer;
    static int[][] map_origin, map;
    // 상하좌우
    static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    // 방향 조합?순열? 넣어두기 용도
    static int[] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        n = Integer.parseInt(br.readLine());
        map_origin = new int[n][n];
        answer = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map_origin[i][j] = Integer.parseInt(st.nextToken());
                answer = Integer.max(answer, map_origin[i][j]);
            }
        }
        arr = new int[5];
        comb(5, 0);
        sb.append(answer);
        System.out.println(sb);
    }

    // 총 경우 : 4가지 방향 * 5번 = 4^5 = 1024번
    private static void comb(int s, int k) {
        if (s == k) {
            map = new int[n][n];
            for (int i = 0; i < n; i++) {
                map[i] = Arrays.copyOf(map_origin[i], n);
            }
            answer = Integer.max(answer, game());
            return;
        }

        for (int i = 0; i < 4; i++) {
            arr[k] = i;
            comb(s, k + 1);
        }
    }

    private static int game() {
        for (int z : arr) {
            if (z == 0) {
                for (int j = 0; j < n; j++) {
                    int temp = -1;
                    int index = 0;
                    for (int i = 0; i < n; i++) {
                        if (map[i][j] == 0)
                            continue;
                        if (temp != map[i][j]) {
                            temp = map[i][j];
                            map[index++][j] = map[i][j];
                            continue;
                        }
                        if (temp == map[i][j]) {
                            index--;
                            temp = -1;
                            map[index++][j] = map[i][j] * 2;
                        }
                    }
                    for (int i = index; i < n; i++) {
                        map[i][j] = 0;
                    }
                }
            } else if (z == 1) {
                for (int j = 0; j < n; j++) {
                    int temp = -1;
                    int index = n - 1;
                    for (int i = n - 1; i >= 0; i--) {
                        if (map[i][j] == 0)
                            continue;
                        if (temp != map[i][j]) {
                            temp = map[i][j];
                            map[index--][j] = map[i][j];
                            continue;
                        }
                        if (temp == map[i][j]) {
                            index++;
                            temp = -1;
                            map[index--][j] = map[i][j] * 2;
                        }
                    }
                    for (int i = index; i >= 0; i--) {
                        map[i][j] = 0;
                    }
                }
            } else if (z == 2) {
                for (int i = 0; i < n; i++) {
                    int temp = -1;
                    int index = 0;
                    for (int j = 0; j < n; j++) {
                        if (map[i][j] == 0)
                            continue;
                        if (temp != map[i][j]) {
                            temp = map[i][j];
                            map[i][index++] = map[i][j];
                            continue;
                        }
                        if (temp == map[i][j]) {
                            index--;
                            temp = -1;
                            map[i][index++] = map[i][j] * 2;
                        }
                    }
                    for (int j = index; j < n; j++) {
                        map[i][j] = 0;
                    }
                }
            } else if (z == 3) {
                for (int i = 0; i < n; i++) {
                    int temp = -1;
                    int index = n - 1;
                    for (int j = n - 1; j >= 0; j--) {
                        if (map[i][j] == 0)
                            continue;
                        if (temp != map[i][j]) {
                            temp = map[i][j];
                            map[i][index--] = map[i][j];
                            continue;
                        }
                        if (temp == map[i][j]) {
                            index++;
                            temp = -1;
                            map[i][index--] = map[i][j] * 2;
                        }
                    }
                    for (int j = index; j >= 0; j--) {
                        map[i][j] = 0;
                    }
                }
            }
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                max = Integer.max(max, map[i][j]);
            }
        }

        return max;
    }
}
