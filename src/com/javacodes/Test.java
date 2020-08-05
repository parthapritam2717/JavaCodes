//package com.javacodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Test {
	
	
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
	
	
	private static void solve() {
		
		FastReader fr=new FastReader(); 
		int t;
		t=fr.nextInt();
		List<String> ans= new ArrayList<String> (t);
		while(t>0) {
			String s=fr.next();
			if(s.length()==2) {
				ans.add(s);
			}
			else {
				StringBuilder sb=new StringBuilder();
				sb.append(s.charAt(0));
				for(int i=2;i<s.length();i+=2) {
					sb.append(s.charAt(i));
				}
				sb.append(s.charAt(s.length()-1));
				ans.add(sb.toString());
			}
			--t;
			
		}
		ans.forEach((s)->System.out.println(s));
	}

	public static void main(String[] args) {
		solve();

	}

}
