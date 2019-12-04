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



public class Validate_OppotunitiesCanbeOpened_UnderSales_Opportunities extends BaseTest {
	String testCaseName="Validate_OppotunitiesCanbeOpened_UnderSales_Opportunities";
	SoftAssert softAssert; 
	Xls_Reader xls;
	
	
	@Test(priority=1,dataProvider="getData")
	public void Validate_AccessToListofOpportunities(Hashtable<String,String> data) throws IOException {
		softAssert = new SoftAssert();
		test= rep.startTest("Validate_OppotunitiesCanbeOpened_UnderSales_Opportunities");
		test.log(LogStatus.INFO, "Starting the Validate_OppotunitiesCanbeOpened_UnderSales_Opportunities");
			if(!DataUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")) {
				test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
				throw new SkipException("Skipping the test as runmode is N");
			}
			test.log(LogStatus.INFO, data.toString());

			openBrowser(data.get("Browser"));
			
			

			navigate("app1url");
			doLogin(envProp.getProperty("username"), envProp.getProperty("password"));
			wait(2);
			
			click("Next_css");
			wait(2);
			if(!isElementPresent("Holland_xpath"))
				reportFailure("Holland is not present by please verify Xpath");
			
			click("Holland_xpath");
			
			test.log(LogStatus.PASS, "Test Navigate to Holland button is successfull");
			
	}	
			
	@Test(priority=2,dataProvider="getData")
	public void Validate_OpportunitySelected_Name_Displayed(Hashtable<String,String> data)throws IOException{
		softAssert = new SoftAssert();
		test= rep.startTest("GeneralInformation_Displayed_ContactListUnderSales");
		test.log(LogStatus.INFO, "Starting the GeneralInformation_Displayed_ContactListUnderSales");
		
	
		
		ScrollToElement("Opportunities_xpath");
		
		if(!isElementPresent("Opportunities_xpath"))
			reportFailure("Opportunities  is not present by please verify Xpath");
		click("Opportunities_xpath");
		
		
		
		
			//if(!isElementPresent("Opport_Select_css"))
			//reportFailure("Opportunities  Down/Selection is not present by please verify the property");
			click("Opport_Select_xpath");
			
			if(!isElementPresent("Oppor_All_xpath"))
				reportFailure("All Opportunities  is not present in dropdown please verify Xpath");
				click("Oppor_All_xpath");
				
			
			
			String Oppotunity = driver.findElement(By.xpath("//a[contains(text(),'LIRR 3 year')]")).getText();
			System.out.println(Oppotunity);
			assertTrue(Oppotunity.equals("LIRR 3 year"));
			
			click("Oppor_LIRR_xpath");
			
			test.log(LogStatus.PASS, "Test Validate_OpportunitySelected_Name_Displayed Passed");
			
			
	}
	

	
	@Test(priority=3) 
	public void Validate_AccountName_Displayed_AccountListUnderSales4() {
		
		WebElement AccountName1=driver.findElement(By.xpath("//*[@id=\"id-a837e4a7-01b8-4f82-a475-be9abd67e667-1-name8-name.fieldControl-text-box-text\"]"));
	//	String AccountName = driver.findElement(By.xpath("//*[@id='name']/div[1]")).getText();
		
	//	assertTrue(AccountName.equals("Adkins Energy"));
		Boolean checkAccountInfoIsDisplayed2 = AccountName1.getText().matches("LIRR 3 year");
	//	System.out.println(AccountName);
		
		test.log(LogStatus.PASS, "Test Validate_AccountName_Displayed_AccountListUnderSales4 Passed");
	}

	@BeforeMethod
	public void init() {
		softAssert = new SoftAssert();
		
		
	}

	@AfterTest
	public void quit(){DefaultLanding();
	wait(3);
	click("Profile_New_xpath");
	wait(2);
	click("Signout_new_xpath");
	System.out.println("User have been signout successfully"); 
	driver.close();
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
	super.init();
	xls = new Xls_Reader(System.getProperty("user.dir")+"\\config\\testcases\\SmokeTestData.xlsx");
	Object[][] data= DataUtil.getTestData(xls, "Validate_OppotunitiesCanbeOpened_UnderSales_Opportunities");
	
	return data;
} 
}


