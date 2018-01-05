package com.cognizant.XMLCompare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.cognizant.XMLCompare.GUI;
import com.cognizant.XMLCompare.Logger;
import com.cognizant.XMLCompare.ResultsSummary;
import com.cognizant.XMLCompare.RunTestCases;
import com.cognizant.XMLCompare.TestParameters;
import com.cognizant.framework.Report.SummaryReport;

public class XMLComparator {
	
	private static List<TestParameters> testinstancestorun;
	private static String overallteststatus;
	private static GUI uiForm = GUI.getInstance();
	private static Logger log = Logger.getInstance();
	private static ResultsSummary results = ResultsSummary.getInstance();
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		uiForm.launch();
		log.initialize();
		testinstancestorun = new ArrayList<>();
		RunTestCases runner = new RunTestCases();
		uiForm.writeLog("Execution started :"+results.getstarrttime());
		log.writeLog("Execution started :"+results.getstarrttime());
		uiForm.writeLog("Getting the test cases from the Run Manager");
		log.writeLog("Getting the test cases from the Run Manager");
		testinstancestorun = runner.gettestinstancestoRun();
		
		uiForm.writeLog("Start comparing the XML(s)...");
		log.writeLog("Start generating the XML(s)...");
		SummaryReport summaryreport = new SummaryReport(results.getReportPath());
		overallteststatus = "Passed";
		
		for (TestParameters testparameter : testinstancestorun) 
		{
			uiForm.writeLog("Starting comparing the XML(s) for "+testparameter.getTestcasename());
			log.writeLog("Starting comparing the XML(s) for "+testparameter.getTestcasename());
			XMLReader reader = new XMLReader(testparameter.getFirstXML());
			XMLReader reader1 = new XMLReader(testparameter.getSecondXML());
			List<String> parseddatafirstXML = new ArrayList<>();
			List<String> parseddatasecondXML = new ArrayList<>();
			uiForm.writeLog("Parsing the first input XML - "+testparameter.getFirstXML());
			log.writeLog("Parsing the first input XML - "+testparameter.getFirstXML());
			parseddatafirstXML = reader.readXML();
			uiForm.writeLog("Parsing the second input XML - "+testparameter.getSecondXML());
			log.writeLog("Parsing the second input XML - "+testparameter.getSecondXML());
			parseddatasecondXML = reader1.readXML();
			
			ExcelAdd data = new ExcelAdd(results.getReportPath()+File.separator+"Report"+"_"+testparameter.getTestcasename()+".xlsx", parseddatafirstXML, parseddatasecondXML);
			uiForm.writeLog("Writing the header row in excel report for "+testparameter.getFirstXML());
			log.writeLog("Writing the header row in excel report for "+testparameter.getFirstXML());
			data.addHeaderfirstXML("FirstXML");
			uiForm.writeLog("Writing the data in excel report for "+testparameter.getFirstXML());
			log.writeLog("Writing the data in excel report for "+testparameter.getFirstXML());
			for (int i = 1; i < parseddatafirstXML.size(); i++) 
			{
				data.inserttoRowfirstXML(i, "FirstXML");
				
			}
			uiForm.writeLog("Writing the header row in excel report for "+testparameter.getSecondXML());
			log.writeLog("Writing the header row in excel report for "+testparameter.getSecondXML());
			data.addHeadersecondXML("SecondXML");
			uiForm.writeLog("Writing the data in excel report for "+testparameter.getSecondXML());
			log.writeLog("Writing the data in excel report for "+testparameter.getSecondXML());
			for (int i = 1; i < parseddatafirstXML.size(); i++) 
			{
					data.inserttoRowsecondXML(i, "SecondXML");
			}
			uiForm.writeLog("Compare between the 2 XMLs");
			log.writeLog("Compare between the 2 XMLs");
			boolean flag = data.compareSheet("FirstXML", "SecondXML");
			
			uiForm.writeLog("Writing data for comparison to excel report");
			log.writeLog("Writing data for comparison to excel report");
			data.writeReport();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			uiForm.writeLog("Summary Report entry for test case - "+testparameter.getTestcasename());
			log.writeLog("Summary Report entry for test case - "+testparameter.getTestcasename());
			summaryreport.createRow();
			summaryreport.testcaseLink(results.getReportPath()+File.separator+"Report"+"_"+testparameter.getTestcasename()+".xlsx", testparameter.getTestcasename());
			summaryreport.infoStatements(new File(testparameter.getFirstXML()).getName());
			summaryreport.infoStatements(new File(testparameter.getSecondXML()).getName());
			if (flag)
			{
				summaryreport.passStatements("Passed");
			}
			else{
				summaryreport.failStatements("Failed");	
				overallteststatus = "Failed";
			}
			
			summaryreport.endRow();
			
		}
		
		summaryreport.createRowSummary();
		summaryreport.addSummarydata(results.getstarrttime());
		summaryreport.addSummarydata(results.getendtime());
		summaryreport.addSummarydata(results.getelapsedtimeinseconds()+ " seconds");
		if (overallteststatus.equalsIgnoreCase("Passed"))
		{
			summaryreport.addSummaryPassedstatus("Passed");

		}
		else{

			summaryreport.addSummaryFailedstatus("Failed");;
		}

		summaryreport.endRowSummary();
		summaryreport.createReport();
		uiForm.writeLog("Summary Report Generated");
		uiForm.writeLog("Execution ends.");
		uiForm.writeLog("Execution ended :"+results.getendtime());
		log.writeLog("Execution ended :"+results.getendtime());
		uiForm.writeLog("Elapsed time :"+results.getelapsedtimeinseconds()+" seconds");
		log.writeLog("Elapsed time :"+results.getelapsedtimeinseconds()+" seconds");
		
		uiForm.closeForm();
		System.exit(0);
		
		
				
				
	}

}
