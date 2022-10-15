package com.ka.operation;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.ka.utility.GetPropertyValue;




public class UIOperationMethods {
	
	public static String PathToStoreScreenShot;
	private static String strParentWindow = "", strNewWindow="";

	public static String enterText(WebDriver driver, String objectName, String objectType, String InputName) throws Exception {

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		try {
			WebElement webelement = driver.findElement(getIdentifier(objectName, objectType));
			if (webelement != null) {

				System.out.println("The object " + webelement.getText() + " is available");
				webelement.clear();
				webelement.sendKeys(InputName);
				return "Passed:: Successfully entered " + InputName + " text in " + objectName + " Object";

			} else {
				System.out.println("Failed - The object " + objectName
						+ " is not available, try another xpath or check the element is available on page");
				return "Failed:: The object " + objectName
						+ " is not available, try another xpath or check the element is available on page";
			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
			return e.toString();

		}
	}

	public static String openPage(WebDriver driver, String InputData) {

		System.out.println("Opening " + InputData);
		try {

			driver.get(InputData);
			return "Passed:: Successfully launch URL - " + InputData;
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	public static String maximizeBrowser(WebDriver driver){
		driver.manage().window().maximize();
		return "Passed::Browser has been maximized successfully";
	}
	
	/**
	 * Keyword to move back to parent window. SWITCH_WINDOW keyword must be used
	 * before it.
	 * 
	 * @param driver
	 * @return
	 */
	public static String parentWindow(WebDriver driver) {
		try {
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			driver.switchTo().window(strParentWindow);
			return "Passed:: Successfully switch to parent window";

		} catch (Exception e) {
			System.out
					.println("::Failed  - Switch Parent Window is not performed. \r\n");
			return "Failed:: Switch Parent Window is not performed. \r\n";
		}

	}
	
	/**
	 * This is a method to perform CLICK operation on any object.
	 * 
	 * @param driver
	 * @param objectName
	 * @param objectType
	 * @return
	 * @throws Exception
	 */

	public static String clickIfAvailable(WebDriver driver, String objectName,
			String objectType) throws Exception {

		String strRes="";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		List<WebElement> webElements;

		try {
			if ((webElements = driver.findElements(getIdentifier(objectName,
					objectType))).size() > 0) {

				webElements.get(0).click();
				// takeScreenShot(driver);
				System.out.println("Successfully clicked on " + objectName
						+ " Object");
				

			} else {
				System.out
						.println("The element " + objectName +" is not available hence not clicked");

			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
			strRes = getErrorMessage(e, objectName);

		}
			return strRes;
	}
	
	public static String mouseMove(WebDriver driver, String objectName,
			String objectType) {
		try {
			driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
			int size = driver.findElements(
					getIdentifier(objectName, objectType)).size();
			if (size == 0) {
				System.out.println("::Failed  - '" + objectName
						+ "' element is not found \r\n");
				return "Failed:: '" + objectName
						+ "' element is not found \r\n";
			} else {
				org.openqa.selenium.Point coordinates = driver.findElement(
						getIdentifier(objectName, objectType)).getLocation();
				Robot robot = new Robot();
				robot.mouseMove(coordinates.getX(), coordinates.getY() + 120);
				System.out
						.println("::Passed - Successfully performed Mouse move on "
								+ objectName + " \r\n");
				return "Passed:: Successfully performed Mouse move on "
						+ objectName + " \r\n";
			}
		} catch (Exception e) {
			System.out.println(getErrorMessage(e, objectName));
			return getErrorMessage(e, objectName);
		}

	}
	
	/**
	 * This is a method to select something from drop down.
	 * 
	 * @param driver
	 * @param ObjectName
	 * @param ObjectType
	 * @param InputData
	 * @return
	 */
	public static String selectFromDropDown(WebDriver driver,
			String ObjectName, String ObjectType, String InputData) {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String inputData[] = InputData.split(";");
		try {

			WebElement element = driver.findElement(getIdentifier(ObjectName,
					ObjectType));
			List<WebElement> elementValues = element.findElements(By
					.tagName(inputData[0]));
			boolean isElementPresent = true;

			for (WebElement i : elementValues) {
				if (i.getText().equals(inputData[1])) {
					i.click();
					System.out.println("\"" + inputData[1] + "\" is selected");
					System.out
							.println("------------------------------------------------------------------");
					isElementPresent = false;

				}
			}
			if (isElementPresent) {
				System.out.println("Element is not available");
				System.out
						.println("------------------------------------------------------------------");
				return "Failed:: Element is not available";

			} else {
				return "Passed:: Element is available and selected";
			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
			return getErrorMessage(e, InputData);

		}

	}
	
	/**
	 * Keyword to set focus on desired Component.
	 * 
	 * @param driver
	 * @param objectName
	 * @param objectType
	 * @return
	 */
	public static String setFocus(WebDriver driver, String objectName,
			String objectType) {
		try {
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			int size = driver.findElements(
					getIdentifier(objectName, objectType)).size();
			if (size == 0) {
				System.out.println("::Failed  - '" + objectName
						+ "' element is not found  \r\n");
				return "Failed:: '" + objectName
						+ "' element is not found  \r\n";
			} else {
				driver.findElement(getIdentifier(objectName, objectType))
						.sendKeys(Keys.TAB);
				System.out.println("::Passed - Successfully Focused on '"
						+ objectName + "' \r\n");// screenshot added
				return "Passed:: Successfully Focused on '" + objectName
						+ "' \r\n";
			}
		} catch (Exception e) {
			System.out.println(getErrorMessage(e, objectName));
			return getErrorMessage(e, objectName);
		}

	}
	
	/**
	 * To verify element is not present. Like closing popup and popup should not
	 * be available.
	 * 
	 * @param driver
	 * @param objectName
	 * @param objectType
	 * @return Message for success or failure.
	 * @author HareshFinadiya
	 */
	public static String verifyElementNotPresent(WebDriver driver,
			String objectName, String objectType) {

		try {
			if (driver.findElements(getIdentifier(objectName, objectType))
					.size() == 0) {
				System.out.println("Element " + objectName
						+ " is not available");
				return "Passed:: Element " + objectName + " is not available";
			} else {
				System.out.println("Element " + objectName + " is available");
				return "Failed:: Element " + objectName + " is available";
			}

		} catch (Exception e) {

			e.printStackTrace(System.out);
			return getErrorMessage(e, objectName);
		}
	}
	
	/**
	 * @param driver
	 * @param objectName
	 * @param objectType
	 * @return
	 * @author HareshFinadiya
	 */
	public static String verifyElementNotVisible(WebDriver driver,
			String objectName, String objectType) {
		String strExecRes = "";
		try {
			if (driver.findElements(getIdentifier(objectName, objectType))
					.size() == 0) {
				System.out
						.println("Element "
								+ objectName
								+ " is not available. Hence no point to check visibility");
				strExecRes = "Failed:: Element is not present on the page. Hence visibility can't be check";

			} else {
				if (driver.findElement(getIdentifier(objectName, objectType))
						.isDisplayed()) {
					System.out.println("Element " + objectName + " is visible");
					strExecRes = "Failed:: Element " + objectName
							+ " is visible";
				} else {
					System.out.println("Element " + objectName
							+ " is not visible");
					strExecRes = "Passed:: Element " + objectName
							+ " is not visible";
				}

			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			strExecRes = getErrorMessage(e, objectName);
		}

		return strExecRes;
	}
	
	public static String verifyElementPresent(WebDriver driver,	String objectName, String objectType) {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		try {
			if (driver.findElements(getIdentifier(objectName, objectType)).size() == 0) {
				System.out.println("Element " + objectName	+ " is not available");
				return "Failed:: Element " + objectName + " is not available";
			} else {
				System.out.println("Element " + objectName + " is available");
				return "Passed:: Element " + objectName + " is available";
			}

		} catch (Exception e) {

			e.printStackTrace(System.out);
			return getErrorMessage(e, objectName);
		}
	}
	
	/**
	 * @param driver
	 * @param objectName
	 * @param objectType
	 * @return
	 * @author DeepenShah
	 */
	public static String verifyElementVisible(WebDriver driver,
			String objectName, String objectType) {
		String strExecRes = "";
		try {
			if (driver.findElements(getIdentifier(objectName, objectType))
					.size() == 0) {
				System.out
						.println("Element "
								+ objectName
								+ " is not present. Hence no point to check visibility");
				strExecRes = "Failed:: Element is not present on the page. Hence visibility can't be check";

			} else {
				if (driver.findElement(getIdentifier(objectName, objectType))
						.isDisplayed()) {
					System.out.println("Element " + objectName + "is visible");
					strExecRes = "Passed:: Element " + objectName
							+ " is visible";
				} else {
					System.out.println("Element " + objectName
							+ "is not visible");
					strExecRes = "Failed:: Element " + objectName
							+ " is not visible";
				}

			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			strExecRes = getErrorMessage(e, objectName);
		}

		return strExecRes;
	}
	
	/**
	 * Keyword to verify if given link is available or not
	 * 
	 * @param driver
	 * @param objectName
	 * @param objectType
	 * @return
	 */
	public static String verifyLink(WebDriver driver, String objectName,
			String objectType) {
		try {
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			if (driver.findElements(getIdentifier(objectName, objectType))
					.size() == 0) {
				System.out.println("::Failed  - '" + objectName
						+ " element is not found \r\n");
				return "Failed:: " + objectName + " element is not found \r\n";
			} else {
				if (driver.findElement(getIdentifier(objectName, objectType))
						.isDisplayed()) {
					System.out.println("Passed :: Sucessfully Performed Link '"
							+ objectName + "' is present.  \r\n");
					return "Passed:: Sucessfully Performed Link '" + objectName
							+ "' is present.  \r\n";
				}
				System.out.println("Failed :: Link '" + objectName
						+ "' is not present  \r\n");
				return "Failed:: Link '" + objectName
						+ "' is not present  \r\n";
			}
		} catch (Exception e) {
			System.out.println(getErrorMessage(e, objectName));
			return getErrorMessage(e, objectName);
		}

	}
	
	
	public static String verifyTextNotMatch(WebDriver driver,
			String ObjectName, String ObjectType, String InputData) {

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement element;
		try {
			element = driver.findElement(getIdentifier(ObjectName, ObjectType));
			if (element != null) {
				String elementText = element.getText().replaceAll("\n", " ");

				/*
				 * Adding this code to remove newline or other breaks
				 */
				// elementText.replaceAll("\n", " ");

				System.out.println("Element Text is : " + elementText);
				// softAssert.assertEquals(elementText, InputData);
				if (elementText.equalsIgnoreCase(InputData)) {
					return "FAILED:: Text <span style='font-weight:bold;'>'"
							+ InputData
							+ "'</span> is verified and matched with: <span style='font-weight:bold;'>'"
							+ elementText + "'</span>";
				} else {
					return "PASSED:: Text <span style='font-weight:bold;'>'"
							+ InputData
							+ "'</span> is verified and not matching with: <span style='font-weight:bold;'>'"
							+ elementText + "'</span>";
				}
			} else {
				return "Failed:: Element not available";
			}

		} catch (Exception e) {

			e.printStackTrace(System.out);
			return getErrorMessage(e, InputData);
		}

	}
	
	/**
	 * This is the method to verify title of the current page
	 * 
	 * @param driver
	 * @param textToVerify
	 * @return
	 */
	public static String verifyTitle(WebDriver driver, String textToVerify) {
		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String pageTitleText = driver.getTitle();
			System.out.println("Title is " + pageTitleText + " and exp " + textToVerify);
			//softAssert.assertEquals(pageTitleText, textToVerify);
			if (pageTitleText.contains(textToVerify)) {
				return "Passed:: Title is verified";
			} else {
				return "Failed::Title is not matching with expected titel data. Expected: <b>" + textToVerify + "</b> and actual <b>" + pageTitleText + "</b>";
			}

		} catch (Exception e) {
			System.out.println("Error is: " + e);
			return getErrorMessage(e, textToVerify);

		}
	}
	
	/**
	 * @param driver
	 * @param ObjectName
	 * @param ObjectType
	 * @param InputData
	 * @return
	 * @author HareshFinadiya
	 */
	public static String selectFromDD(WebDriver driver, String ObjectName,
			String ObjectType, String InputData) {
		String strStepExecRes = "";
		try {

			Select ddElement = new Select(driver.findElement(getIdentifier(
					ObjectName, ObjectType)));
			ddElement.selectByVisibleText(InputData);

			strStepExecRes = "Passed:: Successfully selected value from dropdown";
		} catch (Exception e) {
			e.printStackTrace(System.out);
			strStepExecRes = "Failed::" + getErrorMessage(e, ObjectName);
		}

		return strStepExecRes;
	}
	
	/**
	 * This is a method to close an Alert
	 * 
	 * @param driver
	 * @return
	 */
	public static String closeAlert(WebDriver driver) {
		try {

			driver.switchTo().alert().dismiss();
			return "Passed:: Successfully closed the alert";
		} catch (Exception e) {
			return getErrorMessage(e, "Failed:: Fator error occured");
		}
	}
	
	public static String clickOnLinkText(WebDriver driver, String inputData) {

		driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		WebElement webelement = driver.findElement(By.linkText(inputData));

		try {
			if (webelement != null) {

				webelement.click();
				// takeScreenShot(driver);
				System.out.println("Successfully clicked on " + inputData
						+ " Object");
				return "Passed:: Successfully clicked on " + inputData
						+ " Object";

			} else {
				System.out
						.println("The object is not available, try another xpath or check the element is available on page");
				return "Failed:: The object is not available, try another xpath or check the element is available on page";

			}

		} catch (Exception e) {
			return getErrorMessage(e, inputData);

		}

	}

	
	/**
	 * This is a method to scroll a page by 600 pixels *
	 * 
	 * @param driver
	 * @param InputData
	 * @return
	 */
	public static String scrollPage(WebDriver driver, String InputData) {
		try {
			
			((JavascriptExecutor) driver).executeScript("scroll(0,"+ InputData +")");
			return "Passed:: Successfully scrolled a page";
		} catch (Exception e) {
			return getErrorMessage(e, InputData);
		}
	}
	
	/**
	 * Keyword to switch to other window from Parent window.
	 * 
	 * @param driver
	 * @return
	 */
	public static String switchWindow(WebDriver driver) {
		try {
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			strParentWindow = driver.getWindowHandle();
			System.out.println("Str Parent Window::" + strParentWindow);
			Set<String> handles = driver.getWindowHandles();
			Iterator<String> it = handles.iterator();
			while (it.hasNext()) {
				//strParentWindow = it.next();
				strNewWindow = it.next();
				
				driver.switchTo().window(strNewWindow);
				
				
			}
			System.out.println("Pass - Successfully Switch to '" + strNewWindow
					+ "' is Completed.  \r\n");
			return "Passed:: Successfully Switch to '" + strNewWindow
					+ "' is Completed.  \r\n";
		} catch (Exception e) {
			System.out.println("Unable to Switch Window");
			e.printStackTrace(System.out);
			return getErrorMessage(e, "Unable to Switch Window ");
		}
	}

	public static String reloadPage(WebDriver driver) {


		driver.navigate().refresh();
		return "Passed:: Driver reloaded successfully";

	}
	
	public static String enterKeys(WebDriver driver, String ObjectName,
			String ObjectType, String InputName) throws Exception {

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		WebElement webelement = driver.findElement(getIdentifier(ObjectName,ObjectType));
				
		try {
			if (webelement != null) {

				System.out.println("The object " + webelement.getText()
						+ " is available");
				switch (InputName.toUpperCase()) {

				case "ENTER":
					webelement.sendKeys(Keys.ENTER);
					break;
				case "F1":
					webelement.sendKeys(Keys.F1);
					break;
				case "F2":
					webelement.sendKeys(Keys.F2);
					break;
				case "F3":
					webelement.sendKeys(Keys.F3);
					break;
				case "F4":
					webelement.sendKeys(Keys.F4);
					break;
				case "F5":
					webelement.sendKeys(Keys.F5);
					break;
				case "F6":
					webelement.sendKeys(Keys.F6);
					break;
				case "F7":
					webelement.sendKeys(Keys.F7);
					break;
				case "F8":
					webelement.sendKeys(Keys.F8);
					break;
				case "F9":
					webelement.sendKeys(Keys.F9);
					break;
				case "F10":
					webelement.sendKeys(Keys.F10);
					break;
				case "F11":
					webelement.sendKeys(Keys.F11);
					break;
				case "F12":
					webelement.sendKeys(Keys.F12);
					break;
				default:
					break;

				}
				return "Passed:: Succesfully entered " + InputName
						+ " into the " + ObjectName + " object.";

			} else {
				System.out
						.println("Failed - The object "
								+ ObjectType
								+ " is not available, try another xpath or check the element is available on page");
				return "Failed:: The object "
						+ ObjectType
						+ " is not available, try another xpath or check the element is available on page";
			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
			return getErrorMessage(e, InputName);

		}
	}
	
	/**
	 * @param driver
	 *            WebDriver object
	 * @param strWebelement
	 *            WebElement on which condition to be vierified in String format
	 * @param strWebElementType
	 *            Type of webelement like xpath, link etc
	 * @param strCondition
	 *            Condition to be verified
	 * @param strConditionValue
	 *            Value if condition is like VerifyText
	 * @return
	 * @throws Exception
	 */
	public static String webElementSync(WebDriver driver, String strWebelement,
			String strWebElementType, String strCondition,
			String strConditionValue) throws Exception {

		String strMsg = "";
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.ignoring(NoSuchElementException.class);

		By locator = null;
		if (!strCondition.equalsIgnoreCase("pageload"))
			locator = getIdentifier(strWebelement, strWebElementType);

		try {
			switch (strCondition.toLowerCase()) {
			case "visibility":
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(locator));
				
				break;

			case "notvisible":
				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(locator));
				break;

			case "clickable":
				wait.until(ExpectedConditions.elementToBeClickable(locator));
				break;
			case "pageload":
				System.out.println("Current Window State : "
						+ String.valueOf(((JavascriptExecutor) driver)
								.executeScript("return document.readyState")));
				wait.until(new Function<WebDriver, Boolean>() {

					@Override
					public Boolean apply(WebDriver driver) {
						// TODO Auto-generated method stub
						System.out.println("Current Window State : "
								+ String.valueOf(((JavascriptExecutor) driver)
										.executeScript("return document.readyState")));
						return String
								.valueOf(
										((JavascriptExecutor) driver)
												.executeScript("return document.readyState"))
								.equals("complete");
					}
				});
				break;

			case "verifytext":
				System.out.println("Verifying veriftext, " + strConditionValue + " , for element " + strWebelement);
				wait.until(ExpectedConditions.textToBePresentInElementLocated(
						locator, strConditionValue));
				break;
				
			case "staleness":
				wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
				
				break;
			default:
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(locator));
				break;

			}

			strMsg = "Passed:: Succesfully checked '" + strCondition
					+ "' condition for '" + strWebelement + "'";
		} catch (Exception e) {
			e.printStackTrace(System.out);
			strMsg = "Failed:: Not able to satisfy condition: " + strCondition
					+ " for: " + locator;
		}
		System.out.println(strMsg);

		return strMsg;
	}
	
	
	
	/**
	 * This is a method to verify any particular attribute of the object. To use
	 * this method, enter <attribite_name>;<text to verify> in the InputData
	 * column of test case excel file.
	 * 
	 * @param driver
	 * @param ObjectName
	 * @param ObjectType
	 * @param InputData
	 * @return
	 */
	public static String verifyAttribute(WebDriver driver, String ObjectName,
			String ObjectType, String InputData) {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String attribute[] = InputData.split(";");
		try {
			WebElement element = driver.findElement(getIdentifier(ObjectName,
					ObjectType));
			String strClassName = element.getAttribute(attribute[0]);
			//softAssert.assertEquals(strClassName, attribute[1]);
			if (strClassName.contains(attribute[1])) {
				return "Passed:: Attrubute " + attribute[0]
						+ " matched. Value " + attribute[1];
			} else {
				return "Failed:: Attrubute " + attribute[0]
						+ " not matched. Value found " + strClassName
						+ " and expected " + attribute[1];
			}
		} catch (Exception e) {

			e.printStackTrace(System.out);
			return getErrorMessage(e, InputData);
		}

	}
	/**
	 * This is a method to verify any particular text
	 * @param driver
	 * @param ObjectName
	 * @param ObjectType
	 * @param InputData
	 * @return
	 */
	public static String verifyText(WebDriver driver, String ObjectName,
			String ObjectType, String InputData) {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		
		try {
			WebElement element = driver.findElement(getIdentifier(ObjectName,
					ObjectType));
			String strElementText = element.getText();
			//softAssert.assertEquals(strClassName, attribute[1]);
			if (strElementText.contains(InputData)) {
				return "Passed:: Text '" + strElementText
						+ "' matched. Value is '" + InputData + "'";
			} else {
				return "Failed:: Text '" + InputData
						+ "' not matched. Value found '" + strElementText
						+ "' and expected '" + InputData + "'";
			}
		} catch (Exception e) {

			e.printStackTrace(System.out);
			return "Failed ::" + getErrorMessage(e, InputData);
		}

	}
	
	public static String enterKeys(String objectName, String inputData){
		String strMsg ="";	
		
		try{
			
		
			Robot rb = new Robot();
			
			switch(objectName.toLowerCase()){
				case "enter": rb.keyPress(KeyEvent.VK_ENTER);
				rb.keyRelease(KeyEvent.VK_ENTER);
							break;
							
				case "tab" :rb.keyPress(KeyEvent.VK_TAB);
							rb.keyRelease(KeyEvent.VK_TAB);
							break;
							
				case "copypaste": System.out.println("Pasting " + inputData);
								StringSelection strSel = new StringSelection(inputData);					
								Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
								
								clipboard.setContents(strSel,null);
								
								rb.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
								rb.keyPress(java.awt.event.KeyEvent.VK_V);
								rb.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
								rb.keyRelease(java.awt.event.KeyEvent.VK_V);
								break;
			}
			
			strMsg = "Passed:: Successfully entered " + objectName +".";
		
		}catch(Exception e){
			e.printStackTrace(System.out);
			strMsg = "Failed:: Somne error occured. Check above stacktrace";
			
		}
		
		
		return strMsg;
	}
	
	public static String click(WebDriver driver, String objectName, String objectType) throws Exception {

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		try {
			WebElement webelement = driver.findElement(getIdentifier(objectName, objectType));
			if (webelement != null) {

				System.out.println("TSuccessfully clicked on " + objectName + " is available");
				webelement.click();
				return "Passed:: Successfully clicked on " + objectName;

			} else {
				System.out.println("Failed - The object " + objectName
						+ " is not available, try another xpath or check the element is available on page");
				return "Failed:: The object " + objectName
						+ " is not available, try another xpath or check the element is available on page";
			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
			return "Failed :: "+e.getMessage();

		}
	}
	
	
	public static void takeScreenshot(WebDriver driver, String testCaseName,
			String keyWord, String objectName, String stepExecutionResult,
			int stepNum) {

		String pattern = "MM_dd_yyyy_hh";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String filename;
		
		filename = testCaseName
				+ "_"
				+ Integer.toString(stepNum)
				+ "."
				+ keyWord
				+ "_"
				+ objectName
				+ ".png";

		if (stepExecutionResult.toUpperCase().contains("PASS")) {

			PathToStoreScreenShot = System.getProperty("user.dir")
					+ File.separator
					+ "Output"
					+ File.separator
					+ "Screenshot"
					+ File.separator
					+ "Run At_"
					+ format.format(new Date()) 
					+ File.separator
					+ "Passed test case screenshots";
		} else if (stepExecutionResult.toUpperCase().contains("FAIL")) {
			PathToStoreScreenShot = System.getProperty("user.dir")
					+ File.separator
					+ "Output"
					+ File.separator
					+ "Screenshot"
					+ File.separator
					+ "Run At_"
					+ format.format(new Date()) 
					+ File.separator
					+ "Failed test case screenshots";

		}else if(stepExecutionResult.toUpperCase().contains("ERROR")){
			PathToStoreScreenShot = System.getProperty("user.dir")
					+ File.separator
					+ "Output"
					+ File.separator
					+ "Screenshot"
					+ File.separator
					+ "Run At_"
					+ format.format(new Date()) 
					+ File.separator
					+ "Error test case screenshots";
		}
		File directory = new File(PathToStoreScreenShot);
		if (!directory.exists()) {
			directory.mkdir();
		}

		String screenshotLocationFile = PathToStoreScreenShot + File.separator
				+ filename;

		File screenshotDestination = new File(screenshotLocationFile);

		try {
			File screenShot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(screenShot, screenshotDestination);

		} catch (Exception e) {
			System.out.println("Exception occured while taking screenshot");
			e.printStackTrace(System.out);
		}

	}
	
	public static String getErrorMessage(Exception e, String objectName) {
		if (e.getClass().getName()
				.equals("org.openqa.selenium.UnhandledAlertException")) {
			return "Failed:: '" + objectName + "'is not Found. \r\n";
		} else if (e.getClass().getName()
				.equals("org.openqa.selenium.NoSuchElementException")) {
			return "Failed:: '" + objectName + "' Element is not Found. \r\n";
		} else if (e.getClass().getName()
				.equals("org.openqa.selenium.ElementNotVisibleException")) {
			return "Failed:: '" + objectName
					+ "' Element is not visible. \r\n";
		} else if (e.getClass().getName()
				.equals("org.openqa.selenium.WebDriverException")) {
			return "Failed:: '" + objectName + "'is not Found. \r\n";
		} else {
			return "Failed:: '" + objectName + "'is not Found. \r\n";
		}
	}
	
	private static By getIdentifier(String objectName, String objectType)
			throws Exception {

		Properties allObjects = GetPropertyValue.getObjectRepository();

		if (objectType.equalsIgnoreCase("ID")) {
			return By.id(allObjects.getProperty(objectName));
		} else if (objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(allObjects.getProperty(objectName));
		} else if (objectType.equalsIgnoreCase("CSS")) {
			return By.cssSelector(allObjects.getProperty(objectName));
		} else if (objectType.equalsIgnoreCase("CLASSNAME")) {
			return By.className(allObjects.getProperty(objectName));
		} else if (objectType.equalsIgnoreCase("LINKTEXT")) {
			return By.linkText(allObjects.getProperty(objectName));
		} else if (objectType.equalsIgnoreCase("PARTIAL_LINKTEXT")) {
			return By.partialLinkText(allObjects.getProperty(objectName));
		} else if (objectType.equalsIgnoreCase("TAGNAME")) {
			return By.tagName(allObjects.getProperty(objectName));
		} else if (objectType.equalsIgnoreCase("NAME")) {
			return By.name(allObjects.getProperty(objectName));
		}  else {
			throw new Exception("Wrong Object type entered");
		}
	}

}
