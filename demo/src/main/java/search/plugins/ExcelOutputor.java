package search.plugins;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class ExcelOutputor {
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
		
		String fileName = "order_info_done.log";
		String targetPath = ExcelOutputor.class.getResource("/").getFile();
		FileOutputStream out = new FileOutputStream(targetPath + "/" + getOutputName(fileName));
		// create a new workbook
		Workbook wb = new HSSFWorkbook();
		// create a new sheet
		Sheet s = wb.createSheet();
		String[] columnTitles = {"开始时间","本次消息总量","推送搜索总量","Wrapper数量","数据库数据查询封装耗时","搜索查询耗时","日志打印耗时","总耗时"};
		ExcelHelper.initSheetColumnWidth(s, columnTitles.length);
//		数据标题
		Row columntitleRow = s.createRow(0);
		CellStyle titleCellStyle = ExcelHelper.getColumnTitleCellStyle(wb);
		for(int i = 0; i < columnTitles.length; i++){
			Cell cell = columntitleRow.createCell(i);
			cell.setCellStyle(titleCellStyle);
			cell.setCellValue(columnTitles[i]);
		} 
		AbstractLogInfoGenerator logInfoGenerator = new AbstractLogInfoGenerator();
		List<SearchLogInfo> infoList = logInfoGenerator.generateSheetData(targetPath + "/" + fileName);
		
		// 插入数据
		CellStyle baseCellStyle = ExcelHelper.getBaseCellStyle(wb);
		CellStyle dateCellStyle = ExcelHelper.getDateCellStyle(wb);
		int dataRowIdx = 1;
		Field[] fields = SearchLogInfo.class.getFields();
		for(SearchLogInfo info: infoList){
			if(info != null){
				Row dataRow = s.createRow(dataRowIdx++);
				for (int i = 0; i < fields.length; i++) {
					Cell cell = dataRow.createCell(i);
					cell.setCellStyle(baseCellStyle);
					Object object = fields[i].get(info);
					if(object instanceof Date){
						cell.setCellStyle(dateCellStyle);
					}
					ExcelHelper.setCellValue(cell, object);
				}
			}
		}
		
//		插入数据
//		数据
		wb.write(out);
		out.close();
	}
	
	
	public static String getOutputName(String fileName){
		return String.format("%s.xls", fileName);
	}
	
}
