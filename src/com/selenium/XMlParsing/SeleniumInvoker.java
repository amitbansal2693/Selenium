package com.selenium.XMlParsing;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.selenium.constants.SeleniumConstant;

/**
 * This file is responsible for workflow execution.
 * 
 * @author Amit
 *
 */
public class SeleniumInvoker {
	static Logger log = Logger.getLogger(SeleniumInvoker.class.getName());
	static HashMap<String, String> DataSheet;
	static HashMap<String, String> ConfigurationMap;

	/**
	 * This is main method for execution of selenium script. This method will
	 * map the Datasheet to Hashmap.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		WebDriver driver = null;
		try {
			// get the properties file
			ConfigurationProperties properties = new ConfigurationProperties();
			// Get Configuration Details from flow.properties
			ConfigurationMap = properties.getProperties("Features/flow.properties");
			FlowParser.flowParserExecutor(ConfigurationMap.get(SeleniumConstant.FLOW),
					ConfigurationMap.get(SeleniumConstant.FLOW_ID));
		} catch (Exception e) {
			log.info("Exception :::" + e);
		} finally {
			log.info("Finnaly block Executed :::");
			driver.close();
			log.info("Connection Closed.");
		}
	}

}
