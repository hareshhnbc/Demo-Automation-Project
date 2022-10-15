package com.ka.operation;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.JavascriptExecutor;

import com.ka.driver.TestSuiteBase;



public class UIOperation {

	public TestSuiteBase testBase;
	WebDriver driver;

	public UIOperation(TestSuiteBase testBase) {
		this.testBase = testBase;

		driver = testBase.driver;
	}

	public String perform(String keyword, String objectName, String objectType, String inputData) throws Exception {
		String stepExecutionStatus = null;
		switch (keyword.toUpperCase()) {
		case "MAXIMIZE_BROWSER":
			stepExecutionStatus = UIOperationMethods.maximizeBrowser(driver);
			break;
		case "CLICK":
			stepExecutionStatus = UIOperationMethods.click(driver, objectName, objectType);
			break;
		case "CLICK_ON_LINK_TEXT":
			stepExecutionStatus = UIOperationMethods.clickOnLinkText(driver,
					inputData);
			break;	
		case "BUSINESS_FUNCTION":
			
			testBase.businessCaseExecution("Test Suite", objectName);
			
			stepExecutionStatus = "Passed::<font color=\"green\">---------Business Function Executed Successfully---------</font>";
			break;
		case "CLOSE_ALERT":
			stepExecutionStatus = UIOperationMethods.closeAlert(driver);
			break;
		case "OPEN":
			stepExecutionStatus = UIOperationMethods.openPage(driver, inputData);
			break;
		case "SLEEP":
			try {
				Thread.sleep(5000);
				stepExecutionStatus = "Passed:: Thread slept successfully";
			} catch (Exception e) {
				stepExecutionStatus = "Failed:: Something went wrong " + e;
			}
			break;
		case "ENTER_TEXT":
			stepExecutionStatus = UIOperationMethods.enterText(driver, objectName, objectType, inputData);
			break;
		case "ENTER_KEYS":
			// System.out.println(driver.getCurrentUrl());
			if (objectType.equalsIgnoreCase("")) {
				stepExecutionStatus = UIOperationMethods.enterKeys(objectName, inputData);
			} else {
				stepExecutionStatus = UIOperationMethods.enterKeys(driver, objectName, objectType, inputData);
			}
			break;
		case "VERIFY_ATTRIBUTE":
			stepExecutionStatus = UIOperationMethods.verifyAttribute(driver, objectName, objectType, inputData);
			break;
		case "VERIFY_TEXT":
			stepExecutionStatus = UIOperationMethods.verifyText(driver, objectName, objectType, inputData);
			break;
		case "RELOAD_PAGE":
			stepExecutionStatus = UIOperationMethods.reloadPage(driver);
			break;
		case "SCROLL":
			stepExecutionStatus = UIOperationMethods.scrollPage(driver, inputData);
			
		case "WAITUNTIL":
			String[] objnames = objectName.split("::");

			if (objnames.length == 2) {
				stepExecutionStatus = UIOperationMethods.webElementSync(driver, objnames[0], objectType, objnames[1], "");
			} else if (objnames.length == 3) {
				stepExecutionStatus = UIOperationMethods.webElementSync(driver, objnames[0], objectType, objnames[1], objnames[2]);
			}
			break;
		case "VERIFY_ELEMENT_VISIBLE":
			stepExecutionStatus = UIOperationMethods.verifyElementVisible(
					driver, objectName, objectType);
			break;
		case "VERIFY_ELEMENT_NOT_VISIBLE":
			stepExecutionStatus = UIOperationMethods.verifyElementNotVisible(
					driver, objectName, objectType);
			break;
		case "VERIFY_ELEMENT_NOT_PRESENT":
			stepExecutionStatus = UIOperationMethods.verifyElementNotPresent(
					driver, objectName, objectType);
			break;
		case "VERIFY_ELEMENT_PRESENT":
			stepExecutionStatus = UIOperationMethods.verifyElementPresent(driver, objectName, objectType);
			break;
		case "VERIFY_TEXT_NOT_MATCH":
			stepExecutionStatus = UIOperationMethods.verifyTextNotMatch(driver, objectName, objectType, inputData);
			break;
		case "VERIFY_LINK":
			stepExecutionStatus = UIOperationMethods.verifyLink(driver, objectName, objectType);
			break;
		case "VERIFY_TITLE":
			stepExecutionStatus = UIOperationMethods.verifyTitle(driver, inputData);
			break;
		case "WAIT":
			Thread.sleep(5000);
			stepExecutionStatus = "Passed :: Execution halted for 5 Seconds";
			break;
		case "LONGWAIT":
			Thread.sleep(20000);
			stepExecutionStatus = "Passed :: Execution halted for 20 Seconds";
			break;
		case "BACK":
			try {
				driver.navigate().back();
				stepExecutionStatus = "Passed:: Successfully navigated back";
			} catch (Exception e) {
				stepExecutionStatus = "Failed:: " + e.toString();
			}
			break;
		case "MOUSE_MOVE":
			stepExecutionStatus = UIOperationMethods.mouseMove(driver, objectName, objectType);
			break;
		case "SELECT_FROM_DROPDOWN":
			stepExecutionStatus=UIOperationMethods.selectFromDropDown(driver, objectName, objectType, inputData);
			break;
		case "SELECT_DROPDOWN_BY_VISIBLE_TEXT":
			stepExecutionStatus = UIOperationMethods.selectFromDropDown(driver, objectName, objectType, inputData);
			break;
		case "SWITCH_WINDOW":
			stepExecutionStatus = UIOperationMethods.parentWindow(driver);
			break;
		case "SCROLL_HOME":
			try {
				((JavascriptExecutor) driver).executeScript("scroll(1000,0)");
				stepExecutionStatus = "Passed:: Successfully scroll back";
			} catch (Exception e) {
				e.printStackTrace(System.out);
				stepExecutionStatus = "Failed:: " + e.toString();
			}
			break;
		
		default:
			stepExecutionStatus = "Failed:: Wrong Keyword " + keyword.toUpperCase() + " has been entered, please check available keyword list.";
			break;
		}
		return stepExecutionStatus;
	}

}
