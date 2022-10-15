package com.ka.pojo;

import java.util.HashMap;
import java.util.Map;

public class TestCaseResult {

	Map<String,String> testCaseResultMap = new HashMap<String,String>();
	static Map<String,Map<String,String>> testSuiteResultMap =new HashMap<String,Map<String,String>>();
	

	public static Map<String, Map<String, String>> getTestSuiteResultMap() {
		return testSuiteResultMap;
	}
	public static void setTestSuiteResultMap(Map<String, Map<String, String>> testSuiteResultMap) {
		TestCaseResult.testSuiteResultMap = testSuiteResultMap;
	}
	/*
	public Map<String, Map<String, String>> getTestSuiteResultMap() {
		return testSuiteResultMap;
	}*/
    /* void setTestSuiteResultMap(Map<String, Map<String, String>> testSuiteResultMap) {
		this.testSuiteResultMap = testSuiteResultMap;
	}*/
	public Map<String, String> getTestCaseResultMap() {
		return testCaseResultMap;
	}
	public void setTestCaseResultMap(Map<String, String> testCaseResultMap) {
		this.testCaseResultMap = testCaseResultMap;
	}
	


	
	
}
