package sensitivefilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

public class SensitiveSearch {

    @Test
    public void test() throws IOException {
//        System.out.println(SensitiveSearch.class.getResource("涉枪涉爆违法信息关键词.txt"));
//        InputStream in = SensitiveSearch.class.getResourceAsStream("涉枪涉爆违法信息关键词.txt");
        
        Map<Character, Map> rootDic = new HashMap<>();
        {
	        InputStream in = SensitiveSearch.class.getResourceAsStream("a.txt");
	        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	        String line = br.readLine();
	        while (line != null) {
	            if(StringUtils.isEmpty(line)) {
	                continue;
	            }
	            Map lastMap = rootDic;
	            for(int i = 0; i < line.length(); i++) {
	                char c = line.charAt(i);
	                Map nextMap = (Map) lastMap.get(c);
	                if(nextMap == null){
	                    nextMap = new HashMap();
	                    lastMap.put(c,nextMap);
	                }
	                lastMap = nextMap;
	                if(i == line.length() -1 ) {
	                    // 词汇终点
	                    lastMap.put("end", Collections.emptyMap());
	                }
	            }
	            line = br.readLine();
	        }
	        System.out.println(new ObjectMapper().writeValueAsString(rootDic));
        }
        
        BufferedReader lineBr = new BufferedReader(new InputStreamReader(System.in));
        String content = null;
        List<Integer> sensitiveIndex = null;

        while((content = lineBr.readLine()) != null) {
        	sensitiveIndex = new ArrayList<>();
            boolean hasSensitive = false;
            for(int i = 0 ; i < content.length(); i++) {
                char c = content.charAt(i);
                Map lastMap = rootDic.get(c);
                if(lastMap == null) {
                    continue;
                }
                List<Integer> tempSensitiveWord = new ArrayList<>();
                tempSensitiveWord.add(i);
                Map nextMap = null;
                boolean fireshot = false;
                int j = i + 1;
                for(;j < content.length(); j++) {
                	char nextC = content.charAt(j);
                    nextMap = (Map)lastMap.get(nextC);
                    if(nextMap != null) {
                    	tempSensitiveWord.add(j);
                        lastMap = nextMap;
                        if(lastMap.containsKey("end")){
                            // 达到词尾,跳出循环
                            fireshot = true;
                            break;
                        }
                        continue;
                    }
                    break;
                }
                if(fireshot) {
                    i = j ;
                    sensitiveIndex.addAll(tempSensitiveWord);
                    hasSensitive = true;
                }
            }
            if(hasSensitive) {
                StringBuilder stringBuilder = new StringBuilder(content);
                for (Integer index : sensitiveIndex) {
                    stringBuilder.replace(index,index+1,"*");
                }
                System.out.printf("Has sensitive content -> origin:%s,filtered:%s",content,stringBuilder.toString());
            }

        }

    }


}
