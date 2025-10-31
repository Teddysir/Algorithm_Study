class Solution {
    //가능한 비밀 코드를 비트마스킹할 정수
    static int code = 0;
    static int[] tries;
    static int[] checks;
    static int result = 0;
    static int max;
    
    public int solution(int n, int[][] q, int[] ans) {
        int answer = 0;
        //q, 입력한 정수들을 비트마스킹 후 저장하는 배열
        tries = new int[q.length];
        //solution함수에서 전달받는 인자들 전부 전역변수로 바꿔주기
        checks = ans;
        max = n;
        
        for(int i = 0, idx = 0; i < q.length; i++){
            int temp = 0;
            for(int j = 0; j < q[i].length; j++){
                //입력한 정수를 비트마스킹
                temp |= 1 << q[i][j];
            }
            tries[idx++] = temp;
        }
        
        
        //가능한 비밀코드 만들기
        make(0, 0, n);

        return result;
    }
    
    public boolean check(){
        
        for(int i = 0; i < tries.length; i++){
            //'입력한 정수' 와 선택한 '비밀 코드'가 AND 연산
            int hit = code & tries[i];
            int cnt = 0;
            //비트마스킹해서 최대 30번까지 반복함
            for(int j = 1; j <= max; j++){
                //1을 한칸씩 밀어가면 일치하는 게 있는지 확인, 있으면 ++
                if((hit & (1 << j)) > 0) cnt++;
            }
            
            //확인된 개수가 다르면 선택된 이 코드는 비밀 코드 불가함.
            if(cnt != checks[i]){    
                return false;
            }
        }
        
        return true;
    }
    
    //비밀 코드로 가능한 정수 조합 구하는 함수
    public void make(int idx, int prev, int n){
        //다섯개 정했으면 check() 후 가능하면 ++
        if(idx == 5){
            if(check()){
                result++;
            }
            return;
        }
        
        //이전에 선택한 인덱스를 전달받아서 이후 인덱스부터 탐색하고 선택하는 데, 그 이유는 코드들이 정렬되어 있다는 조건 때문
        //이전 + 1 부터 n까지
        for(int i = prev + 1; i <= n; i++){
            //선택 후 비트마스킹하기
            code = code | 1 << i;
            //다음 인덱스 선택
            make(idx + 1, i, n);
            //탐색 후 선택 취소
            code = code ^ 1 << i;
        }
    }
}
