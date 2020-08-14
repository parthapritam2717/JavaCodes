

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

 class Chef3 {
	
	
	static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() 
        { 
            br = new BufferedReader(new
                     InputStreamReader(System.in)); 
        } 
  
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
  
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
  
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
  
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    } 
	
	public static void main(String[] args) {
		solve();
	}

	
	public  static class Node{
		int index;
		Node left;
		Node right;
//		int fruits;
//		int pop;
		Node(int index){
			this.index=index;
//			this.fruits=fruits;
//			this.pop=pop;
		}
	}
	
	
	private static void solve() {
		
		FastReader fr=new FastReader(); 
		int t=fr.nextInt();
		//List<Integer> ans = new ArrayList<Integer>();
		while(t>0) {
			
			int n=fr.nextInt();
			//List<List<Integer>> graph= new ArrayList<List<Integer>> ();
			//Map<Integer,Set<Integer>> graph=new HashMap<Integer, Set<Integer>>();
			
//			for(int i=1;i<=n;++i) {
//				graph.put(i,new HashSet<Integer>());
//			}
			
			Map<Integer,Node> graph=new HashMap<Integer,Node>();
			
			for(int i=1;i<=n;++i) {
				graph.put(i,new Node(i));
			}
			
			for(int i=0;i<n-1;++i) {
				int u=fr.nextInt();
				int v=fr.nextInt();
				
				Node U=graph.get(u);
				Node V=graph.get(v);
				
				
				if(U.right==null) {
					U.right=V;
					V.left=U;
				}
				else {
					U.left=V;
					V.right=U;
				}
			}
			int permutation[]=new int[n];
			for(int i=0;i<n;++i) {
				permutation[i]=fr.nextInt();
			}
			int population[]=new int[n+1];
			for(int i=1;i<=n;++i) {
				population[i]=fr.nextInt();
			}
			int fruits[]=new int[n+1];
			for(int i=1;i<=n;++i) {
				fruits[i]=fr.nextInt();
			}
			
			solveGraph(graph,permutation,population,fruits,n);
			
			
			--t;
			
		}
		//ans.forEach((s)->System.out.println(s));
	}
	
	private static void solveGraph(Map<Integer,Node> graph,int permutation[],int population[],int fruits[],int n){
		int days[]=new int[n+1];
		Arrays.fill(days, -1);
		for(int i=0;i<n;++i) {
			int source=permutation[i];
			int sourcePop=population[source];
			Node start=graph.get(source);
			Node cur=start;
			while(cur!=null) {
				fruits[cur.index]-=Math.min(fruits[cur.index], sourcePop);
				if(fruits[cur.index]==0 && days[cur.index]==-1) {
					days[cur.index]=i+1;
				}
				cur=cur.left;
			}
			cur=start.right;
			while(cur!=null) {
				fruits[cur.index]-=Math.min(fruits[cur.index], sourcePop);
				if(fruits[cur.index]==0 && days[cur.index]==-1) {
					days[cur.index]=i+1;
				}
				cur=cur.right;
			}
			
			//now to remove edges
			
			Node leftPrev=start.left;
			Node rightnext=start.right;
			
			if(leftPrev!=null) {
				leftPrev.right=null;
				start.left=null;
			}
			if(rightnext!=null) {
				rightnext.left=null;
				start.right=null;
			}
		}
		//print output
		StringBuilder ans= new StringBuilder();
		
		for(int i=1;i<=n;++i) {
			
			ans.append(Integer.toString(days[i])+" ");
			//System.out.print(days[i]+" ");
			
		}
		ans.append("\n");
		System.out.println(ans.toString());
		
	}
	
	
//	private static void solveGraph(Map<Integer,Set<Integer>> graph,int permutation[],int population[],int fruits[],int n) {
//		int days[]=new int[n+1];
//		for(int i=0;i<n;++i) {
//			int source=permutation[i];
//			int sourcePop=population[source];
//			//if(fruits[source]!=0) {
//				//for all nodes reachable from source reduce the number of fruits by pop of source
//			fruits[source]-=Math.min(fruits[source], sourcePop);
//			if(fruits[source]==0 && days[source]==0) {
//				days[source]=i+1;
//			}
//			Set<Integer> list=graph.get(source);
//			boolean vis[]=new boolean[n+1];
//			vis[source]=true;
//			for(int v:list) {
//				if(!vis[v]) {
//					dfs(graph,v,vis,days,sourcePop,fruits,i+1);
//				}
//			}
//			list.forEach(v->{
//				graph.get(v).remove(source);
//			});
//			graph.remove(source);
//
//		}
//		
//		
//		for(int i=1;i<=n;++i) {
//			if(days[i]==0) {
//				System.out.print(-1+" ");
//			}
//			else {
//				System.out.print(days[i]+" ");
//			}
//		}
//		System.out.println();
//		
//	}
//	
//	private static void dfs(Map<Integer,Set<Integer>> graph,int index,boolean vis[],int days[],int pop,int fruits[],int dayCount) {
//		vis[index]=true;
//		//process this node
//		fruits[index]-=Math.min(fruits[index], pop);
//		if(fruits[index]==0 && days[index]==0){
//			days[index]=dayCount;
//		}
//		Set<Integer> list=graph.get(index);
//		
//		for(int v:list) {
//			if(!vis[v]) {
//				dfs(graph,v,vis,days,pop,fruits,dayCount);
//			}
//		}
//		
//	}
//	
//	
}
	
	