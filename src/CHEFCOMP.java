

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

 class CHEFCOMP {
	
	
static class Reader 
    { 
        final private int BUFFER_SIZE = 1 << 16; 
        private DataInputStream din; 
        private byte[] buffer; 
        private int bufferPointer, bytesRead; 
  
        public Reader() 
        { 
            din = new DataInputStream(System.in); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public Reader(String file_name) throws IOException 
        { 
            din = new DataInputStream(new FileInputStream(file_name)); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public String readLine() throws IOException 
        { 
            byte[] buf = new byte[64]; // line length 
            int cnt = 0, c; 
            while ((c = read()) != -1) 
            { 
                if (c == '\n') 
                    break; 
                buf[cnt++] = (byte) c; 
            } 
            return new String(buf, 0, cnt); 
        } 
  
        public int nextInt() throws IOException 
        { 
            int ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do
            { 
                ret = ret * 10 + c - '0'; 
            }  while ((c = read()) >= '0' && c <= '9'); 
  
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        public long nextLong() throws IOException 
        { 
            long ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        public double nextDouble() throws IOException 
        { 
            double ret = 0, div = 1; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
  
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
  
            if (c == '.') 
            { 
                while ((c = read()) >= '0' && c <= '9') 
                { 
                    ret += (c - '0') / (div *= 10); 
                } 
            } 
  
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        private void fillBuffer() throws IOException 
        { 
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE); 
            if (bytesRead == -1) 
                buffer[0] = -1; 
        } 
  
        private byte read() throws IOException 
        { 
            if (bufferPointer == bytesRead) 
                fillBuffer(); 
            return buffer[bufferPointer++]; 
        } 
  
        public void close() throws IOException 
        { 
            if (din == null) 
                return; 
            din.close(); 
        } 
    }  
	
	public static void main(String[] args) throws IOException {
		solve();
	}

	
	
	private static StringBuilder ans=null;
	private static void solve() throws IOException{
		
		Reader fr=new Reader(); 
		int t=fr.nextInt();
		//List<Integer> ans = new ArrayList<Integer>();
		ans=new StringBuilder();
		while(t>0) {
			
			int n=fr.nextInt();
			//List<List<Integer>> graph= new ArrayList<List<Integer>> ();
			Map<Integer,Set<Integer>> graph=new HashMap<Integer, Set<Integer>>();
			
			for(int i=1;i<=n;++i) {
				graph.put(i,new HashSet<Integer>());
			}
			for(int i=0;i<n-1;++i) {
				int u=fr.nextInt();
				int v=fr.nextInt();
				graph.get(u).add(v);
				graph.get(v).add(u);
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
			
			int days[]=new int[n+1];
			
			
			
			Node nodeslist[]=new Node[n+1];
			
			assignNode(graph,nodeslist,fruits,population,0,1,n+1); //initial assignment
			
			int day=0;
			while(day<n) {
				int source=permutation[day];
				int sourcePop=population[source];
				long sum=nodeslist[source].totalsum+sourcePop;
				Node node=nodeslist[source];
				if(!node.cityQueue.isEmpty()) {
					bestCase(node, sum, fruits, days, day+1);
				}
				
				Set<Integer> dest=graph.get(source);
				boolean flag=false;
				if(dest.size()>1) {
					flag=true;
				}
				for(int graphnode:dest) {
					graph.get(graphnode).remove(source);
					
				}
				//now create the new nodes for the other disjoin sets in our array
				nodeslist[source]=null;
				graph.remove(source);
				if(flag) {
					for(int graphnode:dest) {
						assignNode(graph,nodeslist,fruits,population,node.totalsum,graphnode,node.size);
						
					}
				}
				
				++day;
				
			}
			printDays(days,n);
			//bestCase(graph,permutation,population,fruits,n,days);
			
			--t;
			
		}
		System.out.println(ans.toString());
		//ans.forEach((s)->System.out.println(s));
	}
	

	
	private static int nodeLen=0;
	private static void assignNode(Map<Integer,Set<Integer>> graph,Node nodeslist[],int fruits[],int pop[],long sum,int start,int size) {
		//do dfs of all nodes
		
		PriorityQueue<City> pq=new PriorityQueue<City>(size,(c1,c2)-> {
			return c1.fruits-c2.fruits;
		});
		boolean vis[]=new boolean[nodeslist.length];
		Node newnode=new Node(pq,0);
		newnode.size=0;
		dfsutil(graph,fruits,pop,start,pq,vis,sum,nodeslist,newnode);
		
	}
	
	private static void dfsutil(Map<Integer,Set<Integer>> graph,int fruits[],int pop[],int index,PriorityQueue<City> pq,boolean vis[],long sum,Node nodeslist[],Node node) {
		vis[index]=true;
		nodeslist[index]=node;
		long diff=fruits[index]-sum;
		if(diff<0) {
			diff=0;
		}
		fruits[index]=(int)diff;
		pq.add(new City(fruits[index],index,pop[index]));
		node.size++;
		Set<Integer> list=graph.get(index);
		for(int v:list) {
			if(!vis[v]) {
				dfsutil(graph,fruits,pop,v,pq,vis,sum,nodeslist,node);
			}
		}
		
	}
	
	
	
	
	private static void bestCase(Node node,long sum,int fruits[],int days[],int day) {
			node.totalsum=sum;
			PriorityQueue<City> pq=node.cityQueue;
			while(!pq.isEmpty() && pq.peek().fruits<=sum) {
				if(days[pq.peek().index]==0)
					days[pq.peek().index]=day;
				fruits[pq.peek().index]=0;
				pq.poll();
			}
	}
		
	
	

	
	private static void solveGraph(Map<Integer,Set<Integer>> graph,int permutation[],int population[],int fruits[],int n,int start,int days[]) {
		
		for(int i=start;i<n;++i) {
			int source=permutation[i];
			int sourcePop=population[source];
			//if(fruits[source]!=0) {
				//for all nodes reachable from source reduce the number of fruits by pop of source
			fruits[source]-=Math.min(fruits[source], sourcePop);
			if(fruits[source]==0 && days[source]==0) {
				days[source]=i+1;
			}
			Set<Integer> list=graph.get(source);
			boolean vis[]=new boolean[n+1];
			vis[source]=true;
			for(int v:list) {
				if(!vis[v]) {
					dfs(graph,v,vis,days,sourcePop,fruits,i+1);
				}
			}
			Set<Integer> dest=graph.get(source);
			for(int node:dest) {
				graph.get(node).remove(source);
			}
			graph.remove(source);

		}
		printDays(days,n);
		
		
		
	}
	
	private static void printDays(int days[],int n) {
		StringBuilder sb=new StringBuilder();
		
		for(int i=1;i<=n;++i) {
			if(days[i]==0) {
				sb.append(-1+" ");
			}
			else {
				sb.append(days[i]+" ");
			}
		}
		ans.append(sb+"\n");
	}
	
	private static void dfs(Map<Integer,Set<Integer>> graph,int index,boolean vis[],int days[],int pop,int fruits[],int dayCount) {
		vis[index]=true;
		//process this node
		fruits[index]-=Math.min(fruits[index], pop);
		if(fruits[index]==0 && days[index]==0){
			days[index]=dayCount;
		}
		Set<Integer> list=graph.get(index);
		
		for(int v:list) {
			if(!vis[v]) {
				dfs(graph,v,vis,days,pop,fruits,dayCount);
			}
		}
		
	}
	
	static class City{
		int fruits;
		int index;
		int pop;
		City(int amount,int index,int pop){
			this.index=index;
			this.fruits=amount;
			this.pop=pop;
			
			
		}
	}
	
	static class Node{
		long totalsum;
		PriorityQueue<City> cityQueue;
		int size;
		Node(PriorityQueue<City> p, long totalsum){
			this.totalsum=totalsum;
			this.cityQueue=p;
		}
	}
	
	
}
	
	