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



public class Access_ResourceSkill_Resourcelist_Sales extends BaseTest {
	String testCaseName="Access_ResourceSkill_Resourcelist_Sales";
	SoftAssert softAssert; 
	Xls_Reader xls;
	
	
	@Test(priority=1,dataProvider="getData")
	public void Access_ResourceSkill_Sales(Hashtable<String,String> data1) throws IOException {
		softAssert = new SoftAssert();
		test= rep.startTest("Access_ResourceSkill_Resourcelist_Sales");
		test.log(LogStatus.INFO, "Starting the Access_ResourceSkill_Resourcelist_Sales");
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
	@Test(priority=2,dataProvider="getData",dependsOnMethods={"Access_ResourceSkill_Sales"})
	public void Validate_NavToSelectedResourceskill(Hashtable<String,String> data1){
		softAssert = new SoftAssert();
		test= rep.startTest("Validate_NavToSelectedResourceskill");
		test.log(LogStatus.INFO, "Starting the Validate_NavToSelectedResourceskill");

		ScrollToElement("ResourceSkill_xpath");
		
		if(!isElementPresent("ResourceSkill_xpath"))
			reportFailure("Resource Skill  is not present by please verify Xpath");
		
	
		wait(2);
		click("ResourceSkill_xpath");
		wait(1);
		

		test.log(LogStatus.PASS, "Test Validate_NavToSelectedResourceskill is Successfull");
	}		
	
	@Test(priority=3,dependsOnMethods={"Validate_NavToSelectedResourceskill"})
	public void Access_ResourceSkill_Resourcelist_Sales(){
		softAssert = new SoftAssert();
		test= rep.startTest("Access_ResourceSkill_Resourcelist_Sales");
		test.log(LogStatus.INFO, "Starting the Access_ResourceSkill_Resourcelist_Sales");
	
		
		if(!isElementPresent("Resourceskillselect_linktext"))
			reportFailure("Resource Skill  is not present by please verify Xpath");
		
		wait(2);
		click("Resourceskillselect_linktext");
		
		
		test.log(LogStatus.PASS, " Access_ResourceSkill_Resourcelist_Sales is successfull");
		
	}
	
	@Test(priority=4,dependsOnMethods={"Validate_NavToSelectedResourceskill"})
	public void Access_ResourceSkill_PropertyPage_Sales(){
		softAssert = new SoftAssert();
		test= rep.startTest("Access_ResourceSkill_PropertyPage_Sales");
		test.log(LogStatus.INFO, "Starting the Access_ResourceSkill_Resourcelist_Sales");
	
		
		if(!isElementPresent("Resourceskillanding_Desription_xpath"))
			reportFailure("Resource Skill Description is not present by please verify Xpath");
		
		wait(2);
		click("Resourceskillanding_Desription_xpath");
		
		
		test.log(LogStatus.PASS, " Access_ResourceSkill_PropertyPage_Sales is successfull");
		
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


