package org.ijiajia.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

public class PoiTest {

	@Test
	public void test() throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFPalette customPalette = wb.getCustomPalette();  
		customPalette.setColorAtIndex((short)9,(byte) 100, (byte) 149, (byte) 237);
		titleStyle.setFillForegroundColor((short)9);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle contentStyle = wb.createCellStyle();
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFCellStyle urlContentStyle = wb.createCellStyle();
		urlContentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		urlContentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		String isQuit = "n";
		do {
			System.out.println("输入目标路径:");
			String path = reader.readLine();
			System.out.println("输入模块名称:");
			String modelName = reader.readLine();
			System.out.println("输入应用名称:");
			String appName = reader.readLine();

			HSSFSheet appSheet = wb.createSheet(appName);
			HSSFRow titleRow = appSheet.createRow(0);
			generateTileRow(titleRow, titleStyle);
			File[] propsFiles = getPathProperties(path);
			if(propsFiles == null || propsFiles.length < 1){
				continue;
			}
			int sheetRowIndex = 0;
			for(File file: propsFiles){
				System.out.println("loading properties: " + file.getName());
	         	BufferedReader propsReader = new BufferedReader(
	         			new InputStreamReader(new FileInputStream(file)));
	         	String line = propsReader.readLine();
	         	int beforeReadRow = sheetRowIndex;	//从当前位置 + 1开始
	         	while(line != null){
	         		int index = line.indexOf("http:");
	         		if(index > -1){
	         			sheetRowIndex ++;
	         			HSSFRow contentRow = appSheet.createRow(sheetRowIndex);
	         			HSSFCell urlCell = contentRow.createCell(Title.URL
	    						.getIndex());
	         			urlCell.setCellStyle(urlContentStyle);
	         			urlCell.setCellValue(line.substring(index));
	         		}
	         		line = propsReader.readLine();
	         	}
	         	if(beforeReadRow < sheetRowIndex){
	         		int firstRow = beforeReadRow + 1;
	         		int lastRow = sheetRowIndex;
	         		Row row = appSheet.getRow(firstRow);  
//	        		模块名称
	        		CellRangeAddress modelCellRange = new CellRangeAddress(firstRow, lastRow, Title.MODEL.getIndex(), Title.MODEL.getIndex());        
	        		appSheet.addMergedRegion(modelCellRange);  
	                Cell modelCell = row.createCell(Title.MODEL.getIndex());
	                modelCell.setCellStyle(contentStyle);
	                modelCell.setCellValue(modelName);
//	            	  应用名称
	                CellRangeAddress appNameRange = new CellRangeAddress(firstRow, lastRow, Title.APPNAME.getIndex(), Title.APPNAME.getIndex());        
	                appSheet.addMergedRegion(appNameRange);  
	                Cell appNameCell = row.createCell(Title.APPNAME.getIndex());
	                appNameCell.setCellStyle(contentStyle);
	                appNameCell.setCellValue(appName);
//	             	 文件路径
	                CellRangeAddress pathRange = new CellRangeAddress(firstRow, lastRow, Title.PAHT.getIndex(), Title.PAHT.getIndex());        
	                appSheet.addMergedRegion(pathRange);  
	                Cell pathCell = row.createCell(Title.PAHT.getIndex());
	                pathCell.setCellStyle(contentStyle);
	                pathCell.setCellValue(file.getName());
	         	}
	         	propsReader.close();
			}
			System.out.println("是否结束[y/n]: ");
			isQuit = reader.readLine();
		} while ("n".equals(isQuit.toLowerCase()));
		System.out.println("输入导出路径:");
		String exportPath = reader.readLine();
		if (exportPath == null || "".equals(exportPath)) {
			return;
		}
		FileOutputStream fos=new FileOutputStream(exportPath);  
		wb.write(fos);  
        fos.close();
	}
	
	private File[] getPathProperties(String path){
		File rootDoc = new File(path);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".properties");
			}
		};
		File[] fileArray = rootDoc.listFiles(filter);
		return fileArray;
	}
	
	private void generateTileRow(HSSFRow titleRow,HSSFCellStyle titleStyle){
		for (Title title : Title.values()) {
			HSSFCell cell = titleRow.createCell(title.getIndex());
			cell.setCellStyle(titleStyle);
			cell.setCellValue(title.getValue());
		}
	}
	
	
	private enum Title{
		MODEL(0,"模块名"),
		APPNAME(1,"应用名称"),
		URL(2,"url"),
		PAHT(3,"文件路径")
		;
		private int index;
		
		private String value;
		
		private Title(int index,String value){
			this.index = index;
			this.value = value;
		}

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}
		
		
	}
	
	@Test
	public void testBantchReadFile() throws Exception {
		String path = "E:/ULE/WorkSpace/Eclipse/Juno/ULE/opcOrderMgtWeb/conf/beta";
		File rootDoc = new File(path);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".properties");
			}
		};
		File[] fileArray = rootDoc.listFiles(filter);
		for (File file : fileArray) {
         	System.out.println("loading properties: " + file.getName());
         	BufferedReader propsReader = new BufferedReader(
         			new InputStreamReader(new FileInputStream(file)));
         	String line = propsReader.readLine();
         	while(line != null){
         		int index = line.indexOf("http:");
         		if(index > -1){
         			System.out.println(line.substring(index));
         		}
         		line = propsReader.readLine();
         	}
         }
	}
	
	
	
	@Test
	public void testStrIndex(){
		String line = "harryPotterhttp://www.ule.com";
		System.out.println(line.indexOf("https:"));
		System.out.println(line.substring(line.indexOf("http:" )));
	}
	
	@Test
	public void testMergeCell() throws IOException{
		String path = PoiTest.class.getResource("/").getPath() + "a.xls";
		
		FileOutputStream fos=new FileOutputStream(path);  
        
        Workbook wb=new HSSFWorkbook();  
          
        Sheet sheet=wb.createSheet();  
        /* 
         * 设定合并单元格区域范围 
         *  firstRow  0-based 
         *  lastRow   0-based 
         *  firstCol  0-based 
         *  lastCol   0-based 
         */  
        CellRangeAddress cra=new CellRangeAddress(0, 3, 3, 9);        
          
        //在sheet里增加合并单元格  
        sheet.addMergedRegion(cra);  
          
        Row row = sheet.createRow(0);  
          
        Cell cell_1 = row.createCell(3);  
          
        cell_1.setCellValue("When you're right , no one remembers, when you're wrong ,no one forgets .");  
          
        //cell 位置3-9被合并成一个单元格，不管你怎样创建第4个cell还是第5个cell…然后在写数据。都是无法写入的。  
        Cell cell_2 = row.createCell(10);  
          
        cell_2.setCellValue("what's up ! ");  
          
        wb.write(fos);  
          
        fos.close();  

	}
}
