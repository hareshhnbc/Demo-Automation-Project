package com.ka.utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class ReportGenerator {
	 
	public static String reportName = null;
	public static ExtentReports reporter;
	public File directory ;
	
	public static void generateReport() {	
			
			String pattern = "MM_dd_yyyy_hh_mm";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String directoryToCreateReportPath = System.getProperty("user.dir")+ File.separator + "Reports";
	
			File directory = new File(directoryToCreateReportPath);
			if (!directory.exists())			
				directory.mkdirs();
	
			reportName = directoryToCreateReportPath + File.separator
					+ "Demo Test Automation Report_" + "_" + format.format(new Date())+ ".html";
			System.out.println(System.getProperty("user.dir")+File.separator + "extent-config.xml");
			
			reporter = new ExtentReports(reportName, false);
			reporter.loadConfig(new File(System.getProperty("user.dir")+File.separator + "extent-config.xml"));
			//reporter.loadConfig(ExtentReports.class, "extent-config.xml");			
	}
	
	
	public static void generateReportStep(ExtentTest test,String stepExecutionResult, String keyWord, String purpose) {
		
		String stepExecutionResultMessage[] = stepExecutionResult.split("::");		
		if (stepExecutionResult.contains("Passed")) {	
			test.log(LogStatus.PASS, keyWord + " - " + purpose,stepExecutionResultMessage[1]);
			System.out.println(stepExecutionResultMessage[1]);
			
		} else if (stepExecutionResult.contains("Failed")) {
			test.log(LogStatus.FAIL, keyWord + " - " + purpose,stepExecutionResultMessage[1]);
					
		} else {
			test.log(LogStatus.ERROR, keyWord + " - " + purpose,stepExecutionResultMessage[1]);
		}
	}
}
