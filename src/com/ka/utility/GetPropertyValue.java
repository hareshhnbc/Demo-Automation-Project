package com.ka.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetPropertyValue {

	public static Properties propObjects = null;
	public static Properties propParam = null;

	public static Properties getObjectRepository() throws IOException {

		if (propObjects == null) {
			propObjects = new Properties();
			InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + File.separator + "InputData"
					+ File.separator + "Properties" + File.separator + "Objects.Properties");
			propObjects.load(inputStream);
		}

		return propObjects;

	}

	private static synchronized void setPropertyParam(String pathOfPropertyFile) {
		if (propParam != null) {
			return;
		}
		File file = null;
		FileInputStream fileinputstream = null;

		try {
			file = new File(pathOfPropertyFile);
			fileinputstream = new FileInputStream(file);

			if (file.exists()) {
				Properties pro = new Properties();
				pro.load(fileinputstream);
				propParam = pro;
			}
			fileinputstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getParamProperty(String Key) throws IOException {

		String pathForParamProperty = System.getProperty("user.dir") + File.separator + "InputData" + File.separator
				+ "Properties" + File.separator + "Param.properties";

		if (propParam == null) {
			setPropertyParam(pathForParamProperty);
		}
		String propertyValue = null;
		propertyValue = propParam.getProperty(Key);

		return propertyValue;
	}
}
