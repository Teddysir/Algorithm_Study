import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
    
        int N = Integer.parseInt(st.nextToken());
        int goal = Integer.parseInt(st.nextToken());
        int max = Math.min(Math.max(N, goal), 100000);

        int[] dist = new int[max * 2 + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        PriorityQueue<Integer> q = new PriorityQueue<>((a,b)->{
            return dist[a] - dist[b];
        });

        dist[N] = 0;
        q.add(N);

        while(!q.isEmpty()){          
            int cur = q.poll();

            if(cur == goal){
                System.out.println(dist[goal]);
                return;
            }

            int[] nexts = {cur - 1, cur + 1, cur * 2};

            for(int next : nexts){
                if(0 <= next && next <= max * 2){
                    if(cur * 2 == next){
                        if(dist[next] > dist[cur]){
                            dist[next] = dist[cur];
                            q.add(next);
                        }                
                    }else{
                        if(dist[next] > dist[cur] + 1){
                            dist[next] = dist[cur] + 1;
                            q.add(next);
                        }
                    }
                }
            }
        }
    }
}
