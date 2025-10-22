import java.io.*;
import java.util.*;

public class BOJ_19236 {

    static class Pair {
        int no; // 물고기 번호
        int z; // 방향

        int x;
        int y;

        public Pair(int no, int z) {
            this.no = no;
            this.z = z;
        }

        public Pair(int x, int y, int no) {
            this.x = x;
            this.y = y;
            this.no = no;
        }
    }

    // 우상 상 좌상 좌 좌하 하 우하 우
    static int[][] dir = { { -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 } };
    static Pair[][] map;
    static int[] shark;
    static PriorityQueue<Pair> pq;
    static HashSet<Integer> eaten;
    static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        map = new Pair[4][4];
        pq = new PriorityQueue<>((o1, o2) -> o1.no - o2.no);
        for (int i = 0; i < 4; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                int no = Integer.parseInt(st.nextToken());
                int z = Integer.parseInt(st.nextToken());
                map[i][j] = new Pair(no, z);
                pq.offer(map[i][j]);
            }
        }
        shark = new int[3];
        shark[0] = 0; // x 좌표
        shark[1] = 0; // y 좌표
        shark[2] = map[0][0].z; // 방향
        eaten = new HashSet<>();
        eaten.add(map[0][0].no);

        int score = map[0][0].no;
        map[0][0].no = 0;

        Pair[][] use_map = new Pair[4][4];
        for (int i = 0; i < 4; i++) {
            use_map[i] = Arrays.copyOf(map[i], 4);
        }
        answer = 0;
        move(score, use_map);
        sb.append(answer);
        System.out.println(sb);
    }

    private static void move(int score, Pair[][] use_map) {

        // 1 부터 16 물고기 이동
        for (int i = 1; i <= 16; i++) {
            // 이미 먹힌 물고기면 pass
            if (eaten.contains(i))
                continue;
            // map에서 각 물고기 확인
            check_fish: for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (use_map[j][k].no == i) {
                        int z = use_map[j][k].z;
                        for (int l = 0; l < 8; l++) {
                            int nz = z + l;
                            if (nz >= 8) {
                                nz -= 8;
                            }
                            int nx = j + dir[nz][0];
                            int ny = k + dir[nz][1];
                            // 칸 밖
                            if (nx < 0 || nx > 3 || ny < 0 || ny > 3)
                                continue;
                            // 상어 있는 칸
                            if (nx == shark[0] && ny == shark[1])
                                continue;
                            // 빈 칸 or 물고기 칸
                            int temp_no = use_map[j][k].no;
                            use_map[j][k].no = use_map[nx][ny].no;
                            use_map[j][k].z = use_map[nx][ny].z;
                            use_map[nx][ny].no = temp_no;
                            use_map[nx][ny].z = nz;
                            break check_fish;
                        }
                    }
                }
            }
        }

        // 상어 움직일 차례
        List<Pair> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int nx = shark[0] + dir[shark[2]][0] * i;
            int ny = shark[1] + dir[shark[2]][1] * i;
            // 칸 밖이면 stop
            if (nx < 0 || nx > 3 || ny < 0 || ny > 3)
                break;
            // 빈 칸이면 continue
            if (use_map[nx][ny].no == 0)
                continue;
            // 물고기 칸이면 리스트에 추가
            list.add(new Pair(nx, ny, use_map[nx][ny].no));
        }

        // 상어가 이동할 칸이 없으면 종료
        if (list.isEmpty()) {
            answer = Integer.max(answer, score);
            return;
        }

        // 상어가 이동할 수 있는 물고기 칸마다 시도
        Pair[][] shark_map = new Pair[4][4];
        for (Pair s : list) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    shark_map[i][j] = new Pair(use_map[i][j].no, use_map[i][j].z);
                }
            }

            int origin_sx = shark[0];
            int origin_sy = shark[1];
            int origin_sz = shark[2];
            shark[0] = s.x;
            shark[1] = s.y;
            shark[2] = shark_map[shark[0]][shark[1]].z;
            int temp_score = shark_map[s.x][s.y].no;
            eaten.add(temp_score);
            shark_map[s.x][s.y].no = 0;

            move(score + temp_score, shark_map);
            shark[0] = origin_sx;
            shark[1] = origin_sy;
            shark[2] = origin_sz;
            eaten.remove(temp_score);
        }
    }
}
