//'*************************************************************************************************************************
//'	Library Name		:  Execution Listener
//'
//'	Description			: This library contains functions which are part of the generic framework.
//'
//'	Included Functions	:
//'		public static void CaptureScreenShot()
//'		public static void CloseMainReport()
//'		public static void CloseTestReport()
//'		public static void InitializeMainReport()
//'		public static void InitializeTestRepot()
//'		public static void ReportRowNo()
//'		public static void ReportStep(String status, String description, String expected, String actual)
//'		public static void ReportTest(String test)
//'************************************************************************************************************************
package Reporting;


import static org.openqa.selenium.OutputType.BYTES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.maven.surefire.shade.org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Report extends Thread
{
	public  String gbFWPath;
	public  String gbResultName;
	public  int lastDataColumnNumber;
	public  String gbCurrentTestStatus;
	public  String gbCurrentVerifysteptStatus;

	public  String gbCurrentTest;
	public int testNo;
	//public  DateFormat dateFormat;
	public  SimpleDateFormat dateFormat;
	public  int stepNo;
	public  int imageNo;
	public  int noTestsPassed;
	public  int noTestsExecuted;
	public  int noTestsFailed;
	public  int gbTestDataRow;
	public  WebDriver gbDriver;
	public  String gbReturnValue;
	public  String gbClientName;
	public static String stat;
	public static int ChtestcaseNo;
	public static int frtestcaseNo;
	public static int sftestcaseNo;
	public static int ietestcaseNo;
	public static int andrtestcaseNo;
	public static int iostestcaseNo;
	 
	public  Boolean gbBlnScreenshotForPassedStep;
	public  String gbTotalExecTime;
	public  String gbMailTo;
	public  String gbMailFrom;
	public  String gbMailCc;
	public  Properties props;
	
	public  String gstrTestDatapath;
	public  String gstrScreenshotPath;
	public  String Navigationtextfiles;
	public  String sAction;
	public  String strURL;
	public  String strDriverPath;
	public int iSleep;
	public  PrintStream oldoutps = System.out;
	public  File mainFile;
	public  FileOutputStream mainFos;
	public  PrintStream mainOutPs;
	public  File f, fi,fLogoSrc,fLogoDst;
	private  String strTimeStamp;
	public  File indexFile;
	private  Date strTime;
	private  int fntotalsec;
	private  int fntotalmin;
	private  int fntotalhour;
	public String browser;
	
	public Report(String browser)
	{
		try 
		{
			dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
			gstrTestDatapath=new java.io.File(".").getCanonicalPath() + "//src//testData//WFMTestData.xls";
			gstrScreenshotPath = new java.io.File(".").getCanonicalPath()+"//Screenshots";
			Navigationtextfiles = new java.io.File(".").getCanonicalPath() + "\\ScriptFiles";
			this.browser =browser;
            gbFWPath = new java.io.File(".").getCanonicalPath();
 
			System.out.println(gbFWPath);
			 
			gbResultName="Result_" + browser + "_"+dateFormat.format(new Date()).replace("/", "_").replace(" ", "").replace(":", "");;//gbWorkbook.getSheet("Configuration").getCell(1,3).getContents();
	 
			gbCurrentTestStatus = "Pass";
			gbCurrentVerifysteptStatus="Pass";
			testNo=0;
			imageNo=0;
	 
			noTestsPassed=0;
			noTestsFailed=0;
			lastDataColumnNumber=0;
			gbBlnScreenshotForPassedStep=true;
			 
			props= ReadValuesFromTextFile("config.properties");
			gbClientName = props.getProperty("Client_Name").toLowerCase();
			gbMailTo = props.getProperty("EMail_To_SendResults").toLowerCase();
			gbMailFrom = props.getProperty("EMail_From_SendResults").toLowerCase();
			gbMailCc = props.getProperty("EMail_CC_SendResults").toLowerCase();
			
			gbBlnScreenshotForPassedStep = true;
		}
		catch (IOException e) {
			 
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void InitializeMainReport()
	{
		
		try {
			if(System.getProperty("os.name").indexOf("Windows")>=0)
			{
				f = new File(gbFWPath + "\\Results\\" + gbResultName);
				f.mkdir();
		        mainFile = new File(f.getAbsolutePath()+ "\\" + gbResultName + ".html");
		        fi = new File(gbFWPath + "\\Results\\" + gbResultName +"\\Images");
		        fi.mkdir();
		        fLogoSrc = new File(gbFWPath + "\\config\\" + gbClientName + ".jpg");
		        fLogoDst = new File(fi.getAbsolutePath() + "\\" + gbClientName + ".jpg");
		        org.apache.maven.surefire.shade.org.codehaus.plexus.util.FileUtils.copyFile(fLogoSrc, fLogoDst);
		        fLogoSrc = new File(gbFWPath + "\\config\\Spirit.jpg");
		        fLogoDst = new File(fi.getAbsolutePath() + "\\Spirit.jpg");
		        org.apache.maven.surefire.shade.org.codehaus.plexus.util.FileUtils.copyFile(fLogoSrc, fLogoDst);
		        fLogoSrc = new File(gbFWPath + "\\config\\chrome.png");
		        fLogoDst = new File(fi.getAbsolutePath() + "\\chrome.png");
		        FileUtils.copyFile(fLogoSrc, fLogoDst);
		        fLogoSrc = new File(gbFWPath + "\\config\\firefox.png");
		        fLogoDst = new File(fi.getAbsolutePath() + "\\firefox.png");
		        FileUtils.copyFile(fLogoSrc, fLogoDst);
		        fLogoSrc = new File(gbFWPath + "\\config\\ie.png");
		        fLogoDst = new File(fi.getAbsolutePath() + "\\ie.png");
		        FileUtils.copyFile(fLogoSrc, fLogoDst);
		        fLogoSrc = new File(gbFWPath + "\\config\\Android.png");
		        fLogoDst = new File(fi.getAbsolutePath() + "\\Android.png");
		        FileUtils.copyFile(fLogoSrc, fLogoDst);
		        fLogoSrc = new File(gbFWPath + "//config//safari.png");
		        fLogoDst = new File(fi.getAbsolutePath() + "//safari.png");
		        FileUtils.copyFile(fLogoSrc, fLogoDst);
		        fLogoSrc = new File(gbFWPath + "//config//iOS.png");
		        fLogoDst = new File(fi.getAbsolutePath() + "//iOS.png");
		        FileUtils.copyFile(fLogoSrc, fLogoDst);
		        indexFile = new File(gbFWPath + "\\Results\\" + "index.html");
			}
			else
			{
				f = new File(gbFWPath + "/Results/" + gbResultName);
				f.mkdir();
		        mainFile = new File(f.getAbsolutePath()+ "/" + gbResultName + ".html");
		        fi = new File(gbFWPath + "/Results/" + gbResultName +"/Images");
		        fi.mkdir();
		        fLogoSrc = new File(gbFWPath + "/config/" + gbClientName + ".jpg");
		        fLogoDst = new File(fi.getAbsolutePath() + "/" + gbClientName + ".jpg");
		        FileUtils.copyFile(fLogoSrc, fLogoDst);
		        fLogoSrc = new File(gbFWPath + "/config/Spirit.jpg");
		        fLogoDst = new File(fi.getAbsolutePath() + "/Spirit.jpg");
		        FileUtils.copyFile(fLogoSrc, fLogoDst);
		        indexFile = new File(gbFWPath + "/Results/" + "index.html");
			}
	        
	        
	        mainFos = new FileOutputStream(indexFile); //create //new output stream
			mainOutPs = new PrintStream(mainFos); //create new output //stream
			
			mainOutPs.println("<!doctype html>");
			mainOutPs.println("<!-- saved from url=(0016)http://localhost -->");
			mainOutPs.println("<html><head><title>HTML Results</title><script>");
			mainOutPs.println("window.location.href = '"+ gbResultName  +  "/" + gbResultName + ".html"+ "'");
			mainOutPs.println("</script></head><body></body></html>");
			System.setOut(oldoutps);
	        
	        
	        
	        mainFos = new FileOutputStream(mainFile); //create //new output stream
			mainOutPs = new PrintStream(mainFos); //create new output //stream
			System.setOut(mainOutPs); //set the output stream
			mainOutPs.println("<!doctype html>");
			mainOutPs.println("<!-- saved from url=(0016)http://localhost -->");
			mainOutPs.println("<html><head><title>Main Report</title>");
			mainOutPs.println("<style type='text/CSS'>table.main {          width:100%;          border-collapse:collapse;          border: 1;	  font: Trebuchet MS; color:white;     }   table.main th {       font-weight:bold;       text-align:center;	font: Trebuchet MS;	 border:1px solid white;      }   table.main td {	font-family: Trebuchet MS; font-weight:bold;       text-align:center;        border:1px solid white; }   table.sample {          width:100%;          border-collapse:collapse;          border:1;	  font: Trebuchet MS;         }   table.sample tbody {          display:block         }   table.sample th {       text-align:center;	 border:1px solid black;  font-family: Trebuchet MS;    }   table.sample td {       text-align:left;       padding:1px;    font-family: Trebuchet MS;   border:1px solid black; }   .linkspan {              color:black;              background-color:orange;              font-weight:bold;              text-decoration:none;              padding:0 2px;              font-size:1.4em;              margin:right:3px;             }   .vis {         display:block;        }</style>");
			mainOutPs.println("<script type='text/javascript'>    var ELpntr=false;   function hideall()   {      locl = document.getElementsByTagName('tbody');      for (i=0;i<locl.length;i++)      {   var str = locl[i].outerHTML; if(str.indexOf('sample')>-1)     locl[i].style.display='none';      }   }   function showHide(EL,PM)   {      ELpntr=document.getElementById(EL);      if (ELpntr.style.display=='none')      {         document.getElementById(PM).innerHTML=' - ';         ELpntr.style.display='block';      }      else      {         document.getElementById(PM).innerHTML=' + ';         ELpntr.style.display='none';      }   }   function summary(a,b,c,d)   {	document.getElementById('executed').value=a;	document.getElementById('passed').value=b;	document.getElementById('failed').value=c; document.getElementById('totalexectime').value=d;        }   onload=hideall;</script>");
			mainOutPs.println("</head><body>");
			mainOutPs.println("<table border=1  width=100% name='main'><tr BGCOLOR='002060'><td width=10% BGCOLOR='white'><img src='Images\\" + gbClientName + ".jpg'  alt='" + gbClientName + "'></img></td><td width=50%>");
			mainOutPs.println("<table border=1 class='main' width=100% BGCOLOR='002060'><th colspan='2' ><font size='3' face='Trebuchet MS' color='white'> "+browser+" Report </font><div><img src='Images\\"+browser+".png' alt='"+browser+"'></img></div></th></td><tr><td>Number of Tests Executed</td><td><input type='text' id='executed' readonly=true></input></td></tr><tr><td>Number of Tests Passed</td><td><input type='text' readonly=true id='passed'/></td></tr><tr><td>Number of Tests Failed</td><td><input type='text' readonly=true id='failed'/></td></tr><tr><td>Total Execution Time</td><td><input type='text' readonly=true id='totalexectime'/></td></tr></table><td style='text-align:center' width=10% BGCOLOR='white'><img src='Images\\Spirit.jpg'  alt='Spirit'></img></td></tr></table>");
			mainOutPs.println("<table border=1 class='main' width=100%><thead><tr><th BGCOLOR='002060' width =10%> TestCase No</th><th BGCOLOR='002060' width =70%> TestCase ID</th><th BGCOLOR='002060' width =10%>Status</th><th BGCOLOR='002060' width =20%>Start Time</th><th BGCOLOR='002060' width =10%>ExecutionTime</th></tr></thead></table>");
			System.setOut(oldoutps);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public void InitializeTestRepot()
	{
		
		try {
			strTime = new Date();
			lastDataColumnNumber=0;
			gbCurrentTestStatus = "Pass";
			gbCurrentVerifysteptStatus="Pass";
			System.setOut(mainOutPs);
			mainOutPs.println("<table class = 'sample' width=100%><thead>");
			mainOutPs.println("<tr class='vis'>");
			mainOutPs.println("<th width='5%'><a href='#'  onclick=showHide('"+gbCurrentTest+"','"+gbCurrentTest+"span')><span id='"+gbCurrentTest+"span' class='linkspan'> + </span></th><th width='5%'>" +testNo+ "</th><th width='70%'>"+gbCurrentTest+"</th><th width='10%'><input type='text' id='"+gbCurrentTest+"stat'readonly=true size=8 align='middle' style = 'font-weight: bold; border:white;'> </input></font></th><th width='30%'>"+dateFormat.format(new Date())+"</th><th width='10%'><input type='text' id='"+gbCurrentTest+"tottime'readonly=true size=8 align='middle' style = 'font-weight: bold; border:white;'> </input></font></th>");
			mainOutPs.println("</tr></thead></table>");
			mainOutPs.println("<table class='sample' width=100%><tbody id='"+gbCurrentTest+"' name='sample'>");
			mainOutPs.println("<tr BGCOLOR='FFC000'><th width =3%> Step No</th><th width =40%> Description</th><th width =25%> Expected</th><th width =25%> Actual</th><th width =10%>Status</th><th width =15%>Time</th></tr>");
			System.setOut(oldoutps);

			} catch (Exception e) {

			mainOutPs.println("some error");

			}
	}
	
	// Function for thread creation by implementing
		// the Runnable Interface
		public void run()
	    {
	        try
	        {
	        	functionalLib.ExcelRead ex = new functionalLib.ExcelRead(gstrTestDatapath, "Login");
	            // Displaying the thread that is running
	            if(browser.contains("Chrome"))
	              ex.writeToExistingExcel(gstrTestDatapath, "TestSuite",6, ChtestcaseNo, stat);
	            else if(browser.contains("Firefox"))
	  				ex.writeToExistingExcel(gstrTestDatapath, "TestSuite",7, frtestcaseNo, stat);
	  			else if(browser.contains("IE"))
	  				ex.writeToExistingExcel(gstrTestDatapath, "TestSuite",8, ietestcaseNo, stat);
	  			else if(browser.contains("Safari"))
	  				ex.writeToExistingExcel(gstrTestDatapath, "TestSuite",9, sftestcaseNo, stat);
	  			else if(browser.contains("Android"))
	  				ex.writeToExistingExcel(gstrTestDatapath, "TestSuite",10, andrtestcaseNo, stat);
	  			else if(browser.contains("iOS"))
	  				ex.writeToExistingExcel(gstrTestDatapath, "TestSuite",11, iostestcaseNo, stat);
	  			
	            
	 
	        }
	        catch (Exception e)
	        {
	            // Throwing an exception
	            System.out.println ("Exception is caught"+e.getMessage());
	        }
	    }
	public void ReportStep(String status, String description, String expected, String actual) {
		if(actual==null)
			actual="";
		if(actual.toLowerCase().contains("gbReturnValue".toLowerCase()))
		{
			if(gbReturnValue!=null)
				actual=actual.replace("gbReturnValue", gbReturnValue);
			else
				actual=actual.replace("gbReturnValue", "");
		}
		if(description.toLowerCase().contains("gbReturnValue".toLowerCase()))
		{
			if(gbReturnValue!=null)
				description=description.replace("gbReturnValue", gbReturnValue);
			else
				description=description.replace("gbReturnValue", "");
				
		}
			
		if (status.equalsIgnoreCase("pass")){
			gbCurrentTestStatus = "pass";
			if (gbBlnScreenshotForPassedStep)
			{
				if(!browser.contains("Android"))
				  CaptureScreenShot();
				mainOutPs.println( "<tr><td>" + stepNo +  "</td>"+"<td>" + description + "</td>"+"<td>" + expected + "</td>"+"<td>" + actual + "</td>"+"<td><font color='green'><a href=Images/"+ gbCurrentTest + gbCurrentTestStatus + strTimeStamp + ".jpg>" + status + "</a></font></td><td>"+ dateFormat.format(new Date())  +"</td></tr>");
			}
			else{
				if(!browser.contains("Android"))
				  CaptureScreenShot();
				mainOutPs.println( "<tr><td>" + stepNo +  "</td>"+"<td>" + description + "</td>"+"<td>" + expected + "</td>"+"<td>" + actual + "</td>"+"<td><font color='green'>" + status + "</font></td><td>"+ dateFormat.format(new Date())  +"</td></tr>");
			}
		}
		else if (status.equalsIgnoreCase("fail")){
			gbCurrentTestStatus = "fail";
			gbCurrentVerifysteptStatus="fail";
			if(!browser.contains("Android"))
			  CaptureScreenShot();
			mainOutPs.println("<tr><td>" + stepNo +  "</td>"+"<td>" + description + "</td>"+"<td>"+ expected + "</td>"+"<td>" + actual + "</td>"+"<td><font color='red'><a href=Images/"+ gbCurrentTest + gbCurrentTestStatus + strTimeStamp + ".jpg>" + status + "</a></font></td><td>"+ dateFormat.format(new Date()) +"</td></tr>");
			
		}
		else if (status.equalsIgnoreCase("Warning")){
			if(!browser.contains("Android"))
			  CaptureScreenShot();
			System.out.println("<tr><td>" + stepNo +  "</td>"+"<td>" + description + "</td>"+"<td>"+ expected + "</td>"+"<td>" + actual + "</td>"+"<td><font color='red'><a href=Images/"+ gbCurrentTest + gbCurrentTestStatus + strTimeStamp + ".jpg>" + status + "</a></font></td><td>"+ dateFormat.format(new Date()) +"</td></tr>");
			
		}
		else if (status.equalsIgnoreCase("main"))
			System.out.println("<tr BGCOLOR='#F4FBE1'><td>" + stepNo + "</td>"+"<td>" + description + "</td>"+"<td>" + expected + "</td>"+"<td>-" + actual + "</td>"+"<td>-<font color='yellow'>" +  "</font></td><td>"+ dateFormat.format(new Date())+"</td></tr>");
		System.setOut(oldoutps);
		stepNo++;
		try {
			Thread.sleep(Long.valueOf("1000"));
		} catch (NumberFormatException e) {
			 
			e.printStackTrace();
		} catch (InterruptedException e) {
			 
			e.printStackTrace();
		}
	}
	public  void CloseMainReport(){
			mainOutPs.println("<script type='text/javascript'>	summary(" + testNo + "," + noTestsPassed +"," + noTestsFailed +",\"" + gbTotalExecTime + "\");</script>");
			mainOutPs.println("</body></html>");
	}
	public  void ReportRowNo(){
		lastDataColumnNumber=0;
		mainOutPs.println("<tr BGCOLOR='FFE696'>  <td colspan=6><b> Row "+ gbTestDataRow +" </b></td> </tr>");
	}
	public  void CloseTestReport() throws ParseException, InterruptedException{
		Report reobject1 = new Report(browser);
		Report reobject2 = new Report(browser);
		Report reobject3 = new Report(browser);
		Report reobject4 = new Report(browser);
		Report reobject5 = new Report(browser);
		Report reobject6 = new Report(browser);
		String totTime;
        Date endTime = new Date();
		totTime = testDuration(strTime, endTime);
		
		mainOutPs.println("</tbody></table>"); 
		if(gbCurrentTestStatus.equalsIgnoreCase("pass")&&gbCurrentVerifysteptStatus.equalsIgnoreCase("pass"))
		{
			stat="Pass";
			if(browser.contains("Chrome"))
			{
				reobject1.start();
				reobject1.join();
			}
			if(browser.contains("Firefox"))
			{
				reobject2.start();
				reobject2.join();
			}
			if(browser.contains("IE"))
			{
				reobject3.start();
				reobject3.join();
			}
			if(browser.contains("Safari"))
			{
				reobject4.start();
				reobject4.join();
			}
			if(browser.contains("Android"))
			{
				reobject5.start();
				reobject5.join();
			}
			if(browser.contains("iOS"))
			{
				reobject6.start();
				reobject6.join();
			}
			mainOutPs.println("<script type='text/javascript'> document.getElementById('"+gbCurrentTest+"stat').style.color='green';  document.getElementById('"+gbCurrentTest+"stat').value = '" + gbCurrentTestStatus.toUpperCase() + "';</script>");
		}
		else
		{
			stat="Fail";
			gbCurrentTestStatus="Fail";
			if(browser.contains("Chrome"))
			{
				reobject1.start();
				reobject1.join();
			}
			if(browser.contains("Firefox"))
			{
				reobject2.start();
				reobject2.join();
			}
			if(browser.contains("IE"))
			{
				reobject3.start();
				reobject3.join();
			}
			if(browser.contains("Safari"))
			{
				reobject4.start();
				reobject4.join();
			}
			if(browser.contains("Android"))
			{
				reobject5.start();
				reobject5.join();
			}
			if(browser.contains("iOS"))
			{
				reobject6.start();
				reobject6.join();
			}
			mainOutPs.println("<script type='text/javascript'> document.getElementById('"+gbCurrentTest+"stat').style.color='red';  document.getElementById('"+gbCurrentTest+"stat').value = '" + gbCurrentTestStatus.toUpperCase() + "';</script>");
		}
		mainOutPs.println("<script type='text/javascript'> document.getElementById('"+gbCurrentTest+"tottime').style.color='black';  document.getElementById('"+gbCurrentTest+"tottime').value = '" + totTime + "';</script>");
		gbTotalExecTime = TotalExecTime(totTime);
		mainOutPs.println("<script type='text/javascript'>	summary(" + testNo + "," + noTestsPassed +"," + noTestsFailed +",\"" + gbTotalExecTime + "\");</script>");
        
		System.setOut(oldoutps); //for resetting the output stream
	}
	private  String TotalExecTime(String fnactualtime) throws ParseException {
		int fns,fnm,fnh1;
		String returnVal;
	       String[] actualtime = fnactualtime.split(":");
	       fnh1 = Integer.parseInt(actualtime[0]);
	       fnm = Integer.parseInt(actualtime[1]);
	       fns = Integer.parseInt(actualtime[2]);
	       fntotalsec = fntotalsec+fns;
	       if (fntotalsec > 59)
	       {
	    	   fntotalsec = fntotalsec - 60 ;
	 		  fntotalmin = fntotalmin + 1;
	       }
	    	   
	       fntotalmin = fntotalmin + fnm;	
	       if(fntotalmin > 59)
	       {
	    	   fntotalmin = fntotalmin - 60;
	    	   fntotalhour = fntotalhour + 1;
	       }
		  
	       fntotalhour = fntotalhour + fnh1;

	       if(fntotalhour < 10)
		    	  returnVal= "0"+fntotalhour ;
		      else
		    	  returnVal= ""+ fntotalhour ;
	       
	      
	      if(fntotalmin < 10)
	    	  returnVal= fntotalhour + ":0" + fntotalmin;
	      else
	    	  returnVal= fntotalhour + ":" + fntotalmin;
	      
	      if(fntotalsec < 10)
	    	  returnVal= returnVal + ":0" + fntotalsec;
	      else
	    	  returnVal= returnVal + ":" + fntotalsec;
	      
		return returnVal;
	}
	private  String testDuration(Date strTime, Date endTime) {
		 
		long duration  = endTime.getTime() - strTime.getTime()  ;
		return TimeUnit.MILLISECONDS.toHours(duration)+ ":" + (int) ((duration / 1000) / 60) + ":" + (int) ((duration / 1000) % 60);
	}
	public void CaptureScreenShot() {
		
		imageNo++;
		//byte[] bytes = ((TakesScreenshot)gbDriver).getScreenshotAs(BYTES);
		File f=((TakesScreenshot)gbDriver).getScreenshotAs(OutputType.FILE);
		FileOutputStream fos;
		try {
			strTimeStamp = dateFormat.format(new Date()).replace("/", "_").replace(" ", "").replace(":", "");
			//os = new FileOutputStream(gbFWPath + "/Results/" + gbResultName +"/Images/" + gbCurrentTest + gbCurrentTestStatus + strTimeStamp +".jpg");
			//fos.write(bytes);
			FileUtils.copyFile(f, new File(gbFWPath + "/Results/" + gbResultName +"/Images/" + gbCurrentTest + gbCurrentTestStatus + strTimeStamp +".jpg"));
			//fos.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public Properties ReadValuesFromTextFile(String FileName) throws IOException
	{	
		
		FileInputStream objectRepfile = getSeleniumConfig(FileName);
		Properties properties = new Properties();
		properties.load(objectRepfile);		
		return properties;
	}
	
	public FileInputStream getSeleniumConfig(String seleniumConfigFileName) throws FileNotFoundException {

		

        System.getProperty("user.dir");
        FileInputStream configFile=null;
       
                File seleniumPropertyFile = new File(gbFWPath + "/Config/" + seleniumConfigFileName);
                System.out.println("Loading " + seleniumConfigFileName + " from " + seleniumPropertyFile.getAbsolutePath());
                if (seleniumPropertyFile.exists()) {
                    configFile = new FileInputStream(seleniumPropertyFile);
                }
         
        
        if (configFile == null) {
            throw new RuntimeException("Cannot find  config file: " + seleniumConfigFileName);
        }
        return configFile;
    }

}
