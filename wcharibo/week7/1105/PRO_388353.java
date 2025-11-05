import java.util.*;

class Solution {
    static int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    public int solution(String[] storage, String[] requests) {
        int answer = 0;
        int sizeR = storage.length;
        int sizeC = storage[0].length();
        //빈칸을 저장할 2차원 배열, 테두리를 -1로 패딩 처리해주기 위해 size+2 크기로 배열 선언
        int[][] wareHouse = new int[sizeR + 2][sizeC + 2];
        //각 알파벳 별로 2차원 배열의 좌표 저장을 하기 위한 리스트 배열(느리지만 삭제에 용이함) 
        List<int[]>[] containers = new List[26];
        
        for(int i = 0; i < containers.length; i++){
            containers[i] = new ArrayList<int[]>();
        }
        
        for(int i = 0; i < storage.length; i++){
            for(int j = 0; j < storage[i].length(); j++){
                //알파벳 - 'A' 해주면 A - 0, B - 1, C -2 ... Z - 25 됨
                containers[storage[i].charAt(j) - 'A'].add(new int[]{i + 1,j + 1});
            }
        }
        
        //2차원 배열 1로 초기화
        for(int[] i : wareHouse) Arrays.fill(i, 1);
        //테두리 -1 두르기
        for(int i = 0; i <= sizeR + 1; i++){
            wareHouse[i][0] = wareHouse[i][sizeC+1] = -1;
        }
        for(int i = 0; i <= sizeC + 1; i++){
            wareHouse[0][i] = wareHouse[sizeR+1][i] = -1;
        }
        
        for(String request : requests){
            //만약 명령이 "BB"와 같이 크기가 1보다 크다면
            if(request.length() > 1){
                //해당 알파벳 리스트에 있는 모든 좌표 0으로 변경
                for(int [] i : containers[request.charAt(0) - 'A']){
                    wareHouse[i[0]][i[1]] = 0;
                }
                //해당 리스트 크기 0으로 
                containers[request.charAt(0) - 'A'].clear();
            }else{
                //리스트에서 조건에 맞는(테두리랑 연결되어 있는) 컨테이너 출고 처리해줄 텐데 삭제해도 문제가 발생하지 않도록 뒤에서부터 접근
                int size = containers[request.charAt(0) - 'A'].size();
                List<int[]> willDelete = new ArrayList<>();
                
                for(int i = size - 1; i >= 0; i--){
                    int[] cur = containers[request.charAt(0) - 'A'].get(i);
                    //BFS를 통해 테두리와 연결되어 있는지 확인
                    if(check(cur, wareHouse)){
                        //미리 삭제하면 삭제하는 순서에 따라 결과가 달라져서 삭제 예정 배열에만 넣어주고 뒤에서 삭제해줌
                        willDelete.add(cur);
                        containers[request.charAt(0) - 'A'].remove(i);
                    }
                }
                
                for(int[] d : willDelete){
                    wareHouse[d[0]][d[1]] = 0;
                }
                
            }
        }
        
        for(int i = 1; i <= sizeR; i++){
            for(int j = 1; j <= sizeC; j++){
                if(wareHouse[i][j] == 1) answer++;
            }
        }
        
        
        return answer;
    }
    
    boolean check(int[] cur, int[][] wareHouse){
        return bfs(cur[0], cur[1], wareHouse, new boolean[wareHouse.length][wareHouse[0].length]);
    }
    
    boolean bfs(int r, int c, int[][] wareHouse, boolean[][] vis){
        Queue<int[]> q = new ArrayDeque<>();
        
        q.add(new int[]{r,c});
        vis[r][c] = true;
        
        while(!q.isEmpty()){
            int[] cur = q.poll();
            
            if(wareHouse[cur[0]][cur[1]] == -1){
                return true;
            }
            
            for(int i = 0; i < 4; i++){
                int nr = cur[0] + dirs[i][0];
                int nc = cur[1] + dirs[i][1];
                
                if(!vis[nr][nc] && wareHouse[nr][nc] != 1){
                    vis[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                }
            }
        }
        
        return false;
    }
}
