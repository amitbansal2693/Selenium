package com.selenium.XMlParsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;

import com.selenium.constants.SeleniumConstant;

public class ConfigurationProperties {
	public HashMap<String, String> getProperties(String asFilePath) throws Exception {
		HashMap<String, String> configurationMap = new HashMap<String, String>();
		if(asFilePath==null || asFilePath.isEmpty()){
			throw new NullPointerException("File Name is Empty");
		}
		FileReader reader = new FileReader(asFilePath);//"./src/flow.properties"
		Properties property = new Properties();
		property.load(reader);
		configurationMap.put(SeleniumConstant.FLOW, property.getProperty(SeleniumConstant.FLOW));
		configurationMap.put(SeleniumConstant.FLOW_ID, property.getProperty(SeleniumConstant.FLOW_ID));
		configurationMap.put(SeleniumConstant.URL, property.getProperty(SeleniumConstant.URL));
		configurationMap.put(SeleniumConstant.DATASHEET_PATH, property.getProperty(SeleniumConstant.DATASHEET_PATH));
		configurationMap.put(SeleniumConstant.BROWSER_PATH, property.getProperty(SeleniumConstant.BROWSER_PATH));
		configurationMap.put(SeleniumConstant.BROWSER, property.getProperty(SeleniumConstant.BROWSER));
		configurationMap.put(SeleniumConstant.BROWSER_PROPERTY,
				property.getProperty(SeleniumConstant.BROWSER_PROPERTY));
		configurationMap.put(SeleniumConstant.WORKSHEET_NAME, property.getProperty(SeleniumConstant.WORKSHEET_NAME));
		reader.close();
		return configurationMap;
	}
}
