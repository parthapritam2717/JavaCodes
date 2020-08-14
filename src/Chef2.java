

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class Chef2 {
	
	
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

	
	
	
	private static void solve() throws IOException{
		
		Reader fr=new Reader(); 
		int t=fr.nextInt();
		//List<Integer> ans = new ArrayList<Integer>();
		while(t>0) {
			
			int n=fr.nextInt();
			List<List<Integer>> graph= new ArrayList<List<Integer>> ();
			for(int i=0;i<=n;++i) {
				graph.add(new ArrayList<Integer>());
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
			
			solveGraph(graph,permutation,population,fruits,n);
			
			--t;
			
		}
		//ans.forEach((s)->System.out.println(s));
	}
	
	private static void solveGraph(List<List<Integer>> graph,int permutation[],int population[],int fruits[],int n) {
		int days[]=new int[n+1];
		Arrays.fill(days, -1);
		boolean completed[]=new boolean[n+1];
		for(int i=0;i<n;++i) {
			int source=permutation[i];
			int sourcePop=population[source];
			//if(fruits[source]!=0) {
				//for all nodes reachable from source reduce the number of fruits by pop of source
				fruits[source]-=Math.min(fruits[source], sourcePop);
				if(fruits[source]==0 && days[source]==-1) {
					days[source]=i+1;
				}
				List<Integer> list=graph.get(source);
				boolean vis[]=new boolean[n+1];
				vis[source]=true;
				for(int j=0;j<list.size();++j) {
					if(!vis[list.get(j)]) {
						//vis[list.get(j)]=true;
						dfs(graph,list.get(j),vis,days,sourcePop,fruits,i+1,completed);
						//int v=list.get(j);
//						graph.get(source).remove(v);
//						graph.get(v).remove(source);
						
					}
				}
				completed[source]=true;

		}
		
		StringBuilder ans= new StringBuilder();
		
		
		for(int i=1;i<=n;++i) {
			
			ans.append(Integer.toString(days[i])+" ");
			
		}
		System.out.println(ans.toString());
		
	}
	
	private static void dfs(List<List<Integer>> graph,int index,boolean vis[],int days[],int pop,int fruits[],int dayCount,boolean completed[]) {
		vis[index]=true;
		if(completed[index])
			return;
		
		//process this node
		fruits[index]-=Math.min(fruits[index], pop);
		if(fruits[index]==0 && days[index]==-1){
			days[index]=dayCount;
		}
		List<Integer> list=graph.get(index);
		for(int i=0;i<list.size();++i) {
			if(!vis[list.get(i)]) {
				dfs(graph,list.get(i),vis,days,pop,fruits,dayCount,completed);
			}
		}
	}
	
	
}
	
	