

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

//dp using pq

class Color3 {
	
	
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
	
	

	
	static class Node{
		int color;
		int cost;
		int count;
//		Map<Integer,Integer> slopes;
//		Node(int color,int cost,int count,Map<Integer,Integer> slopes){
//			this.color=color;
//			this.cost=cost;
//			this.count=count;
//			this.slopes=slopes;
//		}
		
		PriorityQueue<Slope> slopes;
		
		Node(int color,int cost,int count,Map<Integer,Integer> slopes){
			this.color=color;
			this.cost=cost;
			this.count=count;
			this.slopes= new PriorityQueue<Slope>((s1,s2)-> {
				return s1.count-s2.count;
			});
			for(Map.Entry<Integer,Integer> entry:slopes.entrySet()){
				this.slopes.add(new Slope(entry.getKey(),entry.getValue()));
			}
		}
		
	}
	
	static class Slope{
		int index;
		int count;
		Slope(int i,int c){
			index=i;
			count=c;
		}
	}
	
	
	static class Line{
		int a,b,c;
		Line(int a,int b,int c){
			this.a=a;
			this.b=b;
			this.c=c;
		}
	}
	
	private static void solve() throws IOException {
		
		Reader fr=new Reader(); 
		int t=fr.nextInt();
		List<Long> ans = new ArrayList<Long>();
		while(t>0) {
			int n,c,k;
			n=fr.nextInt();
			c=fr.nextInt();
			k=fr.nextInt();
			//List<Line> lines=new ArrayList<Line>(n+1); 
			//Map<Integer,Integer> colorMap=new HashMap<Integer, Integer>();
			int colorhm[]=new int[c+1];
			//also need to maintain a value for each colors
			Map<Integer,Map<Integer,Integer>> colorSlopeMap=new HashMap(); //colors -> slopw->count
			for(int i=0;i<n;++i) {
				int a1,b1,c1;
				a1=fr.nextInt();
				b1=fr.nextInt();
				c1=fr.nextInt();
				//lines.add(new Line(a1,b1,c1));
				colorhm[c1]++;
				if(colorSlopeMap.containsKey(c1)) {
					Map<Integer,Integer> tempMap=colorSlopeMap.get(c1);
					tempMap.put(a1,tempMap.getOrDefault(a1, 0)+1);
				}
				else {
					Map<Integer,Integer> temp=new HashMap<Integer,Integer>();
					temp.put(a1, 1);
					colorSlopeMap.put(c1, temp);
				}
				
			}
			int cost[]=new int[c];
			for(int i=0;i<c;++i) {
				cost[i]=fr.nextInt();
			}
			

			
			List<Node> list=new ArrayList<Node>(c+1);
			long totalTriangles=0;
			int maxFrequencyNode=0;

			for(int i=1;i<=c;++i) {
				Node newnode=new Node(i,cost[i-1],colorhm[i],colorSlopeMap.get(i));
				if(colorhm[i]>=3 && cost[i-1]!=0) {
					newnode=new Node(i,cost[i-1],colorhm[i],colorSlopeMap.get(i));
					list.add(newnode);
					maxFrequencyNode=Math.max(maxFrequencyNode, colorhm[i]);
					totalTriangles+=getTotal(newnode);
					
				}
//				if(colorhm[i]>=3 && cost[i-1]!=0) {
//					totalTriangles+=getTotal(newnode);
//				}
				
				
			}
//			for(int i=0;i<list.size();++i) {
//				totalTriangles+=getTotal(list.get(i));
//			}
			
			if(k==0) {
				ans.add(totalTriangles);
				--t;
				continue;
			}
			
			long dp[][][]= new long[list.size()+1][k+1][maxFrequencyNode+1];
			for(int i=1;i<=list.size();++i) {
				for(int j=1;j<=k;++j) {
					Arrays.fill(dp[i][j],-1);
				}
			}
			
			
			if(list.size()==1) {
				if(list.get(0).count==list.get(0).slopes.size()) {
					ans.add(getRemovedCountNoParallel(list.get(0).count,k,list.get(0).cost));
				}
				else {
					ans.add(getRemovedCountParallel(list.get(0),k));
				}
			}
			else if(list.size()==0) {
				ans.add((long) 0);
			}
			else{
				long finalTrianglesReduced=knapsack(list,list.size(),k,dp);
				ans.add(totalTriangles-finalTrianglesReduced);
			}
			--t;
		}
		ans.forEach((s)->System.out.println(s));
	}
	
	
	private static long knapsack(List<Node> list,int index,int k,long dp[][][]) {
		
		if(index==0 || k==0)
			return 0;
		
		Node cur=list.get(index-1);
		int tempF=cur.count;
		
		if(dp[index][k][cur.count]!=-1)
			return dp[index][k][cur.count];
		
		if(cur.cost>k || cur.count<3 || cur.slopes.size()<3) {
			dp[index][k][cur.count]=knapsack(list,index-1,k,dp);
			return dp[index][k][cur.count];
			}
		else {
			long costCur=getCurrentProfit(cur);//getncr(cur.count, 3)-getncr(cur.count-1, 3);
			
			Slope edgeDelete=cur.slopes.poll();
			
			int c=edgeDelete.count;
			
			edgeDelete.count--;
			if(c>1) {
				list.get(index-1).slopes.add(edgeDelete);
			}
			list.get(index-1).count=tempF-1;
			
			long usedCount=costCur+knapsack(list,index,k-cur.cost,dp);
			list.get(index-1).count=tempF;
			if(c==1) {
				edgeDelete.count++;
				list.get(index-1).slopes.add(edgeDelete);
			}
			else {
				edgeDelete=list.get(index-1).slopes.poll();
				edgeDelete.count++;
				list.get(index-1).slopes.add(edgeDelete);
			}
			long unusedCount=knapsack(list, index-1, k,dp);
			dp[index][k][cur.count]=Math.max(usedCount, unusedCount);
			return dp[index][k][cur.count];
		}
		
	}
	
	
	private static long getTrianglesParallel(PriorityQueue<Slope> slopes) {
		List<Integer> temp=new ArrayList<Integer>();
//		for(Map.Entry<Integer, Integer> entry:slopes.entrySet()) {
//			//if(entry.getValue()>0)
//				temp.add(entry.getValue());
//		}
		slopes.forEach((s)->{
			temp.add(s.count);
		});
		int sum1=0;
		int k=temp.size();
		for(int i=0;i<k;++i) {
			sum1+=temp.get(i);
		}
		int sum2=0;
		int subSet[]=new int[k+1];
		for(int i=0;i<k;++i) {
			subSet[i]=temp.get(i)*(sum1-temp.get(i));
			sum2+=subSet[i];
		}
		sum2/=2;
		int sum3=0;
		for(int i=0;i<k;++i) {
			sum3+=temp.get(i)*(sum2-subSet[i]);
		}
		sum3/=3;
		
		
		return sum3;
	}
	
