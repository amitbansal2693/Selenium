package com.selenium.XMlParsing;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.selenium.constants.SeleniumConstant;

public class FlowParser {
	static Logger log = Logger.getLogger(SeleniumInvoker.class.getName());
	static HashMap<String, String> DataSheet;
	static HashMap<String, String> ConfigurationMap;
	static HashMap<String, String> flowDetails;
	static SAXBuilder saxBuilder = new SAXBuilder();
	static Document document;

	public static void flowParserExecutor(String asFilename, String asFlowId) throws Exception {
		try {
			ConfigurationProperties properties = new ConfigurationProperties();
			// Get Configuration Details from flow.properties
			ConfigurationMap = properties.getProperties("Features/flow.properties");

			// Get Parser object
			document = documentBuilder(asFilename);
			// Upload Datasheet

			Element flowsElement = document.getRootElement();
			System.out.println("Root Element is::: " + flowsElement.getName());
			// fetch flows tag
			List<Element> flowsList = flowsElement.getChildren();

			// get flow Element and datasheet
			Element flowToExecute = fetchFlowElement(flowsList, asFlowId);

			String datasheetName = flowToExecute.getAttributeValue("datasheet");
			System.out.println(datasheetName);
			DataSheet = DataTracker.ExcelDataSheet(datasheetName,
					ConfigurationMap.get(SeleniumConstant.WORKSHEET_NAME));
			// parse Chart element from Flow.xml
			fetchChartFlow(flowToExecute);
		} finally {

		}
	}

	/**
	 * Genrate the Document Builder for the given file Name
	 * 
	 * @param asFileName
	 * @return
	 * @throws Exception
	 */
	public static Document documentBuilder(String asFilePath) throws Exception {
		File inputFile = new File(asFilePath);
		return saxBuilder.build(inputFile);
	}

	/**
	 * This method will fetch the target element from number of elements with
	 * matching criteria
	 * 
	 * @param flowsList
	 * @param asTargetElement
	 *            matching criteria
	 * @return Target Element
	 * @throws Exception
	 */
	public static Element fetchFlowElement(List<Element> flowsList, String asTargetElement) throws Exception {
		Element flowToExecute = null;
		for (Element flow : flowsList) {
			if (flow != null && flow.getAttributeValue("id").equalsIgnoreCase(asTargetElement)) {
				flowToExecute = flow;
				break;
			}
		}
		return flowToExecute;
	}

	/**
	 * This method will fetch all the child element of given Input. Here it will
	 * fetch all the chart element of <charts> flow from flow.xml
	 * 
	 * @param flowToExecute
	 * @throws Exception
	 */
	public static void fetchChartFlow(Element flowToExecute) throws Exception {

		WebDriver driver = null;
		System.setProperty(ConfigurationMap.get(SeleniumConstant.BROWSER_PROPERTY),
				ConfigurationMap.get(SeleniumConstant.BROWSER_PATH));
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get(ConfigurationMap.get(SeleniumConstant.URL));
		// get all chart element from flow.xml
		List<Element> chartList = flowToExecute.getChildren();
		for (Element chartId : chartList) {
			String dataId = chartId.getAttributeValue("id");
			String xmlFile = chartId.getAttributeValue("xml");
			System.out.println("Data Id::" + dataId + "  :: XML File:::" + xmlFile);
			// Now for Each Chart element, traverse Testing file
			findDataTagToExecute(xmlFile, dataId, driver);

		}
	}

	/**
	 * The method will handle the test script to be executed.
	 * 
	 * @param xmlFilePAth
	 *            Relative Path of file
	 * @param dataTagId
	 *            Id of <Data> Tag to be executed.
	 * @throws Exception
	 */
	public static void findDataTagToExecute(String xmlFilePAth, String dataTagId, WebDriver driver) throws Exception {
		String filePAth = "Features/" + xmlFilePAth;

		document = documentBuilder(filePAth);

		Element flowsElement = document.getRootElement();
		List<Element> childElements = flowsElement.getChildren();

		Element flowToExecute = fetchFlowElement(childElements, dataTagId);
		List<Element> dataTagChildren = flowToExecute.getChildren();

		// fecth Datasheet(ExcelSheet) data

		for (Element input : dataTagChildren) {
			System.out.println(
					"DataTagId:" + flowToExecute.getAttributeValue("id") + " child element is:" + input.getName());
			if (input.getName().equalsIgnoreCase(SeleniumConstant.INPUT)) {
				inputEventHandler(driver, input);
			} else if (input.getName().equalsIgnoreCase(SeleniumConstant.CLICK)) {
				clickEventHandler(driver, input);
			} else if (input.getName().equalsIgnoreCase(SeleniumConstant.WAIT)) {
				waitEventHandler(driver, input);
			}
		}
	}

