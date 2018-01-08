package org.ijiajia.demo;

import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

public class GoogleCollectionTest {
	@Test
	public void test(){
		HashMap m1=new HashMap<String,String>();
    	HashMap m2=new HashMap<String,String>();
    	for(int i=1;i<=50000;i++){
    		if(i==50000){
    			m1.put(i+"",i+"");
    		}else{
    			m1.put(i+"",i+"");
        		m2.put(i+"",i+"");
    		}
    	}
    	long startTime=System.currentTimeMillis();
    	Set<String> difference = Sets.difference(m1.keySet(), m2.keySet());
    	System.out.println(System.currentTimeMillis()-startTime);
    	System.out.println(difference);

	}
}
