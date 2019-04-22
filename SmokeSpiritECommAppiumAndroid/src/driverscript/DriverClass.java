package driverscript;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Reporting.Report;
import functionalLib.ExcelRead;
import functionalLib.WFMBusinessActions;


public class DriverClass {
	
	WebDriver oBrowser;
	String strURL;
	String brow;
	String devicename;
	String platformname;
	String devc;
	String platformversion;
	String browsertype;
	Report chromeReport, firefoxReport, IEReport,AndroidReport,SafariReport,iOSReport;
	
	@SuppressWarnings("static-access")
	@Test
	public void Chrome() 
	{
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try {
			WFMBusinessActions oBA=new WFMBusinessActions(oBrowser,strURL,devicename,platformname,devc,platformversion,"Chrome",chromeReport);
			ExcelRead oExcel = new ExcelRead(chromeReport.gstrTestDatapath, "TestSuite");
			
			chromeReport.InitializeMainReport();
			for(int i=1;i<=oExcel.rowCount()-1;i++){
				 if(oExcel.getCellData(i, 1).contains("Yes")){
					String sScenarioName = oExcel.getCellData("TestScenario", i);
					System.out.println("Script " + sScenarioName + "  Started"  + " [Chrome]");
					System.out.println("Script Start Time " +  sdf.format(new Date())  + " [Chrome]");
					chromeReport.gbCurrentTest=sScenarioName;
					chromeReport.testNo++;
					chromeReport.InitializeTestRepot();
					chromeReport.stepNo=1;
					chromeReport.ChtestcaseNo=i;
					 try{
						 for(int iCol=2;iCol<=oExcel.columnCount()-1;iCol++){
						 String sAction=oExcel.getCellData(i, iCol );
						 System.out.println("***"+sAction);
						 if(sAction.length()>0){
							 
							 Method oMethodToExecute = oBA.getClass().getMethod(sAction);
							 oMethodToExecute.invoke(oBA);
							
					 	}else{System.out.println("Script End Time " +  sdf.format(new Date())  + " [Chrome]"); break; }
					 }
					 }catch(Exception e){System.out.println("Failed at " + sScenarioName + " [Chrome]");}	
					}
				
			}
			 
				
		} catch (Exception e) {System.out.println(e.getMessage());
		
		}
	}
	
	
	@Test
	public void Firefox() 
	{
	 
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try {
			WFMBusinessActions oBA=new WFMBusinessActions(oBrowser,strURL,devicename,platformname,devc,platformversion,"Firefox", firefoxReport);
			ExcelRead oExcel = new ExcelRead(firefoxReport.gstrTestDatapath, "TestSuite");
			
			firefoxReport.InitializeMainReport();
			for(int i=1;i<=oExcel.rowCount()-1;i++){
				 if(oExcel.getCellData(i, 1).contains("Yes")){
					String sScenarioName = oExcel.getCellData("TestScenario", i);
					System.out.println("Script " + sScenarioName + "  Started"  + " [Firefox]");
					System.out.println("Script Start Time " +  sdf.format(new Date()) + " [Firefox]");
					firefoxReport.gbCurrentTest=sScenarioName;
					firefoxReport.testNo++;
					firefoxReport.InitializeTestRepot();
					firefoxReport.stepNo=1;
					firefoxReport.frtestcaseNo=i;
					 try{
						 for(int iCol=2;iCol<=oExcel.columnCount()-1;iCol++){
						 String sAction=oExcel.getCellData(i, iCol );
						 System.out.println("***"+sAction);
						 if(sAction.length()>0){
							 
							 Method oMethodToExecute = oBA.getClass().getMethod(sAction);
							 oMethodToExecute.invoke(oBA);
							
					 	}else{System.out.println("Script End Time " +  sdf.format(new Date()) ); break; }
					 }
					 }catch(Exception e){System.out.println("Failed at " + sScenarioName + " [Firefox]");}	
					}
				
			}
			 
				
		} catch (Exception e) {System.out.println(e.getMessage());
		
		}
	}
	
