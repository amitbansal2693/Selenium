package com.selenium.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class FlowParser {
	static HashMap<String, String> flowDetails;
	static SAXBuilder saxBuilder = new SAXBuilder();
	static Document document;

	/**
	 * Genrate the Document Builder for the given file Name
	 * @param asFileName
	 * @return
	 * @throws Exception
	 */
	public static Document documentBuilder(String asFilePath) throws Exception {
		File inputFile = new File(asFilePath);
		return saxBuilder.build(inputFile);
	}

	public static void main(String[] args) throws Exception {
		try {
			document = documentBuilder("Features/flow.xml");
			Element flowsElement = document.getRootElement();

			System.out.println("Root Element is::: " + flowsElement.getName());
			// fetch flows tag
			List<Element> flowsList = flowsElement.getChildren();

			// get flow Element and datasheet
			Element flowToExecute = fetchFlowElement(flowsList, "businessapplicationapproval");

			String datasheetName = flowToExecute.getAttributeValue("datasheet");
			System.out.println(datasheetName);

			// parse Chat element from Flow.xml
			fetchChartFlow(flowToExecute);
		} finally {

		}
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
		// get all chart element from flow.xml
		List<Element> chartList = flowToExecute.getChildren();
		for (Element chartId : chartList) {
			String dataId = chartId.getAttributeValue("id");
			String xmlFile = chartId.getAttributeValue("xml");
			System.out.println("Data Id::" + dataId + "  :: XML File:::" + xmlFile);
			// Now for Each Chart element, traverse Testing file
			findDataTagToExecute(xmlFile, dataId);

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
	public static void findDataTagToExecute(String xmlFilePAth, String dataTagId) throws Exception {
		String filePAth = "Features/" + xmlFilePAth;
		document = documentBuilder(filePAth);
		Element flowsElement = document.getRootElement();
		List<Element> childElements = flowsElement.getChildren();
		Element flowToExecute = fetchFlowElement(childElements, dataTagId);
		List<Element> dataTagChildren = flowToExecute.getChildren();
		for (Element executor : dataTagChildren) {
			System.out.println(
					"DataTagId:" + flowToExecute.getAttributeValue("id") + " child element is:" + executor.getName());
		}
	}
}
