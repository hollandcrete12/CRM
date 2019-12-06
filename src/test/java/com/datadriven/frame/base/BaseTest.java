package com.datadriven.frame.base;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.datadriven.frame.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class BaseTest {
	
	public WebDriver driver;
	public Properties prop;
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	public Properties envProp;
	boolean gridRun=false;
	 
	
	public void init() throws IOException{
		
		System.out.println("Intialize class called");
		if(prop==null){
		prop= new Properties();
		envProp=new Properties();
		try {
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//projectconfig.properties");
		prop.load(fs);
		String env=prop.getProperty("env");
		fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+env+".properties");
		envProp.load(fs);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}}

	/*		if(CONFIG.getProperty("browser").equals("Firefox")){
		//	System.setProperty("Webdriver.geckodriver","C:\\geckodriver.exe");
			dr = new FirefoxDriver();
			 dr.manage().window().maximize();
			 System.out.println("launching tests in Firefox");
		}
		
		*/
	
	public void openBrowser1(String bType){
	test.log(LogStatus.INFO, "Opening browser "+bType );
		if(bType.equals("Mozilla"))	{
			System.setProperty("Webdriver.gecko.driver","C:\\geckodriver.exe");
			driver=new FirefoxDriver();
			
		}
		else if(bType.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+"//chromedriver.exe"));
			driver=new ChromeDriver();
		}
		else if (bType.equals("IE")){
			System.setProperty("webdriver.chrome.driver", prop.getProperty("iedriver_exe"));
			driver= new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	public void openBrowser(String bType){
		test.log(LogStatus.INFO, "Opening browser "+bType );
		if(!gridRun){
			if(bType.equals("Mozilla")) {
				 System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");

				 DesiredCapabilities capabilities = new DesiredCapabilities();

				 capabilities = DesiredCapabilities.firefox();
				 capabilities.setBrowserName("firefox");
				 capabilities.setVersion("35");
				 capabilities.setPlatform(Platform.WINDOWS);
				 capabilities.setCapability("marionette", false);

				 driver = new FirefoxDriver(capabilities);

				// driver.get("https://maps.mapmyindia.com");
				
			}
			else if(bType.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+"//chromedriver.exe"));
				driver=new ChromeDriver();
				//test.log(LogStatus.INFO, bType+" Browser opened successfully ");
			}
			else if (bType.equals("IE")){
				System.setProperty("webdriver.chrome.driver", prop.getProperty("iedriver_exe"));
				driver= new InternetExplorerDriver();
			}
		}else{// grid run
			
			DesiredCapabilities cap=null;
			if(bType.equals("Mozilla")){
				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setJavascriptEnabled(true);
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
			}else if(bType.equals("Chrome")){
				 cap = DesiredCapabilities.chrome();
				 cap.setBrowserName("chrome");
				 cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			}
			
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		test.log(LogStatus.INFO, "Browser opened successfully "+ bType);

		
	}

	
	public void navigate(String urlKey){
		System.out.println(envProp.getProperty(urlKey));	
		driver.get(envProp.getProperty(urlKey));
		test.log(LogStatus.INFO, "Application "+envProp.getProperty(urlKey)+" is launched successfully" );
	}
	
	public void click(String locatorKey) {
		wait(1);
		if (getElement(locatorKey).isDisplayed()) {
			test.log(LogStatus.INFO, locatorKey +" is displayed");
			getElement(locatorKey).click();
			test.log(LogStatus.INFO, "Clicked successfully on "+locatorKey);
			}
			else {
				test.log(LogStatus.INFO, locatorKey +"is not correct");
				reportFailure("Please make sure "+locatorKey +" is Clickable or check property of the element" );;
			}
		
	}
	
	public void doubleClick(String locatorKey) {
		Actions action = new Actions(driver);
		action.moveToElement(getElement(locatorKey)).doubleClick().build().perform();
		test.log(LogStatus.INFO, "Doubleclicked successfully on "+locatorKey);
	}
	
	public void ScrollToElement(String locatorKey) {
		try {
			wait(2);
			Actions actions = new Actions(driver);
			actions.moveToElement(getElement(locatorKey));
			actions.perform();
			test.log(LogStatus.INFO, "Scroll to Element successfully on "+locatorKey);
			}
			catch(Exception ex){
			// fail the test and report the error
			reportFailure(ex.getMessage());
			ex.printStackTrace();
			Assert.fail("Failed the test - "+ex.getMessage());
			
		}
		}
	
	public WebElement getText_byxpath(String locatorKey) {
		try{
			return driver.findElement(By.xpath(prop.getProperty(locatorKey)));
		}catch(Throwable t){
			// report error
		return null;
		}
		
	}
	
	public WebElement getText_byID(String locatorKey) {
		try{
			return driver.findElement(By.id(prop.getProperty(locatorKey)));
		}catch(Throwable t){
			// report error
		return null;
		}
		
	}
	
	public void IsDisplayed(String locatorKey) {
		wait(3);
		if (getElement(locatorKey).isDisplayed()) {
			test.log(LogStatus.INFO, locatorKey +" is displayed");; 
			}
			else {
				reportFailure("Please make sure"+locatorKey +" is visible" );;
			}
		
	}
		
	public void EnterValue(String locatorKey, String data) {
		getElement(locatorKey).sendKeys(data);
	}
	
	public void type(String locatorKey,String data){
		test.log(LogStatus.INFO, "Tying in "+locatorKey+". Data - "+data);
		getElement(locatorKey).sendKeys(data);
		test.log(LogStatus.INFO, "Typed successfully in "+locatorKey);

	}
	public  WebElement getElement(String locatorKey) {
		WebElement e=null; 
		try {
			if(locatorKey.endsWith("_id"))
				e= driver.findElement(By.id(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_name"))
				e=driver.findElement(By.name(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_xpath"))
				e=driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_css"))
				e=driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_linktext"))
				e=driver.findElement(By.linkText(prop.getProperty(locatorKey)));	
			else {
				
				Assert.fail("Locator is not correct-" + locatorKey);
				
			}
		}catch(Exception ex){
			// fail the test and report the error
			
			reportFailure(ex.getMessage());
			ex.printStackTrace();
			Assert.fail("Failed the test - "+ex.getMessage());
			
		}
		return e;
	}	

	
/***********************Validations***************************/
public boolean verifyTitle(){
	return false;		
}
public boolean isElementPresent(String locatorKey){
	List<WebElement> elementList=null;
	if(locatorKey.endsWith("_id"))
		elementList = driver.findElements(By.id(prop.getProperty(locatorKey)));
	else if(locatorKey.endsWith("_name"))
		elementList = driver.findElements(By.name(prop.getProperty(locatorKey)));
	else if(locatorKey.endsWith("_xpath"))
		elementList = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
	else if(locatorKey.endsWith("_linktext"))
		elementList = driver.findElements(By.linkText(prop.getProperty(locatorKey)));
	else if(locatorKey.endsWith("_css"))
		elementList = driver.findElements(By.cssSelector(prop.getProperty(locatorKey)));
	else{
		reportFailure("Locator not correct - " + locatorKey);
		Assert.fail("Locator not correct - " + locatorKey);
	}
	
	if(elementList.size()==0)
		return false;	
	else
		return true;
}


public boolean verifyText(String locatorKey, String expectedTextKey) {
	String actualText=getElement(locatorKey).getText().trim();
	String expectedText=prop.getProperty(expectedTextKey);
	if(actualText.equals(expectedText))
		return true; 
	else 
		return false; 
}

public void clickAndWait(String locator_clicked,String locator_pres){
	test.log(LogStatus.INFO, "Clicking and waiting on - "+locator_clicked);
	int count=5;
	for(int i=0;i<count;i++){
		getElement(locator_clicked).click();
		wait(2);
		if(isElementPresent(locator_pres))
			break;
	}
}

/*****************************Reporting********************************/
public void reportPass(String msg){
	test.log(LogStatus.PASS, msg);
}

public void reportFailure(String msg){
	test.log(LogStatus.FAIL, msg);
	takeScreenShot();
	Assert.fail(msg);
}


public void takeScreenShot(){
	// fileName of the screenshot
	Date d=new Date();
	String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
	// store screenshot in that file
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	try {
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//put screenshot file in reports
	test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
	
}
public void DefaultLanding(){
	driver.switchTo().defaultContent();
}

public void FrameIndex(int Number){
	driver.switchTo().frame(Number);
}

public void TotalAvailFrames() {
	int total = driver.findElements(By.tagName("iframe")).size();
	System.out.println("Total frames - "+ total);
	test.log(LogStatus.INFO, "Total"+total+"Frames available on this page");
}
public  void SmartFrames(String bType) {
		if(bType.equals("Mozilla"))	{
			wait(2);
			System.out.println("SmartFrames Identifed for Mozilla");
			test.log(LogStatus.INFO, "SmartFrames Identifed for Mozilla");
			driver.switchTo().frame(2);
		
		}
	else if(bType.equals("Chrome")){
		wait(2);
		test.log(LogStatus.INFO, "SmartFrames Identifed for Chrome");
		driver.switchTo().frame(1);
		System.out.println("SmartFrames Identifed for Chrome");
	}
	}

public void wait(int timeToWaitInSec){
	try {
		Thread.sleep(timeToWaitInSec * 1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void acceptAlert(){
	WebDriverWait wait = new WebDriverWait(driver,5);
	wait.until(ExpectedConditions.alertIsPresent());
	test.log(LogStatus.INFO,"Accepting alert");
	driver.switchTo().alert().accept();
	driver.switchTo().defaultContent();
}

public void waitForPageToLoad() throws InterruptedException {
	wait(1);
	JavascriptExecutor js=(JavascriptExecutor)driver;
	String state = (String)js.executeScript("return document.readyState");
	
	while(!state.equals("complete")){
		wait(2);
		state = (String)js.executeScript("return document.readyState");
	}
}



/************************App functions****/

public boolean doLogin(String username,String password)  {
	test.log(LogStatus.INFO, "Trying to login with "+ username+","+password);
//	click("loginLink_xpath");
	wait(2);
	type("email_xpath",username);
	wait(2);
	click("username_next_xpath");
	wait(2);
	type("password_signin_xpath",password);
	wait(2);
	
	click("password_next_xpath");
	click("No_User_Pass_Save_xpath");

	if(isElementPresent("Sales_Dropdown_xpath")){
		test.log(LogStatus.INFO, "Login Success");
		return true;
	}
	else{
		test.log(LogStatus.INFO, "Login Failed");
		return false;
	}
	
}

public void signOut() {
	wait(3);
	click("Profile_New_xpath");
	wait(2);
	click("Signout_new_xpath");
	System.out.println("User have been signout successfully"); 
	driver.close();
	
}

}