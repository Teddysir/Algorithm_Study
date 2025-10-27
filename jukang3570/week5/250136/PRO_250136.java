import java.util.*;

class Solution {
    static int rank; // 석유 구역별을 구분하기 위한 변수.
    static HashMap<Integer,Integer> hmap; //각 석유 구역별의 해시 맵.
    static boolean[][] visited; //bfs 탐색시 방문 표시를 하기 위한 배열.
    static int row, col; //맵의 행과 열.
    static int[][] arr; //석유 번호들을 각 인덱스로 구별한 배열.
    
    static class node { //석유 정보를 보관하기 위한 노드 클래스.
    	int r, c;

    	public node(int r, int c) {
    		super();
    		this.r = r;
    		this.c = c;
    	}
    	
    }
    
    public int solution(int[][] land) {
        int answer = 0;
        hmap = new HashMap<Integer, Integer>(); //각 rank 별 면적을 저장하기 위한 해시 맵.
        row = land.length;
        col = land[0].length;
        rank = 1;
        arr = new int[row][col];
        visited = new boolean[row][col];
        
        for(int i=0; i<row; i++) {
            for(int j=0; j<col ; j++) {
                if(land[i][j] == 1 && !visited[i][j] )
                    bfs(i,j, land);
            }
        }
        
        for(int i=0; i<col; i++) {
            //중복 카운팅 방지를 하기 위한 각 구역별로 방문했는지 표시하는 배열
            //구역이 가장 많을 최대 경우의 수 (row * col / 2 ) + 1 로 check
        	boolean[] check = new boolean[ (row*col) / 2 + 1];
        	int cnt = 0;
        	for(int j=0; j<row; j++) {
        		if(arr[j][i] != 0 && !check[arr[j][i]] ) {
        			check[arr[j][i]] = true;
        			cnt += hmap.get(arr[j][i]); //해당 구역을 만난다? 그 구역 방문 처리하고 그 구역에 해당하는 값 가져오기
        		}
        	}
        	answer = Math.max(cnt, answer);
        }
        
        return answer;
    }
    /*
     * 구역별 석유를 탐색하기 위한 bfs 로직
     * bfs 구역별로 arr 2차원 맵 변수에 저장
     * 
     */


    static void bfs(int r, int c, int[][] land) {
        Queue<node> q = new ArrayDeque<>();
        int[] dr = { 0, 0, -1, 1};
        int[] dc = { -1, 1, 0, 0};
        q.add(new node(r, c));
        visited[r][c] = true;
        arr[r][c] = rank;
        int cnt =1;
        while(!q.isEmpty()) {
        	node curr = q.poll();
        	for(int i=0; i<4; i++) {
        		int nr = curr.r + dr[i];
        		int nc = curr.c + dc[i];
        		if(nr <0 || nr >= row || nc < 0 || nc >= col ) continue;
        		if(!visited[nr][nc] && land[nr][nc] != 0 ) {
        			cnt++;
        			visited[nr][nc] = true;
        			arr[nr][nc] = rank;
        			q.add(new node(nr,nc));
        		}
        	}
        }
        //         for(int i=0; i<row; i++) {
        //     for(int j=0; j<col; j++) {
        //         System.out.print(arr[i][j]+ " ");
        //     }
        //     System.out.println();
        // }
        // System.out.println();
        
        
        hmap.put(rank, cnt); //해당 rank 가 몇 번에 있는지 해시 맵에 저장.
        rank++;
    }
}




