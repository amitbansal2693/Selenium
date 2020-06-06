package com.selenium.test;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class testExcelSheet {

	static private XSSFSheet ExcelWSheet;
	static private XSSFWorkbook ExcelWBook;

	public static void main(String[] args) throws Exception {
		FileInputStream ExcelFile = new FileInputStream("Features/Book1.xlsx");

		// Access the required test data sheet
		ExcelWBook = new XSSFWorkbook(ExcelFile);
		ExcelWSheet = ExcelWBook.getSheet("Sheet1");
		HashMap<String, String> loDataSheet = new HashMap<String, String>();
		int i = ExcelWSheet.getFirstRowNum();
		for (int j = 0; j < ExcelWSheet.getRow(i).getLastCellNum(); j++)
			loDataSheet.put(ExcelWSheet.getRow(i).getCell(j).getStringCellValue(),
					ExcelWSheet.getRow(i + 1).getCell(j).getStringCellValue());

		for (Map.Entry<String, String> entry : loDataSheet.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println("Key is: " + key + " value: " + value);
		}
		if(loDataSheet.containsKey("login.username"))
		System.out.println(loDataSheet.get("login.username"));
		ExcelWBook.close();
	}
}
