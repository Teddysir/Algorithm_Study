import java.io.*;
import java.util.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int D = Integer.parseInt(st.nextToken());
		int P = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());
		int result = Integer.MAX_VALUE;
		
		if(D % P == 0 || D % Q == 0) {
			System.out.println(D);
			return;
		}
		
		int temp = Math.min(P, Q);
		Q = Math.max(P, Q);
		P = temp;
		
		int compensation = Q/P;
		
		int curP = D/P + 1;
		int curQ = 0;
		int cur = curP * P + curQ * Q;
		
		while(curP >= 0 && curQ >= 0) {
			result = Math.min(result , cur);
            
			if(cur == D) {
				break;
			}
			
			curQ++;
			curP-=compensation;
            
			while(curP * P + curQ * Q >= D) {
				curP--;
			}

			curP++;
			
			cur = curP * P + curQ * Q;
		}
		System.out.println(result);
	}
}
