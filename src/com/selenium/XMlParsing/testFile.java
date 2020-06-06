package com.selenium.XMlParsing;

import java.io.File;
import java.util.List;
import java.util.jar.Attributes;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class testFile {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		  /*WebDriver driver;
	 		System.setProperty("webdriver.gecko.driver", "A:\\Selenium\\geckodriver.exe");
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
			driver.get("http://localhost:8061/FestivalPortalR2_Participant/");*/
			File inputFile = new File("./src/login.xml");
			//create parser object
			SAXBuilder saxBuilder = new SAXBuilder();
			//load file in parser memory. and add document pointer to file.
			Document document = saxBuilder.build(inputFile);
			//Get the root element of XML file.
			 Element classElement = document.getRootElement();
	         // Get all the child of XML file.
			 List<Element> eventList = classElement.getChildren();
	         for(Element input:eventList){
	        	 if(input.getAttribute("id")!=null)
	        		System.out.println("Id");
	        	 else if(input.getAttribute("css")!=null)
	        		 System.out.println("css");
}
	         //driver.close();
	}

}
