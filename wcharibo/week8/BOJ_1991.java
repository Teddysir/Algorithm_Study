import java.util.*;
import java.io.*;

public class Main{
	
	static Node[] tree = new Node[26];
	
	public static class Node{
		public int name;
		public int left;
		public int right;
		
		Node(int name){
			this.name = name;
			this.left = -1;
			this.right = -1;
		}
		
		public void setLeft(int left) {
			this.left = left;
		}
		
		public void setRight(int right) {
			this.right = right;
		}
		
		public void preorder(StringBuilder sb) {
			sb.append((char)('A' + this.name));
			if(this.left > 0) tree[this.left].preorder(sb);
			if(this.right > 0) tree[this.right].preorder(sb);
		}
		
		public void inorder(StringBuilder sb) {
			if(this.left > 0) tree[this.left].inorder(sb);
			sb.append((char)('A' + this.name));
			if(this.right > 0) tree[this.right].inorder(sb);
		}
		
		public void postorder(StringBuilder sb) {
			if(this.left > 0) tree[this.left].postorder(sb);
			if(this.right > 0) tree[this.right].postorder(sb);
			sb.append((char)('A' + this.name));
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 0; tc < T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			
			int parent = st.nextToken().charAt(0) - 65;
			
			tree[parent] = new Node(parent);
			tree[parent].setLeft(st.nextToken().charAt(0) - 65);
			tree[parent].setRight(st.nextToken().charAt(0) - 65);
		}
		
		tree[0].preorder(sb);
		System.out.println(sb.toString());
		sb = new StringBuilder();
		tree[0].inorder(sb);
		System.out.println(sb.toString());
		sb = new StringBuilder();
		tree[0].postorder(sb);
		System.out.println(sb);
		
		
		
	}
}
