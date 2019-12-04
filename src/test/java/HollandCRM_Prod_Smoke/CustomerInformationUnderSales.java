package HollandCRM_Prod_Smoke;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.datadriven.frame.base.BaseTest;
import com.datadriven.frame.util.DataUtil;
import com.datadriven.frame.util.ExtentManager;
import com.datadriven.frame.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class CustomerInformationUnderSales extends BaseTest {
	String testCaseName="AccountInformationUnderSales";
	SoftAssert softAssert; 
	Xls_Reader xls;
	
	
	@Test(priority=1,dataProvider="getData")
	public void Validate_AccessToAccountListUnderSales(Hashtable<String,String> data1) throws IOException {
		softAssert = new SoftAssert();
		test= rep.startTest("Validate_AccessToAccountListUnderSales");
		test.log(LogStatus.INFO, "Starting the Validate_AccessToAccountListUnderSales");
			if(!DataUtil.isRunnable(testCaseName, xls) || data1.get("Runmode").equals("N")) {
				test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
				throw new SkipException("Skipping the test as runmode is N");
			}
			test.log(LogStatus.INFO, data1.toString());

			openBrowser(data1.get("Browser"));
			
			

			navigate("app1url");
			doLogin(envProp.getProperty("username"), envProp.getProperty("password"));

			wait(2);
			click("Next_css");
			
		//	softAssert.assertTrue(verifyText("Project_Ser_xpath","Project_Service_xpath"), "Text did not match");
			
			if(!isElementPresent("Holland_xpath"))
				reportFailure("Holland is not present by please verify Xpath");
			
			click("Holland_xpath");
			
			test.log(LogStatus.PASS, "Test Navigate to Holland button is successfull");
		
	}
	@Test(priority=2,dataProvider="getData")
	public void Validate_AccountInformation_Displayed_AccountListUnderSales(Hashtable<String,String> data1){
		softAssert = new SoftAssert();
		test= rep.startTest("GeneralInformation_Displayed_ContactListUnderSales");
		test.log(LogStatus.INFO, "Starting the GeneralInformation_Displayed_ContactListUnderSales");
		
		ScrollToElement("Customer_xpath");
		
		if(!isElementPresent("Customer_xpath"))
			reportFailure("Customer is not present by please verify the locator property");
		
	
		wait(2);
		click("Customer_xpath");
		
		
		if(!isElementPresent("Customer_Dropdown_xpath"))
			reportFailure("Customer Dropdown is not present by please verify the locator property");
		
	
		wait(2);
		click("Customer_Dropdown_xpath");
		
		
		if(!isElementPresent("AllAccount_xpath"))
			reportFailure("All Account under customer is not present by please verify the locator property");
		wait(2);
		click("AllAccount_xpath");
			
		if(!isElementPresent("Customer_linktext"))
			reportFailure("Customer_linktext is not present by please verify the locator property");
		wait(2);
		click("Customer_linktext");
		
		/*
		String IName=driver.findElement(By.tagName("Iframe")).getText();
		System.out.println(IName);
		SmartFrames(data1.get("Browser"));  */

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='account information_c']"))));
		
		IsDisplayed("AccountInfo_xpath");
		
		String AccountInfo =getElement("AccountInfo_xpath").getText();
		System.out.println(AccountInfo);
		assertTrue(AccountInfo.equals("Account Information"));
		
		test.log(LogStatus.PASS, "Test Validate_AccountInformation_Displayed_AccountListUnderSales2 is successfull");
	}	
	

	
	@Test(priority=3,dependsOnMethods={"Validate_AccountInformation_Displayed_AccountListUnderSales"}) 
	public void Validate_AccountName_Displayed_AccountListUnderSales() {
		softAssert = new SoftAssert();
		test= rep.startTest("Validate_AccountName_Displayed_AccountListUnderSales");
		test.log(LogStatus.INFO, "Starting the Validate_AccountName_Displayed_AccountListUnderSales");
		
		String AccountName1=getElement("AccountName_xpath").getText();
		assertTrue(AccountName1.equals("Adkins Energy"));
		IsDisplayed("AccountName_xpath");
		
		test.log(LogStatus.PASS, "Test Validate_AccountName_Displayed_AccountListUnderSales is successfull");
	}
	
	@Test(priority=4,dependsOnMethods={"Validate_AccountName_Displayed_AccountListUnderSales"})
	public void AccountInformationUnderSales(){
		softAssert = new SoftAssert();
		test= rep.startTest("AccountInformationUnderSales");
		test.log(LogStatus.INFO, "Starting the AccountInformationUnderSales");
	
		test.log(LogStatus.PASS, "Dependant tests are successful so AccountInformationUnderSales is successfull");
		
	}

	@BeforeMethod
	public void init() {
		softAssert = new SoftAssert();
		try {
			super.init();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@AfterTest
	public void quit(){
		signOut();
		try {
			softAssert.assertAll();
		}catch (Error e) {
			test.log(LogStatus.FAIL, e.getMessage());
		}
		if(rep!=null){
			rep.endTest(test);
			rep.flush();
		}
		
		if(driver!=null)
			driver.quit();
	}


@DataProvider(parallel=true)
public Object[][] getData() throws IOException{
	init();
	xls = new Xls_Reader(System.getProperty("user.dir")+"\\config\\testcases\\SmokeTestData.xlsx");

	
	return DataUtil.getTestData(xls, testCaseName);
} 
}


