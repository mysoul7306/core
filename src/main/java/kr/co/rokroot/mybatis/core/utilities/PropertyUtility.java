/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.mybatis.core.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.rokroot.mybatis.core.exceptions.DemoException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtility {

	private static InputStream is;
	private static ObjectMapper om = new ObjectMapper();

	public static Properties getConfigurationFile(Class<?> clazz, String name) {
		Properties prop = new Properties();
		is = clazz.getClassLoader().getResourceAsStream("props/" + name);

		switch (name.substring(name.lastIndexOf(".") + 1).toLowerCase()) {
			case "yaml", "yml" -> prop = getYaml(prop);
			case "properties" -> prop = getProperties(prop);
		}

		try {
			assert is != null;
			is.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return prop;
	}


	private static Properties getYaml(Properties prop) {
		try {
			prop = om.readValue(is, Properties.class);
//			prop = new Yaml().loadAs(is, Properties.class);
		} catch (Exception e) {
			throw new DemoException("Property file not found", e);
		}

		return prop;
	}

	private static Properties getProperties(Properties prop) {
		try {
			prop.load(is);
		} catch (Exception e) {
			throw new DemoException("Property file not found", e);
		}

		return prop;
	}
}