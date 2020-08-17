

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;



class ColorCost1 {
	
	
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
	
	public static void main(String[] args) throws Exception {
		solve();
	}

	
	static class Node{
		int color;
		int cost;
		int count;
		Map<Integer,Integer> slopes;
		long profit;
		Node(int color,int cost,int count,Map<Integer,Integer> slopes){
			this.color=color;
			this.cost=cost;
			this.count=count;
			this.slopes=slopes;
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
	

	private static void solve() throws Exception {
		//ncrCalc();
		//try{
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
				if(colorhm[i]>=3 && cost[i-1]!=0 ) {
					list.add(newnode);
					maxFrequencyNode=Math.max(maxFrequencyNode, colorhm[i]);
					newnode.profit=getCurrentProfit(newnode);
					totalTriangles+=getTotal(newnode);
				}		
				
			}
			if(k==0) {
				ans.add(totalTriangles);
				--t;
				continue;
			}
			else {
				
				//Now will have to create cost objects and  insert into the costpq
				Map<Integer,List<Node>> temphm= new HashMap<Integer, List<Node>>();
				for(Node node:list) {
					if(temphm.containsKey(node.cost)) {
						temphm.get(node.cost).add(node);
					}
					else {
						List<Node> temp=new ArrayList<Node>();
						temp.add(node);
						temphm.put(node.cost, temp);
					}
				}
				List<Cost> costlist=new ArrayList<Cost>();
				int maxcount=0;
				for(Map.Entry<Integer, List<Node>> entry:temphm.entrySet()) {
					PriorityQueue<Node> temppq=new PriorityQueue<Node>((n1,n2)-> {
						return Long.compare(n2.profit, n1.profit);
					});
					int count=0;
					if(entry.getKey()>k) {
						continue;
					}
					for(Node node:entry.getValue()) {
						temppq.add(node);
						count+=node.count;
						maxcount=Math.max(maxcount, node.count);
					}
					maxcount=Math.max(maxcount,count);
					Cost costnode=new Cost(entry.getKey(),temppq,count);
					//costpq.add(costnode);
					costlist.add(costnode);
				}
				//calculate(costpq,k,ans);
				Dpdata dp[][]= new Dpdata[costlist.size()+1][3000+1];
//				for(int j=1;j<=costlist.size();++j) {
//					
//						Arrays.fill(dp[j], -1);
//					//}
//				}
				long unused[][]=new long[costlist.size()+1][k+1];
				for(int i=1;i<=costlist.size();++i) {
					Arrays.fill(unused[i], -1);
				}
				long removedTriangles=knapsack2(costlist,costlist.size(),k,dp,unused).value;
				ans.add(totalTriangles-removedTriangles);
				
			}
			
			
			--t;
		}
		ans.forEach((s)->System.out.println(s));
		
		
//		}catch (Exception e) {
//			System.out.println(e);
//		}
	}
	
	
	
	
	
	static class Cost{
		int cost;
		PriorityQueue<Node> pq;
		int count;
		Cost(int cost,PriorityQueue<Node> pq,int c){
			this.cost=cost;
			this.pq=pq;
			this.count=c;
		}
	}
	
	
	private static int getSumSlopes(PriorityQueue<Node> pq) {
		int sum=0;
		for(Node node:pq) {
			if(node.slopes.size()>2) {
				sum++;
			}
		}
		return sum;
		
	}
	
	static class Dpdata{
		int kvalue;
		long val;
		int samecount;
		Cost cost;
		long sameSum;
		Dpdata(int k,long val,int sameCount,Cost cost,long sum){
			this.kvalue=k;
			this.val=val;
			this.samecount=sameCount;
			this.cost=cost;
			this.sameSum=sum;
			
		}
		@Override
		public String toString() {
			return "K="+kvalue+','+"val="+val+","+"sameCount="+samecount;
		}
	}
	
	static class Pair{
		long value;
		boolean used;
		int sameCount;
		long samesum;
		Pair(long v,boolean u,int s,long samesum){
			value=v;
			used=u;
			sameCount=s;
			this.samesum=samesum;
		}
		
		
	}
	
