import java.util.*;

class Solution {
    static int answer;
    public int solution(int n, int[][] q, int[] ans) {
        answer = 0;
        HashSet<Integer> selected = new HashSet<>();
        select(n, q, ans, selected, 1);
        return answer;
    }
    private static void select(int n, int[][] q, int[] ans, HashSet<Integer> selected, int start) {
        if (selected.size() == 5) {
            validate(q, ans, selected);
            return;
        }
        for(int i = start; i <= n; i++) {
            selected.add(i);
            select(n, q, ans, selected, i + 1);
            selected.remove(i);
        }
    }
    private static void validate(int[][] q, int[] ans, HashSet<Integer> selected) {
        boolean isOK = true;
        for(int i = 0; i < q.length; i++) {
            int check = 0;
            for(int j = 0; j < 5; j++) {
                if (selected.contains(q[i][j])) {
                    check++;
                }
            }
            if (check != ans[i]) {
                isOK = false;
                break;
            }
        }
        if(isOK) {
            answer++;
        }
    }
}
