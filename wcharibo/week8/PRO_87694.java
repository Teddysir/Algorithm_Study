     
        characterX *= 2;
        characterY *= 2;
        itemX *= 2;
        itemY *= 2;
        
        int answer = 0;
        // 이동도 2배로
        int[][] dirs = {{-2, 0},{2, 0},{0, -2},{0, 2}};
        
        Queue<Human> q = new ArrayDeque<>();
        
        q.add(new Human(new Pair(characterX, characterY), new Pair(characterX, characterY), 0));
        
        while(!q.isEmpty()){
            Human h = q.poll();
            
            if(h.cur.x == itemX && h.cur.y == itemY){
                return h.cnt;
            }
            
            for(int dir = 0 ; dir < 4; dir++){
                Pair cur = new Pair(h.cur.x, h.cur.y);
                Pair log = new Pair(cur.x, cur.y);
                //이동이 직사각형 선분 위에서 이뤄졌는가
                bimport java.util.*;

class Solution {
    class Pair{
        public int x;
        public int y;
        
        Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    
    class Human{
        public Pair prev;
        public Pair cur;
        public int cnt;
        
        Human(Pair prev, Pair cur, int cnt){
            this.prev = prev;
            this.cur = cur;
            this.cnt = cnt;
        }
    }
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        
        // 중간 위치도 포함되는지 파악하기 위해 2배 해주기
        for(int i = 0 ; i < rectangle.length; i++){
            for(int j = 0 ; j < 4; j++){
                rectangle[i][j] *= 2;
            }
        }
   oolean isOnRec = false;
                //
                boolean isPos = true;
                
                //도착위치
                cur.x += dirs[dir][0];
                cur.y += dirs[dir][1];
                //중간위치 파악
                log.x += (dirs[dir][0]/2);
                log.y += (dirs[dir][1]/2);
                
                //이전 위치인지 확인
                if(cur.x == h.prev.x && cur.y == h.prev.y) continue;
                
                //{x1, y1, x2, y2}
                for(int[] rec : rectangle){
                    //밖에 있는 경우
                    if(cur.x < rec[0] || cur.x > rec[2] || cur.y < rec[1] || cur.y > rec[3]) continue;
                    
                    //안에 있는 경우(이동 과정 위의 위치도 검사)
                    if((cur.x > rec[0] && cur.x < rec[2] && cur.y > rec[1] && cur.y < rec[3]) || 
                      (log.x > rec[0] && log.x < rec[2] && log.y > rec[1] && log.y < rec[3])){
                        isPos = false;
                        continue;
                    }
                    
                    //이동과정이 사각형의 선분 위였는 지 확인
                    if(rec[0] <= log.x && log.x <= rec[2] && rec[1] <= log.y && log.y <= rec[3]){
                        isOnRec = true;
                    }
                }
                
                if(isOnRec && isPos){
                    q.add(new Human(h.cur, cur, h.cnt + 1));
                }
            }
            
        }
        
        return answer;
    }
}
