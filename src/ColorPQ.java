

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class ColorPQ {
	
	
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
		int slope;
		Node(int color,int cost,int count,int slope){
			this.color=color;
			this.cost=cost;
			this.count=count;
			this.slope=slope;
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
			List<Line> lines=new ArrayList<Line>(); 
			Map<Integer,Integer> colorMap=new HashMap<Integer, Integer>();
			for(int i=0;i<n;++i) {
				int a1,b1,c1;
				a1=fr.nextInt();
				b1=fr.nextInt();
				c1=fr.nextInt();
				lines.add(new Line(a1,b1,c1));
				if(colorMap.containsKey(c1)) {
					colorMap.put(c1, colorMap.get(c1)+1);
				}
				else {
					colorMap.put(c1,1);
				}
			}
			int cost[]=new int[c];
			for(int i=0;i<c;++i) {
				cost[i]=fr.nextInt();
			}
			
			//now will create the pq with nodes containing the color cost of erase adn frequency
			PriorityQueue<Node> pq= new PriorityQueue<Node>(c,(n1,n2)-> {
				double profit2cost1=(double)getncr(n1.count,3)/(double)n1.cost;
				
				double profit2cost2=(double)getncr(n2.count,3)/(double)n2.cost;
				
				if(Double.compare(profit2cost2, profit2cost1)==0) {
					return n1.cost-n2.cost;
				}
				return Double.compare(profit2cost2, profit2cost1);
				
				
			});
			
			for(Map.Entry<Integer, Integer> entry:colorMap.entrySet()) {
				if(entry.getValue()>=3) {
					int colF=entry.getValue();
					int colIndex=entry.getKey();
					int removeC=cost[colIndex-1];
					if(removeC!=0)
						pq.add(new Node(colIndex,removeC,colF,-1));
				}
			}
			long finalTriangles=count(pq,k);
			ans.add(finalTriangles);
			--t;
		}
		ans.forEach((s)->System.out.println(s));
	}
	
	

	
	private static long count(PriorityQueue<Node> pq,int k) {
		
		long count=0;
		
		while(k>0 && !pq.isEmpty()) {
			Node f=pq.poll();
			if(f.cost<=k) {
				if(f.count>3)
					pq.add(new Node(f.color,f.cost,f.count-1,-1));
				k-=f.cost;
			}
			else {
				//cannot remove any more edge of this color so will have to remove entireluy and add to the count
				
				count+=getncr(f.count, 3);
			}
			
		}
		while(!pq.isEmpty()) {
			Node f=pq.poll();
			count+=getncr(f.count, 3);
		}
		
		return count;
	}
	
	
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
	
	
}
