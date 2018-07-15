package search.plugins;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelHelper {
	
	
	/**
	 * 
	 * @param wb
	 * @return
	 */
	public static CellStyle getColumnTitleCellStyle(Workbook wb){
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
	
	
//	private static void setIntCellDataFormat(Cell cell){
//		Workbook wb = cell.getSheet().getWorkbook();
//		DataFormat df = wb.createDataFormat();
//		cell.getCellStyle().setDataFormat(df.getFormat("#,#0"));
//	}
//	
//	private static void setFloatCellDataFormat(Cell cell){
//		Workbook wb = cell.getSheet().getWorkbook();
//		DataFormat df = wb.createDataFormat();
//		cell.getCellStyle().setDataFormat(df.getFormat("#,#0"));
//	}
//	
//	private static void setDateCellDataFormat(Cell cell){
//		Workbook wb = cell.getSheet().getWorkbook();
//		DataFormat df = wb.createDataFormat();
//		cell.getCellStyle().setDataFormat(df.getFormat("yyyy-MM-dd hh:mm:ss"));
//	}
	
	public static void setCellValue(Cell cell,Object obj){
		if(obj instanceof Number){
			cell.setCellValue(((Number) obj).doubleValue());
		}else if(obj instanceof String){
			cell.setCellValue(obj.toString());
		}else if (obj instanceof Boolean){
			cell.setCellValue((boolean) obj);
		}else if(obj instanceof Date){
			cell.setCellValue((Date) obj);
		}
	}
	
	
	public static CellStyle getBaseCellStyle(Workbook wb) {
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
	
	public static CellStyle getDateCellStyle(Workbook wb){
		CellStyle cellStyle = getBaseCellStyle(wb);
		DataFormat df = wb.createDataFormat();
		cellStyle.setDataFormat(df.getFormat("yyyy-MM-dd hh:mm:ss"));
		return cellStyle;
	}
	
	public static void initSheetColumnWidth(Sheet sheet,int colums){
		for(int i = 0; i < colums; i++){
			sheet.setColumnWidth(i, 20*256);
		}
	}
	
}