	@Test
	public void IE() 
	{
	 
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try {
			WFMBusinessActions oBA=new WFMBusinessActions(oBrowser,strURL,devicename,platformname,devc,platformversion,"IE",IEReport);
			ExcelRead oExcel = new ExcelRead(IEReport.gstrTestDatapath, "TestSuite");
			
			IEReport.InitializeMainReport();
			for(int i=1;i<=oExcel.rowCount()-1;i++){
				 if(oExcel.getCellData(i, 1).contains("Yes")){
					String sScenarioName = oExcel.getCellData("TestScenario", i);
					System.out.println("Script " + sScenarioName + "  Started"  + " [IE]");
					System.out.println("Script Start Time " +  sdf.format(new Date()) );
					IEReport.gbCurrentTest=sScenarioName;
					IEReport.testNo++;
					IEReport.InitializeTestRepot();
					IEReport.stepNo=1;
					IEReport.ietestcaseNo=i;
					 try{
						 for(int iCol=2;iCol<=oExcel.columnCount()-1;iCol++){
						 String sAction=oExcel.getCellData(i, iCol );
						 System.out.println("***"+sAction);
						 if(sAction.length()>0){
							 
							 Method oMethodToExecute = oBA.getClass().getMethod(sAction);
							 oMethodToExecute.invoke(oBA);
							
					 	}else{System.out.println("Script End Time " +  sdf.format(new Date()) + " [IE]"); break; }
					 }
					 }catch(Exception e){System.out.println("Failed at " + sScenarioName+ " [IE]");}	
					}
				
			}
			 
				
		} catch (Exception e) {System.out.println(e.getMessage());
		
		}
	}
	
	@Test
	public void iOS() 
	{
	 
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try {
			WFMBusinessActions oBA=new WFMBusinessActions(oBrowser,strURL,devicename,platformname,devc,platformversion,"iOS",iOSReport);
			ExcelRead oExcel = new ExcelRead(iOSReport.gstrTestDatapath, "TestSuite");
			
			iOSReport.InitializeMainReport();
			for(int i=1;i<=oExcel.rowCount()-1;i++){
				 if(oExcel.getCellData(i, 1).contains("Yes")){
					String sScenarioName = oExcel.getCellData("TestScenario", i);
					System.out.println("Script " + sScenarioName + "  Started"  + " [iOS]");
					System.out.println("Script Start Time " +  sdf.format(new Date()) );
					iOSReport.gbCurrentTest=sScenarioName;
					iOSReport.testNo++;
					iOSReport.InitializeTestRepot();
					iOSReport.stepNo=1;
					
					 try{
						 for(int iCol=2;iCol<=oExcel.columnCount()-1;iCol++){
						 String sAction=oExcel.getCellData(i, iCol );
						 System.out.println("***"+sAction);
						 if(sAction.length()>0){
							 
							 Method oMethodToExecute = oBA.getClass().getMethod(sAction);
							 oMethodToExecute.invoke(oBA);
							
					 	}else{System.out.println("Script End Time " +  sdf.format(new Date()) + " [iOS]"); break; }
					 }
					 }catch(Exception e){System.out.println("Failed at " + sScenarioName+ " [iOS]");}	
					}
				
			}
			 
				
		} catch (Exception e) {System.out.println(e.getMessage());
		
		}
	}

