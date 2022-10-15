package com.ka.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.ka.utility.ExcelReader;

public class Driver_old {
	
	public static String strBrowsers ;
	public static void main(String[] args) {
		

	try {
		List<String> selectedModules;
		
		Map<String, List<String>> suiteMap;
		ExcelReader excelConroller = new ExcelReader("Controller.xls");
		suiteMap = excelConroller.getSelectedModuleNames();
		selectedModules = suiteMap.get("allSuite");
		
		TestNG testng = new TestNG();
		List<XmlSuite> suites = new ArrayList<>();
		//String[] allBrowser = strBrowsers.split(",");

		// XmlGroups allGroups = new XmlGroups();
			
	//	for (String strBrowser : allBrowser) {
			String strBrowser = "Chrome";
			XmlSuite browserSuite = new XmlSuite();
			browserSuite.setName("KA Test Suite - " + strBrowser);
			HashMap<String, String> suiteParameters = new HashMap<>();
			suiteParameters.put("browser", strBrowser);
			browserSuite.setParameters(suiteParameters);
			
			XmlTest test = new XmlTest(browserSuite);
			test.setName(strBrowser + "_Test ");
			
			
			List<XmlClass> classes = new ArrayList<>();
			
			for (String ts : selectedModules) {
				System.out.println("Adding, " + ts + ", module for "
						+ strBrowser);
				

				
				XmlClass aClass = new XmlClass();
				aClass.setName("com.ka.testsuite." + ts);
				classes.add(aClass);

				
			}
			
			test.setClasses(classes);
			
			suites.add(browserSuite);
			// System.out.println("XML for " + strBrowser + "\n");
			 System.out.println(browserSuite.toXml());
		//}

		testng.setXmlSuites(suites);
	//	testng.run();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}
