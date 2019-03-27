package org.api.test;

import java.io.FileInputStream;
import java.util.Properties;

public class TestBase {
	public Properties prp;
	public FileInputStream file;
	final public int STATUS_CODE_200=200;
	final public int STATUS_CODE_201=201;
	final public int STATUS_CODE_400=400;
	final public int STATUS_CODE_404=404;
	final public int STATUS_CODE_500=500;
	
	public TestBase() {
		prp = new Properties();
		try {
			file = new FileInputStream(
					"C:\\Users\\My Pc\\eclipse-workspace_photon\\api.zip_expanded\\src\\main\\java\\com\\qa\\config\\config.properties");
			prp.load(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
