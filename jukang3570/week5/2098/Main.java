import java.io.*;
import java.util.*;

public class Main {
    static final int INF = 1_000_000_000; // 초기값 설정.
    static int n;
    static int[][] arr;
    static int[][] dp;
    /*
     * 문제 풀이에 대한 접근
     * dp[mask][u]일때
     * mask 는 방문 상태
     * u는 현재 위치 일때 최소 비을 의미한다.
     * 즉 dp는 부분 문제의 결괄르 저장한다.
     */
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());
        arr = new int[n][n]; //가중치 행렬 (각 노드의 정보가 담겨진 행렬) 
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) arr[i][j] = Integer.parseInt(st.nextToken());
        }

        int max = (1 << n);  // n개의 최대 비트 값지정.
        dp = new int[max][n];
        for (int i = 0; i < max; i++) Arrays.fill(dp[i], INF); //최대 가중치 초기화.

        int start = 0;              
        dp[1 << start][start] = 0; //start 부분 방문했다고 새기기.
        
        //모든 경우의 수를 비트마스크로 표현해서 현재까지 일부 도시들을 방문했을 때의 최소 비용을 구하는 로직.
        for (int mask = 0; mask < max; mask++) {
            for (int u = 0; u < n; u++) {
                int curr = dp[mask][u]; 
                if (curr == INF) continue;
                // u에서 v로
                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue; 
                    if (arr[u][v] == 0) continue;     
                    int nextmask = mask | (1 << v); //현재 방문 상태에서 다음방문 도시상태를 추가.
                    int nextcost = curr + arr[u][v]; //방문 하려는 도시의 비용.
                    if (nextcost < dp[nextmask][v]) dp[nextmask][v] = nextcost;
                }
            }
        }

        int ans = INF;
        int all = max - 1; //전체 집합 모든 도시를 방문한 상태.
        for (int u = 0; u < n; u++) { //마지막 방문 도시의 모든 경우의 수를 탐색.
            if (dp[all][u] == INF || arr[u][start] == 0) continue; //도달 할 수 없거나 현재 상태일 경우의 예외처리.
            ans = Math.min(ans, dp[all][u] + arr[u][start]);
        }
        System.out.println(ans == INF ? 0 : ans);
    }
}
