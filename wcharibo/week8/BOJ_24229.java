import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int T = Integer.parseInt(br.readLine());
        int[][] boards = new int[T][2];
        int answer = 0;

        for(int t = 0; t < T; t++){
            StringTokenizer st = new StringTokenizer(br.readLine());

            boards[t][0] = Integer.parseInt(st.nextToken());
            boards[t][1] = Integer.parseInt(st.nextToken());
        }

        if(T == 1){
            System.out.println(boards[0][1]);
            return;
        }

        Arrays.sort(boards, (a,b)->{
                if(a[0] == b[0]) return Integer.compare(a[1], b[1]);
                return Integer.compare(a[0], b[0]);
            });

        int start = boards[0][0];
        int end = boards[0][1];
        List<int[]> mergeds = new ArrayList<>();

        for(int i = 1; i < boards.length; i++){

            if(end < boards[i][0]){
                mergeds.add(new int[]{start, end});
                start = boards[i][0];
                end = boards[i][1];
            }else if(boards[i][1] < end){
                
            }else{
                end = boards[i][1];
            }

            if(i == boards.length - 1){
                mergeds.add(new int[]{start, end});
            }
        }

        boolean[] vis = new boolean[mergeds.size()];

        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);

        while(!q.isEmpty()){
            int cur = q.poll();
            answer = Math.max(answer, mergeds.get(cur)[1]);

            int jump = mergeds.get(cur)[1] - mergeds.get(cur)[0];

            for(int next =  cur + 1; next < mergeds.size() && mergeds.get(next)[0] <= mergeds.get(cur)[1] + jump; next++){
                if(!vis[next]){
                    answer = Math.max(answer, mergeds.get(next)[1]);
                    q.add(next);
                    vis[next] = true;
                    
                }
            }
        }

        System.out.println(answer);
    }
}
