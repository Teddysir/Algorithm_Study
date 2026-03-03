import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int[] nums = new int[N+1];
        
        st = new StringTokenizer(br.readLine());
        
        for(int i = 0; i < N; i++) {
        	nums[i] = Integer.parseInt(st.nextToken());
        }
        
        int left = 0;
        int right = 0;
        int sum = nums[0];
        int minLength = Integer.MAX_VALUE;
        
        while(left <= right && right < N) {
        	
        	if(sum >= S) {
        		minLength = Math.min(minLength, right - left + 1);
        		sum -= nums[left++];
        	}else{
        		sum += nums[++right];
        	}
        }
        
        if(minLength == Integer.MAX_VALUE) minLength = 0;
        
        System.out.println(minLength);
    }
}
