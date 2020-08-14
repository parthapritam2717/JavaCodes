

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

class SubRec {
	
	
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
		while(t>0) {
			int n;
			n=fr.nextInt();
			int arr[]=new int[n];
			for(int i=0;i<n;++i) {
				arr[i]=fr.nextInt();
			}
			
			Map<Integer,Integer> count = new HashMap<Integer, Integer>();
			
			Map<Integer,Integer> globalCount = new HashMap<Integer, Integer>();
			
			recursive(0,arr,count,globalCount);
			List<Integer> ans= new ArrayList<Integer>(n);
			
			for(int i=0;i<n;++i) {
				ans.add(i,globalCount.getOrDefault(i+1, 0));
			}
			
			ans.forEach((s)->System.out.print(s+" "));
			System.out.println();
			
			--t;
			
		}
		
	}
	
	private static int getmostOccurSmallest(Map<Integer,Integer> map) {
		int maxKey=0;
		int maxCount=0;
		for(Map.Entry<Integer, Integer> entry: map.entrySet()) {
			if(entry.getValue()>maxCount) {
				maxCount=entry.getValue();
				maxKey=entry.getKey();
			}
			else if(entry.getValue()==maxCount && entry.getKey()<maxKey) {
				maxKey=entry.getKey();
			}
		}
		return maxKey;
	}
	
	private static void recursive(int index,int arr[],Map<Integer,Integer> count,Map<Integer,Integer> globalCount) {
		
		//pick index element as part of subsequence
		
		final int mod= 1000000007;
		
		if(index==arr.length)
			return;
		if(count.containsKey(arr[index])) {
			count.put(arr[index], count.get(arr[index])+1);
		}
		else {
			count.put(arr[index],1);
		}
		int maxNum=getmostOccurSmallest(count);
		if(globalCount.containsKey(maxNum)) {
			int item=globalCount.get(maxNum)+1;
			item%=mod;
			globalCount.put(maxNum, item);
		}
		else {
			globalCount.put(maxNum, 1);
		}
		recursive(index+1,arr,count,globalCount);
		
		if(count.get(arr[index])==1) {
			count.remove(arr[index]);
		}
		else {
			count.put(arr[index], count.get(arr[index])-1);
		}
		
		recursive(index+1,arr,count,globalCount);
	}

	
	
	


}
