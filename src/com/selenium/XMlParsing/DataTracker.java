package com.selenium.XMlParsing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataTracker {
	static private XSSFSheet ExcelWSheet;
	static private XSSFWorkbook ExcelWBook;

	public static HashMap<String, String> ExcelDataSheet(String asFileName, String asWorkSheetName) throws Exception {
		HashMap<String, String> loDataSheet = new HashMap<String, String>();
		if(asFileName==null || asFileName.isEmpty()){
			throw new FileNotFoundException("File Name is Empty");
		}
		FileInputStream ExcelFile = new FileInputStream(asFileName);
		ExcelWBook = new XSSFWorkbook(ExcelFile);
		ExcelWSheet = ExcelWBook.getSheet(asWorkSheetName);
		if(ExcelWSheet.getSheetName()==null){
			throw new Exception("Sheet Not Found...!!!");
		}
		int i = ExcelWSheet.getFirstRowNum();
		for (int j = 0; j < ExcelWSheet.getRow(i).getLastCellNum(); j++) {
			loDataSheet.put(ExcelWSheet.getRow(i).getCell(j).getStringCellValue(),
					ExcelWSheet.getRow(i + 1).getCell(j).getStringCellValue());
		}
		ExcelWBook.close();
		return loDataSheet;
	}

}
