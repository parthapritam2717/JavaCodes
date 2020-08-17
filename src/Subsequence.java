

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Subsequence {
	
	
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

	
	private static long mod=1000000007;
	
	private static void solve() {
		
		FastReader fr=new FastReader(); 
		int t=fr.nextInt();
		//List<Integer> ans = new ArrayList<Integer>();
		StringBuilder sb=new StringBuilder();
		while(t>0) {
			int n;
			n=fr.nextInt();
			//int arr[]=new int[n];
			Map<Integer,Integer> arr=new HashMap<Integer, Integer>();
			for(int i=0;i<n;++i) {
				int item=fr.nextInt();
				arr.put(item, arr.getOrDefault(item, 0)+1);
			}
			
			if(arr.size()==n) {
				//all perm present
				allNPresent(sb,n);
			}
			else {
				//general case
				generalN(sb,n,arr);
				//System.out.println("0");
			}
			--t;
			
		}
		System.out.println(sb.toString());
	}
	
	
	private static void generalN(StringBuilder sb,int n,Map<Integer,Integer> arr) {
		long ans[]=new long[n+1];
		int totalNum=n;
		List<Integer> keys=new ArrayList<Integer>();
		for(Map.Entry<Integer, Integer> entry:arr.entrySet()) {
			keys.add(entry.getKey());
		}
		Collections.sort(keys);
		
			
			
		
		for(int i=1;i<=n;++i) {
			sb.append(Long.toString(ans[i])+" ");
			
		}
		sb.append("\n");
	}
	
	private static void allNPresent(StringBuilder sb,int n) {
		//lets solve for 1..n permutation for now
		long ans[]=new long[n+1];
		for(int i=1;i<=n;++i) {
			ans[i]=getSolution(i,n);
		}
		
		
		for(int i=1;i<=n;++i) {
			sb.append(Long.toString(ans[i])+" ");
			
		}
		sb.append("\n");
	}
	
	
	private static long getSolution(int i,int n) {
		int end=n-i;
		return myPow(2,end);		
	}
	
	private  static long myPow(long x, long n) {
		long mod=1000000007;
        long N = n;
        long ans = 1;
        long current_product = x;
        for (long i = N; i > 0; i /= 2) {
            if ((i % 2) == 1) {
                ans = (ans * current_product)%mod;;
            }
            current_product = (current_product * current_product)%mod;
        }
        return ans;
    }
	
	
	
	
	private static long getncr(int n,int k) {
		
		int mod=1000000007;
		
		if(n<k)
			return 0;
		
		long res = 1; 
		  
	   
	    if (k > n - k) 
	        k = n - k; 
	    for (int i = 0; i < k; ++i) { 
	        res =res* (n - i); 
	        res =res/ (i + 1);
	        res=res%mod;
	    } 
	  
	    return res; 
	}
}
	
	