	/**
	 * This method will handle the event of input element. This method will
	 * locate find the locator and accordingly perform actions. This method will
	 * use the attribute 'value' as key, and corresponding key's value will be
	 * extracted from data sheet.
	 * 
	 * @param driver
	 * @param input
	 * @throws Exception
	 */
	public static void inputEventHandler(WebDriver driver, Element input) throws Exception {
		log.info("Inside Input Handler");
		String elementLocator = SeleniumConstant.EMPTY;
		String value = getKeyValue(input.getAttributeValue(SeleniumConstant.VALUE));
		if (input.getAttribute(SeleniumConstant.ID) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.ID);
			driver.findElement(By.id(elementLocator)).sendKeys(value);
		} else if (input.getAttribute(SeleniumConstant.CSS) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.CSS);
			driver.findElement(By.cssSelector(elementLocator)).sendKeys(value);
		} else if (input.getAttribute(SeleniumConstant.XPATH) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.XPATH);
			driver.findElement(By.xpath(elementLocator)).sendKeys(value);
		} else if (input.getAttribute(SeleniumConstant.CLASS) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.CLASS);
			driver.findElement(By.className(elementLocator)).sendKeys(value);
		} else if (input.getAttribute(SeleniumConstant.NAME) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.NAME);
			driver.findElement(By.name(elementLocator)).sendKeys(value);
		} else if (input.getAttribute(SeleniumConstant.TAG) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.TAG);
			driver.findElement(By.tagName(elementLocator)).sendKeys(value);
		}

	}

	/**
	 * This method will handle the click event.
	 * 
	 * @param driver
	 * @param input
	 */
	public static void clickEventHandler(WebDriver driver, Element input) {
		log.info("Inside Click Handler");
		String elementLocator = SeleniumConstant.EMPTY;
		if (input.getAttribute(SeleniumConstant.ID) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.ID);
			driver.findElement(By.id(elementLocator)).click();
		} else if (input.getAttribute(SeleniumConstant.CSS) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.CSS);
			driver.findElement(By.cssSelector(elementLocator)).click();
		} else if (input.getAttribute(SeleniumConstant.XPATH) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.XPATH);
			driver.findElement(By.xpath(elementLocator)).click();
		} else if (input.getAttribute(SeleniumConstant.CLASS) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.CLASS);
			driver.findElement(By.className(elementLocator)).click();
		} else if (input.getAttribute(SeleniumConstant.NAME) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.NAME);
			driver.findElement(By.name(elementLocator)).click();
		} else if (input.getAttribute(SeleniumConstant.TAG) != null) {
			elementLocator = input.getAttributeValue(SeleniumConstant.TAG);
			driver.findElement(By.tagName(elementLocator)).click();
		}
	}

	/**
	 * This method will handle the wait event. This method will take time
	 * attribute. The time will be defined in milliseconds.
	 * 
	 * @param driver
	 * @param input
	 */
	public static void waitEventHandler(WebDriver driver, Element input) {
		log.info("Wait Handler");
		int timeInMilliseconds = Integer.parseInt(input.getAttributeValue(SeleniumConstant.TIME));
		driver.manage().timeouts().implicitlyWait(timeInMilliseconds, TimeUnit.MILLISECONDS);

	}

	/**
	 * This method will fetch the value for the input element's key.
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getKeyValue(String key) throws Exception {
		if (DataSheet != null && DataSheet.containsKey(key)) {
			return DataSheet.get(key);
		} else {
			throw new Exception("Key Not found Exception");
		}
	}
}
