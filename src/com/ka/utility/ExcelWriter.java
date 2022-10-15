package com.ka.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.ka.pojo.TestCaseResult;
import com.ka.testsuite.TestSuiteExecuter;

public class ExcelWriter {
	public HSSFWorkbook workbook;
	public FileOutputStream fileout;
	public HSSFSheet spreadsheet;
	TestSuiteExecuter testsuiteexecuter;
	TestCaseResult testcaseresult;

	
	
	public ExcelWriter() {
		System.out.println("Executing excelwriter");
		workbook = new HSSFWorkbook();
		String pattern = "MM_dd_yyyy_hh_mm";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String directoryToCreateReportPath = System.getProperty("user.dir")+ File.separator + "Excel Reports";
		File directory = new File(directoryToCreateReportPath);
		if (!directory.exists())
			directory.mkdirs();
		String filePath = directoryToCreateReportPath + File.separator + "Demo Automation Report_" + "_"
				+ format.format(new Date()) + ".xls";

		try {
			fileout = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void createTestCaseResultSheet(String testSuiteName){
		spreadsheet = workbook.createSheet(testSuiteName);	
		HSSFRow rowhead = spreadsheet.createRow((short) 0);
		HSSFCell rowheadcell = rowhead.createCell(0);
		HSSFCell rowheadcell1 = rowhead.createCell(1);
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		rowheadcell.setCellStyle(headerStyle);
		rowheadcell1.setCellStyle(headerStyle);
		rowheadcell.setCellValue(new HSSFRichTextString("Test Cases"));
		rowheadcell1.setCellValue(new HSSFRichTextString("Results"));
	}

	public void getTestResultData(TestCaseResult testCaseResult) {
		System.out.println("Inside getTestResultData method");
		Map<String, Map<String, String>> resultData = TestCaseResult.getTestSuiteResultMap();
		for (Map.Entry<String, Map<String, String>> testCaseList : resultData.entrySet()) {
		      System.out.println(testCaseList.getKey());
			Map<String, String> testresult = testCaseResult.getTestCaseResultMap();
			int i = 1;
			for (Map.Entry<String, String> testResult : testresult.entrySet()) {
				System.out.println(testResult.getKey() + " : " + testResult.getValue());
				if (testResult.getValue() == "PASS") {
					HSSFRow newrow = spreadsheet.createRow(i);
					HSSFCellStyle style = workbook.createCellStyle();
					style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
					style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					style.setBorderRight(HSSFCellStyle.BORDER_THIN);
					style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

					HSSFCell rowcell = newrow.createCell(0);
					rowcell.setCellValue(testResult.getKey());
					rowcell.setCellStyle(style);

					HSSFCell rowcell1 = newrow.createCell(1);
					rowcell1.setCellValue("PASS");
					rowcell1.setCellStyle(style);

				} else if(testResult.getValue() == "FAIL") {
					HSSFRow newrow = spreadsheet.createRow(i);
					HSSFCellStyle style = workbook.createCellStyle();
					style.setFillForegroundColor(IndexedColors.RED.getIndex());
					style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					style.setBorderRight(HSSFCellStyle.BORDER_THIN);
					style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

					HSSFCell rowcell = newrow.createCell(0);
					rowcell.setCellValue(testResult.getKey());
					rowcell.setCellStyle(style);

					HSSFCell rowcell1 = newrow.createCell(1);
					rowcell1.setCellValue("FAILED");
					rowcell1.setCellStyle(style);
				}
				i++;
			}
		}}
	

/*	public void GenrateExcel(TestCaseResult tcResult) {
		Map<String, String> testresult = tcResult.getTestCaseResultMap();
		int i = 1;
		for (Map.Entry<String, String> testResult : testresult.entrySet()) {
			if (testResult.getValue() == "PASS") {
				HSSFRow newrow = spreadsheet.createRow(i);
				HSSFCellStyle style = workbook.createCellStyle();
				style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

				HSSFCell rowcell = newrow.createCell(0);
				rowcell.setCellValue(testResult.getKey());
				rowcell.setCellStyle(style);

				HSSFCell rowcell1 = newrow.createCell(1);
				rowcell1.setCellValue("PASS");
				rowcell1.setCellStyle(style);

			} else if(testResult.getValue() == "FAIL") {
				HSSFRow newrow = spreadsheet.createRow(i);
				HSSFCellStyle style = workbook.createCellStyle();
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

				HSSFCell rowcell = newrow.createCell(0);
				rowcell.setCellValue(testResult.getKey());
				rowcell.setCellStyle(style);

				HSSFCell rowcell1 = newrow.createCell(1);
				rowcell1.setCellValue("FAILED");
				rowcell1.setCellStyle(style);
			}
			i++;
		}
	}*/
	public void writeToExcel() {
		try {
			workbook.write(fileout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

