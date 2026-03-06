import java.util.*;
import java.io.*;

class Main{
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for(int tc = 0; tc < T; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[] times = new int[N];
            List<Integer>[] graph = new ArrayList[N];
            int[] conns = new int[N];
            int built = 0;

            int answer = 0;


            st = new StringTokenizer(br.readLine());

            for(int i = 0; i < N; i++){
                graph[i] = new ArrayList<>();
                times[i] = Integer.parseInt(st.nextToken());
            }

            for(int i = 0; i < K; i++){
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken()) - 1;
                int d = Integer.parseInt(st.nextToken()) - 1;

                graph[s].add(d);
                conns[d]++;
            }

            int goal = Integer.parseInt(br.readLine()) - 1;

            PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) ->times[a]- times[b]);

            for(int i = 0; i < N; i++){
                if(conns[i] == 0) pq.add(i);
            }

            out:
            while(!pq.isEmpty()){
                int buildTime = times[pq.peek()];
                int qSize = pq.size();
                int cnt = 0;
                Set<Integer> visit = new HashSet<>(); 

                answer+=buildTime;

                while(cnt++ < qSize){
                    int cur = pq.poll();

                    times[cur] -= buildTime;

                    if(times[cur] == 0 && cur == goal){
                        System.out.println(answer);
                        break out;
                    }

                    if(times[cur] > 0){
                        visit.add(cur);
                    }else{
                        for(int d : graph[cur]){
                            conns[d]--;
                            if(conns[d] == 0){
                                visit.add(d);
                            }
                        }
                    }
                }

                for(int i : visit){
                    pq.add(i);
                }
            }
        }
    }
}