	@Test
	public void Safari() 
	{
	 
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try {
			
			WFMBusinessActions oBA=new WFMBusinessActions(oBrowser,strURL,devicename,platformname,devc,platformversion,"Safari",SafariReport);
			ExcelRead oExcel = new ExcelRead(SafariReport.gstrTestDatapath, "TestSuite");
			
			SafariReport.InitializeMainReport();
			for(int i=1;i<=oExcel.rowCount()-1;i++){
				 if(oExcel.getCellData(i, 1).contains("Yes")){
					String sScenarioName = oExcel.getCellData("TestScenario", i);
					System.out.println("Script " + sScenarioName + "  Started"  + " [Safari]");
					System.out.println("Script Start Time " +  sdf.format(new Date()) );
					SafariReport.gbCurrentTest=sScenarioName;
					SafariReport.testNo++;
					SafariReport.InitializeTestRepot();
					SafariReport.stepNo=1;
					SafariReport.sftestcaseNo=i;
					 try{
						 for(int iCol=2;iCol<=oExcel.columnCount()-1;iCol++){
						 String sAction=oExcel.getCellData(i, iCol );
						 System.out.println("***"+sAction);
						 if(sAction.length()>0){
							 
							 Method oMethodToExecute = oBA.getClass().getMethod(sAction);
							 oMethodToExecute.invoke(oBA);
							
					 	}else{System.out.println("Script End Time " +  sdf.format(new Date()) + " [Safari]"); break; }
					 }
					 }catch(Exception e){System.out.println("Failed at " + sScenarioName+ " [Safari]");}	
					}
				
			}
			 
				
		} catch (Exception e) {System.out.println(e.getMessage());
		
		}
	}

	
	@SuppressWarnings("static-access")
	@Test
	public void Android() 
	{
	 
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try {
			WFMBusinessActions oBA=new WFMBusinessActions(oBrowser,strURL,devicename,platformname,devc,platformversion,"Android",AndroidReport);
			ExcelRead oExcel = new ExcelRead(AndroidReport.gstrTestDatapath, "TestSuite");
			AndroidReport.InitializeMainReport();
			for(int i=1;i<=oExcel.rowCount()-1;i++){
				 if(oExcel.getCellData(i, 1).contains("Yes")){
					String sScenarioName = oExcel.getCellData("TestScenario", i);
					System.out.println("Script " + sScenarioName + "  Started" + " [Android]" );
					System.out.println("Script Start Time " +  sdf.format(new Date()) );
					AndroidReport.gbCurrentTest=sScenarioName;
					AndroidReport.testNo++;
					AndroidReport.InitializeTestRepot();
					AndroidReport.stepNo=1;
					AndroidReport.andrtestcaseNo=i;
					 try{
						 for(int iCol=2;iCol<=oExcel.columnCount()-1;iCol++){
						 String sAction=oExcel.getCellData(i, iCol );
						 System.out.println("***"+sAction);
						 if(sAction.length()>0){
							 
							 Method oMethodToExecute = oBA.getClass().getMethod(sAction);
							 oMethodToExecute.invoke(oBA);
							
					 	}else{System.out.println("Script End Time " +  sdf.format(new Date()) ); break; }
					 }
					 }catch(Exception e){System.out.println("Failed at " + sScenarioName);}	
					}
				
			}
			 
				
		} catch (Exception e) {System.out.println(e.getMessage());
		
		}
	}
	

  @Parameters({"appURL","deviceName","platformName","device","platformVersion"})
  @BeforeTest
  public void initializeDriver(String appURL,String deviceName,String platformName,String device,String platformVersion) throws IOException {
	  
	   	this.strURL=appURL;
		devicename=deviceName;
		platformname=platformName;
		devc=device;
		platformversion=platformVersion;
		chromeReport= new Report("Chrome");
		//AndroidReport=new Report("Android");
		//SafariReport=new Report("Safari");
		//iOSReport=new Report("iOS");
		//firefoxReport= new Report("Firefox");
	  	//IEReport= new Report("IE");
	  	
  }
	 
	@AfterTest
	public void CloseReport()
	{
		
		 
		try{
	        chromeReport.CloseMainReport();
			//firefoxReport.CloseMainReport();
			//IEReport.CloseMainReport();
//			AndroidReport.CloseMainReport();
//			SafariReport.CloseMainReport();
//			iOSReport.CloseMainReport();
			//Utilities.emailHTMLResultsusingSMTP();
			//oBrowser.close();
			}
			catch(Exception e)
			{
			 
			}
	}
	
}
