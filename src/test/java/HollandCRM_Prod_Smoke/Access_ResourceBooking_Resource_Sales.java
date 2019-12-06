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



public class Access_ResourceBooking_Resource_Sales extends BaseTest {
	String testCaseName="Access_ResourceBooking_Resource_Sales";
	SoftAssert softAssert; 
	Xls_Reader xls;
	
	
	@Test(priority=1,dataProvider="getData")
	public void Access_ResourceBooking_Resource_Sales1(Hashtable<String,String> data1) throws IOException {
		softAssert = new SoftAssert();
		test= rep.startTest("Access_ResourceBooking_Resource_Sales");
		test.log(LogStatus.INFO, "Starting the Access_Resource_Resourcelist_Sales");
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
	@Test(priority=2)
	public void Validate_AccessToListofResourceBooking(){
		softAssert = new SoftAssert();
		test= rep.startTest("ValidateAccessToResource Bookig ");
		test.log(LogStatus.INFO, "Starting the ValidateAccessToResource Booking ");


		wait(2);
		
		ScrollToElement("Resource_Booking_xpath");
		
		if(!isElementPresent("Resource_Booking_xpath"))
			reportFailure("Resource Booking is not present by please verify Xpath");
		click("Resource_Booking_xpath");
		
		test.log(LogStatus.PASS, "Test Validate_Access To List of Resource Booking is successfull");
	
	}		
	@Test(priority=3,dataProvider="getData")
	public void Validate_NavToSelected_Booking(Hashtable<String,String> data1){
		softAssert = new SoftAssert();
		test= rep.startTest("Validate_NavToSelected_Booking");
		test.log(LogStatus.INFO, "Starting the Validate_NavToSelected_Booking");
		
	    
		wait(2);
		
		if(!isElementPresent("Resource_Booking_name_xpath"))
			reportFailure("Resource_Booking_name_xpath is not present by please verify Xpath");
		click("Resource_Booking_name_xpath");
	    
		
	
	
		
		test.log(LogStatus.PASS, "Test clicking on one of the Resource Booking is successfull");
		
	}
	@Test(priority=4)
	public void Access_ResourceBooking_Resource_Sales(){
		softAssert = new SoftAssert();
		test= rep.startTest("Access_ResourceBooking_Resource_Sales");
		test.log(LogStatus.INFO, "Starting the Access_ResourceBooking_Resource_Sales");
		
		wait(2);
		
		if(!isElementPresent("Resource_Booking_name_property_xpath"))
			reportFailure("Resource_Booking_name_property_xpath is not present by please verify Xpath");
		click("Resource_Booking_name_property_xpath");
	    
		
	
		test.log(LogStatus.PASS, "Able to Navigate to the Landing page of Resource Booking is successfull");
		test.log(LogStatus.PASS, "Dependant tests are successful so Access_ResourceBooking_Resource_Sales is successfull");
		
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


