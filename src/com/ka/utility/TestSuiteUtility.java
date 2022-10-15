package com.ka.utility;

import java.util.ArrayList;
import java.util.List;

import com.ka.pojo.TestData;

public class TestSuiteUtility {

	public static Object[][] getTestDataUtility(ExcelReader xls, String sheetName) {
		return xls.retrieveTestCaseData(sheetName);
	}

	public static List<TestData> getTestData(ExcelReader filePath, String testCaseSheetName) {
		
		final List<TestData> testDataList = new ArrayList<>();

		final Object[][] rawTestData = TestSuiteUtility.getTestDataUtility(filePath, testCaseSheetName);

		for (int i = 0; i < rawTestData.length; i++) {
			TestData testData = new TestData();

			testData.setKeyword((String) rawTestData[i][filePath.getColumnNumber(testCaseSheetName,
					SuiteConstants.KEYWORD_COLUMN)]);
			testData.setObjectName((String) rawTestData[i][filePath.getColumnNumber(testCaseSheetName,
					SuiteConstants.OBJECT_NAME_COLUMN)]);
			testData.setObjectType((String) rawTestData[i][filePath.getColumnNumber(testCaseSheetName,
					SuiteConstants.OBJECT_TYPE_COLUMN)]);
			testData.setInputData((String) rawTestData[i][filePath.getColumnNumber(testCaseSheetName,
					SuiteConstants.INPUTDATA_COLUMN)]);
			testData.setPurpose((String) rawTestData[i][filePath.getColumnNumber(testCaseSheetName,
					SuiteConstants.PURPOSE_COLUMN)]);
			testData.setTakeScreenShot((String) rawTestData[i][filePath.getColumnNumber(testCaseSheetName,
					SuiteConstants.SCREENSHOT_COLUMN)]);
			testDataList.add(testData);

		}
		return testDataList;
	}

}
