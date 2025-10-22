import java.io.*;
import java.util.*;

public class Main {
	//map의 크기
	static short N;
	//전역 최대값
	static short max = 0;
	//보드
	static short[][] map;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Short.parseShort(br.readLine());
		map = new short[N][N];
		
		for(short i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(short j = 0; j < N; j++) {
				map[i][j] = Short.parseShort(st.nextToken());
			}
		}
		//중복 순열 생성
		makeSubset((short) 0);

		System.out.println(max);
	}

	private static void makeSubset(short cur) {
		//백트래킹
		short tempResult = 0;
		for(short i = 0; i < N; i++) {
			for(short j = 0; j < N; j++) {
				tempResult = (short) Math.max(tempResult, map[i][j]);
			}
		}
		if(tempResult * (1 << (5 - cur)) <= max) return;
		
		//중복 순열 생성 완료했으면?
		if(cur == 5) {
			//현재 map 최대값 저장할 변수
			short temp = 0;
			
			//최대값 찾기
			for(short i = 0; i < N; i++) {
				for(short j = 0; j < N; j++) {
					temp = (short) Math.max(temp, map[i][j]);
				}
			}
			
			//전역 최대값 반영
			max = (short) Math.max(temp, max);
			return;
		}
		
		// map움직이고 다시 되돌리기 위해 기존에 위치 정보 저장 start
		short[][] temp = new short[N][N];
		for(short i = 0; i < N; i++) {
			for(short j = 0; j< N; j++) {
				temp[i][j] = map[i][j];
			}
		}
		//end
		
		//4방향 탐색 start
		for(short i = 0; i < 4; i++) {
			//선택한 방향으로 합치기 
			move(i);
			//백트래킹
			
			//중복순열 재귀 DFS
			makeSubset((short) (cur + 1));		
			//되돌리기
			for(short j = 0; j < N; j++) {
				for(short k = 0; k < N; k++) {
					map[j][k] = temp[j][k];
				}
			}
		}
		//end
		
	}

	private static void move(short dir) {
		//0:상, 1:하, 2:좌, 3:우
		if(dir == 0) {
			//반복되는 거라 0만 구현하겠습니다.
			for(short i = 0; i < N; i++) {
				//계산되는 배열 
				short[] temp = new short[N];
				
				for(short j = 0, idx = 0; j < N; j++) {
					//0이라면 pass
					if(map[j][i] == 0) {
						continue;
					}else {
						//현재 인덱스에 값이 있고 그 값이 map과 다르다면 다음 인덱스에 값을 넣어주기
						if(temp[idx] != 0 && temp[idx] != map[j][i]) {
							temp[++idx] = map[j][i];
						// 현재 인덱스에 값이 있고 (0인 경우 위에서 map이 0이면 pass해서 안 걸림) map과 같은 값이면 현재 인덱스에 값을 더해주고 인덱스++
						}else if(temp[idx] == map[j][i]) {
							temp[idx++] += map[j][i];
						// 현재 인덱스에 값이 없으면 현재 인덱스에 값 넣기
						}else {
							temp[idx] = map[j][i];
						}
					}
				}
				
				//계산한 배열 map에 반영
				for(short j = 0; j < N; j++) {
					map[j][i] = temp[j];
					
				}
			}
		}else if(dir == 1) {
			for(short i = 0; i < N; i++) {
				short[] temp = new short[N];
				
				for(short j = (short) (N - 1), idx = (short) (N - 1); j >= 0; j--) {
					if(map[j][i] == 0) {
						continue;
					}else {
						if(temp[idx] != 0 && temp[idx] != map[j][i]) {
							temp[--idx] = map[j][i];
						}else if(temp[idx] == map[j][i]) {
							temp[idx--] += map[j][i];
						}else {
							temp[idx] = map[j][i];
						}
					}
				}

				for(short j = 0; j < N; j++) {
					map[j][i] = temp[j];
					
				}
			}
		}else if(dir == 2) {
			for(short i = 0; i < N; i++) {
				short[] temp = new short[N];
				
				for(short j = 0, idx = 0; j < N; j++) {
					if(map[i][j] == 0) {
						continue;
					}else {
						if(temp[idx] != 0 && temp[idx] != map[i][j]) {
							temp[++idx] = map[i][j];
						}else if(temp[idx] == map[i][j]) {
							temp[idx++] += map[i][j];
						}else {
							temp[idx] = map[i][j];
						}
					}
				}
				for(short j = 0; j < N; j++) {
					map[i][j] = temp[j];
					
				}
			}
		}else {
			for(short i = 0; i < N; i++) {
				short[] temp = new short[N];
				
				for(short j = (short) (N - 1), idx = (short) (N -1); j >= 0; j--) {
					if(map[i][j] == 0) {
						continue;
					}else {
						if(temp[idx] != 0 && temp[idx] != map[i][j]) {
							temp[--idx] = map[i][j];
						}else if(temp[idx] == map[i][j]) {
							temp[idx--] += map[i][j];
						}else {
							temp[idx] = map[i][j];
						}
					}
				}

				for(short j = 0; j < N; j++) {
					map[i][j] = temp[j];
					
				}
			}
		}
	}
}
