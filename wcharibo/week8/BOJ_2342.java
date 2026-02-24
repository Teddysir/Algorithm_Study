import java.util.*;
import java.io.*;

class Main{

    public static class Pair{
        int x;
        int y;
        int power;

        Pair(int x, int y, int power){
            this.x = x;
            this.y = y;
            this.power = power;
        }
    }

    public static void main(String[] args) throws Exception{

        int[][] cost = {{0,2,2,2,2},{0,1,3,4,3},{0,3,1,3,4},{0,4,3,1,3},{0,3,4,3,1}};

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int next = Integer.parseInt(st.nextToken());
        int answer = Integer.MAX_VALUE;

        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(0, 0, 0));

        while(true){

            int[][] powers = new int[5][5];

            for(int i = 0; i < 5; i++){
                Arrays.fill(powers[i], Integer.MAX_VALUE);
            }

            while(!q.isEmpty()){
                Pair cur = q.poll();

                if(next != cur.y && powers[next][cur.y] > cur.power + cost[cur.x][next]){
                    powers[next][cur.y] = cur.power + cost[cur.x][next];
                }

                if(next != cur.x && powers[cur.x][next] > cur.power + cost[cur.y][next]){
                    powers[cur.x][next] = cur.power + cost[cur.y][next];
                }
            }

            for(int i = 0; i < powers.length; i++){
                for(int j = i + 1; j < powers[i].length; j++){
                    if(powers[i][j] != Integer.MAX_VALUE || powers[j][i] != Integer.MAX_VALUE){
                        q.add(new Pair(i, j, Math.min(powers[i][j], powers[j][i])));
                    }
                }
            }

            next = Integer.parseInt(st.nextToken());
            
            if(next == 0){

                for(int i[] : powers){
                    for(int j : i){
                        answer = Math.min(answer, j);
                    }
                }

                break;
            }
        }

        System.out.println(answer);
    }

}
