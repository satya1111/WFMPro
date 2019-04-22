package functionalLib;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.maven.surefire.shade.org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import Reporting.Report;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import uiWFMObjInfo.UIWFMLocator;

public class WFMBusinessActions {

	WebDriver oBrowser;
	String sTDFileName;
	String Navtextfiles;
	UIWFMLocator oUIObj = new UIWFMLocator();
	public String oParentBrw;
	String Screenshotpath;
	File scrFile;
	String appURL;
	String browser;
	String devicename;
	String platformname;
	String devc;
	String platformversion;
	Report selectReport;


	public WFMBusinessActions(WebDriver driver,String strURL,String devicename,String platformname,String devc,String platformversion,String browser,Report report)
	{ 
		sTDFileName = report.gstrTestDatapath;
		Navtextfiles=report.Navigationtextfiles;
		Screenshotpath=report.gstrScreenshotPath;
		this.oBrowser = driver;
		this.appURL=strURL;
		this.devicename=devicename;
		this.platformname=platformname;
		this.devc=devc;
		this.platformversion=platformversion;
		this.browser=browser;
		selectReport = report;

	}

	/*
	 * TC_openApplication
	 * Created by Deven 
	 * Date Created: 11/27/18
	 * Usage: Opening CornerStron application Stage environment from Excel sheet 
	 * @Information will read form Excel sheet 
	 *  */	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void openApplication() throws InterruptedException, BiffException, WriteException, IOException
	{
		//System.out.println( "---" + appURL  +"---");
		if(browser.contains("Android"))	
		{
			System.out.println("Inside if android");
			System.out.println(devc);
			System.out.println(platformversion);
			System.out.println(devicename);
			System.out.println(platformname);

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("browserName","Browser");
			cap.setCapability("deviceName",devicename);
			cap.setCapability("launchTimeout", "100000");
			cap.setCapability("VERSION",platformversion);
			cap.setCapability("platformName",platformname);

			oBrowser=new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),cap);


		}
		else if(browser.contains("iOS"))	
		{
			System.out.println("Inside if iOS");


			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("deviceName","iPhone 7");
			cap.setCapability("platformName","iOS");
			cap.setCapability("platformVersion","11.4");
			cap.setCapability("udid","6A949189-A4EA-4DFA-88CB-D6534048C426");
			cap.setCapability(CapabilityType.BROWSER_NAME,"safari");
			cap.setCapability("automationName","XCUITest");

			cap.setCapability("launchTimeout", "700000");


			//cap.setCapability("automationName","XCUITest");
			//IOSDriver oBrowser;
			System.out.println("***");
			oBrowser=new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),cap);
			System.out.println("***");
			//driver.get(appURL);
			//Thread.sleep(10000);
			System.out.println("***");

		}
		else if(browser.equalsIgnoreCase("Firefox"))
		{
			System.out.println("Inside firefox");
			selectReport.iSleep=500;
			System.out.println(System.getProperty("user.dir") + "\\Resources\\geckodriver.exe");
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "\\Resources\\geckodriver.exe");
			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities = DesiredCapabilities.firefox();
			capabilities.setBrowserName("firefox");
			//capabilities.setVersion("45.0.2");
			capabilities.setPlatform(Platform.WINDOWS);
			capabilities.setCapability("marionette", false);
			try
			{
				oBrowser= new FirefoxDriver(capabilities);
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			System.out.println("**");
			oBrowser.manage().window().maximize();
		}
		else if(browser.contains("Chrome"))
		{
			System.out.println("Inside Chrome");
			selectReport.iSleep=500;
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "/Resources/chromedriver.exe" );
			oBrowser= new ChromeDriver( );
			oBrowser.manage().window().maximize();
		}
		else if(browser.contains("IE"))
		{
			selectReport.iSleep=2000;
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") + "/Resources/IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();

			// this line of code is to resolve protected mode issue capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("unexpectedAlertBehaviour", "accept");
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("enablePersistentHover", false);
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability("nativeEvents", false);
			oBrowser= new InternetExplorerDriver(capabilities);
			oBrowser.manage().window().maximize();
		}
		else if(browser.equalsIgnoreCase("Safari"))
		{
			System.out.println("In Safari");
			selectReport.iSleep=2000;
			SafariOptions safariOpts = new SafariOptions();
			DesiredCapabilities cap = DesiredCapabilities.safari();

			//safariOpts.setUseCleanSession(true);
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			//cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "accept");
			cap.setCapability(CapabilityType.SUPPORTS_ALERTS,true);
			cap.setCapability(SafariOptions.CAPABILITY, safariOpts);
			cap.setBrowserName("safari");
			cap.setPlatform(Platform.MAC);

			oBrowser = new SafariDriver();


		}

		selectReport.gbDriver=oBrowser;
		System.out.println("before get url");

		oBrowser.get(appURL);
		waitforpageload();
		if(browser.contains("iOS"))
			Thread.sleep(10000);
		selectReport.ReportStep("Pass","Open Application","Application should open","Opened "+ selectReport.strURL +" application successfully");

		if(browser.contains("IE"))
		{
			System.out.println("inside IE");
			try
			{
				oBrowser.findElement(By.xpath("//a[contains(@id,'overridelink')]")).click();
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			}
			catch(Exception ex)
			{
				System.out.println("");
			}
		}

		try{
			if(oBrowser.findElement(By.id("continueToSiteAlign")).isDisplayed())
			{
				oBrowser.findElement(By.id("continueToSiteAlign")).click();
				Thread.sleep(300);
			}
		}catch(Exception e){}

		new File(Navtextfiles).mkdir();



	}


	public String[] Filtercommon(String val)
	{ 
		String[] tim=new String[3];
		String[] s1=val.split(" ");
		tim[0]=s1[0];
		tim[1]=s1[3];
		tim[2] = s1[5];
		return tim;
	}
	/*
	 * waitforpageload
	 * Created by Sujatha 
	 * Date Created: 06/12/18
	 * Usage: Wait up to load complete page
	 * @Information will save into database 
	 *  */		
	public void waitforpageload()
	{
		JavascriptExecutor js = (JavascriptExecutor)oBrowser;
		for (int i=0; i<5000; i++){ 
			try {
				Thread.sleep(500);
			}catch (InterruptedException e) {} 
			//To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")){ 
				break; 
			}
		}
	}

	/*
	 * TC_CloseBrowser
	 * Created by Deven 
	 * Date Created: 11/27/18
	 * Usage: All Test Case completed Close the Browsers
	 * @Information Will close all the Browsers open by Selenium 
	 *  */	

	public void closeApplication() throws InterruptedException
	{
		oBrowser.quit();
		if(selectReport.gbCurrentTestStatus.equalsIgnoreCase("pass")&& selectReport.gbCurrentVerifysteptStatus.equalsIgnoreCase("pass")){
			selectReport.noTestsPassed++;}						
		else {
			selectReport.noTestsFailed++;}
		try {
			selectReport.CloseTestReport();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cSideframe()
	{
		oBrowser.switchTo().defaultContent(); 
		oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		oBrowser.switchTo().frame(oBrowser.findElement(By.name(oUIObj.uicFrame_name)));
		oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	/*
	 * TC_captureScreenshotall
	 * Created by LH_Sujatha 
	 * Date Created: 01/12/2017
	 * Usage: Capturing Screenshots of the page
	 * @Information For the validation purpose
	 *  */	

	public void captureScreenshotall(String img) throws IOException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("dd_MMM_yyyy_HHmmss");
		TakesScreenshot oScn = (TakesScreenshot) oBrowser; //Type casting

		//screenshot info will be saved in oScnshot variable (of type file)
		File oScnshot = oScn.getScreenshotAs(OutputType.FILE);

		//Creating a empty image file
		File oDest = new File(Screenshotpath,img+ "_"+  sdf.format(new Date())+".png"); 
		//Save in a Physical location
		org.apache.maven.surefire.shade.org.codehaus.plexus.util.FileUtils.copyFile(oScnshot, oDest);

	}
	/* TC_setText 
	 * Created by Deven 
	 * Date Created: 11/27/18
	 * Usage: Generaic function for Entering value in textbox  
	 * @Information: Reading the value from the Excel Sheet  
	 **/
	public void setText(String strLocator, String strvalue)
	{
		try {
			getElement(strLocator).clear();
			//LH_highlightElement(getElement(strLocator));   
			getElement(strLocator).click();
			getElement(strLocator).sendKeys(strvalue);
			selectReport.ReportStep("Pass","Input Text","Set Text","Value Entered as " + strvalue);
		}
		catch(Exception e){System.out.println("Failed to find element "); //+ strLocator);
		//sreenshotSoftLine(Global.gstrResultPath + "\\setText" + strvalue);
		}
	}

	/* controlClick 
	 * Created by Deven 
	 * Date Created: 9/22/16
	 * Usage: Generic function for clicking on the control 
	 * @Information:  
	 **/
	public void controlClick(String strLocator,String strObjName)
	{
		try {
			//LH_highlightElement(getElement(strLocator));
			getElement(strLocator).click();
			Thread.sleep(500);
			System.out.println("Clicked on element " + strObjName);
			selectReport.ReportStep("Pass","Click Control","Click Control","Clicked on control " + strObjName);
		}
		catch(Exception e){System.err.println("Failed to find element " + e.getMessage());
		}
	}

	/* getElement 
	 * Created by Deven 
	 * Date Created: 11/27/18
	 * Usage: Generaic function for finding element bases on xpath sent 
	 * @Information:  
	 **/
	private  WebElement getElement(String strLocator)
	{
		WebElement element = null;
		if (strLocator.startsWith("//")) {
			element = oBrowser.findElement(By.xpath(strLocator));
		}
		if (strLocator.startsWith("id")) {
			strLocator = strLocator.substring(strLocator.indexOf('=') + 1,
					strLocator.length());
			element = oBrowser.findElement(By.id(strLocator));
		}
		if (strLocator.toLowerCase().startsWith("name")) {
			strLocator = strLocator.substring(strLocator.indexOf('=') + 1,
					strLocator.length());
			element = oBrowser.findElement(By.name(strLocator));
		}

		if (strLocator.toLowerCase().startsWith("css")) {
			strLocator = strLocator.substring(strLocator.indexOf('=') + 1,
					strLocator.length());
			element = oBrowser.findElement(By.cssSelector(strLocator));
		}

		if (strLocator.toLowerCase().startsWith("link")) {
			strLocator = strLocator.substring(strLocator.indexOf('=') + 1,
					strLocator.length());
			element = oBrowser.findElement(By.linkText(strLocator));
		}

		return element;

	}	

	/*
	 * TC_mousehover
	 * Created by Deven 
	 * Date Created: 11/28/2018
	 * Usage:To Mousehover on an element      
	 * @Information Using javascript for mousehover  
	 *  */	

	public void mousehover(String value) throws InterruptedException
	{
		String strJavaScript = "var element = arguments[0];"
				+ "var mouseEventObj = document.createEvent('MouseEvents');"
				+ "mouseEventObj.initEvent( 'mouseover', true, true );"
				+ "element.dispatchEvent(mouseEventObj);";
		WebElement element= oBrowser.findElement(By.xpath(value));

		((JavascriptExecutor) oBrowser).executeScript(strJavaScript, element);
		if(browser.contains("Safari"))
			((JavascriptExecutor) oBrowser).executeScript(strJavaScript, element);
		/*{
			System.out.println("Safari-Mouseover");
			Actions builder = new Actions(oBrowser);
			builder.moveToElement(oBrowser.findElement(By.xpath(value))).build().perform();
			oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}*/
	}


	/*
	 * TC_getFirstletterofStringCaps
	 * Created by Deven  
	 * Date Created: 11/28/2018
	 * Usage: Change the first letter of a string into capitals
	 * @Information For the validation purpose
	 *  */
	public String getFirstletterofStringCaps(String value)
	{
		String str=value;
		char[] chars = str.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++) {
			//char charInt=Character.toChars(i);
			int ascii=(int) chars[i];
			if ((!(found)) && (!(ascii>=48 && ascii<=57)) && (ascii!=32)) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i]=='.'|| chars[i]=='/'|| chars[i]==',' || ascii==32) { // You can add other chars here
				found = false;
			}
		}
		String string1=String.valueOf(chars);
		if(value.contains("tv")||value.contains("TV")||value.contains("Tv"))
		{
			string1="TV";
		}
		else if(string1.contains("'"))
		{
			String[] s1=string1.split("'");
			string1=s1[0]; 
		}

		System.out.println("after split"+string1.trim());
		return string1;
	}



	/*
	 * TC_getlowercaseofString
	 * Created by LH_Sujatha 
	 * Date Created: 03/02/2017
	 * Usage: Change the string into lowercase
	 * @Information For the validation purpose
	 *  */
	public String getlowercaseofString(String value)
	{
		String str=value;
		String string1=str.toLowerCase();
		if(string1.contains("'"))
		{
			String[] s1=string1.split("'");
			string1=s1[0];		
		}
		return string1;
	}

	/*
	 * TC_getFirstletterofStringCaps
	 * Created by LH_Sujatha 
	 * Date Created: 03/02/2017
	 * Usage: Change the string into Uppercase
	 * @Information For the validation purpose
	 *  */
	public String getuppercaseofString(String value)
	{
		String str=value;
		String string1=str.toUpperCase();
		String[] s1=string1.split("'");
		string1=s1[0];
		return string1;
	}

	/*
	 * Scroll Down function -- scroll to object in focus
	 * Created by Vishal Patel 
	 * Date Created: 10/03/2017
	 * Usage:Scroll to object in focus     
	 *  */
	public void scrollDown(String Path){
		WebElement objEdit = oBrowser.findElement(By.xpath(Path));
		((JavascriptExecutor) oBrowser).executeScript("arguments[0].scrollIntoView(true);", objEdit);
	}


	/*
	 * TC_childbrowser
	 * Created by Deven 
	 * Drtate Created: 11/28/2018
	 * Usage: Handling the child browser
	 * @Information:   
	 *  */

	public void childbrowser() throws InterruptedException
	{
		String oParentBrw = oBrowser.getWindowHandle();
		//Get the All Browsers Info
		Set<String> oAllBrws = oBrowser.getWindowHandles();
		for(String oEachBrw:oAllBrws){
			if (!(oEachBrw.equals(oParentBrw)))
			{

				System.out.println(oBrowser.switchTo().window(oEachBrw).getCurrentUrl());
				oBrowser.close();
			}
		}		
		oBrowser.switchTo().window(oParentBrw);
		Thread.sleep(500);
	}

	/*
	 * TC_switchtowindow
	 * Created by Deven 
	 * Drtate Created: 11/28/2018
	 * Usage: Switching to new window
	 * @Information:   
	 *  */
	public void switchtowindow()
	{
		String oParentBrw = oBrowser.getWindowHandle();
		//Get the All Browsers Info
		Set<String> oAllBrws = oBrowser.getWindowHandles();
		for(String oEachBrw:oAllBrws){
			if (!(oEachBrw.equals(oParentBrw)))
			{
				oBrowser.switchTo().window(oEachBrw);

			}
		}		
	}
	/*
	 * Highlight Objects
	 * Created by Deven 
	 * Date Created: 11/28/2018
	 * Usage: It will highlight Object before click
	 * */	

	public void highlightElement(WebElement element) throws InterruptedException
	{
		for(int i = 0; i<3; i++)
		{
			JavascriptExecutor js = (JavascriptExecutor)oBrowser;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "color: yellow; border: 3px solid yellow;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "");
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		}
	}



	/* * TC_Login
	 * Created by Deven 
	 * Date Created: 11/27/18
	 * Usage: Enteing user name and password from Excle spread sheet
	 * @Information will save into database 
	 *  */	
	public void LogIn() throws Exception
	{

		ExcelRead oExcelLogin = new ExcelRead(sTDFileName, "Login");

		//check if already signed in 
		try{

			if(oBrowser.findElement(By.xpath(oUIObj.uID_xp)).isDisplayed())
			{
				//Enter User ID
				oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
				setText( oUIObj.uID_xp ,oExcelLogin.getCellData("UserName", 1));
				selectReport.ReportStep("Pass", "Sign In", "Validate Sign In","UserName entered as " + oExcelLogin.getCellData("UserName", 1));

				//Enter Password
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
				setText(oUIObj.password_xp,oExcelLogin.getCellData("Password", 1));
				selectReport.ReportStep("Pass", "Sign In", "Validate Sign In","Password entered as " + oExcelLogin.getCellData("Password", 1));
				//Thread.sleep(500);

				//Clicking on SignIn
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				controlClick(oUIObj.signIn_xp,"SignIn Button");
				selectReport.ReportStep("Pass", "Sign In", "Validate Sign In","Clicked on Signin Button");

			}
		}catch(Exception e){

			System.out.println("Fail: Login page not displayed");
			selectReport.ReportStep("Fail", "Logout", "Validate Logout out"," unable to clicked on Yes Button");
		}

	}

	/* * TC_Logout
	 * Created by Deven 
	 * Date Created: 11/28/18
	 * Usage: Log out user from the application
	 * @Information will log user out  
	 *  */	
	public void LogOut() throws Exception
	{

		//check if User sign out 
		try{

			if(oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).isDisplayed())
			{
				WebElement we=oBrowser.findElement(By.xpath(oUIObj.logOut_xp));
				highlightElement(we);
				//Clicking on Logout
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				controlClick(oUIObj.logOut_xp,"Logout Button");
				controlClick(oUIObj.confirm_xp, "Confrim Are you sure you wamt to log out?");
				selectReport.ReportStep("Pass", "Logout", "Validate Logout","Clicked on Log out Button");

			}
		}catch(Exception e){

			System.out.println("Log out page is not displayed");
		}

	}


	/* * TC_View Schedule
	 * Created by Gandhi 
	 * Date Created: 02/19/19
	 * Usage: Clicking on View Schedule
	 * @Information It will click link on View Schedule form the header menu  
	 *  */	
	public void ViewSched() throws Exception
	{
		//Validate if View Schedule link exist 
		try{

			if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
			{
				WebElement ViewSchedule=oBrowser.findElement(By.xpath(oUIObj.uiViewSched_xp));
				highlightElement(ViewSchedule);
				oBrowser.findElement(By.xpath(oUIObj.uiViewSched_xp)).click();
				//Enter Employee number
				Thread.sleep(1500);
				cSideframe();
			}
		}catch(Exception e){

			System.out.println("Load Button is not displayed");
		}

	}


	/* * TC_Timesheet
	 * Created by Deven 
	 * Date Created: 11/28/18
	 * Usage: Clicking on Time Sheet
	 * @Information It will click link on Timesheet form the header menu  
	 *  */	
	public void timeSheetAK(String employee) throws Exception
	{
		//Validate if Timesheet link exist 
		try{

			if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
			{
				WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
				highlightElement(timesheet);
				oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
				//Enter Employee number
				Thread.sleep(1500);
				cSideframe();
				//oBrowser.findElement(By.xpath(oUIObj.uiAll_xp)).click();
				oBrowser.findElement(By.xpath(oUIObj.uiinputEmployee_xp)).click();
				oBrowser.findElement(By.xpath(oUIObj.uiinputEmployee_xp)).sendKeys(employee);
				oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				controlClick(oUIObj.uiLoad_xp,"Load Button");
				Thread.sleep(1000);
				selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
			}
		}catch(Exception e){

			System.out.println("Load Button is not displayed");
		}

	}

	/*
	 * FilterExpectedTime 
	 * Created by Sujatha 
	 * Date Created: 12/06/18
	 * Usage: Filter expected time
	 * @Information For the validation purpose
	 *  */  
	public String[] FilterUS_Holiday(String val)
	{ 
		String[] tim=new String[3];
		String[] s1=val.split(" ");
		tim[0]=s1[0];
		tim[1]=s1[3];
		return tim;
	}

	/*
	 * FilterExpectedTime 
	 * Created by Sujatha 
	 * Date Created: 12/06/18
	 * Usage: Filter expected time
	 * @Information For the validation purpose
	 *  */  
	public String[] FilterExpectedTime(String val)
	{ 
		String[] tim=new String[3];
		String[] s1=val.split(" ");
		tim[0]=s1[0];
		tim[1]=s1[3];
		return tim;
	}

	/*
	 * FilteronlyRegularorOTTime Created by Gandhi 
	 * Date Created: 12/19/18 Usage:
	 * Filter expected time for ThankYou_MVP
	 * @Information For the validation purpose
	 */
	public String[] FilterThankyouMVP(String val) {
		String[] tim = new String[3];
		String[] s1 = val.split(" ");
		tim[0] = s1[0];
		tim[1] = s1[1];
		return tim;
	}

	/*
	 * FilterExpectedTime 
	 * Created by Sujatha 
	 * Date Created: 01/16/19
	 * Usage: Filter expected time
	 * @Information For the validation purpose
	 *  */  
	public String[] FilterDailyExpectedTime(String val)
	{ 
		String[] tim=new String[5];
		String[] s1=val.split(" ");
		int len=s1.length;
		System.out.println("len"+len);
		tim[0]=s1[0];
		tim[1]=s1[3];
		if(len>7)
		{
			tim[2]=s1[6];	
			tim[3]=s1[8];		  
		}
		else
		{
			tim[2]=s1[5];	
			tim[3]="";
		}
		System.out.println("**"+tim[3]);
		return tim;
	}

	/*
	 * FilteronlyRegularorOTTime 
	 * Created by Sujatha 
	 * Date Created: 12/19/18
	 * Usage: Filter expected time
	 * @Information For the validation purpose
	 *  */  
	public String[] FilteronlyRegularorOTTime(String val)
	{ 
		String[] tim=new String[3];
		String[] s1=val.split(" ");
		tim[0]=s1[0];
		tim[1]=s1[2];
		return tim;
	}

	/*
	 * FilteronlyRegularorOTTime 
	 * Created by Sujatha 
	 * Date Created: 12/19/18
	 * Usage: Filter expected time
	 * @Information For the validation purpose
	 *  */  
	public String[] FilterGuaraRegularorOTTime(String val)
	{ 
		String[] tim=new String[3];
		String[] s1=val.split(" ");
		tim[0]=s1[0];
		tim[1]=s1[3];
		tim[2]=s1[5];
		return tim;
	}
	/*
	 * FilterExpectedTime Created by Gandhi 
	 * Date Created: 02/14/19 
	 * Usage: Filter expected time
	 * @Information For the validation purpose
	 */
	public String[] FilterDailyExpectedTime1(String val) {
		String[] tim = new String[8];
		String[] s1 = val.split(" ");
		int len = s1.length;
		System.out.println("len" + len);
		tim[0] = s1[0];
		tim[1] = s1[3];
		tim[2] = s1[5];
		if (len > 7) {
			tim[3] = s1[6];
			tim[4] = s1[8];
		} else {
			tim[2] = s1[5];
			tim[3] = "";
		}
		System.out.println("**" + tim[3]);
		return tim;
	}


	/*
	 * WeeklyFilterExpectedTime
	 * Created by Sujatha 
	 * Date Created: 12/18/18
	 * Usage: Filter expected time
	 * @Information For the validation purpose
	 *  */  
	public String[] WeeklyFilterExpectedTime(String val)
	{ 
		String[] tim=new String[3];
		String[] s1=val.split(" ");
		tim[0]=s1[0];
		tim[1]=s1[3];
		tim[2]=s1[5];
		return tim;
	} 

	public String[] Filtercommon1(String val)
	{ 
		String[] tim=new String[3];
		String[] s1=val.split(" ");
		tim[0]=s1[0];
		tim[1]=s1[3];
		//tim[2]=s1[4];
		return tim;
	} 

	public String[] Filtercommon2(String val) {
		String[] tim = new String[3];
		String[] s1 = val.split(" ");
		tim[0] = s1[0];
		tim[1] = s1[2];
		return tim;
	}

	/*
	 * Deletetime 
	 * Created by Deven 
	 * Date Created: 12/06/18
	 * Usage: Delete clock time for the week
	 * @Information For the validation purpose
	 *  */  
	public void Deletetime() throws InterruptedException
	{ 
		try
		{
			oBrowser.switchTo().defaultContent();
			cSideframe();
			oBrowser.findElement(By.xpath(oUIObj.uiselecttimeon_xp)).click();
			Thread.sleep(500);
			oBrowser.findElement(By.xpath(oUIObj.uideletebtn_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			oBrowser.findElement(By.xpath(oUIObj.uiselecttimeoff_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			oBrowser.findElement(By.xpath(oUIObj.uideletebtn_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
			Thread.sleep(1500);
			oBrowser.findElement(By.xpath(oUIObj.uiShowEdits_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<WebElement> we1=oBrowser.findElements(By.xpath(oUIObj.uideletechckbox_xp));
			for(int i=0;i<we1.size();i++)
			{
				we1.get(i).click();
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			}
			oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
			Thread.sleep(1500);
			oBrowser.findElement(By.xpath(oUIObj.uiShowEdits_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println("Pass:Deleted time successfully");
			selectReport.ReportStep("Pass", "Validate Delete time", "Time should delete","Added time deleted successfully");
		}
		catch(Exception ex)
		{
			System.out.println("Fail:Not Deleted time.Please verify");
			selectReport.ReportStep("Fail", "Validate Delete time", "Time should delete","Not deleted added time successfully");
		}
	} 

	/*
	 * Deletealledits Created by Sujatha Date Created: 12/06/18 Usage: Delete clock
	 * time for the week
	 * 
	 * @Information For the validation purpose
	 */
	public void Deletealledits() throws InterruptedException {
		try {
			oBrowser.switchTo().defaultContent();
			cSideframe();
			try {
				if (oBrowser.findElement(By.xpath(oUIObj.uiHideedits_xp)).isDisplayed()) {
					oBrowser.findElement(By.xpath(oUIObj.uiHideedits_xp)).click();
					oBrowser.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				}
			} catch (Exception ex) {
				System.out.println("Deleting previous edits");
			}
			oBrowser.findElement(By.xpath(oUIObj.uiShowEdits_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			//For clocks
			//	List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uideletechckbox_xp));
			//	for work premium
			List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uideletechckboxworkP_xp));
			for (int i = 0; i < we1.size(); i++) {
				we1.get(i).click();
				oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			}
			oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
			Thread.sleep(1000);
			oBrowser.findElement(By.xpath(oUIObj.uiShowEdits_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			System.out.println("Pass:Deleted time successfully");
			selectReport.ReportStep("Pass", "Validate Delete time", "Time should delete",
					"Added time deleted successfully");
		} catch (Exception ex) {
			System.out.println("Fail:Not Deleted time.Please verify");
			selectReport.ReportStep("Fail", "Validate Delete time", "Time should delete",
					"Not deleted added time successfully");
		}

	}

	public void DeletealleditsWP() throws InterruptedException {
		try {
			oBrowser.switchTo().defaultContent();
			cSideframe();
			try {
				if (oBrowser.findElement(By.xpath(oUIObj.uiHideedits_xp)).isDisplayed()) {
					oBrowser.findElement(By.xpath(oUIObj.uiHideedits_xp)).click();
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				}
			} catch (Exception ex) {
				System.out.println("");
			}
			oBrowser.findElement(By.xpath(oUIObj.uiShowEdits_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//	for work premium
			List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uideletechckboxworkP_xp));
			for (int i = 0; i < we1.size(); i++) {
				we1.get(i).click();
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			}
			oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
			Thread.sleep(1500);
			oBrowser.findElement(By.xpath(oUIObj.uiShowEdits_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println("Pass:Deleted time successfully");
			selectReport.ReportStep("Pass", "Validate Delete time", "Time should delete",
					"Added time deleted successfully");
		} catch (Exception ex) {
			System.out.println("Fail:Not Deleted time.Please verify");
			selectReport.ReportStep("Fail", "Validate Delete time", "Time should delete",
					"Not deleted added time successfully");
		}

	}



	/*
	 * TC_MA_Daily
	 * Created by Jeffrey 
	 * Date Created: 1/11/19
	 * Usage: Loads Ma timesheet
	 * @Information For the validation purpose
	 *  */  
	public void MADailyTimesheet()
	{
		String pass = "MA_Daily";
		alaskaAssociate(pass);
	}
	/*
	 * TC_AK_Daily
	 * Created by Jeffrey 
	 * Date Created: 1/11/19
	 * Usage: Loads AK and NV Daily timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void AKDailyTimesheet()
	{
		String pass = "NV_AK_Daily";
		alaskaAssociate(pass);
	}	
	/*
	 * TC_RI_Daily
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads RI Daily timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void RIDailyTimesheet()
	{
		String pass = "RI_Daily";
		alaskaAssociate(pass);
	}
	/*
	 * TC_CO_Daily
	 * Created by Jeffrey 
	 * Date Created: 1/11/19
	 * Usage: Loads CO Daily timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void CODailyTimesheet()
	{
		String pass = "CO_Daily";
		alaskaAssociate(pass);
	}
	/*
	 * TC_CA_Daily
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads CA Daily timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void CADailyTimesheet()
	{
		String pass = "CA_Daily";
		alaskaAssociate(pass);
	}
	/*
	 * TC_Common_Daily
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads Common Daily timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void CommonDailyTimesheet()
	{
		String pass = "Common_Daily";
		alaskaAssociate(pass);
	}
	/*
	 * TC_DM_Daily
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads DM Daily timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void DMDailyTimesheet()
	{
		String pass = "DM_Daily";
		alaskaAssociate(pass);
	}
	/*
	 * TC_AddingAlask Users 
	 * Created by Sujatha 
	 * Date Created: 12/19/18
	 * Usage: Time clock for Alask Associates
	 * @Information For the validation purpose
	 *  */  

	public void alaskaAssociate(String timesheet)
	{
		String supid="";
		String tempsupid="";
		String employee="",tempemp="";	
		ExcelRead AlaskatimeSheet = new ExcelRead(sTDFileName, timesheet);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		//Validate if Timesheet link exist 
		//Loops through each row in excel sheet
		int inofRows = AlaskatimeSheet.rowCount();
		System.out.println("Total number of rows are :" +inofRows);
		for(int i=1;i<inofRows;i++)
		{
			try{
				employee=AlaskatimeSheet.getCellData("Employee", i);
				supid=AlaskatimeSheet.getCellData("SupID", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if(!supid.equals(tempsupid))
				{
					System.out.println("Inside");
					if(i>1)
					{
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}



					tempsupid=supid;
					//Enter User ID
					System.out.println("***");
					//Enter User ID
					try {
						oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
						setText(oUIObj.uID_xp, AlaskatimeSheet.getCellData("SupID", i));
						selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
								"UserName entered as " + AlaskatimeSheet.getCellData("SupID", i));
					}
					catch(Exception e)
					{
						System.out.println("No more rows to execute");
					}
					//Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp,AlaskatimeSheet.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In","Password entered as " + AlaskatimeSheet.getCellData("SupPass", i));
					//Thread.sleep(500);

					//Clicking on SignIn
					Thread.sleep(500);
					controlClick(oUIObj.signIn_xp,"SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In","Clicked on Signin Button");
					//Call timesheet function 
					timeSheetAK(employee);
				}
				//Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String emp;
				String AKDay;
				//Loading  Employee number

				if(!employee.equals(tempemp))
				{
					oBrowser.findElement(By.xpath(oUIObj.uiLoadEmptxt_xp)).clear();
					setText( oUIObj.uiLoadEmptxt_xp ,AlaskatimeSheet.getCellData("Employee", i));
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiLoadH_xp)).click();
					Thread.sleep(1500);
					emp = oBrowser.findElement(By.xpath(oUIObj.uiEmpH_xp)).getText();
					selectReport.ReportStep("Pass", "Employee link is displayed", "Validate Employee Name","Employee Name"+ emp);		
					tempemp=employee;
				}

				AKDay = AlaskatimeSheet.getCellData("Day", i);
				System.out.println(AKDay);
				Thread.sleep(500);
				String overtimetype=AlaskatimeSheet.getCellData("Overwrite_Type", i);
System.out.println(overtimetype);
				List<WebElement> we1=oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
				List<WebElement> we3=oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
				for(int j=0;j<we1.size();j++)
				{
					if(AKDay.contains(we1.get(j).getText()))
					{
						if(overtimetype!="")
						{

							String overwritefield=AlaskatimeSheet.getCellData("Overwrite_field_1", i);
							we3.get(j).click();
							Thread.sleep(500);
							oBrowser.findElement(By.xpath(oUIObj.uiemployeeholiday_xp+overtimetype+"']")).click();
							Thread.sleep(500);
							oBrowser.switchTo().frame(oBrowser.findElement(By.name("ovrPopup")));
							setText(oUIObj.uiholidayname_xp, overwritefield);
							Thread.sleep(500);
							try
							{
								oBrowser.findElement(By.xpath(oUIObj.uiholidaysubmit_xp)).click();
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.uiholidaysubmit_xp)).click();
								Thread.sleep(1500);
								System.out.print("Pass:Added holiday successfully for TC-"+i+" -"+overwritefield);
								selectReport.ReportStep("Pass", "Validate Holiday", "Holiday should add","Added holiday successfully for TC-"+i+" -"+overwritefield);
							}
							catch(Exception ex)
							{
								System.out.print("Fail:Not added holiday successfully for TC-"+i+" -"+overwritefield);
								selectReport.ReportStep("Fail", "Validate Holiday", "Holiday should add","Not Added holiday successfully for TC-"+i+" -"+overwritefield);
							}
							oBrowser.switchTo().defaultContent();
							cSideframe();
							oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						}
						oBrowser.switchTo().defaultContent();
						cSideframe();
						try
						{
							List<WebElement> we2=oBrowser.findElements(By.xpath(oUIObj.uiaddbtns_xp));	
							
							we2.get(j).click();
							
						}
						catch(Exception ex)
						{
							System.out.println(ex.getMessage());
						}
						break;
					}
				}
				
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClick_xp)));
				
				oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
				setText(oUIObj.uiAddNewClick_xp, AlaskatimeSheet.getCellData("Clock_1", i));
				Thread.sleep(1000);
				oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
				Thread.sleep(500);
				if(AlaskatimeSheet.getCellData("Expected", i).contains("Invalid"))
				{
					try
					{
						Alert alert = oBrowser.switchTo().alert();
						alert.accept();	
						Thread.sleep(500);
						oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
						System.out.println("Pass: Invalid clock data not accepted.Alert displayed");
						selectReport.ReportStep("Pass", "Validate InvalidData", "Invalida clock data should not add","Invalid clock data not accepted.Alert displayed");
					}
					catch(Exception ex)
					{

					}
				}
				//Enters Clock 2
				oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
				setText(oUIObj.uiAddNewClick_xp, AlaskatimeSheet.getCellData("Clock_2", i));
				Thread.sleep(500);
				oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
				Thread.sleep(500);
				if(AlaskatimeSheet.getCellData("Expected", i).contains("Invalid"))
				{
					try
					{
						Alert alert = oBrowser.switchTo().alert();
						alert.accept();	
						Thread.sleep(500);
						oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
						System.out.println("Pass: Invalid clock data not accepted.Alert displayed");
						selectReport.ReportStep("Pass", "Validate InvalidData", "Invalida clock data should not add","Invalid clock data not accepted.Alert displayed");
					}
					catch(Exception ex)
					{

					}
				}
				//Enters Clock 3
				if(AlaskatimeSheet.getCellData("Clock_3", i)!="")
				{
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
					setText(oUIObj.uiAddNewClick_xp, AlaskatimeSheet.getCellData("Clock_3", i));
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
					Thread.sleep(1500);
					if(AlaskatimeSheet.getCellData("Expected", i).contains("Invalid"))
					{
						try
						{
							Alert alert = oBrowser.switchTo().alert();
							alert.accept();	
							Thread.sleep(500);
							oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
							System.out.println("Pass: Invalid clock data not accepted.Alert displayed");
							selectReport.ReportStep("Pass", "Validate InvalidData", "Invalida clock data should not add","Invalid clock data not accepted.Alert displayed");
						}
						catch(Exception ex)
						{

						}
					}
				}
				//Enters Clock 4
				if(AlaskatimeSheet.getCellData("Clock_4", i)!="")
				{
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
					setText(oUIObj.uiAddNewClick_xp, AlaskatimeSheet.getCellData("Clock_4", i));
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
					Thread.sleep(1500);
					if(AlaskatimeSheet.getCellData("Expected", i).contains("Invalid"))
					{
						try
						{
							Alert alert = oBrowser.switchTo().alert();
							alert.accept();	
							Thread.sleep(500);
							oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
							System.out.println("Pass: Invalid clock data not accepted.Alert displayed");
							selectReport.ReportStep("Pass", "Validate InvalidData", "Invalida clock data should not add","Invalid clock data not accepted.Alert displayed");
						}
						catch(Exception ex)
						{

						}
					}
				}
				//Enters Clock 5
				if(AlaskatimeSheet.getCellData("Clock_5", i)!="")
				{
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
					setText(oUIObj.uiAddNewClick_xp, AlaskatimeSheet.getCellData("Clock_5", i));
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
					Thread.sleep(1500);
					if(AlaskatimeSheet.getCellData("Expected", i).contains("Invalid"))
					{
						try
						{
							Alert alert = oBrowser.switchTo().alert();
							alert.accept();	
							Thread.sleep(500);
							oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
							System.out.println("Pass: Invalid clock data not accepted.Alert displayed");
							selectReport.ReportStep("Pass", "Validate InvalidData", "Invalida clock data should not add","Invalid clock data not accepted.Alert displayed");
						}
						catch(Exception ex)
						{

						}
					}
				}
				//Enters Clock 6
				if(AlaskatimeSheet.getCellData("Clock_6", i)!="")
				{
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
					setText(oUIObj.uiAddNewClick_xp, AlaskatimeSheet.getCellData("Clock_6", i));
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
					Thread.sleep(1500);
					if(AlaskatimeSheet.getCellData("Expected", i).contains("Invalid"))
					{
						try
						{
							Alert alert = oBrowser.switchTo().alert();
							alert.accept();	
							Thread.sleep(500);
							oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
							System.out.println("Pass: Invalid clock data not accepted.Alert displayed");
							selectReport.ReportStep("Pass", "Validate InvalidData", "Invalida clock data should not add","Invalid clock data not accepted.Alert displayed");
						}
						catch(Exception ex)
						{

						}
					}
				}
				oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
				Thread.sleep(1500);
				if(AlaskatimeSheet.getCellData("Expected", i).contains("|"))
				{

					String[] expectime=FilterDailyExpectedTime(AlaskatimeSheet.getCellData("Expected", i));
					System.out.println("***"+expectime[0]);
					System.out.println("***"+expectime[1].replace("|", ""));
					System.out.println("***"+expectime[2].replace("|", ""));
					System.out.println(expectime[3]);
					System.out.println("***");
					if(expectime[3]=="")
					{
						oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						try
						{
							String actregtime=oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
							String actottime=oBrowser.findElement(By.xpath(oUIObj.uiottime_xp)).getText();
							if(actottime.equals(""))
								actottime=oBrowser.findElement(By.xpath("//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[4]")).getText();
							System.out.println("***"+actottime);
							if(actregtime.equals(expectime[0]))
							{
								System.out.println("Pass:Successfully displayed regular time-"+actregtime+"  for the TC-"+i);
								selectReport.ReportStep("Pass", "Validate regular time", "Added regular time should display","Regular time displayed successfully-"+actregtime+" for the TC-"+i);
							}
							else
							{
								System.out.println("Fail:Not displayed regular time. Please verify-"+actregtime+"  for the TC-"+i);
								selectReport.ReportStep("Fail", "Validate regular time", "Added regular time should display","Regular time not displayed as per test data.Please verify."+" for the TC-"+i);
							}
							if(expectime[2].equals("UNPAID"))
							{
								String actunpaidtime=oBrowser.findElement(By.xpath("//table[@class='wb_tshourtable_table']/tbody/tr[3]/td[3]")).getText();
								if(actunpaidtime.equals(expectime[1].replace("|", "")))
								{
									System.out.println("Pass:Successfully displayed UNPAID time-"+actunpaidtime+"  for the TC-"+i);
									selectReport.ReportStep("Pass", "Validate UNPAID", "Added UNPAID  should display","UNPAID  displayed successfully-"+actunpaidtime+"  for the TC-"+i);
								}
								else
								{
									System.out.println("Fail:UNPAID not displayed as per test data.please verify."+"  for the TC-"+i);
									selectReport.ReportStep("Fail", "Validate UNPAID", "Added UNPAID should display","UNPAID not displayed as per test data.please verify."+"  for the TC-"+i);
								}
							}
							else if(expectime[2].equals("REG"))
							{
								String actmeallttime=oBrowser.findElement(By.xpath(oUIObj.uimeallptime_xp)).getText();
								if(actmeallttime.equals(expectime[1].replace("|", "")))
								{
									System.out.println("Pass:Successfully displayed MealLP time-"+actmeallttime+"  for the TC-"+i);
									selectReport.ReportStep("Pass", "Validate MealLP", "Added MealLP  should display","MealLP  displayed successfully-"+actmeallttime+"  for the TC-"+i);
								}
								else
								{
									System.out.println("Fail:MealLP not displayed as per test data.please verify."+"  for the TC-"+i);
									selectReport.ReportStep("Fail", "Validate MealLP", "Added MealLP should display","MealLP not displayed as per test data.please verify."+"  for the TC-"+i);
								} 
							}
							else
							{
								if(actottime.equals(expectime[1].replace("|", "")))
								{
									System.out.println("Pass:Successfully displayed OT time-"+actottime+"  for the TC-"+i);
									selectReport.ReportStep("Pass", "Validate OT", "Added OT  should display","OT  displayed successfully-"+actottime+"  for the TC-"+i);
								}
								else
								{
									System.out.println("Fail:OT not displayed as per test data.please verify."+"  for the TC-"+i);
									selectReport.ReportStep("Fail", "Validate OT", "Added OT should display","OT not displayed as per test data.please verify."+"  for the TC-"+i);
								}
							}

						}
						catch(Exception ex1)
						{
							System.out.println("Not displayed the time after added.Please verify manually");
							selectReport.ReportStep("Fail", "Validate Regular and OT/UNPAID", "Added regular and OT/UNPAID should display","Regular and OT/UNPAID not displayed successfully");
						}
					}
					else
					{
						System.out.println("Inside");
						oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						try
						{
							String actregtime=oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
							String actottime=oBrowser.findElement(By.xpath("//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[4]")).getText();
							String unpaidtime="";
							if(expectime[3].contains("OT"))
								unpaidtime=oBrowser.findElement(By.xpath("//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[5]")).getText();
							else
								unpaidtime=oBrowser.findElement(By.xpath("//table[@class='wb_tshourtable_table']/tbody/tr[3]/td[3]")).getText();
							if(actregtime.equals(expectime[0]))
							{
								System.out.println("Pass:Successfully displayed regular time-"+actregtime+"  for the TC-"+i);
								selectReport.ReportStep("Pass", "Validate regular time", "Added regular time should display","Regular time displayed successfully-"+actregtime+" for the TC-"+i);
							}
							else
							{
								System.out.println("Fail:Not displayed regular time. Please verify-"+actregtime+"  for the TC-"+i);
								selectReport.ReportStep("Fail", "Validate regular time", "Added regular time should display","Regular time not displayed as per test data.Please verify."+" for the TC-"+i);
							}
							if(actottime.equals(expectime[1].replace("|", "")))
							{
								System.out.println("Pass:Successfully displayed OT time-"+actottime+"  for the TC-"+i);
								selectReport.ReportStep("Pass", "Validate OT", "Added OT  should display","OT  displayed successfully-"+actottime+"  for the TC-"+i);
							}
							else
							{
								System.out.println("Fail:OT not displayed as per test data.please verify."+"  for the TC-"+i);
								selectReport.ReportStep("Fail", "Validate OT", "Added OT should display","OT not displayed as per test data.please verify."+"  for the TC-"+i);
							}
							if(unpaidtime.equals(expectime[2].replace("|", "")))
							{
								System.out.println("Pass:Successfully displayed UNPAID/OT2 time-"+unpaidtime+"  for the TC-"+i);
								selectReport.ReportStep("Pass", "Validate MEAL UNPAID/OT2", "Added UNPAID/OT2  should display","UNPAID/OT2  displayed successfully-"+unpaidtime+"  for the TC-"+i);
							}
							else
							{
								System.out.println("Fail:UNPAID not displayed as per test data.please verify."+"  for the TC-"+i);
								selectReport.ReportStep("Fail", "Validate UNPAID", "Added OT should display","UNPAID not displayed as per test data.please verify."+"  for the TC-"+i);
							}

						}
						catch(Exception ex1)
						{
							System.out.println("Not displayed the time after added.Please verify manually");
							selectReport.ReportStep("Fail", "Validate Regular and OT/UNPAID", "Added regular and OT/UNPAID should display","Regular and OT/UNPAID not displayed successfully");
						}
					}

				}
				else if(AlaskatimeSheet.getCellData("Expected", i).contains("Invalid"))
				{
					System.out.println("Pass: Invalid clock data not accepted.Alert displayed");
					selectReport.ReportStep("Pass", "Validate InvalidData", "Invalida clock data should not add","Invalid clock data not accepted.Alert displayed");
				}
				else
				{
					String[] expectime=FilteronlyRegularorOTTime(AlaskatimeSheet.getCellData("Expected", i));
					System.out.println("***"+expectime[0]);
					System.out.println("***"+expectime[1]);
					oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
					Thread.sleep(1500);
					oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					try
					{
						if(expectime[1].contains("REG"))
						{
							String actregtime=oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
							if(actregtime.equals(expectime[0]))
							{
								System.out.println("Pass:Successfully displayed regular time-"+actregtime+"  for the TC-"+i);
								selectReport.ReportStep("Pass", "Validate regular time", "Added regular time should display","Regular time displayed successfully-"+actregtime+" for the TC-"+i);
							}
							else
							{
								System.out.println("Fail:Not displayed regular time. Please verify-"+actregtime+"  for the TC-"+i);
								selectReport.ReportStep("Fail", "Validate regular time", "Added regular time should display","Regular time not displayed as per test data.Please verify."+" for the TC-"+i);
							}
						}
						else if(expectime[1].contains("OT"))
						{
							String actottime=oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
							if(actottime.equals(""))
								actottime=oBrowser.findElement(By.xpath(oUIObj.uiottime_xp)).getText();
							System.out.println("***"+actottime);
							if(actottime.equals(expectime[0]))
							{
								System.out.println("Pass:Successfully displayed OT time-"+actottime+"  for the TC-"+i);
								selectReport.ReportStep("Pass", "Validate OT", "Added OT  should display","OT  displayed successfully-"+actottime+"  for the TC-"+i);
							}
							else
							{
								System.out.println("Fail:OT not displayed as per test data.please verify."+"  for the TC-"+i);
								selectReport.ReportStep("Fail", "Validate OT", "Added OT should display","OT not displayed as per test data.please verify."+"  for the TC-"+i);
							}
						}
					}
					catch(Exception ex)
					{
						System.out.println("Not displayed the time after added.Please verify manually");
						selectReport.ReportStep("Fail", "Validate Regular and OT", "Added regular and OT should display","Regular and OT not displayed successfully");
					}
				}



				//Deleting added time
				Deletealledits();
			}catch(Exception e){
				System.out.println("Fail: Cannot input Employee Clock-in information"+"  for the TC-"+i);
				selectReport.ReportStep("Fail", "Validate Regular and OT or timecode for the Daily", "Time should add for daily","Time not added for daily.Please verify manually"+"  for the TC-"+i);
			}

		}

	}




	/*
	 * TC_CommonWeekly
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads Common Weekly timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void CommonWeekly()
	{
		String pass = "CommonST_Weekly";
		CommonST_Weekly(pass);
	}
	/*
	 * TC_MA_Weekly
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads MA Weekly timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void MAWeekly()
	{
		String pass = "MA_Weekly";
		CommonST_Weekly(pass);
	}
	/*
	 * TC_AK_NV_Weekly
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads AK and NV Weekly timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void AKNVWeekly()
	{
		String pass = "AK_NV_Weekly";
		CommonST_Weekly(pass);
	}
	/*
	 * TC_RI_Weekly
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads RI Weekly timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void RIWeekly()
	{
		String pass = "RI_Weekly";
		CommonST_Weekly(pass);
	}
	/*
	 * TC_CO_Weekly
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads CO Weekly timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void COWeekly()
	{
		String pass = "CO_Weekly";
		CommonST_Weekly(pass);
	}
	/*
	 * TC_CA_Weekly
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads CA Weekly timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void CAWeekly()
	{
		String pass = "CA_Weekly";
		CommonST_Weekly(pass);
	}
	/*
	 * TC_DM_Weekly
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads DM Weekly timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void DMWeekly()
	{
		String pass = "DM_Weekly";
		CommonST_Weekly(pass);
	}

	/*
	 * TC_CommonST_Weekly Users Created by Gandhi 
	 * Date Updated: 02/06/19
	 * Usage: Weekly clock for ST users 
	 * @Information For the validation purpose
	 */
	public void CommonST_Weekly(String Timesheet) {
		ExcelRead CommonST_Weekly = new ExcelRead(sTDFileName, Timesheet);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		String tcode = "";
		String TCN = "";
		String supid = "";
		String tempsupid = "";
		String employee = "", tempemp = "";
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = CommonST_Weekly.rowCount();

		System.out.println("Total number of rows are :" + inofRows);
		int cnt = 1;
		for (int i = 1; i < inofRows; i = i + 7) {
			try {
				supid = CommonST_Weekly.getCellData("SupID", i);
				employee = CommonST_Weekly.getCellData("Employee", i);
				TCN = CommonST_Weekly.getCellData("TEST#", i);
				System.out.println(TCN);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						try {
							oBrowser.switchTo().defaultContent();
							oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						} catch (Exception e) {
							System.out.println("There is no more test data to execute");
						}
					}
					tempsupid = supid;
					// Enter User ID
					System.out.println("***");
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, CommonST_Weekly.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + CommonST_Weekly.getCellData("SupID", i));

					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, CommonST_Weekly.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + CommonST_Weekly.getCellData("SupPass", i));
					// Thread.sleep(500);

					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");

					// Call timesheet function
					timeSheetAK(employee);
				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				Thread.sleep(500);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String emp;
				String AKDay;
				int temp1 = i;
				tcode = CommonST_Weekly.getCellData("TCODE_Edit", temp1);
				int temp = i;
				// Loading Employee number
				employee = CommonST_Weekly.getCellData("Employee", i);
				System.out.println("**" + employee);
				if (!employee.equals(tempemp)) {
					oBrowser.findElement(By.xpath(oUIObj.uiLoadEmptxt_xp)).clear();
					setText(oUIObj.uiLoadEmptxt_xp, CommonST_Weekly.getCellData("Employee", i));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.uiLoadH_xp)).click();
					Thread.sleep(1500);
					emp = oBrowser.findElement(By.xpath(oUIObj.uiEmpH_xp)).getText();
					selectReport.ReportStep("Pass", "Employee link is displayed", "Validate Employee Name",
							"Employee Name" + emp);
					tempemp = employee;
				}
				for (int p = 1; p < 8; p++) {
					AKDay = CommonST_Weekly.getCellData("Day", temp);
					List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
					// List<WebElement> we3 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
					for (int j = 0; j < we1.size(); j++) {
						if (AKDay.contains(we1.get(j).getText())) {

							try {
								List<WebElement> we2 = oBrowser.findElements(By.xpath(oUIObj.uiaddbtns_xp));
								we2.get(j).click();
							} catch (Exception ex) {
								System.out.println(ex.getMessage());
							}
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClick_xp)));
							if (CommonST_Weekly.getCellData("Clock_1", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, CommonST_Weekly.getCellData("Clock_1", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								System.out.println("Added clock 1");
							}
							// Enters Clock 2
							if (CommonST_Weekly.getCellData("Clock_2", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, CommonST_Weekly.getCellData("Clock_2", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 2");
							}
							// Enters Clock 3
							if (CommonST_Weekly.getCellData("Clock_3", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, CommonST_Weekly.getCellData("Clock_3", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 3");
							}
							// Enters Clock 4
							if (CommonST_Weekly.getCellData("Clock_4", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, CommonST_Weekly.getCellData("Clock_4", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 4");
							}
							// Enters Clock 5
							if (CommonST_Weekly.getCellData("Clock_5", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, CommonST_Weekly.getCellData("Clock_5", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 5");
							}
							// Enters Clock 6
							if (CommonST_Weekly.getCellData("Clock_6", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, CommonST_Weekly.getCellData("Clock_6", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 6");
							}
							oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
							Thread.sleep(1500);
							// Adding Overwrite field
							String overtimetype = CommonST_Weekly.getCellData("Overwrite_Type", temp);
							if (!overtimetype.equals("")) {
								System.out.println("Inside Holiday " + overtimetype);
								List<WebElement> we4 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
								System.out.println("Entered into click on Edit button");
								System.out.println(AKDay);
								we4.get(j).click();
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								controlClick(oUIObj.uimenuEmpHoliSH_xp, "Clicked on Employee Holiday");
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								// entering into over popup
								WebElement frame1 = oBrowser.findElement(By.id("ovrPopup"));
								oBrowser.switchTo().frame(frame1);
								String Holiday = CommonST_Weekly.getCellData("Overwrite_field_1", temp);
								oBrowser.findElement(By.xpath(oUIObj.uiemployeeholidaysel_xp)).click();
								oBrowser.findElement(By.xpath(oUIObj.uiemployeeholidaysel_xp)).sendKeys(Holiday);
								WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.uiemployeeholidaysel_xp));
								Thread.sleep(1500);
								TimeCodeTY.sendKeys(Keys.TAB);
								System.out.println("HOLIDAY entered successfully");
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								try {
									oBrowser.findElement(By.xpath(oUIObj.uiholidaysubmit_xp)).click();
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									System.out.println("Frame Okay");
									oBrowser.switchTo().defaultContent();
									cSideframe();
									oBrowser.findElement(By.xpath(oUIObj.uiSubmitTimeSheet_xp)).click();
									System.out.println("UI submitted");
									Thread.sleep(1500);
									System.out.println("Pass:Added holiday successfully for TC-" + i + " -" + AKDay);
									selectReport.ReportStep("Pass", "Holiday adding", "Validate Holiday",
											"Added holiday successfully for TC-" + i + " -" + AKDay);
								} catch (Exception ex) {
									System.out.println("Fail:Not added holiday successfully for TC-" + i + " -");
									selectReport.ReportStep("Fail", "Validate Holiday", "Holiday should add",
											"Not Added holiday successfully for TC-" + i + " -" + AKDay);
								}
								System.out.println(AKDay);
								oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
							}

							// Adding time code
							if (CommonST_Weekly.getCellData("TCODE_Edit", temp) != "") {

								tcode = CommonST_Weekly.getCellData("TCODE_Edit", temp);
								System.out.println("Inside tcode" + tcode);
								try {
									System.out.println("Inside TCODE" + tcode);
									List<WebElement> we4 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
									System.out.println("Entered into click on Edit button");
									System.out.println(AKDay);
									we4.get(j).click();
									oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
									controlClick(oUIObj.uimenuEmpLTA_xp, "Clicked on Employee LTA");
									Thread.sleep(500);
								
									Thread.sleep(500);
									// entering into over popup
									WebElement frame1 = oBrowser.findElement(By.id("ovrPopup"));
									oBrowser.switchTo().frame(frame1);

									oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTA_xp)).click();
									oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTA_xp)).sendKeys(tcode);
									WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTA_xp));
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									TimeCodeTY.sendKeys(Keys.TAB);
									if (CommonST_Weekly.getCellData("Start Time", temp) != "") 
									{
										String STime = CommonST_Weekly.getCellData("Start Time", temp);
										String ETime = CommonST_Weekly.getCellData("End Time", temp);
										oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAST_xp)).click();
										oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAST_xp)).sendKeys(STime);
										WebElement TimeCodeTY1 = oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAST_xp));
										oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
										TimeCodeTY1.sendKeys(Keys.TAB);
										oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAET_xp)).click();
										oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAET_xp)).sendKeys(ETime);
										WebElement TimeCodeTY2 = oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAET_xp));
										oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
										TimeCodeTY2.sendKeys(Keys.TAB);
										Thread.sleep(500);
									}
									System.out.println("TCODE entered successfully");
									Thread.sleep(500);
									try {
										oBrowser.findElement(By.xpath(oUIObj.uiholidaysubmit_xp)).click();
										Thread.sleep(500);
										System.out.println("Frame Okay");
										oBrowser.switchTo().defaultContent();
										cSideframe();
										oBrowser.findElement(By.xpath(oUIObj.uiSubmitTimeSheet_xp)).click();
										System.out.println("UI submitted");
										oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
										System.out.println("Pass:Added TCODE successfully for TC-" + TCN + " -" + tcode);
										selectReport.ReportStep("Pass", "tcode adding", "tcode Holiday",
												"Added tcode successfully for TC-" + TCN + " -" + tcode);
									} catch (Exception ex) {
										System.out.println("Fail:Not added tcode successfully for TC-" + TCN + " -");
										selectReport.ReportStep("Fail", "Validate tcode", "tcode should add",
												"Not Added tcode successfully for TC-" + TCN + " -" + tcode);
									}
									System.out.println(AKDay);
									oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
								} 
								catch (Exception ex) {
									System.out.println("FAIL:Not added Holiday/Tcode for the TC-" + TCN);
									selectReport.ReportStep("FAIL", "Validate Tcode", "Added tcode should display",
											"Tcode not displayed successfully-" + tcode + " for the TC-" + TCN);
								}

							}
							break;

						}
					}

					if (AKDay.equals("Sunday"))
						if (p == 7) {
							if (tcode.equals("")) {

								System.out.println("entered for sunday after every 6 iterations");
								if (CommonST_Weekly.getCellData("Expected", temp).contains("|")) {
									String[] expectime = FilterDailyExpectedTime1(CommonST_Weekly.getCellData("Expected", temp));
									String actregtime = "";
									System.out.println("***" + expectime[0]);
									System.out.println("***" + expectime[1].replace("|", ""));
									System.out.println("***" + expectime[2]);
									System.out.println("***" + expectime[3].replace("|", ""));
									System.out.println("***" + expectime[4]);

									oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
									Thread.sleep(1500);
									oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
									oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
									if (expectime[2].contains("OT"))
									{
										try {

											actregtime = oBrowser.findElement(By.xpath(oUIObj.uiregtimeMA_xp))
													.getText();

											if (actregtime.equals(expectime[0])) {
												System.out.println("Pass:Successfully displayed regular time-"
														+ actregtime + "  for the TC-" + TCN);
												selectReport.ReportStep("Pass", "Validate regular time",
														"Added regular time should display",
														"Regular time displayed successfully-" + actregtime
														+ " for the TC-" + TCN);
											} else {
												System.out.println("Fail:Not displayed regular time. Please verify-"
														+ actregtime + "  for the TC-" + TCN);
												selectReport.ReportStep("Fail", "Validate regular time",
														"Added regular time should display",
														"Regular time not displayed as per test data.Please verify."
																+ " for the TC-" + TCN);
											}

											String uiempOTtime = oBrowser.findElement(By.xpath(oUIObj.uiempOT_xp))
													.getText();
											String uiempOTtime1 = oBrowser.findElement(By.xpath(oUIObj.uiempOT1_xp))
													.getText();
											String uiempOTtime2 = oBrowser.findElement(By.xpath(oUIObj.uiempOT2_xp))
													.getText();
											System.out.println(uiempOTtime);
											System.out.println(uiempOTtime1);
											System.out.println(uiempOTtime2);
											if (uiempOTtime.equals(expectime[1].replace("|", ""))
													|| uiempOTtime1.equals(expectime[1].replace("|", ""))
													|| uiempOTtime2.equals(expectime[1].replace("|", ""))) 
											{
												System.out.println("Pass:Successfully displayed OT time-" + uiempOTtime
														+ "  for the TC-" + TCN);
												selectReport.ReportStep("Pass", "OT time",
														"Added OT time should display",
														"OT time displayed successfully-" + uiempOTtime + " for the TC-"
																+ TCN);
											} else {
												System.out.println("Fail:Not displayed OT time. Please verify-"
														+ uiempOTtime + "  for the TC-" + TCN);
												selectReport.ReportStep("Fail", "Validate OT time",
														"Added OT time should display",
														"OT time not displayed as per test data.Please verify."
																+ " for the TC-" + TCN);
											}
											if (expectime[4] != null && expectime[4].contains("OT"))
											{
												if (uiempOTtime.equals(expectime[3].replace("|", ""))
														|| uiempOTtime1.equals(expectime[3].replace("|", ""))
														|| uiempOTtime2.equals(expectime[3].replace("|", ""))) {
													System.out.println("Pass:Successfully displayed OT time-" + uiempOTtime
															+ "  for the TC-" + TCN);
													selectReport.ReportStep("Pass", "OT time",
															"Added OT time should display",
															"OT time displayed successfully-" + uiempOTtime + " for the TC-"
																	+ TCN);
												}
												else {
													System.out.println("Fail:Not displayed OT time. Please verify-"
															+ uiempOTtime + "  for the TC-" + TCN);
													selectReport.ReportStep("Fail", "Validate OT time",
															"Added OT time should display",
															"OT time not displayed as per test data.Please verify."
																	+ " for the TC-" + TCN);
												}
											}


										} catch (Exception e) {
											System.out.println(
													"Fail:Not displayed the time after added for OT.Please verify manually"
															+ "  for the TC-" + TCN);
											selectReport.ReportStep("Fail", "Validate Regular and OT",
													"Added regular and OT should display",
													"Regular and OT not displayed successfully" + "  for the TC-"
															+ TCN);
										}



									}

								} else if (!CommonST_Weekly.getCellData("Expected", temp).contains("|")) {
									try {
										String[] expectime2 = Filtercommon2(
												CommonST_Weekly.getCellData("Expected", temp));
										System.out.println("***" + expectime2[0]);
										System.out.println("***" + expectime2[1]);
										oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
										Thread.sleep(1500);
										oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
										oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
										if (expectime2[1].contains("REG")) {
											String actregtime = oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp))
													.getText();
											if (actregtime.equals(expectime2[0])) {
												System.out.println("Pass:Successfully displayed regular time-"
														+ actregtime + "  for the TC-" + TCN);
												selectReport.ReportStep("Pass", "Validate regular time",
														"Added regular time should display",
														"Regular time displayed successfully-" + actregtime
														+ " for the TC-" + TCN);
											} else {
												System.out.println("Fail:Not displayed regular time. Please verify-"
														+ actregtime + "  for the TC-" + TCN);
												selectReport.ReportStep("Fail", "Validate regular time",
														"Added regular time should display",
														"Regular time not displayed as per test data.Please verify."
																+ " for the TC-" + TCN);
											}

										}

									} catch (Exception e) {
										System.out.println(
												"Fail:Not displayed the time after added for OT.Please verify manually"
														+ "  for the TC-" + TCN);
										selectReport.ReportStep("Fail", "Validate Regular and OT",
												"Added regular and OT should display",
												"Regular and OT not displayed successfully" + "  for the TC-" + TCN);
									}

								}
							}
							if (tcode != "") {
								try {
									System.out.println("Entered into vacation periods loop");
									Thread.sleep(1500);
									String actregtime = "";

									if (CommonST_Weekly.getCellData("Expected", temp).contains("|")) {
										System.out.println("entered into vac filter");
										String[] expectime1 = Filtercommon1(
												CommonST_Weekly.getCellData("Expected", temp));
										System.out.println("***" + expectime1[0]);
										System.out.println("***" + expectime1[1].replace("|", ""));
										Thread.sleep(1500);
										oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
										oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
										actregtime = oBrowser.findElement(By.xpath(oUIObj.uiregtimeMA_xp)).getText();
										if (actregtime.equals(expectime1[0])) {
											System.out.println("Pass:Successfully displayed regular time-" + actregtime
													+ "  for the TC-" + TCN);
											selectReport.ReportStep("Pass", "Validate regular time",
													"Added regular time should display",
													"Regular time displayed successfully-" + actregtime + " for the TC-"
															+ TCN);
										} else {
											System.out.println("Fail:Not displayed regular time. Please verify-"
													+ actregtime + "  for the TC-" + TCN);
											selectReport.ReportStep("Fail", "Validate regular time",
													"Added regular time should display",
													"Regular time not displayed as per test data.Please verify."
															+ " for the TC-" + TCN);
										}
										if (tcode.equals("VAC")) {
											String actVACtime = "";
											actVACtime = oBrowser.findElement(By.xpath(oUIObj.uiempVacTotal_xp))
													.getText();
											if (actVACtime.equals(expectime1[1])) {
												System.out.println("Pass:Successfully displayed time-" + tcode
														+ actVACtime + "  for the TC-" + TCN);
												selectReport.ReportStep("Pass", "Validate VAC time",
														"Added VAC time should display",
														"VAC time displayed successfully-" + actVACtime + " for the TC-"
																+ TCN);
											} else {
												System.out.println("Fail:Not displayed VAC time. Please verify-" + tcode
														+ actVACtime + "  for the TC-" + TCN);
												selectReport.ReportStep("Fail", "Validate VAC time",
														"Added VAC time should display",
														"VAC time not displayed as per test data.Please verify."
																+ " for the TC-" + TCN);
											}
										}
										if (tcode.equals("THANKYOU")) {
											String ThankDAY = "";
											ThankDAY = oBrowser.findElement(By.xpath(oUIObj.uiempTHANKDay_xp))
													.getText();
											if (ThankDAY.equals(expectime1[1])) {
												System.out.println("Pass:Successfully displayed time-" + tcode
														+ ThankDAY + "  for the TC-" + TCN);
												selectReport.ReportStep("Pass", "Validate Thank time",
														"Added Thank time should display",
														"Thank time displayed successfully-" + ThankDAY + " for the TC-"
																+ TCN);
											} else {
												System.out.println("Fail:Not displayed Thank time. Please verify-"
														+ tcode + ThankDAY + "  for the TC-" + TCN);
												selectReport.ReportStep("Fail", "Validate Thank time",
														"Added Thank time should display",
														"Thak time not displayed as per test data.Please verify."
																+ " for the TC-" + TCN);
											}
										}
										if (tcode.equals("HOL")) {
											String HOLDAY = "";
											HOLDAY = oBrowser.findElement(By.xpath(oUIObj.uiempHOL_xp)).getText();
											if (HOLDAY.equals(expectime1[1])) {
												System.out.println("Pass:Successfully displayed time-" + tcode + HOLDAY
														+ "  for the TC-" + TCN);
												selectReport.ReportStep("Pass", "Validate HOL time",
														"Added HOL time should display",
														"HOL time displayed successfully-" + HOLDAY + " for the TC-"
																+ TCN);
											} else {
												System.out.println("Fail:Not displayed HOL time. Please verify-"
														+ tcode + HOLDAY + "  for the TC-" + TCN);
												selectReport.ReportStep("Fail", "Validate HOL time",
														"Added HOL time should display",
														"HOL time not displayed as per test data.Please verify."
																+ " for the TC-" + TCN);
											}
										}
										if (tcode.equals("MVPDAY")) {
											String MvpDAY = "";
											MvpDAY = oBrowser.findElement(By.xpath(oUIObj.uiempMVPDay_xp)).getText();
											if (MvpDAY.equals(expectime1[1])) {
												System.out.println("Pass:Successfully displayed time-" + tcode + MvpDAY
														+ "  for the TC-" + TCN);
												selectReport.ReportStep("Pass", "Validate MVP time",
														"Added MVP time should display",
														"MVP time displayed successfully-" + MvpDAY + " for the TC-"
																+ TCN);
											} else {
												System.out.println("Fail:Not displayed MVP time. Please verify-" + tcode
														+ MvpDAY + "  for the TC-" + TCN);
												selectReport.ReportStep("Fail", "Validate MVP time",
														"Added MVP time should display",
														"MVP time not displayed as per test data.Please verify."
																+ " for the TC-" + TCN);
											}
										}
										if (tcode.equals("JURY")) {
											String JURYUNPAID = "";
											JURYUNPAID = oBrowser.findElement(By.xpath(oUIObj.uiempJuryUnpaid_xp))
													.getText();
											if (JURYUNPAID.equals(expectime1[1])) {
												System.out.println("Pass:Successfully displayed time-" + tcode
														+ JURYUNPAID + "  for the TC-" + TCN);
												selectReport.ReportStep("Pass", "Validate JURY time",
														"Added OT JURY time should display",
														"OT JURY time displayed successfully-" + JURYUNPAID
														+ " for the TC-" + TCN);
											} else {
												System.out.println("Fail:Not displayed JURY time. Please verify-"
														+ tcode + JURYUNPAID + "  for the TC-" + TCN);
												selectReport.ReportStep("Fail", "Validate JURY time",
														"Added JURY time should display",
														"JURYtime not displayed as per test data.Please verify."
																+ " for the TC-" + TCN);
											}
										}
									} else {
										System.out.println("Entered into SICK validation");
										String[] expectime2 = Filtercommon2(
												CommonST_Weekly.getCellData("Expected", temp));
										System.out.println("***" + expectime2[0]);
										System.out.println("***" + expectime2[1]);
										oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
										Thread.sleep(1500);
										oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
										oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
										try {
											if (expectime2[1].contains("REG")) {
												actregtime = oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp))
														.getText();
												if (actregtime.equals(expectime2[0])) {
													System.out.println("Pass:Successfully displayed regular time-"
															+ actregtime + "  for the TC-" + TCN);
													selectReport.ReportStep("Pass", "Validate regular time",
															"Added regular time should display",
															"Regular time displayed successfully-" + actregtime
															+ " for the TC-" + TCN);
												} else {
													System.out.println("Fail:Not displayed regular time. Please verify-"
															+ actregtime + "  for the TC-" + TCN);
													selectReport.ReportStep("Fail", "Validate regular time",
															"Added regular time should display",
															"Regular time not displayed as per test data.Please verify."
																	+ " for the TC-" + TCN);
												}
											}
										} catch (Exception ex) {
											System.out.println(
													"Fail:Not displayed the time after added.Please verify manually"
															+ "  for the TC-" + TCN);
											selectReport.ReportStep("Fail", "Validate Regular and OT",
													"Added regular and OT should display",
													"Regular and OT not displayed successfully" + "  for the TC-"
															+ TCN);
										}
									}
								} catch (Exception ex) {
									System.out.println("Fail:Not displayed the time after added.Please verify manually"
											+ "  for the TC-" + TCN);
									selectReport.ReportStep("Fail", "Validate Regular and OT and TCode",
											"Added regular and OT and TCodeshould display",
											"Regular and OT and TCode not displayed successfully" + "  for the TC-"
													+ TCN);
								}
							}
						}
					temp = temp + 1;
					System.out.println("Entering into clocks loop");
				}
				cnt = cnt + 1;
				Deletealledits();
				tcode = "";
			} catch (Exception e) {
				System.out.println("Fail:Cannot input Employee Clock-in information" + " for the test case-TC-" + TCN);
				selectReport.ReportStep("Fail", "Validate Regular and OT or timecode for the weekly",
						"Time should add for weekly" + " for the test case-TC-" + TCN,
						"Time not added for weekly.Please verify manually" + " for the test case-TC-" + TCN);
				try {
					Deletealledits();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/*
	 * TC_EarlyLate
	 * Created by Gandhi 
	 * Date Created: 02/27/19
	 * Usage: Loads EarlyLate timesheets 
	 * @Information For the validation purpose
	 *  */ 
	public void EarlyLate() {
		String pass = "Early_Late";
		EarlyLate(pass);
	}

	/*
	 * TC_EarlyLate
	 * Created by Gandhi 
	 * Date Created: 2/27/19
	 * Usage: Validate regular and late hours after schedule time
	 * @Information For the validation purpose
	 *  */  
	public void EarlyLate(String timesheet)
	{
		String supid="";
		String tempsupid="";
		String employee="",tempemp=""; String TCN = "";
		ExcelRead EarlyLate = new ExcelRead(sTDFileName, timesheet);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		//Validate if Timesheet link exist
		//Loops through each row in excel sheet
		int inofRows = EarlyLate.rowCount();
		System.out.println("Total number of rows are :" +inofRows);
		for(int i=1;i<inofRows;i++)
		{
			try{
				supid=EarlyLate.getCellData("SupID", i);
				TCN = EarlyLate.getCellData("TEST#", i);
				System.out.println(TCN);
				System.out.println(supid);
				System.out.println(tempsupid);
				if(!supid.equals(tempsupid))
				{
					System.out.println("Inside");
					if(i>1)
					{
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid=supid;
					//Enter User ID
					System.out.println("***");
					//Enter User ID
					try {
						oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
						setText(oUIObj.uID_xp, EarlyLate.getCellData("SupID", i));
						selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
								"UserName entered as " + EarlyLate.getCellData("SupID", i));
					}
					catch(Exception e)
					{
						System.out.println("No more rows to execute");
					}
					//Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp,EarlyLate.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In","Password entered as " + EarlyLate.getCellData("SupPass", i));
					//oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					//Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp,"SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In","Clicked on Signin Button");
					//Call timesheet function
					//timeSheetAK();

					employee = EarlyLate.getCellData("Employee", i);
					try{

						if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
						{
							WebElement timesheet1=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
							highlightElement(timesheet1);
							oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
							//Enter Employee number
							Thread.sleep(1500);
							cSideframe();
							setText(oUIObj.UIemployeeTS_xp, employee);
							controlClick(oUIObj.uiLoad_xp,"Load Button");
							selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
						}
					}catch(Exception e){
						System.out.println("Load Button is not displayed");
					}
				}

				//Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String emp;
				String AKDay;
				//Loading Employee number
				employee=EarlyLate.getCellData("Employee", i);
				if(!employee.equals(""))
				{
					if(!employee.equals(tempemp))
					{
						oBrowser.findElement(By.xpath(oUIObj.uiLoadEmptxt_xp)).clear();
						setText( oUIObj.uiLoadEmptxt_xp ,EarlyLate.getCellData("Employee", i));
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.uiLoadH_xp)).click();
						Thread.sleep(1500);
						emp = oBrowser.findElement(By.xpath(oUIObj.uiEmpH_xp)).getText();
						selectReport.ReportStep("Pass", "Employee link is displayed", "Validate Employee Name","Employee Name"+ emp);
						tempemp=employee;
					}
				}

				AKDay = EarlyLate.getCellData("Day", i);
				String overtimetype=EarlyLate.getCellData("Overwrite_Type", i);
				List<WebElement> we1=oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
				List<WebElement> we3=oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
				for(int j=0;j<we1.size();j++)
				{
					if(AKDay.contains(we1.get(j).getText()))
					{
						if(overtimetype!="")
						{
							we3.get(j).click();
							Thread.sleep(500);
							String overwritefield1=EarlyLate.getCellData("Overwrite_field_1", i);
							String overwritefield2=EarlyLate.getCellData("Overwrite_field_2", i);
							//Clicking on Schedule Times
							oBrowser.findElement(By.xpath(oUIObj.uiearlylateschtime_xp)).click();
							Thread.sleep(500);
							oBrowser.switchTo().frame(oBrowser.findElement(By.name("ovrPopup")));
							setText(oUIObj.uistarttime_xp, overwritefield1);
							setText(oUIObj.uiendtime_xp, overwritefield2);
							Thread.sleep(500);
							try
							{
								oBrowser.findElement(By.xpath(oUIObj.uisubmitbtn_xp)).click();
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							}
							catch(Exception ex)
							{
								System.out.println(ex.getMessage());
							}
							oBrowser.switchTo().defaultContent();
							cSideframe();
							oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
							Thread.sleep(1500);
						}
						oBrowser.switchTo().defaultContent();
						cSideframe();
						try
						{
							List<WebElement> we2=oBrowser.findElements(By.xpath(oUIObj.uiaddbtns_xp));
							we2.get(j).click();
						}
						catch(Exception ex)
						{
							System.out.println(ex.getMessage());
						}
						break;
					}
				}
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClick_xp)));
				if(EarlyLate.getCellData("Clock_1", i)!="")
				{
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
					setText(oUIObj.uiAddNewClick_xp, EarlyLate.getCellData("Clock_1", i));
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
					System.out.println("Added clock 1");
				}
				//Enters Clock 2
				if(EarlyLate.getCellData("Clock_2", i)!="")
				{
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
					setText(oUIObj.uiAddNewClick_xp, EarlyLate.getCellData("Clock_2", i));
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
					System.out.println("Added clock 2");
					Thread.sleep(500);
				}
				oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
				oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				if(EarlyLate.getCellData("Expected", i).contains("|"))
				{
					String[] expectime=FilterExpectedTime(EarlyLate.getCellData("Expected", i));
					System.out.println("***"+expectime[0]);
					System.out.println("***"+expectime[1].replace("|", ""));
					oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
					Thread.sleep(1500);
					oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					try
					{
						String lbl=oBrowser.findElement(By.xpath(oUIObj.uiearlylatelbl_xp)).getText();
						if(lbl.equals("GUAR"))
							lbl=oBrowser.findElement(By.xpath(oUIObj.uiearlylatelbl2_xp)).getText();
						String actregtime=oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
						String actottime=oBrowser.findElement(By.xpath(oUIObj.uilatetime_xp+lbl+"']/following-sibling::td[2]")).getText();
						if(actregtime.equals(expectime[0]))
						{
							System.out.println("Pass:Successfully displayed regular time "+actregtime +" for the employee"+ EarlyLate.getCellData("Employee", i)+"-TC"+TCN);
							selectReport.ReportStep("Pass", "Validate regular time", "Added regular time should display","Regular time displayed successfully"+actregtime+" for the employee"+ EarlyLate.getCellData("Employee", i)+"-TC"+TCN);
						}
						else
						{
							System.out.println("Fail:Not displayed regular time for the employee"+ EarlyLate.getCellData("Employee", i)+". Please verify"+"-TC"+TCN);
							selectReport.ReportStep("Fail", "Validate regular time", "Added regular time should display","Regular time not displayed as per test data.Please verify.Displayed time-"+actregtime +" for the employee"+ EarlyLate.getCellData("Employee", i)+"-TC"+TCN);
						}
						if(actottime.equals(expectime[1].replace("|", "")))
						{
							System.out.println("Pass:Successfully displayed "+lbl+" time"+actottime +" for the employee"+ EarlyLate.getCellData("Employee", i)+"-TC"+TCN);
							selectReport.ReportStep("Pass", "Validate "+lbl, "Added "+lbl+" time should display",lbl+" time displayed successfully"+actottime +" for the employee"+ EarlyLate.getCellData("Employee", i)+"-TC"+TCN);
						}
						else
						{
							System.out.println("Fail:Not displayed "+lbl+" time.for the employee"+ EarlyLate.getCellData("Employee", i)+".Please verify"+"-TC"+TCN);
							selectReport.ReportStep("Fail", "Validate "+lbl, "Added "+lbl+" time should display",lbl+" time not displayed as per test data.please verify.Displayed time-"+actottime+" for the employee"+ EarlyLate.getCellData("Employee", i)+"-TC"+TCN);
						}
					}
					catch(Exception ex1)
					{
						System.out.println("Fail:Not displayed the time after added.for the employee"+ EarlyLate.getCellData("Employee", i)+".Please verify manually"+"-TC"+TCN);
						selectReport.ReportStep("Fail", "Validate Regular and Late/Early", "Added regular and Late/Early should display","Regular and LATE/EARLY not displayed successfully for the employee"+ EarlyLate.getCellData("Employee", i)+"-TC"+TCN);
					}    
				}
				else
				{
					String[] expectime=FilteronlyRegularorOTTime(EarlyLate.getCellData("Expected", i));
					System.out.println("***"+expectime[0]);
					System.out.println("***"+expectime[1]);
					oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
					Thread.sleep(1500);
					oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					try
					{
						String actregtime=oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
						System.out.println("***"+actregtime);
						if(expectime[1].contains("REG"))
						{
							//String actregtime=oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
							if(actregtime.equals(expectime[0]))
							{
								System.out.println("Pass:Successfully displayed regular time-"+actregtime+" for the TC-"+TCN);
								selectReport.ReportStep("Pass", "Validate regular time", "Added regular time should display","Regular time displayed successfully-"+actregtime+" for the TC-"+TCN);
							}
							else
							{
								System.out.println("Fail:Not displayed regular time. Please verify-"+actregtime+" for the TC-"+TCN);
								selectReport.ReportStep("Fail", "Validate regular time", "Added regular time should display","Regular time not displayed as per test data.Please verify."+" for the TC-"+TCN);
							}
						}
						else if(expectime[1].contains("OT"))
						{
							String actottime=oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
							if(actottime.equals(""))
								actottime=oBrowser.findElement(By.xpath(oUIObj.uiottime_xp)).getText();
							System.out.println("***"+actottime);
							if(actottime.equals(expectime[0]))
							{
								System.out.println("Pass:Successfully displayed OT time-"+actottime+" for the TC-"+TCN);
								selectReport.ReportStep("Pass", "Validate OT", "Added OT should display","OT displayed successfully-"+actottime+" for the TC-"+TCN);
							}
							else
							{
								System.out.println("Fail:OT not displayed as per test data.please verify."+" for the TC-"+TCN);
								selectReport.ReportStep("Fail", "Validate OT", "Added OT should display","OT not displayed as per test data.please verify."+" for the TC-"+TCN);
							}
						}
					}
					catch(Exception ex)
					{
						System.out.println("Not displayed the time after added.Please verify manually");
						selectReport.ReportStep("Fail", "Validate Regular and OT", "Added regular and OT should display","Regular and OT not displayed successfully");
					}
				}
				//Deleting added time
				Deletealledits();
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Fail: Cannot input Employee Clock-in information"+" for the TC-"+TCN);
				selectReport.ReportStep("Fail", "Validate Regular and OT or timecode for the Daily", "Time should add for daily","Time not added for daily.Please verify manually"+" for the TC-"+TCN);
			}
		}
	}


	/*
	 * TC_MA_Call_in
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads MA Call in Pay timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void MACallinPay()
	{
		String pass = "MA_Call_in";
		CallinPay(pass);
	}
	/*
	 * TC_RI_Call_in
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads RI Call in Pay timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void RICallinPay()
	{
		String pass = "RI_Call_in";
		CallinPay(pass);
	}
	/*
	 * TC_CA_Call_in
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads CA Call in Pay timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void CACallinPay()
	{
		String pass = "CA_Call_in";
		CallinPay(pass);
	}
	/*
	 * TC_NJ_Call_in
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads NJ Call in Pay timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void NJCallinPay()
	{
		String pass = "NJ_Call_in";
		CallinPay(pass);
	}
	/*
	 * TC_NY_Call_in
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads NY Call in Pay timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void NYCallinPay()
	{
		String pass = "NY_Call_in";
		CallinPay(pass);
	}
	/*
	 * TC_CT_Call_in
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads CT Call in Pay timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void CTCallinPay()
	{
		String pass = "CT_Call_in";
		CallinPay(pass);
	}
	/*
	 * TC_DC_Call_in
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads DC Call in Pay timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void DCCallinPay()
	{
		String pass = "DC_Call_in";
		CallinPay(pass);
	}
	/*
	 * TC_NH_Call_in
	 * Created by Jeffrey 
	 * Date Created: 1/14/19
	 * Usage: Loads NH Call in Pay timesheets 
	 * @Information For the validation purpose
	 *  */  
	public void NHCallinPay()
	{
		String pass = "NH_Call_in";
		CallinPay(pass);
	}
	/*
	 * TC_RI_Call_in Created by Gandhi 
	 * Date Modified: 02/05/19 
	 * Usage: Time clock for Call in pay
	 * @Information For the validation purpose
	 */

	public void CallinPay(String timesheets) {
		String supid = "";
		String tempsupid = "";
		String employee = "", tempemp = "";
		ExcelRead RI_Call_in = new ExcelRead(sTDFileName, timesheets);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = RI_Call_in.rowCount();
		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				supid = RI_Call_in.getCellData("SupID", i);
				employee = RI_Call_in.getCellData("Employee", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) 
					{
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						Thread.sleep(1000);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						Thread.sleep(2000);
					}
					tempsupid = supid;
					// Enter User ID
					System.out.println("***");
					// Enter User ID
					try {
						oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
						setText(oUIObj.uID_xp, RI_Call_in.getCellData("SupID", i));
						selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
								"UserName entered as " + RI_Call_in.getCellData("SupID", i));
					}
					catch(Exception e)
					{
						System.out.println("No more rows to execute");
					}

					// Enter Password
					Thread.sleep(1000);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, RI_Call_in.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + RI_Call_in.getCellData("SupPass", i));
					// Thread.sleep(500);

					// Clicking on SignIn
					Thread.sleep(1000);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					// Call timesheet function
					timeSheetAK(employee);
				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String emp;
				String AKDay;
				// Loading Employee number
				employee = RI_Call_in.getCellData("Employee", i);
				if (!employee.equals(tempemp)) {
					oBrowser.findElement(By.xpath(oUIObj.uiLoadEmptxt_xp)).clear();
					setText(oUIObj.uiLoadEmptxt_xp, RI_Call_in.getCellData("Employee", i));
					oBrowser.findElement(By.xpath(oUIObj.uiLoadH_xp)).click();
					Thread.sleep(1500);
					emp = oBrowser.findElement(By.xpath(oUIObj.uiEmpH_xp)).getText();
					selectReport.ReportStep("Pass", "Employee link is displayed", "Validate Employee Name",
							"Employee Name" + emp);
					tempemp = employee;
				}
				AKDay = RI_Call_in.getCellData("Day", i);
				String overtimetype = RI_Call_in.getCellData("Overwrite_Type", i);

				List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
				List<WebElement> we3 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
				for (int j = 0; j < we1.size(); j++) {
					if (AKDay.contains(we1.get(j).getText())) {
						if (overtimetype != "") {

							we3.get(j).click();
							Thread.sleep(1000);
						
						String overwritefield1 = RI_Call_in.getCellData("Overwrite_field_1", i);
						String overwritefield2 = RI_Call_in.getCellData("Overwrite_field_2", i);
						// Clicking on Schedule Times
						Thread.sleep(500);
						oBrowser.findElement(By.xpath(oUIObj.uiearlylateschtime_xp)).click();
						Thread.sleep(1000);
						oBrowser.switchTo().frame(oBrowser.findElement(By.name("ovrPopup")));
						System.out.println("Entered into overpopup frame");
						setText(oUIObj.uistarttime_xp, overwritefield1);
						System.out.println("Entered start time");
						setText(oUIObj.uiendtime_xp, overwritefield2);
						System.out.println("Entered End time");
						Thread.sleep(1000);
						try {
							oBrowser.findElement(By.xpath(oUIObj.uisubmitbtn_xp)).click();
							Thread.sleep(2000);
							selectReport.ReportStep("Pass", "Hours Submit", "Validate Hours Submit in new window",
									"Clicked on Submit in new window");

						} catch (Exception ex) {
							System.out.println("Fail:Not clicked on submit button successfully for TC-" + i);
							selectReport.ReportStep("Fail", "Validate Submit button", "Submit should click",
									"Not clicked on submit successfully for TC-" + i);
						}
						}
						oBrowser.switchTo().defaultContent();
						cSideframe();
						oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
						selectReport.ReportStep("Pass", "Submit", "Validate Submit", "Clicked on Submit");
						Thread.sleep(2000);
						// }
						if (overtimetype.equals("SH")) {
							System.out.println(AKDay);
							Thread.sleep(2000);
							String overwritefield = RI_Call_in.getCellData("Overwrite_field_3", i);
							List<WebElement> we4 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
							if (!overwritefield.equals("")) {
								System.out.println("Entered into click on Edit button");
								System.out.println(j);

								we4.get(j).click();
							}
							Thread.sleep(2000);
							controlClick(oUIObj.uimenuEmpHoliSH_xp, "Clicked on Employee Holiday");
							Thread.sleep(1000);
							selectReport.ReportStep("Pass", "Employee Holiday click", "Validate Employee Holiday click",
									"Clicked on Employee Holiday edit");
							Thread.sleep(1000);
							// entering into over popup
							WebElement frame1 = oBrowser.findElement(By.id("ovrPopup"));
							oBrowser.switchTo().frame(frame1);
							setText(oUIObj.uiemployeeholidaysel_xp,
									RI_Call_in.getCellData("Overwrite_field_3".trim(), i));
							WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.uiemployeeholidaysel_xp));
							Thread.sleep(3000);
							TimeCodeTY.sendKeys(Keys.TAB);
							System.out.println("HOLIDAY entered successfully");
							Thread.sleep(1000);
							try {
								oBrowser.findElement(By.xpath(oUIObj.uiholidaysubmit_xp)).click();
								Thread.sleep(2000);
								System.out.println("Frame Okay");
								oBrowser.switchTo().defaultContent();
								cSideframe();
								oBrowser.findElement(By.xpath(oUIObj.uiSubmitTimeSheet_xp)).click();
								System.out.println("UI submitted");
								Thread.sleep(3000);
								System.out.println("Pass:Added holiday successfully for TC-" + i + " -" + AKDay);
								selectReport.ReportStep("Pass", "Holiday adding", "Validate Holiday",
										"Added holiday successfully for TC-" + i + " -" + AKDay);
							} catch (Exception ex) {
								System.out.println("Fail:Not added holiday successfully for TC-" + i + " -");
								selectReport.ReportStep("Fail", "Validate Holiday", "Holiday should add",
										"Not Added holiday successfully for TC-" + i + " -" + AKDay);
							}
							System.out.println(AKDay);
							Thread.sleep(5000);
						}
						String suppression = RI_Call_in.getCellData("Suppression", i);
						if (!suppression.equals("")) {
							try {
								List<WebElement> we5 = oBrowser.findElements(By.xpath(oUIObj.UIsupAll_xp));
								System.out.println("Entered into click on Edit button");
								System.out.println(j);
								String supression = "";
								supression = RI_Call_in.getCellData("Suppression", i);
								Thread.sleep(1000);
								we5.get(j+1).sendKeys(supression);
								Thread.sleep(1000);
								selectReport.ReportStep("Pass", "Guar Inelig drop down",
										"Validate 	Guar Inelig drop down",
										"Clicked on 	Guar Inelig drop down and selected" + suppression
										+ " for the TC-" + i);
								System.out.println("Slected drop down for Guar Inelig for " + AKDay);
								Thread.sleep(2000);
								oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
							} catch (Exception ex) {
								System.out.println("Fail:Not added Guar Inelig for TC-" + i + " -" + suppression);
								selectReport.ReportStep("Fail", "Validate Guar Inelig", "Guar Inelig should add",
										"Not Added Guar Inelig successfully for TC-" + i + " -" + suppression);
							}
						}
						oBrowser.switchTo().defaultContent();
						cSideframe();
						try {
							List<WebElement> we2 = oBrowser.findElements(By.xpath(oUIObj.uiaddbtns_xp));
							we2.get(j).click();
						} catch (Exception ex) {
							System.out.println("The error is" + ex.getMessage());
						}
						break;
					}
				}
				System.out.println("Entered to add clocks");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClick_xp)));
				System.out.println("Day is " +AKDay);
				if (RI_Call_in.getCellData("Clock_1", i) != "")
				{
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
				Thread.sleep(500);
				setText(oUIObj.uiAddNewClick_xp, RI_Call_in.getCellData("Clock_1", i));
				System.out.println("Entered clock 1 value");
				Thread.sleep(1000);
				oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
				System.out.println("Submitted clock 1 value");
			}
				// Enters Clock 2
				if (RI_Call_in.getCellData("Clock_2", i) != "") {
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
					setText(oUIObj.uiAddNewClick_xp, RI_Call_in.getCellData("Clock_2", i));
					System.out.println("Entered clock 2 value");
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
					System.out.println("Submitted clock 2 value");
					Thread.sleep(1000);
				}
				// Enters Clock 3
				if (RI_Call_in.getCellData("Clock_3", i) != "") {
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
					setText(oUIObj.uiAddNewClick_xp, RI_Call_in.getCellData("Clock_3", i));
					System.out.println("Entered clock 3 value");
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
					System.out.println("Submitted clock 3 value");
					Thread.sleep(3000);
				}
				// Enters Clock 4
				if (RI_Call_in.getCellData("Clock_4", i) != "") {
					oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
					setText(oUIObj.uiAddNewClick_xp, RI_Call_in.getCellData("Clock_4", i));
					System.out.println("Entered clock 4 value");
					Thread.sleep(500);
					oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
					System.out.println("Submitted clock 4 value");
					Thread.sleep(3000);
				}
				if (RI_Call_in.getCellData("Expected", i).contains("|")) {
					String[] expectime = FilterGuaraRegularorOTTime(RI_Call_in.getCellData("Expected", i));
					String actregtime = "";
					String actguarOTtime = "";
					System.out.println("***" + expectime[0]);
					System.out.println("***" + expectime[1].replace("|", ""));
					System.out.println("***" + expectime[2]);
					oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
					Thread.sleep(3000);
					oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					try {
						String actguartime = "";
						if (expectime[2].contains("REG")) {
							actregtime = oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
							actguartime = oBrowser.findElement(By.xpath(oUIObj.uiguarregtime_xp)).getText();

							if (actregtime.equals(expectime[0])) {
								System.out.println(
										"Pass:Successfully displayed regular time-" + actregtime + "  for the TC-" + i);
								selectReport.ReportStep("Pass", "Validate regular time",
										"Added regular time should display",
										"Regular time displayed successfully-" + actregtime + " for the TC-" + i);
							} else {
								System.out.println("Fail:Not displayed regular time. Please verify-" + actregtime
										+ "  for the TC-" + i);
								selectReport.ReportStep("Fail", "Validate regular time",
										"Added regular time should display",
										"Regular time not displayed as per test data.Please verify." + " for the TC-"
												+ i);
							}
							if (actguartime.equals(expectime[1].replace("|", ""))) {
								System.out.println("Pass:Successfully displayed GUAR time-" + expectime[2] + actguartime
										+ "  for the TC-" + i);
								selectReport.ReportStep("Pass", "Validate GUAR",
										"Added GUAR " + expectime[2] + " should display", "GUAR" + expectime[2]
												+ "  displayed successfully-" + actguartime + "  for the TC-" + i);
							} else {
								System.out.println("Fail:GUAR time-" + expectime[2]
										+ " not displayed as per test data.please verify." + "  for the TC-" + i);
								selectReport.ReportStep("Fail", "Validate GUAR",
										"Added GUAR time-" + expectime[2] + " should display",
										"GUAR time-" + expectime[2] + " not displayed as per test data.please verify."
												+ "  for the TC-" + i);
							}
						}
						//
						else if (expectime[2].contains("OT")) {
							actregtime = oBrowser.findElement(By.xpath(oUIObj.uiWrkOT_xp)).getText();
							actguarOTtime = oBrowser.findElement(By.xpath(oUIObj.uiGuarOT_xp)).getText();
							if (actregtime.equals(expectime[0])) {
								System.out.println(
										"Pass:Successfully displayed WRK OT time-" + actregtime + "  for the TC-" + i);
								selectReport.ReportStep("Pass", "Validate WRK OT time",
										"Added WRK OT time should display",
										"WRK OT time displayed successfully-" + actregtime + " for the TC-" + i);
							} else {
								System.out.println("Fail:Not displayed WRK OT time. Please verify-" + actregtime
										+ "  for the TC-" + i);
								selectReport.ReportStep("Fail", "Validate WRK OT time",
										"Added WRK OT time should display",
										"WRK OT time not displayed as per test data.Please verify." + " for the TC-"
												+ i);
							}
							if (actguarOTtime.equals(expectime[1])) {
								System.out.println("Pass:Successfully displayed GUAR OT time-" + expectime[1]
										+ actguarOTtime + "  for the TC-" + i);
								selectReport.ReportStep("Pass", "Validate GUAR OT Time",
										"Added GUAR OT Time " + expectime[1] + " should display",
										"GUAR OT Time" + expectime[2] + "  displayed successfully-" + actguartime
										+ "  for the TC-" + i);
							} else {
								System.out.println("Fail:GUAR OT Time-" + expectime[1]
										+ " not displayed as per test data.please verify." + "  for the TC-" + i);
								selectReport.ReportStep("Fail", "Validate GUAR OT Time",
										"Added GUAR OT Time-" + expectime[1] + " should display",
										"GUAR OT Time-" + expectime[2]
												+ " not displayed as per test data.please verify." + "  for the TC-"
												+ i);
							}
						}

					} catch (Exception ex1) {
						System.out.println("Not displayed the time after added.Please verify manually");
						selectReport.ReportStep("Fail", "Validate Regular/OT and GUAR",
								"Added regular/OT and GUAR should display",
								"Regular and OT not displayed successfully");
					}
				} else {
					String[] expectime = FilteronlyRegularorOTTime(RI_Call_in.getCellData("Expected", i));
					System.out.println("***" + expectime[0]);
					System.out.println("***" + expectime[1]);
					oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
					Thread.sleep(3000);
					oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					try {
						if (expectime[1].contains("REG")) {
							String actregtime = oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
							if (actregtime.equals(expectime[0])) {
								System.out.println(
										"Pass:Successfully displayed regular time-" + actregtime + "  for the TC-" + i);
								selectReport.ReportStep("Pass", "Validate regular time",
										"Added regular time should display",
										"Regular time displayed successfully-" + actregtime + " for the TC-" + i);
							} else {
								System.out.println("Fail:Not displayed regular time. Please verify-" + actregtime
										+ "  for the TC-" + i);
								selectReport.ReportStep("Fail", "Validate regular time",
										"Added regular time should display",
										"Regular time not displayed as per test data.Please verify." + " for the TC-"
												+ i);
							}
						} else if (expectime[1].contains("OT")) {
							String actottime = oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
							if (actottime.equals(expectime[0])) {
								System.out.println(
										"Pass:Successfully displayed OT time-" + actottime + "  for the TC-" + i);
								selectReport.ReportStep("Pass", "Validate OT", "Added OT  should display",
										"OT  displayed successfully-" + actottime + "  for the TC-" + i);
							} else {
								System.out.println(
										"Fail:OT not displayed as per test data.please verify." + "  for the TC-" + i);
								selectReport.ReportStep("Fail", "Validate OT", "Added OT should display",
										"OT not displayed as per test data.please verify." + "  for the TC-" + i);
							}
						}
					} catch (Exception ex) {
						System.out.println(
								"Fail:Not displayed the time after added.Please verify manually" + "  for the TC-" + i);
						selectReport.ReportStep("Fail", "Validate Regular and OT",
								"Added regular and OT should display",
								"Regular and OT not displayed successfully" + "  for the TC-" + i);
					}
				}
				// Deleting added time
				Deletealledits();
			} catch (Exception e) {
				System.out.println("Fail: Cannot input Employee Clock-in information" + " for the TC-" + i);
				selectReport.ReportStep("Fail", "Validate Regular and OT or timecode for the Daily",
						"Time should add for daily",
						"Time not added for daily.Please verify manually" + "  for the TC-" + i);
				e.printStackTrace();
			}
		}}
	/*
	 * TC_Thankyou_MVP Created by Gandhi D
	 * ate Created: 12/01/19 
	 * Usage: Thank you day and MVP day entering into timesheets
	 * @Information For the validation purpose
	 */

	public void Thankyou_MVP() throws Exception 
	{
		// String tcode="";
		String supid = "";
		String tempsupid = "";
		String employee = "", tempemp = "";
		String TYMDay = "";
		String TCN = "";
		ExcelRead Thankyou_MVP = new ExcelRead(sTDFileName, "Thankyou_MVP");
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = Thankyou_MVP.rowCount();

		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				supid = Thankyou_MVP.getCellData("SupID", i);
				employee = Thankyou_MVP.getCellData("Employee", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						Thread.sleep(500);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					TCN = Thankyou_MVP.getCellData("TEST#", i);
					tempsupid = supid;
					System.out.println("***");
					// Enter User ID
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, Thankyou_MVP.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + Thankyou_MVP.getCellData("SupID", i));
					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, Thankyou_MVP.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + Thankyou_MVP.getCellData("SupPass", i));
					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					// Clicking on TimeSheetFrame
					timeSheetAK(employee);
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				}
				TCN = Thankyou_MVP.getCellData("TEST#", i);
				String emp;
				// TimeSheet selection
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				employee = Thankyou_MVP.getCellData("Employee", i);
				System.out.println("**" + employee);
				if (!employee.equals(tempemp)) {
					oBrowser.findElement(By.xpath(oUIObj.uiLoadEmptxt_xp)).clear();
					setText(oUIObj.uiLoadEmptxt_xp, Thankyou_MVP.getCellData("Employee", i));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.uiLoadH_xp)).click();
					Thread.sleep(1500);
					emp = oBrowser.findElement(By.xpath(oUIObj.uiEmpH_xp)).getText();
					selectReport.ReportStep("Pass", "Employee link is displayed", "Validate Employee Name",
							"Employee Name" + emp);
					tempemp = employee;
				} else {
					emp = oBrowser.findElement(By.xpath(oUIObj.uiEmpH_xp)).getText();
					selectReport.ReportStep("Pass", "Employee link is displayed", "Validate Employee Name",
							"Employee Name" + emp);
					tempemp = employee;
				}
				String Date = Thankyou_MVP.getCellData("Date", i);
				if (!Date.equals("")) {
					try {
						oBrowser.findElement(By.xpath(oUIObj.uIEmpDate_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						WebElement frame1 = oBrowser.findElement(By.id("wb_expandableframe1"));
						oBrowser.switchTo().frame(frame1);
						System.out.println("Entered into sub frame in content frame");
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.UIStart_xp));
						Actions act = new Actions(oBrowser);
						act.click(TimeCodeTY).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
						System.out.println("Start date cleared");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						System.out.println("++Day+++" + Date);
						oBrowser.findElement(By.xpath(oUIObj.UIStart_xp)).sendKeys(Date);
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						TimeCodeTY.sendKeys(Keys.TAB);
						WebElement TimeCodeTY1 = oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp));
						Actions act1 = new Actions(oBrowser);
						act1.click(TimeCodeTY1).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
						//oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp)).clear();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						System.out.println("End date cleared");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp)).sendKeys(Date);
						System.out.println("date range entered successfully");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.UILoadinSubFrm_xp)).click();
						Thread.sleep(1500);
					} catch (Exception ex) {
						System.out.println("Fail:Not added Strat and End dates successfully for TC-" + TCN + " -" + Date);
						selectReport.ReportStep("Fail", "Validate Strat and End dates",
								"Strat and End dates should add",
								"Not Added Strat and End dates successfully for TC-" + TCN + " -" + Date);
					}
				}
				// Clicking on pencil edit
				String AKDay = Thankyou_MVP.getCellData("Day", i);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
				for (int j = 0; j < we1.size(); j++) {
					System.out.println(AKDay);

					if (AKDay.contains(we1.get(j).getText())) {
						System.out.println(AKDay);
						System.out.println("Entered into pencil edit click button");
						try {
							List<WebElement> we2 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
							System.out.println("Size of we2 " + we2.size());
							we2.get(j).click();
						} catch (Exception ex) {
							System.out.println(ex.getMessage());
						}
					}
				}
				// Selecting work premium option
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				controlClick(oUIObj.uimenuWorkPre_xp, "Clicked on Work premium");
				
				Thread.sleep(500);
				String overtimetype = Thankyou_MVP.getCellData("Overwrite_Type", i);
				TYMDay = Thankyou_MVP.getCellData("Premium_TimeCode", i);
				if (overtimetype != "") {
					oBrowser.switchTo().defaultContent();
					WebElement frame1 = oBrowser.findElement(By.id("contentFrame"));
					oBrowser.switchTo().frame(frame1);
					WebElement frame2 = oBrowser.findElement(By.id("ovrPopup"));
					oBrowser.switchTo().frame(frame2);
					oBrowser.findElement(By.xpath(oUIObj.uiHoursinoverride_xp)).click();
					setText(oUIObj.uiHoursinoverride_xp, Thankyou_MVP.getCellData("Premium_Hours".trim(), i));
					oBrowser.findElement(By.xpath(oUIObj.uiTimecodeWorkPremium_xp)).click();
					Thread.sleep(1500);
					WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.uiTimecodeWorkPremium_xp));
					setText(oUIObj.uiTimecodeWorkPremium_xp, Thankyou_MVP.getCellData("Premium_TimeCode".trim(), i));
					Thread.sleep(1500);
					TimeCodeTY.sendKeys(Keys.TAB);
					System.out.println("TYDAY entered successfully");
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					try {
						oBrowser.findElement(By.xpath(oUIObj.uiSubmitinFrame_xp)).click();
						System.out.println("Submitted timesheet in frames");
						Thread.sleep(1500);
						oBrowser.switchTo().defaultContent();
						cSideframe();
						oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						System.out.println("Submitted timesheet in UI");
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						System.out.println("Pass:Added TCode successfully for TC-" + TCN + " -" + TYMDay);
						
					} catch (Exception ex) {
						System.out.println("Fail:Not added TCode successfully for TC-" + TCN + " -" + TYMDay);
						selectReport.ReportStep("Fail", "Validate TCode", "TCode should add",
								"Not Added TCode successfully for TC-" + TCN + " -" + TYMDay);
					}
				}
				if (Thankyou_MVP.getCellData("Expected", i).contains("REG"))
				{
					String[] expectime = FilterThankyouMVP(Thankyou_MVP.getCellData("Expected", i));
					System.out.println("***" + expectime[0]);
					System.out.println("***" + expectime[1]);
					// oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
					Thread.sleep(1500);
					oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					try {
						if (expectime[1].contains("MVPDAY")) {
							String ACTMVPTIME = oBrowser.findElement(By.xpath(oUIObj.uiMVPtime_xp)).getText();
							if (ACTMVPTIME.equals(expectime[0])) {
								System.out.println("Pass:Successfully displayed MVP time-" + ACTMVPTIME + " for the TC-" + TCN);
								selectReport.ReportStep("Pass", "Validate MVP time", "Added THANKYOU should display","MVP time displayed successfully-" + ACTMVPTIME + " for the TC-" + TCN);
							} else {
								System.out.println("Fail:MVP time not displayed as per test data.please verify."+ " for the TC-" + TCN);
								selectReport.ReportStep("Fail", "Validate MVP time", "Added MVP time should display","MVP time not displayed as per test data.please verify." + " for the TC-" + TCN);
							}
						}
						if (expectime[1].contains("THANKYOUDAY")) {
							String ACTTHANKTIME = oBrowser.findElement(By.xpath(oUIObj.uiThanktime_xp)).getText();
							if (ACTTHANKTIME.equals(expectime[0])) {
								System.out.println("Pass:Successfully displayed THANK YOU time-" + ACTTHANKTIME + " for the TC-" + TCN);
								selectReport.ReportStep("Pass", "Validate THANK YOU time", "Added THANKYOU should display","THANK YOU time displayed successfully-" + ACTTHANKTIME + " for the TC-" + TCN);
							} else {
								System.out.println("Fail:THANK YOU time not displayed as per test data.please verify."+ " for the TC-" + TCN);
								selectReport.ReportStep("Fail", "Validate THANK YOU time", "Added THANK YOU time should display","THANK YOU time not displayed as per test data.please verify." + " for the TC-" + TCN);
							}
						}
					} catch (Exception ex) {
						System.out.println("Fail:Not displayed the time after added.Please verify manually" + " for the TC-" + TCN);
						selectReport.ReportStep("Fail", "Validate VAC or SICK or MVP Time","Added VAC or SICK or MVP Time should display","VAC or SICK and MVP time not displayed successfully" + " for the TC-" + TCN);
					}
				}
				if (Thankyou_MVP.getCellData("Expected", i).contains("Balance"))
				{
					String Expected = Thankyou_MVP.getCellData("Expected", i);
					String strJavaScript = "var element = arguments[0];"
							+ "var mouseEventObj = document.createEvent('MouseEvents');"
							+ "mouseEventObj.initEvent( 'mouseover', true, true );" + "element.dispatchEvent(mouseEventObj);";
					oBrowser.findElement(By.xpath(oUIObj.UIissue_xp)).click();
					WebElement element = oBrowser.findElement(By.cssSelector(oUIObj.UIissuemessage_xp));
					((JavascriptExecutor) oBrowser).executeScript(strJavaScript, element);
					String toolTipTxt1 = element.getText();
					System.out.println("txt is " +toolTipTxt1);
					System.out.println("Exp txt is "+Expected);

					if (Expected.equalsIgnoreCase(toolTipTxt1)) {

						System.out.println("Pass:Successfully displayed Error message -" + toolTipTxt1 + " for the TC-" + TCN);
						selectReport.ReportStep("Pass", "Validate Error message", "Added Error message should display","Error message displayed successfully-" + toolTipTxt1 + " for the TC-" + TCN);
					}
					else {
						System.out.println("Fail: error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
						selectReport.ReportStep("Fail", "Validate Error message", "Added Error message should display","Error message not displayed as per test data.please verify." + " for the TC-" + TCN);
					}
				}
				DeletealleditsWP();
			}
			catch (Exception e) {
				System.out.println("Fail: Cannot input Employee THANKYOU DAY" + " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "Validate Regular and THANKYOU DAY", "Time should add THANKYOU DAY",
						"THANKYOU DAY not added THANKYOU DAY.Please verify manually" + " for the TC-" + TCN);
			}
		}
	}


	/*
	 * TC_Moving to Maintanance screen
	 * Created by Gandhi 
	 * Date Created: 02/14/19
	 * Usage: Navigating to Maintanance screen
	 * @Information For the validation purpose
	 *  */ 


	public void MaintenanceEmp() throws Exception
	{
		try{

			if(oBrowser.findElement(By.xpath(oUIObj.uiMaintenance_xp)).isDisplayed())
			{
				WebElement Maintenance=oBrowser.findElement(By.xpath(oUIObj.uiMaintenance_xp));
				highlightElement(Maintenance);
				oBrowser.findElement(By.xpath(oUIObj.uiMaintenance_xp)).click();
				System.out.println("Entered into Maintenance screen");
				//Enter Employee number
				Thread.sleep(1500);
				cSideframe();
				oBrowser.findElement(By.xpath(oUIObj.uiMassEditButn_xp)).click();
				System.out.println("Clicked on Mass Edit button");
				Thread.sleep(1500);
				oBrowser.findElement(By.xpath(oUIObj.uiMassEditCrtnButn_xp)).click();
				System.out.println("Clicked on Mass Edit Creation button");

				selectReport.ReportStep("Pass", "Mass Edit", "Validate Mass Edit","Clicked on Mass Edit Button");
			}
		}catch(Exception e){
			System.out.println("Mass Edit Button is not displayed");
		}
	}

	/*
	 * TC_Mass_Edit
	 * Created by Gandhi 
	 * Date Created: 1/22/19
	 * Usage: Loads Mass Edit for employees
	 * @Information For the validation purpose
	 *  */  
	public void CTMassEdit() throws Throwable
	{
		String pass = "Mass_edit";
		MassEdit(pass);
	}

	/*
	 * Filter for MassEdit 
	 * Created by Gandhi 
	 * Date Created: 01/29/18 Usage:
	 * Filter expected time
	 * 
	 * @Information For the validation purpose
	 */
	public String[] FilterMassEdit(String val) {
		String[] tim = new String[3];
		String[] s1 = val.split(" ");
		tim[0] = s1[0];
		tim[1]=s1[2];

		return tim;
	}
	/*
	 * TC_Mass_Edit Created by Gandhi 
	 * Date Created: 01/29/18 
	 * Usage: Mass Edit for employees from maintanence screen
	 * employees in Maintenance
	 * @Information For the validation purpose
	 */
	public void MassEdit(String Maintenance) throws Throwable {
		String supid = "";
		String tempsupid = "";
		String employee = "";
		ExcelRead Mass_edit = new ExcelRead(sTDFileName, Maintenance);
		// WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = Mass_edit.rowCount();
		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				supid = Mass_edit.getCellData("SupID", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid = supid;
					// Enter User ID
					System.out.println("***");
					// Enter User ID
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, Mass_edit.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In","UserName entered as " + Mass_edit.getCellData("SupID", i));
					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, Mass_edit.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In","Password entered as " + Mass_edit.getCellData("SupPass", i));
					// Thread.sleep(500);
					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					// Call Maintenance function
					MaintenanceEmp();
				}
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String Name;
				String Hours;
				String Starttime;
				String TCN = "";
				String EndTime;
				String print1;
				String print2;
				String Expected;
				Expected = Mass_edit.getCellData("Expected", i);
				String frequenc;
				String AddEdit;
				boolean AddEditFlag = false;
				boolean employeeFlag = false;
				boolean nameFlag = false;
				boolean addevntexception = false;
				TCN = Mass_edit.getCellData("TEST#", i);
				AddEdit = Mass_edit.getCellData("Add_Edit", i);
				employee = Mass_edit.getCellData("Employee", i);
				Name = Mass_edit.getCellData("Name", i);
				frequenc = Mass_edit.getCellData("Frequenc", i);
				Starttime = Mass_edit.getCellData("Start Time", i);
				EndTime = Mass_edit.getCellData("End Time", i);
				System.out.println("Employee to select" + employee);
				Hours = Mass_edit.getCellData("Hours", i);
				String Expected1 = Mass_edit.getCellData("Expected", i);
				if (employee.equals("ALL") || !(employee.equals(""))) {
					try {
						oBrowser.switchTo().defaultContent();
						oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
						System.out.println("Entered into content frame");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						System.out.println("Entered employee edit name");
						if (!(employee.equals("ALL") && !(employee.equals("")))) {
							oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAllinput_xp)).sendKeys(Mass_edit.getCellData("Employee", i));
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						}
						oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAll_xp)).click();
						System.out.println("Clicked on employee All button");
						selectReport.ReportStep("Pass", "All", "Validate All","Clicked on All Button in Employee Maintenance Screen");
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						employeeFlag = true;
					}
					catch (Exception e) {
						System.out.println("Fail: Cannot Enter Employee Edit/Unable to click on Employee all button"+ " for the TC-" + TCN);
						selectReport.ReportStep("Fail", "Employee Edit and click on Employee all","Validate Employee Edit and click on Employee all button","should click on Employee Edit and all button" + " for the TC-" + TCN);
					}
				}
				if (!Name.equals("")) {
					try {
						nameFlag = true;
						oBrowser.findElement(By.xpath(oUIObj.uiEmployeeEdit_xp)).clear();
						System.out.println("Edit text box cleared");
						oBrowser.findElement(By.xpath(oUIObj.uiEmployeeEdit_xp))
						.sendKeys(Mass_edit.getCellData("Name", i));
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						selectReport.ReportStep("Pass", "Mass Edit text box", "Validate Mass Edit name","Entered Mass Edit name As" + Name);
					} catch (Exception e) {
						System.out.println("Fail: Cannot input Name in ! equals Null scenario" + " for the TC-" + TCN);
						selectReport.ReportStep("Fail", "Mass Edit text box", "Validate Employee Edit name","should be enter Mass Edit Name Cannot enter MassEdit Name As" + Name);
					}
				}
				if (!AddEdit.equals("")) {
					AddEditFlag = true;
					new Select(oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAdEd_xp))).selectByVisibleText(Mass_edit.getCellData("Add_Edit", i));
					System.out.println("Selected drop down for Add Edits");
					oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					selectReport.ReportStep("Pass", "Add Edit Drop down", "Validate Add Edit","Selected Add Edit as" + AddEdit);
					if ((AddEdit.equals("Mass Edit Work Premium"))) {
						if (Hours.equals(""))
						{
							try {
								ArrayList<String> tabs3 = new ArrayList<String>(oBrowser.getWindowHandles());
								oBrowser.switchTo().window(tabs3.get(1));
								System.out.println("Entered into new window and handling");
								oBrowser.manage().window().maximize();
								oBrowser.findElement(By.xpath(oUIObj.uiEmpSchdTymSub_xp)).click();
								Thread.sleep(1500);
								System.out.println("Clicked on Submit in new window");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								System.out.println("Entered into failed conditionT");
								Thread.sleep(1500);
								Alert alert1 = oBrowser.switchTo().alert();
								System.out.println("Entered into Popup");
								Thread.sleep(1500);
								print2 = alert1.getText();
								System.out.println("**" + print2);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								alert1.accept();
								oBrowser.switchTo().window(tabs3.get(0));
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
								System.out.println("Entered into content aftered clocks added frame");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								if (Expected.equalsIgnoreCase(print2)) {
									System.out.println("Pass:Successfully displayed Error message-" + print2 + " for the TC-" + i);
									selectReport.ReportStep("Pass", "Error popup in Maintenance main screen","Validate Error popup message in Maintenance main screen","Error message displayed successfully-" + print2 + " for the TC-" + TCN);
								} else {
									System.out.println("Fail:Error message not displayed as per test data.please verify."+ " for the TC-" + i);
									selectReport.ReportStep("Fail", "Error popup in Maintenance main screen","Added Error message should display","Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
								}
							} catch (Exception e) {
								System.out.println("Fail: Cannot click on submit button " + " for the TC-" + TCN);
								selectReport.ReportStep("Fail", "Submit", "Validate Submit button new window","Failed in clicking on submit.Please verify manually" + " for the TC-" + TCN);
							}
							addevntexception = true;
						}
						if (!Hours.equals("")) {
							try {
								ArrayList<String> tabs2 = new ArrayList<String>(oBrowser.getWindowHandles());
								oBrowser.switchTo().window(tabs2.get(1));
								System.out.println("Entered into new window and handling");
								oBrowser.manage().window().maximize();
								oBrowser.findElement(By.xpath(oUIObj.uiEmployHours_xp)).sendKeys(Mass_edit.getCellData("Hours", i));
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.uiEmployHol_xp));
								oBrowser.findElement(By.xpath(oUIObj.uiEmployHol_xp)).sendKeys(Mass_edit.getCellData("TimeCode", i));
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								TimeCodeTY.sendKeys(Keys.TAB);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.uiEmpSchdTymSub_xp)).click();
								Thread.sleep(1500);
								oBrowser.switchTo().window(tabs2.get(0));
								System.out.println("Clicked on Submit in new window");
								selectReport.ReportStep("Pass", "Entering hours in new popup", "Validate Hours","Enter Hours/Holiday" + " for the TC-" + TCN + " -" + Hours);
							} catch (Exception e) {
								selectReport.ReportStep("Fail", "Hours", "Validate Hours in new popup window","Failed in Hours/Holiday entering.Please verify manually" + " for the TC-" + TCN);
							}
						}
					}
					if ((AddEdit.equals("Mass Edit Sched Times"))) {
						try {
							ArrayList<String> tabs2 = new ArrayList<String>(oBrowser.getWindowHandles());
							oBrowser.switchTo().window(tabs2.get(1));
							System.out.println("Entered into new window and handling");
							oBrowser.manage().window().maximize();
							// switchtowindow();
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							if (!Starttime.equals("") || !EndTime.equals("")) {
								boolean startFlag = false;
								boolean endFlag = false;
								if (!Starttime.equals("")) {
									oBrowser.findElement(By.xpath(oUIObj.uiEmpSchdStrtTym_xp)).sendKeys(Mass_edit.getCellData("Start Time".trim(), i));
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									if (!Starttime.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
										System.out.println("start flag"); //me change
										startFlag = true;
									}
								}
								if ((!EndTime.equals(""))) {
									oBrowser.findElement(By.xpath(oUIObj.uiEmpSchdEndTymTym_xp)).sendKeys(Mass_edit.getCellData("End Time".trim(), i));
									if (!EndTime.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
										endFlag = true;
									}
								}
								oBrowser.findElement(By.xpath(oUIObj.uiEmpSchdTymSub_xp)).click();
								System.out.println("Clicked on Submit in new window");
								//oBrowser.switchTo().window(tabs2.get(0));
								System.out.println("Entered start and end hours1");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								//addevntexception = true;
								if (startFlag || endFlag) {
									System.out.println("in start and end flag");
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									String print3 = "";
									System.out.println("*1*"+print3);
									System.out.println("Entered into failed condition");
									print3 = oBrowser.switchTo().alert().getText();
									System.out.println("*1*" + print3);
									oBrowser.switchTo().alert().accept();
									System.out.println("**" + print3);
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									//alert.accept();
									oBrowser.switchTo().window(tabs2.get(0));
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
									System.out.println("Entered into content aftered clocks added frame");
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									if (Expected.equalsIgnoreCase(print3)) {
										System.out.println("Pass:Successfully displayed Error message-" + print3+ " for the TC-" + TCN);
										selectReport.ReportStep("Pass", "Error popup in Maintenance main screen","Validate Error popup message in Maintenance main screen","Error message displayed successfully-" + print3 + " for the TC-" + TCN);
									} else {
										System.out.println("Fail:Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
										selectReport.ReportStep("Fail", "Error popup in Maintenance main screen","Added Error message should display","Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
									}
									addevntexception = true;
								}
								System.out.println("Entered into");
								oBrowser.switchTo().window(tabs2.get(0));
								oBrowser.switchTo().defaultContent();
								oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
								System.out.println("Entered into content frame");
							} else if (Starttime.equals("") && EndTime.equals("")) {
								System.out.println("Clicking Submit on new windows");
								oBrowser.findElement(By.xpath(oUIObj.uiEmpSchdTymSub_xp)).click();
								System.out.println("Clicked on Submit in new window");
								System.out.println("Entered start and end hours");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								System.out.println("Entered into failed condition");
								print1 = oBrowser.switchTo().alert().getText();
								oBrowser.switchTo().alert().accept();
								System.out.println("**" + print1);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								oBrowser.switchTo().window(tabs2.get(0));
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
								System.out.println("Entered into content aftered clocks added frame");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								if (Expected.equalsIgnoreCase(print1)) {
									System.out.println("Pass:Successfully displayed Error message-" + print1 + " for the TC-" + TCN);
									selectReport.ReportStep("Pass", "Error popup in Maintenance main screen","Validate Error popup message in Maintenance main screen","Error message displayed successfully-" + print1 + " for the TC-" + TCN);
								} else {
									System.out
									.println("Fail:Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
									selectReport.ReportStep("Fail", "Error popup in Maintenance main screen","Added Error message should display","Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
								}
								addevntexception = true;
							}
							selectReport.ReportStep("Pass", "Add Edit", "Validate Add Edit","Selected Add Edit as" + AddEdit);
						} catch (Exception e) {
							System.out.println("Fail: Cannot Enter Mass sced times to click on Employee all button"+ " for the TC-" + TCN);
							selectReport.ReportStep("Fail", "Add Edit", "Validate Add Edit","should be select value for AddEdit Cannot Selected Add Edit as" + AddEdit);
						}
					}
				}
				if (frequenc.equals("Daily")) {
					oBrowser.switchTo().defaultContent();
					oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
					System.out.println("Entered into content frame");
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					new Select(oBrowser.findElement(By.xpath(oUIObj.uiEmployeeFreq_xp))).selectByVisibleText(Mass_edit.getCellData("Frequenc", i));
					selectReport.ReportStep("Pass", "Frequency drop down", "Validate Frequency drop down","Clicked on Frequency drop down and selected");
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					System.out.println("Slected drop down for frequenc");
					oBrowser.findElement(By.xpath(oUIObj.uiEmployeeStrtDate_xp)).clear();
					System.out.println("Start date cleared");
					String StartDate = Mass_edit.getCellData("Start Date", i);
					System.out.println("++Start+++" + StartDate);
					oBrowser.findElement(By.xpath(oUIObj.uiEmployeeStrtDate_xp)).sendKeys(Mass_edit.getCellData("Start Date", i));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.uiEmployeeEndDate_xp)).clear();
					System.out.println("End date cleared");
					oBrowser.findElement(By.xpath(oUIObj.uiEmployeeEndDate_xp)).sendKeys(Mass_edit.getCellData("End Date", i));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				}
				System.out.println("Getting into addevent"+addevntexception);
				if (!addevntexception) {
					if ((nameFlag && employeeFlag && AddEditFlag)) {
						System.out.println("in final submit");
						oBrowser.switchTo().defaultContent();
						oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
						System.out.println("Entered into content frame");
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.uiEmpSchdTymSub_xp)).click();
						System.out.println("in submit button click " + i);
						System.out.println("Clicked on Submit button");
						Thread.sleep(1500);
						System.out.println("Enter into Timesheets");
						oBrowser.switchTo().defaultContent();
						timeSheetAK(employee);
						System.out.println("Entered into Timesheets Tab");
						oBrowser.findElement(By.xpath(oUIObj.uiLoadEmptxt_xp)).clear();
						if(!employee.equals("ALL"))
						{
							setText(oUIObj.uiLoadEmptxt_xp, Mass_edit.getCellData("Employee", i));
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						}
						oBrowser.findElement(By.xpath(oUIObj.uiLoadH_xp)).click();
						Thread.sleep(1500);
						//for expected
						System.out.println("Entered into filter");
						String[] expectime = FilterMassEdit(Expected);
						System.out.println("Expected stored in string ");
						String VacHolTime = "";
						System.out.println("***" + expectime[0]);
						System.out.println("***" + expectime[1]);
						// oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
						Thread.sleep(1500);
						oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						VacHolTime = oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
						try {
							if (VacHolTime.equals(expectime[0])) {
								System.out.println("Pass:Successfully displayed time afer added -" + VacHolTime + " for the TC-" + TCN);
								selectReport.ReportStep("Pass", "Comparing Vac time", "Validate vac time","Added Time message displayed successfully-" + VacHolTime + " for the TC-" + TCN);
								oBrowser.switchTo().defaultContent();
							} 
							else 
							{
								System.out.println("Fail:Error Time not displayed as per test data.please verify."+ " for the TC-" + TCN);
								selectReport.ReportStep("Fail", "Vac Time", "Added Time should display","Time not displayed as per test data.please verify." + " for the TC-" + TCN);
								oBrowser.switchTo().defaultContent();
							}
							// MaintenanceEmp();
						}
						catch (Exception e) {
							System.out.println("Fail: in code summary and Expected Validation" + " for the TC-" + TCN);
							selectReport.ReportStep("Fail", "Expected and UI code summary","Should validate code summary and Expected","Failed to update mass edit.Please verify manually" + " for the TC-" + TCN);
							oBrowser.switchTo().defaultContent();
						}
						oBrowser.switchTo().defaultContent();
						if(oBrowser.findElement(By.xpath(oUIObj.uiMaintenance_xp)).isDisplayed()) {
							WebElement Maintenance1 = oBrowser.findElement(By.xpath(oUIObj.uiMaintenance_xp));
							highlightElement(Maintenance1);
							oBrowser.findElement(By.xpath(oUIObj.uiMaintenance_xp)).click();
							System.out.println("Entered into Maintenance screen");
							//Enter Employee number
							Thread.sleep(1500);
							cSideframe();
							oBrowser.findElement(By.xpath(oUIObj.uiMassEditButn_xp)).click();
							System.out.println("Clicked on Mass Edit button");
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.uiMassEditHstryButn_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							oBrowser.switchTo().defaultContent(); 
							oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));	
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.uiEmployeesearch_xp)).sendKeys(Mass_edit.getCellData("Name", i));
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.UIFindEmp_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							if (! oBrowser.findElement(By.xpath(oUIObj.UIMasschkBoxEmp_xp)).isSelected()){
								oBrowser.findElement(By.xpath(oUIObj.UIMasschkBoxEmp_xp)).click();
							}
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.UIundoEmp_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							System.out.println("deleted/Undo previous edits Succesfully");
						}
					}
					else {
						System.out.println("in final submit else");
						oBrowser.findElement(By.xpath(oUIObj.uiEmpSchdTymSub_xp)).click();
						System.out.println("Captured alert message");
						try {
							System.out.println("Captured expect message");
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							String print = oBrowser.switchTo().alert().getText();
							oBrowser.switchTo().alert().accept();
							System.out.println("**" + Expected1);
							if (Expected1.equalsIgnoreCase(print)) {
								System.out.println("Pass:Successfully displayed Error message-" + print + " for the TC-" + TCN);
								selectReport.ReportStep("Pass", "Validate Error message","Added Error message should display","Error message displayed successfully-" + print + " for the TC-" + TCN);
							} else {
								System.out.println("Fail:Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
								selectReport.ReportStep("Fail", "Validate Error message","Added Error message should display","Error message not displayed as per test data.please verify." + " for the TC-"+ TCN);
							}
						} catch (Exception e) {

							System.out.println("Fail:cannot validate alert message" + " for the TC-" + TCN);
							selectReport.ReportStep("Fail", "Validate alert message", "","Failed to validatate alert message" + " for the TC-" + TCN);
							oBrowser.switchTo().defaultContent();
						}
					}
				}
				System.out.println("Pass: successfully completed massedit" + " for the TC-" + TCN);
				selectReport.ReportStep("Pass", "Validate Mass Edit", "","Successfully update mass edit.Please verify manually" + " for the TC-" + TCN);	
			} catch (Exception e) {
				System.out.println("Fail: Cannot input Employee information" + " for the TC-" + i);
				selectReport.ReportStep("Fail", "Validate Mass Edit", "","Failed to update mass edit.Please verify manually" + " for the TC-" + i);
				oBrowser.switchTo().defaultContent();
			}
			MaintenanceEmp();
		}
	}

	/*
	 * TC_filter for Sick Accural Created by Gandhi 
	 * Date Created: 02/14/19
	 * Usage:- For storing expected from Excel
	 * @Information For the validation purpose
	 */

	public String[] FilterSick(String val) {
		String[] tim = new String[3];
		String[] s1 = val.split(" ");
		tim[0] = s1[0];
		tim[1] = s1[1];
		return tim;
	}


	/*
	 * TC_Adding Sick Accural scenarios Created by Gandhi 
	 * Date Created: 02/04/19
	 * Usage:Sick Accural Time clock
	 * @Information For the validation purpose
	 */

	public void PSL() throws Throwable {
		String pass = "ProtectedSick";
		SickAccurualTC(pass);
	}



	// Sick_Accrual
	public void SickAccurualTC(String PSL) throws Throwable

	{

		String supid = "";
		String tempsupid = "";
		String Emp = "";
		String Calcgrp = "";
		String state = "";
		String country = "";
		String city = "";
		String exp = "";
		String TCN = "";
		boolean ProtectedFlag = false;

		// String employee = "";
		ExcelRead ProtectedSick = new ExcelRead(sTDFileName, PSL);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);

		// Validate if Timesheet link exist
		// Loops through each row in excel sheet

		int inofRows = ProtectedSick.rowCount();
		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				TCN = ProtectedSick.getCellData("TEST#", i);
				supid = ProtectedSick.getCellData("SupID", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid = supid;
					// Enter User ID
					System.out.println("***");
					// Enter User ID
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, ProtectedSick.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + ProtectedSick.getCellData("SupID", i));

					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, ProtectedSick.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + ProtectedSick.getCellData("SupPass", i));
					// Thread.sleep(500);

					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					// Call timesheet function
					//timeSheetAK();
					Emp = ProtectedSick.getCellData("Employee", i);
					try{

						if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
						{
							WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
							highlightElement(timesheet);
							oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
							//Enter Employee number
							Thread.sleep(1500);
							cSideframe();
							System.out.println("Employee " +Emp);
							setText(oUIObj.UIemployeeTS_xp, Emp);
							controlClick(oUIObj.uiLoad_xp,"Load Button");
							selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
						}
					}catch(Exception e){

						System.out.println("Load Button is not displayed");
					}

				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String Day = ProtectedSick.getCellData("Day", i);
				// Loading Employee number
				Emp = ProtectedSick.getCellData("Employee", i);
				if (!(Emp.equals(""))) {
					try {

						oBrowser.switchTo().defaultContent();
						oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
						System.out.println("Entered into content frame");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						// oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAllinput_xp)).sendKeys(Emp);
						System.out.println("Enter Employee Id");
						setText(oUIObj.uiLoadEmptxt_xp, Emp);

						System.out.println("click on Timesheets Load button");
						controlClick(oUIObj.uiLoadH_xp, " Load button");
						Thread.sleep(1500);;
						System.out.println("Pass:Employee selected successfully");
						selectReport.ReportStep("Pass", "Employee", "Validate Employee",
								"successfully selected employee" + Emp + " for the TC-" + i);
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						controlClick(oUIObj.UIEmployeeinfor_xp, "link");
						Calcgrp = ProtectedSick.getCellData("Calc Group", i);
						System.out.println("calc ");
						if (!Calcgrp.equals("")) {
							try {
								System.out.println("Enter emp calc group");
								WebElement calcselect = oBrowser.findElement(By.xpath(oUIObj.UIEmpcalcgrpclk_xp));

								setText(oUIObj.UIEmpcalcgrpclk_xp, Calcgrp);
								Thread.sleep(1500);
								calcselect.sendKeys(Keys.TAB);
								System.out.println("Pass:Callc Group selected successfully");
							} catch (Exception ex) {
								System.out.println("Fail: Unable to select Calc group");
								selectReport.ReportStep("Fail", "Calc Group", "Validate Calc Group",
										"Failed to select Callc Group as" + Calcgrp + " for the TC-" + TCN);
							}
						}
						String Benefit = ProtectedSick.getCellData("Benefit", i);
						System.out.println("Benefit value " + Benefit);
						if (!Benefit.equals("")) {
							try {
								System.out.println("Enter emp Benefit value");
								setText(oUIObj.UIEmpBenefit_xp, Benefit);
								System.out.println("Pass:Entered Benefit successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter Benefit");
								selectReport.ReportStep("Fail", "Benefit", "Validate Benefit",
										"Failed to enter Benefit as" + Benefit + " for the TC-" + TCN);
							}
						}
						state = ProtectedSick.getCellData("State", i);
						System.out.println("State " + state);
						if (!state.equals("")) {
							try {
								System.out.println("Enter emp state");
								setText(oUIObj.UIEmpstate_xp, state);
								System.out.println("Pass:Entered state successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter state");
								selectReport.ReportStep("Fail", "State", "Validate State",
										"Failed to enter state as" + state + " for the TC-" + TCN);
							}
						}
						country = ProtectedSick.getCellData("County", i);
						System.out.println("country ");
						if (!country.equals("")) {
							try {
								System.out.println("Enter emp country");
								setText(oUIObj.UIEmpCountry_xp, country);
								System.out.println("Pass:Entered country successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter country");
								selectReport.ReportStep("Fail", "country", "Validate country",
										"Failed to enter country as" + country + " for the TC-" + TCN);
							}
						}
						city = ProtectedSick.getCellData("City", i);
						System.out.println("city ");
						if (!city.equals("")) {
							try {
								System.out.println("Enter emp city");
								setText(oUIObj.UIEmpCity_xp, city);
								System.out.println("Pass:Entered city successfully");
							
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter country");
								selectReport.ReportStep("Fail", "city", "Validate city",
										"Failed to enter city as" + city + " for the TC-" + TCN);
							}
						}
						controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
						selectReport.ReportStep("Pass", "Submit in employee",
								"Validate Submit button in employee details screen",
								"successfully Entered employee details as" + city + country + state + " for the TC-"
										+ TCN);

						// Day
						//	String Day = ProtectedSick.getCellData("Day", i);

						System.out.println("day " + Day);
						List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
						List<WebElement> we3 = oBrowser.findElements(By.xpath(oUIObj.uiLeftArrowbtn_xp));
						for (int j = 0; j < we1.size(); j++) {
							if (Day.contains(we1.get(j).getText())) {
								exp = ProtectedSick.getCellData("Expected", i);
								if (exp.contains("PROTECTED")) {
									ProtectedFlag = true;
									we3.get(j).click();
									System.out.println("Selected left side arrow for validation purpose");

								}
								oBrowser.switchTo().defaultContent();
								cSideframe();
								// oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
								Thread.sleep(1500);
								try {
									List<WebElement> we2 = oBrowser.findElements(By.xpath(oUIObj.uiaddbtns_xp));
									we2.get(j).click();
								} catch (Exception ex) {
									System.out.println(ex.getMessage());
								}
								wait.until(
										ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClick_xp)));
								if (ProtectedSick.getCellData("IN1", i) != "") {
									oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
									setText(oUIObj.uiAddNewClick_xp, ProtectedSick.getCellData("IN1", i));
									Thread.sleep(500);
									oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
									System.out.println("Added clock 1");
								}
								// Enters Clock 2
								if (ProtectedSick.getCellData("OUT1", i) != "") {
									oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
									setText(oUIObj.uiAddNewClick_xp, ProtectedSick.getCellData("OUT1", i));
									Thread.sleep(500);
									oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
									Thread.sleep(1500);
									System.out.println("Added clock 2");
								}
								// Enters Clock 3
								if (ProtectedSick.getCellData("IN2", i) != "") {
									oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
									setText(oUIObj.uiAddNewClick_xp, ProtectedSick.getCellData("IN2", i));
									Thread.sleep(500);
									oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
									Thread.sleep(1500);
									System.out.println("Added clock 3");
								}
								// Enters Clock 4
								if (ProtectedSick.getCellData("OUT2", i) != "") {
									oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
									setText(oUIObj.uiAddNewClick_xp, ProtectedSick.getCellData("OUT2", i));
									Thread.sleep(500);
									oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
									Thread.sleep(1500);
									System.out.println("Added clock 4");
								}


								oBrowser.switchTo().defaultContent();
								System.out.println("Switced to default");
								oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
								System.out.println("Entered into content frame");
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UIEmpBasicsubmit_xp)).click();
								System.out.println("Clicked on Submit button");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

							}

						}
					} catch (Exception e) {

					}
				}
				String SickTime = "";
				if (ProtectedFlag) {
					if (!exp.equals("")) {
						System.out.println("Entered into validation");
						String[] expectime = FilterSick(ProtectedSick.getCellData("Expected", i));
						try {
							SickTime = oBrowser.findElement(By.xpath(oUIObj.uIsicktime_xp)).getText();
							System.out.println("UI Sick Value " + SickTime);
							selectReport.ReportStep("Pass", "UI value Time for SICK", "Validate getting time from UI",
									"Successfully stored time from UI");

							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.UIEmpBasicsubmit_xp)).click();
							System.out.println("Clicked on Submit button");
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.uiLeftArrowbtnup_xp)).click();
							System.out.println("Closed details");
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							System.out.println("***" + expectime[0]);
							System.out.println("***" + expectime[1]);
							Thread.sleep(1500);
							oBrowser.switchTo().defaultContent();
							cSideframe();
						}
						catch(Exception ex){
							System.out.println("Sick Time not displayed");
							oBrowser.findElement(By.xpath(oUIObj.uiLeftArrowbtnup_xp)).click();
							System.out.println("Closed details");
						}
						try {

							if (expectime[0].equals(SickTime)) {
								System.out.println("Pass:Successfully displayed time afer added -" + SickTime
										+ " for the TC-" + i);
								selectReport.ReportStep("Pass", "Validating SICK time", "Validate SICK time",
										"Added Time message displayed successfully-" + SickTime + " for the TC-" + TCN);
								oBrowser.switchTo().defaultContent();
							} else {
								System.out.println("Fail:Error Time not displayed as per test data.please verify."
										+ " for the TC-" + TCN);
								selectReport.ReportStep("Fail", "SICK Time", "Added Time should display",
										"Time not displayed as per test data.please verify." + " for the TC-" + TCN);
								oBrowser.switchTo().defaultContent();
							}
						} catch (Exception e) {
							System.out.println("Fail: in UI and Expected Validation" + " for the TC-" + TCN);
							selectReport.ReportStep("Fail", "Expected and UI SICK time",
									"Should validate SICK time and Expected",
									"Failed to update SICK time.Please verify manually" + " for the TC-" + TCN);
							oBrowser.switchTo().defaultContent();
						}
					} else if (exp.equals("")) {
						System.out.println("Pass:Successfully not displayed time afer added for the TC-" + TCN);
						selectReport.ReportStep("Pass", "Validating SICK time", "Validate SICK time",
								"Time Not Added message displayed successfully- for the TC-" + TCN);
						oBrowser.switchTo().defaultContent();
					}
				}
			} catch (Exception ex) {
				System.out.println("Fail: Cannot Complete Sick Accurual and exception in main" + " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "Sick Accurual", "Validate Sick Accurual",
						"Failed to updateSick Accurual.Please verify manually" + " for the TC-" + TCN);
			}
			Deletealledits();
		}
	}

	/*
	 * TC_Adding USHoliday scenarios 
	 * Created by Gandhi 
	 * Date Created: 02/14/19
	 * Usage:USHoliday Time clock
	 * @Information For the validation purpose
	 */

	public void USHolidays() throws Throwable {
		String pass = "USHoliday";
		US_Holiday(pass);
	}

	/*
	 * TC_US_Holiday Created by Gandhi 
	 * Date Updated: 02/06/19
	 * Usage: US_Holiday clock for  users 
	 * @Information For the validation purpose
	 */
	public void US_Holiday(String Timesheet) {
		ExcelRead US_Holiday = new ExcelRead(sTDFileName, Timesheet);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		String TCN = "";
		String supid = "";
		String tempsupid = "";
		String Calcgrp = "";
		String Benefit = "";
		String employee = "", 
				tempemp = "";
		String Startdate = "";
		String Enddate = "";
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = US_Holiday.rowCount();

		System.out.println("Total number of rows are :" + inofRows);
		int cnt = 1;
		for (int i = 1; i < inofRows; i = i + 7) {
			try {
				supid = US_Holiday.getCellData("SupID", i);
				TCN = US_Holiday.getCellData("TEST#", i);
				System.out.println(TCN);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						try {
							oBrowser.switchTo().defaultContent();
							oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						} catch (Exception e) {
							System.out.println("There is no more test data to execute");
						}
					}
					tempsupid = supid;
					// Enter User ID
					System.out.println("***");
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, US_Holiday.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + US_Holiday.getCellData("SupID", i));

					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, US_Holiday.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + US_Holiday.getCellData("SupPass", i));
					// Thread.sleep(500);

					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");

					// Call timesheet function
					//timeSheetAK();

					try{
						employee = US_Holiday.getCellData("Employee", i);
						if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
						{
							WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
							highlightElement(timesheet);
							oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
							//Enter Employee number
							Thread.sleep(1500);
							cSideframe();
							setText(oUIObj.UIemployeeTS_xp, employee);

							controlClick(oUIObj.uiLoad_xp,"Load Button");
							selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
						}
					}catch(Exception e){

						System.out.println("Load Button is not displayed");
					}
				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String emp;
				String AKDay;
				int temp = i;
				Startdate = US_Holiday.getCellData("Start Date", i);
				Enddate = US_Holiday.getCellData("End Date", i);
				if (!(Startdate.equals("") && !(Enddate.equals(""))))
				{
					try {
						oBrowser.findElement(By.xpath(oUIObj.uIEmpDate_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						WebElement frame1 = oBrowser.findElement(By.id("wb_expandableframe1"));
						oBrowser.switchTo().frame(frame1);
						System.out.println("Entered into sub frame in content frame");
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.UIStart_xp));
						Actions act = new Actions(oBrowser);
						act.click(TimeCodeTY).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
						System.out.println("Start date cleared");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						System.out.println("++SDay+++" + Startdate);
						oBrowser.findElement(By.xpath(oUIObj.UIStart_xp)).sendKeys(Startdate);
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						TimeCodeTY.sendKeys(Keys.TAB);
						WebElement TimeCodeTY1 = oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp));
						Actions act1 = new Actions(oBrowser);
						act1.click(TimeCodeTY1).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						System.out.println("End date cleared");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						System.out.println("++SDay+++" + Enddate);
						oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp)).sendKeys(Enddate);
						System.out.println("date range entered successfully");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.UILoadinSubFrm_xp)).click();
						Thread.sleep(1500);
						cSideframe();

						System.out.println("PASS: added Strat and End dates successfully for TC-" + TCN + " -" + Startdate + Enddate);
						
					} catch (Exception ex) {
						System.out.println("Fail:Not added Strat and End dates successfully for TC-" + TCN + " -" + Startdate + Enddate);
						selectReport.ReportStep("Fail", "Validate Strat and End dates",
								"Strat and End dates should add",
								"Not Added Strat and End dates successfully for TC-" + TCN + " -" + Startdate + Enddate);
					}
				}

				// Loading Employee number
				employee = US_Holiday.getCellData("Employee", i);
				System.out.println("**" + employee);
				if (!employee.equals(tempemp)) {
					oBrowser.findElement(By.xpath(oUIObj.uiLoadEmptxt_xp)).clear();
					setText(oUIObj.uiLoadEmptxt_xp, US_Holiday.getCellData("Employee", i));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.uiLoadH_xp)).click();
					Thread.sleep(1500);
					emp = oBrowser.findElement(By.xpath(oUIObj.uiEmpH_xp)).getText();
					selectReport.ReportStep("Pass", "Employee link is displayed", "Validate Employee Name",
							"Employee Name" + emp);
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					controlClick(oUIObj.UIEmployeeinfor_xp, "link");
					tempemp = employee;
					/*Calcgrp = US_Holiday.getCellData("Calc Group", i);
					 *System.out.println("calc ");
					 * if (!Calcgrp.equals("")) { try { System.out.println("Enter emp calc group");
					 * WebElement calcselect =
					 * oBrowser.findElement(By.xpath(oUIObj.UIEmpcalcgrpclk_xp));
					 * 
					 * setText(oUIObj.UIEmpcalcgrpclk_xp, Calcgrp); Thread.sleep(1500);
					 * calcselect.sendKeys(Keys.TAB);
					 * System.out.println("Pass:Callc Group selected successfully");
					 * selectReport.ReportStep("Pass", "Calc Group", "Validate Calc Group",
					 * "successfully selected Callc Group as" + Calcgrp + " for the TC-" + i); }
					 * catch (Exception ex) {
					 * System.out.println("Fail: Unable to select Calc group");
					 * selectReport.ReportStep("Fail", "Calc Group", "Validate Calc Group",
					 * "Failed to select Callc Group as" + Calcgrp + " for the TC-" + i); } }
					 */
					Benefit = US_Holiday.getCellData("Benefit", i);
					System.out.println("Benefit value " + Benefit);
					if (!Benefit.equals("")) {
						try {
							System.out.println("Enter emp Benefit value");
							setText(oUIObj.UIEmpBenefit_xp, Benefit);
							System.out.println("Pass:Entered Benefit successfully");
							
						} catch (Exception ex) {
							System.out.println("Fail: Unable to enter Benefit");
							selectReport.ReportStep("Fail", "Benefit", "Validate Benefit",
									"Failed to enter Benefit as" + Benefit + " for the TC-" + TCN);
						}
					}
					controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
					selectReport.ReportStep("Pass", "Submit in employee",
							"Validate Submit button in employee details screen",
							"successfully Entered employee details as" + Calcgrp + Benefit + " for the TC-"
									+ TCN);
				}



				for (int p = 1; p < 8; p++) 
				{	
					AKDay = US_Holiday.getCellData("Day", temp);
					System.out.println(AKDay);
					List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
					// List<WebElement> we3 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
					for (int j = 0; j < we1.size(); j++) {

						if (AKDay.contains(we1.get(j).getText())) {

							try {
								List<WebElement> we2 = oBrowser.findElements(By.xpath(oUIObj.uiaddbtns_xp));
								we2.get(j).click();
								
								System.out.println("Clicked on clock add button");
							} catch (Exception ex) {
								System.out.println(ex.getMessage());
							}
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClick_xp)));
							if (!(US_Holiday.getCellData("Clock_1", temp)).equals("")) {
								System.out.println("Entered into clock 1");
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, US_Holiday.getCellData("Clock_1", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								System.out.println("Added clock 1");
							}
							// Enters Clock 2
							if (!(US_Holiday.getCellData("Clock_2", temp)).equals("")){
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, US_Holiday.getCellData("Clock_2", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 2");
							}
							// Enters Clock 3
							if (!(US_Holiday.getCellData("Clock_3", temp)).equals("")) {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, US_Holiday.getCellData("Clock_3", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 3");
							}
							// Enters Clock 4
							if (!(US_Holiday.getCellData("Clock_4", temp)).equals("")) {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, US_Holiday.getCellData("Clock_4", temp));
								Thread.sleep(500);
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 4");
							}


							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							System.out.println("After clocks");

							oBrowser.switchTo().defaultContent();
							System.out.println("Switced to default");
							oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
							System.out.println("Entered into content frame");
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.UIEmpBasicsubmit_xp)).click();
							System.out.println("Clicked on Submit button");
							//oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							// Adding Overwrite field                     
							String overtimetype = US_Holiday.getCellData("Overwrite_Type", temp);
							System.out.println(overtimetype);

							if (overtimetype.equals("Employee Holiday") && !(overtimetype.equals(""))) 
							{
								System.out.println("Inside Holiday " + overtimetype);
								List<WebElement> we4 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
								System.out.println("Entered into click on Edit button");
								System.out.println(AKDay);
								we4.get(j).click();
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								controlClick(oUIObj.uimenuEmpHoliSH_xp, "Clicked on Employee Holiday");
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								selectReport.ReportStep("Pass", "Employee Holiday click",
										"Validate Employee Holiday click", "Clicked on Employee Holiday edit");
								Thread.sleep(500);
								// entering into over popup
								WebElement frame1 = oBrowser.findElement(By.id("ovrPopup"));
								oBrowser.switchTo().frame(frame1);
								String Holiday = US_Holiday.getCellData("Overwrite_field_3", temp);
								oBrowser.findElement(By.xpath(oUIObj.uiemployeeholidaysel_xp)).click();
								oBrowser.findElement(By.xpath(oUIObj.uiemployeeholidaysel_xp)).sendKeys(Holiday);
								WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.uiemployeeholidaysel_xp));
								Thread.sleep(1500);
								TimeCodeTY.sendKeys(Keys.TAB);
								System.out.println("HOLIDAY entered successfully");
								Thread.sleep(500);
								try {
									oBrowser.findElement(By.xpath(oUIObj.uiholidaysubmit_xp)).click();
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									System.out.println("Frame Okay");
									oBrowser.switchTo().defaultContent();
									cSideframe();
									oBrowser.findElement(By.xpath(oUIObj.uiSubmitTimeSheet_xp)).click();
									System.out.println("UI submitted");
									Thread.sleep(1500);
									System.out.println("Pass:Added holiday successfully for TC-" + TCN + " -" + AKDay);
									selectReport.ReportStep("Pass", "Holiday adding", "Validate Holiday",
											"Added holiday successfully for TC-" + TCN + " -" + AKDay);
								} catch (Exception ex) {
									System.out.println("Fail:Not added holiday successfully for TC-" + i + " -");
									selectReport.ReportStep("Fail", "Validate Holiday", "Holiday should add",
											"Not Added holiday successfully for TC-" + TCN + " -" + AKDay);
								}
								System.out.println(AKDay);
								oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
							}
							// Adding time code
							if (overtimetype.equals("Sched Times") && !(overtimetype.equals("")))  
							{
								System.out.println("Inside Holiday " + overtimetype);
								List<WebElement> we4 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
								System.out.println("Entered into click on Edit button");
								System.out.println(AKDay);
								we4.get(j).click();
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								String overwritefield1 = US_Holiday.getCellData("Overwrite_field_1", temp);
								String overwritefield2 = US_Holiday.getCellData("Overwrite_field_2", temp);
								// Clicking on Schedule Times
								oBrowser.findElement(By.xpath(oUIObj.uiearlylateschtime_xp)).click();
								Thread.sleep(500);
								oBrowser.switchTo().frame(oBrowser.findElement(By.name("ovrPopup")));
								setText(oUIObj.uistarttime_xp, overwritefield1);
								setText(oUIObj.uiendtime_xp, overwritefield2);
								Thread.sleep(500);
								try {
									oBrowser.findElement(By.xpath(oUIObj.uisubmitbtn_xp)).click();
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									selectReport.ReportStep("Pass", "Hours Submit",
											"Validate Hours Submit in new window", "Clicked on Submit in new window");

								} catch (Exception ex) {
									System.out.print("Fail:Not clicked on submit button successfully for TC-" + TCN);
									selectReport.ReportStep("Fail", "Validate Submit button", "Submit should click",
											"Not clicked on submit successfully for TC-" + TCN);
								}
								oBrowser.switchTo().defaultContent();
								cSideframe();
								oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
								selectReport.ReportStep("Pass", "Submit", "Validate Submit", "Clicked on Submit");

								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							} 
							break;

						}
					}
					if (AKDay.equals("Saturday"))
						if (p == 7) 
						{
							if(US_Holiday.getCellData("Expected", temp).contains("|"))
							{
								System.out.println("Entered into expected validation");
								String[] expectime = FilterUS_Holiday(US_Holiday.getCellData("Expected", temp));
								System.out.println("***" + expectime[0]);
								System.out.println("***" + expectime[1]);
								// oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
								Thread.sleep(1500);
								oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								try {
									String regtime = oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
									if (regtime.equals(expectime[0])) {
										System.out.println("Pass:Successfully displayed Regular time-" + regtime + " for the TC-" + TCN);
										selectReport.ReportStep("Pass", "Validate Regular time", "Added Regular time should display","Regular time displayed successfully-" + regtime + " for the TC-" + TCN);
									} else {
										System.out.println("Fail:Regular time not displayed as per test data.please verify."+ " for the TC-" + TCN);
										selectReport.ReportStep("Fail", "Validate Regular time", "Added Regular time should display","Regular time not displayed as per test data.please verify." + " for the TC-" + TCN);

									}
									String ACTHolTIME = oBrowser.findElement(By.xpath(oUIObj.uiempHOL_xp)).getText();
									if (ACTHolTIME.equals(expectime[1])) {
										System.out.println("Pass:Successfully displayed HOL time-" + ACTHolTIME + " for the TC-" + TCN);
										selectReport.ReportStep("Pass", "Validate HOL time", "Added HOL should display","HOL time displayed successfully-" + ACTHolTIME + " for the TC-" + TCN);
									} else {
										System.out.println("Fail:HoL time not displayed as per test data.please verify."+ " for the TC-" + TCN);
										selectReport.ReportStep("Fail", "Validate HOL time", "Added HOL time should display","HOL time not displayed as per test data.please verify." + " for the TC-" + TCN);

									}


								} catch (Exception ex) {
									System.out.println("Fail:Not displayed the time after added.Please verify manually" + " for the TC-" + TCN);
									selectReport.ReportStep("Fail", "Validate Regular and HOL Time","Added regular and HOL Time should display","Regular and HOL time not displayed successfully" + " for the TC-" + TCN);
								}
							}
							else
							{

								System.out.println("Entered into expected");
								String[] expectime = Filtercommon2(US_Holiday.getCellData("Expected", temp));
								oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
								Thread.sleep(1500);
								oBrowser.switchTo().frame(oBrowser.findElement(By.name("wb_expandableframe0")));
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								String regtime = oBrowser.findElement(By.xpath(oUIObj.uiregtime_xp)).getText();
								if (regtime.equals(expectime[0])) {
									System.out.println("Pass:Successfully displayed Regular time-" + regtime + " for the TC-" + TCN);
									selectReport.ReportStep("Pass", "Validate Regular time", "Added Regular time should display","Regular time displayed successfully-" + regtime + " for the TC-" + TCN);
								} else {
									System.out.println("Fail:Regular time not displayed as per test data.please verify."+ " for the TC-" + TCN);
									selectReport.ReportStep("Fail", "Validate Regular time", "Added Regular time should display","Regular time not displayed as per test data.please verify." + " for the TC-" + TCN);

								}

							}


						}
					temp = temp + 1;
					System.out.println("Entering into clocks loop");
				}
				cnt = cnt + 1;
				Deletealledits();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Fail:Cannot input Employee Clock-in information" + " for the test case-TC-" + TCN);
				selectReport.ReportStep("Fail", "Validate Regular and HOL for the USHoliday",
						"Time should add for USHoliday" + " for the test case-TC-" + TCN,
						"Time not added for USHoliday.Please verify manually" + " for the test case-TC-" + TCN);
				try {
					Deletealledits();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/*
	 * TC_Adding SickMax scenarios 
	 * Created by Gandhi 
	 * Date Created: 02/18/19
	 * Usage:Sick Max document calling
	 * @Information For the validation purpose
	 */

	public void SickMax() throws Throwable {
		String pass = "ProtectedSickMax";
		ProtectedSick_Max(pass);
	}

	/*
	 * TC_Adding Protected sick max scenarios 
	 * Created by Gandhi 
	 * Date Created: 02/18/19
	 * Usage:Protected sick max
	 * @Information For the validation purpose
	 */
	// Sick Max
	public void ProtectedSick_Max(String PSL) throws Throwable
	{
		String supid = "";
		String tempsupid = "";
		String Emp = "";
		String Calcgrp = "";
		String state = "";
		String country = "";
		String city = "";
		String exp = "";
		String PSmax="";
		String TCN ="";

		// String employee = "";
		ExcelRead ProtectedSickMax = new ExcelRead(sTDFileName, PSL);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = ProtectedSickMax.rowCount();
		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				TCN = ProtectedSickMax.getCellData("TEST#", i);
				System.out.println(TCN);
				supid = ProtectedSickMax.getCellData("SupID", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid = supid;

					// Enter User ID
					System.out.println("***");
					// Enter User ID
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, ProtectedSickMax.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + ProtectedSickMax.getCellData("SupID", i));

					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, ProtectedSickMax.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + ProtectedSickMax.getCellData("SupPass", i));
					// Thread.sleep(500);

					// Clicking on SignIn
					Thread.sleep(500);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					Emp = ProtectedSickMax.getCellData("Employee", i);
					// Call timesheet function
					//timeSheetAK();
					try{

						if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
						{
							WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
							highlightElement(timesheet);
							oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
							//Enter Employee number
							Thread.sleep(1500);
							cSideframe();
							setText(oUIObj.UIemployeeTS_xp, Emp);
							controlClick(oUIObj.uiLoad_xp,"Load Button");
							Thread.sleep(1500);
							selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
						}
					}catch(Exception e){

						System.out.println("Load Button is not displayed");
					}
				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String Day = ProtectedSickMax.getCellData("Day", i);
				if (!(Emp.equals(""))) {
					try {
						oBrowser.switchTo().defaultContent();
						oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
						System.out.println("Entered into content frame");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						// oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAllinput_xp)).sendKeys(Emp);
						System.out.println("Enter Employee Id");
						setText(oUIObj.uiLoadEmptxt_xp, Emp);

						System.out.println("click on Timesheets Load button");
						controlClick(oUIObj.uiLoadH_xp, " Load button");
						System.out.println("Pass:Employee selected successfully");
						selectReport.ReportStep("Pass", "Employee", "Validate Employee",
								"successfully selected employee" + Emp + " for the TC-" + TCN);
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						controlClick(oUIObj.UIEmployeeinfor_xp, "link");

						Calcgrp = ProtectedSickMax.getCellData("Calc Group", i);
						System.out.println("calc " + Calcgrp);
						if (!Calcgrp.equals("")) {
							try {
								System.out.println("Enter emp calc group");
								WebElement calcselect = oBrowser.findElement(By.xpath(oUIObj.UIEmpcalcgrpclk_xp));

								setText(oUIObj.UIEmpcalcgrpclk_xp, Calcgrp);
								Thread.sleep(1500);
								calcselect.sendKeys(Keys.TAB);
								System.out.println("Pass:Callc Group selected successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to select Calc group");
								selectReport.ReportStep("Fail", "Calc Group", "Validate Calc Group",
										"Failed to select Callc Group as" + Calcgrp + " for the TC-" + TCN);
							}
						}
						state = ProtectedSickMax.getCellData("State", i);
						System.out.println("State " + state);
						if (!state.equals("")) {
							try {
								System.out.println("Enter emp state");
								setText(oUIObj.UIEmpstate_xp, state);
								System.out.println("Pass:Entered state successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter state");
								selectReport.ReportStep("Fail", "State", "Validate State",
										"Failed to enter state as" + state + " for the TC-" + TCN);
							}
						}
						country = ProtectedSickMax.getCellData("County", i);
						System.out.println("country ");
						if (!country.equals("")) {
							try {
								System.out.println("Enter emp country");
								setText(oUIObj.UIEmpCountry_xp, country);
								System.out.println("Pass:Entered country successfully");
							
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter country");
								selectReport.ReportStep("Fail", "country", "Validate country",
										"Failed to enter country as" + country + " for the TC-" + TCN);
							}
						}

						String Benefit = ProtectedSickMax.getCellData("Benefit", i);
						System.out.println("Benefit value " + Benefit);
						if (!Benefit.equals("")) {
							try {
								System.out.println("Enter emp Benefit value");
								setText(oUIObj.UIEmpBenefit_xp, Benefit);
								System.out.println("Pass:Entered Benefit successfully");
							
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter Benefit");
								selectReport.ReportStep("Fail", "Benefit", "Validate Benefit",
										"Failed to enter Benefit as" + Benefit + " for the TC-" + TCN);
							}
						}

						city = ProtectedSickMax.getCellData("City", i);
						System.out.println("city ");
						if (!city.equals("")) {
							try {
								System.out.println("Enter emp city");
								setText(oUIObj.UIEmpCity_xp, city);
								System.out.println("Pass:Entered city successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter city");
								selectReport.ReportStep("Fail", "city", "Validate city",
										"Failed to enter city as" + city + " for the TC-" + TCN);
							}
						}
						String StartSick = ProtectedSickMax.getCellData("SICK", i);
						System.out.println("StartSickCK ");
						if (!StartSick.equals("")) {
							try {
								System.out.println("Enter emp SICK");


								WebElement startsick = oBrowser.findElement(By.xpath(oUIObj.UISick_xp));
								Actions act = new Actions(oBrowser);
								act.click(startsick).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UISick_xp)).sendKeys(StartSick);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								startsick.sendKeys(Keys.TAB);


								//setText(oUIObj.UISick_xp, StartSick);
								System.out.println("Pass:Entered StartSick successfully");
								selectReport.ReportStep("Pass", "StartSick", "Validate StartSick",
										"successfully Entered StartSick as" + StartSick + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter StartSick");
								selectReport.ReportStep("Fail", "StartSick", "Validate StartSick",
										"Failed to enter StartSick as" + StartSick + " for the TC-" + TCN);
							}
						}
						PSmax = ProtectedSickMax.getCellData("Max Balance", i);
						System.out.println(PSmax);
						if (!PSmax.equals("")) {
							try {
								System.out.println("Enter PSmax ");
								WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.UIPSMax_xp));
								setText(oUIObj.UIPSMax_xp, PSmax);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								TimeCodeTY.sendKeys(Keys.TAB);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								System.out.println("Pass:Entered ProtectedSickMax successfully");
								selectReport.ReportStep("Pass", "ProtectedSickSmax", "Validate city",
										"successfully Entered ProtectedSickSmax as" + PSmax + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter ProtectedSickSmax");
								selectReport.ReportStep("Fail", "ProtectedSickSmax", "Validate ProtectedSickSmax",
										"Failed to enter ProtectedSickSmax as" + PSmax + " for the TC-" + TCN);
							}
						}
						controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
						selectReport.ReportStep("Pass", "Submit in employee",
								"Validate Submit button in employee details screen",
								"successfully Entered employee details as" + city + country + state + " for the TC-"
										+ TCN);
						// Day
						//	String Day = ProtectedSick.getCellData("Day", i);
						System.out.println("day " + Day);
						List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
						for (int j = 0; j < we1.size(); j++) {
							if (Day.contains(we1.get(j).getText())) {

								oBrowser.switchTo().defaultContent();
								cSideframe();
								// oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
								Thread.sleep(1500);
								try {
									List<WebElement> we2 = oBrowser.findElements(By.xpath(oUIObj.uiaddbtns_xp));
									we2.get(j).click();
								} catch (Exception ex) {
									System.out.println(ex.getMessage());
								}
								wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClick_xp)));
								if (ProtectedSickMax.getCellData("IN1", i) != "") 
								{
									try {
										oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
										setText(oUIObj.uiAddNewClick_xp, ProtectedSickMax.getCellData("IN1", i));
										Thread.sleep(500);
										oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
										System.out.println("Added clock 1");
									}
									catch(Exception e)
									{
										System.out.println("Unable to enter clock 1 value");
									}
								}
								// Enters Clock 2
								if (ProtectedSickMax.getCellData("OUT1", i) != "") {
									try {
										oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
										setText(oUIObj.uiAddNewClick_xp, ProtectedSickMax.getCellData("OUT1", i));
										Thread.sleep(500);
										oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
										Thread.sleep(1500);
										System.out.println("Added clock 2");
									}
									catch(Exception ex)
									{
										System.out.println("Unable to enter clock 2 value");
									}
								}	
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UIEmpBasicsubmit_xp)).click();
								System.out.println("Clicked on Submit button");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								controlClick(oUIObj.UInextWeek_xp, "Next week button");
								selectReport.ReportStep("Pass", "Next week in timesheet",
										"Validate Next week in timesheet button in employee details screen",
										"successfully Entered Next week in timesheet for the TC-"
												+ TCN);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								controlClick(oUIObj.UIEmployeeinfor_xp, "link");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							}						
						}		
					} 
					catch (Exception e) {
					}

				}
				exp = ProtectedSickMax.getCellData("ExpectedMaxBalance", i);

				if (!exp.equals("")) {
					System.out.println("Entered into validation");

					WebElement TargetElement = oBrowser.findElement(By.xpath(oUIObj.UIPSMax_xp));
					((JavascriptExecutor) oBrowser).executeScript("arguments[0].scrollIntoView(true);", TargetElement);
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					String UiValue = TargetElement.getAttribute("value");
					selectReport.ReportStep("Pass", "Validating ProtectedSickMax time", "ProtectedSickMax",
							"Added Time captured successfully-" + UiValue + " for the TC-" + TCN);
					System.out.println("UiExpected ***" + UiValue);
					System.out.println("Result ***" + exp);
					try {
						if (UiValue.equals(exp)) {
							System.out.println("Pass:Successfully displayed time afer added -" + UiValue
									+ " for the TC-" + TCN);
							selectReport.ReportStep("Pass", "Validating ProtectedSickMax time", "ProtectedSickMax",
									"Added Time message displayed successfully-" + UiValue + " for the TC-" + TCN);
							//oBrowser.switchTo().defaultContent();
						} else {
							System.out.println("Fail:Error Time not displayed as per test data.please verify."
									+ " for the TC-" + TCN);
							selectReport.ReportStep("Fail", "ProtectedSickMax Time", "Added Time should display",
									"Time not displayed as per test data.please verify." + " for the TC-" + TCN);
							//oBrowser.switchTo().defaultContent();
						}
					} catch (Exception e) {
						System.out.println("Fail: in UI and Expected Validation" + " for the TC-" + TCN);
						selectReport.ReportStep("Fail", "Expected and UI SICK time",
								"Should validate SICK time and Expected",
								"Failed to update SICK time.Please verify manually" + " for the TC-" + TCN);
						//oBrowser.switchTo().defaultContent();
					}
				} 
			}
			catch (Exception ex) {
				System.out.println("Fail: Cannot Complete ProtectedSickMax and exception in main" + " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "ProtectedSickMax", "Validate ProtectedSickMax ",
						"Failed to update ProtectedSickMax.Please verify manually" + " for the TC-" + TCN);
			}
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
			Thread.sleep(500);
			cSideframe();
			controlClick(oUIObj.UIbeforeWeek_xp, "Previous week button");
			selectReport.ReportStep("Pass", "Previos week in timesheet",
					"Validate Previos week in timesheet button in employee details screen","successfully Entered Previos week in timesheet for the TC-"+ TCN);

			Deletealledits();
		}
	}





	/*
	 * Loading  SickMaxBalance scenarios test data
	 * Created by Gandhi 
	 * Date Created: 02/19/19
	 * Usage:SickMax_balance
	 * @Information For the validation purpose
	 */
	public void SickMaxBalance() throws Throwable {
		String pass = "SickMaxBalance";
		ProtectedSickMax_Bal(pass);
	}

	/*
	 * TC_Adding SickMaxBalance scenarios 
	 * Created by Gandhi 
	 * Date Created: 02/19/19
	 * Usage:Sick Max balance update
	 * @Information For the validation purpose
	 */
	// Sick_Accrual
	public void ProtectedSickMax_Bal(String PSL) throws Throwable
	{
		String supid = "";
		String tempsupid = "";
		String Emp = "";
		String Calcgrp = "";
		String state = "";
		String country = "";
		String city = "";
		String exp = "";
		String PSmax="";
		String TCN ="";
		String PSmaxBal ="";

		// String employee = "";
		ExcelRead SickMaxBalance = new ExcelRead(sTDFileName, PSL);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = SickMaxBalance.rowCount();
		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				TCN = SickMaxBalance.getCellData("TEST#", i);
				System.out.println(TCN);
				supid = SickMaxBalance.getCellData("SupID", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid = supid;

					// Enter User ID
					System.out.println("***");
					// Enter User ID
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, SickMaxBalance.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + SickMaxBalance.getCellData("SupID", i));

					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, SickMaxBalance.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + SickMaxBalance.getCellData("SupPass", i));
					// Thread.sleep(500);

					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					Emp = SickMaxBalance.getCellData("Employee", i);
					// Call timesheet function
					//timeSheetAK();
					try{

						if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
						{
							WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
							highlightElement(timesheet);
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
							//Enter Employee number
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							cSideframe();
							setText(oUIObj.UIemployeeTS_xp, Emp);
							controlClick(oUIObj.uiLoad_xp,"Load Button");
							Thread.sleep(1500);
							selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
						}
					}catch(Exception e){

						System.out.println("Load Button is not displayed");
					}
				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String Day = SickMaxBalance.getCellData("Day", i);
				// Loading Employee number
				//						Emp = ProtectedSickMax.getCellData("Employee", i);
				if (!(Emp.equals(""))) {
					try {
						oBrowser.switchTo().defaultContent();
						oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
						System.out.println("Entered into content frame");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						// oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAllinput_xp)).sendKeys(Emp);
						System.out.println("Enter Employee Id");
						setText(oUIObj.uiLoadEmptxt_xp, Emp);

						System.out.println("click on Timesheets Load button");
						controlClick(oUIObj.uiLoadH_xp, " Load button");
						Thread.sleep(1500);
						System.out.println("Pass:Employee selected successfully");
						selectReport.ReportStep("Pass", "Employee", "Validate Employee",
								"successfully selected employee" + Emp + " for the TC-" + TCN);
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						controlClick(oUIObj.UIEmployeeinfor_xp, "link");

						Calcgrp = SickMaxBalance.getCellData("Calc Group", i);
						System.out.println("calc " + Calcgrp);
						if (!Calcgrp.equals("")) {
							try {
								System.out.println("Enter emp calc group");
								WebElement calcselect = oBrowser.findElement(By.xpath(oUIObj.UIEmpcalcgrpclk_xp));

								setText(oUIObj.UIEmpcalcgrpclk_xp, Calcgrp);
								Thread.sleep(1500);
								calcselect.sendKeys(Keys.TAB);
								System.out.println("Pass:Callc Group selected successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to select Calc group");
								selectReport.ReportStep("Fail", "Calc Group", "Validate Calc Group",
										"Failed to select Callc Group as" + Calcgrp + " for the TC-" + TCN);
							}
						}
						state = SickMaxBalance.getCellData("State", i);
						System.out.println("State " + state);
						if (!state.equals("")) {
							try {
								System.out.println("Enter emp state");
								setText(oUIObj.UIEmpstate_xp, state);
								System.out.println("Pass:Entered state successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter state");
								selectReport.ReportStep("Fail", "State", "Validate State",
										"Failed to enter state as" + state + " for the TC-" + TCN);
							}
						}
						country = SickMaxBalance.getCellData("County", i);
						System.out.println("country ");
						if (!country.equals("")) {
							try {
								System.out.println("Enter emp country");
								setText(oUIObj.UIEmpCountry_xp, country);
								System.out.println("Pass:Entered country successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter country");
								selectReport.ReportStep("Fail", "country", "Validate country",
										"Failed to enter country as" + country + " for the TC-" + TCN);
							}
						}
						city = SickMaxBalance.getCellData("City", i);
						System.out.println("city ");
						if (!city.equals("")) {
							try {
								System.out.println("Enter emp city");
								setText(oUIObj.UIEmpCity_xp, city);
								System.out.println("Pass:Entered city successfully");
							
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter city");
								selectReport.ReportStep("Fail", "city", "Validate city",
										"Failed to enter city as" + city + " for the TC-" + TCN);
							}
						}
						PSmaxBal = SickMaxBalance.getCellData("Max Balance", i);
						PSmax = 	SickMaxBalance.getCellData("Sick Max", i);
						System.out.println(PSmaxBal);
						if (!PSmaxBal.equals("")) {
							try {
								System.out.println("Entering PSmax to '0' ");
								WebElement TimeCodeTY0 = oBrowser.findElement(By.xpath(oUIObj.UIPSMax_xp));
								setText(oUIObj.UIPSMax_xp, PSmax);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								TimeCodeTY0.sendKeys(Keys.TAB);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

								selectReport.ReportStep("Pass", "ProtectedSickMax", "Validate ProtectedSickMax",
										"successfully Entered ProtectedSick as" + PSmax + " for the TC-" + TCN);

								WebElement TimeCodeTYbal = oBrowser.findElement(By.xpath(oUIObj.UIPS_xp));
								setText(oUIObj.UIPS_xp, PSmaxBal);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								TimeCodeTYbal.sendKeys(Keys.TAB);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								System.out.println("Pass:Entered ProtectedSick successfully");
								selectReport.ReportStep("Pass", "ProtectedSick", "Validate ProtectedSick",
										"successfully Entered ProtectedSick as" + PSmax + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter ProtectedSick");
								selectReport.ReportStep("Fail", "ProtectedSickmax", "Validate ProtectedSick",
										"Failed to enter ProtectedSick as" + PSmax + " for the TC-" + TCN);
							}
						}
						controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
						selectReport.ReportStep("Pass", "Submit in employee",
								"Validate Submit button in employee details screen",
								"successfully Entered employee details as" + city + country + state + " for the TC-"
										+ TCN);
						// Day
						//	String Day = ProtectedSick.getCellData("Day", i);
						System.out.println("day " + Day);
						List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
						for (int j = 0; j < we1.size(); j++) {
							if (Day.contains(we1.get(j).getText())) {

								oBrowser.switchTo().defaultContent();
								cSideframe();
								// oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
								Thread.sleep(1500);
								try {
									List<WebElement> we2 = oBrowser.findElements(By.xpath(oUIObj.uiaddbtns_xp));
									we2.get(j).click();
								} catch (Exception ex) {
									System.out.println(ex.getMessage());
								}
								wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClick_xp)));
								if (SickMaxBalance.getCellData("IN1", i) != "") 
								{
									try {
										oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
										setText(oUIObj.uiAddNewClick_xp, SickMaxBalance.getCellData("IN1", i));
										Thread.sleep(500);
										oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
										System.out.println("Added clock 1");
									}
									catch(Exception e)
									{
										System.out.println("Unable to enter clock 1 value");
									}
								}
								// Enters Clock 2
								if (SickMaxBalance.getCellData("OUT1", i) != "") {
									try {
										oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
										setText(oUIObj.uiAddNewClick_xp, SickMaxBalance.getCellData("OUT1", i));
										Thread.sleep(500);
										oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
										Thread.sleep(1500);
										System.out.println("Added clock 2");
									}
									catch(Exception ex)
									{
										System.out.println("Unable to enter clock 2 value");
									}
								}	
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UIEmpBasicsubmit_xp)).click();
								System.out.println("Clicked on Submit button");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								controlClick(oUIObj.UInextWeek_xp, "Next week button");
								selectReport.ReportStep("Pass", "Next week in timesheet",
										"Validate Next week in timesheet button in employee details screen",
										"successfully Entered Next week in timesheet for the TC-"
												+ TCN);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								controlClick(oUIObj.UIEmployeeinfor_xp, "link");
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							}						
						}		
					} 
					catch (Exception e) {
					}

				}
				exp = SickMaxBalance.getCellData("ExpectedMaxBalance", i);

				if (!exp.equals("")) {
					System.out.println("Entered into validation");

					WebElement TargetElement = oBrowser.findElement(By.xpath(oUIObj.UIPS_xp));
					((JavascriptExecutor) oBrowser).executeScript("arguments[0].scrollIntoView(true);", TargetElement);
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					String UiValue = TargetElement.getAttribute("value");
					selectReport.ReportStep("Pass", "Validating ProtectedSickMaxBal time", "ProtectedSickMaxBal",
							"Added Time captured successfully-" + UiValue + " for the TC-" + TCN);
					System.out.println("UiExpected ***" + UiValue);
					System.out.println("Result ***" + exp);
					try {
						if (UiValue.equals(exp)) {
							System.out.println("Pass:Successfully displayed time afer added -" + UiValue
									+ " for the TC-" + TCN);
							selectReport.ReportStep("Pass", "Validating ProtectedSickMax time", "ProtectedSickMax",
									"Added Time message displayed successfully-" + UiValue + " for the TC-" + TCN);
							//oBrowser.switchTo().defaultContent();
						} else {
							System.out.println("Fail:Error Time not displayed as per test data.please verify."
									+ " for the TC-" + TCN);
							selectReport.ReportStep("Fail", "ProtectedSickMax Bal", "Added Time should display",
									"Time not displayed as per test data.please verify." + " for the TC-" + TCN);
							//oBrowser.switchTo().defaultContent();
						}
					} catch (Exception e) {
						System.out.println("Fail: in UI and Expected Validation" + " for the TC-" + TCN);
						selectReport.ReportStep("Fail", "Expected and UI SICK time",
								"Should validate SICK time and Expected",
								"Failed to update SICK time.Please verify manually" + " for the TC-" + TCN);
						//oBrowser.switchTo().defaultContent();
					}
				} 
			}
			catch (Exception ex) {
				System.out.println("Fail: Cannot Complete ProtectedSickMaxBal and exception in main" + " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "ProtectedSickMax", "Validate ProtectedSickMax ",
						"Failed to update ProtectedSickMaxBal.Please verify manually" + " for the TC-" + TCN);
			}
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
			Thread.sleep(500);
			cSideframe();
			controlClick(oUIObj.UIbeforeWeek_xp, "Previous week button");
			selectReport.ReportStep("Pass", "Previos week in timesheet",
					"Validate Previos week in timesheet button in employee details screen","successfully Entered Previos week in timesheet for the TC-"+ TCN);

			Deletealledits();
		}
	}


	/*
	 * Calling Employee Display Card test data sheet
	 * Created by Gandhi 
	 * Date Created: 02/25/19
	 * Usage:EmployeeDisplayCardTC
	 * @Information For the validation purpose
	 */
	public void EmployeeDisplay() throws Throwable {
		String pass = "EmployeeDisplayCard";
		EmployeeDisplayCardTC(pass);
	}

	/*
	 * TC_Verifying EmployeeDisplayCardTC
	 * Created by Gandhi 
	 * Date Created: 02/25/19
	 * Usage:Details in EmployeeDisplayCard
	 * @Information For the validation purpose
	 */

	public void EmployeeDisplayCardTC(String s) throws Exception 
	{

		String supid = "";
		String tempsupid = "";
		String TCN = "";

		ExcelRead EmpDisplayCard = new ExcelRead(sTDFileName, s);
		// Validate if Schedule link exist
		// Loops through each row in excel sheet
		int inofRows = EmpDisplayCard.rowCount();

		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				supid = EmpDisplayCard.getCellData("SupID", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid = supid;
					// Enter User ID
					System.out.println("***");
					// Enter User ID
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, EmpDisplayCard.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + EmpDisplayCard.getCellData("SupID", i));

					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, EmpDisplayCard.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + EmpDisplayCard.getCellData("SupPass", i));
					// Thread.sleep(500);
					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					Thread.sleep(1500);
					// Clicking on ViewSchedule
					//ViewSched();

					//for (int j = 0; j <=15 ; j++) {	
					//oBrowser.findElement(By.xpath(oUIObj.uiHeaderRightClick_xp)).click();						
					//oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					//}
					oBrowser.findElement(By.xpath(oUIObj.uiViewSched_xp)).click();
					System.out.println("Clicked on ViewSched_xp");
					selectReport.ReportStep("Pass", "View Sched", "Validate View Sched", "Clicked on View Sched Button");
					Thread.sleep(1500);
					cSideframe();
				}
				TCN = EmpDisplayCard.getCellData("TEST#", i);
				Thread.sleep(1500);



				oBrowser.findElement(By.xpath(oUIObj.uiViewSchedemp_xp)).click();
				Thread.sleep(1500);
				WebElement element = oBrowser.findElement(By.xpath(oUIObj.uiViewSchedempinfor_xp));
				selectReport.ReportStep("Pass", "View Sched Employee", "Validate View Sched Employee", "Clicked on View Sched Employee details Button");
				String toolTipTxt1 = element.getText();
				System.out.println("txt is " + toolTipTxt1);
				// Expeted
				System.out.println("Expected ");
				String Expected = EmpDisplayCard.getCellData("Expected", i);
				System.out.println("Expeted" +Expected);
				if (toolTipTxt1.contains(Expected)) {
					System.out.println(
							"Pass:Successfully displayed employee information for the TC-" + TCN);
					selectReport.ReportStep("Pass", "Validate Expected", "Employee information should display",
							"Employee information displayed successfully-" + toolTipTxt1 + " for the TC-" + TCN);
				} else {
					System.out.println("Fail: Employee information not displayed as per test data.please verify."
							+ " for the TC-" + i);
					selectReport.ReportStep("Fail", "Validate Expected", "Employee information should display",
							"Employee information not displayed as per test data.please verify." + " for the TC-" + TCN);
				}

				oBrowser.findElement(By.xpath(oUIObj.uiViewSchedemp_xp)).click();

			}
			catch (Exception e) {
				System.out.println("Fail: Cannot input EmployeeDisplayCard  " + " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "EmployeeDisplayCard", "Validate EmployeeDisplayCard",
						"Cannot input EmployeeDisplayCard.Please verify manually" + " for the TC-" + TCN);
			}
		}
	}    

	/*
	 * TC_BlackOut
	 * Created by Gandhi 
	 * Date Created: 02/27/19
	 * Usage: Loads BlackOut test data sheet
	 * @Information For the validation purpose
	 *  */  
	public void BlackOut()
	{
		String pass = "BlackOut";
		BlackOut(pass);
	}

	/*
	 * TC_RI_Call_in Created by Gandhi 
	 * Date Modified: 02/05/19 
	 * Usage: Time clock for Call in pay
	 * @Information For the validation purpose
	 */

	public void BlackOut(String timesheets) {String supid = "";
	String tempsupid = "";
	String TCN= "";
	ExcelRead BlackOut = new ExcelRead(sTDFileName, timesheets);
	WebDriverWait wait = new WebDriverWait(oBrowser, 500);
	// Validate if Timesheet link exist
	// Loops through each row in excel sheet
	int inofRows = BlackOut.rowCount();
	System.out.println("Total number of rows are :" + inofRows);
	for (int i = 1; i < inofRows; i++) {
		try {
			supid = BlackOut.getCellData("SupID", i);
			TCN = BlackOut.getCellData("TEST#", i);
			System.out.println(supid);
			System.out.println(tempsupid);
			if (!supid.equals(tempsupid)) {
				System.out.println("Inside");
				if (i > 1)
				{
					oBrowser.switchTo().defaultContent();
					oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				}
				tempsupid = supid;
				// Enter User ID
				System.out.println("***");
				// Enter User ID
				try {
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, BlackOut.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + BlackOut.getCellData("SupID", i));
				}
				catch(Exception e)
				{
					System.out.println("No more rows to execute");
				}

				// Enter Password
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
				setText(oUIObj.password_xp, BlackOut.getCellData("SupPass", i));
				selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
						"Password entered as " + BlackOut.getCellData("SupPass", i));
				// Thread.sleep(500);

				// Clicking on SignIn
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				controlClick(oUIObj.signIn_xp, "SignIn Button");
				selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
				// Call Time off calendar function
				try{

					if(oBrowser.findElement(By.xpath(oUIObj.uiTimeOffCalendar_xp)).isDisplayed())
					{
						WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeOffCalendar_xp));
						highlightElement(timesheet);
						oBrowser.findElement(By.xpath(oUIObj.uiTimeOffCalendar_xp)).click();
						//Enter Employee number
						Thread.sleep(1500);
						selectReport.ReportStep("Pass", "Time Off Calendar button",
								"Validate Time Off Calendar button",
								"Successfully clicked on Time Off Calendar button" + " for the TC-" + TCN);
						cSideframe();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

					}
				}
				catch (Exception e) {
					System.out.println("Fail: Cannot clicked on Time Off Calendar button" + " for the TC-" + TCN);
					selectReport.ReportStep("Fail", "Validate Time Off Calendar button",
							"Should click on Time Off Calendar button",
							"Not clicked on Time Off Calendar button.Please verify manually" + " for the TC-" + TCN);
				}
			}
			//Code after Time off calendar clicks
			oBrowser.findElement(By.xpath(oUIObj.UIdateInfor_xp)).click();
			//Enter Employee number
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			selectReport.ReportStep("Pass", "Calender open to select date",
					"Validate Calender open to select date",
					"Successfully clicked on Calender open to select date" + " for the TC-" + TCN);
			String month = BlackOut.getCellData("Month", i);
			System.out.println("Month " +month);
			String Year = BlackOut.getCellData("Year", i);
			System.out.println("Year " +Year);
			List<WebElement> we1=oBrowser.findElements(By.xpath(oUIObj.UImonths_xp));
			for(int j=0;j<we1.size();j++)
			{
				if(month.contains(we1.get(j).getText()))
				{
					try
					{ System.out.println("Selecting month");
					we1.get(j).click();
					selectReport.ReportStep("Pass", "select Month",
							"Validate select Month",
							"Successfully selected month" + " for the TC-" + TCN);
					System.out.println("Selected month");
					// System.out.println("Selecting year");

					}
					catch(Exception ex)
					{
						System.out.println(ex.getMessage());
						selectReport.ReportStep("FAIL", "select Month",
								"Should select Month",
								"Not selected month" + " for the TC-" + TCN);
						System.out.println("Selected month");
					}
				}
				if(Year.contains(we1.get(j).getText()))
				{
					try
					{ System.out.println("Selecting year");
					we1.get(j).click();
					System.out.println("Selected Year");
					// System.out.println("Selecting year");
					}
					catch(Exception ex)
					{
						System.out.println(ex.getMessage());
					}
				}
			}
			oBrowser.findElement(By.xpath(oUIObj.UIokCalenderPopup_xp)).click();
			System.out.println("Clicked on Ok button in Popup for TC " +TCN);
			selectReport.ReportStep("PASS", "Validate OK Button in Calender popup",
					"Clicked OK Button",
					"Succussfully Clicked on OK Button in Calendar popup" + " for the TC-" + TCN);

			controlClick(oUIObj.UIBlackOut_xp, "Blackout");
			setText(oUIObj.UIBlackOutType_xp, BlackOut.getCellData("Type", i));
			selectReport.ReportStep("PASS", "Validate Type field in Time off popup",
					"Enter Type value",
					"Succussfully entered Type Time off popup" + " for the TC-" + TCN);
			setText(oUIObj.UIBlackOutfromdate_xp, BlackOut.getCellData("Date", i));
			setText(oUIObj.UIBlackOutcomment_xp, BlackOut.getCellData("CommentField", i));
			selectReport.ReportStep("PASS", "Validate Comment field in Time off popup",
					"Enter Comment value",
					"Succussfully entered comment Time off popup" + " for the TC-" + TCN);
			oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			WebElement ok=oBrowser.findElement(By.xpath(oUIObj.UIBlackOutOK_xp));
			highlightElement(ok);
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			controlClick(oUIObj.UIBlackOutOK_xp, "Blackout ok button");
			//    oBrowser.findElement(By.xpath(oUIObj.UIBlackOutOK_xp)).click();
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			controlClick(oUIObj.UIBlackOutsubmit_xp, "Blackout submit");
			oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String PopupMsg = oBrowser.findElement(By.xpath(oUIObj.UIBlackOutReqValErrPopMsg_xp)).getText();
			System.out.println("Popup Message is  " + PopupMsg);
			oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			controlClick(oUIObj.UIBlackOutReqValErrPopOk_xp, "OK submit in error popup");
			oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			//Expected validation
			if (PopupMsg.contains(BlackOut.getCellData("Expected", i)))
			{
				System.out.println("Pass:Successfully displayed Error message-" + PopupMsg+ " for the TC-" + TCN);
				selectReport.ReportStep("Pass", "Error popup","Validate Error popup message in BlackOut screen","Error message displayed successfully-" + PopupMsg + " for the TC-" + TCN);
			} else {
				System.out.println("Fail:Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "Error popup in BlackOut screen","Added Error message should display","Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
			}
			// Deleting added BlackOut
			controlClick(oUIObj.UIclickOnAddedBD_xp, "Added Blackout click");
			controlClick(oUIObj.UIclickOnRemoveBD_xp, "Remove Blackout click");
		} catch (Exception e) {
			System.out.println("Fail: Cannot complete BlackOut" + " for the TC-" + TCN);
			selectReport.ReportStep("Fail", "Validate error message for the Black out",
					"Error message should display for Black out",
					"Error message not displayed for Black out.Please verify manually" + " for the TC-" + TCN);
		}
	}
	}

	/*
	 * Loading  SickMaxBalance scenarios test data
	 * Created by Gandhi 
	 * Date Created: 02/19/19
	 * Usage:SickMax_balance
	 * @Information For the validation purpose
	 */
	public void SickCarryOver() throws Throwable {
		String pass = "SickCarryOver";
		SickCarryOverTC(pass);
	}

	/*
	 * TC_Adding SickMaxBalance scenarios 
	 * Created by Gandhi 
	 * Date Created: 02/19/19
	 * Usage:Sick Max balance update
	 * @Information For the validation purpose
	 */
	// Sick_Accrual
	public void SickCarryOverTC(String PSL) throws Throwable
	{
		String supid = "";
		String tempsupid = "";
		String Emp = "";
		String Calcgrp = "";
		String state = "";
		String country = "";
		String city = "";
		String exp = "";
		String PSmax="";
		String TCN ="";
		String PSmaxBal ="";
		// String employee = "";
		ExcelRead SickCarryOver = new ExcelRead(sTDFileName, PSL);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = SickCarryOver.rowCount();
		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				TCN = SickCarryOver.getCellData("TEST#", i);
				System.out.println(TCN);
				supid = SickCarryOver.getCellData("SupID", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid = supid;
					// Enter User ID
					System.out.println("***");
					// Enter User ID
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, SickCarryOver.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + SickCarryOver.getCellData("SupID", i));
					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, SickCarryOver.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + SickCarryOver.getCellData("SupPass", i));
					// Thread.sleep(500);
					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					Emp = SickCarryOver.getCellData("Employee", i);
					// Call timesheet function
					//timeSheetAK();
					try{
						if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
						{
							WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
							highlightElement(timesheet);
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
							//Enter Employee number
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							cSideframe();
							setText(oUIObj.UIemployeeTS_xp, Emp);
							controlClick(oUIObj.uiLoad_xp,"Load Button");
							Thread.sleep(1500);
							selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
						}
					}catch(Exception e){
						System.out.println("Load Button is not displayed");
					}
				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String Date = SickCarryOver.getCellData("Date", i);
				// Loading Employee number
				//						Emp = ProtectedSickMax.getCellData("Employee", i);
				if (!(Emp.equals(""))) {
					try {
						oBrowser.switchTo().defaultContent();
						oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
						System.out.println("Entered into content frame");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						// oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAllinput_xp)).sendKeys(Emp);
						System.out.println("Enter Employee Id");
						setText(oUIObj.uiLoadEmptxt_xp, Emp);
						System.out.println("click on Timesheets Load button");
						controlClick(oUIObj.uiLoadH_xp, " Load button");
						System.out.println("Pass:Employee selected successfully");
						selectReport.ReportStep("Pass", "Employee", "Validate Employee",
								"successfully selected employee" + Emp + " for the TC-" + TCN);
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.uIEmpDate_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						WebElement frame1 = oBrowser.findElement(By.id("wb_expandableframe1"));
						oBrowser.switchTo().frame(frame1);
						System.out.println("Entered into sub frame in content frame");
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.UIStart_xp));
						Actions act = new Actions(oBrowser);
						act.click(TimeCodeTY).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
						System.out.println("Start date cleared");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.UIStart_xp)).sendKeys(Date);
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						TimeCodeTY.sendKeys(Keys.TAB);
						WebElement TimeCodeTY1 = oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp));
						Actions act1 = new Actions(oBrowser);
						act1.click(TimeCodeTY1).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						System.out.println("End date cleared");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp)).sendKeys(Date);
						System.out.println("date range entered successfully");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.UILoadinSubFrm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						cSideframe();
						System.out.println("PASS: added Strat and End dates successfully for TC-" + TCN + " -" + Date);
						selectReport.ReportStep("PASS", "Validate Start & End dates", "Start + End dates added",
								"Added Start & End dates successfully for TC-" + TCN + " -" + Date);		
						controlClick(oUIObj.UIEmployeeinfor_xp, "link");
						Calcgrp = SickCarryOver.getCellData("Calc Group", i);
						System.out.println("calc " + Calcgrp);
						if (!Calcgrp.equals("")) {
							try {
								System.out.println("Enter emp calc group");
								WebElement calcselect = oBrowser.findElement(By.xpath(oUIObj.UIEmpcalcgrpclk_xp));

								setText(oUIObj.UIEmpcalcgrpclk_xp, Calcgrp);
								Thread.sleep(1500);
								calcselect.sendKeys(Keys.TAB);
								System.out.println("Pass:Callc Group selected successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to select Calc group");
								selectReport.ReportStep("Fail", "Calc Group", "Validate Calc Group",
										"Failed to select Callc Group as" + Calcgrp + " for the TC-" + TCN);
							}
						}
						state = SickCarryOver.getCellData("State", i);
						System.out.println("State " + state);
						if (!state.equals("")) {
							try {
								System.out.println("Enter emp state");
								setText(oUIObj.UIEmpstate_xp, state);
								System.out.println("Pass:Entered state successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter state");
								selectReport.ReportStep("Fail", "State", "Validate State",
										"Failed to enter state as" + state + " for the TC-" + TCN);
							}
						}
						country = SickCarryOver.getCellData("County", i);
						System.out.println("country ");
						if (!country.equals("")) {
							try {
								System.out.println("Enter emp country");
								setText(oUIObj.UIEmpCountry_xp, country);
								System.out.println("Pass:Entered country successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter country");
								selectReport.ReportStep("Fail", "country", "Validate country",
										"Failed to enter country as" + country + " for the TC-" + TCN);
							}
						}
						city = SickCarryOver.getCellData("City", i);
						System.out.println("city ");
						if (!city.equals("")) {
							try {
								System.out.println("Enter emp city");
								setText(oUIObj.UIEmpCity_xp, city);
								System.out.println("Pass:Entered city successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter city");
								selectReport.ReportStep("Fail", "city", "Validate city",
										"Failed to enter city as" + city + " for the TC-" + TCN);
							}
						}
						PSmaxBal = SickCarryOver.getCellData("Max Balance", i);
						PSmax = 	SickCarryOver.getCellData("Sick Max", i);
						System.out.println(PSmaxBal);
						if (!PSmaxBal.equals("")) {
							try {
								System.out.println("Entering PSmax to '0' ");
								WebElement TimeCodeTY0 = oBrowser.findElement(By.xpath(oUIObj.UIPSMax_xp));
								setText(oUIObj.UIPSMax_xp, PSmax);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								TimeCodeTY0.sendKeys(Keys.TAB);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								selectReport.ReportStep("Pass", "ProtectedSickMax", "Validate ProtectedSickMax",
										"successfully Entered ProtectedSick as" + PSmax + " for the TC-" + TCN);
								WebElement TimeCodeTYbal = oBrowser.findElement(By.xpath(oUIObj.UIPS_xp));
								setText(oUIObj.UIPS_xp, PSmaxBal);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								TimeCodeTYbal.sendKeys(Keys.TAB);
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								System.out.println("Pass:Entered ProtectedSick successfully");
								selectReport.ReportStep("Pass", "ProtectedSick", "Validate ProtectedSick",
										"successfully Entered ProtectedSick as" + PSmax + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter ProtectedSick");
								selectReport.ReportStep("Fail", "ProtectedSickmax", "Validate ProtectedSick",
										"Failed to enter ProtectedSick as" + PSmax + " for the TC-" + TCN);
							}
						}
						controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
						selectReport.ReportStep("Pass", "Submit in employee",
								"Validate Submit button in employee details screen",
								"successfully Entered employee details as" + city + country + state + " for the TC-"
										+ TCN);
					} 
					catch (Exception e) {
					}
				}
				exp = SickCarryOver.getCellData("ExpectedMaxBalance", i);
				if (!exp.equals("")) {
					System.out.println("Entered into validation");
					controlClick(oUIObj.UIEmployeeinfor_xp, "link");
					WebElement TargetElement = oBrowser.findElement(By.xpath(oUIObj.UIPS_xp));
					((JavascriptExecutor) oBrowser).executeScript("arguments[0].scrollIntoView(true);", TargetElement);
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					String UiValue = TargetElement.getAttribute("value");
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					selectReport.ReportStep("Pass", "Validating ProtectedSickMaxBal time", "ProtectedSickMaxBal",
							"Added Time captured successfully-" + UiValue + " for the TC-" + TCN);
					System.out.println("UiExpected ***" + UiValue);
					System.out.println("Result ***" + exp);
					controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
					try {
						if (UiValue.equals(exp)) {
							System.out.println("Pass:Successfully displayed time afer added -" + UiValue
									+ " for the TC-" + TCN);
							selectReport.ReportStep("Pass", "Validating ProtectedSickMax time", "ProtectedSickMax",
									"Added Time message displayed successfully-" + UiValue + " for the TC-" + TCN);

						} else {
							System.out.println("Fail:Error Time not displayed as per test data.please verify."
									+ " for the TC-" + TCN);
							selectReport.ReportStep("Fail", "ProtectedSickMax Bal", "Added Time should display",
									"Time not displayed as per test data.please verify." + " for the TC-" + TCN);

						}
					} catch (Exception e) {
						System.out.println("Fail: in UI and Expected Validation" + " for the TC-" + TCN);
						selectReport.ReportStep("Fail", "Expected and UI Protected SICK time",
								"Should validate Protected SICK time and Expected",
								"Failed to update Protected SICK time.Please verify manually" + " for the TC-" + TCN);	
					}
				} 
			}
			catch (Exception ex) {
				System.out.println("Fail: Cannot Complete SickCarryOver and exception in main" + " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "SickCarryOver", "Validate SickCarryOver ",
						"Failed to update SickCarryOver.Please verify manually" + " for the TC-" + TCN);
			}
			oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}

	public void NonPSL() throws Throwable {
		String pass = "Non-PSL";
		Non_ProtectedSick(pass);
	}

	/*
	 * TC_Adding Non-PSL scenarios 
	 * Created by Gandhi 
	 * Date Created: 03/05/19
	 * Usage:Non Protectd Sick Leave change
	 * @Information For the validation purpose
	 */
	// Non-PSL
	public void Non_ProtectedSick(String PSL) throws Throwable

	{
		String supid = "";
		String tempsupid = "";
		String Emp = "";
		String Calcgrp = "";
		String state = "";
		String country = "";
		String city = "";
		String exp = "";
		String TCN ="";
		String Benefit = "";

		// String employee = "";
		ExcelRead nonProtectedsick = new ExcelRead(sTDFileName, PSL);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = nonProtectedsick.rowCount();
		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				TCN = nonProtectedsick.getCellData("TEST#", i);
				System.out.println(TCN);
				supid = nonProtectedsick.getCellData("SupID", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid = supid;

					// Enter User ID
					System.out.println("***");
					// Enter User ID
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, nonProtectedsick.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + nonProtectedsick.getCellData("SupID", i));

					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, nonProtectedsick.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + nonProtectedsick.getCellData("SupPass", i));
					// Thread.sleep(500);

					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					Emp = nonProtectedsick.getCellData("Employee", i);
					// Call timesheet function
					//timeSheetAK();
					try{

						if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
						{
							WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
							highlightElement(timesheet);
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
							//Enter Employee number
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							cSideframe();
							setText(oUIObj.UIemployeeTS_xp, Emp);


							//                                    oBrowser.findElement(By.xpath(oUIObj.uiAll_xp)).click();
							//                                    oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
							controlClick(oUIObj.uiLoad_xp,"Load Button");
							Thread.sleep(1500);
							selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
						}
					}catch(Exception e){

						System.out.println("Load Button is not displayed");
					}
				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();

				// Loading Employee number
				Emp = nonProtectedsick.getCellData("Employee", i);
				if (!(Emp.equals(""))) {
					try {
						oBrowser.switchTo().defaultContent();
						oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
						System.out.println("Entered into content frame");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						// oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAllinput_xp)).sendKeys(Emp);
						System.out.println("Enter Employee Id");
						setText(oUIObj.uiLoadEmptxt_xp, Emp);

						System.out.println("click on Timesheets Load button");
						controlClick(oUIObj.uiLoadH_xp, " Load button");
						System.out.println("Pass:Employee selected successfully");
						selectReport.ReportStep("Pass", "Employee", "Validate Employee",
								"successfully selected employee" + Emp + " for the TC-" + TCN);
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						controlClick(oUIObj.UIEmployeeinfor_xp, "link");
						Benefit = nonProtectedsick.getCellData("Benefit Code", i);
						System.out.println("Benefit value " + Benefit);
						if (!Benefit.equals("")) {
							try {
								System.out.println("Enter emp Benefit value");
								setText(oUIObj.UIEmpBenefit_xp, Benefit);
								System.out.println("Pass:Entered Benefit successfully");
								selectReport.ReportStep("Pass", "Benefit", "Validate Benefit",
										"successfully Entered Benefit as" + Benefit + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter Benefit");
								selectReport.ReportStep("Fail", "Benefit", "Validate Benefit",
										"Failed to enter Benefit as" + Benefit + " for the TC-" + TCN);
							}
						}
						Calcgrp = nonProtectedsick.getCellData("Calc Group", i);
						System.out.println("calc " + Calcgrp);
						if (!Calcgrp.equals("")) {
							try {
								System.out.println("Enter emp calc group");
								WebElement calcselect = oBrowser.findElement(By.xpath(oUIObj.UIEmpcalcgrpclk_xp));

								setText(oUIObj.UIEmpcalcgrpclk_xp, Calcgrp);
								Thread.sleep(1500);
								calcselect.sendKeys(Keys.TAB);
								System.out.println("Pass:Callc Group selected successfully");
							
							} catch (Exception ex) {
								System.out.println("Fail: Unable to select Calc group");
								selectReport.ReportStep("Fail", "Calc Group", "Validate Calc Group",
										"Failed to select Callc Group as" + Calcgrp + " for the TC-" + TCN);
							}
						}
						state = nonProtectedsick.getCellData("State", i);
						System.out.println("State " + state);
						if (!state.equals("")) {
							try {
								System.out.println("Enter emp state");
								setText(oUIObj.UIEmpstate_xp, state);
								System.out.println("Pass:Entered state successfully");
							
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter state");
								selectReport.ReportStep("Fail", "State", "Validate State",
										"Failed to enter state as" + state + " for the TC-" + TCN);
							}
						}
						country = nonProtectedsick.getCellData("County", i);
						System.out.println("country ");
						if (!country.equals("")) {
							try {
								System.out.println("Enter emp country");
								setText(oUIObj.UIEmpCountry_xp, country);
								System.out.println("Pass:Entered country successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter country");
								selectReport.ReportStep("Fail", "country", "Validate country",
										"Failed to enter country as" + country + " for the TC-" + TCN);
							}
						}
						city = nonProtectedsick.getCellData("City", i);
						System.out.println("city ");
						if (!city.equals("")) {
							try {
								System.out.println("Enter emp city");
								setText(oUIObj.UIEmpCity_xp, city);
								System.out.println("Pass:Entered city successfully");
							
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter city");
								selectReport.ReportStep("Fail", "city", "Validate city",
										"Failed to enter city as" + city + " for the TC-" + TCN);
							}
						}

						String HireDate = nonProtectedsick.getCellData("Hire Date", i);
						System.out.println("HireDate " +HireDate);
						if (!HireDate.equals("")) {
							try {
								System.out.println("Enter emp HireDate");                             
								WebElement hiredate = oBrowser.findElement(By.xpath(oUIObj.UIHiredate_xp));
								Actions act = new Actions(oBrowser);
								act.click(hiredate).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UIHiredate_xp)).sendKeys(HireDate);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								hiredate.sendKeys(Keys.TAB);
								Thread.sleep(1500); 
								System.out.println("Pass:Entered HireDate successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter HireDate");
								selectReport.ReportStep("Fail", "HireDate", "Validate HireDate",
										"Failed to enter HireDate as" + HireDate + " for the TC-" + TCN);
							}
						}
						String SeniorityDate = nonProtectedsick.getCellData("Hire Date", i);
						System.out.println("SeniorityDate " +SeniorityDate);
						if (!SeniorityDate.equals("")) {
							try {
								System.out.println("Enter emp SeniorityDate");                             
								WebElement hiredate = oBrowser.findElement(By.xpath(oUIObj.UISeniorityDate_xp));
								Actions act = new Actions(oBrowser);
								act.click(hiredate).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UISeniorityDate_xp)).sendKeys(SeniorityDate);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								hiredate.sendKeys(Keys.TAB);
								Thread.sleep(1500); 
								System.out.println("Pass:Entered SeniorityDate successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter SeniorityDate");
								selectReport.ReportStep("Fail", "SeniorityDate", "Validate SeniorityDate",
										"Failed to enter SeniorityDate as" + HireDate + " for the TC-" + TCN);
							}
						}
						String StartSick = nonProtectedsick.getCellData("Start Sick", i);
						System.out.println("StartSick ");
						if (!StartSick.equals("")) {
							try {
								System.out.println("Enter emp StartSick");
								WebElement startsick = oBrowser.findElement(By.xpath(oUIObj.UISick_xp));
								Actions act = new Actions(oBrowser);
								act.click(startsick).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UISick_xp)).sendKeys(StartSick);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								startsick.sendKeys(Keys.TAB);
								System.out.println("Pass:Entered StartSick successfully");		
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter StartSick");
								selectReport.ReportStep("Fail", "StartSick", "Validate StartSick",
										"Failed to enter StartSick as" + StartSick + " for the TC-" + TCN);
							}
						}


						String SICKGRANT1 = nonProtectedsick.getCellData("SICK GRANT 1", i);
						System.out.println("SICKGRANT1 ");
						if (!SICKGRANT1.equals("")) {
							try {
								System.out.println("Enter emp SICKGRANT1");

								WebElement sickgrant1 = oBrowser.findElement(By.xpath(oUIObj.UISickgrant1_xp));
								Actions act = new Actions(oBrowser);
								act.click(sickgrant1).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UISickgrant1_xp)).sendKeys(SICKGRANT1);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								sickgrant1.sendKeys(Keys.TAB);



								//setText(oUIObj.UISickgrant1_xp, SICKGRANT1);
								System.out.println("Pass:Entered SICKGRANT1 successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter SICKGRANT1");
								selectReport.ReportStep("Fail", "SICKGRANT1", "Validate SICKGRANT1",
										"Failed to enter SICKGRANT1 as" + SICKGRANT1 + " for the TC-" + TCN);
							}
						}


						String SICKGRANT2 = nonProtectedsick.getCellData("SICK GRANT 2", i);
						System.out.println("SICKGRANT2 ");
						if (!SICKGRANT2.equals("")) {
							try {
								System.out.println("Enter emp SICKGRANT2");

								WebElement sickgrant2 = oBrowser.findElement(By.xpath(oUIObj.UISickgrant2_xp));
								Actions act = new Actions(oBrowser);
								act.click(sickgrant2).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UISickgrant2_xp)).sendKeys(SICKGRANT2);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								sickgrant2.sendKeys(Keys.TAB);

								//setText(oUIObj.UISickgrant2_xp, SICKGRANT2);
								System.out.println("Pass:Entered SICKGRANT2 successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter SICKGRANT2");
								selectReport.ReportStep("Fail", "SICKGRANT2", "Validate SICKGRANT2",
										"Failed to enter SICKGRANT2 as" + SICKGRANT2 + " for the TC-" + TCN);
							}
						}


						String SICKGRANT3 = nonProtectedsick.getCellData("SICK GRANT 3", i);
						System.out.println("SICKGRANT3 ");
						if (!SICKGRANT3.equals("")) {
							try {
								System.out.println("Enter emp SICKGRANT3");
								WebElement sickgrant3 = oBrowser.findElement(By.xpath(oUIObj.UISickgrant3_xp));
								Actions act = new Actions(oBrowser);
								act.click(sickgrant3).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UISickgrant3_xp)).sendKeys(SICKGRANT3);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								sickgrant3.sendKeys(Keys.TAB);

								//setText(oUIObj.UISickgrant3_xp, SICKGRANT3);
								System.out.println("Pass:Entered SICKGRANT3 successfully");
							
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter SICKGRANT3");
								selectReport.ReportStep("Fail", "SICKGRANT3", "Validate SICKGRANT3",
										"Failed to enter SICKGRANT3 as" + SICKGRANT3 + " for the TC-" + TCN);
							}
						}



						String OverrideStart = nonProtectedsick.getCellData("Override Start", i);
						System.out.println("OverrideStart ");
						if (!OverrideStart.equals("")) {
							try {
								System.out.println("Enter emp OverrideStart");

								WebElement overridestart = oBrowser.findElement(By.xpath(oUIObj.UIoverridestartdate_xp));
								Actions act = new Actions(oBrowser);
								act.click(overridestart).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UIoverridestartdate_xp)).sendKeys(OverrideStart);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								overridestart.sendKeys(Keys.TAB);

								//setText(oUIObj.UIoverridestartdate_xp, OverrideStart);
								System.out.println("Pass:Entered OverrideStart successfully");
								
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter OverrideStart");
								selectReport.ReportStep("Fail", "OverrideStart", "Validate OverrideStart",
										"Failed to enter OverrideStart as" + OverrideStart + " for the TC-" + TCN);
							}
						}
						controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
						selectReport.ReportStep("Pass", "Submit in employee",
								"Validate Submit button in employee details screen",
								"successfully Entered employee details as" + city + country + state + " for the TC-"
										+ TCN);                        
					} 
					catch (Exception e) {    
					}                    
					String Dates = nonProtectedsick.getCellData("Date", i);
					if (!(Dates.equals("")))
					{
						try {
							oBrowser.findElement(By.xpath(oUIObj.uIEmpDate_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							WebElement frame1 = oBrowser.findElement(By.id("wb_expandableframe1"));
							oBrowser.switchTo().frame(frame1);
							System.out.println("Entered into sub frame in content frame");
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.UIStart_xp));
							Actions act = new Actions(oBrowser);
							act.click(TimeCodeTY).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
							System.out.println("Start date cleared");
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							System.out.println("++SDay+++" + Dates);
							oBrowser.findElement(By.xpath(oUIObj.UIStart_xp)).sendKeys(Dates);
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							TimeCodeTY.sendKeys(Keys.TAB);
							WebElement TimeCodeTY1 = oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp));
							Actions act1 = new Actions(oBrowser);
							act1.click(TimeCodeTY1).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							System.out.println("End date cleared");
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							System.out.println("++SDay+++" + Dates);
							oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp)).sendKeys(Dates);
							System.out.println("date range entered successfully");
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.UILoadinSubFrm_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							cSideframe();

							System.out.println("PASS: added Strat and End dates successfully for TC-" + TCN + " -" + Dates + Dates);
							
						} catch (Exception ex) {
							System.out.println("Fail:Not added Strat and End dates successfully for TC-" + TCN + " -" + Dates + Dates);
							selectReport.ReportStep("Fail", "Validate Strat and End dates",
									"Strat and End dates should add",
									"Not Added Strat and End dates successfully for TC-" + TCN + " -" + Dates + Dates);
						}
					}
					cSideframe();
					System.out.println("Entered to add clocks");
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClickNew_xp)));
					if(nonProtectedsick.getCellData("Clock_1", i)!="")
					{

						oBrowser.findElement(By.xpath(oUIObj.uiAddNewClickNew_xp)).click();
						System.out.println("Adding clock 1");
						//oBrowser.findElement(By.xpath(oUIObj.uiAddNewClickNew_xp)).clear();
						setText(oUIObj.uiAddNewClick_xp, nonProtectedsick.getCellData("Clock_1", i));
						Thread.sleep(500);
						oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
						System.out.println("Added clock 1");
					}
					//Enters Clock 2
					if(nonProtectedsick.getCellData("Clock_2", i)!="")
					{
						//oBrowser.findElement(By.xpath(oUIObj.uiAddNewClickNew_xp)).clear();
						setText(oUIObj.uiAddNewClick_xp, nonProtectedsick.getCellData("Clock_2", i));
						Thread.sleep(500);
						oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
						System.out.println("Added clock 2");
						Thread.sleep(500);
					}  				  				
					oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click(); 				
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					controlClick(oUIObj.UIEmployeeinfor_xp, "link");
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

					exp = nonProtectedsick.getCellData("Expected SICK", i);

					if (!exp.equals("")) {
						System.out.println("Entered into validation");

						WebElement TargetElement = oBrowser.findElement(By.xpath(oUIObj.UISick_xp));
						((JavascriptExecutor) oBrowser).executeScript("arguments[0].scrollIntoView(true);", TargetElement);
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						String UiValue = "";
						UiValue = TargetElement.getAttribute("value");
						selectReport.ReportStep("Pass", "Validating ProtectedSickMaxBal time", "ProtectedSickMaxBal",
								"Added Time captured successfully-" + UiValue + " for the TC-" + TCN);
						System.out.println("UiExpected ***" + UiValue);
						System.out.println("Result ***" + exp);
						try {
							if (UiValue.equals(exp)) {
								System.out.println("Pass:Successfully displayed time afer added -" + UiValue
										+ " for the TC-" + TCN);
								selectReport.ReportStep("Pass", "Validating ProtectedSickMax time", "ProtectedSickMax",
										"Added Time message displayed successfully-" + UiValue + " for the TC-" + TCN);
								//oBrowser.switchTo().defaultContent();
							} else {
								System.out.println("Fail:Error Time not displayed as per test data.please verify."
										+ " for the TC-" + TCN);
								selectReport.ReportStep("Fail", "ProtectedSickMax Bal", "Added Time should display",
										"Time not displayed as per test data.please verify." + " for the TC-" + TCN);
								//oBrowser.switchTo().defaultContent();
							}
						} catch (Exception e) {
							System.out.println("Fail: in UI and Expected Validation" + " for the TC-" + TCN);
							selectReport.ReportStep("Fail", "Expected and UI SICK time",
									"Should validate SICK time and Expected",
									"Failed to update SICK time.Please verify manually" + " for the TC-" + TCN);
							//oBrowser.switchTo().defaultContent();
						}
					} 
					controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");


				}


			}
			catch (Exception ex) {
				System.out.println("Fail: Cannot Complete NonProtectedSickMaxBal and exception in main" + " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "ProtectedSickMax", "Validate ProtectedSickMax ",
						"Failed to update ProtectedSickMaxBal.Please verify manually" + " for the TC-" + TCN);
			}

			Thread.sleep(500);

			Deletealledits();
		}
	}

	/*
	 * TC_MaxRequest
	 * Created by Gandhi 
	 * Date Created: 03/12/19
	 * Usage: Loads MaxRequest test data sheet
	 * @Information For the validation purpose
	 *  */  
	public void MaxRequest() throws Throwable {
		String pass = "MaxRequest";
		RequestingMax(pass);
	}

	/*
	 * TC_MaxRequest Created by Gandhi 
	 * Date Dated: 03/12/19 
	 * Usage:MaxRequest in Time of Calendar
	 * @Information For the validation purpose
	 */

	public void RequestingMax(String Request) throws Throwable
	{
		String supid = "";
		String tempsupid = "";
		String TCN= "";
		ExcelRead MaxRequest = new ExcelRead(sTDFileName, Request);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);

		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = MaxRequest.rowCount();
		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				supid = MaxRequest.getCellData("SupID", i);
				TCN = MaxRequest.getCellData("TEST#", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1)
					{
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						Thread.sleep(500);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid = supid;
					// Enter User ID
					System.out.println("***");
					// Enter User ID
					try {
						oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
						setText(oUIObj.uID_xp, MaxRequest.getCellData("SupID", i));
						selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
								"UserName entered as " + MaxRequest.getCellData("SupID", i));
					}
					catch(Exception e)
					{
						System.out.println("No more rows to execute");
					}

					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, MaxRequest.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + MaxRequest.getCellData("SupPass", i));
					// oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					// Call Time off calendar function
					try{
						if(oBrowser.findElement(By.xpath(oUIObj.uiTimeOffCalendar_xp)).isDisplayed())
						{
							WebElement time=oBrowser.findElement(By.xpath(oUIObj.uiTimeOffCalendar_xp));
							highlightElement(time);
							oBrowser.findElement(By.xpath(oUIObj.uiTimeOffCalendar_xp)).click();
							//Enter Employee number
							Thread.sleep(1500);
							selectReport.ReportStep("Pass", "Time Off Calendar button",
									"Validate Time Off Calendar button",
									"Successfully clicked on Time Off Calendar button" + " for the TC-" + TCN);
							cSideframe();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

						}
					}
					catch (Exception e) {
						System.out.println("Fail: Cannot clicked on Time Off Calendar button" + " for the TC-" + TCN);
						selectReport.ReportStep("Fail", "Validate Time Off Calendar button",
								"Should click on Time Off Calendar button",
								"Not clicked on Time Off Calendar button.Please verify manually" + " for the TC-" + TCN);
					}
				}
				//Code after Time off calendar clicks
				oBrowser.findElement(By.xpath(oUIObj.UIdateInfor_xp)).click();
				//Enter Employee number
				oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				selectReport.ReportStep("Pass", "Calender open to select date",
						"Validate Calender open to select date",
						"Successfully clicked on Calender open to select date" + " for the TC-" + TCN);
				String month = MaxRequest.getCellData("Month", i);
				System.out.println("Month " +month);
				String Year = MaxRequest.getCellData("Year", i);
				System.out.println("Year " +Year);
				List<WebElement> we1=oBrowser.findElements(By.xpath(oUIObj.UImonths_xp));
				for(int j=0;j<we1.size();j++)
				{
					if(month.contains(we1.get(j).getText()))
					{
						try
						{ System.out.println("Selecting month");
						we1.get(j).click();
						selectReport.ReportStep("Pass", "select Month",
								"Validate select Month",
								"Successfully selected month" + " for the TC-" + TCN);
						System.out.println("Selected month");
						// System.out.println("Selecting year");

						}
						catch(Exception ex)
						{
							System.out.println(ex.getMessage());
							selectReport.ReportStep("FAIL", "select Month",
									"Should select Month",
									"Not selected month" + " for the TC-" + TCN);
							System.out.println("Selected month");
						}
					}
					if(Year.contains(we1.get(j).getText()))
					{
						try
						{ System.out.println("Selecting year");
						we1.get(j).click();
						System.out.println("Selected Year");
						// System.out.println("Selecting year");
						}
						catch(Exception ex)
						{
							System.out.println(ex.getMessage());
						}
					}
				}
				oBrowser.findElement(By.xpath(oUIObj.UIokCalenderPopup_xp)).click();
				System.out.println("Clicked on Ok button in Popup for TC " +TCN);
				selectReport.ReportStep("PASS", "Validate OK Button in Calender popup",
						"Clicked OK Button",
						"Succussfully Clicked on OK Button in Calendar popup" + " for the TC-" + TCN);
				controlClick(oUIObj.UIBlackOut_xp, "Blackout");
				setText(oUIObj.UIBlackOutType_xp, MaxRequest.getCellData("Type", i));
				selectReport.ReportStep("PASS", "Validate Type field in Time off popup",
						"Enter Type value",
						"Succussfully entered Type in Time off popup" + " for the TC-" + TCN);	
				oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				String Startdate = MaxRequest.getCellData("Start Date", i);
				String Enddate = MaxRequest.getCellData("End Date", i);
				oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
				WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.UIBlackOutfromdate_xp));
				Actions act = new Actions(oBrowser);
				act.click(TimeCodeTY).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
				oBrowser.findElement(By.xpath(oUIObj.UIBlackOutfromdate_xp)).clear();
				System.out.println("Start date cleared");
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				System.out.println("++SDate+++" + Startdate);
				oBrowser.findElement(By.xpath(oUIObj.UIBlackOutfromdate_xp)).sendKeys(Startdate);
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				TimeCodeTY.sendKeys(Keys.TAB);
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	
				WebElement TimeCodeTY1 = oBrowser.findElement(By.xpath(oUIObj.UIToDte_xp));
				Actions act1 = new Actions(oBrowser);
				act1.click(TimeCodeTY1).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				System.out.println("End date cleared");
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				System.out.println("++EDate+++" + Enddate);
				oBrowser.findElement(By.xpath(oUIObj.UIToDte_xp)).sendKeys(Enddate);
				System.out.println("date range entered successfully");
				oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
				selectReport.ReportStep("PASS", "Validate From and To Dates in Time off popup",
						"Enter From and To Dates value",
						"Succussfully entered Dates in Time off popup" + " for the TC-" + TCN);
				setText(oUIObj.UIBlackOutcomment_xp, MaxRequest.getCellData("CommentField", i));
				selectReport.ReportStep("PASS", "Validate Comment field in Time off popup",
						"Enter Comment value",
						"Succussfully entered comment Time off popup" + " for the TC-" + TCN);
				oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement ok=oBrowser.findElement(By.xpath(oUIObj.UIBlackOutOK_xp));
				highlightElement(ok);
				oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				controlClick(oUIObj.UIBlackOutOK_xp, " ok button");
				//    oBrowser.findElement(By.xpath(oUIObj.UIBlackOutOK_xp)).click();
				oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				controlClick(oUIObj.UIBlackOutsubmit_xp, " submit");
				oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				String PopupMsg = oBrowser.findElement(By.xpath(oUIObj.UIBlackOutReqValErrPopMsg_xp)).getText();
				System.out.println("Popup Message is  " + PopupMsg);
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				controlClick(oUIObj.UIBlackOutReqValErrPopOk_xp, "OK submit in error popup");
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				//Expected validation
				if (PopupMsg.contains(MaxRequest.getCellData("Expected", i)))
				{
					System.out.println("Pass:Successfully displayed Error message-" + PopupMsg+ " for the TC-" + TCN);
					selectReport.ReportStep("Pass", "Error popup","Validate Error popup message in BlackOut screen","Error message displayed successfully-" + PopupMsg + " for the TC-" + TCN);
				} else {
					System.out.println("Fail:Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
					selectReport.ReportStep("Fail", "Error popup in BlackOut screen","Added Error message should display","Error message not displayed as per test data.please verify."+ " for the TC-" + TCN);
				}
				// Deleting added Request
				String Type = MaxRequest.getCellData("Type", i);
				System.out.println("Type  " +Type);
				if(Type.equals("VAC")) {
					List<WebElement> we11=oBrowser.findElements(By.xpath(oUIObj.UIclickOnAddedBD_xp));
					for (int j = 0; j < we11.size(); j++) {
						we11.get(j).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						controlClick(oUIObj.UIclickOnRemoveBD_xp, "Remove Blackout click");
					}}
				if(Type.equals("SICK")) {
					List<WebElement> we11=oBrowser.findElements(By.xpath(oUIObj.UIReqSick));
					for (int j = 0; j < we11.size(); j++) {
						we11.get(j).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						controlClick(oUIObj.UIclickOnRemoveBD_xp, "Remove Blackout click");
					}}
				if(Type.equals("BEREAVEMENT")) {
					List<WebElement> we11=oBrowser.findElements(By.xpath(oUIObj.UIReqBEREAVEMENT));
					for (int j = 0; j < we11.size(); j++) {
						we11.get(j).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						controlClick(oUIObj.UIclickOnRemoveBD_xp, "Remove Blackout click");
					}}
				if(Type.equals("THANK YOU DAY")) {
					List<WebElement> we11=oBrowser.findElements(By.xpath(oUIObj.UIReqTHANKYOUDAY));
					for (int j = 0; j < we11.size(); j++) {
						we11.get(j).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						controlClick(oUIObj.UIclickOnRemoveBD_xp, "Remove Blackout click");
					}}
				if(Type.equals("JURY")) {
					List<WebElement> we11=oBrowser.findElements(By.xpath(oUIObj.UIReqJury));
					for (int j = 0; j < we11.size(); j++) {
						we11.get(j).click();
						Thread.sleep(500);
						controlClick(oUIObj.UIclickOnRemoveBD_xp, "Remove Blackout click");
					}}
				if(Type.equals("UNPAID DAY OFF")) {
					List<WebElement> we11=oBrowser.findElements(By.xpath(oUIObj.UIReqUNPAIDDAYOFF));
					for (int j = 0; j < we11.size(); j++) {
						we11.get(j).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						controlClick(oUIObj.UIclickOnRemoveBD_xp, "Remove Blackout click");
					}}
				//controlClick(oUIObj.UIleavePopup_xp, "Removing Added calendars");
				controlClick(oUIObj.UIBlackOutsubmit_xp, " submit");
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			} catch (Exception e) {
				System.out.println("Fail: Cannot input Employee Clock-in information" + " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "Validate Regular and OT or timecode for the Call in pay",
						"Time should add for call in Pay",
						"Time not added for Call in pay.Please verify manually" + " for the TC-" + TCN);
			}
		}
	}
	/*
	 * Function to call Vacation_grant scenarios sheet
	 * Created by Gandhi 
	 * Date Created: 03/18/19
	 * Usage:NVacation_grant changes
	 * @Information For the validation purpose
	 */
	public void Vacation_grant() throws Throwable {
		String pass = "Vacation_grant";
		Vacation_grnt(pass);
	}



	/*
	 * TC_Adding Vacation_grant scenarios 
	 * Created by Gandhi 
	 * Date Created: 03/18/19
	 * Usage:Vacation_grant values change
	 * @Information For the validation purpose
	 */
	// Vacation_grant
	public void Vacation_grnt(String Grant) throws Throwable

	{
		String supid = "";
		String tempsupid = "";
		String Emp = "";

		String exp = "";
		String TCN ="";
		String SeniorityDate ="";
		String Benefit = "";

		// String employee = "";
		ExcelRead Vacation_grant = new ExcelRead(sTDFileName, Grant);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = Vacation_grant.rowCount();
		System.out.println("Total number of rows are :" + inofRows);
		for (int i = 1; i < inofRows; i++) {
			try {
				TCN = Vacation_grant.getCellData("TEST#", i);
				System.out.println(TCN);
				supid = Vacation_grant.getCellData("SupID", i);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						oBrowser.switchTo().defaultContent();
						oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					tempsupid = supid; 
					// Enter User ID
					System.out.println("***");
					// Enter User ID
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, Vacation_grant.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + Vacation_grant.getCellData("SupID", i));
					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, Vacation_grant.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + Vacation_grant.getCellData("SupPass", i));
					// oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
					Emp = Vacation_grant.getCellData("Employee", i);
					// Call timesheet function
					//timeSheetAK();
					try{
						if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
						{
							WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
							highlightElement(timesheet);
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
							//Enter Employee number
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							cSideframe();
							setText(oUIObj.UIemployeeTS_xp, Emp);
							controlClick(oUIObj.uiLoad_xp,"Load Button");
							Thread.sleep(1500);
							selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
						}
					}catch(Exception e){
						System.out.println("Load Button is not displayed");
					}
				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.switchTo().defaultContent();
				cSideframe();     
				// Loading Employee number
				Emp = Vacation_grant.getCellData("Employee", i);
				if (!(Emp.equals(""))) {
					try {
						oBrowser.switchTo().defaultContent();
						oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
						System.out.println("Entered into content frame");
						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						// oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAllinput_xp)).sendKeys(Emp);
						System.out.println("Enter Employee Id");
						setText(oUIObj.uiLoadEmptxt_xp, Emp);

						System.out.println("click on Timesheets Load button");
						controlClick(oUIObj.uiLoadH_xp, " Load button");
						Thread.sleep(1500);
						System.out.println("Pass:Employee selected successfully");
						selectReport.ReportStep("Pass", "Employee", "Validate Employee",
								"successfully selected employee" + Emp + " for the TC-" + TCN);
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						controlClick(oUIObj.UIEmployeeinfor_xp, "link");
						Benefit = Vacation_grant.getCellData("Benefit Code", i);
						System.out.println("Benefit value " + Benefit);
						if (!Benefit.equals("")) {
							try {
								System.out.println("Enter emp Benefit value");
								setText(oUIObj.UIEmpBenefit_xp, Benefit);
								System.out.println("Pass:Entered Benefit successfully");
								selectReport.ReportStep("Pass", "Benefit", "Validate Benefit",
										"successfully Entered Benefit as" + Benefit + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter Benefit");
								selectReport.ReportStep("Fail", "Benefit", "Validate Benefit",
										"Failed to enter Benefit as" + Benefit + " for the TC-" + TCN);
							}
						}




						SeniorityDate = Vacation_grant.getCellData("Seniority Date", i);
						System.out.println("SeniorityDate " +SeniorityDate);
						if (!SeniorityDate.equals("")) {
							try {
								System.out.println("Enter emp SeniorityDate");                             
								WebElement hiredate = oBrowser.findElement(By.xpath(oUIObj.UISeniorityDate_xp));
								Actions act = new Actions(oBrowser);
								act.click(hiredate).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UISeniorityDate_xp)).sendKeys(SeniorityDate);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								hiredate.sendKeys(Keys.TAB);
								Thread.sleep(1500); 
								System.out.println("Pass:Entered SeniorityDate successfully");
								selectReport.ReportStep("Pass", "SeniorityDate", "Validate SeniorityDate",
										"successfully Entered SeniorityDate as" + SeniorityDate + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter SeniorityDate");
								selectReport.ReportStep("Fail", "SeniorityDate", "Validate SeniorityDate",
										"Failed to enter SeniorityDate as" + SeniorityDate + " for the TC-" + TCN);
							}
						}
						String DailyAvgHours = Vacation_grant.getCellData("Daily Average Hours", i);
						System.out.println("DailyAvgHours ");
						if (!DailyAvgHours.equals("")) {
							try {
								System.out.println("Enter emp DailyAvgHours");
								oBrowser.findElement(By.xpath(oUIObj.UI_AvgDailyHours_xp)).clear();
								oBrowser.findElement(By.xpath(oUIObj.UI_AvgDailyHours_xp)).sendKeys(DailyAvgHours);
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								//setText(oUIObj.UISick_xp, StartSick);
								System.out.println("Pass:Entered DailyAvgHours successfully");
								selectReport.ReportStep("Pass", "DailyAvgHours", "Validate DailyAvgHours",
										"successfully Entered DailyAvgHours as" + DailyAvgHours + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter DailyAvgHours");
								selectReport.ReportStep("Fail", "DailyAvgHours", "Validate DailyAvgHours",
										"Failed to enter DailyAvgHours as" + DailyAvgHours + " for the TC-" + TCN);
							}
						}
						String OverrideStart = Vacation_grant.getCellData("Override Start", i);
						System.out.println("OverrideStart ");
						if (!OverrideStart.equals("")) {
							try {
								System.out.println("Enter emp OverrideStart"); 
								WebElement overridestart = oBrowser.findElement(By.xpath(oUIObj.UIoverridestartdate_xp));
								Actions act = new Actions(oBrowser);
								act.click(overridestart).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
								oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
								oBrowser.findElement(By.xpath(oUIObj.UIoverridestartdate_xp)).sendKeys(OverrideStart);
								Thread.sleep(1500);
								overridestart.sendKeys(Keys.TAB);

								//setText(oUIObj.UIoverridestartdate_xp, OverrideStart);
								System.out.println("Pass:Entered OverrideStart successfully");
								selectReport.ReportStep("Pass", "OverrideStart", "Validate OverrideStart",
										"successfully Entered OverrideStart as" + OverrideStart + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter OverrideStart");
								selectReport.ReportStep("Fail", "OverrideStart", "Validate OverrideStart",
										"Failed to enter OverrideStart as" + OverrideStart + " for the TC-" + TCN);
							}
						}
						String InitialVacation = Vacation_grant.getCellData("Initial Vacation", i);
						System.out.println("InitialVacation ");
						if (!InitialVacation.equals("")) {
							try {
								System.out.println("Enter emp InitialVacation");
								setText(oUIObj.UIEmpInitVac_xp, InitialVacation);
								System.out.println("Pass:Entered InitialVacation successfully");
								selectReport.ReportStep("Pass", "InitialVacation", "Validate InitialVacation",
										"successfully Entered InitialVacation as" + InitialVacation + " for the TC-" + TCN);
							} catch (Exception ex) {
								System.out.println("Fail: Unable to enter InitialVacation");
								selectReport.ReportStep("Fail", "InitialVacation", "Validate InitialVacation",
										"Failed to enter InitialVacation as" + InitialVacation + " for the TC-" + TCN);
							}
						}

						controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
						selectReport.ReportStep("Pass", "Submit in employee",
								"Validate Submit button in employee details screen",
								"successfully Entered employee details as" +  " for the TC-"
										+ TCN);                        
					} 
					catch (Exception e) {    
					}                    
					String Dates = Vacation_grant.getCellData("Check Date", i);
					if (!(Dates.equals("")))
					{
						try {
							oBrowser.findElement(By.xpath(oUIObj.uIEmpDate_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							WebElement frame1 = oBrowser.findElement(By.id("wb_expandableframe1"));
							oBrowser.switchTo().frame(frame1);
							System.out.println("Entered into sub frame in content frame");
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.UIStart_xp));
							Actions act = new Actions(oBrowser);
							act.click(TimeCodeTY).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
							System.out.println("Start date cleared");
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							System.out.println("++SDay+++" + Dates);
							oBrowser.findElement(By.xpath(oUIObj.UIStart_xp)).sendKeys(Dates);
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							TimeCodeTY.sendKeys(Keys.TAB);
							WebElement TimeCodeTY1 = oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp));
							Actions act1 = new Actions(oBrowser);
							act1.click(TimeCodeTY1).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							System.out.println("End date cleared");
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							System.out.println("++SDay+++" + Dates);
							oBrowser.findElement(By.xpath(oUIObj.UIEnd_xp)).sendKeys(Dates);
							System.out.println("date range entered successfully");
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.UILoadinSubFrm_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							cSideframe();

							System.out.println("PASS: added Strat and End dates successfully for TC-" + TCN + " -" + Dates + Dates);
							selectReport.ReportStep("PASS", "Validate Start & End dates", "Start + End dates added",
									"Added Start & End dates successfully for TC-" + TCN + " -" + Dates + Dates);
						} catch (Exception ex) {
							System.out.println("Fail:Not added Strat and End dates successfully for TC-" + TCN + " -" + Dates + Dates);
							selectReport.ReportStep("Fail", "Validate Strat and End dates",
									"Strat and End dates should add",
									"Not Added Strat and End dates successfully for TC-" + TCN + " -" + Dates + Dates);
						}
					}
					cSideframe();			  				
					oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click(); 				
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					controlClick(oUIObj.UIEmployeeinfor_xp, "link");
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

					exp = Vacation_grant.getCellData("Expected VAC", i);

					if (!exp.equals("")) {
						System.out.println("Entered into validation");

						WebElement TargetElement = oBrowser.findElement(By.xpath(oUIObj.UIEmpInitVac_xp));
						((JavascriptExecutor) oBrowser).executeScript("arguments[0].scrollIntoView(true);", TargetElement);
						oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						String UiDisplayedValue = "";
						UiDisplayedValue = TargetElement.getAttribute("value");
						selectReport.ReportStep("Pass", "Validating Vacation time", "Vacation",
								"Added Time captured successfully-" + UiDisplayedValue + " for the TC-" + TCN);
						System.out.println("UiExpected ***" + UiDisplayedValue);
						System.out.println("Result ***" + exp);
						try {
							if (UiDisplayedValue.equals(exp)) {
								System.out.println("Pass:Successfully displayed time afer added -" + UiDisplayedValue
										+ " for the TC-" + TCN);
								selectReport.ReportStep("Pass", "Validating ProtectedSickMax time", "ProtectedSickMax",
										"Added Time message displayed successfully-" + UiDisplayedValue + " for the TC-" + TCN);
								//oBrowser.switchTo().defaultContent();
							} else {
								System.out.println("Fail:Error Time not displayed as per test data.please verify."
										+ " for the TC-" + TCN);
								selectReport.ReportStep("Fail", "ProtectedSickMax Bal", "Added Time should display",
										"Time not displayed as per test data.please verify." + " for the TC-" + TCN);
								//oBrowser.switchTo().defaultContent();
							}
						} catch (Exception e) {
							System.out.println("Fail: in UI and Expected Validation" + " for the TC-" + TCN);
							selectReport.ReportStep("Fail", "Expected and UI SICK time",
									"Should validate SICK time and Expected",
									"Failed to update SICK time.Please verify manually" + " for the TC-" + TCN);
							//oBrowser.switchTo().defaultContent();
						}
					} 
					controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");


				}


			}
			catch (Exception ex) {
				System.out.println("Fail: Cannot Complete NonProtectedSickMaxBal and exception in main" + " for the TC-" + TCN);
				selectReport.ReportStep("Fail", "ProtectedSickMax", "Validate ProtectedSickMax ",
						"Failed to update ProtectedSickMaxBal.Please verify manually" + " for the TC-" + TCN);
			}

			Thread.sleep(500);

			Deletealledits();
		}
	}

	/*
	 * TC_Swipe_test
	 * Created by Gandhi
	 * Date Created: 03/22/19
	 * Usage: Loads Swipe test data sheets 
	 * @Information For the validation purpose
	 *  */  
	public void Swipe_test()throws Throwable {

		String pass = "Swipe_test";
		Swipetest(pass);
	}

	/*
	 * TC_Swipe_test Created by Gandhi 
	 * Date Created: 03/22/19
	 * Usage: Time clock for swipe tests
	 * @Information For the validation purpose
	 */

	public void Swipetest(String timesheet) {

		ExcelRead Swipe_test = new ExcelRead(sTDFileName, timesheet);
		WebDriverWait wait = new WebDriverWait(oBrowser, 500);
		String tcode = "";
		String TCN = "";
		String Scenario = "";
		String supid = "";
		String tempsupid = "";
		String employee = "", tempemp = "";
		// Validate if Timesheet link exist
		// Loops through each row in excel sheet
		int inofRows = Swipe_test.rowCount();

		System.out.println("Total number of rows are :" + inofRows);
		int cnt = 1;
		for (int i = 1; i < inofRows; i = i + 7) {
			try {
				supid = Swipe_test.getCellData("SupID", i);
				employee = Swipe_test.getCellData("Employee", i);
				TCN  = Swipe_test.getCellData("TEST#", i);
				Scenario = Swipe_test.getCellData("Scenario", i);
				System.out.println(TCN + Scenario);
				System.out.println(supid);
				System.out.println(tempsupid);
				if (!supid.equals(tempsupid)) {
					System.out.println("Inside");
					if (i > 1) {
						try {
							oBrowser.switchTo().defaultContent();
							oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
							oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
							oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						} catch (Exception e) {
							System.out.println("There is no more test data to execute");
						}
					}
					tempsupid = supid;
					// Enter User ID
					System.out.println("***");
					oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
					setText(oUIObj.uID_xp, Swipe_test.getCellData("SupID", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"UserName entered as " + Swipe_test.getCellData("SupID", i));

					// Enter Password
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
					setText(oUIObj.password_xp, Swipe_test.getCellData("SupPass", i));
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
							"Password entered as " + Swipe_test.getCellData("SupPass", i));
					// Thread.sleep(500);

					// Clicking on SignIn
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					controlClick(oUIObj.signIn_xp, "SignIn Button");
					selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button"  + TCN + Scenario);

					// Call timesheet function
					timeSheetAK(employee);
					
				}
				// Enter Empolyee clock-in information
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				Thread.sleep(500);
				oBrowser.switchTo().defaultContent();
				cSideframe();
				String emp;
				String AKDay;
				int temp1 = i;
				tcode = Swipe_test.getCellData("TCODE_Edit", temp1);
				int temp = i;
				// Loading Employee number
				employee = Swipe_test.getCellData("Employee", i);
				System.out.println("**" + employee);
				if (!employee.equals(tempemp)) {
					oBrowser.findElement(By.xpath(oUIObj.uiLoadEmptxt_xp)).clear();
					setText(oUIObj.uiLoadEmptxt_xp, Swipe_test.getCellData("Employee", i));
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.uiLoadH_xp)).click();
					Thread.sleep(2000);
					emp = oBrowser.findElement(By.xpath(oUIObj.uiEmpH_xp)).getText();
					selectReport.ReportStep("Pass", "Employee link is displayed", "Validate Employee Name",
							"Employee Name" + emp  + TCN + Scenario);
					tempemp = employee;
				
//					controlClick(oUIObj.UInextWeek_xp, "Next week button");
//					selectReport.ReportStep("Pass", "Next week in timesheet",
//							"Validate Next week in timesheet button in employee details screen",
//							"successfully Entered Next week in timesheet for the TC-"
//									+ TCN);
					 Deletealledits();
				}
				for (int p = 1; p < 8; p++) {
					AKDay = Swipe_test.getCellData("Day", temp);
					List<WebElement> we1 = oBrowser.findElements(By.xpath(oUIObj.uiweeks_xp));
					// List<WebElement> we3 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
					for (int j = 0; j < we1.size(); j++) {
						if (AKDay.contains(we1.get(j).getText())) {
							
							try {
								List<WebElement> we2 = oBrowser.findElements(By.xpath(oUIObj.uiaddbtns_xp));
								we2.get(j).click();
							} catch (Exception ex) {
								System.out.println(ex.getMessage());
							}
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(oUIObj.uiAddNewClick_xp)));
							if (Swipe_test.getCellData("Clock_1", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, Swipe_test.getCellData("Clock_1", temp));
								Thread.sleep(500);
								if (Swipe_test.getCellData("HourType1", temp) != "") {
									Thread.sleep(500);
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockType_xp)).click();
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockTC_xp)).click();
									Thread.sleep(500);
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockData_xp)).clear();
									setText(oUIObj.UIEmpClockData_xp, Swipe_test.getCellData("HourType1", temp));
									Thread.sleep(500);
								}
								
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								System.out.println("Added clock 1 for "  + TCN + Scenario);
							}
							// Enters Clock 2
							if (Swipe_test.getCellData("Clock_2", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, Swipe_test.getCellData("Clock_2", temp));
								Thread.sleep(1000);
								if (Swipe_test.getCellData("HourType2", temp) != "") {
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockType_xp)).click();
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockTC_xp)).click();
									Thread.sleep(500);
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockData_xp)).clear();
									setText(oUIObj.UIEmpClockData_xp, Swipe_test.getCellData("HourType2", temp));
									Thread.sleep(500);
								}
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1000);
								System.out.println("Added clock 2 for "  + TCN + Scenario);
							}
							// Enters Clock 3
							if (Swipe_test.getCellData("Clock_3", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, Swipe_test.getCellData("Clock_3", temp));
								Thread.sleep(1000);
								if (Swipe_test.getCellData("HourType3", temp) != "") {
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockType_xp)).click();
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockTC_xp)).click();
									Thread.sleep(500);
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockData_xp)).clear();
									setText(oUIObj.UIEmpClockData_xp, Swipe_test.getCellData("HourType3", temp));
									Thread.sleep(500);
								}
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 3 for "  + TCN + Scenario);
							}
							// Enters Clock 4
							if (Swipe_test.getCellData("Clock_4", temp) != "") {
								oBrowser.findElement(By.xpath(oUIObj.uiAddNewClick_xp)).clear();
								setText(oUIObj.uiAddNewClick_xp, Swipe_test.getCellData("Clock_4", temp));
								Thread.sleep(500);
								if (Swipe_test.getCellData("HourType4", temp) != "") {
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockType_xp)).click();
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockTC_xp)).click();
									Thread.sleep(500);
									oBrowser.findElement(By.xpath(oUIObj.UIEmpClockData_xp)).clear();
									setText(oUIObj.UIEmpClockData_xp, Swipe_test.getCellData("HourType4", temp));
									Thread.sleep(500);
								}
								oBrowser.findElement(By.xpath(oUIObj.uiAddBtn_xp)).click();
								Thread.sleep(1500);
								System.out.println("Added clock 4 for "  + TCN + Scenario);
							}
							Thread.sleep(500);
							oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
							Thread.sleep(1000);
							String overtimetype = Swipe_test.getCellData("Overwrite_Type", temp);
							if (!overtimetype.equals("")) {
								System.out.println("Inside Holiday " + overtimetype);
								List<WebElement> we4 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
								System.out.println("Entered into click on Edit button");
								System.out.println(AKDay);
								we4.get(j).click();
								oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								String overwritefield1 = Swipe_test.getCellData("Overwrite_field_1", i);
								String overwritefield2 = Swipe_test.getCellData("Overwrite_field_2", i);
								// Clicking on Schedule Times
								oBrowser.findElement(By.xpath(oUIObj.uiearlylateschtime_xp)).click();
								Thread.sleep(500);
								oBrowser.switchTo().frame(oBrowser.findElement(By.name("ovrPopup")));
								Thread.sleep(500);
								System.out.println("Entered into overpopup frame");
								setText(oUIObj.uistarttime_xp, overwritefield1);
								System.out.println("Entered start time for "  + TCN + Scenario);
								setText(oUIObj.uiendtime_xp, overwritefield2);
								System.out.println("Entered End time for "  + TCN + Scenario);
								Thread.sleep(1000);
								try {
									oBrowser.findElement(By.xpath(oUIObj.uisubmitbtn_xp)).click();
									Thread.sleep(2000);
									selectReport.ReportStep("Pass", "Hours Submit", "Validate Hours Submit in new window",
											"Clicked on Submit in new window " +TCN  + Scenario);

								} catch (Exception ex) {
									System.out.println("Fail:Not clicked on submit button successfully for TC-" +TCN + Scenario + Scenario);
									selectReport.ReportStep("Fail", "Validate Submit button", "Submit should click",
											"Not clicked on submit successfully for TC-"  +TCN  + Scenario);
								}
								oBrowser.switchTo().defaultContent();
								cSideframe();
								oBrowser.findElement(By.xpath(oUIObj.uiSubmitClockBtn_xp)).click();
								selectReport.ReportStep("Pass", "Submit", "Validate Submit", "Clicked on Submit"  + TCN + Scenario);
								Thread.sleep(2000);	
								System.out.println(AKDay);
								oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
							}
							// Adding time code
							if (Swipe_test.getCellData("TCODE_Edit", temp) != "") {

								tcode = Swipe_test.getCellData("TCODE_Edit", temp);
								System.out.println("Inside tcode" + tcode);
								try {
									System.out.println("Inside TCODE" + tcode);
									List<WebElement> we4 = oBrowser.findElements(By.xpath(oUIObj.uieditbtn_xp));
									System.out.println("Entered into click on Edit button");
									System.out.println(AKDay);
									we4.get(j).click();
									oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
									controlClick(oUIObj.uimenuEmpLTA_xp, "Clicked on Employee LTA");
									Thread.sleep(500);
									selectReport.ReportStep("Pass", "Employee LTA click",
											"Validate Employee LTA click", "Clicked on Employee LTA edit"  + TCN + Scenario);
									Thread.sleep(500);
									// entering into over popup
									WebElement frame1 = oBrowser.findElement(By.id("ovrPopup"));
									oBrowser.switchTo().frame(frame1);

									oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTA_xp)).click();
									oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTA_xp)).sendKeys(tcode);
									WebElement TimeCodeTY = oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTA_xp));
									oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
									TimeCodeTY.sendKeys(Keys.TAB);
									if (Swipe_test.getCellData("Start Time", temp) != "") 
									{
										String STime = Swipe_test.getCellData("Start Time", temp);
										String ETime = Swipe_test.getCellData("End Time", temp);
										oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAST_xp)).click();
										oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAST_xp)).sendKeys(STime);
										WebElement TimeCodeTY1 = oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAST_xp));
										oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
										TimeCodeTY1.sendKeys(Keys.TAB);
										oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAET_xp)).click();
										oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAET_xp)).sendKeys(ETime);
										WebElement TimeCodeTY2 = oBrowser.findElement(By.xpath(oUIObj.uiempTcodeLTAET_xp));
										oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
										TimeCodeTY2.sendKeys(Keys.TAB);
										Thread.sleep(500);
									}
									System.out.println("TCODE entered successfully");
									Thread.sleep(500);
									try {
										oBrowser.findElement(By.xpath(oUIObj.uiholidaysubmit_xp)).click();
										Thread.sleep(500);
										System.out.println("Frame Okay");
										oBrowser.switchTo().defaultContent();
										cSideframe();
										oBrowser.findElement(By.xpath(oUIObj.uiSubmitTimeSheet_xp)).click();
										System.out.println("UI submitted");
										oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
										System.out.println("Pass:Added TCODE successfully for TC-" + TCN + Scenario + " -" + tcode);
										selectReport.ReportStep("Pass", "tcode adding", "tcode Holiday",
												"Added tcode successfully for TC-" + TCN + Scenario + " -" + tcode);
									} catch (Exception ex) {
										System.out.println("Fail:Not added tcode successfully for TC-" + TCN + Scenario + " -");
										selectReport.ReportStep("Fail", "Validate tcode", "tcode should add",
												"Not Added tcode successfully for TC-" + TCN + Scenario + " -" + tcode);
									}
									System.out.println(AKDay);
									oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
								} 
								catch (Exception ex) {
									System.out.println("FAIL:Not added Holiday/Tcode for the TC-" + TCN + Scenario);
									selectReport.ReportStep("FAIL", "Validate Tcode", "Added tcode should display",
											"Tcode not displayed successfully-" + tcode + " for the TC-" + TCN + Scenario);
								}		
							}
							break;
						}
					}
					if (AKDay.equals("Sunday"))
						if (p == 7) {}
					temp = temp + 1;
					System.out.println("Entering into clocks loop");
				}
				cnt = cnt + 1;
				tcode = "";
			}
			catch (Exception e) {
				System.out.println("Fail:Cannot input Employee Clock-in information" + " for the test case-TC-" + TCN + Scenario);
				selectReport.ReportStep("Fail", "Validate Regular and OT or timecode for the weekly",
						"Time should add for weekly" + " for the test case-TC-" + TCN + Scenario,
						"Time not added for weekly.Please verify manually" + " for the test case-TC-" + TCN + Scenario);
			
			}
		}

	}

	
	
	
	
	
	
	
	
	
	public void ManagerRules() throws Throwable {
		String pass = "Manager_Rules";
		ManagerrulesTC(pass);
	}

	/*
	 * TC_Adding SickMaxBalance scenarios 
	 * Created by Gandhi 
	 * Date Created: 02/19/19
	 * Usage:Sick Max balance update
	 * @Information For the validation purpose
	 */
	// Sick_Accrual
	public void ManagerrulesTC(String PSL) throws Throwable
	
	{
	String supid = "";
	String tempsupid = "";
	String Emp = "";
	String Calcgrp = "";
	String state = "";
	String country = "";
	String city = "";
	String exp = "";
	String PSmax="";
	String TCN ="";
	String PSmaxBal ="";

	// String employee = "";
	ExcelRead ManagerRule = new ExcelRead(sTDFileName, PSL);
	WebDriverWait wait = new WebDriverWait(oBrowser, 500);
	// Validate if Timesheet link exist
	// Loops through each row in excel sheet
	int inofRows = ManagerRule.rowCount();
	System.out.println("Total number of rows are :" + inofRows);
	for (int i = 1; i < inofRows; i++) {
		try {
			TCN = ManagerRule.getCellData("TEST#", i);
			//System.out.println(TCN);
			supid = ManagerRule.getCellData("SupID", i);
			System.out.println(supid);
			System.out.println(tempsupid);
			if (!supid.equals(tempsupid)) {
				System.out.println("Inside");
				if (i > 1) {
					oBrowser.switchTo().defaultContent();
					oBrowser.findElement(By.xpath(oUIObj.logOut_xp)).click();
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					oBrowser.findElement(By.xpath(oUIObj.confirm_xp)).click();
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				}
				tempsupid = supid;

				// Enter User ID
				System.out.println("***");
				// Enter User ID
				oBrowser.findElement(By.xpath(oUIObj.uID_xp)).clear();
				setText(oUIObj.uID_xp, ManagerRule.getCellData("SupID", i));
				selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
						"UserName entered as " + ManagerRule.getCellData("SupID", i));

				// Enter Password
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				oBrowser.findElement(By.xpath(oUIObj.password_xp)).clear();
				setText(oUIObj.password_xp, ManagerRule.getCellData("SupPass", i));
				selectReport.ReportStep("Pass", "Sign In", "Validate Sign In",
						"Password entered as " + ManagerRule.getCellData("SupPass", i));
				// Thread.sleep(500);

				// Clicking on SignIn
				oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				controlClick(oUIObj.signIn_xp, "SignIn Button");
				selectReport.ReportStep("Pass", "Sign In", "Validate Sign In", "Clicked on Signin Button");
				//Emp = ManagerRule.getCellData("Employee", i);
				// Call timesheet function
				//timeSheetAK();
//				try{
//
//					if(oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).isDisplayed())
//					{
//						WebElement timesheet=oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp));
//						highlightElement(timesheet);
//						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//						oBrowser.findElement(By.xpath(oUIObj.uiTimeSheet_xp)).click();
//						//Enter Employee number
//						oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//						cSideframe();
//						setText(oUIObj.UIemployeeTS_xp, Emp);
//						controlClick(oUIObj.uiLoad_xp,"Load Button");
//						Thread.sleep(1500);
//						selectReport.ReportStep("Pass", "Load", "Validate Load","Clicked on Load Button");
//					}
//				}catch(Exception e){
//
//					System.out.println("Load Button is not displayed");
//				}
			}
			// Enter Empolyee clock-in information
//			oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//			oBrowser.switchTo().defaultContent();
//			cSideframe();
//			String Day = ManagerRule.getCellData("Day", i);
			// Loading Employee number
			//						Emp = ProtectedSickMax.getCellData("Employee", i);
			/*if (!(Emp.equals(""))) {
				try {
					oBrowser.switchTo().defaultContent();
					oBrowser.switchTo().frame(oBrowser.findElement(By.id("contentFrame")));
					System.out.println("Entered into content frame");
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					// oBrowser.findElement(By.xpath(oUIObj.uiEmployeeAllinput_xp)).sendKeys(Emp);
					System.out.println("Enter Employee Id");
					setText(oUIObj.uiLoadEmptxt_xp, Emp);

					System.out.println("click on Timesheets Load button");
					controlClick(oUIObj.uiLoadH_xp, " Load button");
					Thread.sleep(1500);
					System.out.println("Pass:Employee selected successfully");
					selectReport.ReportStep("Pass", "Employee", "Validate Employee",
							"successfully selected employee" + Emp + " for the TC-" + TCN);
					oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					controlClick(oUIObj.UIEmployeeinfor_xp, "link");

					Calcgrp = ManagerRule.getCellData("Calc Group", i);
					System.out.println("calc " + Calcgrp);
					if (!Calcgrp.equals("")) {
						try {
							System.out.println("Enter emp calc group");
							WebElement calcselect = oBrowser.findElement(By.xpath(oUIObj.UIEmpcalcgrpclk_xp));

							setText(oUIObj.UIEmpcalcgrpclk_xp, Calcgrp);
							Thread.sleep(1500);
							calcselect.sendKeys(Keys.TAB);
							System.out.println("Pass:Callc Group selected successfully");
							
						} catch (Exception ex) {
							System.out.println("Fail: Unable to select Calc group");
							selectReport.ReportStep("Fail", "Calc Group", "Validate Calc Group",
									"Failed to select Callc Group as" + Calcgrp + " for the TC-" + TCN);
						}
					}
					
	
					controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
					selectReport.ReportStep("Pass", "Submit in employee",
							"Validate Submit button in employee details screen",
							"successfully Entered employee details as" + city + country + state + " for the TC-"
									+ TCN);
				}
					
				catch (Exception e) {
				}

			}*/
			
			//emp completed
			//exp = ManagerRule.getCellData("ExpectedMaxBalance", i);

		/*	if (!exp.equals("")) {
				System.out.println("Entered into validation");

				WebElement TargetElement = oBrowser.findElement(By.xpath(oUIObj.UIPS_xp));
				((JavascriptExecutor) oBrowser).executeScript("arguments[0].scrollIntoView(true);", TargetElement);
				oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				String UiValue = TargetElement.getAttribute("value");
				selectReport.ReportStep("Pass", "Validating ProtectedSickMaxBal time", "ProtectedSickMaxBal",
						"Added Time captured successfully-" + UiValue + " for the TC-" + TCN);
				System.out.println("UiExpected ***" + UiValue);
				System.out.println("Result ***" + exp);
				try {
					if (UiValue.equals(exp)) {
						System.out.println("Pass:Successfully displayed time afer added -" + UiValue
								+ " for the TC-" + TCN);
						selectReport.ReportStep("Pass", "Validating ProtectedSickMax time", "ProtectedSickMax",
								"Added Time message displayed successfully-" + UiValue + " for the TC-" + TCN);
						//oBrowser.switchTo().defaultContent();
					} else {
						System.out.println("Fail:Error Time not displayed as per test data.please verify."
								+ " for the TC-" + TCN);
						selectReport.ReportStep("Fail", "ProtectedSickMax Bal", "Added Time should display",
								"Time not displayed as per test data.please verify." + " for the TC-" + TCN);
						//oBrowser.switchTo().defaultContent();
					}
				} catch (Exception e) {
					System.out.println("Fail: in UI and Expected Validation" + " for the TC-" + TCN);
					selectReport.ReportStep("Fail", "Expected and UI SICK time",
							"Should validate SICK time and Expected",
							"Failed to update SICK time.Please verify manually" + " for the TC-" + TCN);
					//oBrowser.switchTo().defaultContent();
				}
			} */
		}
		catch (Exception ex) {
			System.out.println("Fail: Cannot Complete ProtectedSickMaxBal and exception in main" + " for the TC-" + TCN);
			selectReport.ReportStep("Fail", "ProtectedSickMax", "Validate ProtectedSickMax ",
					"Failed to update ProtectedSickMaxBal.Please verify manually" + " for the TC-" + TCN);
		}
//		oBrowser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		controlClick(oUIObj.UIEmpBasicsubmit_xp, "submit button");
//		Thread.sleep(500);
//		cSideframe();
//		controlClick(oUIObj.UIbeforeWeek_xp, "Previous week button");
//		selectReport.ReportStep("Pass", "Previos week in timesheet",
//				"Validate Previos week in timesheet button in employee details screen","successfully Entered Previos week in timesheet for the TC-"+ TCN);
//
//		Deletealledits();
	}
}
	
	
	
	
	
	
	
	
	
	


	/*
	 * TC_screenshotSpencers Created by Deven Date Created: 11/28/18
	 * 
	 * Usage: Capturing Screenshots of the page
	 * 
	 * @Information For the validation purpose
	 */

	public void screenshotSpencers(String Path) throws IOException {
		TakesScreenshot oScn = (TakesScreenshot) oBrowser;
		// Type casting

		// screenshot info will be saved in oScnshot variable (of type file)

		File oScnshot = oScn.getScreenshotAs(OutputType.FILE);

		// Creating a empty image file

		File oDest = new File(Navtextfiles, "//" + Path);

		// Save in a Physical location

		FileUtils.copyFile(oScnshot, oDest);

	}

}


