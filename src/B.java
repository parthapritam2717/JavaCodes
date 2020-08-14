

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

public class B {
	
	
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

	
	
	
	private static void solve() {
		
		FastReader fr=new FastReader(); 
		int t=fr.nextInt();
		List<Integer> ans = new ArrayList<Integer>();
		while(t>0) {
			int n,m;
			n=fr.nextInt();
			m=fr.nextInt();
			List<String> grid= new ArrayList<String>();
			for(int i=0;i<n;++i) {
				grid.add(fr.nextLine());
			}
			int count=0;
			for(int i=0;i<n;++i) {
				if(grid.get(i).charAt(m-1)=='R') {
					count++;
				}
			}
			for(int i=0;i<m;++i) {
				if(grid.get(n-1).charAt(i)=='D') {
					count++;
				}
			}
			ans.add(count);
			
			--t;
		}
		ans.forEach((s)->System.out.println(s));
	}
	
	

	
}
