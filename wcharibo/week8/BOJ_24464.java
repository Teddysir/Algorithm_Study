import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        long answer = 0;
        final long Big = 1000000007;
        
        long[] lunch = {1,1,1,1,1}; 
        
        
        for(int i = 1; i < N; i++) {
        	long[] prev = lunch.clone();
        	
        	lunch[0] = (prev[1] + prev[2] + prev[3] + prev[4])%Big;
        	lunch[1] = (prev[0] + prev[3] + prev[4])%Big;
        	lunch[2] = (prev[0] + prev[4])%Big;
        	lunch[3] = (prev[0] + prev[1])%Big;
        	lunch[4] = (prev[0] + prev[1] + prev[2])%Big;
        }
        
        for(int i = 0; i < lunch.length; i++) {
        	answer += lunch[i];
        	answer %= Big;
        }
        
        
        System.out.println(answer);
    }

}
