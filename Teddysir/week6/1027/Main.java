import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		String[] str = spliter("12:55");
		

//		System.out.println(spliter("12:55"));
		
		System.out.println(str[0]);
		System.out.println(str[1]);
//        finish_hour = Integer.parseInt(video_len.substring(0,2)); // 동영상 길이 시간
//        finish_min = Intger.parseInt(video_len.substring(3,5)); // 동영상 길이 분 
	}
	
	static String[] spliter(String time) {
	
//		StringBuilder sb = new StringBuilder();
		
		String[] str = time.split(":");
		
		return str;
		
//		sb.append(hour).append(":").append(min);
//		return String.valueOf(sb);
		
	
//		String[] str = {hour, min};
//		return str;
		
		
	}

}
