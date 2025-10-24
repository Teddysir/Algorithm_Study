class Solution {

	static String answer;
	static int op_start_hour, op_start_min, op_end_hour, op_end_min, pos_hour, pos_min, video_len_hour, video_len_min;
	static int op_start_total, op_end_total, pos_total, video_len_total;

	public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {

		String[] video_len_str = video_len.split(":");
		String[] op_start_str = op_start.split(":");
		String[] op_end_str = op_end.split(":");
		String[] pos_str = pos.split(":");

		video_len_hour = Integer.parseInt(video_len_str[0]);
		video_len_min = Integer.parseInt(video_len_str[1]);
		op_start_hour = Integer.parseInt(op_start_str[0]); // 오프닝 시작 시간
		op_start_min = Integer.parseInt(op_start_str[1]); // 오프닝 시작 분
		op_end_hour = Integer.parseInt(op_end_str[0]); // 오프닝 끝 시간
		op_end_min = Integer.parseInt(op_end_str[1]); // 오프닝 끝 분
		pos_hour = Integer.parseInt(pos_str[0]); // 초기 시작 시간
		pos_min = Integer.parseInt(pos_str[1]); // 초기 시작 분

		video_len_total = (video_len_hour * 60) + video_len_min;
		op_start_total = (op_start_hour * 60) + op_start_min;
		op_end_total = (op_end_hour * 60) + op_end_min;
		pos_total = (pos_hour * 60) + pos_min;

		for (int i = 0; i < commands.length; i++) {

			if (commands[i].equals("next")) {
				// TODO : 일단 현재가 포함인지 체크
				opening_check();
				if ((pos_total + 10) >= video_len_total) {
					pos_total = video_len_total;
				} else {
					pos_total += 10;
				}

			} else {
				opening_check();
				if ((pos_total - 10) < 0) {
					pos_total = 0;
				} else {
					pos_total -= 10;

				}
			}

		}

		opening_check();
		answer = String.format("%02d:%02d", pos_total / 60, pos_total % 60);

		return answer;
	}

	static void opening_check() {

		if (pos_total >= op_start_total && pos_total <= op_end_total) {

			pos_total = op_end_total; // 전체 값을 엔딩으로 바꿈,
			pos_hour = pos_total / 60; // 60으로 나눈 몫,
			pos_min = pos_total % 60; // 60으로 나눈 나머지

		}
	}
}