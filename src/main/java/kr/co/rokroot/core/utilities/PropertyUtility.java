/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.rokroot.core.exceptions.DemoException;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtility {

	protected static InputStream is;
	protected static ObjectMapper om = new ObjectMapper();

	public static Properties getConfigurationFile(Class<?> clazz, String name) {
		Properties prop = new Properties();
		try {
			is = clazz.getClassLoader().getResourceAsStream("props/" + name);
			switch (name.substring(name.lastIndexOf(".") + 1).toLowerCase()) {
				case "yaml":
				case "yml" :
					prop = getYaml(prop);
					break;
				case "properties":
					prop = getProperties(prop);
					break;
				default:
					return null;
			}
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				throw new DemoException("input stream error occurred", e);
			}
		}

		return prop;
	}


	protected static Properties getYaml(Properties prop) {
		try {
			prop = om.readValue(is, Properties.class);
//			prop = new Yaml().loadAs(is, Properties.class);
		} catch (Exception e) {
			throw new DemoException("Property file not found", e);
		}

		return prop;
	}

	protected static Properties getProperties(Properties prop) {
		try {
			prop.load(is);
		} catch (Exception e) {
			throw new DemoException("Property file not found", e);
		}

		return prop;
	}
}