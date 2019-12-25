package HollandCRM_Prod_Smoke;
import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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



public class Access_Resource_Resourcelist_Sales extends BaseTest {
	String testCaseName="Access_Resource_Resourcelist_Sales";
	SoftAssert softAssert; 
	Xls_Reader xls;
	
	
	@Test(priority=1,dataProvider="getData")
	public void ValidateAccessToResourceUnderSales(Hashtable<String,String> data1) throws IOException {
		softAssert = new SoftAssert();
		test= rep.startTest("Access_Resource_Resourcelist_Sales");
		test.log(LogStatus.INFO, "Starting the Access_Resource_Resourcelist_Sales");
			if(!DataUtil.isRunnable(testCaseName, xls) || data1.get("Runmode").equals("N")) {
				test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
				throw new SkipException("Skipping the test as runmode is N");
			}
			test.log(LogStatus.INFO, data1.toString());

			openBrowser(data1.get("Browser"));

			navigate("app1url");
			doLogin(envProp.getProperty("username"), envProp.getProperty("password"));
			
			wait(2);
		//	click("Next_css");
			
		//	softAssert.assertTrue(verifyText("Project_Ser_xpath","Project_Service_xpath"), "Text did not match");
			
		//	click("Holland_xpath");
			
			ScrollToElement("ResourceN_xpath");

			wait(3);
			if(!isElementPresent("ResourceN_xpath"))
				reportFailure("Resource is not present by please verify Xpath");
			
			
			click("ResourceN_xpath");
			wait(1);
			wait(2);
			test.log(LogStatus.PASS, "Test ValidateAccessToResourceUnderSales is successfull");

	
	}
	@Test(priority=2,dependsOnMethods={"ValidateAccessToResourceUnderSales"},dataProvider="getData")
	public void Validate_NavToSelectedResource_Name_Displayed(Hashtable<String,String> data1){
		softAssert = new SoftAssert();
		test= rep.startTest("Validate_NavToSelectedResource_Name_Displayed");
		test.log(LogStatus.INFO, "Starting the Validate_NavToSelectedResource_Name_Displayed");
		//DefaultLanding();
		//TotalAvailFrames();
		//FrameIndex(0);
		wait(2);
	
		click("Resource_name_linktext");
		//DefaultLanding();
//		driver.switchTo().parentFrame();
	//	TotalAvailFrames();
		//SmartFrames(data1.get("Browser")); 
		
		//IsDisplayed("Resource_Name_id");
		test.log(LogStatus.PASS, "Test Validate_NavToSelectedResource_Name_Displayed is successfull");
	}
		
		@Test(priority=3,dependsOnMethods={"Validate_NavToSelectedResource_Name_Displayed"})
		public void Access_Resource_Resourcelist_Sales(){
			softAssert = new SoftAssert();
			test= rep.startTest("Access_Resource_Resourcelist_Sales");
			test.log(LogStatus.INFO, "Starting the Access_Resource_Resourcelist_Sales");
		
			
			IsDisplayed("Resource_name_title_xpath");
		
	
		test.log(LogStatus.PASS, "Dependant tests are passes so Access_Resource_Resourcelist_Sales is successfull");
		
	//	driver.findElement(By.id("contactid_lookupValue")).click();
		
	

	//	driver.findElement(By.id("contactid_lookupValue")).isEnabled();
	//	driver.findElement(By.id("contactid_lookupValue")).isDisplayed();
	//	IsDisplayed("Resource_Link_xpath");
		
	/*	
		String Name=driver.findElement(By.id("///html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[5]/td[3]/nobr[1]")).getText();
		System.out.println(Name);
		wait(3);
		driver.findElement(By.id("gridBodyTable_primaryField_{C3DDD977-0CC3-E711-A952-000D3A1A9FA9}_3")).click();
		wait(1);
		driver.switchTo().defaultContent();


		String General = driver.findElement(By.linkText(Name)).getText();
		System.out.println(General);
		//	assertEquals(General, Name);
		//	assertTrue(General.equals(Name));   */
		
		
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


