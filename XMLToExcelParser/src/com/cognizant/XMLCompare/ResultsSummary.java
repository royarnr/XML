package com.cognizant.XMLCompare;

import java.io.File;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultsSummary {
	
	private String rootreportpath = "";

	private static String starttime="";
	private static String endtime = "";
	private static Date starttimedate;
	private static Date endtimedate;
	
	
	public static ResultsSummary report = new ResultsSummary();
	
	private ResultsSummary()
	{
		if (report==null)
		{
		
		Format format1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
		Format format = new SimpleDateFormat("dd-MMM-yyyy_HHmmss");
		starttimedate = new Date();
		starttime = format1.format(starttimedate);
		rootreportpath = Paths.get(System.getProperty("user.dir"), "src", "Results","Results"+format.format(new Date())).toString();
		if (!new File(rootreportpath).exists())
		{
			new File(rootreportpath).mkdirs();
//			System.out.println("Results folder created");
		}
		}
		
		
	}
	
	public static ResultsSummary getInstance()
	{
		return report;
	}
	
	public String getReportPath()
	{
		return rootreportpath;
	}
	
	
	public String getendtime()
	{
		endtimedate = new Date();
		endtime = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa").format(endtimedate);
		return endtime;
	}
	
	public String getstarrttime()
	{
		
		return starttime;
	}

	public long getelapsedtimeinseconds()
	{
		return (endtimedate.getTime() - starttimedate.getTime())/1000;
	}
	
	

}
