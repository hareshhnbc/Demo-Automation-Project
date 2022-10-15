package com.ka.testsuite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.ka.driver.TestSuiteBase;
import com.ka.pojo.TestCaseResult;
import com.ka.utility.ExcelReader;

public class TestSuiteExecuter extends TestSuiteBase {
	ExcelReader testCaseExcelReader;
	String inputDataPath;
	List<String> testCaseList;
	TestCaseResult testCaseResult = new TestCaseResult();
	Map<String, String> testCaseResultMap = new HashMap<String, String>();
    Map<String,Map<String,String>> finalSuite =new HashMap<String,Map<String,String>>();

	/**
	 * Method for suite execution preparation
	 */
	@Parameters({ "browser", "module" })
	@BeforeClass
	public void beforeClass(String browser, String module) {
		/** Fetch Testcase list */
		inputDataPath = "InputData/TestSuite/" + module + ".xls";
		testCaseExcelReader = new ExcelReader(inputDataPath);
		testCaseList = testCaseExcelReader.getSelectedTestCases();
		System.out.println("Module: " + module + " Browser: " + browser);
		/** Loading the browser */
		loadBrowser(browser);
	}
	/**
	 * Method that will read all the test case steps from excel sheet and
	 * execute each steps one by one.
	 */
	@Parameters({ "module" })
	@Test
	public void executeTest(String module) {
		System.out.println("Module name : " + module);
		excelWriter.createTestCaseResultSheet(module);
		String[] testCaseDetail;
		String tcResult;
		boolean testCasePass;
		for (String testCase : testCaseList) {
			testCaseDetail = testCase.split("-");
			testCasePass = keyWordExecution(module, testCaseDetail[0], testCaseDetail[1], testCaseExcelReader);
			tcResult = (testCasePass == true ? "PASS" : "FAIL");
			testCaseResultMap.put(testCaseDetail[0], tcResult);	
		}
		testCaseResult.setTestCaseResultMap(testCaseResultMap);
		finalSuite.put(module, testCaseResult.getTestCaseResultMap());
		TestCaseResult.setTestSuiteResultMap(finalSuite);
		excelWriter.getTestResultData(testCaseResult);
		// excelWriter.GenrateExcel(testCaseResult);
		// TestCaseResult.setTestSuiteResultMap(module,testCaseResult.getTestCaseResultMap());

	}

	/**
	 * Closing driver after test suite execution.
	 */

	@AfterTest
	public void tearDown() {
		closeBrowsers();	
	}

	@AfterSuite
	public void writeToExcel() {
		excelWriter.writeToExcel();
	}
}
