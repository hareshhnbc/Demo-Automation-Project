package com.ka.driver;

import java.util.List;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ka.operation.UIOperation;
import com.ka.operation.UIOperationMethods;
import com.ka.pojo.TestData;
import com.ka.utility.ExcelReader;
import com.ka.utility.ExcelWriter;
import com.ka.utility.ReportGenerator;
import com.ka.utility.SuiteConstants;
import com.ka.utility.TestSuiteUtility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestSuiteBase {
	public WebDriver driver;
	public static DesiredCapabilities capabilities;
	public static ExcelWriter excelWriter = new ExcelWriter();
	public ExtentTest test;
	
	public void loadBrowser(String browserName) {
		if (browserName.equalsIgnoreCase(SuiteConstants.FIREFOX_BROWSER)) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/BrowserDrivers/geckodriver.exe");
			capabilities = DesiredCapabilities.firefox();
			//driver = new FirefoxDriver();
			try {
				driver=new FirefoxDriver();
				//driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (browserName.equalsIgnoreCase(SuiteConstants.CHROME_BROWSER)) {
			try {
			
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/BrowserDrivers/chromedriver.exe");
			driver = new ChromeDriver();
			
			/*capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList(
					"--start-maximized", "--disable-popup-blocking"));
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/BrowserDrivers/chromedriver.exe");
			// Code to faster execution on Chrome 
			ChromeOptions chromOpt = new ChromeOptions();
			chromOpt.addArguments("Proxy","null");
			capabilities.setCapability(ChromeOptions.CAPABILITY,chromOpt );
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:5555/wd/hub"), capabilities);*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (browserName.equalsIgnoreCase(SuiteConstants.IE_BROWSER)) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/BrowserDrivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		else {
			System.out.println("Not supporting other browers now");
		}
	}

	@BeforeSuite
	public void beforeSuite() {
		ReportGenerator.generateReport();
	}


	@AfterSuite
	public void afterSuite() {
		ReportGenerator.reporter.flush();
		ReportGenerator.reporter.close();
	}

	public void businessCaseExecution(String strTestSuiteName, String strTestCaseName){
		test.log(LogStatus.PASS,"" ,"<font color=\"green\">---------Business Function Start---------</font>");
		ExcelReader xls = new ExcelReader("InputData/BusinessFunction/BusinessFunction" + ".xls");
		String strTestStepExecutionResult;
		int stepNum = 1;
		UIOperation operation = new UIOperation(this);
		//System.out.println(strTestCaseName);
		List<TestData> testCaseData = TestSuiteUtility.getTestData(xls, strTestCaseName);
			
		if (testCaseData != null && testCaseData.size() > 0) {

			for (TestData testCaseStepData : testCaseData) {

				try {
					strTestStepExecutionResult = operation.perform(testCaseStepData.getKeyword(), testCaseStepData.getObjectName(), testCaseStepData.getObjectType(),
							testCaseStepData.getInputData());
				} catch (Exception e) {
					System.out.println("Error while executing the test");
					e.printStackTrace();
					strTestStepExecutionResult = "Error:: " + e.getMessage() + ". See the logs for the errors";
				}
				String strDesc = stepNum + ". " + testCaseStepData.getKeyword();
				ReportGenerator.generateReportStep(test, strTestStepExecutionResult, strDesc, testCaseStepData.getPurpose());
				if (testCaseStepData.getTakeScreenShot().toUpperCase().contains("Y") || strTestStepExecutionResult.toUpperCase().contains("FAILED")
						|| strTestStepExecutionResult.toUpperCase().contains("ERROR")) {
					UIOperationMethods.takeScreenshot(driver, strTestCaseName, testCaseStepData.getKeyword(), testCaseStepData.getObjectName(), strTestStepExecutionResult,
							stepNum);
				}
				stepNum++;
			}

		}
		
	}
	public boolean keyWordExecution(String strTestSuiteName, String strTestCaseName, String strTestCaseCategory,ExcelReader xls) {
		
		String strTestStepExecutionResult;
		boolean testCasePass = true;
		int stepNum = 1;
		UIOperation operation = new UIOperation(this);
		List<TestData> testCaseData = TestSuiteUtility.getTestData(xls, strTestCaseName);
		System.out.println("Test Case Name : " + strTestCaseName);
		System.out.println("Test suite name : " + strTestSuiteName);
		test = ReportGenerator.reporter.startTest(strTestCaseName);
		
		
		test.assignCategory(strTestCaseCategory);
       
        
		if (testCaseData != null && testCaseData.size() > 0) {

			for (TestData testCaseStepData : testCaseData) {

				try {
					strTestStepExecutionResult = operation.perform(testCaseStepData.getKeyword(), testCaseStepData.getObjectName(), testCaseStepData.getObjectType(),
							testCaseStepData.getInputData());
				} catch (Exception e) {
					System.out.println("Error while executing the test");
					e.printStackTrace();
					strTestStepExecutionResult = "Error:: " + e.getMessage() + ". See the logs for the errors";
				}
				String strDesc = stepNum + ". " + testCaseStepData.getKeyword();
				ReportGenerator.generateReportStep(test, strTestStepExecutionResult, strDesc, testCaseStepData.getPurpose());
				
				if (testCaseStepData.getTakeScreenShot().toUpperCase().contains("Y") || strTestStepExecutionResult.toUpperCase().contains("FAILED")
						|| strTestStepExecutionResult.toUpperCase().contains("ERROR")) {
					UIOperationMethods.takeScreenshot(driver, strTestCaseName, testCaseStepData.getKeyword(), testCaseStepData.getObjectName(), strTestStepExecutionResult,
							stepNum);
					if (strTestStepExecutionResult.toUpperCase().contains("FAILED")
							|| strTestStepExecutionResult.toUpperCase().contains("ERROR")){
						testCasePass = false;
					}
				}
				stepNum++;
			}
		}		
		ReportGenerator.reporter.endTest(test);
		ReportGenerator.reporter.flush();
		
		return testCasePass;
	}
	public void closeBrowsers() {
		driver.quit();
		driver = null;
	}
}
