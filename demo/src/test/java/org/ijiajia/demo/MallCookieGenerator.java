package org.ijiajia.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.Test;

public class MallCookieGenerator {
	
	@Test
	public void test() throws IOException{
		InputStream resourceAsStream = MallCookieGenerator.class.getResourceAsStream("/mall_cookies.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
		String rootPath = MallCookieGenerator.class.getResource("/").getPath();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rootPath + "/mall_cookie.json")));
		String line = br.readLine();
		writer.write("[");
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		while(line != null){
			sb.append("{\"userName\":\"15921598974\",\"password\":\"Ule201193\",\"mall_cookie\":\\").append(line.subSequence(0, line.length()-2)).append("\",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		System.out.println(sb);
	}
}
