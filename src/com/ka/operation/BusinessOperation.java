package com.ka.operation;

import org.openqa.selenium.WebDriver;

import com.ka.driver.TestSuiteBase;
import com.ka.utility.ExcelReader;

public class BusinessOperation extends TestSuiteBase{
	
	ExcelReader testCaseExcelReader;
	String inputDataPath = "InputData/BusinessFunction/BusinessFunction" + ".xls";
	

	
	/**
	 * Method that will read all the test case steps from excel sheet and
	 * execute each steps one by one.
	 */

	
	public void businessFunctionTest(WebDriver driver, String sheetName) {
		
		testCaseExcelReader = new ExcelReader(inputDataPath);
		businessCaseExecution("Business Function", sheetName);
		
	}

	/**
	 * Closing driver after test suite execution.
	 */

/*	@AfterClass
	public void tearDown() {
		closeBrowsers();
	}*/

}
