

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class C {
	
	
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

	
	private static long getfact(int n) {
		long fact=1;
		int mod=1000000007;
		for(long i=1;i<=n;++i) {
			fact=(fact*i)%mod;
		}
		return fact;
	}
	
	
	private static long pow(int n) {
		long p=1;
		int mod=1000000007;
		while(n>0) {
			p=(p*2)%mod;
			--n;
		}
		return p;
	}
	
	private static void solve() {
		
		FastReader fr=new FastReader(); 
		int n=fr.nextInt();
		long fact=getfact(n);
		long pw=pow(n-1);
		int mod=1000000007;
		System.out.println((fact%mod-pw%mod+mod)%mod);
		
		
		
		
	}
	
	

	
}
