//package search.plugins;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.junit.Test;
//
//public class ExcelOutputor {
//
//
//	/**
//	 * 部分日期的日志文件切割成了多份,将其名称归类到一块
//	 * @param path
//	 * @return
//	 */
//	public static Map<String,List<String>> sortFileName(String path){
//		File doc = new File(path);
//		File[] listFiles = doc.listFiles();
//		Pattern pattern = Pattern.compile(".*([0-9]{4}-[0-9]{2}-[0-9]{2})-([0-9]).log-db.out");
//		Map<String,List<String>> sortedFileMap = new HashMap<String, List<String>>();
//		for (int i = 0; i < listFiles.length; i++) {
//			File file = listFiles[i];
//			if(!file.isFile() ){
//				continue;
//			}
//
//			Matcher matcher = pattern.matcher(file.getName());
//			if(matcher.matches()){
//				String day = matcher.group(1);
//				String idx = matcher.group(2);
//				List<String> dayList = sortedFileMap.get(day);
//				if(dayList == null){
//					dayList = new ArrayList<String>();
//					sortedFileMap.put(day, dayList);
//				}
//				dayList.add(Integer.parseInt(idx), file.getName());
//			}
//		}
//		return sortedFileMap;
//	}
//
//	/**
//	 * 将数据写入sheet
//	 * @param sheet
//	 * @param columnTitles
//	 * @param data
//	 */
//	public static void writeData2Sheet(Sheet sheet,
//			final String[] columnTitles,final List<List<Object>> data){
//		Workbook wb = sheet.getWorkbook();
//		ExcelHelper.initSheetColumnWidth(sheet, columnTitles.length);
//	    // 数据标题
//		Row columntitleRow = sheet.createRow(0);
//		CellStyle titleCellStyle = ExcelHelper.getColumnTitleCellStyle(wb);
//		for(int i = 0; i < columnTitles.length; i++){
//			Cell cell = columntitleRow.createCell(i);
//			cell.setCellStyle(titleCellStyle);
//			cell.setCellValue(columnTitles[i]);
//		}
//		CellStyle baseCellStyle = ExcelHelper.getBaseCellStyle(wb);
//		CellStyle dateCellStyle = ExcelHelper.getDateCellStyle(wb);
//		for (int i = 0; i < data.size(); i++) {
//			List<Object> rowData = data.get(i);
//			Row dataRow = sheet.createRow(i+1);
//			for (int j = 0,columnNum = rowData.size(); j < columnNum; j++) {
//				Cell cell = dataRow.createCell(j);
//				cell.setCellStyle(baseCellStyle);
//				Object column = rowData.get(j);
//				if(column instanceof Date){
//					cell.setCellStyle(dateCellStyle);
//				}
//				ExcelHelper.setCellValue(cell, column);
//			}
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
//
////		String fileName = "order_info-2018-03-26-0_done.log";
////		String targetPath = ExcelOutputor.class.getResource("/").getFile();
////		FileOutputStream out = new FileOutputStream(targetPath + "/" + getOutputName(fileName));
//		String targetPath = "D:\\桌面\\log\\zjq\\70";
//		Map<String, List<String>> sortFileName = sortFileName(targetPath);
//		Set<Entry<String, List<String>>> entrySet = sortFileName.entrySet();
//		for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
//			Entry<String, List<String>> entry = (Entry<String, List<String>>) iterator
//					.next();
//			System.out.printf("开始汇总%s当天的日志\n",entry.getKey());
//
//			// 当天的日志集合
//			List<String> singleDayFileList = entry.getValue();
//
//			// create a new workbook
//
////			LogInfoGenerator logInfoGenerator = new LogInfoGenerator(new SearchLogLineHandler());
////			LogInfoGenerator logInfoGenerator = new LogInfoGenerator(new DBLogLineHandler());
//			List<List<Object>> infoList = new ArrayList<List<Object>>();
//			long handleLogCostTime = System.currentTimeMillis();
//			for(String fileName: singleDayFileList){
//				infoList.addAll(logInfoGenerator.generateSheetData(targetPath + "/" + fileName));
//			}
//			handleLogCostTime = System.currentTimeMillis() - handleLogCostTime;
//			// 插入数据
//			long outputCostTime = System.currentTimeMillis();
//			Workbook wb = new HSSFWorkbook();
//			// create a new sheet
//			Sheet s = wb.createSheet();
//			String[] columnTitles = {"InvokeTime","CTOC_ORDER","DELEVERY_ORDER","CHECKOUT_ORDER_PRD","SHOPPINGORDER_USER_INFO","DG_ORDER_COMMISSION","CHECKOUT_ORDER_PRD_ASSIGN"," 总耗时"};
//
//			writeData2Sheet(s, columnTitles, infoList);
//			// 插入数据
//			// 数据
//			// TODO
//			FileOutputStream out = new FileOutputStream(new File(generateOutputFilePath(targetPath, entry.getKey())));
//			wb.write(out);
//			wb.close();
//			outputCostTime = System.currentTimeMillis()-outputCostTime;
//			System.out.printf("handleLogCostTime: %d,outputCostTime:%d\n",handleLogCostTime,outputCostTime);
//			System.out.printf("done\n");
//		}
//	}
//
//
//	public static String generateOutputFilePath(String path,String fileName){
//		// String prefix = "orderInfo-";
//		String prefix = "orderInfo-db-";
//		return String.format("%s/%s%s.xls",path,prefix,fileName);
//	}
//
//	public static String getAbsoluteFilePath(String path,String name){
//		return String.format("%s/%s", path,name);
//	}
//
////	order_info-2018-03-26-1.log.out
//	@Test
//	public void test(){
////		Pattern.new
//		String path = "D:\\桌面\\log\\zjq\\70";
//		System.out.println(sortFileName(path));
//	}
//
//	@Test
//	public void test2(){
//
//	}
//}
