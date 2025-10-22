package 청소년상어;

import java.io.*;
import java.util.*;

public class BOJ_19236 {

    static class Info {
        int r, c, dir;
        boolean alive;

        public Info(int r, int c, int dir, boolean alive) {
            this.r = r;
            this.c = c;
            this.dir = dir;
            this.alive = alive;
        } // 행, 열, 방향, 먹혔는지 안먹혔는지 표시하기 위한 매서드
        
        /*
         * 각 분기마다 배열의 값은 달라야 하며 해시의 값 또한 달라야 한다.
         * 해시는 물고기가 1~16번까지 존재하고 낮은 순으로 차례대로 움직이기 때문에 
         * 해시 크기만큼 for 문돌려서 물고기의 이동 로직을 구현하면 괜찮다고 판단하여 접근
         * 해시 또한 참조값이기 때문에 각 분기마다 해시의 값을 복사하기 위해서는 생성자를 새로 만들어서 해시에 값을 넣는 것이 중요
         * 그렇기 위해서 깊은 복사를 하기 위한 메서드를 하나 넘겨서 Info 객체 자체를 넘겨서 저장하는 형식으로 접근했습니다.
         * ->그냥 같은 info 쓰면 되지 않냐 하지만 결국 같은 주소값이라 다른 주소값의 객체가 필요합니다.
         */

        public Info(Info o) {
            this.r = o.r;
            this.c = o.c;
            this.dir = o.dir;
            this.alive = o.alive;
        }
    }

    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};
    static int ans = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] arr = new int[4][4];
        HashMap<Integer, Info> map = new HashMap<>(); //물고기들의 정보를 저장하는 해시맵.

        for (int i = 0; i < 4; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                int num = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken()) - 1; //방향은 0 부터.
                arr[i][j] = num;
                map.put(num, new Info(i, j, dir, true));
            }
        }

        int start = arr[0][0];
        Info first = map.get(start);
        int dir = first.dir;
        map.get(start).alive = false;
        arr[0][0] = -1;

        dfs(arr, map, 0, 0, dir, start);
        System.out.println(ans);
    }

    static void dfs(int[][] arr, HashMap<Integer, Info> map, int sr, int sc, int sdir, int score) {
        ans = Math.max(ans, score); 

        moveFish(arr, map, sr, sc); //물고기 이동.

        for (int step = 1; step <= 3; step++) { //맵의 크기는 4*4 고정이기 때문에 step(이동)은 본인 위치 제외 최대 3번만 가능하다.
            int nr = sr + dr[sdir] * step;
            int nc = sc + dc[sdir] * step;

            if (nr < 0 || nc < 0 || nr >= 4 || nc >= 4) break;
            if (arr[nr][nc] == 0) continue;

            int[][] nextArr = copyArr(arr);
            HashMap<Integer, Info> nextMap = copyMap(map); //현재 분기에서 이동한 상어 상태 업데이트.

            int eat = nextArr[nr][nc]; //물고기를 먹을 위치.
            int ndir = nextMap.get(eat).dir; //물고기를 먹었을때 갱신되는 상어의 방향.

            nextArr[sr][sc] = 0; //현재 상어의 위치 0.
            nextArr[nr][nc] = -1; //상어 위치 이동 후 표시.
            nextMap.get(eat).alive = false; //상어가 이동한 좌표의 물고기는 죽었져?

            dfs(nextArr, nextMap, nr, nc, ndir, score + eat); //다음 부니골 이동.
        }
    }

    static void moveFish(int[][] arr, HashMap<Integer, Info> map, int sr, int sc) {
        for (int num = 1; num <= 16; num++) {
            if (!map.containsKey(num)) continue; //값이 존재하지 않는 경우.
            Info f = map.get(num);
            if (!f.alive) continue; //이미 물고기가 상어에게 잡아먹힌 경우.

            for (int i = 0; i < 8; i++) {
                int ndir = (f.dir + i) % 8;
                int nr = f.r + dr[ndir];
                int nc = f.c + dc[ndir];

                if (nr < 0 || nc < 0 || nr >= 4 || nc >= 4) continue; //범위 밖일 경우 다음 방향으로.
                if (nr == sr && nc == sc) continue; //이동하는 좌표가 상어인경우 이동 불가.

                if (arr[nr][nc] > 0) { // 다른 물고기와 swap
                    int other = arr[nr][nc];
                    Info o = map.get(other);

                    arr[f.r][f.c] = other;
                    arr[nr][nc] = num;

                    int tr = f.r, tc = f.c;
                    f.r = nr; f.c = nc;
                    o.r = tr; o.c = tc;
                } else { // 빈 칸
                    arr[f.r][f.c] = 0;
                    arr[nr][nc] = num;
                    f.r = nr; f.c = nc;
                }
                f.dir = ndir;
                break;
            }
        }
    }

    /*
     * 배열 복사 메서
     */
    static int[][] copyArr(int[][] arr) {
        int[][] next = new int[4][4];
        for (int i = 0; i < 4; i++) next[i] = arr[i].clone();
        return next;
    }

    /*
     * 해시 복사 메서드
     * 객체만 그대로 가져가버리면 참조값이라 의미 없으니 객체를 새로 만들어서 넣어야 함!!
     */
    static HashMap<Integer, Info> copyMap(HashMap<Integer, Info> map) {
        HashMap<Integer, Info> next = new HashMap<>();
        for (Map.Entry<Integer, Info> e : map.entrySet()) {
            next.put(e.getKey(), new Info(e.getValue()));
        }
        return next;
    }
}