import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static class Info implements Comparable<Info> {

		int from, to, num;

		public Info(int from, int to, int num) {
			this.from = from;
			this.to = to;
			this.num = num;
		}

		@Override
		public int compareTo(Info o) {
			if (this.to == o.to) return Integer.compare(this.from, o.from);
			return Integer.compare(this.to, o.to);
		}

	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(br.readLine());
		Info[] infos = new Info[M];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			infos[i] = new Info(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()));
		}
		Arrays.sort(infos);
		ArrayList<Info> truck = new ArrayList<>();
//		System.out.println();
//		for (int i = 0; i < M; i++) {
//			System.out.println(infos[i].from + " " + infos[i].to + " " + infos[i].num);
//		}
		int currentCapacity = 0;
		int total = 0;
		for (int i = 1; i <= N; i++) {
			// 하차
			if (truck.size() != 0) {
				for (int j = truck.size() - 1; j >= 0; j--) {
					if (truck.get(j).to == i) {
						currentCapacity -= truck.get(j).num;
						total += truck.get(j).num;
						truck.remove(j);
					}
				}
			}
			// 상차
			for (int j = 0; j < M; j++) {
				if (infos[j].from == i) {
					if (C - currentCapacity >= infos[j].num) {
						truck.add(new Info(infos[j].from, infos[j].to, infos[j].num));
						currentCapacity += infos[j].num;
					} else {
						truck.add(new Info(infos[j].from, infos[j].to, C - currentCapacity));
						currentCapacity = C;
					}
				}
			}
		}
		System.out.println(total);
	}
}
