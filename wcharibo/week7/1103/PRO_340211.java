import java.util.*;

class Solution {
    //로봇의 현재 위치, 목적지, 현재 시간을 저장할 클래스
    class Robot{
        int[] cur;
        //경유지가 존재하여서 큐를 이용해서 구현
        Queue<int[]> dest = new ArrayDeque<>();
        int time;
        
        Robot(int[] cur, List<int[]> dest){
            this.cur = cur;
            //경유지들을 전부 큐에 삽입
            for(int[] i : dest){
                this.dest.add(i);
            }
            this.time = 0;
        }
        
        //로봇의 이동을 구현한 함수
        int[] move(){
            int moving;
            //행 이동 먼저
            if(dest.peek()[0] != cur[0]){
                //경유지와 현재 위치의 차이가 양수-> 현재 위치의 수가 더 작다 -> 1 아니면 -1
                moving = (dest.peek()[0] - cur[0]) > 0 ? 1 : -1;
                this.cur[0] += moving;
            //그다음 열 이동
            }else if(dest.peek()[1] != cur[1]){
                moving = (dest.peek()[1] - cur[1]) > 0 ? 1 : -1;
                this.cur[1] += moving;
            }else{
                //약간 무의미함
                return new int[]{-1,-1};
            }
            
            //이번 이동으로 경유지에 도착했다면 다음 경유지를 가리키도록 poll()
            if(dest.peek()[0] == cur[0] && dest.peek()[1] == cur[1]) dest.poll();
            
            return this.cur;
        }
    }
    //2차원 배열 사용해서 로봇의 현재 위치와 충돌하는 지를 판단할 것인 데,
    //이번 시간에 충돌하나 적이 있는지를 기록하기 위해 선언한 클래스
    class Pair{
        int time;
        boolean alreadyCrashed;
        
        Pair(int time){
            this.time = time;
            this.alreadyCrashed = false;
        }
        
        void crash(){
            this.alreadyCrashed = true;
        }
    }
    
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;
        
        Queue<Robot> robots = new ArrayDeque<>();
        Set<int[]> start = new HashSet<>();
        Pair[][] map = new Pair[101][101];
        
        for(int[] route : routes){
            List<int[]> r = new ArrayList<>();
            for(int i = 1; i < route.length; i++){
                r.add(new int[] {points[route[i] - 1][0], points[route[i] -1][1]});
            }
            
            robots.add(new Robot(
                new int[]{points[route[0] - 1][0], points[route[0] -1][1]},
                r
            ));
            
            //조금 더러운 데, 밑에 있는 충돌하는지 확인하는 로직과 같음, 시작 지점부터 충돌위험이 존재할 수 있음
            if(map[points[route[0] - 1][0]][points[route[0] -1][1]] != null){
                if(map[points[route[0] - 1][0]][points[route[0] -1][1]].time == 0){
                    if(!map[points[route[0] - 1][0]][points[route[0] -1][1]].alreadyCrashed){
                        map[points[route[0] - 1][0]][points[route[0] -1][1]].alreadyCrashed = true;
                        answer++;
                    }
                }else{
                    map[points[route[0] - 1][0]][points[route[0] -1][1]] = new Pair(0);
                }
            }else{
                map[points[route[0] - 1][0]][points[route[0] -1][1]] = new Pair(0);
            }
        }
        
        //로봇별로 이동시키기
        while(!robots.isEmpty()){
            Robot cur = robots.poll();
            
            //최종 목적지에 도달했다면 이 로봇은 끝내기
            if(cur.dest.isEmpty() || cur.cur[0] == cur.dest.peek()[0] && cur.cur[1] == cur.dest.peek()[1]){
                continue;
            }
            
            //움직이기
            int[] result = cur.move();
            //시간++
            cur.time++;
            //다시 큐에 추가
            robots.add(cur);
            
            //완전 초기에 해당 위치를 방문한적 있는지 확인
            if(map[result[0]][result[1]] != null){
                //해당 위치를 방문한적이 있고 && 방문한 시간이 같은지 확인
                if(map[result[0]][result[1]].time == cur.time){
                    //방문한 시간이 같고 충돌한 적이 있는지 확인
                    if(!map[result[0]][result[1]].alreadyCrashed){
                        map[result[0]][result[1]].alreadyCrashed = true;
                        answer++;
                    }
                // 방문한 시간이 같지 않다면? 반드시 뒤늦게 방문함
                }else{
                    map[result[0]][result[1]] = new Pair(cur.time);
                }
            //없다면 그냥 넣기
            }else{
                map[result[0]][result[1]] = new Pair(cur.time);
            }
        }
        
        
        return answer;
    }
}
