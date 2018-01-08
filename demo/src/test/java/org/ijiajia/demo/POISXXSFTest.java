package org.ijiajia.demo;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

public class POISXXSFTest {

	@Test
	public void testSXSSFWorkbook() throws IOException{
		 SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
	        for(int sheetNum = 1; sheetNum <= 3; sheetNum ++){
	        	Sheet sh = wb.createSheet(Integer.toString(sheetNum));
	        	for(int rownum = 0; rownum < 1000; rownum++){
	        		Row row = sh.createRow(rownum);
	        		for(int cellnum = 0; cellnum < 10; cellnum++){
	        			Cell cell = row.createCell(cellnum);
	        			String address = new CellReference(cell).formatAsString();
	        			cell.setCellValue(address);
	        		}
	        		
	        		// manually control how rows are flushed to disk 
	        		if(rownum % 100 == 0) {
	        			((SXSSFSheet)sh).flushRows(100); // retain 100 last rows and flush all others
	        			
	        			// ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
	        			// this method flushes all rows
	        		}
	        	}
	        }
	        
	        FileOutputStream out = new FileOutputStream(POISXXSFTest.class.getResource("/").getPath() + "/MulitSheetTest.xls");
	        wb.write(out);
	        out.close();

	        // dispose of temporary files backing this workbook on disk
	        wb.dispose();
	}
	
	@Test
	public void testCellStyle() throws Exception {
		// create a new file
		FileOutputStream out = new FileOutputStream(POISXXSFTest.class.getResource("/").getPath() + "/workbook.xls");
		// create a new workbook
		Workbook wb = new HSSFWorkbook();
		// create a new sheet
		Sheet s = wb.createSheet();
		
//		初始化sheet
		initSheet(s);
		String[] columnTitles = {"大订单号", "订单号","商品id", "sku", "货号",
				"商品名字", "数量", "单价", "下单时间", "订单状态", "发货地", "用户ID", "总金额", "商品属性",
				"收货人姓名", "发货时间", "签收时间", "收货地址省", "收货地址市", "收货地址县","需要发票", "发票内容", "发票抬头",
				"发票寄送地址"};
//		数据标题
		Row columntitleRow = s.createRow(0);
		for(int i = 0; i < columnTitles.length; i++){
			Cell cell = columntitleRow.createCell(i);
			cell.setCellStyle(getColumnTitleCellStyle(wb));
			cell.setCellValue(columnTitles[i]);
		}
		Row dataRow = s.createRow(1);
		for(int i = 0; i < columnTitles.length; i++){
			Cell cell = dataRow.createCell(i);
			cell.setCellStyle(getDataCellStyle(wb));
			cell.setCellValue(columnTitles[i]);
		}
//		数据
		wb.write(out);
		out.close();
	}

	
//	private CellStyle getMainTitleCellStyle(Workbook wb) {
//		CellStyle cs = wb.createCellStyle();
////		 居中显示
//		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
////		字体
//		Font f = wb.createFont();
//		f.setFontName("Times New Roman");
//		f.setFontHeightInPoints((short) 25);
//		f.setItalic(false);
//		f.setBoldweight(Font.BOLDWEIGHT_NORMAL);
//		cs.setFont(f);
//		return cs;
//	}

	private CellStyle getDataCellStyle(Workbook wb) {
		CellStyle cs = wb.createCellStyle();
//		 居中显示
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		字体
		Font f = wb.createFont();
		f.setFontName("Times New Roman");
		f.setFontHeightInPoints((short) 10);
		f.setItalic(false);
		f.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		cs.setFont(f);
//		边框
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);    
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);    
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);    
//		背景色
		cs.setFillForegroundColor(HSSFColor.WHITE.index);
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		自动换行
		cs.setWrapText(true);
		return cs;
	}

	private CellStyle getColumnTitleCellStyle(Workbook wb) {
		CellStyle cs = wb.createCellStyle();
//		 居中显示
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		字体
		Font f = wb.createFont();
		f.setFontName("Times New Roman");
		f.setFontHeightInPoints((short) 12);
		f.setItalic(false);
		f.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		f.setColor(Font.COLOR_RED);
		cs.setFont(f);
//		边框
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);    
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);    
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);    
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN); 
//		背景色
		cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		自动换行
		cs.setWrapText(true);
		return cs;
	}

	private void initSheet(Sheet sheet) {
		sheet.setColumnWidth(0, 20*256);// 大订单号
		sheet.setColumnWidth(1, 15*256);// 订单号
		sheet.setColumnWidth(2, 10*256);
		sheet.setColumnWidth(3, 10*256);
		sheet.setColumnWidth(4, 10*256);
		sheet.setColumnWidth(5, 40*256);
		sheet.setColumnWidth(6, 5*256);
		sheet.setColumnWidth(7, 15*256);
		sheet.setColumnWidth(8, 10*256);
		sheet.setColumnWidth(9, 15*256);
		sheet.setColumnWidth(10, 10*256);
		sheet.setColumnWidth(11, 10*256);
		sheet.setColumnWidth(12, 15*256);
		sheet.setColumnWidth(13, 15*256);
		sheet.setColumnWidth(14, 15*256);
		sheet.setColumnWidth(15, 15*256);
		sheet.setColumnWidth(16, 15*256);
		sheet.setColumnWidth(17, 15*256);
		sheet.setColumnWidth(18, 15*256);
		sheet.setColumnWidth(19, 15*256);
		sheet.setColumnWidth(20, 15*256);
		sheet.setColumnWidth(21, 15*256);
		sheet.setColumnWidth(22, 15*256);
		sheet.setColumnWidth(23, 15*256);
	}
	
	@Test
	public void test2(){
		String[] columnTitles = {"大订单号", "订单号","商品id", "sku", "货号",
				"商品名字", "数量", "单价", "下单时间", "订单状态", "发货地", "用户ID", "总金额", "商品属性",
				"收货人姓名", "发货时间", "签收时间", "收货地址省", "收货地址市", "收货地址县","需要发票", "发票内容", "发票抬头",
				"发票寄送地址"};
		System.out.println(columnTitles.length);
	}
}
