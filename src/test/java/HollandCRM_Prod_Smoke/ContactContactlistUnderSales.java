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



public class ContactContactlistUnderSales extends BaseTest {
	String testCaseName="ContactContactlistUnderSales";
	SoftAssert softAssert; 
	Xls_Reader xls;
	
	
	@Test(priority=1,dataProvider="getData")
	public void AccessToContactListUnderSales(Hashtable<String,String> data1) throws IOException {
		softAssert = new SoftAssert();
		test= rep.startTest("AccessToContactListUnderSales");
		test.log(LogStatus.INFO, "Starting the AccessToContactListUnderSales");
			if(!DataUtil.isRunnable(testCaseName, xls) || data1.get("Runmode").equals("N")) {
				test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
				throw new SkipException("Skipping the test as runmode is N");
			}
			test.log(LogStatus.INFO, data1.toString());

			openBrowser(data1.get("Browser"));
			
			

			navigate("app1url");
			doLogin(envProp.getProperty("username"), envProp.getProperty("password"));

		/*	wait(2);
			click("Next_css");
			
		//	softAssert.assertTrue(verifyText("Project_Ser_xpath","Project_Service_xpath"), "Text did not match");
			
			if(!isElementPresent("Holland_xpath"))
				reportFailure("Holland is not present by please verify Xpath");
			
			click("Holland_xpath");  */
			
			test.log(LogStatus.PASS, "Test Navigate to Holland button is successfull");
		
	}
	@Test(priority=2,dataProvider="getData",dependsOnMethods={"AccessToContactListUnderSales"})
	public void GeneralInformation_Displayed_ContactListUnderSales(Hashtable<String,String> data1){
		softAssert = new SoftAssert();
		test= rep.startTest("GeneralInformation_Displayed_ContactListUnderSales");
		test.log(LogStatus.INFO, "Starting the GeneralInformation_Displayed_ContactListUnderSales");
		DefaultLanding();

		
		
		
		ScrollToElement("Contact_xpath");
		
		if(!isElementPresent("Contact_xpath"))
			reportFailure("Contact is not present by please verify the locator property");
		
	
		wait(2);
		click("Contact_xpath");
		wait(1);
		

		test.log(LogStatus.PASS, "Test Navigate to Contact is Successfull");
		
	}	
	
	@Test(priority=3,dependsOnMethods={"GeneralInformation_Displayed_ContactListUnderSales"})
	public void FirstName_LastName_Displayed_ContactContactListGeneralInformation(){
		softAssert = new SoftAssert();
		test= rep.startTest("FirstName_LastName_Displayed_ContactContactListGeneralInformation");
		test.log(LogStatus.INFO, "Starting the FirstName_LastName_Displayed_ContactContactListGeneralInformation");
		
		
		if(!isElementPresent("Contact_select_linktext"))
			reportFailure("Unable to select a contact please verify the locator ");
		
		wait(2);
		click("Contact_select_linktext");
		
		
		test.log(LogStatus.PASS, " Naivigattion to general information page of selected contact is successfull");
		
		
		
	}
	
	@Test(priority=4,dependsOnMethods={"FirstName_LastName_Displayed_ContactContactListGeneralInformation"})
	public void ContactContactlistUnderSales(){
		softAssert = new SoftAssert();
		test= rep.startTest("ContactContactlistUnderSales");
		test.log(LogStatus.INFO, "Starting the ContactContactlistUnderSales");
		
		WebElement Name=getElement("Contact_h1_xpath");
		Boolean checkLastNameDisplayed = Name.getText().matches("Aaron C Dodrill");
		System.out.println(Name);
	
		
		WebElement GeneralInformation = getElement("ContactGeneralInformation_xpath");
		Boolean checGeneralInfoIsDisplayed = GeneralInformation.isDisplayed();
		if (checGeneralInfoIsDisplayed == true) {
		System.out.println("General Information is displayed");
		} else {
			reportFailure("General Informations is not displayed" );
		}
		
		
		test.log(LogStatus.PASS, "Dependant tests are successful so ContactContactlistUnderSales is successfull");
		
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


