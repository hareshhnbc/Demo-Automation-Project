package com.ka.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class ExcelReader {

	public String fileLocation;
	public FileInputStream inputStream = null;
	public FileOutputStream outputStream = null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;

	public ExcelReader(String fileLocation) {
		this.fileLocation = fileLocation;

		try {
			inputStream = new FileInputStream(fileLocation);
			workbook = new HSSFWorkbook(inputStream);
			sheet = workbook.getSheetAt(0);
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Map<String, List<String>> getSelectedModuleNames() {

		Map<String, List<String>> suiteListMap = new HashMap<String, List<String>>();
		String allTestSuite = "";

		try {
			sheet = workbook.getSheet("SuiteList");

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				List<String> strBrowsersList = new ArrayList<String>();
				HSSFRow row = sheet.getRow(i);
				String strSuiteName = row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String strSuiteToRun = row.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String runOnFirefox = row.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String runOnChrome = row.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String runOnIE = row.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();

				if (strSuiteToRun.equalsIgnoreCase("y") || strSuiteToRun.equalsIgnoreCase("yes")) {
					allTestSuite = strSuiteName;

					if ("Y".equalsIgnoreCase(runOnFirefox) || "yes".equalsIgnoreCase(runOnFirefox)) {
						System.out.println("Firefox - " + runOnFirefox);
						strBrowsersList.add(SuiteConstants.FIREFOX_BROWSER);
					}
					if ("Y".equalsIgnoreCase(runOnChrome) || "yes".equalsIgnoreCase(runOnChrome)) {
						strBrowsersList.add(SuiteConstants.CHROME_BROWSER);
						System.out.println("Chrome - " + runOnChrome);
					}
					if ("Y".equalsIgnoreCase(runOnIE) || "yes".equalsIgnoreCase(runOnIE)) {
						strBrowsersList.add(SuiteConstants.IE_BROWSER);
						System.out.println("IE - " + runOnIE);
					}
					suiteListMap.put(allTestSuite, strBrowsersList);
				}
			}

		} catch (Exception e) {
			System.out.println("Some error occured while fetching list of selected modules. See below stack trace");
			e.printStackTrace();
		}

		return suiteListMap;
	}

	public List<String> getSelectedTestCases() {
		List<String> allTestCases = new ArrayList<>();
		String strSuiteName;
		String strSuiteToRun;
		String strTestCaseCategory;
		
		try {
			sheet = workbook.getSheet("TestCasesList");

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				strSuiteName = row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				strSuiteToRun = row.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				strTestCaseCategory = row.getCell(4,MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				if (strSuiteToRun.equalsIgnoreCase("y") || strSuiteToRun.equalsIgnoreCase("yes")){
					if(strTestCaseCategory.isEmpty()){
						strTestCaseCategory="Uncategorized";
					}
					allTestCases.add(strSuiteName+"-"+strTestCaseCategory);
				}

			}
		} catch (Exception e) {
			System.out.println("Some error occured while fetching list of selected Testcases. See below stack trace");
			e.printStackTrace();
		}

		return allTestCases;
	}

	public Object[][] retrieveTestCaseData(String sheetName) {

		sheet = workbook.getSheet(sheetName);
		int rowNum = sheet.getLastRowNum();
		Object object[][] = new Object[rowNum][6];

		for (int i = 0; i < rowNum; i++) {
			HSSFRow row = sheet.getRow(i + 1);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				object[i][j] = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();

			}
		}
		return object;
	}

	public int getColumnNumber(String sheetName, String cellName) {
		sheet = workbook.getSheet(sheetName);
		HSSFRow row = sheet.getRow(0);
		int cellNumber = 0;
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).toString().equalsIgnoreCase(cellName)) {
				cellNumber = i;
				break;
			}
		}
		return cellNumber;
	}

}
