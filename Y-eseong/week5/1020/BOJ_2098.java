import java.io.*;
import java.util.*;

public class BOJ_2098 {

    static int n, all, big = 999999999;
    static int[][] map, dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        all = (1 << n) - 1;
        dp = new int[n][all];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        int answer = tsp(0, 1);
        sb.append(answer);
        System.out.println(sb);
    }

    private static int tsp(int x, int check) {

        if (check == all) {
            if (map[x][0] == 0)
                return big;
            else
                return map[x][0];
        }

        if (dp[x][check] != -1)
            return dp[x][check];

        dp[x][check] = big;

        for (int i = 0; i < n; i++) {
            int next = check | (1 << i);
            if (map[x][i] == 0 || (check & (1 << i)) != 0)
                continue;
            dp[x][check] = Math.min(dp[x][check], tsp(i, next) + map[x][i]);
        }

        return dp[x][check];
    }
}
