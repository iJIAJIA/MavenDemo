package search.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class AbstractLogInfoGenerator {
	
	
	
	public List<SearchLogInfo> generateSheetData(String pathname){
		BufferedReader bin = null;
		try{
			InputStream in = new FileInputStream(new File(pathname));
			bin = new BufferedReader(new InputStreamReader(in));
			String line = bin.readLine();
			List<SearchLogInfo> sheetData = new ArrayList<>();
			while(line != null){
				SearchLogInfo lineData = handleLine(line);
				if(lineData != null){
					sheetData.add(lineData);
				}
				line = bin.readLine();
			}
			return sheetData;
		}catch (Exception e){
			
		}finally {
			if(bin != null){
				try{
					bin.close();
				}catch(Exception e){
				}
			}
		}
		return null;
	}
	
	public SearchLogInfo handleLine(String line){
		String[] keyValueArray = line.split(",");
		Map<String,String> dataMap = new HashMap<>();
		for(String keyValue: keyValueArray){
			String[] keyValueData = keyValue.split("::");
			dataMap.put(keyValueData[0], keyValueData[1]);
		}
		SearchLogInfo searchLogInfo = new SearchLogInfo();
		searchLogInfo.invokeTime = DateUtils.baseParse(dataMap.get("invokeTime"));
		searchLogInfo.pullSize = Integer.parseInt(dataMap.get("pullSize"));
		searchLogInfo.pushSize = Integer.parseInt(dataMap.get("pushSize"));
		searchLogInfo.wrappers = Integer.parseInt(dataMap.get("wrappers"));
		searchLogInfo.dbTime = Integer.parseInt(dataMap.get("dbTime"));
		searchLogInfo.searchTime = Integer.parseInt(dataMap.get("searchTime"));
		searchLogInfo.totalCost = Integer.parseInt(dataMap.get("totalCost"));
		int wrapCost = Integer.parseInt(dataMap.get("wrap"));
		searchLogInfo.bigLogTime = searchLogInfo.totalCost - wrapCost;
		return searchLogInfo;
	}
	
	
}
