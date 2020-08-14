

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

class Test2 {
	
	
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
		List<Integer> ans = new ArrayList<Integer>();
		while(t>0) {
			int n,k;
			n=fr.nextInt();
			k=fr.nextInt();
			int family[]=new int[n];
			for(int i=0;i<n;++i) {
				family[i]=fr.nextInt();
			}
			int dp[]=new int[n+1];
			Arrays.fill(dp, -1);
			
			Map<Integer,Integer> hm=new HashMap<Integer,Integer>();
			//hm.put(family[n-1], 1);
			
			int minCost= getCost(n,family,k,k,hm,dp);
			ans.add(minCost);
			
			--t;
		}
		StringBuilder res=new StringBuilder();
		ans.forEach((s)->res.append(Integer.toString(s)+"\n"));
		System.out.println(res.toString());
	}

	
	
	private static int getCost(int i,int family[],int k,int totalCost,Map<Integer,Integer> hm,int dp[]) {
		
		if(i==0)
			return totalCost;
		
		
		//if(dp)
		
		if(hm.containsKey(family[i-1])) {
			hm.put(family[i-1], hm.get(family[i-1])+1);
			int costIncluded;
			if(hm.get(family[i-1])>2) {
				costIncluded=getCost(i-1,family,k,totalCost+1,hm,dp);
			}
			else {
				costIncluded=getCost(i-1,family,k,totalCost+2,hm,dp);
			}
					
			Map<Integer,Integer> hmap=new HashMap<Integer,Integer>();
			hmap.put(family[i-1], 1);
			
			int costNewTable=getCost(i-1,family,k,totalCost+k,hmap,dp);
			
			
			return Math.min(costIncluded, costNewTable);
			
		}
		else {
			hm.put(family[i-1], 1);
			return getCost(i-1,family,k,totalCost,hm,dp);
		}
		
	}
	
	

}
