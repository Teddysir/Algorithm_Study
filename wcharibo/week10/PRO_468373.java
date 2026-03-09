import java.util.*;
import java.io.*;

class Solution {
    static int answer = 0;
    static List<int[]>[] conns;
    
    public void choose(int cur, int k, boolean[] vis){
        if(cur == k){
            int cnt = 0;
            for(boolean v : vis) if(v) cnt++;
            answer = Math.max(answer, cnt);
            return;
        }
        
        for(int i = 0; i < 3; i++){
            Queue<Integer> q = new ArrayDeque<>();
            
            for(int j = 1; j < vis.length; j++){
                if(vis[j]) q.add(j);
            }
            
            boolean[] visN = new boolean[vis.length];
            
            while(!q.isEmpty()){
                int now = q.poll();
                visN[now] = true;
                
                for(int[] conn : conns[now]){
                    if(visN[conn[0]] || i != conn[1]) continue;
                    else{
                        q.add(conn[0]);
                        visN[conn[0]] = true;
                    }
                }
            }
            
            choose(cur + 1, k, visN);
        }
        
        
        
    }
    
    public int solution(int n, int infection, int[][] edges, int k) {

        conns = new ArrayList[n+1];
        
        for(int i = 0; i < n+1; i++){
            conns[i] = new ArrayList<>();
        }
        
        for(int [] edge : edges){
            conns[edge[0]].add(new int[]{edge[1], edge[2]-1});
            conns[edge[1]].add(new int[]{edge[0], edge[2]-1});
        }
        
        boolean[] vis = new boolean[n+1];
        vis[infection] = true;
        choose(0, k, vis);
        
        
        
        
        return answer;
    }
}
