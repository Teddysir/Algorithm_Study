import java.io.*;
import java.util.*;

public class Main {
	static int result;
	static Node[] tree;
	
	static class Node{
		int parent;
		List<Integer> children;
		
		Node(){
			this.children = new ArrayList<>();
		}
		
		void addP(int parent) {
			this.parent = parent;
		}
		
		void add(int child) {
			this.children.add(child);
		}
		
		void delete(int child) {
			for(int idx = 0; idx < this.children.size(); idx++) {
				if(this.children.get(idx) == child) {
					this.children.remove(idx);
					break;
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		int root = 0;
		result = 0;
		tree = new Node[N];
		
		for(int i = 0; i < N; i++) {
			tree[i] = new Node();
		}

		st = new StringTokenizer(br.readLine());
		
		for(int i = 0; i < N; i++) {
			
			int cur = Integer.parseInt(st.nextToken());
			
			if(cur == -1) {
				root = i;
				continue;
			}
			
			tree[cur].add(i);
			tree[i].addP(cur);
		}
		
		int r = Integer.parseInt(br.readLine());
		
		if(r == root) {
			System.out.println(0);
			return;
		}
		
		tree[tree[r].parent].delete(r);
		
		calculate(root);

		System.out.println(result);
	}
	
	private static void calculate(int i) {
		if(tree[i].children.size() == 0) {
			result++;
			return;
		}
		
		for(int child : tree[i].children) {
			calculate(child);
		}
	}
}
