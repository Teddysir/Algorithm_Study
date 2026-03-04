import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int[] liquids = new int[N];
        
        for(int i = 0; i < N; i++) {
        	liquids[i] = Integer.parseInt(st.nextToken()); 
        }
        
        Arrays.sort(liquids);
        
        int front = 0;
        int back = N - 1;
        int sumMin = Integer.MAX_VALUE;
        int[] answer = new int[2];
        
        while(front < back) {
        	
        	int sum = liquids[front] + liquids[back];
        	
        	if(sum == 0) {
        		System.out.println(liquids[front] + " " + liquids[back]);
        		return;
        	}else if(sum < 0) {
        		if(Math.abs(sum) < sumMin) {
        			sumMin = Math.abs(sum);
        			answer[0] = front;
        			answer[1] = back;
        		}
        		
        		front++;
        	}else{
        		if(sum < sumMin) {
        			sumMin = sum;
        			answer[0] = front;
        			answer[1] = back;
        		}
        		
        		back--;
        	}
        }
        
        System.out.println(liquids[answer[0]] + " " + liquids[answer[1]]);
        
    }
}
