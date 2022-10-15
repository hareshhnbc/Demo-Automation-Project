package com.ka.pojo;

public class TestData {
	
	String Keyword;
	String ObjectName;
	String ObjectType;
	String InputData;
	String Purpose;
	String takeScreenShot;
	
	public String getTakeScreenShot() {
		return takeScreenShot;
	}
	public void setTakeScreenShot(String takeScreenShot) {
		this.takeScreenShot = takeScreenShot;
	}
	public String getKeyword() {
		return Keyword;
	}
	public void setKeyword(String keyword) {
		Keyword = keyword;
	}
	public String getObjectName() {
		return ObjectName;
	}
	public void setObjectName(String objectName) {
		ObjectName = objectName;
	}
	public String getObjectType() {
		return ObjectType;
	}
	public void setObjectType(String objectType) {
		ObjectType = objectType;
	}
	public String getInputData() {
		return InputData;
	}
	public void setInputData(String inputData) {
		InputData = inputData;
	}
	public String getPurpose() {
		return Purpose;
	}
	public void setPurpose(String purpose) {
		Purpose = purpose;
	}
	
	public String toString(){
		return "TestData [Keyword=" + Keyword + ", ObjectName=" + ObjectName
				+ ", ObjectType=" + ObjectType + ", InputData=" + InputData
				+ "]";
	}
	

}
