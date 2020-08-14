

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



class Test4 {
	
	
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
		List<Node> list= new ArrayList<Node>();
		
		int i=0;
		int count=0;
		Map<Integer,Integer> map=new HashMap<Integer, Integer>();
		while(i<family.length) {
			if(map.containsKey(family[i])) {
				//list.add(new Node(k,map));
				map.clear();
				count++;
			}
			else {
				map.put(family[i],1);
				i++;
			}
		}
		count++;
		
		if(k==1)
			return count*k;
		else {
			//first lets find the first node which is unique subsrray from beginning
			int begin=0;
			map.clear();
			while(begin<family.length && !map.containsKey(family[begin])) {
				map.put(family[begin], 1);
				++begin;
			}
			
			//whatever is the value of begin i will have to start from there
			
			list.add(new Node(k,map));
			map.clear();
			//now will chek the distinct subsrray from back
			int end=family.length-1;
			while(end>=begin && !map.containsKey(family[end])) {
				map.put(family[end], 1);
				--end;
			}
			Node endNode=new Node(k,map);
			if(end<begin) {
				//then we have 2 unique subarrays
				int costUnique=2*k;
				int costAll=0;
				int hm[]=new int[101];
				for(int j=0;j<family.length;++j) {
					hm[family[j]]++;
				}
				for(int j=0;j<=100;++j) {
					if(hm[j]>=2) {
						costAll+=hm[j];
					}
				}
				costAll+=k;
				return Math.min(costUnique, costAll);
			}
			
			//here means we have few elements in between
			i=begin;
			map.clear();
			while(i<=end) {
				if(map.containsKey(family[i])) {
					list.add(new Node(k,map));
					map.clear();
					count++;
				}
				else {
					map.put(family[i],1);
					i++;
				}
			}
			list.add(new Node(k,map));
			map.clear();
			list.add(endNode);
			
			//now we have prepared our list for processing with hash of all unique tables
			int totalCost=0;
			int delta=0;
			do {
				delta=getMaxDelta(list,k);
				//totalCost-=delta;
				
			}while(delta>=0);
			
			for(i=0;i<list.size();++i) {
				totalCost+=list.get(i).cost;
			}
			
			int hm[]=new int[101];
			int costAll2=0;
			for(int j=0;j<family.length;++j) {
				hm[family[j]]++;
			}
			for(int j=0;j<=100;++j) {
				if(hm[j]>=2) {
					costAll2+=hm[j];
				}
			}
			costAll2+=k;
			return Math.min(totalCost, costAll2);
			
			//return totalCost;
		}
	}
	
	
	private static int getMaxDelta(List<Node> list,int k) {
		int delta=-1;
		int maxDeltaIndex=-1;
		int maxDeltaNeighbour=-1;
		int delteCount=-1;
		for(int i=1;i<list.size()-1;++i) {
			//left and current
			
			Node f=list.get(i-1);
			Node s=list.get(i);
			int nonMerge=f.cost+s.cost;
			int countDup1=0;
			Map<Integer,Integer> hmFirstSecond=getDuplicateCount(f.map,s.map);
			countDup1=gethashcount(hmFirstSecond);
			int mergeCost=k+countDup1;
			int leftSideDiff=nonMerge-mergeCost;
			f=list.get(i);
			s=list.get(i+1);
			nonMerge=f.cost+s.cost;
			hmFirstSecond=getDuplicateCount(f.map,s.map);
			int countDup2=gethashcount(hmFirstSecond);

			mergeCost=k+countDup2;
			int rightSideDiff=nonMerge-mergeCost;
			
			if(leftSideDiff>=rightSideDiff) {
				//left side merge
				if(leftSideDiff>delta) {
					delta=leftSideDiff;
					maxDeltaIndex=i-1;
					maxDeltaNeighbour=i;
					delteCount=countDup1;
					break;
				}
				else if(leftSideDiff==delta) {
					if(countDup1>delteCount) {
						delta=leftSideDiff;
						maxDeltaIndex=i-1;
						maxDeltaNeighbour=i;
						delteCount=countDup1;
						break;
					}
				}
			}
			else {
				//right side merge
				
				if(rightSideDiff>delta) {
					delta=rightSideDiff;
					maxDeltaIndex=i;
					maxDeltaNeighbour=i+1;
					delteCount=countDup2;
					break;
				}
				
				else if(rightSideDiff==delta) {
					if(countDup2>delteCount) {
						delta=leftSideDiff;
						maxDeltaIndex=i;
						maxDeltaNeighbour=i+1;
						delteCount=countDup2;
						break;
					}
				}
			}
			

			
		}
		if(delta!=-1) {
			//have to create a new node 
			Map<Integer,Integer> hm=getDuplicateCount(list.get(maxDeltaIndex).map,list.get(maxDeltaNeighbour).map);
			int countDup=gethashcount(hm);
			list.add(maxDeltaIndex, new Node(k+countDup,hm));
			list.remove(maxDeltaIndex+1);
			list.remove(maxDeltaIndex+1);
			
		}
		return delta;
		
	}
	
	
	private static int gethashcount(Map<Integer,Integer> f) {
		int c=0;
		for(Map.Entry<Integer, Integer> entry: f.entrySet()) {
			if(entry.getValue()>1) {
				c+=entry.getValue();
			}
		}
		return c;
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

		return hm;
		
	}
}
//
//	
//}