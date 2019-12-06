package HollandCRM_Prod_Smoke;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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



public class Access_ResourceRoles_Resourcelist_Sales extends BaseTest {
	String testCaseName="Access_ResourceRoles_Resourcelist_Sales";
	SoftAssert softAssert; 
	Xls_Reader xls;
	
	
	@Test(priority=1,dataProvider="getData")
	public void Access_Resourcelist_Sales(Hashtable<String,String> data1) throws IOException {
		softAssert = new SoftAssert();
		test= rep.startTest("Access_ResourceRoles_Resourcelist_Sales ");
		test.log(LogStatus.INFO, "Starting the Access_ResourceRoles_Resourcelist_Sales");
			if(!DataUtil.isRunnable(testCaseName, xls) || data1.get("Runmode").equals("N")) {
				test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
				throw new SkipException("Skipping the test as runmode is N");
			}
			test.log(LogStatus.INFO, data1.toString());

			openBrowser(data1.get("Browser"));
			
			

			navigate("app1url");
			doLogin(envProp.getProperty("username"), envProp.getProperty("password"));
			wait(1);
			
			wait(2);
			click("Next_css");
			
		//	softAssert.assertTrue(verifyText("Project_Ser_xpath","Project_Service_xpath"), "Text did not match");
			
			if(!isElementPresent("Holland_xpath"))
				reportFailure("Holland is not present by please verify Xpath");
			
			click("Holland_xpath");
			
			test.log(LogStatus.PASS, "Test Navigate to Holland button is successfull");
		}
	@Test(priority=2,dataProvider="getData")
	public void Validate_NavToSelectedProject_Name_Displayed(Hashtable<String,String> data1){
		softAssert = new SoftAssert();
		test= rep.startTest("Validate_2NavToSelectedProject_Name_Displayed");
		test.log(LogStatus.INFO, "Starting the Validate_2NavToSelectedProject_Name_Displayed");

		
		
		wait(2);
		
		ScrollToElement("ResourceRolesMain_xpath");
		
		if(!isElementPresent("ResourceRolesMain_xpath"))
			reportFailure("Resource Roles is not present by please verify Xpath");
		click("ResourceRolesMain_xpath");
		
		test.log(LogStatus.PASS, "Test Validate_Access To List of Resource Roles is successfull");
	
		
		
		
	/*	
//	String General1=getElement("ResourceName_linktext").getText();
		String General = driver.findElement(By.linkText(Name)).getText();
		System.out.println(General);
//	assertEquals(General, Name);
		assertTrue(General.equals(Name));

		
		test.log(LogStatus.PASS, "Test Validate_NavToSelectedProject_Name_Displayed is successfull");*/
	}	
	
	@Test(priority=3,dataProvider="getData")
	public void Validate_NavToSelectedRoles(Hashtable<String,String> data1){
		softAssert = new SoftAssert();
		test= rep.startTest("Validate_NavToSelectedRoles");
		test.log(LogStatus.INFO, "Starting the Validate_NavToSelectedRoles");

		
		ScrollToElement("RolesAdminiAssistant_linktext");
		
		if(!isElementPresent("RolesAdminiAssistant_linktext"))
			reportFailure("Resource Role Administation Assistant is not present by please verify Xpath");
		click("RolesAdminiAssistant_linktext");
		
		test.log(LogStatus.PASS, "Test Validate_Access To List of Resource Roles is successfull");
	
	}	
	
	@Test(priority=4,dataProvider="getData")
	public void Validate_NavToSelectedRolesLandingPage(Hashtable<String,String> data1){
		softAssert = new SoftAssert();
		test= rep.startTest("Validate_NavToSelectedRolesLandingPage");
		test.log(LogStatus.INFO, "Starting the Validate_NavToSelectedRolesLandingPage");

		
		ScrollToElement("RolesLandingProp_xpath");
		
		if(!isElementPresent("RolesLandingProp_xpath"))
			reportFailure("Resource Role Administation Assistant Landing Page is not present by please verify Xpath");
		click("RolesLandingProp_xpath");
		
		test.log(LogStatus.PASS, "Test Resource Role Administation Assistant Landing Page is successfull");
	
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


