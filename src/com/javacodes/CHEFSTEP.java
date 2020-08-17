package com.javacodes;

import java.io.IOException;
import java.util.Scanner;

class CHEFSTEP {
	public static void main(String[] args) throws IOException {
		solve();
	}
	
	
	private static void solve() throws IOException{
		
		int t,n,k;
		Scanner s=new Scanner(System.in);
		
		t=s.nextInt();
		StringBuilder ans=new StringBuilder();
		while(t>0) {
			n=s.nextInt();
			k=s.nextInt();
			
			for(int i=0;i<n;++i) {
				int num=s.nextInt();
				if(num%k==0) {
					ans.append(Integer.toString(1));
				}
				else {
					ans.append(Integer.toString(0));
				}
				
			}
			ans.append("\n");
			
			
			--t;
		}
		System.out.println(ans.toString());
		
		
		s.close();
	}
}
