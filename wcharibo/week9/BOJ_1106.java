import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int C = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        
        int[] dp = new int[C+200];
        int[][] costs = new int[N][2];
        
        Arrays.fill(dp, 2000000);
        dp[0] = 0;
        
        for(int i = 0 ; i < N ; i++) {
        	st = new StringTokenizer(br.readLine());
        	costs[i][0] = Integer.parseInt(st.nextToken());
        	costs[i][1] = Integer.parseInt(st.nextToken());
        }
        
        
        for(int[] cost : costs) {
        	for(int i = cost[1]; i < dp.length; i++) {
        		dp[i] = Math.min(dp[i], cost[0] + dp[i - cost[1]]);        			
        	}
        }
        
        int answer = dp[C];
        
        for(int i = C + 1; i < dp.length; i++) {
        	answer = Math.min(answer, dp[i]);
        }
        
        System.out.println(answer);
    }
}
