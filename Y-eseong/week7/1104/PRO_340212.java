class Solution {

    public int solution(int[] diffs, int[] times, long limit) {
        int answer = 0;

        int left = 1;
        int right = 0;
        for (int x : diffs) {
            right = Integer.max(right, x);
        }
        int level = right;
        // 1 밖에 없는 경우
        if (left == right) {
            answer = level;
        } else {
            left = 0; // 이렇게 안만들면 diffs[1, 2]로 들어올 때 2를 출력
            big: while (true) {
                // left는 limit을 넘겨 통과하지 못한 level, right은 limit을 충족해서 통과한 level
                if (left + 1 == right) {
                    answer = right;
                    break;
                }
                int index = 0;
                long sum = 0;
                for (int diff : diffs) {
                    if (diff <= level) {
                        sum += times[index];
                    } else {
                        sum += (diff - level) * (times[index] + times[index - 1]) + times[index];
                    }
                    if (sum > limit) {
                        left = level;
                        level = (left + right) / 2;
                        continue big;
                    }
                    index++;
                }
                if (sum == limit) {
                    answer = level;
                    break;
                }
                right = level;
                level = (left + right) / 2;
            }
        }
        return answer;
    }
}