	private static long getTotal(Node node) {
		if(node.count==node.slopes.size()) {
			return getncr(node.count, 3);
		}
		else {
			return getTrianglesParallel(node.slopes);
		}
	}
	
	
	private static long getCurrentProfit(Node node) {
		if(node.count==node.slopes.size()) {
			return getncr(node.count-1, 2);
		}
		else {
			//calculate the maxProfit achievavle by deleting line from map which contains the min number of lines
			
			long prev=getTrianglesParallel(node.slopes);
			Slope small=node.slopes.poll();
			small.count--;
			node.slopes.add(small);
			long nowcost=getTrianglesParallel(node.slopes);
			small=node.slopes.poll();
			small.count++;
			node.slopes.add(small);
			return prev-nowcost;
		}
	}
	
//	private static int findSmallestNumSlope(Node node) {
//		int minSlope=-1;
//		int minSlopeCount=Integer.MAX_VALUE;
//		for(Map.Entry<Integer, Integer> entry:node.slopes.entrySet()) {
//			if(entry.getValue()<minSlopeCount) {
//				minSlopeCount=entry.getValue();
//				minSlope=entry.getKey();
//			}
//		}
//		return minSlope;
//	}
	
	private static long getncr(int n,int k) {
		
		if(n<k)
			return 0;
		long res = 1; 
		  
	    // Since C(n, k) = C(n, n-k) 
	    if (k > n - k) 
	        k = n - k; 
	  
	    // Calculate value of 
	    // [n * (n-1) *---* (n-k+1)] / [k * (k-1) *----* 1] 
	    for (int i = 0; i < k; ++i) { 
	        res *= (n - i); 
	        res /= (i + 1); 
	    } 
	  
	    return res; 
	}
	
	private static long getRemovedCountNoParallel(int size,int k,int cost) {
		
		int count=k/cost;
		
		return getncr(size-count, 3);
	}
	
	private static long getRemovedCountParallel(Node node,int k) {
		int count=k/node.cost;
		PriorityQueue<Slope> pq=node.slopes;
		while(count>0) {
			Slope f=pq.poll();
			if(f.count>1) {
				f.count--;
				pq.add(f);
			}
			--count;
		}
		return getTrianglesParallel(pq);
	}
	

	
	
	
}
