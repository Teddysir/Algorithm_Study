f(firstRule < mid) {
        		high = mid - 1;
        		continue;
        	}
        	
        	int cnt = mid;
        	boolean flag = false;
        	
        	for(int paper : papers) {
        		if(cnt > 0 && paper > mid) cnt--;
        		else if(paper > mid) {
        			flag = true;
        			break;
        		}
        	}
        	
        	if(flag) {
        		low = mid + 1;
        	}else {
        		answer = mid;
        		break;
        	}
        }
        
        System.out.println(answer);
        
    }
}import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] papers = new int[N];
        int low = 0;
        int high = 0;
        int answer = 0;
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        for(int i = 0; i < N; i++) {
        	papers[i] = Integer.parseInt(st.nextToken());
        	high = Math.max(high, papers[i]);
        }
        
        while(low <= high) {
        	int mid = low + (high - low)/2;
        	int firstRule = 0;
        	
        	for(int paper : papers) {
        		if(mid <= paper) firstRu:wq
i:wqq
:q
[200~import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] papers = new int[N];
        int low = 0;
        int high = 0;
        int answer = 0;
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        for(int i = 0; i < N; i++) {
        	papers[i] = Integer.parseInt(st.nextToken());
        	high = Math.max(high, papers[i]);
        }
        
        while(low <= high) {
        	int mid = low + (high - low)/2;
        	int firstRule = 0;
        	
        	for(int paper : papers) {
        		if(mid <= paper) firstRule++;
        	}
        	
        	if(firstRule < mid) {
        		high = mid - 1;
        		continue;
        	}
        	
        	int cnt = mid;
        	boolean flag = false;
        	
        	for(int paper : papers) {
        		if(cnt > 0 && paper > mid) cnt--;
        		else if(paper > mid) {
        			flag = true;
        			break;
        		}
        	}
        	
        	if(flag) {
        		low = mid + 1;
        	}else {
        		answer = mid;
        		break;
        	}
        }
        
        System.out.println(answer);
        
    }
}

