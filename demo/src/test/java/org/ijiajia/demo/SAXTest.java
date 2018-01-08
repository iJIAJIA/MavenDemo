package org.ijiajia.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import junit.framework.Assert;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class SAXTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws DocumentException, ClassNotFoundException {
		// 加载Excel文档
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("D:/ULE/WorkSpace/Luna/GitHub/MavenDemo/demo/src/test/resources/persistence.xml"));
		//
		// 获取根节点
		Element root = document.getRootElement();
		//获取其他节点
		List<Element> classNodes = root.element("persistence-unit").elements("class");
		for(Element node: classNodes){
			String className = node.getText();
			Class<?> clazz = Class.forName(className);
			Table table = clazz.getAnnotation(Table.class);
			if(table != null){
				System.out.println(table.name());
			}
		}
	}
	
	@Test
	public void generateZabbixModel() throws IOException, DocumentException, SAXException{
		InputStream zabbixInfo = SAXTest.class.getResourceAsStream("/zabbix.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(zabbixInfo));
		String zabbixInfoJson = br.readLine();
		br.close();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(zabbixInfoJson);
		String zabbixName = rootNode.get("name").asText();
		JsonNode IPsNode = rootNode.get("IPs");
		String[] IPsArray = objectMapper.readValue(IPsNode.toString(), TypeFactory.defaultInstance().constructArrayType(String.class));
		Assert.assertNotNull("fuck you",IPsArray);
		String[] resourceNames = objectMapper.readValue(rootNode.get("resourceNames").toString(), TypeFactory.defaultInstance().constructArrayType(String.class));
		Assert.assertNotNull("fuck you too",resourceNames);
		String width = rootNode.get("width").asText();
		String height = rootNode.get("height").asText();
		Document document = getZabbixModel();
		Assert.assertNotNull(document);
		Element root = document.getRootElement();
		Element date = root.element("date");
//		TODO 设置时间日期
//		2017-09-16T09:32:16Z
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
		date.setText(sdf.format(new Date()).replaceFirst(" ", "T").replaceFirst(" ", "Z"));
		
		
		Element screen = root.element("screens").element("screen");
		Element projectNameNode = screen.element("name");
		projectNameNode.addText(zabbixName);
		Element hsizeNode = screen.element("hsize");
		hsizeNode.addText(resourceNames.length + "");
		Element vsizeNode = screen.element("vsize");
		vsizeNode.addText(IPsArray.length + "");
		Element screenItemsNode = screen.element("screen_items");
		Element screenItemModel = screen.element("screen_items").element("screen_item");
		for(int i = 0; i<IPsArray.length; i++){
			String IP = IPsArray[i];
			for(int j = 0; j<resourceNames.length; j++){
				String resourceName = resourceNames[j];
				Element cloneItem = (Element) screenItemModel.clone();
				cloneItem.element("width").addText(width);
				cloneItem.element("height").addText(height);
				cloneItem.element("x").addText(j + "");
				cloneItem.element("y").addText(i + "");
				cloneItem.element("resource").element("name").addText(resourceName);
				cloneItem.element("resource").element("host").addText(IP);
				cloneItem.element("max_columns").addText(resourceNames.length + "");
				screenItemsNode.add(cloneItem);
			}
		}
		screenItemsNode.remove(screenItemModel);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");// 设置XML文件的编码格式
		XMLWriter xmlWriter = new XMLWriter(new FileWriter(SAXTest.class.getResource("/").getPath() + "/zabbix_" + zabbixName + ".xml"), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
	
	public Document getZabbixModel() throws DocumentException{
		SAXReader reader = new SAXReader();
		Document document = reader.read(SAXTest.class.getResourceAsStream("/zabbixModel.xml"));
		return document;
	}
}
