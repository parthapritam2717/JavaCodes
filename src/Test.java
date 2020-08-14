

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

class Test {
	
	
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

	
	static class Node{
		int cost;
		Map<Integer,Integer> map;
		//int index;
		Node(int cost,Map<Integer,Integer> map){
			this.map=new HashMap<Integer, Integer>();
			this.map.putAll(map);
			this.cost=cost;
			//this.index=index;
		}
		
	}
	
	private static void solve() {
		
		FastReader fr=new FastReader(); 
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
			int cost =getCost(family,k);
			ans.add(cost);
			--t;
		}
		ans.forEach((s)->System.out.println(s));
	}
	
	
	private static int getCost(int family[],int k) {
		List<Node> list= new LinkedList<Node>();
		
		int i=0;
		//int count=1;
		Map<Integer,Integer> map=new HashMap<Integer, Integer>();
		while(i<family.length) {
			if(map.containsKey(family[i])) {
				list.add(new Node(k,map));
				map.clear();
				//count++;
			}
			else {
				map.put(family[i],1);
				i++;
			}
		}
		list.add(new Node(k,map));
		
		int totalCost=0;
		
		int delta=0;
		do {
			delta=getMaxDelta(list,k);
			//totalCost-=delta;
			
		}while(delta!=-1);
		
		for(i=0;i<list.size();++i) {
			totalCost+=list.get(i).cost;
		}
		return totalCost;
	}
	
	
	private static int getMaxDelta(List<Node> list,int k) {
		int delta=-1;
		int maxDeltaIndex=-1;
		for(int i=0;i<list.size()-1;++i) {
			Node f=list.get(i);
			Node s=list.get(i+1);
			int nonMerge=f.cost+s.cost;
			Map<Integer,Integer> hmFirstSecond=getDuplicateCount(f.map,s.map);
			int countDup1=0;
			for(Map.Entry<Integer, Integer> entry: hmFirstSecond.entrySet()) {
				if(entry.getValue()>1) {
					countDup1+=entry.getValue();
				}
			}
			int mergeCost=k+countDup1;
			if(mergeCost<=nonMerge) {
				int diff =nonMerge-mergeCost;
				if(diff>=delta) {
					delta=diff;
					maxDeltaIndex=i;
				}
			}
			
		}
		if(delta!=-1) {
			//have to create a new node 
			Map<Integer,Integer> hmFirstSecond=getDuplicateCount(list.get(maxDeltaIndex).map,list.get(maxDeltaIndex+1).map);
			int countDup1=0;
			for(Map.Entry<Integer, Integer> entry: hmFirstSecond.entrySet()) {
				if(entry.getValue()>1) {
					countDup1+=entry.getValue();
				}
			}
			list.add(maxDeltaIndex, new Node(k+countDup1,hmFirstSecond));
			list.remove(maxDeltaIndex+1);
			list.remove(maxDeltaIndex+1);
			
		}
		return delta;
		
	}
	
	private static Map<Integer,Integer> getDuplicateCount(Map<Integer,Integer> f,Map<Integer,Integer> s) {
		Map<Integer,Integer> hm=new HashMap<Integer, Integer>();
		hm.putAll(f);
		s.forEach( 
	            (key, value) 
	                -> hm.merge( key,value,(v1,v2)->
	                	 v1+v2
	                )
	    );
//		int count=0;
//		
//		for(Map.Entry<Integer, Integer> entry: hm.entrySet()) {
//			if(entry.getValue()>1) {
//				count+=entry.getValue();
//			}
//		}
		return hm;
		
	}
}
//
//	
//}