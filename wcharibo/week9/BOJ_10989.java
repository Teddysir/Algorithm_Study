import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
    
        int N = Integer.parseInt(br.readLine());

        int[] nums = new int[10001];

        for(int i = 0; i < N; i++){
            nums[Integer.parseInt(br.readLine())]++;
        }

        int start = 1;

        while(start <= 10000){
            if(nums[start] != 0) {
                for(int i = 0; i < nums[start]; i++){
                    sb.append(start).append("\n");
                }
                start++;
            }else{
                start++;
            }

        }

        System.out.println(sb.toString());
    }
}
