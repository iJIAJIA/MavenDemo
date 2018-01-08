package spoon;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.Test;

import spoon.support.compiler.FileSystemFolder;

public class SpoonDemo {
	
	@Test
	public void testCMDLuancher() throws Exception{
		List<CHClass> recordList = new ArrayList<CHClass>();
		String classpath = generateClasspath("D:/ULE/WorkSpace/Luna/ULE/ShoppingOrderEar/target/shoppingOrderEar-3.0.0.HEAD/lib");
//		String classpath = "D:/ULE/WorkSpace/Luna/ULE/myShoppingOrderWeb/target/myShoppingOrderWeb-beta-2.0.0.HEAD/WEB-INF/lib/";
		System.out.println(classpath);
//		配置源文件根路径
		String sourceFolder = "D:/ULE/WorkSpace/Luna/ULE/ShoppingOrderEjb/src/main/java/";
		Launcher launcher = new Launcher();
		launcher.addProcessor(new ClassProcessor(recordList));
//		launcher.addProcessor(new MethodExecutionProcessor());
		launcher.setArgs(new String[]{"--source-classpath",classpath});
		launcher.addInputResource(new FileSystemFolder(new File(sourceFolder)));
		launcher.run();
		exportData(recordList);
	}
	
	public String generateClasspath(String path){
//		StringBuilder classpath = new StringBuilder("D:/ULE/WorkSpace/Luna/ULE/myShoppingOrderWeb/target/myShoppingOrderWeb-beta-2.0.0.HEAD/WEB-INF/lib/");
		StringBuilder classpath = new StringBuilder();
		File classpathDoc = new File(path.toString());
		File[] jarFiles = classpathDoc.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".jar");
			}
		});
		for(File jar: jarFiles){
			classpath.append(path).append(jar.getName()).append(";");
		}
		classpath.deleteCharAt(classpath.length()-1);
		classpath.append(";D:/ULE/WorkSpace/Luna/ULE/myShoppingOrderWeb/target/classes");
		return classpath.toString();
		
	}
	
	private void exportData(List<CHClass> dataList) throws IOException{
		HSSFWorkbook wb = new HSSFWorkbook();
		CellStyle cs = wb.createCellStyle();
//		 居中显示
		cs.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFSheet appSheet = wb.createSheet("BLABLA");
		appSheet.setColumnWidth(0, 30*256);// 大订单号
		appSheet.setColumnWidth(1, 30*256);// 订单号
		appSheet.setColumnWidth(2, 50*256);
		appSheet.setColumnWidth(3, 30*256);
		String[] titles = {"入口类名","入口方法名","接口类名","接口"};
		insertRows(titles,appSheet.createRow(0), cs);
		int rowcount = 1;
		for(CHClass chClass: dataList){
			System.out.println("classname: " + chClass.getName());
			String className = chClass.getName();
			for(CHMethod chMethod: chClass.getMethod()){
				String methodName = chMethod.getName();
				System.out.println("classname: " + methodName);
				for(int i = 0; i < chMethod.getBodyCallMethods().size(); i++){
					CHMethod execute = chMethod.getBodyCallMethods().get(i);
					HSSFRow contentRow = appSheet.createRow(rowcount++);
					String[] datas = {className,methodName,execute.getDecalaredClassName(),execute.getName()};
					insertRows(datas, contentRow,cs);
//					HSSFCell entryCell = contentRow.createCell(0);
//					entryCell.setCellValue(className + "." + methodName);
//					HSSFCell callCell = contentRow.createCell(1);
//					callCell.setCellValue(execute.getDecalaredClassName() + "." + execute.getName());
//					System.out.println("executable.getDecalaredClassName(): " + execute.getDecalaredClassName());
//					System.out.println("executable.getName(): " + execute.getName());
				}
			}
		}
		FileOutputStream fos=new FileOutputStream("D:/ULE/WorkSpace/Luna/GitHub/MavenDemo/demo/src/test/resources/BLABLA.xls");  
		wb.write(fos);  
        fos.close();
	}
	
	private void insertRows(String[] datas,HSSFRow row,CellStyle cs){
		for(int i = 0 ; i < datas.length; i++){
			HSSFCell entryCell = row.createCell(i);
			entryCell.setCellValue(datas[i]);
			entryCell.setCellStyle(cs);
		}
	}
}
