import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int X = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());
        
        if(X%8 == 0 && T >= X/8) {
        	System.out.println(1);
        	System.out.println(T - X/8 + " " + 8);
        }else if(X%4 == 0 && T >= X/4) {
        	System.out.println(1);
        	System.out.println(T - X/4 + " " + 4);
        }else if(T >= X) {
        	System.out.println(1);
        	System.out.println(T - X + " " + 1);
        }else if(X%8%4==0 && T >= X/8 + X%8/4) {
        	System.out.println(2);
        	System.out.println(T - X/8 - (X%8)/4 + " " + 8);
        	System.out.println(T - (X%8)/4 + " " + 4);
        }else if(T >= X/8 + X%8) {
        	System.out.println(2);
        	System.out.println(T - X/8 - X%8 + " " + 8);
        	System.out.println(T - (X%8) + " " + 1);
        }else if(T >= X/4 + X%4) {
        	System.out.println(2);
        	System.out.println(T - X/4 - X%4 + " " + 4);
        	System.out.println(T - X%4 + " " + 1);
        }else if(T >= X/8 + X%8/4 + X%8%4) {
        	System.out.println(3);
        	System.out.println(T - X/8 - (X%8)/4 - ((X%8)%4) + " " + 8);
        	System.out.println(T - (X%8)/4 - ((X%8)%4) + " " + 4);
        	System.out.println(T - ((X%8)%4) + " " + 1);
        }else {
        	System.out.println(-1);
        }
        
    }

}