	private static Pair knapsack2(List<Cost> list,int index,int k,Dpdata dp[][],long unused[][]) throws Exception{
		if(index==0 || k==0)
			return new Pair(0, false, 0,0);
		Cost cur=list.get(index-1);
		
		
		if(dp[index][cur.count]!=null) {
			//System.out.println("Found previous saved data"+index+","+cur.count+','+k+"="+dp[index][cur.count]);
			//will do some calculation and return the data
		
			Dpdata d=dp[index][cur.count];
			if(k==d.kvalue) {
				return new Pair(dp[index][cur.count].val,dp[index][cur.count].samecount>0?true:false,dp[index][cur.count].samecount,dp[index][cur.count].sameSum); 
			}
			else if(k<d.kvalue){
				int sameCountusedPrev=d.samecount;
				if(sameCountusedPrev==0)
					new Pair(dp[index][cur.count].val,dp[index][cur.count].samecount>0?true:false,dp[index][cur.count].samecount,dp[index][cur.count].sameSum); 
				
				int cost=cur.cost;
				int ktillprev=d.kvalue-cost*sameCountusedPrev;
				if(ktillprev<k){
					int accomodatenow=(k-ktillprev)/cost;
					long profitEarn=getProfitFromCostObject(d.cost,accomodatenow); //get the profit from removing these number of same objects
				
					long unuseddata=unused[index-1][ktillprev];
					
					return new Pair(profitEarn+unuseddata,accomodatenow>0?true:false,accomodatenow,profitEarn);
				}
								
				 
			}
		}
		
			
		
		//int presentCount=cur.count;
		//if(cur.cost>k || cur.count<3 || getSumSlopes(cur.pq)<1) {
		if(cur.cost>k || cur.count<3 ) {
			Pair res= knapsack2(list,index-1,k,dp,unused);
			dp[index][cur.count]=new Dpdata(k, res.value, 0,cur,0);
			return new Pair(res.value,false,0,0);
		}
		
		//first lets see the unused
		long unusedVal=0;
		if(unused[index][k]!=-1) {
			unusedVal=unused[index][k];
		}
		else {
			unusedVal=knapsack2(list, index-1, k, dp,unused).value;
			unused[index][k]=unusedVal;
		}
		
		
		//Now will have to do the operation for using
		Node colorCluster=null;
		long profitbyUsing=0;
		int edgeDelete=0;
		Pair ans=null;
		try{
			colorCluster=cur.pq.poll();
			profitbyUsing=getCurrentProfit(colorCluster);
			edgeDelete=reduceline(colorCluster);
			cur.count--;
			colorCluster.profit=getCurrentProfit(colorCluster);
			cur.pq.add(colorCluster);
			ans=knapsack2(list,index,k-cur.cost,dp,unused);
			cur.count++;
			revertremovedLine(colorCluster,edgeDelete);
			
		
		
		
		//dp[index][cur.count]=Math.max(unusedVal, profitbyUsing+ans);	
		
		if(unusedVal>ans.value+profitbyUsing) {
			dp[index][cur.count]=new Dpdata(k, unusedVal, 0,cur,0);
		}
		else {
			dp[index][cur.count]=new Dpdata(k, ans.value+profitbyUsing, ans.sameCount+1,cur,ans.samesum+profitbyUsing);
		}
		
		
		//System.out.println("New saved data"+index+","+cur.count+','+k+"="+dp[index][cur.count]);
		
		}catch (Exception e) {
			
		}
		
		return new Pair(dp[index][cur.count].val,dp[index][cur.count].samecount>0?true:false,dp[index][cur.count].samecount,dp[index][cur.count].sameSum);
		
		
		
	}
	
	private static long getProfitFromCostObject(Cost cost,int c) {
		try{
			long amount=0;
		
		PriorityQueue<Node> pq1=new PriorityQueue<Node>(cost.pq.size(),(n1,n2)-> {
			return Long.compare(n2.profit, n1.profit);
		});
		for(Node node:cost.pq) {
			pq1.add(node);
		}
		while(c>0 && !pq1.isEmpty()) {
			Node p=pq1.poll();
			amount+=getCurrentProfit(p);
			reduceline(p);
			if(p.count>=3)
				p.profit=getCurrentProfit(p);
				pq1.add(p);
			--c;
		}
		
		return amount;
		
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	
	private static int reduceline(Node cur) throws Exception{
		int edgeDelete=findSmallestNumSlope(cur);
		
		if(cur.slopes.get(edgeDelete)==1) {
			cur.slopes.remove(edgeDelete);
		}
		else {
			cur.slopes.put(edgeDelete,cur.slopes.get(edgeDelete)-1);
		}
		cur.count--;
		return edgeDelete;
	}
	
	private static void revertremovedLine(Node cur ,int edgeDelete) throws Exception{
		cur.count++;
		if(cur.slopes.containsKey(edgeDelete)) {
			cur.slopes.put(edgeDelete,cur.slopes.get(edgeDelete)+1);
		}
		else {
			cur.slopes.put(edgeDelete,1);
		}
		cur.profit=getCurrentProfit(cur);
	}
	
	
	
	private static long getTrianglesParallel(Map<Integer,Integer> slopes) throws Exception{
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
	
	private static long getTotal(Node node) throws Exception{
		if(node !=null && (node.count==node.slopes.size())) {
			return getncr(node.count, 3);
		}
		
		return 0;
//		else {
//			return getTrianglesParallel(node.slopes);
//		}
	}
	
	
	private static long getCurrentProfit(Node node)throws Exception{
		if(node!=null && node.count==node.slopes.size()) {
			return getncr(node.count-1, 2);
		}
		return 0;
//		else {
//			//calculate the maxProfit achievavle by deleting line from map which contains the min number of lines
//			
//			int deletEdge=findSmallestNumSlope(node);
//			Map<Integer,Integer> temp=node.slopes;
//			long prev=getTrianglesParallel(node.slopes);
//			int deleteCount=temp.get(deletEdge);
//			node.slopes.put(deletEdge, deleteCount-1);
//			long nowcost=getTrianglesParallel(node.slopes);
//			node.slopes.put(deletEdge, deleteCount);
//			return prev-nowcost;
//		}
	}
	
	private static int findSmallestNumSlope(Node node) throws Exception{
		if(node.count==node.slopes.size()) {
			for(Map.Entry<Integer, Integer> entry:node.slopes.entrySet()) {
				return entry.getKey();
			}
			
		}
		
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
		
//		if(ncr[n][k]!=0)
//			return ncr[n][k];
		
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
	    //ncr[n][k]=res;
	    return res; 
	}
	
	
private static long getRemovedCountParallel(Node node,int k) throws Exception{
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
