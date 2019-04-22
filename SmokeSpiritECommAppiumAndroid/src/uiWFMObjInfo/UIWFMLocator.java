package uiWFMObjInfo;


public class UIWFMLocator {

//Log in to Infor Wroforce Management
public String uID_xp = "//input[@id = 'loginField']";
public String password_xp = "//input[@id = 'passwordField']";
public String signIn_xp = "//button[@id = 'loginButton']";

// Log out
public String logOut_xp = "//a[text() = 'Log Out']";
public String confirm_xp = "//button/span[text() = 'Yes']";

//Header links
public String uiTimeSheet_xp = "//a[contains(.,'Timesheet')]";
public String uiViewSched_xp = "//a[contains(.,'View Schedule')]";

//Time Sheet Selection
public String uiinputEmployee_xp = "//table[@class='dblTb']/tbody/tr/td/input[@aria-label='Employee DBLookupInput']";
public String uiAll_xp = "//td[3]/button";
public String uiEmployeemglass_xp = "//table/tbody/tr[1]/td[2]/input";//[@type = 'button']";
public String uiEmployeetxt_xp = "//input[@id = 'findFieldTextBox1']";
public String uiFindbtn_xp = "//button[text() = 'Find']";
public String uiChexbox_xp = "//td[1]";//input[@id = 'dblCheckBox_0']";
public String uiSubmit_xp = "//button[3][text() = 'Submit']";

//public String uiEmployeetxtt_xp = "//td[1]/input[@name = 'EMPLOYEE_IDS_label']";
public String uiStartDate_xp = "//td[2]/table/tbody/tr/td[1]/input";
public String uiEndDate_xp = "//td[2]/table/tbody/tr/td[1]/input";
public String uiLoad_xp = "//td/input[@id = 'Load']";
public String uiFindEmp_xp = "//td[3]/a/span";

//Frames
public String uicFrame_name = "contentFrame";
public String uiAggularFr_Id = "angularFrame";

//Header menu
public String uiTimesheetH_xp = "//tr/td[1][contains(text(), 'Timesheet')]";
public String uiLoadEmptxt_xp = "//input[@name = 'LOAD_EMP_ID_label']";
public String uiLoadH_xp = "//input[@id= 'Load']";
public String uiEmpH_xp = "//div[1]/a";



//Clocks
public String uiClock0_xp = "//td[@id = 'c_0_newcell']";
public String uiClock1_xp = "//td[@id = 'c_1_newcell']";
public String uiClock2_xp = "//td[@id = 'c_2_newcell']";
public String uiClock3_xp = "//td[@id = 'c_3_newcell']";
public String uiClock4_xp = "//td[@id = 'c_4_newcell']";
public String uiClock5_xp = "//td[@id = 'c_5_newcell']";
public String uiClock6_xp = "//td[@id = 'c_6_newcell']";

//Detils
public String uiDetailMon_xp = "//td[4]/span[2][text() = 'Mon']";
public String uiDetailTue_xp = "//td[4]/span[2][text() = 'Tue']";
public String uiDetailWed_xp = "//td[4]/span[2][text() = 'Wed']";
public String uiDetailThu_xp = "//td[4]/span[2][text() = 'Thu']";
public String uiDetailFri_xp = "//td[4]/span[2][text() = 'Fri']";
public String uiDetailSat_xp = "//td[4]/span[2][text() = 'Sat']";
public String uiDetailSun_xp = "//td[4]/span[2][text() = 'Sun']";

//Add clock time
public String uiAddNewClickNew_xp = "//span[contains(@class,'new-timeentry-icon')]";
public String uiAddNewClick_xp = "//input[contains(@id,'new_entry_time')]";
public String uiAddBtn_xp = "//input[contains(@id,'new_add')]";
public String uiAddNewClick1_xp = "//input[@id = 'c_1_new_entry_time']";
public String uiAddBtn1_xp = "//input[@id = 'c_1_new_add']";
public String uiAddNewClick2_xp = "//input[@id = 'c_2_new_entry_time']";
public String uiAddBtn2_xp = "//input[@id = 'c_2_new_add']";
public String uiAddNewClick3_xp = "//input[@id = 'c_3_new_entry_time']";
public String uiAddBtn3_xp = "//input[@id = 'c_3_new_add']";
public String uiAddNewClick4_xp = "//input[@id = 'c_4_new_entry_time']";
public String uiAddBtn4_xp = "//input[@id = 'c_4_new_add']";
public String uiAddNewClick5_xp = "//input[@id = 'c_5_new_entry_time']";
public String uiAddBtn5_xp = "//input[@id = 'c_5_new_add']";
public String uiAddNewClick6_xp = "//input[@id = 'c_6_new_entry_time']";
public String uiAddBtn6_xp = "//input[@id = 'c_6_new_add']";
public String uiSubmitClockBtn_xp = "//button[@name = 'SubmitBottomButton']";
public String uiweeks_xp = "//span[@class='dow']";
public String uiaddbtns_xp = "//span[@class='tsclocksui-new-timeentry-icon']";


//Click delete
public String uiShowEditsBtn_xp = "//a[@id = 'SHOW_HIDE_APPLIED_OVERRIDES_LINK']";
//public String uiDeleteOVR_xp = "//inforCheckbox[@name='DELETE_OVR_0']";
public String uiDeleteOVR_xp = "//input[@name = 'DELETE_OVR_0']";
public String uiDeleteOVR1_xp = "//inforCheckbox[@name='DELETE_OVR_1']";
public String uiDeleteOVR2_xp = "//inforCheckbox[@name='DELETE_OVR_2']";

//Pencile Edit button
public String uiPencileEdit_xp = "//div[@id = 'newEdit0']";
public String uiPencileEdit1_xp = "//div[@id = 'newEdit1']";
public String uiPencileEdit2_xp = "//div[@id = 'newEdit2']";
public String uiPencileEdit3_xp = "//div[@id = 'newEdit3']";
public String uiPencileEdit4_xp = "//div[@id = 'newEdit4']";
public String uiPencileEdit5_xp = "//div[@id = 'newEdit5']";
public String uiPencileEdit6_xp = "//div[@id = 'newEdit6']";

//Wb menu items
public String uimenuJob_xp = "//tr[1]/td[1]/div[contains(.,'Job')]";
//public String uimenuEmpHoliSH_xp = "//div[contains(text(),'Employee Holiday')]";

//public String uimenuEmpHoli_xp = "//tr[1]/td[2]/div/table/tbody/tr/td/div[text()='Employee Holiday']";
public String uimenuWrkDetail_xp = "//tr[2]/td[2]/div/table/tbody/tr/td/div[text() = 'Work Detail']";
public String uimenuWorkPre_xp = "//tr[2]/td[1]/div/table/tbody/tr/td/div[text()= 'Work Premium']";
public String uimenuFlatRate_xp = "//tr[3]/td[1]/div/table/tbody/tr/td/div[text() = 'Flat Rate']";
public String uimenuShift_xp = "//tr[4]/td[1]/div/table/tbody/tr/td/div[text() = 'Shift']";
public String uimenuSchedT_xp = "//tr[5]/td[1]/div/table/tbody/tr/td/div[text() = 'Sched Times']";
public String uimenuLTA_xp = "//tr[6]/td[1]/div/table/tbody/tr/td/div[text() = 'LTA']";
public String uimenuSchedTB_xp = "//tr[7]/td[1]/div/table/tbody/tr/td/div[text() = 'Sched Times w Breaks']";
public String uimenuDeleteD_xp = "//tr[8]/td[1]/div/table/tbody/tr/td/div[text() = 'Delete Details']";

//Override
public String uiJob_xp = "//input[@name = 'WRKD_JOB_NAME_label']";
public String uiQty_xp = "//input[@name = 'WRKD_QUANTITY']";
public String uiTeam_xp = "//input[@name = 'WRKD_WBT_NAME_label']";
public String uiSubmitORide_xp = "//div[2]/button[1][text() = 'Submit']";
public String uiCancelORide_xp = "//div[2]/button[2][text() = 'Cancel']";
public String uiHoliday_xp = "//input[@name = 'HOL_NAME_label']";
public String uiScheduleS_xp = "//input[2][@name = 'EMPSKD_ACT_START_TIME_time']";
public String uiScheduleE_xp = "//input[2][@name = 'EMPSKD_ACT_END_TIME_time']";
public String uiScheduleDate_xp = "//input[@name = 'START_TIME_DATE_dummy']";

//Show Edits
public String uiShowEdits_xp = "//a[@id = 'SHOW_HIDE_APPLIED_OVERRIDES_LINK']";
public String uiOT_xp = "//td[3][text() = 'OT1.5']";
public String uiOTTotal_xp = "//tr[3]/td[3][contains(@class,'total')]";
public String uiTotal_xp = "//tr[3]/td[4][contains(@class,'wb_tshourtable_grand dark')]";
public String uideletechckbox_xp = "//tr[contains(@class,'editRow editRow')]/td/nobr/div[@class='wrksOverrideRemove']/span/input[@type='checkbox']";
public String uiHideedits_xp = "//a[text()='Hide Edits']";

//Validate Regular and OTTime
public String uiregtime_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[2]";
public String uiottime_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[3]";
public String uimeallptime_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[3]/td[2]";
public String uiregtimeTotal_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[4]/td[2]";
//Verify time already exists
public String uiexisttimeon00 = "//td[@id='c_0_newcell']/following-sibling::td[2]/div";
public String uiexisttimeoff00 = "//td[@id='c_0_newcell']/following-sibling::td[3]/div";
public String uiexisttimeon01 = "//td[@id='c_1_newcell']/following-sibling::td[2]/div";
public String uiexisttimeoff01 = "//td[@id='c_1_newcell']/following-sibling::td[3]/div";
public String uiexisttimeon02 = "//td[@id='c_2_newcell']/following-sibling::td[2]/div";
public String uiexisttimeoff02 = "//td[@id='c_2_newcell']/following-sibling::td[3]/div";
public String uiexisttimeon03 = "//td[@id='c_3_newcell']/following-sibling::td[2]/div";
public String uiexisttimeoff03 = "//td[@id='c_3_newcell']/following-sibling::td[3]/div";
public String uiexisttimeon04 = "//td[@id='c_4_newcell']/following-sibling::td[2]/div";
public String uiexisttimeoff04 = "//td[@id='c_4_newcell']/following-sibling::td[3]/div";
public String uiexisttimeon05 = "//td[@id='c_5_newcell']/following-sibling::td[2]/div";
public String uiexisttimeoff05 = "//td[@id='c_5_newcell']/following-sibling::td[3]/div";
public String uiexisttimeon06 = "//td[@id='c_6_newcell']/following-sibling::td[2]/div";
public String uiexisttimeoff06 = "//td[@id='c_6_newcell']/following-sibling::td[3]/div";

//Delete clock time
public String uiselecttimeon_xp = "//div[@class='tsclocksui-on']/span[@class='wb_timeedit_label']";
public String uiselecttimeoff_xp = "//div[@class='tsclocksui-off']/span[@class='wb_timeedit_label']";
public String uideletebtn_xp = "//input[@class='inforIconButton delete']";

//OverWrite
public String uieditbtn_xp = "//div[contains(@aria-label,'NewEdit Icon')]";
public String uiemployeeholiday_xp = "//table/tbody/tr/td/div[text()='";
public String uiholidayname_xp = "//input[@name='HOL_NAME_label']";
public String uiholidaysubmit_xp = "//div[@class='wholePage']/button[@name='DailyTypesSubmit']";

//Weekly clock
public String uitogglelnks_xp = "//div[contains(@id,'idb-toggle')]";
public String uitimecodetxt_xp = "//input[@aria-label='Time Code DBLookupInput']";
public String uiweeklyottime_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[3]";
public String uiweekllyunpaindottime_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[4]";
public String uiunpaidlbl_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[1]/td[3]";

//EarlyLate
public String uiearlylateschtime_xp = "//table/tbody/tr/td/div[text()='Sched Times']";
public String uistarttime_xp = "//input[@name='EMPSKD_ACT_START_TIME_time']";
public String uiendtime_xp = "//input[@name='EMPSKD_ACT_END_TIME_time']";
public String uisubmitbtn_xp = "//button[@name='DailyTypesSubmit']";
public String uilatetime_xp = "//td[@class='wb_tshourtable_total'][text()='";
public String uiearlylatelbl_xp = "//tr[3]/td[@class='wb_tshourtable_total']";
public String uiearlylatelbl2_xp = "//tr[4]/td[@class='wb_tshourtable_total']";


//ThankyouMVP
public String uiTimecodeWorkPremium_xp = "//input[@name='WRKP_PREM_TCODE_NAME_label']";
// uiTimecodeWorkPremium_xp=".//input[@name='WRKP_PREM_TCODE_NAME_label']";
public String uiTimecodeInput_xp = "//input[@id='findFieldTextBox1']";
public String uiFind_xp = "//button[text()='Find']";
public String uiMVPDAY_xp = "//span[text()='MVPDAY']";
public String uiThankyou_xp = "//span[text()='THANKYOU']";
public String uIDateRange_xp = "//select[@name='DATE_SELECTION']";
public String uIDates_xp = "//span[@class='textMedium']";
public String uiSubmitinFrame_xp = "//button[text()='Submit']";
public String uiHoursinoverride_xp = "//input[@name='WRKP_MINUTES_time']";
public String uiSubmitTimeSheet_xp = ".//button[@value='Submit']";
public String uiThanktimeTotal_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[3]";
public String uiThanktime_xp = "//tr/td[text()='THANKYOU']/following-sibling::td[1]";
public String uiMVPtime_xp = "//tr/td[text()='MVPDAY']/following-sibling::td[1]";
public String uIEmpDate_xp = "//span[@class='dateRangeControl']";
public String UIStart_xp = "//input[@name='START_DATE_dummy']";
public String UIEnd_xp= "//input[@name='END_DATE_dummy']";
public String UILoadinSubFrm_xp= "//input[@id='Load']";
public String UIissue_xp= "//div[contains(@aria-label,'TS_ISSUES')]";
public String UIissuetooltip_xp= "//div[contains(@id,'inforTooltip')]";
public String uideletechckboxworkP_xp="//div[@class='wrksOverrideRemove']//input[@aria-label='Delete Override']";
public String UIissuemessage_xp = ".wb_issues-body td:nth-child(2)";




//Call in Pay
public String uimenuEmpLTA_xp = "//div[contains(text(),'LTA')]";
public String uiguarregtime_xp = "//tr/td[text()='GUAR']/following-sibling::td[1]";
public String uiguarottime_xp = "//tr/td[text()='GUAR']/following-sibling::td[3]";
public String uicallinpayottime_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[4]";
public String uimenuEmpHoliSH_xp = "//div[contains(text(),'Employee Holiday')]";
public String uiregtimeMA_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[2]"; //UI 1st line
public String uiguarregtimeMA_xp = "//tr/td[text()='GUAR']/following-sibling::td[1]"; //UI 1st line
public String uiguarottimeMA_xp = "//tr/td[text()='GUAR']/following-sibling::td[3]"; //Total GUAR time
public String uicallinpaySTtime_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[4]"; //Total REG
public String uiemployeeholidayMA_xp = "//table/tbody/tr/td/div[text()='Employee Holiday']";
public String uiemployeeholidaysel_xp = "//input[@name='HOL_NAME_label']";
public String uiWrkOT_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[3]";
public String uiGuarOT_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[3]/td[3]";
public String uiSupSun_xp = "//*[@name='WRKS_FLAG1_0']";
public String uiSupMon_xp = "//*[@name='WRKS_FLAG1_1']";
public String uiSupTue_xp = "//*[@name='WRKS_FLAG1_2']";
public String uiSupWed_xp = "//*[@name='WRKS_FLAG1_3']";
public String uiSupThu_xp = "//*[@name='WRKS_FLAG1_4']";
public String uiSupFri_xp = "//*[@name='WRKS_FLAG1_5']";
public String uiSupSat_xp = "//*[@name='WRKS_FLAG1_6']";
public String UIsupAll_xp = "//select[contains(@name,'WRKS_FLAG1')]";

//Common Weekly
public String uiempTcodeLTAET_xp = "//input[@name='OVR_END_TIME_time']";
public String uiempTcodeLTAST_xp = "//input[@name='OVR_START_TIME_time']";
public String uiempTcodeLTA_xp = "//input[@name='LTA_TCODE_NAME_label']";
public String uiempVac_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[4]/td[2]";
public String uiempVacTotal_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[4]/td[2]";
public String uiempOT_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[3]";
public String uiempOT1_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[4]";
public String uiempOT2_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[3]/td[2]";
public String uiempSICKot_xp = "html/body/form/table/tbody/tr/td/div/table/tbody/tr[2]/td[4]";
public String uiempJuryUnpaid_xp = "html/body/form/table/tbody/tr/td/div/table/tbody/tr[4]/td[3]";
public String uiempTHANKDay_xp = "html/body/form/table/tbody/tr/td/div/table/tbody/tr[4]/td[2]";
public String uiempMVPDay_xp = "html/body/form/table/tbody/tr/td/div/table/tbody/tr[4]/td[2]";
public String uiempHOL_xp = "html/body/form/table/tbody/tr/td/div/table/tbody/tr[3]/td[2]";
public String uiemployeeclickonaddpremium_xp = "//div[contains(text(),'Add Premium')]";
public String uiemployeehoursaddindownbutton_xp = "//td[@data-field='PREM_WRKD_MINUTES' and @style='text-align: center;']/input[@class='inforTextInput TimeEditUIInput']";
public String uiTcodeenter_xp = "//tr[@class='newPremiumRow']//input[@aria-label='Time Code DBLookupInput']";


//Mass_edit
public String uiMaintenance_xp = "//a[contains(.,'Maintenance')]";
public String uiMassEditButn_xp = "//div[@id = 'id1_node_id_1_1']";
public String uiMassEditCrtnButn_xp = "//div[@id = 'id1_leaf_id_2_0']";
public String uiMassEditHstryButn_xp = "//div[@id = 'id1_leaf_id_2_1']";
public String UIundoEmp_xp = "//button[@value='Undo']";
public String UIMasschkBoxEmp_xp = "//input[@id='masterCheckbox']";
public String uiEmployeeAll_xp =".//button[@name='EMPLOYEE_IDS_button_ALL']";
public String uiEmployeeAllinput_xp ="//input[@name='EMPLOYEE_IDS_label']";
public String uiEmployeeAllIp_xp =".//input[@name='EMPLOYEE_IDS_label']";
public String uiEmployeeStrtDate_xp ="//input[@name='START_DATE_dummy']";
public String uiEmployeeEndDate_xp ="//input[@name='END_DATE_dummy']";
public String uiEmployeeEdit_xp = "(//*[@class='inforTextInput'])[1]"; //".//input[@name='mass-entry-name']";
public String uiEmployeeAdEd_xp = "//*[@id='editTypeSlct']";
public String uiEmpSchdStrtTym_xp = ".//input[@name='EMPSKD_ACT_START_TIME_time']";
public String uiEmpSchdEndTymTym_xp = "//input[@name='EMPSKD_ACT_END_TIME_time']";
public String uiEmpSchdTymSub_xp = "//button[@value='Submit']";
public String uiEmployeeFreq_xp = "//*[@name='date-repeat-frequency']"; //Name or id same
public String uiEmployHours_xp = "//input[@name='WRKP_MINUTES_time']";
public String uiEmployHol_xp = "//input[@name='WRKP_TCODE_NAME_label']";
public String uiEmployeesearch_xp = "//input[@name = 'QFT_fmyForm_MSE_NAME_0']"; //Emp search text box in mass creation
public String UIFindEmp_xp = "//button[text()='Find']";


//Sick Accurual
public String UiLeftArrow_xp = "//div[contains(@aria-label,'rolldown')]";
public String UIEmployees_xp = "//span[contains(text(),'Employees')]";
public String UIEmpBasicInfor_xp = "//a[contains(text(),'Employee Basic Information - Override')]";
public String UIEmployeeId_xp = "//input[@name='EMP_ID_0_label']";
public String UIEmployeeIdGo_xp = "//button[@value='Go']";
public String UIEmployeeinfor_xp = "//a[contains(text(),'Employee')]";
public String UIEmpcalcgrpclk_xp = "//input[@name='EMP_CALCGRP_NAME_label']";
public String UIEmpcalcgrptext_xp = "//input[@id='findFieldTextBox1']";
public String UIEmpcalcgrptextsel_xp= "//span[@xpath='1']";
public String UIEmpcalcgrp_xp = "//input[@value='NON-EXEMPT COMMON STATES']";
public String UIEmpstate_xp = "//input[@name='EMP_VAL5']";
public String UIEmpCountry_xp = "//input[@name='EMP_VAL6']";
public String UIEmpCity_xp = "//input[@name='EMP_VAL7']";
public String UIEmpBenefit_xp = "//input[@name='EMP_VAL9']";
public String UIEmpBasicsubmit_xp = "//button[@value='Submit']";
public String uiLeftArrowbtn_xp ="//div[contains(@aria-label,'rolldown')]";
public String uiLeftArrowbtnup_xp ="//div[contains(@class,'rollup')]";
public String uIsicktime_xp ="(//span[@class='TimeEditUIView'])[1]";
public String uITymEmpLoad_xp ="(//input[@name = 'LOAD_EMP_ID_label'])[1]";

//ProtectedSickMax
public String UIPSMax_xp ="//input[@name='PROTECTED SICK MAX']";
public String UInextWeek_xp ="//a[@class = 'inforIconButton next']";
public String UIbeforeWeek_xp ="//a[@class = 'inforIconButton previous']";
public String UIemployeeTS_xp = "//input[@name = 'EMPLOYEE_IDS_label']";

//ProtectedSickMaxBal
public String UIPS_xp ="//input[@name='SICK']";

//EmployeeDisplayCard

public String uiHeaderRightClick_xp= "//a[@class='scroll-right']";
public String uiViewSchedemp_xp= ".//div[@id='10025_10304_empCard']";
public String uiViewSchedempinfor_xp= ".//div[@id='10025_10304_empCard_content']";

//BlackOut
public String UIdateInfor_xp = "//span[@id='monthNameInNav']";
public String uiTimeOffCalendar_xp = "//a[contains(text(),'Time Off Calendar')]";
public String UImonths_xp = "//div[@class='yearMonthPick']";
public String UIokCalenderPopup_xp = "//button[@id='buttonOkSelectYearMonth']";
public String UIBlackOut_xp ="//table[@class='calendarTable']//tr[1]//td[3]";
public String UIBlackOutType_xp="//input[@name='topTypeName_label']";
public String UIBlackOutcomment_xp="//textarea[@name='topComment']";
public String UIBlackOutOK_xp="//button[@id='buttonOkNewTimeOff']";
public String UIBlackOutsubmit_xp= "//button[@id='buttonSubmitTimeOff']";
public String UIBlackOutReqValErrPopMsg_xp="//div[@id='timeOffRequestErrorMsgPopup']";
public String UIBlackOutfromdate_xp= "//input[@name='topStartDate_dummy']";
public String UIBlackOutReqValErrPopOk_xp="//button[@id='timeOffRequestErrorOkBtn']";
public String UIclickOnAddedBD_xp="//span[contains(.,'VAC')]";
public String UIclickOnRemoveBD_xp="//button[@id='buttonRemoveNewTimeOff']";

//Non_Protected sick
public String UISeniorityDate_xp = ".//input[@name='EMP_SENIORITY_DATE_dummy']";
public String UIHiredate_xp = ".//input[@name='EMP_HIRE_DATE_dummy']";
public String UISick_xp = ".//input[@name='SICK']";
public String UISickgrant1_xp = ".//input[@name='SICK GRANT 1']";
public String UISickgrant2_xp = ".//input[@name='SICK GRANT 2']";
public String UISickgrant3_xp = ".//input[@name='SICK GRANT 3']";
public String UIoverridestartdate_xp = ".//input[@name='OVR_CURR_START_DATE_dummy']";


//MaxRequest
public String UIToDte_xp= "//input[@name='topEndDate_dummy']";
public String UIleavePopup_xp= "//button/span[text() = 'Leave']";
public String UIReqSick="//span[contains(.,'SICK')]";
public String UIReqBEREAVEMENT="//span[contains(.,'BEREAVEMENT')]";
public String UIReqTHANKYOUDAY="//span[contains(.,'THANK YOU DAY')]";
public String UIReqJury="//span[contains(.,'JURY')]";
public String UIReqUNPAIDDAYOFF="//span[contains(.,'UNPAID DAY OFF')]";

//Vacation Grant
public String UIOvrRdeStrtDate_xp= ".//input[@name='OVR_CURR_START_DATE_dummy']";
public String UI_AvgDailyHours_xp= "//input[@name='EMP_VAL10_time']";
public String UIEmpInitVac_xp = "//input[@name='VACATION']";

//Swipe_test
public String UIEmpAddWork_xp = "//div[contains(text(),'Add Work')]";
public String UIEmpAddHourType_xp = ".//input[@aria-label='Hour Type DBLookupInput']";
public String UIEmpMealMon_xp = ".//input[@name='X10104_label']";
public String UIEmpMealTue_xp = ".//input[@name='X20104_label']";
public String UIEmpMealTWed_xp = ".//input[@name='X30104_label']";
public String UIEmpMealThu_xp = ".//input[@name='X40104_label']";
public String UIEmpMealFri_xp = ".//input[@name='X50104_label']";
public String UIEmpClockType_xp = "//input[contains(@id,'new_type_main')]";
public String UIEmpClockData_xp = "//input[contains(@id,'new_data')]";
public String UIEmpClockTC_xp = "//option[text()='TIMECODE']";

//Manager Rules
public String UIManRulVacOrSick_xp = "//table[@class='wb_tshourtable_table']/tbody/tr[2]/td[2]";



}
