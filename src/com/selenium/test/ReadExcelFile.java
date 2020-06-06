package com.selenium.test;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {
	
	private XSSFSheet ExcelWSheet;
	   private XSSFWorkbook ExcelWBook;
	   public static void main(String[] args) throws Exception {
		   ExcelUtils  dd = new ExcelUtils ("./src/Book1.xlsx","Sheet1");
		      System.out.println("The Row count is " + dd.excel_get_rows());

		      
		      dd.getCellDataasstring(0, 0);
		      dd.getCellDataasstring(0, 1);
		     // dd.getCellDataasstring(1, 3);
		      dd.getCellDataasstring(1, 0);
		      dd.getCellDataasstring(1, 1);
		     // dd.getCellDataasstring(2, 3);
		     // dd.getCellDataasstring(3, 1);
		     // dd.getCellDataasstring(3, 2);
		     // dd.getCellDataasstring(3, 3);
		   }
	   
	  

}