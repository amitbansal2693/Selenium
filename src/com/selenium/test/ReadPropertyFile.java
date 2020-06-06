package com.selenium.test;

import java.util.*;
import java.io.*;

public class ReadPropertyFile {
	public static void main(String[] args) throws Exception {
		FileReader reader = new FileReader("./src/flow.properties");
		Properties p = new Properties();
		p.load(reader);
		System.out.println(p.getProperty("flowId"));
		System.out.println(p);
	}
}
