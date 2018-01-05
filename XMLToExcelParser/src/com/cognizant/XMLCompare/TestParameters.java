package com.cognizant.XMLCompare;

public class TestParameters {
	
	private String testcasename;
	private String firstXML;
	private String secondXML;
	
	
	public TestParameters()
	{
		this.setTestcasename("");
		this.setFirstXML("");
		this.setSecondXML("");
	}

	public String getTestcasename() {
		return testcasename;
	}

	public void setTestcasename(String testcasename) {
		this.testcasename = testcasename;
	}

	public String getFirstXML() {
		return firstXML;
	}

	public void setFirstXML(String firstXML) {
		this.firstXML = firstXML;
	}

	public String getSecondXML() {
		return secondXML;
	}

	public void setSecondXML(String secondXML) {
		this.secondXML = secondXML;
	}
	
}
