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

public class Driver {

	public static String strBrowsers;

	public static void main(String[] args) {

		try {
			List<String> allBrowserList;
			Map<String, List<String>> suiteMap;
			ExcelReader excelConroller = new ExcelReader("Controller.xls");
			suiteMap = excelConroller.getSelectedModuleNames();
			TestNG testng = new TestNG();
			List<XmlSuite> suites = new ArrayList<>();
			XmlSuite browserSuite = new XmlSuite();
			browserSuite.setName("KA Test Suite");
			for (Map.Entry<String, List<String>> entry : suiteMap.entrySet()) {

				if (!entry.getKey().isEmpty()) {
					String moduleName = entry.getKey();
					allBrowserList = entry.getValue();
					System.out.println(moduleName + " - " + allBrowserList);
					for (String browser : allBrowserList) {
						List<XmlClass> classes = new ArrayList<>();

						HashMap<String, String> suiteParameters = new HashMap<>();
						suiteParameters.put("browser", browser);
						suiteParameters.put("module", moduleName);

						XmlTest test = new XmlTest(browserSuite);
						test.setName(moduleName + "_" + browser + "_Test ");
						test.setParameters(suiteParameters);

						XmlClass aClass = new XmlClass();
						aClass.setName("com.ka.testsuite." + "TestSuiteExecuter");
						classes.add(aClass);

						test.setClasses(classes);
					}
				}
			}
			suites.add(browserSuite);
			// System.out.println("XML for " + strBrowser + "\n");
			System.out.println(browserSuite.toXml());
			// }

			testng.setXmlSuites(suites);
			testng.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
