

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;



class ColorCount {
	
	
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
		Map<Integer,Integer> slopes;
		Node(int color,int cost,int count,Map<Integer,Integer> slopes){
			this.color=color;
			this.cost=cost;
			this.count=count;
			this.slopes=slopes;
//			this.slopes=new HashMap<Integer, Integer>();
//			this.slopes.putAll(slopes);
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
	private static long ncr[][]=new long [3001][4];
	
	private static void ncrCalc() {
		//ncr[0][0]=1;
		for(int i=0;i<=3000;++i) {
			int end=i<=3?i:3;
			for(int j=0;j<=end;++j) {
				if(j==0 || i==0) {
					ncr[i][j]=1;
				}
				else{
					ncr[i][j]=ncr[i-1][j]+ncr[i-1][j-1];
				}
			}
		}
	}
	
	private static void solve() throws IOException {
		//ncrCalc();
		Reader fr=new Reader(); 
		int t=fr.nextInt();
		List<Long> ans = new ArrayList<Long>(t);
		while(t>0) {
			int n,c,k;
			n=fr.nextInt();
			c=fr.nextInt();
			k=fr.nextInt();
			//List<Line> lines=new ArrayList<Line>(n+1); 
			//Map<Integer,Integer> colorMap=new HashMap<Integer, Integer>();
			int colorhm[]=new int[c+1];
			//also need to maintain a value for each colors
			//Map<Integer,Map<Integer,Integer>> colorSlopeMap=new HashMap(); //colors -> slopw->count
			for(int i=0;i<n;++i) {
				int a1,b1,c1;
				a1=fr.nextInt();
				b1=fr.nextInt();
				c1=fr.nextInt();
				//lines.add(new Line(a1,b1,c1));
				colorhm[c1]++;
//				if(colorSlopeMap.containsKey(c1)) {
//					Map<Integer,Integer> tempMap=colorSlopeMap.get(c1);
//					tempMap.put(a1,tempMap.getOrDefault(a1, 0)+1);
//				}
//				else {
//					Map<Integer,Integer> temp=new HashMap<Integer,Integer>();
//					temp.put(a1, 1);
//					colorSlopeMap.put(c1, temp);
//				}
				
			}
			int cost[]=new int[c];
			for(int i=0;i<c;++i) {
				cost[i]=fr.nextInt();
			}
			

			
			List<Node> list=new ArrayList<Node>(c+1);
			long totalTriangles=0;
			int maxFrequencyNode=0;

			for(int i=1;i<=c;++i) {
				//Node newnode=new Node(i,cost[i-1],colorhm[i],null);
				if(colorhm[i]>=3 && cost[i-1]!=0) {
					Node newnode=new Node(i,cost[i-1],colorhm[i],null);
					list.add(newnode);
					maxFrequencyNode=Math.max(maxFrequencyNode, colorhm[i]);
					
				}
//				if(colorhm[i]>=3 && cost[i-1]!=0 ) {
//					totalTriangles+=getTotal(newnode);
//				}
				
				
			}

			int size=list.size();
			
			
			//check if all elemnsts available are of same color
			boolean flag=true;
			for(int i=1;i<size;++i) {
				if(list.get(i-1).cost!=list.get(i).cost) {
					flag=false;
					break;
				}
			}
			
			if(flag) {
				//all cost are same
				PriorityQueue<Node> pq=new PriorityQueue<Node>((n1,n2)-> {
					return Long.compare(getncr(n2.count-1, 2),getncr(n1.count-1, 2));
				});
				for(int i=0;i<size;++i) {
					pq.add(list.get(i));
				}
				long amount=0;
				while(k>0 && !pq.isEmpty()) {
					Node f=pq.poll();
					if(f.cost<=k) {
						if(f.count>3) {
							f.count--;
							pq.add(f);
						}
						k-=f.cost;		
					}
					else {
						amount+=getncr(f.count, 3);
					}
					
				}
				
				while(!pq.isEmpty()) {
					Node f=pq.poll();
					amount+=getncr(f.count, 3);
				}
				ans.add(amount);
			}
			else if(list.size()==0) {
				ans.add((long) 0);
			}
			else{
				
				for(int i=0;i<size;++i) {
					totalTriangles+=getncr(list.get(i).count,3);
				}
				
				if(k==0) {
					ans.add(totalTriangles);
					--t;
					continue;
				}
				long profit[][]= new long[size+1][maxFrequencyNode+1];
				populateProfit(list,size,profit);
				
				//populate the data for dp which will store the max profit you can achieve at a given index and count
				long dp[][][]= new long[list.size()+1][k+1][maxFrequencyNode+1];
//				for(int i=1;i<=list.size();++i) {
//					for(int j=1;j<=k;j++) {
//						Arrays.fill(dp[i][j], -1);
//					}
//				}
				long unused[][]=new long[size+1][k+1];
//				for(int i=0;i<=list.size();++i) {
//					Arrays.fill(unused[i], -1);
//				}
				
				
				long finalTrianglesReduced=knapsack(list,size,k,dp,profit,unused);
				ans.add(totalTriangles-finalTrianglesReduced);
//				long dp[]=new long[k+1];
//				Arrays.fill(dp, -1);
//				long finalTrianglesReduced=knapsack2(list,list.size(),k,dp,profit);
//				if(finalTrianglesReduced==-1) {
//					ans.add(totalTriangles);
//				}
//				else {
//					ans.add(totalTriangles-finalTrianglesReduced);
//				}
			}
			--t;
		}
		StringBuilder sb=new StringBuilder();
		ans.forEach((s)->sb.append(s+"\n"));
		System.out.println(sb.toString());
	}
	
	
	
	private static void populateProfit(List<Node> list,int index,long profit[][]) {
		int size=list.size();
		for(int i=1;i<=size;++i) {
			for(int j=list.get(i-1).count;j>=3;--j) {
				profit[i][j]=getncr(j-1, 2);
			}
		}
	}
	
	private static long knapsack2(List<Node> list,int index,int k,long dp[],long profit[][]) {
		if(k==0)
			return 0;
		long res=-1;
		
		if(dp[k]!=-1)
			return dp[k];
		
		for(int i=1;i<=index;++i) {
			Node cur=list.get(i-1);
			if(cur.cost<=k && cur.count>=3) {
				//long costCur=getCurrentProfit(cur);
				long costCur=profit[i][cur.count];
				int tempCount=cur.count;
				cur.count--;
				long tempRes=knapsack2(list, index, k-cur.cost,dp,profit);
				if(tempRes==-1)
					tempRes=0;
				if(tempRes+costCur>res) {
					res=tempRes+costCur;
				}
				cur.count=tempCount;
				
			}
		}
		dp[k]=res;
		return res;
	}
	
	
	private static long knapsack(List<Node> list,int index,int k,long dp[][][],long profit[][],long unused[][]) {
		
		if(index==0 || k==0)
			return 0;
		
		Node cur=list.get(index-1);
		int tempF=cur.count;
		
		if(dp[index][k][cur.count]!=0)
			return dp[index][k][cur.count];
		
		if(cur.cost>k || cur.count<3 ) {
			dp[index][k][cur.count]=knapsack(list,index-1,k,dp,profit,unused);
			return dp[index][k][cur.count];
			}
		else {
			//long costCur=getCurrentProfit(cur);//getncr(cur.count, 3)-getncr(cur.count-1, 3);
			long costCur=profit[index][cur.count];
//			int edgeDelete=findSmallestNumSlope(cur);
//			
//			if(list.get(index-1).slopes.get(edgeDelete)==1) {
//				list.get(index-1).slopes.remove(edgeDelete);
//			}
//			else {
//				list.get(index-1).slopes.put(edgeDelete,list.get(index-1).slopes.get(edgeDelete)-1);
//			}
			list.get(index-1).count=tempF-1;
			long usedCount=costCur+knapsack(list,index,k-cur.cost,dp,profit,unused);
			list.get(index-1).count=tempF;
//			if(list.get(index-1).slopes.containsKey(edgeDelete)) {
//				list.get(index-1).slopes.put(edgeDelete,list.get(index-1).slopes.get(edgeDelete)+1);
//			}
//			else {
//				list.get(index-1).slopes.put(edgeDelete,1);
//			}
			long unusedCount=0;
			if(unused[index][k]==0) {
				unusedCount=knapsack(list, index-1, k,dp,profit,unused);
				unused[index][k]=unusedCount;
			}
			else {
				unusedCount=unused[index][k];
			}
			dp[index][k][cur.count]=Math.max(usedCount, unusedCount);
			return dp[index][k][cur.count];
		}
		
	}
	
	
	private static long getTrianglesParallel(Map<Integer,Integer> slopes) {
		List<Integer> temp=new ArrayList<Integer>(200);
		for(Map.Entry<Integer, Integer> entry:slopes.entrySet()) {
			//if(entry.getValue()>0)
				temp.add(entry.getValue());
		}
		long sum1=0;
		int k=temp.size();
		for(int i=0;i<k;++i) {
			sum1+=temp.get(i);
		}
		long sum2=0;
		long subSet[]=new long[k+1];
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
		//if(node.count==node.slopes.size()) {
			return getncr(node.count, 3);
//		}
//		else {
//			return getTrianglesParallel(node.slopes);
//		}
	}
	
	
	private static long getCurrentProfit(Node node) {
		if(node.count==node.slopes.size()) {
			return getncr(node.count-1, 2);
		}
		else {
			//calculate the maxProfit achievavle by deleting line from map which contains the min number of lines
			
			int deletEdge=findSmallestNumSlope(node);
			Map<Integer,Integer> temp=node.slopes;
			long prev=getTrianglesParallel(node.slopes);
			int deleteCount=temp.get(deletEdge);
			node.slopes.put(deletEdge, deleteCount-1);
			long nowcost=getTrianglesParallel(node.slopes);
			node.slopes.put(deletEdge, deleteCount);
			return prev-nowcost;
		}
	}
	
	private static int findSmallestNumSlope(Node node) {
		int minSlope=-1;
		int minSlopeCount=Integer.MAX_VALUE;
		for(Map.Entry<Integer, Integer> entry:node.slopes.entrySet()) {
			if(entry.getValue()<minSlopeCount) {
				minSlopeCount=entry.getValue();
				minSlope=entry.getKey();
			}
		}
		return minSlope;
	}
	
	private static long getncr(int n,int k) {
		
		if(ncr[n][k]!=0)
			return ncr[n][k];
		
		if(n<k)
			return 0;
		
		//return ncr[n][k];
		
		long res = 1; 
		  
	    if (k > n - k) 
	        k = n - k; 
	    for (int i = 0; i < k; ++i) { 
	        res *= (n - i); 
	        res /= (i + 1); 
	    } 
	    ncr[n][k]=res;
	    return res; 
	}
	
	private static long getRemovedCountNoParallel(int size,int k,int cost) {
		
		int count=k/cost;
		
		return getncr(size-count, 3);
	}
	
	private static long getRemovedCountParallel(Node node,int k) {
		int count=k/node.cost;
		PriorityQueue<Slope> pq=new PriorityQueue<Slope>(100,(n1,n2)-> {
			return n1.count-n2.count;
		});
		for(Map.Entry<Integer, Integer> entry:node.slopes.entrySet()) {
			pq.add(new Slope(entry.getKey(),entry.getValue()));
		}
		while(count>0) {
			Slope f=pq.poll();
			if(f.count>1) {
				f.count--;
				pq.add(f);
			}
			--count;
		}
		Map<Integer,Integer> temp=new HashMap();
		while(!pq.isEmpty()) {
			temp.put(pq.peek().index, pq.peek().count);
			pq.poll();
		}
		return getTrianglesParallel(temp);
		
		
	}
	
	static class Slope{
		int index;
		int count;
		Slope(int i,int c){
			index=i;
			count=c;
		}
	}
	
	
	
}
