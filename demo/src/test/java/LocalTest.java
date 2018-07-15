import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.util.StringUtils;

import search.plugins.ExcelHelper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LocalTest {
	
	@JsonProperty("jdkf")
	private String a;
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		InputStream in = LocalTest.class.getResourceAsStream("/search.txt");
		BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		String line = null;
		line = bin.readLine();
		HashMap<String,Object> orignalData = (HashMap<String,Object>)mapper.readValue(line, HashMap.class);
		line = bin.readLine();
		HashMap<String,Object> transformData = (HashMap<String,Object>) mapper.readValue(line, HashMap.class);
		line = bin.readLine();
		HashMap<String,Object> wrappedData = (HashMap<String,Object>) mapper.readValue(line, HashMap.class);
		line = bin.readLine();
		HashMap<String,Object> pushData = (HashMap<String,Object>) mapper.readValue(line, HashMap.class);
		bin.close();
		List<String> neccessaryField = new ArrayList<String>();
		for(String orinalField: orignalData.keySet()){
			if(pushData.containsKey(orinalField)){
				neccessaryField.add(orinalField);
			}
		}
		System.out.println("从始至终存在的字段: " + mapper.writeValueAsString(neccessaryField));
		
		List<String> transformAddFields = new ArrayList<String>();
		
		for(String field: transformData.keySet()){
			if(!orignalData.containsKey(field)){
				transformAddFields.add(field);
			}
		}
		System.out.println("转换阶段增加的字段: " + mapper.writeValueAsString(transformAddFields));
		
		HashMap<String,Object> cloneWrapData = (HashMap<String, Object>) wrappedData.clone();
		for(String field: transformData.keySet()){
			cloneWrapData.remove(field);
		}
		System.out.println("订单索引数据结构: " + mapper.writeValueAsString(cloneWrapData.keySet()));
		
		List<String> wrapDeleteFields = new ArrayList<String>();
		for(String field: transformData.keySet()){
			if(! wrappedData.containsKey(field)){
				wrapDeleteFields.add(field);
			}
		}
		System.out.println("索引数据封装阶段删除的数据: " + mapper.writeValueAsString(wrapDeleteFields));
		
		List<String> pushDeleteFields = new ArrayList<String>();
		for(String field: wrappedData.keySet()){
			if(! pushData.containsKey(field)){
				pushDeleteFields.add(field);
			}
		}
		System.out.println("推送阶段删除的数据: " + mapper.writeValueAsString(pushDeleteFields));
		
		List<String> pushField = new ArrayList<String>(pushData.keySet());
		Collections.sort(pushField);
		System.out.println(pushField);
		
		Workbook wb = new HSSFWorkbook();
		// create a new sheet
		Sheet s = wb.createSheet();
		String[] columnTitles = {"字段名称","备注"};
		List<List<Object>> infoList = new ArrayList<List<Object>>();
		for(String field : pushField){
			List<Object> columnsData = new ArrayList<Object>();
			columnsData.add(field);
			infoList.add(columnsData);
			if("DELEVERY_ORDERS".equals(field)){
				String dOrderInfos = (String) pushData.get("DELEVERY_ORDERS");
				ObjectMapper objMapper = new ObjectMapper();
				JsonNode rootNode = objMapper.readTree(dOrderInfos);
				if(rootNode.isArray()){
					List<String> dOrderField = new ArrayList<String>();
					HashMap valueMap = objMapper.readValue(rootNode.get(0).toString(), HashMap.class);
					List<String> dFieldList = new ArrayList<String>(valueMap.keySet());
					Collections.sort(dFieldList);
					List<String> prdFields = null;
					for(String dField: dFieldList){
						System.out.println(dField.toUpperCase());
						if("prds".equals(dField)){
							List<Map<String,String>> prdKeyValueMaps = ((List<Map<String,String>>)valueMap.get(dField));
							prdFields = new ArrayList<String>(prdKeyValueMaps.get(0).keySet());
						}
					}
					System.out.println(">>>>>>>");
					for(String prdField: prdFields){
						System.out.println(prdField.toUpperCase());
					}
				}
			}
		}
		writeData2Sheet(s, columnTitles, infoList);
		// 插入数据
		// 数据
		// TODO
		FileOutputStream out = new FileOutputStream(new File(LocalTest.class.getResource("/").getPath() + "/搜索结构.xls"));
		wb.write(out);
		wb.close();
		
		for(String key: pushData.keySet()){
			System.out.println("private String " + camelcaseName(key) + ";");
			System.out.println();
		}
		
		for(String key: pushData.keySet()){
			System.out.println("orderInfo." + getSetMethodName(key) + "(map.get(\"" + key.toLowerCase() + "\"));");
		}
	}
	
	@Test
	public void test2(){
		String[] idontwantit = {"_rt_executeTime","_rt_datasyncTime","TABLE_NAME","_rt_changeFlg","CREATE_TIME","ESC_ORDERID","KEY_VALUE","_rt_indexName","RN","OWNER","PUSH_SOURCE","_rt_docIdField","GLOBAL_LOGID"};
		HashSet<String> set = new HashSet<String>();
		for(String value: idontwantit){
			set.add(value);
		}
		String[] strs = {"AREA","AREA_CODE","BUSINESS_TYPE","BUYER_FEEDBACK","BUYER_FEEDBACK_TIME","BUYER_LOGINID","BUYER_MOBILE","BUYER_NAME","BUYER_NOTE","BUYER_NOTE2","BUYER_NOTE2_TIME","BUYER_NOTE3","BUYER_NOTE3_TIME","BUYER_NOTE_TIME","BUYER_ONLYID","BUYER_PAY_TIME","BUY_SUBTYPE","BUY_TYPE","CANCEL_NOTE","CANCEL_TIME","CANCEL_TYPE","CARD_CASH_PAY","CASH_TRANS_AMOUNT","CASH_TRANS_AMOUNT_PAYED","CHANNEL","COMMISSION_AMOUNT","CREATE_TIME","DELIVER_TIME","DISCOUNT_AMOUNT","DO_BUYER_NOTE2","DO_DELEVERY_ORDER_ID","DO_ORDER_STATUS","DO_ORDER_SUBSTATUS","DO_PACKAGE_ID","DO_SELLER_PREPARED_TIME","DO_STORAGE_ADDRESS","DO_STORAGE_ID","DO_STORAGE_NAME","DO_TRANS_INFO","ESC_ORDERID","FC_ESCID_STORENAME_PRONAMES","FEEDBACK_INVOKE_STATUS","GLOBAL_LOGID","GROUPBUY_AMOUNT","KEY_VALUE","MERCHANT_BACKURL","MERCHANT_ID","MERCHANT_ORDER","MERCHANT_ORDER_REQ_ID","MERCHANT_SITE_CODE","ORDER_ACTIVE_TIME","ORDER_AMOUNT","ORDER_CREATE_TIME","ORDER_DELETED","ORDER_ID","ORDER_POINT","ORDER_STATUS","ORDER_SUBSTATUS","ORDER_TAG","ORDER_TYPE","OWNER","PARTNER_PURCHASE_NOTE","PARTNER_SHIPPING_NOTE","PAYED_AMOUNT","PAYMENT_AMOUNT","PAYMENT_COMMISSION_TOTAL","PAYMENT_DUE_DATE","PAY_VERSION","PLACE_REGIONAL","POSTAL_CLERK_ID","PRD_ITEM_ID","PRD_LISTING_ID","PRD_MERCHANT_CN_NAME","PRD_MERCHANT_ONLYID","PRD_MERCHANT_SHOP_NAME","PRD_PRODUCT_NAME","PRODUCT_AMOUNT","PRODUCT_CLASSID","PRODUCT_VERSION","PROMOTION_TICKET","PUSH_SOURCE","REBATE_AMOUNT","REFER_TRANS_AMOUNT","RELEASE_NOTIFY_EMAIL1","REMARK","RESERVE_FLAG","RN","SALES_CHANNEL","SALE_TYPE","SELLER_HD_ID","SELLER_LOGINID","SELLER_MODIFY_TIME","SELLER_NOTE","SELLER_NOTE2","SELLER_NOTE3","SELLER_NOTE5","SELLER_NOTE6","SELLER_ONLYID","SELLER_SHIP_TIME","SETTLEMENT_FLAG","SETTLEMENT_VERSION","SHIPITEM_NOTIFY_EMAIL","SUPPORTED_BUYTYPE","TABLE_NAME","TEL_AREA_CODE","TEL_NUMBER","TRANS_ADDRESS","TRANS_AMOUNT","TRANS_CITY","TRANS_CITY_CODE","TRANS_COUNTRY","TRANS_POSTAL_CODE","TRANS_PROVINCE","TRANS_PROVINCE_CODE","TRANS_TOWN","TRANS_TOWN_CODE","TRANS_TYPE","TRANS_TYPE3_AMOUNT","TRANS_TYPE3_VALUE","TRANS_TYPE4_ADDVALUE","TRANS_TYPE5_ADDVALUE","TRANS_TYPE5_AMOUNT","TRANS_TYPE5_VALUE","TRANS_TYPE6_ADDVALUE","TRANS_TYPE6_AMOUNT","TRANS_TYPE7_ADDVALUE","TRANS_TYPE7_AMOUNT","TRANS_TYPE7_VALUE","TRANS_USR_NAME","TRANS_USR_PHONE","UPDATE_TIME","USEDPOINT","USEDPOINTAMOUNT","USEDPOINTTYPE","VR_ORDER_STATUS","VR_ORDER_SUBSTATUS","YZG_MOBILE_NUMBER","YZG_ORG_AREA","YZG_ORG_AREA_NAME","YZG_ORG_CITY","YZG_ORG_CITY_NAME","YZG_ORG_CODE","YZG_ORG_PROVINCE","YZG_ORG_PROVINCE_NAME","YZG_ORG_TOWN","YZG_ORG_TOWN_NAME","YZG_POSTAL_CODE","YZG_STATION_NAME","YZG_USR_ACCOUNT","YZG_USR_NAME","cancel_flag","deliver_delay_flag","returnfund_item_flag","DELEVERY_ORDERS"};
//		for(String key: strs){
//			System.out.println("@JsonProperty(\"" + key.toLowerCase() + "\")");
//			System.out.println("private String " + camelcaseName(key) + ";");
//			System.out.println();
//		}
		for(String key: strs){
			if(set.contains(key)){
				System.out.println(">>>>>>>> i don't want it: " + key);
			}
		}
		List<String> hashSet = new ArrayList<String>();
		for(String key : strs){
			if(set.contains(key)) continue;
			hashSet.add(key);
			System.out.println("orderInfo." + getSetMethodName(key) + "(convertUtils.convert(map.get(\"" + key.toLowerCase() + "\")));");
		}
		
		for(String key: hashSet){
//			System.out.println("@JsonProperty(\"" + key.toLowerCase() + "\")");
//			System.out.println("private String " + camelcaseName(key) + ";");
//			System.out.println();
			System.out.println(key.toLowerCase());
		}
		
		
		for(String key: strs){
			if(! hashSet.contains(key)){
				System.out.println(key + " ");
			}
		}
	}
	
	/**
	 * 将数据写入sheet
	 * @param sheet
	 * @param columnTitles
	 * @param data
	 */
	public static void writeData2Sheet(Sheet sheet,
			final String[] columnTitles,final List<List<Object>> data){
		Workbook wb = sheet.getWorkbook();
		ExcelHelper.initSheetColumnWidth(sheet, columnTitles.length);
	    // 数据标题
		Row columntitleRow = sheet.createRow(0);
		CellStyle titleCellStyle = ExcelHelper.getColumnTitleCellStyle(wb);
		for(int i = 0; i < columnTitles.length; i++){
			Cell cell = columntitleRow.createCell(i);
			cell.setCellStyle(titleCellStyle);
			cell.setCellValue(columnTitles[i]);
		} 
		CellStyle baseCellStyle = ExcelHelper.getBaseCellStyle(wb);
		CellStyle dateCellStyle = ExcelHelper.getDateCellStyle(wb);
		for (int i = 0; i < data.size(); i++) {
			List<Object> rowData = data.get(i);
			Row dataRow = sheet.createRow(i+1);
			for (int j = 0,columnNum = rowData.size(); j < columnNum; j++) {
				Cell cell = dataRow.createCell(j);
				cell.setCellStyle(baseCellStyle);
				Object column = rowData.get(j);
				if(column instanceof Date){
					cell.setCellStyle(dateCellStyle);
				}
				ExcelHelper.setCellValue(cell, column);
			}
		}
	}
	
	private String getSetMethodName(String name){
		String fieldCamelcaseName = camelcaseName(name);
		return "set" + fieldCamelcaseName.substring(0,1).toUpperCase() + fieldCamelcaseName.substring(1);
	}
	
	@Test
	public void generateSql(){
		String[] columns = {"ESC_ORDERID","ORDER_ID","SELLER_ONLYID","SELLER_LOGINID","BUYER_ONLYID","BUYER_LOGINID","PRODUCT_CLASSID","TRANS_TYPE5_VALUE","TRANS_TYPE5_ADDVALUE","TRANS_TYPE5_AMOUNT","SALE_TYPE","BUY_TYPE","TRANS_USR_NAME","TRANS_USR_PHONE","ORDER_ACTIVE_TIME","ORDER_CREATE_TIME","CANCEL_TIME","BUYER_PAY_TIME","ORDER_DELETED","ORDER_STATUS","ORDER_SUBSTATUS","MERCHANT_ORDER",",","MERCHANT_BACKURL","PARTNER_PURCHASE_NOTE","PARTNER_SHIPPING_NOTE","ORDER_TYPE","SALES_CHANNEL","BUSINESS_TYPE","PRODUCT_VERSION","PRODUCT_AMOUNT","TRANS_TYPE6_AMOUNT","TRANS_TYPE7_VALUE","TRANS_TYPE7_ADDVALUE","TRANS_TYPE7_AMOUNT","TRANS_TYPE","TRANS_ADDRESS","TRANS_POSTAL_CODE","TRANS_COUNTRY","TRANS_PROVINCE","TRANS_CITY","TRANS_AMOUNT","REBATE_AMOUNT","GROUPBUY_AMOUNT","ORDER_AMOUNT","PAYMENT_AMOUNT","SELLER_SHIP_TIME","SELLER_NOTE","SELLER_NOTE2","SELLER_NOTE3","SELLER_NOTE5","SELLER_NOTE6","SHIPITEM_NOTIFY_EMAIL","RELEASE_NOTIFY_EMAIL1","UPDATE_TIME","DELIVER_TIME","TEL_NUMBER","TEL_AREA_CODE","TRANS_PROVINCE_CODE","TRANS_CITY_CODE","AREA_CODE","AREA","TRANS_TOWN_CODE","TRANS_TOWN","DISCOUNT_AMOUNT","PAYMENT_DUE_DATE","CARD_CASH_PAY","CHANNEL","USEDPOINTAMOUNT","USEDPOINT","USEDPOINTTYPE","ORDER_POINT","CASH_TRANS_AMOUNT","CASH_TRANS_AMOUNT_PAYED","SUPPORTED_BUYTYPE","BUYER_NOTE","BUYER_NOTE_TIME","BUYER_NOTE2","BUYER_NOTE2_TIME","BUYER_NOTE3","BUYER_NOTE3_TIME","SELLER_MODIFY_TIME","ORDER_TAG","BUYER_FEEDBACK","BUYER_FEEDBACK_TIME","REMARK","SELLER_HD_ID","BUY_SUBTYPE","PAYED_AMOUNT","SETTLEMENT_FLAG","SETTLEMENT_VERSION","COMMISSION_AMOUNT","PAY_VERSION","TRANS_TYPE6_ADDVALUE","MERCHANT_SITE_CODE","FEEDBACK_INVOKE_STATUS","CANCEL_TYPE","CANCEL_NOTE","CANCEL_TIME","MERCHANT_ID","BUYER_NAME","BUYER_MOBILE","TRANS_TYPE4_ADDVALUE","PROMOTION_TICKET","RESERVE_FLAG","TRANS_TYPE3_VALUE","POSTAL_CLERK_ID","TRANS_TYPE3_AMOUNT","REFER_TRANS_AMOUNT","MERCHANT_ORDER_REQ_ID"};
		for(String column: columns){
			System.out.printf("%s AS %s,\n",column,camelcaseName(column));
		}
	}
	
	protected String camelcaseName(String name){
		if (!StringUtils.hasLength(name)) {
			return "";
		}
		String[] splitStrs = name.split("_");
		if(splitStrs.length == 1){
			return lowerCaseName(name);
		}
		StringBuilder result = new StringBuilder();
		result.append(lowerCaseName(splitStrs[0]));
		for(int i = 1,length = splitStrs.length; i < length; i++){
			String s = splitStrs[i];
			result.append(upperCaseName(s.substring(0, 1)))
				.append(lowerCaseName(s.substring(1)));
		}
		return result.toString();
	}
	
	protected String lowerCaseName(String name) {
		return name.toLowerCase(Locale.US);
	}
	
	protected String upperCaseName(String name) {
		return name.toUpperCase(Locale.US);
	}
	
	@Test
	public void test3(){
		Parent p = new Child();
		p.sayHello();
	}
	
	@Test
	public void test4(){
		LocalTest.class.getResourceAsStream("");
	}
	
	@Test
	public void test5() throws IOException{
		InputStream in = LocalTest.class.getResourceAsStream("/escorderids.properties");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String id = null;
		StringJoiner escOrderidJoiner = new StringJoiner("','", "('", "')");
//		StringJoiner condition = new StringJoiner("OR ORDER_TYPE IN ","ORDER_TYPE IN ","");
		int count = 1;
		while((id  = reader.readLine()) != null){
			escOrderidJoiner.add(id);
			if(((count + 1) % 100 )== 0){
//				condition.add(escOrderidJoiner.toString());
				System.out.println( "ESC_ORDERID IN " + escOrderidJoiner.toString() + " OR" );
				escOrderidJoiner = new StringJoiner("','", "('", "')");
			}
			count++;
		}
//		condition.add(escOrderidJoiner.toString());
//		System.out.println(condition.toString());
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		CloseableHttpClient build = HttpClientBuilder.create().build();
		build.execute(new HttpGet("http://ule.cn/ybczff"));
	}
	
	private void doSomething(String... params){
		System.out.println(params == null);
		System.out.println(params.length);
	}
	
	@Test
	public void test1(){
		doSomething();
	}	
}
